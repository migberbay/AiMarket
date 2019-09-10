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


	You bought this aiBox:<br>
	<b><spring:message code="aiBox.ticker"/>:</b> <jstl:out value="${purchase.aiBox.ticker}"/><br>
	<b><spring:message code="aiBox.title"/>:</b> <jstl:out value="${purchase.aiBox.title}"/><br>
	<b><spring:message code="aiBox.description"/>:</b> <jstl:out value="${purchase.aiBox.description}"/><br>
	<b><spring:message code="aiBox.price"/>:</b><jstl:out value="${purchase.aiBox.price}"/><br>
	on:<br>
	<b><spring:message code="purchase.moment"/>:</b><jstl:out value="${purchase.moment}"/><br>
	with credit card:<br>
	
	<b><spring:message code="actor.make"/>:</b><jstl:out value="${purchase.make}"/><br>
	<b><spring:message code="actor.number"/>:</b><jstl:out value="${purchase.number}"/><br>
