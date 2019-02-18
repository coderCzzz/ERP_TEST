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
   

   

   











