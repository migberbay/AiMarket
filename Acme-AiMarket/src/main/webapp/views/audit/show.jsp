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
	
	<b><spring:message code="audit.text"/>:</b> <jstl:out value="${audit.text}"/><br>
	<b><spring:message code="audit.score"/>:</b> <jstl:out value="${audit.score}"/><br>
	<b><spring:message code="audit.final"/>:</b> <jstl:out value="${audit.isFinal}"/><br>
	<b><spring:message code="audit.moment"/>:</b><jstl:out value="${audit.moment}"/><br>


	<input type="button" name="back"
		value="<spring:message code="aiBox.back" />"
		onclick="javascript: window.location.replace('/Acme-AiMarket/audit/auditor/list.do')" />
	<br />
