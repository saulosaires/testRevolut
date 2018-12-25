package test.revolut.service;

import java.util.List;
import java.util.Optional;

import org.jvnet.hk2.annotations.Contract;

import test.revolut.model.User;

@Contract
public interface UserService {

	public Optional<User> findByUserName(String userName);

	public List<User> findAll();

	public User create(User user);

	public User update(User user);

	public User delete(User user);

	public User findById(Long userId);

}
