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
public class LongHashWriter extends AbstractWriter {

  private static final long END_HASH = 101;
  private static final long NEXT_HASH = 103;
  private static final long BEGIN_HASH = 107;
  private static final long NULL_HASH = "null".hashCode();
  /** Calendário */
  protected final Calendar c = Calendar.getInstance();
  /** Calendário */
  protected long hash = 1;

  /**
   * @param out
   */
  public LongHashWriter(OutputStream out) {
    super(out);
  }

  @Override
  public LongHashWriter begin() throws IOException {
    hash = 3 * hash + BEGIN_HASH;
    return this;
  }

  @Override
  public LongHashWriter next() throws IOException {
    hash = 5 * hash + NEXT_HASH;
    return this;
  }

  @Override
  public LongHashWriter end() throws IOException {
    hash = 7 * hash + END_HASH;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractWriter writeColumns(String... names) throws IOException {
    for (int n = 0; n < names.length; n++) {
      hash = 101 * hash + hash(names[n]);
    }
    return this;
  }

  @Override
  public LongHashWriter writeInt(String name, int value) throws IOException {
    hash = 11 * hash + hash(name);
    hash = 11 * hash + value;
    return this;
  }

  @Override
  public LongHashWriter writeLong(String name, long value) throws IOException {
    hash = 13 * hash + hash(name);
    hash = 13 * hash + value;
    return this;
  }

  @Override
  public LongHashWriter writeFloat(String name, float value) throws IOException {
    hash = 17 * hash + hash(name);
    hash = 17 * hash + Float.floatToIntBits(value);
    return this;
  }

  @Override
  public LongHashWriter writeDouble(String name, double value)
    throws IOException {
    hash = 19 * hash + hash(name);
    hash = 19 * hash + Double.doubleToRawLongBits(value);
    return this;
  }

  @Override
  public LongHashWriter writeBoolean(String name, boolean value)
    throws IOException {
    hash = 23 * hash + hash(name);
    hash = 23 * hash + (value ? 1231 : 1237);
    return this;
  }

  @Override
  public LongHashWriter writeString(String name, String value)
    throws IOException {
    hash = 29 * hash + hash(name);
    if (value != null) {
      hash = 29 * hash + hash(value);
    }
    else {
      writeNull();
    }
    return this;
  }

  @Override
  public LongHashWriter writeDate(String name, Date value) throws IOException {
    hash = 31 * hash + hash(name);
    if (value != null) {
      hash = 31 * hash + value.hashCode();
    }
    else {
      writeNull();
    }
    return this;
  }

  @Override
  public LongHashWriter writeNull() throws IOException {
    hash = 37 * hash + NULL_HASH;
    return this;
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
  public LongHashWriter flush() throws IOException {
    write(Long.toString(hash));
    out.flush();
    return this;
  }

}
