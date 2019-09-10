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
	<form:form action="aiBox/list.do" modelAttribute="form">
		<acme:textbox code="aiBox.search" path="keyword"/>
		<acme:submit name="save" code="aiBox.save"/>
	</form:form>
	
	<jstl:if test="${auditCreated}">
	<p class = "error">You've already audited this aiBox!</p>
	</jstl:if>
	
	<br>
	<jstl:if test="${isOwner and loggedUser}">
	<b><a href = "aiBox/scientist/create.do">Create</a></b>
	</jstl:if>
	
	<display:table name="aiBoxes" id="row" requestURI="${requestURI}" pagesize="5">
	
	<jstl:if test="${loggedUser}">
		<display:column titleKey="aiBox.action">
			<a href = "comment/create.do?aiBoxId=${row.id}">Comment</a><br>
			<jstl:if test="${isCustomer and row.isDecomissioned == false}">
				<a href = "purchase/customer/create.do?aiBoxId=${row.id}">Purchase</a><br>
			</jstl:if>
			<jstl:if test="${isAuditor}">
				<a href = "audit/auditor/create.do?aiBoxId=${row.id}">Audit</a><br>
			</jstl:if>
		
			<jstl:if test="${isOwner}">
				<a href = "aiBox/scientist/edit.do?aiBoxId=${row.id}">Edit</a><br>
				<a href = "aiBox/scientist/show.do?aiBoxId=${row.id}">Show</a><br>
				<a href = "aiBox/scientist/delete.do?aiBoxId=${row.id}">Delete</a><br>
			</jstl:if>
		</display:column>
	</jstl:if>

		<display:column titleKey="aiBox.scientist">
			<a href = "actor/show.do?actorId=${row.scientist.id}" ><jstl:out value="${row.scientist.name}"/></a>
		</display:column>
		<display:column property="title" titleKey="aiBox.title" />
		<display:column property="ticker" titleKey="aiBox.ticker" />
		<display:column property="description" titleKey="aiBox.description" />
		<display:column property="price" titleKey="aiBox.price" />
	
	</display:table>
	
	