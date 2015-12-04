/*     */ package processing.core;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract interface PConstants
/*     */ {
/*     */   public static final int X = 0;
/*     */   
/*     */ 
/*     */   public static final int Y = 1;
/*     */   
/*     */ 
/*     */   public static final int Z = 2;
/*     */   
/*     */ 
/*     */   public static final int R = 3;
/*     */   
/*     */ 
/*     */   public static final int G = 4;
/*     */   
/*     */ 
/*     */   public static final int B = 5;
/*     */   
/*     */ 
/*     */   public static final int A = 6;
/*     */   
/*     */ 
/*     */   public static final int U = 7;
/*     */   
/*     */ 
/*     */   public static final int V = 8;
/*     */   
/*     */ 
/*     */   public static final int NX = 9;
/*     */   
/*     */ 
/*     */   public static final int NY = 10;
/*     */   
/*     */ 
/*     */   public static final int NZ = 11;
/*     */   
/*     */ 
/*     */   public static final int EDGE = 12;
/*     */   
/*     */ 
/*     */   public static final int SR = 13;
/*     */   
/*     */ 
/*     */   public static final int SG = 14;
/*     */   
/*     */ 
/*     */   public static final int SB = 15;
/*     */   
/*     */ 
/*     */   public static final int SA = 16;
/*     */   
/*     */ 
/*     */   public static final int SW = 17;
/*     */   
/*     */ 
/*     */   public static final int TX = 18;
/*     */   
/*     */ 
/*     */   public static final int TY = 19;
/*     */   
/*     */ 
/*     */   public static final int TZ = 20;
/*     */   
/*     */ 
/*     */   public static final int VX = 21;
/*     */   
/*     */ 
/*     */   public static final int VY = 22;
/*     */   
/*     */ 
/*     */   public static final int VZ = 23;
/*     */   
/*     */ 
/*     */   public static final int VW = 24;
/*     */   
/*     */   public static final int AR = 25;
/*     */   
/*     */   public static final int AG = 26;
/*     */   
/*     */   public static final int AB = 27;
/*     */   
/*     */   public static final int DR = 3;
/*     */   
/*     */   public static final int DG = 4;
/*     */   
/*     */   public static final int DB = 5;
/*     */   
/*     */   public static final int DA = 6;
/*     */   
/*     */   public static final int SPR = 28;
/*     */   
/*     */   public static final int SPG = 29;
/*     */   
/*     */   public static final int SPB = 30;
/*     */   
/*     */   public static final int SHINE = 31;
/*     */   
/*     */   public static final int ER = 32;
/*     */   
/*     */   public static final int EG = 33;
/*     */   
/*     */   public static final int EB = 34;
/*     */   
/*     */   public static final int BEEN_LIT = 35;
/*     */   
/*     */   public static final int HAS_NORMAL = 36;
/*     */   
/*     */   public static final int VERTEX_FIELD_COUNT = 37;
/*     */   
/*     */   public static final String P2D = "processing.core.PGraphics2D";
/*     */   
/*     */   public static final String P3D = "processing.core.PGraphics3D";
/*     */   
/*     */   public static final String JAVA2D = "processing.core.PGraphicsJava2D";
/*     */   
/*     */   public static final String OPENGL = "processing.opengl.PGraphicsOpenGL";
/*     */   
/*     */   public static final String OPENGL2 = "processing.opengl2.PGraphicsOpenGL2";
/*     */   
/*     */   public static final String PDF = "processing.pdf.PGraphicsPDF";
/*     */   
/*     */   public static final String DXF = "processing.dxf.RawDXF";
/*     */   
/*     */   public static final int OTHER = 0;
/*     */   
/*     */   public static final int WINDOWS = 1;
/*     */   
/*     */   public static final int MACOSX = 2;
/*     */   
/*     */   public static final int LINUX = 3;
/*     */   
/* 137 */   public static final String[] platformNames = {
/* 138 */     "other", "windows", "macosx", "linux" };
/*     */   public static final float EPSILON = 1.0E-4F;
/*     */   public static final float MAX_FLOAT = Float.MAX_VALUE;
/*     */   public static final float MIN_FLOAT = -3.4028235E38F;
/*     */   public static final int MAX_INT = Integer.MAX_VALUE;
/*     */   public static final int MIN_INT = Integer.MIN_VALUE;
/*     */   public static final float PI = 3.1415927F;
/*     */   public static final float HALF_PI = 1.5707964F;
/*     */   public static final float THIRD_PI = 1.0471976F;
/*     */   public static final float QUARTER_PI = 0.7853982F;
/*     */   public static final float TWO_PI = 6.2831855F;
/*     */   public static final float DEG_TO_RAD = 0.017453292F;
/*     */   public static final float RAD_TO_DEG = 57.295776F;
/*     */   public static final String WHITESPACE = " \t\n\r\f ";
/*     */   public static final int RGB = 1;
/*     */   public static final int ARGB = 2;
/*     */   public static final int HSB = 3;
/*     */   public static final int ALPHA = 4;
/*     */   public static final int CMYK = 5;
/*     */   public static final int TIFF = 0;
/*     */   public static final int TARGA = 1;
/*     */   public static final int JPEG = 2;
/*     */   public static final int GIF = 3;
/*     */   public static final int BLUR = 11;
/*     */   public static final int GRAY = 12;
/*     */   public static final int INVERT = 13;
/*     */   public static final int OPAQUE = 14;
/*     */   public static final int POSTERIZE = 15;
/*     */   public static final int THRESHOLD = 16;
/*     */   public static final int ERODE = 17;
/*     */   public static final int DILATE = 18;
/*     */   public static final int REPLACE = 0;
/*     */   public static final int BLEND = 1;
/*     */   public static final int ADD = 2;
/*     */   public static final int SUBTRACT = 4;
/*     */   public static final int LIGHTEST = 8;
/*     */   public static final int DARKEST = 16;
/*     */   public static final int DIFFERENCE = 32;
/*     */   public static final int EXCLUSION = 64;
/*     */   public static final int MULTIPLY = 128;
/*     */   public static final int SCREEN = 256;
/*     */   public static final int OVERLAY = 512;
/*     */   public static final int HARD_LIGHT = 1024;
/*     */   public static final int SOFT_LIGHT = 2048;
/*     */   public static final int DODGE = 4096;
/*     */   public static final int BURN = 8192;
/*     */   public static final int ALPHA_MASK = -16777216;
/*     */   public static final int RED_MASK = 16711680;
/*     */   public static final int GREEN_MASK = 65280;
/*     */   public static final int BLUE_MASK = 255;
/*     */   public static final int CHATTER = 0;
/*     */   public static final int COMPLAINT = 1;
/*     */   public static final int PROBLEM = 2;
/*     */   public static final int PROJECTION = 0;
/*     */   public static final int MODELVIEW = 1;
/*     */   public static final int CUSTOM = 0;
/*     */   public static final int ORTHOGRAPHIC = 2;
/*     */   public static final int PERSPECTIVE = 3;
/*     */   public static final int POINT = 2;
/*     */   public static final int POINTS = 2;
/*     */   public static final int LINE = 4;
/*     */   public static final int LINES = 4;
/*     */   public static final int TRIANGLE = 8;
/*     */   public static final int TRIANGLES = 9;
/*     */   public static final int TRIANGLE_STRIP = 10;
/*     */   public static final int TRIANGLE_FAN = 11;
/*     */   public static final int QUAD = 16;
/*     */   public static final int QUADS = 16;
/*     */   public static final int QUAD_STRIP = 17;
/*     */   public static final int POLYGON = 20;
/*     */   public static final int PATH = 21;
/*     */   public static final int RECT = 30;
/*     */   public static final int ELLIPSE = 31;
/*     */   public static final int ARC = 32;
/*     */   public static final int SPHERE = 40;
/*     */   public static final int BOX = 41;
/*     */   public static final int LINE_STRIP = 50;
/*     */   public static final int LINE_LOOP = 51;
/*     */   public static final int POINT_SPRITES = 52;
/*     */   public static final int OPEN = 1;
/*     */   public static final int CLOSE = 2;
/*     */   public static final int CORNER = 0;
/*     */   public static final int CORNERS = 1;
/*     */   public static final int RADIUS = 2;
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public static final int CENTER_RADIUS = 2;
/*     */   public static final int CENTER = 3;
/*     */   public static final int DIAMETER = 3;
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public static final int CENTER_DIAMETER = 3;
/*     */   public static final int BASELINE = 0;
/*     */   public static final int TOP = 101;
/*     */   public static final int BOTTOM = 102;
/*     */   public static final int NORMAL = 1;
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public static final int NORMALIZED = 1;
/*     */   public static final int IMAGE = 2;
/*     */   public static final int MODEL = 4;
/*     */   public static final int SHAPE = 5;
/*     */   public static final int TEXTURE2D = 0;
/*     */   public static final int BILINEAR = 3;
/*     */   public static final int TRILINEAR = 4;
/*     */   public static final int CLAMP = 0;
/*     */   public static final int REPEAT = 1;
/*     */   public static final int LINEAR = 0;
/*     */   public static final int QUADRATIC = 1;
/*     */   public static final int STATIC = 0;
/*     */   public static final int DYNAMIC = 1;
/*     */   public static final int STREAM = 2;
/*     */   public static final int SQUARE = 1;
/*     */   public static final int ROUND = 2;
/*     */   public static final int PROJECT = 4;
/*     */   public static final int MITER = 8;
/*     */   public static final int BEVEL = 32;
/*     */   public static final int AMBIENT = 0;
/*     */   public static final int DIRECTIONAL = 1;
/*     */   public static final int SPOT = 3;
/*     */   public static final char BACKSPACE = '\b';
/*     */   public static final char TAB = '\t';
/*     */   public static final char ENTER = '\n';
/*     */   public static final char RETURN = '\r';
/*     */   public static final char ESC = '\033';
/*     */   public static final char DELETE = '';
/*     */   public static final int CODED = 65535;
/*     */   public static final int UP = 38;
/*     */   public static final int DOWN = 40;
/*     */   public static final int LEFT = 37;
/*     */   public static final int RIGHT = 39;
/*     */   public static final int ALT = 18;
/*     */   public static final int CONTROL = 17;
/*     */   public static final int SHIFT = 16;
/*     */   public static final int ARROW = 0;
/*     */   public static final int CROSS = 1;
/*     */   public static final int HAND = 12;
/*     */   public static final int MOVE = 13;
/*     */   public static final int TEXT = 2;
/*     */   public static final int WAIT = 3;
/*     */   public static final int DISABLE_OPENGL_2X_SMOOTH = 1;
/*     */   public static final int ENABLE_OPENGL_2X_SMOOTH = -1;
/*     */   public static final int ENABLE_OPENGL_4X_SMOOTH = 2;
/*     */   public static final int ENABLE_NATIVE_FONTS = 3;
/*     */   public static final int DISABLE_NATIVE_FONTS = -3;
/*     */   public static final int DISABLE_DEPTH_TEST = 4;
/*     */   public static final int ENABLE_DEPTH_TEST = -4;
/*     */   public static final int ENABLE_DEPTH_SORT = 5;
/*     */   public static final int DISABLE_DEPTH_SORT = -5;
/*     */   public static final int DISABLE_OPENGL_ERROR_REPORT = 6;
/*     */   public static final int ENABLE_OPENGL_ERROR_REPORT = -6;
/*     */   public static final int ENABLE_ACCURATE_TEXTURES = 7;
/*     */   public static final int DISABLE_ACCURATE_TEXTURES = -7;
/*     */   public static final int DISABLE_DEPTH_MASK = 8;
/*     */   public static final int ENABLE_DEPTH_MASK = -8;
/*     */   public static final int HINT_COUNT = 10;
/*     */   public static final int FIXED = 0;
/*     */   public static final int PROG_GL2 = 1;
/*     */   public static final int PROG_GL3 = 2;
/*     */   public static final int PROG_GL4 = 3;
/*     */   public static final String ERROR_BACKGROUND_IMAGE_SIZE = "background image must be the same size as your application";
/*     */   public static final String ERROR_BACKGROUND_IMAGE_FORMAT = "background images should be RGB or ARGB";
/*     */   public static final String ERROR_TEXTFONT_NULL_PFONT = "A null PFont was passed to textFont()";
/*     */   public static final String ERROR_PUSHMATRIX_OVERFLOW = "Too many calls to pushMatrix().";
/*     */   public static final String ERROR_PUSHMATRIX_UNDERFLOW = "Too many calls to popMatrix(), and not enough to pushMatrix().";
/*     */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */