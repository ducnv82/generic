package generic.service;

import generic.dao.GenericDao;
import generic.domain.BaseEntity;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service(value="genericService")
@Transactional
public class GenericService<T extends BaseEntity> {
	/**
	 * Log variable for all child classes.
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	protected GenericDao<T> genericDao;

	/**
	 * {@inheritDoc}
	 */
	public int deleteByIds(List<Long> ids) {
		return genericDao.deleteByIds(ids);
	}
	
	public int deleteByIds(List<Long> ids, Class<T> clazz) {
		return genericDao.deleteByIds(ids, clazz);
	}	

	/**
	 * {@inheritDoc}
	 */
	public Long countEntities() {
		return genericDao.countEntities();
	}
	
	public Long countEntities(Class<T> clazz) {
		return genericDao.countEntities(clazz);
	}	

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAllNotDeleted() {
		return genericDao.getAllNotDeleted();
	}
	
	public List<T> getAllNotDeleted(Class<T> clazz) {
		return genericDao.getAllNotDeleted(clazz);
	}	

	public List<T> getAll() {
		return genericDao.getAll();
	}
	
	public List<T> getAll(Class<T> clazz) {
		return genericDao.getAll(clazz);
	}

	public void saveOrUpdate(T entity) {
		genericDao.saveOrUpdate(entity);
	}
	
	public T getEntityById(final Long id) {
		return genericDao.getEntityById(id);
	}
	
	public T getEntityById(final Long id, Class<T> clazz) {
		return genericDao.getEntityById(id, clazz);
	}
}