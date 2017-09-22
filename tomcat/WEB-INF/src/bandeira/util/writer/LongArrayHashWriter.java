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
public class LongArrayHashWriter extends AbstractWriter {

  private static final long NULL_HASH = "null".hashCode();
  /** Calendário */
  protected final Calendar c = Calendar.getInstance();
  /** Calendário */
  protected long[] hash;
  /** Indice */
  protected int index;

  /**
   * @param out
   */
  public LongArrayHashWriter(OutputStream out) {
    super(out);
    hash = new long[8];
  }

  @Override
  public LongArrayHashWriter begin() throws IOException {
    inc(3, 107);
    return this;
  }

  @Override
  public LongArrayHashWriter next() throws IOException {
    inc(5, 103);
    return this;
  }

  @Override
  public LongArrayHashWriter end() throws IOException {
    inc(7, 101);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractWriter writeColumns(String... names) throws IOException {
    for (int n = 0; n < names.length; n++) {
      inc(101, hash(names[n]));
    }
    return this;
  }

  @Override
  public LongArrayHashWriter writeInt(String name, int value)
    throws IOException {
    inc(11, hash(name));
    inc(11, value);
    return this;
  }

  @Override
  public LongArrayHashWriter writeLong(String name, long value)
    throws IOException {
    inc(13, hash(name));
    inc(13, value);
    return this;
  }

  @Override
  public LongArrayHashWriter writeFloat(String name, float value)
    throws IOException {
    inc(17, hash(name));
    inc(17, Float.floatToIntBits(value));
    return this;
  }

  @Override
  public LongArrayHashWriter writeDouble(String name, double value)
    throws IOException {
    inc(19, hash(name));
    inc(19, Double.doubleToRawLongBits(value));
    return this;
  }

  @Override
  public LongArrayHashWriter writeBoolean(String name, boolean value)
    throws IOException {
    inc(23, hash(name));
    inc(23, value ? 1231 : 1237);
    return this;
  }

  @Override
  public LongArrayHashWriter writeString(String name, String value)
    throws IOException {
    inc(29, hash(name));
    if (value != null) {
      inc(29, hash(value));
    }
    else {
      inc(29, NULL_HASH);
    }
    return this;
  }

  @Override
  public LongArrayHashWriter writeDate(String name, Date value)
    throws IOException {
    inc(29, hash(name));
    if (value != null) {
      inc(29, hash(value.toString()));
    }
    else {
      inc(29, NULL_HASH);
    }
    return this;
  }

  @Override
  public LongArrayHashWriter writeNull() throws IOException {
    inc(29, 37);
    return this;
  }

  protected void inc(int prime, long h) {
    hash[index] = hash[index] * prime + h;
    index = (index + 1) % hash.length;
  }

  protected long hash(String value) {
    long h = 31 * value.length();
    for (int i = 0; i < value.length(); i++) {
      h = 31 * h + value.charAt(i);
    }
    return h;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LongArrayHashWriter flush() throws IOException {
    for (int n = 0; n < hash.length; n++) {
      write(Long.toString(Math.abs(hash[n]), 36));
    }
    out.flush();
    return this;
  }

}
