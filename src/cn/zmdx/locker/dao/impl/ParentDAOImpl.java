package cn.zmdx.locker.dao.impl;

import java.io.Serializable;

import cn.zmdx.locker.dao.interfaces.ParentDAO;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ParentDAOImpl extends HibernateDaoSupport  implements ParentDAO  {
	HibernateTemplate template;

	public ParentDAOImpl(HibernateTemplate template) {
		this.template = template;
	}
	public ParentDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object getEntity(Class entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return template.get(entityClass, id);
	}

}
