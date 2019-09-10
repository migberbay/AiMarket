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

<security:authorize access="hasRole('ADMIN')">

		<spring:message code="admin.datatable"/>
	<table style="width:'100%' border='0' align='center' ">
			<tr>
				<th><spring:message code="admin.type"/></th>
				<th><spring:message code="admin.aiBoxesPerScientist"/></th>
				<th><spring:message code="admin.auditScoreScientist"/></th>
				<th><spring:message code="admin.auditScoreAiBox"/></th>
			</tr>
			<tr>
				<td><spring:message code="admin.average"/></td>
				<td><jstl:out value="${avgAiBoxesPerScientist}"/></td>
				
				<jstl:choose>
				<jstl:when test="${avgAuditScoreScientist == null}"> <td>NIL</td></jstl:when>
				<jstl:otherwise><td><jstl:out value="${avgAuditScoreScientist}"/></td></jstl:otherwise>
				</jstl:choose>
				
				
				<jstl:choose>
				<jstl:when test="${avgAuditScoreAiBox == null}"> <td>NIL</td></jstl:when>
				<jstl:otherwise><td><jstl:out value="${avgAuditScoreAiBox}"/></td></jstl:otherwise>
				</jstl:choose>
				
				
		
			</tr>
			<tr>
				<td><spring:message code="admin.minimum"/></td>
				<td><jstl:out value="${minAiBoxesPerScientist}"/></td>
				
				<jstl:choose>
				<jstl:when test="${minAuditScoreScientist == null}"> <td>NIL</td></jstl:when>
				<jstl:otherwise><td><jstl:out value="${minAuditScoreScientist}"/></td></jstl:otherwise>
				</jstl:choose>
				
								
				<jstl:choose>
				<jstl:when test="${minAuditScoreAiBox == null}"> <td>NIL</td></jstl:when>
				<jstl:otherwise><td><jstl:out value="${minAuditScoreAiBox}"/></td></jstl:otherwise>
				</jstl:choose>
			
			</tr>	
			<tr>
				<td><spring:message code="admin.maximum"/></td>
				<td><jstl:out value="${maxAiBoxesPerScientist}"/></td>
				
				<jstl:choose>
				<jstl:when test="${maxAuditScoreScientist == null}"> <td>NIL</td></jstl:when>
				<jstl:otherwise><td><jstl:out value="${maxAuditScoreScientist}"/></td></jstl:otherwise>
				</jstl:choose>
				
				
				<jstl:choose>
				<jstl:when test="${maxAuditScoreAiBox == null}"> <td>NIL</td></jstl:when>
				<jstl:otherwise><td><jstl:out value="${maxAuditScoreAiBox}"/></td></jstl:otherwise>
				</jstl:choose>
	
			</tr>
			<tr>
				<td><spring:message code="admin.stdv"/></td>
				<td><jstl:out value="${stdevAiBoxesPerScientist}"/></td>
				
				<jstl:choose>
				<jstl:when test="${stdevAuditScoreScientist == null}"> <td>NIL</td></jstl:when>
				<jstl:otherwise><td><jstl:out value="${stdevAuditScoreScientist}"/></td></jstl:otherwise>
				</jstl:choose>
				
				<jstl:choose>
				<jstl:when test="${stdevAuditScoreAiBox == null}"> <td>NIL</td></jstl:when>
				<jstl:otherwise><td><jstl:out value="${stdevAuditScoreAiBox}"/></td></jstl:otherwise>
				</jstl:choose>
			
			</tr>
	</table>
	

	<jstl:set var="cont" value="0"/>
	
 	<b><spring:message code="admin.top10bestSellingAiBoxes"/></b>
	<jstl:if test="${empty top10bestSellingAiBoxes}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
				<th><spring:message code="actor.aiBox"/></th>
				<th><spring:message code="aiBox.purchases"/></th>
				
		</tr>
		<jstl:forEach var="i" items="${top10bestSellingAiBoxes}">
		<jstl:set var="cont" value="${cont + 1}"/>
		<tr>
			<td><jstl:out value="${cont}"/>º) <jstl:out value="${i.title}"/> (<a href="actor/show.do?actorId=${i.scientist.id}"><jstl:out value="${i.scientist.userAccount.username}"/></a>)  </td>
			<td><jstl:out value="${i.purchases.size()}"/></td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<jstl:set var="cont" value="0"/>
	
	<b><spring:message code="admin.top10bestSellingScientist"/></b>
	<jstl:if test="${empty top10bestSellingScientist}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
				<th><spring:message code="admin.scientist"/></th>
				<th><spring:message code="aiBox.purchases"/></th>
				
		</tr>
		<jstl:forEach var="i" items="${top10bestSellingScientist}">
		<jstl:set var="cont" value="${cont + 1}"/>
		<tr>
			<td><jstl:out value="${cont}"/>º) <a href="actor/show.do?actorId=${i.getKey().id}"><jstl:out value="${i.getKey().userAccount.username}"/></a>  </td>
			<td><jstl:out value="${i.getValue()}"/></td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<jstl:set var="cont" value="0"/>
	
	<b><spring:message code="admin.top3ScientistAuditScore"/></b>
	<jstl:if test="${empty top3ScientistAuditScore}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
				<th><spring:message code="admin.scientist"/></th>
				<th><spring:message code="audit.score"/></th>
				
		</tr>
		<jstl:forEach var="i" items="${top3ScientistAuditScore}">
		<jstl:set var="cont" value="${cont + 1}"/>
		<tr>
			<td><jstl:out value="${cont}"/>º) <a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>  </td>
			<td><jstl:out value="${i.auditScore}"/></td>
		</tr>			
		</jstl:forEach>
	</table>
	
	
	
<%--	
	<b><spring:message code="admin.maxBetsUsers"/></b>
	<jstl:if test="${empty maxBetsUsers}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxBetsUsers}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.maxRequestsUsers"/></b>
	<jstl:if test="${empty maxRequestsUsers}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxRequestsUsers}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.maxRequestsUsers"/></b>
	<jstl:if test="${empty maxRequestsUsers}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxRequestsUsers}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.highestAvgScoreCounselor"/></b>
	<jstl:if test="${highestAvgScoreCounselor==null}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<td><jstl:out value="${highestAvgScoreCounselor.name}"/> <jstl:out value="${highestAvgScoreCounselor.surnames}"/> (<a href="actor/show.do?actorId=${highestAvgScoreCounselor.id}"><jstl:out value="${highestAvgScoreCounselor.userAccount.username}"/>)</a></td>
		</tr>			
	</table>
	
	<b><spring:message code="admin.topInActivatedSponsorships"/></b>
	<jstl:if test="${empty topInActivatedSponsorships}"><spring:message code="admin.empty"/></jstl:if>	
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${topInActivatedSponsorships}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>		
	</table> --%>
</security:authorize>

