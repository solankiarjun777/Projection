/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Writer;
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
/*     */ public class XMLWriter
/*     */ {
/*     */   static final int INDENT = 2;
/*     */   static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
/*     */   private PrintWriter writer;
/*     */   
/*     */   public XMLWriter(Writer writer)
/*     */   {
/*  61 */     if ((writer instanceof PrintWriter)) {
/*  62 */       this.writer = ((PrintWriter)writer);
/*     */     } else {
/*  64 */       this.writer = new PrintWriter(writer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLWriter(OutputStream stream)
/*     */   {
/*  76 */     this.writer = new PrintWriter(stream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  86 */     this.writer = null;
/*  87 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(XMLElement xml)
/*     */     throws IOException
/*     */   {
/*  99 */     write(xml, false, 0, 2, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(XMLElement xml, boolean prettyPrint)
/*     */     throws IOException
/*     */   {
/* 111 */     write(xml, prettyPrint, 0, 2, true);
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
/*     */   public void write(XMLElement xml, boolean prettyPrint, int initialIndent)
/*     */     throws IOException
/*     */   {
/* 125 */     write(xml, prettyPrint, initialIndent, 2, true);
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
/*     */   public void write(XMLElement xml, boolean prettyPrint, int initialIndent, int eachIndent, boolean collapseEmptyElements)
/*     */     throws IOException
/*     */   {
/* 142 */     if (prettyPrint) {
/* 143 */       for (int i = 0; i < initialIndent; i++) {
/* 144 */         this.writer.print(' ');
/*     */       }
/*     */     }
/*     */     
/* 148 */     if (xml.getLocalName() == null) {
/* 149 */       if (xml.getContent() != null) {
/* 150 */         if (prettyPrint) {
/* 151 */           writeEncoded(xml.getContent().trim());
/* 152 */           this.writer.println();
/*     */         } else {
/* 154 */           writeEncoded(xml.getContent());
/*     */         }
/*     */       }
/*     */     } else {
/* 158 */       this.writer.print('<');
/* 159 */       this.writer.print(xml.getName());
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
/*     */       String[] arrayOfString;
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
/* 203 */       int j = (arrayOfString = xml.listAttributes()).length; for (int i = 0; i < j; i++) { String key = arrayOfString[i];
/* 204 */         String value = xml.getString(key, null);
/* 205 */         this.writer.print(" " + key + "=\"");
/* 206 */         writeEncoded(value);
/* 207 */         this.writer.print('"');
/*     */       }
/*     */       
/* 210 */       if ((xml.getContent() != null) && 
/* 211 */         (xml.getContent().length() > 0)) {
/* 212 */         this.writer.print('>');
/* 213 */         writeEncoded(xml.getContent());
/* 214 */         this.writer.print("</" + xml.getName() + '>');
/*     */         
/* 216 */         if (prettyPrint) {
/* 217 */           this.writer.println();
/*     */         }
/* 219 */       } else if ((xml.hasChildren()) || (!collapseEmptyElements)) {
/* 220 */         this.writer.print('>');
/*     */         
/* 222 */         if (prettyPrint) {
/* 223 */           this.writer.println();
/*     */         }
/*     */         
/* 226 */         int count = xml.getChildCount();
/* 227 */         for (int i = 0; i < count; i++) {
/* 228 */           XMLElement child = xml.getChild(i);
/* 229 */           write(child, prettyPrint, 
/* 230 */             initialIndent + eachIndent, eachIndent, 
/* 231 */             collapseEmptyElements);
/*     */         }
/*     */         
/* 234 */         if (prettyPrint) {
/* 235 */           for (int i = 0; i < initialIndent; i++) {
/* 236 */             this.writer.print(' ');
/*     */           }
/*     */         }
/*     */         
/* 240 */         this.writer.print("</" + xml.getName() + ">");
/*     */         
/* 242 */         if (prettyPrint) {
/* 243 */           this.writer.println();
/*     */         }
/*     */       } else {
/* 246 */         this.writer.print("/>");
/*     */         
/* 248 */         if (prettyPrint) {
/* 249 */           this.writer.println();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 254 */     this.writer.flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeEncoded(String str)
/*     */   {
/* 265 */     for (int i = 0; i < str.length(); i++) {
/* 266 */       char c = str.charAt(i);
/*     */       
/* 268 */       switch (c) {
/*     */       case '\n': 
/* 270 */         this.writer.print(c);
/* 271 */         break;
/*     */       
/*     */       case '<': 
/* 274 */         this.writer.print("&lt;");
/* 275 */         break;
/*     */       
/*     */       case '>': 
/* 278 */         this.writer.print("&gt;");
/* 279 */         break;
/*     */       
/*     */       case '&': 
/* 282 */         this.writer.print("&amp;");
/* 283 */         break;
/*     */       
/*     */       case '\'': 
/* 286 */         this.writer.print("&apos;");
/* 287 */         break;
/*     */       
/*     */       case '"': 
/* 290 */         this.writer.print("&quot;");
/* 291 */         break;
/*     */       
/*     */       default: 
/* 294 */         if ((c < ' ') || (c > '~')) {
/* 295 */           this.writer.print("&#x");
/* 296 */           this.writer.print(Integer.toString(c, 16));
/* 297 */           this.writer.print(';');
/*     */         } else {
/* 299 */           this.writer.print(c);
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\xml\XMLWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */