#用户api  -董国彦
curl http://localhost:9006/form/rest/ouser
#查询一条用户记录
curl http://localhost:9006/form/rest/ouser/1
#添加一条用户记录
curl -i -X POST -H "Content-Type:application/json" \
		-d '{   "telNumber": "13671343167",\
				"ownerPlace": "河南" }'\
	  http://localhost:9006/form/rest/ouser
#修改一个用户记录
curl -X PATCH -H "Content-Type:application/json" \
		-d '{  	"telNumber": "28298330420","ownerPlace": "123" }'\
	 http://localhost:9006/form/rest/ouser/4
#删除一个用户记录
curl -X DELETE http://localhost:9006/form/rest/ouser/4
#通过电话号码查询一条用户记录 
http://localhost:9006/form/rest/ouser/search/findByTelNumber?telnum=13671343167
#订单api  -董国彦
curl http://localhost:9006/form/rest/order
#查询一条订单记录
curl http://localhost:9006/form/rest/order/1
#查询id为1的订单所对应的用户(此方法的添加或修改用户字段的api与之前的put的api所冲突)
curl http://localhost:9006/form/rest/order/1
order.ouser
#查询订单id为1的活动及活动类别  (三级关联的表)
curl http://localhost:9006/form/rest/order/1
order.activityDetail.activityName
#添加一条订单记录
curl -i -X POST -H "Content-Type:application/json" \
		-d '{   "payAmount": 120   }'\
	  http://localhost:9006/form/rest/order
#修改一条订单记录 (不包含外键的字段)
curl -X PATCH -H "Content-Type:application/json" \
		-d '{   "payAmount": 240   }'\
	 http://localhost:9006/form/rest/order/1
#修改订单时间，自定义接口。
http://localhost:9006/form/rest/updateTime2?id=2
#修改订单，支持全字段修改  传入json对象
http://localhost:9006/form/rest/updateorderone
#删除一条订单记录
curl -X DELETE http://localhost:9006/form/rest/order/3
#添加订单 参数是： 工单id   页面类型   同时回写工单字段：完成时间、完成结果
http://localhost:9006/form/rest/saveorder?uid=1&type=存费送费
#工单api	  -董国彦
curl http://localhost:9006/form/rest/work
#查询一条工单记录
curl http://localhost:9006/form/rest/work/1
#查询工单id为1 的用户 
curl http://localhost:9006/form/rest/work/1/ouser
#添加一条工单记录  自定义方法
http://localhost:9006/form/rest/savework
{
"result": "ssss",
"ouser" : {"id" : 1},
"product":{"id" : 1}
}
#添加一条工单记录  作废
curl -i -X POST -H "Content-Type:application/json" \
		-d '{	"result": "工单完成", "wxhsh": "120元送240元" }'\
	  http://localhost:9006/form/rest/work
#添加用户关联
curl -i -X PUT -H "Content-Type:text/uri-list" \
		         http://localhost:9006/form/rest/ouser/1
	  http://localhost:9006/form/rest/work/3/ouser
#添加活动关联
#添加产品管理
#修改工单
curl -X PATCH -H "Content-Type:application/json" \
		-d '{   "result": "uncomplete"   }'\
	 http://localhost:9006/form/rest/work/1
#修改一条工单的api(带三个外键) 发送的对象要严格遵守json格式。 
http://localhost:9006/form/rest/savework  
#删除工单
curl -X DELETE http://localhost:9006/form/rest/work/3
#修改工单的完成时间和完成结果字段
http://localhost:9006/form/rest/updatework?id=1
#通过用户id查询一条工单id
http://localhost:9006/form/rest/work/search/findByOuser?uid=1
#Excel数据导入
#通过电话号码查询任务工单
http://localhost:9006/form/rest/work/search/findByUserTelNumber?tel=13671343167 

#验证码验证
http://localhost:9006/form/rest/static/myServlet2

#微信url处理的接口，微信触发
http://localhost:9006/form/rest/findUrl?telNumber=13671343167
#微信的连调
http://192.168.6.117:8040/wechat/bind/excute.do? page=&isbind=yes&openId=ofC0CtwD0hOUAnvRACkq8niVjVgA&scope=user&param=/needbind/main.do?publicId=gh_c1cbf2230f00&publicId=gh_c1cbf2230f00&redirectUrl=http://localhost:9006/form/rest/findUrl
#手机号，业务类型  查询 一条任务工单（办理页面展示）
http://localhost:9006/form/rest/work/search/findByTelandProtype?tel=18638787085&type=flow
#插入工单  id是字符串
http://localhost:9006/form/rest/saveOnework
{
"id":93,
"EndTime":"2016-10-31",
"StartTime":"2016-10-31",
"wxhsh":"asdjaisdjoias",
"elementid":"asd",
"packageid":"tew",
"productid":"turyr",
"telNu":"132"
}
#用户合规工单查询   是否查到用户对应的任务工单  成功返回  0 works 失败 返回  1
http://localhost:9006/form/rest/findWorkByTel?tel=18638787085   
#查询产品  参数  producid
http://localhost:9006/form/rest/product/search/findByProductOrderId?productOrderId=G0000004
#订购页面的测试url
http://172.16.63.93:8080/form/rest/static/order-transact2.html?id=179&key=t1djbbr1ps455ldlbdd98tltr949dj39
#http://code.bonc.com.cn/apicreator/preview.htm?id=12#!/api/post_http_order_8080_xorder_interface_order
#查询的字段 payMode,cityId,telNu,nettype,tenant_id,orderproduct_id2,orderproduct_id1,orderproduct_id3
http://172.16.63.152:8080/form/rest/workinfo


