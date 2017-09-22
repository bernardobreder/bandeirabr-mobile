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

public class ProductSetListServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(ProductSetListServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("id", "shortname", "name", "unit", "begin_date",
        "end_date");
      while (rs.next()) {
        writer.next();
        writer.begin();
        writer.writeString("id", rs.getString("id")).next();
        writer.writeString("shortname", rs.getString("shortname")).next();
        writer.writeString("name", rs.getString("name")).next();
        writer.writeBoolean("is_mass", rs.getInt("unit") == 1).next();
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
