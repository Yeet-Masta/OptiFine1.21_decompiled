package net.minecraft.src;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletionException;
import javax.annotation.Nullable;
import net.optifine.CrashReporter;
import net.optifine.reflect.Reflector;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class C_4883_ {
   private static final Logger f_127499_ = LogUtils.getLogger();
   private static final DateTimeFormatter f_241641_ = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
   private final String f_127500_;
   private final Throwable f_127501_;
   private final List<C_4909_> f_127503_ = Lists.newArrayList();
   @Nullable
   private Path f_127504_;
   private boolean f_127505_ = true;
   private StackTraceElement[] f_127506_ = new StackTraceElement[0];
   private final C_140956_ f_178624_ = new C_140956_();
   private boolean reported = false;

   public C_4883_(String descriptionIn, Throwable causeThrowable) {
      this.f_127500_ = descriptionIn;
      this.f_127501_ = causeThrowable;
   }

   public String m_127511_() {
      return this.f_127500_;
   }

   public Throwable m_127524_() {
      return this.f_127501_;
   }

   public String m_178625_() {
      StringBuilder stringbuilder = new StringBuilder();
      this.m_127519_(stringbuilder);
      return stringbuilder.toString();
   }

   public void m_127519_(StringBuilder builder) {
      if ((this.f_127506_ == null || this.f_127506_.length <= 0) && !this.f_127503_.isEmpty()) {
         this.f_127506_ = (StackTraceElement[])ArrayUtils.subarray(((C_4909_)this.f_127503_.get(0)).m_128143_(), 0, 1);
      }

      if (this.f_127506_ != null && this.f_127506_.length > 0) {
         builder.append("-- Head --\n");
         builder.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         if (Reflector.CrashReportExtender_generateEnhancedStackTraceSTE.exists()) {
            builder.append(Reflector.CrashReportAnalyser_appendSuspectedMods.callString(this.f_127501_, this.f_127506_));
            builder.append("Stacktrace:");
            builder.append(Reflector.CrashReportExtender_generateEnhancedStackTraceSTE.callString1(this.f_127506_));
         } else {
            builder.append("Stacktrace:\n");

            for (StackTraceElement stacktraceelement : this.f_127506_) {
               builder.append("\t").append("at ").append(stacktraceelement);
               builder.append("\n");
            }

            builder.append("\n");
         }
      }

      for (C_4909_ crashreportcategory : this.f_127503_) {
         crashreportcategory.m_128168_(builder);
         builder.append("\n\n");
      }

      Reflector.CrashReportExtender_extendSystemReport.call(this.f_178624_);
      this.f_178624_.m_143525_(builder);
   }

   public String m_127525_() {
      StringWriter stringwriter = null;
      PrintWriter printwriter = null;
      Throwable throwable = this.f_127501_;
      if (throwable.getMessage() == null) {
         if (throwable instanceof NullPointerException) {
            throwable = new NullPointerException(this.f_127500_);
         } else if (throwable instanceof StackOverflowError) {
            throwable = new StackOverflowError(this.f_127500_);
         } else if (throwable instanceof OutOfMemoryError) {
            throwable = new OutOfMemoryError(this.f_127500_);
         }

         throwable.setStackTrace(this.f_127501_.getStackTrace());
      }

      try {
         if (Reflector.CrashReportExtender_generateEnhancedStackTraceT.exists()) {
            return Reflector.CrashReportExtender_generateEnhancedStackTraceT.callString(throwable);
         }
      } catch (Throwable var9) {
         var9.printStackTrace();
      }

      String s;
      try {
         stringwriter = new StringWriter();
         printwriter = new PrintWriter(stringwriter);
         throwable.printStackTrace(printwriter);
         s = stringwriter.toString();
      } finally {
         IOUtils.closeQuietly(stringwriter);
         IOUtils.closeQuietly(printwriter);
      }

      return s;
   }

   public String m_339571_(C_336586_ typeIn, List<String> headerIn) {
      if (!this.reported) {
         this.reported = true;
         CrashReporter.onCrashReport(this, this.f_178624_);
      }

      StringBuilder stringbuilder = new StringBuilder();
      typeIn.m_338999_(stringbuilder, headerIn);
      stringbuilder.append("Time: ");
      stringbuilder.append(f_241641_.format(ZonedDateTime.now()));
      stringbuilder.append("\n");
      stringbuilder.append("Description: ");
      stringbuilder.append(this.f_127500_);
      stringbuilder.append("\n\n");
      stringbuilder.append(this.m_127525_());
      stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for (int i = 0; i < 87; i++) {
         stringbuilder.append("-");
      }

      stringbuilder.append("\n\n");
      this.m_127519_(stringbuilder);
      return stringbuilder.toString();
   }

   public String m_127526_(C_336586_ typeIn) {
      return this.m_339571_(typeIn, List.of());
   }

   @Nullable
   public Path m_127527_() {
      return this.f_127504_;
   }

   public boolean m_127512_(Path pathIn, C_336586_ typeIn, List<String> headerIn) {
      if (this.f_127504_ != null) {
         return false;
      } else {
         try {
            if (pathIn.getParent() != null) {
               C_5144_.m_257659_(pathIn.getParent());
            }

            Writer writer = Files.newBufferedWriter(pathIn, StandardCharsets.UTF_8);

            try {
               writer.write(this.m_339571_(typeIn, headerIn));
            } catch (Throwable var8) {
               if (writer != null) {
                  try {
                     writer.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (writer != null) {
               writer.close();
            }

            this.f_127504_ = pathIn;
            return true;
         } catch (Throwable var9) {
            f_127499_.error("Could not save crash report to {}", pathIn, var9);
            return false;
         }
      }
   }

   public boolean m_339810_(Path pathIn, C_336586_ typeIn) {
      return this.m_127512_(pathIn, typeIn, List.of());
   }

   public C_140956_ m_178626_() {
      return this.f_178624_;
   }

   public C_4909_ m_127514_(String name) {
      return this.m_127516_(name, 1);
   }

   public C_4909_ m_127516_(String categoryName, int stacktraceLength) {
      C_4909_ crashreportcategory = new C_4909_(categoryName);

      try {
         if (this.f_127505_) {
            int i = crashreportcategory.m_128148_(stacktraceLength);
            StackTraceElement[] astacktraceelement = this.f_127501_.getStackTrace();
            StackTraceElement stacktraceelement = null;
            StackTraceElement stacktraceelement1 = null;
            int j = astacktraceelement.length - i;
            if (j < 0) {
               f_127499_.error("Negative index in crash report handler ({}/{})", astacktraceelement.length, i);
            }

            if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length) {
               stacktraceelement = astacktraceelement[j];
               if (astacktraceelement.length + 1 - i < astacktraceelement.length) {
                  stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
               }
            }

            this.f_127505_ = crashreportcategory.m_128156_(stacktraceelement, stacktraceelement1);
            if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j && j < astacktraceelement.length) {
               this.f_127506_ = new StackTraceElement[j];
               System.arraycopy(astacktraceelement, 0, this.f_127506_, 0, this.f_127506_.length);
            } else {
               this.f_127505_ = false;
            }
         }
      } catch (Throwable var9) {
         var9.printStackTrace();
      }

      this.f_127503_.add(crashreportcategory);
      return crashreportcategory;
   }

   public static C_4883_ m_127521_(Throwable causeIn, String descriptionIn) {
      while (causeIn instanceof CompletionException && causeIn.getCause() != null) {
         causeIn = causeIn.getCause();
      }

      C_4883_ crashreport;
      if (causeIn instanceof C_5204_ reportedexception) {
         crashreport = reportedexception.m_134761_();
      } else {
         crashreport = new C_4883_(descriptionIn, causeIn);
      }

      return crashreport;
   }

   public static void m_127529_() {
      C_181893_.m_182327_();
      new C_4883_("Don't panic!", new Throwable()).m_127526_(C_336586_.f_337619_);
   }
}
