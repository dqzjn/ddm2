package cn.zmdx.locker.service.impl;

import cn.zmdx.locker.dao.interfaces.UserDAO;
import cn.zmdx.locker.entity.User;
import cn.zmdx.locker.service.interfaces.UserService;
import cn.zmdx.locker.util.Encrypter;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO;

	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public int addUser(User user) {

		user.setPassword(Encrypter.md5(user.getPassword()));
		return userDAO.save(user);

	}

	@Override
	public boolean verifyUser(User user) {
		String password = userDAO.getPassword(user);
		if (password == null)
			return false;
		if (password.equals(Encrypter.md5(user.getPassword())))
			return true;
		return false;
	}

	public User verifyUser(String loginname, String password) {
		return userDAO.getMatchUser(loginname, password);
	}

	@Override
	public User findUsersById(int userid) {
		return this.userDAO.getUsersById(userid);
	}

	@Override
	public void updateUserInfo(User user) throws Exception {
		try {
			// 更新用户基本信息
			userDAO.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
