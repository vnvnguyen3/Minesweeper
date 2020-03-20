import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 20, height = 20;
	
	public static BufferedImage blank;
	public static BufferedImage mine;
	public static BufferedImage one;
	public static BufferedImage two;
	public static BufferedImage three;
	public static BufferedImage four;
	public static BufferedImage five;
	public static BufferedImage six;
	public static BufferedImage seven;
	public static BufferedImage eight;
	public static BufferedImage cover;
	public static BufferedImage flag;
	public static BufferedImage smile;
	public static BufferedImage win;
	public static BufferedImage lose;
	public static BufferedImage explode;
	public static BufferedImage x;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("Tiles.png"));
		blank = sheet.crop(0, 0, width, height);
		mine = sheet.crop(width, 0, width, height);
		one = sheet.crop(width*2, 0, width, height);
		two = sheet.crop(width*3, 0, width, height);
		three = sheet.crop(width*4, 0, width, height);
		four = sheet.crop(0, height, width, height);
		five = sheet.crop(width, height, width, height);
		six = sheet.crop(width*2, height, width, height);
		seven = sheet.crop(width*3, height, width, height);
		eight = sheet.crop(width*4, height, width, height);
		cover = sheet.crop(0, height*2, width, height);
		flag = sheet.crop(width, height*2, width, height);
		smile = sheet.crop(width*2, height*2, width, height);
		win = sheet.crop(width*3, height*2, width, height);
		lose = sheet.crop(width*4, height*2, width, height);
		explode = sheet.crop(0, height*3, width, height);
		x = sheet.crop(width, height*3, width, height);
	}
}