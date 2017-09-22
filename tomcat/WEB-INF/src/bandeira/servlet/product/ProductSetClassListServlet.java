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

public class ProductSetClassListServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(ProductSetClassListServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("parent_id", "key1", "key2", "key3", "key4",
        "begin_date", "end_date");
      while (rs.next()) {
        writer.next();
        writer.begin();
        writer.writeInt("parent_id", rs.getInt("parent_id")).next();
        writer.writeString("key1", rs.getString("key1")).next();
        writer.writeString("key2", rs.getString("key2")).next();
        writer.writeString("key3", rs.getString("key3")).next();
        writer.writeString("key4", rs.getString("key4")).next();
        writer.writeDate("begin_date", rs.getDate("begin_date")).next();
        writer.writeDate("end_date", rs.getDate("end_date"));
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
