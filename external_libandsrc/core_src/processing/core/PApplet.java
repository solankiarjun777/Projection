/*       */ package processing.core;
/*       */ 
/*       */ import java.applet.Applet;
/*       */ import java.applet.AppletContext;
/*       */ import java.awt.Color;
/*       */ import java.awt.Component;
/*       */ import java.awt.Cursor;
/*       */ import java.awt.Dimension;
/*       */ import java.awt.DisplayMode;
/*       */ import java.awt.FileDialog;
/*       */ import java.awt.Font;
/*       */ import java.awt.Frame;
/*       */ import java.awt.Graphics;
/*       */ import java.awt.GraphicsDevice;
/*       */ import java.awt.GraphicsEnvironment;
/*       */ import java.awt.Image;
/*       */ import java.awt.Insets;
/*       */ import java.awt.Label;
/*       */ import java.awt.MediaTracker;
/*       */ import java.awt.Point;
/*       */ import java.awt.Rectangle;
/*       */ import java.awt.SystemColor;
/*       */ import java.awt.Toolkit;
/*       */ import java.awt.event.ComponentAdapter;
/*       */ import java.awt.event.ComponentEvent;
/*       */ import java.awt.event.FocusEvent;
/*       */ import java.awt.event.FocusListener;
/*       */ import java.awt.event.KeyEvent;
/*       */ import java.awt.event.KeyListener;
/*       */ import java.awt.event.MouseAdapter;
/*       */ import java.awt.event.MouseEvent;
/*       */ import java.awt.event.MouseListener;
/*       */ import java.awt.event.MouseMotionListener;
/*       */ import java.awt.event.WindowAdapter;
/*       */ import java.awt.event.WindowEvent;
/*       */ import java.awt.image.BufferedImage;
/*       */ import java.awt.image.MemoryImageSource;
/*       */ import java.io.BufferedInputStream;
/*       */ import java.io.BufferedOutputStream;
/*       */ import java.io.BufferedReader;
/*       */ import java.io.ByteArrayOutputStream;
/*       */ import java.io.File;
/*       */ import java.io.FileInputStream;
/*       */ import java.io.FileNotFoundException;
/*       */ import java.io.FileOutputStream;
/*       */ import java.io.IOException;
/*       */ import java.io.InputStream;
/*       */ import java.io.InputStreamReader;
/*       */ import java.io.OutputStream;
/*       */ import java.io.OutputStreamWriter;
/*       */ import java.io.PrintStream;
/*       */ import java.io.PrintWriter;
/*       */ import java.io.UnsupportedEncodingException;
/*       */ import java.lang.reflect.Array;
/*       */ import java.lang.reflect.Constructor;
/*       */ import java.lang.reflect.InvocationTargetException;
/*       */ import java.lang.reflect.Method;
/*       */ import java.net.MalformedURLException;
/*       */ import java.net.URL;
/*       */ import java.net.URLConnection;
/*       */ import java.text.NumberFormat;
/*       */ import java.util.ArrayList;
/*       */ import java.util.Arrays;
/*       */ import java.util.Calendar;
/*       */ import java.util.HashMap;
/*       */ import java.util.Random;
/*       */ import java.util.StringTokenizer;
/*       */ import java.util.regex.Matcher;
/*       */ import java.util.regex.Pattern;
/*       */ import java.util.zip.GZIPInputStream;
/*       */ import java.util.zip.GZIPOutputStream;
/*       */ import javax.imageio.ImageIO;
/*       */ import javax.swing.JFileChooser;
/*       */ import javax.swing.SwingUtilities;
/*       */ import processing.xml.XMLElement;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public class PApplet
/*       */   extends Applet
/*       */   implements PConstants, Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener
/*       */ {
/*   174 */   public static final String javaVersionName = System.getProperty("java.version");
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   188 */   public static final float javaVersion = new Float(javaVersionName.substring(0, 3)).floatValue();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int platform;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static
/*       */   {
/*   208 */     String osname = System.getProperty("os.name");
/*       */     
/*   210 */     if (osname.indexOf("Mac") != -1) {
/*   211 */       platform = 2;
/*       */     }
/*   213 */     else if (osname.indexOf("Windows") != -1) {
/*   214 */       platform = 1;
/*       */     }
/*   216 */     else if (osname.equals("Linux")) {
/*   217 */       platform = 3;
/*       */     }
/*       */     else {
/*   220 */       platform = 0;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   241 */   public static boolean useQuartz = true;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   249 */   public static final int MENU_SHORTCUT = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics g;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Frame frame;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int screenWidth;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int screenHeight;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*   285 */   public Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics recorder;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String[] args;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String sketchPath;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static final boolean THREAD_DEBUG = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int DEFAULT_WIDTH = 100;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int DEFAULT_HEIGHT = 100;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int MIN_WINDOW_WIDTH = 128;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int MIN_WINDOW_HEIGHT = 128;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean defaultSize;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   volatile boolean resizeRequest;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   volatile int resizeWidth;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   volatile int resizeHeight;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int[] pixels;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int width;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int height;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int mouseX;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int mouseY;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int pmouseX;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int pmouseY;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected int dmouseX;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected int dmouseY;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected int emouseX;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected int emouseY;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean firstMouse;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int mouseButton;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean mousePressed;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public MouseEvent mouseEvent;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public char key;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int keyCode;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean keyPressed;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public KeyEvent keyEvent;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   527 */   public boolean focused = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   536 */   public boolean online = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   543 */   long millisOffset = System.currentTimeMillis();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   554 */   public float frameRate = 10.0F;
/*       */   
/*   556 */   protected long frameRateLastNanos = 0L;
/*       */   
/*       */ 
/*   559 */   protected float frameRateTarget = 60.0F;
/*   560 */   protected long frameRatePeriod = 16666666L;
/*       */   
/*       */ 
/*       */ 
/*       */   protected boolean looping;
/*       */   
/*       */ 
/*       */ 
/*       */   protected boolean redraw;
/*       */   
/*       */ 
/*       */ 
/*       */   public int frameCount;
/*       */   
/*       */ 
/*       */ 
/*       */   public volatile boolean finished;
/*       */   
/*       */ 
/*       */ 
/*       */   public volatile boolean paused;
/*       */   
/*       */ 
/*       */ 
/*       */   protected boolean exitCalled;
/*       */   
/*       */ 
/*       */ 
/*       */   Thread thread;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods sizeMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods preMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods drawMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods postMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods mouseEventMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods keyEventMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods disposeMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   public static final String ARGS_EDITOR_LOCATION = "--editor-location";
/*       */   
/*       */ 
/*       */ 
/*       */   public static final String ARGS_EXTERNAL = "--external";
/*       */   
/*       */ 
/*       */   public static final String ARGS_LOCATION = "--location";
/*       */   
/*       */ 
/*       */   public static final String ARGS_DISPLAY = "--display";
/*       */   
/*       */ 
/*       */   public static final String ARGS_BGCOLOR = "--bgcolor";
/*       */   
/*       */ 
/*       */   public static final String ARGS_PRESENT = "--present";
/*       */   
/*       */ 
/*       */   public static final String ARGS_EXCLUSIVE = "--exclusive";
/*       */   
/*       */ 
/*       */   public static final String ARGS_STOP_COLOR = "--stop-color";
/*       */   
/*       */ 
/*       */   public static final String ARGS_HIDE_STOP = "--hide-stop";
/*       */   
/*       */ 
/*       */   public static final String ARGS_SKETCH_FOLDER = "--sketch-path";
/*       */   
/*       */ 
/*       */   public static final String EXTERNAL_STOP = "__STOP__";
/*       */   
/*       */ 
/*       */   public static final String EXTERNAL_MOVE = "__MOVE__";
/*       */   
/*       */ 
/*   657 */   boolean external = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static final String ERROR_MIN_MAX = "Cannot use min() or max() on an empty array.";
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void init()
/*       */   {
/*   673 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*   674 */     this.screenWidth = screen.width;
/*   675 */     this.screenHeight = screen.height;
/*       */     
/*       */ 
/*   678 */     setFocusTraversalKeysEnabled(false);
/*       */     
/*       */ 
/*       */ 
/*   682 */     this.finished = false;
/*       */     
/*       */ 
/*   685 */     this.looping = true;
/*   686 */     this.redraw = true;
/*   687 */     this.firstMouse = true;
/*       */     
/*       */ 
/*   690 */     this.sizeMethods = new RegisteredMethods();
/*   691 */     this.preMethods = new RegisteredMethods();
/*   692 */     this.drawMethods = new RegisteredMethods();
/*   693 */     this.postMethods = new RegisteredMethods();
/*   694 */     this.mouseEventMethods = new RegisteredMethods();
/*   695 */     this.keyEventMethods = new RegisteredMethods();
/*   696 */     this.disposeMethods = new RegisteredMethods();
/*       */     try
/*       */     {
/*   699 */       getAppletContext();
/*   700 */       this.online = true;
/*       */     } catch (NullPointerException e) {
/*   702 */       this.online = false;
/*       */     }
/*       */     try
/*       */     {
/*   706 */       if (this.sketchPath == null) {
/*   707 */         this.sketchPath = System.getProperty("user.dir");
/*       */       }
/*       */     }
/*       */     catch (Exception localException) {}
/*   711 */     Dimension size = getSize();
/*   712 */     if ((size.width != 0) && (size.height != 0))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*   717 */       this.g = makeGraphics(size.width, size.height, sketchRenderer(), null, true);
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*       */ 
/*   723 */       this.defaultSize = true;
/*   724 */       int w = sketchWidth();
/*   725 */       int h = sketchHeight();
/*   726 */       this.g = makeGraphics(w, h, sketchRenderer(), null, true);
/*       */       
/*   728 */       setSize(w, h);
/*   729 */       setPreferredSize(new Dimension(w, h));
/*       */     }
/*   731 */     this.width = this.g.width;
/*   732 */     this.height = this.g.height;
/*       */     
/*   734 */     addListeners();
/*       */     
/*       */ 
/*       */ 
/*   738 */     start();
/*       */   }
/*       */   
/*       */   public int sketchWidth()
/*       */   {
/*   743 */     return 100;
/*       */   }
/*       */   
/*       */   public int sketchHeight()
/*       */   {
/*   748 */     return 100;
/*       */   }
/*       */   
/*       */   public String sketchRenderer()
/*       */   {
/*   753 */     return "processing.core.PGraphicsJava2D";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void start()
/*       */   {
/*   769 */     this.finished = false;
/*   770 */     this.paused = false;
/*       */     
/*       */ 
/*   773 */     if (this.thread == null) {
/*   774 */       this.thread = new Thread(this, "Animation Thread");
/*   775 */       this.thread.start();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void stop()
/*       */   {
/*   792 */     this.paused = true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void destroy()
/*       */   {
/*   810 */     exit();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static class RendererChangeException
/*       */     extends RuntimeException
/*       */   {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public class RegisteredMethods
/*       */   {
/*       */     int count;
/*       */     
/*       */ 
/*       */     Object[] objects;
/*       */     
/*       */ 
/*       */     Method[] methods;
/*       */     
/*       */ 
/*       */ 
/*       */     public RegisteredMethods() {}
/*       */     
/*       */ 
/*       */ 
/*       */     public void handle()
/*       */     {
/*   841 */       handle(new Object[0]);
/*       */     }
/*       */     
/*       */     public void handle(Object[] oargs) {
/*   845 */       for (int i = 0; i < this.count; i++) {
/*       */         try
/*       */         {
/*   848 */           this.methods[i].invoke(this.objects[i], oargs);
/*       */         } catch (Exception e) {
/*   850 */           if ((e instanceof InvocationTargetException)) {
/*   851 */             InvocationTargetException ite = (InvocationTargetException)e;
/*   852 */             ite.getTargetException().printStackTrace();
/*       */           } else {
/*   854 */             e.printStackTrace();
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */     public void add(Object object, Method method) {
/*   861 */       if (this.objects == null) {
/*   862 */         this.objects = new Object[5];
/*   863 */         this.methods = new Method[5];
/*       */       }
/*   865 */       if (this.count == this.objects.length) {
/*   866 */         this.objects = ((Object[])PApplet.expand(this.objects));
/*   867 */         this.methods = ((Method[])PApplet.expand(this.methods));
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   875 */       this.objects[this.count] = object;
/*   876 */       this.methods[this.count] = method;
/*   877 */       this.count += 1;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void remove(Object object, Method method)
/*       */     {
/*   887 */       int index = findIndex(object, method);
/*   888 */       if (index != -1)
/*       */       {
/*   890 */         this.count -= 1;
/*   891 */         for (int i = index; i < this.count; i++) {
/*   892 */           this.objects[i] = this.objects[(i + 1)];
/*   893 */           this.methods[i] = this.methods[(i + 1)];
/*       */         }
/*       */         
/*   896 */         this.objects[this.count] = null;
/*   897 */         this.methods[this.count] = null;
/*       */       }
/*       */     }
/*       */     
/*       */     protected int findIndex(Object object, Method method) {
/*   902 */       for (int i = 0; i < this.count; i++) {
/*   903 */         if ((this.objects[i] == object) && (this.methods[i].equals(method)))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*   908 */           return i;
/*       */         }
/*       */       }
/*   911 */       return -1;
/*       */     }
/*       */   }
/*       */   
/*       */   public void registerSize(Object o)
/*       */   {
/*   917 */     Class[] methodArgs = { Integer.TYPE, Integer.TYPE };
/*   918 */     registerWithArgs(this.sizeMethods, "size", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void registerPre(Object o) {
/*   922 */     registerNoArgs(this.preMethods, "pre", o);
/*       */   }
/*       */   
/*       */   public void registerDraw(Object o) {
/*   926 */     registerNoArgs(this.drawMethods, "draw", o);
/*       */   }
/*       */   
/*       */   public void registerPost(Object o) {
/*   930 */     registerNoArgs(this.postMethods, "post", o);
/*       */   }
/*       */   
/*       */   public void registerMouseEvent(Object o) {
/*   934 */     Class[] methodArgs = { MouseEvent.class };
/*   935 */     registerWithArgs(this.mouseEventMethods, "mouseEvent", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void registerKeyEvent(Object o)
/*       */   {
/*   940 */     Class[] methodArgs = { KeyEvent.class };
/*   941 */     registerWithArgs(this.keyEventMethods, "keyEvent", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void registerDispose(Object o) {
/*   945 */     registerNoArgs(this.disposeMethods, "dispose", o);
/*       */   }
/*       */   
/*       */ 
/*       */   protected void registerNoArgs(RegisteredMethods meth, String name, Object o)
/*       */   {
/*   951 */     Class<?> c = o.getClass();
/*       */     try {
/*   953 */       Method method = c.getMethod(name, new Class[0]);
/*   954 */       meth.add(o, method);
/*       */     }
/*       */     catch (NoSuchMethodException nsme) {
/*   957 */       die("There is no public " + name + "() method in the class " + 
/*   958 */         o.getClass().getName());
/*       */     }
/*       */     catch (Exception e) {
/*   961 */       die("Could not register " + name + " + () for " + o, e);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   protected void registerWithArgs(RegisteredMethods meth, String name, Object o, Class<?>[] cargs)
/*       */   {
/*   968 */     Class<?> c = o.getClass();
/*       */     try {
/*   970 */       Method method = c.getMethod(name, cargs);
/*   971 */       meth.add(o, method);
/*       */     }
/*       */     catch (NoSuchMethodException nsme) {
/*   974 */       die("There is no public " + name + "() method in the class " + 
/*   975 */         o.getClass().getName());
/*       */     }
/*       */     catch (Exception e) {
/*   978 */       die("Could not register " + name + " + () for " + o, e);
/*       */     }
/*       */   }
/*       */   
/*       */   public void unregisterSize(Object o)
/*       */   {
/*   984 */     Class[] methodArgs = { Integer.TYPE, Integer.TYPE };
/*   985 */     unregisterWithArgs(this.sizeMethods, "size", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void unregisterPre(Object o) {
/*   989 */     unregisterNoArgs(this.preMethods, "pre", o);
/*       */   }
/*       */   
/*       */   public void unregisterDraw(Object o) {
/*   993 */     unregisterNoArgs(this.drawMethods, "draw", o);
/*       */   }
/*       */   
/*       */   public void unregisterPost(Object o) {
/*   997 */     unregisterNoArgs(this.postMethods, "post", o);
/*       */   }
/*       */   
/*       */   public void unregisterMouseEvent(Object o) {
/*  1001 */     Class[] methodArgs = { MouseEvent.class };
/*  1002 */     unregisterWithArgs(this.mouseEventMethods, "mouseEvent", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void unregisterKeyEvent(Object o) {
/*  1006 */     Class[] methodArgs = { KeyEvent.class };
/*  1007 */     unregisterWithArgs(this.keyEventMethods, "keyEvent", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void unregisterDispose(Object o) {
/*  1011 */     unregisterNoArgs(this.disposeMethods, "dispose", o);
/*       */   }
/*       */   
/*       */ 
/*       */   protected void unregisterNoArgs(RegisteredMethods meth, String name, Object o)
/*       */   {
/*  1017 */     Class<?> c = o.getClass();
/*       */     try {
/*  1019 */       Method method = c.getMethod(name, new Class[0]);
/*  1020 */       meth.remove(o, method);
/*       */     } catch (Exception e) {
/*  1022 */       die("Could not unregister " + name + "() for " + o, e);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   protected void unregisterWithArgs(RegisteredMethods meth, String name, Object o, Class<?>[] cargs)
/*       */   {
/*  1029 */     Class<?> c = o.getClass();
/*       */     try {
/*  1031 */       Method method = c.getMethod(name, cargs);
/*  1032 */       meth.remove(o, method);
/*       */     } catch (Exception e) {
/*  1034 */       die("Could not unregister " + name + "() for " + o, e);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setup() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void draw()
/*       */   {
/*  1049 */     this.finished = true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void resizeRenderer(int iwidth, int iheight)
/*       */   {
/*  1058 */     if ((this.width != iwidth) || (this.height != iheight))
/*       */     {
/*  1060 */       this.g.setSize(iwidth, iheight);
/*  1061 */       this.width = iwidth;
/*  1062 */       this.height = iheight;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void size(int iwidth, int iheight)
/*       */   {
/*  1098 */     size(iwidth, iheight, "processing.core.PGraphicsJava2D", null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void size(int iwidth, int iheight, String irenderer)
/*       */   {
/*  1106 */     size(iwidth, iheight, irenderer, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void size(final int iwidth, final int iheight, String irenderer, String ipath)
/*       */   {
/*  1126 */     SwingUtilities.invokeLater(new Runnable()
/*       */     {
/*       */       public void run() {
/*  1129 */         PApplet.this.setPreferredSize(new Dimension(iwidth, iheight));
/*  1130 */         PApplet.this.setSize(iwidth, iheight);
/*       */       }
/*       */     });
/*       */     
/*       */ 
/*  1135 */     if (ipath != null) { ipath = savePath(ipath);
/*       */     }
/*  1137 */     String currentRenderer = this.g.getClass().getName();
/*  1138 */     if (currentRenderer.equals(irenderer))
/*       */     {
/*  1140 */       resizeRenderer(iwidth, iheight);
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*       */ 
/*  1146 */       this.g = makeGraphics(iwidth, iheight, irenderer, ipath, true);
/*  1147 */       this.width = iwidth;
/*  1148 */       this.height = iheight;
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1154 */       this.defaultSize = false;
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1160 */       throw new RendererChangeException();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics createGraphics(int iwidth, int iheight, String irenderer)
/*       */   {
/*  1230 */     PGraphics pg = makeGraphics(iwidth, iheight, irenderer, null, false);
/*       */     
/*  1232 */     return pg;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics createGraphics(int iwidth, int iheight, String irenderer, String ipath)
/*       */   {
/*  1243 */     if (ipath != null) {
/*  1244 */       ipath = savePath(ipath);
/*       */     }
/*  1246 */     PGraphics pg = makeGraphics(iwidth, iheight, irenderer, ipath, false);
/*  1247 */     pg.parent = this;
/*  1248 */     return pg;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected PGraphics makeGraphics(int iwidth, int iheight, String irenderer, String ipath, boolean iprimary)
/*       */   {
/*  1262 */     if ((irenderer.equals("processing.opengl.PGraphicsOpenGL")) && 
/*  1263 */       (platform == 1)) {
/*  1264 */       String s = System.getProperty("java.version");
/*  1265 */       if ((s != null) && 
/*  1266 */         (s.equals("1.5.0_10"))) {
/*  1267 */         System.err.println("OpenGL support is broken with Java 1.5.0_10");
/*  1268 */         System.err.println("See http://dev.processing.org/bugs/show_bug.cgi?id=513 for more info.");
/*       */         
/*  1270 */         throw new RuntimeException("Please update your Java installation (see bug #513)");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1282 */     String openglError = 
/*  1283 */       "Before using OpenGL, first select Import Library > opengl from the Sketch menu.";
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  1314 */       Class<?> rendererClass = 
/*  1315 */         Thread.currentThread().getContextClassLoader().loadClass(irenderer);
/*       */       
/*       */ 
/*       */ 
/*  1319 */       Constructor<?> constructor = rendererClass.getConstructor(new Class[0]);
/*  1320 */       PGraphics pg = (PGraphics)constructor.newInstance(new Object[0]);
/*       */       
/*  1322 */       pg.setParent(this);
/*  1323 */       pg.setPrimary(iprimary);
/*  1324 */       if (ipath != null) pg.setPath(ipath);
/*  1325 */       pg.setSize(iwidth, iheight);
/*       */       
/*       */ 
/*  1328 */       return pg;
/*       */     }
/*       */     catch (InvocationTargetException ite) {
/*  1331 */       String msg = ite.getTargetException().getMessage();
/*  1332 */       if ((msg != null) && 
/*  1333 */         (msg.indexOf("no jogl in java.library.path") != -1)) {
/*  1334 */         throw new RuntimeException(openglError + 
/*  1335 */           " (The native library is missing.)");
/*       */       }
/*       */       
/*  1338 */       ite.getTargetException().printStackTrace();
/*  1339 */       Throwable target = ite.getTargetException();
/*  1340 */       if (platform == 2) { target.printStackTrace(System.out);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  1345 */       throw new RuntimeException(target.getMessage());
/*       */     }
/*       */     catch (ClassNotFoundException cnfe)
/*       */     {
/*  1349 */       if (cnfe.getMessage().indexOf("processing.opengl.PGraphicsGL") != -1) {
/*  1350 */         throw new RuntimeException(openglError + 
/*  1351 */           " (The library .jar file is missing.)");
/*       */       }
/*  1353 */       throw new RuntimeException("You need to use \"Import Library\" to add " + 
/*  1354 */         irenderer + " to your sketch.");
/*       */ 
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  1359 */       if (((e instanceof IllegalArgumentException)) || 
/*  1360 */         ((e instanceof NoSuchMethodException)) || 
/*  1361 */         ((e instanceof IllegalAccessException))) {
/*  1362 */         e.printStackTrace();
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1370 */         String msg = irenderer + " needs to be updated " + 
/*  1371 */           "for the current release of Processing.";
/*  1372 */         throw new RuntimeException(msg);
/*       */       }
/*       */       
/*  1375 */       if (platform == 2) e.printStackTrace(System.out);
/*  1376 */       throw new RuntimeException(e.getMessage());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage createImage(int wide, int high, int format)
/*       */   {
/*  1386 */     return createImage(wide, high, format, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage createImage(int wide, int high, int format, Object params)
/*       */   {
/*  1408 */     PImage image = new PImage(wide, high, format);
/*  1409 */     if (params != null) {
/*  1410 */       image.setParams(this.g, params);
/*       */     }
/*  1412 */     image.parent = this;
/*  1413 */     return image;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void update(Graphics screen)
/*       */   {
/*  1421 */     paint(screen);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void paint(Graphics screen)
/*       */   {
/*  1432 */     if (this.frameCount == 0)
/*       */     {
/*       */ 
/*       */ 
/*  1436 */       return;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1465 */     if (this.g != null)
/*       */     {
/*       */ 
/*  1468 */       if (this.g.image != null) {
/*  1469 */         synchronized (this.g.image) {
/*  1470 */           screen.drawImage(this.g.image, 0, 0, null);
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void run()
/*       */   {
/*  1496 */     long beforeTime = System.nanoTime();
/*  1497 */     long overSleepTime = 0L;
/*       */     
/*  1499 */     int noDelays = 0;
/*       */     
/*       */ 
/*  1502 */     int NO_DELAYS_PER_YIELD = 15;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1512 */     while ((Thread.currentThread() == this.thread) && (!this.finished)) {
/*  1513 */       while (this.paused) {
/*       */         try
/*       */         {
/*  1516 */           Thread.sleep(100L);
/*       */         }
/*       */         catch (InterruptedException localInterruptedException) {}
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  1524 */       if (this.resizeRequest) {
/*  1525 */         resizeRenderer(this.resizeWidth, this.resizeHeight);
/*  1526 */         this.resizeRequest = false;
/*       */       }
/*       */       
/*       */ 
/*  1530 */       handleDraw();
/*       */       
/*  1532 */       if (this.frameCount == 1)
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1543 */         requestFocusInWindow();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1551 */       long afterTime = System.nanoTime();
/*  1552 */       long timeDiff = afterTime - beforeTime;
/*       */       
/*  1554 */       long sleepTime = this.frameRatePeriod - timeDiff - overSleepTime;
/*       */       
/*  1556 */       if (sleepTime > 0L)
/*       */       {
/*       */         try {
/*  1559 */           Thread.sleep(sleepTime / 1000000L, (int)(sleepTime % 1000000L));
/*  1560 */           noDelays = 0;
/*       */         }
/*       */         catch (InterruptedException localInterruptedException1) {}
/*  1563 */         overSleepTime = System.nanoTime() - afterTime - sleepTime;
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  1568 */         overSleepTime = 0L;
/*       */         
/*  1570 */         if (noDelays > 15) {
/*  1571 */           Thread.yield();
/*  1572 */           noDelays = 0;
/*       */         }
/*       */       }
/*       */       
/*  1576 */       beforeTime = System.nanoTime();
/*       */     }
/*       */     
/*  1579 */     dispose();
/*       */     
/*       */ 
/*       */ 
/*  1583 */     if (this.exitCalled) {
/*  1584 */       exit2();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public void handleDraw()
/*       */   {
/*  1591 */     if ((this.g != null) && ((this.looping) || (this.redraw))) {
/*  1592 */       if (!this.g.canDraw())
/*       */       {
/*       */ 
/*  1595 */         return;
/*       */       }
/*       */       
/*  1598 */       this.g.beginDraw();
/*  1599 */       if (this.recorder != null) {
/*  1600 */         this.recorder.beginDraw();
/*       */       }
/*       */       
/*  1603 */       long now = System.nanoTime();
/*       */       
/*  1605 */       if (this.frameCount == 0)
/*       */       {
/*       */         try {
/*  1608 */           setup();
/*       */ 
/*       */         }
/*       */         catch (RendererChangeException e)
/*       */         {
/*  1613 */           return;
/*       */         }
/*  1615 */         this.defaultSize = false;
/*       */       }
/*       */       else
/*       */       {
/*  1619 */         double rate = 1000000.0D / ((now - this.frameRateLastNanos) / 1000000.0D);
/*  1620 */         float instantaneousRate = (float)rate / 1000.0F;
/*  1621 */         this.frameRate = (this.frameRate * 0.9F + instantaneousRate * 0.1F);
/*       */         
/*  1623 */         this.preMethods.handle();
/*       */         
/*       */ 
/*       */ 
/*  1627 */         this.pmouseX = this.dmouseX;
/*  1628 */         this.pmouseY = this.dmouseY;
/*       */         
/*       */ 
/*  1631 */         draw();
/*       */         
/*       */ 
/*       */ 
/*  1635 */         this.dmouseX = this.mouseX;
/*  1636 */         this.dmouseY = this.mouseY;
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1642 */         dequeueMouseEvents();
/*  1643 */         dequeueKeyEvents();
/*       */         
/*  1645 */         this.drawMethods.handle();
/*       */         
/*  1647 */         this.redraw = false;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  1652 */       this.g.endDraw();
/*       */       
/*  1654 */       if (this.recorder != null) {
/*  1655 */         this.recorder.endDraw();
/*       */       }
/*       */       
/*  1658 */       this.frameRateLastNanos = now;
/*  1659 */       this.frameCount += 1;
/*       */       
/*  1661 */       repaint();
/*  1662 */       getToolkit().sync();
/*       */       
/*  1664 */       this.postMethods.handle();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void redraw()
/*       */   {
/*  1674 */     if (!this.looping) {
/*  1675 */       this.redraw = true;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void loop()
/*       */   {
/*  1692 */     if (!this.looping) {
/*  1693 */       this.looping = true;
/*       */     }
/*       */   }
/*       */   
/*       */   public synchronized void noLoop()
/*       */   {
/*  1699 */     if (this.looping) {
/*  1700 */       this.looping = false;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void addListeners()
/*       */   {
/*  1709 */     addMouseListener(this);
/*  1710 */     addMouseMotionListener(this);
/*  1711 */     addKeyListener(this);
/*  1712 */     addFocusListener(this);
/*       */     
/*  1714 */     addComponentListener(new ComponentAdapter() {
/*       */       public void componentResized(ComponentEvent e) {
/*  1716 */         Component c = e.getComponent();
/*       */         
/*  1718 */         Rectangle bounds = c.getBounds();
/*  1719 */         PApplet.this.resizeRequest = true;
/*  1720 */         PApplet.this.resizeWidth = bounds.width;
/*  1721 */         PApplet.this.resizeHeight = bounds.height;
/*       */       }
/*       */     });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1730 */   MouseEvent[] mouseEventQueue = new MouseEvent[10];
/*       */   int mouseEventCount;
/*       */   
/*       */   protected void enqueueMouseEvent(MouseEvent e) {
/*  1734 */     synchronized (this.mouseEventQueue) {
/*  1735 */       if (this.mouseEventCount == this.mouseEventQueue.length) {
/*  1736 */         MouseEvent[] temp = new MouseEvent[this.mouseEventCount << 1];
/*  1737 */         System.arraycopy(this.mouseEventQueue, 0, temp, 0, this.mouseEventCount);
/*  1738 */         this.mouseEventQueue = temp;
/*       */       }
/*  1740 */       this.mouseEventQueue[(this.mouseEventCount++)] = e;
/*       */     }
/*       */   }
/*       */   
/*       */   protected void dequeueMouseEvents() {
/*  1745 */     synchronized (this.mouseEventQueue) {
/*  1746 */       for (int i = 0; i < this.mouseEventCount; i++) {
/*  1747 */         this.mouseEvent = this.mouseEventQueue[i];
/*  1748 */         handleMouseEvent(this.mouseEvent);
/*       */       }
/*  1750 */       this.mouseEventCount = 0;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void handleMouseEvent(MouseEvent event)
/*       */   {
/*  1763 */     int id = event.getID();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1769 */     if ((id == 506) || 
/*  1770 */       (id == 503)) {
/*  1771 */       this.pmouseX = this.emouseX;
/*  1772 */       this.pmouseY = this.emouseY;
/*  1773 */       this.mouseX = event.getX();
/*  1774 */       this.mouseY = event.getY();
/*       */     }
/*       */     
/*  1777 */     this.mouseEvent = event;
/*       */     
/*  1779 */     int modifiers = event.getModifiers();
/*  1780 */     if ((modifiers & 0x10) != 0) {
/*  1781 */       this.mouseButton = 37;
/*  1782 */     } else if ((modifiers & 0x8) != 0) {
/*  1783 */       this.mouseButton = 3;
/*  1784 */     } else if ((modifiers & 0x4) != 0) {
/*  1785 */       this.mouseButton = 39;
/*       */     }
/*       */     
/*  1788 */     if ((platform == 2) && 
/*  1789 */       (this.mouseEvent.isPopupTrigger())) {
/*  1790 */       this.mouseButton = 39;
/*       */     }
/*       */     
/*       */ 
/*  1794 */     this.mouseEventMethods.handle(new Object[] { event });
/*       */     
/*       */ 
/*       */ 
/*  1798 */     if (this.firstMouse) {
/*  1799 */       this.pmouseX = this.mouseX;
/*  1800 */       this.pmouseY = this.mouseY;
/*  1801 */       this.dmouseX = this.mouseX;
/*  1802 */       this.dmouseY = this.mouseY;
/*  1803 */       this.firstMouse = false;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1808 */     switch (id) {
/*       */     case 501: 
/*  1810 */       this.mousePressed = true;
/*  1811 */       mousePressed();
/*  1812 */       break;
/*       */     case 502: 
/*  1814 */       this.mousePressed = false;
/*  1815 */       mouseReleased();
/*  1816 */       break;
/*       */     case 500: 
/*  1818 */       mouseClicked();
/*  1819 */       break;
/*       */     case 506: 
/*  1821 */       mouseDragged();
/*  1822 */       break;
/*       */     case 503: 
/*  1824 */       mouseMoved();
/*       */     }
/*       */     
/*       */     
/*  1828 */     if ((id == 506) || 
/*  1829 */       (id == 503)) {
/*  1830 */       this.emouseX = this.mouseX;
/*  1831 */       this.emouseY = this.mouseY;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void checkMouseEvent(MouseEvent event)
/*       */   {
/*  1842 */     if (this.looping) {
/*  1843 */       enqueueMouseEvent(event);
/*       */     } else {
/*  1845 */       handleMouseEvent(event);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mousePressed(MouseEvent e)
/*       */   {
/*  1856 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseReleased(MouseEvent e) {
/*  1860 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseClicked(MouseEvent e) {
/*  1864 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseEntered(MouseEvent e) {
/*  1868 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseExited(MouseEvent e) {
/*  1872 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseDragged(MouseEvent e) {
/*  1876 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseMoved(MouseEvent e) {
/*  1880 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mousePressed() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mouseReleased() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mouseClicked() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mouseDragged() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1961 */   KeyEvent[] keyEventQueue = new KeyEvent[10];
/*       */   
/*       */   public void mouseMoved() {}
/*       */   
/*  1965 */   protected void enqueueKeyEvent(KeyEvent e) { synchronized (this.keyEventQueue) {
/*  1966 */       if (this.keyEventCount == this.keyEventQueue.length) {
/*  1967 */         KeyEvent[] temp = new KeyEvent[this.keyEventCount << 1];
/*  1968 */         System.arraycopy(this.keyEventQueue, 0, temp, 0, this.keyEventCount);
/*  1969 */         this.keyEventQueue = temp;
/*       */       }
/*  1971 */       this.keyEventQueue[(this.keyEventCount++)] = e;
/*       */     }
/*       */   }
/*       */   
/*       */   protected void dequeueKeyEvents() {
/*  1976 */     synchronized (this.keyEventQueue) {
/*  1977 */       for (int i = 0; i < this.keyEventCount; i++) {
/*  1978 */         this.keyEvent = this.keyEventQueue[i];
/*  1979 */         handleKeyEvent(this.keyEvent);
/*       */       }
/*  1981 */       this.keyEventCount = 0;
/*       */     }
/*       */   }
/*       */   
/*       */   protected void handleKeyEvent(KeyEvent event)
/*       */   {
/*  1987 */     this.keyEvent = event;
/*  1988 */     this.key = event.getKeyChar();
/*  1989 */     this.keyCode = event.getKeyCode();
/*       */     
/*  1991 */     this.keyEventMethods.handle(new Object[] { event });
/*       */     
/*  1993 */     switch (event.getID()) {
/*       */     case 401: 
/*  1995 */       this.keyPressed = true;
/*  1996 */       keyPressed();
/*  1997 */       break;
/*       */     case 402: 
/*  1999 */       this.keyPressed = false;
/*  2000 */       keyReleased();
/*  2001 */       break;
/*       */     case 400: 
/*  2003 */       keyTyped();
/*       */     }
/*       */     
/*       */     
/*       */ 
/*       */ 
/*  2009 */     if (event.getID() == 401) {
/*  2010 */       if (this.key == '\033') {
/*  2011 */         exit();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  2017 */       if ((this.external) && 
/*  2018 */         (event.getModifiers() == MENU_SHORTCUT) && 
/*  2019 */         (event.getKeyCode() == 87)) {
/*  2020 */         exit();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   protected void checkKeyEvent(KeyEvent event)
/*       */   {
/*  2027 */     if (this.looping) {
/*  2028 */       enqueueKeyEvent(event);
/*       */     } else {
/*  2030 */       handleKeyEvent(event);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2042 */   public void keyPressed(KeyEvent e) { checkKeyEvent(e); }
/*  2043 */   public void keyReleased(KeyEvent e) { checkKeyEvent(e); }
/*  2044 */   public void keyTyped(KeyEvent e) { checkKeyEvent(e); }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   int keyEventCount;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void keyPressed() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void keyReleased() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void keyTyped() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void focusGained() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void focusGained(FocusEvent e)
/*       */   {
/*  2140 */     this.focused = true;
/*  2141 */     focusGained();
/*       */   }
/*       */   
/*       */   public void focusLost() {}
/*       */   
/*       */   public void focusLost(FocusEvent e)
/*       */   {
/*  2148 */     this.focused = false;
/*  2149 */     focusLost();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int millis()
/*       */   {
/*  2176 */     return (int)(System.currentTimeMillis() - this.millisOffset);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int second()
/*       */   {
/*  2190 */     return Calendar.getInstance().get(13);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int minute()
/*       */   {
/*  2206 */     return Calendar.getInstance().get(12);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int hour()
/*       */   {
/*  2228 */     return Calendar.getInstance().get(11);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int day()
/*       */   {
/*  2248 */     return Calendar.getInstance().get(5);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int month()
/*       */   {
/*  2264 */     return Calendar.getInstance().get(2) + 1;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int year()
/*       */   {
/*  2280 */     return Calendar.getInstance().get(1);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void delay(int napTime)
/*       */   {
/*  2303 */     if ((this.frameCount != 0) && 
/*  2304 */       (napTime > 0)) {
/*       */       try {
/*  2306 */         Thread.sleep(napTime);
/*       */       }
/*       */       catch (InterruptedException localInterruptedException) {}
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void frameRate(float newRateTarget)
/*       */   {
/*  2330 */     this.frameRateTarget = newRateTarget;
/*  2331 */     this.frameRatePeriod = ((1.0E9D / this.frameRateTarget));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String param(String what)
/*       */   {
/*  2351 */     if (this.online) {
/*  2352 */       return getParameter(what);
/*       */     }
/*       */     
/*  2355 */     System.err.println("param() only works inside a web browser");
/*       */     
/*  2357 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void status(String what)
/*       */   {
/*  2374 */     if (this.online) {
/*  2375 */       showStatus(what);
/*       */     }
/*       */     else {
/*  2378 */       System.out.println(what);
/*       */     }
/*       */   }
/*       */   
/*       */   public void link(String here)
/*       */   {
/*  2384 */     link(here, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void link(String url, String frameTitle)
/*       */   {
/*  2407 */     if (this.online) {
/*       */       try {
/*  2409 */         if (frameTitle == null) {
/*  2410 */           getAppletContext().showDocument(new URL(url));
/*       */         } else {
/*  2412 */           getAppletContext().showDocument(new URL(url), frameTitle);
/*       */         }
/*       */       } catch (Exception e) {
/*  2415 */         e.printStackTrace();
/*  2416 */         throw new RuntimeException("Could not open " + url);
/*       */       }
/*       */     } else {
/*       */       try {
/*  2420 */         if (platform == 1)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2432 */           url = url.replaceAll("&", "^&");
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  2437 */           Runtime.getRuntime().exec("cmd /c start " + url);
/*       */         }
/*  2439 */         else if (platform == 2)
/*       */         {
/*       */ 
/*       */           try
/*       */           {
/*       */ 
/*  2445 */             Class<?> eieio = Class.forName("com.apple.eio.FileManager");
/*  2446 */             Method openMethod = 
/*  2447 */               eieio.getMethod("openURL", new Class[] { String.class });
/*  2448 */             openMethod.invoke(null, new Object[] { url });
/*       */           } catch (Exception e) {
/*  2450 */             e.printStackTrace();
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/*  2455 */           open(url);
/*       */         }
/*       */       } catch (IOException e) {
/*  2458 */         e.printStackTrace();
/*  2459 */         throw new RuntimeException("Could not open " + url);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void open(String filename)
/*       */   {
/*  2481 */     open(new String[] { filename });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static Process open(String[] argv)
/*       */   {
/*  2496 */     String[] params = (String[])null;
/*       */     
/*  2498 */     if (platform == 1)
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  2503 */       params = new String[] { "cmd", "/c" };
/*       */     }
/*  2505 */     else if (platform == 2) {
/*  2506 */       params = new String[] { "open" };
/*       */     }
/*  2508 */     else if (platform == 3) {
/*  2509 */       if (openLauncher == null) {
/*       */         try
/*       */         {
/*  2512 */           Process p = Runtime.getRuntime().exec(new String[] { "gnome-open" });
/*  2513 */           p.waitFor();
/*       */           
/*  2515 */           openLauncher = "gnome-open";
/*       */         } catch (Exception localException) {}
/*       */       }
/*  2518 */       if (openLauncher == null) {
/*       */         try
/*       */         {
/*  2521 */           Process p = Runtime.getRuntime().exec(new String[] { "kde-open" });
/*  2522 */           p.waitFor();
/*  2523 */           openLauncher = "kde-open";
/*       */         } catch (Exception localException1) {}
/*       */       }
/*  2526 */       if (openLauncher == null) {
/*  2527 */         System.err.println("Could not find gnome-open or kde-open, the open() command may not work.");
/*       */       }
/*       */       
/*  2530 */       if (openLauncher != null) {
/*  2531 */         params = new String[] { openLauncher };
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  2537 */     if (params != null)
/*       */     {
/*  2539 */       if (params[0].equals(argv[0]))
/*       */       {
/*  2541 */         return exec(argv);
/*       */       }
/*  2543 */       params = concat(params, argv);
/*  2544 */       return exec(params);
/*       */     }
/*       */     
/*  2547 */     return exec(argv);
/*       */   }
/*       */   
/*       */   public static Process exec(String[] argv)
/*       */   {
/*       */     try
/*       */     {
/*  2554 */       return Runtime.getRuntime().exec(argv);
/*       */     } catch (Exception e) {
/*  2556 */       e.printStackTrace();
/*  2557 */       throw new RuntimeException("Could not open " + join(argv, ' '));
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void die(String what)
/*       */   {
/*  2570 */     dispose();
/*  2571 */     throw new RuntimeException(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void die(String what, Exception e)
/*       */   {
/*  2579 */     if (e != null) e.printStackTrace();
/*  2580 */     die(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void exit()
/*       */   {
/*  2589 */     if (this.thread == null)
/*       */     {
/*       */ 
/*  2592 */       exit2();
/*       */     }
/*  2594 */     else if (this.looping)
/*       */     {
/*  2596 */       this.finished = true;
/*       */       
/*       */ 
/*  2599 */       this.exitCalled = true;
/*       */     }
/*  2601 */     else if (!this.looping)
/*       */     {
/*       */ 
/*  2604 */       dispose();
/*       */       
/*       */ 
/*  2607 */       exit2();
/*       */     }
/*       */   }
/*       */   
/*       */   void exit2() {
/*       */     try {
/*  2613 */       System.exit(0);
/*       */     }
/*       */     catch (SecurityException localSecurityException) {}
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void dispose()
/*       */   {
/*  2628 */     this.finished = true;
/*       */     
/*       */ 
/*  2631 */     if (this.thread == null) return;
/*  2632 */     this.thread = null;
/*       */     
/*       */ 
/*  2635 */     if (this.g != null) this.g.dispose();
/*  2636 */     this.disposeMethods.handle();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void method(String name)
/*       */   {
/*       */     try
/*       */     {
/*  2645 */       Method method = getClass().getMethod(name, new Class[0]);
/*  2646 */       method.invoke(this, new Object[0]);
/*       */     }
/*       */     catch (IllegalArgumentException e) {
/*  2649 */       e.printStackTrace();
/*       */     } catch (IllegalAccessException e) {
/*  2651 */       e.printStackTrace();
/*       */     } catch (InvocationTargetException e) {
/*  2653 */       e.getTargetException().printStackTrace();
/*       */     } catch (NoSuchMethodException nsme) {
/*  2655 */       System.err.println("There is no public " + name + "() method " + 
/*  2656 */         "in the class " + getClass().getName());
/*       */     } catch (Exception e) {
/*  2658 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */   public void thread(final String name)
/*       */   {
/*  2664 */     Thread later = new Thread() {
/*       */       public void run() {
/*  2666 */         PApplet.this.method(name);
/*       */       }
/*  2668 */     };
/*  2669 */     later.start();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static String openLauncher;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void save(String filename)
/*       */   {
/*  2718 */     this.g.save(savePath(filename));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void saveFrame()
/*       */   {
/*       */     try
/*       */     {
/*  2732 */       this.g.save(savePath("screen-" + nf(this.frameCount, 4) + ".tif"));
/*       */     } catch (SecurityException se) {
/*  2734 */       System.err.println("Can't use saveFrame() when running in a browser, unless using a signed applet.");
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void saveFrame(String what)
/*       */   {
/*       */     try
/*       */     {
/*  2752 */       this.g.save(savePath(insertFrame(what)));
/*       */     } catch (SecurityException se) {
/*  2754 */       System.err.println("Can't use saveFrame() when running in a browser, unless using a signed applet.");
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected String insertFrame(String what)
/*       */   {
/*  2768 */     int first = what.indexOf('#');
/*  2769 */     int last = what.lastIndexOf('#');
/*       */     
/*  2771 */     if ((first != -1) && (last - first > 0)) {
/*  2772 */       String prefix = what.substring(0, first);
/*  2773 */       int count = last - first + 1;
/*  2774 */       String suffix = what.substring(last + 1);
/*  2775 */       return prefix + nf(this.frameCount, count) + suffix;
/*       */     }
/*  2777 */     return what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2789 */   int cursorType = 0;
/*  2790 */   boolean cursorVisible = true;
/*       */   PImage invisibleCursor;
/*       */   Random internalRandom;
/*       */   static final int PERLIN_YWRAPB = 4;
/*       */   static final int PERLIN_YWRAP = 16;
/*       */   static final int PERLIN_ZWRAPB = 8;
/*       */   static final int PERLIN_ZWRAP = 256;
/*       */   static final int PERLIN_SIZE = 4095;
/*       */   
/*  2799 */   public void cursor(int cursorType) { setCursor(Cursor.getPredefinedCursor(cursorType));
/*  2800 */     this.cursorVisible = true;
/*  2801 */     this.cursorType = cursorType;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void cursor(PImage image)
/*       */   {
/*  2810 */     cursor(image, image.width / 2, image.height / 2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void cursor(PImage image, int hotspotX, int hotspotY)
/*       */   {
/*  2837 */     Image jimage = 
/*  2838 */       createImage(new MemoryImageSource(image.width, image.height, 
/*  2839 */       image.pixels, 0, image.width));
/*  2840 */     Point hotspot = new Point(hotspotX, hotspotY);
/*  2841 */     Toolkit tk = Toolkit.getDefaultToolkit();
/*  2842 */     Cursor cursor = tk.createCustomCursor(jimage, hotspot, "Custom Cursor");
/*  2843 */     setCursor(cursor);
/*  2844 */     this.cursorVisible = true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void cursor()
/*       */   {
/*  2857 */     if (!this.cursorVisible) {
/*  2858 */       this.cursorVisible = true;
/*  2859 */       setCursor(Cursor.getPredefinedCursor(this.cursorType));
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noCursor()
/*       */   {
/*  2874 */     if (!this.cursorVisible) { return;
/*       */     }
/*  2876 */     if (this.invisibleCursor == null) {
/*  2877 */       this.invisibleCursor = new PImage(16, 16, 2);
/*       */     }
/*       */     
/*       */ 
/*  2881 */     cursor(this.invisibleCursor, 8, 8);
/*  2882 */     this.cursorVisible = false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void print(byte what)
/*       */   {
/*  2890 */     System.out.print(what);
/*  2891 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(boolean what) {
/*  2895 */     System.out.print(what);
/*  2896 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(char what) {
/*  2900 */     System.out.print(what);
/*  2901 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(int what) {
/*  2905 */     System.out.print(what);
/*  2906 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(float what) {
/*  2910 */     System.out.print(what);
/*  2911 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(String what) {
/*  2915 */     System.out.print(what);
/*  2916 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(Object what) {
/*  2920 */     if (what == null)
/*       */     {
/*  2922 */       System.out.print("null");
/*       */     } else {
/*  2924 */       System.out.println(what.toString());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public static void println()
/*       */   {
/*  2931 */     System.out.println();
/*       */   }
/*       */   
/*       */ 
/*       */   public static void println(byte what)
/*       */   {
/*  2937 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(boolean what) {
/*  2941 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(char what) {
/*  2945 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(int what) {
/*  2949 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(float what) {
/*  2953 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(String what) {
/*  2957 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(Object what) {
/*  2961 */     if (what == null)
/*       */     {
/*  2963 */       System.out.println("null");
/*       */     }
/*       */     else {
/*  2966 */       String name = what.getClass().getName();
/*  2967 */       if (name.charAt(0) == '[')
/*  2968 */         switch (name.charAt(1))
/*       */         {
/*       */ 
/*       */         case '[': 
/*  2972 */           System.out.println(what);
/*  2973 */           break;
/*       */         
/*       */ 
/*       */         case 'L': 
/*  2977 */           Object[] poo = (Object[])what;
/*  2978 */           for (int i = 0; i < poo.length; i++) {
/*  2979 */             if ((poo[i] instanceof String)) {
/*  2980 */               System.out.println("[" + i + "] \"" + poo[i] + "\"");
/*       */             } else {
/*  2982 */               System.out.println("[" + i + "] " + poo[i]);
/*       */             }
/*       */           }
/*  2985 */           break;
/*       */         
/*       */         case 'Z': 
/*  2988 */           boolean[] zz = (boolean[])what;
/*  2989 */           for (int i = 0; i < zz.length; i++) {
/*  2990 */             System.out.println("[" + i + "] " + zz[i]);
/*       */           }
/*  2992 */           break;
/*       */         
/*       */         case 'B': 
/*  2995 */           byte[] bb = (byte[])what;
/*  2996 */           for (int i = 0; i < bb.length; i++) {
/*  2997 */             System.out.println("[" + i + "] " + bb[i]);
/*       */           }
/*  2999 */           break;
/*       */         
/*       */         case 'C': 
/*  3002 */           char[] cc = (char[])what;
/*  3003 */           for (int i = 0; i < cc.length; i++) {
/*  3004 */             System.out.println("[" + i + "] '" + cc[i] + "'");
/*       */           }
/*  3006 */           break;
/*       */         
/*       */         case 'I': 
/*  3009 */           int[] ii = (int[])what;
/*  3010 */           for (int i = 0; i < ii.length; i++) {
/*  3011 */             System.out.println("[" + i + "] " + ii[i]);
/*       */           }
/*  3013 */           break;
/*       */         
/*       */         case 'F': 
/*  3016 */           float[] ff = (float[])what;
/*  3017 */           for (int i = 0; i < ff.length; i++) {
/*  3018 */             System.out.println("[" + i + "] " + ff[i]);
/*       */           }
/*  3020 */           break;
/*       */         
/*       */         case 'D': 
/*  3023 */           double[] dd = (double[])what;
/*  3024 */           for (int i = 0; i < dd.length; i++) {
/*  3025 */             System.out.println("[" + i + "] " + dd[i]);
/*       */           }
/*  3027 */           break;
/*       */         
/*       */         default: 
/*  3030 */           System.out.println(what);break;
/*       */         }
/*       */          else {
/*  3033 */         System.out.println(what);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float abs(float n)
/*       */   {
/*  3064 */     return n < 0.0F ? -n : n;
/*       */   }
/*       */   
/*       */   public static final int abs(int n) {
/*  3068 */     return n < 0 ? -n : n;
/*       */   }
/*       */   
/*       */   public static final float sq(float a) {
/*  3072 */     return a * a;
/*       */   }
/*       */   
/*       */   public static final float sqrt(float a) {
/*  3076 */     return (float)Math.sqrt(a);
/*       */   }
/*       */   
/*       */   public static final float log(float a) {
/*  3080 */     return (float)Math.log(a);
/*       */   }
/*       */   
/*       */   public static final float exp(float a) {
/*  3084 */     return (float)Math.exp(a);
/*       */   }
/*       */   
/*       */   public static final float pow(float a, float b) {
/*  3088 */     return (float)Math.pow(a, b);
/*       */   }
/*       */   
/*       */   public static final int max(int a, int b)
/*       */   {
/*  3093 */     return a > b ? a : b;
/*       */   }
/*       */   
/*       */   public static final float max(float a, float b) {
/*  3097 */     return a > b ? a : b;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int max(int a, int b, int c)
/*       */   {
/*  3108 */     return b > c ? b : a > b ? c : a > c ? a : c;
/*       */   }
/*       */   
/*       */   public static final float max(float a, float b, float c) {
/*  3112 */     return b > c ? b : a > b ? c : a > c ? a : c;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int max(int[] list)
/*       */   {
/*  3123 */     if (list.length == 0) {
/*  3124 */       throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
/*       */     }
/*  3126 */     int max = list[0];
/*  3127 */     for (int i = 1; i < list.length; i++) {
/*  3128 */       if (list[i] > max) max = list[i];
/*       */     }
/*  3130 */     return max;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float max(float[] list)
/*       */   {
/*  3140 */     if (list.length == 0) {
/*  3141 */       throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
/*       */     }
/*  3143 */     float max = list[0];
/*  3144 */     for (int i = 1; i < list.length; i++) {
/*  3145 */       if (list[i] > max) max = list[i];
/*       */     }
/*  3147 */     return max;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int min(int a, int b)
/*       */   {
/*  3172 */     return a < b ? a : b;
/*       */   }
/*       */   
/*       */   public static final float min(float a, float b) {
/*  3176 */     return a < b ? a : b;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int min(int a, int b, int c)
/*       */   {
/*  3187 */     return b < c ? b : a < b ? c : a < c ? a : c;
/*       */   }
/*       */   
/*       */   public static final float min(float a, float b, float c) {
/*  3191 */     return b < c ? b : a < b ? c : a < c ? a : c;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int min(int[] list)
/*       */   {
/*  3208 */     if (list.length == 0) {
/*  3209 */       throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
/*       */     }
/*  3211 */     int min = list[0];
/*  3212 */     for (int i = 1; i < list.length; i++) {
/*  3213 */       if (list[i] < min) min = list[i];
/*       */     }
/*  3215 */     return min;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float min(float[] list)
/*       */   {
/*  3226 */     if (list.length == 0) {
/*  3227 */       throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
/*       */     }
/*  3229 */     float min = list[0];
/*  3230 */     for (int i = 1; i < list.length; i++) {
/*  3231 */       if (list[i] < min) min = list[i];
/*       */     }
/*  3233 */     return min;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int constrain(int amt, int low, int high)
/*       */   {
/*  3257 */     return amt > high ? high : amt < low ? low : amt;
/*       */   }
/*       */   
/*       */   public static final float constrain(float amt, float low, float high) {
/*  3261 */     return amt > high ? high : amt < low ? low : amt;
/*       */   }
/*       */   
/*       */   public static final float sin(float angle)
/*       */   {
/*  3266 */     return (float)Math.sin(angle);
/*       */   }
/*       */   
/*       */   public static final float cos(float angle) {
/*  3270 */     return (float)Math.cos(angle);
/*       */   }
/*       */   
/*       */   public static final float tan(float angle) {
/*  3274 */     return (float)Math.tan(angle);
/*       */   }
/*       */   
/*       */   public static final float asin(float value)
/*       */   {
/*  3279 */     return (float)Math.asin(value);
/*       */   }
/*       */   
/*       */   public static final float acos(float value) {
/*  3283 */     return (float)Math.acos(value);
/*       */   }
/*       */   
/*       */   public static final float atan(float value) {
/*  3287 */     return (float)Math.atan(value);
/*       */   }
/*       */   
/*       */   public static final float atan2(float a, float b) {
/*  3291 */     return (float)Math.atan2(a, b);
/*       */   }
/*       */   
/*       */   public static final float degrees(float radians)
/*       */   {
/*  3296 */     return radians * 57.295776F;
/*       */   }
/*       */   
/*       */   public static final float radians(float degrees) {
/*  3300 */     return degrees * 0.017453292F;
/*       */   }
/*       */   
/*       */   public static final int ceil(float what)
/*       */   {
/*  3305 */     return (int)Math.ceil(what);
/*       */   }
/*       */   
/*       */   public static final int floor(float what) {
/*  3309 */     return (int)Math.floor(what);
/*       */   }
/*       */   
/*       */   public static final int round(float what) {
/*  3313 */     return Math.round(what);
/*       */   }
/*       */   
/*       */   public static final float mag(float a, float b)
/*       */   {
/*  3318 */     return (float)Math.sqrt(a * a + b * b);
/*       */   }
/*       */   
/*       */   public static final float mag(float a, float b, float c) {
/*  3322 */     return (float)Math.sqrt(a * a + b * b + c * c);
/*       */   }
/*       */   
/*       */   public static final float dist(float x1, float y1, float x2, float y2)
/*       */   {
/*  3327 */     return sqrt(sq(x2 - x1) + sq(y2 - y1));
/*       */   }
/*       */   
/*       */   public static final float dist(float x1, float y1, float z1, float x2, float y2, float z2)
/*       */   {
/*  3332 */     return sqrt(sq(x2 - x1) + sq(y2 - y1) + sq(z2 - z1));
/*       */   }
/*       */   
/*       */   public static final float lerp(float start, float stop, float amt)
/*       */   {
/*  3337 */     return start + (stop - start) * amt;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float norm(float value, float start, float stop)
/*       */   {
/*  3346 */     return (value - start) / (stop - start);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float map(float value, float istart, float istop, float ostart, float ostop)
/*       */   {
/*  3356 */     return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float random(float howbig)
/*       */   {
/*  3389 */     if (howbig == 0.0F) { return 0.0F;
/*       */     }
/*       */     
/*  3392 */     if (this.internalRandom == null) { this.internalRandom = new Random();
/*       */     }
/*  3394 */     float value = 0.0F;
/*       */     do
/*       */     {
/*  3397 */       value = this.internalRandom.nextFloat() * howbig;
/*  3398 */     } while (value == howbig);
/*  3399 */     return value;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float random(float howsmall, float howbig)
/*       */   {
/*  3414 */     if (howsmall >= howbig) return howsmall;
/*  3415 */     float diff = howbig - howsmall;
/*  3416 */     return random(diff) + howsmall;
/*       */   }
/*       */   
/*       */ 
/*       */   public final void randomSeed(long what)
/*       */   {
/*  3422 */     if (this.internalRandom == null) this.internalRandom = new Random();
/*  3423 */     this.internalRandom.setSeed(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3449 */   int perlin_octaves = 4;
/*  3450 */   float perlin_amp_falloff = 0.5F;
/*       */   
/*       */   int perlin_TWOPI;
/*       */   
/*       */   int perlin_PI;
/*       */   
/*       */   float[] perlin_cosTable;
/*       */   
/*       */   float[] perlin;
/*       */   
/*       */   Random perlinRandom;
/*       */   
/*       */   protected String[] loadImageFormats;
/*       */   
/*       */   public float noise(float x)
/*       */   {
/*  3466 */     return noise(x, 0.0F, 0.0F);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public float noise(float x, float y)
/*       */   {
/*  3473 */     return noise(x, y, 0.0F);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public float noise(float x, float y, float z)
/*       */   {
/*  3480 */     if (this.perlin == null) {
/*  3481 */       if (this.perlinRandom == null) {
/*  3482 */         this.perlinRandom = new Random();
/*       */       }
/*  3484 */       this.perlin = new float['က'];
/*  3485 */       for (int i = 0; i < 4096; i++) {
/*  3486 */         this.perlin[i] = this.perlinRandom.nextFloat();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  3491 */       this.perlin_cosTable = PGraphics.cosLUT;
/*  3492 */       this.perlin_TWOPI = (this.perlin_PI = 'ː');
/*  3493 */       this.perlin_PI >>= 1;
/*       */     }
/*       */     
/*  3496 */     if (x < 0.0F) x = -x;
/*  3497 */     if (y < 0.0F) y = -y;
/*  3498 */     if (z < 0.0F) { z = -z;
/*       */     }
/*  3500 */     int xi = (int)x;int yi = (int)y;int zi = (int)z;
/*  3501 */     float xf = x - xi;
/*  3502 */     float yf = y - yi;
/*  3503 */     float zf = z - zi;
/*       */     
/*       */ 
/*  3506 */     float r = 0.0F;
/*  3507 */     float ampl = 0.5F;
/*       */     
/*       */ 
/*       */ 
/*  3511 */     for (int i = 0; i < this.perlin_octaves; i++) {
/*  3512 */       int of = xi + (yi << 4) + (zi << 8);
/*       */       
/*  3514 */       float rxf = noise_fsc(xf);
/*  3515 */       float ryf = noise_fsc(yf);
/*       */       
/*  3517 */       float n1 = this.perlin[(of & 0xFFF)];
/*  3518 */       n1 += rxf * (this.perlin[(of + 1 & 0xFFF)] - n1);
/*  3519 */       float n2 = this.perlin[(of + 16 & 0xFFF)];
/*  3520 */       n2 += rxf * (this.perlin[(of + 16 + 1 & 0xFFF)] - n2);
/*  3521 */       n1 += ryf * (n2 - n1);
/*       */       
/*  3523 */       of += 256;
/*  3524 */       n2 = this.perlin[(of & 0xFFF)];
/*  3525 */       n2 += rxf * (this.perlin[(of + 1 & 0xFFF)] - n2);
/*  3526 */       float n3 = this.perlin[(of + 16 & 0xFFF)];
/*  3527 */       n3 += rxf * (this.perlin[(of + 16 + 1 & 0xFFF)] - n3);
/*  3528 */       n2 += ryf * (n3 - n2);
/*       */       
/*  3530 */       n1 += noise_fsc(zf) * (n2 - n1);
/*       */       
/*  3532 */       r += n1 * ampl;
/*  3533 */       ampl *= this.perlin_amp_falloff;
/*  3534 */       xi <<= 1;xf *= 2.0F;
/*  3535 */       yi <<= 1;yf *= 2.0F;
/*  3536 */       zi <<= 1;zf *= 2.0F;
/*       */       
/*  3538 */       if (xf >= 1.0F) { xi++;xf -= 1.0F; }
/*  3539 */       if (yf >= 1.0F) { yi++;yf -= 1.0F; }
/*  3540 */       if (zf >= 1.0F) { zi++;zf -= 1.0F;
/*       */       } }
/*  3542 */     return r;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   private float noise_fsc(float i)
/*       */   {
/*  3550 */     return 0.5F * (1.0F - this.perlin_cosTable[((int)(i * this.perlin_PI) % this.perlin_TWOPI)]);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noiseDetail(int lod)
/*       */   {
/*  3559 */     if (lod > 0) this.perlin_octaves = lod;
/*       */   }
/*       */   
/*       */   public void noiseDetail(int lod, float falloff) {
/*  3563 */     if (lod > 0) this.perlin_octaves = lod;
/*  3564 */     if (falloff > 0.0F) this.perlin_amp_falloff = falloff;
/*       */   }
/*       */   
/*       */   public void noiseSeed(long what) {
/*  3568 */     if (this.perlinRandom == null) this.perlinRandom = new Random();
/*  3569 */     this.perlinRandom.setSeed(what);
/*       */     
/*  3571 */     this.perlin = null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage loadImage(String filename)
/*       */   {
/*  3637 */     return loadImage(filename, null, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage loadImage(String filename, String extension)
/*       */   {
/*  3645 */     return loadImage(filename, extension, null);
/*       */   }
/*       */   
/*       */   public PImage loadImage(String filename, Object params)
/*       */   {
/*  3650 */     return loadImage(filename, null, params);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage loadImage(String filename, String extension, Object params)
/*       */   {
/*  3679 */     if (extension == null) {
/*  3680 */       String lower = filename.toLowerCase();
/*  3681 */       int dot = filename.lastIndexOf('.');
/*  3682 */       if (dot == -1) {
/*  3683 */         extension = "unknown";
/*       */       }
/*  3685 */       extension = lower.substring(dot + 1);
/*       */       
/*       */ 
/*       */ 
/*  3689 */       int question = extension.indexOf('?');
/*  3690 */       if (question != -1) {
/*  3691 */         extension = extension.substring(0, question);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  3696 */     extension = extension.toLowerCase();
/*       */     
/*  3698 */     if (extension.equals("tga")) {
/*       */       try {
/*  3700 */         PImage image = loadImageTGA(filename);
/*  3701 */         if (params != null) {
/*  3702 */           image.setParams(this.g, params);
/*       */         }
/*  3704 */         return image;
/*       */       } catch (IOException e) {
/*  3706 */         e.printStackTrace();
/*  3707 */         return null;
/*       */       }
/*       */     }
/*       */     
/*  3711 */     if ((extension.equals("tif")) || (extension.equals("tiff"))) {
/*  3712 */       byte[] bytes = loadBytes(filename);
/*  3713 */       PImage image = bytes == null ? null : PImage.loadTIFF(bytes);
/*  3714 */       if (params != null) {
/*  3715 */         image.setParams(this.g, params);
/*       */       }
/*  3717 */       return image;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  3724 */       if ((extension.equals("jpg")) || (extension.equals("jpeg")) || 
/*  3725 */         (extension.equals("gif")) || (extension.equals("png")) || 
/*  3726 */         (extension.equals("unknown"))) {
/*  3727 */         byte[] bytes = loadBytes(filename);
/*  3728 */         if (bytes == null) {
/*  3729 */           return null;
/*       */         }
/*  3731 */         Image awtImage = Toolkit.getDefaultToolkit().createImage(bytes);
/*  3732 */         PImage image = loadImageMT(awtImage);
/*  3733 */         if (image.width == -1) {
/*  3734 */           System.err.println("The file " + filename + 
/*  3735 */             " contains bad image data, or may not be an image.");
/*       */         }
/*       */         
/*  3738 */         if ((extension.equals("gif")) || (extension.equals("png"))) {
/*  3739 */           image.checkAlpha();
/*       */         }
/*       */         
/*  3742 */         if (params != null) {
/*  3743 */           image.setParams(this.g, params);
/*       */         }
/*  3745 */         return image;
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  3750 */       e.printStackTrace();
/*       */       
/*       */ 
/*  3753 */       if (this.loadImageFormats == null) {
/*  3754 */         this.loadImageFormats = ImageIO.getReaderFormatNames();
/*       */       }
/*  3756 */       if (this.loadImageFormats != null) {
/*  3757 */         for (int i = 0; i < this.loadImageFormats.length; i++) {
/*  3758 */           if (extension.equals(this.loadImageFormats[i]))
/*       */           {
/*  3760 */             PImage image = loadImageIO(filename);
/*  3761 */             if (params != null) {
/*  3762 */               image.setParams(this.g, params);
/*       */             }
/*  3764 */             return image;
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  3770 */       System.err.println("Could not find a method to load " + filename); }
/*  3771 */     return null;
/*       */   }
/*       */   
/*       */   public PImage requestImage(String filename) {
/*  3775 */     return requestImage(filename, null, null);
/*       */   }
/*       */   
/*       */   public PImage requestImage(String filename, String extension)
/*       */   {
/*  3780 */     return requestImage(filename, extension, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage requestImage(String filename, String extension, Object params)
/*       */   {
/*  3796 */     PImage vessel = createImage(0, 0, 2, params);
/*  3797 */     AsyncImageLoader ail = 
/*  3798 */       new AsyncImageLoader(filename, extension, vessel);
/*  3799 */     ail.start();
/*  3800 */     return vessel;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3813 */   public int requestImageMax = 4;
/*       */   volatile int requestImageCount;
/*       */   protected String[] loadShapeFormats;
/*       */   public File selectedFile;
/*       */   protected Frame parentFrame;
/*       */   protected static HashMap<String, Pattern> matchPatterns;
/*       */   private static NumberFormat int_nf;
/*       */   
/*       */   class AsyncImageLoader extends Thread {
/*  3822 */     public AsyncImageLoader(String filename, String extension, PImage vessel) { this.filename = filename;
/*  3823 */       this.extension = extension;
/*  3824 */       this.vessel = vessel;
/*       */     }
/*       */     
/*       */     public void run() {
/*  3828 */       while (PApplet.this.requestImageCount == PApplet.this.requestImageMax) {
/*       */         try {
/*  3830 */           Thread.sleep(10L);
/*       */         } catch (InterruptedException localInterruptedException) {}
/*       */       }
/*  3833 */       PApplet.this.requestImageCount += 1;
/*       */       
/*  3835 */       PImage actual = PApplet.this.loadImage(this.filename, this.extension);
/*       */       
/*       */ 
/*  3838 */       if (actual == null) {
/*  3839 */         this.vessel.width = -1;
/*  3840 */         this.vessel.height = -1;
/*       */       }
/*       */       else {
/*  3843 */         this.vessel.width = actual.width;
/*  3844 */         this.vessel.height = actual.height;
/*  3845 */         this.vessel.format = actual.format;
/*  3846 */         this.vessel.pixels = actual.pixels;
/*       */       }
/*  3848 */       PApplet.this.requestImageCount -= 1;
/*       */     }
/*       */     
/*       */     String filename;
/*       */     String extension;
/*       */     PImage vessel;
/*       */   }
/*       */   
/*       */   protected PImage loadImageMT(Image awtImage)
/*       */   {
/*  3858 */     MediaTracker tracker = new MediaTracker(this);
/*  3859 */     tracker.addImage(awtImage, 0);
/*       */     try {
/*  3861 */       tracker.waitForAll();
/*       */     }
/*       */     catch (InterruptedException localInterruptedException) {}
/*       */     
/*       */ 
/*  3866 */     PImage image = new PImage(awtImage);
/*  3867 */     image.parent = this;
/*  3868 */     return image;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   protected PImage loadImageIO(String filename)
/*       */   {
/*  3876 */     InputStream stream = createInput(filename);
/*  3877 */     if (stream == null) {
/*  3878 */       System.err.println("The image " + filename + " could not be found.");
/*  3879 */       return null;
/*       */     }
/*       */     try
/*       */     {
/*  3883 */       BufferedImage bi = ImageIO.read(stream);
/*  3884 */       PImage outgoing = new PImage(bi.getWidth(), bi.getHeight());
/*  3885 */       outgoing.parent = this;
/*       */       
/*  3887 */       bi.getRGB(0, 0, outgoing.width, outgoing.height, 
/*  3888 */         outgoing.pixels, 0, outgoing.width);
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3897 */       outgoing.checkAlpha();
/*       */       
/*       */ 
/*  3900 */       return outgoing;
/*       */     }
/*       */     catch (Exception e) {
/*  3903 */       e.printStackTrace(); }
/*  3904 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected PImage loadImageTGA(String filename)
/*       */     throws IOException
/*       */   {
/*  3917 */     InputStream is = createInput(filename);
/*  3918 */     if (is == null) { return null;
/*       */     }
/*  3920 */     byte[] header = new byte[18];
/*  3921 */     int offset = 0;
/*       */     do {
/*  3923 */       int count = is.read(header, offset, header.length - offset);
/*  3924 */       if (count == -1) return null;
/*  3925 */       offset += count;
/*  3926 */     } while (offset < 18);
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3945 */     int format = 0;
/*       */     
/*  3947 */     if (((header[2] == 3) || (header[2] == 11)) && 
/*  3948 */       (header[16] == 8) && (
/*  3949 */       (header[17] == 8) || (header[17] == 40))) {
/*  3950 */       format = 4;
/*       */     }
/*  3952 */     else if (((header[2] == 2) || (header[2] == 10)) && 
/*  3953 */       (header[16] == 24) && (
/*  3954 */       (header[17] == 32) || (header[17] == 0))) {
/*  3955 */       format = 1;
/*       */     }
/*  3957 */     else if (((header[2] == 2) || (header[2] == 10)) && 
/*  3958 */       (header[16] == 32) && (
/*  3959 */       (header[17] == 8) || (header[17] == 40))) {
/*  3960 */       format = 2;
/*       */     }
/*       */     
/*  3963 */     if (format == 0) {
/*  3964 */       System.err.println("Unknown .tga file format for " + filename);
/*       */       
/*       */ 
/*       */ 
/*  3968 */       return null;
/*       */     }
/*       */     
/*  3971 */     int w = ((header[13] & 0xFF) << 8) + (header[12] & 0xFF);
/*  3972 */     int h = ((header[15] & 0xFF) << 8) + (header[14] & 0xFF);
/*  3973 */     PImage outgoing = createImage(w, h, format);
/*       */     
/*       */ 
/*       */ 
/*  3977 */     boolean reversed = (header[17] & 0x20) != 0;
/*       */     int count;
/*  3979 */     if ((header[2] == 2) || (header[2] == 3)) {
/*  3980 */       if (reversed) {
/*  3981 */         int index = (h - 1) * w;
/*  3982 */         switch (format) {
/*       */         case 4: 
/*  3984 */           for (int y = h - 1; y >= 0; y--) {
/*  3985 */             for (int x = 0; x < w; x++) {
/*  3986 */               outgoing.pixels[(index + x)] = is.read();
/*       */             }
/*  3988 */             index -= w;
/*       */           }
/*  3990 */           break;
/*       */         case 1: 
/*  3992 */           for (int y = h - 1; y >= 0; y--) {
/*  3993 */             for (int x = 0; x < w; x++) {
/*  3994 */               outgoing.pixels[(index + x)] = 
/*  3995 */                 (is.read() | is.read() << 8 | is.read() << 16 | 
/*  3996 */                 0xFF000000);
/*       */             }
/*  3998 */             index -= w;
/*       */           }
/*  4000 */           break;
/*       */         case 2: 
/*  4002 */           for (int y = h - 1; y >= 0; y--) {
/*  4003 */             for (int x = 0; x < w; x++) {
/*  4004 */               outgoing.pixels[(index + x)] = 
/*  4005 */                 (is.read() | is.read() << 8 | is.read() << 16 | 
/*  4006 */                 is.read() << 24);
/*       */             }
/*  4008 */             index -= w;
/*       */           }
/*       */         }
/*       */       } else {
/*  4012 */         count = w * h;
/*  4013 */       } } else switch (format) {
/*       */       case 4: 
/*  4015 */         for (int i = 0; i < count; i++) {
/*  4016 */           outgoing.pixels[i] = is.read();
/*       */         }
/*  4018 */         break;
/*       */       case 1: 
/*  4020 */         for (int i = 0; i < count; i++) {
/*  4021 */           outgoing.pixels[i] = 
/*  4022 */             (is.read() | is.read() << 8 | is.read() << 16 | 
/*  4023 */             0xFF000000);
/*       */         }
/*  4025 */         break;
/*       */       case 2: 
/*  4027 */         for (int i = 0; i < count; i++) {
/*  4028 */           outgoing.pixels[i] = 
/*  4029 */             (is.read() | is.read() << 8 | is.read() << 16 | 
/*  4030 */             is.read() << 24);
/*       */         }
/*       */       
/*       */ 
/*       */ 
/*       */       case 3: 
/*       */       default: 
/*  4037 */         break;int index = 0;
/*  4038 */         int[] px = outgoing.pixels;
/*       */         
/*  4040 */         while (index < px.length) {
/*  4041 */           int num = is.read();
/*  4042 */           boolean isRLE = (num & 0x80) != 0;
/*  4043 */           if (isRLE) {
/*  4044 */             num -= 127;
/*  4045 */             int pixel = 0;
/*  4046 */             switch (format) {
/*       */             case 4: 
/*  4048 */               pixel = is.read();
/*  4049 */               break;
/*       */             case 1: 
/*  4051 */               pixel = 0xFF000000 | 
/*  4052 */                 is.read() | is.read() << 8 | is.read() << 16;
/*       */               
/*  4054 */               break;
/*       */             case 2: 
/*  4056 */               pixel = is.read() | 
/*  4057 */                 is.read() << 8 | is.read() << 16 | is.read() << 24;
/*       */             }
/*       */             
/*  4060 */             for (int i = 0; i < num; i++) {
/*  4061 */               px[(index++)] = pixel;
/*  4062 */               if (index == px.length)
/*       */                 break;
/*       */             }
/*  4065 */           } else { num++;
/*  4066 */             switch (format) {
/*       */             case 4: 
/*  4068 */               for (int i = 0; i < num; i++) {
/*  4069 */                 px[(index++)] = is.read();
/*       */               }
/*  4071 */               break;
/*       */             case 1: 
/*  4073 */               for (int i = 0; i < num; i++) {
/*  4074 */                 px[(index++)] = 
/*  4075 */                   (0xFF000000 | is.read() | is.read() << 8 | is.read() << 16);
/*       */               }
/*       */               
/*  4078 */               break;
/*       */             case 2: 
/*  4080 */               for (int i = 0; i < num; i++) {
/*  4081 */                 px[(index++)] = 
/*  4082 */                   (is.read() | is.read() << 8 | is.read() << 16 | is.read() << 24);
/*       */               }
/*       */             }
/*       */             
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  4090 */         if (!reversed) {
/*  4091 */           int[] temp = new int[w];
/*  4092 */           for (int y = 0; y < h / 2; y++) {
/*  4093 */             int z = h - 1 - y;
/*  4094 */             System.arraycopy(px, y * w, temp, 0, w);
/*  4095 */             System.arraycopy(px, z * w, px, y * w, w);
/*  4096 */             System.arraycopy(temp, 0, px, z * w, w);
/*       */           }
/*       */         }
/*       */         break;
/*       */       }
/*  4101 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PShape loadShape(String filename)
/*       */   {
/*  4114 */     return loadShape(filename, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PShape loadShape(String filename, Object params)
/*       */   {
/*  4138 */     String lower = filename.toLowerCase();
/*  4139 */     int dot = filename.lastIndexOf('.');
/*  4140 */     if (dot == -1) {
/*  4141 */       String str1 = "unknown";
/*       */     }
/*  4143 */     String extension = lower.substring(dot + 1);
/*       */     
/*       */ 
/*       */ 
/*  4147 */     int question = extension.indexOf('?');
/*  4148 */     if (question != -1) {
/*  4149 */       extension = extension.substring(0, question);
/*       */     }
/*       */     
/*  4152 */     if (extension.equals("svg")) {
/*  4153 */       return new PShapeSVG(this, filename);
/*       */     }
/*  4155 */     if (extension.equals("svgz")) {
/*       */       try {
/*  4157 */         InputStream input = new GZIPInputStream(createInput(filename));
/*  4158 */         XMLElement xml = new XMLElement(createReader(input));
/*  4159 */         return new PShapeSVG(xml);
/*       */       } catch (IOException e) {
/*  4161 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */     else
/*       */     {
/*  4166 */       this.loadShapeFormats = this.g.getSupportedShapeFormats();
/*       */       
/*  4168 */       if (this.loadShapeFormats != null) {
/*  4169 */         for (int i = 0; i < this.loadShapeFormats.length; i++) {
/*  4170 */           if (extension.equals(this.loadShapeFormats[i])) {
/*  4171 */             return this.g.loadShape(filename, params);
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  4178 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PShape createShape(int size, Object params)
/*       */   {
/*  4187 */     return this.g.createShape(size, params);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PFont loadFont(String filename)
/*       */   {
/*       */     try
/*       */     {
/*  4198 */       InputStream input = createInput(filename);
/*  4199 */       return new PFont(input);
/*       */     }
/*       */     catch (Exception e) {
/*  4202 */       die("Could not load font " + filename + ". " + 
/*  4203 */         "Make sure that the font has been copied " + 
/*  4204 */         "to the data folder of your sketch.", e);
/*       */     }
/*  4206 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected PFont createDefaultFont(float size)
/*       */   {
/*  4218 */     return createFont("Lucida Sans", size, true, null);
/*       */   }
/*       */   
/*       */   public PFont createFont(String name, float size)
/*       */   {
/*  4223 */     return createFont(name, size, true, null);
/*       */   }
/*       */   
/*       */   public PFont createFont(String name, float size, boolean smooth)
/*       */   {
/*  4228 */     return createFont(name, size, smooth, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PFont createFont(String name, float size, boolean smooth, char[] charset)
/*       */   {
/*  4253 */     String lowerName = name.toLowerCase();
/*  4254 */     Font baseFont = null;
/*       */     try
/*       */     {
/*  4257 */       InputStream stream = null;
/*  4258 */       if ((lowerName.endsWith(".otf")) || (lowerName.endsWith(".ttf"))) {
/*  4259 */         stream = createInput(name);
/*  4260 */         if (stream == null) {
/*  4261 */           System.err.println("The font \"" + name + "\" " + 
/*  4262 */             "is missing or inaccessible, make sure " + 
/*  4263 */             "the URL is valid or that the file has been " + 
/*  4264 */             "added to your sketch and is readable.");
/*  4265 */           return null;
/*       */         }
/*  4267 */         baseFont = Font.createFont(0, createInput(name));
/*       */       }
/*       */       else {
/*  4270 */         baseFont = PFont.findFont(name);
/*       */       }
/*  4272 */       return new PFont(baseFont.deriveFont(size), smooth, charset, 
/*  4273 */         stream != null);
/*       */     }
/*       */     catch (Exception e) {
/*  4276 */       System.err.println("Problem createFont(" + name + ")");
/*  4277 */       e.printStackTrace(); }
/*  4278 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void checkParentFrame()
/*       */   {
/*  4294 */     if (this.parentFrame == null) {
/*  4295 */       Component comp = getParent();
/*  4296 */       while (comp != null) {
/*  4297 */         if ((comp instanceof Frame)) {
/*  4298 */           this.parentFrame = ((Frame)comp);
/*  4299 */           break;
/*       */         }
/*  4301 */         comp = comp.getParent();
/*       */       }
/*       */       
/*  4304 */       if (this.parentFrame == null) {
/*  4305 */         this.parentFrame = new Frame();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectInput()
/*       */   {
/*  4316 */     return selectInput("Select a file...");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectInput(String prompt)
/*       */   {
/*  4331 */     return selectFileImpl(prompt, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectOutput()
/*       */   {
/*  4340 */     return selectOutput("Save as...");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectOutput(String prompt)
/*       */   {
/*  4358 */     return selectFileImpl(prompt, 1);
/*       */   }
/*       */   
/*       */   protected String selectFileImpl(final String prompt, final int mode)
/*       */   {
/*  4363 */     checkParentFrame();
/*       */     try
/*       */     {
/*  4366 */       SwingUtilities.invokeAndWait(new Runnable() {
/*       */         public void run() {
/*  4368 */           FileDialog fileDialog = 
/*  4369 */             new FileDialog(PApplet.this.parentFrame, prompt, mode);
/*  4370 */           fileDialog.setVisible(true);
/*  4371 */           String directory = fileDialog.getDirectory();
/*  4372 */           String filename = fileDialog.getFile();
/*  4373 */           PApplet.this.selectedFile = 
/*  4374 */             (filename == null ? null : new File(directory, filename));
/*       */         }
/*  4376 */       });
/*  4377 */       return this.selectedFile == null ? null : this.selectedFile.getAbsolutePath();
/*       */     }
/*       */     catch (Exception e) {
/*  4380 */       e.printStackTrace(); }
/*  4381 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */   public String selectFolder()
/*       */   {
/*  4387 */     return selectFolder("Select a folder...");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectFolder(final String prompt)
/*       */   {
/*  4403 */     checkParentFrame();
/*       */     try
/*       */     {
/*  4406 */       SwingUtilities.invokeAndWait(new Runnable() {
/*       */         public void run() {
/*  4408 */           if (PApplet.platform == 2) {
/*  4409 */             FileDialog fileDialog = 
/*  4410 */               new FileDialog(PApplet.this.parentFrame, prompt, 0);
/*  4411 */             System.setProperty("apple.awt.fileDialogForDirectories", "true");
/*  4412 */             fileDialog.setVisible(true);
/*  4413 */             System.setProperty("apple.awt.fileDialogForDirectories", "false");
/*  4414 */             String filename = fileDialog.getFile();
/*  4415 */             PApplet.this.selectedFile = (filename == null ? null : 
/*  4416 */               new File(fileDialog.getDirectory(), fileDialog.getFile()));
/*       */           } else {
/*  4418 */             JFileChooser fileChooser = new JFileChooser();
/*  4419 */             fileChooser.setDialogTitle(prompt);
/*  4420 */             fileChooser.setFileSelectionMode(1);
/*       */             
/*  4422 */             int returned = fileChooser.showOpenDialog(PApplet.this.parentFrame);
/*  4423 */             System.out.println(returned);
/*  4424 */             if (returned == 1) {
/*  4425 */               PApplet.this.selectedFile = null;
/*       */             } else {
/*  4427 */               PApplet.this.selectedFile = fileChooser.getSelectedFile();
/*       */             }
/*       */           }
/*       */         }
/*  4431 */       });
/*  4432 */       return this.selectedFile == null ? null : this.selectedFile.getAbsolutePath();
/*       */     }
/*       */     catch (Exception e) {
/*  4435 */       e.printStackTrace(); }
/*  4436 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public BufferedReader createReader(String filename)
/*       */   {
/*       */     try
/*       */     {
/*  4453 */       InputStream is = createInput(filename);
/*  4454 */       if (is == null) {
/*  4455 */         System.err.println(filename + " does not exist or could not be read");
/*  4456 */         return null;
/*       */       }
/*  4458 */       return createReader(is);
/*       */     }
/*       */     catch (Exception e) {
/*  4461 */       if (filename == null) {
/*  4462 */         System.err.println("Filename passed to reader() was null");
/*       */       } else {
/*  4464 */         System.err.println("Couldn't create a reader for " + filename);
/*       */       }
/*       */     }
/*  4467 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static BufferedReader createReader(File file)
/*       */   {
/*       */     try
/*       */     {
/*  4476 */       InputStream is = new FileInputStream(file);
/*  4477 */       if (file.getName().toLowerCase().endsWith(".gz")) {
/*  4478 */         is = new GZIPInputStream(is);
/*       */       }
/*  4480 */       return createReader(is);
/*       */     }
/*       */     catch (Exception e) {
/*  4483 */       if (file == null) {
/*  4484 */         throw new RuntimeException("File passed to createReader() was null");
/*       */       }
/*  4486 */       e.printStackTrace();
/*  4487 */       throw new RuntimeException("Couldn't create a reader for " + 
/*  4488 */         file.getAbsolutePath());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static BufferedReader createReader(InputStream input)
/*       */   {
/*  4500 */     InputStreamReader isr = null;
/*       */     try {
/*  4502 */       isr = new InputStreamReader(input, "UTF-8");
/*       */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
/*  4504 */     return new BufferedReader(isr);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public PrintWriter createWriter(String filename)
/*       */   {
/*  4512 */     return createWriter(saveFile(filename));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static PrintWriter createWriter(File file)
/*       */   {
/*       */     try
/*       */     {
/*  4522 */       createPath(file);
/*  4523 */       OutputStream output = new FileOutputStream(file);
/*  4524 */       if (file.getName().toLowerCase().endsWith(".gz")) {
/*  4525 */         output = new GZIPOutputStream(output);
/*       */       }
/*  4527 */       return createWriter(output);
/*       */     }
/*       */     catch (Exception e) {
/*  4530 */       if (file == null) {
/*  4531 */         throw new RuntimeException("File passed to createWriter() was null");
/*       */       }
/*  4533 */       e.printStackTrace();
/*  4534 */       throw new RuntimeException("Couldn't create a writer for " + 
/*  4535 */         file.getAbsolutePath());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static PrintWriter createWriter(OutputStream output)
/*       */   {
/*       */     try
/*       */     {
/*  4548 */       BufferedOutputStream bos = new BufferedOutputStream(output, 8192);
/*  4549 */       OutputStreamWriter osw = new OutputStreamWriter(bos, "UTF-8");
/*  4550 */       return new PrintWriter(osw);
/*       */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
/*  4552 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*       */   public InputStream openStream(String filename)
/*       */   {
/*  4565 */     return createInput(filename);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static int int_nf_digits;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static boolean int_nf_commas;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static NumberFormat float_nf;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static int float_nf_left;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static int float_nf_right;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static boolean float_nf_commas;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public InputStream createInput(String filename)
/*       */   {
/*  4621 */     InputStream input = createInputRaw(filename);
/*  4622 */     if ((input != null) && (filename.toLowerCase().endsWith(".gz"))) {
/*       */       try {
/*  4624 */         return new GZIPInputStream(input);
/*       */       } catch (IOException e) {
/*  4626 */         e.printStackTrace();
/*  4627 */         return null;
/*       */       }
/*       */     }
/*  4630 */     return input;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public InputStream createInputRaw(String filename)
/*       */   {
/*  4638 */     InputStream stream = null;
/*       */     
/*  4640 */     if (filename == null) { return null;
/*       */     }
/*  4642 */     if (filename.length() == 0)
/*       */     {
/*       */ 
/*  4645 */       return null;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  4650 */     if (filename.indexOf(":") != -1) {
/*       */       try {
/*  4652 */         URL url = new URL(filename);
/*  4653 */         return url.openStream();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       }
/*       */       catch (MalformedURLException localMalformedURLException) {}catch (FileNotFoundException localFileNotFoundException) {}catch (IOException e)
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4665 */         e.printStackTrace();
/*       */         
/*  4667 */         return null;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  4678 */       File file = new File(dataPath(filename));
/*  4679 */       if (!file.exists())
/*       */       {
/*  4681 */         file = new File(this.sketchPath, filename);
/*       */       }
/*  4683 */       if (file.isDirectory()) {
/*  4684 */         return null;
/*       */       }
/*  4686 */       if (file.exists()) {
/*       */         try
/*       */         {
/*  4689 */           String filePath = file.getCanonicalPath();
/*  4690 */           String filenameActual = new File(filePath).getName();
/*       */           
/*  4692 */           String filenameShort = new File(filename).getName();
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  4697 */           if (!filenameActual.equals(filenameShort)) {
/*  4698 */             throw new RuntimeException("This file is named " + 
/*  4699 */               filenameActual + " not " + 
/*  4700 */               filename + ". Rename the file " + 
/*  4701 */               "or change your code.");
/*       */           }
/*       */         }
/*       */         catch (IOException localIOException1) {}
/*       */       }
/*       */       
/*  4707 */       stream = new FileInputStream(file);
/*  4708 */       if (stream != null) { return stream;
/*       */       }
/*       */     }
/*       */     catch (IOException localIOException2) {}catch (SecurityException localSecurityException) {}
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4719 */     ClassLoader cl = getClass().getClassLoader();
/*       */     
/*       */ 
/*       */ 
/*  4723 */     stream = cl.getResourceAsStream("data/" + filename);
/*  4724 */     if (stream != null) {
/*  4725 */       String cn = stream.getClass().getName();
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  4730 */       if (!cn.equals("sun.plugin.cache.EmptyInputStream")) {
/*  4731 */         return stream;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  4738 */     stream = cl.getResourceAsStream(filename);
/*  4739 */     if (stream != null) {
/*  4740 */       String cn = stream.getClass().getName();
/*  4741 */       if (!cn.equals("sun.plugin.cache.EmptyInputStream")) {
/*  4742 */         return stream;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  4751 */       URL base = getDocumentBase();
/*  4752 */       if (base != null) {
/*  4753 */         URL url = new URL(base, filename);
/*  4754 */         URLConnection conn = url.openConnection();
/*  4755 */         return conn.getInputStream();
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (Exception localException1)
/*       */     {
/*       */ 
/*       */       try
/*       */       {
/*       */ 
/*  4766 */         URL base = getDocumentBase();
/*  4767 */         if (base != null) {
/*  4768 */           URL url = new URL(base, "data/" + filename);
/*  4769 */           URLConnection conn = url.openConnection();
/*  4770 */           return conn.getInputStream();
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       catch (Exception localException2)
/*       */       {
/*       */         try
/*       */         {
/*  4779 */           stream = new FileInputStream(dataPath(filename));
/*  4780 */           if (stream != null) return stream;
/*       */         }
/*       */         catch (IOException localIOException3) {
/*       */           try {
/*  4784 */             stream = new FileInputStream(sketchPath(filename));
/*  4785 */             if (stream != null) return stream;
/*       */           }
/*       */           catch (Exception localException3) {
/*       */             try {
/*  4789 */               stream = new FileInputStream(filename);
/*  4790 */               if (stream != null) return stream;
/*       */             }
/*       */             catch (IOException localIOException4) {}
/*       */           }
/*       */         }
/*       */         catch (SecurityException localSecurityException1) {}catch (Exception e)
/*       */         {
/*  4797 */           e.printStackTrace();
/*       */         }
/*       */       } }
/*  4800 */     return null;
/*       */   }
/*       */   
/*       */   public static InputStream createInput(File file)
/*       */   {
/*  4805 */     if (file == null) {
/*  4806 */       throw new IllegalArgumentException("File passed to createInput() was null");
/*       */     }
/*       */     try {
/*  4809 */       InputStream input = new FileInputStream(file);
/*  4810 */       if (file.getName().toLowerCase().endsWith(".gz")) {
/*  4811 */         return new GZIPInputStream(input);
/*       */       }
/*  4813 */       return input;
/*       */     }
/*       */     catch (IOException e) {
/*  4816 */       System.err.println("Could not createInput() for " + file);
/*  4817 */       e.printStackTrace(); }
/*  4818 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public byte[] loadBytes(String filename)
/*       */   {
/*  4836 */     InputStream is = createInput(filename);
/*  4837 */     if (is != null) { return loadBytes(is);
/*       */     }
/*  4839 */     System.err.println("The file \"" + filename + "\" " + 
/*  4840 */       "is missing or inaccessible, make sure " + 
/*  4841 */       "the URL is valid or that the file has been " + 
/*  4842 */       "added to your sketch and is readable.");
/*  4843 */     return null;
/*       */   }
/*       */   
/*       */   public static byte[] loadBytes(InputStream input)
/*       */   {
/*       */     try {
/*  4849 */       BufferedInputStream bis = new BufferedInputStream(input);
/*  4850 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/*       */       
/*  4852 */       int c = bis.read();
/*  4853 */       while (c != -1) {
/*  4854 */         out.write(c);
/*  4855 */         c = bis.read();
/*       */       }
/*  4857 */       return out.toByteArray();
/*       */     }
/*       */     catch (IOException e) {
/*  4860 */       e.printStackTrace();
/*       */     }
/*       */     
/*  4863 */     return null;
/*       */   }
/*       */   
/*       */   public static byte[] loadBytes(File file)
/*       */   {
/*  4868 */     InputStream is = createInput(file);
/*  4869 */     return loadBytes(is);
/*       */   }
/*       */   
/*       */   public static String[] loadStrings(File file)
/*       */   {
/*  4874 */     InputStream is = createInput(file);
/*  4875 */     if (is != null) return loadStrings(is);
/*  4876 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String[] loadStrings(String filename)
/*       */   {
/*  4906 */     InputStream is = createInput(filename);
/*  4907 */     if (is != null) { return loadStrings(is);
/*       */     }
/*  4909 */     System.err.println("The file \"" + filename + "\" " + 
/*  4910 */       "is missing or inaccessible, make sure " + 
/*  4911 */       "the URL is valid or that the file has been " + 
/*  4912 */       "added to your sketch and is readable.");
/*  4913 */     return null;
/*       */   }
/*       */   
/*       */   public static String[] loadStrings(InputStream input)
/*       */   {
/*       */     try {
/*  4919 */       BufferedReader reader = 
/*  4920 */         new BufferedReader(new InputStreamReader(input, "UTF-8"));
/*       */       
/*  4922 */       String[] lines = new String[100];
/*  4923 */       int lineCount = 0;
/*  4924 */       String line = null;
/*  4925 */       while ((line = reader.readLine()) != null) {
/*  4926 */         if (lineCount == lines.length) {
/*  4927 */           String[] temp = new String[lineCount << 1];
/*  4928 */           System.arraycopy(lines, 0, temp, 0, lineCount);
/*  4929 */           lines = temp;
/*       */         }
/*  4931 */         lines[(lineCount++)] = line;
/*       */       }
/*  4933 */       reader.close();
/*       */       
/*  4935 */       if (lineCount == lines.length) {
/*  4936 */         return lines;
/*       */       }
/*       */       
/*       */ 
/*  4940 */       String[] output = new String[lineCount];
/*  4941 */       System.arraycopy(lines, 0, output, 0, lineCount);
/*  4942 */       return output;
/*       */     }
/*       */     catch (IOException e) {
/*  4945 */       e.printStackTrace();
/*       */     }
/*       */     
/*  4948 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public OutputStream createOutput(String filename)
/*       */   {
/*  4973 */     return createOutput(saveFile(filename));
/*       */   }
/*       */   
/*       */   public static OutputStream createOutput(File file)
/*       */   {
/*       */     try {
/*  4979 */       createPath(file);
/*  4980 */       FileOutputStream fos = new FileOutputStream(file);
/*  4981 */       if (file.getName().toLowerCase().endsWith(".gz")) {
/*  4982 */         return new GZIPOutputStream(fos);
/*       */       }
/*  4984 */       return fos;
/*       */     }
/*       */     catch (IOException e) {
/*  4987 */       e.printStackTrace();
/*       */     }
/*  4989 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean saveStream(String targetFilename, String sourceLocation)
/*       */   {
/*  4999 */     return saveStream(saveFile(targetFilename), sourceLocation);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean saveStream(File targetFile, String sourceLocation)
/*       */   {
/*  5011 */     return saveStream(targetFile, createInputRaw(sourceLocation));
/*       */   }
/*       */   
/*       */   public boolean saveStream(String targetFilename, InputStream sourceStream)
/*       */   {
/*  5016 */     return saveStream(saveFile(targetFilename), sourceStream);
/*       */   }
/*       */   
/*       */   public static boolean saveStream(File targetFile, InputStream sourceStream)
/*       */   {
/*  5021 */     File tempFile = null;
/*       */     try {
/*  5023 */       File parentDir = targetFile.getParentFile();
/*       */       
/*  5025 */       createPath(targetFile);
/*  5026 */       tempFile = File.createTempFile(targetFile.getName(), null, parentDir);
/*       */       
/*  5028 */       BufferedInputStream bis = new BufferedInputStream(sourceStream, 16384);
/*  5029 */       FileOutputStream fos = new FileOutputStream(tempFile);
/*  5030 */       BufferedOutputStream bos = new BufferedOutputStream(fos);
/*       */       
/*  5032 */       byte[] buffer = new byte[' '];
/*       */       int bytesRead;
/*  5034 */       while ((bytesRead = bis.read(buffer)) != -1) { int bytesRead;
/*  5035 */         bos.write(buffer, 0, bytesRead);
/*       */       }
/*       */       
/*  5038 */       bos.flush();
/*  5039 */       bos.close();
/*  5040 */       bos = null;
/*       */       
/*  5042 */       if (!tempFile.renameTo(targetFile)) {
/*  5043 */         System.err.println("Could not rename temporary file " + 
/*  5044 */           tempFile.getAbsolutePath());
/*  5045 */         return false;
/*       */       }
/*  5047 */       return true;
/*       */     }
/*       */     catch (IOException e) {
/*  5050 */       if (tempFile != null) {
/*  5051 */         tempFile.delete();
/*       */       }
/*  5053 */       e.printStackTrace(); }
/*  5054 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void saveBytes(String filename, byte[] buffer)
/*       */   {
/*  5067 */     saveBytes(saveFile(filename), buffer);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void saveBytes(File file, byte[] buffer)
/*       */   {
/*  5075 */     File tempFile = null;
/*       */     try {
/*  5077 */       File parentDir = file.getParentFile();
/*  5078 */       tempFile = File.createTempFile(file.getName(), null, parentDir);
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5088 */       OutputStream output = createOutput(tempFile);
/*  5089 */       saveBytes(output, buffer);
/*  5090 */       output.close();
/*  5091 */       output = null;
/*       */       
/*  5093 */       if (!tempFile.renameTo(file)) {
/*  5094 */         System.err.println("Could not rename temporary file " + 
/*  5095 */           tempFile.getAbsolutePath());
/*       */       }
/*       */     }
/*       */     catch (IOException e) {
/*  5099 */       System.err.println("error saving bytes to " + file);
/*  5100 */       if (tempFile != null) {
/*  5101 */         tempFile.delete();
/*       */       }
/*  5103 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static void saveBytes(OutputStream output, byte[] buffer)
/*       */   {
/*       */     try
/*       */     {
/*  5113 */       output.write(buffer);
/*  5114 */       output.flush();
/*       */     }
/*       */     catch (IOException e) {
/*  5117 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public void saveStrings(String filename, String[] strings)
/*       */   {
/*  5124 */     saveStrings(saveFile(filename), strings);
/*       */   }
/*       */   
/*       */   public static void saveStrings(File file, String[] strings)
/*       */   {
/*  5129 */     saveStrings(createOutput(file), strings);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void saveStrings(OutputStream output, String[] strings)
/*       */   {
/*  5149 */     PrintWriter writer = createWriter(output);
/*  5150 */     for (int i = 0; i < strings.length; i++) {
/*  5151 */       writer.println(strings[i]);
/*       */     }
/*  5153 */     writer.flush();
/*  5154 */     writer.close();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String sketchPath(String where)
/*       */   {
/*  5176 */     if (this.sketchPath == null) {
/*  5177 */       return where;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  5186 */       if (new File(where).isAbsolute()) return where;
/*       */     }
/*       */     catch (Exception localException) {}
/*  5189 */     return this.sketchPath + File.separator + where;
/*       */   }
/*       */   
/*       */   public File sketchFile(String where)
/*       */   {
/*  5194 */     return new File(sketchPath(where));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String savePath(String where)
/*       */   {
/*  5212 */     if (where == null) return null;
/*  5213 */     String filename = sketchPath(where);
/*  5214 */     createPath(filename);
/*  5215 */     return filename;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public File saveFile(String where)
/*       */   {
/*  5223 */     return new File(savePath(where));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String dataPath(String where)
/*       */   {
/*  5242 */     if (new File(where).isAbsolute()) { return where;
/*       */     }
/*  5244 */     return this.sketchPath + File.separator + "data" + File.separator + where;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public File dataFile(String where)
/*       */   {
/*  5253 */     return new File(dataPath(where));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void createPath(String path)
/*       */   {
/*  5263 */     createPath(new File(path));
/*       */   }
/*       */   
/*       */   public static void createPath(File file)
/*       */   {
/*       */     try {
/*  5269 */       String parent = file.getParent();
/*  5270 */       if (parent != null) {
/*  5271 */         File unit = new File(parent);
/*  5272 */         if (!unit.exists()) unit.mkdirs();
/*       */       }
/*       */     } catch (SecurityException se) {
/*  5275 */       System.err.println("You don't have permissions to create " + 
/*  5276 */         file.getAbsolutePath());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static byte[] sort(byte[] what)
/*       */   {
/*  5288 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static byte[] sort(byte[] what, int count)
/*       */   {
/*  5293 */     byte[] outgoing = new byte[what.length];
/*  5294 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5295 */     Arrays.sort(outgoing, 0, count);
/*  5296 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static char[] sort(char[] what)
/*       */   {
/*  5301 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static char[] sort(char[] what, int count)
/*       */   {
/*  5306 */     char[] outgoing = new char[what.length];
/*  5307 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5308 */     Arrays.sort(outgoing, 0, count);
/*  5309 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static int[] sort(int[] what)
/*       */   {
/*  5314 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static int[] sort(int[] what, int count)
/*       */   {
/*  5319 */     int[] outgoing = new int[what.length];
/*  5320 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5321 */     Arrays.sort(outgoing, 0, count);
/*  5322 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static float[] sort(float[] what)
/*       */   {
/*  5327 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static float[] sort(float[] what, int count)
/*       */   {
/*  5332 */     float[] outgoing = new float[what.length];
/*  5333 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5334 */     Arrays.sort(outgoing, 0, count);
/*  5335 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static String[] sort(String[] what)
/*       */   {
/*  5340 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static String[] sort(String[] what, int count)
/*       */   {
/*  5345 */     String[] outgoing = new String[what.length];
/*  5346 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5347 */     Arrays.sort(outgoing, 0, count);
/*  5348 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void arrayCopy(Object src, int srcPosition, Object dst, int dstPosition, int length)
/*       */   {
/*  5366 */     System.arraycopy(src, srcPosition, dst, dstPosition, length);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void arrayCopy(Object src, Object dst, int length)
/*       */   {
/*  5375 */     System.arraycopy(src, 0, dst, 0, length);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void arrayCopy(Object src, Object dst)
/*       */   {
/*  5385 */     System.arraycopy(src, 0, dst, 0, Array.getLength(src));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*       */   public static void arraycopy(Object src, int srcPosition, Object dst, int dstPosition, int length)
/*       */   {
/*  5396 */     System.arraycopy(src, srcPosition, dst, dstPosition, length);
/*       */   }
/*       */   
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*       */   public static void arraycopy(Object src, Object dst, int length) {
/*  5403 */     System.arraycopy(src, 0, dst, 0, length);
/*       */   }
/*       */   
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*       */   public static void arraycopy(Object src, Object dst) {
/*  5410 */     System.arraycopy(src, 0, dst, 0, Array.getLength(src));
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] expand(boolean[] list)
/*       */   {
/*  5416 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static boolean[] expand(boolean[] list, int newSize) {
/*  5420 */     boolean[] temp = new boolean[newSize];
/*  5421 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5422 */     return temp;
/*       */   }
/*       */   
/*       */   public static byte[] expand(byte[] list)
/*       */   {
/*  5427 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static byte[] expand(byte[] list, int newSize) {
/*  5431 */     byte[] temp = new byte[newSize];
/*  5432 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5433 */     return temp;
/*       */   }
/*       */   
/*       */   public static char[] expand(char[] list)
/*       */   {
/*  5438 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static char[] expand(char[] list, int newSize) {
/*  5442 */     char[] temp = new char[newSize];
/*  5443 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5444 */     return temp;
/*       */   }
/*       */   
/*       */   public static int[] expand(int[] list)
/*       */   {
/*  5449 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static int[] expand(int[] list, int newSize) {
/*  5453 */     int[] temp = new int[newSize];
/*  5454 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5455 */     return temp;
/*       */   }
/*       */   
/*       */   public static float[] expand(float[] list)
/*       */   {
/*  5460 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static float[] expand(float[] list, int newSize) {
/*  5464 */     float[] temp = new float[newSize];
/*  5465 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5466 */     return temp;
/*       */   }
/*       */   
/*       */   public static String[] expand(String[] list)
/*       */   {
/*  5471 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static String[] expand(String[] list, int newSize) {
/*  5475 */     String[] temp = new String[newSize];
/*       */     
/*  5477 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5478 */     return temp;
/*       */   }
/*       */   
/*       */   public static Object expand(Object array)
/*       */   {
/*  5483 */     return expand(array, Array.getLength(array) << 1);
/*       */   }
/*       */   
/*       */   public static Object expand(Object list, int newSize) {
/*  5487 */     Class<?> type = list.getClass().getComponentType();
/*  5488 */     Object temp = Array.newInstance(type, newSize);
/*  5489 */     System.arraycopy(list, 0, temp, 0, 
/*  5490 */       Math.min(Array.getLength(list), newSize));
/*  5491 */     return temp;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static byte[] append(byte[] b, byte value)
/*       */   {
/*  5502 */     b = expand(b, b.length + 1);
/*  5503 */     b[(b.length - 1)] = value;
/*  5504 */     return b;
/*       */   }
/*       */   
/*       */   public static char[] append(char[] b, char value) {
/*  5508 */     b = expand(b, b.length + 1);
/*  5509 */     b[(b.length - 1)] = value;
/*  5510 */     return b;
/*       */   }
/*       */   
/*       */   public static int[] append(int[] b, int value) {
/*  5514 */     b = expand(b, b.length + 1);
/*  5515 */     b[(b.length - 1)] = value;
/*  5516 */     return b;
/*       */   }
/*       */   
/*       */   public static float[] append(float[] b, float value) {
/*  5520 */     b = expand(b, b.length + 1);
/*  5521 */     b[(b.length - 1)] = value;
/*  5522 */     return b;
/*       */   }
/*       */   
/*       */   public static String[] append(String[] b, String value) {
/*  5526 */     b = expand(b, b.length + 1);
/*  5527 */     b[(b.length - 1)] = value;
/*  5528 */     return b;
/*       */   }
/*       */   
/*       */   public static Object append(Object b, Object value) {
/*  5532 */     int length = Array.getLength(b);
/*  5533 */     b = expand(b, length + 1);
/*  5534 */     Array.set(b, length, value);
/*  5535 */     return b;
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] shorten(boolean[] list)
/*       */   {
/*  5541 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static byte[] shorten(byte[] list) {
/*  5545 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static char[] shorten(char[] list) {
/*  5549 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static int[] shorten(int[] list) {
/*  5553 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static float[] shorten(float[] list) {
/*  5557 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static String[] shorten(String[] list) {
/*  5561 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static Object shorten(Object list) {
/*  5565 */     int length = Array.getLength(list);
/*  5566 */     return subset(list, 0, length - 1);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final boolean[] splice(boolean[] list, boolean v, int index)
/*       */   {
/*  5573 */     boolean[] outgoing = new boolean[list.length + 1];
/*  5574 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5575 */     outgoing[index] = v;
/*  5576 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5577 */       list.length - index);
/*  5578 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final boolean[] splice(boolean[] list, boolean[] v, int index)
/*       */   {
/*  5583 */     boolean[] outgoing = new boolean[list.length + v.length];
/*  5584 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5585 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5586 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5587 */       list.length - index);
/*  5588 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final byte[] splice(byte[] list, byte v, int index)
/*       */   {
/*  5594 */     byte[] outgoing = new byte[list.length + 1];
/*  5595 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5596 */     outgoing[index] = v;
/*  5597 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5598 */       list.length - index);
/*  5599 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final byte[] splice(byte[] list, byte[] v, int index)
/*       */   {
/*  5604 */     byte[] outgoing = new byte[list.length + v.length];
/*  5605 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5606 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5607 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5608 */       list.length - index);
/*  5609 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final char[] splice(char[] list, char v, int index)
/*       */   {
/*  5615 */     char[] outgoing = new char[list.length + 1];
/*  5616 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5617 */     outgoing[index] = v;
/*  5618 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5619 */       list.length - index);
/*  5620 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final char[] splice(char[] list, char[] v, int index)
/*       */   {
/*  5625 */     char[] outgoing = new char[list.length + v.length];
/*  5626 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5627 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5628 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5629 */       list.length - index);
/*  5630 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final int[] splice(int[] list, int v, int index)
/*       */   {
/*  5636 */     int[] outgoing = new int[list.length + 1];
/*  5637 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5638 */     outgoing[index] = v;
/*  5639 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5640 */       list.length - index);
/*  5641 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final int[] splice(int[] list, int[] v, int index)
/*       */   {
/*  5646 */     int[] outgoing = new int[list.length + v.length];
/*  5647 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5648 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5649 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5650 */       list.length - index);
/*  5651 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final float[] splice(float[] list, float v, int index)
/*       */   {
/*  5657 */     float[] outgoing = new float[list.length + 1];
/*  5658 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5659 */     outgoing[index] = v;
/*  5660 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5661 */       list.length - index);
/*  5662 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final float[] splice(float[] list, float[] v, int index)
/*       */   {
/*  5667 */     float[] outgoing = new float[list.length + v.length];
/*  5668 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5669 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5670 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5671 */       list.length - index);
/*  5672 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final String[] splice(String[] list, String v, int index)
/*       */   {
/*  5678 */     String[] outgoing = new String[list.length + 1];
/*  5679 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5680 */     outgoing[index] = v;
/*  5681 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5682 */       list.length - index);
/*  5683 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final String[] splice(String[] list, String[] v, int index)
/*       */   {
/*  5688 */     String[] outgoing = new String[list.length + v.length];
/*  5689 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5690 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5691 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5692 */       list.length - index);
/*  5693 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final Object splice(Object list, Object v, int index)
/*       */   {
/*  5698 */     Object[] outgoing = (Object[])null;
/*  5699 */     int length = Array.getLength(list);
/*       */     
/*       */ 
/*  5702 */     if (v.getClass().getName().charAt(0) == '[') {
/*  5703 */       int vlength = Array.getLength(v);
/*  5704 */       outgoing = new Object[length + vlength];
/*  5705 */       System.arraycopy(list, 0, outgoing, 0, index);
/*  5706 */       System.arraycopy(v, 0, outgoing, index, vlength);
/*  5707 */       System.arraycopy(list, index, outgoing, index + vlength, length - index);
/*       */     }
/*       */     else {
/*  5710 */       outgoing = new Object[length + 1];
/*  5711 */       System.arraycopy(list, 0, outgoing, 0, index);
/*  5712 */       Array.set(outgoing, index, v);
/*  5713 */       System.arraycopy(list, index, outgoing, index + 1, length - index);
/*       */     }
/*  5715 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] subset(boolean[] list, int start)
/*       */   {
/*  5721 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static boolean[] subset(boolean[] list, int start, int count) {
/*  5725 */     boolean[] output = new boolean[count];
/*  5726 */     System.arraycopy(list, start, output, 0, count);
/*  5727 */     return output;
/*       */   }
/*       */   
/*       */   public static byte[] subset(byte[] list, int start)
/*       */   {
/*  5732 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static byte[] subset(byte[] list, int start, int count) {
/*  5736 */     byte[] output = new byte[count];
/*  5737 */     System.arraycopy(list, start, output, 0, count);
/*  5738 */     return output;
/*       */   }
/*       */   
/*       */   public static char[] subset(char[] list, int start)
/*       */   {
/*  5743 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static char[] subset(char[] list, int start, int count) {
/*  5747 */     char[] output = new char[count];
/*  5748 */     System.arraycopy(list, start, output, 0, count);
/*  5749 */     return output;
/*       */   }
/*       */   
/*       */   public static int[] subset(int[] list, int start)
/*       */   {
/*  5754 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static int[] subset(int[] list, int start, int count) {
/*  5758 */     int[] output = new int[count];
/*  5759 */     System.arraycopy(list, start, output, 0, count);
/*  5760 */     return output;
/*       */   }
/*       */   
/*       */   public static float[] subset(float[] list, int start)
/*       */   {
/*  5765 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static float[] subset(float[] list, int start, int count) {
/*  5769 */     float[] output = new float[count];
/*  5770 */     System.arraycopy(list, start, output, 0, count);
/*  5771 */     return output;
/*       */   }
/*       */   
/*       */   public static String[] subset(String[] list, int start)
/*       */   {
/*  5776 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static String[] subset(String[] list, int start, int count) {
/*  5780 */     String[] output = new String[count];
/*  5781 */     System.arraycopy(list, start, output, 0, count);
/*  5782 */     return output;
/*       */   }
/*       */   
/*       */   public static Object subset(Object list, int start)
/*       */   {
/*  5787 */     int length = Array.getLength(list);
/*  5788 */     return subset(list, start, length - start);
/*       */   }
/*       */   
/*       */   public static Object subset(Object list, int start, int count) {
/*  5792 */     Class<?> type = list.getClass().getComponentType();
/*  5793 */     Object outgoing = Array.newInstance(type, count);
/*  5794 */     System.arraycopy(list, start, outgoing, 0, count);
/*  5795 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] concat(boolean[] a, boolean[] b)
/*       */   {
/*  5801 */     boolean[] c = new boolean[a.length + b.length];
/*  5802 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5803 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5804 */     return c;
/*       */   }
/*       */   
/*       */   public static byte[] concat(byte[] a, byte[] b) {
/*  5808 */     byte[] c = new byte[a.length + b.length];
/*  5809 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5810 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5811 */     return c;
/*       */   }
/*       */   
/*       */   public static char[] concat(char[] a, char[] b) {
/*  5815 */     char[] c = new char[a.length + b.length];
/*  5816 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5817 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5818 */     return c;
/*       */   }
/*       */   
/*       */   public static int[] concat(int[] a, int[] b) {
/*  5822 */     int[] c = new int[a.length + b.length];
/*  5823 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5824 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5825 */     return c;
/*       */   }
/*       */   
/*       */   public static float[] concat(float[] a, float[] b) {
/*  5829 */     float[] c = new float[a.length + b.length];
/*  5830 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5831 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5832 */     return c;
/*       */   }
/*       */   
/*       */   public static String[] concat(String[] a, String[] b) {
/*  5836 */     String[] c = new String[a.length + b.length];
/*  5837 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5838 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5839 */     return c;
/*       */   }
/*       */   
/*       */   public static Object concat(Object a, Object b) {
/*  5843 */     Class<?> type = a.getClass().getComponentType();
/*  5844 */     int alength = Array.getLength(a);
/*  5845 */     int blength = Array.getLength(b);
/*  5846 */     Object outgoing = Array.newInstance(type, alength + blength);
/*  5847 */     System.arraycopy(a, 0, outgoing, 0, alength);
/*  5848 */     System.arraycopy(b, 0, outgoing, alength, blength);
/*  5849 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] reverse(boolean[] list)
/*       */   {
/*  5855 */     boolean[] outgoing = new boolean[list.length];
/*  5856 */     int length1 = list.length - 1;
/*  5857 */     for (int i = 0; i < list.length; i++) {
/*  5858 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5860 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static byte[] reverse(byte[] list) {
/*  5864 */     byte[] outgoing = new byte[list.length];
/*  5865 */     int length1 = list.length - 1;
/*  5866 */     for (int i = 0; i < list.length; i++) {
/*  5867 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5869 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static char[] reverse(char[] list) {
/*  5873 */     char[] outgoing = new char[list.length];
/*  5874 */     int length1 = list.length - 1;
/*  5875 */     for (int i = 0; i < list.length; i++) {
/*  5876 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5878 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static int[] reverse(int[] list) {
/*  5882 */     int[] outgoing = new int[list.length];
/*  5883 */     int length1 = list.length - 1;
/*  5884 */     for (int i = 0; i < list.length; i++) {
/*  5885 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5887 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static float[] reverse(float[] list) {
/*  5891 */     float[] outgoing = new float[list.length];
/*  5892 */     int length1 = list.length - 1;
/*  5893 */     for (int i = 0; i < list.length; i++) {
/*  5894 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5896 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static String[] reverse(String[] list) {
/*  5900 */     String[] outgoing = new String[list.length];
/*  5901 */     int length1 = list.length - 1;
/*  5902 */     for (int i = 0; i < list.length; i++) {
/*  5903 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5905 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static Object reverse(Object list) {
/*  5909 */     Class<?> type = list.getClass().getComponentType();
/*  5910 */     int length = Array.getLength(list);
/*  5911 */     Object outgoing = Array.newInstance(type, length);
/*  5912 */     for (int i = 0; i < length; i++) {
/*  5913 */       Array.set(outgoing, i, Array.get(list, length - 1 - i));
/*       */     }
/*  5915 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String trim(String str)
/*       */   {
/*  5931 */     return str.replace(' ', ' ').trim();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] trim(String[] array)
/*       */   {
/*  5940 */     String[] outgoing = new String[array.length];
/*  5941 */     for (int i = 0; i < array.length; i++) {
/*  5942 */       if (array[i] != null) {
/*  5943 */         outgoing[i] = array[i].replace(' ', ' ').trim();
/*       */       }
/*       */     }
/*  5946 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String join(String[] str, char separator)
/*       */   {
/*  5955 */     return join(str, String.valueOf(separator));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String join(String[] str, String separator)
/*       */   {
/*  5971 */     StringBuffer buffer = new StringBuffer();
/*  5972 */     for (int i = 0; i < str.length; i++) {
/*  5973 */       if (i != 0) buffer.append(separator);
/*  5974 */       buffer.append(str[i]);
/*       */     }
/*  5976 */     return buffer.toString();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] splitTokens(String what)
/*       */   {
/*  5996 */     return splitTokens(what, " \t\n\r\f ");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] splitTokens(String what, String delim)
/*       */   {
/*  6015 */     StringTokenizer toker = new StringTokenizer(what, delim);
/*  6016 */     String[] pieces = new String[toker.countTokens()];
/*       */     
/*  6018 */     int index = 0;
/*  6019 */     while (toker.hasMoreTokens()) {
/*  6020 */       pieces[(index++)] = toker.nextToken();
/*       */     }
/*  6022 */     return pieces;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] split(String what, char delim)
/*       */   {
/*  6040 */     if (what == null) { return null;
/*       */     }
/*       */     
/*  6043 */     char[] chars = what.toCharArray();
/*  6044 */     int splitCount = 0;
/*  6045 */     for (int i = 0; i < chars.length; i++) {
/*  6046 */       if (chars[i] == delim) { splitCount++;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6054 */     if (splitCount == 0) {
/*  6055 */       String[] splits = new String[1];
/*  6056 */       splits[0] = new String(what);
/*  6057 */       return splits;
/*       */     }
/*       */     
/*  6060 */     String[] splits = new String[splitCount + 1];
/*  6061 */     int splitIndex = 0;
/*  6062 */     int startIndex = 0;
/*  6063 */     for (int i = 0; i < chars.length; i++) {
/*  6064 */       if (chars[i] == delim) {
/*  6065 */         splits[(splitIndex++)] = 
/*  6066 */           new String(chars, startIndex, i - startIndex);
/*  6067 */         startIndex = i + 1;
/*       */       }
/*       */     }
/*       */     
/*  6071 */     splits[splitIndex] = 
/*  6072 */       new String(chars, startIndex, chars.length - startIndex);
/*       */     
/*  6074 */     return splits;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] split(String what, String delim)
/*       */   {
/*  6085 */     ArrayList<String> items = new ArrayList();
/*       */     
/*  6087 */     int offset = 0;
/*  6088 */     int index; while ((index = what.indexOf(delim, offset)) != -1) { int index;
/*  6089 */       items.add(what.substring(offset, index));
/*  6090 */       offset = index + delim.length();
/*       */     }
/*  6092 */     items.add(what.substring(offset));
/*  6093 */     String[] outgoing = new String[items.size()];
/*  6094 */     items.toArray(outgoing);
/*  6095 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   static Pattern matchPattern(String regexp)
/*       */   {
/*  6102 */     Pattern p = null;
/*  6103 */     if (matchPatterns == null) {
/*  6104 */       matchPatterns = new HashMap();
/*       */     } else {
/*  6106 */       p = (Pattern)matchPatterns.get(regexp);
/*       */     }
/*  6108 */     if (p == null) {
/*  6109 */       if (matchPatterns.size() == 10)
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6117 */         matchPatterns.clear();
/*       */       }
/*  6119 */       p = Pattern.compile(regexp, 40);
/*  6120 */       matchPatterns.put(regexp, p);
/*       */     }
/*  6122 */     return p;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] match(String what, String regexp)
/*       */   {
/*  6137 */     Pattern p = matchPattern(regexp);
/*  6138 */     Matcher m = p.matcher(what);
/*  6139 */     if (m.find()) {
/*  6140 */       int count = m.groupCount() + 1;
/*  6141 */       String[] groups = new String[count];
/*  6142 */       for (int i = 0; i < count; i++) {
/*  6143 */         groups[i] = m.group(i);
/*       */       }
/*  6145 */       return groups;
/*       */     }
/*  6147 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[][] matchAll(String what, String regexp)
/*       */   {
/*  6156 */     Pattern p = matchPattern(regexp);
/*  6157 */     Matcher m = p.matcher(what);
/*  6158 */     ArrayList<String[]> results = new ArrayList();
/*  6159 */     int count = m.groupCount() + 1;
/*  6160 */     while (m.find()) {
/*  6161 */       String[] groups = new String[count];
/*  6162 */       for (int i = 0; i < count; i++) {
/*  6163 */         groups[i] = m.group(i);
/*       */       }
/*  6165 */       results.add(groups);
/*       */     }
/*  6167 */     if (results.isEmpty()) {
/*  6168 */       return null;
/*       */     }
/*  6170 */     String[][] matches = new String[results.size()][count];
/*  6171 */     for (int i = 0; i < matches.length; i++) {
/*  6172 */       matches[i] = ((String[])results.get(i));
/*       */     }
/*  6174 */     return matches;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean parseBoolean(int what)
/*       */   {
/*  6202 */     return what != 0;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean parseBoolean(String what)
/*       */   {
/*  6217 */     return new Boolean(what).booleanValue();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean[] parseBoolean(int[] what)
/*       */   {
/*  6256 */     boolean[] outgoing = new boolean[what.length];
/*  6257 */     for (int i = 0; i < what.length; i++) {
/*  6258 */       outgoing[i] = (what[i] != 0 ? 1 : false);
/*       */     }
/*  6260 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean[] parseBoolean(String[] what)
/*       */   {
/*  6275 */     boolean[] outgoing = new boolean[what.length];
/*  6276 */     for (int i = 0; i < what.length; i++) {
/*  6277 */       outgoing[i] = new Boolean(what[i]).booleanValue();
/*       */     }
/*  6279 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final byte parseByte(boolean what)
/*       */   {
/*  6285 */     return what ? 1 : 0;
/*       */   }
/*       */   
/*       */   public static final byte parseByte(char what) {
/*  6289 */     return (byte)what;
/*       */   }
/*       */   
/*       */   public static final byte parseByte(int what) {
/*  6293 */     return (byte)what;
/*       */   }
/*       */   
/*       */   public static final byte parseByte(float what) {
/*  6297 */     return (byte)(int)what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final byte[] parseByte(boolean[] what)
/*       */   {
/*  6310 */     byte[] outgoing = new byte[what.length];
/*  6311 */     for (int i = 0; i < what.length; i++) {
/*  6312 */       outgoing[i] = (what[i] != 0 ? 1 : 0);
/*       */     }
/*  6314 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final byte[] parseByte(char[] what) {
/*  6318 */     byte[] outgoing = new byte[what.length];
/*  6319 */     for (int i = 0; i < what.length; i++) {
/*  6320 */       outgoing[i] = ((byte)what[i]);
/*       */     }
/*  6322 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final byte[] parseByte(int[] what) {
/*  6326 */     byte[] outgoing = new byte[what.length];
/*  6327 */     for (int i = 0; i < what.length; i++) {
/*  6328 */       outgoing[i] = ((byte)what[i]);
/*       */     }
/*  6330 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final byte[] parseByte(float[] what) {
/*  6334 */     byte[] outgoing = new byte[what.length];
/*  6335 */     for (int i = 0; i < what.length; i++) {
/*  6336 */       outgoing[i] = ((byte)(int)what[i]);
/*       */     }
/*  6338 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final char parseChar(byte what)
/*       */   {
/*  6360 */     return (char)(what & 0xFF);
/*       */   }
/*       */   
/*       */   public static final char parseChar(int what) {
/*  6364 */     return (char)what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final char[] parseChar(byte[] what)
/*       */   {
/*  6390 */     char[] outgoing = new char[what.length];
/*  6391 */     for (int i = 0; i < what.length; i++) {
/*  6392 */       outgoing[i] = ((char)(what[i] & 0xFF));
/*       */     }
/*  6394 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final char[] parseChar(int[] what) {
/*  6398 */     char[] outgoing = new char[what.length];
/*  6399 */     for (int i = 0; i < what.length; i++) {
/*  6400 */       outgoing[i] = ((char)what[i]);
/*       */     }
/*  6402 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int parseInt(boolean what)
/*       */   {
/*  6426 */     return what ? 1 : 0;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final int parseInt(byte what)
/*       */   {
/*  6433 */     return what & 0xFF;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int parseInt(char what)
/*       */   {
/*  6442 */     return what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final int parseInt(float what)
/*       */   {
/*  6449 */     return (int)what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final int parseInt(String what)
/*       */   {
/*  6456 */     return parseInt(what, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final int parseInt(String what, int otherwise)
/*       */   {
/*       */     try
/*       */     {
/*  6465 */       int offset = what.indexOf('.');
/*  6466 */       if (offset == -1) {
/*  6467 */         return Integer.parseInt(what);
/*       */       }
/*  6469 */       return Integer.parseInt(what.substring(0, offset));
/*       */     }
/*       */     catch (NumberFormatException localNumberFormatException) {}
/*  6472 */     return otherwise;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final int[] parseInt(boolean[] what)
/*       */   {
/*  6478 */     int[] list = new int[what.length];
/*  6479 */     for (int i = 0; i < what.length; i++) {
/*  6480 */       list[i] = (what[i] != 0 ? 1 : 0);
/*       */     }
/*  6482 */     return list;
/*       */   }
/*       */   
/*       */   public static final int[] parseInt(byte[] what) {
/*  6486 */     int[] list = new int[what.length];
/*  6487 */     for (int i = 0; i < what.length; i++) {
/*  6488 */       what[i] &= 0xFF;
/*       */     }
/*  6490 */     return list;
/*       */   }
/*       */   
/*       */   public static final int[] parseInt(char[] what) {
/*  6494 */     int[] list = new int[what.length];
/*  6495 */     for (int i = 0; i < what.length; i++) {
/*  6496 */       list[i] = what[i];
/*       */     }
/*  6498 */     return list;
/*       */   }
/*       */   
/*       */   public static int[] parseInt(float[] what) {
/*  6502 */     int[] inties = new int[what.length];
/*  6503 */     for (int i = 0; i < what.length; i++) {
/*  6504 */       inties[i] = ((int)what[i]);
/*       */     }
/*  6506 */     return inties;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int[] parseInt(String[] what)
/*       */   {
/*  6519 */     return parseInt(what, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int[] parseInt(String[] what, int missing)
/*       */   {
/*  6533 */     int[] output = new int[what.length];
/*  6534 */     for (int i = 0; i < what.length; i++) {
/*       */       try {
/*  6536 */         output[i] = Integer.parseInt(what[i]);
/*       */       } catch (NumberFormatException e) {
/*  6538 */         output[i] = missing;
/*       */       }
/*       */     }
/*  6541 */     return output;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float parseFloat(int what)
/*       */   {
/*  6557 */     return what;
/*       */   }
/*       */   
/*       */   public static final float parseFloat(String what) {
/*  6561 */     return parseFloat(what, NaN.0F);
/*       */   }
/*       */   
/*       */   public static final float parseFloat(String what, float otherwise) {
/*       */     try {
/*  6566 */       return new Float(what).floatValue();
/*       */     }
/*       */     catch (NumberFormatException localNumberFormatException) {}
/*  6569 */     return otherwise;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float[] parseByte(byte[] what)
/*       */   {
/*  6593 */     float[] floaties = new float[what.length];
/*  6594 */     for (int i = 0; i < what.length; i++) {
/*  6595 */       floaties[i] = what[i];
/*       */     }
/*  6597 */     return floaties;
/*       */   }
/*       */   
/*       */   public static final float[] parseFloat(int[] what) {
/*  6601 */     float[] floaties = new float[what.length];
/*  6602 */     for (int i = 0; i < what.length; i++) {
/*  6603 */       floaties[i] = what[i];
/*       */     }
/*  6605 */     return floaties;
/*       */   }
/*       */   
/*       */   public static final float[] parseFloat(String[] what) {
/*  6609 */     return parseFloat(what, NaN.0F);
/*       */   }
/*       */   
/*       */   public static final float[] parseFloat(String[] what, float missing) {
/*  6613 */     float[] output = new float[what.length];
/*  6614 */     for (int i = 0; i < what.length; i++) {
/*       */       try {
/*  6616 */         output[i] = new Float(what[i]).floatValue();
/*       */       } catch (NumberFormatException e) {
/*  6618 */         output[i] = missing;
/*       */       }
/*       */     }
/*  6621 */     return output;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final String str(boolean x)
/*       */   {
/*  6627 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */   public static final String str(byte x) {
/*  6631 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */   public static final String str(char x) {
/*  6635 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */   public static final String str(int x) {
/*  6639 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */   public static final String str(float x) {
/*  6643 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */ 
/*       */   public static final String[] str(boolean[] x)
/*       */   {
/*  6649 */     String[] s = new String[x.length];
/*  6650 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6651 */     return s;
/*       */   }
/*       */   
/*       */   public static final String[] str(byte[] x) {
/*  6655 */     String[] s = new String[x.length];
/*  6656 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6657 */     return s;
/*       */   }
/*       */   
/*       */   public static final String[] str(char[] x) {
/*  6661 */     String[] s = new String[x.length];
/*  6662 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6663 */     return s;
/*       */   }
/*       */   
/*       */   public static final String[] str(int[] x) {
/*  6667 */     String[] s = new String[x.length];
/*  6668 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6669 */     return s;
/*       */   }
/*       */   
/*       */   public static final String[] str(float[] x) {
/*  6673 */     String[] s = new String[x.length];
/*  6674 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6675 */     return s;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] nf(int[] num, int digits)
/*       */   {
/*  6693 */     String[] formatted = new String[num.length];
/*  6694 */     for (int i = 0; i < formatted.length; i++) {
/*  6695 */       formatted[i] = nf(num[i], digits);
/*       */     }
/*  6697 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nf(int num, int digits)
/*       */   {
/*  6702 */     if ((int_nf != null) && 
/*  6703 */       (int_nf_digits == digits) && 
/*  6704 */       (!int_nf_commas)) {
/*  6705 */       return int_nf.format(num);
/*       */     }
/*       */     
/*  6708 */     int_nf = NumberFormat.getInstance();
/*  6709 */     int_nf.setGroupingUsed(false);
/*  6710 */     int_nf_commas = false;
/*  6711 */     int_nf.setMinimumIntegerDigits(digits);
/*  6712 */     int_nf_digits = digits;
/*  6713 */     return int_nf.format(num);
/*       */   }
/*       */   
/*       */   public static String[] nfc(int[] num)
/*       */   {
/*  6718 */     String[] formatted = new String[num.length];
/*  6719 */     for (int i = 0; i < formatted.length; i++) {
/*  6720 */       formatted[i] = nfc(num[i]);
/*       */     }
/*  6722 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nfc(int num)
/*       */   {
/*  6727 */     if ((int_nf != null) && 
/*  6728 */       (int_nf_digits == 0) && 
/*  6729 */       (int_nf_commas)) {
/*  6730 */       return int_nf.format(num);
/*       */     }
/*       */     
/*  6733 */     int_nf = NumberFormat.getInstance();
/*  6734 */     int_nf.setGroupingUsed(true);
/*  6735 */     int_nf_commas = true;
/*  6736 */     int_nf.setMinimumIntegerDigits(0);
/*  6737 */     int_nf_digits = 0;
/*  6738 */     return int_nf.format(num);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String nfs(int num, int digits)
/*       */   {
/*  6749 */     return ' ' + nf(num, digits);
/*       */   }
/*       */   
/*       */   public static String[] nfs(int[] num, int digits) {
/*  6753 */     String[] formatted = new String[num.length];
/*  6754 */     for (int i = 0; i < formatted.length; i++) {
/*  6755 */       formatted[i] = nfs(num[i], digits);
/*       */     }
/*  6757 */     return formatted;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String nfp(int num, int digits)
/*       */   {
/*  6768 */     return '+' + nf(num, digits);
/*       */   }
/*       */   
/*       */   public static String[] nfp(int[] num, int digits) {
/*  6772 */     String[] formatted = new String[num.length];
/*  6773 */     for (int i = 0; i < formatted.length; i++) {
/*  6774 */       formatted[i] = nfp(num[i], digits);
/*       */     }
/*  6776 */     return formatted;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] nf(float[] num, int left, int right)
/*       */   {
/*  6792 */     String[] formatted = new String[num.length];
/*  6793 */     for (int i = 0; i < formatted.length; i++) {
/*  6794 */       formatted[i] = nf(num[i], left, right);
/*       */     }
/*  6796 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nf(float num, int left, int right)
/*       */   {
/*  6801 */     if ((float_nf != null) && 
/*  6802 */       (float_nf_left == left) && 
/*  6803 */       (float_nf_right == right) && 
/*  6804 */       (!float_nf_commas)) {
/*  6805 */       return float_nf.format(num);
/*       */     }
/*       */     
/*  6808 */     float_nf = NumberFormat.getInstance();
/*  6809 */     float_nf.setGroupingUsed(false);
/*  6810 */     float_nf_commas = false;
/*       */     
/*  6812 */     if (left != 0) float_nf.setMinimumIntegerDigits(left);
/*  6813 */     if (right != 0) {
/*  6814 */       float_nf.setMinimumFractionDigits(right);
/*  6815 */       float_nf.setMaximumFractionDigits(right);
/*       */     }
/*  6817 */     float_nf_left = left;
/*  6818 */     float_nf_right = right;
/*  6819 */     return float_nf.format(num);
/*       */   }
/*       */   
/*       */   public static String[] nfc(float[] num, int right)
/*       */   {
/*  6824 */     String[] formatted = new String[num.length];
/*  6825 */     for (int i = 0; i < formatted.length; i++) {
/*  6826 */       formatted[i] = nfc(num[i], right);
/*       */     }
/*  6828 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nfc(float num, int right)
/*       */   {
/*  6833 */     if ((float_nf != null) && 
/*  6834 */       (float_nf_left == 0) && 
/*  6835 */       (float_nf_right == right) && 
/*  6836 */       (float_nf_commas)) {
/*  6837 */       return float_nf.format(num);
/*       */     }
/*       */     
/*  6840 */     float_nf = NumberFormat.getInstance();
/*  6841 */     float_nf.setGroupingUsed(true);
/*  6842 */     float_nf_commas = true;
/*       */     
/*  6844 */     if (right != 0) {
/*  6845 */       float_nf.setMinimumFractionDigits(right);
/*  6846 */       float_nf.setMaximumFractionDigits(right);
/*       */     }
/*  6848 */     float_nf_left = 0;
/*  6849 */     float_nf_right = right;
/*  6850 */     return float_nf.format(num);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] nfs(float[] num, int left, int right)
/*       */   {
/*  6859 */     String[] formatted = new String[num.length];
/*  6860 */     for (int i = 0; i < formatted.length; i++) {
/*  6861 */       formatted[i] = nfs(num[i], left, right);
/*       */     }
/*  6863 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nfs(float num, int left, int right) {
/*  6867 */     return ' ' + nf(num, left, right);
/*       */   }
/*       */   
/*       */   public static String[] nfp(float[] num, int left, int right)
/*       */   {
/*  6872 */     String[] formatted = new String[num.length];
/*  6873 */     for (int i = 0; i < formatted.length; i++) {
/*  6874 */       formatted[i] = nfp(num[i], left, right);
/*       */     }
/*  6876 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nfp(float num, int left, int right) {
/*  6880 */     return '+' + nf(num, left, right);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String hex(byte what)
/*       */   {
/*  6891 */     return hex(what, 2);
/*       */   }
/*       */   
/*       */   public static final String hex(char what) {
/*  6895 */     return hex(what, 4);
/*       */   }
/*       */   
/*       */   public static final String hex(int what) {
/*  6899 */     return hex(what, 8);
/*       */   }
/*       */   
/*       */   public static final String hex(int what, int digits) {
/*  6903 */     String stuff = Integer.toHexString(what).toUpperCase();
/*       */     
/*  6905 */     int length = stuff.length();
/*  6906 */     if (length > digits) {
/*  6907 */       return stuff.substring(length - digits);
/*       */     }
/*  6909 */     if (length < digits) {
/*  6910 */       return "00000000".substring(8 - (digits - length)) + stuff;
/*       */     }
/*  6912 */     return stuff;
/*       */   }
/*       */   
/*       */   public static final int unhex(String what)
/*       */   {
/*  6917 */     return (int)Long.parseLong(what, 16);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String binary(byte what)
/*       */   {
/*  6927 */     return binary(what, 8);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String binary(char what)
/*       */   {
/*  6936 */     return binary(what, 16);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String binary(int what)
/*       */   {
/*  6948 */     return Integer.toBinaryString(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String binary(int what, int digits)
/*       */   {
/*  6957 */     String stuff = Integer.toBinaryString(what);
/*       */     
/*  6959 */     int length = stuff.length();
/*  6960 */     if (length > digits) {
/*  6961 */       return stuff.substring(length - digits);
/*       */     }
/*  6963 */     if (length < digits) {
/*  6964 */       int offset = 32 - (digits - length);
/*  6965 */       return "00000000000000000000000000000000".substring(offset) + stuff;
/*       */     }
/*  6967 */     return stuff;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int unbinary(String what)
/*       */   {
/*  6976 */     return Integer.parseInt(what, 2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final int color(int gray)
/*       */   {
/*  6990 */     if (this.g == null) {
/*  6991 */       if (gray > 255) gray = 255; else if (gray < 0) gray = 0;
/*  6992 */       return 0xFF000000 | gray << 16 | gray << 8 | gray;
/*       */     }
/*  6994 */     return this.g.color(gray);
/*       */   }
/*       */   
/*       */   public final int color(float fgray)
/*       */   {
/*  6999 */     if (this.g == null) {
/*  7000 */       int gray = (int)fgray;
/*  7001 */       if (gray > 255) gray = 255; else if (gray < 0) gray = 0;
/*  7002 */       return 0xFF000000 | gray << 16 | gray << 8 | gray;
/*       */     }
/*  7004 */     return this.g.color(fgray);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final int color(int gray, int alpha)
/*       */   {
/*  7014 */     if (this.g == null) {
/*  7015 */       if (alpha > 255) alpha = 255; else if (alpha < 0) alpha = 0;
/*  7016 */       if (gray > 255)
/*       */       {
/*  7018 */         return alpha << 24 | gray & 0xFFFFFF;
/*       */       }
/*       */       
/*  7021 */       return alpha << 24 | gray << 16 | gray << 8 | gray;
/*       */     }
/*       */     
/*  7024 */     return this.g.color(gray, alpha);
/*       */   }
/*       */   
/*       */   public final int color(float fgray, float falpha)
/*       */   {
/*  7029 */     if (this.g == null) {
/*  7030 */       int gray = (int)fgray;
/*  7031 */       int alpha = (int)falpha;
/*  7032 */       if (gray > 255) gray = 255; else if (gray < 0) gray = 0;
/*  7033 */       if (alpha > 255) alpha = 255; else if (alpha < 0) alpha = 0;
/*  7034 */       return 0xFF000000 | gray << 16 | gray << 8 | gray;
/*       */     }
/*  7036 */     return this.g.color(fgray, falpha);
/*       */   }
/*       */   
/*       */   public final int color(int x, int y, int z)
/*       */   {
/*  7041 */     if (this.g == null) {
/*  7042 */       if (x > 255) x = 255; else if (x < 0) x = 0;
/*  7043 */       if (y > 255) y = 255; else if (y < 0) y = 0;
/*  7044 */       if (z > 255) z = 255; else if (z < 0) { z = 0;
/*       */       }
/*  7046 */       return 0xFF000000 | x << 16 | y << 8 | z;
/*       */     }
/*  7048 */     return this.g.color(x, y, z);
/*       */   }
/*       */   
/*       */   public final int color(float x, float y, float z)
/*       */   {
/*  7053 */     if (this.g == null) {
/*  7054 */       if (x > 255.0F) x = 255.0F; else if (x < 0.0F) x = 0.0F;
/*  7055 */       if (y > 255.0F) y = 255.0F; else if (y < 0.0F) y = 0.0F;
/*  7056 */       if (z > 255.0F) z = 255.0F; else if (z < 0.0F) { z = 0.0F;
/*       */       }
/*  7058 */       return 0xFF000000 | (int)x << 16 | (int)y << 8 | (int)z;
/*       */     }
/*  7060 */     return this.g.color(x, y, z);
/*       */   }
/*       */   
/*       */   public final int color(int x, int y, int z, int a)
/*       */   {
/*  7065 */     if (this.g == null) {
/*  7066 */       if (a > 255) a = 255; else if (a < 0) a = 0;
/*  7067 */       if (x > 255) x = 255; else if (x < 0) x = 0;
/*  7068 */       if (y > 255) y = 255; else if (y < 0) y = 0;
/*  7069 */       if (z > 255) z = 255; else if (z < 0) { z = 0;
/*       */       }
/*  7071 */       return a << 24 | x << 16 | y << 8 | z;
/*       */     }
/*  7073 */     return this.g.color(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final int color(float x, float y, float z, float a)
/*       */   {
/*  7089 */     if (this.g == null) {
/*  7090 */       if (a > 255.0F) a = 255.0F; else if (a < 0.0F) a = 0.0F;
/*  7091 */       if (x > 255.0F) x = 255.0F; else if (x < 0.0F) x = 0.0F;
/*  7092 */       if (y > 255.0F) y = 255.0F; else if (y < 0.0F) y = 0.0F;
/*  7093 */       if (z > 255.0F) z = 255.0F; else if (z < 0.0F) { z = 0.0F;
/*       */       }
/*  7095 */       return (int)a << 24 | (int)x << 16 | (int)y << 8 | (int)z;
/*       */     }
/*  7097 */     return this.g.color(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setupExternalMessages()
/*       */   {
/*  7116 */     this.frame.addComponentListener(new ComponentAdapter() {
/*       */       public void componentMoved(ComponentEvent e) {
/*  7118 */         Point where = ((Frame)e.getSource()).getLocation();
/*  7119 */         System.err.println("__MOVE__ " + 
/*  7120 */           where.x + " " + where.y);
/*  7121 */         System.err.flush();
/*       */       }
/*       */       
/*  7124 */     });
/*  7125 */     this.frame.addWindowListener(new WindowAdapter()
/*       */     {
/*       */ 
/*       */       public void windowClosing(WindowEvent e)
/*       */       {
/*  7130 */         PApplet.this.exit();
/*       */       }
/*       */     });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setupFrameResizeListener()
/*       */   {
/*  7141 */     this.frame.addComponentListener(new ComponentAdapter()
/*       */     {
/*       */ 
/*       */ 
/*       */       public void componentResized(ComponentEvent e)
/*       */       {
/*       */ 
/*  7148 */         if (PApplet.this.frame.isResizable())
/*       */         {
/*       */ 
/*       */ 
/*  7152 */           Frame farm = (Frame)e.getComponent();
/*  7153 */           if (farm.isVisible()) {
/*  7154 */             Insets insets = farm.getInsets();
/*  7155 */             Dimension windowSize = farm.getSize();
/*  7156 */             int usableW = windowSize.width - insets.left - insets.right;
/*  7157 */             int usableH = windowSize.height - insets.top - insets.bottom;
/*       */             
/*       */ 
/*  7160 */             PApplet.this.setBounds(insets.left, insets.top, usableW, usableH);
/*       */           }
/*       */         }
/*       */       }
/*       */     });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7171 */   public static final byte[] ICON_IMAGE = {
/*  7172 */     71, 73, 70, 56, 57, 97, 16, 0, 16, 0, -77, 0, 0, 0, 0, 0, -1, -1, -1, 12, 
/*  7173 */     12, 13, -15, -15, -14, 45, 57, 74, 54, 80, 111, 47, 71, 97, 62, 88, 117, 
/*  7174 */     1, 14, 27, 7, 41, 73, 15, 52, 85, 2, 31, 55, 4, 54, 94, 18, 69, 109, 37, 
/*  7175 */     87, 126, -1, -1, -1, 33, -7, 4, 1, 0, 0, 15, 0, 44, 0, 0, 0, 0, 16, 0, 16, 
/*  7176 */     0, 0, 4, 122, -16, -107, 114, -86, -67, 83, 30, -42, 26, -17, -100, -45, 
/*  7177 */     56, -57, -108, 48, 40, 122, -90, 104, 67, -91, -51, 32, -53, 77, -78, -100, 
/*  7178 */     47, -86, 12, 76, -110, -20, -74, -101, 97, -93, 27, 40, 20, -65, 65, 48, 
/*  7179 */     -111, 99, -20, -112, -117, -123, -47, -105, 24, 114, -112, 74, 69, 84, 25, 
/*  7180 */     93, 88, -75, 9, 46, 2, 49, 88, -116, -67, 7, -19, -83, 60, 38, 3, -34, 2, 
/*  7181 */     66, -95, 27, -98, 13, 4, -17, 55, 33, 109, 11, 11, -2, Byte.MIN_VALUE, 121, 123, 62, 
/*  7182 */     91, 120, Byte.MIN_VALUE, Byte.MAX_VALUE, 122, 115, 102, 2, 119, 0, -116, -113, -119, 6, 102, 
/*  7183 */     121, -108, -126, 5, 18, 6, 4, -102, -101, -100, 114, 15, 17, 059 };
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void runSketch(String[] args, PApplet constructedApplet)
/*       */   {
/*  7244 */     if (platform == 2)
/*       */     {
/*       */ 
/*  7247 */       System.setProperty("apple.awt.graphics.UseQuartz", 
/*  7248 */         String.valueOf(useQuartz));
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7259 */     if (args.length < 1) {
/*  7260 */       System.err.println("Usage: PApplet <appletname>");
/*  7261 */       System.err.println("For additional options, see the Javadoc for PApplet");
/*       */       
/*  7263 */       System.exit(1);
/*       */     }
/*       */     
/*  7266 */     boolean external = false;
/*  7267 */     int[] location = (int[])null;
/*  7268 */     int[] editorLocation = (int[])null;
/*       */     
/*  7270 */     String name = null;
/*  7271 */     boolean present = false;
/*  7272 */     boolean exclusive = false;
/*  7273 */     Color backgroundColor = Color.BLACK;
/*  7274 */     Color stopColor = Color.GRAY;
/*  7275 */     GraphicsDevice displayDevice = null;
/*  7276 */     boolean hideStop = false;
/*       */     
/*  7278 */     String param = null;String value = null;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  7283 */     String folder = null;
/*       */     try {
/*  7285 */       folder = System.getProperty("user.dir");
/*       */     }
/*       */     catch (Exception localException1) {}
/*  7288 */     int argIndex = 0;
/*  7289 */     while (argIndex < args.length) {
/*  7290 */       int equals = args[argIndex].indexOf('=');
/*  7291 */       if (equals != -1) {
/*  7292 */         param = args[argIndex].substring(0, equals);
/*  7293 */         value = args[argIndex].substring(equals + 1);
/*       */         
/*  7295 */         if (param.equals("--editor-location")) {
/*  7296 */           external = true;
/*  7297 */           editorLocation = parseInt(split(value, ','));
/*       */         }
/*  7299 */         else if (param.equals("--display")) {
/*  7300 */           int deviceIndex = Integer.parseInt(value) - 1;
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  7305 */           GraphicsEnvironment environment = 
/*  7306 */             GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  7307 */           GraphicsDevice[] devices = environment.getScreenDevices();
/*  7308 */           if ((deviceIndex >= 0) && (deviceIndex < devices.length)) {
/*  7309 */             displayDevice = devices[deviceIndex];
/*       */           } else {
/*  7311 */             System.err.println("Display " + value + " does not exist, " + 
/*  7312 */               "using the default display instead.");
/*       */           }
/*       */         }
/*  7315 */         else if (param.equals("--bgcolor")) {
/*  7316 */           if (value.charAt(0) == '#') value = value.substring(1);
/*  7317 */           backgroundColor = new Color(Integer.parseInt(value, 16));
/*       */         }
/*  7319 */         else if (param.equals("--stop-color")) {
/*  7320 */           if (value.charAt(0) == '#') value = value.substring(1);
/*  7321 */           stopColor = new Color(Integer.parseInt(value, 16));
/*       */         }
/*  7323 */         else if (param.equals("--sketch-path")) {
/*  7324 */           folder = value;
/*       */         }
/*  7326 */         else if (param.equals("--location")) {
/*  7327 */           location = parseInt(split(value, ','));
/*       */         }
/*       */         
/*       */       }
/*  7331 */       else if (args[argIndex].equals("--present")) {
/*  7332 */         present = true;
/*       */       }
/*  7334 */       else if (args[argIndex].equals("--exclusive")) {
/*  7335 */         exclusive = true;
/*       */       }
/*  7337 */       else if (args[argIndex].equals("--hide-stop")) {
/*  7338 */         hideStop = true;
/*       */       }
/*  7340 */       else if (args[argIndex].equals("--external")) {
/*  7341 */         external = true;
/*       */       }
/*       */       else {
/*  7344 */         name = args[argIndex];
/*  7345 */         break;
/*       */       }
/*       */       
/*  7348 */       argIndex++;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7356 */     if (displayDevice == null) {
/*  7357 */       GraphicsEnvironment environment = 
/*  7358 */         GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  7359 */       displayDevice = environment.getDefaultScreenDevice();
/*       */     }
/*       */     
/*  7362 */     Frame frame = new Frame(displayDevice.getDefaultConfiguration());
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7379 */     Image image = Toolkit.getDefaultToolkit().createImage(ICON_IMAGE);
/*  7380 */     frame.setIconImage(image);
/*  7381 */     frame.setTitle(name);
/*       */     
/*       */     PApplet applet;
/*  7384 */     if (constructedApplet != null) {
/*  7385 */       applet = constructedApplet;
/*       */     } else {
/*       */       try {
/*  7388 */         Class<?> c = Thread.currentThread().getContextClassLoader().loadClass(name);
/*  7389 */         applet = (PApplet)c.newInstance();
/*       */       } catch (Exception e) { PApplet applet;
/*  7391 */         throw new RuntimeException(e);
/*       */       }
/*       */     }
/*       */     
/*       */     PApplet applet;
/*  7396 */     applet.frame = frame;
/*  7397 */     applet.sketchPath = folder;
/*  7398 */     applet.args = subset(args, 1);
/*  7399 */     applet.external = external;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  7404 */     Rectangle fullScreenRect = null;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7413 */     if (present) {
/*  7414 */       frame.setUndecorated(true);
/*  7415 */       frame.setBackground(backgroundColor);
/*  7416 */       if (exclusive) {
/*  7417 */         displayDevice.setFullScreenWindow(frame);
/*       */         
/*       */ 
/*  7420 */         fullScreenRect = frame.getBounds();
/*       */       } else {
/*  7422 */         DisplayMode mode = displayDevice.getDisplayMode();
/*  7423 */         fullScreenRect = new Rectangle(0, 0, mode.getWidth(), mode.getHeight());
/*  7424 */         frame.setBounds(fullScreenRect);
/*  7425 */         frame.setVisible(true);
/*       */       }
/*       */     }
/*  7428 */     frame.setLayout(null);
/*  7429 */     frame.add(applet);
/*  7430 */     if (present) {
/*  7431 */       frame.invalidate();
/*       */     } else {
/*  7433 */       frame.pack();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7440 */     frame.setResizable(false);
/*       */     
/*  7442 */     applet.init();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7448 */     while ((applet.defaultSize) && (!applet.finished)) {
/*       */       try
/*       */       {
/*  7451 */         Thread.sleep(5L);
/*       */       }
/*       */       catch (InterruptedException localInterruptedException) {}
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7460 */     if (present)
/*       */     {
/*  7462 */       frame.setBounds(fullScreenRect);
/*  7463 */       applet.setBounds((fullScreenRect.width - applet.width) / 2, 
/*  7464 */         (fullScreenRect.height - applet.height) / 2, 
/*  7465 */         applet.width, applet.height);
/*       */       
/*  7467 */       if (!hideStop) {
/*  7468 */         Label label = new Label("stop");
/*  7469 */         label.setForeground(stopColor);
/*  7470 */         label.addMouseListener(new MouseAdapter() {
/*       */           public void mousePressed(MouseEvent e) {
/*  7472 */             System.exit(0);
/*       */           }
/*  7474 */         });
/*  7475 */         frame.add(label);
/*       */         
/*  7477 */         Dimension labelSize = label.getPreferredSize();
/*       */         
/*       */ 
/*  7480 */         labelSize = new Dimension(100, labelSize.height);
/*  7481 */         label.setSize(labelSize);
/*  7482 */         label.setLocation(20, fullScreenRect.height - labelSize.height - 20);
/*       */       }
/*       */       
/*       */ 
/*  7486 */       if (external) {
/*  7487 */         applet.setupExternalMessages();
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*  7494 */       Insets insets = frame.getInsets();
/*       */       
/*  7496 */       int windowW = Math.max(applet.width, 128) + 
/*  7497 */         insets.left + insets.right;
/*  7498 */       int windowH = Math.max(applet.height, 128) + 
/*  7499 */         insets.top + insets.bottom;
/*       */       
/*  7501 */       frame.setSize(windowW, windowH);
/*       */       
/*  7503 */       if (location != null)
/*       */       {
/*       */ 
/*  7506 */         frame.setLocation(location[0], location[1]);
/*       */       }
/*  7508 */       else if (external) {
/*  7509 */         int locationX = editorLocation[0] - 20;
/*  7510 */         int locationY = editorLocation[1];
/*       */         
/*  7512 */         if (locationX - windowW > 10)
/*       */         {
/*  7514 */           frame.setLocation(locationX - windowW, locationY);
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*       */ 
/*  7520 */           locationX = editorLocation[0] + 66;
/*  7521 */           locationY = editorLocation[1] + 66;
/*       */           
/*  7523 */           if ((locationX + windowW > applet.screenWidth - 33) || 
/*  7524 */             (locationY + windowH > applet.screenHeight - 33))
/*       */           {
/*  7526 */             locationX = (applet.screenWidth - windowW) / 2;
/*  7527 */             locationY = (applet.screenHeight - windowH) / 2;
/*       */           }
/*  7529 */           frame.setLocation(locationX, locationY);
/*       */         }
/*       */       } else {
/*  7532 */         frame.setLocation((applet.screenWidth - applet.width) / 2, 
/*  7533 */           (applet.screenHeight - applet.height) / 2);
/*       */       }
/*  7535 */       Point frameLoc = frame.getLocation();
/*  7536 */       if (frameLoc.y < 0)
/*       */       {
/*       */ 
/*  7539 */         frame.setLocation(frameLoc.x, 30);
/*       */       }
/*       */       
/*  7542 */       if (backgroundColor == Color.black)
/*       */       {
/*  7544 */         backgroundColor = SystemColor.control;
/*       */       }
/*  7546 */       frame.setBackground(backgroundColor);
/*       */       
/*  7548 */       int usableWindowH = windowH - insets.top - insets.bottom;
/*  7549 */       applet.setBounds((windowW - applet.width) / 2, 
/*  7550 */         insets.top + (usableWindowH - applet.height) / 2, 
/*  7551 */         applet.width, applet.height);
/*       */       
/*  7553 */       if (external) {
/*  7554 */         applet.setupExternalMessages();
/*       */       }
/*       */       else {
/*  7557 */         frame.addWindowListener(new WindowAdapter() {
/*       */           public void windowClosing(WindowEvent e) {
/*  7559 */             System.exit(0);
/*       */           }
/*       */         });
/*       */       }
/*       */       
/*       */ 
/*  7565 */       applet.setupFrameResizeListener();
/*       */       
/*       */ 
/*  7568 */       if (applet.displayable()) {
/*  7569 */         frame.setVisible(true);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void main(String[] args)
/*       */   {
/*  7580 */     runSketch(args, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void runSketch(String[] args)
/*       */   {
/*  7594 */     String[] argsWithSketchName = new String[args.length + 1];
/*  7595 */     System.arraycopy(args, 0, argsWithSketchName, 0, args.length);
/*  7596 */     String className = getClass().getSimpleName();
/*  7597 */     String cleanedClass = 
/*  7598 */       className.replaceAll("__[^_]+__\\$", "").replaceAll("\\$\\d+", "");
/*  7599 */     argsWithSketchName[args.length] = cleanedClass;
/*  7600 */     runSketch(argsWithSketchName, this);
/*       */   }
/*       */   
/*       */   protected void runSketch() {
/*  7604 */     runSketch(new String[0]);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics beginRecord(String renderer, String filename)
/*       */   {
/*  7615 */     filename = insertFrame(filename);
/*  7616 */     PGraphics rec = createGraphics(this.width, this.height, renderer, filename);
/*  7617 */     beginRecord(rec);
/*  7618 */     return rec;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void beginRecord(PGraphics recorder)
/*       */   {
/*  7626 */     this.recorder = recorder;
/*  7627 */     recorder.beginDraw();
/*       */   }
/*       */   
/*       */   public void endRecord()
/*       */   {
/*  7632 */     if (this.recorder != null) {
/*  7633 */       this.recorder.endDraw();
/*  7634 */       this.recorder.dispose();
/*  7635 */       this.recorder = null;
/*       */     }
/*  7637 */     if (this.g.isRecording()) {
/*  7638 */       this.g.endRecord();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics beginRaw(String renderer, String filename)
/*       */   {
/*  7651 */     filename = insertFrame(filename);
/*  7652 */     PGraphics rec = createGraphics(this.width, this.height, renderer, filename);
/*  7653 */     this.g.beginRaw(rec);
/*  7654 */     return rec;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void beginRaw(PGraphics rawGraphics)
/*       */   {
/*  7666 */     this.g.beginRaw(rawGraphics);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void endRaw()
/*       */   {
/*  7678 */     this.g.endRaw();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PShape beginRecord()
/*       */   {
/*  7687 */     return this.g.beginRecord();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void loadPixels()
/*       */   {
/*  7706 */     this.g.loadPixels();
/*  7707 */     this.pixels = this.g.pixels;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void updatePixels()
/*       */   {
/*  7722 */     this.g.updatePixels();
/*       */   }
/*       */   
/*       */   public void updatePixels(int x1, int y1, int x2, int y2)
/*       */   {
/*  7727 */     this.g.updatePixels(x1, y1, x2, y2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void flush()
/*       */   {
/*  7741 */     if (this.recorder != null) this.recorder.flush();
/*  7742 */     this.g.flush();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void hint(int which)
/*       */   {
/*  7765 */     if (this.recorder != null) this.recorder.hint(which);
/*  7766 */     this.g.hint(which);
/*       */   }
/*       */   
/*       */   public boolean hintEnabled(int which)
/*       */   {
/*  7771 */     return this.g.hintEnabled(which);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void beginShape()
/*       */   {
/*  7779 */     if (this.recorder != null) this.recorder.beginShape();
/*  7780 */     this.g.beginShape();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void beginShape(int kind)
/*       */   {
/*  7807 */     if (this.recorder != null) this.recorder.beginShape(kind);
/*  7808 */     this.g.beginShape(kind);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void edge(boolean edge)
/*       */   {
/*  7817 */     if (this.recorder != null) this.recorder.edge(edge);
/*  7818 */     this.g.edge(edge);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void normal(float nx, float ny, float nz)
/*       */   {
/*  7838 */     if (this.recorder != null) this.recorder.normal(nx, ny, nz);
/*  7839 */     this.g.normal(nx, ny, nz);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textureMode(int mode)
/*       */   {
/*  7848 */     if (this.recorder != null) this.recorder.textureMode(mode);
/*  7849 */     this.g.textureMode(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void texture(PImage image)
/*       */   {
/*  7860 */     if (this.recorder != null) this.recorder.texture(image);
/*  7861 */     this.g.texture(image);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noTexture()
/*       */   {
/*  7871 */     if (this.recorder != null) this.recorder.noTexture();
/*  7872 */     this.g.noTexture();
/*       */   }
/*       */   
/*       */   public void vertex(float x, float y)
/*       */   {
/*  7877 */     if (this.recorder != null) this.recorder.vertex(x, y);
/*  7878 */     this.g.vertex(x, y);
/*       */   }
/*       */   
/*       */   public void vertex(float x, float y, float z)
/*       */   {
/*  7883 */     if (this.recorder != null) this.recorder.vertex(x, y, z);
/*  7884 */     this.g.vertex(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void vertexFields(float[] v)
/*       */   {
/*  7894 */     if (this.recorder != null) this.recorder.vertexFields(v);
/*  7895 */     this.g.vertexFields(v);
/*       */   }
/*       */   
/*       */   public void vertex(float x, float y, float u, float v)
/*       */   {
/*  7900 */     if (this.recorder != null) this.recorder.vertex(x, y, u, v);
/*  7901 */     this.g.vertex(x, y, u, v);
/*       */   }
/*       */   
/*       */   public void vertex(float x, float y, float z, float u, float v)
/*       */   {
/*  7906 */     if (this.recorder != null) this.recorder.vertex(x, y, z, u, v);
/*  7907 */     this.g.vertex(x, y, z, u, v);
/*       */   }
/*       */   
/*       */ 
/*       */   public void breakShape()
/*       */   {
/*  7913 */     if (this.recorder != null) this.recorder.breakShape();
/*  7914 */     this.g.breakShape();
/*       */   }
/*       */   
/*       */   public void endShape()
/*       */   {
/*  7919 */     if (this.recorder != null) this.recorder.endShape();
/*  7920 */     this.g.endShape();
/*       */   }
/*       */   
/*       */   public void endShape(int mode)
/*       */   {
/*  7925 */     if (this.recorder != null) this.recorder.endShape(mode);
/*  7926 */     this.g.endShape(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4)
/*       */   {
/*  7933 */     if (this.recorder != null) this.recorder.bezierVertex(x2, y2, x3, y3, x4, y4);
/*  7934 */     this.g.bezierVertex(x2, y2, x3, y3, x4, y4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*       */   {
/*  7941 */     if (this.recorder != null) this.recorder.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*  7942 */     this.g.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*       */   }
/*       */   
/*       */ 
/*       */   public void quadVertex(float cx, float cy, float x3, float y3)
/*       */   {
/*  7948 */     if (this.recorder != null) this.recorder.quadVertex(cx, cy, x3, y3);
/*  7949 */     this.g.quadVertex(cx, cy, x3, y3);
/*       */   }
/*       */   
/*       */ 
/*       */   public void quadVertex(float cx, float cy, float cz, float x3, float y3, float z3)
/*       */   {
/*  7955 */     if (this.recorder != null) this.recorder.quadVertex(cx, cy, cz, x3, y3, z3);
/*  7956 */     this.g.quadVertex(cx, cy, cz, x3, y3, z3);
/*       */   }
/*       */   
/*       */   public void curveVertex(float x, float y)
/*       */   {
/*  7961 */     if (this.recorder != null) this.recorder.curveVertex(x, y);
/*  7962 */     this.g.curveVertex(x, y);
/*       */   }
/*       */   
/*       */   public void curveVertex(float x, float y, float z)
/*       */   {
/*  7967 */     if (this.recorder != null) this.recorder.curveVertex(x, y, z);
/*  7968 */     this.g.curveVertex(x, y, z);
/*       */   }
/*       */   
/*       */   public void point(float x, float y)
/*       */   {
/*  7973 */     if (this.recorder != null) this.recorder.point(x, y);
/*  7974 */     this.g.point(x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void point(float x, float y, float z)
/*       */   {
/*  7999 */     if (this.recorder != null) this.recorder.point(x, y, z);
/*  8000 */     this.g.point(x, y, z);
/*       */   }
/*       */   
/*       */   public void line(float x1, float y1, float x2, float y2)
/*       */   {
/*  8005 */     if (this.recorder != null) this.recorder.line(x1, y1, x2, y2);
/*  8006 */     this.g.line(x1, y1, x2, y2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void line(float x1, float y1, float z1, float x2, float y2, float z2)
/*       */   {
/*  8037 */     if (this.recorder != null) this.recorder.line(x1, y1, z1, x2, y2, z2);
/*  8038 */     this.g.line(x1, y1, z1, x2, y2, z2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void triangle(float x1, float y1, float x2, float y2, float x3, float y3)
/*       */   {
/*  8059 */     if (this.recorder != null) this.recorder.triangle(x1, y1, x2, y2, x3, y3);
/*  8060 */     this.g.triangle(x1, y1, x2, y2, x3, y3);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*       */   {
/*  8084 */     if (this.recorder != null) this.recorder.quad(x1, y1, x2, y2, x3, y3, x4, y4);
/*  8085 */     this.g.quad(x1, y1, x2, y2, x3, y3, x4, y4);
/*       */   }
/*       */   
/*       */   public void rectMode(int mode)
/*       */   {
/*  8090 */     if (this.recorder != null) this.recorder.rectMode(mode);
/*  8091 */     this.g.rectMode(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rect(float a, float b, float c, float d)
/*       */   {
/*  8111 */     if (this.recorder != null) this.recorder.rect(a, b, c, d);
/*  8112 */     this.g.rect(a, b, c, d);
/*       */   }
/*       */   
/*       */   public void rect(float a, float b, float c, float d, float hr, float vr)
/*       */   {
/*  8117 */     if (this.recorder != null) this.recorder.rect(a, b, c, d, hr, vr);
/*  8118 */     this.g.rect(a, b, c, d, hr, vr);
/*       */   }
/*       */   
/*       */ 
/*       */   public void rect(float a, float b, float c, float d, float tl, float tr, float bl, float br)
/*       */   {
/*  8124 */     if (this.recorder != null) this.recorder.rect(a, b, c, d, tl, tr, bl, br);
/*  8125 */     this.g.rect(a, b, c, d, tl, tr, bl, br);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void ellipseMode(int mode)
/*       */   {
/*  8147 */     if (this.recorder != null) this.recorder.ellipseMode(mode);
/*  8148 */     this.g.ellipseMode(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void ellipse(float a, float b, float c, float d)
/*       */   {
/*  8167 */     if (this.recorder != null) this.recorder.ellipse(a, b, c, d);
/*  8168 */     this.g.ellipse(a, b, c, d);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void arc(float a, float b, float c, float d, float start, float stop)
/*       */   {
/*  8194 */     if (this.recorder != null) this.recorder.arc(a, b, c, d, start, stop);
/*  8195 */     this.g.arc(a, b, c, d, start, stop);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void box(float size)
/*       */   {
/*  8203 */     if (this.recorder != null) this.recorder.box(size);
/*  8204 */     this.g.box(size);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void box(float w, float h, float d)
/*       */   {
/*  8220 */     if (this.recorder != null) this.recorder.box(w, h, d);
/*  8221 */     this.g.box(w, h, d);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void sphereDetail(int res)
/*       */   {
/*  8229 */     if (this.recorder != null) this.recorder.sphereDetail(res);
/*  8230 */     this.g.sphereDetail(res);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void sphereDetail(int ures, int vres)
/*       */   {
/*  8266 */     if (this.recorder != null) this.recorder.sphereDetail(ures, vres);
/*  8267 */     this.g.sphereDetail(ures, vres);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void sphere(float r)
/*       */   {
/*  8299 */     if (this.recorder != null) this.recorder.sphere(r);
/*  8300 */     this.g.sphere(r);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float bezierPoint(float a, float b, float c, float d, float t)
/*       */   {
/*  8344 */     return this.g.bezierPoint(a, b, c, d, t);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float bezierTangent(float a, float b, float c, float d, float t)
/*       */   {
/*  8367 */     return this.g.bezierTangent(a, b, c, d, t);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void bezierDetail(int detail)
/*       */   {
/*  8382 */     if (this.recorder != null) this.recorder.bezierDetail(detail);
/*  8383 */     this.g.bezierDetail(detail);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*       */   {
/*  8440 */     if (this.recorder != null) this.recorder.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
/*  8441 */     this.g.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void bezier(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*       */   {
/*  8449 */     if (this.recorder != null) this.recorder.bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*  8450 */     this.g.bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float curvePoint(float a, float b, float c, float d, float t)
/*       */   {
/*  8473 */     return this.g.curvePoint(a, b, c, d, t);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float curveTangent(float a, float b, float c, float d, float t)
/*       */   {
/*  8496 */     return this.g.curveTangent(a, b, c, d, t);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void curveDetail(int detail)
/*       */   {
/*  8513 */     if (this.recorder != null) this.recorder.curveDetail(detail);
/*  8514 */     this.g.curveDetail(detail);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void curveTightness(float tightness)
/*       */   {
/*  8536 */     if (this.recorder != null) this.recorder.curveTightness(tightness);
/*  8537 */     this.g.curveTightness(tightness);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*       */   {
/*  8589 */     if (this.recorder != null) this.recorder.curve(x1, y1, x2, y2, x3, y3, x4, y4);
/*  8590 */     this.g.curve(x1, y1, x2, y2, x3, y3, x4, y4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void curve(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*       */   {
/*  8598 */     if (this.recorder != null) this.recorder.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*  8599 */     this.g.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void smooth()
/*       */   {
/*  8608 */     if (this.recorder != null) this.recorder.smooth();
/*  8609 */     this.g.smooth();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noSmooth()
/*       */   {
/*  8617 */     if (this.recorder != null) this.recorder.noSmooth();
/*  8618 */     this.g.noSmooth();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void imageMode(int mode)
/*       */   {
/*  8644 */     if (this.recorder != null) this.recorder.imageMode(mode);
/*  8645 */     this.g.imageMode(mode);
/*       */   }
/*       */   
/*       */   public void image(PImage image, float x, float y)
/*       */   {
/*  8650 */     if (this.recorder != null) this.recorder.image(image, x, y);
/*  8651 */     this.g.image(image, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void image(PImage image, float x, float y, float c, float d)
/*       */   {
/*  8689 */     if (this.recorder != null) this.recorder.image(image, x, y, c, d);
/*  8690 */     this.g.image(image, x, y, c, d);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void image(PImage image, float a, float b, float c, float d, int u1, int v1, int u2, int v2)
/*       */   {
/*  8702 */     if (this.recorder != null) this.recorder.image(image, a, b, c, d, u1, v1, u2, v2);
/*  8703 */     this.g.image(image, a, b, c, d, u1, v1, u2, v2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void shapeMode(int mode)
/*       */   {
/*  8728 */     if (this.recorder != null) this.recorder.shapeMode(mode);
/*  8729 */     this.g.shapeMode(mode);
/*       */   }
/*       */   
/*       */   public void shape(PShape shape)
/*       */   {
/*  8734 */     if (this.recorder != null) this.recorder.shape(shape);
/*  8735 */     this.g.shape(shape);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void shape(PShape shape, float x, float y)
/*       */   {
/*  8743 */     if (this.recorder != null) this.recorder.shape(shape, x, y);
/*  8744 */     this.g.shape(shape, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void shape(PShape shape, float x, float y, float c, float d)
/*       */   {
/*  8778 */     if (this.recorder != null) this.recorder.shape(shape, x, y, c, d);
/*  8779 */     this.g.shape(shape, x, y, c, d);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textAlign(int align)
/*       */   {
/*  8788 */     if (this.recorder != null) this.recorder.textAlign(align);
/*  8789 */     this.g.textAlign(align);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textAlign(int alignX, int alignY)
/*       */   {
/*  8799 */     if (this.recorder != null) this.recorder.textAlign(alignX, alignY);
/*  8800 */     this.g.textAlign(alignX, alignY);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float textAscent()
/*       */   {
/*  8810 */     return this.g.textAscent();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float textDescent()
/*       */   {
/*  8820 */     return this.g.textDescent();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textFont(PFont which)
/*       */   {
/*  8830 */     if (this.recorder != null) this.recorder.textFont(which);
/*  8831 */     this.g.textFont(which);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textFont(PFont which, float size)
/*       */   {
/*  8839 */     if (this.recorder != null) this.recorder.textFont(which, size);
/*  8840 */     this.g.textFont(which, size);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textLeading(float leading)
/*       */   {
/*  8850 */     if (this.recorder != null) this.recorder.textLeading(leading);
/*  8851 */     this.g.textLeading(leading);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textMode(int mode)
/*       */   {
/*  8863 */     if (this.recorder != null) this.recorder.textMode(mode);
/*  8864 */     this.g.textMode(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textSize(float size)
/*       */   {
/*  8872 */     if (this.recorder != null) this.recorder.textSize(size);
/*  8873 */     this.g.textSize(size);
/*       */   }
/*       */   
/*       */   public float textWidth(char c)
/*       */   {
/*  8878 */     return this.g.textWidth(c);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float textWidth(String str)
/*       */   {
/*  8887 */     return this.g.textWidth(str);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public float textWidth(char[] chars, int start, int length)
/*       */   {
/*  8895 */     return this.g.textWidth(chars, start, length);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(char c)
/*       */   {
/*  8903 */     if (this.recorder != null) this.recorder.text(c);
/*  8904 */     this.g.text(c);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(char c, float x, float y)
/*       */   {
/*  8914 */     if (this.recorder != null) this.recorder.text(c, x, y);
/*  8915 */     this.g.text(c, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(char c, float x, float y, float z)
/*       */   {
/*  8923 */     if (this.recorder != null) this.recorder.text(c, x, y, z);
/*  8924 */     this.g.text(c, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(String str)
/*       */   {
/*  8932 */     if (this.recorder != null) this.recorder.text(str);
/*  8933 */     this.g.text(str);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(String str, float x, float y)
/*       */   {
/*  8944 */     if (this.recorder != null) this.recorder.text(str, x, y);
/*  8945 */     this.g.text(str, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(char[] chars, int start, int stop, float x, float y)
/*       */   {
/*  8955 */     if (this.recorder != null) this.recorder.text(chars, start, stop, x, y);
/*  8956 */     this.g.text(chars, start, stop, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(String str, float x, float y, float z)
/*       */   {
/*  8964 */     if (this.recorder != null) this.recorder.text(str, x, y, z);
/*  8965 */     this.g.text(str, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */   public void text(char[] chars, int start, int stop, float x, float y, float z)
/*       */   {
/*  8971 */     if (this.recorder != null) this.recorder.text(chars, start, stop, x, y, z);
/*  8972 */     this.g.text(chars, start, stop, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(String str, float x1, float y1, float x2, float y2)
/*       */   {
/*  8990 */     if (this.recorder != null) this.recorder.text(str, x1, y1, x2, y2);
/*  8991 */     this.g.text(str, x1, y1, x2, y2);
/*       */   }
/*       */   
/*       */   public void text(String s, float x1, float y1, float x2, float y2, float z)
/*       */   {
/*  8996 */     if (this.recorder != null) this.recorder.text(s, x1, y1, x2, y2, z);
/*  8997 */     this.g.text(s, x1, y1, x2, y2, z);
/*       */   }
/*       */   
/*       */   public void text(int num, float x, float y)
/*       */   {
/*  9002 */     if (this.recorder != null) this.recorder.text(num, x, y);
/*  9003 */     this.g.text(num, x, y);
/*       */   }
/*       */   
/*       */   public void text(int num, float x, float y, float z)
/*       */   {
/*  9008 */     if (this.recorder != null) this.recorder.text(num, x, y, z);
/*  9009 */     this.g.text(num, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(float num, float x, float y)
/*       */   {
/*  9021 */     if (this.recorder != null) this.recorder.text(num, x, y);
/*  9022 */     this.g.text(num, x, y);
/*       */   }
/*       */   
/*       */   public void text(float num, float x, float y, float z)
/*       */   {
/*  9027 */     if (this.recorder != null) this.recorder.text(num, x, y, z);
/*  9028 */     this.g.text(num, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void pushMatrix()
/*       */   {
/*  9036 */     if (this.recorder != null) this.recorder.pushMatrix();
/*  9037 */     this.g.pushMatrix();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void popMatrix()
/*       */   {
/*  9045 */     if (this.recorder != null) this.recorder.popMatrix();
/*  9046 */     this.g.popMatrix();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void translate(float tx, float ty)
/*       */   {
/*  9054 */     if (this.recorder != null) this.recorder.translate(tx, ty);
/*  9055 */     this.g.translate(tx, ty);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void translate(float tx, float ty, float tz)
/*       */   {
/*  9063 */     if (this.recorder != null) this.recorder.translate(tx, ty, tz);
/*  9064 */     this.g.translate(tx, ty, tz);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotate(float angle)
/*       */   {
/*  9078 */     if (this.recorder != null) this.recorder.rotate(angle);
/*  9079 */     this.g.rotate(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotateX(float angle)
/*       */   {
/*  9087 */     if (this.recorder != null) this.recorder.rotateX(angle);
/*  9088 */     this.g.rotateX(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotateY(float angle)
/*       */   {
/*  9096 */     if (this.recorder != null) this.recorder.rotateY(angle);
/*  9097 */     this.g.rotateY(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotateZ(float angle)
/*       */   {
/*  9110 */     if (this.recorder != null) this.recorder.rotateZ(angle);
/*  9111 */     this.g.rotateZ(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotate(float angle, float vx, float vy, float vz)
/*       */   {
/*  9119 */     if (this.recorder != null) this.recorder.rotate(angle, vx, vy, vz);
/*  9120 */     this.g.rotate(angle, vx, vy, vz);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void scale(float s)
/*       */   {
/*  9128 */     if (this.recorder != null) this.recorder.scale(s);
/*  9129 */     this.g.scale(s);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void scale(float sx, float sy)
/*       */   {
/*  9140 */     if (this.recorder != null) this.recorder.scale(sx, sy);
/*  9141 */     this.g.scale(sx, sy);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void scale(float x, float y, float z)
/*       */   {
/*  9149 */     if (this.recorder != null) this.recorder.scale(x, y, z);
/*  9150 */     this.g.scale(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void shearX(float angle)
/*       */   {
/*  9158 */     if (this.recorder != null) this.recorder.shearX(angle);
/*  9159 */     this.g.shearX(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void shearY(float angle)
/*       */   {
/*  9167 */     if (this.recorder != null) this.recorder.shearY(angle);
/*  9168 */     this.g.shearY(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void resetMatrix()
/*       */   {
/*  9176 */     if (this.recorder != null) this.recorder.resetMatrix();
/*  9177 */     this.g.resetMatrix();
/*       */   }
/*       */   
/*       */   public void applyMatrix(PMatrix source)
/*       */   {
/*  9182 */     if (this.recorder != null) this.recorder.applyMatrix(source);
/*  9183 */     this.g.applyMatrix(source);
/*       */   }
/*       */   
/*       */   public void applyMatrix(PMatrix2D source)
/*       */   {
/*  9188 */     if (this.recorder != null) this.recorder.applyMatrix(source);
/*  9189 */     this.g.applyMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*       */   {
/*  9198 */     if (this.recorder != null) this.recorder.applyMatrix(n00, n01, n02, n10, n11, n12);
/*  9199 */     this.g.applyMatrix(n00, n01, n02, n10, n11, n12);
/*       */   }
/*       */   
/*       */   public void applyMatrix(PMatrix3D source)
/*       */   {
/*  9204 */     if (this.recorder != null) this.recorder.applyMatrix(source);
/*  9205 */     this.g.applyMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*       */   {
/*  9216 */     if (this.recorder != null) this.recorder.applyMatrix(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
/*  9217 */     this.g.applyMatrix(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
/*       */   }
/*       */   
/*       */   public PMatrix getMatrix()
/*       */   {
/*  9222 */     return this.g.getMatrix();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PMatrix2D getMatrix(PMatrix2D target)
/*       */   {
/*  9231 */     return this.g.getMatrix(target);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PMatrix3D getMatrix(PMatrix3D target)
/*       */   {
/*  9240 */     return this.g.getMatrix(target);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setMatrix(PMatrix source)
/*       */   {
/*  9248 */     if (this.recorder != null) this.recorder.setMatrix(source);
/*  9249 */     this.g.setMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setMatrix(PMatrix2D source)
/*       */   {
/*  9257 */     if (this.recorder != null) this.recorder.setMatrix(source);
/*  9258 */     this.g.setMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setMatrix(PMatrix3D source)
/*       */   {
/*  9266 */     if (this.recorder != null) this.recorder.setMatrix(source);
/*  9267 */     this.g.setMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void printMatrix()
/*       */   {
/*  9275 */     if (this.recorder != null) this.recorder.printMatrix();
/*  9276 */     this.g.printMatrix();
/*       */   }
/*       */   
/*       */   public void beginCamera()
/*       */   {
/*  9281 */     if (this.recorder != null) this.recorder.beginCamera();
/*  9282 */     this.g.beginCamera();
/*       */   }
/*       */   
/*       */   public void endCamera()
/*       */   {
/*  9287 */     if (this.recorder != null) this.recorder.endCamera();
/*  9288 */     this.g.endCamera();
/*       */   }
/*       */   
/*       */   public void camera()
/*       */   {
/*  9293 */     if (this.recorder != null) this.recorder.camera();
/*  9294 */     this.g.camera();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
/*       */   {
/*  9301 */     if (this.recorder != null) this.recorder.camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
/*  9302 */     this.g.camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
/*       */   }
/*       */   
/*       */   public void printCamera()
/*       */   {
/*  9307 */     if (this.recorder != null) this.recorder.printCamera();
/*  9308 */     this.g.printCamera();
/*       */   }
/*       */   
/*       */   public void ortho()
/*       */   {
/*  9313 */     if (this.recorder != null) this.recorder.ortho();
/*  9314 */     this.g.ortho();
/*       */   }
/*       */   
/*       */ 
/*       */   public void ortho(float left, float right, float bottom, float top)
/*       */   {
/*  9320 */     if (this.recorder != null) this.recorder.ortho(left, right, bottom, top);
/*  9321 */     this.g.ortho(left, right, bottom, top);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void ortho(float left, float right, float bottom, float top, float near, float far)
/*       */   {
/*  9328 */     if (this.recorder != null) this.recorder.ortho(left, right, bottom, top, near, far);
/*  9329 */     this.g.ortho(left, right, bottom, top, near, far);
/*       */   }
/*       */   
/*       */   public void perspective()
/*       */   {
/*  9334 */     if (this.recorder != null) this.recorder.perspective();
/*  9335 */     this.g.perspective();
/*       */   }
/*       */   
/*       */   public void perspective(float fovy, float aspect, float zNear, float zFar)
/*       */   {
/*  9340 */     if (this.recorder != null) this.recorder.perspective(fovy, aspect, zNear, zFar);
/*  9341 */     this.g.perspective(fovy, aspect, zNear, zFar);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void frustum(float left, float right, float bottom, float top, float near, float far)
/*       */   {
/*  9348 */     if (this.recorder != null) this.recorder.frustum(left, right, bottom, top, near, far);
/*  9349 */     this.g.frustum(left, right, bottom, top, near, far);
/*       */   }
/*       */   
/*       */   public void printProjection()
/*       */   {
/*  9354 */     if (this.recorder != null) this.recorder.printProjection();
/*  9355 */     this.g.printProjection();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenX(float x, float y)
/*       */   {
/*  9365 */     return this.g.screenX(x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenY(float x, float y)
/*       */   {
/*  9375 */     return this.g.screenY(x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenX(float x, float y, float z)
/*       */   {
/*  9387 */     return this.g.screenX(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenY(float x, float y, float z)
/*       */   {
/*  9399 */     return this.g.screenY(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenZ(float x, float y, float z)
/*       */   {
/*  9415 */     return this.g.screenZ(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float modelX(float x, float y, float z)
/*       */   {
/*  9429 */     return this.g.modelX(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public float modelY(float x, float y, float z)
/*       */   {
/*  9437 */     return this.g.modelY(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public float modelZ(float x, float y, float z)
/*       */   {
/*  9445 */     return this.g.modelZ(x, y, z);
/*       */   }
/*       */   
/*       */   public void pushStyle()
/*       */   {
/*  9450 */     if (this.recorder != null) this.recorder.pushStyle();
/*  9451 */     this.g.pushStyle();
/*       */   }
/*       */   
/*       */   public void popStyle()
/*       */   {
/*  9456 */     if (this.recorder != null) this.recorder.popStyle();
/*  9457 */     this.g.popStyle();
/*       */   }
/*       */   
/*       */   public void style(PStyle s)
/*       */   {
/*  9462 */     if (this.recorder != null) this.recorder.style(s);
/*  9463 */     this.g.style(s);
/*       */   }
/*       */   
/*       */   public void strokeWeight(float weight)
/*       */   {
/*  9468 */     if (this.recorder != null) this.recorder.strokeWeight(weight);
/*  9469 */     this.g.strokeWeight(weight);
/*       */   }
/*       */   
/*       */   public void strokeJoin(int join)
/*       */   {
/*  9474 */     if (this.recorder != null) this.recorder.strokeJoin(join);
/*  9475 */     this.g.strokeJoin(join);
/*       */   }
/*       */   
/*       */   public void strokeCap(int cap)
/*       */   {
/*  9480 */     if (this.recorder != null) this.recorder.strokeCap(cap);
/*  9481 */     this.g.strokeCap(cap);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noStroke()
/*       */   {
/*  9494 */     if (this.recorder != null) this.recorder.noStroke();
/*  9495 */     this.g.noStroke();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void stroke(int rgb)
/*       */   {
/*  9506 */     if (this.recorder != null) this.recorder.stroke(rgb);
/*  9507 */     this.g.stroke(rgb);
/*       */   }
/*       */   
/*       */   public void stroke(int rgb, float alpha)
/*       */   {
/*  9512 */     if (this.recorder != null) this.recorder.stroke(rgb, alpha);
/*  9513 */     this.g.stroke(rgb, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void stroke(float gray)
/*       */   {
/*  9522 */     if (this.recorder != null) this.recorder.stroke(gray);
/*  9523 */     this.g.stroke(gray);
/*       */   }
/*       */   
/*       */   public void stroke(float gray, float alpha)
/*       */   {
/*  9528 */     if (this.recorder != null) this.recorder.stroke(gray, alpha);
/*  9529 */     this.g.stroke(gray, alpha);
/*       */   }
/*       */   
/*       */   public void stroke(float x, float y, float z)
/*       */   {
/*  9534 */     if (this.recorder != null) this.recorder.stroke(x, y, z);
/*  9535 */     this.g.stroke(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void stroke(float x, float y, float z, float a)
/*       */   {
/*  9562 */     if (this.recorder != null) this.recorder.stroke(x, y, z, a);
/*  9563 */     this.g.stroke(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noTint()
/*       */   {
/*  9575 */     if (this.recorder != null) this.recorder.noTint();
/*  9576 */     this.g.noTint();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void tint(int rgb)
/*       */   {
/*  9584 */     if (this.recorder != null) this.recorder.tint(rgb);
/*  9585 */     this.g.tint(rgb);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void tint(int rgb, float alpha)
/*       */   {
/*  9595 */     if (this.recorder != null) this.recorder.tint(rgb, alpha);
/*  9596 */     this.g.tint(rgb, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void tint(float gray)
/*       */   {
/*  9604 */     if (this.recorder != null) this.recorder.tint(gray);
/*  9605 */     this.g.tint(gray);
/*       */   }
/*       */   
/*       */   public void tint(float gray, float alpha)
/*       */   {
/*  9610 */     if (this.recorder != null) this.recorder.tint(gray, alpha);
/*  9611 */     this.g.tint(gray, alpha);
/*       */   }
/*       */   
/*       */   public void tint(float x, float y, float z)
/*       */   {
/*  9616 */     if (this.recorder != null) this.recorder.tint(x, y, z);
/*  9617 */     this.g.tint(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void tint(float x, float y, float z, float a)
/*       */   {
/*  9651 */     if (this.recorder != null) this.recorder.tint(x, y, z, a);
/*  9652 */     this.g.tint(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noFill()
/*       */   {
/*  9666 */     if (this.recorder != null) this.recorder.noFill();
/*  9667 */     this.g.noFill();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void fill(int rgb)
/*       */   {
/*  9676 */     if (this.recorder != null) this.recorder.fill(rgb);
/*  9677 */     this.g.fill(rgb);
/*       */   }
/*       */   
/*       */   public void fill(int rgb, float alpha)
/*       */   {
/*  9682 */     if (this.recorder != null) this.recorder.fill(rgb, alpha);
/*  9683 */     this.g.fill(rgb, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void fill(float gray)
/*       */   {
/*  9691 */     if (this.recorder != null) this.recorder.fill(gray);
/*  9692 */     this.g.fill(gray);
/*       */   }
/*       */   
/*       */   public void fill(float gray, float alpha)
/*       */   {
/*  9697 */     if (this.recorder != null) this.recorder.fill(gray, alpha);
/*  9698 */     this.g.fill(gray, alpha);
/*       */   }
/*       */   
/*       */   public void fill(float x, float y, float z)
/*       */   {
/*  9703 */     if (this.recorder != null) this.recorder.fill(x, y, z);
/*  9704 */     this.g.fill(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void fill(float x, float y, float z, float a)
/*       */   {
/*  9727 */     if (this.recorder != null) this.recorder.fill(x, y, z, a);
/*  9728 */     this.g.fill(x, y, z, a);
/*       */   }
/*       */   
/*       */   public void ambient(int rgb)
/*       */   {
/*  9733 */     if (this.recorder != null) this.recorder.ambient(rgb);
/*  9734 */     this.g.ambient(rgb);
/*       */   }
/*       */   
/*       */   public void ambient(float gray)
/*       */   {
/*  9739 */     if (this.recorder != null) this.recorder.ambient(gray);
/*  9740 */     this.g.ambient(gray);
/*       */   }
/*       */   
/*       */   public void ambient(float x, float y, float z)
/*       */   {
/*  9745 */     if (this.recorder != null) this.recorder.ambient(x, y, z);
/*  9746 */     this.g.ambient(x, y, z);
/*       */   }
/*       */   
/*       */   public void specular(int rgb)
/*       */   {
/*  9751 */     if (this.recorder != null) this.recorder.specular(rgb);
/*  9752 */     this.g.specular(rgb);
/*       */   }
/*       */   
/*       */   public void specular(float gray)
/*       */   {
/*  9757 */     if (this.recorder != null) this.recorder.specular(gray);
/*  9758 */     this.g.specular(gray);
/*       */   }
/*       */   
/*       */   public void specular(float x, float y, float z)
/*       */   {
/*  9763 */     if (this.recorder != null) this.recorder.specular(x, y, z);
/*  9764 */     this.g.specular(x, y, z);
/*       */   }
/*       */   
/*       */   public void shininess(float shine)
/*       */   {
/*  9769 */     if (this.recorder != null) this.recorder.shininess(shine);
/*  9770 */     this.g.shininess(shine);
/*       */   }
/*       */   
/*       */   public void emissive(int rgb)
/*       */   {
/*  9775 */     if (this.recorder != null) this.recorder.emissive(rgb);
/*  9776 */     this.g.emissive(rgb);
/*       */   }
/*       */   
/*       */   public void emissive(float gray)
/*       */   {
/*  9781 */     if (this.recorder != null) this.recorder.emissive(gray);
/*  9782 */     this.g.emissive(gray);
/*       */   }
/*       */   
/*       */   public void emissive(float x, float y, float z)
/*       */   {
/*  9787 */     if (this.recorder != null) this.recorder.emissive(x, y, z);
/*  9788 */     this.g.emissive(x, y, z);
/*       */   }
/*       */   
/*       */   public void lights()
/*       */   {
/*  9793 */     if (this.recorder != null) this.recorder.lights();
/*  9794 */     this.g.lights();
/*       */   }
/*       */   
/*       */   public void noLights()
/*       */   {
/*  9799 */     if (this.recorder != null) this.recorder.noLights();
/*  9800 */     this.g.noLights();
/*       */   }
/*       */   
/*       */   public void ambientLight(float red, float green, float blue)
/*       */   {
/*  9805 */     if (this.recorder != null) this.recorder.ambientLight(red, green, blue);
/*  9806 */     this.g.ambientLight(red, green, blue);
/*       */   }
/*       */   
/*       */ 
/*       */   public void ambientLight(float red, float green, float blue, float x, float y, float z)
/*       */   {
/*  9812 */     if (this.recorder != null) this.recorder.ambientLight(red, green, blue, x, y, z);
/*  9813 */     this.g.ambientLight(red, green, blue, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */   public void directionalLight(float red, float green, float blue, float nx, float ny, float nz)
/*       */   {
/*  9819 */     if (this.recorder != null) this.recorder.directionalLight(red, green, blue, nx, ny, nz);
/*  9820 */     this.g.directionalLight(red, green, blue, nx, ny, nz);
/*       */   }
/*       */   
/*       */ 
/*       */   public void pointLight(float red, float green, float blue, float x, float y, float z)
/*       */   {
/*  9826 */     if (this.recorder != null) this.recorder.pointLight(red, green, blue, x, y, z);
/*  9827 */     this.g.pointLight(red, green, blue, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void spotLight(float red, float green, float blue, float x, float y, float z, float nx, float ny, float nz, float angle, float concentration)
/*       */   {
/*  9835 */     if (this.recorder != null) this.recorder.spotLight(red, green, blue, x, y, z, nx, ny, nz, angle, concentration);
/*  9836 */     this.g.spotLight(red, green, blue, x, y, z, nx, ny, nz, angle, concentration);
/*       */   }
/*       */   
/*       */   public void lightFalloff(float constant, float linear, float quadratic)
/*       */   {
/*  9841 */     if (this.recorder != null) this.recorder.lightFalloff(constant, linear, quadratic);
/*  9842 */     this.g.lightFalloff(constant, linear, quadratic);
/*       */   }
/*       */   
/*       */   public void lightSpecular(float x, float y, float z)
/*       */   {
/*  9847 */     if (this.recorder != null) this.recorder.lightSpecular(x, y, z);
/*  9848 */     this.g.lightSpecular(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(int rgb)
/*       */   {
/*  9866 */     if (this.recorder != null) this.recorder.background(rgb);
/*  9867 */     this.g.background(rgb);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(int rgb, float alpha)
/*       */   {
/*  9875 */     if (this.recorder != null) this.recorder.background(rgb, alpha);
/*  9876 */     this.g.background(rgb, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(float gray)
/*       */   {
/*  9885 */     if (this.recorder != null) this.recorder.background(gray);
/*  9886 */     this.g.background(gray);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(float gray, float alpha)
/*       */   {
/*  9896 */     if (this.recorder != null) this.recorder.background(gray, alpha);
/*  9897 */     this.g.background(gray, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(float x, float y, float z)
/*       */   {
/*  9906 */     if (this.recorder != null) this.recorder.background(x, y, z);
/*  9907 */     this.g.background(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(float x, float y, float z, float a)
/*       */   {
/*  9938 */     if (this.recorder != null) this.recorder.background(x, y, z, a);
/*  9939 */     this.g.background(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(PImage image)
/*       */   {
/*  9956 */     if (this.recorder != null) this.recorder.background(image);
/*  9957 */     this.g.background(image);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void colorMode(int mode)
/*       */   {
/*  9966 */     if (this.recorder != null) this.recorder.colorMode(mode);
/*  9967 */     this.g.colorMode(mode);
/*       */   }
/*       */   
/*       */   public void colorMode(int mode, float max)
/*       */   {
/*  9972 */     if (this.recorder != null) this.recorder.colorMode(mode, max);
/*  9973 */     this.g.colorMode(mode, max);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void colorMode(int mode, float maxX, float maxY, float maxZ)
/*       */   {
/*  9987 */     if (this.recorder != null) this.recorder.colorMode(mode, maxX, maxY, maxZ);
/*  9988 */     this.g.colorMode(mode, maxX, maxY, maxZ);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void colorMode(int mode, float maxX, float maxY, float maxZ, float maxA)
/*       */   {
/* 10007 */     if (this.recorder != null) this.recorder.colorMode(mode, maxX, maxY, maxZ, maxA);
/* 10008 */     this.g.colorMode(mode, maxX, maxY, maxZ, maxA);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float alpha(int what)
/*       */   {
/* 10019 */     return this.g.alpha(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float red(int what)
/*       */   {
/* 10037 */     return this.g.red(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float green(int what)
/*       */   {
/* 10055 */     return this.g.green(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float blue(int what)
/*       */   {
/* 10072 */     return this.g.blue(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float hue(int what)
/*       */   {
/* 10089 */     return this.g.hue(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float saturation(int what)
/*       */   {
/* 10106 */     return this.g.saturation(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float brightness(int what)
/*       */   {
/* 10124 */     return this.g.brightness(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int lerpColor(int c1, int c2, float amt)
/*       */   {
/* 10140 */     return this.g.lerpColor(c1, c2, amt);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int lerpColor(int c1, int c2, float amt, int mode)
/*       */   {
/* 10149 */     return PGraphics.lerpColor(c1, c2, amt, mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void showDepthWarning(String method)
/*       */   {
/* 10158 */     PGraphics.showDepthWarning(method);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void showDepthWarningXYZ(String method)
/*       */   {
/* 10168 */     PGraphics.showDepthWarningXYZ(method);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void showMethodWarning(String method)
/*       */   {
/* 10176 */     PGraphics.showMethodWarning(method);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void showVariationWarning(String str)
/*       */   {
/* 10186 */     PGraphics.showVariationWarning(str);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void showMissingWarning(String method)
/*       */   {
/* 10196 */     PGraphics.showMissingWarning(method);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean displayable()
/*       */   {
/* 10209 */     return this.g.displayable();
/*       */   }
/*       */   
/*       */   public void screenBlend(int mode)
/*       */   {
/* 10214 */     if (this.recorder != null) this.recorder.screenBlend(mode);
/* 10215 */     this.g.screenBlend(mode);
/*       */   }
/*       */   
/*       */   public void textureBlend(int mode)
/*       */   {
/* 10220 */     if (this.recorder != null) this.recorder.textureBlend(mode);
/* 10221 */     this.g.textureBlend(mode);
/*       */   }
/*       */   
/*       */   public boolean isRecording()
/*       */   {
/* 10226 */     return this.g.isRecording();
/*       */   }
/*       */   
/*       */   public void mergeShapes(boolean val)
/*       */   {
/* 10231 */     if (this.recorder != null) this.recorder.mergeShapes(val);
/* 10232 */     this.g.mergeShapes(val);
/*       */   }
/*       */   
/*       */   public void shapeName(String name)
/*       */   {
/* 10237 */     if (this.recorder != null) this.recorder.shapeName(name);
/* 10238 */     this.g.shapeName(name);
/*       */   }
/*       */   
/*       */   public void autoNormal(boolean auto)
/*       */   {
/* 10243 */     if (this.recorder != null) this.recorder.autoNormal(auto);
/* 10244 */     this.g.autoNormal(auto);
/*       */   }
/*       */   
/*       */   public void matrixMode(int mode)
/*       */   {
/* 10249 */     if (this.recorder != null) this.recorder.matrixMode(mode);
/* 10250 */     this.g.matrixMode(mode);
/*       */   }
/*       */   
/*       */   public void beginText()
/*       */   {
/* 10255 */     if (this.recorder != null) this.recorder.beginText();
/* 10256 */     this.g.beginText();
/*       */   }
/*       */   
/*       */   public void endText()
/*       */   {
/* 10261 */     if (this.recorder != null) this.recorder.endText();
/* 10262 */     this.g.endText();
/*       */   }
/*       */   
/*       */   public void texture(PImage... images)
/*       */   {
/* 10267 */     if (this.recorder != null) this.recorder.texture(images);
/* 10268 */     this.g.texture(images);
/*       */   }
/*       */   
/*       */   public void vertex(float... values)
/*       */   {
/* 10273 */     if (this.recorder != null) this.recorder.vertex(values);
/* 10274 */     this.g.vertex(values);
/*       */   }
/*       */   
/*       */   public void delete()
/*       */   {
/* 10279 */     if (this.recorder != null) this.recorder.delete();
/* 10280 */     this.g.delete();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setCache(PGraphics renderer, Object storage)
/*       */   {
/* 10294 */     if (this.recorder != null) this.recorder.setCache(renderer, storage);
/* 10295 */     this.g.setCache(renderer, storage);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Object getCache(PGraphics renderer)
/*       */   {
/* 10308 */     return this.g.getCache(renderer);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void removeCache(PGraphics renderer)
/*       */   {
/* 10317 */     if (this.recorder != null) this.recorder.removeCache(renderer);
/* 10318 */     this.g.removeCache(renderer);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setParams(PGraphics renderer, Object params)
/*       */   {
/* 10329 */     if (this.recorder != null) this.recorder.setParams(renderer, params);
/* 10330 */     this.g.setParams(renderer, params);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Object getParams(PGraphics renderer)
/*       */   {
/* 10340 */     return this.g.getParams(renderer);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void removeParams(PGraphics renderer)
/*       */   {
/* 10349 */     if (this.recorder != null) this.recorder.removeParams(renderer);
/* 10350 */     this.g.removeParams(renderer);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int get(int x, int y)
/*       */   {
/* 10373 */     return this.g.get(x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage get(int x, int y, int w, int h)
/*       */   {
/* 10394 */     return this.g.get(x, y, w, h);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage get()
/*       */   {
/* 10402 */     return this.g.get();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void set(int x, int y, int c)
/*       */   {
/* 10420 */     if (this.recorder != null) this.recorder.set(x, y, c);
/* 10421 */     this.g.set(x, y, c);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void set(int x, int y, PImage src)
/*       */   {
/* 10431 */     if (this.recorder != null) this.recorder.set(x, y, src);
/* 10432 */     this.g.set(x, y, src);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mask(int[] maskArray)
/*       */   {
/* 10452 */     if (this.recorder != null) this.recorder.mask(maskArray);
/* 10453 */     this.g.mask(maskArray);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mask(PImage maskImg)
/*       */   {
/* 10469 */     if (this.recorder != null) this.recorder.mask(maskImg);
/* 10470 */     this.g.mask(maskImg);
/*       */   }
/*       */   
/*       */   public void filter(int kind)
/*       */   {
/* 10475 */     if (this.recorder != null) this.recorder.filter(kind);
/* 10476 */     this.g.filter(kind);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void filter(int kind, float param)
/*       */   {
/* 10506 */     if (this.recorder != null) this.recorder.filter(kind, param);
/* 10507 */     this.g.filter(kind, param);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*       */   {
/* 10517 */     if (this.recorder != null) this.recorder.copy(sx, sy, sw, sh, dx, dy, dw, dh);
/* 10518 */     this.g.copy(sx, sy, sw, sh, dx, dy, dw, dh);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void copy(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*       */   {
/* 10544 */     if (this.recorder != null) this.recorder.copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
/* 10545 */     this.g.copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int blendColor(int c1, int c2, int mode)
/*       */   {
/* 10614 */     return PGraphics.blendColor(c1, c2, mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void blend(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode)
/*       */   {
/* 10625 */     if (this.recorder != null) this.recorder.blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
/* 10626 */     this.g.blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void blend(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode)
/*       */   {
/* 10669 */     if (this.recorder != null) this.recorder.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
/* 10670 */     this.g.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
/*       */   }
/*       */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PApplet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */