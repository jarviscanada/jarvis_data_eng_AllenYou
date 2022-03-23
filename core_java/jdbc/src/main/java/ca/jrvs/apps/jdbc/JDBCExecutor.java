package ca.jrvs.apps.jdbc;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Properties;



public class JDBCExecutor {
  final static Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);
  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost","hplussport","postgres", "password");
    try{
      Connection connection = dcm.getConnection();
      OrderDAO orderDAO = new OrderDAO(connection);
      Order order = orderDAO.findById(1000);
      System.out.println(order);

    }catch(SQLException e){
      JDBCExecutor.logger.error("Error: Could not establish connection", e);
      e.printStackTrace();
    }
  }
}