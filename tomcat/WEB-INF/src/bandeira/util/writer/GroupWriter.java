package bandeira.util.writer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * 
 * @author Tecgraf
 */
public abstract class GroupWriter {

  /** Saída */
  protected final OutputStream out;

  /**
   * @param out
   * @throws IOException
   */
  public GroupWriter(OutputStream out) throws IOException {
    this.out = out;
    out.write('{');
  }

  public GroupWriter writeWriter(String name, AbstractWriter writer) {
    return this;
  }

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
  public abstract GroupWriter flush() throws IOException;

}
