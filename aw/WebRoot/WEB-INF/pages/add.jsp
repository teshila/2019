<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/pub/pub.jsp"%>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<body>
	<%@include file="goback.jsp"%>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<form role="form" class="form-horizontal">
					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">代码</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" placeholder="请输入代码" id="code" autofocus="autofocus">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" readonly="readonly" placeholder="名称" id="name">
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">昨收</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" readonly="readonly" placeholder="当前价格" id="preClosePrice">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">当日最低</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" readonly="readonly" placeholder="当日最低" id="minPrice">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">当日最高</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" readonly="readonly" placeholder="当日最高" id="maxPrice">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">当前价格</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" readonly="readonly" placeholder="当前价格" id="price">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">预设提示价格</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" placeholder="预设提示价格" id="preprice">
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">股票简称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  placeholder="股票简称" id="pinyin">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-danger btn-md btn-block" onclick="subInfos(this)">保存</button>
						</div>

					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-primary btn-md btn-block" id="clearInputs">清空</button>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>


	<script>
		$(function() {
			$("#code").on('input porpertychange', function() {
				var code = $("#code").val();
				if (code != null && code.length == 6 && code != '') {
					var code = $("#code").val();
					$.ajax({
						url : "stock/getStockInfo",
						data : {
							"code" : code
						},
						dataType : "json",
						success : function(data) {
							$("#name").val(data.name);
							$("#price").val(data.price);
							$("#preClosePrice").val(data.preClosePrice);
							$("#maxPrice").val(data.maxPrice);
							$("#minPrice").val(data.minPrice);
							$("#pinyin").val(data.pinyin);
						}
					});
	
				}
			});
	
	
	
			$("#clearInputs").on("click", function() {
				$("input[type='text']").val("");
			});
		});
	
	
		function subInfos(obj) {
			var _this = $(obj);
			_this.html("正在保存中...");
			_this.removeAttr("onclick");
			var code = $("#code").val();
			var name = $("#name").val();
			var price = $("#price").val();
			var pointPrice = $("#preprice").val();
			var preClosePrice = $("#preClosePrice").val();
			var maxPrice = $("#maxPrice").val();
			var minPrice = $("#minPrice").val();
			var pinyin = $("#pinyin").val();
			if (pointPrice == '') {
				pointPrice = 0;
			}
			if (code == '') {
				_this.attr("onclick", "subInfos(this)");
				_this.html("保存");
				BootstrapDialog.show({
					message : '代码不能为空',
					spinicon : BootstrapDialog.ICON_SPINNER,
					autodestroy : true,
					buttons : [
						{
							icon : 'glyphicon glyphicon-ban-circle',
							label : "好的",
							action : function(dialog) {
								//给当前按钮添加点击事件 
								dialog.close();
							},
							cssClass : 'btn-warning'
						}
					]
				});
				return;
			}
			$.ajax({
				url : "stock/save",
				data : {
					"stockCode" : code,
					"pointerPrice" : pointPrice,
					"preClosePrice" : preClosePrice,
					"name" : name,
					"price" : price,
					"minPrice" : minPrice,
					"maxPrice" : maxPrice,
					"pinyin":pinyin
				},
				dataType : "json",
				success : function(data) {
	
					if (data == 1) {
						BootstrapDialog.alert('保存成功');
						$("input[type='text']").val("");
	
					} else if (data == 0) {
						BootstrapDialog.alert('保存失败');
					} else {
						BootstrapDialog.alert('已有的数据!');
					}
					_this.html("保存");
					_this.attr("onclick", "subInfos(this)");
				},
				error : function() {
					_this.attr("onclick", "subInfos(this)");
				}
			});
		}
	</script>
</body>
</html>
