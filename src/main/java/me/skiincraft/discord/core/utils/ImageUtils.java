package me.skiincraft.discord.core.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;

import me.skiincraft.discord.core.apis.ColorThiefMMCQ;
import me.skiincraft.discord.core.apis.ColorThiefMMCQ.CMap;
import me.skiincraft.discord.core.apis.ColorThiefMMCQ.DenormalizedVBox;

public class ImageUtils {

	public static Color getPredominatColor(BufferedImage image) {
		try {
			BufferedImage img = new BufferedImage(40, 40, 2);
			Graphics2D graph = img.createGraphics();
			graph.drawImage(image, 0, 0, 40, 40, null);
			graph.dispose();

			CMap result;

			result = ColorThiefMMCQ.computeMap(img, 3);

			Iterator<DenormalizedVBox> boxes = result.getBoxes().iterator();

			int[] cor = boxes.next().getColor();
			return new Color(cor[0], cor[1], cor[2]);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean existsImage(URL url) {
		try {
			BufferedImage b = ImageIO.read(url);
			b.createGraphics();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static boolean existsImage(String url) {
		try {
			BufferedImage b = ImageIO.read(new URL(url));
			b.createGraphics();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
