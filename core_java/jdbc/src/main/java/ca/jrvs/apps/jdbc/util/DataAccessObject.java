package ca.jrvs.apps.jdbc.util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DataAccessObject  <T extends DataTransferObject> {

  final static Logger logger = LoggerFactory.getLogger(DataAccessObject.class);
  protected final Connection connection;
  protected final static String LAST_VAL = "SELECT last_value FROM ";
  protected final static String CUSTOMER_SEQUENCE = "hp_customer_seq";

  public DataAccessObject(Connection connection){
    super();
    this.connection = connection;
  }

  public abstract T findById(long id);
  public abstract List<T> findAll();
  public abstract T update(T dto);
  public abstract T create(T dto);
  public abstract void delete(long id);

  protected int getLastVal(String sequence){
    int key = 0;
    String sql = LAST_VAL + sequence;
    try(Statement statement = connection.createStatement()){
      ResultSet rs = statement.executeQuery(sql);
      while(rs.next()){
        key=rs.getInt(1);
      }
      return key;
    }catch (SQLException e ){
      DataAccessObject.logger.error("Error: Could not establish connection", e);
      throw new RuntimeException(e);
    }
  }
}