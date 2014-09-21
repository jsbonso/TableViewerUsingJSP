package com.tutorialsdojo;

import java.sql.*;

/**
 * This is a sample program to show the table contents from a database via JSP. 
 * Once called by the JSP, this class connects to an Oracle database, fetch 
 * all of its values, format it to a HTML table and finally, send it back to the JSP.
 * 
 * www.TutorialsDojo.com
 * 
 * @author Sensei
 *
 */
public class DBViewer {
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet result = null;
	private ResultSetMetaData rsmd = null;

	private int columnCount = 0;
	
	// You can set your default values here.
    private static final String default_tableName = "ALL_TABLES";
    private static final String selectQuery = "SELECT * FROM "; 
    
    // A public, no-argument constructor for our class.
	public DBViewer(){
		
	}	
	
	
	/**  
	 * @param tableName that the user inputed from the HTML form.
	 * @return A String which contains HTML Table codes.
	 * @throws SQLException
	 */
	
	public String showTable(String tableName) throws SQLException {
	     
		// If the user didn't specify a table, then set it to a default value
		if (tableName == "" || tableName == null){
			tableName = default_tableName;
		}
		
		// Check if there is an Oracle JDBC driver installed. 
		try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch ( Exception e ) {
            System.out.println("[ERROR] No Oracle JDBC (ojdbc-14.jar) found.");
        }
		
	    try {
	      	
	        /* 
	         * This is where we set the values of our Database details. See the
	         * syntax below: 
	    	 * 
	    	 * String url = "jdbc:oracle:thin:@<SERVER_NAME>:<PORT>:<DATABASE NAME>";
	         * conn = DriverManager.getConnection(url,<USERNAME>, <PASSWORD>); */
	        	    		    	
	    	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	        conn = DriverManager.getConnection(url,"tutorialsdojo", "Password123");
	 
	        stmt = conn.createStatement();
	        
	        // This will execute the query: 
	        //  "SELECT * FROM " + <the table that the user specified.>
	        
	        result = stmt.executeQuery (selectQuery + tableName);
	        
	        // Before sending the result to the JSP, we'll format it to a HTML Table.
	       return (formatResult(result));
	          	       
	     } catch (SQLException error) {
	         return ("SQL error: <pre> " + error + " </pre>\n");
	     
	     } finally {
	    	 
	    	 // Close the Database Connection:
	        if (result!= null) result.close();
	        if (stmt!= null) stmt.close();
	        if (conn!= null) conn.close();
	     }
	  }
	 
	 
	
	  /**
	   * 
	   * @param ResultSet from showTable() method
	   * @return A String which contains HTML Table codes.
	   * @throws SQLException
	   */
	
	  public String formatResult(ResultSet rset) throws SQLException {

		StringBuffer resultToHTMLTable = new StringBuffer();
	    
	    if (!rset.next())
	    	resultToHTMLTable.append("<P> There are no matching rows. Please select other table. <P>\n");
	    
	    else{
	    	
	       /* This is the start tag of our HTML table.
	        *  
	        * Just for quick review, below are the HTML tags that we'll be using:  
	        * 	
	        *   <TABLE> - HTML Table tag.
	        * 	<TH>	- Table Header. Text will be bold and centered.  
	        * 	<TR>	- Table Row. Adds new rows. 
	        * 	<TD>	- Table Details. Text will be left-aligned. 
	        * 
	    	*/
	    	
	    	resultToHTMLTable.append("<TABLE style=\"margin-left:40px\" border=\"1\">");
	    		
	    	
	       /* First, we will generate the Column Header of our table. We are using the 
	        * metadata of the table using ResultSetMetaData to know how many columns the 
	        * table has and then afterwards, we'll fetch all of the column names.*/
	    	   		
	    	rsmd = rset.getMetaData();
	    		 
	    	System.out.println("column count: " + rsmd.getColumnCount());
	    	columnCount= rsmd.getColumnCount();
	    	
	    	resultToHTMLTable.append("<TR>");
	    	
	    	for(int i=1; i<=columnCount; i++){
	    		resultToHTMLTable.append("<TH bgcolor=\"#FFF0F0\">");			
	    		resultToHTMLTable.append(rsmd.getColumnName(i));
	    		resultToHTMLTable.append("</TH>");
	    	}
	    			
	    	
	    	resultToHTMLTable.append("</TR>");
	    
	    	/*  This is the row details of our table. 
	    	 *  Basically, we will iterate through the ResultSet and then set the values
	    	 *  to the rows of our table
	    	 */
	    	
	    	while(rset.next()){
				    		
	    		resultToHTMLTable.append("<TR>");	    		
	    			
	    		for(int j=1;j<=columnCount;j++){
	    			resultToHTMLTable.append("<TD align=\"center\" bgcolor=\"#CCFFFF\">");			
	    			resultToHTMLTable.append(rset.getString(j));
	    			resultToHTMLTable.append("</TD>");
	    		}	   
	    		// End tag for the row.
	    		resultToHTMLTable.append("</TR>");	    		
	   		} 
	    	
	    	// End tag for our table
	    	resultToHTMLTable.append("</TABLE>");	    
	    }
	    
	    // Get the string value of the StringBuffer.
	    String generatedHTMLTable = resultToHTMLTable.toString();
	    
	    // For your reference, we will output the actual HTML values that we generated.
	    System.out.println("Generated HTML Table codes: "+ generatedHTMLTable);	    
	    
	    // Returns a string of HTML Table codes which contains table information.
	    return generatedHTMLTable;
	  }

}
