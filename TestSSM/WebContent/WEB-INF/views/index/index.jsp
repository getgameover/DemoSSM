<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="r"  uri="http://tag.luqili.com/tag/resource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello Word</title>
<r:resource url="/abc/a.jpg"></r:resource>
<style type="text/css">
	body {
		margin: 0 auto 0 auto;
		text-align: center;
		width: 1024px;
	}
	table{
		margin: 20px;
		border: 1px solid silver;
		float: left;
		min-height: 160px;
	}
	table tbody th{
		text-align: right;
		width: 120px;
	}
	table thead th{
		text-align: center;
		width: 120px;
	}
	table tbody td{
		text-align: left;
	}
</style>
</head>
<body>
	${p}
	<form action="saveuser.lu" method="post">
	<table>
		<thead>
			<tr><th colspan="2"><label>保存用户信息</label></th></tr>
		</thead>
		<tbody>
		<tr><th>UserName:</th><td><input name="username" type="text"></td></tr>
		<tr><th>PassWord:</th><td><input name="password" type="password"></td></tr>
		<tr><th>Page:</th><td><input name="page" type="number"></td></tr>
		<tr><th>sex:</th><td><select name="sex"><option value="男">男</option><option value="女">女</option></select> </td></tr>
		<tr><th></th><td><input type="submit" value="提交"></td></tr>
		</tbody>
	</table>
	</form>
	<form action="getuser.lu" method="post">
	<table>
		<thead>
			<tr><th colspan="2"><label>查询用户信息</label></th></tr>
		</thead>
		<tbody>
		<tr><th>ID:</th><td><input name="id"></td></tr>
		<tr><th></th><td><input type="submit" value="查询"></td></tr>
		</tbody>
	</table>
	</form>
	<a href="bootstarp.lu">BootStrap</a>
</body>
</html>