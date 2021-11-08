package com.javacruitment.core.users;

import com.javacruitment.common.exceptions.UserNotFoundException;
import com.javacruitment.dao.entities.UserEntity;
import com.javacruitment.dao.users.UserDao;
import com.javacruitment.rest.model.User;
import com.javacruitment.rest.model.UserUpsert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

	private final UserMapper userMapper = new UserMapper();
	private final UserDao userDao;

	@Value("#{'${not-allowed-names.entries}'.split(',')}")
	private final List<String> illegalNames = new ArrayList<>();

	public UUID createUser(UserUpsert userUpsert) {
		UserEntity userEntity = userDao.create(userMapper.map(userUpsert));
		return userEntity.getId();
	}

	public List<User> getAllUsers() {
		return userDao.findAll().stream()
				.map(userMapper::map)
				.collect(Collectors.toUnmodifiableList());
	}

	public User getUser(UUID id) throws UserNotFoundException {
		UserEntity user = userDao.findOrDie(id);
		return userMapper.map(user);
	}

	public void deleteUser(UUID id) throws UserNotFoundException {
		UserEntity userEntity = userDao.findOrDie(id);
		userDao.delete(userEntity);
	}

	public void checkUserExists(UUID id) throws UserNotFoundException {
		userDao.checkExists(id);
	}

    public boolean isCandidateDataIncorrect(UserUpsert candidate) {
		return  userDao.isUsernameAlreadyInUse(candidate.getUsername())
				|| userDao.isEmailAlreadyInUse(candidate.getEmail());
	}

	public boolean isGivenUsernameNotAllowed(String username) {
		return illegalNames.stream()
				.anyMatch(illegalName -> illegalName.equalsIgnoreCase(username));
	}

	public List<User> getAllUsersWhereUsernameContain(String text) {
		return userDao.findAllWhereUsernameContain(text).stream()
				.map(userMapper::map)
				.collect(Collectors.toUnmodifiableList());
	}

}
