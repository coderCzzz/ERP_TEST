// 表格数据初始化
$(function(){
	var url="orders_listByPage?t1.type=1";
	if(Request['oper']=='doCheck'){
		url+='&t1.state=0';
	}
	//如果确认业务，加上state=1，只查询出已审核过的订单
	if(Request['oper'] == 'doStart'){
		url += "&t1.state=1";
	}
	//如果入库业务，加上state=2，只查询出已确认过的订单
	if(Request['oper'] == 'doInStore'){
		url += "&t1.state=2";
	}
	$('#grid').datagrid({
		url:url,
		columns:[[
		            {field:'uuid',title:'编号',width:100},
		  		    {field:'createtime',title:'生成日期',width:100,formatter:formatDate},
		  		    {field:'checktime',title:'审核日期',width:100,formatter:formatDate},
		  		    {field:'starttime',title:'确认日期',width:100,formatter:formatDate},
		  		    {field:'endtime',title:'入库日期',width:100,formatter:formatDate},
		  		    {field:'createrName',title:'下单员',width:100},
		  		    {field:'checkerName',title:'审核员',width:100},
		  		    {field:'starterName',title:'采购员',width:100},
		  		    {field:'enderName',title:'库管员',width:100},
		  		    {field:'supplierName',title:'供应商或客户',width:100},
		  		    {field:'totalmoney',title:'合计金额',width:100},
		  		    {field:'state',title:'状态',width:100,formatter:getState},
		  		    {field:'waybillsn',title:'运单号',width:100}
				]],
				singleSelect:true,
				pagination:true,
				fitColumns:true,
				onDblClickRow:function(rowIndex, rowData){
					//rowIndex， 行的索引
					//rowData， 行里的数据
					//alert(JSON.stringify(rowData));
					//显示详情
					$('#uuid').html(rowData.uuid);
					$('#suppliername').html(rowData.supplierName);
					$('#state').html(getState(rowData.state));
					$('#creater').html(rowData.createrName);
					$('#checker').html(rowData.checkerName);
					$('#starter').html(rowData.starterName);
					$('#ender').html(rowData.enderName);
					$('#createtime').html(formatDate(rowData.createtime));
					$('#checktime').html(formatDate(rowData.checktime));
					$('#starttime').html(formatDate(rowData.starttime));
					$('#endtime').html(formatDate(rowData.endtime));
					//打开窗口
					$('#ordersDlg').dialog('open');
					//加载明细列表
					$('#itemgrid').datagrid('loadData',rowData.orderDetails);
				}
		
	});
	//明细表格
	$('#itemgrid').datagrid({
		columns:[[
            {field:'uuid',title:'编号',width:100},
  		    {field:'goodsuuid',title:'商品编号',width:100},
  		    {field:'goodsname',title:'商品名称',width:100},
  		    {field:'price',title:'价格',width:100},
  		    {field:'num',title:'数量',width:100},
  		    {field:'money',title:'金额',width:100},
  		    {field:'state',title:'状态',width:100,formatter:getDetailState}
		]],
		fitColumns:true,
		singleSelect:true
	}); 
	//添加审核按钮
	if(Request['oper'] == 'doCheck'){
		$('#ordersDlg').dialog({
			toolbar:[{
				text:'审核',
				iconCls:'icon-search',
				handler:doCheck
			}]
		});
	}
	
	//添加确认按钮
	if(Request['oper'] == 'doStart'){
		$('#ordersDlg').dialog({
			toolbar:[{
				text:'确认',
				iconCls:'icon-search',
				handler:doStart
			}]
		});
	}
	//添加入库双击事件
	if(Request['oper'] == 'doInStore'){
		$('#itemgrid').datagrid({
			onDblClickRow:function(rowIndex, rowData){
				//显示数据
				$('#itemuuid').val(rowData.uuid);
				$('#goodsuuid').html(rowData.goodsuuid);
				$('#goodsname').html(rowData.goodsname);
				$('#goodsnum').html(rowData.num);
				//打开入库窗口
				$('#itemDlg').dialog('open');
			}
		});
	}
	
	//入库窗口
	$('#itemDlg').dialog({
		width:300,
		height:200,
		title:'入库',
		modal:true,
		closed:true,
		buttons:[
		   {
			   text:'入库',
			   iconCls:'icon-save',
			   handler:doInStore
		   }
		]
	});
});
//日期格式化
function formatDate(dateValue){
	return new Date(dateValue).Format('yyyy-MM-dd');
}
//获取订单状态
function getState(value){
	switch(value*1){
	case 0:return '未审核';
	case 1:return '已审核';
	case 2:return '已确认';
	case 3:return '已入库';
	}
}
/**
 * 获取订单明细的状态
 * 0=未入库，1=已入库
 * @param value
 */
function getDetailState(value){
	switch(value * 1){
		case 0:return '未入库';
		case 1:return '已入库';
		default: return '';
	}
}
/**
 * 审核
 */
function doCheck(){
	$.messager.confirm('确认', '确认要审核吗？', function(yes){
		if(yes){
		    $.ajax({
		    	url: 'orders_doCheck?id=' + $('#uuid').html(),
		    	dataType: 'json',
		    	type: 'post',
		    	success:function(rtn){
		    		$.messager.alert('提示',rtn.message,'info',function(){
		    			if(rtn.success){
		    				//关闭窗口
		    				$('#ordersDlg').dialog('close');
		    				//刷新表格
		    				$('#grid').datagrid('reload');
		    			}
		    		});
		    	}
		    });  
		}
	});
}

/**
 * 确认
 */
function doStart(){
	$.messager.confirm('确认', '确定要确认吗？', function(yes){
		if(yes){
		    $.ajax({
		    	url: 'orders_doStart?id=' + $('#uuid').html(),
		    	dataType: 'json',
		    	type: 'post',
		    	success:function(rtn){
		    		$.messager.alert('提示',rtn.message,'info',function(){
		    			if(rtn.success){
		    				//关闭窗口
		    				$('#ordersDlg').dialog('close');
		    				//刷新表格
		    				$('#grid').datagrid('reload');
		    			}
		    		});
		    	}
		    });  
		}
	});
}

/**
 * 入库
 */
function doInStore(){
	var formdata = $('#itemForm').serializeJSON();
	if(formdata.storeuuid == ''){
		$.messager.alert('提示','请选择仓库!','info');
		return;
	}
	$.messager.confirm("确认","确认要入库吗？",function(yes){
		if(yes){
			$.ajax({
				url: 'orderdetail_doInStore',
				data: formdata,
				dataType: 'json',
				type: 'post',
				success:function(rtn){
					$.messager.alert('提示',rtn.message,'info',function(){
						if(rtn.success){
							//关闭入库窗口
							$('#itemDlg').dialog('close');
							//设置明细的状态
							$('#itemgrid').datagrid('getSelected').state = "1";
							//刷新明细列
							var data = $('#itemgrid').datagrid('getData');
							$('#itemgrid').datagrid('loadData',data);
							//如果所有明细都 入库了，应该关闭订单详情，并且刷新订单列表
							var allIn = true;
							$.each(data.rows,function(i,row){
								if(row.state * 1 == 0){
									allIn = false;
									//跳出循环
									return false;
								}
							});
							if(allIn == true){
								//关闭详情窗口
								$('#ordersDlg').dialog('close');
								//刷新订单列表
								$('#grid').datagrid('reload');
							}
						}
					});
				}
			});
		}
	});
}