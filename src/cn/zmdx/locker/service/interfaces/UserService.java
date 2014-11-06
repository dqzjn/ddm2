package cn.zmdx.locker.service.interfaces;

import cn.zmdx.locker.entity.User;

public interface UserService {
   int addUser(User user);
   boolean verifyUser(User user);
   User findUsersById(int userid);
   /**
	 * 修改用户信息
	 * @throws Exception
	 */
   void updateUserInfo(User user) throws Exception;
   
}
