/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLException
/*     */   extends Exception
/*     */ {
/*     */   private String msg;
/*     */   private String systemID;
/*     */   private int lineNr;
/*     */   private Exception encapsulatedException;
/*     */   
/*     */   public XMLException(String msg)
/*     */   {
/*  78 */     this(null, -1, null, msg, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLException(Exception e)
/*     */   {
/*  89 */     this(null, -1, e, "Nested Exception", false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLException(String systemID, int lineNr, Exception e)
/*     */   {
/* 106 */     this(systemID, lineNr, e, "Nested Exception", true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLException(String systemID, int lineNr, String msg)
/*     */   {
/* 123 */     this(systemID, lineNr, null, msg, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLException(String systemID, int lineNr, Exception e, String msg, boolean reportParams)
/*     */   {
/* 145 */     super(buildMessage(systemID, lineNr, e, msg, reportParams));
/* 146 */     this.systemID = systemID;
/* 147 */     this.lineNr = lineNr;
/* 148 */     this.encapsulatedException = e;
/* 149 */     this.msg = buildMessage(systemID, lineNr, e, msg, 
/* 150 */       reportParams);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String buildMessage(String systemID, int lineNr, Exception e, String msg, boolean reportParams)
/*     */   {
/* 171 */     String str = msg;
/*     */     
/* 173 */     if (reportParams) {
/* 174 */       if (systemID != null) {
/* 175 */         str = str + ", SystemID='" + systemID + "'";
/*     */       }
/*     */       
/* 178 */       if (lineNr >= 0) {
/* 179 */         str = str + ", Line=" + lineNr;
/*     */       }
/*     */       
/* 182 */       if (e != null) {
/* 183 */         str = str + ", Exception: " + e;
/*     */       }
/*     */     }
/*     */     
/* 187 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 197 */     this.systemID = null;
/* 198 */     this.encapsulatedException = null;
/* 199 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSystemID()
/*     */   {
/* 209 */     return this.systemID;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLineNr()
/*     */   {
/* 219 */     return this.lineNr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Exception getException()
/*     */   {
/* 229 */     return this.encapsulatedException;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printStackTrace(PrintWriter writer)
/*     */   {
/* 240 */     super.printStackTrace(writer);
/*     */     
/* 242 */     if (this.encapsulatedException != null) {
/* 243 */       writer.println("*** Nested Exception:");
/* 244 */       this.encapsulatedException.printStackTrace(writer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printStackTrace(PrintStream stream)
/*     */   {
/* 256 */     super.printStackTrace(stream);
/*     */     
/* 258 */     if (this.encapsulatedException != null) {
/* 259 */       stream.println("*** Nested Exception:");
/* 260 */       this.encapsulatedException.printStackTrace(stream);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printStackTrace()
/*     */   {
/* 270 */     super.printStackTrace();
/*     */     
/* 272 */     if (this.encapsulatedException != null) {
/* 273 */       System.err.println("*** Nested Exception:");
/* 274 */       this.encapsulatedException.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 284 */     return this.msg;
/*     */   }
/*     */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\xml\XMLException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */