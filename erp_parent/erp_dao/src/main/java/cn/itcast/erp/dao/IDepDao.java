package cn.itcast.erp.dao;
import java.util.List;
import cn.itcast.erp.entity.Dep;
public interface IDepDao {
List<Dep> getList();
//分页与过滤
List<Dep> getList(Dep dep1,Dep dep2,Object param,int firstResult,int maxResults);
//计算总记录数
long getCount(Dep dep1,Dep dep2,Object param);
//新增部门
void add(Dep dep);
//删除部门
void delete(Long uuid);
//获取单列部门信息
Dep get(Long uuid);
//修改部门
void update(Dep dep);
}
