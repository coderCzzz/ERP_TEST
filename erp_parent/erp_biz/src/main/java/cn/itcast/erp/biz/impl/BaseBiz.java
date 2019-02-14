package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.dao.IBaseDao;
public class BaseBiz<T> {
	private IBaseDao<T> baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	/**
	 * 条件查询
	 */
	public List<T> getList(T t1,T t2,Object param,int firstResult,int maxResults) {
		
		return baseDao.getList(t1,t2,param,firstResult,maxResults);
	}

	public long getCount(T t1,T t2,Object param) {
		return baseDao.getCount(t1,t2,param);
	}

	public void add(T dep) {
		baseDao.add(dep);
	}
	//删除部门
	public void delete(Long uuid) {
		// TODO Auto-generated method stub
		baseDao.delete(uuid);
	}

	public T get(Long uuid) {	
		return (T) baseDao.get(uuid);
	}

	public void update(T dep) {
		baseDao.update(dep);
	}
}
