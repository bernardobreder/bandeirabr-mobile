package bandeira.servlet.product;

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

public class ProductListHashServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(ProductListHashServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("id", "shortname", "name", "is_mass", "begin_date",
        "end_date");
      rs.next();
      writer.writeLong("id", rs.getLong("id"));
      writer.writeLong("shortname", rs.getLong("shortname"));
      writer.writeLong("name", rs.getLong("name"));
      writer.writeLong("is_mass", rs.getLong("unit"));
      writer.writeLong("density", rs.getLong("density"));
      writer.writeLong("active", rs.getLong("active"));
      writer.writeLong("key1", rs.getLong("key1"));
      writer.writeLong("key2", rs.getLong("key2"));
      writer.writeLong("key3", rs.getLong("key3"));
      writer.writeLong("key4", rs.getLong("key4"));
      rs.close();
      ps.close();
      writer.flush();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
  }

}
