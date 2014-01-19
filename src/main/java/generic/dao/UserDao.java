package generic.dao;

import generic.domain.User;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends GenericDao<User> {

	public static final String ALIAS = "u";

	@SuppressWarnings("unchecked")
	public List<User> loadData(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		StringBuilder builder = new StringBuilder();
		builder.append("select distinct u from ").append(getEntityClass().getName()).append( " as u " );
		builder.append("where (u.deleted is null or u.deleted is false) ");
		if ( filters.get( "name" ) != null ) {
			builder.append( "and u.name like :name " );
		}
		if ( filters.get( "email" ) != null ) {
			builder.append( "and u.email like :email " );
		}
		
		if ( StringUtils.isNotBlank( sortField ) && sortOrder.equals( SortOrder.ASCENDING ) ) {
			builder.append( " order by u." ).append( sortField ).append( " asc " );
		} else if ( StringUtils.isNotBlank( sortField ) && sortOrder.equals( SortOrder.DESCENDING ) ) {
			builder.append( " order by u." ).append( sortField ).append( " desc " );
		}
		
		Query query = getSession().createQuery( builder.toString() ).setFirstResult( first ).setMaxResults( pageSize ).setReadOnly( true );
		if ( filters.get( "name" ) != null ) {
			query.setParameter( "name", "%" + filters.get( "name" ) + "%" );
		}
		if ( filters.get( "email" ) != null ) {
			query.setParameter( "email", "%" + filters.get( "email" ) + "%" );
		}
		
		return query.list();		
	}

	public int countEntitiesWithFilters(Map<String, String> filters) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) from ").append(getEntityClass().getName()).append(" as ").append(ALIAS);
		builder.append(" where (").append(ALIAS).append(".deleted is false or ").append(ALIAS).append(".deleted is null) ");
		return countWithFilters(builder.toString(), filters);
	}

}
