<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log In</title>
</head>
<body>
<div class = "container">
		<form action="LogIn" method = "post">
			<table>
				<tr style = "height:20px">
					<td>Email</td>
					<td><input type="text" name="email" />
					<td><p style="color:Red; font-size: 15px;">${error0}</p></td>
				</tr>
					<tr>
					<td>Password</td>
					<td><input type="password" name="password" /></td>
					<td><p style=" color:Red; font-size: 15px;">${error3}</p></td>
				</tr>
				<tr>
					<td>
						
					</td>
				</tr>

			</table>
			<input type="submit" value="Log in" />
		</form>
	</div>
	
</body>
</html>