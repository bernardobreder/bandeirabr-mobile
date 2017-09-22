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

public class ProductSetClassListHashServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(ProductSetClassListHashServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("parent_id", "key1", "key2", "key3", "key4",
        "begin_date", "end_date");
      rs.next();
      writer.writeLong("parent_id", rs.getLong("parent_id"));
      writer.writeLong("key1", rs.getLong("key1"));
      writer.writeLong("key2", rs.getLong("key2"));
      writer.writeLong("key3", rs.getLong("key3"));
      writer.writeLong("key4", rs.getLong("key4"));
      writer.writeLong("begin_date", rs.getLong("begin_date"));
      writer.writeLong("end_date", rs.getLong("end_date"));
      rs.close();
      ps.close();
      writer.flush();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
  }

}
