package bandeira.servlet.point;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bandeira.servlet.util.AuthServlet;
import bandeira.util.writer.AbstractWriter;


public class PointSetAtListHashServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    Date date = readParamDate(req, "date");
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(PointSetAtListHashServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ps.setDate(1, date);
      ps.setDate(2, date);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("id", "shortname", "name");
      rs.next();
      writer.writeLong("id", rs.getLong("id"));
      writer.writeLong("shortname", rs.getLong("shortname"));
      rs.close();
      ps.close();
      writer.flush();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
  }

}


