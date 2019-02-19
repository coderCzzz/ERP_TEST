$(function(){
	//表格初始化
	$('#grid').datagrid({
		url:'report_orderReport',
		columns:[[
		   {field:'name',title:'商品类型',width:100},       
		   {field:'y',title:'销售额',width:100},       
		]],
		singleSelect:true,
		onLoadSuccess:function(data){
			showChart(data.rows);
		}
	});
	//为查询按钮绑定事件
	$('#btnSearch').bind('click',function(){
		var formdata=$('#searchForm').serializeJSON();
		console.log(formdata);
		console.log(JSON.stringify(formdata));
		if(formdata.endDate!=''){
			formdata.endDate=formdata.endDate+" 23:59:59";
		}
		$('#grid').datagrid('load',formdata);
	});
});
//画饼图
function showChart(data){
	$('#pieChart').highcharts({
		//图表基本属性
		chart:{
			plotBackgroundColor:null,//区域背景颜色
			plotBorderWidth:null,//区域边框宽度
			plotShadow:false,//区域阴影
			type:'pie'//图表类型
		},
		title:{
			text:'销售统计'
		},
		tooltip:{//工具提示
			pointFormat:'{series.name}:<b>{point,percentage:.1f}%</b>'
		},
		plotOptions:{//区域选项
			pie:{
				allowPointSelect:true,//点击区域后选择
				cursor:'pointer',//光标类型
				dataLabels:{//数据标签
					enabled:true,
					formmat:'<b>{point.name}</b>:{point.percentage:.1f}%'
				},
				showInLegend:true//是否显示图例
			}
		},
		series:[{//数据组
			name:"比例",//名字
			colorByPoint:true,
			data:data
		}]
	});
}