/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Image;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.PixelGrabber;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.HashMap;
/*      */ import java.util.Set;
/*      */ import javax.imageio.ImageIO;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PImage
/*      */   implements PConstants, Cloneable
/*      */ {
/*      */   public int format;
/*      */   public int[] pixels;
/*      */   public int width;
/*      */   public int height;
/*      */   public PApplet parent;
/*      */   protected HashMap<PGraphics, Object> cacheMap;
/*      */   protected HashMap<PGraphics, Object> paramMap;
/*      */   protected boolean modified;
/*      */   protected int mx1;
/*      */   protected int my1;
/*      */   protected int mx2;
/*      */   protected int my2;
/*      */   private int fracU;
/*      */   private int ifU;
/*      */   private int fracV;
/*      */   private int ifV;
/*      */   private int u1;
/*      */   private int u2;
/*      */   private int v1;
/*      */   private int v2;
/*      */   private int sX;
/*      */   private int sY;
/*      */   private int iw;
/*      */   private int iw1;
/*      */   private int ih1;
/*      */   private int ul;
/*      */   private int ll;
/*      */   private int ur;
/*      */   private int lr;
/*      */   private int cUL;
/*      */   private int cLL;
/*      */   private int cUR;
/*      */   private int cLR;
/*      */   private int srcXOffset;
/*      */   private int srcYOffset;
/*      */   private int r;
/*      */   private int g;
/*      */   private int b;
/*      */   private int a;
/*      */   private int[] srcBuffer;
/*      */   static final int PRECISIONB = 15;
/*      */   static final int PRECISIONF = 32768;
/*      */   static final int PREC_MAXVAL = 32767;
/*      */   static final int PREC_ALPHA_SHIFT = 9;
/*      */   static final int PREC_RED_SHIFT = 1;
/*      */   private int blurRadius;
/*      */   private int blurKernelSize;
/*      */   private int[] blurKernel;
/*      */   private int[][] blurMult;
/*      */   
/*      */   public PImage()
/*      */   {
/*  150 */     this.format = 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PImage(int width, int height)
/*      */   {
/*  161 */     init(width, height, 1);
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
/*      */   public PImage(int width, int height, int format)
/*      */   {
/*  180 */     init(width, height, format);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void init(int width, int height, int format)
/*      */   {
/*  192 */     this.width = width;
/*  193 */     this.height = height;
/*  194 */     this.pixels = new int[width * height];
/*  195 */     this.format = format;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkAlpha()
/*      */   {
/*  204 */     if (this.pixels == null) { return;
/*      */     }
/*  206 */     for (int i = 0; i < this.pixels.length; i++)
/*      */     {
/*      */ 
/*  209 */       if ((this.pixels[i] & 0xFF000000) != -16777216) {
/*  210 */         this.format = 2;
/*  211 */         break;
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
/*      */   public PImage(Image img)
/*      */   {
/*  229 */     this.format = 1;
/*  230 */     if ((img instanceof BufferedImage)) {
/*  231 */       BufferedImage bi = (BufferedImage)img;
/*  232 */       this.width = bi.getWidth();
/*  233 */       this.height = bi.getHeight();
/*  234 */       this.pixels = new int[this.width * this.height];
/*  235 */       WritableRaster raster = bi.getRaster();
/*  236 */       raster.getDataElements(0, 0, this.width, this.height, this.pixels);
/*  237 */       if (bi.getType() == 2) {
/*  238 */         this.format = 2;
/*      */       }
/*      */     }
/*      */     else {
/*  242 */       this.width = img.getWidth(null);
/*  243 */       this.height = img.getHeight(null);
/*  244 */       this.pixels = new int[this.width * this.height];
/*  245 */       PixelGrabber pg = 
/*  246 */         new PixelGrabber(img, 0, 0, this.width, this.height, this.pixels, 0, this.width);
/*      */       try {
/*  248 */         pg.grabPixels();
/*      */       }
/*      */       catch (InterruptedException localInterruptedException) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Image getImage()
/*      */   {
/*  258 */     loadPixels();
/*  259 */     int type = this.format == 1 ? 
/*  260 */       1 : 2;
/*  261 */     BufferedImage image = new BufferedImage(this.width, this.height, type);
/*  262 */     WritableRaster wr = image.getRaster();
/*  263 */     wr.setDataElements(0, 0, this.width, this.height, this.pixels);
/*  264 */     return image;
/*      */   }
/*      */   
/*      */   public void delete()
/*      */   {
/*  269 */     if (this.cacheMap != null) {
/*  270 */       Set<PGraphics> keySet = this.cacheMap.keySet();
/*  271 */       if (!keySet.isEmpty()) {
/*  272 */         Object[] keys = keySet.toArray();
/*  273 */         for (int i = 0; i < keys.length; i++) {
/*  274 */           Object data = getCache((PGraphics)keys[i]);
/*  275 */           Method del = null;
/*      */           try
/*      */           {
/*  278 */             Class<?> c = data.getClass();
/*  279 */             del = c.getMethod("delete", new Class[0]);
/*      */           }
/*      */           catch (Exception localException) {}
/*  282 */           if (del != null) {
/*      */             try
/*      */             {
/*  285 */               del.invoke(data, new Object[0]);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCache(PGraphics renderer, Object storage)
/*      */   {
/*  307 */     if (this.cacheMap == null) this.cacheMap = new HashMap();
/*  308 */     this.cacheMap.put(renderer, storage);
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
/*  321 */     if (this.cacheMap == null) return null;
/*  322 */     return this.cacheMap.get(renderer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeCache(PGraphics renderer)
/*      */   {
/*  331 */     if (this.cacheMap != null) {
/*  332 */       this.cacheMap.remove(renderer);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setParams(PGraphics renderer, Object params)
/*      */   {
/*  344 */     if (this.paramMap == null) this.paramMap = new HashMap();
/*  345 */     this.paramMap.put(renderer, params);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getParams(PGraphics renderer)
/*      */   {
/*  355 */     if (this.paramMap == null) return null;
/*  356 */     return this.paramMap.get(renderer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeParams(PGraphics renderer)
/*      */   {
/*  365 */     if (this.paramMap != null) {
/*  366 */       this.paramMap.remove(renderer);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isModified()
/*      */   {
/*  377 */     return this.modified;
/*      */   }
/*      */   
/*      */   public void setModified()
/*      */   {
/*  382 */     this.modified = true;
/*      */   }
/*      */   
/*      */   public void setModified(boolean m)
/*      */   {
/*  387 */     this.modified = m;
/*      */   }
/*      */   
/*      */   public int getModifiedX1()
/*      */   {
/*  392 */     return this.mx1;
/*      */   }
/*      */   
/*      */   public int getModifiedX2()
/*      */   {
/*  397 */     return this.mx2;
/*      */   }
/*      */   
/*      */   public int getModifiedY1()
/*      */   {
/*  402 */     return this.my1;
/*      */   }
/*      */   
/*      */   public int getModifiedY2()
/*      */   {
/*  407 */     return this.my2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void loadPixels() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePixels()
/*      */   {
/*  427 */     updatePixelsImpl(0, 0, this.width, this.height);
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
/*      */   public void updatePixels(int x, int y, int w, int h)
/*      */   {
/*  457 */     updatePixelsImpl(x, y, w, h);
/*      */   }
/*      */   
/*      */   protected void updatePixelsImpl(int x, int y, int w, int h)
/*      */   {
/*  462 */     int x2 = x + w;
/*  463 */     int y2 = y + h;
/*      */     
/*  465 */     if (!this.modified) {
/*  466 */       this.mx1 = x;
/*  467 */       this.mx2 = x2;
/*  468 */       this.my1 = y;
/*  469 */       this.my2 = y2;
/*  470 */       this.modified = true;
/*      */     }
/*      */     else {
/*  473 */       if (x < this.mx1) this.mx1 = x;
/*  474 */       if (x > this.mx2) this.mx2 = x;
/*  475 */       if (y < this.my1) this.my1 = y;
/*  476 */       if (y > this.my2) { this.my2 = y;
/*      */       }
/*  478 */       if (x2 < this.mx1) this.mx1 = x2;
/*  479 */       if (x2 > this.mx2) this.mx2 = x2;
/*  480 */       if (y2 < this.my1) this.my1 = y2;
/*  481 */       if (y2 > this.my2) { this.my2 = y2;
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
/*      */   public Object clone()
/*      */     throws CloneNotSupportedException
/*      */   {
/*  501 */     return get();
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
/*      */   public void resize(int wide, int high)
/*      */   {
/*  517 */     loadPixels();
/*      */     
/*  519 */     if ((wide <= 0) && (high <= 0)) {
/*  520 */       this.width = 0;
/*  521 */       this.height = 0;
/*  522 */       this.pixels = new int[0];
/*      */     }
/*      */     else {
/*  525 */       if (wide == 0) {
/*  526 */         float diff = high / this.height;
/*  527 */         wide = (int)(this.width * diff);
/*  528 */       } else if (high == 0) {
/*  529 */         float diff = wide / this.width;
/*  530 */         high = (int)(this.height * diff);
/*      */       }
/*  532 */       PImage temp = new PImage(wide, high, this.format);
/*  533 */       temp.copy(this, 0, 0, this.width, this.height, 0, 0, wide, high);
/*  534 */       this.width = wide;
/*  535 */       this.height = high;
/*  536 */       this.pixels = temp.pixels;
/*      */     }
/*      */     
/*  539 */     updatePixels();
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
/*      */   public int get(int x, int y)
/*      */   {
/*  568 */     if ((x < 0) || (y < 0) || (x >= this.width) || (y >= this.height)) { return 0;
/*      */     }
/*  570 */     switch (this.format) {
/*      */     case 1: 
/*  572 */       return this.pixels[(y * this.width + x)] | 0xFF000000;
/*      */     
/*      */     case 2: 
/*  575 */       return this.pixels[(y * this.width + x)];
/*      */     
/*      */     case 4: 
/*  578 */       return this.pixels[(y * this.width + x)] << 24 | 0xFFFFFF;
/*      */     }
/*  580 */     return 0;
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
/*      */   public PImage get(int x, int y, int w, int h)
/*      */   {
/*  613 */     if (x < 0) {
/*  614 */       w += x;
/*  615 */       x = 0;
/*      */     }
/*  617 */     if (y < 0) {
/*  618 */       h += y;
/*  619 */       y = 0;
/*      */     }
/*      */     
/*  622 */     if (x + w > this.width) w = this.width - x;
/*  623 */     if (y + h > this.height) { h = this.height - y;
/*      */     }
/*  625 */     return getImpl(x, y, w, h);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PImage getImpl(int x, int y, int w, int h)
/*      */   {
/*  636 */     PImage newbie = new PImage(w, h, this.format);
/*  637 */     newbie.parent = this.parent;
/*      */     
/*  639 */     int index = y * this.width + x;
/*  640 */     int index2 = 0;
/*  641 */     for (int row = y; row < y + h; row++) {
/*  642 */       System.arraycopy(this.pixels, index, newbie.pixels, index2, w);
/*  643 */       index += this.width;
/*  644 */       index2 += w;
/*      */     }
/*  646 */     return newbie;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PImage get()
/*      */   {
/*  656 */     return get(0, 0, this.width, this.height);
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
/*      */   public void set(int x, int y, int c)
/*      */   {
/*  674 */     if ((x < 0) || (y < 0) || (x >= this.width) || (y >= this.height)) return;
/*  675 */     this.pixels[(y * this.width + x)] = c;
/*  676 */     updatePixelsImpl(x, y, x + 1, y + 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void set(int x, int y, PImage src)
/*      */   {
/*  686 */     int sx = 0;
/*  687 */     int sy = 0;
/*  688 */     int sw = src.width;
/*  689 */     int sh = src.height;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  695 */     if (x < 0) {
/*  696 */       sx -= x;
/*  697 */       sw += x;
/*  698 */       x = 0;
/*      */     }
/*  700 */     if (y < 0) {
/*  701 */       sy -= y;
/*  702 */       sh += y;
/*  703 */       y = 0;
/*      */     }
/*  705 */     if (x + sw > this.width) {
/*  706 */       sw = this.width - x;
/*      */     }
/*  708 */     if (y + sh > this.height) {
/*  709 */       sh = this.height - y;
/*      */     }
/*      */     
/*      */ 
/*  713 */     if ((sw <= 0) || (sh <= 0)) { return;
/*      */     }
/*  715 */     setImpl(x, y, sx, sy, sw, sh, src);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setImpl(int dx, int dy, int sx, int sy, int sw, int sh, PImage src)
/*      */   {
/*  725 */     int srcOffset = sy * src.width + sx;
/*  726 */     int dstOffset = dy * this.width + dx;
/*      */     
/*  728 */     for (int y = sy; y < sy + sh; y++) {
/*  729 */       System.arraycopy(src.pixels, srcOffset, this.pixels, dstOffset, sw);
/*  730 */       srcOffset += src.width;
/*  731 */       dstOffset += this.width;
/*      */     }
/*  733 */     updatePixelsImpl(sx, sy, sx + sw, sy + sh);
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
/*      */   public void mask(int[] maskArray)
/*      */   {
/*  759 */     loadPixels();
/*      */     
/*  761 */     if (maskArray.length != this.pixels.length) {
/*  762 */       throw new RuntimeException("The PImage used with mask() must be the same size as the applet.");
/*      */     }
/*      */     
/*  765 */     for (int i = 0; i < this.pixels.length; i++) {
/*  766 */       this.pixels[i] = ((maskArray[i] & 0xFF) << 24 | this.pixels[i] & 0xFFFFFF);
/*      */     }
/*  768 */     this.format = 2;
/*  769 */     updatePixels();
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
/*      */   public void mask(PImage maskImg)
/*      */   {
/*  785 */     maskImg.loadPixels();
/*  786 */     mask(maskImg.pixels);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void filter(int kind)
/*      */   {
/*  795 */     loadPixels();
/*      */     
/*  797 */     switch (kind)
/*      */     {
/*      */ 
/*      */ 
/*      */     case 11: 
/*  802 */       filter(11, 1.0F);
/*  803 */       break;
/*      */     
/*      */     case 12: 
/*  806 */       if (this.format == 4)
/*      */       {
/*  808 */         for (int i = 0; i < this.pixels.length; i++) {
/*  809 */           int col = 255 - this.pixels[i];
/*  810 */           this.pixels[i] = (0xFF000000 | col << 16 | col << 8 | col);
/*      */         }
/*  812 */         this.format = 1;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  818 */         for (int i = 0; i < this.pixels.length; i++) {
/*  819 */           int col = this.pixels[i];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  824 */           int lum = 77 * (col >> 16 & 0xFF) + 151 * (col >> 8 & 0xFF) + 28 * (col & 0xFF) >> 8;
/*  825 */           this.pixels[i] = (col & 0xFF000000 | lum << 16 | lum << 8 | lum);
/*      */         }
/*      */       }
/*  828 */       break;
/*      */     
/*      */     case 13: 
/*  831 */       for (int i = 0; i < this.pixels.length; i++)
/*      */       {
/*  833 */         this.pixels[i] ^= 0xFFFFFF;
/*      */       }
/*  835 */       break;
/*      */     
/*      */     case 15: 
/*  838 */       throw new RuntimeException("Use filter(POSTERIZE, int levels) instead of filter(POSTERIZE)");
/*      */     
/*      */ 
/*      */     case 14: 
/*  842 */       for (int i = 0; i < this.pixels.length; i++) {
/*  843 */         this.pixels[i] |= 0xFF000000;
/*      */       }
/*  845 */       this.format = 1;
/*  846 */       break;
/*      */     
/*      */     case 16: 
/*  849 */       filter(16, 0.5F);
/*  850 */       break;
/*      */     
/*      */ 
/*      */     case 17: 
/*  854 */       dilate(true);
/*  855 */       break;
/*      */     
/*      */     case 18: 
/*  858 */       dilate(false);
/*      */     }
/*      */     
/*  861 */     updatePixels();
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
/*      */   public void filter(int kind, float param)
/*      */   {
/*  891 */     loadPixels();
/*      */     
/*  893 */     switch (kind) {
/*      */     case 11: 
/*  895 */       if (this.format == 4) {
/*  896 */         blurAlpha(param);
/*  897 */       } else if (this.format == 2) {
/*  898 */         blurARGB(param);
/*      */       } else
/*  900 */         blurRGB(param);
/*  901 */       break;
/*      */     
/*      */     case 12: 
/*  904 */       throw new RuntimeException("Use filter(GRAY) instead of filter(GRAY, param)");
/*      */     
/*      */ 
/*      */     case 13: 
/*  908 */       throw new RuntimeException("Use filter(INVERT) instead of filter(INVERT, param)");
/*      */     
/*      */ 
/*      */     case 14: 
/*  912 */       throw new RuntimeException("Use filter(OPAQUE) instead of filter(OPAQUE, param)");
/*      */     
/*      */ 
/*      */     case 15: 
/*  916 */       int levels = (int)param;
/*  917 */       if ((levels < 2) || (levels > 255)) {
/*  918 */         throw new RuntimeException("Levels must be between 2 and 255 for filter(POSTERIZE, levels)");
/*      */       }
/*      */       
/*  921 */       int levels1 = levels - 1;
/*  922 */       for (int i = 0; i < this.pixels.length; i++) {
/*  923 */         int rlevel = this.pixels[i] >> 16 & 0xFF;
/*  924 */         int glevel = this.pixels[i] >> 8 & 0xFF;
/*  925 */         int blevel = this.pixels[i] & 0xFF;
/*  926 */         rlevel = (rlevel * levels >> 8) * 255 / levels1;
/*  927 */         glevel = (glevel * levels >> 8) * 255 / levels1;
/*  928 */         blevel = (blevel * levels >> 8) * 255 / levels1;
/*  929 */         this.pixels[i] = 
/*      */         
/*      */ 
/*  932 */           (0xFF000000 & this.pixels[i] | rlevel << 16 | glevel << 8 | blevel);
/*      */       }
/*  934 */       break;
/*      */     
/*      */     case 16: 
/*  937 */       int thresh = (int)(param * 255.0F);
/*  938 */       for (int i = 0; i < this.pixels.length; i++) {
/*  939 */         int max = Math.max((this.pixels[i] & 0xFF0000) >> 16, 
/*  940 */           Math.max((this.pixels[i] & 0xFF00) >> 8, 
/*  941 */           this.pixels[i] & 0xFF));
/*  942 */         this.pixels[i] = 
/*  943 */           (this.pixels[i] & 0xFF000000 | (max < thresh ? 0 : 16777215));
/*      */       }
/*  945 */       break;
/*      */     
/*      */ 
/*      */     case 17: 
/*  949 */       throw new RuntimeException("Use filter(ERODE) instead of filter(ERODE, param)");
/*      */     
/*      */     case 18: 
/*  952 */       throw new RuntimeException("Use filter(DILATE) instead of filter(DILATE, param)");
/*      */     }
/*      */     
/*  955 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void buildBlurKernel(float r)
/*      */   {
/*  967 */     int radius = (int)(r * 3.5F);
/*  968 */     radius = radius < 248 ? radius : radius < 1 ? 1 : 248;
/*  969 */     if (this.blurRadius != radius) {
/*  970 */       this.blurRadius = radius;
/*  971 */       this.blurKernelSize = (1 + this.blurRadius << 1);
/*  972 */       this.blurKernel = new int[this.blurKernelSize];
/*  973 */       this.blurMult = new int[this.blurKernelSize]['Ā'];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  978 */       int i = 1; for (int radiusi = radius - 1; i < radius; i++) { int bki;
/*  979 */         this.blurKernel[(radius + i)] = (this.blurKernel[radiusi] = bki = radiusi * radiusi);
/*  980 */         int[] bm = this.blurMult[(radius + i)];
/*  981 */         int[] bmi = this.blurMult[(radiusi--)];
/*  982 */         for (int j = 0; j < 256; j++)
/*  983 */           bm[j] = (bmi[j] = bki * j);
/*      */       }
/*  985 */       int bk = this.blurKernel[radius] = radius * radius;
/*  986 */       int[] bm = this.blurMult[radius];
/*  987 */       for (int j = 0; j < 256; j++) {
/*  988 */         bm[j] = (bk * j);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void blurAlpha(float r)
/*      */   {
/*  996 */     int[] b2 = new int[this.pixels.length];
/*  997 */     int yi = 0;
/*      */     
/*  999 */     buildBlurKernel(r);
/*      */     
/* 1001 */     for (int y = 0; y < this.height; y++) {
/* 1002 */       for (int x = 0; x < this.width; x++) {
/*      */         int sum;
/* 1004 */         int cb = sum = 0;
/* 1005 */         int read = x - this.blurRadius;
/* 1006 */         int bk0; if (read < 0) {
/* 1007 */           int bk0 = -read;
/* 1008 */           read = 0;
/*      */         } else {
/* 1010 */           if (read >= this.width)
/*      */             break;
/* 1012 */           bk0 = 0;
/*      */         }
/* 1014 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1015 */           if (read >= this.width)
/*      */             break;
/* 1017 */           int c = this.pixels[(read + yi)];
/* 1018 */           int[] bm = this.blurMult[i];
/* 1019 */           cb += bm[(c & 0xFF)];
/* 1020 */           sum += this.blurKernel[i];
/* 1021 */           read++;
/*      */         }
/* 1023 */         int ri = yi + x;
/* 1024 */         b2[ri] = (cb / sum);
/*      */       }
/* 1026 */       yi += this.width;
/*      */     }
/*      */     
/* 1029 */     yi = 0;
/* 1030 */     int ym = -this.blurRadius;
/* 1031 */     int ymi = ym * this.width;
/*      */     
/* 1033 */     for (int y = 0; y < this.height; y++) {
/* 1034 */       for (int x = 0; x < this.width; x++) {
/*      */         int sum;
/* 1036 */         int cb = sum = 0;
/* 1037 */         int read; int bk0; int ri; int read; if (ym < 0) { int ri;
/* 1038 */           int bk0 = ri = -ym;
/* 1039 */           read = x;
/*      */         } else {
/* 1041 */           if (ym >= this.height)
/*      */             break;
/* 1043 */           bk0 = 0;
/* 1044 */           ri = ym;
/* 1045 */           read = x + ymi;
/*      */         }
/* 1047 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1048 */           if (ri >= this.height)
/*      */             break;
/* 1050 */           int[] bm = this.blurMult[i];
/* 1051 */           cb += bm[b2[read]];
/* 1052 */           sum += this.blurKernel[i];
/* 1053 */           ri++;
/* 1054 */           read += this.width;
/*      */         }
/* 1056 */         this.pixels[(x + yi)] = (cb / sum);
/*      */       }
/* 1058 */       yi += this.width;
/* 1059 */       ymi += this.width;
/* 1060 */       ym++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void blurRGB(float r)
/*      */   {
/* 1068 */     int[] r2 = new int[this.pixels.length];
/* 1069 */     int[] g2 = new int[this.pixels.length];
/* 1070 */     int[] b2 = new int[this.pixels.length];
/* 1071 */     int yi = 0;
/*      */     
/* 1073 */     buildBlurKernel(r);
/*      */     
/* 1075 */     for (int y = 0; y < this.height; y++) {
/* 1076 */       for (int x = 0; x < this.width; x++) { int sum;
/* 1077 */         int cr; int cg; int cb = cg = cr = sum = 0;
/* 1078 */         int read = x - this.blurRadius;
/* 1079 */         int bk0; if (read < 0) {
/* 1080 */           int bk0 = -read;
/* 1081 */           read = 0;
/*      */         } else {
/* 1083 */           if (read >= this.width)
/*      */             break;
/* 1085 */           bk0 = 0;
/*      */         }
/* 1087 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1088 */           if (read >= this.width)
/*      */             break;
/* 1090 */           int c = this.pixels[(read + yi)];
/* 1091 */           int[] bm = this.blurMult[i];
/* 1092 */           cr += bm[((c & 0xFF0000) >> 16)];
/* 1093 */           cg += bm[((c & 0xFF00) >> 8)];
/* 1094 */           cb += bm[(c & 0xFF)];
/* 1095 */           sum += this.blurKernel[i];
/* 1096 */           read++;
/*      */         }
/* 1098 */         int ri = yi + x;
/* 1099 */         r2[ri] = (cr / sum);
/* 1100 */         g2[ri] = (cg / sum);
/* 1101 */         b2[ri] = (cb / sum);
/*      */       }
/* 1103 */       yi += this.width;
/*      */     }
/*      */     
/* 1106 */     yi = 0;
/* 1107 */     int ym = -this.blurRadius;
/* 1108 */     int ymi = ym * this.width;
/*      */     
/* 1110 */     for (int y = 0; y < this.height; y++) {
/* 1111 */       for (int x = 0; x < this.width; x++) { int sum;
/* 1112 */         int cr; int cg; int cb = cg = cr = sum = 0;
/* 1113 */         int read; int bk0; int ri; int read; if (ym < 0) { int ri;
/* 1114 */           int bk0 = ri = -ym;
/* 1115 */           read = x;
/*      */         } else {
/* 1117 */           if (ym >= this.height)
/*      */             break;
/* 1119 */           bk0 = 0;
/* 1120 */           ri = ym;
/* 1121 */           read = x + ymi;
/*      */         }
/* 1123 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1124 */           if (ri >= this.height)
/*      */             break;
/* 1126 */           int[] bm = this.blurMult[i];
/* 1127 */           cr += bm[r2[read]];
/* 1128 */           cg += bm[g2[read]];
/* 1129 */           cb += bm[b2[read]];
/* 1130 */           sum += this.blurKernel[i];
/* 1131 */           ri++;
/* 1132 */           read += this.width;
/*      */         }
/* 1134 */         this.pixels[(x + yi)] = (0xFF000000 | cr / sum << 16 | cg / sum << 8 | cb / sum);
/*      */       }
/* 1136 */       yi += this.width;
/* 1137 */       ymi += this.width;
/* 1138 */       ym++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void blurARGB(float r)
/*      */   {
/* 1146 */     int wh = this.pixels.length;
/* 1147 */     int[] r2 = new int[wh];
/* 1148 */     int[] g2 = new int[wh];
/* 1149 */     int[] b2 = new int[wh];
/* 1150 */     int[] a2 = new int[wh];
/* 1151 */     int yi = 0;
/*      */     
/* 1153 */     buildBlurKernel(r);
/*      */     
/* 1155 */     for (int y = 0; y < this.height; y++) {
/* 1156 */       for (int x = 0; x < this.width; x++) { int sum;
/* 1157 */         int ca; int cr; int cg; int cb = cg = cr = ca = sum = 0;
/* 1158 */         int read = x - this.blurRadius;
/* 1159 */         int bk0; if (read < 0) {
/* 1160 */           int bk0 = -read;
/* 1161 */           read = 0;
/*      */         } else {
/* 1163 */           if (read >= this.width)
/*      */             break;
/* 1165 */           bk0 = 0;
/*      */         }
/* 1167 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1168 */           if (read >= this.width)
/*      */             break;
/* 1170 */           int c = this.pixels[(read + yi)];
/* 1171 */           int[] bm = this.blurMult[i];
/* 1172 */           ca += bm[((c & 0xFF000000) >>> 24)];
/* 1173 */           cr += bm[((c & 0xFF0000) >> 16)];
/* 1174 */           cg += bm[((c & 0xFF00) >> 8)];
/* 1175 */           cb += bm[(c & 0xFF)];
/* 1176 */           sum += this.blurKernel[i];
/* 1177 */           read++;
/*      */         }
/* 1179 */         int ri = yi + x;
/* 1180 */         a2[ri] = (ca / sum);
/* 1181 */         r2[ri] = (cr / sum);
/* 1182 */         g2[ri] = (cg / sum);
/* 1183 */         b2[ri] = (cb / sum);
/*      */       }
/* 1185 */       yi += this.width;
/*      */     }
/*      */     
/* 1188 */     yi = 0;
/* 1189 */     int ym = -this.blurRadius;
/* 1190 */     int ymi = ym * this.width;
/*      */     
/* 1192 */     for (int y = 0; y < this.height; y++) {
/* 1193 */       for (int x = 0; x < this.width; x++) { int sum;
/* 1194 */         int ca; int cr; int cg; int cb = cg = cr = ca = sum = 0;
/* 1195 */         int read; int bk0; int ri; int read; if (ym < 0) { int ri;
/* 1196 */           int bk0 = ri = -ym;
/* 1197 */           read = x;
/*      */         } else {
/* 1199 */           if (ym >= this.height)
/*      */             break;
/* 1201 */           bk0 = 0;
/* 1202 */           ri = ym;
/* 1203 */           read = x + ymi;
/*      */         }
/* 1205 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1206 */           if (ri >= this.height)
/*      */             break;
/* 1208 */           int[] bm = this.blurMult[i];
/* 1209 */           ca += bm[a2[read]];
/* 1210 */           cr += bm[r2[read]];
/* 1211 */           cg += bm[g2[read]];
/* 1212 */           cb += bm[b2[read]];
/* 1213 */           sum += this.blurKernel[i];
/* 1214 */           ri++;
/* 1215 */           read += this.width;
/*      */         }
/* 1217 */         this.pixels[(x + yi)] = (ca / sum << 24 | cr / sum << 16 | cg / sum << 8 | cb / sum);
/*      */       }
/* 1219 */       yi += this.width;
/* 1220 */       ymi += this.width;
/* 1221 */       ym++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void dilate(boolean isInverted)
/*      */   {
/* 1231 */     int currIdx = 0;
/* 1232 */     int maxIdx = this.pixels.length;
/* 1233 */     int[] out = new int[maxIdx];
/*      */     
/* 1235 */     if (!isInverted)
/*      */     {
/* 1237 */       for (; currIdx < maxIdx; 
/*      */           
/*      */ 
/* 1240 */           currIdx < maxRowIdx)
/*      */       {
/* 1238 */         currRowIdx = currIdx;
/* 1239 */         maxRowIdx = currIdx + this.width;
/* 1240 */         continue;
/*      */         
/* 1242 */         colOrig = colOut = this.pixels[currIdx];
/* 1243 */         idxLeft = currIdx - 1;
/* 1244 */         idxRight = currIdx + 1;
/* 1245 */         idxUp = currIdx - this.width;
/* 1246 */         idxDown = currIdx + this.width;
/* 1247 */         if (idxLeft < currRowIdx)
/* 1248 */           idxLeft = currIdx;
/* 1249 */         if (idxRight >= maxRowIdx)
/* 1250 */           idxRight = currIdx;
/* 1251 */         if (idxUp < 0)
/* 1252 */           idxUp = currIdx;
/* 1253 */         if (idxDown >= maxIdx) {
/* 1254 */           idxDown = currIdx;
/*      */         }
/* 1256 */         colUp = this.pixels[idxUp];
/* 1257 */         colLeft = this.pixels[idxLeft];
/* 1258 */         colDown = this.pixels[idxDown];
/* 1259 */         colRight = this.pixels[idxRight];
/*      */         
/*      */ 
/* 1262 */         currLum = 
/* 1263 */           77 * (colOrig >> 16 & 0xFF) + 151 * (colOrig >> 8 & 0xFF) + 28 * (colOrig & 0xFF);
/* 1264 */         lumLeft = 
/* 1265 */           77 * (colLeft >> 16 & 0xFF) + 151 * (colLeft >> 8 & 0xFF) + 28 * (colLeft & 0xFF);
/* 1266 */         lumRight = 
/* 1267 */           77 * (colRight >> 16 & 0xFF) + 151 * (colRight >> 8 & 0xFF) + 28 * (colRight & 0xFF);
/* 1268 */         lumUp = 
/* 1269 */           77 * (colUp >> 16 & 0xFF) + 151 * (colUp >> 8 & 0xFF) + 28 * (colUp & 0xFF);
/* 1270 */         lumDown = 
/* 1271 */           77 * (colDown >> 16 & 0xFF) + 151 * (colDown >> 8 & 0xFF) + 28 * (colDown & 0xFF);
/*      */         
/* 1273 */         if (lumLeft > currLum) {
/* 1274 */           colOut = colLeft;
/* 1275 */           currLum = lumLeft;
/*      */         }
/* 1277 */         if (lumRight > currLum) {
/* 1278 */           colOut = colRight;
/* 1279 */           currLum = lumRight;
/*      */         }
/* 1281 */         if (lumUp > currLum) {
/* 1282 */           colOut = colUp;
/* 1283 */           currLum = lumUp;
/*      */         }
/* 1285 */         if (lumDown > currLum) {
/* 1286 */           colOut = colDown;
/* 1287 */           currLum = lumDown;
/*      */         }
/* 1289 */         out[(currIdx++)] = colOut;
/*      */       }
/*      */     }
/*      */     else {
/*      */       int maxRowIdx;
/* 1294 */       for (; currIdx < maxIdx; 
/*      */           
/*      */ 
/* 1297 */           currIdx < maxRowIdx)
/*      */       {
/*      */         int currRowIdx;
/*      */         int maxRowIdx;
/*      */         int colOut;
/*      */         int colOrig;
/*      */         int idxLeft;
/*      */         int idxRight;
/*      */         int idxUp;
/*      */         int idxDown;
/*      */         int colUp;
/*      */         int colLeft;
/*      */         int colDown;
/*      */         int colRight;
/*      */         int currLum;
/*      */         int lumLeft;
/*      */         int lumRight;
/*      */         int lumUp;
/*      */         int lumDown;
/* 1295 */         int currRowIdx = currIdx;
/* 1296 */         maxRowIdx = currIdx + this.width;
/* 1297 */         continue;
/*      */         int colOut;
/* 1299 */         int colOrig = colOut = this.pixels[currIdx];
/* 1300 */         int idxLeft = currIdx - 1;
/* 1301 */         int idxRight = currIdx + 1;
/* 1302 */         int idxUp = currIdx - this.width;
/* 1303 */         int idxDown = currIdx + this.width;
/* 1304 */         if (idxLeft < currRowIdx)
/* 1305 */           idxLeft = currIdx;
/* 1306 */         if (idxRight >= maxRowIdx)
/* 1307 */           idxRight = currIdx;
/* 1308 */         if (idxUp < 0)
/* 1309 */           idxUp = currIdx;
/* 1310 */         if (idxDown >= maxIdx) {
/* 1311 */           idxDown = currIdx;
/*      */         }
/* 1313 */         int colUp = this.pixels[idxUp];
/* 1314 */         int colLeft = this.pixels[idxLeft];
/* 1315 */         int colDown = this.pixels[idxDown];
/* 1316 */         int colRight = this.pixels[idxRight];
/*      */         
/*      */ 
/* 1319 */         int currLum = 
/* 1320 */           77 * (colOrig >> 16 & 0xFF) + 151 * (colOrig >> 8 & 0xFF) + 28 * (colOrig & 0xFF);
/* 1321 */         int lumLeft = 
/* 1322 */           77 * (colLeft >> 16 & 0xFF) + 151 * (colLeft >> 8 & 0xFF) + 28 * (colLeft & 0xFF);
/* 1323 */         int lumRight = 
/* 1324 */           77 * (colRight >> 16 & 0xFF) + 151 * (colRight >> 8 & 0xFF) + 28 * (colRight & 0xFF);
/* 1325 */         int lumUp = 
/* 1326 */           77 * (colUp >> 16 & 0xFF) + 151 * (colUp >> 8 & 0xFF) + 28 * (colUp & 0xFF);
/* 1327 */         int lumDown = 
/* 1328 */           77 * (colDown >> 16 & 0xFF) + 151 * (colDown >> 8 & 0xFF) + 28 * (colDown & 0xFF);
/*      */         
/* 1330 */         if (lumLeft < currLum) {
/* 1331 */           colOut = colLeft;
/* 1332 */           currLum = lumLeft;
/*      */         }
/* 1334 */         if (lumRight < currLum) {
/* 1335 */           colOut = colRight;
/* 1336 */           currLum = lumRight;
/*      */         }
/* 1338 */         if (lumUp < currLum) {
/* 1339 */           colOut = colUp;
/* 1340 */           currLum = lumUp;
/*      */         }
/* 1342 */         if (lumDown < currLum) {
/* 1343 */           colOut = colDown;
/* 1344 */           currLum = lumDown;
/*      */         }
/* 1346 */         out[(currIdx++)] = colOut;
/*      */       }
/*      */     }
/*      */     
/* 1350 */     System.arraycopy(out, 0, this.pixels, 0, maxIdx);
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
/*      */   public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*      */   {
/* 1366 */     blend(this, sx, sy, sw, sh, dx, dy, dw, dh, 0);
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
/*      */   public void copy(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*      */   {
/* 1392 */     blend(src, sx, sy, sw, sh, dx, dy, dw, dh, 0);
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
/*      */   public static int blendColor(int c1, int c2, int mode)
/*      */   {
/* 1467 */     switch (mode) {
/* 1468 */     case 0:  return c2;
/* 1469 */     case 1:  return blend_blend(c1, c2);
/*      */     case 2: 
/* 1471 */       return blend_add_pin(c1, c2);
/* 1472 */     case 4:  return blend_sub_pin(c1, c2);
/*      */     case 8: 
/* 1474 */       return blend_lightest(c1, c2);
/* 1475 */     case 16:  return blend_darkest(c1, c2);
/*      */     case 32: 
/* 1477 */       return blend_difference(c1, c2);
/* 1478 */     case 64:  return blend_exclusion(c1, c2);
/*      */     case 128: 
/* 1480 */       return blend_multiply(c1, c2);
/* 1481 */     case 256:  return blend_screen(c1, c2);
/*      */     case 1024: 
/* 1483 */       return blend_hard_light(c1, c2);
/* 1484 */     case 2048:  return blend_soft_light(c1, c2);
/* 1485 */     case 512:  return blend_overlay(c1, c2);
/*      */     case 4096: 
/* 1487 */       return blend_dodge(c1, c2);
/* 1488 */     case 8192:  return blend_burn(c1, c2);
/*      */     }
/* 1490 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void blend(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode)
/*      */   {
/* 1501 */     blend(this, sx, sy, sw, sh, dx, dy, dw, dh, mode);
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
/*      */   public void blend(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode)
/*      */   {
/* 1562 */     int sx2 = sx + sw;
/* 1563 */     int sy2 = sy + sh;
/* 1564 */     int dx2 = dx + dw;
/* 1565 */     int dy2 = dy + dh;
/*      */     
/* 1567 */     loadPixels();
/* 1568 */     if (src == this) {
/* 1569 */       if (intersect(sx, sy, sx2, sy2, dx, dy, dx2, dy2)) {
/* 1570 */         blit_resize(get(sx, sy, sx2 - sx, sy2 - sy), 
/* 1571 */           0, 0, sx2 - sx - 1, sy2 - sy - 1, 
/* 1572 */           this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
/*      */       }
/*      */       else {
/* 1575 */         blit_resize(src, sx, sy, sx2, sy2, 
/* 1576 */           this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
/*      */       }
/*      */     } else {
/* 1579 */       src.loadPixels();
/* 1580 */       blit_resize(src, sx, sy, sx2, sy2, 
/* 1581 */         this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
/*      */     }
/*      */     
/* 1584 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean intersect(int sx1, int sy1, int sx2, int sy2, int dx1, int dy1, int dx2, int dy2)
/*      */   {
/* 1593 */     int sw = sx2 - sx1 + 1;
/* 1594 */     int sh = sy2 - sy1 + 1;
/* 1595 */     int dw = dx2 - dx1 + 1;
/* 1596 */     int dh = dy2 - dy1 + 1;
/*      */     
/* 1598 */     if (dx1 < sx1) {
/* 1599 */       dw += dx1 - sx1;
/* 1600 */       if (dw > sw) {
/* 1601 */         dw = sw;
/*      */       }
/*      */     } else {
/* 1604 */       int w = sw + sx1 - dx1;
/* 1605 */       if (dw > w) {
/* 1606 */         dw = w;
/*      */       }
/*      */     }
/* 1609 */     if (dy1 < sy1) {
/* 1610 */       dh += dy1 - sy1;
/* 1611 */       if (dh > sh) {
/* 1612 */         dh = sh;
/*      */       }
/*      */     } else {
/* 1615 */       int h = sh + sy1 - dy1;
/* 1616 */       if (dh > h) {
/* 1617 */         dh = h;
/*      */       }
/*      */     }
/* 1620 */     return (dw > 0) && (dh > 0);
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
/*      */   private void blit_resize(PImage img, int srcX1, int srcY1, int srcX2, int srcY2, int[] destPixels, int screenW, int screenH, int destX1, int destY1, int destX2, int destY2, int mode)
/*      */   {
/* 1637 */     if (srcX1 < 0) srcX1 = 0;
/* 1638 */     if (srcY1 < 0) srcY1 = 0;
/* 1639 */     if (srcX2 > img.width) srcX2 = img.width;
/* 1640 */     if (srcY2 > img.height) { srcY2 = img.height;
/*      */     }
/* 1642 */     int srcW = srcX2 - srcX1;
/* 1643 */     int srcH = srcY2 - srcY1;
/* 1644 */     int destW = destX2 - destX1;
/* 1645 */     int destH = destY2 - destY1;
/*      */     
/* 1647 */     boolean smooth = true;
/*      */     
/* 1649 */     if (!smooth) {
/* 1650 */       srcW++;srcH++;
/*      */     }
/*      */     
/* 1653 */     if ((destW <= 0) || (destH <= 0) || 
/* 1654 */       (srcW <= 0) || (srcH <= 0) || 
/* 1655 */       (destX1 >= screenW) || (destY1 >= screenH) || 
/* 1656 */       (srcX1 >= img.width) || (srcY1 >= img.height)) {
/* 1657 */       return;
/*      */     }
/*      */     
/* 1660 */     int dx = (int)(srcW / destW * 32768.0F);
/* 1661 */     int dy = (int)(srcH / destH * 32768.0F);
/*      */     
/* 1663 */     this.srcXOffset = (destX1 < 0 ? -destX1 * dx : srcX1 * 32768);
/* 1664 */     this.srcYOffset = (destY1 < 0 ? -destY1 * dy : srcY1 * 32768);
/*      */     
/* 1666 */     if (destX1 < 0) {
/* 1667 */       destW += destX1;
/* 1668 */       destX1 = 0;
/*      */     }
/* 1670 */     if (destY1 < 0) {
/* 1671 */       destH += destY1;
/* 1672 */       destY1 = 0;
/*      */     }
/*      */     
/* 1675 */     destW = low(destW, screenW - destX1);
/* 1676 */     destH = low(destH, screenH - destY1);
/*      */     
/* 1678 */     int destOffset = destY1 * screenW + destX1;
/* 1679 */     this.srcBuffer = img.pixels;
/*      */     
/* 1681 */     if (smooth)
/*      */     {
/* 1683 */       this.iw = img.width;
/* 1684 */       this.iw1 = (img.width - 1);
/* 1685 */       this.ih1 = (img.height - 1);
/*      */       
/* 1687 */       switch (mode)
/*      */       {
/*      */       case 1: 
/* 1690 */         for (int y = 0; y < destH; y++) {
/* 1691 */           filter_new_scanline();
/* 1692 */           for (int x = 0; x < destW; x++)
/*      */           {
/* 1694 */             destPixels[(destOffset + x)] = 
/* 1695 */               blend_blend(destPixels[(destOffset + x)], filter_bilinear());
/* 1696 */             this.sX += dx;
/*      */           }
/* 1698 */           destOffset += screenW;
/* 1699 */           this.srcYOffset += dy;
/*      */         }
/* 1701 */         break;
/*      */       
/*      */       case 2: 
/* 1704 */         for (int y = 0; y < destH; y++) {
/* 1705 */           filter_new_scanline();
/* 1706 */           for (int x = 0; x < destW; x++) {
/* 1707 */             destPixels[(destOffset + x)] = 
/* 1708 */               blend_add_pin(destPixels[(destOffset + x)], filter_bilinear());
/* 1709 */             this.sX += dx;
/*      */           }
/* 1711 */           destOffset += screenW;
/* 1712 */           this.srcYOffset += dy;
/*      */         }
/* 1714 */         break;
/*      */       
/*      */       case 4: 
/* 1717 */         for (int y = 0; y < destH; y++) {
/* 1718 */           filter_new_scanline();
/* 1719 */           for (int x = 0; x < destW; x++) {
/* 1720 */             destPixels[(destOffset + x)] = 
/* 1721 */               blend_sub_pin(destPixels[(destOffset + x)], filter_bilinear());
/* 1722 */             this.sX += dx;
/*      */           }
/* 1724 */           destOffset += screenW;
/* 1725 */           this.srcYOffset += dy;
/*      */         }
/* 1727 */         break;
/*      */       
/*      */       case 8: 
/* 1730 */         for (int y = 0; y < destH; y++) {
/* 1731 */           filter_new_scanline();
/* 1732 */           for (int x = 0; x < destW; x++) {
/* 1733 */             destPixels[(destOffset + x)] = 
/* 1734 */               blend_lightest(destPixels[(destOffset + x)], filter_bilinear());
/* 1735 */             this.sX += dx;
/*      */           }
/* 1737 */           destOffset += screenW;
/* 1738 */           this.srcYOffset += dy;
/*      */         }
/* 1740 */         break;
/*      */       
/*      */       case 16: 
/* 1743 */         for (int y = 0; y < destH; y++) {
/* 1744 */           filter_new_scanline();
/* 1745 */           for (int x = 0; x < destW; x++) {
/* 1746 */             destPixels[(destOffset + x)] = 
/* 1747 */               blend_darkest(destPixels[(destOffset + x)], filter_bilinear());
/* 1748 */             this.sX += dx;
/*      */           }
/* 1750 */           destOffset += screenW;
/* 1751 */           this.srcYOffset += dy;
/*      */         }
/* 1753 */         break;
/*      */       
/*      */       case 0: 
/* 1756 */         for (int y = 0; y < destH; y++) {
/* 1757 */           filter_new_scanline();
/* 1758 */           for (int x = 0; x < destW; x++) {
/* 1759 */             destPixels[(destOffset + x)] = filter_bilinear();
/* 1760 */             this.sX += dx;
/*      */           }
/* 1762 */           destOffset += screenW;
/* 1763 */           this.srcYOffset += dy;
/*      */         }
/* 1765 */         break;
/*      */       
/*      */       case 32: 
/* 1768 */         for (int y = 0; y < destH; y++) {
/* 1769 */           filter_new_scanline();
/* 1770 */           for (int x = 0; x < destW; x++) {
/* 1771 */             destPixels[(destOffset + x)] = 
/* 1772 */               blend_difference(destPixels[(destOffset + x)], filter_bilinear());
/* 1773 */             this.sX += dx;
/*      */           }
/* 1775 */           destOffset += screenW;
/* 1776 */           this.srcYOffset += dy;
/*      */         }
/* 1778 */         break;
/*      */       
/*      */       case 64: 
/* 1781 */         for (int y = 0; y < destH; y++) {
/* 1782 */           filter_new_scanline();
/* 1783 */           for (int x = 0; x < destW; x++) {
/* 1784 */             destPixels[(destOffset + x)] = 
/* 1785 */               blend_exclusion(destPixels[(destOffset + x)], filter_bilinear());
/* 1786 */             this.sX += dx;
/*      */           }
/* 1788 */           destOffset += screenW;
/* 1789 */           this.srcYOffset += dy;
/*      */         }
/* 1791 */         break;
/*      */       
/*      */       case 128: 
/* 1794 */         for (int y = 0; y < destH; y++) {
/* 1795 */           filter_new_scanline();
/* 1796 */           for (int x = 0; x < destW; x++) {
/* 1797 */             destPixels[(destOffset + x)] = 
/* 1798 */               blend_multiply(destPixels[(destOffset + x)], filter_bilinear());
/* 1799 */             this.sX += dx;
/*      */           }
/* 1801 */           destOffset += screenW;
/* 1802 */           this.srcYOffset += dy;
/*      */         }
/* 1804 */         break;
/*      */       
/*      */       case 256: 
/* 1807 */         for (int y = 0; y < destH; y++) {
/* 1808 */           filter_new_scanline();
/* 1809 */           for (int x = 0; x < destW; x++) {
/* 1810 */             destPixels[(destOffset + x)] = 
/* 1811 */               blend_screen(destPixels[(destOffset + x)], filter_bilinear());
/* 1812 */             this.sX += dx;
/*      */           }
/* 1814 */           destOffset += screenW;
/* 1815 */           this.srcYOffset += dy;
/*      */         }
/* 1817 */         break;
/*      */       
/*      */       case 512: 
/* 1820 */         for (int y = 0; y < destH; y++) {
/* 1821 */           filter_new_scanline();
/* 1822 */           for (int x = 0; x < destW; x++) {
/* 1823 */             destPixels[(destOffset + x)] = 
/* 1824 */               blend_overlay(destPixels[(destOffset + x)], filter_bilinear());
/* 1825 */             this.sX += dx;
/*      */           }
/* 1827 */           destOffset += screenW;
/* 1828 */           this.srcYOffset += dy;
/*      */         }
/* 1830 */         break;
/*      */       
/*      */       case 1024: 
/* 1833 */         for (int y = 0; y < destH; y++) {
/* 1834 */           filter_new_scanline();
/* 1835 */           for (int x = 0; x < destW; x++) {
/* 1836 */             destPixels[(destOffset + x)] = 
/* 1837 */               blend_hard_light(destPixels[(destOffset + x)], filter_bilinear());
/* 1838 */             this.sX += dx;
/*      */           }
/* 1840 */           destOffset += screenW;
/* 1841 */           this.srcYOffset += dy;
/*      */         }
/* 1843 */         break;
/*      */       
/*      */       case 2048: 
/* 1846 */         for (int y = 0; y < destH; y++) {
/* 1847 */           filter_new_scanline();
/* 1848 */           for (int x = 0; x < destW; x++) {
/* 1849 */             destPixels[(destOffset + x)] = 
/* 1850 */               blend_soft_light(destPixels[(destOffset + x)], filter_bilinear());
/* 1851 */             this.sX += dx;
/*      */           }
/* 1853 */           destOffset += screenW;
/* 1854 */           this.srcYOffset += dy;
/*      */         }
/* 1856 */         break;
/*      */       
/*      */ 
/*      */       case 4096: 
/* 1860 */         for (int y = 0; y < destH; y++) {
/* 1861 */           filter_new_scanline();
/* 1862 */           for (int x = 0; x < destW; x++) {
/* 1863 */             destPixels[(destOffset + x)] = 
/* 1864 */               blend_dodge(destPixels[(destOffset + x)], filter_bilinear());
/* 1865 */             this.sX += dx;
/*      */           }
/* 1867 */           destOffset += screenW;
/* 1868 */           this.srcYOffset += dy;
/*      */         }
/* 1870 */         break;
/*      */       
/*      */       case 8192: 
/* 1873 */         for (int y = 0; y < destH; y++) {
/* 1874 */           filter_new_scanline();
/* 1875 */           for (int x = 0; x < destW; x++) {
/* 1876 */             destPixels[(destOffset + x)] = 
/* 1877 */               blend_burn(destPixels[(destOffset + x)], filter_bilinear());
/* 1878 */             this.sX += dx;
/*      */           }
/* 1880 */           destOffset += screenW;
/* 1881 */           this.srcYOffset += dy;
/*      */         }
/*      */       
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/* 1889 */       switch (mode)
/*      */       {
/*      */       case 1: 
/* 1892 */         for (int y = 0; y < destH; y++) {
/* 1893 */           this.sX = this.srcXOffset;
/* 1894 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1895 */           for (int x = 0; x < destW; x++)
/*      */           {
/* 1897 */             destPixels[(destOffset + x)] = 
/* 1898 */               blend_blend(destPixels[(destOffset + x)], 
/* 1899 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1900 */             this.sX += dx;
/*      */           }
/* 1902 */           destOffset += screenW;
/* 1903 */           this.srcYOffset += dy;
/*      */         }
/* 1905 */         break;
/*      */       
/*      */       case 2: 
/* 1908 */         for (int y = 0; y < destH; y++) {
/* 1909 */           this.sX = this.srcXOffset;
/* 1910 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1911 */           for (int x = 0; x < destW; x++) {
/* 1912 */             destPixels[(destOffset + x)] = 
/* 1913 */               blend_add_pin(destPixels[(destOffset + x)], 
/* 1914 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1915 */             this.sX += dx;
/*      */           }
/* 1917 */           destOffset += screenW;
/* 1918 */           this.srcYOffset += dy;
/*      */         }
/* 1920 */         break;
/*      */       
/*      */       case 4: 
/* 1923 */         for (int y = 0; y < destH; y++) {
/* 1924 */           this.sX = this.srcXOffset;
/* 1925 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1926 */           for (int x = 0; x < destW; x++) {
/* 1927 */             destPixels[(destOffset + x)] = 
/* 1928 */               blend_sub_pin(destPixels[(destOffset + x)], 
/* 1929 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1930 */             this.sX += dx;
/*      */           }
/* 1932 */           destOffset += screenW;
/* 1933 */           this.srcYOffset += dy;
/*      */         }
/* 1935 */         break;
/*      */       
/*      */       case 8: 
/* 1938 */         for (int y = 0; y < destH; y++) {
/* 1939 */           this.sX = this.srcXOffset;
/* 1940 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1941 */           for (int x = 0; x < destW; x++) {
/* 1942 */             destPixels[(destOffset + x)] = 
/* 1943 */               blend_lightest(destPixels[(destOffset + x)], 
/* 1944 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1945 */             this.sX += dx;
/*      */           }
/* 1947 */           destOffset += screenW;
/* 1948 */           this.srcYOffset += dy;
/*      */         }
/* 1950 */         break;
/*      */       
/*      */       case 16: 
/* 1953 */         for (int y = 0; y < destH; y++) {
/* 1954 */           this.sX = this.srcXOffset;
/* 1955 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1956 */           for (int x = 0; x < destW; x++) {
/* 1957 */             destPixels[(destOffset + x)] = 
/* 1958 */               blend_darkest(destPixels[(destOffset + x)], 
/* 1959 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1960 */             this.sX += dx;
/*      */           }
/* 1962 */           destOffset += screenW;
/* 1963 */           this.srcYOffset += dy;
/*      */         }
/* 1965 */         break;
/*      */       
/*      */       case 0: 
/* 1968 */         for (int y = 0; y < destH; y++) {
/* 1969 */           this.sX = this.srcXOffset;
/* 1970 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1971 */           for (int x = 0; x < destW; x++) {
/* 1972 */             destPixels[(destOffset + x)] = this.srcBuffer[(this.sY + (this.sX >> 15))];
/* 1973 */             this.sX += dx;
/*      */           }
/* 1975 */           destOffset += screenW;
/* 1976 */           this.srcYOffset += dy;
/*      */         }
/* 1978 */         break;
/*      */       
/*      */       case 32: 
/* 1981 */         for (int y = 0; y < destH; y++) {
/* 1982 */           this.sX = this.srcXOffset;
/* 1983 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1984 */           for (int x = 0; x < destW; x++) {
/* 1985 */             destPixels[(destOffset + x)] = 
/* 1986 */               blend_difference(destPixels[(destOffset + x)], 
/* 1987 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1988 */             this.sX += dx;
/*      */           }
/* 1990 */           destOffset += screenW;
/* 1991 */           this.srcYOffset += dy;
/*      */         }
/* 1993 */         break;
/*      */       
/*      */       case 64: 
/* 1996 */         for (int y = 0; y < destH; y++) {
/* 1997 */           this.sX = this.srcXOffset;
/* 1998 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1999 */           for (int x = 0; x < destW; x++) {
/* 2000 */             destPixels[(destOffset + x)] = 
/* 2001 */               blend_exclusion(destPixels[(destOffset + x)], 
/* 2002 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2003 */             this.sX += dx;
/*      */           }
/* 2005 */           destOffset += screenW;
/* 2006 */           this.srcYOffset += dy;
/*      */         }
/* 2008 */         break;
/*      */       
/*      */       case 128: 
/* 2011 */         for (int y = 0; y < destH; y++) {
/* 2012 */           this.sX = this.srcXOffset;
/* 2013 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2014 */           for (int x = 0; x < destW; x++) {
/* 2015 */             destPixels[(destOffset + x)] = 
/* 2016 */               blend_multiply(destPixels[(destOffset + x)], 
/* 2017 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2018 */             this.sX += dx;
/*      */           }
/* 2020 */           destOffset += screenW;
/* 2021 */           this.srcYOffset += dy;
/*      */         }
/* 2023 */         break;
/*      */       
/*      */       case 256: 
/* 2026 */         for (int y = 0; y < destH; y++) {
/* 2027 */           this.sX = this.srcXOffset;
/* 2028 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2029 */           for (int x = 0; x < destW; x++) {
/* 2030 */             destPixels[(destOffset + x)] = 
/* 2031 */               blend_screen(destPixels[(destOffset + x)], 
/* 2032 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2033 */             this.sX += dx;
/*      */           }
/* 2035 */           destOffset += screenW;
/* 2036 */           this.srcYOffset += dy;
/*      */         }
/* 2038 */         break;
/*      */       
/*      */       case 512: 
/* 2041 */         for (int y = 0; y < destH; y++) {
/* 2042 */           this.sX = this.srcXOffset;
/* 2043 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2044 */           for (int x = 0; x < destW; x++) {
/* 2045 */             destPixels[(destOffset + x)] = 
/* 2046 */               blend_overlay(destPixels[(destOffset + x)], 
/* 2047 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2048 */             this.sX += dx;
/*      */           }
/* 2050 */           destOffset += screenW;
/* 2051 */           this.srcYOffset += dy;
/*      */         }
/* 2053 */         break;
/*      */       
/*      */       case 1024: 
/* 2056 */         for (int y = 0; y < destH; y++) {
/* 2057 */           this.sX = this.srcXOffset;
/* 2058 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2059 */           for (int x = 0; x < destW; x++) {
/* 2060 */             destPixels[(destOffset + x)] = 
/* 2061 */               blend_hard_light(destPixels[(destOffset + x)], 
/* 2062 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2063 */             this.sX += dx;
/*      */           }
/* 2065 */           destOffset += screenW;
/* 2066 */           this.srcYOffset += dy;
/*      */         }
/* 2068 */         break;
/*      */       
/*      */       case 2048: 
/* 2071 */         for (int y = 0; y < destH; y++) {
/* 2072 */           this.sX = this.srcXOffset;
/* 2073 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2074 */           for (int x = 0; x < destW; x++) {
/* 2075 */             destPixels[(destOffset + x)] = 
/* 2076 */               blend_soft_light(destPixels[(destOffset + x)], 
/* 2077 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2078 */             this.sX += dx;
/*      */           }
/* 2080 */           destOffset += screenW;
/* 2081 */           this.srcYOffset += dy;
/*      */         }
/* 2083 */         break;
/*      */       
/*      */ 
/*      */       case 4096: 
/* 2087 */         for (int y = 0; y < destH; y++) {
/* 2088 */           this.sX = this.srcXOffset;
/* 2089 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2090 */           for (int x = 0; x < destW; x++) {
/* 2091 */             destPixels[(destOffset + x)] = 
/* 2092 */               blend_dodge(destPixels[(destOffset + x)], 
/* 2093 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2094 */             this.sX += dx;
/*      */           }
/* 2096 */           destOffset += screenW;
/* 2097 */           this.srcYOffset += dy;
/*      */         }
/* 2099 */         break;
/*      */       
/*      */       case 8192: 
/* 2102 */         for (int y = 0; y < destH; y++) {
/* 2103 */           this.sX = this.srcXOffset;
/* 2104 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2105 */           for (int x = 0; x < destW; x++) {
/* 2106 */             destPixels[(destOffset + x)] = 
/* 2107 */               blend_burn(destPixels[(destOffset + x)], 
/* 2108 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2109 */             this.sX += dx;
/*      */           }
/* 2111 */           destOffset += screenW;
/* 2112 */           this.srcYOffset += dy;
/*      */         }
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void filter_new_scanline()
/*      */   {
/* 2122 */     this.sX = this.srcXOffset;
/* 2123 */     this.fracV = (this.srcYOffset & 0x7FFF);
/* 2124 */     this.ifV = (32767 - this.fracV);
/* 2125 */     this.v1 = ((this.srcYOffset >> 15) * this.iw);
/* 2126 */     this.v2 = (low((this.srcYOffset >> 15) + 1, this.ih1) * this.iw);
/*      */   }
/*      */   
/*      */   private int filter_bilinear()
/*      */   {
/* 2131 */     this.fracU = (this.sX & 0x7FFF);
/* 2132 */     this.ifU = (32767 - this.fracU);
/* 2133 */     this.ul = (this.ifU * this.ifV >> 15);
/* 2134 */     this.ll = (this.ifU * this.fracV >> 15);
/* 2135 */     this.ur = (this.fracU * this.ifV >> 15);
/* 2136 */     this.lr = (this.fracU * this.fracV >> 15);
/* 2137 */     this.u1 = (this.sX >> 15);
/* 2138 */     this.u2 = low(this.u1 + 1, this.iw1);
/*      */     
/*      */ 
/* 2141 */     this.cUL = this.srcBuffer[(this.v1 + this.u1)];
/* 2142 */     this.cUR = this.srcBuffer[(this.v1 + this.u2)];
/* 2143 */     this.cLL = this.srcBuffer[(this.v2 + this.u1)];
/* 2144 */     this.cLR = this.srcBuffer[(this.v2 + this.u2)];
/*      */     
/* 2146 */     this.r = 
/*      */     
/* 2148 */       (this.ul * ((this.cUL & 0xFF0000) >> 16) + this.ll * ((this.cLL & 0xFF0000) >> 16) + this.ur * ((this.cUR & 0xFF0000) >> 16) + this.lr * ((this.cLR & 0xFF0000) >> 16) << 1 & 0xFF0000);
/*      */     
/* 2150 */     this.g = 
/*      */     
/* 2152 */       (this.ul * (this.cUL & 0xFF00) + this.ll * (this.cLL & 0xFF00) + this.ur * (this.cUR & 0xFF00) + this.lr * (this.cLR & 0xFF00) >>> 15 & 0xFF00);
/*      */     
/* 2154 */     this.b = 
/*      */     
/* 2156 */       (this.ul * (this.cUL & 0xFF) + this.ll * (this.cLL & 0xFF) + this.ur * (this.cUR & 0xFF) + this.lr * (this.cLR & 0xFF) >>> 15);
/*      */     
/* 2158 */     this.a = 
/*      */     
/* 2160 */       (this.ul * ((this.cUL & 0xFF000000) >>> 24) + this.ll * ((this.cLL & 0xFF000000) >>> 24) + this.ur * ((this.cUR & 0xFF000000) >>> 24) + this.lr * ((this.cLR & 0xFF000000) >>> 24) << 9 & 0xFF000000);
/*      */     
/* 2162 */     return this.a | this.r | this.g | this.b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int low(int a, int b)
/*      */   {
/* 2173 */     return a < b ? a : b;
/*      */   }
/*      */   
/*      */   private static int high(int a, int b)
/*      */   {
/* 2178 */     return a > b ? a : b;
/*      */   }
/*      */   
/*      */   private static int peg(int n)
/*      */   {
/* 2183 */     return n > 255 ? 255 : n < 0 ? 0 : n;
/*      */   }
/*      */   
/*      */   private static int mix(int a, int b, int f) {
/* 2187 */     return a + ((b - a) * f >> 8);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_blend(int a, int b)
/*      */   {
/* 2198 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2200 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2201 */       mix(a & 0xFF0000, b & 0xFF0000, f) & 0xFF0000 | 
/* 2202 */       mix(a & 0xFF00, b & 0xFF00, f) & 0xFF00 | 
/* 2203 */       mix(a & 0xFF, b & 0xFF, f);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_add_pin(int a, int b)
/*      */   {
/* 2211 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2213 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2214 */       low((a & 0xFF0000) + 
/* 2215 */       ((b & 0xFF0000) >> 8) * f, 16711680) & 0xFF0000 | 
/* 2216 */       low((a & 0xFF00) + 
/* 2217 */       ((b & 0xFF00) >> 8) * f, 65280) & 0xFF00 | 
/* 2218 */       low((a & 0xFF) + (
/* 2219 */       (b & 0xFF) * f >> 8), 255);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_sub_pin(int a, int b)
/*      */   {
/* 2227 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2229 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2230 */       high((a & 0xFF0000) - ((b & 0xFF0000) >> 8) * f, 
/* 2231 */       65280) & 0xFF0000 | 
/* 2232 */       high((a & 0xFF00) - ((b & 0xFF00) >> 8) * f, 
/* 2233 */       255) & 0xFF00 | 
/* 2234 */       high((a & 0xFF) - ((b & 0xFF) * f >> 8), 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_lightest(int a, int b)
/*      */   {
/* 2242 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2244 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2245 */       high(a & 0xFF0000, ((b & 0xFF0000) >> 8) * f) & 0xFF0000 | 
/* 2246 */       high(a & 0xFF00, ((b & 0xFF00) >> 8) * f) & 0xFF00 | 
/* 2247 */       high(a & 0xFF, (b & 0xFF) * f >> 8);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_darkest(int a, int b)
/*      */   {
/* 2255 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2257 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2258 */       mix(a & 0xFF0000, 
/* 2259 */       low(a & 0xFF0000, 
/* 2260 */       ((b & 0xFF0000) >> 8) * f), f) & 0xFF0000 | 
/* 2261 */       mix(a & 0xFF00, 
/* 2262 */       low(a & 0xFF00, 
/* 2263 */       ((b & 0xFF00) >> 8) * f), f) & 0xFF00 | 
/* 2264 */       mix(a & 0xFF, 
/* 2265 */       low(a & 0xFF, 
/* 2266 */       (b & 0xFF) * f >> 8), f);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_difference(int a, int b)
/*      */   {
/* 2276 */     int f = (b & 0xFF000000) >>> 24;
/* 2277 */     int ar = (a & 0xFF0000) >> 16;
/* 2278 */     int ag = (a & 0xFF00) >> 8;
/* 2279 */     int ab = a & 0xFF;
/* 2280 */     int br = (b & 0xFF0000) >> 16;
/* 2281 */     int bg = (b & 0xFF00) >> 8;
/* 2282 */     int bb = b & 0xFF;
/*      */     
/* 2284 */     int cr = ar > br ? ar - br : br - ar;
/* 2285 */     int cg = ag > bg ? ag - bg : bg - ag;
/* 2286 */     int cb = ab > bb ? ab - bb : bb - ab;
/*      */     
/* 2288 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2289 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2290 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2291 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_exclusion(int a, int b)
/*      */   {
/* 2302 */     int f = (b & 0xFF000000) >>> 24;
/* 2303 */     int ar = (a & 0xFF0000) >> 16;
/* 2304 */     int ag = (a & 0xFF00) >> 8;
/* 2305 */     int ab = a & 0xFF;
/* 2306 */     int br = (b & 0xFF0000) >> 16;
/* 2307 */     int bg = (b & 0xFF00) >> 8;
/* 2308 */     int bb = b & 0xFF;
/*      */     
/* 2310 */     int cr = ar + br - (ar * br >> 7);
/* 2311 */     int cg = ag + bg - (ag * bg >> 7);
/* 2312 */     int cb = ab + bb - (ab * bb >> 7);
/*      */     
/* 2314 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2315 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2316 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2317 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_multiply(int a, int b)
/*      */   {
/* 2327 */     int f = (b & 0xFF000000) >>> 24;
/* 2328 */     int ar = (a & 0xFF0000) >> 16;
/* 2329 */     int ag = (a & 0xFF00) >> 8;
/* 2330 */     int ab = a & 0xFF;
/* 2331 */     int br = (b & 0xFF0000) >> 16;
/* 2332 */     int bg = (b & 0xFF00) >> 8;
/* 2333 */     int bb = b & 0xFF;
/*      */     
/* 2335 */     int cr = ar * br >> 8;
/* 2336 */     int cg = ag * bg >> 8;
/* 2337 */     int cb = ab * bb >> 8;
/*      */     
/* 2339 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2340 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2341 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2342 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_screen(int a, int b)
/*      */   {
/* 2352 */     int f = (b & 0xFF000000) >>> 24;
/* 2353 */     int ar = (a & 0xFF0000) >> 16;
/* 2354 */     int ag = (a & 0xFF00) >> 8;
/* 2355 */     int ab = a & 0xFF;
/* 2356 */     int br = (b & 0xFF0000) >> 16;
/* 2357 */     int bg = (b & 0xFF00) >> 8;
/* 2358 */     int bb = b & 0xFF;
/*      */     
/* 2360 */     int cr = 255 - ((255 - ar) * (255 - br) >> 8);
/* 2361 */     int cg = 255 - ((255 - ag) * (255 - bg) >> 8);
/* 2362 */     int cb = 255 - ((255 - ab) * (255 - bb) >> 8);
/*      */     
/* 2364 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2365 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2366 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2367 */       peg(ab + ((cb - ab) * f >> 8));
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
/*      */   private static int blend_overlay(int a, int b)
/*      */   {
/* 2380 */     int f = (b & 0xFF000000) >>> 24;
/* 2381 */     int ar = (a & 0xFF0000) >> 16;
/* 2382 */     int ag = (a & 0xFF00) >> 8;
/* 2383 */     int ab = a & 0xFF;
/* 2384 */     int br = (b & 0xFF0000) >> 16;
/* 2385 */     int bg = (b & 0xFF00) >> 8;
/* 2386 */     int bb = b & 0xFF;
/*      */     
/* 2388 */     int cr = ar < 128 ? ar * br >> 7 : 255 - ((255 - ar) * (255 - br) >> 7);
/* 2389 */     int cg = ag < 128 ? ag * bg >> 7 : 255 - ((255 - ag) * (255 - bg) >> 7);
/* 2390 */     int cb = ab < 128 ? ab * bb >> 7 : 255 - ((255 - ab) * (255 - bb) >> 7);
/*      */     
/* 2392 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2393 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2394 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2395 */       peg(ab + ((cb - ab) * f >> 8));
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
/*      */   private static int blend_hard_light(int a, int b)
/*      */   {
/* 2408 */     int f = (b & 0xFF000000) >>> 24;
/* 2409 */     int ar = (a & 0xFF0000) >> 16;
/* 2410 */     int ag = (a & 0xFF00) >> 8;
/* 2411 */     int ab = a & 0xFF;
/* 2412 */     int br = (b & 0xFF0000) >> 16;
/* 2413 */     int bg = (b & 0xFF00) >> 8;
/* 2414 */     int bb = b & 0xFF;
/*      */     
/* 2416 */     int cr = br < 128 ? ar * br >> 7 : 255 - ((255 - ar) * (255 - br) >> 7);
/* 2417 */     int cg = bg < 128 ? ag * bg >> 7 : 255 - ((255 - ag) * (255 - bg) >> 7);
/* 2418 */     int cb = bb < 128 ? ab * bb >> 7 : 255 - ((255 - ab) * (255 - bb) >> 7);
/*      */     
/* 2420 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2421 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2422 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2423 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_soft_light(int a, int b)
/*      */   {
/* 2433 */     int f = (b & 0xFF000000) >>> 24;
/* 2434 */     int ar = (a & 0xFF0000) >> 16;
/* 2435 */     int ag = (a & 0xFF00) >> 8;
/* 2436 */     int ab = a & 0xFF;
/* 2437 */     int br = (b & 0xFF0000) >> 16;
/* 2438 */     int bg = (b & 0xFF00) >> 8;
/* 2439 */     int bb = b & 0xFF;
/*      */     
/* 2441 */     int cr = (ar * br >> 7) + (ar * ar >> 8) - (ar * ar * br >> 15);
/* 2442 */     int cg = (ag * bg >> 7) + (ag * ag >> 8) - (ag * ag * bg >> 15);
/* 2443 */     int cb = (ab * bb >> 7) + (ab * ab >> 8) - (ab * ab * bb >> 15);
/*      */     
/* 2445 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2446 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2447 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2448 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_dodge(int a, int b)
/*      */   {
/* 2458 */     int f = (b & 0xFF000000) >>> 24;
/* 2459 */     int ar = (a & 0xFF0000) >> 16;
/* 2460 */     int ag = (a & 0xFF00) >> 8;
/* 2461 */     int ab = a & 0xFF;
/* 2462 */     int br = (b & 0xFF0000) >> 16;
/* 2463 */     int bg = (b & 0xFF00) >> 8;
/* 2464 */     int bb = b & 0xFF;
/*      */     
/* 2466 */     int cr = br == 255 ? 255 : peg((ar << 8) / (255 - br));
/* 2467 */     int cg = bg == 255 ? 255 : peg((ag << 8) / (255 - bg));
/* 2468 */     int cb = bb == 255 ? 255 : peg((ab << 8) / (255 - bb));
/*      */     
/* 2470 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2471 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2472 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2473 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_burn(int a, int b)
/*      */   {
/* 2483 */     int f = (b & 0xFF000000) >>> 24;
/* 2484 */     int ar = (a & 0xFF0000) >> 16;
/* 2485 */     int ag = (a & 0xFF00) >> 8;
/* 2486 */     int ab = a & 0xFF;
/* 2487 */     int br = (b & 0xFF0000) >> 16;
/* 2488 */     int bg = (b & 0xFF00) >> 8;
/* 2489 */     int bb = b & 0xFF;
/*      */     
/* 2491 */     int cr = br == 0 ? 0 : 255 - peg((255 - ar << 8) / br);
/* 2492 */     int cg = bg == 0 ? 0 : 255 - peg((255 - ag << 8) / bg);
/* 2493 */     int cb = bb == 0 ? 0 : 255 - peg((255 - ab << 8) / bb);
/*      */     
/* 2495 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2496 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2497 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2498 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2507 */   static byte[] TIFF_HEADER = {
/* 2508 */     77, 77, 0, 42, 0, 0, 0, 8, 0, 9, 0, -2, 0, 4, 0, 0, 0, 1, 
/* 2509 */     0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 3, 0, 0, 0, 1, 
/* 2510 */     0, 0, 0, 0, 1, 2, 0, 3, 0, 0, 0, 3, 0, 0, 0, 122, 1, 6, 0, 3, 
/* 2511 */     0, 0, 0, 1, 0, 2, 0, 0, 1, 17, 0, 4, 0, 0, 0, 1, 0, 0, 3, 0, 1, 21, 
/* 2512 */     0, 3, 0, 0, 0, 1, 0, 3, 0, 0, 1, 22, 0, 3, 0, 0, 0, 1, 
/* 2513 */     0, 0, 0, 0, 1, 23, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 8, 08 };
/*      */   
/*      */   static final String TIFF_ERROR = "Error: Processing can only read its own TIFF files.";
/*      */   
/*      */   protected String[] saveImageFormats;
/*      */   
/*      */   protected static PImage loadTIFF(byte[] tiff)
/*      */   {
/* 2521 */     if ((tiff[42] != tiff[102]) || 
/* 2522 */       (tiff[43] != tiff[103])) {
/* 2523 */       System.err.println("Error: Processing can only read its own TIFF files.");
/* 2524 */       return null;
/*      */     }
/*      */     
/* 2527 */     int width = 
/* 2528 */       (tiff[30] & 0xFF) << 8 | tiff[31] & 0xFF;
/* 2529 */     int height = 
/* 2530 */       (tiff[42] & 0xFF) << 8 | tiff[43] & 0xFF;
/*      */     
/* 2532 */     int count = 
/* 2533 */       (tiff[114] & 0xFF) << 24 | 
/* 2534 */       (tiff[115] & 0xFF) << 16 | 
/* 2535 */       (tiff[116] & 0xFF) << 8 | 
/* 2536 */       tiff[117] & 0xFF;
/* 2537 */     if (count != width * height * 3) {
/* 2538 */       System.err.println("Error: Processing can only read its own TIFF files. (" + width + ", " + height + ")");
/* 2539 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2543 */     for (int i = 0; i < TIFF_HEADER.length; i++) {
/* 2544 */       if ((i != 30) && (i != 31) && (i != 42) && (i != 43) && 
/* 2545 */         (i != 102) && (i != 103) && 
/* 2546 */         (i != 114) && (i != 115) && (i != 116) && (i != 117))
/*      */       {
/* 2548 */         if (tiff[i] != TIFF_HEADER[i]) {
/* 2549 */           System.err.println("Error: Processing can only read its own TIFF files. (" + i + ")");
/* 2550 */           return null;
/*      */         }
/*      */       }
/*      */     }
/* 2554 */     PImage outgoing = new PImage(width, height, 1);
/* 2555 */     int index = 768;
/* 2556 */     count /= 3;
/* 2557 */     for (int i = 0; i < count; i++) {
/* 2558 */       outgoing.pixels[i] = 
/* 2559 */         (0xFF000000 | 
/* 2560 */         (tiff[(index++)] & 0xFF) << 16 | 
/* 2561 */         (tiff[(index++)] & 0xFF) << 8 | 
/* 2562 */         tiff[(index++)] & 0xFF);
/*      */     }
/* 2564 */     return outgoing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean saveTIFF(OutputStream output)
/*      */   {
/*      */     try
/*      */     {
/* 2577 */       byte[] tiff = new byte['̀'];
/* 2578 */       System.arraycopy(TIFF_HEADER, 0, tiff, 0, TIFF_HEADER.length);
/*      */       
/* 2580 */       tiff[30] = ((byte)(this.width >> 8 & 0xFF));
/* 2581 */       tiff[31] = ((byte)(this.width & 0xFF));
/* 2582 */       tiff[42] = (tiff[102] = (byte)(this.height >> 8 & 0xFF));
/* 2583 */       tiff[43] = (tiff[103] = (byte)(this.height & 0xFF));
/*      */       
/* 2585 */       int count = this.width * this.height * 3;
/* 2586 */       tiff[114] = ((byte)(count >> 24 & 0xFF));
/* 2587 */       tiff[115] = ((byte)(count >> 16 & 0xFF));
/* 2588 */       tiff[116] = ((byte)(count >> 8 & 0xFF));
/* 2589 */       tiff[117] = ((byte)(count & 0xFF));
/*      */       
/*      */ 
/* 2592 */       output.write(tiff);
/*      */       
/* 2594 */       for (int i = 0; i < this.pixels.length; i++) {
/* 2595 */         output.write(this.pixels[i] >> 16 & 0xFF);
/* 2596 */         output.write(this.pixels[i] >> 8 & 0xFF);
/* 2597 */         output.write(this.pixels[i] & 0xFF);
/*      */       }
/* 2599 */       output.flush();
/* 2600 */       return true;
/*      */     }
/*      */     catch (IOException e) {
/* 2603 */       e.printStackTrace();
/*      */     }
/* 2605 */     return false;
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
/*      */   protected boolean saveTGA(OutputStream output)
/*      */   {
/* 2628 */     byte[] header = new byte[18];
/*      */     
/* 2630 */     if (this.format == 4) {
/* 2631 */       header[2] = 11;
/* 2632 */       header[16] = 8;
/* 2633 */       header[17] = 40;
/*      */     }
/* 2635 */     else if (this.format == 1) {
/* 2636 */       header[2] = 10;
/* 2637 */       header[16] = 24;
/* 2638 */       header[17] = 32;
/*      */     }
/* 2640 */     else if (this.format == 2) {
/* 2641 */       header[2] = 10;
/* 2642 */       header[16] = 32;
/* 2643 */       header[17] = 40;
/*      */     }
/*      */     else {
/* 2646 */       throw new RuntimeException("Image format not recognized inside save()");
/*      */     }
/*      */     
/* 2649 */     header[12] = ((byte)(this.width & 0xFF));
/* 2650 */     header[13] = ((byte)(this.width >> 8));
/* 2651 */     header[14] = ((byte)(this.height & 0xFF));
/* 2652 */     header[15] = ((byte)(this.height >> 8));
/*      */     try
/*      */     {
/* 2655 */       output.write(header);
/*      */       
/* 2657 */       int maxLen = this.height * this.width;
/* 2658 */       int index = 0;
/*      */       
/* 2660 */       int[] currChunk = new int[''];
/*      */       
/*      */ 
/*      */ 
/* 2664 */       if (this.format == 4) {
/* 2665 */         while (index < maxLen) {
/* 2666 */           isRLE = false;
/* 2667 */           rle = 1;
/* 2668 */           currChunk[0] = (col = this.pixels[index] & 0xFF);
/* 2669 */           while (index + rle < maxLen) {
/* 2670 */             if ((col != (this.pixels[(index + rle)] & 0xFF)) || (rle == 128)) {
/* 2671 */               isRLE = rle > 1;
/* 2672 */               break;
/*      */             }
/* 2674 */             rle++;
/*      */           }
/* 2676 */           if (isRLE) {
/* 2677 */             output.write(0x80 | rle - 1);
/* 2678 */             output.write(col);
/*      */           }
/*      */           else {
/* 2681 */             rle = 1;
/* 2682 */             while (index + rle < maxLen) {
/* 2683 */               cscan = this.pixels[(index + rle)] & 0xFF;
/* 2684 */               if (((col != cscan) && (rle < 128)) || (rle < 3)) {
/* 2685 */                 currChunk[rle] = (col = cscan);
/*      */               } else {
/* 2687 */                 if (col != cscan) break; rle -= 2;
/* 2688 */                 break;
/*      */               }
/* 2690 */               rle++;
/*      */             }
/* 2692 */             output.write(rle - 1);
/* 2693 */             for (i = 0; i < rle; i++) output.write(currChunk[i]);
/*      */           }
/* 2695 */           index += rle;
/*      */         }
/*      */       } else {
/* 2698 */         while (index < maxLen) { boolean isRLE;
/* 2699 */           int rle; int col; int cscan; int i; boolean isRLE = false;
/* 2700 */           int col; currChunk[0] = (col = this.pixels[index]);
/* 2701 */           int rle = 1;
/*      */           
/*      */ 
/* 2704 */           while (index + rle < maxLen) {
/* 2705 */             if ((col != this.pixels[(index + rle)]) || (rle == 128)) {
/* 2706 */               isRLE = rle > 1;
/* 2707 */               break;
/*      */             }
/* 2709 */             rle++;
/*      */           }
/* 2711 */           if (isRLE) {
/* 2712 */             output.write(0x80 | rle - 1);
/* 2713 */             output.write(col & 0xFF);
/* 2714 */             output.write(col >> 8 & 0xFF);
/* 2715 */             output.write(col >> 16 & 0xFF);
/* 2716 */             if (this.format == 2) output.write(col >>> 24 & 0xFF);
/*      */           }
/*      */           else {
/* 2719 */             rle = 1;
/* 2720 */             while (index + rle < maxLen) {
/* 2721 */               if (((col != this.pixels[(index + rle)]) && (rle < 128)) || (rle < 3)) {
/* 2722 */                 currChunk[rle] = (col = this.pixels[(index + rle)]);
/*      */               }
/*      */               else
/*      */               {
/* 2726 */                 if (col != this.pixels[(index + rle)]) break; rle -= 2;
/* 2727 */                 break;
/*      */               }
/* 2729 */               rle++;
/*      */             }
/*      */             
/* 2732 */             output.write(rle - 1);
/* 2733 */             if (this.format == 2) {
/* 2734 */               for (int i = 0; i < rle; i++) {
/* 2735 */                 col = currChunk[i];
/* 2736 */                 output.write(col & 0xFF);
/* 2737 */                 output.write(col >> 8 & 0xFF);
/* 2738 */                 output.write(col >> 16 & 0xFF);
/* 2739 */                 output.write(col >>> 24 & 0xFF);
/*      */               }
/*      */             } else {
/* 2742 */               for (int i = 0; i < rle; i++) {
/* 2743 */                 col = currChunk[i];
/* 2744 */                 output.write(col & 0xFF);
/* 2745 */                 output.write(col >> 8 & 0xFF);
/* 2746 */                 output.write(col >> 16 & 0xFF);
/*      */               }
/*      */             }
/*      */           }
/* 2750 */           index += rle;
/*      */         }
/*      */       }
/* 2753 */       output.flush();
/* 2754 */       return true;
/*      */     }
/*      */     catch (IOException e) {
/* 2757 */       e.printStackTrace(); }
/* 2758 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean saveImageIO(String path)
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/* 2771 */       int outputFormat = this.format == 2 ? 
/* 2772 */         2 : 1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2777 */       String lower = path.toLowerCase();
/* 2778 */       if ((lower.endsWith("bmp")) || (lower.endsWith("jpg")) || (lower.endsWith("jpeg"))) {
/* 2779 */         outputFormat = 1;
/*      */       }
/*      */       
/* 2782 */       BufferedImage bimage = new BufferedImage(this.width, this.height, outputFormat);
/* 2783 */       bimage.setRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
/*      */       
/* 2785 */       File file = new File(path);
/* 2786 */       String extension = path.substring(path.lastIndexOf('.') + 1);
/*      */       
/* 2788 */       return ImageIO.write(bimage, extension, file);
/*      */     }
/*      */     catch (Exception e) {
/* 2791 */       e.printStackTrace();
/* 2792 */       throw new IOException("image save failed.");
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
/*      */   public void save(String filename)
/*      */   {
/* 2839 */     boolean success = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2844 */     if (this.parent != null)
/*      */     {
/* 2846 */       filename = this.parent.savePath(filename);
/*      */     } else {
/* 2848 */       String msg = "PImage.save() requires an absolute path. Use createImage(), or pass savePath() to save().";
/*      */       
/* 2850 */       PGraphics.showException(msg);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2855 */     loadPixels();
/*      */     try
/*      */     {
/* 2858 */       OutputStream os = null;
/*      */       
/* 2860 */       if (this.saveImageFormats == null) {
/* 2861 */         this.saveImageFormats = ImageIO.getWriterFormatNames();
/*      */       }
/* 2863 */       if (this.saveImageFormats != null) {
/* 2864 */         for (int i = 0; i < this.saveImageFormats.length; i++) {
/* 2865 */           if (filename.endsWith("." + this.saveImageFormats[i])) {
/* 2866 */             if (!saveImageIO(filename)) {
/* 2867 */               throw new RuntimeException("Error while saving image.");
/*      */             }
/* 2869 */             return;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2874 */       if (filename.toLowerCase().endsWith(".tga")) {
/* 2875 */         os = new BufferedOutputStream(new FileOutputStream(filename), 32768);
/* 2876 */         success = saveTGA(os);
/*      */       }
/*      */       else {
/* 2879 */         if ((!filename.toLowerCase().endsWith(".tif")) && 
/* 2880 */           (!filename.toLowerCase().endsWith(".tiff")))
/*      */         {
/* 2882 */           filename = filename + ".tif";
/*      */         }
/* 2884 */         os = new BufferedOutputStream(new FileOutputStream(filename), 32768);
/* 2885 */         success = saveTIFF(os);
/*      */       }
/* 2887 */       os.flush();
/* 2888 */       os.close();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 2892 */       e.printStackTrace();
/* 2893 */       success = false;
/*      */     }
/* 2895 */     if (!success) {
/* 2896 */       throw new RuntimeException("Error while saving image.");
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PImage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */