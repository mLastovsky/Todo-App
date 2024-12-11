<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Todo App</title>
        <link rel="stylesheet"
        	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        	crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="common/header.jsp"></jsp:include>
        <div class="container">
            <div class="col-md-6 col-md-offset-3" style="overflow: auto">
       		    <h2>Log in</h2>
       		    <form action="<%=request.getContextPath()%>/login" method="post">
       			    <div class="form-group">
       				    <input type="text" class="form-control" id="username" placeholder="username" name="username" required>
       			    </div>
       			    <div class="form-group">
       				    <input type="password" class="form-control" id="password" placeholder="password" name="password" required>
       			    </div>
       			    <button type="submit" class="btn btn-primary">Log in</button>
       		    </form>
       	    </div>
       	</div>
    </body>
</html>