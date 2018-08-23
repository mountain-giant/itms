<%--
  Created by IntelliJ IDEA.
  User: Lister
  Date: 16/11/23
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>页面被吃掉了...</title>
    <jsp:include page="/sys/basecss.bms"/>
</head>
<body>
        <!-- Content Header (Page header)
        <section class="content-header">
            <h1>
                404 Error Page
            </h1>
        </section> -->
        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-yellow"> 404</h2>

                <div class="error-content">
                    <h3><i class="fa fa-warning text-yellow"></i> 哦!页面没有找到.</h3>
                    <p>
                        我们无法找到您正在寻找的页面.你可以尝试回到<a href="/sys/index.bms">主页面</a>
                    </p>
                </div>
                <!-- /.error-content -->
            </div>
            <!-- /.error-page -->
        </section>
        <!-- /.content -->
</body>
</html>
