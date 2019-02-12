package cn.itcast.erp.dao;
import java.util.List;
import cn.itcast.erp.entity.Dep;
public interface IDepDao {
List<Dep> getList();
//分页与过滤
List<Dep> getList(Dep dep1,int firstResult,int maxResults);
//计算总记录数
long getCount(Dep dep1);
}
