package bandeira.util.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 
 * @author Tecgraf
 */
public class BigIntegerHashWriter extends AbstractWriter {

  private static final BigInteger HASH = new BigInteger("31");
  private static final BigInteger BEGIN_HASH = new BigInteger("107");
  private static final BigInteger NEXT_HASH = new BigInteger("103");
  private static final BigInteger END_HASH = new BigInteger("101");
  private static final BigInteger NULL_HASH = new BigInteger("109");
  private static final BigInteger TRUE_HASH = new BigInteger("1231");
  private static final BigInteger FALSE_HASH = new BigInteger("1237");
  /** Calendário */
  protected final Calendar c = Calendar.getInstance();
  /** Calendário */
  protected BigInteger hash = new BigInteger("1");

  /**
   * @param out
   */
  public BigIntegerHashWriter(OutputStream out) {
    super(out);
  }

  @Override
  public BigIntegerHashWriter begin() throws IOException {
    hash = hash.multiply(HASH).add(BEGIN_HASH);
    return this;
  }

  @Override
  public BigIntegerHashWriter next() throws IOException {
    hash = hash.multiply(HASH).add(NEXT_HASH);
    return this;
  }

  @Override
  public BigIntegerHashWriter end() throws IOException {
    hash = hash.multiply(HASH).add(END_HASH);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractWriter writeColumns(String... names) throws IOException {
    for (String name : names) {
      hash = hash.multiply(HASH).add(hash(name));
    }
    return this;
  }

  @Override
  public BigIntegerHashWriter writeInt(String name, int value)
    throws IOException {
    hash = hash.multiply(HASH).add(hash(name));
    hash = hash.multiply(HASH).add(new BigInteger(Integer.toString(value)));
    return this;
  }

  @Override
  public BigIntegerHashWriter writeLong(String name, long value)
    throws IOException {
    hash = hash.multiply(HASH).add(hash(name));
    hash = hash.multiply(HASH).add(new BigInteger(Long.toString(value)));
    return this;
  }

  @Override
  public BigIntegerHashWriter writeFloat(String name, float value)
    throws IOException {
    hash = hash.multiply(HASH).add(hash(name));
    hash =
      hash.multiply(HASH).add(
        new BigInteger(Long.toString(Float.floatToIntBits(value))));
    return this;
  }

  @Override
  public BigIntegerHashWriter writeDouble(String name, double value)
    throws IOException {
    hash = hash.multiply(HASH).add(hash(name));
    hash =
      hash.multiply(HASH).add(
        new BigInteger(Long.toString(Double.doubleToRawLongBits(value))));
    return this;
  }

  @Override
  public BigIntegerHashWriter writeBoolean(String name, boolean value)
    throws IOException {
    hash = hash.multiply(HASH).add(hash(name));
    hash = hash.multiply(HASH).add(value ? TRUE_HASH : FALSE_HASH);
    return this;
  }

  @Override
  public BigIntegerHashWriter writeString(String name, String value)
    throws IOException {
    hash = hash.multiply(HASH).add(hash(name));
    if (value != null) {
      hash = hash.multiply(HASH).add(hash(value));
    }
    else {
      writeNull();
    }
    return this;
  }

  @Override
  public BigIntegerHashWriter writeDate(String name, Date value)
    throws IOException {
    hash = hash.multiply(HASH).add(hash(name));
    if (value != null) {
      hash = hash.multiply(HASH).add(hash(value.toString()));
    }
    else {
      writeNull();
    }
    return this;
  }

  @Override
  public BigIntegerHashWriter writeNull() throws IOException {
    hash = hash.multiply(HASH).add(NULL_HASH);
    return this;
  }

  protected BigInteger hash(String value) {
    BigInteger h = new BigInteger("1");
    h = h.multiply(HASH).add(new BigInteger(Integer.toString(value.length())));
    for (int i = 0; i < value.length(); i++) {
      h =
        h.multiply(HASH).add(new BigInteger(Integer.toString(value.charAt(i))));
    }
    return h;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigIntegerHashWriter flush() throws IOException {
    write(hash.toString(36));
    out.flush();
    return this;
  }

}
