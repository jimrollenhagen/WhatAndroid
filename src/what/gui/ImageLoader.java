package what.gui;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 *
 */
public class ImageLoader {
	public static Bitmap loadBitmap(String s) throws IOException {
		URL url = new URL(s);
		Bitmap b = BitmapFactory.decodeStream(new PatchInputStream(url.openStream()));
		return b;
	}
}