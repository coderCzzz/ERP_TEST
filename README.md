[TOC]

# 知识点总结

### day02

#### (一)easyUI

1. datagrid属性和方法

- pagination开启分页栏。并会给后台带page和row两个参数。后台的返回格式有要求{total：total，rows：[]}
- getData方法：获取表格数据{total：10，rows：[]}
- toolbar：加添加之类的按钮的
- reload：刷新数据，通常用于增删改后刷新表格数据
- 列属性：formatter 比如把列转换为相应的<a >

2. messager消息窗口

- alert：消息提醒，参数：1 窗口标题 2：提示信息
- confirm：询问对话框 参数：1 窗口标题  2：提示信息  3：回调函数  

3. form表单

- load:加载数据，通常用于修改数据前读取记录信息
- clear：清空表单数据，通常用于增加数据前清空表单数据

#### (二)json&js

- `JSON.stringfy`：将表单对象转化为json字符串

  fastjson：`JSON.toJsonString`：把对象转化为json字符串

  `JSON.praseObject`:把json字符串转化为map

#### (三)hibernate

1. 离线QBC查询：不需要session就可以创建

2. 分页查询

3. 投影查询

4. 序列生成策略的设置
### day03
#### (一)easyui
1. 格式化器：`formatter:function(value,row,index)`  
`row`表示当前列的对象数据，value代表返回的字段值
2. 日期输入控件`class="easy-databox"`
3. 下拉框`class="easyui-combobox" data-options="
url:'',valueField:'提交的key'，textField：'下拉显示的key'"`
4. 验证控件：`class="easyui-validatebox" data-options="required:true"`  
`validType`:验证类型  
`missMessage`:文本框未填写时的提示信息
`invalidMessage`：验证失败出现的信息
5. form的validate方法：校验表单的所有控件是否均已通过校验
6. `numberbox`:数字框
#### (二)fastJSON
- 对json字符串进行日期格式化
`JSON.toJSONStringWithDateFormat(obj,"yyyy-MM-dd")`
#### (三)hibernate
1. 多对一关联的配置
`<many-to-one></many-to-one>`
2. 删除一的一方，导致多的一方的列表无法显示  
解决方案：1.添加外键约束 2.建立级联删除
### day04--菜单功能
1. 树状数据结构的设计  
menu表中加一个表示上一级id的字段，通过自关联，实现菜单的查询
2. shiro MD5加密，
salt的概念，还有散列次数参数
3. 静态页面传参
通过一个request.js插件，供应商和客户的字段一样，因此我们添加一个type字段来区分
###day05--采购功能
#### 1.业务分析
1. 采购业务--审核业务
订单与订单明细：一对多。
订单状态：0未审核，1已审核 2已确认 3已结束
采购员采购订单--采购经理进行审核之后--采购员进行采购确认--入库
2. 订单应该有的字段
- 生成日期、审核日期、确认日期、出入库事件、订单类型、下单员、审核员、采购员、库管员、供应商及客户编号、合计金额、订单状态、运单号、有一个字段对应订单明细
3. 订单明细
- 编号、商品编号、名称、价格、数量、金额、结束日期、库管员、仓库编号、明细状态：出库入库、订单编号
4. 保存订单的时候，保存订单明细？--hibernate级联
#### 2.Easyui
1. Datagrid
- ==方法==
- `appendRow`追加一个新行。新行将被添加到最后的位置
- `deleteRow`删除行
- `beginEdit`行开启编辑功能，配合行的editor使用
- `getData`:返回加载完毕后表格所有的数据
- `loadData`:加载本地数据，原有的行将被移除
- `getRows`返回当前页的所在行
- `getEditor`获取指定编辑器
- ==属性==
- showFooter定义是否显示行脚
- ==事件==
- `onClickRow`:点击某一行触发
2. Combogrid下拉表单
- ==属性==
- `url`
- `idField`:下拉列表选中后提交的列的字段
- `textField`:下拉列表后显示的列
- `columns`:列
- `panelWidth`:下拉面板宽度
3. combobox下拉列表
- `onSelect`:用户选择列表项时触发
4. numberbox
- `precision`:保留几位小数
#### 3.Jquery
- `val()`:获取和设置元素的值
- `target`：获取元素对象目标
#### 4.JS
- `toFixed()`:保留几位小数
- `parseFloat`:将字符串转换未浮点类型
#### 5.fastJson
- `parseArray`:将json字符串转为List对象
### day06--订单查询、审核、确认、入库
#### 1.业务分析
- 订单的状态：未审核、审核、确认。通过一个字段，条件查询出不同状态的订单
- 涉及到订单表、订单明细表、员工表（获取下单、审核的人员的信息）
- 审核、确认的后台实现也是修改这个字段实现
- 公用一个页面和表格，通过静态页面传参：url？oper=param，由param的不同，判断双击列时，实现的是审核、确认哪个功能，并且调用不同的url
- 查询出明细是因为orders表和ordersDetail实现了一对多
#### 2.入库业务
- 入库业务涉及到订单表、订单明细表、仓库表、仓库变更明细表
- 入库时，订单明细表要修改明细状态为入库、入到哪个仓库、入库时间、库管员
- 订单表要判断明细是否全部入库，全部入库了要更新订单的状态，修改库管员、入库时间
- 库存表要检查是否存在库存信息，存在就数量增加，不存在就新增记录
- 库存变更明细插入记录
### day08 
#### 1.json和js对象
- json可以理解为是一种格式要求严格的js对象，看如下代码
```
var obj={}//这是js对象
var obj1={width:100,height:200}//这也是js对象
var obj2={"width":100,"height":200,"name":role}//这是json格式的js对象，也就是json，键值对格式
var obj3='{"width":100,"height":200,"name":role}'//外面加了引号，所以是json字符串
``` 
- 区别
1. JSON:
1.仅仅是一种数据格式
2.可以跨平台数据传输 
==3.键值对方式，键必须加双引号==
==值不能是方法函数，不能是undefined==
==4.JSON转js对象==  
JSON.parse()
2. JS
1.不能传输
2.键值对方式，键不加引号
3.值可以是函数，对象，字符串等
4.js对象转Json
JSON.stringify():其实是转化为json字符串
#### 2.前端的一些转化json的方法的区别
1.JSON.parse() 用来把json字符串转化为json格式的js对象（也叫json对象）
- json字符串：符合json格式要求，外边加双引号，比如
- 首先啥是json，就是键值对，键必须加双引号；啥是js对象，就是键值对，键不加双引号
```
var obj1="{"name":role,"width"：100}"//这里是json，外边加双引号，就是json字符串
var obj2={"name":role,"width"：100}//这货是json
var obj3={name:role,width：100}//这货是js对象，因为和json的格式比较像，所以叫json格式的js对象
所以
obj3=JSON.parse(obj1)//把json字符串转化为json格式的js对象（json对象）
```
2.JSON.serializeJSON()
把表单的数据,转化为json对象，和上面的方法类似
3.JSON.stringify把JS对象转化为json字符串
#### 3.后端一些转化JSON数据的方法（以fastjson）
 1. 首先要明确的一点是ajax传送的数据是json对象
 比如struts2使用属性驱动（原理暂时我还不知道）得到相应的的值，最后需要将这些值进行转化
 2.JSON.toJSONString
 把java对象转化为json字符串，传回前端
 3.JSON.parseArray
 把json字符串转化为List对象
