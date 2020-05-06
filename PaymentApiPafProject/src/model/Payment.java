package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paymentmanagement?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String Ptype, String Pname, String Pamount, String Pdesc)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement    
			String query = " insert into payment(`payID`,`payType`,`payName`,`payAmount`,`payDesc`)" + " values (?, ?, ?, ?, ?)"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, 0);    
			preparedStmt.setString(2, Ptype);    
			preparedStmt.setString(3, Pname);    
			preparedStmt.setDouble(4, Double.parseDouble(Pamount));    
			preparedStmt.setString(5, Pdesc);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newPayments = readPayments(); 
			output =  "{\"status\":\"success\", \"data\": \"" +        
							newPayments + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the payment.\"}";  
			System.err.println(e.getMessage());   
		} 
		
	  return output;  
	} 
	
	public String readPayments()  
	{   
		String output = ""; 
	
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>PaymentType</th><th>PatientName</th><th>PatientAmount</th><th>PaymentDescription</th><th>Update</th><th>Remove</th></tr>"; 
	 
			String query = "select * from payment";    
			Statement stmt = con.createStatement();    
			ResultSet rs = stmt.executeQuery(query); 
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				String payID = Integer.toString(rs.getInt("payID"));     
				String payType = rs.getString("payType");     
				String payName = rs.getString("payName");     
				String payAmount = Double.toString(rs.getDouble("payAmount"));     
				String payDesc = rs.getString("payDesc"); 
			
	 
				// Add into the html table 
				output += "<tr><td><input id=\'hidPaymentIDUpdate\' name=\'hidPaymentIDUpdate\' type=\'hidden\' value=\'" + payID + "'>" 
							+ payType + "</td>";      
				output += "<td>" + payName + "</td>";     
				output += "<td>" + payAmount + "</td>";     
				output += "<td>" + payDesc + "</td>"; 
	 
				// buttons     
//				output += "<td><input name=\'btnUpdate\' type=\'button\' value=\'Update\' class=\' btnUpdate btn btn-secondary\'></td>"
//						//+ "<td><form method=\"post\" action=\"payments.jsp\">      "
//						+ "<input name=\'btnRemove\' type=\'submit\' value=\'Remove\' class=\'btn btn-danger\'> "
//						+ "<input name=\"hidPaymentIDDelete\" type=\"hidden\" value=\"" + paymentID + "\">" + "</form></td></tr>"; 
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-payid='" + payID + "'>" + "</td></tr>"; 
			} 
	 
			con.close(); 
	 
			// Complete the html table    
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the payments.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	 
	
	public String updatePayment(String ID, String Ptype, String Pname, String Pamount, String Pdesc)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE payment SET payType=?,payName=?,payAmount=?,payDesc=? WHERE payID=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, Ptype);    
			preparedStmt.setString(2, Pname);    
			preparedStmt.setDouble(3, Double.parseDouble(Pamount));    
			preparedStmt.setString(4, Pdesc);    
			preparedStmt.setInt(5, Integer.parseInt(ID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newPayments = readPayments();    
			output = "{\"status\":\"success\", \"data\": \"" +        
									newPayments + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";   
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	} 
	
	public String deletePayment(String payID)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for deleting."; } 
	 
			// create a prepared statement    
			String query = "delete from payment where payID=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(payID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newPayments = readPayments();    
			output = "{\"status\":\"success\", \"data\": \"" +       
								newPayments + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the payment.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	 
	 
}