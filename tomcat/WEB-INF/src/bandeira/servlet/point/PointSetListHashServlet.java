package bandeira.servlet.point;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bandeira.servlet.util.AuthServlet;
import bandeira.util.writer.AbstractWriter;

public class PointSetListHashServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(PointSetListHashServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("id", "shortname", "name", "begin_date", "end_date");
      rs.next();
      writer.writeLong("id", rs.getLong("id"));
      writer.writeLong("shortname", rs.getLong("shortname"));
      writer.writeLong("begin_date", rs.getLong("begin_date"));
      writer.writeLong("end_date", rs.getLong("end_date"));
      writer.end();
      rs.close();
      ps.close();
      writer.flush();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
  }

}
