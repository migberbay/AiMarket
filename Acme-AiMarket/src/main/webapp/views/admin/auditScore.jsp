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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- PRUEBA FORM -->

<security:authorize access="hasRole('ADMIN')">

<display:table name="scientists" id="row" requestURI="${requestURI}" pagesize="5">
		<display:column property="name" titleKey="actor.name" />
		<display:column property="surnames" titleKey="actor.surnames" />
		<jstl:choose>
			<jstl:when test="${row.auditScore == null}">
				<display:column titleKey="audit.score">
					NIL
				</display:column>
			</jstl:when>
			<jstl:otherwise>
				<display:column property="auditScore" titleKey="audit.score" />
			</jstl:otherwise>
		</jstl:choose>

	</display:table>
	

</security:authorize>