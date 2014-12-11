package assigment2.ex2.max.tictactoe;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Common {
	public static final String WON_GAMES = "Games Won";
	public static final String PLAYER_NAME = "Player Name";
	public static final String LEVEL = "Level";
	public static final String IMAGE = "Image";
	public static final String PREFS_NAME = "Tic Tac Toe Prefs";
	
	public static String BitmapToString(Bitmap bitmap)
	{
		if(bitmap == null)
		{
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object   
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT); 
	}
	
	public static Bitmap StringToBitmap(final String encoded)
	{
		if(encoded == null)
		{
			return null;
		}
		byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
	}
}
