package test.revolut.repository;

import java.io.Serializable;
import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import test.revolut.exception.RepositoryException;

@Contract
public interface AbstractRepository<T extends Serializable> {


	void setClazz(Class<T> clazzToSet);

	T findOne(long id);

	List<T> findAll();

	T create(T entity) throws RepositoryException;

	T update(T entity) throws RepositoryException;

	T delete(T entity) throws RepositoryException;

	void deleteById(long entityId) throws RepositoryException;

}