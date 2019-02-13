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
public List<Dep> getList(Dep dep1,Dep dep2,Object param,int firstResult,int maxResults) {
	
	return depDao.getList(dep1,dep2,param,firstResult,maxResults);
}

public long getCount(Dep dep1,Dep dep2,Object param) {
	return depDao.getCount(dep1,dep2,param);
}

public void add(Dep dep) {
	depDao.add(dep);
}
//ɾ������
public void delete(Long uuid) {
	// TODO Auto-generated method stub
	depDao.delete(uuid);
}

}
