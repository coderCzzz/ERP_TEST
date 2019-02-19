package cn.itcast.erp.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IReportBiz;

public class ReportAction {
private Date startDate;
private Date endDate;
private IReportBiz reportBiz;
private int year;
public int getYear() {
	return year;
}
public void setYear(int year) {
	this.year = year;
}
public IReportBiz getReportBiz() {
	return reportBiz;
}
public void setReportBiz(IReportBiz reportBiz) {
	this.reportBiz = reportBiz;
}
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
public void orderReport(){
	List reportData = reportBiz.orderReport(startDate, endDate);
	write(JSON.toJSONString(reportData));
}
public void trendReport(){
	List<Map<String, Object>> trendReport = reportBiz.trendReport(year);
	write(JSON.toJSONString(trendReport));
}
public void write(String jsonString){
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setContentType("text/html;charset=utf-8");
	try {
		response.getWriter().write(jsonString);
	} catch (Exception e) {
		// TODO: handle exception
	}
}
}
