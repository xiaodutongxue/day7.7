<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style type="text/css">
		td{
			text-align: center;
			width: 125px;
		}
		.c2{
			margin-left: 40px;
			margin-bottom: 40px;
		}
	</style>
	<%-- 引入jQuery--%>
	<script src="js/jquery.js"></script>
	<script type="text/javascript">
		// 准备函数
		$(function () {
			// 全选和全不选
			$("#checkAll").click(function () {
				var check = this.checked;
				console.log(check);
				// 遍历其它复选框 其中index表示获取的复选框中每个对象的索引,ele 表每个复选框对象
				$(".checkAll").each(function (index,ele) {
					// 给剩下的复选框属性checked 赋值
					ele.checked = check;
				})
			})

			// 批量删除
			$("#batchDel").click(function () {
				var len = $(".checkAll").length; // 获取被选中元素的长度
				if(len <= 0){ // 表示没有选中任何元素
					alert("没有要删除的数据");
					return;
				}
				var bo = confirm("你确定要批量删除码??");
				var ids = "";
				if(bo){
					// 删除的逻辑,获取需要删除的学生编号
					// 找到被选中的元素,获取选中元素的值

					$(".checkAll:checked").each(function () {
						// ,1,2,3,4
						ids += "," + this.value;
					})
					console.log("截取前: ",ids);
					ids = ids.substring(1);
					console.log(ids);
					// 将要删除的学生编号传递给后台实现删除
					location.href = "stu?method=BatchDelete&ids=" + ids;
				}

			})



		})


	</script>
</head>
<body>
<br/>
<center>
	<form action="stu" method="get">
		<%-- 找到对应分页的方法--%>
		<input type="hidden" name="method" value="queryStu">
		姓名<input name="sname" value="${student.sname}"/>&nbsp;&nbsp;&nbsp;
		性别
		<select name="gender">
			<option value="">请选择</option>

			<option value="男"  <c:if test="${student.gender == '男'}">selected="selected"</c:if> >男</option>
			<option value="女"  <c:if test="${student.gender == '女'}">selected="selected"</c:if> >女</option>
		</select>&nbsp;&nbsp;&nbsp;
		<input type="submit" value="查询"/>
	</form>
	<br/>
</center><br/>
<input type="button" id="batchDel" value="批量删除" style="margin-left: 150px"/>
<br/>
<table border="1px" width="80%" align="center" cellpadding="0"
	   cellspacing="0">
	<tr>
		<th><input type="checkbox"  id="checkAll" />全选/全不选</th>
		<th>学号</th>
		<th>姓名</th>
		<th>性别</th>
		<th>生日</th>
		<th>爱好</th>
		<th>头像</th>
		<th>操作</th>
	</tr>
	<!-- 遍历学生的信息 -->
	<c:forEach items="${page.pageList}" var="stu">
		<tr>
			<td><input type="checkbox" class="checkAll" value="${stu.sid}"/></td>
			<td>${stu.sid}</td>
			<td>${stu.sname}</td>
			<td>${stu.gender}</td>
			<td><fmt:formatDate value="${stu.sbir}" pattern="yyyy-MM-dd" /> </td>
			<td>${stu.hobby}</td>
			<td><img src="http://localhost:8080/upload/${stu.photo}" alt="头像" width="60px" height="60px"></td>
			<td><a href="stu?method=updateStudentById&sid=${stu.sid}">修改</a></td>
		</tr>
	</c:forEach>

</table>
<br/><br/>
<center>
	<a href="stu?method=queryStu&currentPage=1&sname=${student.sname}&gender=${student.gender}" class="c2">首页</a>
	<a href="stu?method=queryStu&currentPage=${page.prePage}&sname=${student.sname}&gender=${student.gender}" class="c2">上一页</a>
	<a href="stu?method=queryStu&currentPage=${page.nextpage}&sname=${student.sname}&gender=${student.gender}" class="c2">下一页</a>
	<a href="stu?method=queryStu&currentPage=${page.countpage}&sname=${student.sname}&gender=${student.gender}" class="c2">尾页</a>
	<span class="c2">当前页码<input size="4" value="${page.currentPage}"/></span>
	<span class="c2">总记录数<input size="4" value="${page.countNum}"/></span>
</center>
</body>
</html>