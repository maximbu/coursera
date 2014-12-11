package max.somenamefor.assigment2_app1;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;

public class JabberwockyActivity extends Activity {

	WebView webView;
	MediaPlayer music;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jabberwocky);
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setBuiltInZoomControls(true);
		//webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/jabberwocky.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jabberwocky, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onResume() {
		Log.d("test","onResume");
		music = MediaPlayer.create(this,R.raw.enigma_why);
		music.start();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.d("test","onPause");
		music.stop();
		music.release();
		super.onPause();
	}

	public void openWiki(View v) {
		String url = "http://en.wikipedia.org/wiki/Jabberwocky";
		openURL(url);
	}
	
	public void openImage(View v) {
		webView.loadDataWithBaseURL("file:///android_res/drawable/", "<img src='rsz_img_8003.jpg' />", "text/html", "utf-8", null);
	}
	

	private void openURL(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

}
