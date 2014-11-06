package cn.zmdx.locker.dao.interfaces;

import java.io.Serializable;

public interface ParentDAO {
	public Object getEntity(Class entityClass, Serializable id);

}
