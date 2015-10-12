<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="r"  uri="http://tag.luqili.com/tag/resource" %>
<c:choose><c:when test="${'base' eq type }">
<script src="<r:resource url="/plug/jquery/jquery.min.js"></r:resource>"></script>
</c:when><c:when test="${'bootstrap' eq type }">
<link rel="stylesheet" href="<r:resource url="/plug/bootstrap/css/bootstrap.min.css"></r:resource>">
<link rel="stylesheet" href="<r:resource url="/plug/bootstrap/css/bootstrap-theme.min.css"></r:resource>">
<script src="<r:resource url="/plug/bootstrap/js/bootstrap.min.js"></r:resource>"></script>
</c:when>
</c:choose>