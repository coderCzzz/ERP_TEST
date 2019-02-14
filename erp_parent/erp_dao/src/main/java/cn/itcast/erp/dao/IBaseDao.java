package cn.itcast.erp.dao;

import java.util.List;

public interface IBaseDao<T> {
	//分页与过滤
	List<T> getList(T t1,T t2,Object param,int firstResult,int maxResults);
	//计算总记录数
	long getCount(T t1,T t2,Object param);
	//新增部门
	void add(T dep);
	//删除部门
	void delete(Long uuid);
	//获取单列部门信息
	T get(Long uuid);
	//修改部门
	void update(T t);
}
