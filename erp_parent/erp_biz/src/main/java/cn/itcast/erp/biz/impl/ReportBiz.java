package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.dao.IReportDao;

public class ReportBiz implements IReportBiz {
	private IReportDao reportDao;	
	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}
	@Override
	public List orderReport(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return reportDao.orderReoport(startDate, endDate);
	}
	//某年的每月销售额
	@Override
	public List<Map<String, Object>> trendReport(int year) {
		//保存每个月份的销售额
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>(12);
		//获取当年每月的销售额
		List<Map<String, Object>> yearData = reportDao.getSumMoney(year);
		//把从db中得到的当年每月销售额转成map集合，用于下面的查缺补漏，因为可能存在某些月份没有销售额的情况
		Map<String,Map<String,Object>> map=new HashMap<String,Map<String,Object>>();
		for (Map<String, Object> m : yearData) {
			map.put((String)m.get("month"), m);
		}
		Map<String,Object> data=null;
		//按12月，对每个月的数据进行封装，最终以List<Map<String,Object>>的形式返回
		for (int i = 0; i < 12; i++) {
			data=map.get(i+"月");
			if(null==data){
				//如果当月没有销售额，则不上当月的月份和数据0
				data=new HashMap<String,Object>();
				data.put("month", i+"月");
				data.put("y",0);
			}
			result.add(data);
		}
		return result;
	}

}
