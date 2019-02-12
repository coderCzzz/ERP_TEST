package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.biz.IDepBiz;
import cn.itcast.erp.dao.IDepDao;
import cn.itcast.erp.entity.Dep;

public class DepBiz implements IDepBiz {
private IDepDao depDao;

public void setDepDao(IDepDao depDao) {
	this.depDao = depDao;
}
//查询全部列表

public List<Dep> getList() {
	return depDao.getList();
}

}
