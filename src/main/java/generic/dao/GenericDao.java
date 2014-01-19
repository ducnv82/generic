package generic.dao;

import generic.domain.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository(value = "genericDao")
public class GenericDao<T extends BaseEntity> {
	/**
	 * Log variable for all child classes.
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public static final String [] ID_CODE =  new String [] { "id", "code" }; 

	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.strator.iris.common.dao.IGenericDao#get(long)
	 */
	@SuppressWarnings("unchecked")
	public T getEntityById(final Long id) {
		return (T) getSession().get(getEntityClass(), id);
	}

	@SuppressWarnings("unchecked")
	public T getEntityById(final Long id, Class<T> clazz) {
		return (T) getSession().get(clazz, id);
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.strator.iris.common.dao.IGenericDao#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		final Query q = getSession().createQuery("from " + getEntityClass().getName());
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll(Class<T> clazz) {
		final Query q = getSession().createQuery("from " + clazz.getName());
		return q.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.strator.iris.common.dao.IGenericDao#save(T)
	 */
	public Long save(final T entity) {
		return (Long) getSession().save(entity);
	}

	public void saveOrUpdate(final T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	public void merge(final T entity) {
		getSession().merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.strator.iris.common.dao.IGenericDao#delete(T)
	 */
	public void delete(final T entity) {
		getSession().delete(entity);
	}

	public int delete(final Long id) {
		final Query q = getSession().createQuery("delete from " + getEntityClass().getName() + " where id = :id ");
		q.setParameter("id", id);
		return q.executeUpdate();
	}

	public int executeHQLUpdate(final String hql) {
		return getSession().createQuery(hql).executeUpdate();
	}

	public void save(final List<T> entities) {
		for (final T entity : entities) {
			saveVoid(entity);
		}
	}

	private void saveVoid(final T entity) {
		getSession().save(entity);
	}

	public void evict(final T... entities) {
		for (final T entity : entities) {
			getSession().evict(entity);
		}
	}

	public List<?> loadData(final String queryString, final String[] paramNames, final Object[] values, final int firstResult, final int maxResults, final String sortField, final String sortOrder,
			final Map<String, String> filters) {
		final StringBuilder builder = new StringBuilder();
		builder.append(queryString);
		if (sortField != null && "UNSORTED".equalsIgnoreCase(sortOrder.toString())) {
			builder.append(" order by ").append(sortOrder.toString());
		}
		return getData4Paging(builder.toString(), paramNames, values, firstResult, maxResults);
	}

	public List<?> loadData(final String queryString, final int firstResult, final int maxResults, final String sortField, final String sortOrder, final Map<String, String> filters) {
		final StringBuilder builder = buildWhereClause(queryString, filters);
		if (sortField != null && !"UNSORTED".equalsIgnoreCase(sortOrder)) {
			builder.append(" order by ").append(sortField).append(" ").append(sortOrder.equalsIgnoreCase("ASCENDING") ? "ASC" : "DESC");
		}
		return getData4Paging(builder.toString(), filters, firstResult, maxResults);
	}

	private StringBuilder buildWhereClause(final String queryString, final Map<String, String> filters) {
		final StringBuilder builder = new StringBuilder();
		builder.append(queryString);
		if (queryString.indexOf("where") <= 0) {
			builder.append(" where 1 = 1");
		}

		if (filters.containsKey("globalFilter")) {
			builder.append(" and 1 = 0");
			filters.remove("globalFilter");
		}

		final Iterator<String> iter = filters.keySet().iterator();
		String filterKey = null;
		while ( iter.hasNext() ) {
			filterKey = iter.next();
			// If filter by id or code, the application must filter "exact"
			boolean isIdentification = StringUtils.endsWithAny(filterKey.toLowerCase(), GenericDao.ID_CODE);
			builder.append(" ").append( filterKey ).append(isIdentification ? "= ?" : " like ?");
		}
		return builder;
	}

	public int countWithFilters(String queryString, Map<String, String> filters) {
		final StringBuilder builder = buildWhereClause(queryString, filters);
		Long count = (Long) getUniqueResult(builder.toString(), filters);
		return count.intValue();
	}

	public List<?> getData4Paging(final String queryString, final String[] paramNames, final Object[] values, final int firstResult, final int maxResults) {
		final Query query = buildQueryByNamedParam(queryString, paramNames, values);

		setParameters(firstResult, null, maxResults, query);

		return query.list();
	}

	public List<?> getData4Paging(final String queryString,  final Map<String, String> filters, final int firstResult, final int maxResults) {
		final Query query = getSession().createQuery(queryString);

		setParameters(firstResult, filters, maxResults, query);

		return query.setReadOnly(true).list();
	}

	public List<?> getDataList(final String queryString, final String[] paramNames, final Object[] values) {
		final Query query = buildQueryByNamedParam(queryString, paramNames, values);
		return query.list();
	}

	public List<?> getDataList(final String queryString) {
		final Query query = getSession().createQuery(queryString);

		return query.list();
	}

	public Object getUniqueResult(final String queryString, final String[] paramNames, final Object[] values) {
		final Query query = buildQueryByNamedParam(queryString, paramNames, values);
		return query.uniqueResult();
	}

	public Object getUniqueResult(final String queryString, Map<String, String> filters) {
		final Query query = getSession().createQuery(queryString);
		
		setParameters( 0, filters, 1, query );
		return query.uniqueResult();
	}

	/**
	 * Builds the query by named param.
	 * 
	 * @param queryString
	 *            the query string
	 * @param paramNames
	 *            the param names
	 * @param values
	 *            the values
	 * @return the query
	 */
	private Query buildQueryByNamedParam(final String queryString, final String[] paramNames, final Object[] values) {
		final Query query = getSession().createQuery(queryString);
		for (int i = 0; i < paramNames.length; i++) {
			query.setParameter(paramNames[i], values[i]);
		}
		return query;
	}

	/**
	 * Adds the paging param.
	 * 
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @param query
	 *            the query
	 */
	private void setParameters(final int firstResult, final Map<String, String> filters, final int maxResults, final Query query) {
		int index = 0;
		//check filters != null for backward compatibility
		if ( filters != null ) {
			for ( String filterKey : filters.keySet() ) {
				boolean isIdentification = StringUtils.endsWithAny(filterKey.toLowerCase(), GenericDao.ID_CODE);
				query.setParameter( index, isIdentification ? filters.get( filterKey ) : '%' + filters.get( filterKey ) + '%', StandardBasicTypes.STRING);
				index++;
			}
		}
		
		if ( maxResults > 0 ) {
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int deleteByIds(List<Long> ids) {
		StringBuilder builder = new StringBuilder("update ");
		builder.append(getEntityClass().getName()).append(" e set e.deleted = :deleted where id in (:ids)");
		return getSession().createQuery(builder.toString()).setParameter("deleted", true).setParameterList("ids", ids).executeUpdate();
	}

	public int deleteByIds(List<Long> ids, Class<T> clazz) {
		StringBuilder builder = new StringBuilder("update ");
		builder.append(clazz.getName()).append(" e set e.deleted = :deleted where id in (:ids)");
		return getSession().createQuery(builder.toString()).setParameter("deleted", true).setParameterList("ids", ids).executeUpdate();
	}	
	/**
	 * {@inheritDoc}
	 */
	public Long countEntities() {
		StringBuilder builder = new StringBuilder("select count(*) from ").append(getEntityClass().getName());
		builder.append(" where deleted = :deleted or deleted is null");
		return (Long) getSession().createQuery(builder.toString()).setParameter("deleted", false).uniqueResult();
	}
	
	public Long countEntities(Class<T> clazz) {
		StringBuilder builder = new StringBuilder("select count(*) from ").append(clazz.getName());
		builder.append(" where deleted = :deleted or deleted is null");
		return (Long) getSession().createQuery(builder.toString()).setParameter("deleted", false).uniqueResult();
	}	

	@SuppressWarnings("unchecked")
	public List<T> getAllNotDeleted() {
		StringBuilder builder = new StringBuilder("select t from ").append(getEntityClass().getName());
		builder.append(" t where t.deleted is null or t.deleted = :deleted");
		final Query q = getSession().createQuery(builder.toString()).setParameter("deleted", false);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAllNotDeleted(Class<T> clazz) {
		StringBuilder builder = new StringBuilder("select t from ").append(clazz);
		builder.append(" t where t.deleted is null or t.deleted = :deleted");
		final Query q = getSession().createQuery(builder.toString()).setParameter("deleted", false);
		return q.list();
	}	

	public String getUserRole(Long userId) {
		StringBuilder builder = new StringBuilder();
		builder.append("select group_concat(r.name separator ',') as roles from role r");
		builder.append(" join user_role ur on r.id = ur.role_id where");
		builder.append(" ur.user_id = :userId");
		SQLQuery sqlQuery = getSession().createSQLQuery(builder.toString());
		sqlQuery.setParameter("userId", userId);
		return (String) sqlQuery.uniqueResult();
	}

	public void flush() {
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	public T loadNotDeletedEntityById(Long id) {
		StringBuilder builder = new StringBuilder();
		builder.append("from ").append(getEntityClass().getName()).append(" where (deleted is false or deleted is null) and id = :id ");
		return  (T)getSession().createQuery( builder.toString() ).setParameter("id", id).uniqueResult();
	}
}
