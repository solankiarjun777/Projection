/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Stack;
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
/*     */ public class StdXMLBuilder
/*     */ {
/*     */   private Stack<XMLElement> stack;
/*     */   private XMLElement root;
/*     */   private XMLElement parent;
/*     */   
/*     */   public StdXMLBuilder()
/*     */   {
/*  61 */     this(new XMLElement());
/*  62 */     this.stack = null;
/*  63 */     this.root = null;
/*     */   }
/*     */   
/*     */   public StdXMLBuilder(XMLElement parent)
/*     */   {
/*  68 */     this.parent = parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  79 */     this.root = null;
/*  80 */     this.stack.clear();
/*  81 */     this.stack = null;
/*  82 */     super.finalize();
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
/*     */   public void startBuilding(String systemID, int lineNr)
/*     */   {
/*  95 */     this.stack = new Stack();
/*  96 */     this.root = null;
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
/*     */   public void newProcessingInstruction(String target, Reader reader) {}
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
/*     */   public void startElement(String name, String nsPrefix, String nsURI, String systemID, int lineNr)
/*     */   {
/* 134 */     String fullName = name;
/*     */     
/* 136 */     if (nsPrefix != null) {
/* 137 */       fullName = nsPrefix + ':' + name;
/*     */     }
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
/* 153 */     if (this.stack.empty())
/*     */     {
/* 155 */       this.parent.init(fullName, nsURI, systemID, lineNr);
/* 156 */       this.stack.push(this.parent);
/* 157 */       this.root = this.parent;
/*     */     } else {
/* 159 */       XMLElement top = (XMLElement)this.stack.peek();
/*     */       
/* 161 */       XMLElement elt = new XMLElement(fullName, nsURI, systemID, lineNr);
/* 162 */       top.addChild(elt);
/* 163 */       this.stack.push(elt);
/*     */     }
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
/*     */ 
/*     */   public void elementAttributesProcessed(String name, String nsPrefix, String nsURI) {}
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
/*     */   public void endElement(String name, String nsPrefix, String nsURI)
/*     */   {
/* 206 */     XMLElement elt = (XMLElement)this.stack.pop();
/*     */     
/* 208 */     if (elt.getChildCount() == 1) {
/* 209 */       XMLElement child = elt.getChild(0);
/*     */       
/* 211 */       if (child.getLocalName() == null) {
/* 212 */         elt.setContent(child.getContent());
/* 213 */         elt.removeChild(0);
/*     */       }
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addAttribute(String key, String nsPrefix, String nsURI, String value, String type)
/*     */     throws Exception
/*     */   {
/* 243 */     String fullName = key;
/*     */     
/* 245 */     if (nsPrefix != null) {
/* 246 */       fullName = nsPrefix + ':' + key;
/*     */     }
/*     */     
/* 249 */     XMLElement top = (XMLElement)this.stack.peek();
/*     */     
/* 251 */     if (top.hasAttribute(fullName)) {
/* 252 */       throw new XMLParseException(top.getSystemID(), 
/* 253 */         top.getLine(), 
/* 254 */         "Duplicate attribute: " + key);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 260 */     top.setString(fullName, value);
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
/*     */   public void addPCData(Reader reader, String systemID, int lineNr)
/*     */   {
/* 281 */     int bufSize = 2048;
/* 282 */     int sizeRead = 0;
/* 283 */     StringBuffer str = new StringBuffer(bufSize);
/* 284 */     char[] buf = new char[bufSize];
/*     */     for (;;)
/*     */     {
/* 287 */       if (sizeRead >= bufSize) {
/* 288 */         bufSize *= 2;
/* 289 */         str.ensureCapacity(bufSize);
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 295 */         size = reader.read(buf);
/*     */       } catch (IOException e) { int size;
/* 297 */         break;
/*     */       }
/*     */       int size;
/* 300 */       if (size < 0) {
/*     */         break;
/*     */       }
/*     */       
/* 304 */       str.append(buf, 0, size);
/* 305 */       sizeRead += size;
/*     */     }
/*     */     
/*     */ 
/* 309 */     XMLElement elt = new XMLElement(null, null, systemID, lineNr);
/* 310 */     elt.setContent(str.toString());
/*     */     
/* 312 */     if (!this.stack.empty()) {
/* 313 */       XMLElement top = (XMLElement)this.stack.peek();
/* 314 */       top.addChild(elt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getResult()
/*     */   {
/* 327 */     return this.root;
/*     */   }
/*     */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\xml\StdXMLBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */