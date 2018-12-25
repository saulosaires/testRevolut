package test.revolut.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jvnet.hk2.annotations.Service;

import test.revolut.model.User;

@Service
public class UserRepository extends AbstractRepositoryImpl<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1358843609602731648L;

	private static final String USERNAME = "userName";
	
	public UserRepository() {
		setClazz(User.class);

	}

	public Optional<User> findByUserName(String userName) {
		
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		Root<User> m = query.from(User.class);
		query.select(m);
		
		query.where(cb.equal(m.get(USERNAME),userName ));
 
		List<User> list = entityManager.createQuery(query).setMaxResults(1).getResultList();
		
		return list.stream().findFirst();
	}

}