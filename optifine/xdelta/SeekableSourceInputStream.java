package optifine.xdelta;

import java.io.IOException;
import java.io.InputStream;

public class SeekableSourceInputStream extends InputStream {
   // $FF: renamed from: ss optifine.xdelta.SeekableSource
   SeekableSource field_71;

   public SeekableSourceInputStream(SeekableSource ss) {
      this.field_71 = ss;
   }

   public int read() throws IOException {
      byte[] b = new byte[1];
      this.field_71.read(b, 0, 1);
      return b[0];
   }

   public int read(byte[] b, int off, int len) throws IOException {
      return this.field_71.read(b, off, len);
   }

   public void close() throws IOException {
      this.field_71.close();
   }
}
