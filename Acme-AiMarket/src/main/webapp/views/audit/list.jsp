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

<!-- 

-->
<security:authorize access="hasRole('AUDITOR')">
	<!-- <a href = "audit/auditor/create.do">Create</a> -->

	<display:table name="audits" id="row" requestURI="audits/auditor/list.do" pagesize="5">
	
		<display:column titleKey="audit.action">
				<a href = "audit/auditor/show.do?auditId=${row.id}">Show</a><br>
				<jstl:if test="${row.isFinal == false}">
					<a href = "audit/auditor/edit.do?auditId=${row.id}">Edit</a><br>
					<a href = "audit/auditor/delete.do?auditId=${row.id}">Delete</a><br>
				</jstl:if>
		</display:column>
		
		<display:column property="moment" titleKey="audit.moment" />
		<display:column property="text" titleKey="audit.text" />
		<display:column property="score" titleKey="audit.score" />
	
	</display:table>
	
</security:authorize>