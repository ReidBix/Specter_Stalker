package game.dev.specter_stalker.model;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 * java.x = Available through the Java interface
 */

import java.util.HashMap;
import java.util.Map;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/*
 * Class for creating words, numbers, symbols in the Legend of Zelda font
 */
public class Glyphs {

	/*
	 * Initialized variables for use in later code
	 */

	// Used for displaying errors with methods in this class on LogCat
	private static final String TAG = Glyphs.class.getSimpleName();
	// Bitmap for the glyph's sprite(s)
	private Bitmap bitmap;
	// Map of characters to bitmaps/sprites
	private Map<Character, Bitmap> glyphs = new HashMap<Character, Bitmap>(82);
	// Width of a single glyph
	private int width;
	// Height of a single glyph
	private int height;
	// Lower case letters
	private char[] charactersL = new char[] { 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' };
	// Upper case letters
	private char[] charactersU = new char[] { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	// Numbers
	private char[] numbers = new char[] { '1', '2', '3', '4', '5', '6', '7',
			'8', '9', '0' };
	// Symbols
	private char[] symbols = new char[] { '!', '@', '#', '$', '%', '^', '&',
			'*', '(', ')', '-', '+', '=', '\'', '"', '.', ',', ':', ';', ' ' };

	/*
	 * Constructor for the Glyphs, takes the sprite/bitmap of glyphs
	 */
	public Glyphs(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
		// EVEN THOUGH THE LETTERS ARE SUPPOSED TO BE 18x18 PIXELS, EITHER JAVA
		// OR SOMETHING ELSE IS WAY DUMB AND IT HAS TO BE 24x24 TO WORK PROPERLY
		this.width = 24;
		this.height = 24;
		// For the 26 lower case letters...
		for (int i = 0; i < 26; i++) {
			// Put the 26 letters from the Bitmap
			glyphs.put(charactersL[i], Bitmap.createBitmap(bitmap,
					0 + (i * width), 0, width, height));
		}
		// For the 26 upper case letters...
		for (int i = 0; i < 26; i++) {
			// Put the 26 letters from the Bitmap (EXACTLY THE SAME AS LOWER
			// CASE)
			glyphs.put(charactersU[i], Bitmap.createBitmap(bitmap,
					0 + (i * width), 0, width, height));
		}
		Log.d(TAG, "Characters initialised");
		// For the 10 numbers...
		for (int i = 0; i < 10; ++i) {
			// Put the 10 numbers from the Bitmap (25 is for the starting y
			// point, which is if you look at the bitmap, it is 1 pixel down
			// from the letters so therefore 24 + 1 = 25)
			glyphs.put(numbers[i], Bitmap.createBitmap(bitmap, 0 + (i * width),
					25, width, height));
		}
		Log.d(TAG, "Numbers initialised");
		// For the 20 symbols (including space/blank)
		for (int i = 0; i < 20; ++i) {
			// Put the 20 symbols from the Bitmap (50 from the starting y
			// point, because 25 + 25 = 50
			glyphs.put(symbols[i], Bitmap.createBitmap(bitmap, 0 + (i * width),
					50, width, height));
		}
		Log.d(TAG, "Symbols initialised");
	}

	/*
	 * Method for drawing a String given a canvas, some text, and an x and y point
	 */
	public void drawString(Canvas canvas, String text, int x, int y) {
		// As long as the canvas isn't null...
		if (canvas == null) {
			Log.d(TAG, "Canvas is null");
		}
		// For however long the text is..
		for (int i = 0; i < text.length(); ++i) {
			// Get the characters and...
			Character c = text.charAt(i);
			// Get the character from the Map of glyphs...
			if (glyphs.get(c) != null) {
				// and draw it
				canvas.drawBitmap(glyphs.get(c), x + (i * width), y, null);
			}
		}
	}

	/*
	 * Getters and Setters
	 */
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public Map<Character, Bitmap> getGlyphs() {
		return glyphs;
	}

	public void setGlyphs(Map<Character, Bitmap> glyphs) {
		this.glyphs = glyphs;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public char[] getCharactersL() {
		return charactersL;
	}

	public void setCharactersL(char[] charactersL) {
		this.charactersL = charactersL;
	}

	public char[] getCharactersU() {
		return charactersU;
	}

	public void setCharactersU(char[] charactersU) {
		this.charactersU = charactersU;
	}

	public char[] getNumbers() {
		return numbers;
	}

	public void setNumbers(char[] numbers) {
		this.numbers = numbers;
	}

	public char[] getSymbols() {
		return symbols;
	}

	public void setSymbols(char[] symbols) {
		this.symbols = symbols;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
