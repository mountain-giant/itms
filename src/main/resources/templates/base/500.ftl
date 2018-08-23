<%--
  Created by IntelliJ IDEA.
  User: Lister
  Date: 16/11/22
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>程序出现异常</title>
    <jsp:include page="/sys/basecss.bms"/>
</head>
<body style="padding: 0px;background-color: #ECF0F5;">
    <section class="content-header">
        <h1>
            500 Error Page
        </h1>
    </section>

    <!-- Main content -->
    <section class="content">

        <div class="error-page">
            <h2 class="headline text-red">500</h2>

            <div class="error-content">
                <h3><i class="fa fa-warning text-red"></i> 哦!发生了一些错误.</h3>
                <p>
                    我们将马上修复.
                    你可以尝试回到<a href="/sys/index.bms">主页面</a>
                    <br/>
                    <%=request.getAttribute("error")%>
                </p>
            </div>
        </div>
        <!-- /.error-page -->

    </section>
<jsp:include page="/sys/basejs.bms"/>
</body>
</html>
