package cn.itcast.erp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReportDao {
/**
 * 报表数据访问接口
 */
public List orderReoport(Date startDate,Date endDate);
/**
 * 统计某年中每个月的销售额
 */
public List<Map<String,Object>> getSumMoney(int year);
}
