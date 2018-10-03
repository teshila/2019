<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="/pub/pub.jsp"%>
<base href="<%=basePath%>">
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- https://www.cnblogs.com/MuYunyun/p/5692385.html -->
<style>

ul, li {
	list-style: none;
}

li {
	float: left;
	padding: 1.2rem 2.8rem;
	background: #fbfbfb;
	text-align: center;
}

li i {
	font-style: normal;
	display: block;
}

.btns ul {
	margin: 0 auto;
	display: table;
}

.iconfont {
	font-size: 2.4rem;
}

.border_left {
	border-left: 1px solid #ccc;
}

.border_right {
	border-right: 1px solid #ccc;
}

.tm_m_b {
	padding-bottom: 6.4rem;
}


.tmhide{
	display: none;
}

.wrapHeight{
	height:45rem;
	overflow-y: scroll;
}

@media only screen and (max-width: 650px) {
	.iconfont {
		font-size: 1.6rem;
	}
	li {
		padding: 0.2rem 0.8rem;
	}
	.tm_m_b {
		padding-bottom: 5.5rem;
	}
	
	.table>tbody>tr>td{
		padding:2px;
	}
}

.dates{
	font-size: 11px;
	font-style: normal;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row clearfix">
			<div class="col-md-12 column" style="padding: 0;">
				<div class="wrapHeight">
					<table class="table table-hover" id="stockList">
					</table>
				</div>
				
				<div id="zhiShuPointer">
					上证：<span id="sh"></span>
					深证：<span id="sz"></span>
					创业板：<span id="cy"></span>
				</div>
				<div class="m-style" id="pagination"></div>
				<div class="tm_m_b"></div>
			</div>
		</div>
	</div>

	<nav class="navbar navbar-default navbar-fixed-bottom">
	<div class="container">
		<div class="btns">
			<ul class="clearfix">
				<li class="clearfix border_left" onclick="toAll()">
					<i class="iconfont  icon-leimu"></i> <i>所有</i>
				</li>
				<li class="clearfix border_left" onclick="toAdd()">
					<i class="iconfont  icon-jiahao2fill"></i> <i>添加</i>
				</li>
				<li class="clearfix border_left">
					<i class="iconfont  icon-dingdan"></i> <i>编辑</i>
				</li>
				<li class="clearfix border_left">
					<i class="iconfont  icon-gonglve"></i> <i>预警</i>
				</li>
				<li class="clearfix border_left border_right">
					<i class="iconfont  icon-shezhi"></i> <i>邮件</i>
				</li>
			</ul>
		</div>
	</div>
	</nav>
	<script>
		var pageSize = 100; //每页显示多少条记录
		var total; //总共多少记录
		var pageForRefresh=0;
		$(function() {
			Init(0);
	
		});
		function Init(pageIndex) { //这个参数就是点击的那个分页的页数索引值，第一页为0，上面提到了，下面这部分就是AJAX传值了。
			$.ajax({
				url : "stock/quShi?page=" + pageIndex+"&rows=" + pageSize,
				dataType:'json',
				beforeSend:function(){
					$("#stockList").empty();
					$("#stockList").append("<tr class='warning'><td >代码</td><td >名称</td><td >当前</td><td >昨收</td><td >前二天</td><td >前三天</td><td >前四天</td><td >前五天</td><td >前六天</td><td >前七天</td></tr>");
					$("#stockList").append("<tr><td colspan='9'>加载中....</td></tr>");
				},
				success : function(data) {
					var total = data.total;
					var dataStr = data.list;
				 	var _html ="";
				 	_html +="<tr class='warning'><td >代码</td><td >名称</td><td >当前</td><td >昨收</td><td >前二天</td><td >前三天</td><td >前四天</td><td >前五天</td><td >前六天</td><td >前七天</td></tr>";
				 	$("#stockList").empty();
				 	for(var i=0;i<dataStr.length;i++){
				 		console.log(dataStr[i])
				 		 if(!$.isEmptyObject(dataStr[i])){
				 			_html+="<tr class='warn'><td>"+dataStr[i].code+"</td>";
				 			_html+="<td>"+dataStr[i].name+"</td>";
				 			_html+="<td>"+dataStr[i].todayPrice+"</td>";
				 			_html+="<td>"+dataStr[i].prevPrice+"</td>";
				 			var daysPirce = dataStr[i].datePrice;
				 			/* for ( var pr in daysPirce) {
			 					_html+="<td>"+pr.price+"</td>";
							} */
							if(!$.isEmptyObject(daysPirce)){
								for (var k = 0; k < daysPirce.length; k++) {
									var price = daysPirce[k].price;
									var date = daysPirce[k].date;
									_html+="<td>"+price+"<br/><i class='dates'>"+date+"</i>"+"</td>";
								}
							}
							
				 			
					 		_html+="</tr>";
				 		} 
				 	}
				 $("#stockList").append(_html);
				}
			});
		}
	</script>
</body>
</html>
