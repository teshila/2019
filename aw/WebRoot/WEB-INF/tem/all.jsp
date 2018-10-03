<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="/pub/pub.jsp" %>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style>
.header{height: 36px;line-height: 36px;}
.header i{font-size: 3.6rem;}
</style>
</head>
<body>


	<div class="header">
	<i class="iconfont  icon-fanhui2" onclick="window.history.go(-1)"></i>
	</div>
	<div class="container-fluid">
		<div class="row clearfix">
			
			<div class="col-md-12 column">
			
				<table class="table table-hover" id="stockList">
				</table>
			</div>
		</div>
	</div>
	<script>
	$(window).load(function(){
		initData();
	});
	
	
	function initData(obj){
		$.ajax({
			url:"stock/list?random="+obj,
			dataType:'json',
			beforeSend:function(data){
				
			},
			success:function(data){
				$("#stockList").empty();
				var dataObj = data;
				var _html ="<tr><td >代码</td><td >名称</td><td >当前  / 昨收</td><td >最高</td><td >最低</td><td >提示价</td></tr>";
				for (var i = 0; i < dataObj.length; i++) {
					
					if(i%2==0){
						if(parseFloat(dataObj[i].price)>parseFloat(dataObj[i].preClosePrice)){
							 _html+="<tr class='info' style='color:red'><td >"+dataObj[i].stockCode+"</td><td >"+dataObj[i].name+"</td><td >"+dataObj[i].price+"  / "+dataObj[i].preClosePrice+"</td><td >"+dataObj[i].maxPrice+"</td><td >"+dataObj[i].minPrice+"</td><td >"+dataObj[i].pointerPrice+"</td></tr>";
						}else if(parseFloat(dataObj[i].price)<parseFloat(dataObj[i].preClosePrice)){
							 _html+="<tr class='info' style='color:green'><td >"+dataObj[i].stockCode+"</td><td >"+dataObj[i].name+"</td><td >"+dataObj[i].price+"  / "+dataObj[i].preClosePrice+"</td><td >"+dataObj[i].maxPrice+"</td><td >"+dataObj[i].minPrice+"</td><td >"+dataObj[i].pointerPrice+"</td></tr>";
						}else{
							 _html+="<tr class='info' style='color:#ccc'><td >"+dataObj[i].stockCode+"</td><td >"+dataObj[i].name+"</td><td >"+dataObj[i].price+"  / "+dataObj[i].preClosePrice+"</td><td >"+dataObj[i].maxPrice+"</td><td >"+dataObj[i].minPrice+"</td><td >"+dataObj[i].pointerPrice+"</td></tr>";
						}
					}else{
						if(parseFloat(dataObj[i].price)>parseFloat(dataObj[i].preClosePrice)){
							 _html+="<tr class='warning' style='color:red'><td >"+dataObj[i].stockCode+"</td><td >"+dataObj[i].name+"</td><td >"+dataObj[i].price+"  / "+dataObj[i].preClosePrice+"</td><td >"+dataObj[i].maxPrice+"</td><td >"+dataObj[i].minPrice+"</td><td >"+dataObj[i].pointerPrice+"</td></tr>";
						}else if(parseFloat(dataObj[i].price)<parseFloat(dataObj[i].preClosePrice)){
							 _html+="<tr class='warning' style='color:green'><td >"+dataObj[i].stockCode+"</td><td >"+dataObj[i].name+"</td><td >"+dataObj[i].price+"  / "+dataObj[i].preClosePrice+"</td><td >"+dataObj[i].maxPrice+"</td><td >"+dataObj[i].minPrice+"</td><td >"+dataObj[i].pointerPrice+"</td></tr>";
						}else{
							 _html+="<tr class='warning' style='color:#ccc'><td >"+dataObj[i].stockCode+"</td><td >"+dataObj[i].name+"</td><td >"+dataObj[i].price+"  / "+dataObj[i].preClosePrice+"</td><td >"+dataObj[i].maxPrice+"</td><td >"+dataObj[i].minPrice+"</td><td >"+dataObj[i].pointerPrice+"</td></tr>";
						}
					}
				}
				$("#stockList").append(_html);
			}
		});
	}
	
	setInterval(function(){
		var random =Math.random();
		initData(random);
	}, 1000);
	</script>
</body>
</html>
