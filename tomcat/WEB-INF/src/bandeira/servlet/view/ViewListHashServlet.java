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

public class ViewListHashServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(ViewListHashServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("id", "productset_id", "pointset_id", "name",
        "factor", "created_date", "user_created_id", "is_view_corp",
        "is_view_public");
      rs.next();
      writer.writeLong("id", rs.getLong("id"));
      writer.writeLong("productset_id", rs.getLong("productset_id"));
      writer.writeLong("pointset_id", rs.getLong("pointset_id"));
      writer.writeLong("name", rs.getLong("name"));
      writer.writeLong("factor", rs.getLong("factor"));
      writer.writeLong("created_date", rs.getLong("created_date"));
      writer.writeLong("user_created_name", rs.getLong("user_created_name"));
      writer.writeLong("is_view_corp", rs.getLong("is_view_corp"));
      writer.writeLong("is_view_public", rs.getLong("is_view_public"));
      rs.close();
      ps.close();
      writer.flush();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
  }

}
