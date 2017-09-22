package bandeira.util.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * 
 * 
 * @author Tecgraf
 */
public abstract class AbstractWriter {

  /** Saída */
  protected final OutputStream out;

  /**
   * @param out
   */
  public AbstractWriter(OutputStream out) {
    this.out = out;
  }

  public abstract AbstractWriter begin() throws IOException;

  public abstract AbstractWriter next() throws IOException;

  public abstract AbstractWriter end() throws IOException;

  public abstract AbstractWriter writeInt(String name, int value)
    throws IOException;

  public abstract AbstractWriter writeColumns(String... name)
    throws IOException;

  public abstract AbstractWriter writeLong(String name, long value)
    throws IOException;

  public abstract AbstractWriter writeFloat(String name, float value)
    throws IOException;

  public abstract AbstractWriter writeDouble(String name, double value)
    throws IOException;

  public abstract AbstractWriter writeBoolean(String name, boolean value)
    throws IOException;

  public abstract AbstractWriter writeString(String name, String value)
    throws IOException;

  public abstract AbstractWriter writeDate(String name, Date value)
    throws IOException;

  /**
   * @return this
   * @throws IOException
   */
  public abstract AbstractWriter writeNull() throws IOException;

  /**
   * Escreve um conteúdo
   * 
   * @param text
   * @throws IOException
   */
  protected void write(String text) throws IOException {
    int size = text.length();
    for (int n = 0; n < size; n++) {
      char c = text.charAt(n);
      if (c <= 0x7F) {
        out.write(c);
      }
      else if (c <= 0x7FF) {
        out.write(((c >> 6) & 0x1F) + 0xC0);
        out.write((c & 0x3F) + 0x80);
      }
      else {
        out.write(((c >> 12) & 0xF) + 0xE0);
        out.write(((c >> 6) & 0x3F) + 0x80);
        out.write((c & 0x3F) + 0x80);
      }
    }
  }

  /**
   * @return this
   * @throws IOException
   */
  public abstract AbstractWriter flush() throws IOException;

}
