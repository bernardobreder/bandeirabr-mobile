package bandeira.util.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 
 * @author Tecgraf
 */
public class JsonWriter extends AbstractWriter {

  /** Calendário */
  protected final Calendar c = Calendar.getInstance();
  /** Calendário */
  protected final StringBuilder sb = new StringBuilder();
  /** Formatador de data */
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
  public JsonWriter(OutputStream out) throws IOException {
    super(out);
    out.write('[');
  }

  @Override
  public JsonWriter begin() throws IOException {
    out.write('[');
    return this;
  }

  @Override
  public JsonWriter next() throws IOException {
    out.write(',');
    return this;
  }

  @Override
  public JsonWriter end() throws IOException {
    out.write(']');
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractWriter writeColumns(String... names) throws IOException {
    out.write('[');
    for (int n = 0; n < names.length; n++) {
      out.write('\"');
      write(names[n]);
      out.write('\"');
      if (n != names.length - 1) {
        out.write(',');
      }
    }
    out.write(']');
    return this;
  }

  @Override
  public JsonWriter writeInt(String name, int value) throws IOException {
    write(Integer.toString(value));
    return this;
  }

  @Override
  public JsonWriter writeLong(String name, long value) throws IOException {
    write(Long.toString(value));
    return this;
  }

  @Override
  public JsonWriter writeFloat(String name, float value) throws IOException {
    write(Float.toString(value));
    return this;
  }

  @Override
  public JsonWriter writeDouble(String name, double value) throws IOException {
    write(Double.toString(value));
    return this;
  }

  @Override
  public JsonWriter writeBoolean(String name, boolean value) throws IOException {
    if (value) {
      out.write(trueBytes);
    }
    else {
      out.write(falseBytes);
    }
    return this;
  }

  public JsonWriter writeInt(String name, Integer value) throws IOException {
    write(value.toString());
    return this;
  }

  public JsonWriter writeLong(String name, Long value) throws IOException {
    write(value.toString());
    return this;
  }

  public JsonWriter writeFloat(String name, Float value) throws IOException {
    write(value.toString());
    return this;
  }

  /**
   * @param name
   * @param value
   * @return this
   * @throws IOException
   */
  public JsonWriter writeDouble(String name, Double value) throws IOException {
    write(value.toString());
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonWriter writeString(String name, String value) throws IOException {
    if (value != null) {
      out.write('\"');
      write(value);
      out.write('\"');
    }
    else {
      out.write(nullBytes);
    }
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonWriter writeDate(String name, Date value) throws IOException {
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
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonWriter writeNull() throws IOException {
    out.write(nullBytes);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonWriter flush() throws IOException {
    out.write(']');
    out.flush();
    return this;
  }

}
