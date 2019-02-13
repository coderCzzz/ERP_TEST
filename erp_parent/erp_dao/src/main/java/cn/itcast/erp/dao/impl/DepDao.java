package cn.itcast.erp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IDepDao;
import cn.itcast.erp.entity.Dep;

public class DepDao extends HibernateDaoSupport implements IDepDao {

	public List<Dep> getList() {
		return (List<Dep>)  getHibernateTemplate().find("from Dep");
	}
	/**
	 * 条件过滤
	 */
	public List<Dep> getList(Dep dep1,Dep dep2,Object param,int firstResult,int maxResults) {
		DetachedCriteria dc = this.getDetachedCriteria(dep1);
		List<Dep> list = (List<Dep>) this.getHibernateTemplate().findByCriteria(dc,firstResult,maxResults);
		return list;
	}
	public long getCount(Dep dep1,Dep dep2,Object param) {
		DetachedCriteria dc = this.getDetachedCriteria(dep1);
		dc.setProjection(Projections.rowCount());
		return (Long) this.getHibernateTemplate().findByCriteria(dc).get(0);
	}
	//封装离线QBC
	private DetachedCriteria getDetachedCriteria(Dep dep1){
		DetachedCriteria dc=DetachedCriteria.forClass(Dep.class);
		if(dep1!=null){
			if(dep1.getName()!=null&&dep1.getName().trim().length()>0){
				dc.add(Restrictions.ilike("name", dep1.getName(), MatchMode.ANYWHERE));
			}
			if(dep1.getTele()!=null&&dep1.getTele().trim().length()>0){
				dc.add(Restrictions.ilike("tele", dep1.getTele(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	/**
	 * 新增部门
	 */
	public void add(Dep dep) {
		this.getHibernateTemplate().save(dep);
	}
	/**
	 * 删除部门
	 */
	public void delete(Long uuid) {
		Dep dep = this.getHibernateTemplate().get(Dep.class, uuid);
		this.getHibernateTemplate().delete(dep);
	}

}
