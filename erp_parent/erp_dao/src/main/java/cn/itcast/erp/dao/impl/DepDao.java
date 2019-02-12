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
	 * Ìõ¼þ¹ýÂË
	 */
	public List<Dep> getList(Dep dep1,int firstResult,int maxResults) {
		DetachedCriteria dc=DetachedCriteria.forClass(Dep.class);
		if(dep1!=null){
			if(dep1.getName()!=null&&dep1.getName().trim().length()>0){
				dc.add(Restrictions.ilike("name", dep1.getName(), MatchMode.ANYWHERE));
			}
			if(dep1.getTele()!=null&&dep1.getTele().trim().length()>0){
				dc.add(Restrictions.ilike("tele", dep1.getTele(), MatchMode.ANYWHERE));
			}
		}
		List<Dep> list = (List<Dep>) this.getHibernateTemplate().findByCriteria(dc,firstResult,maxResults);
		return list;
	}
	public long getCount(Dep dep1) {
		DetachedCriteria dc=DetachedCriteria.forClass(Dep.class);
		if(dep1!=null){
			if(dep1.getName()!=null&&dep1.getName().trim().length()>0){
				dc.add(Restrictions.ilike("name", dep1.getName(), MatchMode.ANYWHERE));
			}
			if(dep1.getTele()!=null&&dep1.getTele().trim().length()>0){
				dc.add(Restrictions.ilike("tele", dep1.getTele(), MatchMode.ANYWHERE));
			}
		}
		dc.setProjection(Projections.rowCount());
		return (Long) this.getHibernateTemplate().findByCriteria(dc).get(0);
	}

}
