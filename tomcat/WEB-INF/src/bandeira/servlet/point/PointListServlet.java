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

public class PointListServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(PointListServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("id", "type", "name");
      while (rs.next()) {
        writer.next();
        writer.begin();
        writer.writeInt("id", rs.getInt("id")).next();
        writer.writeString("type", rs.getString("type")).next();
        writer.writeString("name", rs.getString("name"));
        writer.end();
      }
      rs.close();
      ps.close();
      writer.flush();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
  }

}
