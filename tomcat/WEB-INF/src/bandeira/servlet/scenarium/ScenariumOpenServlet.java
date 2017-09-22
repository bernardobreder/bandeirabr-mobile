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

public class ScenariumOpenServlet extends AuthServlet {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection c = getConnection();
      String query = getSqlStream(ScenariumOpenServlet.class);
      PreparedStatement ps = c.prepareStatement(query);
      ps.setInt(1, 3676);
      ResultSet rs = ps.executeQuery();
      writer.writeColumns("time", "balance", "point", "product", "corp_mass",
        "corp_volume", "mass", "volume");
      while (rs.next()) {
        writer.next();
        writer.begin();
        writer.writeDate("time", rs.getDate("time")).next();
        writer.writeInt("balance", rs.getInt("balance")).next();
        writer.writeInt("point", rs.getInt("point")).next();
        writer.writeString("product", rs.getString("product")).next();
        writer.writeLong("corp_mass", rs.getLong("corp_mass")).next();
        writer.writeLong("corp_volume", rs.getLong("corp_volume")).next();
        writer.writeLong("mass", rs.getLong("mass")).next();
        writer.writeLong("volume", rs.getLong("volume"));
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
