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
public class BinaryWriter extends AbstractWriter {

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
   */
  public BinaryWriter(OutputStream out) {
    super(out);
  }

  @Override
  public BinaryWriter begin() throws IOException {
    out.write('<');
    //    writeInt(count < 0 ? 0xFFFFFFFF : count);
    return this;
  }

  @Override
  public BinaryWriter next() throws IOException {
    out.write('-');
    return this;
  }

  @Override
  public BinaryWriter end() throws IOException {
    out.write('>');
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractWriter writeColumns(String... name) throws IOException {
    return this;
  }

  @Override
  public BinaryWriter writeInt(String name, int value) throws IOException {
    out.write('I');
    write(name);
    writeInt(value);
    return this;
  }

  @Override
  public BinaryWriter writeLong(String name, long value) throws IOException {
    out.write('L');
    write(name);
    writeLong(value);
    return this;
  }

  @Override
  public BinaryWriter writeFloat(String name, float value) throws IOException {
    out.write('F');
    throw new IllegalStateException("not implemented");
  }

  @Override
  public BinaryWriter writeDouble(String name, double value) throws IOException {
    out.write('D');
    throw new IllegalStateException("not implemented");
  }

  @Override
  public BinaryWriter writeBoolean(String name, boolean value)
    throws IOException {
    out.write('B');
    write(name);
    out.write(value ? 0x1 : 0x0);
    return this;
  }

  @Override
  public BinaryWriter writeString(String name, String value) throws IOException {
    out.write('S');
    write(name);
    if (value != null) {
      out.write(0x1);
      write(value);
    }
    else {
      out.write(0x0);
    }
    return this;
  }

  @Override
  public BinaryWriter writeDate(String name, Date value) throws IOException {
    out.write('C');
    write(name);
    out.write(':');
    if (value != null) {
      out.write(0x1);
      writeLong(value.getTime());
    }
    else {
      out.write(0x0);
    }
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BinaryWriter writeNull() throws IOException {
    out.write('N');
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BinaryWriter flush() throws IOException {
    out.flush();
    return this;
  }

  /**
   * Escreve um conteúdo
   * 
   * @param text
   * @throws IOException
   */
  @Override
  protected void write(String text) throws IOException {
    int size = text.length();
    if (size > 0xFFFF) {
      throw new IllegalArgumentException("text too large");
    }
    writeShort(size);
    super.write(text);
  }

  protected void writeShort(int value) throws IOException {
    out.write((value >> 8) & 0xFF);
    out.write((value >> 0) & 0xFF);
  }

  protected void writeInt(int value) throws IOException {
    out.write((value >> 24) & 0xFF);
    out.write((value >> 16) & 0xFF);
    out.write((value >> 8) & 0xFF);
    out.write((value >> 0) & 0xFF);
  }

  protected void writeLong(long value) throws IOException {
    out.write((int) ((value >> 56) & 0xFF));
    out.write((int) ((value >> 48) & 0xFF));
    out.write((int) ((value >> 40) & 0xFF));
    out.write((int) ((value >> 32) & 0xFF));
    out.write((int) ((value >> 24) & 0xFF));
    out.write((int) ((value >> 16) & 0xFF));
    out.write((int) ((value >> 8) & 0xFF));
    out.write((int) ((value >> 0) & 0xFF));
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

  public boolean equals(double value1, double value2) {
    return (Math.abs(value1 - value2) < 0.0000001F);
  }

  public boolean equals(float value1, float value2) {
    return (Math.abs(value1 - value2) < 0.001F);
  }

}
