package cn.itcast.erp.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IBaseDao;

public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T>{
	private Class<T> entityClass;
	public BaseDao(){
		Type baseDaoClass = getClass().getGenericSuperclass();
		ParameterizedType pType=(ParameterizedType)baseDaoClass;
		Type[] types = pType.getActualTypeArguments();
		Type targetType=types[0];
		entityClass=(Class<T>)targetType;
	}
	/**
	 * 条件过滤
	 */
	public List<T> getList(T t1,T t2,Object param,int firstResult,int maxResults) {
		DetachedCriteria dc = this.getDetachedCriteria(t1);
		List<T> list = (List<T>) this.getHibernateTemplate().findByCriteria(dc,firstResult,maxResults);
		return list;
	}
	public long getCount(T t1,T t2,Object param) {
		DetachedCriteria dc = this.getDetachedCriteria(t1);
		dc.setProjection(Projections.rowCount());
		return (Long) this.getHibernateTemplate().findByCriteria(dc).get(0);
	}
	//封装离线QBC,由子类实现
	public DetachedCriteria getDetachedCriteria(T t1){
		return null;
	}
	/**
	 * 新增部门
	 */
	public void add(T t) {
		this.getHibernateTemplate().save(t);
	}
	/**
	 * 删除部门
	 */
	public void delete(Long uuid) {
		T t = this.getHibernateTemplate().get(entityClass, uuid);
		this.getHibernateTemplate().delete(t);
	}
	/**
	 * 获取单列部门信息
	 */
	public T get(Long uuid) {
		return getHibernateTemplate().get(entityClass, uuid);
	}
	//修改部门
	public void update(T t) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(t);
	}
}
