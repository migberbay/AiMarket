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

<form:form action="comment/edit.do" modelAttribute="comment">

	<form:hidden path="id"/>
	<form:hidden path="aiBox"/>
	
	<acme:textbox code="aiBox.body" path="body"/>
	
	<br/>
	<button type="submit" name="save" class="btn btn-primary">
		<spring:message code="aiBox.save" />
	</button>
	
	<acme:cancel url="aiBox/list.do" code="aiBox.back"/>
	<br />	

</form:form>