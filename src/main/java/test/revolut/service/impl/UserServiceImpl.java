package test.revolut.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import test.revolut.exception.RepositoryException;
import test.revolut.model.User;
import test.revolut.repository.impl.UserRepository;
import test.revolut.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private static Logger LOG = Logger.getLogger(UserService.class);
	
	@Inject
	UserRepository userRepository;
	
	
	@Override
	public Optional<User> findByUserName(String userName) {
		 
		return userRepository.findByUserName(userName);
	}

	@Override
	public List<User> findAll() {
		 
		return userRepository.findAll();
	}

	@Override
	public User create(User user) {
		 
		try {
			return userRepository.create(user);
		} catch (RepositoryException e) {
			LOG.fatal(e.getCause());
			 
		}
		
		return null;
	}

	@Override
	public User update(User user) {
		try {
			return userRepository.update(user);
		} catch (RepositoryException e) {
			LOG.fatal(e.getCause());
			 
		}
		
		return null;
	}

	@Override
	public User delete(User user) {
		 
		try {
			return userRepository.delete(user);
		} catch (RepositoryException e) {
			LOG.fatal(e.getCause());
		}
		
		return null;
	}

	@Override
	public User findById(Long userId) {
		 
		return userRepository.findOne(userId);
	}

}
