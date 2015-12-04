/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.image.DirectColorModel;
/*      */ import java.awt.image.MemoryImageSource;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PGraphics3D
/*      */   extends PGraphics
/*      */ {
/*      */   public float[] zbuffer;
/*      */   public PMatrix3D modelview;
/*      */   public PMatrix3D modelviewInv;
/*      */   protected boolean sizeChanged;
/*      */   public PMatrix3D camera;
/*      */   protected PMatrix3D cameraInv;
/*      */   public float cameraFOV;
/*      */   public float cameraX;
/*      */   public float cameraY;
/*      */   public float cameraZ;
/*      */   public float cameraNear;
/*      */   public float cameraFar;
/*      */   public float cameraAspect;
/*      */   public PMatrix3D projection;
/*      */   public static final int MAX_LIGHTS = 8;
/*   88 */   public int lightCount = 0;
/*      */   
/*      */ 
/*      */   public int[] lightType;
/*      */   
/*      */ 
/*      */   public PVector[] lightPosition;
/*      */   
/*      */ 
/*      */   public PVector[] lightNormal;
/*      */   
/*      */ 
/*      */   public float[] lightFalloffConstant;
/*      */   
/*      */ 
/*      */   public float[] lightFalloffLinear;
/*      */   
/*      */   public float[] lightFalloffQuadratic;
/*      */   
/*      */   public float[] lightSpotAngle;
/*      */   
/*      */   public float[] lightSpotAngleCos;
/*      */   
/*      */   public float[] lightSpotConcentration;
/*      */   
/*      */   public float[][] lightDiffuse;
/*      */   
/*      */   public float[][] lightSpecular;
/*      */   
/*      */   public float[] currentLightSpecular;
/*      */   
/*      */   public float currentLightFalloffConstant;
/*      */   
/*      */   public float currentLightFalloffLinear;
/*      */   
/*      */   public float currentLightFalloffQuadratic;
/*      */   
/*      */   public static final int TRI_DIFFUSE_R = 0;
/*      */   
/*      */   public static final int TRI_DIFFUSE_G = 1;
/*      */   
/*      */   public static final int TRI_DIFFUSE_B = 2;
/*      */   
/*      */   public static final int TRI_DIFFUSE_A = 3;
/*      */   
/*      */   public static final int TRI_SPECULAR_R = 4;
/*      */   
/*      */   public static final int TRI_SPECULAR_G = 5;
/*      */   
/*      */   public static final int TRI_SPECULAR_B = 6;
/*      */   
/*      */   public static final int TRI_COLOR_COUNT = 7;
/*      */   
/*      */   private boolean lightingDependsOnVertexPosition;
/*      */   
/*      */   static final int LIGHT_AMBIENT_R = 0;
/*      */   
/*      */   static final int LIGHT_AMBIENT_G = 1;
/*      */   
/*      */   static final int LIGHT_AMBIENT_B = 2;
/*      */   
/*      */   static final int LIGHT_DIFFUSE_R = 3;
/*      */   
/*      */   static final int LIGHT_DIFFUSE_G = 4;
/*      */   
/*      */   static final int LIGHT_DIFFUSE_B = 5;
/*      */   
/*      */   static final int LIGHT_SPECULAR_R = 6;
/*      */   
/*      */   static final int LIGHT_SPECULAR_G = 7;
/*      */   
/*      */   static final int LIGHT_SPECULAR_B = 8;
/*      */   
/*      */   static final int LIGHT_COLOR_COUNT = 9;
/*      */   
/*  163 */   protected float[] tempLightingContribution = new float[9];
/*      */   
/*      */ 
/*      */ 
/*  167 */   protected PVector lightTriangleNorm = new PVector();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean manipulatingCamera;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  178 */   float[][] matrixStack = new float[32][16];
/*  179 */   float[][] matrixInvStack = new float[32][16];
/*      */   
/*      */   int matrixStackDepth;
/*  182 */   protected int matrixMode = 1;
/*  183 */   float[][] pmatrixStack = new float[32][16];
/*      */   
/*      */ 
/*      */   int pmatrixStackDepth;
/*      */   
/*      */ 
/*      */   protected PMatrix3D forwardTransform;
/*      */   
/*      */ 
/*      */   protected PMatrix3D reverseTransform;
/*      */   
/*      */ 
/*      */   protected float leftScreen;
/*      */   
/*      */   protected float rightScreen;
/*      */   
/*      */   protected float topScreen;
/*      */   
/*      */   protected float bottomScreen;
/*      */   
/*      */   protected float nearPlane;
/*      */   
/*  205 */   private boolean frustumMode = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  211 */   protected static boolean s_enableAccurateTextures = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PSmoothTriangle smoothTriangle;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int shapeFirst;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int shapeLast;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int shapeLastPlusClipped;
/*      */   
/*      */ 
/*      */ 
/*  232 */   protected int[] vertexOrder = new int['Ȁ'];
/*      */   
/*      */ 
/*      */ 
/*      */   protected int pathCount;
/*      */   
/*      */ 
/*      */ 
/*  240 */   protected int[] pathOffset = new int[64];
/*  241 */   protected int[] pathLength = new int[64];
/*      */   
/*      */ 
/*      */   protected static final int VERTEX1 = 0;
/*      */   
/*      */   protected static final int VERTEX2 = 1;
/*      */   
/*      */   protected static final int VERTEX3 = 2;
/*      */   
/*      */   protected static final int STROKE_COLOR = 1;
/*      */   
/*      */   protected static final int TEXTURE_INDEX = 3;
/*      */   
/*      */   protected static final int POINT_FIELD_COUNT = 2;
/*      */   
/*      */   protected static final int LINE_FIELD_COUNT = 2;
/*      */   
/*      */   protected static final int TRIANGLE_FIELD_COUNT = 4;
/*      */   
/*      */   static final int DEFAULT_POINTS = 512;
/*      */   
/*  262 */   protected int[][] points = new int['Ȁ'][2];
/*      */   
/*      */   protected int pointCount;
/*      */   
/*      */   static final int DEFAULT_LINES = 512;
/*      */   public PLine line;
/*  268 */   protected int[][] lines = new int['Ȁ'][2];
/*      */   
/*      */   protected int lineCount;
/*      */   
/*      */   static final int DEFAULT_TRIANGLES = 256;
/*      */   
/*      */   public PTriangle triangle;
/*  275 */   protected int[][] triangles = new int['Ā'][4];
/*      */   
/*  277 */   protected float[][][] triangleColors = new float['Ā'][3][7];
/*      */   
/*      */ 
/*      */   protected int triangleCount;
/*      */   
/*      */ 
/*      */   static final int DEFAULT_TEXTURES = 3;
/*      */   
/*      */ 
/*  286 */   protected PImage[] textures = new PImage[3];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int textureIndex;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   DirectColorModel cm;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MemoryImageSource mis;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSize(int iwidth, int iheight)
/*      */   {
/*  321 */     this.width = iwidth;
/*  322 */     this.height = iheight;
/*  323 */     this.width1 = (this.width - 1);
/*  324 */     this.height1 = (this.height - 1);
/*      */     
/*  326 */     allocate();
/*  327 */     reapplySettings();
/*      */     
/*      */ 
/*  330 */     this.lightType = new int[8];
/*  331 */     this.lightPosition = new PVector[8];
/*  332 */     this.lightNormal = new PVector[8];
/*  333 */     for (int i = 0; i < 8; i++) {
/*  334 */       this.lightPosition[i] = new PVector();
/*  335 */       this.lightNormal[i] = new PVector();
/*      */     }
/*  337 */     this.lightDiffuse = new float[8][3];
/*  338 */     this.lightSpecular = new float[8][3];
/*  339 */     this.lightFalloffConstant = new float[8];
/*  340 */     this.lightFalloffLinear = new float[8];
/*  341 */     this.lightFalloffQuadratic = new float[8];
/*  342 */     this.lightSpotAngle = new float[8];
/*  343 */     this.lightSpotAngleCos = new float[8];
/*  344 */     this.lightSpotConcentration = new float[8];
/*  345 */     this.currentLightSpecular = new float[3];
/*      */     
/*  347 */     this.projection = new PMatrix3D();
/*  348 */     this.modelview = new PMatrix3D();
/*  349 */     this.modelviewInv = new PMatrix3D();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  355 */     this.forwardTransform = this.modelview;
/*  356 */     this.reverseTransform = this.modelviewInv;
/*      */     
/*      */ 
/*  359 */     this.cameraFOV = 1.0471976F;
/*  360 */     this.cameraX = (this.width / 2.0F);
/*  361 */     this.cameraY = (this.height / 2.0F);
/*  362 */     this.cameraZ = (this.cameraY / (float)Math.tan(this.cameraFOV / 2.0F));
/*  363 */     this.cameraNear = (this.cameraZ / 10.0F);
/*  364 */     this.cameraFar = (this.cameraZ * 10.0F);
/*  365 */     this.cameraAspect = (this.width / this.height);
/*      */     
/*  367 */     this.camera = new PMatrix3D();
/*  368 */     this.cameraInv = new PMatrix3D();
/*      */     
/*      */ 
/*  371 */     this.sizeChanged = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void allocate()
/*      */   {
/*  379 */     this.pixelCount = (this.width * this.height);
/*  380 */     this.pixels = new int[this.pixelCount];
/*  381 */     this.zbuffer = new float[this.pixelCount];
/*      */     
/*  383 */     if (this.primarySurface) {
/*  384 */       this.cm = new DirectColorModel(32, 16711680, 65280, 255);
/*  385 */       this.mis = new MemoryImageSource(this.width, this.height, this.pixels, 0, this.width);
/*  386 */       this.mis.setFullBufferUpdates(true);
/*  387 */       this.mis.setAnimated(true);
/*  388 */       this.image = Toolkit.getDefaultToolkit().createImage(this.mis);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  393 */       Arrays.fill(this.zbuffer, Float.MAX_VALUE);
/*      */     }
/*      */     
/*  396 */     this.line = new PLine(this);
/*  397 */     this.triangle = new PTriangle(this);
/*  398 */     this.smoothTriangle = new PSmoothTriangle(this);
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
/*      */   public void beginDraw()
/*      */   {
/*  415 */     if (!this.settingsInited) { defaultSettings();
/*      */     }
/*  417 */     if (this.sizeChanged)
/*      */     {
/*  419 */       camera();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  424 */       perspective();
/*      */       
/*      */ 
/*  427 */       this.sizeChanged = false;
/*      */     }
/*      */     
/*  430 */     resetMatrix();
/*      */     
/*      */ 
/*  433 */     this.vertexCount = 0;
/*      */     
/*  435 */     this.modelview.set(this.camera);
/*  436 */     this.modelviewInv.set(this.cameraInv);
/*      */     
/*      */ 
/*  439 */     this.lightCount = 0;
/*  440 */     this.lightingDependsOnVertexPosition = false;
/*  441 */     lightFalloff(1.0F, 0.0F, 0.0F);
/*  442 */     lightSpecular(0.0F, 0.0F, 0.0F);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  455 */     this.shapeFirst = 0;
/*      */     
/*      */ 
/*  458 */     Arrays.fill(this.textures, null);
/*  459 */     this.textureIndex = 0;
/*      */     
/*  461 */     normal(0.0F, 0.0F, 1.0F);
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
/*      */   public void endDraw()
/*      */   {
/*  475 */     if (this.hints[5] != 0) {
/*  476 */       flush();
/*      */     }
/*  478 */     if (this.mis != null) {
/*  479 */       this.mis.newPixels(this.pixels, this.cm, 0, this.width);
/*      */     }
/*      */     
/*      */ 
/*  483 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void defaultSettings()
/*      */   {
/*  494 */     super.defaultSettings();
/*      */     
/*  496 */     this.manipulatingCamera = false;
/*  497 */     this.forwardTransform = this.modelview;
/*  498 */     this.reverseTransform = this.modelviewInv;
/*      */     
/*      */ 
/*  501 */     camera();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  506 */     perspective();
/*      */     
/*      */ 
/*  509 */     textureMode(2);
/*      */     
/*  511 */     emissive(0.0F);
/*  512 */     specular(0.5F);
/*  513 */     shininess(1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void hint(int which)
/*      */   {
/*  524 */     if (which == -5) {
/*  525 */       flush();
/*  526 */     } else if ((which == 4) && 
/*  527 */       (this.zbuffer != null)) {
/*  528 */       Arrays.fill(this.zbuffer, Float.MAX_VALUE);
/*      */     }
/*      */     
/*  531 */     super.hint(which);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void beginShape(int kind)
/*      */   {
/*  542 */     this.shape = kind;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  549 */     if (this.hints[5] != 0)
/*      */     {
/*      */ 
/*  552 */       this.shapeFirst = this.vertexCount;
/*  553 */       this.shapeLast = 0;
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  558 */       this.vertexCount = 0;
/*  559 */       if (this.line != null) this.line.reset();
/*  560 */       this.lineCount = 0;
/*      */       
/*  562 */       if (this.triangle != null) this.triangle.reset();
/*  563 */       this.triangleCount = 0;
/*      */     }
/*      */     
/*  566 */     this.textureImage = null;
/*  567 */     this.curveVertexCount = 0;
/*  568 */     this.normalMode = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void texture(PImage image)
/*      */   {
/*  580 */     this.textureImage = image;
/*      */     
/*  582 */     if (this.textureIndex == this.textures.length - 1) {
/*  583 */       this.textures = ((PImage[])PApplet.expand(this.textures));
/*      */     }
/*  585 */     if (this.textures[this.textureIndex] != null) {
/*  586 */       this.textureIndex += 1;
/*      */     }
/*  588 */     this.textures[this.textureIndex] = image;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void vertex(float x, float y)
/*      */   {
/*  595 */     vertex(x, y, 0.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void vertex(float x, float y, float u, float v)
/*      */   {
/*  604 */     vertex(x, y, 0.0F, u, v);
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
/*      */   public void endShape(int mode)
/*      */   {
/*  618 */     this.shapeLast = this.vertexCount;
/*  619 */     this.shapeLastPlusClipped = this.shapeLast;
/*      */     
/*      */ 
/*      */ 
/*  623 */     if (this.vertexCount == 0) {
/*  624 */       this.shape = 0;
/*  625 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  630 */     endShapeModelToCamera(this.shapeFirst, this.shapeLast);
/*      */     
/*  632 */     if (this.stroke) {
/*  633 */       endShapeStroke(mode);
/*      */     }
/*      */     
/*  636 */     if ((this.fill) || (this.textureImage != null)) {
/*  637 */       endShapeFill();
/*      */     }
/*      */     
/*      */ 
/*  641 */     endShapeLighting((this.lightCount > 0) && (this.fill));
/*      */     
/*      */ 
/*      */ 
/*  645 */     endShapeCameraToScreen(this.shapeFirst, this.shapeLastPlusClipped);
/*      */     
/*      */ 
/*      */ 
/*  649 */     if (this.hints[5] == 0) {
/*  650 */       if (((this.fill) || (this.textureImage != null)) && 
/*  651 */         (this.triangleCount > 0)) {
/*  652 */         renderTriangles(0, this.triangleCount);
/*  653 */         if (this.raw != null) {
/*  654 */           rawTriangles(0, this.triangleCount);
/*      */         }
/*  656 */         this.triangleCount = 0;
/*      */       }
/*      */       
/*  659 */       if (this.stroke) {
/*  660 */         if (this.pointCount > 0) {
/*  661 */           renderPoints(0, this.pointCount);
/*  662 */           if (this.raw != null) {
/*  663 */             rawPoints(0, this.pointCount);
/*      */           }
/*  665 */           this.pointCount = 0;
/*      */         }
/*      */         
/*  668 */         if (this.lineCount > 0) {
/*  669 */           renderLines(0, this.lineCount);
/*  670 */           if (this.raw != null) {
/*  671 */             rawLines(0, this.lineCount);
/*      */           }
/*  673 */           this.lineCount = 0;
/*      */         }
/*      */       }
/*  676 */       this.pathCount = 0;
/*      */     }
/*      */     
/*  679 */     this.shape = 0;
/*      */   }
/*      */   
/*      */   protected void endShapeModelToCamera(int start, int stop)
/*      */   {
/*  684 */     for (int i = start; i < stop; i++) {
/*  685 */       float[] vertex = this.vertices[i];
/*      */       
/*  687 */       vertex[21] = 
/*  688 */         (this.modelview.m00 * vertex[0] + this.modelview.m01 * vertex[1] + 
/*  689 */         this.modelview.m02 * vertex[2] + this.modelview.m03);
/*  690 */       vertex[22] = 
/*  691 */         (this.modelview.m10 * vertex[0] + this.modelview.m11 * vertex[1] + 
/*  692 */         this.modelview.m12 * vertex[2] + this.modelview.m13);
/*  693 */       vertex[23] = 
/*  694 */         (this.modelview.m20 * vertex[0] + this.modelview.m21 * vertex[1] + 
/*  695 */         this.modelview.m22 * vertex[2] + this.modelview.m23);
/*  696 */       vertex[24] = 
/*  697 */         (this.modelview.m30 * vertex[0] + this.modelview.m31 * vertex[1] + 
/*  698 */         this.modelview.m32 * vertex[2] + this.modelview.m33);
/*      */       
/*      */ 
/*  701 */       if ((vertex[24] != 0.0F) && (vertex[24] != 1.0F)) {
/*  702 */         vertex[21] /= vertex[24];
/*  703 */         vertex[22] /= vertex[24];
/*  704 */         vertex[23] /= vertex[24];
/*      */       }
/*  706 */       vertex[24] = 1.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   protected void endShapeStroke(int mode)
/*      */   {
/*  712 */     switch (this.shape)
/*      */     {
/*      */     case 2: 
/*  715 */       int stop = this.shapeLast;
/*  716 */       for (int i = this.shapeFirst; i < stop; i++)
/*      */       {
/*  718 */         addPoint(i);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  725 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 4: 
/*  730 */       int first = this.lineCount;
/*  731 */       int stop = this.shapeLast - 1;
/*      */       
/*      */ 
/*      */ 
/*  735 */       if (this.shape != 4) { addLineBreak();
/*      */       }
/*  737 */       for (int i = this.shapeFirst; i < stop; i += 2)
/*      */       {
/*  739 */         if (this.shape == 4) addLineBreak();
/*  740 */         addLine(i, i + 1);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  745 */       if (mode == 2) {
/*  746 */         addLine(stop, this.lines[first][0]);
/*      */       }
/*      */       
/*  749 */       break;
/*      */     
/*      */ 
/*      */     case 9: 
/*  753 */       for (int i = this.shapeFirst; i < this.shapeLast - 2; i += 3) {
/*  754 */         addLineBreak();
/*      */         
/*  756 */         addLine(i + 0, i + 1);
/*  757 */         addLine(i + 1, i + 2);
/*  758 */         addLine(i + 2, i + 0);
/*      */       }
/*      */       
/*  761 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 10: 
/*  766 */       int stop = this.shapeLast - 1;
/*      */       
/*  768 */       addLineBreak();
/*  769 */       for (int i = this.shapeFirst; i < stop; i++)
/*      */       {
/*  771 */         addLine(i, i + 1);
/*      */       }
/*      */       
/*      */ 
/*  775 */       stop = this.shapeLast - 2;
/*  776 */       for (int i = this.shapeFirst; i < stop; i++) {
/*  777 */         addLineBreak();
/*  778 */         addLine(i, i + 2);
/*      */       }
/*      */       
/*  781 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 11: 
/*  787 */       for (int i = this.shapeFirst + 1; i < this.shapeLast; i++) {
/*  788 */         addLineBreak();
/*  789 */         addLine(this.shapeFirst, i);
/*      */       }
/*      */       
/*      */ 
/*  793 */       addLineBreak();
/*  794 */       for (int i = this.shapeFirst + 1; i < this.shapeLast - 1; i++) {
/*  795 */         addLine(i, i + 1);
/*      */       }
/*      */       
/*  798 */       addLine(this.shapeLast - 1, this.shapeFirst + 1);
/*      */       
/*  800 */       break;
/*      */     
/*      */ 
/*      */     case 16: 
/*  804 */       for (int i = this.shapeFirst; i < this.shapeLast; i += 4) {
/*  805 */         addLineBreak();
/*      */         
/*  807 */         addLine(i + 0, i + 1);
/*  808 */         addLine(i + 1, i + 2);
/*  809 */         addLine(i + 2, i + 3);
/*  810 */         addLine(i + 3, i + 0);
/*      */       }
/*      */       
/*  813 */       break;
/*      */     
/*      */ 
/*      */     case 17: 
/*  817 */       for (int i = this.shapeFirst; i < this.shapeLast - 3; i += 2) {
/*  818 */         addLineBreak();
/*  819 */         addLine(i + 0, i + 2);
/*  820 */         addLine(i + 2, i + 3);
/*  821 */         addLine(i + 3, i + 1);
/*  822 */         addLine(i + 1, i + 0);
/*      */       }
/*      */       
/*  825 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 20: 
/*  830 */       int stop = this.shapeLast - 1;
/*      */       
/*  832 */       addLineBreak();
/*  833 */       for (int i = this.shapeFirst; i < stop; i++) {
/*  834 */         addLine(i, i + 1);
/*      */       }
/*  836 */       if (mode == 2)
/*      */       {
/*  838 */         addLine(stop, this.shapeFirst);
/*      */       }
/*      */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */   protected void endShapeFill()
/*      */   {
/*  847 */     switch (this.shape)
/*      */     {
/*      */     case 11: 
/*  850 */       int stop = this.shapeLast - 1;
/*  851 */       for (int i = this.shapeFirst + 1; i < stop; i++) {
/*  852 */         addTriangle(this.shapeFirst, i, i + 1);
/*      */       }
/*      */       
/*  855 */       break;
/*      */     
/*      */ 
/*      */     case 9: 
/*  859 */       int stop = this.shapeLast - 2;
/*  860 */       for (int i = this.shapeFirst; i < stop; i += 3)
/*      */       {
/*      */ 
/*  863 */         if (i % 2 == 0) {
/*  864 */           addTriangle(i, i + 2, i + 1);
/*      */         } else {
/*  866 */           addTriangle(i, i + 1, i + 2);
/*      */         }
/*      */       }
/*      */       
/*  870 */       break;
/*      */     
/*      */ 
/*      */     case 10: 
/*  874 */       int stop = this.shapeLast - 2;
/*  875 */       for (int i = this.shapeFirst; i < stop; i++)
/*      */       {
/*      */ 
/*  878 */         if (i % 2 == 0) {
/*  879 */           addTriangle(i, i + 2, i + 1);
/*      */         } else {
/*  881 */           addTriangle(i, i + 1, i + 2);
/*      */         }
/*      */       }
/*      */       
/*  885 */       break;
/*      */     
/*      */ 
/*      */     case 16: 
/*  889 */       int stop = this.vertexCount - 3;
/*  890 */       for (int i = this.shapeFirst; i < stop; i += 4)
/*      */       {
/*  892 */         addTriangle(i, i + 1, i + 2);
/*      */         
/*  894 */         addTriangle(i, i + 2, i + 3);
/*      */       }
/*      */       
/*  897 */       break;
/*      */     
/*      */ 
/*      */     case 17: 
/*  901 */       int stop = this.vertexCount - 3;
/*  902 */       for (int i = this.shapeFirst; i < stop; i += 2)
/*      */       {
/*  904 */         addTriangle(i + 0, i + 2, i + 1);
/*      */         
/*  906 */         addTriangle(i + 2, i + 3, i + 1);
/*      */       }
/*      */       
/*  909 */       break;
/*      */     
/*      */ 
/*      */     case 20: 
/*  913 */       addPolygonTriangles();
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */   protected void endShapeLighting(boolean lights)
/*      */   {
/*  921 */     if (lights)
/*      */     {
/*      */ 
/*      */ 
/*  925 */       if ((!this.lightingDependsOnVertexPosition) && (this.normalMode == 1)) {
/*  926 */         calcLightingContribution(this.shapeFirst, this.tempLightingContribution);
/*  927 */         for (int tri = 0; tri < this.triangleCount; tri++) {
/*  928 */           lightTriangle(tri, this.tempLightingContribution);
/*      */         }
/*      */       } else {
/*  931 */         for (int tri = 0; tri < this.triangleCount; tri++) {
/*  932 */           lightTriangle(tri);
/*      */         }
/*      */       }
/*      */     } else {
/*  936 */       for (int tri = 0; tri < this.triangleCount; tri++) {
/*  937 */         int index = this.triangles[tri][0];
/*  938 */         copyPrelitVertexColor(tri, index, 0);
/*  939 */         index = this.triangles[tri][1];
/*  940 */         copyPrelitVertexColor(tri, index, 1);
/*  941 */         index = this.triangles[tri][2];
/*  942 */         copyPrelitVertexColor(tri, index, 2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void endShapeCameraToScreen(int start, int stop)
/*      */   {
/*  949 */     for (int i = start; i < stop; i++) {
/*  950 */       float[] vx = this.vertices[i];
/*      */       
/*  952 */       float ox = 
/*  953 */         this.projection.m00 * vx[21] + this.projection.m01 * vx[22] + 
/*  954 */         this.projection.m02 * vx[23] + this.projection.m03 * vx[24];
/*  955 */       float oy = 
/*  956 */         this.projection.m10 * vx[21] + this.projection.m11 * vx[22] + 
/*  957 */         this.projection.m12 * vx[23] + this.projection.m13 * vx[24];
/*  958 */       float oz = 
/*  959 */         this.projection.m20 * vx[21] + this.projection.m21 * vx[22] + 
/*  960 */         this.projection.m22 * vx[23] + this.projection.m23 * vx[24];
/*  961 */       float ow = 
/*  962 */         this.projection.m30 * vx[21] + this.projection.m31 * vx[22] + 
/*  963 */         this.projection.m32 * vx[23] + this.projection.m33 * vx[24];
/*      */       
/*  965 */       if ((ow != 0.0F) && (ow != 1.0F)) {
/*  966 */         ox /= ow;oy /= ow;oz /= ow;
/*      */       }
/*      */       
/*  969 */       vx[18] = (this.width * (1.0F + ox) / 2.0F);
/*  970 */       vx[19] = (this.height * (1.0F + oy) / 2.0F);
/*  971 */       vx[20] = ((oz + 1.0F) / 2.0F);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void addPoint(int a)
/*      */   {
/*  983 */     if (this.pointCount == this.points.length) {
/*  984 */       int[][] temp = new int[this.pointCount << 1][2];
/*  985 */       System.arraycopy(this.points, 0, temp, 0, this.pointCount);
/*  986 */       this.points = temp;
/*      */     }
/*  988 */     this.points[this.pointCount][0] = a;
/*      */     
/*  990 */     this.points[this.pointCount][1] = this.strokeColor;
/*      */     
/*  992 */     this.pointCount += 1;
/*      */   }
/*      */   
/*      */   protected void renderPoints(int start, int stop)
/*      */   {
/*  997 */     if (this.strokeWeight != 1.0F) {
/*  998 */       for (int i = start; i < stop; i++) {
/*  999 */         float[] a = this.vertices[this.points[i][0]];
/* 1000 */         renderLineVertices(a, a);
/*      */       }
/*      */     } else {
/* 1003 */       for (int i = start; i < stop; i++) {
/* 1004 */         float[] a = this.vertices[this.points[i][0]];
/* 1005 */         int sx = (int)(a[18] + 0.4999F);
/* 1006 */         int sy = (int)(a[19] + 0.4999F);
/* 1007 */         if ((sx >= 0) && (sx < this.width) && (sy >= 0) && (sy < this.height)) {
/* 1008 */           int index = sy * this.width + sx;
/* 1009 */           this.pixels[index] = this.points[i][1];
/* 1010 */           this.zbuffer[index] = a[20];
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void rawPoints(int start, int stop)
/*      */   {
/* 1073 */     this.raw.colorMode(1, 1.0F);
/* 1074 */     this.raw.noFill();
/* 1075 */     this.raw.strokeWeight(this.vertices[this.lines[start][0]][17]);
/* 1076 */     this.raw.beginShape(2);
/*      */     
/* 1078 */     for (int i = start; i < stop; i++) {
/* 1079 */       float[] a = this.vertices[this.lines[i][0]];
/*      */       
/* 1081 */       if (this.raw.is3D()) {
/* 1082 */         if (a[24] != 0.0F) {
/* 1083 */           this.raw.stroke(a[13], a[14], a[15], a[16]);
/* 1084 */           this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
/*      */         }
/*      */       } else {
/* 1087 */         this.raw.stroke(a[13], a[14], a[15], a[16]);
/* 1088 */         this.raw.vertex(a[18], a[19]);
/*      */       }
/*      */     }
/* 1091 */     this.raw.endShape();
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
/*      */   protected final void addLineBreak()
/*      */   {
/* 1105 */     if (this.pathCount == this.pathOffset.length) {
/* 1106 */       this.pathOffset = PApplet.expand(this.pathOffset);
/* 1107 */       this.pathLength = PApplet.expand(this.pathLength);
/*      */     }
/* 1109 */     this.pathOffset[this.pathCount] = this.lineCount;
/* 1110 */     this.pathLength[this.pathCount] = 0;
/* 1111 */     this.pathCount += 1;
/*      */   }
/*      */   
/*      */   protected void addLine(int a, int b)
/*      */   {
/* 1116 */     addLineWithClip(a, b);
/*      */   }
/*      */   
/*      */   protected final void addLineWithClip(int a, int b)
/*      */   {
/* 1121 */     float az = this.vertices[a][23];
/* 1122 */     float bz = this.vertices[b][23];
/* 1123 */     if (az > this.cameraNear) {
/* 1124 */       if (bz > this.cameraNear) {
/* 1125 */         return;
/*      */       }
/* 1127 */       int cb = interpolateClipVertex(a, b);
/* 1128 */       addLineWithoutClip(cb, b);
/* 1129 */       return;
/*      */     }
/*      */     
/* 1132 */     if (bz <= this.cameraNear) {
/* 1133 */       addLineWithoutClip(a, b);
/* 1134 */       return;
/*      */     }
/* 1136 */     int cb = interpolateClipVertex(a, b);
/* 1137 */     addLineWithoutClip(a, cb);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected final void addLineWithoutClip(int a, int b)
/*      */   {
/* 1144 */     if (this.lineCount == this.lines.length) {
/* 1145 */       int[][] temp = new int[this.lineCount << 1][2];
/* 1146 */       System.arraycopy(this.lines, 0, temp, 0, this.lineCount);
/* 1147 */       this.lines = temp;
/*      */     }
/* 1149 */     this.lines[this.lineCount][0] = a;
/* 1150 */     this.lines[this.lineCount][1] = b;
/*      */     
/*      */ 
/*      */ 
/* 1154 */     this.lineCount += 1;
/*      */     
/*      */ 
/* 1157 */     this.pathLength[(this.pathCount - 1)] += 1;
/*      */   }
/*      */   
/*      */   protected void renderLines(int start, int stop)
/*      */   {
/* 1162 */     for (int i = start; i < stop; i++) {
/* 1163 */       renderLineVertices(this.vertices[this.lines[i][0]], 
/* 1164 */         this.vertices[this.lines[i][1]]);
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
/*      */   protected void renderLineVertices(float[] a, float[] b)
/*      */   {
/* 1202 */     if ((a[17] > 1.25F) || (a[17] < 0.75F)) {
/* 1203 */       float ox1 = a[18];
/* 1204 */       float oy1 = a[19];
/* 1205 */       float ox2 = b[18];
/* 1206 */       float oy2 = b[19];
/*      */       
/*      */ 
/* 1209 */       float weight = a[17] / 2.0F;
/*      */       
/*      */ 
/* 1212 */       if ((ox1 == ox2) && (oy1 == oy2)) {
/* 1213 */         oy1 -= weight;
/* 1214 */         oy2 += weight;
/*      */       }
/*      */       
/* 1217 */       float dX = ox2 - ox1 + 1.0E-4F;
/* 1218 */       float dY = oy2 - oy1 + 1.0E-4F;
/* 1219 */       float len = (float)Math.sqrt(dX * dX + dY * dY);
/*      */       
/* 1221 */       float rh = weight / len;
/*      */       
/* 1223 */       float dx0 = rh * dY;
/* 1224 */       float dy0 = rh * dX;
/* 1225 */       float dx1 = rh * dY;
/* 1226 */       float dy1 = rh * dX;
/*      */       
/* 1228 */       float ax1 = ox1 + dx0;
/* 1229 */       float ay1 = oy1 - dy0;
/*      */       
/* 1231 */       float ax2 = ox1 - dx0;
/* 1232 */       float ay2 = oy1 + dy0;
/*      */       
/* 1234 */       float bx1 = ox2 + dx1;
/* 1235 */       float by1 = oy2 - dy1;
/*      */       
/* 1237 */       float bx2 = ox2 - dx1;
/* 1238 */       float by2 = oy2 + dy1;
/*      */       
/* 1240 */       if (this.smooth) {
/* 1241 */         this.smoothTriangle.reset(3);
/* 1242 */         this.smoothTriangle.smooth = true;
/* 1243 */         this.smoothTriangle.interpARGB = true;
/*      */         
/*      */ 
/* 1246 */         this.smoothTriangle.setVertices(ax1, ay1, a[20], 
/* 1247 */           bx2, by2, b[20], 
/* 1248 */           ax2, ay2, a[20]);
/* 1249 */         this.smoothTriangle.setIntensities(a[13], a[14], a[15], a[16], 
/* 1250 */           b[13], b[14], b[15], b[16], 
/* 1251 */           a[13], a[14], a[15], a[16]);
/* 1252 */         this.smoothTriangle.render();
/*      */         
/*      */ 
/* 1255 */         this.smoothTriangle.setVertices(ax1, ay1, a[20], 
/* 1256 */           bx2, by2, b[20], 
/* 1257 */           bx1, by1, b[20]);
/* 1258 */         this.smoothTriangle.setIntensities(a[13], a[14], a[15], a[16], 
/* 1259 */           b[13], b[14], b[15], b[16], 
/* 1260 */           b[13], b[14], b[15], b[16]);
/* 1261 */         this.smoothTriangle.render();
/*      */       }
/*      */       else {
/* 1264 */         this.triangle.reset();
/*      */         
/*      */ 
/* 1267 */         this.triangle.setVertices(ax1, ay1, a[20], 
/* 1268 */           bx2, by2, b[20], 
/* 1269 */           ax2, ay2, a[20]);
/* 1270 */         this.triangle.setIntensities(a[13], a[14], a[15], a[16], 
/* 1271 */           b[13], b[14], b[15], b[16], 
/* 1272 */           a[13], a[14], a[15], a[16]);
/* 1273 */         this.triangle.render();
/*      */         
/*      */ 
/* 1276 */         this.triangle.setVertices(ax1, ay1, a[20], 
/* 1277 */           bx2, by2, b[20], 
/* 1278 */           bx1, by1, b[20]);
/* 1279 */         this.triangle.setIntensities(a[13], a[14], a[15], a[16], 
/* 1280 */           b[13], b[14], b[15], b[16], 
/* 1281 */           b[13], b[14], b[15], b[16]);
/* 1282 */         this.triangle.render();
/*      */       }
/*      */     }
/*      */     else {
/* 1286 */       this.line.reset();
/*      */       
/* 1288 */       this.line.setIntensities(a[13], a[14], a[15], a[16], 
/* 1289 */         b[13], b[14], b[15], b[16]);
/*      */       
/* 1291 */       this.line.setVertices(a[18], a[19], a[20], 
/* 1292 */         b[18], b[19], b[20]);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1310 */       this.line.draw();
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
/*      */   protected void rawLines(int start, int stop)
/*      */   {
/* 1329 */     this.raw.colorMode(1, 1.0F);
/* 1330 */     this.raw.noFill();
/* 1331 */     this.raw.beginShape(4);
/*      */     
/* 1333 */     for (int i = start; i < stop; i++) {
/* 1334 */       float[] a = this.vertices[this.lines[i][0]];
/* 1335 */       float[] b = this.vertices[this.lines[i][1]];
/* 1336 */       this.raw.strokeWeight(this.vertices[this.lines[i][1]][17]);
/*      */       
/* 1338 */       if (this.raw.is3D()) {
/* 1339 */         if ((a[24] != 0.0F) && (b[24] != 0.0F)) {
/* 1340 */           this.raw.stroke(a[13], a[14], a[15], a[16]);
/* 1341 */           this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
/* 1342 */           this.raw.stroke(b[13], b[14], b[15], b[16]);
/* 1343 */           this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24]);
/*      */         }
/* 1345 */       } else if (this.raw.is2D()) {
/* 1346 */         this.raw.stroke(a[13], a[14], a[15], a[16]);
/* 1347 */         this.raw.vertex(a[18], a[19]);
/* 1348 */         this.raw.stroke(b[13], b[14], b[15], b[16]);
/* 1349 */         this.raw.vertex(b[18], b[19]);
/*      */       }
/*      */     }
/* 1352 */     this.raw.endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void addTriangle(int a, int b, int c)
/*      */   {
/* 1363 */     addTriangleWithClip(a, b, c);
/*      */   }
/*      */   
/*      */   protected final void addTriangleWithClip(int a, int b, int c)
/*      */   {
/* 1368 */     boolean aClipped = false;
/* 1369 */     boolean bClipped = false;
/* 1370 */     int clippedCount = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1375 */     this.cameraNear = -8.0F;
/* 1376 */     if (this.vertices[a][23] > this.cameraNear) {
/* 1377 */       aClipped = true;
/* 1378 */       clippedCount++;
/*      */     }
/* 1380 */     if (this.vertices[b][23] > this.cameraNear) {
/* 1381 */       bClipped = true;
/* 1382 */       clippedCount++;
/*      */     }
/* 1384 */     if (this.vertices[c][23] > this.cameraNear)
/*      */     {
/* 1386 */       clippedCount++;
/*      */     }
/* 1388 */     if (clippedCount == 0)
/*      */     {
/*      */ 
/*      */ 
/* 1392 */       addTriangleWithoutClip(a, b, c);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 1398 */     else if (clippedCount != 3)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 1403 */       if (clippedCount == 2) { int cc;
/*      */         int ca;
/*      */         int cb;
/*      */         int cc;
/* 1407 */         if (!aClipped) {
/* 1408 */           int ca = a;
/* 1409 */           int cb = b;
/* 1410 */           cc = c;
/*      */         } else { int cc;
/* 1412 */           if (!bClipped) {
/* 1413 */             int ca = b;
/* 1414 */             int cb = a;
/* 1415 */             cc = c;
/*      */           }
/*      */           else {
/* 1418 */             ca = c;
/* 1419 */             cb = b;
/* 1420 */             cc = a;
/*      */           }
/*      */         }
/* 1423 */         int cd = interpolateClipVertex(ca, cb);
/* 1424 */         int ce = interpolateClipVertex(ca, cc);
/* 1425 */         addTriangleWithoutClip(ca, cd, ce);
/*      */       }
/*      */       else
/*      */       {
/*      */         int cc;
/*      */         
/*      */         int ca;
/*      */         
/*      */         int cb;
/*      */         int cc;
/* 1435 */         if (aClipped)
/*      */         {
/* 1437 */           int ca = c;
/* 1438 */           int cb = b;
/* 1439 */           cc = a;
/*      */         } else { int cc;
/* 1441 */           if (bClipped)
/*      */           {
/* 1443 */             int ca = a;
/* 1444 */             int cb = c;
/* 1445 */             cc = b;
/*      */           }
/*      */           else
/*      */           {
/* 1449 */             ca = a;
/* 1450 */             cb = b;
/* 1451 */             cc = c;
/*      */           }
/*      */         }
/* 1454 */         int cd = interpolateClipVertex(ca, cc);
/* 1455 */         int ce = interpolateClipVertex(cb, cc);
/* 1456 */         addTriangleWithoutClip(ca, cd, cb);
/*      */         
/*      */ 
/*      */ 
/* 1460 */         addTriangleWithoutClip(cb, cd, ce);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected final int interpolateClipVertex(int a, int b) {
/*      */     float[] vb;
/*      */     float[] va;
/*      */     float[] vb;
/* 1469 */     if (this.vertices[a][23] < this.vertices[b][23]) {
/* 1470 */       float[] va = this.vertices[b];
/* 1471 */       vb = this.vertices[a];
/*      */     }
/*      */     else {
/* 1474 */       va = this.vertices[a];
/* 1475 */       vb = this.vertices[b];
/*      */     }
/* 1477 */     float az = va[23];
/* 1478 */     float bz = vb[23];
/*      */     
/* 1480 */     float dz = az - bz;
/*      */     
/* 1482 */     if (dz == 0.0F) {
/* 1483 */       return a;
/*      */     }
/*      */     
/*      */ 
/* 1487 */     float pa = (this.cameraNear - bz) / dz;
/* 1488 */     float pb = 1.0F - pa;
/*      */     
/* 1490 */     vertex(pa * va[0] + pb * vb[0], 
/* 1491 */       pa * va[1] + pb * vb[1], 
/* 1492 */       pa * va[2] + pb * vb[2]);
/* 1493 */     int irv = this.vertexCount - 1;
/* 1494 */     this.shapeLastPlusClipped += 1;
/*      */     
/* 1496 */     float[] rv = this.vertices[irv];
/*      */     
/* 1498 */     rv[18] = (pa * va[18] + pb * vb[18]);
/* 1499 */     rv[19] = (pa * va[19] + pb * vb[19]);
/* 1500 */     rv[20] = (pa * va[20] + pb * vb[20]);
/*      */     
/* 1502 */     rv[21] = (pa * va[21] + pb * vb[21]);
/* 1503 */     rv[22] = (pa * va[22] + pb * vb[22]);
/* 1504 */     rv[23] = (pa * va[23] + pb * vb[23]);
/* 1505 */     rv[24] = (pa * va[24] + pb * vb[24]);
/*      */     
/* 1507 */     rv[3] = (pa * va[3] + pb * vb[3]);
/* 1508 */     rv[4] = (pa * va[4] + pb * vb[4]);
/* 1509 */     rv[5] = (pa * va[5] + pb * vb[5]);
/* 1510 */     rv[6] = (pa * va[6] + pb * vb[6]);
/*      */     
/* 1512 */     rv[7] = (pa * va[7] + pb * vb[7]);
/* 1513 */     rv[8] = (pa * va[8] + pb * vb[8]);
/*      */     
/* 1515 */     rv[13] = (pa * va[13] + pb * vb[13]);
/* 1516 */     rv[14] = (pa * va[14] + pb * vb[14]);
/* 1517 */     rv[15] = (pa * va[15] + pb * vb[15]);
/* 1518 */     rv[16] = (pa * va[16] + pb * vb[16]);
/*      */     
/* 1520 */     rv[9] = (pa * va[9] + pb * vb[9]);
/* 1521 */     rv[10] = (pa * va[10] + pb * vb[10]);
/* 1522 */     rv[11] = (pa * va[11] + pb * vb[11]);
/*      */     
/*      */ 
/*      */ 
/* 1526 */     rv[25] = (pa * va[25] + pb * vb[25]);
/* 1527 */     rv[26] = (pa * va[26] + pb * vb[26]);
/* 1528 */     rv[27] = (pa * va[27] + pb * vb[27]);
/*      */     
/* 1530 */     rv[28] = (pa * va[28] + pb * vb[28]);
/* 1531 */     rv[29] = (pa * va[29] + pb * vb[29]);
/* 1532 */     rv[30] = (pa * va[30] + pb * vb[30]);
/*      */     
/*      */ 
/* 1535 */     rv[32] = (pa * va[32] + pb * vb[32]);
/* 1536 */     rv[33] = (pa * va[33] + pb * vb[33]);
/* 1537 */     rv[34] = (pa * va[34] + pb * vb[34]);
/*      */     
/* 1539 */     rv[31] = (pa * va[31] + pb * vb[31]);
/*      */     
/* 1541 */     rv[35] = 0.0F;
/*      */     
/* 1543 */     return irv;
/*      */   }
/*      */   
/*      */   protected final void addTriangleWithoutClip(int a, int b, int c)
/*      */   {
/* 1548 */     if (this.triangleCount == this.triangles.length) {
/* 1549 */       int[][] temp = new int[this.triangleCount << 1][4];
/* 1550 */       System.arraycopy(this.triangles, 0, temp, 0, this.triangleCount);
/* 1551 */       this.triangles = temp;
/*      */       
/* 1553 */       float[][][] ftemp = new float[this.triangleCount << 1][3][7];
/* 1554 */       System.arraycopy(this.triangleColors, 0, ftemp, 0, this.triangleCount);
/* 1555 */       this.triangleColors = ftemp;
/*      */     }
/* 1557 */     this.triangles[this.triangleCount][0] = a;
/* 1558 */     this.triangles[this.triangleCount][1] = b;
/* 1559 */     this.triangles[this.triangleCount][2] = c;
/*      */     
/* 1561 */     if (this.textureImage == null) {
/* 1562 */       this.triangles[this.triangleCount][3] = -1;
/*      */     } else {
/* 1564 */       this.triangles[this.triangleCount][3] = this.textureIndex;
/*      */     }
/*      */     
/*      */ 
/* 1568 */     this.triangleCount += 1;
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
/*      */   protected void addPolygonTriangles()
/*      */   {
/* 1581 */     if (this.vertexOrder.length != this.vertices.length) {
/* 1582 */       int[] temp = new int[this.vertices.length];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1587 */       PApplet.arrayCopy(this.vertexOrder, temp, this.vertexOrder.length);
/* 1588 */       this.vertexOrder = temp;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1597 */     int d1 = 0;
/* 1598 */     int d2 = 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1621 */     float area = 0.0F;
/* 1622 */     int p = this.shapeLast - 1; for (int q = this.shapeFirst; q < this.shapeLast; p = q++)
/*      */     {
/* 1624 */       area = area + (this.vertices[q][d1] * this.vertices[p][d2] - this.vertices[p][d1] * this.vertices[q][d2]);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1629 */     if (area == 0.0F)
/*      */     {
/* 1631 */       boolean foundValidX = false;
/* 1632 */       boolean foundValidY = false;
/*      */       
/* 1634 */       for (int i = this.shapeFirst; i < this.shapeLast; i++) {
/* 1635 */         for (int j = i; j < this.shapeLast; j++) {
/* 1636 */           if (this.vertices[i][0] != this.vertices[j][0]) foundValidX = true;
/* 1637 */           if (this.vertices[i][1] != this.vertices[j][1]) { foundValidY = true;
/*      */           }
/*      */         }
/*      */       }
/* 1641 */       if (foundValidX)
/*      */       {
/* 1643 */         d2 = 2;
/* 1644 */       } else if (foundValidY)
/*      */       {
/* 1646 */         d1 = 1;
/* 1647 */         d2 = 2;
/*      */       }
/*      */       else {
/* 1650 */         return;
/*      */       }
/*      */       
/*      */ 
/* 1654 */       int p = this.shapeLast - 1; for (int q = this.shapeFirst; q < this.shapeLast; p = q++)
/*      */       {
/* 1656 */         area = area + (this.vertices[q][d1] * this.vertices[p][d2] - this.vertices[p][d1] * this.vertices[q][d2]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1663 */     float[] vfirst = this.vertices[this.shapeFirst];
/* 1664 */     float[] vlast = this.vertices[(this.shapeLast - 1)];
/* 1665 */     if ((abs(vfirst[0] - vlast[0]) < 1.0E-4F) && 
/* 1666 */       (abs(vfirst[1] - vlast[1]) < 1.0E-4F) && 
/* 1667 */       (abs(vfirst[2] - vlast[2]) < 1.0E-4F)) {
/* 1668 */       this.shapeLast -= 1;
/*      */     }
/*      */     
/*      */ 
/* 1672 */     int j = 0;
/* 1673 */     if (area > 0.0F) {
/* 1674 */       for (int i = this.shapeFirst; i < this.shapeLast; i++) {
/* 1675 */         j = i - this.shapeFirst;
/* 1676 */         this.vertexOrder[j] = i;
/*      */       }
/*      */     } else {
/* 1679 */       for (int i = this.shapeFirst; i < this.shapeLast; i++) {
/* 1680 */         j = i - this.shapeFirst;
/* 1681 */         this.vertexOrder[j] = (this.shapeLast - 1 - j);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1686 */     int vc = this.shapeLast - this.shapeFirst;
/* 1687 */     int count = 2 * vc;
/*      */     
/* 1689 */     int m = 0; for (int v = vc - 1; vc > 2;) {
/* 1690 */       boolean snip = true;
/*      */       
/*      */ 
/* 1693 */       if (count-- <= 0) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/* 1698 */       int u = v; if (vc <= u) u = 0;
/* 1699 */       v = u + 1; if (vc <= v) v = 0;
/* 1700 */       int w = v + 1; if (vc <= w) { w = 0;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1710 */       double Ax = -10.0F * this.vertices[this.vertexOrder[u]][d1];
/* 1711 */       double Ay = 10.0F * this.vertices[this.vertexOrder[u]][d2];
/* 1712 */       double Bx = -10.0F * this.vertices[this.vertexOrder[v]][d1];
/* 1713 */       double By = 10.0F * this.vertices[this.vertexOrder[v]][d2];
/* 1714 */       double Cx = -10.0F * this.vertices[this.vertexOrder[w]][d1];
/* 1715 */       double Cy = 10.0F * this.vertices[this.vertexOrder[w]][d2];
/*      */       
/*      */ 
/* 1718 */       if (9.999999747378752E-5D <= (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax))
/*      */       {
/*      */ 
/*      */ 
/* 1722 */         for (int p = 0; p < vc; p++) {
/* 1723 */           if ((p != u) && (p != v) && (p != w))
/*      */           {
/*      */ 
/*      */ 
/* 1727 */             double Px = -10.0F * this.vertices[this.vertexOrder[p]][d1];
/* 1728 */             double Py = 10.0F * this.vertices[this.vertexOrder[p]][d2];
/*      */             
/* 1730 */             double ax = Cx - Bx;double ay = Cy - By;
/* 1731 */             double bx = Ax - Cx;double by = Ay - Cy;
/* 1732 */             double cx = Bx - Ax;double cy = By - Ay;
/* 1733 */             double apx = Px - Ax;double apy = Py - Ay;
/* 1734 */             double bpx = Px - Bx;double bpy = Py - By;
/* 1735 */             double cpx = Px - Cx;double cpy = Py - Cy;
/*      */             
/* 1737 */             double aCROSSbp = ax * bpy - ay * bpx;
/* 1738 */             double cCROSSap = cx * apy - cy * apx;
/* 1739 */             double bCROSScp = bx * cpy - by * cpx;
/*      */             
/* 1741 */             if ((aCROSSbp >= 0.0D) && (bCROSScp >= 0.0D) && (cCROSSap >= 0.0D)) {
/* 1742 */               snip = false;
/*      */             }
/*      */           }
/*      */         }
/* 1746 */         if (snip) {
/* 1747 */           addTriangle(this.vertexOrder[u], this.vertexOrder[v], this.vertexOrder[w]);
/*      */           
/* 1749 */           m++;
/*      */           
/*      */ 
/* 1752 */           int s = v; for (int t = v + 1; t < vc; t++) {
/* 1753 */             this.vertexOrder[s] = this.vertexOrder[t];s++;
/*      */           }
/* 1755 */           vc--;
/*      */           
/*      */ 
/* 1758 */           count = 2 * vc;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void toWorldNormal(float nx, float ny, float nz, float[] out) {
/* 1765 */     out[0] = 
/* 1766 */       (this.modelviewInv.m00 * nx + this.modelviewInv.m10 * ny + 
/* 1767 */       this.modelviewInv.m20 * nz + this.modelviewInv.m30);
/* 1768 */     out[1] = 
/* 1769 */       (this.modelviewInv.m01 * nx + this.modelviewInv.m11 * ny + 
/* 1770 */       this.modelviewInv.m21 * nz + this.modelviewInv.m31);
/* 1771 */     out[2] = 
/* 1772 */       (this.modelviewInv.m02 * nx + this.modelviewInv.m12 * ny + 
/* 1773 */       this.modelviewInv.m22 * nz + this.modelviewInv.m32);
/* 1774 */     out[3] = 
/* 1775 */       (this.modelviewInv.m03 * nx + this.modelviewInv.m13 * ny + 
/* 1776 */       this.modelviewInv.m23 * nz + this.modelviewInv.m33);
/*      */     
/* 1778 */     if ((out[3] != 0.0F) && (out[3] != 1.0F))
/*      */     {
/* 1780 */       out[0] /= out[3];out[1] /= out[3];out[2] /= out[3];
/*      */     }
/* 1782 */     out[3] = 1.0F;
/*      */     
/* 1784 */     float nlen = mag(out[0], out[1], out[2]);
/* 1785 */     if ((nlen != 0.0F) && (nlen != 1.0F)) {
/* 1786 */       out[0] /= nlen;out[1] /= nlen;out[2] /= nlen;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1793 */   float[] worldNormal = new float[4];
/*      */   
/*      */ 
/*      */   private void calcLightingContribution(int vIndex, float[] contribution)
/*      */   {
/* 1798 */     calcLightingContribution(vIndex, contribution, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void calcLightingContribution(int vIndex, float[] contribution, boolean normalIsWorld)
/*      */   {
/* 1805 */     float[] v = this.vertices[vIndex];
/*      */     
/* 1807 */     float sr = v[28];
/* 1808 */     float sg = v[29];
/* 1809 */     float sb = v[30];
/*      */     
/* 1811 */     float wx = v[21];
/* 1812 */     float wy = v[22];
/* 1813 */     float wz = v[23];
/* 1814 */     float shine = v[31];
/*      */     
/* 1816 */     float nx = v[9];
/* 1817 */     float ny = v[10];
/* 1818 */     float nz = v[11];
/*      */     
/* 1820 */     if (!normalIsWorld)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1834 */       toWorldNormal(v[9], v[10], v[11], this.worldNormal);
/* 1835 */       nx = this.worldNormal[0];
/* 1836 */       ny = this.worldNormal[1];
/* 1837 */       nz = this.worldNormal[2];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1861 */       nx = v[9];
/* 1862 */       ny = v[10];
/* 1863 */       nz = v[11];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1869 */     float dir = dot(nx, ny, nz, -wx, -wy, -wz);
/*      */     
/*      */ 
/*      */ 
/* 1873 */     if (dir < 0.0F) {
/* 1874 */       nx = -nx;
/* 1875 */       ny = -ny;
/* 1876 */       nz = -nz;
/*      */     }
/*      */     
/*      */ 
/* 1880 */     contribution[0] = 0.0F;
/* 1881 */     contribution[1] = 0.0F;
/* 1882 */     contribution[2] = 0.0F;
/*      */     
/* 1884 */     contribution[3] = 0.0F;
/* 1885 */     contribution[4] = 0.0F;
/* 1886 */     contribution[5] = 0.0F;
/*      */     
/* 1888 */     contribution[6] = 0.0F;
/* 1889 */     contribution[7] = 0.0F;
/* 1890 */     contribution[8] = 0.0F;
/*      */     
/*      */ 
/*      */ 
/* 1894 */     for (int i = 0; i < this.lightCount; i++)
/*      */     {
/* 1896 */       float denom = this.lightFalloffConstant[i];
/* 1897 */       float spotTerm = 1.0F;
/*      */       
/* 1899 */       if (this.lightType[i] == 0) {
/* 1900 */         if ((this.lightFalloffQuadratic[i] != 0.0F) || (this.lightFalloffLinear[i] != 0.0F))
/*      */         {
/* 1902 */           float distSq = mag(this.lightPosition[i].x - wx, 
/* 1903 */             this.lightPosition[i].y - wy, 
/* 1904 */             this.lightPosition[i].z - wz);
/*      */           
/* 1906 */           denom = denom + (this.lightFalloffQuadratic[i] * distSq + 
/* 1907 */             this.lightFalloffLinear[i] * sqrt(distSq));
/*      */         }
/* 1909 */         if (denom == 0.0F) { denom = 1.0F;
/*      */         }
/* 1911 */         contribution[0] += this.lightDiffuse[i][0] / denom;
/* 1912 */         contribution[1] += this.lightDiffuse[i][1] / denom;
/* 1913 */         contribution[2] += this.lightDiffuse[i][2] / denom;
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 1920 */         float lightDir_dot_li = 0.0F;
/* 1921 */         float n_dot_li = 0.0F;
/*      */         float lix;
/* 1923 */         float liy; float liz; if (this.lightType[i] == 1) {
/* 1924 */           float lix = -this.lightNormal[i].x;
/* 1925 */           float liy = -this.lightNormal[i].y;
/* 1926 */           float liz = -this.lightNormal[i].z;
/* 1927 */           denom = 1.0F;
/* 1928 */           n_dot_li = nx * lix + ny * liy + nz * liz;
/*      */           
/* 1930 */           if (n_dot_li <= 0.0F) {
/*      */             continue;
/*      */           }
/*      */         } else {
/* 1934 */           lix = this.lightPosition[i].x - wx;
/* 1935 */           liy = this.lightPosition[i].y - wy;
/* 1936 */           liz = this.lightPosition[i].z - wz;
/*      */           
/* 1938 */           float distSq = mag(lix, liy, liz);
/* 1939 */           if (distSq != 0.0F) {
/* 1940 */             lix /= distSq;
/* 1941 */             liy /= distSq;
/* 1942 */             liz /= distSq;
/*      */           }
/* 1944 */           n_dot_li = nx * lix + ny * liy + nz * liz;
/*      */           
/* 1946 */           if (n_dot_li <= 0.0F) {
/*      */             continue;
/*      */           }
/*      */           
/* 1950 */           if (this.lightType[i] == 3) {
/* 1951 */             lightDir_dot_li = 
/* 1952 */               -(this.lightNormal[i].x * lix + 
/* 1953 */               this.lightNormal[i].y * liy + 
/* 1954 */               this.lightNormal[i].z * liz);
/*      */             
/* 1956 */             if (lightDir_dot_li <= this.lightSpotAngleCos[i]) {
/*      */               continue;
/*      */             }
/* 1959 */             spotTerm = (float)Math.pow(lightDir_dot_li, this.lightSpotConcentration[i]);
/*      */           }
/*      */           
/* 1962 */           if ((this.lightFalloffQuadratic[i] != 0.0F) || (this.lightFalloffLinear[i] != 0.0F))
/*      */           {
/*      */ 
/* 1965 */             denom = denom + (this.lightFalloffQuadratic[i] * distSq + 
/* 1966 */               this.lightFalloffLinear[i] * sqrt(distSq));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1973 */         if (denom == 0.0F)
/* 1974 */           denom = 1.0F;
/* 1975 */         float mul = n_dot_li * spotTerm / denom;
/* 1976 */         contribution[3] += this.lightDiffuse[i][0] * mul;
/* 1977 */         contribution[4] += this.lightDiffuse[i][1] * mul;
/* 1978 */         contribution[5] += this.lightDiffuse[i][2] * mul;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1983 */         if (((sr > 0.0F) || (sg > 0.0F) || (sb > 0.0F)) && (
/* 1984 */           (this.lightSpecular[i][0] > 0.0F) || 
/* 1985 */           (this.lightSpecular[i][1] > 0.0F) || 
/* 1986 */           (this.lightSpecular[i][2] > 0.0F)))
/*      */         {
/* 1988 */           float vmag = mag(wx, wy, wz);
/* 1989 */           if (vmag != 0.0F) {
/* 1990 */             wx /= vmag;
/* 1991 */             wy /= vmag;
/* 1992 */             wz /= vmag;
/*      */           }
/* 1994 */           float sx = lix - wx;
/* 1995 */           float sy = liy - wy;
/* 1996 */           float sz = liz - wz;
/* 1997 */           vmag = mag(sx, sy, sz);
/* 1998 */           if (vmag != 0.0F) {
/* 1999 */             sx /= vmag;
/* 2000 */             sy /= vmag;
/* 2001 */             sz /= vmag;
/*      */           }
/* 2003 */           float s_dot_n = sx * nx + sy * ny + sz * nz;
/*      */           
/* 2005 */           if (s_dot_n > 0.0F) {
/* 2006 */             s_dot_n = (float)Math.pow(s_dot_n, shine);
/* 2007 */             mul = s_dot_n * spotTerm / denom;
/* 2008 */             contribution[6] += this.lightSpecular[i][0] * mul;
/* 2009 */             contribution[7] += this.lightSpecular[i][1] * mul;
/* 2010 */             contribution[8] += this.lightSpecular[i][2] * mul;
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
/*      */   private void applyLightingContribution(int vIndex, float[] contribution)
/*      */   {
/* 2024 */     float[] v = this.vertices[vIndex];
/*      */     
/* 2026 */     v[3] = clamp(v[32] + v[25] * contribution[0] + v[3] * contribution[3]);
/* 2027 */     v[4] = clamp(v[33] + v[26] * contribution[1] + v[4] * contribution[4]);
/* 2028 */     v[5] = clamp(v[34] + v[27] * contribution[2] + v[5] * contribution[5]);
/* 2029 */     v[6] = clamp(v[6]);
/*      */     
/* 2031 */     v[28] = clamp(v[28] * contribution[6]);
/* 2032 */     v[29] = clamp(v[29] * contribution[7]);
/* 2033 */     v[30] = clamp(v[30] * contribution[8]);
/*      */     
/*      */ 
/* 2036 */     v[35] = 1.0F;
/*      */   }
/*      */   
/*      */   private void lightVertex(int vIndex, float[] contribution)
/*      */   {
/* 2041 */     calcLightingContribution(vIndex, contribution);
/* 2042 */     applyLightingContribution(vIndex, contribution);
/*      */   }
/*      */   
/*      */   private void lightUnlitVertex(int vIndex, float[] contribution)
/*      */   {
/* 2047 */     if (this.vertices[vIndex][35] == 0.0F) {
/* 2048 */       lightVertex(vIndex, contribution);
/*      */     }
/*      */   }
/*      */   
/*      */   private void copyPrelitVertexColor(int triIndex, int index, int colorIndex)
/*      */   {
/* 2054 */     float[] triColor = this.triangleColors[triIndex][colorIndex];
/* 2055 */     float[] v = this.vertices[index];
/*      */     
/* 2057 */     triColor[0] = v[3];
/* 2058 */     triColor[1] = v[4];
/* 2059 */     triColor[2] = v[5];
/* 2060 */     triColor[3] = v[6];
/* 2061 */     triColor[4] = v[28];
/* 2062 */     triColor[5] = v[29];
/* 2063 */     triColor[6] = v[30];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void copyVertexColor(int triIndex, int index, int colorIndex, float[] contrib)
/*      */   {
/* 2070 */     float[] triColor = this.triangleColors[triIndex][colorIndex];
/* 2071 */     float[] v = this.vertices[index];
/*      */     
/* 2073 */     triColor[0] = 
/* 2074 */       clamp(v[32] + v[25] * contrib[0] + v[3] * contrib[3]);
/* 2075 */     triColor[1] = 
/* 2076 */       clamp(v[33] + v[26] * contrib[1] + v[4] * contrib[4]);
/* 2077 */     triColor[2] = 
/* 2078 */       clamp(v[34] + v[27] * contrib[2] + v[5] * contrib[5]);
/* 2079 */     triColor[3] = clamp(v[6]);
/*      */     
/* 2081 */     triColor[4] = clamp(v[28] * contrib[6]);
/* 2082 */     triColor[5] = clamp(v[29] * contrib[7]);
/* 2083 */     triColor[6] = clamp(v[30] * contrib[8]);
/*      */   }
/*      */   
/*      */   private void lightTriangle(int triIndex, float[] lightContribution)
/*      */   {
/* 2088 */     int vIndex = this.triangles[triIndex][0];
/* 2089 */     copyVertexColor(triIndex, vIndex, 0, lightContribution);
/* 2090 */     vIndex = this.triangles[triIndex][1];
/* 2091 */     copyVertexColor(triIndex, vIndex, 1, lightContribution);
/* 2092 */     vIndex = this.triangles[triIndex][2];
/* 2093 */     copyVertexColor(triIndex, vIndex, 2, lightContribution);
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
/*      */   private void lightTriangle(int triIndex)
/*      */   {
/* 2119 */     if (this.normalMode == 2) {
/* 2120 */       int vIndex = this.triangles[triIndex][0];
/* 2121 */       lightUnlitVertex(vIndex, this.tempLightingContribution);
/* 2122 */       copyPrelitVertexColor(triIndex, vIndex, 0);
/*      */       
/* 2124 */       vIndex = this.triangles[triIndex][1];
/* 2125 */       lightUnlitVertex(vIndex, this.tempLightingContribution);
/* 2126 */       copyPrelitVertexColor(triIndex, vIndex, 1);
/*      */       
/* 2128 */       vIndex = this.triangles[triIndex][2];
/* 2129 */       lightUnlitVertex(vIndex, this.tempLightingContribution);
/* 2130 */       copyPrelitVertexColor(triIndex, vIndex, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 2139 */     else if (!this.lightingDependsOnVertexPosition) {
/* 2140 */       int vIndex = this.triangles[triIndex][0];
/* 2141 */       int vIndex2 = this.triangles[triIndex][1];
/* 2142 */       int vIndex3 = this.triangles[triIndex][2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2156 */       cross(this.vertices[vIndex2][21] - this.vertices[vIndex][21], 
/* 2157 */         this.vertices[vIndex2][22] - this.vertices[vIndex][22], 
/* 2158 */         this.vertices[vIndex2][23] - this.vertices[vIndex][23], 
/* 2159 */         this.vertices[vIndex3][21] - this.vertices[vIndex][21], 
/* 2160 */         this.vertices[vIndex3][22] - this.vertices[vIndex][22], 
/* 2161 */         this.vertices[vIndex3][23] - this.vertices[vIndex][23], this.lightTriangleNorm);
/*      */       
/* 2163 */       this.lightTriangleNorm.normalize();
/* 2164 */       this.vertices[vIndex][9] = this.lightTriangleNorm.x;
/* 2165 */       this.vertices[vIndex][10] = this.lightTriangleNorm.y;
/* 2166 */       this.vertices[vIndex][11] = this.lightTriangleNorm.z;
/*      */       
/*      */ 
/* 2169 */       calcLightingContribution(vIndex, this.tempLightingContribution, true);
/* 2170 */       copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
/* 2171 */       copyVertexColor(triIndex, vIndex2, 1, this.tempLightingContribution);
/* 2172 */       copyVertexColor(triIndex, vIndex3, 2, this.tempLightingContribution);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 2177 */     else if (this.normalMode == 1) {
/* 2178 */       int vIndex = this.triangles[triIndex][0];
/* 2179 */       this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
/* 2180 */       this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
/* 2181 */       this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
/* 2182 */       calcLightingContribution(vIndex, this.tempLightingContribution);
/* 2183 */       copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
/*      */       
/* 2185 */       vIndex = this.triangles[triIndex][1];
/* 2186 */       this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
/* 2187 */       this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
/* 2188 */       this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
/* 2189 */       calcLightingContribution(vIndex, this.tempLightingContribution);
/* 2190 */       copyVertexColor(triIndex, vIndex, 1, this.tempLightingContribution);
/*      */       
/* 2192 */       vIndex = this.triangles[triIndex][2];
/* 2193 */       this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
/* 2194 */       this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
/* 2195 */       this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
/* 2196 */       calcLightingContribution(vIndex, this.tempLightingContribution);
/* 2197 */       copyVertexColor(triIndex, vIndex, 2, this.tempLightingContribution);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2202 */       int vIndex = this.triangles[triIndex][0];
/* 2203 */       int vIndex2 = this.triangles[triIndex][1];
/* 2204 */       int vIndex3 = this.triangles[triIndex][2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2218 */       cross(this.vertices[vIndex2][21] - this.vertices[vIndex][21], 
/* 2219 */         this.vertices[vIndex2][22] - this.vertices[vIndex][22], 
/* 2220 */         this.vertices[vIndex2][23] - this.vertices[vIndex][23], 
/* 2221 */         this.vertices[vIndex3][21] - this.vertices[vIndex][21], 
/* 2222 */         this.vertices[vIndex3][22] - this.vertices[vIndex][22], 
/* 2223 */         this.vertices[vIndex3][23] - this.vertices[vIndex][23], this.lightTriangleNorm);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2228 */       this.lightTriangleNorm.normalize();
/* 2229 */       this.vertices[vIndex][9] = this.lightTriangleNorm.x;
/* 2230 */       this.vertices[vIndex][10] = this.lightTriangleNorm.y;
/* 2231 */       this.vertices[vIndex][11] = this.lightTriangleNorm.z;
/*      */       
/* 2233 */       calcLightingContribution(vIndex, this.tempLightingContribution, true);
/* 2234 */       copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
/*      */       
/* 2236 */       this.vertices[vIndex2][9] = this.lightTriangleNorm.x;
/* 2237 */       this.vertices[vIndex2][10] = this.lightTriangleNorm.y;
/* 2238 */       this.vertices[vIndex2][11] = this.lightTriangleNorm.z;
/*      */       
/* 2240 */       calcLightingContribution(vIndex2, this.tempLightingContribution, true);
/* 2241 */       copyVertexColor(triIndex, vIndex2, 1, this.tempLightingContribution);
/*      */       
/* 2243 */       this.vertices[vIndex3][9] = this.lightTriangleNorm.x;
/* 2244 */       this.vertices[vIndex3][10] = this.lightTriangleNorm.y;
/* 2245 */       this.vertices[vIndex3][11] = this.lightTriangleNorm.z;
/*      */       
/* 2247 */       calcLightingContribution(vIndex3, this.tempLightingContribution, true);
/* 2248 */       copyVertexColor(triIndex, vIndex3, 2, this.tempLightingContribution);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void renderTriangles(int start, int stop)
/*      */   {
/* 2255 */     for (int i = start; i < stop; i++) {
/* 2256 */       float[] a = this.vertices[this.triangles[i][0]];
/* 2257 */       float[] b = this.vertices[this.triangles[i][1]];
/* 2258 */       float[] c = this.vertices[this.triangles[i][2]];
/* 2259 */       int tex = this.triangles[i][3];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2284 */       this.triangle.reset();
/*      */       
/*      */ 
/*      */ 
/* 2288 */       float ar = clamp(this.triangleColors[i][0][0] + this.triangleColors[i][0][4]);
/* 2289 */       float ag = clamp(this.triangleColors[i][0][1] + this.triangleColors[i][0][5]);
/* 2290 */       float ab = clamp(this.triangleColors[i][0][2] + this.triangleColors[i][0][6]);
/* 2291 */       float br = clamp(this.triangleColors[i][1][0] + this.triangleColors[i][1][4]);
/* 2292 */       float bg = clamp(this.triangleColors[i][1][1] + this.triangleColors[i][1][5]);
/* 2293 */       float bb = clamp(this.triangleColors[i][1][2] + this.triangleColors[i][1][6]);
/* 2294 */       float cr = clamp(this.triangleColors[i][2][0] + this.triangleColors[i][2][4]);
/* 2295 */       float cg = clamp(this.triangleColors[i][2][1] + this.triangleColors[i][2][5]);
/* 2296 */       float cb = clamp(this.triangleColors[i][2][2] + this.triangleColors[i][2][6]);
/*      */       
/*      */ 
/* 2299 */       boolean failedToPrecalc = false;
/* 2300 */       if ((s_enableAccurateTextures) && (this.frustumMode)) {
/* 2301 */         boolean textured = true;
/* 2302 */         this.smoothTriangle.reset(3);
/* 2303 */         this.smoothTriangle.smooth = true;
/* 2304 */         this.smoothTriangle.interpARGB = true;
/* 2305 */         this.smoothTriangle.setIntensities(ar, ag, ab, a[6], 
/* 2306 */           br, bg, bb, b[6], 
/* 2307 */           cr, cg, cb, c[6]);
/* 2308 */         if ((tex > -1) && (this.textures[tex] != null)) {
/* 2309 */           this.smoothTriangle.setCamVertices(a[21], a[22], a[23], 
/* 2310 */             b[21], b[22], b[23], 
/* 2311 */             c[21], c[22], c[23]);
/* 2312 */           this.smoothTriangle.interpUV = true;
/* 2313 */           this.smoothTriangle.texture(this.textures[tex]);
/* 2314 */           float umult = this.textures[tex].width;
/* 2315 */           float vmult = this.textures[tex].height;
/* 2316 */           this.smoothTriangle.vertices[0][7] = (a[7] * umult);
/* 2317 */           this.smoothTriangle.vertices[0][8] = (a[8] * vmult);
/* 2318 */           this.smoothTriangle.vertices[1][7] = (b[7] * umult);
/* 2319 */           this.smoothTriangle.vertices[1][8] = (b[8] * vmult);
/* 2320 */           this.smoothTriangle.vertices[2][7] = (c[7] * umult);
/* 2321 */           this.smoothTriangle.vertices[2][8] = (c[8] * vmult);
/*      */         } else {
/* 2323 */           this.smoothTriangle.interpUV = false;
/* 2324 */           textured = false;
/*      */         }
/*      */         
/* 2327 */         this.smoothTriangle.setVertices(a[18], a[19], a[20], 
/* 2328 */           b[18], b[19], b[20], 
/* 2329 */           c[18], c[19], c[20]);
/*      */         
/*      */ 
/* 2332 */         if ((!textured) || (this.smoothTriangle.precomputeAccurateTexturing())) {
/* 2333 */           this.smoothTriangle.render();
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2338 */           failedToPrecalc = true;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2346 */       if ((!s_enableAccurateTextures) || (failedToPrecalc) || (!this.frustumMode)) {
/* 2347 */         if ((tex > -1) && (this.textures[tex] != null)) {
/* 2348 */           this.triangle.setTexture(this.textures[tex]);
/* 2349 */           this.triangle.setUV(a[7], a[8], b[7], b[8], c[7], c[8]);
/*      */         }
/*      */         
/* 2352 */         this.triangle.setIntensities(ar, ag, ab, a[6], 
/* 2353 */           br, bg, bb, b[6], 
/* 2354 */           cr, cg, cb, c[6]);
/*      */         
/* 2356 */         this.triangle.setVertices(a[18], a[19], a[20], 
/* 2357 */           b[18], b[19], b[20], 
/* 2358 */           c[18], c[19], c[20]);
/*      */         
/* 2360 */         this.triangle.render();
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
/*      */   protected void rawTriangles(int start, int stop)
/*      */   {
/* 2385 */     this.raw.colorMode(1, 1.0F);
/* 2386 */     this.raw.noStroke();
/* 2387 */     this.raw.beginShape(9);
/*      */     
/* 2389 */     for (int i = start; i < stop; i++) {
/* 2390 */       float[] a = this.vertices[this.triangles[i][0]];
/* 2391 */       float[] b = this.vertices[this.triangles[i][1]];
/* 2392 */       float[] c = this.vertices[this.triangles[i][2]];
/*      */       
/* 2394 */       float ar = clamp(this.triangleColors[i][0][0] + this.triangleColors[i][0][4]);
/* 2395 */       float ag = clamp(this.triangleColors[i][0][1] + this.triangleColors[i][0][5]);
/* 2396 */       float ab = clamp(this.triangleColors[i][0][2] + this.triangleColors[i][0][6]);
/* 2397 */       float br = clamp(this.triangleColors[i][1][0] + this.triangleColors[i][1][4]);
/* 2398 */       float bg = clamp(this.triangleColors[i][1][1] + this.triangleColors[i][1][5]);
/* 2399 */       float bb = clamp(this.triangleColors[i][1][2] + this.triangleColors[i][1][6]);
/* 2400 */       float cr = clamp(this.triangleColors[i][2][0] + this.triangleColors[i][2][4]);
/* 2401 */       float cg = clamp(this.triangleColors[i][2][1] + this.triangleColors[i][2][5]);
/* 2402 */       float cb = clamp(this.triangleColors[i][2][2] + this.triangleColors[i][2][6]);
/*      */       
/* 2404 */       int tex = this.triangles[i][3];
/* 2405 */       PImage texImage = tex > -1 ? this.textures[tex] : null;
/* 2406 */       if (texImage != null) {
/* 2407 */         if (this.raw.is3D()) {
/* 2408 */           if ((a[24] != 0.0F) && (b[24] != 0.0F) && (c[24] != 0.0F)) {
/* 2409 */             this.raw.fill(ar, ag, ab, a[6]);
/* 2410 */             this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24], a[7], a[8]);
/* 2411 */             this.raw.fill(br, bg, bb, b[6]);
/* 2412 */             this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24], b[7], b[8]);
/* 2413 */             this.raw.fill(cr, cg, cb, c[6]);
/* 2414 */             this.raw.vertex(c[21] / c[24], c[22] / c[24], c[23] / c[24], c[7], c[8]);
/*      */           }
/* 2416 */         } else if (this.raw.is2D()) {
/* 2417 */           this.raw.fill(ar, ag, ab, a[6]);
/* 2418 */           this.raw.vertex(a[18], a[19], a[7], a[8]);
/* 2419 */           this.raw.fill(br, bg, bb, b[6]);
/* 2420 */           this.raw.vertex(b[18], b[19], b[7], b[8]);
/* 2421 */           this.raw.fill(cr, cg, cb, c[6]);
/* 2422 */           this.raw.vertex(c[18], c[19], c[7], c[8]);
/*      */         }
/*      */       }
/* 2425 */       else if (this.raw.is3D()) {
/* 2426 */         if ((a[24] != 0.0F) && (b[24] != 0.0F) && (c[24] != 0.0F)) {
/* 2427 */           this.raw.fill(ar, ag, ab, a[6]);
/* 2428 */           this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
/* 2429 */           this.raw.fill(br, bg, bb, b[6]);
/* 2430 */           this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24]);
/* 2431 */           this.raw.fill(cr, cg, cb, c[6]);
/* 2432 */           this.raw.vertex(c[21] / c[24], c[22] / c[24], c[23] / c[24]);
/*      */         }
/* 2434 */       } else if (this.raw.is2D()) {
/* 2435 */         this.raw.fill(ar, ag, ab, a[6]);
/* 2436 */         this.raw.vertex(a[18], a[19]);
/* 2437 */         this.raw.fill(br, bg, bb, b[6]);
/* 2438 */         this.raw.vertex(b[18], b[19]);
/* 2439 */         this.raw.fill(cr, cg, cb, c[6]);
/* 2440 */         this.raw.vertex(c[18], c[19]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2445 */     this.raw.endShape();
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
/*      */   public void flush()
/*      */   {
/* 2480 */     if (this.hints[5] != 0) {
/* 2481 */       sort();
/*      */     }
/* 2483 */     render();
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
/*      */   protected void render()
/*      */   {
/* 2508 */     if (this.pointCount > 0) {
/* 2509 */       renderPoints(0, this.pointCount);
/* 2510 */       if (this.raw != null) {
/* 2511 */         rawPoints(0, this.pointCount);
/*      */       }
/* 2513 */       this.pointCount = 0;
/*      */     }
/* 2515 */     if (this.lineCount > 0) {
/* 2516 */       renderLines(0, this.lineCount);
/* 2517 */       if (this.raw != null) {
/* 2518 */         rawLines(0, this.lineCount);
/*      */       }
/* 2520 */       this.lineCount = 0;
/* 2521 */       this.pathCount = 0;
/*      */     }
/* 2523 */     if (this.triangleCount > 0) {
/* 2524 */       renderTriangles(0, this.triangleCount);
/* 2525 */       if (this.raw != null) {
/* 2526 */         rawTriangles(0, this.triangleCount);
/*      */       }
/* 2528 */       this.triangleCount = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void sort()
/*      */   {
/* 2539 */     if (this.triangleCount > 0) {
/* 2540 */       sortTrianglesInternal(0, this.triangleCount - 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void sortTrianglesInternal(int i, int j)
/*      */   {
/* 2546 */     int pivotIndex = (i + j) / 2;
/* 2547 */     sortTrianglesSwap(pivotIndex, j);
/* 2548 */     int k = sortTrianglesPartition(i - 1, j);
/* 2549 */     sortTrianglesSwap(k, j);
/* 2550 */     if (k - i > 1) sortTrianglesInternal(i, k - 1);
/* 2551 */     if (j - k > 1) sortTrianglesInternal(k + 1, j);
/*      */   }
/*      */   
/*      */   private int sortTrianglesPartition(int left, int right)
/*      */   {
/* 2556 */     int pivot = right;
/*      */     do {
/* 2558 */       while (sortTrianglesCompare(++left, pivot) < 0.0F) {}
/* 2559 */       while ((right != 0) && 
/* 2560 */         (sortTrianglesCompare(--right, pivot) > 0.0F)) {}
/* 2561 */       sortTrianglesSwap(left, right);
/* 2562 */     } while (left < right);
/* 2563 */     sortTrianglesSwap(left, right);
/* 2564 */     return left;
/*      */   }
/*      */   
/*      */   private void sortTrianglesSwap(int a, int b)
/*      */   {
/* 2569 */     int[] tempi = this.triangles[a];
/* 2570 */     this.triangles[a] = this.triangles[b];
/* 2571 */     this.triangles[b] = tempi;
/* 2572 */     float[][] tempf = this.triangleColors[a];
/* 2573 */     this.triangleColors[a] = this.triangleColors[b];
/* 2574 */     this.triangleColors[b] = tempf;
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
/*      */   private float sortTrianglesCompare(int a, int b)
/*      */   {
/* 2589 */     return this.vertices[this.triangles[b][0]][20] + 
/* 2590 */       this.vertices[this.triangles[b][1]][20] + 
/* 2591 */       this.vertices[this.triangles[b][2]][20] - (
/* 2592 */       this.vertices[this.triangles[a][0]][20] + 
/* 2593 */       this.vertices[this.triangles[a][1]][20] + 
/* 2594 */       this.vertices[this.triangles[a][2]][20]);
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
/*      */   protected void ellipseImpl(float x, float y, float w, float h)
/*      */   {
/* 2656 */     float radiusH = w / 2.0F;
/* 2657 */     float radiusV = h / 2.0F;
/*      */     
/* 2659 */     float centerX = x + radiusH;
/* 2660 */     float centerY = y + radiusV;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2668 */     int rough = (int)(4.0D + Math.sqrt(w + h) * 3.0D);
/* 2669 */     int accuracy = PApplet.constrain(rough, 6, 100);
/*      */     
/* 2671 */     if (this.fill)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2677 */       float inc = 720.0F / accuracy;
/* 2678 */       float val = 0.0F;
/*      */       
/* 2680 */       boolean strokeSaved = this.stroke;
/* 2681 */       this.stroke = false;
/* 2682 */       boolean smoothSaved = this.smooth;
/* 2683 */       if ((this.smooth) && (this.stroke)) {
/* 2684 */         this.smooth = false;
/*      */       }
/*      */       
/* 2687 */       beginShape(11);
/* 2688 */       normal(0.0F, 0.0F, 1.0F);
/* 2689 */       vertex(centerX, centerY);
/* 2690 */       for (int i = 0; i < accuracy; i++) {
/* 2691 */         vertex(centerX + cosLUT[((int)val)] * radiusH, 
/* 2692 */           centerY + sinLUT[((int)val)] * radiusV);
/* 2693 */         val = (val + inc) % 720.0F;
/*      */       }
/*      */       
/* 2696 */       vertex(centerX + cosLUT[0] * radiusH, 
/* 2697 */         centerY + sinLUT[0] * radiusV);
/* 2698 */       endShape();
/*      */       
/* 2700 */       this.stroke = strokeSaved;
/* 2701 */       this.smooth = smoothSaved;
/*      */     }
/*      */     
/* 2704 */     if (this.stroke)
/*      */     {
/*      */ 
/*      */ 
/* 2708 */       float inc = 720.0F / accuracy;
/* 2709 */       float val = 0.0F;
/*      */       
/* 2711 */       boolean savedFill = this.fill;
/* 2712 */       this.fill = false;
/*      */       
/* 2714 */       val = 0.0F;
/* 2715 */       beginShape();
/* 2716 */       for (int i = 0; i < accuracy; i++) {
/* 2717 */         vertex(centerX + cosLUT[((int)val)] * radiusH, 
/* 2718 */           centerY + sinLUT[((int)val)] * radiusV);
/* 2719 */         val = (val + inc) % 720.0F;
/*      */       }
/* 2721 */       endShape(2);
/*      */       
/* 2723 */       this.fill = savedFill;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void arcImpl(float x, float y, float w, float h, float start, float stop)
/*      */   {
/* 2734 */     float hr = w / 2.0F;
/* 2735 */     float vr = h / 2.0F;
/*      */     
/* 2737 */     float centerX = x + hr;
/* 2738 */     float centerY = y + vr;
/*      */     
/* 2740 */     if (this.fill)
/*      */     {
/* 2742 */       boolean savedStroke = this.stroke;
/* 2743 */       this.stroke = false;
/*      */       
/* 2745 */       int startLUT = (int)(0.5F + start / 6.2831855F * 720.0F);
/* 2746 */       int stopLUT = (int)(0.5F + stop / 6.2831855F * 720.0F);
/*      */       
/* 2748 */       beginShape(11);
/* 2749 */       vertex(centerX, centerY);
/* 2750 */       int increment = 1;
/* 2751 */       for (int i = startLUT; i < stopLUT; i += increment) {
/* 2752 */         int ii = i % 720;
/*      */         
/* 2754 */         if (ii < 0) ii += 720;
/* 2755 */         vertex(centerX + cosLUT[ii] * hr, 
/* 2756 */           centerY + sinLUT[ii] * vr);
/*      */       }
/*      */       
/* 2759 */       vertex(centerX + cosLUT[(stopLUT % 720)] * hr, 
/* 2760 */         centerY + sinLUT[(stopLUT % 720)] * vr);
/* 2761 */       endShape();
/*      */       
/* 2763 */       this.stroke = savedStroke;
/*      */     }
/*      */     
/* 2766 */     if (this.stroke)
/*      */     {
/*      */ 
/*      */ 
/* 2770 */       boolean savedFill = this.fill;
/* 2771 */       this.fill = false;
/*      */       
/* 2773 */       int startLUT = (int)(0.5F + start / 6.2831855F * 720.0F);
/* 2774 */       int stopLUT = (int)(0.5F + stop / 6.2831855F * 720.0F);
/*      */       
/* 2776 */       beginShape();
/* 2777 */       int increment = 1;
/* 2778 */       for (int i = startLUT; i < stopLUT; i += increment) {
/* 2779 */         int ii = i % 720;
/* 2780 */         if (ii < 0) ii += 720;
/* 2781 */         vertex(centerX + cosLUT[ii] * hr, 
/* 2782 */           centerY + sinLUT[ii] * vr);
/*      */       }
/*      */       
/* 2785 */       vertex(centerX + cosLUT[(stopLUT % 720)] * hr, 
/* 2786 */         centerY + sinLUT[(stopLUT % 720)] * vr);
/* 2787 */       endShape();
/*      */       
/* 2789 */       this.fill = savedFill;
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
/*      */   public void box(float w, float h, float d)
/*      */   {
/* 2804 */     if (this.triangle != null) {
/* 2805 */       this.triangle.setCulling(true);
/*      */     }
/*      */     
/* 2808 */     super.box(w, h, d);
/*      */     
/* 2810 */     if (this.triangle != null) {
/* 2811 */       this.triangle.setCulling(false);
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
/*      */   public void sphere(float r)
/*      */   {
/* 2829 */     if (this.triangle != null) {
/* 2830 */       this.triangle.setCulling(true);
/*      */     }
/*      */     
/* 2833 */     super.sphere(r);
/*      */     
/* 2835 */     if (this.triangle != null) {
/* 2836 */       this.triangle.setCulling(false);
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
/*      */   public void smooth()
/*      */   {
/* 2906 */     s_enableAccurateTextures = true;
/* 2907 */     this.smooth = true;
/*      */   }
/*      */   
/*      */   public void noSmooth()
/*      */   {
/* 2912 */     s_enableAccurateTextures = false;
/* 2913 */     this.smooth = false;
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
/*      */   protected boolean textModeCheck(int mode)
/*      */   {
/* 2970 */     return (this.textMode == 4) || (this.textMode == 256);
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
/*      */   public void pushMatrix()
/*      */   {
/* 2997 */     if (this.matrixMode == 0) {
/* 2998 */       if (this.pmatrixStackDepth == 32) {
/* 2999 */         throw new RuntimeException("Too many calls to pushMatrix().");
/*      */       }
/* 3001 */       this.projection.get(this.pmatrixStack[this.pmatrixStackDepth]);
/* 3002 */       this.pmatrixStackDepth += 1;
/*      */     } else {
/* 3004 */       if (this.matrixStackDepth == 32) {
/* 3005 */         throw new RuntimeException("Too many calls to pushMatrix().");
/*      */       }
/* 3007 */       this.modelview.get(this.matrixStack[this.matrixStackDepth]);
/* 3008 */       this.modelviewInv.get(this.matrixInvStack[this.matrixStackDepth]);
/* 3009 */       this.matrixStackDepth += 1;
/*      */     }
/*      */   }
/*      */   
/*      */   public void popMatrix()
/*      */   {
/* 3015 */     if (this.matrixMode == 0) {
/* 3016 */       if (this.pmatrixStackDepth == 0) {
/* 3017 */         throw new RuntimeException("Too many calls to popMatrix(), and not enough to pushMatrix().");
/*      */       }
/* 3019 */       this.pmatrixStackDepth -= 1;
/* 3020 */       this.projection.set(this.pmatrixStack[this.pmatrixStackDepth]);
/*      */     } else {
/* 3022 */       if (this.matrixStackDepth == 0) {
/* 3023 */         throw new RuntimeException("Too many calls to popMatrix(), and not enough to pushMatrix().");
/*      */       }
/* 3025 */       this.matrixStackDepth -= 1;
/* 3026 */       this.modelview.set(this.matrixStack[this.matrixStackDepth]);
/* 3027 */       this.modelviewInv.set(this.matrixInvStack[this.matrixStackDepth]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void translate(float tx, float ty)
/*      */   {
/* 3038 */     translate(tx, ty, 0.0F);
/*      */   }
/*      */   
/*      */   public void translate(float tx, float ty, float tz)
/*      */   {
/* 3043 */     if (this.matrixMode == 0) {
/* 3044 */       this.projection.translate(tx, ty, tz);
/*      */     } else {
/* 3046 */       this.forwardTransform.translate(tx, ty, tz);
/* 3047 */       this.reverseTransform.invTranslate(tx, ty, tz);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle)
/*      */   {
/* 3059 */     rotateZ(angle);
/*      */   }
/*      */   
/*      */   public void rotateX(float angle)
/*      */   {
/* 3064 */     if (this.matrixMode == 0) {
/* 3065 */       this.projection.rotateX(angle);
/*      */     } else {
/* 3067 */       this.forwardTransform.rotateX(angle);
/* 3068 */       this.reverseTransform.invRotateX(angle);
/*      */     }
/*      */   }
/*      */   
/*      */   public void rotateY(float angle)
/*      */   {
/* 3074 */     if (this.matrixMode == 0) {
/* 3075 */       this.projection.rotateY(angle);
/*      */     } else {
/* 3077 */       this.forwardTransform.rotateY(angle);
/* 3078 */       this.reverseTransform.invRotateY(angle);
/*      */     }
/*      */   }
/*      */   
/*      */   public void rotateZ(float angle)
/*      */   {
/* 3084 */     if (this.matrixMode == 0) {
/* 3085 */       this.projection.rotateZ(angle);
/*      */     } else {
/* 3087 */       this.forwardTransform.rotateZ(angle);
/* 3088 */       this.reverseTransform.invRotateZ(angle);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle, float v0, float v1, float v2)
/*      */   {
/* 3098 */     if (this.matrixMode == 0) {
/* 3099 */       this.projection.rotate(angle, v0, v1, v2);
/*      */     } else {
/* 3101 */       this.forwardTransform.rotate(angle, v0, v1, v2);
/* 3102 */       this.reverseTransform.invRotate(angle, v0, v1, v2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float s)
/*      */   {
/* 3111 */     scale(s, s, s);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float sx, float sy)
/*      */   {
/* 3119 */     scale(sx, sy, 1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float x, float y, float z)
/*      */   {
/* 3127 */     if (this.matrixMode == 0) {
/* 3128 */       this.projection.scale(x, y, z);
/*      */     } else {
/* 3130 */       this.forwardTransform.scale(x, y, z);
/* 3131 */       this.reverseTransform.invScale(x, y, z);
/*      */     }
/*      */   }
/*      */   
/*      */   public void shearX(float angle)
/*      */   {
/* 3137 */     float t = (float)Math.tan(angle);
/* 3138 */     applyMatrix(1.0F, t, 0.0F, 0.0F, 
/* 3139 */       0.0F, 1.0F, 0.0F, 0.0F, 
/* 3140 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 3141 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public void shearY(float angle)
/*      */   {
/* 3146 */     float t = (float)Math.tan(angle);
/* 3147 */     applyMatrix(1.0F, 0.0F, 0.0F, 0.0F, 
/* 3148 */       t, 1.0F, 0.0F, 0.0F, 
/* 3149 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 3150 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resetMatrix()
/*      */   {
/* 3161 */     if (this.matrixMode == 0) {
/* 3162 */       this.projection.reset();
/*      */     } else {
/* 3164 */       this.forwardTransform.reset();
/* 3165 */       this.reverseTransform.reset();
/*      */     }
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix2D source)
/*      */   {
/* 3171 */     applyMatrix(source.m00, source.m01, source.m02, 
/* 3172 */       source.m10, source.m11, source.m12);
/*      */   }
/*      */   
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/* 3178 */     applyMatrix(n00, n01, n02, 0.0F, 
/* 3179 */       n10, n11, n12, 0.0F, 
/* 3180 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 3181 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix3D source)
/*      */   {
/* 3186 */     applyMatrix(source.m00, source.m01, source.m02, source.m03, 
/* 3187 */       source.m10, source.m11, source.m12, source.m13, 
/* 3188 */       source.m20, source.m21, source.m22, source.m23, 
/* 3189 */       source.m30, source.m31, source.m32, source.m33);
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
/*      */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*      */   {
/* 3202 */     if (this.matrixMode == 0) {
/* 3203 */       this.projection.apply(n00, n01, n02, n03, 
/* 3204 */         n10, n11, n12, n13, 
/* 3205 */         n20, n21, n22, n23, 
/* 3206 */         n30, n31, n32, n33);
/*      */     } else {
/* 3208 */       this.forwardTransform.apply(n00, n01, n02, n03, 
/* 3209 */         n10, n11, n12, n13, 
/* 3210 */         n20, n21, n22, n23, 
/* 3211 */         n30, n31, n32, n33);
/*      */       
/* 3213 */       this.reverseTransform.invApply(n00, n01, n02, n03, 
/* 3214 */         n10, n11, n12, n13, 
/* 3215 */         n20, n21, n22, n23, 
/* 3216 */         n30, n31, n32, n33);
/*      */     }
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
/* 3228 */     if (this.matrixMode == 0) {
/* 3229 */       return this.projection.get();
/*      */     }
/* 3231 */     return this.modelview.get();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix3D getMatrix(PMatrix3D target)
/*      */   {
/* 3240 */     if (target == null) {
/* 3241 */       target = new PMatrix3D();
/*      */     }
/* 3243 */     if (this.matrixMode == 0) {
/* 3244 */       target.set(this.projection);
/*      */     } else {
/* 3246 */       target.set(this.modelview);
/*      */     }
/* 3248 */     return target;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix2D source)
/*      */   {
/* 3257 */     resetMatrix();
/* 3258 */     applyMatrix(source);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix3D source)
/*      */   {
/* 3267 */     resetMatrix();
/* 3268 */     applyMatrix(source);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printMatrix()
/*      */   {
/* 3276 */     if (this.matrixMode == 0) {
/* 3277 */       this.projection.print();
/*      */     } else {
/* 3279 */       this.modelview.print();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void beginCamera()
/*      */   {
/* 3371 */     if (this.manipulatingCamera) {
/* 3372 */       throw new RuntimeException("beginCamera() cannot be called again before endCamera()");
/*      */     }
/*      */     
/* 3375 */     this.manipulatingCamera = true;
/* 3376 */     this.forwardTransform = this.cameraInv;
/* 3377 */     this.reverseTransform = this.camera;
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
/*      */   public void endCamera()
/*      */   {
/* 3391 */     if (!this.manipulatingCamera) {
/* 3392 */       throw new RuntimeException("Cannot call endCamera() without first calling beginCamera()");
/*      */     }
/*      */     
/*      */ 
/* 3396 */     this.modelview.set(this.camera);
/* 3397 */     this.modelviewInv.set(this.cameraInv);
/*      */     
/*      */ 
/* 3400 */     this.forwardTransform = this.modelview;
/* 3401 */     this.reverseTransform = this.modelviewInv;
/*      */     
/*      */ 
/* 3404 */     this.manipulatingCamera = false;
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
/*      */   public void camera()
/*      */   {
/* 3461 */     camera(this.cameraX, this.cameraY, this.cameraZ, 
/* 3462 */       this.cameraX, this.cameraY, 0.0F, 
/* 3463 */       0.0F, 1.0F, 0.0F);
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
/*      */   public void camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
/*      */   {
/* 3520 */     float z0 = eyeX - centerX;
/* 3521 */     float z1 = eyeY - centerY;
/* 3522 */     float z2 = eyeZ - centerZ;
/* 3523 */     float mag = sqrt(z0 * z0 + z1 * z1 + z2 * z2);
/*      */     
/* 3525 */     if (mag != 0.0F) {
/* 3526 */       z0 /= mag;
/* 3527 */       z1 /= mag;
/* 3528 */       z2 /= mag;
/*      */     }
/*      */     
/* 3531 */     float y0 = upX;
/* 3532 */     float y1 = upY;
/* 3533 */     float y2 = upZ;
/*      */     
/* 3535 */     float x0 = y1 * z2 - y2 * z1;
/* 3536 */     float x1 = -y0 * z2 + y2 * z0;
/* 3537 */     float x2 = y0 * z1 - y1 * z0;
/*      */     
/* 3539 */     y0 = z1 * x2 - z2 * x1;
/* 3540 */     y1 = -z0 * x2 + z2 * x0;
/* 3541 */     y2 = z0 * x1 - z1 * x0;
/*      */     
/* 3543 */     mag = sqrt(x0 * x0 + x1 * x1 + x2 * x2);
/* 3544 */     if (mag != 0.0F) {
/* 3545 */       x0 /= mag;
/* 3546 */       x1 /= mag;
/* 3547 */       x2 /= mag;
/*      */     }
/*      */     
/* 3550 */     mag = sqrt(y0 * y0 + y1 * y1 + y2 * y2);
/* 3551 */     if (mag != 0.0F) {
/* 3552 */       y0 /= mag;
/* 3553 */       y1 /= mag;
/* 3554 */       y2 /= mag;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3559 */     this.camera.set(x0, x1, x2, 0.0F, 
/* 3560 */       y0, y1, y2, 0.0F, 
/* 3561 */       z0, z1, z2, 0.0F, 
/* 3562 */       0.0F, 0.0F, 0.0F, 1.0F);
/* 3563 */     this.camera.translate(-eyeX, -eyeY, -eyeZ);
/*      */     
/* 3565 */     this.cameraInv.reset();
/* 3566 */     this.cameraInv.invApply(x0, x1, x2, 0.0F, 
/* 3567 */       y0, y1, y2, 0.0F, 
/* 3568 */       z0, z1, z2, 0.0F, 
/* 3569 */       0.0F, 0.0F, 0.0F, 1.0F);
/* 3570 */     this.cameraInv.translate(eyeX, eyeY, eyeZ);
/*      */     
/* 3572 */     this.modelview.set(this.camera);
/* 3573 */     this.modelviewInv.set(this.cameraInv);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printCamera()
/*      */   {
/* 3581 */     this.camera.print();
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
/*      */   public void ortho()
/*      */   {
/* 3595 */     ortho(0.0F, this.width, 0.0F, this.height, -10.0F, 10.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ortho(float left, float right, float bottom, float top, float near, float far)
/*      */   {
/* 3607 */     float x = 2.0F / (right - left);
/* 3608 */     float y = 2.0F / (top - bottom);
/* 3609 */     float z = -2.0F / (far - near);
/*      */     
/* 3611 */     float tx = -(right + left) / (right - left);
/* 3612 */     float ty = -(top + bottom) / (top - bottom);
/* 3613 */     float tz = -(far + near) / (far - near);
/*      */     
/* 3615 */     this.projection.set(x, 0.0F, 0.0F, tx, 
/* 3616 */       0.0F, y, 0.0F, ty, 
/* 3617 */       0.0F, 0.0F, z, tz, 
/* 3618 */       0.0F, 0.0F, 0.0F, 1.0F);
/* 3619 */     updateProjection();
/*      */     
/* 3621 */     this.frustumMode = false;
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
/*      */   public void perspective()
/*      */   {
/* 3647 */     perspective(this.cameraFOV, this.cameraAspect, this.cameraNear, this.cameraFar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void perspective(float fov, float aspect, float zNear, float zFar)
/*      */   {
/* 3656 */     float ymax = zNear * (float)Math.tan(fov / 2.0F);
/* 3657 */     float ymin = -ymax;
/*      */     
/* 3659 */     float xmin = ymin * aspect;
/* 3660 */     float xmax = ymax * aspect;
/*      */     
/* 3662 */     frustum(xmin, xmax, ymin, ymax, zNear, zFar);
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
/*      */   public void frustum(float left, float right, float bottom, float top, float znear, float zfar)
/*      */   {
/* 3675 */     this.leftScreen = left;
/* 3676 */     this.rightScreen = right;
/* 3677 */     this.bottomScreen = bottom;
/* 3678 */     this.topScreen = top;
/* 3679 */     this.nearPlane = znear;
/* 3680 */     this.frustumMode = true;
/*      */     
/*      */ 
/* 3683 */     this.projection.set(2.0F * znear / (right - left), 0.0F, (right + left) / (right - left), 0.0F, 
/* 3684 */       0.0F, 2.0F * znear / (top - bottom), (top + bottom) / (top - bottom), 0.0F, 
/* 3685 */       0.0F, 0.0F, -(zfar + znear) / (zfar - znear), -(2.0F * zfar * znear) / (zfar - znear), 
/* 3686 */       0.0F, 0.0F, -1.0F, 0.0F);
/* 3687 */     updateProjection();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateProjection() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printProjection()
/*      */   {
/* 3700 */     this.projection.print();
/*      */   }
/*      */   
/*      */   public PMatrix getProjection()
/*      */   {
/* 3705 */     return this.projection.get();
/*      */   }
/*      */   
/*      */   public void matrixMode(int mode)
/*      */   {
/* 3710 */     if (mode == 0) {
/* 3711 */       this.matrixMode = 0;
/* 3712 */     } else if (mode == 1) {
/* 3713 */       this.matrixMode = 1;
/*      */     } else {
/* 3715 */       showWarning("Invalid matrix mode. Use PROJECTION or MODELVIEW");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float screenX(float x, float y)
/*      */   {
/* 3726 */     return screenX(x, y, 0.0F);
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y)
/*      */   {
/* 3731 */     return screenY(x, y, 0.0F);
/*      */   }
/*      */   
/*      */   public float screenX(float x, float y, float z)
/*      */   {
/* 3736 */     float ax = 
/* 3737 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3738 */     float ay = 
/* 3739 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3740 */     float az = 
/* 3741 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3742 */     float aw = 
/* 3743 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3745 */     float ox = 
/* 3746 */       this.projection.m00 * ax + this.projection.m01 * ay + 
/* 3747 */       this.projection.m02 * az + this.projection.m03 * aw;
/* 3748 */     float ow = 
/* 3749 */       this.projection.m30 * ax + this.projection.m31 * ay + 
/* 3750 */       this.projection.m32 * az + this.projection.m33 * aw;
/*      */     
/* 3752 */     if (ow != 0.0F) ox /= ow;
/* 3753 */     return this.width * (1.0F + ox) / 2.0F;
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y, float z)
/*      */   {
/* 3758 */     float ax = 
/* 3759 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3760 */     float ay = 
/* 3761 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3762 */     float az = 
/* 3763 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3764 */     float aw = 
/* 3765 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3767 */     float oy = 
/* 3768 */       this.projection.m10 * ax + this.projection.m11 * ay + 
/* 3769 */       this.projection.m12 * az + this.projection.m13 * aw;
/* 3770 */     float ow = 
/* 3771 */       this.projection.m30 * ax + this.projection.m31 * ay + 
/* 3772 */       this.projection.m32 * az + this.projection.m33 * aw;
/*      */     
/* 3774 */     if (ow != 0.0F) oy /= ow;
/* 3775 */     return this.height * (1.0F + oy) / 2.0F;
/*      */   }
/*      */   
/*      */   public float screenZ(float x, float y, float z)
/*      */   {
/* 3780 */     float ax = 
/* 3781 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3782 */     float ay = 
/* 3783 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3784 */     float az = 
/* 3785 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3786 */     float aw = 
/* 3787 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3789 */     float oz = 
/* 3790 */       this.projection.m20 * ax + this.projection.m21 * ay + 
/* 3791 */       this.projection.m22 * az + this.projection.m23 * aw;
/* 3792 */     float ow = 
/* 3793 */       this.projection.m30 * ax + this.projection.m31 * ay + 
/* 3794 */       this.projection.m32 * az + this.projection.m33 * aw;
/*      */     
/* 3796 */     if (ow != 0.0F) oz /= ow;
/* 3797 */     return (oz + 1.0F) / 2.0F;
/*      */   }
/*      */   
/*      */   public float modelX(float x, float y, float z)
/*      */   {
/* 3802 */     float ax = 
/* 3803 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3804 */     float ay = 
/* 3805 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3806 */     float az = 
/* 3807 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3808 */     float aw = 
/* 3809 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3811 */     float ox = 
/* 3812 */       this.cameraInv.m00 * ax + this.cameraInv.m01 * ay + 
/* 3813 */       this.cameraInv.m02 * az + this.cameraInv.m03 * aw;
/* 3814 */     float ow = 
/* 3815 */       this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + 
/* 3816 */       this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
/*      */     
/* 3818 */     return ow != 0.0F ? ox / ow : ox;
/*      */   }
/*      */   
/*      */   public float modelY(float x, float y, float z)
/*      */   {
/* 3823 */     float ax = 
/* 3824 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3825 */     float ay = 
/* 3826 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3827 */     float az = 
/* 3828 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3829 */     float aw = 
/* 3830 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3832 */     float oy = 
/* 3833 */       this.cameraInv.m10 * ax + this.cameraInv.m11 * ay + 
/* 3834 */       this.cameraInv.m12 * az + this.cameraInv.m13 * aw;
/* 3835 */     float ow = 
/* 3836 */       this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + 
/* 3837 */       this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
/*      */     
/* 3839 */     return ow != 0.0F ? oy / ow : oy;
/*      */   }
/*      */   
/*      */   public float modelZ(float x, float y, float z)
/*      */   {
/* 3844 */     float ax = 
/* 3845 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3846 */     float ay = 
/* 3847 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3848 */     float az = 
/* 3849 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3850 */     float aw = 
/* 3851 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3853 */     float oz = 
/* 3854 */       this.cameraInv.m20 * ax + this.cameraInv.m21 * ay + 
/* 3855 */       this.cameraInv.m22 * az + this.cameraInv.m23 * aw;
/* 3856 */     float ow = 
/* 3857 */       this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + 
/* 3858 */       this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
/*      */     
/* 3860 */     return ow != 0.0F ? oz / ow : oz;
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
/*      */   public void strokeJoin(int join)
/*      */   {
/* 3886 */     if (join != 8) {
/* 3887 */       showMethodWarning("strokeJoin");
/*      */     }
/*      */   }
/*      */   
/*      */   public void strokeCap(int cap)
/*      */   {
/* 3893 */     if (cap != 2) {
/* 3894 */       showMethodWarning("strokeCap");
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
/*      */   protected void fillFromCalc()
/*      */   {
/* 3922 */     super.fillFromCalc();
/* 3923 */     ambientFromCalc();
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
/* 3941 */   PVector lightPositionVec = new PVector();
/* 3942 */   PVector lightDirectionVec = new PVector();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4071 */     int colorModeSaved = this.colorMode;
/* 4072 */     this.colorMode = 1;
/*      */     
/* 4074 */     lightFalloff(1.0F, 0.0F, 0.0F);
/* 4075 */     lightSpecular(0.0F, 0.0F, 0.0F);
/*      */     
/* 4077 */     ambientLight(this.colorModeX * 0.5F, 
/* 4078 */       this.colorModeY * 0.5F, 
/* 4079 */       this.colorModeZ * 0.5F);
/* 4080 */     directionalLight(this.colorModeX * 0.5F, 
/* 4081 */       this.colorModeY * 0.5F, 
/* 4082 */       this.colorModeZ * 0.5F, 
/* 4083 */       0.0F, 0.0F, -1.0F);
/*      */     
/* 4085 */     this.colorMode = colorModeSaved;
/*      */     
/* 4087 */     this.lightingDependsOnVertexPosition = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void noLights()
/*      */   {
/* 4096 */     flush();
/*      */     
/* 4098 */     this.lightCount = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ambientLight(float r, float g, float b)
/*      */   {
/* 4106 */     ambientLight(r, g, b, 0.0F, 0.0F, 0.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ambientLight(float r, float g, float b, float x, float y, float z)
/*      */   {
/* 4117 */     if (this.lightCount == 8) {
/* 4118 */       throw new RuntimeException("can only create 8 lights");
/*      */     }
/* 4120 */     colorCalc(r, g, b);
/* 4121 */     this.lightDiffuse[this.lightCount][0] = this.calcR;
/* 4122 */     this.lightDiffuse[this.lightCount][1] = this.calcG;
/* 4123 */     this.lightDiffuse[this.lightCount][2] = this.calcB;
/*      */     
/* 4125 */     this.lightType[this.lightCount] = 0;
/* 4126 */     this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
/* 4127 */     this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
/* 4128 */     this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
/* 4129 */     lightPosition(this.lightCount, x, y, z);
/* 4130 */     this.lightCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void directionalLight(float r, float g, float b, float nx, float ny, float nz)
/*      */   {
/* 4137 */     if (this.lightCount == 8) {
/* 4138 */       throw new RuntimeException("can only create 8 lights");
/*      */     }
/* 4140 */     colorCalc(r, g, b);
/* 4141 */     this.lightDiffuse[this.lightCount][0] = this.calcR;
/* 4142 */     this.lightDiffuse[this.lightCount][1] = this.calcG;
/* 4143 */     this.lightDiffuse[this.lightCount][2] = this.calcB;
/*      */     
/* 4145 */     this.lightType[this.lightCount] = 1;
/* 4146 */     this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
/* 4147 */     this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
/* 4148 */     this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
/* 4149 */     this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
/* 4150 */     this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
/* 4151 */     this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
/* 4152 */     lightDirection(this.lightCount, nx, ny, nz);
/* 4153 */     this.lightCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */   public void pointLight(float r, float g, float b, float x, float y, float z)
/*      */   {
/* 4159 */     if (this.lightCount == 8) {
/* 4160 */       throw new RuntimeException("can only create 8 lights");
/*      */     }
/* 4162 */     colorCalc(r, g, b);
/* 4163 */     this.lightDiffuse[this.lightCount][0] = this.calcR;
/* 4164 */     this.lightDiffuse[this.lightCount][1] = this.calcG;
/* 4165 */     this.lightDiffuse[this.lightCount][2] = this.calcB;
/*      */     
/* 4167 */     this.lightType[this.lightCount] = 2;
/* 4168 */     this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
/* 4169 */     this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
/* 4170 */     this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
/* 4171 */     this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
/* 4172 */     this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
/* 4173 */     this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
/* 4174 */     lightPosition(this.lightCount, x, y, z);
/* 4175 */     this.lightCount += 1;
/*      */     
/* 4177 */     this.lightingDependsOnVertexPosition = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void spotLight(float r, float g, float b, float x, float y, float z, float nx, float ny, float nz, float angle, float concentration)
/*      */   {
/* 4185 */     if (this.lightCount == 8) {
/* 4186 */       throw new RuntimeException("can only create 8 lights");
/*      */     }
/* 4188 */     colorCalc(r, g, b);
/* 4189 */     this.lightDiffuse[this.lightCount][0] = this.calcR;
/* 4190 */     this.lightDiffuse[this.lightCount][1] = this.calcG;
/* 4191 */     this.lightDiffuse[this.lightCount][2] = this.calcB;
/*      */     
/* 4193 */     this.lightType[this.lightCount] = 3;
/* 4194 */     this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
/* 4195 */     this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
/* 4196 */     this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
/* 4197 */     this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
/* 4198 */     this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
/* 4199 */     this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
/* 4200 */     lightPosition(this.lightCount, x, y, z);
/* 4201 */     lightDirection(this.lightCount, nx, ny, nz);
/* 4202 */     this.lightSpotAngle[this.lightCount] = angle;
/* 4203 */     this.lightSpotAngleCos[this.lightCount] = Math.max(0.0F, (float)Math.cos(angle));
/* 4204 */     this.lightSpotConcentration[this.lightCount] = concentration;
/* 4205 */     this.lightCount += 1;
/*      */     
/* 4207 */     this.lightingDependsOnVertexPosition = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void lightFalloff(float constant, float linear, float quadratic)
/*      */   {
/* 4216 */     this.currentLightFalloffConstant = constant;
/* 4217 */     this.currentLightFalloffLinear = linear;
/* 4218 */     this.currentLightFalloffQuadratic = quadratic;
/*      */     
/* 4220 */     this.lightingDependsOnVertexPosition = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void lightSpecular(float x, float y, float z)
/*      */   {
/* 4228 */     colorCalc(x, y, z);
/* 4229 */     this.currentLightSpecular[0] = this.calcR;
/* 4230 */     this.currentLightSpecular[1] = this.calcG;
/* 4231 */     this.currentLightSpecular[2] = this.calcB;
/*      */     
/* 4233 */     this.lightingDependsOnVertexPosition = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void lightPosition(int num, float x, float y, float z)
/*      */   {
/* 4242 */     this.lightPositionVec.set(x, y, z);
/* 4243 */     this.modelview.mult(this.lightPositionVec, this.lightPosition[num]);
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
/*      */   protected void lightDirection(int num, float x, float y, float z)
/*      */   {
/* 4260 */     this.lightNormal[num].set(this.modelviewInv.m00 * x + this.modelviewInv.m10 * y + this.modelviewInv.m20 * z + this.modelviewInv.m30, 
/* 4261 */       this.modelviewInv.m01 * x + this.modelviewInv.m11 * y + this.modelviewInv.m21 * z + this.modelviewInv.m31, 
/* 4262 */       this.modelviewInv.m02 * x + this.modelviewInv.m12 * y + this.modelviewInv.m22 * z + this.modelviewInv.m32);
/* 4263 */     this.lightNormal[num].normalize();
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
/*      */   protected void backgroundImpl(PImage image)
/*      */   {
/* 4306 */     System.arraycopy(image.pixels, 0, this.pixels, 0, this.pixels.length);
/* 4307 */     Arrays.fill(this.zbuffer, Float.MAX_VALUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backgroundImpl()
/*      */   {
/* 4315 */     Arrays.fill(this.pixels, this.backgroundColor);
/* 4316 */     Arrays.fill(this.zbuffer, Float.MAX_VALUE);
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
/*      */   public boolean is2D()
/*      */   {
/* 4387 */     return false;
/*      */   }
/*      */   
/*      */   public boolean is3D()
/*      */   {
/* 4392 */     return true;
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
/*      */   private final float sqrt(float a)
/*      */   {
/* 4423 */     return (float)Math.sqrt(a);
/*      */   }
/*      */   
/*      */   private final float mag(float a, float b, float c)
/*      */   {
/* 4428 */     return (float)Math.sqrt(a * a + b * b + c * c);
/*      */   }
/*      */   
/*      */   private final float clamp(float a)
/*      */   {
/* 4433 */     return a < 1.0F ? a : 1.0F;
/*      */   }
/*      */   
/*      */   private final float abs(float a)
/*      */   {
/* 4438 */     return a < 0.0F ? -a : a;
/*      */   }
/*      */   
/*      */ 
/*      */   private float dot(float ax, float ay, float az, float bx, float by, float bz)
/*      */   {
/* 4444 */     return ax * bx + ay * by + az * bz;
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
/*      */   private final void cross(float a0, float a1, float a2, float b0, float b1, float b2, PVector out)
/*      */   {
/* 4462 */     out.x = (a1 * b2 - a2 * b1);
/* 4463 */     out.y = (a2 * b0 - a0 * b2);
/* 4464 */     out.z = (a0 * b1 - a1 * b0);
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PGraphics3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */