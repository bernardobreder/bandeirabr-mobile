package bandeira.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;
import bandeirabr.logic.util.time.MonthTime;

public class ServerDBConnection {

  public static Connection connect() throws SQLException {
    String localurl = "jdbc:oracle:thin:@localhost:5000:sismica";
    String url = "jdbc:oracle:thin:@piedade.tecgraf.puc-rio.br:1521:sismica";
    String user = "sbbhml";
    String password = "sbbhml";
    Connection connection = connect(localurl, user, password);
    if (connection == null) {
      connection = connect(url, user, password);
    }
    return connection;
  }

  protected static Connection connect(String url, String user, String password)
    throws SQLException {
    OracleDataSource dataSource = new OracleDataSource();
    dataSource.setURL(url);
    dataSource.setUser(user);
    dataSource.setPassword(password);
    Connection connection = dataSource.getConnection();
    OracleConnection oracleConnection = (OracleConnection) connection;
    oracleConnection.setAutoCommit(false);
    return connection;
  }

  public static void main(String[] args) throws SQLException {
    Connection connect = new ServerDBConnection().connect();
    PreparedStatement ps =
      connect
        .prepareStatement("select cena_nr_numero_cenario id, DIRE_CD_ID folder_id, CENA_NM_CENARIO name , cena_dt_periodo_inicio begin_date, cena_dt_periodo_fim end_date from cenario order by cena_nr_numero_cenario, DIRE_CD_ID");
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      int id = rs.getInt(1);
      int parentId = rs.getInt(2);
      String name = rs.getString(3);
      MonthTime beginTime = new MonthTime(rs.getDate(4));
      MonthTime endTime = new MonthTime(rs.getDate(5));
      System.out
        .println(String
          .format(
            "[_tree add:%d value:[[BRScenariumInfo alloc] init:%d name:@\"%s\" parentId:%d startTime:[[MonthTime alloc] initWithYear:%d andMonth:%d] endTime:[[MonthTime alloc] initWithYear:%d andMonth:%d]]];",
            id, id, name, parentId, beginTime.getYear(), beginTime.getMonth(),
            endTime.getYear(), endTime.getMonth()));
    }
  }

}
