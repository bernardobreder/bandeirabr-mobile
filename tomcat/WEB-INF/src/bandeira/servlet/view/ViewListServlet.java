package bandeira.servlet.view;

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

public class ViewListServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(ViewListServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("id", "productset_id", "pointset_id", "name",
        "factor", "created_date", "user_created_id", "is_view_corp",
        "is_view_public");
      while (rs.next()) {
        writer.next();
        writer.begin();
        writer.writeInt("id", rs.getInt("id")).next();
        writer.writeString("productset_id", rs.getString("productset_id"))
          .next();
        writer.writeInt("pointset_id", rs.getInt("pointset_id")).next();
        writer.writeString("name", rs.getString("name")).next();
        writer.writeLong("factor", rs.getLong("factor")).next();
        writer.writeDate("created_date", rs.getDate("created_date")).next();
        writer.writeString("user_created_name",
          rs.getString("user_created_name")).next();
        writer.writeBoolean("is_view_corp", rs.getBoolean("is_view_corp"))
          .next();
        writer.writeBoolean("is_view_public", rs.getBoolean("is_view_public"));
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
