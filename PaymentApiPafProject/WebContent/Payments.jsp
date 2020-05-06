<%@ page import="model.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/payments.js"></script> 
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>PAYMENTS MANAGEMENT</h1>
				<form id="formPayment" name="formPayment" method="post" action="payments.jsp">  
					PaymentType:  
					<input id="payType" name="payType" type="text" class="form-control form-control-sm">  
					<br> PatientName:  
					<input id="payName" name="payName" type="text" class="form-control form-control-sm">  
					<br> PaymentAmount:  
					<input id="payAmount" name="payAmount" type="text" class="form-control form-control-sm">  
					<br> PaymentDescription:  
					<input id="payDesc" name="payDesc" type="text" class="form-control form-control-sm">  
					<br>  
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
					<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success">
					<%
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>
				<div id="alertError" class="alert alert-danger"></div>
				
				<br>
				<div id="divPaymentsGrid">
					<%
						Payment paymentObj = new Payment();
						out.print(paymentObj.readPayments());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
 
</body>
</html>