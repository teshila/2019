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
				<div class="tm_m_b"></div>
			</div>
		</div>
	</div>

	<nav class="navbar navbar-default navbar-fixed-bottom">
	<div class="container">
		<div class="form-group search tmhide animated">
			<!-- <label class="sr-only" for="name">名称</label> -->
			<input type="text" class="form-control" id="queryStock" autofocus placeholder="请输入代码或股票拼音首字母" >
		</div>
		<div class="btns">
			<ul class="clearfix">
				<li class="clearfix border_left" onclick="showSearchInput(this)">
					<i class="iconfont icon-msnui-find"></i> <i>查找</i>
				</li>
				<li class="clearfix border_left">
					<i class="iconfont  icon-leimu"></i> <i>所有</i>
				</li>
				<li class="clearfix border_left" onclick="toQuShi()">
					<i class="iconfont  icon-leimu"></i> <i>走势</i>
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

	
	
		function showSearchInput(obj){
				var _this = $(obj);
				if(!_this.hasClass("cs")){
					_this.addClass("cs");
					$(".search").removeClass("tmhide fadeOutUp").addClass(" fadeInUp");
				}else{
					_this.removeClass("cs");
					$(".search").removeClass("fadeInUp").addClass("fadeOutUp");
				}
				
		}
	
		
		function dataToHtml(dataObj){
		 	$("#stockList").empty();
			var _html ="";
			if(!$.isEmptyObject(dataObj)){
				 _html = "<tr class='warning'><td >代码</td><td >名称</td><td >当前  / 昨收</td><td >最高</td><td >最低</td><td >提示价</td></tr>";
				for (var i = 0; i < dataObj.length; i++) {
					if (i % 2 == 0) {
						if (parseFloat(dataObj[i].price) > parseFloat(dataObj[i].preClosePrice)) {
							_html += "<tr class='info' style='color:red'><td >" + dataObj[i].stockCode + "</td><td >" + dataObj[i].name + "</td><td >" + dataObj[i].price + "  / " + dataObj[i].preClosePrice + "</td><td >" + dataObj[i].maxPrice + "</td><td >" + dataObj[i].minPrice + "</td><td >" + dataObj[i].pointerPrice + "</td></tr>";
						} else if (parseFloat(dataObj[i].price) < parseFloat(dataObj[i].preClosePrice)) {
							_html += "<tr class='info' style='color:green'><td >" + dataObj[i].stockCode + "</td><td >" + dataObj[i].name + "</td><td >" + dataObj[i].price + "  / " + dataObj[i].preClosePrice + "</td><td >" + dataObj[i].maxPrice + "</td><td >" + dataObj[i].minPrice + "</td><td >" + dataObj[i].pointerPrice + "</td></tr>";
						} else {
							_html += "<tr class='info' style='color:#ccc'><td >" + dataObj[i].stockCode + "</td><td >" + dataObj[i].name + "</td><td >" + dataObj[i].price + "  / " + dataObj[i].preClosePrice + "</td><td >" + dataObj[i].maxPrice + "</td><td >" + dataObj[i].minPrice + "</td><td >" + dataObj[i].pointerPrice + "</td></tr>";
						}
					} else {
						if (parseFloat(dataObj[i].price) > parseFloat(dataObj[i].preClosePrice)) {
							_html += "<tr class='warning' style='color:red'><td >" + dataObj[i].stockCode + "</td><td >" + dataObj[i].name + "</td><td >" + dataObj[i].price + "  / " + dataObj[i].preClosePrice + "</td><td >" + dataObj[i].maxPrice + "</td><td >" + dataObj[i].minPrice + "</td><td >" + dataObj[i].pointerPrice + "</td></tr>";
						} else if (parseFloat(dataObj[i].price) < parseFloat(dataObj[i].preClosePrice)) {
							_html += "<tr class='warning' style='color:green'><td >" + dataObj[i].stockCode + "</td><td >" + dataObj[i].name + "</td><td >" + dataObj[i].price + "  / " + dataObj[i].preClosePrice + "</td><td >" + dataObj[i].maxPrice + "</td><td >" + dataObj[i].minPrice + "</td><td >" + dataObj[i].pointerPrice + "</td></tr>";
						} else {
							_html += "<tr class='warning' style='color:#ccc'><td >" + dataObj[i].stockCode + "</td><td >" + dataObj[i].name + "</td><td >" + dataObj[i].price + "  / " + dataObj[i].preClosePrice + "</td><td >" + dataObj[i].maxPrice + "</td><td >" + dataObj[i].minPrice + "</td><td >" + dataObj[i].pointerPrice + "</td></tr>";
						}
					}
				}
			}else{
				_html ="<tr colspan='6' style='text-align='center''>无对应信息<td></td></tr>";
			}
			
			$("#stockList").append(_html);
		}
		
		
		
    	$(function(){
			var	timmer = setInterval(function() {
				initData();
			}, 1000)
	
	
			$("#queryStock").on('input porpertychange', function() {
    			clearInterval(timmer);
				var code = $("#queryStock").val();
				$.ajax({
					url : "stock/queryStock",
					data : {
						"keyword" : code,
					},
					dataType : "json",
					beforeSend: function(){
						/* $("#stockList").empty();
						var _html = "<tr class='warning'><td >代码</td><td >名称</td><td >当前  / 昨收</td><td >最高</td><td >最低</td><td >提示价</td></tr>";	
						_html+="<tr><td colspan='6'>正在加载中，请稍后...</td></tr>";
						$("#stockList").append(_html); */
					},
					success : function(data) {
							var dataObj = data.list;
							dataToHtml(dataObj);
						},
						error:function(data){
							console.log(data)
						}
				});
			});
			
    	});
    	
    	
    	function initData(){
    		$.ajax({
				url : "stock/list",
				dataType : "json",
				beforeSend : function() {
					/* $("#stockList").empty();
					var _html = "<tr class='warning'><td >代码</td><td >名称</td><td >当前  / 昨收</td><td >最高</td><td >最低</td><td >提示价</td></tr>";	
					_html+="<tr><td colspan='6'>正在加载中，请稍后...</td></tr>";
					$("#stockList").append(_html); */
				},
				success : function(data) {
					var dataObj = data.list;
					dataToHtml(dataObj);
				},
				error : function(data) {
					console.log(data);
				}
			});
    	
    	}
		
	</script>
</body>
</html>
