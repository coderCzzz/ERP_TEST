package cn.itcast.erp.biz.impl;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;
/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;
	public IEmpDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public ISupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		setBaseDao(ordersDao);
	}

	@Override
	public void add(Orders orders) {
		// TODO Auto-generated method stub 
		orders.setState(Orders.STATE_CREATE);//未审核
		//orders.setType(Orders.TYPE_IN);//采购
		orders.setCreatetime(new Date());
		//计算总金额
		double total=0;
		for(Orderdetail detail:orders.getOrderDetails()){
			total+=detail.getMoney();//累计金额
			detail.setState(Orderdetail.STATE_NOT_IN);//设置未入库
			detail.setOrders(orders);//设置明细对应的订单，
		}
		orders.setTotalmoney(total);//设置总金额
		ordersDao.add(orders);//保存订单 
	}
	@Override
	public List<Orders> getListByPage(Orders t1, Orders t2, Object param, int firstResult, int maxResults) {
		//获取分页结果
		List<Orders> ordersList = super.getListByPage(t1, t2, param, firstResult, maxResults);
		//缓存员工名称,key为员工编号，value为员工编号
		Map<Long,String> empNameMap=new HashMap<Long,String>();
		//缓存供应商名称
		Map<Long,String> supplierNameMap=new HashMap<Long,String>();
		for(Orders o:ordersList){
			o.setCreaterName(getEmpName(o.getCreater(), empNameMap,empDao));
			o.setCheckName(getEmpName(o.getChecker(), empNameMap,empDao));
			o.setStarterName(getEmpName(o.getStarter(), empNameMap,empDao));
			o.setEnderName(getEmpName(o.getEnder(), empNameMap,empDao));
			o.setSupplierName(getSupplierName(o.getSupplieruuid(), supplierNameMap,supplierDao));
		}
		return ordersList;
	}
	/**
	 * 采购订单审核
	 */
	@Override
	public void doCheck(Long uuid, Long empUuid) {
		// TODO Auto-generated method stub
		Orders orders = ordersDao.get(uuid);
		if(!orders.STATE_CREATE.equals(orders.getState())){
			throw new ErpException("该订单已经审核！");
		}
		//更新审核员
		orders.setChecker(empUuid);
		//更新审核时间
		orders.setChecktime(new Date());
		//更新订单状态为已审核
		orders.setState(orders.STATE_CHECK);
		
	}
	/**
	 * 采购确认
	 */
	@Override
	public void doStart(Long uuid, Long empUuid) {
		// TODO Auto-generated method stub
		Orders orders = ordersDao.get(uuid);
		if(!orders.STATE_CHECK.equals(orders.getState())){
			throw new ErpException("该订单已经审核！");
		}
		//更新审核员
		orders.setStarter(empUuid);
		//更新审核时间
		orders.setStarttime(new Date());
		//更新订单状态为已审核
		orders.setState(orders.STATE_START);
		
	}
	@Override
	public void exportById(OutputStream os, Long uuid) throws Exception {
		//创建一个工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		//获取订单
		Orders orders = ordersDao.get(uuid);
		List<Orderdetail> detailList = orders.getOrderDetails();
		String sheetName = "";
		if(Orders.TYPE_IN.equals(orders.getType())){
			sheetName = "采 购 单";
		}
		if(Orders.TYPE_OUT.equals(orders.getType())){
			sheetName = "销 售 单";
		}
		//创建一个工作表
		HSSFSheet sheet = wb.createSheet(sheetName);
		//创建一行,行的索引是从0开始
		HSSFRow row = sheet.createRow(0);
		//创建内容体的单元格的样式
		HSSFCellStyle style_content = wb.createCellStyle();
		style_content.setBorderBottom(BorderStyle.THIN);//下边框
		style_content.setBorderTop(BorderStyle.THIN);//上边框
		style_content.setBorderLeft(BorderStyle.THIN);//左边框
		style_content.setBorderRight(BorderStyle.THIN);//右边框
		//对齐方式：水平居中
		style_content.setAlignment(HorizontalAlignment.CENTER);
		//垂直居中
		style_content.setVerticalAlignment(VerticalAlignment.CENTER);
		//创建内容样式的字体
		HSSFFont font_content = wb.createFont();
		//设置字体名称，相当选中哪种字符
		font_content.setFontName("宋体");
		//设置字体的大小
		font_content.setFontHeightInPoints((short)11);
		style_content.setFont(font_content);
		
		//设置日期格式
		HSSFCellStyle style_date = wb.createCellStyle();
		//把 style_content里样式复制到date_style		
		style_date.cloneStyleFrom(style_content);		
		DataFormat df = wb.createDataFormat();
		style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
		
		//标题样式
		HSSFCellStyle style_title = wb.createCellStyle();
		style_title.setAlignment(HorizontalAlignment.CENTER);
		style_title.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont style_font = wb.createFont();
		style_font.setFontName("黑体");
		style_font.setFontHeightInPoints((short)18);
		//加粗
		style_font.setBold(true);
		style_title.setFont(style_font);
		
		
		
		//合并单元格
		//标题：采购单
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		//供应商
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
		//订单明细
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 3));		
		
		//创建矩阵 11行，4列
		int rowCount = detailList.size() + 9;
		for(int i = 2; i <= rowCount; i++){
			row = sheet.createRow(i);
			for(int j = 0; j < 4; j++){
				//设置单元格的样式
				row.createCell(j).setCellStyle(style_content);
			}
			//row.setHeight((short)500);
		}
		//必须先有创建的行和单元格
		//创建标题单元格
		HSSFCell titleCell = sheet.createRow(0).createCell(0);
		//设置标题样式
		titleCell.setCellStyle(style_title);
		titleCell.setCellValue(sheetName);
		
		sheet.getRow(2).getCell(0).setCellValue("供应商");
		sheet.getRow(3).getCell(0).setCellValue("下单日期");
		sheet.getRow(4).getCell(0).setCellValue("审核日期");
		sheet.getRow(5).getCell(0).setCellValue("采购日期");
		sheet.getRow(6).getCell(0).setCellValue("入库日期");
		sheet.getRow(3).getCell(2).setCellValue("经办人");
		sheet.getRow(4).getCell(2).setCellValue("经办人");
		sheet.getRow(5).getCell(2).setCellValue("经办人");
		sheet.getRow(6).getCell(2).setCellValue("经办人");
		
		sheet.getRow(7).getCell(0).setCellValue("订单明细");
		
		sheet.getRow(8).getCell(0).setCellValue("商品名称");
		sheet.getRow(8).getCell(1).setCellValue("数量");
		sheet.getRow(8).getCell(2).setCellValue("价格");
		sheet.getRow(8).getCell(3).setCellValue("金额");
		
		//设置行高
		//标题的行高
		sheet.getRow(0).setHeight((short)1000);
		//内容体的行高
		for(int i = 2; i <= rowCount; i++){
			sheet.getRow(i).setHeight((short)500);
		}
		//设置列宽
		for(int i = 0; i < 4; i++){
			sheet.setColumnWidth(i, 6000);
		}
		
		//缓存供应商编号与员工的名称, key=供应商的编号，value=供应商的名称
		Map<Long, String> supplierNameMap = new HashMap<Long, String>();
		//设置供应商
		sheet.getRow(2).getCell(1).setCellValue(getSupplierName(orders.getSupplieruuid(), supplierNameMap,supplierDao));
		
		//订单详情, 设置日期
		sheet.getRow(3).getCell(1).setCellStyle(style_date);
		sheet.getRow(4).getCell(1).setCellStyle(style_date);
		sheet.getRow(5).getCell(1).setCellStyle(style_date);
		sheet.getRow(6).getCell(1).setCellStyle(style_date);
		if(null != orders.getCreatetime()){
			sheet.getRow(3).getCell(1).setCellValue(orders.getCreatetime());
		}
		if(null != orders.getChecktime()){
			sheet.getRow(4).getCell(1).setCellValue(orders.getChecktime());
		}
		if(null != orders.getStarttime()){
			sheet.getRow(5).getCell(1).setCellValue(orders.getStarttime());
		}
		if(null != orders.getEndtime()){
			sheet.getRow(6).getCell(1).setCellValue(orders.getEndtime());
		}
		
		
		//缓存员工编号与员工的名称, key=员工的编号，value=员工的名称
		Map<Long, String> empNameMap = new HashMap<Long, String>();		
		//设置经办人
		sheet.getRow(3).getCell(3).setCellValue(getEmpName(orders.getCreater(),empNameMap, empDao));
		sheet.getRow(4).getCell(3).setCellValue(getEmpName(orders.getChecker(),empNameMap, empDao));
		sheet.getRow(5).getCell(3).setCellValue(getEmpName(orders.getStarter(),empNameMap, empDao));
		sheet.getRow(6).getCell(3).setCellValue(getEmpName(orders.getEnder(),empNameMap, empDao));
		
		//设置明细内容
		int index = 0;
		Orderdetail od = null;
		for(int i = 9; i < rowCount; i++){
			od = detailList.get(index);
			row = sheet.getRow(i);
			row.getCell(0).setCellValue(od.getGoodsname());
			row.getCell(1).setCellValue(od.getNum());
			row.getCell(2).setCellValue(od.getPrice());
			row.getCell(3).setCellValue(od.getMoney());
			index++;
		}
		//设置合计
		sheet.getRow(rowCount).getCell(0).setCellValue("合计");
		sheet.getRow(rowCount).getCell(3).setCellValue(orders.getTotalmoney());
		
		//写到输出流里去
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	
	
}
