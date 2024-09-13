package optifine.xdelta;

import java.io.IOException;
import java.io.InputStream;

public class SeekableSourceInputStream extends InputStream {
   SeekableSource f_12175_;

   public SeekableSourceInputStream(SeekableSource ss) {
      this.f_12175_ = ss;
   }

   public int read() throws IOException {
      byte[] b = new byte[1];
      this.f_12175_.read(b, 0, 1);
      return b[0];
   }

   public int read(byte[] b, int off, int len) throws IOException {
      return this.f_12175_.read(b, off, len);
   }

   public void close() throws IOException {
      this.f_12175_.close();
   }
}
