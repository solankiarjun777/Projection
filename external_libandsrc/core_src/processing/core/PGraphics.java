/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Image;
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PGraphics
/*      */   extends PImage
/*      */   implements PConstants
/*      */ {
/*      */   protected int width1;
/*      */   protected int height1;
/*      */   public int pixelCount;
/*  142 */   public boolean smooth = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean settingsInited;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PGraphics raw;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String path;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean primarySurface;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  179 */   protected boolean[] hints = new boolean[10];
/*      */   
/*      */ 
/*      */   public int colorMode;
/*      */   
/*      */ 
/*      */   public float colorModeX;
/*      */   
/*      */ 
/*      */   public float colorModeY;
/*      */   
/*      */ 
/*      */   public float colorModeZ;
/*      */   
/*      */ 
/*      */   public float colorModeA;
/*      */   
/*      */ 
/*      */   boolean colorModeScale;
/*      */   
/*      */ 
/*      */   boolean colorModeDefault;
/*      */   
/*      */ 
/*      */   public boolean tint;
/*      */   
/*      */ 
/*      */   public int tintColor;
/*      */   
/*      */ 
/*      */   protected boolean tintAlpha;
/*      */   
/*      */ 
/*      */   protected float tintR;
/*      */   
/*      */ 
/*      */   protected float tintG;
/*      */   
/*      */ 
/*      */   protected float tintB;
/*      */   
/*      */ 
/*      */   protected float tintA;
/*      */   
/*      */ 
/*      */   protected int tintRi;
/*      */   
/*      */ 
/*      */   protected int tintGi;
/*      */   
/*      */ 
/*      */   protected int tintBi;
/*      */   
/*      */ 
/*      */   protected int tintAi;
/*      */   
/*      */ 
/*      */   public boolean fill;
/*      */   
/*      */ 
/*  239 */   public int fillColor = -1;
/*      */   
/*      */   protected boolean fillAlpha;
/*      */   
/*      */   protected float fillR;
/*      */   
/*      */   protected float fillG;
/*      */   protected float fillB;
/*      */   protected float fillA;
/*      */   protected int fillRi;
/*      */   protected int fillGi;
/*      */   protected int fillBi;
/*      */   protected int fillAi;
/*      */   public boolean stroke;
/*  253 */   public int strokeColor = -16777216;
/*      */   
/*      */   protected boolean strokeAlpha;
/*      */   
/*      */   protected float strokeR;
/*      */   
/*      */   protected float strokeG;
/*      */   
/*      */   protected float strokeB;
/*      */   
/*      */   protected float strokeA;
/*      */   
/*      */   protected int strokeRi;
/*      */   protected int strokeGi;
/*      */   protected int strokeBi;
/*      */   protected int strokeAi;
/*      */   protected static final float DEFAULT_STROKE_WEIGHT = 1.0F;
/*      */   protected static final int DEFAULT_STROKE_JOIN = 8;
/*      */   protected static final int DEFAULT_STROKE_CAP = 2;
/*  272 */   public float strokeWeight = 1.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  279 */   public int strokeJoin = 8;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  286 */   public int strokeCap = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int rectMode;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int ellipseMode;
/*      */   
/*      */ 
/*      */ 
/*      */   public int shapeMode;
/*      */   
/*      */ 
/*      */ 
/*  304 */   public int imageMode = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PFont textFont;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  314 */   public int textAlign = 37;
/*      */   
/*      */ 
/*  317 */   public int textAlignY = 0;
/*      */   
/*      */ 
/*  320 */   public int textMode = 4;
/*      */   
/*      */   public float textSize;
/*      */   
/*      */   public float textLeading;
/*      */   
/*      */   public float ambientR;
/*      */   
/*      */   public float ambientG;
/*      */   
/*      */   public float ambientB;
/*      */   
/*      */   public float specularR;
/*      */   
/*      */   public float specularG;
/*      */   
/*      */   public float specularB;
/*      */   
/*      */   public float emissiveR;
/*      */   
/*      */   public float emissiveG;
/*      */   
/*      */   public float emissiveB;
/*      */   public float shininess;
/*      */   static final int STYLE_STACK_DEPTH = 64;
/*  345 */   PStyle[] styleStack = new PStyle[64];
/*      */   
/*      */ 
/*      */ 
/*      */   int styleStackDepth;
/*      */   
/*      */ 
/*      */ 
/*  353 */   public int backgroundColor = -3355444;
/*      */   
/*      */   protected boolean backgroundAlpha;
/*      */   
/*      */   protected float backgroundR;
/*      */   
/*      */   protected float backgroundG;
/*      */   
/*      */   protected float backgroundB;
/*      */   
/*      */   protected float backgroundA;
/*      */   
/*      */   protected int backgroundRi;
/*      */   
/*      */   protected int backgroundGi;
/*      */   
/*      */   protected int backgroundBi;
/*      */   
/*      */   protected int backgroundAi;
/*      */   
/*      */   static final int MATRIX_STACK_DEPTH = 32;
/*      */   
/*      */   public Image image;
/*      */   
/*      */   protected float calcR;
/*      */   
/*      */   protected float calcG;
/*      */   
/*      */   protected float calcB;
/*      */   
/*      */   protected float calcA;
/*      */   
/*      */   protected int calcRi;
/*      */   
/*      */   protected int calcGi;
/*      */   
/*      */   protected int calcBi;
/*      */   
/*      */   protected int calcAi;
/*      */   
/*      */   protected int calcColor;
/*      */   
/*      */   protected boolean calcAlpha;
/*      */   
/*      */   int cacheHsbKey;
/*  398 */   float[] cacheHsbValue = new float[3];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int shape;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int DEFAULT_VERTICES = 512;
/*      */   
/*      */ 
/*      */ 
/*  411 */   protected float[][] vertices = new float['Ȁ'][37];
/*      */   
/*      */ 
/*      */   protected int vertexCount;
/*      */   
/*  416 */   protected boolean bezierInited = false;
/*  417 */   public int bezierDetail = 20;
/*      */   
/*      */ 
/*      */ 
/*  421 */   protected PMatrix3D bezierBasisMatrix = new PMatrix3D(-1.0F, 3.0F, -3.0F, 1.0F, 
/*  422 */     3.0F, -6.0F, 3.0F, 0.0F, 
/*  423 */     -3.0F, 3.0F, 0.0F, 0.0F, 
/*  424 */     1.0F, 0.0F, 0.0F, 0.0F);
/*      */   
/*      */ 
/*      */ 
/*      */   protected PMatrix3D bezierDrawMatrix;
/*      */   
/*      */ 
/*  431 */   protected boolean curveInited = false;
/*  432 */   protected int curveDetail = 20;
/*  433 */   public float curveTightness = 0.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PMatrix3D curveBasisMatrix;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PMatrix3D curveDrawMatrix;
/*      */   
/*      */ 
/*      */ 
/*      */   protected PMatrix3D bezierBasisInverse;
/*      */   
/*      */ 
/*      */ 
/*      */   protected PMatrix3D curveToBezierMatrix;
/*      */   
/*      */ 
/*      */ 
/*      */   protected float[][] curveVertices;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int curveVertexCount;
/*      */   
/*      */ 
/*      */ 
/*  463 */   protected static final float[] sinLUT = new float['ː'];
/*  464 */   protected static final float[] cosLUT = new float['ː'];
/*  465 */   static { for (int i = 0; i < 720; i++) {
/*  466 */       sinLUT[i] = ((float)Math.sin(i * 0.017453292F * 0.5F));
/*  467 */       cosLUT[i] = ((float)Math.cos(i * 0.017453292F * 0.5F));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final float SINCOS_PRECISION = 0.5F;
/*      */   
/*      */ 
/*      */   protected static final int SINCOS_LENGTH = 720;
/*      */   
/*      */ 
/*      */   protected float textX;
/*      */   
/*      */ 
/*      */   protected float textY;
/*      */   
/*      */   protected float textZ;
/*      */   
/*  486 */   protected char[] textBuffer = new char[' '];
/*  487 */   protected char[] textWidthBuffer = new char[' '];
/*      */   
/*      */   protected int textBreakCount;
/*      */   
/*      */   protected int[] textBreakStart;
/*      */   
/*      */   protected int[] textBreakStop;
/*      */   
/*  495 */   public boolean edge = true;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NORMAL_MODE_AUTO = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NORMAL_MODE_SHAPE = 1;
/*      */   
/*      */ 
/*      */   protected static final int NORMAL_MODE_VERTEX = 2;
/*      */   
/*      */ 
/*      */   protected int normalMode;
/*      */   
/*      */ 
/*      */   protected boolean autoNormal;
/*      */   
/*      */ 
/*      */   public float normalX;
/*      */   
/*      */ 
/*      */   public float normalY;
/*      */   
/*      */ 
/*      */   public float normalZ;
/*      */   
/*      */ 
/*      */   public int textureMode;
/*      */   
/*      */ 
/*      */   public float textureU;
/*      */   
/*      */ 
/*      */   public float textureV;
/*      */   
/*      */ 
/*      */   public PImage textureImage;
/*      */   
/*      */ 
/*      */   float[] sphereX;
/*      */   
/*      */ 
/*      */   float[] sphereY;
/*      */   
/*      */ 
/*      */   float[] sphereZ;
/*      */   
/*      */ 
/*  545 */   public int sphereDetailU = 0;
/*      */   
/*  547 */   public int sphereDetailV = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static float[] lerpColorHSB1;
/*      */   
/*      */ 
/*      */ 
/*      */   static float[] lerpColorHSB2;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static HashMap<String, Object> warnings;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setParent(PApplet parent)
/*      */   {
/*  567 */     this.parent = parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPrimary(boolean primary)
/*      */   {
/*  577 */     this.primarySurface = primary;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  582 */     if (this.primarySurface) {
/*  583 */       this.format = 1;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPath(String path)
/*      */   {
/*  589 */     this.path = path;
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
/*      */   public void setSize(int w, int h)
/*      */   {
/*  605 */     this.width = w;
/*  606 */     this.height = h;
/*  607 */     this.width1 = (this.width - 1);
/*  608 */     this.height1 = (this.height - 1);
/*      */     
/*  610 */     allocate();
/*  611 */     reapplySettings();
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
/*      */   protected void allocate() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void dispose() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean canDraw()
/*      */   {
/*  643 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void beginDraw() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void endDraw() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void flush() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkSettings()
/*      */   {
/*  681 */     if (!this.settingsInited) { defaultSettings();
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
/*      */   protected void defaultSettings()
/*      */   {
/*  697 */     noSmooth();
/*      */     
/*  699 */     colorMode(1, 255.0F);
/*  700 */     fill(255);
/*  701 */     stroke(0);
/*      */     
/*      */ 
/*      */ 
/*  705 */     strokeWeight(1.0F);
/*  706 */     strokeJoin(8);
/*  707 */     strokeCap(2);
/*      */     
/*      */ 
/*  710 */     this.shape = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  715 */     rectMode(0);
/*  716 */     ellipseMode(3);
/*      */     
/*  718 */     this.autoNormal = true;
/*      */     
/*      */ 
/*  721 */     this.textFont = null;
/*  722 */     this.textSize = 12.0F;
/*  723 */     this.textLeading = 14.0F;
/*  724 */     this.textAlign = 37;
/*  725 */     this.textMode = 4;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  733 */     if (this.primarySurface)
/*      */     {
/*  735 */       background(this.backgroundColor);
/*      */     }
/*      */     
/*  738 */     this.settingsInited = true;
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
/*      */   protected void reapplySettings()
/*      */   {
/*  756 */     if (!this.settingsInited) { return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  761 */     colorMode(this.colorMode, this.colorModeX, this.colorModeY, this.colorModeZ);
/*  762 */     if (this.fill)
/*      */     {
/*  764 */       fill(this.fillColor);
/*      */     } else {
/*  766 */       noFill();
/*      */     }
/*  768 */     if (this.stroke) {
/*  769 */       stroke(this.strokeColor);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  774 */       strokeWeight(this.strokeWeight);
/*      */       
/*      */ 
/*  777 */       strokeCap(this.strokeCap);
/*      */       
/*      */ 
/*  780 */       strokeJoin(this.strokeJoin);
/*      */     }
/*      */     else {
/*  783 */       noStroke();
/*      */     }
/*  785 */     if (this.tint) {
/*  786 */       tint(this.tintColor);
/*      */     } else {
/*  788 */       noTint();
/*      */     }
/*  790 */     if (this.smooth) {
/*  791 */       smooth();
/*      */     }
/*      */     else {
/*  794 */       noSmooth();
/*      */     }
/*  796 */     if (this.textFont != null)
/*      */     {
/*      */ 
/*  799 */       float saveLeading = this.textLeading;
/*  800 */       textFont(this.textFont, this.textSize);
/*  801 */       textLeading(saveLeading);
/*      */     }
/*  803 */     textMode(this.textMode);
/*  804 */     textAlign(this.textAlign, this.textAlignY);
/*  805 */     background(this.backgroundColor);
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
/*      */   public void hint(int which)
/*      */   {
/*  836 */     if (which > 0) {
/*  837 */       this.hints[which] = true;
/*      */     } else {
/*  839 */       this.hints[(-which)] = false;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hintEnabled(int which) {
/*  844 */     if (which > 0) {
/*  845 */       return this.hints[which];
/*      */     }
/*  847 */     return this.hints[(-which)];
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
/*      */   public void beginShape()
/*      */   {
/*  860 */     beginShape(20);
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
/*      */   public void beginShape(int kind)
/*      */   {
/*  887 */     this.shape = kind;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void edge(boolean edge)
/*      */   {
/*  896 */     this.edge = edge;
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
/*      */   public void normal(float nx, float ny, float nz)
/*      */   {
/*  916 */     this.normalX = nx;
/*  917 */     this.normalY = ny;
/*  918 */     this.normalZ = nz;
/*      */     
/*      */ 
/*      */ 
/*  922 */     if (this.shape != 0) {
/*  923 */       if (this.normalMode == 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  931 */         this.normalMode = 1;
/*      */       }
/*  933 */       else if (this.normalMode == 1)
/*      */       {
/*  935 */         this.normalMode = 2;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textureMode(int mode)
/*      */   {
/*  946 */     this.textureMode = mode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void texture(PImage image)
/*      */   {
/*  957 */     this.textureImage = image;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void noTexture()
/*      */   {
/*  967 */     this.textureImage = null;
/*      */   }
/*      */   
/*      */   protected void vertexCheck()
/*      */   {
/*  972 */     if (this.vertexCount == this.vertices.length) {
/*  973 */       float[][] temp = new float[this.vertexCount << 1][37];
/*  974 */       System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
/*  975 */       this.vertices = temp;
/*      */     }
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y)
/*      */   {
/*  981 */     vertexCheck();
/*  982 */     float[] vertex = this.vertices[this.vertexCount];
/*      */     
/*  984 */     this.curveVertexCount = 0;
/*      */     
/*  986 */     vertex[0] = x;
/*  987 */     vertex[1] = y;
/*      */     
/*  989 */     vertex[12] = (this.edge ? 1 : 0);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  997 */     boolean textured = this.textureImage != null;
/*  998 */     if ((this.fill) || (textured)) {
/*  999 */       if (this.textureImage == null) {
/* 1000 */         vertex[3] = this.fillR;
/* 1001 */         vertex[4] = this.fillG;
/* 1002 */         vertex[5] = this.fillB;
/* 1003 */         vertex[6] = this.fillA;
/*      */       }
/* 1005 */       else if (this.tint) {
/* 1006 */         vertex[3] = this.tintR;
/* 1007 */         vertex[4] = this.tintG;
/* 1008 */         vertex[5] = this.tintB;
/* 1009 */         vertex[6] = this.tintA;
/*      */       } else {
/* 1011 */         vertex[3] = 1.0F;
/* 1012 */         vertex[4] = 1.0F;
/* 1013 */         vertex[5] = 1.0F;
/* 1014 */         vertex[6] = 1.0F;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1019 */     if (this.stroke) {
/* 1020 */       vertex[13] = this.strokeR;
/* 1021 */       vertex[14] = this.strokeG;
/* 1022 */       vertex[15] = this.strokeB;
/* 1023 */       vertex[16] = this.strokeA;
/* 1024 */       vertex[17] = this.strokeWeight;
/*      */     }
/*      */     
/* 1027 */     if (textured) {
/* 1028 */       vertex[7] = this.textureU;
/* 1029 */       vertex[8] = this.textureV;
/*      */     }
/*      */     
/* 1032 */     if (this.autoNormal) {
/* 1033 */       float norm2 = this.normalX * this.normalX + this.normalY * this.normalY + this.normalZ * this.normalZ;
/* 1034 */       if (norm2 < 1.0E-4F) {
/* 1035 */         vertex[36] = 0.0F;
/*      */       } else {
/* 1037 */         if (Math.abs(norm2 - 1.0F) > 1.0E-4F)
/*      */         {
/* 1039 */           float norm = PApplet.sqrt(norm2);
/* 1040 */           this.normalX /= norm;
/* 1041 */           this.normalY /= norm;
/* 1042 */           this.normalZ /= norm;
/*      */         }
/* 1044 */         vertex[36] = 1.0F;
/*      */       }
/*      */     } else {
/* 1047 */       vertex[36] = 1.0F;
/*      */     }
/*      */     
/* 1050 */     this.vertexCount += 1;
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float z)
/*      */   {
/* 1055 */     vertexCheck();
/* 1056 */     float[] vertex = this.vertices[this.vertexCount];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1062 */     if ((this.shape == 20) && 
/* 1063 */       (this.vertexCount > 0)) {
/* 1064 */       float[] pvertex = this.vertices[(this.vertexCount - 1)];
/* 1065 */       if ((Math.abs(pvertex[0] - x) < 1.0E-4F) && 
/* 1066 */         (Math.abs(pvertex[1] - y) < 1.0E-4F) && 
/* 1067 */         (Math.abs(pvertex[2] - z) < 1.0E-4F))
/*      */       {
/*      */ 
/* 1070 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1078 */     this.curveVertexCount = 0;
/*      */     
/* 1080 */     vertex[0] = x;
/* 1081 */     vertex[1] = y;
/* 1082 */     vertex[2] = z;
/*      */     
/* 1084 */     vertex[12] = (this.edge ? 1 : 0);
/*      */     
/* 1086 */     boolean textured = this.textureImage != null;
/* 1087 */     if ((this.fill) || (textured)) {
/* 1088 */       if (this.textureImage == null) {
/* 1089 */         vertex[3] = this.fillR;
/* 1090 */         vertex[4] = this.fillG;
/* 1091 */         vertex[5] = this.fillB;
/* 1092 */         vertex[6] = this.fillA;
/*      */       }
/* 1094 */       else if (this.tint) {
/* 1095 */         vertex[3] = this.tintR;
/* 1096 */         vertex[4] = this.tintG;
/* 1097 */         vertex[5] = this.tintB;
/* 1098 */         vertex[6] = this.tintA;
/*      */       } else {
/* 1100 */         vertex[3] = 1.0F;
/* 1101 */         vertex[4] = 1.0F;
/* 1102 */         vertex[5] = 1.0F;
/* 1103 */         vertex[6] = 1.0F;
/*      */       }
/*      */       
/*      */ 
/* 1107 */       vertex[25] = this.ambientR;
/* 1108 */       vertex[26] = this.ambientG;
/* 1109 */       vertex[27] = this.ambientB;
/*      */       
/* 1111 */       vertex[28] = this.specularR;
/* 1112 */       vertex[29] = this.specularG;
/* 1113 */       vertex[30] = this.specularB;
/*      */       
/*      */ 
/* 1116 */       vertex[31] = this.shininess;
/*      */       
/* 1118 */       vertex[32] = this.emissiveR;
/* 1119 */       vertex[33] = this.emissiveG;
/* 1120 */       vertex[34] = this.emissiveB;
/*      */     }
/*      */     
/* 1123 */     if (this.stroke) {
/* 1124 */       vertex[13] = this.strokeR;
/* 1125 */       vertex[14] = this.strokeG;
/* 1126 */       vertex[15] = this.strokeB;
/* 1127 */       vertex[16] = this.strokeA;
/* 1128 */       vertex[17] = this.strokeWeight;
/*      */     }
/*      */     
/* 1131 */     if (textured) {
/* 1132 */       vertex[7] = this.textureU;
/* 1133 */       vertex[8] = this.textureV;
/*      */     }
/*      */     
/* 1136 */     if (this.autoNormal) {
/* 1137 */       float norm2 = this.normalX * this.normalX + this.normalY * this.normalY + this.normalZ * this.normalZ;
/* 1138 */       if (norm2 < 1.0E-4F) {
/* 1139 */         vertex[36] = 0.0F;
/*      */       } else {
/* 1141 */         if (Math.abs(norm2 - 1.0F) > 1.0E-4F)
/*      */         {
/* 1143 */           float norm = PApplet.sqrt(norm2);
/* 1144 */           this.normalX /= norm;
/* 1145 */           this.normalY /= norm;
/* 1146 */           this.normalZ /= norm;
/*      */         }
/* 1148 */         vertex[36] = 1.0F;
/*      */       }
/*      */     } else {
/* 1151 */       vertex[36] = 1.0F;
/*      */     }
/*      */     
/* 1154 */     vertex[9] = this.normalX;
/* 1155 */     vertex[10] = this.normalY;
/* 1156 */     vertex[11] = this.normalZ;
/*      */     
/* 1158 */     vertex[35] = 0.0F;
/*      */     
/* 1160 */     this.vertexCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void vertexFields(float[] v)
/*      */   {
/* 1170 */     vertexCheck();
/* 1171 */     this.curveVertexCount = 0;
/* 1172 */     float[] vertex = this.vertices[this.vertexCount];
/* 1173 */     System.arraycopy(v, 0, vertex, 0, 37);
/* 1174 */     this.vertexCount += 1;
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float u, float v)
/*      */   {
/* 1179 */     vertexTexture(u, v);
/* 1180 */     vertex(x, y);
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float z, float u, float v)
/*      */   {
/* 1185 */     vertexTexture(u, v);
/* 1186 */     vertex(x, y, z);
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
/*      */   protected void vertexTexture(float u, float v)
/*      */   {
/* 1214 */     if (this.textureImage == null) {
/* 1215 */       throw new RuntimeException("You must first call texture() before using u and v coordinates with vertex()");
/*      */     }
/*      */     
/* 1218 */     if (this.textureMode == 2) {
/* 1219 */       u /= this.textureImage.width;
/* 1220 */       v /= this.textureImage.height;
/*      */     }
/*      */     
/* 1223 */     this.textureU = u;
/* 1224 */     this.textureV = v;
/*      */     
/* 1226 */     if (this.textureU < 0.0F) { this.textureU = 0.0F;
/* 1227 */     } else if (this.textureU > 1.0F) { this.textureU = 1.0F;
/*      */     }
/* 1229 */     if (this.textureV < 0.0F) { this.textureV = 0.0F;
/* 1230 */     } else if (this.textureV > 1.0F) { this.textureV = 1.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   public void breakShape()
/*      */   {
/* 1236 */     showWarning("This renderer cannot currently handle concave shapes, or shapes with holes.");
/*      */   }
/*      */   
/*      */ 
/*      */   public void endShape()
/*      */   {
/* 1242 */     endShape(1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void endShape(int mode) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void bezierVertexCheck()
/*      */   {
/* 1257 */     if ((this.shape == 0) || (this.shape != 20)) {
/* 1258 */       throw new RuntimeException("beginShape() or beginShape(POLYGON) must be used before bezierVertex() or quadVertex()");
/*      */     }
/*      */     
/* 1261 */     if (this.vertexCount == 0) {
/* 1262 */       throw new RuntimeException("vertex() must be used at least oncebefore bezierVertex() or quadVertex()");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 1271 */     bezierInitCheck();
/* 1272 */     bezierVertexCheck();
/* 1273 */     PMatrix3D draw = this.bezierDrawMatrix;
/*      */     
/* 1275 */     float[] prev = this.vertices[(this.vertexCount - 1)];
/* 1276 */     float x1 = prev[0];
/* 1277 */     float y1 = prev[1];
/*      */     
/* 1279 */     float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
/* 1280 */     float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
/* 1281 */     float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
/*      */     
/* 1283 */     float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
/* 1284 */     float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
/* 1285 */     float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
/*      */     
/* 1287 */     for (int j = 0; j < this.bezierDetail; j++) {
/* 1288 */       x1 += xplot1;xplot1 += xplot2;xplot2 += xplot3;
/* 1289 */       y1 += yplot1;yplot1 += yplot2;yplot2 += yplot3;
/* 1290 */       vertex(x1, y1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 1298 */     bezierInitCheck();
/* 1299 */     bezierVertexCheck();
/* 1300 */     PMatrix3D draw = this.bezierDrawMatrix;
/*      */     
/* 1302 */     float[] prev = this.vertices[(this.vertexCount - 1)];
/* 1303 */     float x1 = prev[0];
/* 1304 */     float y1 = prev[1];
/* 1305 */     float z1 = prev[2];
/*      */     
/* 1307 */     float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
/* 1308 */     float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
/* 1309 */     float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
/*      */     
/* 1311 */     float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
/* 1312 */     float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
/* 1313 */     float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
/*      */     
/* 1315 */     float zplot1 = draw.m10 * z1 + draw.m11 * z2 + draw.m12 * z3 + draw.m13 * z4;
/* 1316 */     float zplot2 = draw.m20 * z1 + draw.m21 * z2 + draw.m22 * z3 + draw.m23 * z4;
/* 1317 */     float zplot3 = draw.m30 * z1 + draw.m31 * z2 + draw.m32 * z3 + draw.m33 * z4;
/*      */     
/* 1319 */     for (int j = 0; j < this.bezierDetail; j++) {
/* 1320 */       x1 += xplot1;xplot1 += xplot2;xplot2 += xplot3;
/* 1321 */       y1 += yplot1;yplot1 += yplot2;yplot2 += yplot3;
/* 1322 */       z1 += zplot1;zplot1 += zplot2;zplot2 += zplot3;
/* 1323 */       vertex(x1, y1, z1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void quadVertex(float cx, float cy, float x3, float y3)
/*      */   {
/* 1330 */     float[] prev = this.vertices[(this.vertexCount - 1)];
/* 1331 */     float x1 = prev[0];
/* 1332 */     float y1 = prev[1];
/*      */     
/* 1334 */     bezierVertex(x1 + (cx - x1) * 2.0F / 3.0F, y1 + (cy - y1) * 2.0F / 3.0F, 
/* 1335 */       x3 + (cx - x3) * 2.0F / 3.0F, y3 + (cy - y3) * 2.0F / 3.0F, 
/* 1336 */       x3, y3);
/*      */   }
/*      */   
/*      */ 
/*      */   public void quadVertex(float cx, float cy, float cz, float x3, float y3, float z3)
/*      */   {
/* 1342 */     float[] prev = this.vertices[(this.vertexCount - 1)];
/* 1343 */     float x1 = prev[0];
/* 1344 */     float y1 = prev[1];
/* 1345 */     float z1 = prev[2];
/*      */     
/* 1347 */     bezierVertex(x1 + (cx - x1) * 2.0F / 3.0F, y1 + (cy - y1) * 2.0F / 3.0F, z1 + (cz - z1) * 2.0F / 3.0F, 
/* 1348 */       x3 + (cx - x3) * 2.0F / 3.0F, y3 + (cy - y3) * 2.0F / 3.0F, z3 + (cz - z3) * 2.0F / 3.0F, 
/* 1349 */       x3, y3, z3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void curveVertexCheck()
/*      */   {
/* 1358 */     if (this.shape != 20) {
/* 1359 */       throw new RuntimeException("You must use beginShape() or beginShape(POLYGON) before curveVertex()");
/*      */     }
/*      */     
/*      */ 
/* 1363 */     if (this.curveVertices == null) {
/* 1364 */       this.curveVertices = new float[''][3];
/*      */     }
/*      */     
/* 1367 */     if (this.curveVertexCount == this.curveVertices.length)
/*      */     {
/* 1369 */       float[][] temp = new float[this.curveVertexCount << 1][3];
/* 1370 */       System.arraycopy(this.curveVertices, 0, temp, 0, this.curveVertexCount);
/* 1371 */       this.curveVertices = temp;
/*      */     }
/* 1373 */     curveInitCheck();
/*      */   }
/*      */   
/*      */   public void curveVertex(float x, float y)
/*      */   {
/* 1378 */     curveVertexCheck();
/* 1379 */     float[] vertex = this.curveVertices[this.curveVertexCount];
/* 1380 */     vertex[0] = x;
/* 1381 */     vertex[1] = y;
/* 1382 */     this.curveVertexCount += 1;
/*      */     
/*      */ 
/* 1385 */     if (this.curveVertexCount > 3) {
/* 1386 */       curveVertexSegment(this.curveVertices[(this.curveVertexCount - 4)][0], 
/* 1387 */         this.curveVertices[(this.curveVertexCount - 4)][1], 
/* 1388 */         this.curveVertices[(this.curveVertexCount - 3)][0], 
/* 1389 */         this.curveVertices[(this.curveVertexCount - 3)][1], 
/* 1390 */         this.curveVertices[(this.curveVertexCount - 2)][0], 
/* 1391 */         this.curveVertices[(this.curveVertexCount - 2)][1], 
/* 1392 */         this.curveVertices[(this.curveVertexCount - 1)][0], 
/* 1393 */         this.curveVertices[(this.curveVertexCount - 1)][1]);
/*      */     }
/*      */   }
/*      */   
/*      */   public void curveVertex(float x, float y, float z)
/*      */   {
/* 1399 */     curveVertexCheck();
/* 1400 */     float[] vertex = this.curveVertices[this.curveVertexCount];
/* 1401 */     vertex[0] = x;
/* 1402 */     vertex[1] = y;
/* 1403 */     vertex[2] = z;
/* 1404 */     this.curveVertexCount += 1;
/*      */     
/*      */ 
/* 1407 */     if (this.curveVertexCount > 3) {
/* 1408 */       curveVertexSegment(this.curveVertices[(this.curveVertexCount - 4)][0], 
/* 1409 */         this.curveVertices[(this.curveVertexCount - 4)][1], 
/* 1410 */         this.curveVertices[(this.curveVertexCount - 4)][2], 
/* 1411 */         this.curveVertices[(this.curveVertexCount - 3)][0], 
/* 1412 */         this.curveVertices[(this.curveVertexCount - 3)][1], 
/* 1413 */         this.curveVertices[(this.curveVertexCount - 3)][2], 
/* 1414 */         this.curveVertices[(this.curveVertexCount - 2)][0], 
/* 1415 */         this.curveVertices[(this.curveVertexCount - 2)][1], 
/* 1416 */         this.curveVertices[(this.curveVertexCount - 2)][2], 
/* 1417 */         this.curveVertices[(this.curveVertexCount - 1)][0], 
/* 1418 */         this.curveVertices[(this.curveVertexCount - 1)][1], 
/* 1419 */         this.curveVertices[(this.curveVertexCount - 1)][2]);
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
/*      */   protected void curveVertexSegment(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 1432 */     float x0 = x2;
/* 1433 */     float y0 = y2;
/*      */     
/* 1435 */     PMatrix3D draw = this.curveDrawMatrix;
/*      */     
/* 1437 */     float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
/* 1438 */     float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
/* 1439 */     float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
/*      */     
/* 1441 */     float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
/* 1442 */     float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
/* 1443 */     float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
/*      */     
/*      */ 
/* 1446 */     int savedCount = this.curveVertexCount;
/*      */     
/* 1448 */     vertex(x0, y0);
/* 1449 */     for (int j = 0; j < this.curveDetail; j++) {
/* 1450 */       x0 += xplot1;xplot1 += xplot2;xplot2 += xplot3;
/* 1451 */       y0 += yplot1;yplot1 += yplot2;yplot2 += yplot3;
/* 1452 */       vertex(x0, y0);
/*      */     }
/* 1454 */     this.curveVertexCount = savedCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void curveVertexSegment(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 1466 */     float x0 = x2;
/* 1467 */     float y0 = y2;
/* 1468 */     float z0 = z2;
/*      */     
/* 1470 */     PMatrix3D draw = this.curveDrawMatrix;
/*      */     
/* 1472 */     float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
/* 1473 */     float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
/* 1474 */     float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
/*      */     
/* 1476 */     float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
/* 1477 */     float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
/* 1478 */     float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
/*      */     
/*      */ 
/* 1481 */     int savedCount = this.curveVertexCount;
/*      */     
/* 1483 */     float zplot1 = draw.m10 * z1 + draw.m11 * z2 + draw.m12 * z3 + draw.m13 * z4;
/* 1484 */     float zplot2 = draw.m20 * z1 + draw.m21 * z2 + draw.m22 * z3 + draw.m23 * z4;
/* 1485 */     float zplot3 = draw.m30 * z1 + draw.m31 * z2 + draw.m32 * z3 + draw.m33 * z4;
/*      */     
/* 1487 */     vertex(x0, y0, z0);
/* 1488 */     for (int j = 0; j < this.curveDetail; j++) {
/* 1489 */       x0 += xplot1;xplot1 += xplot2;xplot2 += xplot3;
/* 1490 */       y0 += yplot1;yplot1 += yplot2;yplot2 += yplot3;
/* 1491 */       z0 += zplot1;zplot1 += zplot2;zplot2 += zplot3;
/* 1492 */       vertex(x0, y0, z0);
/*      */     }
/* 1494 */     this.curveVertexCount = savedCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void point(float x, float y)
/*      */   {
/* 1505 */     beginShape(2);
/* 1506 */     vertex(x, y);
/* 1507 */     endShape();
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
/*      */   public void point(float x, float y, float z)
/*      */   {
/* 1532 */     beginShape(2);
/* 1533 */     vertex(x, y, z);
/* 1534 */     endShape();
/*      */   }
/*      */   
/*      */   public void line(float x1, float y1, float x2, float y2)
/*      */   {
/* 1539 */     beginShape(4);
/* 1540 */     vertex(x1, y1);
/* 1541 */     vertex(x2, y2);
/* 1542 */     endShape();
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
/*      */   public void line(float x1, float y1, float z1, float x2, float y2, float z2)
/*      */   {
/* 1574 */     beginShape(4);
/* 1575 */     vertex(x1, y1, z1);
/* 1576 */     vertex(x2, y2, z2);
/* 1577 */     endShape();
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
/*      */   public void triangle(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/* 1598 */     beginShape(9);
/* 1599 */     vertex(x1, y1);
/* 1600 */     vertex(x2, y2);
/* 1601 */     vertex(x3, y3);
/* 1602 */     endShape();
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
/*      */   public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 1626 */     beginShape(16);
/* 1627 */     vertex(x1, y1);
/* 1628 */     vertex(x2, y2);
/* 1629 */     vertex(x3, y3);
/* 1630 */     vertex(x4, y4);
/* 1631 */     endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rectMode(int mode)
/*      */   {
/* 1642 */     this.rectMode = mode;
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
/*      */   public void rect(float a, float b, float c, float d)
/*      */   {
/* 1663 */     switch (this.rectMode) {
/*      */     case 1: 
/*      */       break;
/*      */     case 0: 
/* 1667 */       c += a;d += b;
/* 1668 */       break;
/*      */     case 2: 
/* 1670 */       float hradius = c;
/* 1671 */       float vradius = d;
/* 1672 */       c = a + hradius;
/* 1673 */       d = b + vradius;
/* 1674 */       a -= hradius;
/* 1675 */       b -= vradius;
/* 1676 */       break;
/*      */     case 3: 
/* 1678 */       float hradius = c / 2.0F;
/* 1679 */       float vradius = d / 2.0F;
/* 1680 */       c = a + hradius;
/* 1681 */       d = b + vradius;
/* 1682 */       a -= hradius;
/* 1683 */       b -= vradius;
/*      */     }
/*      */     
/* 1686 */     if (a > c) {
/* 1687 */       float temp = a;a = c;c = temp;
/*      */     }
/*      */     
/* 1690 */     if (b > d) {
/* 1691 */       float temp = b;b = d;d = temp;
/*      */     }
/*      */     
/* 1694 */     rectImpl(a, b, c, d);
/*      */   }
/*      */   
/*      */   protected void rectImpl(float x1, float y1, float x2, float y2)
/*      */   {
/* 1699 */     quad(x1, y1, x2, y1, x2, y2, x1, y2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void quadraticVertex(float cpx, float cpy, float x, float y)
/*      */   {
/* 1708 */     float[] prev = this.vertices[(this.vertexCount - 1)];
/* 1709 */     float prevX = prev[0];
/* 1710 */     float prevY = prev[1];
/* 1711 */     float cp1x = prevX + 0.6666667F * (cpx - prevX);
/* 1712 */     float cp1y = prevY + 0.6666667F * (cpy - prevY);
/* 1713 */     float cp2x = cp1x + (x - prevX) / 3.0F;
/* 1714 */     float cp2y = cp1y + (y - prevY) / 3.0F;
/* 1715 */     bezierVertex(cp1x, cp1y, cp2x, cp2y, x, y);
/*      */   }
/*      */   
/*      */ 
/*      */   public void rect(float a, float b, float c, float d, float hr, float vr)
/*      */   {
/* 1721 */     switch (this.rectMode) {
/*      */     case 1: 
/*      */       break;
/*      */     case 0: 
/* 1725 */       c += a;d += b;
/* 1726 */       break;
/*      */     case 2: 
/* 1728 */       float hradius = c;
/* 1729 */       float vradius = d;
/* 1730 */       c = a + hradius;
/* 1731 */       d = b + vradius;
/* 1732 */       a -= hradius;
/* 1733 */       b -= vradius;
/* 1734 */       break;
/*      */     case 3: 
/* 1736 */       float hradius = c / 2.0F;
/* 1737 */       float vradius = d / 2.0F;
/* 1738 */       c = a + hradius;
/* 1739 */       d = b + vradius;
/* 1740 */       a -= hradius;
/* 1741 */       b -= vradius;
/*      */     }
/*      */     
/* 1744 */     if (a > c) {
/* 1745 */       float temp = a;a = c;c = temp;
/*      */     }
/*      */     
/* 1748 */     if (b > d) {
/* 1749 */       float temp = b;b = d;d = temp;
/*      */     }
/*      */     
/* 1752 */     rectImpl(a, b, c, d, hr, vr);
/*      */   }
/*      */   
/*      */   protected void rectImpl(float x1, float y1, float x2, float y2, float hr, float vr)
/*      */   {
/* 1757 */     beginShape();
/*      */     
/* 1759 */     vertex(x2 - hr, y1);
/* 1760 */     quadraticVertex(x2, y1, x2, y1 + vr);
/* 1761 */     vertex(x2, y2 - vr);
/* 1762 */     quadraticVertex(x2, y2, x2 - hr, y2);
/* 1763 */     vertex(x1 + hr, y2);
/* 1764 */     quadraticVertex(x1, y2, x1, y2 - vr);
/* 1765 */     vertex(x1, y1 + vr);
/* 1766 */     quadraticVertex(x1, y1, x1 + hr, y1);
/*      */     
/* 1768 */     endShape(2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void rect(float a, float b, float c, float d, float tl, float tr, float bl, float br)
/*      */   {
/* 1775 */     switch (this.rectMode) {
/*      */     case 1: 
/*      */       break;
/*      */     case 0: 
/* 1779 */       c += a;d += b;
/* 1780 */       break;
/*      */     case 2: 
/* 1782 */       float hradius = c;
/* 1783 */       float vradius = d;
/* 1784 */       c = a + hradius;
/* 1785 */       d = b + vradius;
/* 1786 */       a -= hradius;
/* 1787 */       b -= vradius;
/* 1788 */       break;
/*      */     case 3: 
/* 1790 */       float hradius = c / 2.0F;
/* 1791 */       float vradius = d / 2.0F;
/* 1792 */       c = a + hradius;
/* 1793 */       d = b + vradius;
/* 1794 */       a -= hradius;
/* 1795 */       b -= vradius;
/*      */     }
/*      */     
/* 1798 */     if (a > c) {
/* 1799 */       float temp = a;a = c;c = temp;
/*      */     }
/*      */     
/* 1802 */     if (b > d) {
/* 1803 */       float temp = b;b = d;d = temp;
/*      */     }
/*      */     
/* 1806 */     rectImpl(a, b, c, d, tl, tr, bl, br);
/*      */   }
/*      */   
/*      */ 
/*      */   protected void rectImpl(float x1, float y1, float x2, float y2, float tl, float tr, float bl, float br)
/*      */   {
/* 1812 */     beginShape();
/*      */     
/* 1814 */     if (tr != 0.0F) {
/* 1815 */       vertex(x2 - tr, y1);
/* 1816 */       quadraticVertex(x2, y1, x2, y1 + tr);
/*      */     } else {
/* 1818 */       vertex(x2, y1);
/*      */     }
/* 1820 */     if (br != 0.0F) {
/* 1821 */       vertex(x2, y2 - br);
/* 1822 */       quadraticVertex(x2, y2, x2 - br, y2);
/*      */     } else {
/* 1824 */       vertex(x2, y2);
/*      */     }
/* 1826 */     if (bl != 0.0F) {
/* 1827 */       vertex(x1 + bl, y2);
/* 1828 */       quadraticVertex(x1, y2, x1, y2 - bl);
/*      */     } else {
/* 1830 */       vertex(x1, y2);
/*      */     }
/* 1832 */     if (tl != 0.0F) {
/* 1833 */       vertex(x1, y1 + tl);
/* 1834 */       quadraticVertex(x1, y1, x1 + tl, y1);
/*      */     } else {
/* 1836 */       vertex(x1, y1);
/*      */     }
/*      */     
/* 1839 */     endShape(2);
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
/*      */   public void ellipseMode(int mode)
/*      */   {
/* 1867 */     this.ellipseMode = mode;
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
/*      */   public void ellipse(float a, float b, float c, float d)
/*      */   {
/* 1886 */     float x = a;
/* 1887 */     float y = b;
/* 1888 */     float w = c;
/* 1889 */     float h = d;
/*      */     
/* 1891 */     if (this.ellipseMode == 1) {
/* 1892 */       w = c - a;
/* 1893 */       h = d - b;
/*      */     }
/* 1895 */     else if (this.ellipseMode == 2) {
/* 1896 */       x = a - c;
/* 1897 */       y = b - d;
/* 1898 */       w = c * 2.0F;
/* 1899 */       h = d * 2.0F;
/*      */     }
/* 1901 */     else if (this.ellipseMode == 3) {
/* 1902 */       x = a - c / 2.0F;
/* 1903 */       y = b - d / 2.0F;
/*      */     }
/*      */     
/* 1906 */     if (w < 0.0F) {
/* 1907 */       x += w;
/* 1908 */       w = -w;
/*      */     }
/*      */     
/* 1911 */     if (h < 0.0F) {
/* 1912 */       y += h;
/* 1913 */       h = -h;
/*      */     }
/*      */     
/* 1916 */     ellipseImpl(x, y, w, h);
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
/*      */   protected void ellipseImpl(float x, float y, float w, float h) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void arc(float a, float b, float c, float d, float start, float stop)
/*      */   {
/* 1946 */     float x = a;
/* 1947 */     float y = b;
/* 1948 */     float w = c;
/* 1949 */     float h = d;
/*      */     
/* 1951 */     if (this.ellipseMode == 1) {
/* 1952 */       w = c - a;
/* 1953 */       h = d - b;
/*      */     }
/* 1955 */     else if (this.ellipseMode == 2) {
/* 1956 */       x = a - c;
/* 1957 */       y = b - d;
/* 1958 */       w = c * 2.0F;
/* 1959 */       h = d * 2.0F;
/*      */     }
/* 1961 */     else if (this.ellipseMode == 3) {
/* 1962 */       x = a - c / 2.0F;
/* 1963 */       y = b - d / 2.0F;
/*      */     }
/*      */     
/*      */ 
/* 1967 */     if ((Float.isInfinite(start)) || (Float.isInfinite(stop))) { return;
/*      */     }
/* 1969 */     if (stop < start) { return;
/*      */     }
/*      */     
/* 1972 */     while (start < 0.0F) {
/* 1973 */       start += 6.2831855F;
/* 1974 */       stop += 6.2831855F;
/*      */     }
/*      */     
/* 1977 */     if (stop - start > 6.2831855F) {
/* 1978 */       start = 0.0F;
/* 1979 */       stop = 6.2831855F;
/*      */     }
/*      */     
/* 1982 */     arcImpl(x, y, w, h, start, stop);
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
/*      */   protected void arcImpl(float x, float y, float w, float h, float start, float stop) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void box(float size)
/*      */   {
/* 2007 */     box(size, size, size);
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
/*      */   public void box(float w, float h, float d)
/*      */   {
/* 2023 */     float x1 = -w / 2.0F;float x2 = w / 2.0F;
/* 2024 */     float y1 = -h / 2.0F;float y2 = h / 2.0F;
/* 2025 */     float z1 = -d / 2.0F;float z2 = d / 2.0F;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2030 */     beginShape(16);
/*      */     
/*      */ 
/* 2033 */     normal(0.0F, 0.0F, 1.0F);
/* 2034 */     vertex(x1, y1, z1);
/* 2035 */     vertex(x2, y1, z1);
/* 2036 */     vertex(x2, y2, z1);
/* 2037 */     vertex(x1, y2, z1);
/*      */     
/*      */ 
/* 2040 */     normal(1.0F, 0.0F, 0.0F);
/* 2041 */     vertex(x2, y1, z1);
/* 2042 */     vertex(x2, y1, z2);
/* 2043 */     vertex(x2, y2, z2);
/* 2044 */     vertex(x2, y2, z1);
/*      */     
/*      */ 
/* 2047 */     normal(0.0F, 0.0F, -1.0F);
/* 2048 */     vertex(x2, y1, z2);
/* 2049 */     vertex(x1, y1, z2);
/* 2050 */     vertex(x1, y2, z2);
/* 2051 */     vertex(x2, y2, z2);
/*      */     
/*      */ 
/* 2054 */     normal(-1.0F, 0.0F, 0.0F);
/* 2055 */     vertex(x1, y1, z2);
/* 2056 */     vertex(x1, y1, z1);
/* 2057 */     vertex(x1, y2, z1);
/* 2058 */     vertex(x1, y2, z2);
/*      */     
/*      */ 
/* 2061 */     normal(0.0F, 1.0F, 0.0F);
/* 2062 */     vertex(x1, y1, z2);
/* 2063 */     vertex(x2, y1, z2);
/* 2064 */     vertex(x2, y1, z1);
/* 2065 */     vertex(x1, y1, z1);
/*      */     
/*      */ 
/* 2068 */     normal(0.0F, -1.0F, 0.0F);
/* 2069 */     vertex(x1, y2, z1);
/* 2070 */     vertex(x2, y2, z1);
/* 2071 */     vertex(x2, y2, z2);
/* 2072 */     vertex(x1, y2, z2);
/*      */     
/* 2074 */     endShape();
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
/*      */   public void sphereDetail(int res)
/*      */   {
/* 2088 */     sphereDetail(res, res);
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
/*      */   public void sphereDetail(int ures, int vres)
/*      */   {
/* 2124 */     if (ures < 3) ures = 3;
/* 2125 */     if (vres < 2) vres = 2;
/* 2126 */     if ((ures == this.sphereDetailU) && (vres == this.sphereDetailV)) { return;
/*      */     }
/* 2128 */     float delta = 720.0F / ures;
/* 2129 */     float[] cx = new float[ures];
/* 2130 */     float[] cz = new float[ures];
/*      */     
/* 2132 */     for (int i = 0; i < ures; i++) {
/* 2133 */       cx[i] = cosLUT[((int)(i * delta) % 720)];
/* 2134 */       cz[i] = sinLUT[((int)(i * delta) % 720)];
/*      */     }
/*      */     
/*      */ 
/* 2138 */     int vertCount = ures * (vres - 1) + 2;
/* 2139 */     int currVert = 0;
/*      */     
/*      */ 
/* 2142 */     this.sphereX = new float[vertCount];
/* 2143 */     this.sphereY = new float[vertCount];
/* 2144 */     this.sphereZ = new float[vertCount];
/*      */     
/* 2146 */     float angle_step = 360.0F / vres;
/* 2147 */     float angle = angle_step;
/*      */     
/*      */ 
/* 2150 */     for (int i = 1; i < vres; i++) {
/* 2151 */       float curradius = sinLUT[((int)angle % 720)];
/* 2152 */       float currY = -cosLUT[((int)angle % 720)];
/* 2153 */       for (int j = 0; j < ures; j++) {
/* 2154 */         this.sphereX[currVert] = (cx[j] * curradius);
/* 2155 */         this.sphereY[currVert] = currY;
/* 2156 */         this.sphereZ[(currVert++)] = (cz[j] * curradius);
/*      */       }
/* 2158 */       angle += angle_step;
/*      */     }
/* 2160 */     this.sphereDetailU = ures;
/* 2161 */     this.sphereDetailV = vres;
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
/*      */   public void sphere(float r)
/*      */   {
/* 2193 */     if ((this.sphereDetailU < 3) || (this.sphereDetailV < 2)) {
/* 2194 */       sphereDetail(30);
/*      */     }
/*      */     
/* 2197 */     edge(false);
/*      */     
/*      */ 
/* 2200 */     beginShape(10);
/* 2201 */     for (int i = 0; i < this.sphereDetailU; i++) {
/* 2202 */       normal(0.0F, -1.0F, 0.0F);
/* 2203 */       vertex(0.0F, -r, 0.0F);
/* 2204 */       normal(this.sphereX[i], this.sphereY[i], this.sphereZ[i]);
/* 2205 */       vertex(r * this.sphereX[i], r * this.sphereY[i], r * this.sphereZ[i]);
/*      */     }
/* 2207 */     normal(0.0F, -1.0F, 0.0F);
/* 2208 */     vertex(0.0F, -1.0F, 0.0F);
/* 2209 */     normal(this.sphereX[0], this.sphereY[0], this.sphereZ[0]);
/* 2210 */     vertex(r * this.sphereX[0], r * this.sphereY[0], r * this.sphereZ[0]);
/* 2211 */     endShape();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2216 */     int voff = 0;
/* 2217 */     for (int i = 2; i < this.sphereDetailV; i++) { int v11;
/* 2218 */       int v1 = v11 = voff;
/* 2219 */       voff += this.sphereDetailU;
/* 2220 */       int v2 = voff;
/* 2221 */       beginShape(10);
/* 2222 */       for (int j = 0; j < this.sphereDetailU; j++) {
/* 2223 */         normal(this.sphereX[v1], this.sphereY[v1], this.sphereZ[v1]);
/* 2224 */         vertex(r * this.sphereX[v1], r * this.sphereY[v1], r * this.sphereZ[(v1++)]);
/* 2225 */         normal(this.sphereX[v2], this.sphereY[v2], this.sphereZ[v2]);
/* 2226 */         vertex(r * this.sphereX[v2], r * this.sphereY[v2], r * this.sphereZ[(v2++)]);
/*      */       }
/*      */       
/* 2229 */       v1 = v11;
/* 2230 */       v2 = voff;
/* 2231 */       normal(this.sphereX[v1], this.sphereY[v1], this.sphereZ[v1]);
/* 2232 */       vertex(r * this.sphereX[v1], r * this.sphereY[v1], this.sphereZ[v1]);
/* 2233 */       normal(this.sphereX[v2], this.sphereY[v2], this.sphereZ[v2]);
/* 2234 */       vertex(r * this.sphereX[v2], r * this.sphereY[v2], r * this.sphereZ[v2]);
/* 2235 */       endShape();
/*      */     }
/*      */     
/*      */ 
/* 2239 */     beginShape(10);
/* 2240 */     for (int i = 0; i < this.sphereDetailU; i++) {
/* 2241 */       int v2 = voff + i;
/* 2242 */       normal(this.sphereX[v2], this.sphereY[v2], this.sphereZ[v2]);
/* 2243 */       vertex(r * this.sphereX[v2], r * this.sphereY[v2], r * this.sphereZ[v2]);
/* 2244 */       normal(0.0F, 1.0F, 0.0F);
/* 2245 */       vertex(0.0F, r, 0.0F);
/*      */     }
/* 2247 */     normal(this.sphereX[voff], this.sphereY[voff], this.sphereZ[voff]);
/* 2248 */     vertex(r * this.sphereX[voff], r * this.sphereY[voff], r * this.sphereZ[voff]);
/* 2249 */     normal(0.0F, 1.0F, 0.0F);
/* 2250 */     vertex(0.0F, r, 0.0F);
/* 2251 */     endShape();
/*      */     
/* 2253 */     edge(true);
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
/*      */   public float bezierPoint(float a, float b, float c, float d, float t)
/*      */   {
/* 2306 */     float t1 = 1.0F - t;
/* 2307 */     return a * t1 * t1 * t1 + 3.0F * b * t * t1 * t1 + 3.0F * c * t * t * t1 + d * t * t * t;
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
/*      */   public float bezierTangent(float a, float b, float c, float d, float t)
/*      */   {
/* 2330 */     return 3.0F * t * t * (-a + 3.0F * b - 3.0F * c + d) + 
/* 2331 */       6.0F * t * (a - 2.0F * b + c) + 
/* 2332 */       3.0F * (-a + b);
/*      */   }
/*      */   
/*      */   protected void bezierInitCheck()
/*      */   {
/* 2337 */     if (!this.bezierInited) {
/* 2338 */       bezierInit();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void bezierInit()
/*      */   {
/* 2345 */     bezierDetail(this.bezierDetail);
/* 2346 */     this.bezierInited = true;
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
/*      */   public void bezierDetail(int detail)
/*      */   {
/* 2361 */     this.bezierDetail = detail;
/*      */     
/* 2363 */     if (this.bezierDrawMatrix == null) {
/* 2364 */       this.bezierDrawMatrix = new PMatrix3D();
/*      */     }
/*      */     
/*      */ 
/* 2368 */     splineForward(detail, this.bezierDrawMatrix);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2374 */     this.bezierDrawMatrix.apply(this.bezierBasisMatrix);
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
/*      */   public void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 2431 */     beginShape();
/* 2432 */     vertex(x1, y1);
/* 2433 */     bezierVertex(x2, y2, x3, y3, x4, y4);
/* 2434 */     endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void bezier(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 2442 */     beginShape();
/* 2443 */     vertex(x1, y1, z1);
/* 2444 */     bezierVertex(x2, y2, z2, 
/* 2445 */       x3, y3, z3, 
/* 2446 */       x4, y4, z4);
/* 2447 */     endShape();
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
/*      */   public float curvePoint(float a, float b, float c, float d, float t)
/*      */   {
/* 2476 */     curveInitCheck();
/*      */     
/* 2478 */     float tt = t * t;
/* 2479 */     float ttt = t * tt;
/* 2480 */     PMatrix3D cb = this.curveBasisMatrix;
/*      */     
/*      */ 
/* 2483 */     return a * (ttt * cb.m00 + tt * cb.m10 + t * cb.m20 + cb.m30) + 
/* 2484 */       b * (ttt * cb.m01 + tt * cb.m11 + t * cb.m21 + cb.m31) + 
/* 2485 */       c * (ttt * cb.m02 + tt * cb.m12 + t * cb.m22 + cb.m32) + 
/* 2486 */       d * (ttt * cb.m03 + tt * cb.m13 + t * cb.m23 + cb.m33);
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
/*      */   public float curveTangent(float a, float b, float c, float d, float t)
/*      */   {
/* 2509 */     curveInitCheck();
/*      */     
/* 2511 */     float tt3 = t * t * 3.0F;
/* 2512 */     float t2 = t * 2.0F;
/* 2513 */     PMatrix3D cb = this.curveBasisMatrix;
/*      */     
/*      */ 
/* 2516 */     return a * (tt3 * cb.m00 + t2 * cb.m10 + cb.m20) + 
/* 2517 */       b * (tt3 * cb.m01 + t2 * cb.m11 + cb.m21) + 
/* 2518 */       c * (tt3 * cb.m02 + t2 * cb.m12 + cb.m22) + 
/* 2519 */       d * (tt3 * cb.m03 + t2 * cb.m13 + cb.m23);
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
/*      */   public void curveDetail(int detail)
/*      */   {
/* 2536 */     this.curveDetail = detail;
/* 2537 */     curveInit();
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
/*      */   public void curveTightness(float tightness)
/*      */   {
/* 2559 */     this.curveTightness = tightness;
/* 2560 */     curveInit();
/*      */   }
/*      */   
/*      */   protected void curveInitCheck()
/*      */   {
/* 2565 */     if (!this.curveInited) {
/* 2566 */       curveInit();
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
/*      */   protected void curveInit()
/*      */   {
/* 2584 */     if (this.curveDrawMatrix == null) {
/* 2585 */       this.curveBasisMatrix = new PMatrix3D();
/* 2586 */       this.curveDrawMatrix = new PMatrix3D();
/* 2587 */       this.curveInited = true;
/*      */     }
/*      */     
/* 2590 */     float s = this.curveTightness;
/* 2591 */     this.curveBasisMatrix.set((s - 1.0F) / 2.0F, (s + 3.0F) / 2.0F, (-3.0F - s) / 2.0F, (1.0F - s) / 2.0F, 
/* 2592 */       1.0F - s, (-5.0F - s) / 2.0F, s + 2.0F, (s - 1.0F) / 2.0F, 
/* 2593 */       (s - 1.0F) / 2.0F, 0.0F, (1.0F - s) / 2.0F, 0.0F, 
/* 2594 */       0.0F, 1.0F, 0.0F, 0.0F);
/*      */     
/*      */ 
/* 2597 */     splineForward(this.curveDetail, this.curveDrawMatrix);
/*      */     
/* 2599 */     if (this.bezierBasisInverse == null) {
/* 2600 */       this.bezierBasisInverse = this.bezierBasisMatrix.get();
/* 2601 */       this.bezierBasisInverse.invert();
/* 2602 */       this.curveToBezierMatrix = new PMatrix3D();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2608 */     this.curveToBezierMatrix.set(this.curveBasisMatrix);
/* 2609 */     this.curveToBezierMatrix.preApply(this.bezierBasisInverse);
/*      */     
/*      */ 
/*      */ 
/* 2613 */     this.curveDrawMatrix.apply(this.curveBasisMatrix);
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
/*      */   public void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 2665 */     beginShape();
/* 2666 */     curveVertex(x1, y1);
/* 2667 */     curveVertex(x2, y2);
/* 2668 */     curveVertex(x3, y3);
/* 2669 */     curveVertex(x4, y4);
/* 2670 */     endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void curve(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 2678 */     beginShape();
/* 2679 */     curveVertex(x1, y1, z1);
/* 2680 */     curveVertex(x2, y2, z2);
/* 2681 */     curveVertex(x3, y3, z3);
/* 2682 */     curveVertex(x4, y4, z4);
/* 2683 */     endShape();
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
/*      */   protected void splineForward(int segments, PMatrix3D matrix)
/*      */   {
/* 2703 */     float f = 1.0F / segments;
/* 2704 */     float ff = f * f;
/* 2705 */     float fff = ff * f;
/*      */     
/* 2707 */     matrix.set(0.0F, 0.0F, 0.0F, 1.0F, 
/* 2708 */       fff, ff, f, 0.0F, 
/* 2709 */       6.0F * fff, 2.0F * ff, 0.0F, 0.0F, 
/* 2710 */       6.0F * fff, 0.0F, 0.0F, 0.0F);
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
/*      */   public void smooth()
/*      */   {
/* 2725 */     this.smooth = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void noSmooth()
/*      */   {
/* 2733 */     this.smooth = false;
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
/*      */   public void imageMode(int mode)
/*      */   {
/* 2765 */     if ((mode == 0) || (mode == 1) || (mode == 3)) {
/* 2766 */       this.imageMode = mode;
/*      */     } else {
/* 2768 */       String msg = 
/* 2769 */         "imageMode() only works with CORNER, CORNERS, or CENTER";
/* 2770 */       throw new RuntimeException(msg);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void image(PImage image, float x, float y)
/*      */   {
/* 2778 */     if ((image.width == -1) || (image.height == -1)) { return;
/*      */     }
/* 2780 */     if ((this.imageMode == 0) || (this.imageMode == 1)) {
/* 2781 */       imageImpl(image, 
/* 2782 */         x, y, x + image.width, y + image.height, 
/* 2783 */         0, 0, image.width, image.height);
/*      */     }
/* 2785 */     else if (this.imageMode == 3) {
/* 2786 */       float x1 = x - image.width / 2;
/* 2787 */       float y1 = y - image.height / 2;
/* 2788 */       imageImpl(image, 
/* 2789 */         x1, y1, x1 + image.width, y1 + image.height, 
/* 2790 */         0, 0, image.width, image.height);
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
/*      */   public void image(PImage image, float x, float y, float c, float d)
/*      */   {
/* 2829 */     image(image, x, y, c, d, 0, 0, image.width, image.height);
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
/*      */   public void image(PImage image, float a, float b, float c, float d, int u1, int v1, int u2, int v2)
/*      */   {
/* 2843 */     if ((image.width == -1) || (image.height == -1)) { return;
/*      */     }
/* 2845 */     if (this.imageMode == 0) {
/* 2846 */       if (c < 0.0F) {
/* 2847 */         a += c;c = -c;
/*      */       }
/* 2849 */       if (d < 0.0F) {
/* 2850 */         b += d;d = -d;
/*      */       }
/*      */       
/* 2853 */       imageImpl(image, 
/* 2854 */         a, b, a + c, b + d, 
/* 2855 */         u1, v1, u2, v2);
/*      */     }
/* 2857 */     else if (this.imageMode == 1) {
/* 2858 */       if (c < a) {
/* 2859 */         float temp = a;a = c;c = temp;
/*      */       }
/* 2861 */       if (d < b) {
/* 2862 */         float temp = b;b = d;d = temp;
/*      */       }
/*      */       
/* 2865 */       imageImpl(image, 
/* 2866 */         a, b, c, d, 
/* 2867 */         u1, v1, u2, v2);
/*      */     }
/* 2869 */     else if (this.imageMode == 3)
/*      */     {
/* 2871 */       if (c < 0.0F) c = -c;
/* 2872 */       if (d < 0.0F) d = -d;
/* 2873 */       float x1 = a - c / 2.0F;
/* 2874 */       float y1 = b - d / 2.0F;
/*      */       
/* 2876 */       imageImpl(image, 
/* 2877 */         x1, y1, x1 + c, y1 + d, 
/* 2878 */         u1, v1, u2, v2);
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
/*      */   protected void imageImpl(PImage image, float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2)
/*      */   {
/* 2893 */     boolean savedStroke = this.stroke;
/*      */     
/* 2895 */     int savedTextureMode = this.textureMode;
/*      */     
/* 2897 */     this.stroke = false;
/*      */     
/* 2899 */     this.textureMode = 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2919 */     beginShape(16);
/* 2920 */     texture(image);
/* 2921 */     vertex(x1, y1, u1, v1);
/* 2922 */     vertex(x1, y2, u1, v2);
/* 2923 */     vertex(x2, y2, u2, v2);
/* 2924 */     vertex(x2, y1, u2, v1);
/* 2925 */     endShape();
/*      */     
/* 2927 */     this.stroke = savedStroke;
/*      */     
/* 2929 */     this.textureMode = savedTextureMode;
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
/*      */   public void shapeMode(int mode)
/*      */   {
/* 2965 */     this.shapeMode = mode;
/*      */   }
/*      */   
/*      */   public void shape(PShape shape)
/*      */   {
/* 2970 */     if (shape.isVisible()) {
/* 2971 */       if (this.shapeMode == 3) {
/* 2972 */         pushMatrix();
/* 2973 */         translate(-shape.getWidth() / 2.0F, -shape.getHeight() / 2.0F);
/*      */       }
/*      */       
/* 2976 */       shape.draw(this);
/*      */       
/* 2978 */       if (this.shapeMode == 3) {
/* 2979 */         popMatrix();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void shape(PShape shape, float x, float y)
/*      */   {
/* 2989 */     if (shape.isVisible()) {
/* 2990 */       pushMatrix();
/*      */       
/* 2992 */       if (this.shapeMode == 3) {
/* 2993 */         translate(x - shape.getWidth() / 2.0F, y - shape.getHeight() / 2.0F);
/*      */       }
/* 2995 */       else if ((this.shapeMode == 0) || (this.shapeMode == 1)) {
/* 2996 */         translate(x, y);
/*      */       }
/* 2998 */       shape.draw(this);
/*      */       
/* 3000 */       popMatrix();
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
/*      */   public void shape(PShape shape, float x, float y, float c, float d)
/*      */   {
/* 3035 */     if (shape.isVisible()) {
/* 3036 */       pushMatrix();
/*      */       
/* 3038 */       if (this.shapeMode == 3)
/*      */       {
/* 3040 */         translate(x - c / 2.0F, y - d / 2.0F);
/* 3041 */         scale(c / shape.getWidth(), d / shape.getHeight());
/*      */       }
/* 3043 */       else if (this.shapeMode == 0) {
/* 3044 */         translate(x, y);
/* 3045 */         scale(c / shape.getWidth(), d / shape.getHeight());
/*      */       }
/* 3047 */       else if (this.shapeMode == 1)
/*      */       {
/* 3049 */         c -= x;
/* 3050 */         d -= y;
/*      */         
/* 3052 */         translate(x, y);
/* 3053 */         scale(c / shape.getWidth(), d / shape.getHeight());
/*      */       }
/* 3055 */       shape.draw(this);
/*      */       
/* 3057 */       popMatrix();
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
/*      */   public void textAlign(int align)
/*      */   {
/* 3073 */     textAlign(align, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textAlign(int alignX, int alignY)
/*      */   {
/* 3083 */     this.textAlign = alignX;
/* 3084 */     this.textAlignY = alignY;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textAscent()
/*      */   {
/* 3094 */     if (this.textFont == null) {
/* 3095 */       defaultFontOrDeath("textAscent");
/*      */     }
/* 3097 */     return this.textFont.ascent() * (this.textMode == 256 ? this.textFont.size : this.textSize);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textDescent()
/*      */   {
/* 3107 */     if (this.textFont == null) {
/* 3108 */       defaultFontOrDeath("textDescent");
/*      */     }
/* 3110 */     return this.textFont.descent() * (this.textMode == 256 ? this.textFont.size : this.textSize);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textFont(PFont which)
/*      */   {
/* 3120 */     if (which != null) {
/* 3121 */       this.textFont = which;
/* 3122 */       if (this.hints[3] != 0)
/*      */       {
/* 3124 */         which.findFont();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3150 */       textSize(which.size);
/*      */     }
/*      */     else {
/* 3153 */       throw new RuntimeException("A null PFont was passed to textFont()");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textFont(PFont which, float size)
/*      */   {
/* 3162 */     textFont(which);
/* 3163 */     textSize(size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textLeading(float leading)
/*      */   {
/* 3173 */     this.textLeading = leading;
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
/*      */   public void textMode(int mode)
/*      */   {
/* 3186 */     if ((mode == 37) || (mode == 39)) {
/* 3187 */       showWarning("Since Processing beta, textMode() is now textAlign().");
/* 3188 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3195 */     if (textModeCheck(mode)) {
/* 3196 */       this.textMode = mode;
/*      */     } else {
/* 3198 */       String modeStr = String.valueOf(mode);
/* 3199 */       switch (mode) {
/* 3200 */       case 256:  modeStr = "SCREEN"; break;
/* 3201 */       case 4:  modeStr = "MODEL"; break;
/* 3202 */       case 5:  modeStr = "SHAPE";
/*      */       }
/* 3204 */       showWarning("textMode(" + modeStr + ") is not supported by this renderer.");
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
/*      */   protected boolean textModeCheck(int mode)
/*      */   {
/* 3220 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textSize(float size)
/*      */   {
/* 3228 */     if (this.textFont == null) {
/* 3229 */       defaultFontOrDeath("textSize", size);
/*      */     }
/* 3231 */     this.textSize = size;
/* 3232 */     this.textLeading = ((textAscent() + textDescent()) * 1.275F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public float textWidth(char c)
/*      */   {
/* 3239 */     this.textWidthBuffer[0] = c;
/* 3240 */     return textWidthImpl(this.textWidthBuffer, 0, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textWidth(String str)
/*      */   {
/* 3249 */     if (this.textFont == null) {
/* 3250 */       defaultFontOrDeath("textWidth");
/*      */     }
/*      */     
/* 3253 */     int length = str.length();
/* 3254 */     if (length > this.textWidthBuffer.length) {
/* 3255 */       this.textWidthBuffer = new char[length + 10];
/*      */     }
/* 3257 */     str.getChars(0, length, this.textWidthBuffer, 0);
/*      */     
/* 3259 */     float wide = 0.0F;
/* 3260 */     int index = 0;
/* 3261 */     int start = 0;
/*      */     
/* 3263 */     while (index < length) {
/* 3264 */       if (this.textWidthBuffer[index] == '\n') {
/* 3265 */         wide = Math.max(wide, textWidthImpl(this.textWidthBuffer, start, index));
/* 3266 */         start = index + 1;
/*      */       }
/* 3268 */       index++;
/*      */     }
/* 3270 */     if (start < length) {
/* 3271 */       wide = Math.max(wide, textWidthImpl(this.textWidthBuffer, start, index));
/*      */     }
/* 3273 */     return wide;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textWidth(char[] chars, int start, int length)
/*      */   {
/* 3281 */     return textWidthImpl(chars, start, start + length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected float textWidthImpl(char[] buffer, int start, int stop)
/*      */   {
/* 3292 */     float wide = 0.0F;
/* 3293 */     for (int i = start; i < stop; i++)
/*      */     {
/* 3295 */       wide += this.textFont.width(buffer[i]) * this.textSize;
/*      */     }
/* 3297 */     return wide;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(char c)
/*      */   {
/* 3308 */     text(c, this.textX, this.textY, this.textZ);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(char c, float x, float y)
/*      */   {
/* 3318 */     if (this.textFont == null) {
/* 3319 */       defaultFontOrDeath("text");
/*      */     }
/*      */     
/* 3322 */     if (this.textMode == 256) { loadPixels();
/*      */     }
/* 3324 */     if (this.textAlignY == 3) {
/* 3325 */       y += textAscent() / 2.0F;
/* 3326 */     } else if (this.textAlignY == 101) {
/* 3327 */       y += textAscent();
/* 3328 */     } else if (this.textAlignY == 102) {
/* 3329 */       y -= textDescent();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3334 */     this.textBuffer[0] = c;
/* 3335 */     textLineAlignImpl(this.textBuffer, 0, 1, x, y);
/*      */     
/* 3337 */     if (this.textMode == 256) { updatePixels();
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
/*      */   public void text(char c, float x, float y, float z)
/*      */   {
/* 3350 */     if (z != 0.0F) { translate(0.0F, 0.0F, z);
/*      */     }
/* 3352 */     text(c, x, y);
/* 3353 */     this.textZ = z;
/*      */     
/* 3355 */     if (z != 0.0F) { translate(0.0F, 0.0F, -z);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void text(String str)
/*      */   {
/* 3363 */     text(str, this.textX, this.textY, this.textZ);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(String str, float x, float y)
/*      */   {
/* 3374 */     if (this.textFont == null) {
/* 3375 */       defaultFontOrDeath("text");
/*      */     }
/*      */     
/* 3378 */     if (this.textMode == 256) { loadPixels();
/*      */     }
/* 3380 */     int length = str.length();
/* 3381 */     if (length > this.textBuffer.length) {
/* 3382 */       this.textBuffer = new char[length + 10];
/*      */     }
/* 3384 */     str.getChars(0, length, this.textBuffer, 0);
/* 3385 */     text(this.textBuffer, 0, length, x, y);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(char[] chars, int start, int stop, float x, float y)
/*      */   {
/* 3396 */     float high = 0.0F;
/* 3397 */     for (int i = start; i < stop; i++) {
/* 3398 */       if (chars[i] == '\n') {
/* 3399 */         high += this.textLeading;
/*      */       }
/*      */     }
/* 3402 */     if (this.textAlignY == 3)
/*      */     {
/*      */ 
/*      */ 
/* 3406 */       y += (textAscent() - high) / 2.0F;
/* 3407 */     } else if (this.textAlignY == 101)
/*      */     {
/*      */ 
/* 3410 */       y += textAscent();
/* 3411 */     } else if (this.textAlignY == 102)
/*      */     {
/*      */ 
/* 3414 */       y -= textDescent() + high;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3420 */     int index = 0;
/* 3421 */     while (index < stop) {
/* 3422 */       if (chars[index] == '\n') {
/* 3423 */         textLineAlignImpl(chars, start, index, x, y);
/* 3424 */         start = index + 1;
/* 3425 */         y += this.textLeading;
/*      */       }
/* 3427 */       index++;
/*      */     }
/* 3429 */     if (start < stop) {
/* 3430 */       textLineAlignImpl(chars, start, index, x, y);
/*      */     }
/* 3432 */     if (this.textMode == 256) { updatePixels();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void text(String str, float x, float y, float z)
/*      */   {
/* 3440 */     if (z != 0.0F) { translate(0.0F, 0.0F, z);
/*      */     }
/* 3442 */     text(str, x, y);
/* 3443 */     this.textZ = z;
/*      */     
/* 3445 */     if (z != 0.0F) { translate(0.0F, 0.0F, -z);
/*      */     }
/*      */   }
/*      */   
/*      */   public void text(char[] chars, int start, int stop, float x, float y, float z)
/*      */   {
/* 3451 */     if (z != 0.0F) { translate(0.0F, 0.0F, z);
/*      */     }
/* 3453 */     text(chars, start, stop, x, y);
/* 3454 */     this.textZ = z;
/*      */     
/* 3456 */     if (z != 0.0F) { translate(0.0F, 0.0F, -z);
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
/*      */   public void text(String str, float x1, float y1, float x2, float y2)
/*      */   {
/* 3474 */     if (this.textFont == null) {
/* 3475 */       defaultFontOrDeath("text");
/*      */     }
/*      */     
/* 3478 */     if (this.textMode == 256) { loadPixels();
/*      */     }
/*      */     
/* 3481 */     switch (this.rectMode) {
/*      */     case 0: 
/* 3483 */       x2 += x1;y2 += y1;
/* 3484 */       break;
/*      */     case 2: 
/* 3486 */       float hradius = x2;
/* 3487 */       float vradius = y2;
/* 3488 */       x2 = x1 + hradius;
/* 3489 */       y2 = y1 + vradius;
/* 3490 */       x1 -= hradius;
/* 3491 */       y1 -= vradius;
/* 3492 */       break;
/*      */     case 3: 
/* 3494 */       float hradius = x2 / 2.0F;
/* 3495 */       float vradius = y2 / 2.0F;
/* 3496 */       x2 = x1 + hradius;
/* 3497 */       y2 = y1 + vradius;
/* 3498 */       x1 -= hradius;
/* 3499 */       y1 -= vradius;
/*      */     }
/* 3501 */     if (x2 < x1) {
/* 3502 */       float temp = x1;x1 = x2;x2 = temp;
/*      */     }
/* 3504 */     if (y2 < y1) {
/* 3505 */       float temp = y1;y1 = y2;y2 = temp;
/*      */     }
/*      */     
/*      */ 
/* 3509 */     float boxWidth = x2 - x1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3516 */     float spaceWidth = textWidth(' ');
/*      */     
/* 3518 */     if (this.textBreakStart == null) {
/* 3519 */       this.textBreakStart = new int[20];
/* 3520 */       this.textBreakStop = new int[20];
/*      */     }
/* 3522 */     this.textBreakCount = 0;
/*      */     
/* 3524 */     int length = str.length();
/* 3525 */     if (length + 1 > this.textBuffer.length) {
/* 3526 */       this.textBuffer = new char[length + 1];
/*      */     }
/* 3528 */     str.getChars(0, length, this.textBuffer, 0);
/*      */     
/* 3530 */     this.textBuffer[(length++)] = '\n';
/*      */     
/* 3532 */     int sentenceStart = 0;
/* 3533 */     for (int i = 0; i < length; i++) {
/* 3534 */       if (this.textBuffer[i] == '\n')
/*      */       {
/*      */ 
/* 3537 */         boolean legit = 
/* 3538 */           textSentence(this.textBuffer, sentenceStart, i, boxWidth, spaceWidth);
/* 3539 */         if (!legit) {
/*      */           break;
/*      */         }
/* 3542 */         sentenceStart = i + 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3548 */     float lineX = x1;
/* 3549 */     if (this.textAlign == 3) {
/* 3550 */       lineX += boxWidth / 2.0F;
/* 3551 */     } else if (this.textAlign == 39) {
/* 3552 */       lineX = x2;
/*      */     }
/*      */     
/* 3555 */     float boxHeight = y2 - y1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3560 */     float topAndBottom = textAscent() + textDescent();
/* 3561 */     int lineFitCount = 1 + PApplet.floor((boxHeight - topAndBottom) / this.textLeading);
/* 3562 */     int lineCount = Math.min(this.textBreakCount, lineFitCount);
/*      */     
/* 3564 */     if (this.textAlignY == 3) {
/* 3565 */       float lineHigh = textAscent() + this.textLeading * (lineCount - 1);
/* 3566 */       float y = y1 + textAscent() + (boxHeight - lineHigh) / 2.0F;
/* 3567 */       for (int i = 0; i < lineCount; i++) {
/* 3568 */         textLineAlignImpl(this.textBuffer, this.textBreakStart[i], this.textBreakStop[i], lineX, y);
/* 3569 */         y += this.textLeading;
/*      */       }
/*      */     }
/* 3572 */     else if (this.textAlignY == 102) {
/* 3573 */       float y = y2 - textDescent() - this.textLeading * (lineCount - 1);
/* 3574 */       for (int i = 0; i < lineCount; i++) {
/* 3575 */         textLineAlignImpl(this.textBuffer, this.textBreakStart[i], this.textBreakStop[i], lineX, y);
/* 3576 */         y += this.textLeading;
/*      */       }
/*      */     }
/*      */     else {
/* 3580 */       float y = y1 + textAscent();
/* 3581 */       for (int i = 0; i < lineCount; i++) {
/* 3582 */         textLineAlignImpl(this.textBuffer, this.textBreakStart[i], this.textBreakStop[i], lineX, y);
/* 3583 */         y += this.textLeading;
/*      */       }
/*      */     }
/*      */     
/* 3587 */     if (this.textMode == 256) { updatePixels();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean textSentence(char[] buffer, int start, int stop, float boxWidth, float spaceWidth)
/*      */   {
/* 3597 */     float runningX = 0.0F;
/*      */     
/*      */ 
/*      */ 
/* 3601 */     int lineStart = start;
/* 3602 */     int wordStart = start;
/* 3603 */     int index = start;
/* 3604 */     while (index <= stop)
/*      */     {
/* 3606 */       if ((buffer[index] == ' ') || (index == stop)) {
/* 3607 */         float wordWidth = textWidthImpl(buffer, wordStart, index);
/*      */         
/* 3609 */         if (runningX + wordWidth > boxWidth) {
/* 3610 */           if (runningX != 0.0F)
/*      */           {
/* 3612 */             index = wordStart;
/* 3613 */             textSentenceBreak(lineStart, index);
/*      */             
/*      */             do
/*      */             {
/* 3617 */               index++;
/* 3616 */               if (index >= stop) break; } while (buffer[index] == ' ');
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*      */             do
/*      */             {
/* 3624 */               index--;
/* 3625 */               if (index == wordStart)
/*      */               {
/*      */ 
/* 3628 */                 return false;
/*      */               }
/* 3630 */               wordWidth = textWidthImpl(buffer, wordStart, index);
/* 3631 */             } while (wordWidth > boxWidth);
/*      */             
/*      */ 
/* 3634 */             textSentenceBreak(lineStart, index);
/*      */           }
/* 3636 */           lineStart = index;
/* 3637 */           wordStart = index;
/* 3638 */           runningX = 0.0F;
/*      */         }
/* 3640 */         else if (index == stop)
/*      */         {
/*      */ 
/* 3643 */           textSentenceBreak(lineStart, index);
/*      */           
/* 3645 */           index++;
/*      */         }
/*      */         else {
/* 3648 */           runningX += wordWidth + spaceWidth;
/* 3649 */           wordStart = index + 1;
/* 3650 */           index++;
/*      */         }
/*      */       } else {
/* 3653 */         index++;
/*      */       }
/*      */     }
/*      */     
/* 3657 */     return true;
/*      */   }
/*      */   
/*      */   protected void textSentenceBreak(int start, int stop)
/*      */   {
/* 3662 */     if (this.textBreakCount == this.textBreakStart.length) {
/* 3663 */       this.textBreakStart = PApplet.expand(this.textBreakStart);
/* 3664 */       this.textBreakStop = PApplet.expand(this.textBreakStop);
/*      */     }
/* 3666 */     this.textBreakStart[this.textBreakCount] = start;
/* 3667 */     this.textBreakStop[this.textBreakCount] = stop;
/* 3668 */     this.textBreakCount += 1;
/*      */   }
/*      */   
/*      */   public void text(String s, float x1, float y1, float x2, float y2, float z)
/*      */   {
/* 3673 */     if (z != 0.0F) { translate(0.0F, 0.0F, z);
/*      */     }
/* 3675 */     text(s, x1, y1, x2, y2);
/* 3676 */     this.textZ = z;
/*      */     
/* 3678 */     if (z != 0.0F) translate(0.0F, 0.0F, -z);
/*      */   }
/*      */   
/*      */   public void text(int num, float x, float y)
/*      */   {
/* 3683 */     text(String.valueOf(num), x, y);
/*      */   }
/*      */   
/*      */   public void text(int num, float x, float y, float z)
/*      */   {
/* 3688 */     text(String.valueOf(num), x, y, z);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(float num, float x, float y)
/*      */   {
/* 3700 */     text(PApplet.nfs(num, 0, 3), x, y);
/*      */   }
/*      */   
/*      */   public void text(float num, float x, float y, float z)
/*      */   {
/* 3705 */     text(PApplet.nfs(num, 0, 3), x, y, z);
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
/*      */   protected void textLineAlignImpl(char[] buffer, int start, int stop, float x, float y)
/*      */   {
/* 3724 */     if (this.textAlign == 3) {
/* 3725 */       x -= textWidthImpl(buffer, start, stop) / 2.0F;
/*      */     }
/* 3727 */     else if (this.textAlign == 39) {
/* 3728 */       x -= textWidthImpl(buffer, start, stop);
/*      */     }
/*      */     
/* 3731 */     textLineImpl(buffer, start, stop, x, y);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void textLineImpl(char[] buffer, int start, int stop, float x, float y)
/*      */   {
/* 3740 */     for (int index = start; index < stop; index++) {
/* 3741 */       textCharImpl(buffer[index], x, y);
/*      */       
/*      */ 
/* 3744 */       x += textWidth(buffer[index]);
/*      */     }
/* 3746 */     this.textX = x;
/* 3747 */     this.textY = y;
/* 3748 */     this.textZ = 0.0F;
/*      */   }
/*      */   
/*      */   protected void textCharImpl(char ch, float x, float y)
/*      */   {
/* 3753 */     PFont.Glyph glyph = this.textFont.getGlyph(ch);
/* 3754 */     if (glyph != null) {
/* 3755 */       if (this.textMode == 4) {
/* 3756 */         float high = glyph.height / this.textFont.size;
/* 3757 */         float bwidth = glyph.width / this.textFont.size;
/* 3758 */         float lextent = glyph.leftExtent / this.textFont.size;
/* 3759 */         float textent = glyph.topExtent / this.textFont.size;
/*      */         
/* 3761 */         float x1 = x + lextent * this.textSize;
/* 3762 */         float y1 = y - textent * this.textSize;
/* 3763 */         float x2 = x1 + bwidth * this.textSize;
/* 3764 */         float y2 = y1 + high * this.textSize;
/*      */         
/* 3766 */         textCharModelImpl(glyph.image, 
/* 3767 */           x1, y1, x2, y2, 
/* 3768 */           glyph.width, glyph.height);
/*      */       }
/* 3770 */       else if (this.textMode == 256) {
/* 3771 */         int xx = (int)x + glyph.leftExtent;
/* 3772 */         int yy = (int)y - glyph.topExtent;
/*      */         
/* 3774 */         int w0 = glyph.width;
/* 3775 */         int h0 = glyph.height;
/*      */         
/* 3777 */         textCharScreenImpl(glyph.image, xx, yy, w0, h0);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void textCharModelImpl(PImage glyph, float x1, float y1, float x2, float y2, int u2, int v2)
/*      */   {
/* 3787 */     boolean savedTint = this.tint;
/* 3788 */     int savedTintColor = this.tintColor;
/* 3789 */     float savedTintR = this.tintR;
/* 3790 */     float savedTintG = this.tintG;
/* 3791 */     float savedTintB = this.tintB;
/* 3792 */     float savedTintA = this.tintA;
/* 3793 */     boolean savedTintAlpha = this.tintAlpha;
/*      */     
/* 3795 */     this.tint = true;
/* 3796 */     this.tintColor = this.fillColor;
/* 3797 */     this.tintR = this.fillR;
/* 3798 */     this.tintG = this.fillG;
/* 3799 */     this.tintB = this.fillB;
/* 3800 */     this.tintA = this.fillA;
/* 3801 */     this.tintAlpha = this.fillAlpha;
/*      */     
/* 3803 */     imageImpl(glyph, x1, y1, x2, y2, 0, 0, u2, v2);
/*      */     
/* 3805 */     this.tint = savedTint;
/* 3806 */     this.tintColor = savedTintColor;
/* 3807 */     this.tintR = savedTintR;
/* 3808 */     this.tintG = savedTintG;
/* 3809 */     this.tintB = savedTintB;
/* 3810 */     this.tintA = savedTintA;
/* 3811 */     this.tintAlpha = savedTintAlpha;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void textCharScreenImpl(PImage glyph, int xx, int yy, int w0, int h0)
/*      */   {
/* 3818 */     int x0 = 0;
/* 3819 */     int y0 = 0;
/*      */     
/* 3821 */     if ((xx >= this.width) || (yy >= this.height) || 
/* 3822 */       (xx + w0 < 0) || (yy + h0 < 0)) { return;
/*      */     }
/* 3824 */     if (xx < 0) {
/* 3825 */       x0 -= xx;
/* 3826 */       w0 += xx;
/* 3827 */       xx = 0;
/*      */     }
/* 3829 */     if (yy < 0) {
/* 3830 */       y0 -= yy;
/* 3831 */       h0 += yy;
/* 3832 */       yy = 0;
/*      */     }
/* 3834 */     if (xx + w0 > this.width) {
/* 3835 */       w0 -= xx + w0 - this.width;
/*      */     }
/* 3837 */     if (yy + h0 > this.height) {
/* 3838 */       h0 -= yy + h0 - this.height;
/*      */     }
/*      */     
/* 3841 */     int fr = this.fillRi;
/* 3842 */     int fg = this.fillGi;
/* 3843 */     int fb = this.fillBi;
/* 3844 */     int fa = this.fillAi;
/*      */     
/* 3846 */     int[] pixels1 = glyph.pixels;
/*      */     
/*      */ 
/* 3849 */     for (int row = y0; row < y0 + h0; row++) {
/* 3850 */       for (int col = x0; col < x0 + w0; col++)
/*      */       {
/* 3852 */         int a1 = fa * pixels1[(row * glyph.width + col)] >> 8;
/* 3853 */         int a2 = a1 ^ 0xFF;
/*      */         
/* 3855 */         int p2 = this.pixels[((yy + row - y0) * this.width + (xx + col - x0))];
/*      */         
/* 3857 */         this.pixels[((yy + row - y0) * this.width + xx + col - x0)] = 
/* 3858 */           (0xFF000000 | 
/* 3859 */           (a1 * fr + a2 * (p2 >> 16 & 0xFF) & 0xFF00) << 8 | 
/* 3860 */           a1 * fg + a2 * (p2 >> 8 & 0xFF) & 0xFF00 | 
/* 3861 */           a1 * fb + a2 * (p2 & 0xFF) >> 8);
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
/*      */   public void pushMatrix()
/*      */   {
/* 3877 */     showMethodWarning("pushMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void popMatrix()
/*      */   {
/* 3885 */     showMethodWarning("popMatrix");
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
/*      */   public void translate(float tx, float ty)
/*      */   {
/* 3899 */     showMissingWarning("translate");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void translate(float tx, float ty, float tz)
/*      */   {
/* 3907 */     showMissingWarning("translate");
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
/*      */   public void rotate(float angle)
/*      */   {
/* 3921 */     showMissingWarning("rotate");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotateX(float angle)
/*      */   {
/* 3929 */     showMethodWarning("rotateX");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotateY(float angle)
/*      */   {
/* 3937 */     showMethodWarning("rotateY");
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
/*      */   public void rotateZ(float angle)
/*      */   {
/* 3950 */     showMethodWarning("rotateZ");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle, float vx, float vy, float vz)
/*      */   {
/* 3958 */     showMissingWarning("rotate");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float s)
/*      */   {
/* 3966 */     showMissingWarning("scale");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float sx, float sy)
/*      */   {
/* 3977 */     showMissingWarning("scale");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float x, float y, float z)
/*      */   {
/* 3985 */     showMissingWarning("scale");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void shearX(float angle)
/*      */   {
/* 3993 */     showMissingWarning("shearX");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void shearY(float angle)
/*      */   {
/* 4001 */     showMissingWarning("shearY");
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
/*      */   public void resetMatrix()
/*      */   {
/* 4014 */     showMethodWarning("resetMatrix");
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix source)
/*      */   {
/* 4019 */     if ((source instanceof PMatrix2D)) {
/* 4020 */       applyMatrix((PMatrix2D)source);
/* 4021 */     } else if ((source instanceof PMatrix3D)) {
/* 4022 */       applyMatrix((PMatrix3D)source);
/*      */     }
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix2D source)
/*      */   {
/* 4028 */     applyMatrix(source.m00, source.m01, source.m02, 
/* 4029 */       source.m10, source.m11, source.m12);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/* 4038 */     showMissingWarning("applyMatrix");
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix3D source)
/*      */   {
/* 4043 */     applyMatrix(source.m00, source.m01, source.m02, source.m03, 
/* 4044 */       source.m10, source.m11, source.m12, source.m13, 
/* 4045 */       source.m20, source.m21, source.m22, source.m23, 
/* 4046 */       source.m30, source.m31, source.m32, source.m33);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*      */   {
/* 4057 */     showMissingWarning("applyMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix getMatrix()
/*      */   {
/* 4068 */     showMissingWarning("getMatrix");
/* 4069 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix2D getMatrix(PMatrix2D target)
/*      */   {
/* 4078 */     showMissingWarning("getMatrix");
/* 4079 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix3D getMatrix(PMatrix3D target)
/*      */   {
/* 4088 */     showMissingWarning("getMatrix");
/* 4089 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix source)
/*      */   {
/* 4097 */     if ((source instanceof PMatrix2D)) {
/* 4098 */       setMatrix((PMatrix2D)source);
/* 4099 */     } else if ((source instanceof PMatrix3D)) {
/* 4100 */       setMatrix((PMatrix3D)source);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix2D source)
/*      */   {
/* 4109 */     showMissingWarning("setMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix3D source)
/*      */   {
/* 4117 */     showMissingWarning("setMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printMatrix()
/*      */   {
/* 4125 */     showMethodWarning("printMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void beginCamera()
/*      */   {
/* 4135 */     showMethodWarning("beginCamera");
/*      */   }
/*      */   
/*      */   public void endCamera()
/*      */   {
/* 4140 */     showMethodWarning("endCamera");
/*      */   }
/*      */   
/*      */   public void camera()
/*      */   {
/* 4145 */     showMissingWarning("camera");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
/*      */   {
/* 4152 */     showMissingWarning("camera");
/*      */   }
/*      */   
/*      */   public void printCamera()
/*      */   {
/* 4157 */     showMethodWarning("printCamera");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ortho()
/*      */   {
/* 4168 */     showMissingWarning("ortho");
/*      */   }
/*      */   
/*      */ 
/*      */   public void ortho(float left, float right, float bottom, float top)
/*      */   {
/* 4174 */     showMissingWarning("ortho");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void ortho(float left, float right, float bottom, float top, float near, float far)
/*      */   {
/* 4181 */     showMissingWarning("ortho");
/*      */   }
/*      */   
/*      */   public void perspective()
/*      */   {
/* 4186 */     showMissingWarning("perspective");
/*      */   }
/*      */   
/*      */   public void perspective(float fovy, float aspect, float zNear, float zFar)
/*      */   {
/* 4191 */     showMissingWarning("perspective");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void frustum(float left, float right, float bottom, float top, float near, float far)
/*      */   {
/* 4198 */     showMethodWarning("frustum");
/*      */   }
/*      */   
/*      */   public void printProjection()
/*      */   {
/* 4203 */     showMethodWarning("printCamera");
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
/*      */   public float screenX(float x, float y)
/*      */   {
/* 4219 */     showMissingWarning("screenX");
/* 4220 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float screenY(float x, float y)
/*      */   {
/* 4230 */     showMissingWarning("screenY");
/* 4231 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float screenX(float x, float y, float z)
/*      */   {
/* 4243 */     showMissingWarning("screenX");
/* 4244 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float screenY(float x, float y, float z)
/*      */   {
/* 4256 */     showMissingWarning("screenY");
/* 4257 */     return 0.0F;
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
/*      */   public float screenZ(float x, float y, float z)
/*      */   {
/* 4273 */     showMissingWarning("screenZ");
/* 4274 */     return 0.0F;
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
/*      */   public float modelX(float x, float y, float z)
/*      */   {
/* 4288 */     showMissingWarning("modelX");
/* 4289 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float modelY(float x, float y, float z)
/*      */   {
/* 4297 */     showMissingWarning("modelY");
/* 4298 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float modelZ(float x, float y, float z)
/*      */   {
/* 4306 */     showMissingWarning("modelZ");
/* 4307 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushStyle()
/*      */   {
/* 4318 */     if (this.styleStackDepth == this.styleStack.length) {
/* 4319 */       this.styleStack = ((PStyle[])PApplet.expand(this.styleStack));
/*      */     }
/* 4321 */     if (this.styleStack[this.styleStackDepth] == null) {
/* 4322 */       this.styleStack[this.styleStackDepth] = new PStyle();
/*      */     }
/* 4324 */     PStyle s = this.styleStack[(this.styleStackDepth++)];
/* 4325 */     getStyle(s);
/*      */   }
/*      */   
/*      */   public void popStyle()
/*      */   {
/* 4330 */     if (this.styleStackDepth == 0) {
/* 4331 */       throw new RuntimeException("Too many popStyle() without enough pushStyle()");
/*      */     }
/* 4333 */     this.styleStackDepth -= 1;
/* 4334 */     style(this.styleStack[this.styleStackDepth]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void style(PStyle s)
/*      */   {
/* 4345 */     imageMode(s.imageMode);
/* 4346 */     rectMode(s.rectMode);
/* 4347 */     ellipseMode(s.ellipseMode);
/* 4348 */     shapeMode(s.shapeMode);
/*      */     
/* 4350 */     if (s.tint) {
/* 4351 */       tint(s.tintColor);
/*      */     } else {
/* 4353 */       noTint();
/*      */     }
/* 4355 */     if (s.fill) {
/* 4356 */       fill(s.fillColor);
/*      */     } else {
/* 4358 */       noFill();
/*      */     }
/* 4360 */     if (s.stroke) {
/* 4361 */       stroke(s.strokeColor);
/*      */     } else {
/* 4363 */       noStroke();
/*      */     }
/* 4365 */     strokeWeight(s.strokeWeight);
/* 4366 */     strokeCap(s.strokeCap);
/* 4367 */     strokeJoin(s.strokeJoin);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4372 */     colorMode(1, 1.0F);
/* 4373 */     ambient(s.ambientR, s.ambientG, s.ambientB);
/* 4374 */     emissive(s.emissiveR, s.emissiveG, s.emissiveB);
/* 4375 */     specular(s.specularR, s.specularG, s.specularB);
/* 4376 */     shininess(s.shininess);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4396 */     colorMode(s.colorMode, 
/* 4397 */       s.colorModeX, s.colorModeY, s.colorModeZ, s.colorModeA);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4403 */     if (s.textFont != null) {
/* 4404 */       textFont(s.textFont, s.textSize);
/* 4405 */       textLeading(s.textLeading);
/*      */     }
/*      */     
/* 4408 */     textAlign(s.textAlign, s.textAlignY);
/* 4409 */     textMode(s.textMode);
/*      */   }
/*      */   
/*      */   public PStyle getStyle()
/*      */   {
/* 4414 */     return getStyle(null);
/*      */   }
/*      */   
/*      */   public PStyle getStyle(PStyle s)
/*      */   {
/* 4419 */     if (s == null) {
/* 4420 */       s = new PStyle();
/*      */     }
/*      */     
/* 4423 */     s.imageMode = this.imageMode;
/* 4424 */     s.rectMode = this.rectMode;
/* 4425 */     s.ellipseMode = this.ellipseMode;
/* 4426 */     s.shapeMode = this.shapeMode;
/*      */     
/* 4428 */     s.colorMode = this.colorMode;
/* 4429 */     s.colorModeX = this.colorModeX;
/* 4430 */     s.colorModeY = this.colorModeY;
/* 4431 */     s.colorModeZ = this.colorModeZ;
/* 4432 */     s.colorModeA = this.colorModeA;
/*      */     
/* 4434 */     s.tint = this.tint;
/* 4435 */     s.tintColor = this.tintColor;
/* 4436 */     s.fill = this.fill;
/* 4437 */     s.fillColor = this.fillColor;
/* 4438 */     s.stroke = this.stroke;
/* 4439 */     s.strokeColor = this.strokeColor;
/* 4440 */     s.strokeWeight = this.strokeWeight;
/* 4441 */     s.strokeCap = this.strokeCap;
/* 4442 */     s.strokeJoin = this.strokeJoin;
/*      */     
/* 4444 */     s.ambientR = this.ambientR;
/* 4445 */     s.ambientG = this.ambientG;
/* 4446 */     s.ambientB = this.ambientB;
/* 4447 */     s.specularR = this.specularR;
/* 4448 */     s.specularG = this.specularG;
/* 4449 */     s.specularB = this.specularB;
/* 4450 */     s.emissiveR = this.emissiveR;
/* 4451 */     s.emissiveG = this.emissiveG;
/* 4452 */     s.emissiveB = this.emissiveB;
/* 4453 */     s.shininess = this.shininess;
/*      */     
/* 4455 */     s.textFont = this.textFont;
/* 4456 */     s.textAlign = this.textAlign;
/* 4457 */     s.textAlignY = this.textAlignY;
/* 4458 */     s.textMode = this.textMode;
/* 4459 */     s.textSize = this.textSize;
/* 4460 */     s.textLeading = this.textLeading;
/*      */     
/* 4462 */     return s;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void strokeWeight(float weight)
/*      */   {
/* 4473 */     this.strokeWeight = weight;
/*      */   }
/*      */   
/*      */   public void strokeJoin(int join)
/*      */   {
/* 4478 */     this.strokeJoin = join;
/*      */   }
/*      */   
/*      */   public void strokeCap(int cap)
/*      */   {
/* 4483 */     this.strokeCap = cap;
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
/*      */   public void noStroke()
/*      */   {
/* 4502 */     this.stroke = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void stroke(int rgb)
/*      */   {
/* 4513 */     colorCalc(rgb);
/* 4514 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */   public void stroke(int rgb, float alpha)
/*      */   {
/* 4519 */     colorCalc(rgb, alpha);
/* 4520 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void stroke(float gray)
/*      */   {
/* 4529 */     colorCalc(gray);
/* 4530 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */   public void stroke(float gray, float alpha)
/*      */   {
/* 4535 */     colorCalc(gray, alpha);
/* 4536 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */   public void stroke(float x, float y, float z)
/*      */   {
/* 4541 */     colorCalc(x, y, z);
/* 4542 */     strokeFromCalc();
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
/*      */   public void stroke(float x, float y, float z, float a)
/*      */   {
/* 4569 */     colorCalc(x, y, z, a);
/* 4570 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */   protected void strokeFromCalc()
/*      */   {
/* 4575 */     this.stroke = true;
/* 4576 */     this.strokeR = this.calcR;
/* 4577 */     this.strokeG = this.calcG;
/* 4578 */     this.strokeB = this.calcB;
/* 4579 */     this.strokeA = this.calcA;
/* 4580 */     this.strokeRi = this.calcRi;
/* 4581 */     this.strokeGi = this.calcGi;
/* 4582 */     this.strokeBi = this.calcBi;
/* 4583 */     this.strokeAi = this.calcAi;
/* 4584 */     this.strokeColor = this.calcColor;
/* 4585 */     this.strokeAlpha = this.calcAlpha;
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
/*      */   public void noTint()
/*      */   {
/* 4603 */     this.tint = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void tint(int rgb)
/*      */   {
/* 4611 */     colorCalc(rgb);
/* 4612 */     tintFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void tint(int rgb, float alpha)
/*      */   {
/* 4622 */     colorCalc(rgb, alpha);
/* 4623 */     tintFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void tint(float gray)
/*      */   {
/* 4631 */     colorCalc(gray);
/* 4632 */     tintFromCalc();
/*      */   }
/*      */   
/*      */   public void tint(float gray, float alpha)
/*      */   {
/* 4637 */     colorCalc(gray, alpha);
/* 4638 */     tintFromCalc();
/*      */   }
/*      */   
/*      */   public void tint(float x, float y, float z)
/*      */   {
/* 4643 */     colorCalc(x, y, z);
/* 4644 */     tintFromCalc();
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
/*      */   public void tint(float x, float y, float z, float a)
/*      */   {
/* 4678 */     colorCalc(x, y, z, a);
/* 4679 */     tintFromCalc();
/*      */   }
/*      */   
/*      */   protected void tintFromCalc()
/*      */   {
/* 4684 */     this.tint = true;
/* 4685 */     this.tintR = this.calcR;
/* 4686 */     this.tintG = this.calcG;
/* 4687 */     this.tintB = this.calcB;
/* 4688 */     this.tintA = this.calcA;
/* 4689 */     this.tintRi = this.calcRi;
/* 4690 */     this.tintGi = this.calcGi;
/* 4691 */     this.tintBi = this.calcBi;
/* 4692 */     this.tintAi = this.calcAi;
/* 4693 */     this.tintColor = this.calcColor;
/* 4694 */     this.tintAlpha = this.calcAlpha;
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
/*      */   public void noFill()
/*      */   {
/* 4714 */     this.fill = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fill(int rgb)
/*      */   {
/* 4723 */     colorCalc(rgb);
/* 4724 */     fillFromCalc();
/*      */   }
/*      */   
/*      */   public void fill(int rgb, float alpha)
/*      */   {
/* 4729 */     colorCalc(rgb, alpha);
/* 4730 */     fillFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fill(float gray)
/*      */   {
/* 4738 */     colorCalc(gray);
/* 4739 */     fillFromCalc();
/*      */   }
/*      */   
/*      */   public void fill(float gray, float alpha)
/*      */   {
/* 4744 */     colorCalc(gray, alpha);
/* 4745 */     fillFromCalc();
/*      */   }
/*      */   
/*      */   public void fill(float x, float y, float z)
/*      */   {
/* 4750 */     colorCalc(x, y, z);
/* 4751 */     fillFromCalc();
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
/*      */   public void fill(float x, float y, float z, float a)
/*      */   {
/* 4774 */     colorCalc(x, y, z, a);
/* 4775 */     fillFromCalc();
/*      */   }
/*      */   
/*      */   protected void fillFromCalc()
/*      */   {
/* 4780 */     this.fill = true;
/* 4781 */     this.fillR = this.calcR;
/* 4782 */     this.fillG = this.calcG;
/* 4783 */     this.fillB = this.calcB;
/* 4784 */     this.fillA = this.calcA;
/* 4785 */     this.fillRi = this.calcRi;
/* 4786 */     this.fillGi = this.calcGi;
/* 4787 */     this.fillBi = this.calcBi;
/* 4788 */     this.fillAi = this.calcAi;
/* 4789 */     this.fillColor = this.calcColor;
/* 4790 */     this.fillAlpha = this.calcAlpha;
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
/*      */   public void ambient(int rgb)
/*      */   {
/* 4808 */     colorCalc(rgb);
/* 4809 */     ambientFromCalc();
/*      */   }
/*      */   
/*      */   public void ambient(float gray)
/*      */   {
/* 4814 */     colorCalc(gray);
/* 4815 */     ambientFromCalc();
/*      */   }
/*      */   
/*      */   public void ambient(float x, float y, float z)
/*      */   {
/* 4820 */     colorCalc(x, y, z);
/* 4821 */     ambientFromCalc();
/*      */   }
/*      */   
/*      */   protected void ambientFromCalc()
/*      */   {
/* 4826 */     this.ambientR = this.calcR;
/* 4827 */     this.ambientG = this.calcG;
/* 4828 */     this.ambientB = this.calcB;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void specular(int rgb)
/*      */   {
/* 4840 */     colorCalc(rgb);
/* 4841 */     specularFromCalc();
/*      */   }
/*      */   
/*      */   public void specular(float gray)
/*      */   {
/* 4846 */     colorCalc(gray);
/* 4847 */     specularFromCalc();
/*      */   }
/*      */   
/*      */   public void specular(float x, float y, float z)
/*      */   {
/* 4852 */     colorCalc(x, y, z);
/* 4853 */     specularFromCalc();
/*      */   }
/*      */   
/*      */   protected void specularFromCalc()
/*      */   {
/* 4858 */     this.specularR = this.calcR;
/* 4859 */     this.specularG = this.calcG;
/* 4860 */     this.specularB = this.calcB;
/*      */   }
/*      */   
/*      */   public void shininess(float shine)
/*      */   {
/* 4865 */     this.shininess = shine;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void emissive(int rgb)
/*      */   {
/* 4877 */     colorCalc(rgb);
/* 4878 */     emissiveFromCalc();
/*      */   }
/*      */   
/*      */   public void emissive(float gray)
/*      */   {
/* 4883 */     colorCalc(gray);
/* 4884 */     emissiveFromCalc();
/*      */   }
/*      */   
/*      */   public void emissive(float x, float y, float z)
/*      */   {
/* 4889 */     colorCalc(x, y, z);
/* 4890 */     emissiveFromCalc();
/*      */   }
/*      */   
/*      */   protected void emissiveFromCalc()
/*      */   {
/* 4895 */     this.emissiveR = this.calcR;
/* 4896 */     this.emissiveG = this.calcG;
/* 4897 */     this.emissiveB = this.calcB;
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
/*      */   public void lights()
/*      */   {
/* 4912 */     showMethodWarning("lights");
/*      */   }
/*      */   
/*      */   public void noLights() {
/* 4916 */     showMethodWarning("noLights");
/*      */   }
/*      */   
/*      */   public void ambientLight(float red, float green, float blue) {
/* 4920 */     showMethodWarning("ambientLight");
/*      */   }
/*      */   
/*      */   public void ambientLight(float red, float green, float blue, float x, float y, float z)
/*      */   {
/* 4925 */     showMethodWarning("ambientLight");
/*      */   }
/*      */   
/*      */   public void directionalLight(float red, float green, float blue, float nx, float ny, float nz)
/*      */   {
/* 4930 */     showMethodWarning("directionalLight");
/*      */   }
/*      */   
/*      */   public void pointLight(float red, float green, float blue, float x, float y, float z)
/*      */   {
/* 4935 */     showMethodWarning("pointLight");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void spotLight(float red, float green, float blue, float x, float y, float z, float nx, float ny, float nz, float angle, float concentration)
/*      */   {
/* 4942 */     showMethodWarning("spotLight");
/*      */   }
/*      */   
/*      */   public void lightFalloff(float constant, float linear, float quadratic) {
/* 4946 */     showMethodWarning("lightFalloff");
/*      */   }
/*      */   
/*      */   public void lightSpecular(float x, float y, float z) {
/* 4950 */     showMethodWarning("lightSpecular");
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
/*      */   public void background(int rgb)
/*      */   {
/* 4985 */     colorCalc(rgb);
/* 4986 */     backgroundFromCalc();
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
/*      */   public void background(int rgb, float alpha)
/*      */   {
/* 5007 */     colorCalc(rgb, alpha);
/* 5008 */     backgroundFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void background(float gray)
/*      */   {
/* 5017 */     colorCalc(gray);
/* 5018 */     backgroundFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void background(float gray, float alpha)
/*      */   {
/* 5029 */     if (this.format == 1) {
/* 5030 */       background(gray);
/*      */     }
/*      */     else {
/* 5033 */       colorCalc(gray, alpha);
/* 5034 */       backgroundFromCalc();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void background(float x, float y, float z)
/*      */   {
/* 5045 */     colorCalc(x, y, z);
/* 5046 */     backgroundFromCalc();
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
/*      */   public void background(float x, float y, float z, float a)
/*      */   {
/* 5086 */     colorCalc(x, y, z, a);
/* 5087 */     backgroundFromCalc();
/*      */   }
/*      */   
/*      */   protected void backgroundFromCalc()
/*      */   {
/* 5092 */     this.backgroundR = this.calcR;
/* 5093 */     this.backgroundG = this.calcG;
/* 5094 */     this.backgroundB = this.calcB;
/* 5095 */     this.backgroundA = (this.format == 1 ? this.colorModeA : this.calcA);
/* 5096 */     this.backgroundRi = this.calcRi;
/* 5097 */     this.backgroundGi = this.calcGi;
/* 5098 */     this.backgroundBi = this.calcBi;
/* 5099 */     this.backgroundAi = (this.format == 1 ? 255 : this.calcAi);
/* 5100 */     this.backgroundAlpha = (this.format == 1 ? false : this.calcAlpha);
/* 5101 */     this.backgroundColor = this.calcColor;
/*      */     
/* 5103 */     backgroundImpl();
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
/*      */   public void background(PImage image)
/*      */   {
/* 5120 */     if ((image.width != this.width) || (image.height != this.height)) {
/* 5121 */       throw new RuntimeException("background image must be the same size as your application");
/*      */     }
/* 5123 */     if ((image.format != 1) && (image.format != 2)) {
/* 5124 */       throw new RuntimeException("background images should be RGB or ARGB");
/*      */     }
/* 5126 */     this.backgroundColor = 0;
/* 5127 */     backgroundImpl(image);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backgroundImpl(PImage image)
/*      */   {
/* 5137 */     set(0, 0, image);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backgroundImpl()
/*      */   {
/* 5148 */     pushStyle();
/* 5149 */     pushMatrix();
/* 5150 */     resetMatrix();
/* 5151 */     fill(this.backgroundColor);
/* 5152 */     rect(0.0F, 0.0F, this.width, this.height);
/* 5153 */     popMatrix();
/* 5154 */     popStyle();
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
/*      */   public void colorMode(int mode)
/*      */   {
/* 5194 */     colorMode(mode, this.colorModeX, this.colorModeY, this.colorModeZ, this.colorModeA);
/*      */   }
/*      */   
/*      */   public void colorMode(int mode, float max)
/*      */   {
/* 5199 */     colorMode(mode, max, max, max, max);
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
/*      */   public void colorMode(int mode, float maxX, float maxY, float maxZ)
/*      */   {
/* 5213 */     colorMode(mode, maxX, maxY, maxZ, this.colorModeA);
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
/*      */   public void colorMode(int mode, float maxX, float maxY, float maxZ, float maxA)
/*      */   {
/* 5232 */     this.colorMode = mode;
/*      */     
/* 5234 */     this.colorModeX = maxX;
/* 5235 */     this.colorModeY = maxY;
/* 5236 */     this.colorModeZ = maxZ;
/* 5237 */     this.colorModeA = maxA;
/*      */     
/*      */ 
/* 5240 */     this.colorModeScale = 
/* 5241 */       ((maxA != 1.0F) || (maxX != maxY) || (maxY != maxZ) || (maxZ != maxA));
/*      */     
/*      */ 
/*      */ 
/* 5245 */     this.colorModeDefault = ((this.colorMode == 1) && 
/* 5246 */       (this.colorModeA == 255.0F) && (this.colorModeX == 255.0F) && 
/* 5247 */       (this.colorModeY == 255.0F) && (this.colorModeZ == 255.0F));
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
/*      */   protected void colorCalc(int rgb)
/*      */   {
/* 5288 */     if (((rgb & 0xFF000000) == 0) && (rgb <= this.colorModeX)) {
/* 5289 */       colorCalc(rgb);
/*      */     }
/*      */     else {
/* 5292 */       colorCalcARGB(rgb, this.colorModeA);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void colorCalc(int rgb, float alpha)
/*      */   {
/* 5298 */     if (((rgb & 0xFF000000) == 0) && (rgb <= this.colorModeX)) {
/* 5299 */       colorCalc(rgb, alpha);
/*      */     }
/*      */     else {
/* 5302 */       colorCalcARGB(rgb, alpha);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void colorCalc(float gray)
/*      */   {
/* 5308 */     colorCalc(gray, this.colorModeA);
/*      */   }
/*      */   
/*      */   protected void colorCalc(float gray, float alpha)
/*      */   {
/* 5313 */     if (gray > this.colorModeX) gray = this.colorModeX;
/* 5314 */     if (alpha > this.colorModeA) { alpha = this.colorModeA;
/*      */     }
/* 5316 */     if (gray < 0.0F) gray = 0.0F;
/* 5317 */     if (alpha < 0.0F) { alpha = 0.0F;
/*      */     }
/* 5319 */     this.calcR = (this.colorModeScale ? gray / this.colorModeX : gray);
/* 5320 */     this.calcG = this.calcR;
/* 5321 */     this.calcB = this.calcR;
/* 5322 */     this.calcA = (this.colorModeScale ? alpha / this.colorModeA : alpha);
/*      */     
/* 5324 */     this.calcRi = ((int)(this.calcR * 255.0F));this.calcGi = ((int)(this.calcG * 255.0F));
/* 5325 */     this.calcBi = ((int)(this.calcB * 255.0F));this.calcAi = ((int)(this.calcA * 255.0F));
/* 5326 */     this.calcColor = (this.calcAi << 24 | this.calcRi << 16 | this.calcGi << 8 | this.calcBi);
/* 5327 */     this.calcAlpha = (this.calcAi != 255);
/*      */   }
/*      */   
/*      */   protected void colorCalc(float x, float y, float z)
/*      */   {
/* 5332 */     colorCalc(x, y, z, this.colorModeA);
/*      */   }
/*      */   
/*      */   protected void colorCalc(float x, float y, float z, float a)
/*      */   {
/* 5337 */     if (x > this.colorModeX) x = this.colorModeX;
/* 5338 */     if (y > this.colorModeY) y = this.colorModeY;
/* 5339 */     if (z > this.colorModeZ) z = this.colorModeZ;
/* 5340 */     if (a > this.colorModeA) { a = this.colorModeA;
/*      */     }
/* 5342 */     if (x < 0.0F) x = 0.0F;
/* 5343 */     if (y < 0.0F) y = 0.0F;
/* 5344 */     if (z < 0.0F) z = 0.0F;
/* 5345 */     if (a < 0.0F) { a = 0.0F;
/*      */     }
/* 5347 */     switch (this.colorMode) {
/*      */     case 1: 
/* 5349 */       if (this.colorModeScale) {
/* 5350 */         this.calcR = (x / this.colorModeX);
/* 5351 */         this.calcG = (y / this.colorModeY);
/* 5352 */         this.calcB = (z / this.colorModeZ);
/* 5353 */         this.calcA = (a / this.colorModeA);
/*      */       } else {
/* 5355 */         this.calcR = x;this.calcG = y;this.calcB = z;this.calcA = a;
/*      */       }
/* 5357 */       break;
/*      */     
/*      */     case 3: 
/* 5360 */       x /= this.colorModeX;
/* 5361 */       y /= this.colorModeY;
/* 5362 */       z /= this.colorModeZ;
/*      */       
/* 5364 */       this.calcA = (this.colorModeScale ? a / this.colorModeA : a);
/*      */       
/* 5366 */       if (y == 0.0F) {
/* 5367 */         this.calcR = (this.calcG = this.calcB = z);
/*      */       }
/*      */       else {
/* 5370 */         float which = (x - (int)x) * 6.0F;
/* 5371 */         float f = which - (int)which;
/* 5372 */         float p = z * (1.0F - y);
/* 5373 */         float q = z * (1.0F - y * f);
/* 5374 */         float t = z * (1.0F - y * (1.0F - f));
/*      */         
/* 5376 */         switch ((int)which) {
/* 5377 */         case 0:  this.calcR = z;this.calcG = t;this.calcB = p; break;
/* 5378 */         case 1:  this.calcR = q;this.calcG = z;this.calcB = p; break;
/* 5379 */         case 2:  this.calcR = p;this.calcG = z;this.calcB = t; break;
/* 5380 */         case 3:  this.calcR = p;this.calcG = q;this.calcB = z; break;
/* 5381 */         case 4:  this.calcR = t;this.calcG = p;this.calcB = z; break;
/* 5382 */         case 5:  this.calcR = z;this.calcG = p;this.calcB = q;
/*      */         }
/*      */       }
/*      */       break;
/*      */     }
/* 5387 */     this.calcRi = ((int)(255.0F * this.calcR));this.calcGi = ((int)(255.0F * this.calcG));
/* 5388 */     this.calcBi = ((int)(255.0F * this.calcB));this.calcAi = ((int)(255.0F * this.calcA));
/* 5389 */     this.calcColor = (this.calcAi << 24 | this.calcRi << 16 | this.calcGi << 8 | this.calcBi);
/* 5390 */     this.calcAlpha = (this.calcAi != 255);
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
/*      */   protected void colorCalcARGB(int argb, float alpha)
/*      */   {
/* 5406 */     if (alpha == this.colorModeA) {
/* 5407 */       this.calcAi = (argb >> 24 & 0xFF);
/* 5408 */       this.calcColor = argb;
/*      */     } else {
/* 5410 */       this.calcAi = ((int)((argb >> 24 & 0xFF) * (alpha / this.colorModeA)));
/* 5411 */       this.calcColor = (this.calcAi << 24 | argb & 0xFFFFFF);
/*      */     }
/* 5413 */     this.calcRi = (argb >> 16 & 0xFF);
/* 5414 */     this.calcGi = (argb >> 8 & 0xFF);
/* 5415 */     this.calcBi = (argb & 0xFF);
/* 5416 */     this.calcA = (this.calcAi / 255.0F);
/* 5417 */     this.calcR = (this.calcRi / 255.0F);
/* 5418 */     this.calcG = (this.calcGi / 255.0F);
/* 5419 */     this.calcB = (this.calcBi / 255.0F);
/* 5420 */     this.calcAlpha = (this.calcAi != 255);
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
/*      */   public final int color(int c)
/*      */   {
/* 5450 */     colorCalc(c);
/* 5451 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(float gray)
/*      */   {
/* 5456 */     colorCalc(gray);
/* 5457 */     return this.calcColor;
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
/*      */   public final int color(int c, int alpha)
/*      */   {
/* 5472 */     colorCalc(c, alpha);
/* 5473 */     return this.calcColor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int color(int c, float alpha)
/*      */   {
/* 5482 */     colorCalc(c, alpha);
/*      */     
/*      */ 
/*      */ 
/* 5486 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(float gray, float alpha)
/*      */   {
/* 5491 */     colorCalc(gray, alpha);
/* 5492 */     return this.calcColor;
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
/*      */   public final int color(int x, int y, int z)
/*      */   {
/* 5505 */     colorCalc(x, y, z);
/* 5506 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(float x, float y, float z)
/*      */   {
/* 5511 */     colorCalc(x, y, z);
/* 5512 */     return this.calcColor;
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
/*      */   public final int color(int x, int y, int z, int a)
/*      */   {
/* 5526 */     colorCalc(x, y, z, a);
/* 5527 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(float x, float y, float z, float a)
/*      */   {
/* 5532 */     colorCalc(x, y, z, a);
/* 5533 */     return this.calcColor;
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
/*      */   public final float alpha(int what)
/*      */   {
/* 5552 */     float c = what >> 24 & 0xFF;
/* 5553 */     if (this.colorModeA == 255.0F) return c;
/* 5554 */     return c / 255.0F * this.colorModeA;
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
/*      */   public final float red(int what)
/*      */   {
/* 5572 */     float c = what >> 16 & 0xFF;
/* 5573 */     if (this.colorModeDefault) return c;
/* 5574 */     return c / 255.0F * this.colorModeX;
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
/*      */   public final float green(int what)
/*      */   {
/* 5592 */     float c = what >> 8 & 0xFF;
/* 5593 */     if (this.colorModeDefault) return c;
/* 5594 */     return c / 255.0F * this.colorModeY;
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
/*      */   public final float blue(int what)
/*      */   {
/* 5611 */     float c = what & 0xFF;
/* 5612 */     if (this.colorModeDefault) return c;
/* 5613 */     return c / 255.0F * this.colorModeZ;
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
/*      */   public final float hue(int what)
/*      */   {
/* 5630 */     if (what != this.cacheHsbKey) {
/* 5631 */       Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, 
/* 5632 */         what & 0xFF, this.cacheHsbValue);
/* 5633 */       this.cacheHsbKey = what;
/*      */     }
/* 5635 */     return this.cacheHsbValue[0] * this.colorModeX;
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
/*      */   public final float saturation(int what)
/*      */   {
/* 5652 */     if (what != this.cacheHsbKey) {
/* 5653 */       Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, 
/* 5654 */         what & 0xFF, this.cacheHsbValue);
/* 5655 */       this.cacheHsbKey = what;
/*      */     }
/* 5657 */     return this.cacheHsbValue[1] * this.colorModeY;
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
/*      */   public final float brightness(int what)
/*      */   {
/* 5675 */     if (what != this.cacheHsbKey) {
/* 5676 */       Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, 
/* 5677 */         what & 0xFF, this.cacheHsbValue);
/* 5678 */       this.cacheHsbKey = what;
/*      */     }
/* 5680 */     return this.cacheHsbValue[2] * this.colorModeZ;
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
/*      */   public int lerpColor(int c1, int c2, float amt)
/*      */   {
/* 5704 */     return lerpColor(c1, c2, amt, this.colorMode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int lerpColor(int c1, int c2, float amt, int mode)
/*      */   {
/* 5715 */     if (mode == 1) {
/* 5716 */       float a1 = c1 >> 24 & 0xFF;
/* 5717 */       float r1 = c1 >> 16 & 0xFF;
/* 5718 */       float g1 = c1 >> 8 & 0xFF;
/* 5719 */       float b1 = c1 & 0xFF;
/* 5720 */       float a2 = c2 >> 24 & 0xFF;
/* 5721 */       float r2 = c2 >> 16 & 0xFF;
/* 5722 */       float g2 = c2 >> 8 & 0xFF;
/* 5723 */       float b2 = c2 & 0xFF;
/*      */       
/* 5725 */       return (int)(a1 + (a2 - a1) * amt) << 24 | 
/* 5726 */         (int)(r1 + (r2 - r1) * amt) << 16 | 
/* 5727 */         (int)(g1 + (g2 - g1) * amt) << 8 | 
/* 5728 */         (int)(b1 + (b2 - b1) * amt);
/*      */     }
/* 5730 */     if (mode == 3) {
/* 5731 */       if (lerpColorHSB1 == null) {
/* 5732 */         lerpColorHSB1 = new float[3];
/* 5733 */         lerpColorHSB2 = new float[3];
/*      */       }
/*      */       
/* 5736 */       float a1 = c1 >> 24 & 0xFF;
/* 5737 */       float a2 = c2 >> 24 & 0xFF;
/* 5738 */       int alfa = (int)(a1 + (a2 - a1) * amt) << 24;
/*      */       
/* 5740 */       Color.RGBtoHSB(c1 >> 16 & 0xFF, c1 >> 8 & 0xFF, c1 & 0xFF, 
/* 5741 */         lerpColorHSB1);
/* 5742 */       Color.RGBtoHSB(c2 >> 16 & 0xFF, c2 >> 8 & 0xFF, c2 & 0xFF, 
/* 5743 */         lerpColorHSB2);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5770 */       float ho = PApplet.lerp(lerpColorHSB1[0], lerpColorHSB2[0], amt);
/* 5771 */       float so = PApplet.lerp(lerpColorHSB1[1], lerpColorHSB2[1], amt);
/* 5772 */       float bo = PApplet.lerp(lerpColorHSB1[2], lerpColorHSB2[2], amt);
/*      */       
/* 5774 */       return alfa | Color.HSBtoRGB(ho, so, bo) & 0xFFFFFF;
/*      */     }
/* 5776 */     return 0;
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
/*      */   public void beginRaw(PGraphics rawGraphics)
/*      */   {
/* 5790 */     this.raw = rawGraphics;
/* 5791 */     rawGraphics.beginDraw();
/*      */   }
/*      */   
/*      */   public void endRaw()
/*      */   {
/* 5796 */     if (this.raw != null)
/*      */     {
/*      */ 
/* 5799 */       flush();
/*      */       
/*      */ 
/*      */ 
/* 5803 */       this.raw.endDraw();
/* 5804 */       this.raw.dispose();
/* 5805 */       this.raw = null;
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
/*      */   public static void showWarning(String msg)
/*      */   {
/* 5824 */     if (warnings == null) {
/* 5825 */       warnings = new HashMap();
/*      */     }
/* 5827 */     if (!warnings.containsKey(msg)) {
/* 5828 */       System.err.println(msg);
/* 5829 */       warnings.put(msg, new Object());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void showDepthWarning(String method)
/*      */   {
/* 5839 */     showWarning(
/* 5840 */       method + "() can only be used with a renderer that " + "supports 3D, such as P3D or OPENGL.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void showDepthWarningXYZ(String method)
/*      */   {
/* 5850 */     showWarning(
/*      */     
/*      */ 
/* 5853 */       method + "() with x, y, and z coordinates " + "can only be used with a renderer that " + "supports 3D, such as P3D or OPENGL. " + "Use a version without a z-coordinate instead.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void showMethodWarning(String method)
/*      */   {
/* 5861 */     showWarning(method + "() is not available with this renderer.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void showVariationWarning(String str)
/*      */   {
/* 5871 */     showWarning(str + " is not available with this renderer.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void showMissingWarning(String method)
/*      */   {
/* 5881 */     showWarning(
/* 5882 */       method + "(), or this particular variation of it, " + "is not available with this renderer.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void showException(String msg)
/*      */   {
/* 5892 */     throw new RuntimeException(msg);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void defaultFontOrDeath(String method)
/*      */   {
/* 5900 */     defaultFontOrDeath(method, 12.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void defaultFontOrDeath(String method, float size)
/*      */   {
/* 5910 */     if (this.parent != null) {
/* 5911 */       this.textFont = this.parent.createDefaultFont(size);
/*      */     } else {
/* 5913 */       throw new RuntimeException("Use textFont() before " + method + "()");
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
/*      */   public boolean displayable()
/*      */   {
/* 5933 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean is2D()
/*      */   {
/* 5941 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean is3D()
/*      */   {
/* 5949 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String[] getSupportedShapeFormats()
/*      */   {
/* 5957 */     showMissingWarning("getSupportedShapeFormats");
/* 5958 */     return null;
/*      */   }
/*      */   
/*      */   protected PShape loadShape(String filename, Object params)
/*      */   {
/* 5963 */     showMissingWarning("loadShape");
/* 5964 */     return null;
/*      */   }
/*      */   
/*      */   protected PShape createShape(int size, Object params)
/*      */   {
/* 5969 */     showMissingWarning("createShape");
/* 5970 */     return null;
/*      */   }
/*      */   
/*      */   public void screenBlend(int mode)
/*      */   {
/* 5975 */     showMissingWarning("screenBlend");
/*      */   }
/*      */   
/*      */   public void textureBlend(int mode)
/*      */   {
/* 5980 */     showMissingWarning("textureBlend");
/*      */   }
/*      */   
/*      */   public PShape beginRecord()
/*      */   {
/* 5985 */     showMissingWarning("beginRecord");
/* 5986 */     return null;
/*      */   }
/*      */   
/*      */   public void endRecord()
/*      */   {
/* 5991 */     showMissingWarning("endRecord");
/*      */   }
/*      */   
/*      */   public boolean isRecording()
/*      */   {
/* 5996 */     showMissingWarning("isRecording");
/* 5997 */     return false;
/*      */   }
/*      */   
/*      */   public void mergeShapes(boolean val)
/*      */   {
/* 6002 */     showMissingWarning("mergeShapes");
/*      */   }
/*      */   
/*      */   public void shapeName(String name)
/*      */   {
/* 6007 */     showMissingWarning("shapeName");
/*      */   }
/*      */   
/*      */   public void autoNormal(boolean auto)
/*      */   {
/* 6012 */     this.autoNormal = auto;
/*      */   }
/*      */   
/*      */   public void matrixMode(int mode)
/*      */   {
/* 6017 */     showMissingWarning("matrixMode");
/*      */   }
/*      */   
/*      */   public void beginText()
/*      */   {
/* 6022 */     showMissingWarning("beginText");
/*      */   }
/*      */   
/*      */   public void endText()
/*      */   {
/* 6027 */     showMissingWarning("endText");
/*      */   }
/*      */   
/*      */   public void texture(PImage... images)
/*      */   {
/* 6032 */     showMissingWarning("texture");
/*      */   }
/*      */   
/*      */   public void vertex(float... values) {
/* 6036 */     showMissingWarning("vertex");
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PGraphics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */