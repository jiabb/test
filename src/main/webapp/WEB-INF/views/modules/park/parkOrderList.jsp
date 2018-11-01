<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>停车订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/park/parkOrder/">停车订单列表</a></li>
		<shiro:hasPermission name="park:parkOrder:edit"><li><a href="${ctx}/park/parkOrder/form">停车订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="parkOrder" action="${ctx}/park/parkOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>归属用户：</label>
				<sys:treeselect id="user" name="user.id" value="${parkOrder.user.id}" labelName="user.name" labelValue="${parkOrder.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>开始停车时间：</label>
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${parkOrder.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>结束停车时间：</label>
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${parkOrder.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>停车地点：</label>
				<form:input path="address" htmlEscape="false" maxlength="1000" class="input-medium"/>
			</li>
			<li><label>停车状态：</label>
				<form:select path="stopFlag" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('stop_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>归属用户</th>
				<th>图片URL</th>
				<th>开始停车时间</th>
				<th>结束停车时间</th>
				<th>停车时长</th>
				<th>停车地点</th>
				<th>预付款</th>
				<th>免费时长</th>
				<th>停车状态</th>
				<shiro:hasPermission name="park:parkOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="parkOrder">
			<tr>
				<td><a href="${ctx}/park/parkOrder/form?id=${parkOrder.id}">
					${parkOrder.user.name}
				</a></td>
				<td>
					${parkOrder.imgUrl}
				</td>
				<td>
					<fmt:formatDate value="${parkOrder.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${parkOrder.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${parkOrder.parkTime}
				</td>
				<td>
					${parkOrder.address}
				</td>
				<td>
					${parkOrder.prepay}
				</td>
				<td>
					${parkOrder.freeTime}
				</td>
				<td>
						${parkOrder.stopFlag}
				</td>
				<shiro:hasPermission name="park:parkOrder:edit"><td>
    				<a href="${ctx}/park/parkOrder/form?id=${parkOrder.id}">修改</a>
					<a href="${ctx}/park/parkOrder/delete?id=${parkOrder.id}" onclick="return confirmx('确认要删除该停车订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
