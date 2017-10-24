/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.reports.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author rahimAdmin
 */
public class JasperServices {

   
    
    public static JasperPrint getStandardFrom() throws SQLException, JRException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        String databaseURL = "jdbc:mysql://localhost:3306/fairhandlerdb";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "we3lehTakoulTefah");
        Connection connection = DriverManager.getConnection(databaseURL, properties);
        return JasperFillManager.fillReport("C:\\Users\\rahimAdmin\\Dropbox\\fairHandler\\src\\nrz\\fairHandler\\reports\\template\\standardForm.jasper",
                null, connection);

    }
}
