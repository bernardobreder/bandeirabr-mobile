package bandeira.servlet.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import bandeira.database.ServerDBConnection;
import bandeira.util.writer.AbstractWriter;
import bandeira.util.writer.BinaryWriter;
import bandeira.util.writer.BufferOutputStream;
import bandeira.util.writer.HashWriter;
import bandeira.util.writer.JsonWriter;
import bandeira.util.writer.XmlWriter;

public class AuthServlet extends HttpServlet {

  protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
    "yyyy-mm-dd");

  /**  */
  private static final Timer timer = new Timer(true);
  /**  */
  private Connection connection;
  /**  */
  private TimerTask timerTask;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    timerTask = new TimerTask() {
      @Override
      public void run() {
        try {
          synchronized (timer) {
            if (connection != null) {
              connection.prepareStatement("select 1 from dual").execute();
            }
          }
        }
        catch (Throwable e) {
          this.cancel();
          e.printStackTrace();
        }
        System.out.println("ping");
      }
    };
    timer.schedule(timerTask, 0, 10 * 1000);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
    super.destroy();
    timerTask.cancel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      super.doGet(req, resp);
    }
    catch (ServletException e) {
      rollback();
    }
    catch (IOException e) {
      rollback();
    }
  }

  /**
   * 
   */
  private void rollback() {
    synchronized (timer) {
      if (this.connection != null) {
        try {
          this.connection.rollback();
        }
        catch (SQLException e) {
          Connection c = this.connection;
          this.connection = null;
          try {
            c.close();
          }
          catch (SQLException e1) {
          }
        }
      }
    }
  }

  /**
   * @return conexão
   * @throws SQLException
   */
  public Connection getConnection() throws SQLException {
    synchronized (timer) {
      if (this.connection == null) {
        this.connection = ServerDBConnection.connect();
      }
      if (connection instanceof OracleConnection) {
        OracleConnection oracleConnection = (OracleConnection) connection;
        if (oracleConnection.pingDatabase() != 0) {
          try {
            connection.close();
          }
          catch (SQLException e) {
          }
          connection = null;
          return getConnection();
        }
      }
      return this.connection;
    }
  }

  /**
   * @param c
   * @return query
   * @throws IOException
   */
  protected String getSqlStream(Class<?> c) throws IOException {
    InputStream in =
      c.getResourceAsStream("/" + c.getPackage().getName().replace('.', '/')
        + "/" + c.getSimpleName() + ".sql");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] bytes = new byte[1024];
    for (int n; (n = in.read(bytes)) != -1;) {
      out.write(bytes, 0, n);
    }
    return new String(out.toByteArray(), "utf-8");
  }

  /**
   * @param req
   * @param resp
   * @return writer
   * @throws IOException
   */
  protected AbstractWriter getWriter(HttpServletRequest req,
    HttpServletResponse resp) throws IOException {
    AbstractWriter writer;
    if (req.getRequestURI().endsWith(".hash")) {
      resp.setContentType("text/json;charset=utf-8");
      writer = new HashWriter(new BufferOutputStream(resp.getOutputStream()));
    }
    else if (req.getRequestURI().endsWith(".xml")) {
      resp.setContentType("text/xml;charset=utf-8");
      writer = new XmlWriter(new BufferOutputStream(resp.getOutputStream()));
    }
    else if (req.getRequestURI().endsWith(".bin")) {
      resp.setContentType("text/bin;charset=utf-8");
      writer = new BinaryWriter(new BufferOutputStream(resp.getOutputStream()));
    }
    else {
      resp.setContentType("text/json;charset=utf-8");
      writer = new JsonWriter(new BufferOutputStream(resp.getOutputStream()));
    }
    return writer;
  }

  protected Date readParamDate(HttpServletRequest req, String key) {
    Date date;
    try {
      String dateParam = req.getParameter(key);
      if (dateParam == null) {
        throw new IllegalArgumentException("date is required");
      }
      date = new Date(DATE_FORMAT.parse(dateParam).getTime());
    }
    catch (ParseException e) {
      throw new IllegalArgumentException(
        "date need be in the format yyyy-MM-dd");
    }
    return date;
  }

}
