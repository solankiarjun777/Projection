/*      */ package processing.xml;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Vector;
/*      */ import processing.core.PApplet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLElement
/*      */   implements Serializable
/*      */ {
/*      */   public static final int NO_LINE = -1;
/*      */   private PApplet sketch;
/*      */   private XMLElement parent;
/*      */   private Vector<XMLAttribute> attributes;
/*      */   private Vector<XMLElement> children;
/*      */   private String name;
/*      */   private String fullName;
/*      */   private String namespace;
/*      */   private String content;
/*      */   private String systemID;
/*      */   private int line;
/*      */   
/*      */   public XMLElement()
/*      */   {
/*   91 */     this(null, null, null, -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement(String fullName)
/*      */   {
/*  101 */     this(fullName, null, null, -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement(String fullName, String namespace, String systemID, int lineNr)
/*      */   {
/*  144 */     this.attributes = new Vector();
/*  145 */     this.children = new Vector(8);
/*  146 */     this.fullName = fullName;
/*  147 */     if (namespace == null) {
/*  148 */       this.name = fullName;
/*      */     } else {
/*  150 */       int index = fullName.indexOf(':');
/*  151 */       if (index >= 0) {
/*  152 */         this.name = fullName.substring(index + 1);
/*      */       } else {
/*  154 */         this.name = fullName;
/*      */       }
/*      */     }
/*  157 */     this.namespace = namespace;
/*  158 */     this.content = null;
/*  159 */     this.line = lineNr;
/*  160 */     this.systemID = systemID;
/*  161 */     this.parent = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement(PApplet sketch, String filename)
/*      */   {
/*  174 */     this();
/*  175 */     this.sketch = sketch;
/*  176 */     init(sketch.createReader(filename));
/*      */   }
/*      */   
/*      */   public XMLElement(Reader reader)
/*      */   {
/*  181 */     this();
/*  182 */     init(reader);
/*      */   }
/*      */   
/*      */   public static XMLElement parse(String xml)
/*      */   {
/*  187 */     return parse(new StringReader(xml));
/*      */   }
/*      */   
/*      */   public static XMLElement parse(Reader r)
/*      */   {
/*      */     try {
/*  193 */       StdXMLParser parser = new StdXMLParser();
/*  194 */       parser.setBuilder(new StdXMLBuilder());
/*  195 */       parser.setValidator(new XMLValidator());
/*  196 */       parser.setReader(new StdXMLReader(r));
/*  197 */       return (XMLElement)parser.parse();
/*      */     } catch (XMLException e) {
/*  199 */       e.printStackTrace(); }
/*  200 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void init(String fullName, String namespace, String systemID, int lineNr)
/*      */   {
/*  209 */     this.fullName = fullName;
/*  210 */     if (namespace == null) {
/*  211 */       this.name = fullName;
/*      */     } else {
/*  213 */       int index = fullName.indexOf(':');
/*  214 */       if (index >= 0) {
/*  215 */         this.name = fullName.substring(index + 1);
/*      */       } else {
/*  217 */         this.name = fullName;
/*      */       }
/*      */     }
/*  220 */     this.namespace = namespace;
/*  221 */     this.line = lineNr;
/*  222 */     this.systemID = systemID;
/*      */   }
/*      */   
/*      */   protected void init(Reader r)
/*      */   {
/*      */     try {
/*  228 */       StdXMLParser parser = new StdXMLParser();
/*  229 */       parser.setBuilder(new StdXMLBuilder(this));
/*  230 */       parser.setValidator(new XMLValidator());
/*  231 */       parser.setReader(new StdXMLReader(r));
/*  232 */       parser.parse();
/*      */     } catch (XMLException e) {
/*  234 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/*  305 */     this.attributes.clear();
/*  306 */     this.attributes = null;
/*  307 */     this.children = null;
/*  308 */     this.fullName = null;
/*  309 */     this.name = null;
/*  310 */     this.namespace = null;
/*  311 */     this.content = null;
/*  312 */     this.systemID = null;
/*  313 */     this.parent = null;
/*  314 */     super.finalize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement getParent()
/*      */   {
/*  323 */     return this.parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  336 */     return this.fullName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getLocalName()
/*      */   {
/*  346 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getNamespace()
/*      */   {
/*  357 */     return this.namespace;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setName(String name)
/*      */   {
/*  368 */     this.name = name;
/*  369 */     this.fullName = name;
/*  370 */     this.namespace = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setName(String fullName, String namespace)
/*      */   {
/*  381 */     int index = fullName.indexOf(':');
/*  382 */     if ((namespace == null) || (index < 0)) {
/*  383 */       this.name = fullName;
/*      */     } else {
/*  385 */       this.name = fullName.substring(index + 1);
/*      */     }
/*  387 */     this.fullName = fullName;
/*  388 */     this.namespace = namespace;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addChild(XMLElement child)
/*      */   {
/*  398 */     if (child == null) {
/*  399 */       throw new IllegalArgumentException("child must not be null");
/*      */     }
/*  401 */     if ((child.getLocalName() == null) && (!this.children.isEmpty())) {
/*  402 */       XMLElement lastChild = (XMLElement)this.children.lastElement();
/*      */       
/*  404 */       if (lastChild.getLocalName() == null) {
/*  405 */         lastChild.setContent(lastChild.getContent() + 
/*  406 */           child.getContent());
/*  407 */         return;
/*      */       }
/*      */     }
/*  410 */     child.parent = this;
/*  411 */     this.children.addElement(child);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void insertChild(XMLElement child, int index)
/*      */   {
/*  422 */     if (child == null) {
/*  423 */       throw new IllegalArgumentException("child must not be null");
/*      */     }
/*  425 */     if ((child.getLocalName() == null) && (!this.children.isEmpty())) {
/*  426 */       XMLElement lastChild = (XMLElement)this.children.lastElement();
/*  427 */       if (lastChild.getLocalName() == null) {
/*  428 */         lastChild.setContent(lastChild.getContent() + 
/*  429 */           child.getContent());
/*  430 */         return;
/*      */       }
/*      */     }
/*  433 */     child.parent = this;
/*  434 */     this.children.insertElementAt(child, index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeChild(XMLElement child)
/*      */   {
/*  444 */     if (child == null) {
/*  445 */       throw new IllegalArgumentException("child must not be null");
/*      */     }
/*  447 */     this.children.removeElement(child);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeChild(int index)
/*      */   {
/*  457 */     this.children.removeElementAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLeaf()
/*      */   {
/*  477 */     return this.children.isEmpty();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasChildren()
/*      */   {
/*  487 */     return !this.children.isEmpty();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getChildCount()
/*      */   {
/*  500 */     return this.children.size();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] listChildren()
/*      */   {
/*  519 */     int childCount = getChildCount();
/*  520 */     String[] outgoing = new String[childCount];
/*  521 */     for (int i = 0; i < childCount; i++) {
/*  522 */       outgoing[i] = getChild(i).getName();
/*      */     }
/*  524 */     return outgoing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement[] getChildren()
/*      */   {
/*  532 */     int childCount = getChildCount();
/*  533 */     XMLElement[] kids = new XMLElement[childCount];
/*  534 */     this.children.copyInto(kids);
/*  535 */     return kids;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement getChild(int index)
/*      */   {
/*  545 */     return (XMLElement)this.children.elementAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement getChild(String path)
/*      */   {
/*  563 */     if (path.indexOf('/') != -1) {
/*  564 */       return getChildRecursive(PApplet.split(path, '/'), 0);
/*      */     }
/*  566 */     int childCount = getChildCount();
/*  567 */     for (int i = 0; i < childCount; i++) {
/*  568 */       XMLElement kid = getChild(i);
/*  569 */       String kidName = kid.getName();
/*  570 */       if ((kidName != null) && (kidName.equals(path))) {
/*  571 */         return kid;
/*      */       }
/*      */     }
/*  574 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected XMLElement getChildRecursive(String[] items, int offset)
/*      */   {
/*  587 */     if (Character.isDigit(items[offset].charAt(0))) {
/*  588 */       XMLElement kid = getChild(Integer.parseInt(items[offset]));
/*  589 */       if (offset == items.length - 1) {
/*  590 */         return kid;
/*      */       }
/*  592 */       return kid.getChildRecursive(items, offset + 1);
/*      */     }
/*      */     
/*  595 */     int childCount = getChildCount();
/*  596 */     for (int i = 0; i < childCount; i++) {
/*  597 */       XMLElement kid = getChild(i);
/*  598 */       String kidName = kid.getName();
/*  599 */       if ((kidName != null) && (kidName.equals(items[offset]))) {
/*  600 */         if (offset == items.length - 1) {
/*  601 */           return kid;
/*      */         }
/*  603 */         return kid.getChildRecursive(items, offset + 1);
/*      */       }
/*      */     }
/*      */     
/*  607 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement[] getChildren(String path)
/*      */   {
/*  641 */     if (path.indexOf('/') != -1) {
/*  642 */       return getChildrenRecursive(PApplet.split(path, '/'), 0);
/*      */     }
/*      */     
/*      */ 
/*  646 */     if (Character.isDigit(path.charAt(0))) {
/*  647 */       return new XMLElement[] { getChild(Integer.parseInt(path)) };
/*      */     }
/*  649 */     int childCount = getChildCount();
/*  650 */     XMLElement[] matches = new XMLElement[childCount];
/*  651 */     int matchCount = 0;
/*  652 */     for (int i = 0; i < childCount; i++) {
/*  653 */       XMLElement kid = getChild(i);
/*  654 */       String kidName = kid.getName();
/*  655 */       if ((kidName != null) && (kidName.equals(path))) {
/*  656 */         matches[(matchCount++)] = kid;
/*      */       }
/*      */     }
/*  659 */     return (XMLElement[])PApplet.subset(matches, 0, matchCount);
/*      */   }
/*      */   
/*      */   protected XMLElement[] getChildrenRecursive(String[] items, int offset)
/*      */   {
/*  664 */     if (offset == items.length - 1) {
/*  665 */       return getChildren(items[offset]);
/*      */     }
/*  667 */     XMLElement[] matches = getChildren(items[offset]);
/*  668 */     XMLElement[] outgoing = new XMLElement[0];
/*  669 */     for (int i = 0; i < matches.length; i++) {
/*  670 */       XMLElement[] kidMatches = matches[i].getChildrenRecursive(items, offset + 1);
/*  671 */       outgoing = (XMLElement[])PApplet.concat(outgoing, kidMatches);
/*      */     }
/*  673 */     return outgoing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private XMLAttribute findAttribute(String fullName)
/*      */   {
/*  685 */     Enumeration<XMLAttribute> en = this.attributes.elements();
/*  686 */     while (en.hasMoreElements()) {
/*  687 */       XMLAttribute attr = (XMLAttribute)en.nextElement();
/*  688 */       if (attr.getName().equals(fullName)) {
/*  689 */         return attr;
/*      */       }
/*      */     }
/*  692 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getAttributeCount()
/*      */   {
/*  728 */     return this.attributes.size();
/*      */   }
/*      */   
/*      */   public String[] listAttributes()
/*      */   {
/*  733 */     String[] outgoing = new String[this.attributes.size()];
/*  734 */     for (int i = 0; i < this.attributes.size(); i++) {
/*  735 */       outgoing[i] = ((XMLAttribute)this.attributes.get(i)).getName();
/*      */     }
/*  737 */     return outgoing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public String getStringAttribute(String name)
/*      */   {
/*  795 */     return getString(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public String getStringAttribute(String name, String defaultValue)
/*      */   {
/*  812 */     return getString(name, defaultValue);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getString(String name)
/*      */   {
/*  827 */     return getString(name, null);
/*      */   }
/*      */   
/*      */   public String getString(String name, String defaultValue)
/*      */   {
/*  832 */     XMLAttribute attr = findAttribute(name);
/*  833 */     if (attr == null) {
/*  834 */       return defaultValue;
/*      */     }
/*  836 */     return attr.getValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getBoolean(String name)
/*      */   {
/*  845 */     return getBoolean(name, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getBoolean(String name, boolean defaultValue)
/*      */   {
/*  862 */     String value = getString(name);
/*  863 */     if (value == null) {
/*  864 */       return defaultValue;
/*      */     }
/*  866 */     return (value.equals("1")) || (value.toLowerCase().equals("true"));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public int getIntAttribute(String name)
/*      */   {
/*  892 */     return getInt(name, 0);
/*      */   }
/*      */   
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public int getIntAttribute(String name, int defaultValue)
/*      */   {
/*  900 */     return getInt(name, defaultValue);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getInt(String name)
/*      */   {
/*  908 */     return getInt(name, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getInt(String name, int defaultValue)
/*      */   {
/*  925 */     String value = getString(name);
/*  926 */     return value == null ? defaultValue : PApplet.parseInt(value, defaultValue);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public float getFloatAttribute(String name)
/*      */   {
/*  953 */     return getFloat(name, 0.0F);
/*      */   }
/*      */   
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public float getFloatAttribute(String name, float defaultValue)
/*      */   {
/*  961 */     return getFloat(name, 0.0F);
/*      */   }
/*      */   
/*      */   public float getFloat(String name)
/*      */   {
/*  966 */     return getFloat(name, 0.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getFloat(String name, float defaultValue)
/*      */   {
/*  984 */     String value = getString(name);
/*  985 */     if (value == null) {
/*  986 */       return defaultValue;
/*      */     }
/*  988 */     return PApplet.parseFloat(value, defaultValue);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDouble(String name)
/*      */   {
/* 1015 */     return getDouble(name, 0.0D);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDouble(String name, double defaultValue)
/*      */   {
/* 1028 */     String value = getString(name);
/* 1029 */     return value == null ? defaultValue : Double.parseDouble(value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setString(String name, String value)
/*      */   {
/* 1119 */     XMLAttribute attr = findAttribute(name);
/* 1120 */     if (attr == null) {
/* 1121 */       attr = new XMLAttribute(name, name, null, value, "CDATA");
/* 1122 */       this.attributes.addElement(attr);
/*      */     } else {
/* 1124 */       attr.setValue(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setBoolean(String name, boolean value)
/*      */   {
/* 1130 */     setString(name, String.valueOf(value));
/*      */   }
/*      */   
/*      */   public void setInt(String name, int value)
/*      */   {
/* 1135 */     setString(name, String.valueOf(value));
/*      */   }
/*      */   
/*      */   public void setFloat(String name, float value)
/*      */   {
/* 1140 */     setString(name, String.valueOf(value));
/*      */   }
/*      */   
/*      */   public void setDouble(String name, double value)
/*      */   {
/* 1145 */     setString(name, String.valueOf(value));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void remove(String name)
/*      */   {
/* 1177 */     for (int i = 0; i < this.attributes.size(); i++) {
/* 1178 */       XMLAttribute attr = (XMLAttribute)this.attributes.elementAt(i);
/* 1179 */       if (attr.getName().equals(name)) {
/* 1180 */         this.attributes.removeElementAt(i);
/* 1181 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasAttribute(String name)
/*      */   {
/* 1234 */     return findAttribute(name) != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSystemID()
/*      */   {
/* 1299 */     return this.systemID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getLine()
/*      */   {
/* 1312 */     return this.line;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getContent()
/*      */   {
/* 1329 */     return this.content;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setContent(String content)
/*      */   {
/* 1340 */     this.content = content;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object object)
/*      */   {
/* 1350 */     if (!(object instanceof XMLElement)) {
/* 1351 */       return false;
/*      */     }
/* 1353 */     XMLElement rawElement = (XMLElement)object;
/*      */     
/* 1355 */     if (!this.name.equals(rawElement.getLocalName())) {
/* 1356 */       return false;
/*      */     }
/* 1358 */     if (this.attributes.size() != rawElement.getAttributeCount()) {
/* 1359 */       return false;
/*      */     }
/* 1361 */     Enumeration<XMLAttribute> en = this.attributes.elements();
/* 1362 */     while (en.hasMoreElements()) {
/* 1363 */       XMLAttribute attr = (XMLAttribute)en.nextElement();
/*      */       
/* 1365 */       if (!rawElement.hasAttribute(attr.getName())) {
/* 1366 */         return false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1371 */       String value = rawElement.getString(attr.getName(), null);
/* 1372 */       if (!attr.getValue().equals(value)) {
/* 1373 */         return false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1381 */     if (this.children.size() != rawElement.getChildCount()) {
/* 1382 */       return false;
/*      */     }
/* 1384 */     for (int i = 0; i < this.children.size(); i++) {
/* 1385 */       XMLElement child1 = getChild(i);
/* 1386 */       XMLElement child2 = rawElement.getChild(i);
/*      */       
/* 1388 */       if (!child1.equals(child2)) {
/* 1389 */         return false;
/*      */       }
/*      */     }
/* 1392 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1460 */     return toString(true);
/*      */   }
/*      */   
/*      */   public String toString(boolean pretty)
/*      */   {
/* 1465 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1466 */     OutputStreamWriter osw = new OutputStreamWriter(baos);
/* 1467 */     XMLWriter writer = new XMLWriter(osw);
/*      */     try {
/* 1469 */       writer.write(this, pretty);
/*      */     } catch (IOException e) {
/* 1471 */       e.printStackTrace();
/*      */     }
/* 1473 */     return baos.toString();
/*      */   }
/*      */   
/*      */   private PApplet findSketch()
/*      */   {
/* 1478 */     if (this.sketch != null) {
/* 1479 */       return this.sketch;
/*      */     }
/* 1481 */     if (this.parent != null) {
/* 1482 */       return this.parent.findSketch();
/*      */     }
/* 1484 */     return null;
/*      */   }
/*      */   
/*      */   public boolean save(String filename)
/*      */   {
/* 1489 */     if (this.sketch == null) {
/* 1490 */       this.sketch = findSketch();
/*      */     }
/* 1492 */     if (this.sketch == null) {
/* 1493 */       System.err.println("save() can only be used on elements loaded by a sketch");
/* 1494 */       throw new RuntimeException("no sketch found, use write(PrintWriter) instead.");
/*      */     }
/* 1496 */     return write(this.sketch.createWriter(filename));
/*      */   }
/*      */   
/*      */   public boolean write(PrintWriter writer)
/*      */   {
/* 1501 */     writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
/* 1502 */     XMLWriter xmlw = new XMLWriter(writer);
/*      */     try {
/* 1504 */       xmlw.write(this, true);
/* 1505 */       writer.flush();
/* 1506 */       return true;
/*      */     }
/*      */     catch (IOException e) {
/* 1509 */       e.printStackTrace(); }
/* 1510 */     return false;
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\xml\XMLElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */