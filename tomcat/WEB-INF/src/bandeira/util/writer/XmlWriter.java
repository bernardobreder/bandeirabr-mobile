package bandeira.util.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 
 * @author Tecgraf
 */
public class XmlWriter extends AbstractWriter {

  /** Calendário */
  protected final Calendar c = Calendar.getInstance();
  /** Calendário */
  protected final StringBuilder sb = new StringBuilder();
  /** Formatador de data */
  protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  /** Bytes */
  protected static final byte[] nullBytes = new byte[] { 'n', 'u', 'l', 'l' };
  /** Bytes */
  protected static final byte[] trueBytes = new byte[] { 't', 'r', 'u', 'e' };
  /** Bytes */
  protected static final byte[] falseBytes = new byte[] { 'f', 'a', 'l', 's',
      'e' };

  /**
   * @param out
   * @throws IOException
   */
  public XmlWriter(OutputStream out) throws IOException {
    super(out);
    write("<data>");
    out.write('\n');
  }

  @Override
  public XmlWriter begin() throws IOException {
    write("\t<item");
    return this;
  }

  @Override
  public XmlWriter next() throws IOException {
    //    out.write('\n');
    return this;
  }

  @Override
  public XmlWriter end() throws IOException {
    write("/>\n");
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractWriter writeColumns(String... names) throws IOException {
    write("\t<columnset>");
    for (String name : names) {
      write("<column name=\"");
      write(name);
      write("\"/>");
    }
    write("</columnset>\n");
    return this;
  }

  @Override
  public XmlWriter writeInt(String name, int value) throws IOException {
    out.write(' ');
    write(name);
    out.write('=');
    out.write('\'');
    write(Integer.toString(value));
    out.write('\'');
    return this;
  }

  @Override
  public XmlWriter writeLong(String name, long value) throws IOException {
    out.write(' ');
    write(name);
    out.write('=');
    out.write('\'');
    write(Long.toString(value));
    out.write('\'');
    return this;
  }

  @Override
  public XmlWriter writeFloat(String name, float value) throws IOException {
    out.write(' ');
    write(name);
    out.write('=');
    out.write('\'');
    write(Float.toString(value));
    out.write('\'');
    return this;
  }

  @Override
  public XmlWriter writeDouble(String name, double value) throws IOException {
    out.write(' ');
    write(name);
    out.write('=');
    out.write('\'');
    write(Double.toString(value));
    out.write('\'');
    return this;
  }

  @Override
  public XmlWriter writeBoolean(String name, boolean value) throws IOException {
    out.write(' ');
    write(name);
    out.write('=');
    out.write('\'');
    if (value) {
      out.write(trueBytes);
    }
    else {
      out.write(falseBytes);
    }
    out.write('\'');
    return this;
  }

  public XmlWriter writeInt(String name, Integer value) throws IOException {
    if (value != null) {
      out.write(' ');
      write(name);
      out.write('=');
      out.write('\'');
      write(value.toString());
      out.write('\'');
    }
    return this;
  }

  public XmlWriter writeLong(String name, Long value) throws IOException {
    if (value != null) {
      out.write(' ');
      write(name);
      out.write('=');
      out.write('\'');
      write(value.toString());
      out.write('\'');
    }
    return this;
  }

  public XmlWriter writeFloat(String name, Float value) throws IOException {
    if (value != null) {
      out.write(' ');
      write(name);
      out.write('=');
      out.write('\'');
      write(value.toString());
      out.write('\'');
    }
    return this;
  }

  public XmlWriter writeDouble(String name, Double value) throws IOException {
    if (value != null) {
      out.write(' ');
      write(name);
      out.write('=');
      out.write('\'');
      write(value.toString());
      out.write('\'');
    }
    return this;
  }

  @Override
  public XmlWriter writeString(String name, String value) throws IOException {
    if (value != null) {
      out.write(' ');
      write(name);
      out.write('=');
      if (value != null) {
        out.write('\"');
        write(value);
        out.write('\"');
      }
    }
    return this;
  }

  @Override
  public XmlWriter writeDate(String name, Date value) throws IOException {
    if (value != null) {
      out.write(' ');
      write(name);
      out.write('=');
      if (value != null) {
        c.setTime(value);
        out.write('\"');
        sb.delete(0, sb.length());
        sb.append(c.get(Calendar.YEAR));
        sb.append('-');
        sb.append(c.get(Calendar.MONDAY));
        sb.append('-');
        sb.append(c.get(Calendar.DAY_OF_MONTH));
        write(sb.toString());
        out.write('\"');
      }
      else {
        out.write(nullBytes);
      }
    }
    return this;
  }

  @Override
  public XmlWriter writeNull() throws IOException {
    out.write(nullBytes);
    return this;
  }

  @Override
  public XmlWriter flush() throws IOException {
    write("</data>");
    out.flush();
    return this;
  }

  /**
   * Retorna
   * 
   * @return dateFormat
   */
  public SimpleDateFormat getDateFormat() {
    return dateFormat;
  }

  /**
   * @param dateFormat
   */
  public void setDateFormat(SimpleDateFormat dateFormat) {
    this.dateFormat = dateFormat;
  }

}
