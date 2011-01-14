package games.stendhal.client.gui.j2d;

import games.stendhal.client.sprite.ImageSprite;
import games.stendhal.client.sprite.Sprite;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class AchievementBoxFactory {
	private static final String ACHIEVEMENT_IMAGE_FOLDER = "data/sprites/achievements/";
	/** Background image. */
	private static final String BACKGROUND = "data/gui/banner_background.png";
	// These are dependent on the sprite
	/** Space to leave at the top of the sprite above the text. */
	private static final int TOP_MARGIN = 5;
	/** Space to leave at the sides of the sprite. */
	private static final int SIDE_MARGIN = 20;
	/** Space to leave at the bottom of the sprite below the text */
	private static final int BOTTOM_MARGIN = 25;
	
	private Graphics2D graphics;
	
	public AchievementBoxFactory(Graphics2D graphics) {
		this.graphics = graphics;
	}
	
	/**
	 * Create a sprite for a reached achievement
	 * @param title
	 * @param description
	 * @param category
	 * @return the drawn sprite
	 */
	public Sprite createAchievementBox(String title, String description, String category) {
		final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		// Get the category image
		// initialize category image with empty image in case loading the image fails
		BufferedImage categoryImage = gc.createCompatibleImage(32, 32, Transparency.BITMASK);
		try {
			categoryImage = ImageIO.read(new File(ACHIEVEMENT_IMAGE_FOLDER+category.toLowerCase(Locale.ENGLISH)+".png"));
		} catch (IOException e) {
			Logger.getLogger(AchievementBoxFactory.class).error("Error loading achievement box image.", e);
		}
		// Calculate size for the message box
		Font font = graphics.getFont();
		Font largeFont = font.deriveFont(20f);
		Rectangle2D titleRect = largeFont.getStringBounds(title, graphics.getFontRenderContext());
		Rectangle2D textRect = font.getStringBounds(description, graphics.getFontRenderContext());
		int width = (int) Math.max(titleRect.getWidth(), textRect.getWidth())+categoryImage.getWidth();
		int height = (int) Math.max(categoryImage.getHeight(), (titleRect.getHeight() + textRect.getHeight()));
		width += 3 * SIDE_MARGIN;
		height += TOP_MARGIN + BOTTOM_MARGIN;
		
		// Create the background sprite
		final BufferedImage image = gc.createCompatibleImage(width, height, Transparency.BITMASK);
		final Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setComposite(AlphaComposite.Src);
		BackgroundPainter bp = new BackgroundPainter(BACKGROUND);
		bp.paint(g2d, width, height);
		
		// Draw the texts
		g2d.setColor(Color.BLACK); 
		g2d.setFont(largeFont);
		g2d.drawString(title, SIDE_MARGIN + SIDE_MARGIN + categoryImage.getWidth(), TOP_MARGIN + (int) titleRect.getHeight());
		
		g2d.setFont(font);
		g2d.drawString(description, SIDE_MARGIN + SIDE_MARGIN + categoryImage.getWidth(), height - BOTTOM_MARGIN);
		
		// Draw the image
		g2d.setComposite(AlphaComposite.SrcOver);
		g2d.drawImage(categoryImage, SIDE_MARGIN, TOP_MARGIN, null);
		
		g2d.dispose();
		
		return new ImageSprite(image);
	}

}
