package optifine.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JSONParser {
   public static int S_INIT;
   public static int S_IN_FINISHED_VALUE;
   public static int S_IN_OBJECT;
   public static int S_IN_ARRAY;
   public static int S_PASSED_PAIR_KEY;
   public static int S_IN_PAIR_VALUE;
   public static int S_END;
   public static int S_IN_ERROR;
   private LinkedList handlerStatusStack;
   private Yylex lexer = new Yylex(null);
   private Yytoken token = null;
   private int status = 0;

   private int peekStatus(LinkedList statusStack) {
      if (statusStack.size() == 0) {
         return -1;
      } else {
         Integer status = (Integer)statusStack.getFirst();
         return status;
      }
   }

   public void reset() {
      this.token = null;
      this.status = 0;
      this.handlerStatusStack = null;
   }

   public void reset(Reader in) {
      this.lexer.yyreset(in);
      this.reset();
   }

   public int getPosition() {
      return this.lexer.getPosition();
   }

   public Object m_82160_(String s) throws ParseException {
      return this.m_82160_(s, null);
   }

   public Object m_82160_(String s, ContainerFactory containerFactory) throws ParseException {
      StringReader in = new StringReader(s);

      try {
         return this.m_82160_(in, containerFactory);
      } catch (IOException var5) {
         throw new ParseException(-1, 2, var5);
      }
   }

   public Object m_82160_(Reader in) throws IOException, ParseException {
      return this.m_82160_(in, null);
   }

   public Object m_82160_(Reader in, ContainerFactory containerFactory) throws IOException, ParseException {
      this.reset(in);
      LinkedList statusStack = new LinkedList();
      LinkedList valueStack = new LinkedList();

      try {
         do {
            this.nextToken();
            label62:
            switch (this.status) {
               case -1:
                  throw new ParseException(this.getPosition(), 1, this.token);
               case 0:
                  switch (this.token.type) {
                     case 0:
                        this.status = 1;
                        statusStack.addFirst(new Integer(this.status));
                        valueStack.addFirst(this.token.value);
                        break label62;
                     case 1:
                        this.status = 2;
                        statusStack.addFirst(new Integer(this.status));
                        valueStack.addFirst(this.createObjectContainer(containerFactory));
                        break label62;
                     case 2:
                     default:
                        this.status = -1;
                        break label62;
                     case 3:
                        this.status = 3;
                        statusStack.addFirst(new Integer(this.status));
                        valueStack.addFirst(this.createArrayContainer(containerFactory));
                        break label62;
                  }
               case 1:
                  if (this.token.type == -1) {
                     return valueStack.removeFirst();
                  }

                  throw new ParseException(this.getPosition(), 1, this.token);
               case 2:
                  switch (this.token.type) {
                     case 0:
                        if (this.token.value instanceof String) {
                           String key = (String)this.token.value;
                           valueStack.addFirst(key);
                           this.status = 4;
                           statusStack.addFirst(new Integer(this.status));
                        } else {
                           this.status = -1;
                        }
                        break label62;
                     case 1:
                     case 3:
                     case 4:
                     default:
                        this.status = -1;
                        break label62;
                     case 2:
                        if (valueStack.size() > 1) {
                           statusStack.removeFirst();
                           valueStack.removeFirst();
                           this.status = this.peekStatus(statusStack);
                        } else {
                           this.status = 1;
                        }
                     case 5:
                        break label62;
                  }
               case 3:
                  switch (this.token.type) {
                     case 0: {
                        List val = (List)valueStack.getFirst();
                        val.add(this.token.value);
                        break label62;
                     }
                     case 1: {
                        List val = (List)valueStack.getFirst();
                        Map newObject = this.createObjectContainer(containerFactory);
                        val.add(newObject);
                        this.status = 2;
                        statusStack.addFirst(new Integer(this.status));
                        valueStack.addFirst(newObject);
                        break label62;
                     }
                     case 2:
                     default:
                        this.status = -1;
                        break label62;
                     case 3: {
                        List val = (List)valueStack.getFirst();
                        List newArray = this.createArrayContainer(containerFactory);
                        val.add(newArray);
                        this.status = 3;
                        statusStack.addFirst(new Integer(this.status));
                        valueStack.addFirst(newArray);
                        break label62;
                     }
                     case 4:
                        if (valueStack.size() > 1) {
                           statusStack.removeFirst();
                           valueStack.removeFirst();
                           this.status = this.peekStatus(statusStack);
                        } else {
                           this.status = 1;
                        }
                     case 5:
                        break label62;
                  }
               case 4:
                  switch (this.token.type) {
                     case 0: {
                        statusStack.removeFirst();
                        String key = (String)valueStack.removeFirst();
                        Map parent = (Map)valueStack.getFirst();
                        parent.put(key, this.token.value);
                        this.status = this.peekStatus(statusStack);
                        break;
                     }
                     case 1: {
                        statusStack.removeFirst();
                        String key = (String)valueStack.removeFirst();
                        Map parent = (Map)valueStack.getFirst();
                        Map newObject = this.createObjectContainer(containerFactory);
                        parent.put(key, newObject);
                        this.status = 2;
                        statusStack.addFirst(new Integer(this.status));
                        valueStack.addFirst(newObject);
                        break;
                     }
                     case 2:
                     case 4:
                     case 5:
                     default:
                        this.status = -1;
                        break;
                     case 3: {
                        statusStack.removeFirst();
                        String key = (String)valueStack.removeFirst();
                        Map parent = (Map)valueStack.getFirst();
                        List newArray = this.createArrayContainer(containerFactory);
                        parent.put(key, newArray);
                        this.status = 3;
                        statusStack.addFirst(new Integer(this.status));
                        valueStack.addFirst(newArray);
                     }
                     case 6:
                  }
            }

            if (this.status == -1) {
               throw new ParseException(this.getPosition(), 1, this.token);
            }
         } while (this.token.type != -1);
      } catch (IOException var9) {
         throw var9;
      }

      throw new ParseException(this.getPosition(), 1, this.token);
   }

   private void nextToken() throws ParseException, IOException {
      this.token = this.lexer.yylex();
      if (this.token == null) {
         this.token = new Yytoken(-1, null);
      }
   }

   private Map createObjectContainer(ContainerFactory containerFactory) {
      if (containerFactory == null) {
         return new JSONObject();
      } else {
         Map m = containerFactory.createObjectContainer();
         return (Map)(m == null ? new JSONObject() : m);
      }
   }

   private List createArrayContainer(ContainerFactory containerFactory) {
      if (containerFactory == null) {
         return new JSONArray();
      } else {
         List l = containerFactory.creatArrayContainer();
         return (List)(l == null ? new JSONArray() : l);
      }
   }

   public void m_82160_(String s, ContentHandler contentHandler) throws ParseException {
      this.m_82160_(s, contentHandler, false);
   }

   public void m_82160_(String s, ContentHandler contentHandler, boolean isResume) throws ParseException {
      StringReader in = new StringReader(s);

      try {
         this.m_82160_(in, contentHandler, isResume);
      } catch (IOException var6) {
         throw new ParseException(-1, 2, var6);
      }
   }

   public void m_82160_(Reader in, ContentHandler contentHandler) throws IOException, ParseException {
      this.m_82160_(in, contentHandler, false);
   }

   public void m_82160_(Reader in, ContentHandler contentHandler, boolean isResume) throws IOException, ParseException {
      if (!isResume) {
         this.reset(in);
         this.handlerStatusStack = new LinkedList();
      } else if (this.handlerStatusStack == null) {
         isResume = false;
         this.reset(in);
         this.handlerStatusStack = new LinkedList();
      }

      LinkedList statusStack = this.handlerStatusStack;

      try {
         do {
            label171:
            switch (this.status) {
               case -1:
                  throw new ParseException(this.getPosition(), 1, this.token);
               case 0:
                  contentHandler.startJSON();
                  this.nextToken();
                  switch (this.token.type) {
                     case 0:
                        this.status = 1;
                        statusStack.addFirst(new Integer(this.status));
                        if (!contentHandler.primitive(this.token.value)) {
                           return;
                        }
                        break label171;
                     case 1:
                        this.status = 2;
                        statusStack.addFirst(new Integer(this.status));
                        if (!contentHandler.startObject()) {
                           return;
                        }
                        break label171;
                     case 2:
                     default:
                        this.status = -1;
                        break label171;
                     case 3:
                        this.status = 3;
                        statusStack.addFirst(new Integer(this.status));
                        if (!contentHandler.startArray()) {
                           return;
                        }
                        break label171;
                  }
               case 1:
                  this.nextToken();
                  if (this.token.type == -1) {
                     contentHandler.endJSON();
                     this.status = 6;
                     return;
                  }

                  this.status = -1;
                  throw new ParseException(this.getPosition(), 1, this.token);
               case 2:
                  this.nextToken();
                  switch (this.token.type) {
                     case 0:
                        if (this.token.value instanceof String) {
                           String key = (String)this.token.value;
                           this.status = 4;
                           statusStack.addFirst(new Integer(this.status));
                           if (!contentHandler.startObjectEntry(key)) {
                              return;
                           }
                        } else {
                           this.status = -1;
                        }
                        break label171;
                     case 1:
                     case 3:
                     case 4:
                     default:
                        this.status = -1;
                        break label171;
                     case 2:
                        if (statusStack.size() > 1) {
                           statusStack.removeFirst();
                           this.status = this.peekStatus(statusStack);
                        } else {
                           this.status = 1;
                        }

                        if (!contentHandler.endObject()) {
                           return;
                        }
                     case 5:
                        break label171;
                  }
               case 3:
                  this.nextToken();
                  switch (this.token.type) {
                     case 0:
                        if (!contentHandler.primitive(this.token.value)) {
                           return;
                        }
                        break label171;
                     case 1:
                        this.status = 2;
                        statusStack.addFirst(new Integer(this.status));
                        if (!contentHandler.startObject()) {
                           return;
                        }
                        break label171;
                     case 2:
                     default:
                        this.status = -1;
                        break label171;
                     case 3:
                        this.status = 3;
                        statusStack.addFirst(new Integer(this.status));
                        if (!contentHandler.startArray()) {
                           return;
                        }
                        break label171;
                     case 4:
                        if (statusStack.size() > 1) {
                           statusStack.removeFirst();
                           this.status = this.peekStatus(statusStack);
                        } else {
                           this.status = 1;
                        }

                        if (!contentHandler.endArray()) {
                           return;
                        }
                     case 5:
                        break label171;
                  }
               case 4:
                  this.nextToken();
                  switch (this.token.type) {
                     case 0:
                        statusStack.removeFirst();
                        this.status = this.peekStatus(statusStack);
                        if (!contentHandler.primitive(this.token.value)) {
                           return;
                        }

                        if (!contentHandler.endObjectEntry()) {
                           return;
                        }
                        break label171;
                     case 1:
                        statusStack.removeFirst();
                        statusStack.addFirst(new Integer(5));
                        this.status = 2;
                        statusStack.addFirst(new Integer(this.status));
                        if (!contentHandler.startObject()) {
                           return;
                        }
                        break label171;
                     case 2:
                     case 4:
                     case 5:
                     default:
                        this.status = -1;
                        break label171;
                     case 3:
                        statusStack.removeFirst();
                        statusStack.addFirst(new Integer(5));
                        this.status = 3;
                        statusStack.addFirst(new Integer(this.status));
                        if (!contentHandler.startArray()) {
                           return;
                        }
                     case 6:
                        break label171;
                  }
               case 5:
                  statusStack.removeFirst();
                  this.status = this.peekStatus(statusStack);
                  if (!contentHandler.endObjectEntry()) {
                     return;
                  }
                  break;
               case 6:
                  return;
            }

            if (this.status == -1) {
               throw new ParseException(this.getPosition(), 1, this.token);
            }
         } while (this.token.type != -1);
      } catch (IOException var6) {
         this.status = -1;
         throw var6;
      } catch (ParseException var7) {
         this.status = -1;
         throw var7;
      } catch (RuntimeException var8) {
         this.status = -1;
         throw var8;
      } catch (Error var9) {
         this.status = -1;
         throw var9;
      }

      this.status = -1;
      throw new ParseException(this.getPosition(), 1, this.token);
   }

   public static Date parseDate(String input) {
      if (input == null) {
         return null;
      } else {
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
         if (input.endsWith("Z")) {
            input = input.substring(0, input.length() - 1) + "GMT-00:00";
         } else {
            int inset = 6;
            String s0 = input.substring(0, input.length() - inset);
            String s1 = input.substring(input.length() - inset, input.length());
            input = s0 + "GMT" + s1;
         }

         try {
            return df.m_82160_(input);
         } catch (java.text.ParseException var5) {
            System.out.println("Error parsing date: " + input);
            System.out.println(var5.getClass().getName() + ": " + var5.getMessage());
            return null;
         }
      }
   }
}
