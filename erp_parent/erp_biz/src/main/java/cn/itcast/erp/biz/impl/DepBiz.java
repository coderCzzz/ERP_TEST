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
//��ѯȫ���б�

public List<Dep> getList() {
	return depDao.getList();
}
/**
 * ������ѯ
 */
public List<Dep> getList(Dep dep1,int firstResult,int maxResults) {
	
	return depDao.getList(dep1,firstResult,maxResults);
}

public long getCount(Dep dep1) {
	return depDao.getCount(dep1);
}

}
