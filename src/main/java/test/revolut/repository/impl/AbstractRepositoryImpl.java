package test.revolut.repository.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.jvnet.hk2.annotations.Contract;

import test.revolut.exception.RepositoryException;
import test.revolut.repository.AbstractRepository;

@Contract
public abstract class AbstractRepositoryImpl <T extends Serializable> implements Serializable, AbstractRepository<T> {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public static final EntityManager entityManager;
	private Class<T> clazz;

	static {
		entityManager = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
	}


	@Override
	public final void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}


	@Override
	public T findOne(long id) {
		return entityManager.find(clazz, id);
	}

	 

	@Override
	public List<T> findAll() {
		 return entityManager.createQuery("from " + clazz.getName()).getResultList();

	}


	@Override
	public T create(T entity) throws RepositoryException {
		EntityTransaction t = null;
		try {
			t = entityManager.getTransaction();
			t.begin();
			entityManager.persist(entity);
			entityManager.flush();
			t.commit();
			return entity;
		} catch (Exception e) {

			if (t != null)
				t.rollback();

			throw new RepositoryException("persist Error: "+entity.toString(),e);
		}

	}


	@Override
	public T update(T entity) throws RepositoryException {
		EntityTransaction t = null;
		try {
			t = entityManager.getTransaction();
			t.begin();
			entityManager.merge(entity);
			entityManager.flush();
			t.commit();
			return entity;
		} catch (Exception e) {

			if (t != null)
				t.rollback();

			throw new RepositoryException("merge Error:"+entity.toString(),e);
		}

	}

	@Override
	public T delete(T entity) throws RepositoryException {
		EntityTransaction t = null;
		try {
			t = entityManager.getTransaction();
			t.begin();
			entityManager.remove(entity);
			entityManager.flush();
			t.commit();
			return entity;
		} catch (Exception e) {

			if (t != null)
				t.rollback();

			throw new RepositoryException("remove Error:"+entity.toString(),e);
		}

	}
	

	@Override
	public void deleteById(long entityId) throws RepositoryException {
		T entity = findOne(entityId);
		delete(entity);
	}

}