<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>千度一下，你就知道</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
</head>
<style type="text/css">

</style>

<body>
<center>

<img src="images/qiandu.png" alt="千度一下" style="margin-top: 100px;height:100px;width:200px"/>
<form action="search.do" method="post" id="form1" style="margin-top: 5px;">
    <input name="num" value="1" type="hidden"/>
    <input name="keywords" maxlength="30" style="width: 540px;height: 38px;font-size: 20px;" />
    <input type="submit" value="千度一下" style="width: 100px;height: 38px;font-size: 20px;background-color: blue;color: white;"/>
</form>

</center>
</body>
</html>
