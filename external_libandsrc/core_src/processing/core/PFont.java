/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PFont
/*      */   implements PConstants
/*      */ {
/*      */   protected int glyphCount;
/*      */   protected Glyph[] glyphs;
/*      */   protected String name;
/*      */   protected String psname;
/*      */   protected int size;
/*      */   protected boolean smooth;
/*      */   protected int ascent;
/*      */   protected int descent;
/*      */   protected int[] ascii;
/*      */   protected boolean lazy;
/*      */   protected Font font;
/*      */   protected boolean stream;
/*      */   protected boolean subsetting;
/*      */   protected boolean fontSearched;
/*      */   protected static Font[] fonts;
/*      */   protected static HashMap<String, Font> fontDifferent;
/*      */   protected BufferedImage lazyImage;
/*      */   protected Graphics2D lazyGraphics;
/*      */   protected FontMetrics lazyMetrics;
/*      */   protected int[] lazySamples;
/*      */   protected HashMap<PGraphics, Object> cacheMap;
/*      */   
/*      */   public PFont(Font font, boolean smooth)
/*      */   {
/*  174 */     this(font, smooth, null);
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
/*      */   public PFont(Font font, boolean smooth, char[] charset)
/*      */   {
/*  188 */     this.font = font;
/*  189 */     this.smooth = smooth;
/*      */     
/*  191 */     this.name = font.getName();
/*  192 */     this.psname = font.getPSName();
/*  193 */     this.size = font.getSize();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  200 */     int initialCount = 10;
/*  201 */     this.glyphs = new Glyph[initialCount];
/*      */     
/*  203 */     this.ascii = new int[''];
/*  204 */     Arrays.fill(this.ascii, -1);
/*      */     
/*  206 */     int mbox3 = this.size * 3;
/*      */     
/*  208 */     this.lazyImage = new BufferedImage(mbox3, mbox3, 1);
/*  209 */     this.lazyGraphics = ((Graphics2D)this.lazyImage.getGraphics());
/*  210 */     this.lazyGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  211 */       smooth ? 
/*  212 */       RenderingHints.VALUE_ANTIALIAS_ON : 
/*  213 */       RenderingHints.VALUE_ANTIALIAS_OFF);
/*      */     
/*  215 */     this.lazyGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
/*  216 */       smooth ? 
/*  217 */       RenderingHints.VALUE_TEXT_ANTIALIAS_ON : 
/*  218 */       RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*      */     
/*  220 */     this.lazyGraphics.setFont(font);
/*  221 */     this.lazyMetrics = this.lazyGraphics.getFontMetrics();
/*  222 */     this.lazySamples = new int[mbox3 * mbox3];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  230 */     if (charset == null) {
/*  231 */       this.lazy = true;
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  237 */       Arrays.sort(charset);
/*      */       
/*  239 */       this.glyphs = new Glyph[charset.length];
/*      */       
/*  241 */       this.glyphCount = 0;
/*  242 */       char[] arrayOfChar; int j = (arrayOfChar = charset).length; for (int i = 0; i < j; i++) { char c = arrayOfChar[i];
/*  243 */         if (font.canDisplay(c)) {
/*  244 */           Glyph glyf = new Glyph(c);
/*  245 */           if (glyf.value < 128) {
/*  246 */             this.ascii[glyf.value] = this.glyphCount;
/*      */           }
/*  248 */           glyf.index = this.glyphCount;
/*  249 */           this.glyphs[(this.glyphCount++)] = glyf;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  254 */       if (this.glyphCount != charset.length) {
/*  255 */         this.glyphs = ((Glyph[])PApplet.subset(this.glyphs, 0, this.glyphCount));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  288 */     if (this.ascent == 0) {
/*  289 */       if (font.canDisplay('d')) {
/*  290 */         new Glyph('d');
/*      */       } else {
/*  292 */         this.ascent = this.lazyMetrics.getAscent();
/*      */       }
/*      */     }
/*  295 */     if (this.descent == 0) {
/*  296 */       if (font.canDisplay('p')) {
/*  297 */         new Glyph('p');
/*      */       } else {
/*  299 */         this.descent = this.lazyMetrics.getDescent();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PFont(Font font, boolean smooth, char[] charset, boolean stream)
/*      */   {
/*  310 */     this(font, smooth, charset);
/*  311 */     this.stream = stream;
/*      */   }
/*      */   
/*      */   public PFont(InputStream input) throws IOException
/*      */   {
/*  316 */     DataInputStream is = new DataInputStream(input);
/*      */     
/*      */ 
/*  319 */     this.glyphCount = is.readInt();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  324 */     int version = is.readInt();
/*      */     
/*      */ 
/*      */ 
/*  328 */     this.size = is.readInt();
/*      */     
/*      */ 
/*      */ 
/*  332 */     is.readInt();
/*      */     
/*  334 */     this.ascent = is.readInt();
/*  335 */     this.descent = is.readInt();
/*      */     
/*      */ 
/*  338 */     this.glyphs = new Glyph[this.glyphCount];
/*      */     
/*  340 */     this.ascii = new int[''];
/*  341 */     Arrays.fill(this.ascii, -1);
/*      */     
/*      */ 
/*  344 */     for (int i = 0; i < this.glyphCount; i++) {
/*  345 */       glyph = new Glyph(is);
/*      */       
/*  347 */       if (glyph.value < 128) {
/*  348 */         this.ascii[glyph.value] = i;
/*      */       }
/*  350 */       glyph.index = i;
/*  351 */       this.glyphs[i] = glyph;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  356 */     if ((this.ascent == 0) && (this.descent == 0)) {
/*  357 */       throw new RuntimeException("Please use \"Create Font\" to re-create this font.");
/*      */     }
/*      */     
/*      */     Glyph[] arrayOfGlyph;
/*  361 */     Glyph localGlyph1 = (arrayOfGlyph = this.glyphs).length; for (Glyph glyph = 0; glyph < localGlyph1; glyph++) { Glyph glyph = arrayOfGlyph[glyph];
/*  362 */       glyph.readBitmap(is);
/*      */     }
/*      */     
/*  365 */     if (version >= 10) {
/*  366 */       this.name = is.readUTF();
/*  367 */       this.psname = is.readUTF();
/*      */     }
/*  369 */     if (version == 11) {
/*  370 */       this.smooth = is.readBoolean();
/*      */     }
/*      */     
/*      */ 
/*  374 */     findFont();
/*      */   }
/*      */   
/*      */   void delete()
/*      */   {
/*  379 */     if (this.cacheMap != null) {
/*  380 */       Set<PGraphics> keySet = this.cacheMap.keySet();
/*  381 */       if (!keySet.isEmpty()) {
/*  382 */         Object[] keys = keySet.toArray();
/*  383 */         for (int i = 0; i < keys.length; i++) {
/*  384 */           Object data = getCache((PGraphics)keys[i]);
/*  385 */           Method del = null;
/*      */           try
/*      */           {
/*  388 */             Class<?> c = data.getClass();
/*  389 */             del = c.getMethod("delete", new Class[0]);
/*      */           }
/*      */           catch (Exception localException) {}
/*  392 */           if (del != null) {
/*      */             try
/*      */             {
/*  395 */               del.invoke(data, new Object[0]);
/*      */             }
/*      */             catch (Exception localException1) {}
/*      */           }
/*      */         }
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
/*      */   public void save(OutputStream output)
/*      */     throws IOException
/*      */   {
/*  413 */     DataOutputStream os = new DataOutputStream(output);
/*      */     
/*  415 */     os.writeInt(this.glyphCount);
/*      */     
/*  417 */     if ((this.name == null) || (this.psname == null)) {
/*  418 */       this.name = "";
/*  419 */       this.psname = "";
/*      */     }
/*      */     
/*  422 */     os.writeInt(11);
/*  423 */     os.writeInt(this.size);
/*  424 */     os.writeInt(0);
/*  425 */     os.writeInt(this.ascent);
/*  426 */     os.writeInt(this.descent);
/*      */     
/*  428 */     for (int i = 0; i < this.glyphCount; i++) {
/*  429 */       this.glyphs[i].writeHeader(os);
/*      */     }
/*      */     
/*  432 */     for (int i = 0; i < this.glyphCount; i++) {
/*  433 */       this.glyphs[i].writeBitmap(os);
/*      */     }
/*      */     
/*      */ 
/*  437 */     os.writeUTF(this.name);
/*  438 */     os.writeUTF(this.psname);
/*  439 */     os.writeBoolean(this.smooth);
/*      */     
/*  441 */     os.flush();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void addGlyph(char c)
/*      */   {
/*  450 */     Glyph glyph = new Glyph(c);
/*      */     
/*  452 */     if (this.glyphCount == this.glyphs.length) {
/*  453 */       this.glyphs = ((Glyph[])PApplet.expand(this.glyphs));
/*      */     }
/*  455 */     if (this.glyphCount == 0) {
/*  456 */       glyph.index = 0;
/*  457 */       this.glyphs[this.glyphCount] = glyph;
/*  458 */       if (glyph.value < 128) {
/*  459 */         this.ascii[glyph.value] = 0;
/*      */       }
/*      */     }
/*  462 */     else if (this.glyphs[(this.glyphCount - 1)].value < glyph.value) {
/*  463 */       this.glyphs[this.glyphCount] = glyph;
/*  464 */       if (glyph.value < 128) {
/*  465 */         this.ascii[glyph.value] = this.glyphCount;
/*      */       }
/*      */     }
/*      */     else {
/*  469 */       for (int i = 0; i < this.glyphCount; i++) {
/*  470 */         if (this.glyphs[i].value > c) {
/*  471 */           for (int j = this.glyphCount; j > i; j--) {
/*  472 */             this.glyphs[j] = this.glyphs[(j - 1)];
/*  473 */             if (this.glyphs[j].value < 128) {
/*  474 */               this.ascii[this.glyphs[j].value] = j;
/*      */             }
/*      */           }
/*  477 */           glyph.index = i;
/*  478 */           this.glyphs[i] = glyph;
/*      */           
/*  480 */           if (c >= '') break; this.ascii[c] = i;
/*  481 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  485 */     this.glyphCount += 1;
/*      */   }
/*      */   
/*      */   public String getName()
/*      */   {
/*  490 */     return this.name;
/*      */   }
/*      */   
/*      */   public String getPostScriptName()
/*      */   {
/*  495 */     return this.psname;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFont(Font font)
/*      */   {
/*  505 */     this.font = font;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Font getFont()
/*      */   {
/*  513 */     if (this.subsetting) {
/*  514 */       return null;
/*      */     }
/*  516 */     return this.font;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getSize()
/*      */   {
/*  524 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isStream()
/*      */   {
/*  529 */     return this.stream;
/*      */   }
/*      */   
/*      */   public void setSubsetting()
/*      */   {
/*  534 */     this.subsetting = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Font findFont()
/*      */   {
/*  543 */     if ((this.font == null) && 
/*  544 */       (!this.fontSearched))
/*      */     {
/*  546 */       this.font = new Font(this.name, 0, this.size);
/*      */       
/*  548 */       if (!this.font.getPSName().equals(this.psname))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  553 */         this.font = new Font(this.psname, 0, this.size);
/*      */       }
/*      */       
/*  556 */       if (!this.font.getPSName().equals(this.psname)) {
/*  557 */         this.font = null;
/*      */       }
/*  559 */       this.fontSearched = true;
/*      */     }
/*      */     
/*  562 */     return this.font;
/*      */   }
/*      */   
/*      */   public Glyph getGlyph(char c)
/*      */   {
/*  567 */     int index = index(c);
/*  568 */     return index == -1 ? null : this.glyphs[index];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int index(char c)
/*      */   {
/*  577 */     if (this.lazy) {
/*  578 */       int index = indexActual(c);
/*  579 */       if (index != -1) {
/*  580 */         return index;
/*      */       }
/*  582 */       if ((this.font != null) && (this.font.canDisplay(c)))
/*      */       {
/*  584 */         addGlyph(c);
/*      */         
/*  586 */         return indexActual(c);
/*      */       }
/*      */       
/*  589 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*  593 */     return indexActual(c);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int indexActual(char c)
/*      */   {
/*  602 */     if (this.glyphCount == 0) { return -1;
/*      */     }
/*      */     
/*  605 */     if (c < '') { return this.ascii[c];
/*      */     }
/*      */     
/*      */ 
/*  609 */     return indexHunt(c, 0, this.glyphCount - 1);
/*      */   }
/*      */   
/*      */   protected int indexHunt(int c, int start, int stop)
/*      */   {
/*  614 */     int pivot = (start + stop) / 2;
/*      */     
/*      */ 
/*  617 */     if (c == this.glyphs[pivot].value) { return pivot;
/*      */     }
/*      */     
/*      */ 
/*  621 */     if (start >= stop) { return -1;
/*      */     }
/*      */     
/*  624 */     if (c < this.glyphs[pivot].value) { return indexHunt(c, start, pivot - 1);
/*      */     }
/*      */     
/*  627 */     return indexHunt(c, pivot + 1, stop);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float kern(char a, char b)
/*      */   {
/*  636 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float ascent()
/*      */   {
/*  645 */     return this.ascent / this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float descent()
/*      */   {
/*  654 */     return this.descent / this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float width(char c)
/*      */   {
/*  662 */     if (c == ' ') { return width('i');
/*      */     }
/*  664 */     int cc = index(c);
/*  665 */     if (cc == -1) { return 0.0F;
/*      */     }
/*  667 */     return this.glyphs[cc].setWidth / this.size;
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
/*      */   public void setCache(PGraphics renderer, Object storage)
/*      */   {
/*  684 */     if (this.cacheMap == null) this.cacheMap = new HashMap();
/*  685 */     this.cacheMap.put(renderer, storage);
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
/*      */   public Object getCache(PGraphics renderer)
/*      */   {
/*  698 */     if (this.cacheMap == null) return null;
/*  699 */     return this.cacheMap.get(renderer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeCache(PGraphics renderer)
/*      */   {
/*  708 */     if (this.cacheMap != null) {
/*  709 */       this.cacheMap.remove(renderer);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getGlyphCount()
/*      */   {
/*  717 */     return this.glyphCount;
/*      */   }
/*      */   
/*      */   public Glyph getGlyph(int i) {
/*  721 */     return this.glyphs[i];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  727 */   static final char[] EXTRA_CHARS = {
/*  728 */     '', '', '', '', '', '', '', '', 
/*  729 */     '', '', '', '', '', '', '', '', 
/*  730 */     '', '', '', '', '', '', '', '', 
/*  731 */     '', '', '', '', '', '', '', '', 
/*  732 */     ' ', '¡', '¢', '£', '¤', '¥', '¦', '§', 
/*  733 */     '¨', '©', 'ª', '«', '¬', '­', '®', '¯', 
/*  734 */     '°', '±', '´', 'µ', '¶', '·', '¸', 'º', 
/*  735 */     '»', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 
/*  736 */     'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 
/*  737 */     'Î', 'Ï', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', 
/*  738 */     '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'ß', 
/*  739 */     'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 
/*  740 */     'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 
/*  741 */     'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 
/*  742 */     'ù', 'ú', 'û', 'ü', 'ý', 'ÿ', 'Ă', 'ă', 
/*  743 */     'Ą', 'ą', 'Ć', 'ć', 'Č', 'č', 'Ď', 'ď', 
/*  744 */     'Đ', 'đ', 'Ę', 'ę', 'Ě', 'ě', 'ı', 'Ĺ', 
/*  745 */     'ĺ', 'Ľ', 'ľ', 'Ł', 'ł', 'Ń', 'ń', 'Ň', 
/*  746 */     'ň', 'Ő', 'ő', 'Œ', 'œ', 'Ŕ', 'ŕ', 'Ř', 
/*  747 */     'ř', 'Ś', 'ś', 'Ş', 'ş', 'Š', 'š', 'Ţ', 
/*  748 */     'ţ', 'Ť', 'ť', 'Ů', 'ů', 'Ű', 'ű', 'Ÿ', 
/*  749 */     'Ź', 'ź', 'Ż', 'ż', 'Ž', 'ž', 'ƒ', 'ˆ', 
/*  750 */     'ˇ', '˘', '˙', '˚', '˛', '˜', '˝', 'Ω', 
/*  751 */     'π', '–', '—', '‘', '’', '‚', '“', '”', 
/*  752 */     '„', '†', '‡', '•', '…', '‰', '‹', '›', 
/*  753 */     '⁄', '€', '™', '∂', '∆', '∏', '∑', '√', 
/*  754 */     '∞', '∫', '≈', '≠', '≤', '≥', '◊', 63743, 
/*  755 */     64257, 64258 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  775 */   public static char[] CHARSET = new char[94 + EXTRA_CHARS.length];
/*  776 */   static { int index = 0;
/*  777 */     for (int i = 33; i <= 126; i++) {
/*  778 */       CHARSET[(index++)] = ((char)i);
/*      */     }
/*  780 */     for (int i = 0; i < EXTRA_CHARS.length; i++) {
/*  781 */       CHARSET[(index++)] = EXTRA_CHARS[i];
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
/*      */   public static String[] list()
/*      */   {
/*  801 */     loadFonts();
/*  802 */     String[] list = new String[fonts.length];
/*  803 */     for (int i = 0; i < list.length; i++) {
/*  804 */       list[i] = fonts[i].getName();
/*      */     }
/*  806 */     return list;
/*      */   }
/*      */   
/*      */   public static void loadFonts()
/*      */   {
/*  811 */     if (fonts == null) {
/*  812 */       GraphicsEnvironment ge = 
/*  813 */         GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  814 */       fonts = ge.getAllFonts();
/*  815 */       if (PApplet.platform == 2) {
/*  816 */         fontDifferent = new HashMap();
/*  817 */         Font[] arrayOfFont; int j = (arrayOfFont = fonts).length; for (int i = 0; i < j; i++) { Font font = arrayOfFont[i];
/*      */           
/*  819 */           fontDifferent.put(font.getName(), font);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Font findFont(String name)
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*  834 */     if (PApplet.platform == 2) {
/*  835 */       Font maybe = (Font)fontDifferent.get(name);
/*  836 */       if (maybe != null) {
/*  837 */         return maybe;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  845 */     return new Font(name, 0, 1);
/*      */   }
/*      */   
/*      */ 
/*      */   public PFont() {}
/*      */   
/*      */ 
/*      */   public class Glyph
/*      */   {
/*      */     public PImage image;
/*      */     
/*      */     public int value;
/*      */     
/*      */     public int height;
/*      */     public int width;
/*      */     public int index;
/*      */     public int setWidth;
/*      */     public int topExtent;
/*      */     public int leftExtent;
/*      */     
/*      */     public Glyph()
/*      */     {
/*  867 */       this.index = -1;
/*      */     }
/*      */     
/*      */     public Glyph(DataInputStream is)
/*      */       throws IOException
/*      */     {
/*  873 */       this.index = -1;
/*  874 */       readHeader(is);
/*      */     }
/*      */     
/*      */     protected void readHeader(DataInputStream is) throws IOException
/*      */     {
/*  879 */       this.value = is.readInt();
/*  880 */       this.height = is.readInt();
/*  881 */       this.width = is.readInt();
/*  882 */       this.setWidth = is.readInt();
/*  883 */       this.topExtent = is.readInt();
/*  884 */       this.leftExtent = is.readInt();
/*      */       
/*      */ 
/*  887 */       is.readInt();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  892 */       if ((this.value == 100) && 
/*  893 */         (PFont.this.ascent == 0)) { PFont.this.ascent = this.topExtent;
/*      */       }
/*  895 */       if ((this.value == 112) && 
/*  896 */         (PFont.this.descent == 0)) PFont.this.descent = (-this.topExtent + this.height);
/*      */     }
/*      */     
/*      */     protected void writeHeader(DataOutputStream os)
/*      */       throws IOException
/*      */     {
/*  902 */       os.writeInt(this.value);
/*  903 */       os.writeInt(this.height);
/*  904 */       os.writeInt(this.width);
/*  905 */       os.writeInt(this.setWidth);
/*  906 */       os.writeInt(this.topExtent);
/*  907 */       os.writeInt(this.leftExtent);
/*  908 */       os.writeInt(0);
/*      */     }
/*      */     
/*      */     protected void readBitmap(DataInputStream is) throws IOException
/*      */     {
/*  913 */       this.image = new PImage(this.width, this.height, 4);
/*  914 */       int bitmapSize = this.width * this.height;
/*      */       
/*  916 */       byte[] temp = new byte[bitmapSize];
/*  917 */       is.readFully(temp);
/*      */       
/*      */ 
/*  920 */       int w = this.width;
/*  921 */       int h = this.height;
/*  922 */       int[] pixels = this.image.pixels;
/*  923 */       for (int y = 0; y < h; y++) {
/*  924 */         for (int x = 0; x < w; x++) {
/*  925 */           pixels[(y * this.width + x)] = (temp[(y * w + x)] & 0xFF);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     protected void writeBitmap(DataOutputStream os)
/*      */       throws IOException
/*      */     {
/*  935 */       int[] pixels = this.image.pixels;
/*  936 */       for (int y = 0; y < this.height; y++) {
/*  937 */         for (int x = 0; x < this.width; x++) {
/*  938 */           os.write(pixels[(y * this.width + x)] & 0xFF);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     protected Glyph(char c)
/*      */     {
/*  945 */       int mbox3 = PFont.this.size * 3;
/*  946 */       PFont.this.lazyGraphics.setColor(Color.white);
/*  947 */       PFont.this.lazyGraphics.fillRect(0, 0, mbox3, mbox3);
/*  948 */       PFont.this.lazyGraphics.setColor(Color.black);
/*  949 */       PFont.this.lazyGraphics.drawString(String.valueOf(c), PFont.this.size, PFont.this.size * 2);
/*      */       
/*  951 */       WritableRaster raster = PFont.this.lazyImage.getRaster();
/*  952 */       raster.getDataElements(0, 0, mbox3, mbox3, PFont.this.lazySamples);
/*      */       
/*  954 */       int minX = 1000;int maxX = 0;
/*  955 */       int minY = 1000;int maxY = 0;
/*  956 */       boolean pixelFound = false;
/*      */       
/*  958 */       for (int y = 0; y < mbox3; y++) {
/*  959 */         for (int x = 0; x < mbox3; x++) {
/*  960 */           int sample = PFont.this.lazySamples[(y * mbox3 + x)] & 0xFF;
/*  961 */           if (sample != 255) {
/*  962 */             if (x < minX) minX = x;
/*  963 */             if (y < minY) minY = y;
/*  964 */             if (x > maxX) maxX = x;
/*  965 */             if (y > maxY) maxY = y;
/*  966 */             pixelFound = true;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  971 */       if (!pixelFound) {
/*  972 */         minX = minY = 0;
/*  973 */         maxX = maxY = 0;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  978 */       this.value = c;
/*  979 */       this.height = (maxY - minY + 1);
/*  980 */       this.width = (maxX - minX + 1);
/*  981 */       this.setWidth = PFont.this.lazyMetrics.charWidth(c);
/*      */       
/*      */ 
/*      */ 
/*  985 */       this.topExtent = (PFont.this.size * 2 - minY);
/*      */       
/*      */ 
/*  988 */       this.leftExtent = (minX - PFont.this.size);
/*      */       
/*  990 */       this.image = new PImage(this.width, this.height, 4);
/*  991 */       int[] pixels = this.image.pixels;
/*  992 */       for (int y = minY; y <= maxY; y++) {
/*  993 */         for (int x = minX; x <= maxX; x++) {
/*  994 */           int val = 255 - (PFont.this.lazySamples[(y * mbox3 + x)] & 0xFF);
/*  995 */           int pindex = (y - minY) * this.width + (x - minX);
/*  996 */           pixels[pindex] = val;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1001 */       if ((this.value == 100) && 
/* 1002 */         (PFont.this.ascent == 0)) { PFont.this.ascent = this.topExtent;
/*      */       }
/* 1004 */       if ((this.value == 112) && 
/* 1005 */         (PFont.this.descent == 0)) PFont.this.descent = (-this.topExtent + this.height);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PFont.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */