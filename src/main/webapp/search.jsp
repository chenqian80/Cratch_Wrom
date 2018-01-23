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

<body style="margin-left: 101px;">
<form action="search.do" method="post" style="position: fixed;">
    <input name="num" value="1" type="hidden"/>
    <input name="keywords" maxlength="30" style="width: 540px;height: 38px;font-size: 20px;" value="${keywords}"/>
    <input type="submit" value="千度一下" style="width: 100px;height: 38px;font-size: 20px;background-color: blue;color: white;"/>
</form>

<br/>
<div style="margin-top: 40px;width: 700px;">
<c:if test="${! empty page.list}" >
千度为您找到相关结果约${page.rowCount }个，查询时间${getTime}
<br/>
<p></p>
<c:forEach items="${page.list}" var="hb">
    <a href="${hb.url}" style ="TEXT-DECORATION: none;font-weight: bold;font-size: 20px;">${hb.title}</a>
    <p>来自:${hb.newsFrom}&nbsp&nbsp发布时间:${hb.time}&nbsp&nbsp评论数:${hb.commentCount}</p>
    <p>${hb.catgory}</p>
    <a href="${hb.url}" style ="TEXT-DECORATION: none;color:black;">文章正文:${hb.content}</a>
    <p>${hb.keywords}</p>
    <a href="${hb.url}">${hb.url}</a>
    <br/>
    <br/>
</c:forEach>
<br/>

    <!-- 这里是分页的逻辑 -->
    <c:if test="${page.hasPrevious}" >
        <a href="search.do?num=${page.previousPageNum}&keywords=${keywords}" >上一页</a>
    </c:if>
    <c:forEach begin="${page.everyPageStart}" end="${page.everyPageEnd}" var="current">
        <c:choose >
            <c:when test="${current eq page.pageNum}">
                <a ><font color="#a52a2a">${current}</font></a>
            </c:when>
            <c:otherwise>
                <a href="search.do?num=${current}&keywords=${keywords}">${current}</a>
            </c:otherwise>
        </c:choose>
        &nbsp;&nbsp;&nbsp;
    </c:forEach>
    <c:if test="${page.hasNext}" >
        <a href="search.do?num=${page.nextPageNum}&keywords=${keywords}">下一页</a>
    </c:if>
</c:if>
</div>
</body>
</html>
