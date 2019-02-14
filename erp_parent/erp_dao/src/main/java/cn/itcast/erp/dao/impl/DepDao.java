package cn.itcast.erp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IDepDao;
import cn.itcast.erp.entity.Dep;

public class DepDao extends BaseDao<Dep> implements IDepDao {
	@Override
	public DetachedCriteria getDetachedCriteria(Dep dep1) {
		// TODO Auto-generated method stub
		DetachedCriteria dc=DetachedCriteria.forClass(Dep.class);
		if(null != dep1){
			//�Ƿ����벿������
			if(null != dep1.getName() && dep1.getName().trim().length() > 0){
				dc.add(Restrictions.like("name", dep1.getName(), MatchMode.ANYWHERE));
			}
			//�Ƿ����벿�ŵĵ绰
			if(null != dep1.getTele() && dep1.getTele().trim().length() > 0){
				dc.add(Restrictions.like("tele", dep1.getTele(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}	
}
