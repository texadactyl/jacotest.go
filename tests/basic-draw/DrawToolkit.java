
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public final class DrawToolkit implements ActionListener,
									MouseListener, 
									MouseMotionListener,
									KeyListener {

	public static final Color BLACK = Color.BLACK;
	public static final Color BLUE = Color.BLUE;
	public static final Color CYAN = Color.CYAN;
	public static final Color DARK_GRAY = Color.DARK_GRAY;
	public static final Color GRAY = Color.GRAY;
	public static final Color GREEN  = Color.GREEN;
	public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
	public static final Color MAGENTA = Color.MAGENTA;
	public static final Color ORANGE = Color.ORANGE;
	public static final Color PINK = Color.PINK;
	public static final Color RED = Color.RED;
	public static final Color WHITE = Color.WHITE;
	public static final Color YELLOW = Color.YELLOW;
	public static final Color BOOK_BLUE = new Color(9, 90, 166);
	public static final Color BOOK_LIGHT_BLUE = new Color(103, 198, 243);
	public static final Color BOOK_RED = new Color(150, 35, 31);
	private static final Color DEFAULT_PEN_COLOR   = BLACK;
	private static final Color DEFAULT_CLEAR_COLOR = WHITE;

	private static Color penColor;
	private static final int DEFAULT_SIZE = 512;
	private static int width = DEFAULT_SIZE;
	private static int height = DEFAULT_SIZE;
	private static final double DEFAULT_PEN_RADIUS = 0.002;
	private static double penRadius;
	private static boolean defer = true;
	private static final double BORDER = 0.00;
	private static final double DEFAULT_XMIN = 0.0;
	private static final double DEFAULT_XMAX = 1.0;
	private static final double DEFAULT_YMIN = 0.0;
	private static final double DEFAULT_YMAX = 1.0;
	private static double xmin, ymin, xmax, ymax;

	private static Object mouseLock = new Object();
	private static Object keyLock = new Object();

	private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);

	private static Font font;

	private static BufferedImage offscreenImage, onscreenImage;
	private static Graphics2D offscreen, onscreen;

	private static DrawToolkit std = new DrawToolkit();

	private static JFrame frame;

	private static boolean mousePressed = false;
	private static double mouseX = 0;
	private static double mouseY = 0;

	private static LinkedList<Character> keysTyped = new LinkedList<Character>();

	private static TreeSet<Integer> keysDown = new TreeSet<Integer>();

	private static long nextDraw = -1;

	private static boolean isVisible = false;

	private DrawToolkit() {}

	public static void setCanvasSize(){
		setCanvasSize(DEFAULT_SIZE, DEFAULT_SIZE);
	}

	private static void initJFrame(boolean argIsVisible) {
		if (frame != null) {
			frame.setVisible(false);
			frame = null;
		}
		frame = new JFrame();
		frame.setVisible(isVisible);
		offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		offscreen = offscreenImage.createGraphics();
		onscreen = onscreenImage.createGraphics();
		setXscale();
		setYscale();
		offscreen.setColor(DEFAULT_CLEAR_COLOR);
		offscreen.fillRect(0, 0, width, height);
		setPenColor();
		setPenRadius();
		setFont();
		clear();
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		offscreen.addRenderingHints(hints);
		ImageIcon icon = new ImageIcon(onscreenImage);
		JLabel draw = new JLabel(icon);
		draw.addMouseListener(std);
		draw.addMouseMotionListener(std);
		frame.setContentPane(draw);
		frame.addKeyListener(std);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Standard Draw");
		frame.setJMenuBar(createMenuBar());
		frame.pack();

		if (argIsVisible) {
			frame.requestFocus();
			frame.setVisible(true);
		} else {
			frame.setVisible(false);
		}
	}

	public static void setCanvasSize(int canvasWidth, int canvasHeight) {

		if(width <= 0 || height <= 0) throw new IllegalArgumentException("setCanvasSize: width and height must be positive");

		width = canvasWidth;
		height = canvasHeight;

		initJFrame(isVisible);

	}

	public DrawToolkit(boolean argIsVisible) {
		isVisible = argIsVisible;
		initJFrame(argIsVisible);
	}

	private static JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenuItem menuItem1 = new JMenuItem(" Save...   ");
		menuItem1.addActionListener(std);
		menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		menu.add(menuItem1);
		return menuBar;
	}

	public static void setXscale(){
		setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
	}

	public static void setYscale(){
		setYscale(DEFAULT_XMIN, DEFAULT_XMAX);
	}

	public static void setScale(){
		setXscale();
		setYscale();
	}

	public static void setXscale(double min, double max){
		double size = max - min;
		if(size == 0.0) throw new IllegalArgumentException("setXscale: the min and max are the same");
		synchronized(mouseLock){
			xmin = min - BORDER * size;
			xmax = max + BORDER * size;
		}
	}

	public static void setYscale(double min, double max){
		double size = max - min;
		if(size == 0.0) throw new IllegalArgumentException("setYscale: the min and max are the same");
		synchronized(mouseLock){
			ymin = min - BORDER * size;
			ymax = max + BORDER * size;
		}
	}

	public static void setScale(double min, double max){
		double size = max - min;
		if(size == 0.0) throw new IllegalArgumentException("setScale (X and Y): the min and max are the same");
		synchronized(mouseLock){
			xmin = min - BORDER * size;
			xmax = max + BORDER * size;
			ymin = min - BORDER * size;
			ymax = max - BORDER * size;
		}
	}

	private static double scaleX(double x) { return width * (x - xmin) / (xmax - xmin); }
	private static double scaleY(double y) { return height * (ymax - y) / (ymax - ymin); }
	private static double factorX(double w) { return w * width / Math.abs(xmax - xmin); }
	private static double factorY(double h) { return h * height / Math.abs(ymax - ymin); }
	private static double userX(double x) { return xmin + x * (xmax - xmin) / width; }
	private static double userY(double y) { return ymax - y * (ymax - ymin) / height; }

	public static void clear(){
		clear(DEFAULT_CLEAR_COLOR);
	}


	public static void clear(Color color){
		offscreen.setColor(color);
		offscreen.fillRect(0, 0, width, height);
		offscreen.setColor(penColor);
		draw();
	}


	public static void setPenRadius(){
		setPenRadius(DEFAULT_PEN_RADIUS);
	}

	public static void setPenRadius(double radius){
		if(!(radius >= 0)) throw new IllegalArgumentException("setPenRadius: pen radius must be nonnegative");
		penRadius = radius;
		float scalePenRadius = (float)(radius * DEFAULT_SIZE);
		BasicStroke stroke = new BasicStroke(scalePenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		offscreen.setStroke(stroke);
	}


	public static Color getPenColor(){
		return penColor;
	}

	public static void setPenColor(){
		setPenColor(DEFAULT_PEN_COLOR);
	}

	public static void setPenColor(Color color){
		if(color == null) throw new NullPointerException("setPenColor");
		penColor = color;
		offscreen.setColor(penColor);
	}

	public static void setPenColor(int red, int green, int blue) {
		if (red   < 0 || red   >= 256) throw new IllegalArgumentException("amount of red must be between 0 and 255");
		if (green < 0 || green >= 256) throw new IllegalArgumentException("amount of green must be between 0 and 255");
		if (blue  < 0 || blue  >= 256) throw new IllegalArgumentException("amount of blue must be between 0 and 255");
		setPenColor(new Color(red, green, blue));
	}


	public static Font getFont(){
		return font;
	}

	public static void setFont(){
		setFont(DEFAULT_FONT);
	}

	public static void setFont(Font font){
		if(font == null) throw new NullPointerException("setFont");
		DrawToolkit.font = font;
	}

	public static void line(double x0, double y0, double x1, double y1){
		offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
		draw();
	}

	private static void pixel(double x, double y){
		offscreen.fillRect((int) Math.round(scaleX(x)), (int)Math.round(scaleY(y)), 1, 1);
	}

	public static void point(double x, double y){
		double xs = scaleX(x);
		double ys = scaleY(y);
		double r = penRadius;
		float scaledPenRadius = (float)(r*DEFAULT_SIZE);

		if(scaledPenRadius <=1) pixel(x,y);
		else offscreen.fill(new Ellipse2D.Double(xs-scaledPenRadius/2, ys - scaledPenRadius/2, scaledPenRadius, scaledPenRadius));
		draw();
	}

	public static void circle(double x, double y, double radius){
		if(!(radius >= 0)) throw new IllegalArgumentException("circle: radius must be nonnegative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*radius);
		double hs = factorY(2*radius);
		if(ws <= 1 && hs <= 1) pixel(x,y);
		else offscreen.draw(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
		draw();
	}

	public static void filledCircle(double x, double y, double radius) {
		if (!(radius >= 0)) throw new IllegalArgumentException("filledCircle: radius must be nonnegative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*radius);
		double hs = factorY(2*radius);
		if (ws <= 1 && hs <= 1) pixel(x, y);
		else offscreen.fill(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
		draw();
	}

	public static void ellipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
		if (!(semiMajorAxis >= 0)) throw new IllegalArgumentException("ellipse: semimajor axis must be nonnegative");
		if (!(semiMinorAxis >= 0)) throw new IllegalArgumentException("ellipse: semiminor axis must be nonnegative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*semiMajorAxis);
		double hs = factorY(2*semiMinorAxis);
		if (ws <= 1 && hs <= 1) pixel(x, y);
		else offscreen.draw(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
		draw();
	}

	public static void filledEllipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
		if (!(semiMajorAxis >= 0)) throw new IllegalArgumentException("filledEllipse: semimajor axis must be nonnegative");
		if (!(semiMinorAxis >= 0)) throw new IllegalArgumentException("filledEllipse: semiminor axis must be nonnegative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*semiMajorAxis);
		double hs = factorY(2*semiMinorAxis);
		if (ws <= 1 && hs <= 1) pixel(x, y);
		else offscreen.fill(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
		draw();
	}

	public static void arc(double x, double y, double radius, double angle1, double angle2) {
		if (radius < 0) throw new IllegalArgumentException("arc: radius must be nonnegative");
		while (angle2 < angle1) angle2 += 360;
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*radius);
		double hs = factorY(2*radius);
		if (ws <= 1 && hs <= 1) pixel(x, y);
		else offscreen.draw(new Arc2D.Double(xs - ws/2, ys - hs/2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
		draw();
	}

	public static void square(double x, double y, double halfLength) {
		if (halfLength < 0) throw new IllegalArgumentException("square: half length must be nonnegative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*halfLength);
		double hs = factorY(2*halfLength);
		if (ws <= 1 && hs <= 1) pixel(x, y);
		else offscreen.draw(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
		draw();
	}

	public static void filledSquare(double x, double y, double halfLength) {
		if (halfLength < 0) throw new IllegalArgumentException("filledSquare: half length must be nonnegative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*halfLength);
		double hs = factorY(2*halfLength);
		if (ws <= 1 && hs <= 1) pixel(x, y);
		else offscreen.fill(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
		draw();
	}

	public static void rectangle(double x, double y, double halfWidth, double halfHeight) {
		if (halfWidth  < 0) throw new IllegalArgumentException("rectangle: half width must be nonnegative");
		if (halfHeight  < 0) throw new IllegalArgumentException("rectangle: half height must be nonnegative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*halfWidth);
		double hs = factorY(2*halfHeight);
		if (ws <= 1 && hs <= 1) pixel(x, y);
		else offscreen.draw(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
		draw();
	}

	public static void filledRectangle(double x, double y, double halfWidth, double halfHeight) {
		if (halfWidth  < 0) throw new IllegalArgumentException("filledRectangle: half width must be nonnegative");
		if (halfHeight  < 0) throw new IllegalArgumentException("filledRectangle: half height must be nonnegative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2*halfWidth);
		double hs = factorY(2*halfHeight);
		if (ws <= 1 && hs <= 1) pixel(x, y);
		else offscreen.fill(new Rectangle2D.Double(xs - ws/2, ys - hs/2, ws, hs));
		draw();
	}

	public static void polygon(double[] x, double[] y) {
		if (x == null) throw new NullPointerException("polygon: x");
		if (y == null) throw new NullPointerException("polygon: y");
		int n1 = x.length;
		int n2 = y.length;
		if (n1 != n2) throw new IllegalArgumentException("polygon: x and y arrays must be of the same length");
		int n = n1;
		GeneralPath path = new GeneralPath();
		path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
		for (int i = 0; i < n; i++)
			path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
		path.closePath();
		offscreen.draw(path);
		draw();
	}

	public static void filledPolygon(double[] x, double[] y) {
		if (x == null) throw new NullPointerException("filledPolygon: x");
		if (y == null) throw new NullPointerException("filledPolygon: y");
		int n1 = x.length;
		int n2 = y.length;
		if (n1 != n2) throw new IllegalArgumentException("filledPolygon: arrays x and y must be of the same length");
		int n = n1;
		GeneralPath path = new GeneralPath();
		path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
		for (int i = 0; i < n; i++)
			path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
		path.closePath();
		offscreen.fill(path);
		draw();
	}

	private static Image getImage(String filename) {
		if (filename == null) throw new NullPointerException();

		ImageIcon icon = new ImageIcon(filename);

		if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
			try {
				URL url = new URL(filename);
				icon = new ImageIcon(url);
			}
			catch (Exception e) {
				/* not a url */
			}
		}

		if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
			URL url = DrawToolkit.class.getResource(filename);
			if (url != null)
				icon = new ImageIcon(url);
		}

		if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
			URL url = DrawToolkit.class.getResource("/" + filename);
			if (url == null) {
				String msg = String.format("getImage: image " + filename + " not found");
				throw new IllegalArgumentException(msg);
			}
			icon = new ImageIcon(url);
		}

		return icon.getImage();
	}

	public static void picture(double x, double y, String filename) {
		Image image = getImage(filename);
		double xs = scaleX(x);
		double ys = scaleY(y);
		int ws = image.getWidth(null);
		int hs = image.getHeight(null);
		if (ws < 0 || hs < 0) {
			String msg = String.format("picture-1: image " + filename + " is corrupt");
			throw new IllegalArgumentException(msg);
		}

		offscreen.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
		draw();
	}

	public static void picture(double x, double y, String filename, double degrees) {
		Image image = getImage(filename);
		double xs = scaleX(x);
		double ys = scaleY(y);
		int ws = image.getWidth(null);
		int hs = image.getHeight(null);
		if (ws < 0 || hs < 0) {
			String msg = String.format("picture-2: image " + filename + " is corrupt");
			throw new IllegalArgumentException(msg);
		}

		offscreen.rotate(Math.toRadians(-degrees), xs, ys);
		offscreen.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
		offscreen.rotate(Math.toRadians(+degrees), xs, ys);

		draw();
	}

	public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight) {
		Image image = getImage(filename);
		if (scaledWidth < 0) throw new IllegalArgumentException("picture-3: width is negative: " + scaledWidth);
		if (scaledHeight < 0) throw new IllegalArgumentException("picture-3: height is negative: " + scaledHeight);
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(scaledWidth);
		double hs = factorY(scaledHeight);
		if (ws < 0 || hs < 0) {
			String msg = String.format("picture-3: image " + filename + " is corrupt");
			throw new IllegalArgumentException(msg);
		}

		if (ws <= 1 && hs <= 1) pixel(x, y);
		else {
			offscreen.drawImage(image, (int) Math.round(xs - ws/2.0),
					(int) Math.round(ys - hs/2.0),
					(int) Math.round(ws),
					(int) Math.round(hs), null);
		}
		draw();
	}

	public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight, double degrees) {
		if (scaledWidth < 0) throw new IllegalArgumentException("picture-4: width is negative: " + scaledWidth);
		if (scaledHeight < 0) throw new IllegalArgumentException("picture-4: height is negative: " + scaledHeight);
		Image image = getImage(filename);
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(scaledWidth);
		double hs = factorY(scaledHeight);
		if (ws < 0 || hs < 0) {
			String msg = String.format("picture-4: image " + filename + " is corrupt");
			throw new IllegalArgumentException(msg);
		}

		if (ws <= 1 && hs <= 1) pixel(x, y);

		offscreen.rotate(Math.toRadians(-degrees), xs, ys);
		offscreen.drawImage(image, (int) Math.round(xs - ws/2.0),
				(int) Math.round(ys - hs/2.0),
				(int) Math.round(ws),
				(int) Math.round(hs), null);
		offscreen.rotate(Math.toRadians(+degrees), xs, ys);

		draw();
	}

	public static void text(double x, double y, String text) {
		if (text == null) throw new NullPointerException("text-1");
		offscreen.setFont(font);
		FontMetrics metrics = offscreen.getFontMetrics();
		double xs = scaleX(x);
		double ys = scaleY(y);
		int ws = metrics.stringWidth(text);
		int hs = metrics.getDescent();
		offscreen.drawString(text, (float) (xs - ws/2.0), (float) (ys + hs));
		draw();
	}

	public static void text(double x, double y, String text, double degrees) {
		if (text == null) throw new NullPointerException("text-2");
		double xs = scaleX(x);
		double ys = scaleY(y);
		offscreen.rotate(Math.toRadians(-degrees), xs, ys);
		text(x, y, text);
		offscreen.rotate(Math.toRadians(+degrees), xs, ys);
	}

	public static void textLeft(double x, double y, String text) {
		if (text == null) throw new NullPointerException("textLeft");
		offscreen.setFont(font);
		FontMetrics metrics = offscreen.getFontMetrics();
		double xs = scaleX(x);
		double ys = scaleY(y);
		int hs = metrics.getDescent();
		offscreen.drawString(text, (float) xs, (float) (ys + hs));
		draw();
	}

	public static void textRight(double x, double y, String text) {
		if (text == null) throw new NullPointerException("textRight");
		offscreen.setFont(font);
		FontMetrics metrics = offscreen.getFontMetrics();
		double xs = scaleX(x);
		double ys = scaleY(y);
		int ws = metrics.stringWidth(text);
		int hs = metrics.getDescent();
		offscreen.drawString(text, (float) (xs - ws), (float) (ys + hs));
		draw();
	}

	public static void show(int t) {
		// sleep until the next time we're allowed to draw
		long millis = System.currentTimeMillis();
		if (millis < nextDraw) {
			try {
				Thread.sleep(nextDraw - millis);
			}
			catch (InterruptedException e) {
				throw new AssertionError("show-1: Interrupted while sleeping");
			}
			millis = nextDraw;
		}

		defer = false;
		draw();
		defer = true;

		// when are we allowed to draw again
		nextDraw = millis + t;
	}

	public static void show() {
		defer = false;
		nextDraw = -1;
		draw();
	}


	private static void draw() {
		if (defer) return;
		onscreen.drawImage(offscreenImage, 0, 0, null);
		frame.repaint();
	}

	public static void save(String filename) {
		if (filename == null) throw new NullPointerException("save filename");
		File file = new File(filename);
		String suffix = filename.substring(filename.lastIndexOf('.') + 1);

		// png files
		if (suffix.toLowerCase().equals("png")) {
			try {
				ImageIO.write(onscreenImage, suffix, file);
			}
			catch (IOException e) {
                String msg = String.format("save-png(%s): ImageIO.write IOException\n", filename);
				throw new AssertionError(msg);
			}
		}

		// need to change from ARGB to RGB for JPEG
		// reference: http://archives.java.sun.com/cgi-bin/wa?A2=ind0404&L=java2d-interest&D=0&P=2727
		else if (suffix.toLowerCase().equals("jpg") || suffix.toLowerCase().equals("jpeg")) {
			WritableRaster raster = onscreenImage.getRaster();
			WritableRaster newRaster;
			newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[] {0, 1, 2});
			DirectColorModel cm = (DirectColorModel) onscreenImage.getColorModel();
			DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
					cm.getRedMask(),
					cm.getGreenMask(),
					cm.getBlueMask());
			BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false,  null);
			try {
				ImageIO.write(rgbBuffer, suffix, file);
			}
			catch (IOException e) {
				String msg = String.format("save-jpg(%s): ImageIO.write IOException\n", filename);
				throw new AssertionError(msg);
			}
		}

		else {
			String msg = String.format("save: Invalid image file type (%s) for %s\n", suffix, filename);
			throw new AssertionError(msg);
		}
	}

	public static boolean mousePressed(){
		synchronized(mouseLock){
			return mousePressed;
		}
	}

	public static double mouseX(){
		synchronized(mouseLock){
			return mouseX;
		}
	}

	public static double mouseY(){
		synchronized(mouseLock){
			return mouseY;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		synchronized (keyLock) {
			keysDown.add(arg0.getKeyCode());
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		synchronized (keyLock) {
			keysDown.remove(arg0.getKeyCode());
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		synchronized (keyLock) {
			keysTyped.addFirst(arg0.getKeyChar());
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		synchronized (mouseLock) {
			mouseX = DrawToolkit.userX(arg0.getX());
			mouseY = DrawToolkit.userY(arg0.getY());
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		synchronized (mouseLock) {
			mouseX = DrawToolkit.userX(arg0.getX());
			mouseY = DrawToolkit.userY(arg0.getY());
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		synchronized (mouseLock) {
			mouseX = DrawToolkit.userX(arg0.getX());
			mouseY = DrawToolkit.userY(arg0.getY());
			mousePressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		synchronized (mouseLock) {
			mousePressed = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		FileDialog chooser = new FileDialog(DrawToolkit.frame, "Use a .png or .jpg extension", FileDialog.SAVE);
		chooser.setVisible(true);
		String filename = chooser.getFile();
		if (filename != null) {
			DrawToolkit.save(chooser.getDirectory() + File.separator + chooser.getFile());
		}
	}

	public static boolean hasNextKeyTyped(){
		synchronized(keyLock){
			return !keysTyped.isEmpty();
		}
	}

	public static char nextKeyTyped(){
		synchronized(keyLock){
			if(keysTyped.isEmpty()){
				throw new NoSuchElementException("nextKeyTyped: your program has already processed all keystrikes");
			}
			return keysTyped.removeLast();
		}
	}

	public static boolean isKeyPressed(int keycode){
		synchronized(keyLock){
			return keysDown.contains(keycode);
		}
	}

}