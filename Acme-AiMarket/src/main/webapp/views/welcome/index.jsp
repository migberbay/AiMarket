<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1>${systemName}</h1>
<spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /><br>
<jstl:out value="${welcomeMessageToDisplay}"></jstl:out>
<jstl:if test="${showNotification}">
	<div class = "error">
		<h4>
		The company has been sued and due to this every reference to Irobot is now either Ai-Box as an object or Ai-Market as a company.<br>
		We are really sorry about the possible inconveniences this might cause to our users.
		</h4>
	</div>
</jstl:if>

