import java.util.function.IntConsumer;

public interface BitStorage {
   int a(int var1, int var2);

   void b(int var1, int var2);

   int a(int var1);

   long[] a();

   int b();

   int c();

   void a(IntConsumer var1);

   void a(int[] var1);

   BitStorage d();

   default void finish() {
   }
}
