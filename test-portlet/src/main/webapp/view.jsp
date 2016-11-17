<%@include file="/init.jsp" %>

<h2>Count</h2>
<p>${count}</p>

<h2>Articles</h2><ul>
<c:if test="${empty articles}">
	None found (Perhaps an exception ?)
</c:if>
<c:forEach items="${articles}" var="article">
<li>${article.articleId} - ${article.version}</li>
</c:forEach>
</ul>

<h2>Last version Articles</h2><ul>
<c:if test="${empty articlesLastVersion}">
	None found (Perhaps an exception ?)
</c:if>
<c:forEach items="${articlesLastVersion}" var="article">
<li>${article.articleId} - ${article.version}</li>
</c:forEach>
</ul>

<h2>Last version Articles</h2><ul>
<c:if test="${empty articlesLastVersionNoJar}">
	None found (Perhaps an exception ?)
</c:if>
<c:forEach items="${articlesLastVersionNoJar}" var="article">
<li>${article.articleId} - ${article.version}</li>
</c:forEach>
</ul>
