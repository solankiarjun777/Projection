/*      */ package processing.core;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PTriangle
/*      */   implements PConstants
/*      */ {
/*      */   static final float PIXEL_CENTER = 0.5F;
/*      */   
/*      */ 
/*      */   static final int R_GOURAUD = 1;
/*      */   
/*      */ 
/*      */   static final int R_TEXTURE8 = 2;
/*      */   
/*      */ 
/*      */   static final int R_TEXTURE24 = 4;
/*      */   
/*      */ 
/*      */   static final int R_TEXTURE32 = 8;
/*      */   
/*      */ 
/*      */   static final int R_ALPHA = 16;
/*      */   
/*      */ 
/*      */   private int[] m_pixels;
/*      */   
/*      */ 
/*      */   private int[] m_texture;
/*      */   
/*      */ 
/*      */   private float[] m_zbuffer;
/*      */   
/*      */ 
/*      */   private int SCREEN_WIDTH;
/*      */   
/*      */ 
/*      */   private int SCREEN_HEIGHT;
/*      */   
/*      */ 
/*      */   private int TEX_WIDTH;
/*      */   
/*      */ 
/*      */   private int TEX_HEIGHT;
/*      */   
/*      */ 
/*      */   private float F_TEX_WIDTH;
/*      */   
/*      */ 
/*      */   private float F_TEX_HEIGHT;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_UV;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_RGB;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_ALPHA;
/*      */   
/*      */ 
/*      */   private static final int DEFAULT_INTERP_POWER = 3;
/*      */   
/*   64 */   private static int TEX_INTERP_POWER = 3;
/*      */   
/*      */   private float[] x_array;
/*      */   
/*      */   private float[] y_array;
/*      */   
/*      */   private float[] z_array;
/*      */   
/*      */   private float[] camX;
/*      */   
/*      */   private float[] camY;
/*      */   
/*      */   private float[] camZ;
/*      */   
/*      */   private float[] u_array;
/*      */   
/*      */   private float[] v_array;
/*      */   
/*      */   private float[] r_array;
/*      */   
/*      */   private float[] g_array;
/*      */   
/*      */   private float[] b_array;
/*      */   
/*      */   private float[] a_array;
/*      */   
/*      */   private int o0;
/*      */   
/*      */   private int o1;
/*      */   
/*      */   private int o2;
/*      */   
/*      */   private float r0;
/*      */   
/*      */   private float r1;
/*      */   
/*      */   private float r2;
/*      */   
/*      */   private float g0;
/*      */   
/*      */   private float g1;
/*      */   
/*      */   private float g2;
/*      */   
/*      */   private float b0;
/*      */   
/*      */   private float b1;
/*      */   
/*      */   private float b2;
/*      */   
/*      */   private float a0;
/*      */   
/*      */   private float a1;
/*      */   
/*      */   private float a2;
/*      */   
/*      */   private float u0;
/*      */   
/*      */   private float u1;
/*      */   
/*      */   private float u2;
/*      */   
/*      */   private float v0;
/*      */   
/*      */   private float v1;
/*      */   
/*      */   private float v2;
/*      */   
/*      */   private float dx2;
/*      */   
/*      */   private float dy0;
/*      */   
/*      */   private float dy1;
/*      */   
/*      */   private float dy2;
/*      */   
/*      */   private float dz0;
/*      */   
/*      */   private float dz2;
/*      */   
/*      */   private float du0;
/*      */   
/*      */   private float du2;
/*      */   
/*      */   private float dv0;
/*      */   
/*      */   private float dv2;
/*      */   
/*      */   private float dr0;
/*      */   
/*      */   private float dr2;
/*      */   
/*      */   private float dg0;
/*      */   
/*      */   private float dg2;
/*      */   
/*      */   private float db0;
/*      */   
/*      */   private float db2;
/*      */   
/*      */   private float da0;
/*      */   
/*      */   private float da2;
/*      */   
/*      */   private float uleft;
/*      */   
/*      */   private float vleft;
/*      */   
/*      */   private float uleftadd;
/*      */   
/*      */   private float vleftadd;
/*      */   
/*      */   private float xleft;
/*      */   
/*      */   private float xrght;
/*      */   
/*      */   private float xadd1;
/*      */   
/*      */   private float xadd2;
/*      */   
/*      */   private float zleft;
/*      */   
/*      */   private float zleftadd;
/*      */   private float rleft;
/*      */   private float gleft;
/*      */   private float bleft;
/*      */   private float aleft;
/*      */   private float rleftadd;
/*      */   private float gleftadd;
/*      */   private float bleftadd;
/*      */   private float aleftadd;
/*      */   private float dta;
/*      */   private float temp;
/*      */   private float width;
/*      */   private int iuadd;
/*      */   private int ivadd;
/*      */   private int iradd;
/*      */   private int igadd;
/*      */   private int ibadd;
/*      */   private int iaadd;
/*      */   private float izadd;
/*      */   private int m_fill;
/*      */   public int m_drawFlags;
/*      */   private PGraphics3D parent;
/*      */   private boolean noDepthTest;
/*      */   private boolean m_culling;
/*      */   private boolean m_singleRight;
/*  211 */   private boolean m_bilinear = true;
/*      */   private float ax;
/*      */   private float ay;
/*      */   private float az;
/*      */   private float bx;
/*      */   private float by;
/*      */   private float bz;
/*      */   private float cx;
/*      */   private float cy;
/*      */   private float cz;
/*      */   private float nearPlaneWidth;
/*      */   private float nearPlaneHeight;
/*      */   private float nearPlaneDepth;
/*      */   private float xmult;
/*      */   private float ymult;
/*      */   private float newax;
/*      */   private float newbx;
/*      */   private float newcx;
/*      */   private boolean firstSegment;
/*      */   
/*      */   public PTriangle(PGraphics3D g) {
/*  232 */     this.x_array = new float[3];
/*  233 */     this.y_array = new float[3];
/*  234 */     this.z_array = new float[3];
/*  235 */     this.u_array = new float[3];
/*  236 */     this.v_array = new float[3];
/*  237 */     this.r_array = new float[3];
/*  238 */     this.g_array = new float[3];
/*  239 */     this.b_array = new float[3];
/*  240 */     this.a_array = new float[3];
/*      */     
/*  242 */     this.camX = new float[3];
/*  243 */     this.camY = new float[3];
/*  244 */     this.camZ = new float[3];
/*      */     
/*  246 */     this.parent = g;
/*  247 */     reset();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void reset()
/*      */   {
/*  257 */     this.SCREEN_WIDTH = this.parent.width;
/*  258 */     this.SCREEN_HEIGHT = this.parent.height;
/*      */     
/*      */ 
/*      */ 
/*  262 */     this.m_pixels = this.parent.pixels;
/*      */     
/*  264 */     this.m_zbuffer = this.parent.zbuffer;
/*      */     
/*  266 */     this.noDepthTest = this.parent.hints[4];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  271 */     this.INTERPOLATE_UV = false;
/*  272 */     this.INTERPOLATE_RGB = false;
/*  273 */     this.INTERPOLATE_ALPHA = false;
/*      */     
/*  275 */     this.m_texture = null;
/*  276 */     this.m_drawFlags = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCulling(boolean tf)
/*      */   {
/*  284 */     this.m_culling = tf;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVertices(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2)
/*      */   {
/*  294 */     this.x_array[0] = x0;
/*  295 */     this.x_array[1] = x1;
/*  296 */     this.x_array[2] = x2;
/*      */     
/*  298 */     this.y_array[0] = y0;
/*  299 */     this.y_array[1] = y1;
/*  300 */     this.y_array[2] = y2;
/*      */     
/*  302 */     this.z_array[0] = z0;
/*  303 */     this.z_array[1] = z1;
/*  304 */     this.z_array[2] = z2;
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
/*      */   public void setCamVertices(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2)
/*      */   {
/*  317 */     this.camX[0] = x0;
/*  318 */     this.camX[1] = x1;
/*  319 */     this.camX[2] = x2;
/*      */     
/*  321 */     this.camY[0] = y0;
/*  322 */     this.camY[1] = y1;
/*  323 */     this.camY[2] = y2;
/*      */     
/*  325 */     this.camZ[0] = z0;
/*  326 */     this.camZ[1] = z1;
/*  327 */     this.camZ[2] = z2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUV(float u0, float v0, float u1, float v1, float u2, float v2)
/*      */   {
/*  338 */     this.u_array[0] = ((u0 * this.F_TEX_WIDTH + 0.5F) * 65536.0F);
/*  339 */     this.u_array[1] = ((u1 * this.F_TEX_WIDTH + 0.5F) * 65536.0F);
/*  340 */     this.u_array[2] = ((u2 * this.F_TEX_WIDTH + 0.5F) * 65536.0F);
/*  341 */     this.v_array[0] = ((v0 * this.F_TEX_HEIGHT + 0.5F) * 65536.0F);
/*  342 */     this.v_array[1] = ((v1 * this.F_TEX_HEIGHT + 0.5F) * 65536.0F);
/*  343 */     this.v_array[2] = ((v2 * this.F_TEX_HEIGHT + 0.5F) * 65536.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setIntensities(float r0, float g0, float b0, float a0, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2)
/*      */   {
/*  354 */     if ((a0 != 1.0F) || (a1 != 1.0F) || (a2 != 1.0F)) {
/*  355 */       this.INTERPOLATE_ALPHA = true;
/*  356 */       this.a_array[0] = ((a0 * 253.0F + 1.0F) * 65536.0F);
/*  357 */       this.a_array[1] = ((a1 * 253.0F + 1.0F) * 65536.0F);
/*  358 */       this.a_array[2] = ((a2 * 253.0F + 1.0F) * 65536.0F);
/*  359 */       this.m_drawFlags |= 0x10;
/*      */     } else {
/*  361 */       this.INTERPOLATE_ALPHA = false;
/*  362 */       this.m_drawFlags &= 0xFFFFFFEF;
/*      */     }
/*      */     
/*      */ 
/*  366 */     if ((r0 != r1) || (r1 != r2)) {
/*  367 */       this.INTERPOLATE_RGB = true;
/*  368 */       this.m_drawFlags |= 0x1;
/*  369 */     } else if ((g0 != g1) || (g1 != g2)) {
/*  370 */       this.INTERPOLATE_RGB = true;
/*  371 */       this.m_drawFlags |= 0x1;
/*  372 */     } else if ((b0 != b1) || (b1 != b2)) {
/*  373 */       this.INTERPOLATE_RGB = true;
/*  374 */       this.m_drawFlags |= 0x1;
/*      */     }
/*      */     else {
/*  377 */       this.m_drawFlags &= 0xFFFFFFFE;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  382 */     this.r_array[0] = ((r0 * 253.0F + 1.0F) * 65536.0F);
/*  383 */     this.r_array[1] = ((r1 * 253.0F + 1.0F) * 65536.0F);
/*  384 */     this.r_array[2] = ((r2 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*  386 */     this.g_array[0] = ((g0 * 253.0F + 1.0F) * 65536.0F);
/*  387 */     this.g_array[1] = ((g1 * 253.0F + 1.0F) * 65536.0F);
/*  388 */     this.g_array[2] = ((g2 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*  390 */     this.b_array[0] = ((b0 * 253.0F + 1.0F) * 65536.0F);
/*  391 */     this.b_array[1] = ((b1 * 253.0F + 1.0F) * 65536.0F);
/*  392 */     this.b_array[2] = ((b2 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*      */ 
/*  395 */     this.m_fill = 
/*  396 */       (0xFF000000 | (int)(255.0F * r0) << 16 | (int)(255.0F * g0) << 8 | (int)(255.0F * b0));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTexture(PImage image)
/*      */   {
/*  405 */     this.m_texture = image.pixels;
/*  406 */     this.TEX_WIDTH = image.width;
/*  407 */     this.TEX_HEIGHT = image.height;
/*  408 */     this.F_TEX_WIDTH = (this.TEX_WIDTH - 1);
/*  409 */     this.F_TEX_HEIGHT = (this.TEX_HEIGHT - 1);
/*  410 */     this.INTERPOLATE_UV = true;
/*      */     
/*  412 */     if (image.format == 2) {
/*  413 */       this.m_drawFlags |= 0x8;
/*  414 */     } else if (image.format == 1) {
/*  415 */       this.m_drawFlags |= 0x4;
/*  416 */     } else if (image.format == 4) {
/*  417 */       this.m_drawFlags |= 0x2;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUV(float[] u, float[] v)
/*      */   {
/*  426 */     if (this.m_bilinear)
/*      */     {
/*  428 */       this.u_array[0] = (u[0] * this.F_TEX_WIDTH * 65500.0F);
/*  429 */       this.u_array[1] = (u[1] * this.F_TEX_WIDTH * 65500.0F);
/*  430 */       this.u_array[2] = (u[2] * this.F_TEX_WIDTH * 65500.0F);
/*  431 */       this.v_array[0] = (v[0] * this.F_TEX_HEIGHT * 65500.0F);
/*  432 */       this.v_array[1] = (v[1] * this.F_TEX_HEIGHT * 65500.0F);
/*  433 */       this.v_array[2] = (v[2] * this.F_TEX_HEIGHT * 65500.0F);
/*      */     }
/*      */     else {
/*  436 */       this.u_array[0] = (u[0] * this.TEX_WIDTH * 65500.0F);
/*  437 */       this.u_array[1] = (u[1] * this.TEX_WIDTH * 65500.0F);
/*  438 */       this.u_array[2] = (u[2] * this.TEX_WIDTH * 65500.0F);
/*  439 */       this.v_array[0] = (v[0] * this.TEX_HEIGHT * 65500.0F);
/*  440 */       this.v_array[1] = (v[1] * this.TEX_HEIGHT * 65500.0F);
/*  441 */       this.v_array[2] = (v[2] * this.TEX_HEIGHT * 65500.0F);
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
/*      */   public void render()
/*      */   {
/*  458 */     float y0 = this.y_array[0];
/*  459 */     float y1 = this.y_array[1];
/*  460 */     float y2 = this.y_array[2];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  466 */     this.firstSegment = true;
/*      */     
/*      */ 
/*  469 */     if (this.m_culling) {
/*  470 */       float x0 = this.x_array[0];
/*  471 */       if ((this.x_array[2] - x0) * (y1 - y0) < (this.x_array[1] - x0) * (y2 - y0)) {
/*  472 */         return;
/*      */       }
/*      */     }
/*      */     
/*  476 */     if (y0 < y1) {
/*  477 */       if (y2 < y1) {
/*  478 */         if (y2 < y0) {
/*  479 */           this.o0 = 2;
/*  480 */           this.o1 = 0;
/*  481 */           this.o2 = 1;
/*      */         } else {
/*  483 */           this.o0 = 0;
/*  484 */           this.o1 = 2;
/*  485 */           this.o2 = 1;
/*      */         }
/*      */       } else {
/*  488 */         this.o0 = 0;
/*  489 */         this.o1 = 1;
/*  490 */         this.o2 = 2;
/*      */       }
/*      */     }
/*  493 */     else if (y2 > y1) {
/*  494 */       if (y2 < y0) {
/*  495 */         this.o0 = 1;
/*  496 */         this.o1 = 2;
/*  497 */         this.o2 = 0;
/*      */       } else {
/*  499 */         this.o0 = 1;
/*  500 */         this.o1 = 0;
/*  501 */         this.o2 = 2;
/*      */       }
/*      */     } else {
/*  504 */       this.o0 = 2;
/*  505 */       this.o1 = 1;
/*  506 */       this.o2 = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  516 */     y0 = this.y_array[this.o0];
/*  517 */     int yi0 = (int)(y0 + 0.5F);
/*  518 */     if (yi0 > this.SCREEN_HEIGHT)
/*  519 */       return;
/*  520 */     if (yi0 < 0) {
/*  521 */       yi0 = 0;
/*      */     }
/*      */     
/*  524 */     y2 = this.y_array[this.o2];
/*  525 */     int yi2 = (int)(y2 + 0.5F);
/*  526 */     if (yi2 < 0)
/*  527 */       return;
/*  528 */     if (yi2 > this.SCREEN_HEIGHT) {
/*  529 */       yi2 = this.SCREEN_HEIGHT;
/*      */     }
/*      */     
/*      */ 
/*  533 */     if (yi2 > yi0) {
/*  534 */       float x0 = this.x_array[this.o0];
/*  535 */       float x1 = this.x_array[this.o1];
/*  536 */       float x2 = this.x_array[this.o2];
/*      */       
/*      */ 
/*  539 */       y1 = this.y_array[this.o1];
/*  540 */       int yi1 = (int)(y1 + 0.5F);
/*  541 */       if (yi1 < 0)
/*  542 */         yi1 = 0;
/*  543 */       if (yi1 > this.SCREEN_HEIGHT) {
/*  544 */         yi1 = this.SCREEN_HEIGHT;
/*      */       }
/*      */       
/*  547 */       this.dx2 = (x2 - x0);
/*  548 */       this.dy0 = (y1 - y0);
/*  549 */       this.dy2 = (y2 - y0);
/*  550 */       this.xadd2 = (this.dx2 / this.dy2);
/*  551 */       this.temp = (this.dy0 / this.dy2);
/*  552 */       this.width = (this.temp * this.dx2 + x0 - x1);
/*      */       
/*      */ 
/*  555 */       if (this.INTERPOLATE_ALPHA) {
/*  556 */         this.a0 = this.a_array[this.o0];
/*  557 */         this.a1 = this.a_array[this.o1];
/*  558 */         this.a2 = this.a_array[this.o2];
/*  559 */         this.da0 = (this.a1 - this.a0);
/*  560 */         this.da2 = (this.a2 - this.a0);
/*  561 */         this.iaadd = ((int)((this.temp * this.da2 - this.da0) / this.width));
/*      */       }
/*      */       
/*      */ 
/*  565 */       if (this.INTERPOLATE_RGB) {
/*  566 */         this.r0 = this.r_array[this.o0];
/*  567 */         this.r1 = this.r_array[this.o1];
/*  568 */         this.r2 = this.r_array[this.o2];
/*      */         
/*  570 */         this.g0 = this.g_array[this.o0];
/*  571 */         this.g1 = this.g_array[this.o1];
/*  572 */         this.g2 = this.g_array[this.o2];
/*      */         
/*  574 */         this.b0 = this.b_array[this.o0];
/*  575 */         this.b1 = this.b_array[this.o1];
/*  576 */         this.b2 = this.b_array[this.o2];
/*      */         
/*  578 */         this.dr0 = (this.r1 - this.r0);
/*  579 */         this.dg0 = (this.g1 - this.g0);
/*  580 */         this.db0 = (this.b1 - this.b0);
/*      */         
/*  582 */         this.dr2 = (this.r2 - this.r0);
/*  583 */         this.dg2 = (this.g2 - this.g0);
/*  584 */         this.db2 = (this.b2 - this.b0);
/*      */         
/*  586 */         this.iradd = ((int)((this.temp * this.dr2 - this.dr0) / this.width));
/*  587 */         this.igadd = ((int)((this.temp * this.dg2 - this.dg0) / this.width));
/*  588 */         this.ibadd = ((int)((this.temp * this.db2 - this.db0) / this.width));
/*      */       }
/*      */       
/*      */ 
/*  592 */       if (this.INTERPOLATE_UV) {
/*  593 */         this.u0 = this.u_array[this.o0];
/*  594 */         this.u1 = this.u_array[this.o1];
/*  595 */         this.u2 = this.u_array[this.o2];
/*  596 */         this.v0 = this.v_array[this.o0];
/*  597 */         this.v1 = this.v_array[this.o1];
/*  598 */         this.v2 = this.v_array[this.o2];
/*  599 */         this.du0 = (this.u1 - this.u0);
/*  600 */         this.dv0 = (this.v1 - this.v0);
/*  601 */         this.du2 = (this.u2 - this.u0);
/*  602 */         this.dv2 = (this.v2 - this.v0);
/*  603 */         this.iuadd = ((int)((this.temp * this.du2 - this.du0) / this.width));
/*  604 */         this.ivadd = ((int)((this.temp * this.dv2 - this.dv0) / this.width));
/*      */       }
/*      */       
/*  607 */       float z0 = this.z_array[this.o0];
/*  608 */       float z1 = this.z_array[this.o1];
/*  609 */       float z2 = this.z_array[this.o2];
/*  610 */       this.dz0 = (z1 - z0);
/*  611 */       this.dz2 = (z2 - z0);
/*  612 */       this.izadd = ((this.temp * this.dz2 - this.dz0) / this.width);
/*      */       
/*      */ 
/*  615 */       if (yi1 > yi0) {
/*  616 */         this.dta = (yi0 + 0.5F - y0);
/*  617 */         this.xadd1 = ((x1 - x0) / this.dy0);
/*      */         
/*      */ 
/*      */ 
/*  621 */         if (this.xadd2 > this.xadd1) {
/*  622 */           this.xleft = (x0 + this.dta * this.xadd1);
/*  623 */           this.xrght = (x0 + this.dta * this.xadd2);
/*  624 */           this.zleftadd = (this.dz0 / this.dy0);
/*  625 */           this.zleft = (this.dta * this.zleftadd + z0);
/*      */           
/*  627 */           if (this.INTERPOLATE_UV) {
/*  628 */             this.uleftadd = (this.du0 / this.dy0);
/*  629 */             this.vleftadd = (this.dv0 / this.dy0);
/*  630 */             this.uleft = (this.dta * this.uleftadd + this.u0);
/*  631 */             this.vleft = (this.dta * this.vleftadd + this.v0);
/*      */           }
/*      */           
/*  634 */           if (this.INTERPOLATE_RGB) {
/*  635 */             this.rleftadd = (this.dr0 / this.dy0);
/*  636 */             this.gleftadd = (this.dg0 / this.dy0);
/*  637 */             this.bleftadd = (this.db0 / this.dy0);
/*  638 */             this.rleft = (this.dta * this.rleftadd + this.r0);
/*  639 */             this.gleft = (this.dta * this.gleftadd + this.g0);
/*  640 */             this.bleft = (this.dta * this.bleftadd + this.b0);
/*      */           }
/*      */           
/*  643 */           if (this.INTERPOLATE_ALPHA) {
/*  644 */             this.aleftadd = (this.da0 / this.dy0);
/*  645 */             this.aleft = (this.dta * this.aleftadd + this.a0);
/*      */             
/*  647 */             if (this.m_drawFlags == 16) {
/*  648 */               drawsegment_plain_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  649 */             } else if (this.m_drawFlags == 17) {
/*  650 */               drawsegment_gouraud_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  651 */             } else if (this.m_drawFlags == 18) {
/*  652 */               drawsegment_texture8_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  653 */             } else if (this.m_drawFlags == 20) {
/*  654 */               drawsegment_texture24_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  655 */             } else if (this.m_drawFlags == 24) {
/*  656 */               drawsegment_texture32_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  657 */             } else if (this.m_drawFlags == 19) {
/*  658 */               drawsegment_gouraud_texture8_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  659 */             } else if (this.m_drawFlags == 21) {
/*  660 */               drawsegment_gouraud_texture24_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  661 */             } else if (this.m_drawFlags == 25) {
/*  662 */               drawsegment_gouraud_texture32_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*      */             }
/*      */           }
/*  665 */           else if (this.m_drawFlags == 0) {
/*  666 */             drawsegment_plain(this.xadd1, this.xadd2, yi0, yi1);
/*  667 */           } else if (this.m_drawFlags == 1) {
/*  668 */             drawsegment_gouraud(this.xadd1, this.xadd2, yi0, yi1);
/*  669 */           } else if (this.m_drawFlags == 2) {
/*  670 */             drawsegment_texture8(this.xadd1, this.xadd2, yi0, yi1);
/*  671 */           } else if (this.m_drawFlags == 4) {
/*  672 */             drawsegment_texture24(this.xadd1, this.xadd2, yi0, yi1);
/*  673 */           } else if (this.m_drawFlags == 8) {
/*  674 */             drawsegment_texture32(this.xadd1, this.xadd2, yi0, yi1);
/*  675 */           } else if (this.m_drawFlags == 3) {
/*  676 */             drawsegment_gouraud_texture8(this.xadd1, this.xadd2, yi0, yi1);
/*  677 */           } else if (this.m_drawFlags == 5) {
/*  678 */             drawsegment_gouraud_texture24(this.xadd1, this.xadd2, yi0, yi1);
/*  679 */           } else if (this.m_drawFlags == 9) {
/*  680 */             drawsegment_gouraud_texture32(this.xadd1, this.xadd2, yi0, yi1);
/*      */           }
/*      */           
/*  683 */           this.m_singleRight = true;
/*      */         } else {
/*  685 */           this.xleft = (x0 + this.dta * this.xadd2);
/*  686 */           this.xrght = (x0 + this.dta * this.xadd1);
/*  687 */           this.zleftadd = (this.dz2 / this.dy2);
/*  688 */           this.zleft = (this.dta * this.zleftadd + z0);
/*      */           
/*  690 */           if (this.INTERPOLATE_UV) {
/*  691 */             this.uleftadd = (this.du2 / this.dy2);
/*  692 */             this.vleftadd = (this.dv2 / this.dy2);
/*  693 */             this.uleft = (this.dta * this.uleftadd + this.u0);
/*  694 */             this.vleft = (this.dta * this.vleftadd + this.v0);
/*      */           }
/*      */           
/*      */ 
/*  698 */           if (this.INTERPOLATE_RGB) {
/*  699 */             this.rleftadd = (this.dr2 / this.dy2);
/*  700 */             this.gleftadd = (this.dg2 / this.dy2);
/*  701 */             this.bleftadd = (this.db2 / this.dy2);
/*  702 */             this.rleft = (this.dta * this.rleftadd + this.r0);
/*  703 */             this.gleft = (this.dta * this.gleftadd + this.g0);
/*  704 */             this.bleft = (this.dta * this.bleftadd + this.b0);
/*      */           }
/*      */           
/*      */ 
/*  708 */           if (this.INTERPOLATE_ALPHA) {
/*  709 */             this.aleftadd = (this.da2 / this.dy2);
/*  710 */             this.aleft = (this.dta * this.aleftadd + this.a0);
/*      */             
/*  712 */             if (this.m_drawFlags == 16) {
/*  713 */               drawsegment_plain_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  714 */             } else if (this.m_drawFlags == 17) {
/*  715 */               drawsegment_gouraud_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  716 */             } else if (this.m_drawFlags == 18) {
/*  717 */               drawsegment_texture8_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  718 */             } else if (this.m_drawFlags == 20) {
/*  719 */               drawsegment_texture24_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  720 */             } else if (this.m_drawFlags == 24) {
/*  721 */               drawsegment_texture32_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  722 */             } else if (this.m_drawFlags == 19) {
/*  723 */               drawsegment_gouraud_texture8_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  724 */             } else if (this.m_drawFlags == 21) {
/*  725 */               drawsegment_gouraud_texture24_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  726 */             } else if (this.m_drawFlags == 25) {
/*  727 */               drawsegment_gouraud_texture32_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*      */             }
/*      */           }
/*  730 */           else if (this.m_drawFlags == 0) {
/*  731 */             drawsegment_plain(this.xadd2, this.xadd1, yi0, yi1);
/*  732 */           } else if (this.m_drawFlags == 1) {
/*  733 */             drawsegment_gouraud(this.xadd2, this.xadd1, yi0, yi1);
/*  734 */           } else if (this.m_drawFlags == 2) {
/*  735 */             drawsegment_texture8(this.xadd2, this.xadd1, yi0, yi1);
/*  736 */           } else if (this.m_drawFlags == 4) {
/*  737 */             drawsegment_texture24(this.xadd2, this.xadd1, yi0, yi1);
/*  738 */           } else if (this.m_drawFlags == 8) {
/*  739 */             drawsegment_texture32(this.xadd2, this.xadd1, yi0, yi1);
/*  740 */           } else if (this.m_drawFlags == 3) {
/*  741 */             drawsegment_gouraud_texture8(this.xadd2, this.xadd1, yi0, yi1);
/*  742 */           } else if (this.m_drawFlags == 5) {
/*  743 */             drawsegment_gouraud_texture24(this.xadd2, this.xadd1, yi0, yi1);
/*  744 */           } else if (this.m_drawFlags == 9) {
/*  745 */             drawsegment_gouraud_texture32(this.xadd2, this.xadd1, yi0, yi1);
/*      */           }
/*      */           
/*  748 */           this.m_singleRight = false;
/*      */         }
/*      */         
/*      */ 
/*  752 */         if (yi2 == yi1) { return;
/*      */         }
/*      */         
/*  755 */         this.dy1 = (y2 - y1);
/*  756 */         this.xadd1 = ((x2 - x1) / this.dy1);
/*      */       }
/*      */       else
/*      */       {
/*  760 */         this.dy1 = (y2 - y1);
/*  761 */         this.xadd1 = ((x2 - x1) / this.dy1);
/*      */         
/*      */ 
/*  764 */         if (this.xadd2 < this.xadd1) {
/*  765 */           this.xrght = ((yi1 + 0.5F - y0) * this.xadd2 + x0);
/*  766 */           this.m_singleRight = true;
/*      */         } else {
/*  768 */           this.dta = (yi1 + 0.5F - y0);
/*  769 */           this.xleft = (this.dta * this.xadd2 + x0);
/*  770 */           this.zleftadd = (this.dz2 / this.dy2);
/*  771 */           this.zleft = (this.dta * this.zleftadd + z0);
/*      */           
/*  773 */           if (this.INTERPOLATE_UV) {
/*  774 */             this.uleftadd = (this.du2 / this.dy2);
/*  775 */             this.vleftadd = (this.dv2 / this.dy2);
/*  776 */             this.uleft = (this.dta * this.uleftadd + this.u0);
/*  777 */             this.vleft = (this.dta * this.vleftadd + this.v0);
/*      */           }
/*      */           
/*  780 */           if (this.INTERPOLATE_RGB) {
/*  781 */             this.rleftadd = (this.dr2 / this.dy2);
/*  782 */             this.gleftadd = (this.dg2 / this.dy2);
/*  783 */             this.bleftadd = (this.db2 / this.dy2);
/*  784 */             this.rleft = (this.dta * this.rleftadd + this.r0);
/*  785 */             this.gleft = (this.dta * this.gleftadd + this.g0);
/*  786 */             this.bleft = (this.dta * this.bleftadd + this.b0);
/*      */           }
/*      */           
/*      */ 
/*  790 */           if (this.INTERPOLATE_ALPHA) {
/*  791 */             this.aleftadd = (this.da2 / this.dy2);
/*  792 */             this.aleft = (this.dta * this.aleftadd + this.a0);
/*      */           }
/*  794 */           this.m_singleRight = false;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  799 */       if (this.m_singleRight) {
/*  800 */         this.dta = (yi1 + 0.5F - y1);
/*  801 */         this.xleft = (this.dta * this.xadd1 + x1);
/*  802 */         this.zleftadd = ((z2 - z1) / this.dy1);
/*  803 */         this.zleft = (this.dta * this.zleftadd + z1);
/*      */         
/*  805 */         if (this.INTERPOLATE_UV) {
/*  806 */           this.uleftadd = ((this.u2 - this.u1) / this.dy1);
/*  807 */           this.vleftadd = ((this.v2 - this.v1) / this.dy1);
/*  808 */           this.uleft = (this.dta * this.uleftadd + this.u1);
/*  809 */           this.vleft = (this.dta * this.vleftadd + this.v1);
/*      */         }
/*      */         
/*  812 */         if (this.INTERPOLATE_RGB) {
/*  813 */           this.rleftadd = ((this.r2 - this.r1) / this.dy1);
/*  814 */           this.gleftadd = ((this.g2 - this.g1) / this.dy1);
/*  815 */           this.bleftadd = ((this.b2 - this.b1) / this.dy1);
/*  816 */           this.rleft = (this.dta * this.rleftadd + this.r1);
/*  817 */           this.gleft = (this.dta * this.gleftadd + this.g1);
/*  818 */           this.bleft = (this.dta * this.bleftadd + this.b1);
/*      */         }
/*      */         
/*  821 */         if (this.INTERPOLATE_ALPHA) {
/*  822 */           this.aleftadd = ((this.a2 - this.a1) / this.dy1);
/*  823 */           this.aleft = (this.dta * this.aleftadd + this.a1);
/*      */           
/*  825 */           if (this.m_drawFlags == 16) {
/*  826 */             drawsegment_plain_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  827 */           } else if (this.m_drawFlags == 17) {
/*  828 */             drawsegment_gouraud_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  829 */           } else if (this.m_drawFlags == 18) {
/*  830 */             drawsegment_texture8_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  831 */           } else if (this.m_drawFlags == 20) {
/*  832 */             drawsegment_texture24_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  833 */           } else if (this.m_drawFlags == 24) {
/*  834 */             drawsegment_texture32_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  835 */           } else if (this.m_drawFlags == 19) {
/*  836 */             drawsegment_gouraud_texture8_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  837 */           } else if (this.m_drawFlags == 21) {
/*  838 */             drawsegment_gouraud_texture24_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  839 */           } else if (this.m_drawFlags == 25) {
/*  840 */             drawsegment_gouraud_texture32_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*      */           }
/*      */         }
/*  843 */         else if (this.m_drawFlags == 0) {
/*  844 */           drawsegment_plain(this.xadd1, this.xadd2, yi1, yi2);
/*  845 */         } else if (this.m_drawFlags == 1) {
/*  846 */           drawsegment_gouraud(this.xadd1, this.xadd2, yi1, yi2);
/*  847 */         } else if (this.m_drawFlags == 2) {
/*  848 */           drawsegment_texture8(this.xadd1, this.xadd2, yi1, yi2);
/*  849 */         } else if (this.m_drawFlags == 4) {
/*  850 */           drawsegment_texture24(this.xadd1, this.xadd2, yi1, yi2);
/*  851 */         } else if (this.m_drawFlags == 8) {
/*  852 */           drawsegment_texture32(this.xadd1, this.xadd2, yi1, yi2);
/*  853 */         } else if (this.m_drawFlags == 3) {
/*  854 */           drawsegment_gouraud_texture8(this.xadd1, this.xadd2, yi1, yi2);
/*  855 */         } else if (this.m_drawFlags == 5) {
/*  856 */           drawsegment_gouraud_texture24(this.xadd1, this.xadd2, yi1, yi2);
/*  857 */         } else if (this.m_drawFlags == 9) {
/*  858 */           drawsegment_gouraud_texture32(this.xadd1, this.xadd2, yi1, yi2);
/*      */         }
/*      */       }
/*      */       else {
/*  862 */         this.xrght = ((yi1 + 0.5F - y1) * this.xadd1 + x1);
/*      */         
/*  864 */         if (this.INTERPOLATE_ALPHA) {
/*  865 */           if (this.m_drawFlags == 16) {
/*  866 */             drawsegment_plain_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  867 */           } else if (this.m_drawFlags == 17) {
/*  868 */             drawsegment_gouraud_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  869 */           } else if (this.m_drawFlags == 18) {
/*  870 */             drawsegment_texture8_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  871 */           } else if (this.m_drawFlags == 20) {
/*  872 */             drawsegment_texture24_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  873 */           } else if (this.m_drawFlags == 24) {
/*  874 */             drawsegment_texture32_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  875 */           } else if (this.m_drawFlags == 19) {
/*  876 */             drawsegment_gouraud_texture8_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  877 */           } else if (this.m_drawFlags == 21) {
/*  878 */             drawsegment_gouraud_texture24_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  879 */           } else if (this.m_drawFlags == 25) {
/*  880 */             drawsegment_gouraud_texture32_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*      */           }
/*      */         }
/*  883 */         else if (this.m_drawFlags == 0) {
/*  884 */           drawsegment_plain(this.xadd2, this.xadd1, yi1, yi2);
/*  885 */         } else if (this.m_drawFlags == 1) {
/*  886 */           drawsegment_gouraud(this.xadd2, this.xadd1, yi1, yi2);
/*  887 */         } else if (this.m_drawFlags == 2) {
/*  888 */           drawsegment_texture8(this.xadd2, this.xadd1, yi1, yi2);
/*  889 */         } else if (this.m_drawFlags == 4) {
/*  890 */           drawsegment_texture24(this.xadd2, this.xadd1, yi1, yi2);
/*  891 */         } else if (this.m_drawFlags == 8) {
/*  892 */           drawsegment_texture32(this.xadd2, this.xadd1, yi1, yi2);
/*  893 */         } else if (this.m_drawFlags == 3) {
/*  894 */           drawsegment_gouraud_texture8(this.xadd2, this.xadd1, yi1, yi2);
/*  895 */         } else if (this.m_drawFlags == 5) {
/*  896 */           drawsegment_gouraud_texture24(this.xadd2, this.xadd1, yi1, yi2);
/*  897 */         } else if (this.m_drawFlags == 9) {
/*  898 */           drawsegment_gouraud_texture32(this.xadd2, this.xadd1, yi1, yi2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean precomputeAccurateTexturing()
/*      */   {
/*  966 */     float myFact = 65500.0F;
/*  967 */     float myFact2 = 65500.0F;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  999 */     if (this.firstSegment) {
/* 1000 */       PMatrix3D myMatrix = 
/* 1001 */         new PMatrix3D(this.u_array[this.o0] / myFact, this.v_array[this.o0] / myFact2, 1.0F, 0.0F, 
/* 1002 */         this.u_array[this.o1] / myFact, this.v_array[this.o1] / myFact2, 1.0F, 0.0F, 
/* 1003 */         this.u_array[this.o2] / myFact, this.v_array[this.o2] / myFact2, 1.0F, 0.0F, 
/* 1004 */         0.0F, 0.0F, 0.0F, 1.0F);
/*      */       
/*      */ 
/*      */ 
/* 1008 */       if (!myMatrix.invert()) { return false;
/*      */       }
/*      */       
/* 1011 */       float m00 = myMatrix.m00 * this.camX[this.o0] + myMatrix.m01 * this.camX[this.o1] + myMatrix.m02 * this.camX[this.o2];
/* 1012 */       float m01 = myMatrix.m10 * this.camX[this.o0] + myMatrix.m11 * this.camX[this.o1] + myMatrix.m12 * this.camX[this.o2];
/* 1013 */       float m02 = myMatrix.m20 * this.camX[this.o0] + myMatrix.m21 * this.camX[this.o1] + myMatrix.m22 * this.camX[this.o2];
/* 1014 */       float m10 = myMatrix.m00 * this.camY[this.o0] + myMatrix.m01 * this.camY[this.o1] + myMatrix.m02 * this.camY[this.o2];
/* 1015 */       float m11 = myMatrix.m10 * this.camY[this.o0] + myMatrix.m11 * this.camY[this.o1] + myMatrix.m12 * this.camY[this.o2];
/* 1016 */       float m12 = myMatrix.m20 * this.camY[this.o0] + myMatrix.m21 * this.camY[this.o1] + myMatrix.m22 * this.camY[this.o2];
/* 1017 */       float m20 = -(myMatrix.m00 * this.camZ[this.o0] + myMatrix.m01 * this.camZ[this.o1] + myMatrix.m02 * this.camZ[this.o2]);
/* 1018 */       float m21 = -(myMatrix.m10 * this.camZ[this.o0] + myMatrix.m11 * this.camZ[this.o1] + myMatrix.m12 * this.camZ[this.o2]);
/* 1019 */       float m22 = -(myMatrix.m20 * this.camZ[this.o0] + myMatrix.m21 * this.camZ[this.o1] + myMatrix.m22 * this.camZ[this.o2]);
/*      */       
/* 1021 */       float px = m02;
/* 1022 */       float py = m12;
/* 1023 */       float pz = m22;
/*      */       
/*      */ 
/* 1026 */       float resultT0x = m00 * this.TEX_WIDTH + m02;
/* 1027 */       float resultT0y = m10 * this.TEX_WIDTH + m12;
/* 1028 */       float resultT0z = m20 * this.TEX_WIDTH + m22;
/* 1029 */       float result0Tx = m01 * this.TEX_HEIGHT + m02;
/* 1030 */       float result0Ty = m11 * this.TEX_HEIGHT + m12;
/* 1031 */       float result0Tz = m21 * this.TEX_HEIGHT + m22;
/* 1032 */       float mx = resultT0x - m02;
/* 1033 */       float my = resultT0y - m12;
/* 1034 */       float mz = resultT0z - m22;
/* 1035 */       float nx = result0Tx - m02;
/* 1036 */       float ny = result0Ty - m12;
/* 1037 */       float nz = result0Tz - m22;
/*      */       
/*      */ 
/* 1040 */       this.ax = ((py * nz - pz * ny) * this.TEX_WIDTH);
/* 1041 */       this.ay = ((pz * nx - px * nz) * this.TEX_WIDTH);
/* 1042 */       this.az = ((px * ny - py * nx) * this.TEX_WIDTH);
/*      */       
/* 1044 */       this.bx = ((my * pz - mz * py) * this.TEX_HEIGHT);
/* 1045 */       this.by = ((mz * px - mx * pz) * this.TEX_HEIGHT);
/* 1046 */       this.bz = ((mx * py - my * px) * this.TEX_HEIGHT);
/*      */       
/* 1048 */       this.cx = (ny * mz - nz * my);
/* 1049 */       this.cy = (nz * mx - nx * mz);
/* 1050 */       this.cz = (nx * my - ny * mx);
/*      */     }
/*      */     
/* 1053 */     this.nearPlaneWidth = (this.parent.rightScreen - this.parent.leftScreen);
/* 1054 */     this.nearPlaneHeight = (this.parent.topScreen - this.parent.bottomScreen);
/* 1055 */     this.nearPlaneDepth = this.parent.nearPlane;
/*      */     
/* 1057 */     this.xmult = (this.nearPlaneWidth / this.SCREEN_WIDTH);
/* 1058 */     this.ymult = (this.nearPlaneHeight / this.SCREEN_HEIGHT);
/*      */     
/* 1060 */     this.newax = (this.ax * this.xmult);
/* 1061 */     this.newbx = (this.bx * this.xmult);
/* 1062 */     this.newcx = (this.cx * this.xmult);
/* 1063 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setInterpPower(int pwr)
/*      */   {
/* 1073 */     TEX_INTERP_POWER = pwr;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_plain(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1084 */     ytop *= this.SCREEN_WIDTH;
/* 1085 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/*      */ 
/* 1089 */     while (ytop < ybottom) {
/* 1090 */       int xstart = (int)(this.xleft + 0.5F);
/* 1091 */       if (xstart < 0) {
/* 1092 */         xstart = 0;
/*      */       }
/* 1094 */       int xend = (int)(this.xrght + 0.5F);
/* 1095 */       if (xend > this.SCREEN_WIDTH) {
/* 1096 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1098 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1099 */       float iz = this.izadd * xdiff + this.zleft;
/* 1100 */       xstart += ytop;
/* 1101 */       xend += ytop;
/* 1103 */       for (; 
/* 1103 */           xstart < xend; xstart++) {
/* 1104 */         if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart])) {
/* 1105 */           this.m_zbuffer[xstart] = iz;
/* 1106 */           this.m_pixels[xstart] = this.m_fill;
/*      */         }
/*      */         
/* 1109 */         iz += this.izadd;
/*      */       }
/*      */       
/* 1112 */       ytop += this.SCREEN_WIDTH;
/* 1113 */       this.xleft += leftadd;
/* 1114 */       this.xrght += rghtadd;
/* 1115 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_plain_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1127 */     ytop *= this.SCREEN_WIDTH;
/* 1128 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/* 1130 */     int pr = this.m_fill & 0xFF0000;
/* 1131 */     int pg = this.m_fill & 0xFF00;
/* 1132 */     int pb = this.m_fill & 0xFF;
/*      */     
/*      */ 
/* 1135 */     float iaf = this.iaadd;
/*      */     
/* 1137 */     while (ytop < ybottom) {
/* 1138 */       int xstart = (int)(this.xleft + 0.5F);
/* 1139 */       if (xstart < 0) {
/* 1140 */         xstart = 0;
/*      */       }
/* 1142 */       int xend = (int)(this.xrght + 0.5F);
/* 1143 */       if (xend > this.SCREEN_WIDTH) {
/* 1144 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1146 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1147 */       float iz = this.izadd * xdiff + this.zleft;
/* 1148 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 1149 */       xstart += ytop;
/* 1150 */       xend += ytop;
/* 1154 */       for (; 
/*      */           
/*      */ 
/* 1154 */           xstart < xend; xstart++) {
/* 1155 */         if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */         {
/*      */ 
/*      */ 
/* 1159 */           int alpha = ia >> 16;
/* 1160 */           int mr0 = this.m_pixels[xstart];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1167 */           int mg0 = mr0 & 0xFF00;
/* 1168 */           int mb0 = mr0 & 0xFF;
/* 1169 */           mr0 &= 0xFF0000;
/*      */           
/* 1171 */           mr0 += ((pr - mr0) * alpha >> 8);
/* 1172 */           mg0 += ((pg - mg0) * alpha >> 8);
/* 1173 */           mb0 += ((pb - mb0) * alpha >> 8);
/*      */           
/* 1175 */           this.m_pixels[xstart] = 
/* 1176 */             (0xFF000000 | mr0 & 0xFF0000 | mg0 & 0xFF00 | mb0 & 0xFF);
/*      */         }
/*      */         
/*      */ 
/* 1180 */         iz += this.izadd;
/* 1181 */         ia += this.iaadd;
/*      */       }
/* 1183 */       ytop += this.SCREEN_WIDTH;
/* 1184 */       this.xleft += leftadd;
/* 1185 */       this.xrght += rghtadd;
/* 1186 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1198 */     float irf = this.iradd;
/* 1199 */     float igf = this.igadd;
/* 1200 */     float ibf = this.ibadd;
/*      */     
/* 1202 */     ytop *= this.SCREEN_WIDTH;
/* 1203 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1206 */     while (ytop < ybottom) {
/* 1207 */       int xstart = (int)(this.xleft + 0.5F);
/* 1208 */       if (xstart < 0) {
/* 1209 */         xstart = 0;
/*      */       }
/* 1211 */       int xend = (int)(this.xrght + 0.5F);
/* 1212 */       if (xend > this.SCREEN_WIDTH) {
/* 1213 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1215 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1216 */       int ir = (int)(irf * xdiff + this.rleft);
/* 1217 */       int ig = (int)(igf * xdiff + this.gleft);
/* 1218 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 1219 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1221 */       xstart += ytop;
/* 1222 */       xend += ytop;
/* 1224 */       for (; 
/* 1224 */           xstart < xend; xstart++) {
/* 1225 */         if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart])) {
/* 1226 */           this.m_zbuffer[xstart] = iz;
/* 1227 */           this.m_pixels[xstart] = 
/* 1228 */             (0xFF000000 | ir & 0xFF0000 | ig >> 8 & 0xFF00 | ib >> 16);
/*      */         }
/*      */         
/*      */ 
/* 1232 */         ir += this.iradd;
/* 1233 */         ig += this.igadd;
/* 1234 */         ib += this.ibadd;
/* 1235 */         iz += this.izadd;
/*      */       }
/*      */       
/* 1238 */       ytop += this.SCREEN_WIDTH;
/* 1239 */       this.xleft += leftadd;
/* 1240 */       this.xrght += rghtadd;
/* 1241 */       this.rleft += this.rleftadd;
/* 1242 */       this.gleft += this.gleftadd;
/* 1243 */       this.bleft += this.bleftadd;
/* 1244 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1256 */     ytop *= this.SCREEN_WIDTH;
/* 1257 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1260 */     float irf = this.iradd;
/* 1261 */     float igf = this.igadd;
/* 1262 */     float ibf = this.ibadd;
/* 1263 */     float iaf = this.iaadd;
/*      */     
/* 1265 */     while (ytop < ybottom) {
/* 1266 */       int xstart = (int)(this.xleft + 0.5F);
/* 1267 */       if (xstart < 0)
/* 1268 */         xstart = 0;
/* 1269 */       int xend = (int)(this.xrght + 0.5F);
/* 1270 */       if (xend > this.SCREEN_WIDTH)
/* 1271 */         xend = this.SCREEN_WIDTH;
/* 1272 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 1274 */       int ir = (int)(irf * xdiff + this.rleft);
/* 1275 */       int ig = (int)(igf * xdiff + this.gleft);
/* 1276 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 1277 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 1278 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1280 */       xstart += ytop;
/* 1281 */       xend += ytop;
/* 1283 */       for (; 
/* 1283 */           xstart < xend; xstart++) {
/* 1284 */         if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */         {
/*      */ 
/*      */ 
/* 1288 */           int red = ir & 0xFF0000;
/* 1289 */           int grn = ig >> 8 & 0xFF00;
/* 1290 */           int blu = ib >> 16;
/*      */           
/*      */ 
/* 1293 */           int bb = this.m_pixels[xstart];
/* 1294 */           int br = bb & 0xFF0000;
/* 1295 */           int bg = bb & 0xFF00;
/* 1296 */           bb &= 0xFF;
/*      */           
/*      */ 
/* 1299 */           int al = ia >> 16;
/*      */           
/*      */ 
/* 1302 */           this.m_pixels[xstart] = 
/*      */           
/*      */ 
/* 1305 */             (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1310 */         ir += this.iradd;
/* 1311 */         ig += this.igadd;
/* 1312 */         ib += this.ibadd;
/* 1313 */         ia += this.iaadd;
/* 1314 */         iz += this.izadd;
/*      */       }
/*      */       
/* 1317 */       ytop += this.SCREEN_WIDTH;
/* 1318 */       this.xleft += leftadd;
/* 1319 */       this.xrght += rghtadd;
/* 1320 */       this.rleft += this.rleftadd;
/* 1321 */       this.gleft += this.gleftadd;
/* 1322 */       this.bleft += this.bleftadd;
/* 1323 */       this.aleft += this.aleftadd;
/* 1324 */       this.zleft += this.zleftadd;
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
/*      */   private void drawsegment_texture8(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1340 */     int ypixel = ytop;
/* 1341 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 1342 */     boolean accurateMode = this.parent.hints[7];
/* 1343 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 1344 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 1345 */     int linearInterpPower = TEX_INTERP_POWER;
/* 1346 */     int linearInterpLength = 1 << linearInterpPower;
/* 1347 */     if (accurateMode)
/*      */     {
/* 1349 */       if (precomputeAccurateTexturing()) {
/* 1350 */         this.newax *= linearInterpLength;
/* 1351 */         this.newbx *= linearInterpLength;
/* 1352 */         this.newcx *= linearInterpLength;
/* 1353 */         screenz = this.nearPlaneDepth;
/* 1354 */         this.firstSegment = false;
/*      */       }
/*      */       else
/*      */       {
/* 1358 */         accurateMode = false;
/*      */       }
/*      */     }
/* 1361 */     ytop *= this.SCREEN_WIDTH;
/* 1362 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1365 */     float iuf = this.iuadd;
/* 1366 */     float ivf = this.ivadd;
/*      */     
/* 1368 */     int red = this.m_fill & 0xFF0000;
/* 1369 */     int grn = this.m_fill & 0xFF00;
/* 1370 */     int blu = this.m_fill & 0xFF;
/*      */     
/* 1372 */     while (ytop < ybottom) {
/* 1373 */       int xstart = (int)(this.xleft + 0.5F);
/* 1374 */       if (xstart < 0) {
/* 1375 */         xstart = 0;
/*      */       }
/* 1377 */       int xpixel = xstart;
/*      */       
/* 1379 */       int xend = (int)(this.xrght + 0.5F);
/* 1380 */       if (xend > this.SCREEN_WIDTH) {
/* 1381 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1383 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1384 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 1385 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 1386 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1388 */       xstart += ytop;
/* 1389 */       xend += ytop;
/*      */       
/* 1391 */       if (accurateMode) {
/* 1392 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 1393 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 1394 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 1395 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 1396 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 1398 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 1399 */       int interpCounter = 0;
/* 1400 */       int deltaU = 0;int deltaV = 0;
/* 1401 */       float fu = 0.0F;float fv = 0.0F;
/* 1402 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 1404 */       if ((accurateMode) && (goingIn)) {
/* 1405 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 1406 */         int leftOffset = linearInterpLength - rightOffset;
/* 1407 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 1408 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 1409 */         interpCounter = leftOffset;
/* 1410 */         float ao = a - leftOffset2 * this.newax;
/* 1411 */         float bo = b - leftOffset2 * this.newbx;
/* 1412 */         float co = c - leftOffset2 * this.newcx;
/* 1413 */         float oneoverc = 65536.0F / co;
/* 1414 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 1415 */         a += rightOffset2 * this.newax;
/* 1416 */         b += rightOffset2 * this.newbx;
/* 1417 */         c += rightOffset2 * this.newcx;
/* 1418 */         oneoverc = 65536.0F / c;
/* 1419 */         fu = a * oneoverc;fv = b * oneoverc;
/* 1420 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1421 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 1422 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;
/* 1423 */         iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 1425 */         float preoneoverc = 65536.0F / c;
/* 1426 */         fu = a * preoneoverc;
/* 1427 */         fv = b * preoneoverc;
/*      */       }
/* 1430 */       for (; 
/* 1430 */           xstart < xend; xstart++) {
/* 1431 */         if (accurateMode) {
/* 1432 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 1433 */           if (interpCounter == 0) {
/* 1434 */             a += this.newax;
/* 1435 */             b += this.newbx;
/* 1436 */             c += this.newcx;
/* 1437 */             float oneoverc = 65536.0F / c;
/* 1438 */             oldfu = fu;oldfv = fv;
/* 1439 */             fu = a * oneoverc;fv = b * oneoverc;
/* 1440 */             iu = (int)oldfu;iv = (int)oldfv;
/* 1441 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1442 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 1444 */             iu += deltaU;
/* 1445 */             iv += deltaV;
/*      */           }
/* 1447 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 1451 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */             int al0;
/*      */             
/* 1455 */             if (this.m_bilinear) {
/* 1456 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 1457 */               int iui = iu & 0xFFFF;
/* 1458 */               int al0 = this.m_texture[ofs] & 0xFF;
/* 1459 */               int al1 = this.m_texture[(ofs + 1)] & 0xFF;
/* 1460 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 1461 */               int al2 = this.m_texture[ofs] & 0xFF;
/* 1462 */               int al3 = this.m_texture[(ofs + 1)] & 0xFF;
/* 1463 */               al0 += ((al1 - al0) * iui >> 16);
/* 1464 */               al2 += ((al3 - al2) * iui >> 16);
/* 1465 */               al0 += ((al2 - al0) * (iv & 0xFFFF) >> 16);
/*      */             } else {
/* 1467 */               al0 = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))] & 0xFF;
/*      */             }
/*      */             
/* 1470 */             int br = this.m_pixels[xstart];
/* 1471 */             int bg = br & 0xFF00;
/* 1472 */             int bb = br & 0xFF;
/* 1473 */             br &= 0xFF0000;
/* 1474 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 1477 */               (0xFF000000 | br + ((red - br) * al0 >> 8) & 0xFF0000 | bg + ((grn - bg) * al0 >> 8) & 0xFF00 | bb + ((blu - bb) * al0 >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/* 1483 */         xpixel++;
/* 1484 */         if (!accurateMode) {
/* 1485 */           iu += this.iuadd;
/* 1486 */           iv += this.ivadd;
/*      */         }
/* 1488 */         iz += this.izadd;
/*      */       }
/* 1490 */       ypixel++;
/* 1491 */       ytop += this.SCREEN_WIDTH;
/* 1492 */       this.xleft += leftadd;
/* 1493 */       this.xrght += rghtadd;
/* 1494 */       this.uleft += this.uleftadd;
/* 1495 */       this.vleft += this.vleftadd;
/* 1496 */       this.zleft += this.zleftadd;
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
/*      */   private void drawsegment_texture8_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1512 */     int ypixel = ytop;
/* 1513 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 1514 */     boolean accurateMode = this.parent.hints[7];
/* 1515 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 1516 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 1517 */     int linearInterpPower = TEX_INTERP_POWER;
/* 1518 */     int linearInterpLength = 1 << linearInterpPower;
/* 1519 */     if (accurateMode)
/*      */     {
/* 1521 */       if (precomputeAccurateTexturing()) {
/* 1522 */         this.newax *= linearInterpLength;
/* 1523 */         this.newbx *= linearInterpLength;
/* 1524 */         this.newcx *= linearInterpLength;
/* 1525 */         screenz = this.nearPlaneDepth;
/* 1526 */         this.firstSegment = false;
/*      */       }
/*      */       else
/*      */       {
/* 1530 */         accurateMode = false;
/*      */       }
/*      */     }
/* 1533 */     ytop *= this.SCREEN_WIDTH;
/* 1534 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1537 */     float iuf = this.iuadd;
/* 1538 */     float ivf = this.ivadd;
/* 1539 */     float iaf = this.iaadd;
/*      */     
/* 1541 */     int red = this.m_fill & 0xFF0000;
/* 1542 */     int grn = this.m_fill & 0xFF00;
/* 1543 */     int blu = this.m_fill & 0xFF;
/*      */     
/* 1545 */     while (ytop < ybottom) {
/* 1546 */       int xstart = (int)(this.xleft + 0.5F);
/* 1547 */       if (xstart < 0) {
/* 1548 */         xstart = 0;
/*      */       }
/* 1550 */       int xpixel = xstart;
/*      */       
/* 1552 */       int xend = (int)(this.xrght + 0.5F);
/* 1553 */       if (xend > this.SCREEN_WIDTH) {
/* 1554 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1556 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1557 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 1558 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 1559 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 1560 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1562 */       xstart += ytop;
/* 1563 */       xend += ytop;
/*      */       
/* 1565 */       if (accurateMode) {
/* 1566 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 1567 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 1568 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 1569 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 1570 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 1572 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 1573 */       int interpCounter = 0;
/* 1574 */       int deltaU = 0;int deltaV = 0;
/* 1575 */       float fu = 0.0F;float fv = 0.0F;
/* 1576 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 1578 */       if ((accurateMode) && (goingIn)) {
/* 1579 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 1580 */         int leftOffset = linearInterpLength - rightOffset;
/* 1581 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 1582 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 1583 */         interpCounter = leftOffset;
/* 1584 */         float ao = a - leftOffset2 * this.newax;
/* 1585 */         float bo = b - leftOffset2 * this.newbx;
/* 1586 */         float co = c - leftOffset2 * this.newcx;
/* 1587 */         float oneoverc = 65536.0F / co;
/* 1588 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 1589 */         a += rightOffset2 * this.newax;
/* 1590 */         b += rightOffset2 * this.newbx;
/* 1591 */         c += rightOffset2 * this.newcx;
/* 1592 */         oneoverc = 65536.0F / c;
/* 1593 */         fu = a * oneoverc;fv = b * oneoverc;
/* 1594 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1595 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 1596 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 1598 */         float preoneoverc = 65536.0F / c;
/* 1599 */         fu = a * preoneoverc;
/* 1600 */         fv = b * preoneoverc;
/*      */       }
/* 1604 */       for (; 
/*      */           
/* 1604 */           xstart < xend; xstart++) {
/* 1605 */         if (accurateMode) {
/* 1606 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 1607 */           if (interpCounter == 0) {
/* 1608 */             a += this.newax;
/* 1609 */             b += this.newbx;
/* 1610 */             c += this.newcx;
/* 1611 */             float oneoverc = 65536.0F / c;
/* 1612 */             oldfu = fu;oldfv = fv;
/* 1613 */             fu = a * oneoverc;fv = b * oneoverc;
/* 1614 */             iu = (int)oldfu;iv = (int)oldfv;
/* 1615 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1616 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 1618 */             iu += deltaU;
/* 1619 */             iv += deltaV;
/*      */           }
/* 1621 */           interpCounter++;
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 1626 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 1630 */             if (this.m_bilinear) {
/* 1631 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 1632 */               int iui = iu & 0xFFFF;
/* 1633 */               int al0 = this.m_texture[ofs] & 0xFF;
/* 1634 */               int al1 = this.m_texture[(ofs + 1)] & 0xFF;
/* 1635 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 1636 */               int al2 = this.m_texture[ofs] & 0xFF;
/* 1637 */               int al3 = this.m_texture[(ofs + 1)] & 0xFF;
/* 1638 */               al0 += ((al1 - al0) * iui >> 16);
/* 1639 */               al2 += ((al3 - al2) * iui >> 16);
/* 1640 */               al0 += ((al2 - al0) * (iv & 0xFFFF) >> 16);
/*      */             } else {
/* 1642 */               al0 = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))] & 0xFF;
/*      */             }
/* 1644 */             int al0 = al0 * (ia >> 16) >> 8;
/*      */             
/* 1646 */             int br = this.m_pixels[xstart];
/* 1647 */             int bg = br & 0xFF00;
/* 1648 */             int bb = br & 0xFF;
/* 1649 */             br &= 0xFF0000;
/* 1650 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 1653 */               (0xFF000000 | br + ((red - br) * al0 >> 8) & 0xFF0000 | bg + ((grn - bg) * al0 >> 8) & 0xFF00 | bb + ((blu - bb) * al0 >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/* 1659 */         xpixel++;
/* 1660 */         if (!accurateMode) {
/* 1661 */           iu += this.iuadd;
/* 1662 */           iv += this.ivadd;
/*      */         }
/* 1664 */         iz += this.izadd;
/* 1665 */         ia += this.iaadd;
/*      */       }
/* 1667 */       ypixel++;
/* 1668 */       ytop += this.SCREEN_WIDTH;
/* 1669 */       this.xleft += leftadd;
/* 1670 */       this.xrght += rghtadd;
/* 1671 */       this.uleft += this.uleftadd;
/* 1672 */       this.vleft += this.vleftadd;
/* 1673 */       this.zleft += this.zleftadd;
/* 1674 */       this.aleft += this.aleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_texture24(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1685 */     ytop *= this.SCREEN_WIDTH;
/* 1686 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/* 1688 */     float iuf = this.iuadd;
/* 1689 */     float ivf = this.ivadd;
/*      */     
/* 1691 */     boolean tint = (this.m_fill & 0xFFFFFF) != 16777215;
/* 1692 */     int rtint = this.m_fill >> 16 & 0xFF;
/* 1693 */     int gtint = this.m_fill >> 8 & 0xFF;
/* 1694 */     int btint = this.m_fill & 0xFF;
/*      */     
/* 1696 */     int ypixel = ytop / this.SCREEN_WIDTH;
/* 1697 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/*      */     
/* 1699 */     boolean accurateMode = this.parent.hints[7];
/* 1700 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 1701 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1708 */     int linearInterpPower = TEX_INTERP_POWER;
/* 1709 */     int linearInterpLength = 1 << linearInterpPower;
/* 1710 */     if (accurateMode) {
/* 1711 */       if (precomputeAccurateTexturing()) {
/* 1712 */         this.newax *= linearInterpLength;
/* 1713 */         this.newbx *= linearInterpLength;
/* 1714 */         this.newcx *= linearInterpLength;
/* 1715 */         screenz = this.nearPlaneDepth;
/* 1716 */         this.firstSegment = false;
/*      */       } else {
/* 1718 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1723 */     while (ytop < ybottom) {
/* 1724 */       int xstart = (int)(this.xleft + 0.5F);
/* 1725 */       if (xstart < 0) { xstart = 0;
/*      */       }
/* 1727 */       int xpixel = xstart;
/*      */       
/* 1729 */       int xend = (int)(this.xrght + 0.5F);
/* 1730 */       if (xend > this.SCREEN_WIDTH) xend = this.SCREEN_WIDTH;
/* 1731 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1732 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 1733 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 1734 */       float iz = this.izadd * xdiff + this.zleft;
/* 1735 */       xstart += ytop;
/* 1736 */       xend += ytop;
/*      */       
/* 1738 */       if (accurateMode)
/*      */       {
/* 1740 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 1741 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 1742 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 1743 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 1744 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/*      */       
/*      */ 
/* 1748 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/*      */       
/*      */ 
/* 1751 */       int interpCounter = 0;
/* 1752 */       int deltaU = 0;int deltaV = 0;
/*      */       
/*      */ 
/* 1755 */       float fu = 0.0F;float fv = 0.0F;
/* 1756 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1765 */       if ((accurateMode) && (goingIn))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1771 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 1772 */         int leftOffset = linearInterpLength - rightOffset;
/* 1773 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 1774 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 1775 */         interpCounter = leftOffset;
/*      */         
/*      */ 
/* 1778 */         float ao = a - leftOffset2 * this.newax;
/* 1779 */         float bo = b - leftOffset2 * this.newbx;
/* 1780 */         float co = c - leftOffset2 * this.newcx;
/* 1781 */         float oneoverc = 65536.0F / co;
/* 1782 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/*      */         
/*      */ 
/* 1785 */         a += rightOffset2 * this.newax;
/* 1786 */         b += rightOffset2 * this.newbx;
/* 1787 */         c += rightOffset2 * this.newcx;
/* 1788 */         oneoverc = 65536.0F / c;
/* 1789 */         fu = a * oneoverc;fv = b * oneoverc;
/*      */         
/*      */ 
/* 1792 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1793 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 1794 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       }
/*      */       else {
/* 1797 */         float preoneoverc = 65536.0F / c;
/* 1798 */         fu = a * preoneoverc;
/* 1799 */         fv = b * preoneoverc;
/*      */       }
/* 1802 */       for (; 
/* 1802 */           xstart < xend; xstart++)
/*      */       {
/* 1804 */         if (accurateMode)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1816 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 1817 */           if (interpCounter == 0)
/*      */           {
/* 1819 */             a += this.newax;
/* 1820 */             b += this.newbx;
/* 1821 */             c += this.newcx;
/* 1822 */             float oneoverc = 65536.0F / c;
/* 1823 */             oldfu = fu;oldfv = fv;
/* 1824 */             fu = a * oneoverc;fv = b * oneoverc;
/* 1825 */             iu = (int)oldfu;iv = (int)oldfv;
/* 1826 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1827 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 1829 */             iu += deltaU;
/* 1830 */             iv += deltaV;
/*      */           }
/* 1832 */           interpCounter++;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 1862 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart])) {
/* 1863 */             this.m_zbuffer[xstart] = iz;
/* 1864 */             if (this.m_bilinear)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1870 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 1871 */               int iui = (iu & 0xFFFF) >> 9;
/* 1872 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1878 */               int pix0 = this.m_texture[ofs];
/* 1879 */               int pix1 = this.m_texture[(ofs + 1)];
/* 1880 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 1881 */               int pix2 = this.m_texture[ofs];
/* 1882 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 1885 */               int red0 = pix0 & 0xFF0000;
/* 1886 */               int red2 = pix2 & 0xFF0000;
/* 1887 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 1888 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 1889 */               int red = up + ((dn - up) * ivi >> 7);
/* 1890 */               if (tint) { red = red * rtint >> 8 & 0xFF0000;
/*      */               }
/*      */               
/* 1893 */               red0 = pix0 & 0xFF00;
/* 1894 */               red2 = pix2 & 0xFF00;
/* 1895 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 1896 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 1897 */               int grn = up + ((dn - up) * ivi >> 7);
/* 1898 */               if (tint) { grn = grn * gtint >> 8 & 0xFF00;
/*      */               }
/*      */               
/* 1901 */               red0 = pix0 & 0xFF;
/* 1902 */               red2 = pix2 & 0xFF;
/* 1903 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 1904 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 1905 */               int blu = up + ((dn - up) * ivi >> 7);
/* 1906 */               if (tint) { blu = blu * btint >> 8 & 0xFF;
/*      */               }
/*      */               
/* 1909 */               this.m_pixels[xstart] = 
/* 1910 */                 (0xFF000000 | red & 0xFF0000 | grn & 0xFF00 | blu & 0xFF);
/*      */             }
/*      */             else {
/* 1913 */               this.m_pixels[xstart] = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/* 1918 */         iz += this.izadd;
/* 1919 */         xpixel++;
/* 1920 */         if (!accurateMode) {
/* 1921 */           iu += this.iuadd;
/* 1922 */           iv += this.ivadd;
/*      */         }
/*      */       }
/* 1925 */       ypixel++;
/* 1926 */       ytop += this.SCREEN_WIDTH;
/* 1927 */       this.xleft += leftadd;
/* 1928 */       this.xrght += rghtadd;
/* 1929 */       this.zleft += this.zleftadd;
/* 1930 */       this.uleft += this.uleftadd;
/* 1931 */       this.vleft += this.vleftadd;
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
/*      */   private void drawsegment_texture24_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1945 */     int ypixel = ytop;
/* 1946 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 1947 */     boolean accurateMode = this.parent.hints[7];
/* 1948 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 1949 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 1950 */     int linearInterpPower = TEX_INTERP_POWER;
/* 1951 */     int linearInterpLength = 1 << linearInterpPower;
/* 1952 */     if (accurateMode) {
/* 1953 */       if (precomputeAccurateTexturing()) {
/* 1954 */         this.newax *= linearInterpLength;
/* 1955 */         this.newbx *= linearInterpLength;
/* 1956 */         this.newcx *= linearInterpLength;
/* 1957 */         screenz = this.nearPlaneDepth;
/* 1958 */         this.firstSegment = false;
/*      */       } else {
/* 1960 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 1964 */     boolean tint = (this.m_fill & 0xFFFFFF) != 16777215;
/* 1965 */     int rtint = this.m_fill >> 16 & 0xFF;
/* 1966 */     int gtint = this.m_fill >> 8 & 0xFF;
/* 1967 */     int btint = this.m_fill & 0xFF;
/*      */     
/* 1969 */     ytop *= this.SCREEN_WIDTH;
/* 1970 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1973 */     float iuf = this.iuadd;
/* 1974 */     float ivf = this.ivadd;
/* 1975 */     float iaf = this.iaadd;
/*      */     
/* 1977 */     while (ytop < ybottom) {
/* 1978 */       int xstart = (int)(this.xleft + 0.5F);
/* 1979 */       if (xstart < 0) {
/* 1980 */         xstart = 0;
/*      */       }
/* 1982 */       int xpixel = xstart;
/*      */       
/* 1984 */       int xend = (int)(this.xrght + 0.5F);
/* 1985 */       if (xend > this.SCREEN_WIDTH) {
/* 1986 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1988 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1989 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 1990 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 1991 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 1992 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1994 */       xstart += ytop;
/* 1995 */       xend += ytop;
/*      */       
/* 1997 */       if (accurateMode) {
/* 1998 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 1999 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2000 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2001 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2002 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2004 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2005 */       int interpCounter = 0;
/* 2006 */       int deltaU = 0;int deltaV = 0;
/* 2007 */       float fu = 0.0F;float fv = 0.0F;
/* 2008 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2010 */       if ((accurateMode) && (goingIn)) {
/* 2011 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2012 */         int leftOffset = linearInterpLength - rightOffset;
/* 2013 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2014 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2015 */         interpCounter = leftOffset;
/* 2016 */         float ao = a - leftOffset2 * this.newax;
/* 2017 */         float bo = b - leftOffset2 * this.newbx;
/* 2018 */         float co = c - leftOffset2 * this.newcx;
/* 2019 */         float oneoverc = 65536.0F / co;
/* 2020 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2021 */         a += rightOffset2 * this.newax;
/* 2022 */         b += rightOffset2 * this.newbx;
/* 2023 */         c += rightOffset2 * this.newcx;
/* 2024 */         oneoverc = 65536.0F / c;
/* 2025 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2026 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2027 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2028 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2030 */         float preoneoverc = 65536.0F / c;
/* 2031 */         fu = a * preoneoverc;
/* 2032 */         fv = b * preoneoverc;
/*      */       }
/* 2036 */       for (; 
/*      */           
/* 2036 */           xstart < xend; xstart++) {
/* 2037 */         if (accurateMode) {
/* 2038 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2039 */           if (interpCounter == 0) {
/* 2040 */             a += this.newax;
/* 2041 */             b += this.newbx;
/* 2042 */             c += this.newcx;
/* 2043 */             float oneoverc = 65536.0F / c;
/* 2044 */             oldfu = fu;oldfv = fv;
/* 2045 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2046 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2047 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2048 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2050 */             iu += deltaU;
/* 2051 */             iv += deltaV;
/*      */           }
/* 2053 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 2057 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 2061 */             int al = ia >> 16;
/*      */             
/* 2063 */             if (this.m_bilinear) {
/* 2064 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2065 */               int iui = (iu & 0xFFFF) >> 9;
/* 2066 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 2069 */               int pix0 = this.m_texture[ofs];
/* 2070 */               int pix1 = this.m_texture[(ofs + 1)];
/* 2071 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2072 */               int pix2 = this.m_texture[ofs];
/* 2073 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 2076 */               int red0 = pix0 & 0xFF0000;
/* 2077 */               int red2 = pix2 & 0xFF0000;
/* 2078 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 2079 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 2080 */               int red = up + ((dn - up) * ivi >> 7);
/* 2081 */               if (tint) { red = red * rtint >> 8 & 0xFF0000;
/*      */               }
/*      */               
/* 2084 */               red0 = pix0 & 0xFF00;
/* 2085 */               red2 = pix2 & 0xFF00;
/* 2086 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 2087 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 2088 */               int grn = up + ((dn - up) * ivi >> 7);
/* 2089 */               if (tint) { grn = grn * gtint >> 8 & 0xFF00;
/*      */               }
/*      */               
/* 2092 */               red0 = pix0 & 0xFF;
/* 2093 */               red2 = pix2 & 0xFF;
/* 2094 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 2095 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 2096 */               int blu = up + ((dn - up) * ivi >> 7);
/* 2097 */               if (tint) { blu = blu * btint >> 8 & 0xFF;
/*      */               }
/*      */               
/* 2100 */               int bb = this.m_pixels[xstart];
/* 2101 */               int br = bb & 0xFF0000;
/* 2102 */               int bg = bb & 0xFF00;
/* 2103 */               bb &= 0xFF;
/* 2104 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2107 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             }
/*      */             else {
/* 2110 */               int red = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 2111 */               int grn = red & 0xFF00;
/* 2112 */               int blu = red & 0xFF;
/* 2113 */               red &= 0xFF0000;
/*      */               
/*      */ 
/* 2116 */               int bb = this.m_pixels[xstart];
/* 2117 */               int br = bb & 0xFF0000;
/* 2118 */               int bg = bb & 0xFF00;
/* 2119 */               bb &= 0xFF;
/* 2120 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2123 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/* 2129 */         xpixel++;
/* 2130 */         if (!accurateMode) {
/* 2131 */           iu += this.iuadd;
/* 2132 */           iv += this.ivadd;
/*      */         }
/* 2134 */         ia += this.iaadd;
/* 2135 */         iz += this.izadd;
/*      */       }
/* 2137 */       ypixel++;
/*      */       
/* 2139 */       ytop += this.SCREEN_WIDTH;
/*      */       
/* 2141 */       this.xleft += leftadd;
/* 2142 */       this.xrght += rghtadd;
/* 2143 */       this.uleft += this.uleftadd;
/* 2144 */       this.vleft += this.vleftadd;
/* 2145 */       this.zleft += this.zleftadd;
/* 2146 */       this.aleft += this.aleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_texture32(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2158 */     int ypixel = ytop;
/* 2159 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2160 */     boolean accurateMode = this.parent.hints[7];
/* 2161 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2162 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2163 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2164 */     int linearInterpLength = 1 << linearInterpPower;
/* 2165 */     if (accurateMode) {
/* 2166 */       if (precomputeAccurateTexturing()) {
/* 2167 */         this.newax *= linearInterpLength;
/* 2168 */         this.newbx *= linearInterpLength;
/* 2169 */         this.newcx *= linearInterpLength;
/* 2170 */         screenz = this.nearPlaneDepth;
/* 2171 */         this.firstSegment = false;
/*      */       } else {
/* 2173 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2177 */     ytop *= this.SCREEN_WIDTH;
/* 2178 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 2181 */     boolean tint = this.m_fill != -1;
/* 2182 */     int rtint = this.m_fill >> 16 & 0xFF;
/* 2183 */     int gtint = this.m_fill >> 8 & 0xFF;
/* 2184 */     int btint = this.m_fill & 0xFF;
/*      */     
/* 2186 */     float iuf = this.iuadd;
/* 2187 */     float ivf = this.ivadd;
/*      */     
/* 2189 */     while (ytop < ybottom) {
/* 2190 */       int xstart = (int)(this.xleft + 0.5F);
/* 2191 */       if (xstart < 0) {
/* 2192 */         xstart = 0;
/*      */       }
/* 2194 */       int xpixel = xstart;
/*      */       
/* 2196 */       int xend = (int)(this.xrght + 0.5F);
/* 2197 */       if (xend > this.SCREEN_WIDTH) {
/* 2198 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 2200 */       float xdiff = xstart + 0.5F - this.xleft;
/* 2201 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 2202 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 2203 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 2205 */       xstart += ytop;
/* 2206 */       xend += ytop;
/*      */       
/* 2208 */       if (accurateMode) {
/* 2209 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2210 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2211 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2212 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2213 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2215 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2216 */       int interpCounter = 0;
/* 2217 */       int deltaU = 0;int deltaV = 0;
/* 2218 */       float fu = 0.0F;float fv = 0.0F;
/* 2219 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2221 */       if ((accurateMode) && (goingIn)) {
/* 2222 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2223 */         int leftOffset = linearInterpLength - rightOffset;
/* 2224 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2225 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2226 */         interpCounter = leftOffset;
/* 2227 */         float ao = a - leftOffset2 * this.newax;
/* 2228 */         float bo = b - leftOffset2 * this.newbx;
/* 2229 */         float co = c - leftOffset2 * this.newcx;
/* 2230 */         float oneoverc = 65536.0F / co;
/* 2231 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2232 */         a += rightOffset2 * this.newax;
/* 2233 */         b += rightOffset2 * this.newbx;
/* 2234 */         c += rightOffset2 * this.newcx;
/* 2235 */         oneoverc = 65536.0F / c;
/* 2236 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2237 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2238 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2239 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2241 */         float preoneoverc = 65536.0F / c;
/* 2242 */         fu = a * preoneoverc;
/* 2243 */         fv = b * preoneoverc;
/*      */       }
/* 2247 */       for (; 
/*      */           
/* 2247 */           xstart < xend; xstart++) {
/* 2248 */         if (accurateMode) {
/* 2249 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2250 */           if (interpCounter == 0) {
/* 2251 */             a += this.newax;
/* 2252 */             b += this.newbx;
/* 2253 */             c += this.newcx;
/* 2254 */             float oneoverc = 65536.0F / c;
/* 2255 */             oldfu = fu;oldfv = fv;
/* 2256 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2257 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2258 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2259 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2261 */             iu += deltaU;
/* 2262 */             iv += deltaV;
/*      */           }
/* 2264 */           interpCounter++;
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 2269 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/* 2272 */             if (this.m_bilinear) {
/* 2273 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2274 */               int iui = (iu & 0xFFFF) >> 9;
/* 2275 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 2278 */               int pix0 = this.m_texture[ofs];
/* 2279 */               int pix1 = this.m_texture[(ofs + 1)];
/* 2280 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2281 */               int pix2 = this.m_texture[ofs];
/* 2282 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 2285 */               int red0 = pix0 & 0xFF0000;
/* 2286 */               int red2 = pix2 & 0xFF0000;
/* 2287 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 2288 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 2289 */               int red = up + ((dn - up) * ivi >> 7);
/* 2290 */               if (tint) { red = red * rtint >> 8 & 0xFF0000;
/*      */               }
/*      */               
/* 2293 */               red0 = pix0 & 0xFF00;
/* 2294 */               red2 = pix2 & 0xFF00;
/* 2295 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 2296 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 2297 */               int grn = up + ((dn - up) * ivi >> 7);
/* 2298 */               if (tint) { grn = grn * gtint >> 8 & 0xFF00;
/*      */               }
/*      */               
/* 2301 */               red0 = pix0 & 0xFF;
/* 2302 */               red2 = pix2 & 0xFF;
/* 2303 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 2304 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 2305 */               int blu = up + ((dn - up) * ivi >> 7);
/* 2306 */               if (tint) { blu = blu * btint >> 8 & 0xFF;
/*      */               }
/*      */               
/* 2309 */               pix0 >>>= 24;
/* 2310 */               pix2 >>>= 24;
/* 2311 */               up = pix0 + (((pix1 >>> 24) - pix0) * iui >> 7);
/* 2312 */               dn = pix2 + (((pix3 >>> 24) - pix2) * iui >> 7);
/* 2313 */               int al = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 2316 */               int bb = this.m_pixels[xstart];
/* 2317 */               int br = bb & 0xFF0000;
/* 2318 */               int bg = bb & 0xFF00;
/* 2319 */               bb &= 0xFF;
/* 2320 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2323 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             } else {
/* 2325 */               int red = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 2326 */               int al = red >>> 24;
/* 2327 */               int grn = red & 0xFF00;
/* 2328 */               int blu = red & 0xFF;
/* 2329 */               red &= 0xFF0000;
/*      */               
/*      */ 
/* 2332 */               int bb = this.m_pixels[xstart];
/* 2333 */               int br = bb & 0xFF0000;
/* 2334 */               int bg = bb & 0xFF00;
/* 2335 */               bb &= 0xFF;
/* 2336 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2339 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/* 2344 */         xpixel++;
/* 2345 */         if (!accurateMode) {
/* 2346 */           iu += this.iuadd;
/* 2347 */           iv += this.ivadd;
/*      */         }
/* 2349 */         iz += this.izadd;
/*      */       }
/* 2351 */       ypixel++;
/*      */       
/* 2353 */       ytop += this.SCREEN_WIDTH;
/*      */       
/* 2355 */       this.xleft += leftadd;
/* 2356 */       this.xrght += rghtadd;
/* 2357 */       this.uleft += this.uleftadd;
/* 2358 */       this.vleft += this.vleftadd;
/* 2359 */       this.zleft += this.zleftadd;
/* 2360 */       this.aleft += this.aleftadd;
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
/*      */   private void drawsegment_texture32_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2374 */     int ypixel = ytop;
/* 2375 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2376 */     boolean accurateMode = this.parent.hints[7];
/* 2377 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2378 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2379 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2380 */     int linearInterpLength = 1 << linearInterpPower;
/* 2381 */     if (accurateMode) {
/* 2382 */       if (precomputeAccurateTexturing()) {
/* 2383 */         this.newax *= linearInterpLength;
/* 2384 */         this.newbx *= linearInterpLength;
/* 2385 */         this.newcx *= linearInterpLength;
/* 2386 */         screenz = this.nearPlaneDepth;
/* 2387 */         this.firstSegment = false;
/*      */       } else {
/* 2389 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2393 */     ytop *= this.SCREEN_WIDTH;
/* 2394 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 2397 */     boolean tint = (this.m_fill & 0xFFFFFF) != 16777215;
/* 2398 */     int rtint = this.m_fill >> 16 & 0xFF;
/* 2399 */     int gtint = this.m_fill >> 8 & 0xFF;
/* 2400 */     int btint = this.m_fill & 0xFF;
/*      */     
/* 2402 */     float iuf = this.iuadd;
/* 2403 */     float ivf = this.ivadd;
/* 2404 */     float iaf = this.iaadd;
/*      */     
/* 2406 */     while (ytop < ybottom) {
/* 2407 */       int xstart = (int)(this.xleft + 0.5F);
/* 2408 */       if (xstart < 0) {
/* 2409 */         xstart = 0;
/*      */       }
/* 2411 */       int xpixel = xstart;
/*      */       
/* 2413 */       int xend = (int)(this.xrght + 0.5F);
/* 2414 */       if (xend > this.SCREEN_WIDTH) {
/* 2415 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 2417 */       float xdiff = xstart + 0.5F - this.xleft;
/* 2418 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 2419 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 2420 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 2421 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 2423 */       xstart += ytop;
/* 2424 */       xend += ytop;
/*      */       
/* 2426 */       if (accurateMode) {
/* 2427 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2428 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2429 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2430 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2431 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2433 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2434 */       int interpCounter = 0;
/* 2435 */       int deltaU = 0;int deltaV = 0;
/* 2436 */       float fu = 0.0F;float fv = 0.0F;
/* 2437 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2439 */       if ((accurateMode) && (goingIn)) {
/* 2440 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2441 */         int leftOffset = linearInterpLength - rightOffset;
/* 2442 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2443 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2444 */         interpCounter = leftOffset;
/* 2445 */         float ao = a - leftOffset2 * this.newax;
/* 2446 */         float bo = b - leftOffset2 * this.newbx;
/* 2447 */         float co = c - leftOffset2 * this.newcx;
/* 2448 */         float oneoverc = 65536.0F / co;
/* 2449 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2450 */         a += rightOffset2 * this.newax;
/* 2451 */         b += rightOffset2 * this.newbx;
/* 2452 */         c += rightOffset2 * this.newcx;
/* 2453 */         oneoverc = 65536.0F / c;
/* 2454 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2455 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2456 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2457 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2459 */         float preoneoverc = 65536.0F / c;
/* 2460 */         fu = a * preoneoverc;
/* 2461 */         fv = b * preoneoverc;
/*      */       }
/* 2464 */       for (; 
/* 2464 */           xstart < xend; xstart++) {
/* 2465 */         if (accurateMode) {
/* 2466 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2467 */           if (interpCounter == 0) {
/* 2468 */             a += this.newax;
/* 2469 */             b += this.newbx;
/* 2470 */             c += this.newcx;
/* 2471 */             float oneoverc = 65536.0F / c;
/* 2472 */             oldfu = fu;oldfv = fv;
/* 2473 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2474 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2475 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2476 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2478 */             iu += deltaU;
/* 2479 */             iv += deltaV;
/*      */           }
/* 2481 */           interpCounter++;
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 2486 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 2490 */             int al = ia >> 16;
/*      */             
/* 2492 */             if (this.m_bilinear) {
/* 2493 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2494 */               int iui = (iu & 0xFFFF) >> 9;
/* 2495 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 2498 */               int pix0 = this.m_texture[ofs];
/* 2499 */               int pix1 = this.m_texture[(ofs + 1)];
/* 2500 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2501 */               int pix2 = this.m_texture[ofs];
/* 2502 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 2505 */               int red0 = pix0 & 0xFF0000;
/* 2506 */               int red2 = pix2 & 0xFF0000;
/* 2507 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 2508 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 2509 */               int red = up + ((dn - up) * ivi >> 7);
/* 2510 */               if (tint) { red = red * rtint >> 8 & 0xFF0000;
/*      */               }
/*      */               
/* 2513 */               red0 = pix0 & 0xFF00;
/* 2514 */               red2 = pix2 & 0xFF00;
/* 2515 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 2516 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 2517 */               int grn = up + ((dn - up) * ivi >> 7);
/* 2518 */               if (tint) { grn = grn * gtint >> 8 & 0xFF00;
/*      */               }
/*      */               
/* 2521 */               red0 = pix0 & 0xFF;
/* 2522 */               red2 = pix2 & 0xFF;
/* 2523 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 2524 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 2525 */               int blu = up + ((dn - up) * ivi >> 7);
/* 2526 */               if (tint) { blu = blu * btint >> 8 & 0xFF;
/*      */               }
/*      */               
/* 2529 */               pix0 >>>= 24;
/* 2530 */               pix2 >>>= 24;
/* 2531 */               up = pix0 + (((pix1 >>> 24) - pix0) * iui >> 7);
/* 2532 */               dn = pix2 + (((pix3 >>> 24) - pix2) * iui >> 7);
/* 2533 */               al = al * (up + ((dn - up) * ivi >> 7)) >> 8;
/*      */               
/*      */ 
/* 2536 */               int bb = this.m_pixels[xstart];
/* 2537 */               int br = bb & 0xFF0000;
/* 2538 */               int bg = bb & 0xFF00;
/* 2539 */               bb &= 0xFF;
/* 2540 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2543 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             } else {
/* 2545 */               int red = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 2546 */               al = al * (red >>> 24) >> 8;
/* 2547 */               int grn = red & 0xFF00;
/* 2548 */               int blu = red & 0xFF;
/* 2549 */               red &= 0xFF0000;
/*      */               
/*      */ 
/* 2552 */               int bb = this.m_pixels[xstart];
/* 2553 */               int br = bb & 0xFF0000;
/* 2554 */               int bg = bb & 0xFF00;
/* 2555 */               bb &= 0xFF;
/* 2556 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2559 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/* 2565 */         xpixel++;
/* 2566 */         if (!accurateMode) {
/* 2567 */           iu += this.iuadd;
/* 2568 */           iv += this.ivadd;
/*      */         }
/* 2570 */         ia += this.iaadd;
/* 2571 */         iz += this.izadd;
/*      */       }
/* 2573 */       ypixel++;
/*      */       
/* 2575 */       ytop += this.SCREEN_WIDTH;
/*      */       
/* 2577 */       this.xleft += leftadd;
/* 2578 */       this.xrght += rghtadd;
/* 2579 */       this.uleft += this.uleftadd;
/* 2580 */       this.vleft += this.vleftadd;
/* 2581 */       this.zleft += this.zleftadd;
/* 2582 */       this.aleft += this.aleftadd;
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
/*      */   private void drawsegment_gouraud_texture8(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2597 */     int ypixel = ytop;
/* 2598 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2599 */     boolean accurateMode = this.parent.hints[7];
/* 2600 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2601 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2602 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2603 */     int linearInterpLength = 1 << linearInterpPower;
/* 2604 */     if (accurateMode) {
/* 2605 */       if (precomputeAccurateTexturing()) {
/* 2606 */         this.newax *= linearInterpLength;
/* 2607 */         this.newbx *= linearInterpLength;
/* 2608 */         this.newcx *= linearInterpLength;
/* 2609 */         screenz = this.nearPlaneDepth;
/* 2610 */         this.firstSegment = false;
/*      */       } else {
/* 2612 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2616 */     ytop *= this.SCREEN_WIDTH;
/* 2617 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 2620 */     float iuf = this.iuadd;
/* 2621 */     float ivf = this.ivadd;
/* 2622 */     float irf = this.iradd;
/* 2623 */     float igf = this.igadd;
/* 2624 */     float ibf = this.ibadd;
/*      */     
/* 2626 */     while (ytop < ybottom) {
/* 2627 */       int xstart = (int)(this.xleft + 0.5F);
/* 2628 */       if (xstart < 0) {
/* 2629 */         xstart = 0;
/*      */       }
/* 2631 */       int xpixel = xstart;
/*      */       
/* 2633 */       int xend = (int)(this.xrght + 0.5F);
/* 2634 */       if (xend > this.SCREEN_WIDTH)
/* 2635 */         xend = this.SCREEN_WIDTH;
/* 2636 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 2638 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 2639 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 2640 */       int ir = (int)(irf * xdiff + this.rleft);
/* 2641 */       int ig = (int)(igf * xdiff + this.gleft);
/* 2642 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 2643 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 2645 */       xstart += ytop;
/* 2646 */       xend += ytop;
/*      */       
/* 2648 */       if (accurateMode) {
/* 2649 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2650 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2651 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2652 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2653 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2655 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2656 */       int interpCounter = 0;
/* 2657 */       int deltaU = 0;int deltaV = 0;
/* 2658 */       float fu = 0.0F;float fv = 0.0F;
/* 2659 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2661 */       if ((accurateMode) && (goingIn)) {
/* 2662 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2663 */         int leftOffset = linearInterpLength - rightOffset;
/* 2664 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2665 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2666 */         interpCounter = leftOffset;
/* 2667 */         float ao = a - leftOffset2 * this.newax;
/* 2668 */         float bo = b - leftOffset2 * this.newbx;
/* 2669 */         float co = c - leftOffset2 * this.newcx;
/* 2670 */         float oneoverc = 65536.0F / co;
/* 2671 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2672 */         a += rightOffset2 * this.newax;
/* 2673 */         b += rightOffset2 * this.newbx;
/* 2674 */         c += rightOffset2 * this.newcx;
/* 2675 */         oneoverc = 65536.0F / c;
/* 2676 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2677 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2678 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2679 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2681 */         float preoneoverc = 65536.0F / c;
/* 2682 */         fu = a * preoneoverc;
/* 2683 */         fv = b * preoneoverc;
/*      */       }
/* 2687 */       for (; 
/*      */           
/* 2687 */           xstart < xend; xstart++) {
/* 2688 */         if (accurateMode) {
/* 2689 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2690 */           if (interpCounter == 0) {
/* 2691 */             a += this.newax;
/* 2692 */             b += this.newbx;
/* 2693 */             c += this.newcx;
/* 2694 */             float oneoverc = 65536.0F / c;
/* 2695 */             oldfu = fu;oldfv = fv;
/* 2696 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2697 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2698 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2699 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2701 */             iu += deltaU;
/* 2702 */             iv += deltaV;
/*      */           }
/* 2704 */           interpCounter++;
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 2709 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */             int al0;
/*      */             
/* 2713 */             if (this.m_bilinear) {
/* 2714 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2715 */               int iui = iu & 0xFFFF;
/* 2716 */               int al0 = this.m_texture[ofs] & 0xFF;
/* 2717 */               int al1 = this.m_texture[(ofs + 1)] & 0xFF;
/* 2718 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2719 */               int al2 = this.m_texture[ofs] & 0xFF;
/* 2720 */               int al3 = this.m_texture[(ofs + 1)] & 0xFF;
/* 2721 */               al0 += ((al1 - al0) * iui >> 16);
/* 2722 */               al2 += ((al3 - al2) * iui >> 16);
/* 2723 */               al0 += ((al2 - al0) * (iv & 0xFFFF) >> 16);
/*      */             } else {
/* 2725 */               al0 = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))] & 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 2729 */             int red = ir & 0xFF0000;
/* 2730 */             int grn = ig >> 8 & 0xFF00;
/* 2731 */             int blu = ib >> 16;
/*      */             
/*      */ 
/* 2734 */             int bb = this.m_pixels[xstart];
/* 2735 */             int br = bb & 0xFF0000;
/* 2736 */             int bg = bb & 0xFF00;
/* 2737 */             bb &= 0xFF;
/* 2738 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 2741 */               (0xFF000000 | br + ((red - br) * al0 >> 8) & 0xFF0000 | bg + ((grn - bg) * al0 >> 8) & 0xFF00 | bb + ((blu - bb) * al0 >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2752 */         xpixel++;
/* 2753 */         if (!accurateMode) {
/* 2754 */           iu += this.iuadd;
/* 2755 */           iv += this.ivadd;
/*      */         }
/* 2757 */         ir += this.iradd;
/* 2758 */         ig += this.igadd;
/* 2759 */         ib += this.ibadd;
/* 2760 */         iz += this.izadd;
/*      */       }
/* 2762 */       ypixel++;
/* 2763 */       ytop += this.SCREEN_WIDTH;
/* 2764 */       this.xleft += leftadd;
/* 2765 */       this.xrght += rghtadd;
/*      */       
/* 2767 */       this.uleft += this.uleftadd;
/* 2768 */       this.vleft += this.vleftadd;
/* 2769 */       this.rleft += this.rleftadd;
/* 2770 */       this.gleft += this.gleftadd;
/* 2771 */       this.bleft += this.bleftadd;
/* 2772 */       this.zleft += this.zleftadd;
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
/*      */   private void drawsegment_gouraud_texture8_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2786 */     int ypixel = ytop;
/* 2787 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2788 */     boolean accurateMode = this.parent.hints[7];
/* 2789 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2790 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2791 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2792 */     int linearInterpLength = 1 << linearInterpPower;
/* 2793 */     if (accurateMode)
/*      */     {
/* 2795 */       if (precomputeAccurateTexturing()) {
/* 2796 */         this.newax *= linearInterpLength;
/* 2797 */         this.newbx *= linearInterpLength;
/* 2798 */         this.newcx *= linearInterpLength;
/* 2799 */         screenz = this.nearPlaneDepth;
/* 2800 */         this.firstSegment = false;
/*      */       }
/*      */       else
/*      */       {
/* 2804 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2808 */     ytop *= this.SCREEN_WIDTH;
/* 2809 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 2812 */     float iuf = this.iuadd;
/* 2813 */     float ivf = this.ivadd;
/* 2814 */     float irf = this.iradd;
/* 2815 */     float igf = this.igadd;
/* 2816 */     float ibf = this.ibadd;
/* 2817 */     float iaf = this.iaadd;
/*      */     
/* 2819 */     while (ytop < ybottom) {
/* 2820 */       int xstart = (int)(this.xleft + 0.5F);
/* 2821 */       if (xstart < 0) {
/* 2822 */         xstart = 0;
/*      */       }
/* 2824 */       int xpixel = xstart;
/*      */       
/* 2826 */       int xend = (int)(this.xrght + 0.5F);
/* 2827 */       if (xend > this.SCREEN_WIDTH)
/* 2828 */         xend = this.SCREEN_WIDTH;
/* 2829 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 2831 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 2832 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 2833 */       int ir = (int)(irf * xdiff + this.rleft);
/* 2834 */       int ig = (int)(igf * xdiff + this.gleft);
/* 2835 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 2836 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 2837 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 2839 */       xstart += ytop;
/* 2840 */       xend += ytop;
/*      */       
/* 2842 */       if (accurateMode) {
/* 2843 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2844 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2845 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2846 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2847 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2849 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2850 */       int interpCounter = 0;
/* 2851 */       int deltaU = 0;int deltaV = 0;
/* 2852 */       float fu = 0.0F;float fv = 0.0F;
/* 2853 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2855 */       if ((accurateMode) && (goingIn)) {
/* 2856 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2857 */         int leftOffset = linearInterpLength - rightOffset;
/* 2858 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2859 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2860 */         interpCounter = leftOffset;
/* 2861 */         float ao = a - leftOffset2 * this.newax;
/* 2862 */         float bo = b - leftOffset2 * this.newbx;
/* 2863 */         float co = c - leftOffset2 * this.newcx;
/* 2864 */         float oneoverc = 65536.0F / co;
/* 2865 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2866 */         a += rightOffset2 * this.newax;
/* 2867 */         b += rightOffset2 * this.newbx;
/* 2868 */         c += rightOffset2 * this.newcx;
/* 2869 */         oneoverc = 65536.0F / c;
/* 2870 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2871 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2872 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2873 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2875 */         float preoneoverc = 65536.0F / c;
/* 2876 */         fu = a * preoneoverc;
/* 2877 */         fv = b * preoneoverc;
/*      */       }
/* 2881 */       for (; 
/*      */           
/* 2881 */           xstart < xend; xstart++) {
/* 2882 */         if (accurateMode) {
/* 2883 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2884 */           if (interpCounter == 0) {
/* 2885 */             a += this.newax;
/* 2886 */             b += this.newbx;
/* 2887 */             c += this.newcx;
/* 2888 */             float oneoverc = 65536.0F / c;
/* 2889 */             oldfu = fu;oldfv = fv;
/* 2890 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2891 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2892 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2893 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2895 */             iu += deltaU;
/* 2896 */             iv += deltaV;
/*      */           }
/* 2898 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 2902 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 2906 */             if (this.m_bilinear) {
/* 2907 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2908 */               int iui = iu & 0xFFFF;
/* 2909 */               int al0 = this.m_texture[ofs] & 0xFF;
/* 2910 */               int al1 = this.m_texture[(ofs + 1)] & 0xFF;
/* 2911 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2912 */               int al2 = this.m_texture[ofs] & 0xFF;
/* 2913 */               int al3 = this.m_texture[(ofs + 1)] & 0xFF;
/* 2914 */               al0 += ((al1 - al0) * iui >> 16);
/* 2915 */               al2 += ((al3 - al2) * iui >> 16);
/* 2916 */               al0 += ((al2 - al0) * (iv & 0xFFFF) >> 16);
/*      */             } else {
/* 2918 */               al0 = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))] & 0xFF;
/*      */             }
/* 2920 */             int al0 = al0 * (ia >> 16) >> 8;
/*      */             
/*      */ 
/* 2923 */             int red = ir & 0xFF0000;
/* 2924 */             int grn = ig >> 8 & 0xFF00;
/* 2925 */             int blu = ib >> 16;
/*      */             
/*      */ 
/* 2928 */             int bb = this.m_pixels[xstart];
/* 2929 */             int br = bb & 0xFF0000;
/* 2930 */             int bg = bb & 0xFF00;
/* 2931 */             bb &= 0xFF;
/*      */             
/* 2933 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 2936 */               (0xFF000000 | br + ((red - br) * al0 >> 8) & 0xFF0000 | bg + ((grn - bg) * al0 >> 8) & 0xFF00 | bb + ((blu - bb) * al0 >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2944 */         xpixel++;
/* 2945 */         if (!accurateMode) {
/* 2946 */           iu += this.iuadd;
/* 2947 */           iv += this.ivadd;
/*      */         }
/* 2949 */         ir += this.iradd;
/* 2950 */         ig += this.igadd;
/* 2951 */         ib += this.ibadd;
/* 2952 */         ia += this.iaadd;
/* 2953 */         iz += this.izadd;
/*      */       }
/* 2955 */       ypixel++;
/* 2956 */       ytop += this.SCREEN_WIDTH;
/* 2957 */       this.xleft += leftadd;
/* 2958 */       this.xrght += rghtadd;
/* 2959 */       this.uleft += this.uleftadd;
/* 2960 */       this.vleft += this.vleftadd;
/* 2961 */       this.rleft += this.rleftadd;
/* 2962 */       this.gleft += this.gleftadd;
/* 2963 */       this.bleft += this.bleftadd;
/* 2964 */       this.aleft += this.aleftadd;
/* 2965 */       this.zleft += this.zleftadd;
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
/*      */   private void drawsegment_gouraud_texture24(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2978 */     int ypixel = ytop;
/* 2979 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2980 */     boolean accurateMode = this.parent.hints[7];
/* 2981 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2982 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2983 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2984 */     int linearInterpLength = 1 << linearInterpPower;
/* 2985 */     if (accurateMode) {
/* 2986 */       if (precomputeAccurateTexturing()) {
/* 2987 */         this.newax *= linearInterpLength;
/* 2988 */         this.newbx *= linearInterpLength;
/* 2989 */         this.newcx *= linearInterpLength;
/* 2990 */         screenz = this.nearPlaneDepth;
/* 2991 */         this.firstSegment = false;
/*      */       } else {
/* 2993 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2997 */     ytop *= this.SCREEN_WIDTH;
/* 2998 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 3001 */     float iuf = this.iuadd;
/* 3002 */     float ivf = this.ivadd;
/* 3003 */     float irf = this.iradd;
/* 3004 */     float igf = this.igadd;
/* 3005 */     float ibf = this.ibadd;
/*      */     
/* 3007 */     while (ytop < ybottom) {
/* 3008 */       int xstart = (int)(this.xleft + 0.5F);
/* 3009 */       if (xstart < 0) {
/* 3010 */         xstart = 0;
/*      */       }
/* 3012 */       int xpixel = xstart;
/*      */       
/* 3014 */       int xend = (int)(this.xrght + 0.5F);
/* 3015 */       if (xend > this.SCREEN_WIDTH)
/* 3016 */         xend = this.SCREEN_WIDTH;
/* 3017 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 3019 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 3020 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 3021 */       int ir = (int)(irf * xdiff + this.rleft);
/* 3022 */       int ig = (int)(igf * xdiff + this.gleft);
/* 3023 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 3024 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 3026 */       xstart += ytop;
/* 3027 */       xend += ytop;
/*      */       
/* 3029 */       if (accurateMode) {
/* 3030 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 3031 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 3032 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 3033 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 3034 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 3036 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 3037 */       int interpCounter = 0;
/* 3038 */       int deltaU = 0;int deltaV = 0;
/* 3039 */       float fu = 0.0F;float fv = 0.0F;
/* 3040 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 3042 */       if ((accurateMode) && (goingIn)) {
/* 3043 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 3044 */         int leftOffset = linearInterpLength - rightOffset;
/* 3045 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 3046 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 3047 */         interpCounter = leftOffset;
/* 3048 */         float ao = a - leftOffset2 * this.newax;
/* 3049 */         float bo = b - leftOffset2 * this.newbx;
/* 3050 */         float co = c - leftOffset2 * this.newcx;
/* 3051 */         float oneoverc = 65536.0F / co;
/* 3052 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 3053 */         a += rightOffset2 * this.newax;
/* 3054 */         b += rightOffset2 * this.newbx;
/* 3055 */         c += rightOffset2 * this.newcx;
/* 3056 */         oneoverc = 65536.0F / c;
/* 3057 */         fu = a * oneoverc;fv = b * oneoverc;
/* 3058 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3059 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 3060 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 3062 */         float preoneoverc = 65536.0F / c;
/* 3063 */         fu = a * preoneoverc;
/* 3064 */         fv = b * preoneoverc;
/*      */       }
/* 3067 */       for (; 
/* 3067 */           xstart < xend; xstart++) {
/* 3068 */         if (accurateMode) {
/* 3069 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 3070 */           if (interpCounter == 0) {
/* 3071 */             a += this.newax;
/* 3072 */             b += this.newbx;
/* 3073 */             c += this.newcx;
/* 3074 */             float oneoverc = 65536.0F / c;
/* 3075 */             oldfu = fu;oldfv = fv;
/* 3076 */             fu = a * oneoverc;fv = b * oneoverc;
/* 3077 */             iu = (int)oldfu;iv = (int)oldfv;
/* 3078 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3079 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 3081 */             iu += deltaU;
/* 3082 */             iv += deltaV;
/*      */           }
/* 3084 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 3088 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart])) {
/* 3089 */             this.m_zbuffer[xstart] = iz;
/*      */             
/*      */             int blu;
/*      */             int blu;
/*      */             int red;
/*      */             int grn;
/* 3095 */             if (this.m_bilinear) {
/* 3096 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 3097 */               int iui = (iu & 0xFFFF) >> 9;
/* 3098 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 3101 */               int pix0 = this.m_texture[ofs];
/* 3102 */               int pix1 = this.m_texture[(ofs + 1)];
/* 3103 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 3104 */               int pix2 = this.m_texture[ofs];
/* 3105 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 3108 */               int red0 = pix0 & 0xFF0000;
/* 3109 */               int red2 = pix2 & 0xFF0000;
/* 3110 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 3111 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 3112 */               int red = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 3115 */               red0 = pix0 & 0xFF00;
/* 3116 */               red2 = pix2 & 0xFF00;
/* 3117 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 3118 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 3119 */               int grn = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 3122 */               red0 = pix0 & 0xFF;
/* 3123 */               red2 = pix2 & 0xFF;
/* 3124 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 3125 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 3126 */               blu = up + ((dn - up) * ivi >> 7);
/*      */             }
/*      */             else {
/* 3129 */               blu = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 3130 */               red = blu & 0xFF0000;
/* 3131 */               grn = blu & 0xFF00;
/* 3132 */               blu &= 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 3136 */             int r = ir >> 16;
/* 3137 */             int g = ig >> 16;
/*      */             
/*      */ 
/* 3140 */             int bb2 = ib >> 16;
/*      */             
/* 3142 */             this.m_pixels[xstart] = 
/* 3143 */               (0xFF000000 | (red * r & 0xFF000000 | grn * g & 0xFF0000 | blu * bb2) >> 8);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/* 3149 */         xpixel++;
/* 3150 */         if (!accurateMode) {
/* 3151 */           iu += this.iuadd;
/* 3152 */           iv += this.ivadd;
/*      */         }
/* 3154 */         ir += this.iradd;
/* 3155 */         ig += this.igadd;
/* 3156 */         ib += this.ibadd;
/* 3157 */         iz += this.izadd;
/*      */       }
/* 3159 */       ypixel++;
/*      */       
/* 3161 */       ytop += this.SCREEN_WIDTH;
/* 3162 */       this.xleft += leftadd;
/* 3163 */       this.xrght += rghtadd;
/* 3164 */       this.uleft += this.uleftadd;
/* 3165 */       this.vleft += this.vleftadd;
/* 3166 */       this.rleft += this.rleftadd;
/* 3167 */       this.gleft += this.gleftadd;
/* 3168 */       this.bleft += this.bleftadd;
/* 3169 */       this.zleft += this.zleftadd;
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
/*      */   private void drawsegment_gouraud_texture24_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 3186 */     int ypixel = ytop;
/* 3187 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 3188 */     boolean accurateMode = this.parent.hints[7];
/* 3189 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 3190 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 3191 */     int linearInterpPower = TEX_INTERP_POWER;
/* 3192 */     int linearInterpLength = 1 << linearInterpPower;
/* 3193 */     if (accurateMode) {
/* 3194 */       if (precomputeAccurateTexturing()) {
/* 3195 */         this.newax *= linearInterpLength;
/* 3196 */         this.newbx *= linearInterpLength;
/* 3197 */         this.newcx *= linearInterpLength;
/* 3198 */         screenz = this.nearPlaneDepth;
/* 3199 */         this.firstSegment = false;
/*      */       } else {
/* 3201 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 3205 */     ytop *= this.SCREEN_WIDTH;
/* 3206 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 3209 */     float iuf = this.iuadd;
/* 3210 */     float ivf = this.ivadd;
/* 3211 */     float irf = this.iradd;
/* 3212 */     float igf = this.igadd;
/* 3213 */     float ibf = this.ibadd;
/* 3214 */     float iaf = this.iaadd;
/*      */     
/* 3216 */     while (ytop < ybottom) {
/* 3217 */       int xstart = (int)(this.xleft + 0.5F);
/* 3218 */       if (xstart < 0) {
/* 3219 */         xstart = 0;
/*      */       }
/* 3221 */       int xpixel = xstart;
/*      */       
/* 3223 */       int xend = (int)(this.xrght + 0.5F);
/* 3224 */       if (xend > this.SCREEN_WIDTH)
/* 3225 */         xend = this.SCREEN_WIDTH;
/* 3226 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 3228 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 3229 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 3230 */       int ir = (int)(irf * xdiff + this.rleft);
/* 3231 */       int ig = (int)(igf * xdiff + this.gleft);
/* 3232 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 3233 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 3234 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 3236 */       xstart += ytop;
/* 3237 */       xend += ytop;
/*      */       
/* 3239 */       if (accurateMode) {
/* 3240 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 3241 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 3242 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 3243 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 3244 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 3246 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 3247 */       int interpCounter = 0;
/* 3248 */       int deltaU = 0;int deltaV = 0;
/* 3249 */       float fu = 0.0F;float fv = 0.0F;
/* 3250 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 3252 */       if ((accurateMode) && (goingIn)) {
/* 3253 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 3254 */         int leftOffset = linearInterpLength - rightOffset;
/* 3255 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 3256 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 3257 */         interpCounter = leftOffset;
/* 3258 */         float ao = a - leftOffset2 * this.newax;
/* 3259 */         float bo = b - leftOffset2 * this.newbx;
/* 3260 */         float co = c - leftOffset2 * this.newcx;
/* 3261 */         float oneoverc = 65536.0F / co;
/* 3262 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 3263 */         a += rightOffset2 * this.newax;
/* 3264 */         b += rightOffset2 * this.newbx;
/* 3265 */         c += rightOffset2 * this.newcx;
/* 3266 */         oneoverc = 65536.0F / c;
/* 3267 */         fu = a * oneoverc;fv = b * oneoverc;
/* 3268 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3269 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 3270 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 3272 */         float preoneoverc = 65536.0F / c;
/* 3273 */         fu = a * preoneoverc;
/* 3274 */         fv = b * preoneoverc;
/*      */       }
/* 3277 */       for (; 
/* 3277 */           xstart < xend; xstart++) {
/* 3278 */         if (accurateMode) {
/* 3279 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 3280 */           if (interpCounter == 0) {
/* 3281 */             a += this.newax;
/* 3282 */             b += this.newbx;
/* 3283 */             c += this.newcx;
/* 3284 */             float oneoverc = 65536.0F / c;
/* 3285 */             oldfu = fu;oldfv = fv;
/* 3286 */             fu = a * oneoverc;fv = b * oneoverc;
/* 3287 */             iu = (int)oldfu;iv = (int)oldfv;
/* 3288 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3289 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 3291 */             iu += deltaU;
/* 3292 */             iv += deltaV;
/*      */           }
/* 3294 */           interpCounter++;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 3301 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 3305 */             int al = ia >> 16;
/*      */             
/*      */ 
/*      */             int blu;
/*      */             
/*      */ 
/* 3311 */             if (this.m_bilinear) {
/* 3312 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 3313 */               int iui = (iu & 0xFFFF) >> 9;
/* 3314 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 3317 */               int pix0 = this.m_texture[ofs];
/* 3318 */               int pix1 = this.m_texture[(ofs + 1)];
/* 3319 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 3320 */               int pix2 = this.m_texture[ofs];
/* 3321 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 3324 */               int red0 = pix0 & 0xFF0000;
/* 3325 */               int red2 = pix2 & 0xFF0000;
/* 3326 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 3327 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 3328 */               int red = up + ((dn - up) * ivi >> 7) >> 16;
/*      */               
/*      */ 
/* 3331 */               red0 = pix0 & 0xFF00;
/* 3332 */               red2 = pix2 & 0xFF00;
/* 3333 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 3334 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 3335 */               int grn = up + ((dn - up) * ivi >> 7) >> 8;
/*      */               
/*      */ 
/* 3338 */               red0 = pix0 & 0xFF;
/* 3339 */               red2 = pix2 & 0xFF;
/* 3340 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 3341 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 3342 */               blu = up + ((dn - up) * ivi >> 7);
/*      */             } else {
/* 3344 */               blu = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 3345 */               red = (blu & 0xFF0000) >> 16;
/* 3346 */               grn = (blu & 0xFF00) >> 8;
/* 3347 */               blu &= 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 3351 */             int red = red * ir >>> 8;
/* 3352 */             int grn = grn * ig >>> 16;
/* 3353 */             int blu = blu * ib >>> 24;
/*      */             
/*      */ 
/* 3356 */             int bb = this.m_pixels[xstart];
/* 3357 */             int br = bb & 0xFF0000;
/* 3358 */             int bg = bb & 0xFF00;
/* 3359 */             bb &= 0xFF;
/*      */             
/*      */ 
/* 3362 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 3365 */               (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3373 */         xpixel++;
/* 3374 */         if (!accurateMode) {
/* 3375 */           iu += this.iuadd;
/* 3376 */           iv += this.ivadd;
/*      */         }
/* 3378 */         ir += this.iradd;
/* 3379 */         ig += this.igadd;
/* 3380 */         ib += this.ibadd;
/* 3381 */         ia += this.iaadd;
/* 3382 */         iz += this.izadd;
/*      */       }
/*      */       
/* 3385 */       ypixel++;
/*      */       
/* 3387 */       ytop += this.SCREEN_WIDTH;
/* 3388 */       this.xleft += leftadd;
/* 3389 */       this.xrght += rghtadd;
/* 3390 */       this.uleft += this.uleftadd;
/* 3391 */       this.vleft += this.vleftadd;
/* 3392 */       this.rleft += this.rleftadd;
/* 3393 */       this.gleft += this.gleftadd;
/* 3394 */       this.bleft += this.bleftadd;
/* 3395 */       this.aleft += this.aleftadd;
/* 3396 */       this.zleft += this.zleftadd;
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
/*      */   private void drawsegment_gouraud_texture32(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 3413 */     int ypixel = ytop;
/* 3414 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 3415 */     boolean accurateMode = this.parent.hints[7];
/* 3416 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 3417 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 3418 */     int linearInterpPower = TEX_INTERP_POWER;
/* 3419 */     int linearInterpLength = 1 << linearInterpPower;
/* 3420 */     if (accurateMode) {
/* 3421 */       if (precomputeAccurateTexturing()) {
/* 3422 */         this.newax *= linearInterpLength;
/* 3423 */         this.newbx *= linearInterpLength;
/* 3424 */         this.newcx *= linearInterpLength;
/* 3425 */         screenz = this.nearPlaneDepth;
/* 3426 */         this.firstSegment = false;
/*      */       } else {
/* 3428 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 3432 */     ytop *= this.SCREEN_WIDTH;
/* 3433 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 3436 */     float iuf = this.iuadd;
/* 3437 */     float ivf = this.ivadd;
/* 3438 */     float irf = this.iradd;
/* 3439 */     float igf = this.igadd;
/* 3440 */     float ibf = this.ibadd;
/*      */     
/* 3442 */     while (ytop < ybottom) {
/* 3443 */       int xstart = (int)(this.xleft + 0.5F);
/* 3444 */       if (xstart < 0) {
/* 3445 */         xstart = 0;
/*      */       }
/* 3447 */       int xpixel = xstart;
/*      */       
/* 3449 */       int xend = (int)(this.xrght + 0.5F);
/* 3450 */       if (xend > this.SCREEN_WIDTH)
/* 3451 */         xend = this.SCREEN_WIDTH;
/* 3452 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 3454 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 3455 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 3456 */       int ir = (int)(irf * xdiff + this.rleft);
/* 3457 */       int ig = (int)(igf * xdiff + this.gleft);
/* 3458 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 3459 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 3461 */       xstart += ytop;
/* 3462 */       xend += ytop;
/* 3463 */       if (accurateMode) {
/* 3464 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 3465 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 3466 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 3467 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 3468 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 3470 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 3471 */       int interpCounter = 0;
/* 3472 */       int deltaU = 0;int deltaV = 0;
/* 3473 */       float fu = 0.0F;float fv = 0.0F;
/* 3474 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 3476 */       if ((accurateMode) && (goingIn)) {
/* 3477 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 3478 */         int leftOffset = linearInterpLength - rightOffset;
/* 3479 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 3480 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 3481 */         interpCounter = leftOffset;
/* 3482 */         float ao = a - leftOffset2 * this.newax;
/* 3483 */         float bo = b - leftOffset2 * this.newbx;
/* 3484 */         float co = c - leftOffset2 * this.newcx;
/* 3485 */         float oneoverc = 65536.0F / co;
/* 3486 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 3487 */         a += rightOffset2 * this.newax;
/* 3488 */         b += rightOffset2 * this.newbx;
/* 3489 */         c += rightOffset2 * this.newcx;
/* 3490 */         oneoverc = 65536.0F / c;
/* 3491 */         fu = a * oneoverc;fv = b * oneoverc;
/* 3492 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3493 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 3494 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 3496 */         float preoneoverc = 65536.0F / c;
/* 3497 */         fu = a * preoneoverc;
/* 3498 */         fv = b * preoneoverc;
/*      */       }
/* 3502 */       for (; 
/*      */           
/* 3502 */           xstart < xend; xstart++) {
/* 3503 */         if (accurateMode) {
/* 3504 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 3505 */           if (interpCounter == 0) {
/* 3506 */             a += this.newax;
/* 3507 */             b += this.newbx;
/* 3508 */             c += this.newcx;
/* 3509 */             float oneoverc = 65536.0F / c;
/* 3510 */             oldfu = fu;oldfv = fv;
/* 3511 */             fu = a * oneoverc;fv = b * oneoverc;
/* 3512 */             iu = (int)oldfu;iv = (int)oldfv;
/* 3513 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3514 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 3516 */             iu += deltaU;
/* 3517 */             iv += deltaV;
/*      */           }
/* 3519 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 3523 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */             int al;
/*      */             
/*      */ 
/*      */             int al;
/*      */             
/*      */ 
/* 3531 */             if (this.m_bilinear) {
/* 3532 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 3533 */               int iui = (iu & 0xFFFF) >> 9;
/* 3534 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 3537 */               int pix0 = this.m_texture[ofs];
/* 3538 */               int pix1 = this.m_texture[(ofs + 1)];
/* 3539 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 3540 */               int pix2 = this.m_texture[ofs];
/* 3541 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 3544 */               int red0 = pix0 & 0xFF0000;
/* 3545 */               int red2 = pix2 & 0xFF0000;
/* 3546 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 3547 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 3548 */               int red = up + ((dn - up) * ivi >> 7) >> 16;
/*      */               
/*      */ 
/* 3551 */               red0 = pix0 & 0xFF00;
/* 3552 */               red2 = pix2 & 0xFF00;
/* 3553 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 3554 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 3555 */               int grn = up + ((dn - up) * ivi >> 7) >> 8;
/*      */               
/*      */ 
/* 3558 */               red0 = pix0 & 0xFF;
/* 3559 */               red2 = pix2 & 0xFF;
/* 3560 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 3561 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 3562 */               int blu = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 3565 */               pix0 >>>= 24;
/* 3566 */               pix2 >>>= 24;
/* 3567 */               up = pix0 + (((pix1 >>> 24) - pix0) * iui >> 7);
/* 3568 */               dn = pix2 + (((pix3 >>> 24) - pix2) * iui >> 7);
/* 3569 */               al = up + ((dn - up) * ivi >> 7);
/*      */             }
/*      */             else {
/* 3572 */               blu = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 3573 */               al = blu >>> 24;
/* 3574 */               red = (blu & 0xFF0000) >> 16;
/* 3575 */               grn = (blu & 0xFF00) >> 8;
/* 3576 */               blu &= 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 3580 */             int red = red * ir >>> 8;
/* 3581 */             int grn = grn * ig >>> 16;
/* 3582 */             int blu = blu * ib >>> 24;
/*      */             
/*      */ 
/* 3585 */             int bb = this.m_pixels[xstart];
/* 3586 */             int br = bb & 0xFF0000;
/* 3587 */             int bg = bb & 0xFF00;
/* 3588 */             bb &= 0xFF;
/*      */             
/*      */ 
/* 3591 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 3594 */               (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/* 3599 */         xpixel++;
/* 3600 */         if (!accurateMode) {
/* 3601 */           iu += this.iuadd;
/* 3602 */           iv += this.ivadd;
/*      */         }
/* 3604 */         ir += this.iradd;
/* 3605 */         ig += this.igadd;
/* 3606 */         ib += this.ibadd;
/* 3607 */         iz += this.izadd;
/*      */       }
/* 3609 */       ypixel++;
/* 3610 */       ytop += this.SCREEN_WIDTH;
/* 3611 */       this.xleft += leftadd;
/* 3612 */       this.xrght += rghtadd;
/* 3613 */       this.uleft += this.uleftadd;
/* 3614 */       this.vleft += this.vleftadd;
/* 3615 */       this.rleft += this.rleftadd;
/* 3616 */       this.gleft += this.gleftadd;
/* 3617 */       this.bleft += this.bleftadd;
/* 3618 */       this.zleft += this.zleftadd;
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
/*      */   private void drawsegment_gouraud_texture32_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 3634 */     int ypixel = ytop;
/* 3635 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 3636 */     boolean accurateMode = this.parent.hints[7];
/* 3637 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 3638 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 3639 */     int linearInterpPower = TEX_INTERP_POWER;
/* 3640 */     int linearInterpLength = 1 << linearInterpPower;
/* 3641 */     if (accurateMode) {
/* 3642 */       if (precomputeAccurateTexturing()) {
/* 3643 */         this.newax *= linearInterpLength;
/* 3644 */         this.newbx *= linearInterpLength;
/* 3645 */         this.newcx *= linearInterpLength;
/* 3646 */         screenz = this.nearPlaneDepth;
/* 3647 */         this.firstSegment = false;
/*      */       } else {
/* 3649 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 3653 */     ytop *= this.SCREEN_WIDTH;
/* 3654 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 3657 */     float iuf = this.iuadd;
/* 3658 */     float ivf = this.ivadd;
/* 3659 */     float irf = this.iradd;
/* 3660 */     float igf = this.igadd;
/* 3661 */     float ibf = this.ibadd;
/* 3662 */     float iaf = this.iaadd;
/*      */     
/* 3664 */     while (ytop < ybottom) {
/* 3665 */       int xstart = (int)(this.xleft + 0.5F);
/* 3666 */       if (xstart < 0) {
/* 3667 */         xstart = 0;
/*      */       }
/* 3669 */       int xpixel = xstart;
/*      */       
/* 3671 */       int xend = (int)(this.xrght + 0.5F);
/* 3672 */       if (xend > this.SCREEN_WIDTH)
/* 3673 */         xend = this.SCREEN_WIDTH;
/* 3674 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 3676 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 3677 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 3678 */       int ir = (int)(irf * xdiff + this.rleft);
/* 3679 */       int ig = (int)(igf * xdiff + this.gleft);
/* 3680 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 3681 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 3682 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 3684 */       xstart += ytop;
/* 3685 */       xend += ytop;
/* 3686 */       if (accurateMode) {
/* 3687 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 3688 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 3689 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 3690 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 3691 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 3693 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 3694 */       int interpCounter = 0;
/* 3695 */       int deltaU = 0;int deltaV = 0;
/* 3696 */       float fu = 0.0F;float fv = 0.0F;
/* 3697 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 3699 */       if ((accurateMode) && (goingIn)) {
/* 3700 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 3701 */         int leftOffset = linearInterpLength - rightOffset;
/* 3702 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 3703 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 3704 */         interpCounter = leftOffset;
/* 3705 */         float ao = a - leftOffset2 * this.newax;
/* 3706 */         float bo = b - leftOffset2 * this.newbx;
/* 3707 */         float co = c - leftOffset2 * this.newcx;
/* 3708 */         float oneoverc = 65536.0F / co;
/* 3709 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 3710 */         a += rightOffset2 * this.newax;
/* 3711 */         b += rightOffset2 * this.newbx;
/* 3712 */         c += rightOffset2 * this.newcx;
/* 3713 */         oneoverc = 65536.0F / c;
/* 3714 */         fu = a * oneoverc;fv = b * oneoverc;
/* 3715 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3716 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 3717 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 3719 */         float preoneoverc = 65536.0F / c;
/* 3720 */         fu = a * preoneoverc;
/* 3721 */         fv = b * preoneoverc;
/*      */       }
/* 3724 */       for (; 
/* 3724 */           xstart < xend; xstart++) {
/* 3725 */         if (accurateMode) {
/* 3726 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 3727 */           if (interpCounter == 0) {
/* 3728 */             a += this.newax;
/* 3729 */             b += this.newbx;
/* 3730 */             c += this.newcx;
/* 3731 */             float oneoverc = 65536.0F / c;
/* 3732 */             oldfu = fu;oldfv = fv;
/* 3733 */             fu = a * oneoverc;fv = b * oneoverc;
/* 3734 */             iu = (int)oldfu;iv = (int)oldfv;
/* 3735 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3736 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 3738 */             iu += deltaU;
/* 3739 */             iv += deltaV;
/*      */           }
/* 3741 */           interpCounter++;
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 3747 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 3751 */             int al = ia >> 16;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3757 */             if (this.m_bilinear) {
/* 3758 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 3759 */               int iui = (iu & 0xFFFF) >> 9;
/* 3760 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 3763 */               int pix0 = this.m_texture[ofs];
/* 3764 */               int pix1 = this.m_texture[(ofs + 1)];
/* 3765 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 3766 */               int pix2 = this.m_texture[ofs];
/* 3767 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 3770 */               int red0 = pix0 & 0xFF0000;
/* 3771 */               int red2 = pix2 & 0xFF0000;
/* 3772 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 3773 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 3774 */               int red = up + ((dn - up) * ivi >> 7) >> 16;
/*      */               
/*      */ 
/* 3777 */               red0 = pix0 & 0xFF00;
/* 3778 */               red2 = pix2 & 0xFF00;
/* 3779 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 3780 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 3781 */               int grn = up + ((dn - up) * ivi >> 7) >> 8;
/*      */               
/*      */ 
/* 3784 */               red0 = pix0 & 0xFF;
/* 3785 */               red2 = pix2 & 0xFF;
/* 3786 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 3787 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 3788 */               int blu = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 3791 */               pix0 >>>= 24;
/* 3792 */               pix2 >>>= 24;
/* 3793 */               up = pix0 + (((pix1 >>> 24) - pix0) * iui >> 7);
/* 3794 */               dn = pix2 + (((pix3 >>> 24) - pix2) * iui >> 7);
/* 3795 */               al = al * (up + ((dn - up) * ivi >> 7)) >> 8;
/*      */             } else {
/* 3797 */               blu = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 3798 */               al = al * (blu >>> 24) >> 8;
/* 3799 */               red = (blu & 0xFF0000) >> 16;
/* 3800 */               grn = (blu & 0xFF00) >> 8;
/* 3801 */               blu &= 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 3805 */             int red = red * ir >>> 8;
/* 3806 */             int grn = grn * ig >>> 16;
/* 3807 */             int blu = blu * ib >>> 24;
/*      */             
/*      */ 
/* 3810 */             int bb = this.m_pixels[xstart];
/* 3811 */             int br = bb & 0xFF0000;
/* 3812 */             int bg = bb & 0xFF00;
/* 3813 */             bb &= 0xFF;
/*      */             
/*      */ 
/* 3816 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 3819 */               (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/* 3825 */         xpixel++;
/* 3826 */         if (!accurateMode) {
/* 3827 */           iu += this.iuadd;
/* 3828 */           iv += this.ivadd;
/*      */         }
/* 3830 */         ir += this.iradd;
/* 3831 */         ig += this.igadd;
/* 3832 */         ib += this.ibadd;
/* 3833 */         ia += this.iaadd;
/* 3834 */         iz += this.izadd;
/*      */       }
/* 3836 */       ypixel++;
/* 3837 */       ytop += this.SCREEN_WIDTH;
/* 3838 */       this.xleft += leftadd;
/* 3839 */       this.xrght += rghtadd;
/* 3840 */       this.uleft += this.uleftadd;
/* 3841 */       this.vleft += this.vleftadd;
/* 3842 */       this.rleft += this.rleftadd;
/* 3843 */       this.gleft += this.gleftadd;
/* 3844 */       this.bleft += this.bleftadd;
/* 3845 */       this.aleft += this.aleftadd;
/* 3846 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PTriangle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */