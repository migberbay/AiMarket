<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
	
	<b><spring:message code="aiBox.ticker"/>:</b> <jstl:out value="${aiBox.ticker}"/><br>
	<b><spring:message code="aiBox.title"/>:</b> <jstl:out value="${aiBox.title}"/><br>
	<b><spring:message code="aiBox.description"/>:</b> <jstl:out value="${aiBox.description}"/><br>
	<b><spring:message code="aiBox.price"/>:</b><jstl:out value="${aiBox.price}"/><br>
	
	<h2><spring:message code="aiBox.purchases"/>:</h2>
	
	<display:table name="aiBox.purchases" id="row" requestURI="aiBox/show.do" pagesize="5">
		<display:column titleKey="aiBox.customer" >
 			<a href="actor/show.do?actorId =${row.customer.id}"><jstl:out value="${row.customer.userAccount.username}"/></a>
		</display:column> 
		<display:column titleKey="aiBox.moment" property="moment"/>
	</display:table>
	
	<h2><spring:message code="aiBox.comments"/>:</h2>
	
	<display:table name="comments" id="row" requestURI="aiBox/show.do" pagesize="5">
		<display:column titleKey="aiBox.comments" property="body"/>
	</display:table>
	
	<h2><spring:message code="aiBox.audits"/>:</h2>
	
	<display:table name="audits" id="row" requestURI="aiBox/show.do" pagesize="5">
		<display:column titleKey="aiBox.audits" property="text"/>
		<display:column titleKey="audit.score" property="score"/>
	</display:table>

	<input type="button" name="back"
		value="<spring:message code="aiBox.back" />"
		onclick="javascript: window.location.replace('/Acme-AiMarket/aiBox/scientist/list.do')" />
	<br />
