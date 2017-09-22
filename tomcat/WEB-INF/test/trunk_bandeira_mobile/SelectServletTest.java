package trunk_bandeira_mobile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class SelectServletTest {

  @Test
  public void testProductSetList() throws IOException {
    open("/productset/list");
    open("/productset/list_at", "date=2008-03-01");
    open("/productset/product");
    open("/productset/product_at", "date=2008-03-01");
    open("/productset/productset");
    open("/productset/productset_at", "date=2008-03-01");
    open("/productset/class");
    open("/productset/class_at", "date=2008-03-01");
  }

  @Test
  public void testProductList() throws IOException {
    open("/product/list");
  }

  @Test
  public void testPointSetList() throws IOException {
    open("/pointset/list");
    open("/pointset/list_at", "date=2008-03-01");
    open("/pointset/point");
    open("/pointset/point_at", "date=2008-03-01");
    open("/pointset/pointset");
    open("/pointset/pointset_at", "date=2008-03-01");
  }

  @Test
  public void testPointList() throws IOException {
    open("/point/list");
  }

  @Test
  public void testViewList() throws IOException {
    open("/view/list");
  }

  @Test
  public void testScenariumOpen() throws IOException {
    open("/scenarium/open");
  }

  @Test
  public void testHash() throws IOException {
    open("/point/list.hash");
    open("/pointset/list.hash");
    open("/pointset/list_at.hash", "date=2008-03-01");
    open("/pointset/point.hash");
    open("/pointset/point_at.hash", "date=2008-03-01");
    open("/pointset/pointset.hash");
    open("/pointset/pointset_at.hash", "date=2008-03-01");
    open("/productset/list.hash");
    open("/productset/list_at.hash", "date=2008-03-01");
    open("/productset/product.hash");
    open("/productset/product_at.hash", "date=2008-03-01");
    open("/productset/productset.hash");
    open("/productset/productset_at.hash", "date=2008-03-01");
    open("/productset/class.hash");
    open("/productset/class_at.hash", "date=2008-03-01");
    open("/view/list.hash");
    open("/scenarium/open.hash");
  }

  private void open(String path, String... args) throws IOException,
    MalformedURLException {
    {
      StringBuilder sb = new StringBuilder();
      sb.append(path);
      if (args.length > 0) {
        sb.append('?');
        for (String arg : args) {
          sb.append(arg);
        }
      }
      sb.append(": ");
      System.out.println(sb.toString());
    }
    String[] sufixs = new String[] { "", ".json", ".hash", ".xml" };
    if (path.contains(".")) {
      sufixs = new String[] { "" };
    }
    for (String sufix : sufixs) {
      StringBuilder sb = new StringBuilder("http://localhost:8080/mbandeirabr");
      sb.append(path);
      sb.append(sufix);
      if (args.length > 0) {
        sb.append('?');
        for (String arg : args) {
          sb.append(arg);
        }
      }
      long time = System.currentTimeMillis();
      URLConnection connection = new URL(sb.toString()).openConnection();
      InputStream in = connection.getInputStream();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      byte[] bytes = new byte[1024];
      for (int n; (n = in.read(bytes)) != -1;) {
        out.write(bytes, 0, n);
      }
      in.close();
      System.out.println("\t" + sufix + ": " + out.size() + " bytes in "
        + (System.currentTimeMillis() - time) + " milisegs");
    }
  }
}
