<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="audit/auditor/edit.do" modelAttribute="audit">

	<form:hidden path="id" value="${audit.id}"/>
	<form:hidden path="aiBox" value="${audit.aiBox.id}"/>
	
	<acme:textbox code="audit.text" path="text"/>
	<acme:textbox code="audit.score" path="score"/>
	
	<spring:message code="audit.final"/>
	<form:checkbox  path="isFinal"/><br>
	
	<button type="submit" name="save" class="btn btn-primary">
		<spring:message code="aiBox.save" />
	</button>
	
	<acme:cancel url="audit/auditor/list.do" code="aiBox.back"/>
	<br />	

</form:form>
	