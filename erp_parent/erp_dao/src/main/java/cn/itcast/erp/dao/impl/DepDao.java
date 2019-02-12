package cn.itcast.erp.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IDepDao;
import cn.itcast.erp.entity.Dep;

public class DepDao extends HibernateDaoSupport implements IDepDao {

	public List<Dep> getList() {
		return (List<Dep>) getHibernateTemplate().find("from Dep");
	}

}
