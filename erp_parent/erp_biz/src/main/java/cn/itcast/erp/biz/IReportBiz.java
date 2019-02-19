package cn.itcast.erp.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReportBiz {
//销售报表统计
	public List orderReport(Date startDate,Date endDate);
	//某年的每月销售额
	public List<Map<String,Object>> trendReport(int year);
}
