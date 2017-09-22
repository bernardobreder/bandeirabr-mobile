package bandeira.servlet.scenarium;

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

public class ScenariumOpenHashServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(ScenariumOpenHashServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ps.setLong(1, 3676);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("time", "balance", "point", "product", "corp_mass",
        "corp_volume", "mass", "volume");
      rs.next();
      writer.writeLong("time", rs.getLong("time"));
      writer.writeLong("balance", rs.getLong("balance"));
      writer.writeLong("point", rs.getLong("point"));
      writer.writeLong("product", rs.getLong("product"));
      writer.writeLong("corp_mass", rs.getLong("corp_mass"));
      writer.writeLong("corp_volume", rs.getLong("corp_volume"));
      writer.writeLong("mass", rs.getLong("mass"));
      writer.writeLong("volume", rs.getLong("volume"));
      rs.close();
      ps.close();
      writer.flush();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}