#### 3.按类型销售统计和年份统计
1. 主要是sql的使用：聚合函数和分组查询、投影查询：
a.查询语句编写，可以从简单到复杂
b.简单的多表查询，然后再加条件查询，再加分组和聚合函数
2. hql语句编写注意事项：
a.from后跟的是类名（开头大写）
b.如果两个实体进行了一对多的关联，多的一方.一的一方的属性=一的一方的别名
c.如果使用+拼接，注意每个字符串后面加空格
3. 投影查询
把查询出来的结果生成类的实例
#### 4.饼图和折线图
1. java开源的是JFreeChart
2. js有个开源的HighCharts
3. 怎么画？
从后端查询出数据，自己去看HighCharts的文档，在前端怎么写，把数据放好就OK了
### day09 javaMail实现库存预警
#### 1.库存预警报表
1. 统计每种商品的库存数量和发货数量，如果库存数量小于待发货数量则要进行库存预警
- 涉及到的表：
商品名称--orders,orderdetail、
库存数量--storeDetail
待发货数量：orderdetail type=2
子查询
2. sql语句
考虑写子查询，分成两张表：表一关联查询出商品名称和库存数量，表二关联查询出商品名称和代发货数量
- 表一查询
```
sql1=select g.uuid,sum(sd.num) storenum from Goods g,Storedetail  where g.uuid=sd.goodsuuid group by g.uuid,g.name 
```
此时只能查询有库存的商品，无法查询出库存中不存在的商品：此时选择使用==左外连接，nvl（）方法把不存在把null转成0
`sql1=select g.uuid,g.name,nvl(sum(s.num),0) storenum from goods g,storedetail s where g.uuid=s.goodsuuid(+) group by g.uuid,g.name `
- 表二查询
`sql2=select od.goodsuuid,sum(od.num) outnum from orderdetail od,orders o where od.ordersuuid=o.uuid and o.type='2' and od.state='0' group by od.goodsuuid`
- 将两个查询组合起来
` SQL=select a.uuid,a.name,a.storenum,b.outnum from(sql1) a,(sql2) b where a.uuid=b.goodsuuid`
3. 上边的语句查询结果是我们经常要用的，每次写都很繁琐，因此我们可以把它建立为视图
`create view view_storealter as SQL`
#### 2.JavaMail
1. 使用spring的javamailsender
用的时候查一下文档就行了
#### 3.Quartz 
1. 干啥用的：有一些任务，需要时间点触发。这个框架就是干这个的
2. 怎么用？
核心：
- job:任务，就是你要干啥
- jobDetail：这个是用来执行job的程序，包括了具体怎么执行这个job，就是所谓的调度方法和策略
- Trigger：一个类，描述job执行的时间触发规则，一个job可以对应多个trigger，但一个trigger只能对应一个job
- scheduler：调度容器：就是说里面可以放多个jobDetail和Trigger。就是用来管理他俩的，将一个Trigger绑定到jobDetail上
- Note：作业，
3. 具体使用
- job ：自己写具体要干啥
- jobDetail 由Quartz生成，属性`targetObject`指定job对象，`targetMethod`指定执行的方法
- Trigger，Quartz生成，属性`jobDetail`关联`jobDeatil`，另一个属性`cronExpression`指定触发时机--七子表达式
- scheduler任务调度管理容器，由Quartz生成，属性`List<> triggers`可以关联多个`Trigger`
4. 七子表达式cron
- 七个域：秒 分 时 日|月 月 日|星期 年
- 字符含义：
==*== 匹配该域任意值，比如如果分使用，就是每分钟都会触发
==？== 不确定值，只用在DayofMonth和DayofWeek
-表示范围
==/== 表示 5/20假如用在分，就是5分钟的时候开始触发，每20分钟触发一次
,枚举值 5，20在分，就是5和20分各触发一次
L：表示最后，只能出现在DayofMonth和DayofWeek
W：表示最近有效工作日，只出现在DayofMonth
LW:连用，表示某个月的最后一个工作日
==#==用于确定每个月第几个星期几
#### 总结
1. 子查询
首先所有的查询的结果都是一张表，但是可能这张表在数据库中不是实际存在的
我们从这张结果表再查出我们想要的数据，这就是子查询
2. 内连接、左（外）连接、右外连接
- 定义：什么是内连接、左外....
针对多表查询的一个概念。如果两个表有关系，我们要查询出这两个表中的一些数据，就要使用这个连接多表查询。
顾名思义，内连接就是把两张表连接起来查询，左、右连接也类似
- 使用
==内连接的语法==：`select * from table1 t1,table2 t2 where t1.id=t2.forid`
也可以这样写（不用where 关键字）`select * from table t1 inner join on t1.id=t2.forid`:==使用inner join关键字，用on来进行条件查询==
==左外连接==：`select * from table1 t1 left join table t2 on t1.id=t2.forid`：
或者`select * from table1 t1,table2 t2 where t1.id=t2.forid(+)`:==+在哪里，谁就是参考表==：参考表是啥？看下面的区别
==右外连接==：类似
- 区别
内连接查出满足条件的所有结果
左外连接：列出左表所有满足条件的列，右表中的列如果不存在或为null，也补上，右表就是参考表
3. nvl（）函数
nvl(t1,t2):t1的值存在就返回t1，否则返回t2
4. 视图
就是把一个查询语句的结果，也就是表存储起来，可以看成是数据库的虚表
### day10--数据的导入导出
#### 1.数据的导入
1. POI
- 主要使用POI这个框架
2. 导出excel文件
- 后端
```
//1.创建excel工作簿
HSSHWorkbook wk=new HSSHWorkbook();
//2.创建excel工作表
HSSFSheet sheet=null;
//3.创建行
HSSFRow row=sheet.createRow(i);//表示第几行
//4.创建行的单元格
cell=row.createCell(i);
//5.给单元格设置值
cell.setCellValue(headerNames[i]);
//6.文件写入
wk.write(os);
```
- 前端显示出导出--设置http的表头
```
response.setHeader("Content-Disposition", "attachment;filename=" +new String(filename.getBytes(),"ISO-8859-1"));//中文名称进行转码
```
==Content-Disposition==表示导出文件
==attachment;filename==设置导出时的文件名
- 前端使用jquery的download插件
#### 2.数据的导入
1. 后端实现
- action层需要三个参数
```
file 文件
fileName 文件名
fileContentType 文件类型`application/vnd.ms-excel`
```
- 对表格的操作
`hssfworkbook(FileInputStream(file)`将文件读进去操作)
2. 前端实现
```
<form enctype="multipart/form-data">
  <input type="file" name="file">
</form>
```
- 再用ajax将form里的数据传回后端
```
$.ajax({
			    			url: name + '_doImport',
			    			data:new FormData($('#importForm')[0]),
			    			type:'post',
			    			processData:false,
			    			contentType:false,
			    			dataType:'json',
			    			success:function(rtn){
			    				$.messager.alert('提示',rtn.message,'info',function(){
			    					if(rtn.success){
			    						$('#importDlg').dialog('close');
			    						$('#importForm').form('clear');
			    						$('#grid').datagrid('reload');
			    					}
			    				});
			    			}
			    		});
```
注意这里的一些参数
==processData==:要求为Boolean类型的参数，默认为true。默认情况下，发送的数据将被转换为对象（从技术角度来讲关非字符串）以配合默认内容类型”application/x-www-form-urlencoded”。如果要发送DOM树信息或者其它不希望转换的信息，请设置为false。
==contentType==：要求为String类型的参数，当发送信息至服务器时，内容编码类型默认为”application/x-www-form-urlencoded”。该默认值适合大多数应用场合 
==data:new FormData($('#importForm')[0]),==

FormData是js的一个对象，
`$('#importForm')`是一颗DOM树  











