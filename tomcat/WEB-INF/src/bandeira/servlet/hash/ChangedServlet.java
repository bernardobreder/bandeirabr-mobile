package bandeira.servlet.hash;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bandeira.servlet.util.AuthServlet;
import bandeira.servlet.view.ViewListHashServlet;
import bandeira.util.writer.AbstractWriter;

public class ChangedServlet extends AuthServlet {

  protected SortedSet<Entry> entrys = new TreeSet<Entry>();

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    {
      entrys = new TreeSet<ChangedServlet.Entry>();
      entrys.add(new Entry(ViewListHashServlet.class, "view"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      AbstractWriter writer = getWriter(req, resp);
      Connection conn = getConnection();
      for (Entry entry : entrys) {
        String query = getSqlStream(entry.servletClass);
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();
        writer.writeLong(entry.name, hash);
        rs.close();
        ps.close();
      }
      writer.flush();
    }
    catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  public static class Entry implements Comparable<Entry> {

    public final Class<?> servletClass;

    public final String name;

    public Entry(Class<?> servletClass, String name) {
      super();
      this.servletClass = servletClass;
      this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Entry o) {
      return name.compareTo(o.name);
    }

  }

}
