package cn.zmdx.locker.dao.impl;

import java.util.List;
import cn.zmdx.locker.dao.interfaces.UserDAO;
import cn.zmdx.locker.entity.User;
import cn.zmdx.locker.util.Encrypter;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class UserDAOImpl extends ParentDAOImpl implements UserDAO {

	public UserDAOImpl(HibernateTemplate template) {
		super(template);
		// TODO Auto-generated constructor stub
	}

	public UserDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int save(User user) {
		// TODO Auto-generated method stub
		template.save(user);
		return user.getUserid();
	}

	@Override
	public String getPassword(User user) {
		// TODO Auto-generated method stub
		List<String> password = template
				.find("select password from User where worker_no=?",
						user.getUserid());
		if (password.size() > 0) {
			return password.get(0);
		}
		return null;
	}

	@Override
	public User get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getMatchUser(String loginname, String password) {
		String sql = "from User where loginname = '" + loginname
				+ "' and password = '" + Encrypter.md5(password) + "'";
		Query query = getSession().createQuery(String.valueOf(sql));
		List<User> list = query.list();
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	// 通过主键id查询用户信息
	public User getUsersById(int userid) {
		User users = (User) super.getHibernateTemplate()
				.get(User.class, userid);
		return users;
	}

	public void update(Object object) {
		this.getHibernateTemplate().update(
				this.getHibernateTemplate().merge(object));
	}
}
