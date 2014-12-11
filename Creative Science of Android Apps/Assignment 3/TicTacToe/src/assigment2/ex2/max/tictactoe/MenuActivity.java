package assigment2.ex2.max.tictactoe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {

	private Button mNewGame;
	private Button mSettings;
	private TextView mWonText;
	private SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		mNewGame = (Button) findViewById(R.id.NewGame);
		mSettings = (Button) findViewById(R.id.Settings);
		mWonText = (TextView) findViewById(R.id.Won);
		
		mPrefs = getSharedPreferences(Common.PREFS_NAME,MODE_PRIVATE);
		int gamesWon = mPrefs.getInt(Common.WON_GAMES, 0);
		mWonText.setText("You won "+gamesWon+" games" );
		setListeners();
	}
	
	private void setListeners() {
		OnClickListener newGameListener = new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Create an intent stating which Activity you would like to start
				Intent intent = new Intent(MenuActivity.this, MainActivity.class);			
				// Launch the Activity using the intent
				startActivity(intent);
				finish();
			}
		};
		mNewGame.setOnClickListener(newGameListener );
		
		OnClickListener settingsListener = new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Create an intent stating which Activity you would like to start
				Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);			
				// Launch the Activity using the intent
				startActivity(intent);
				finish();
			}
		};
		mSettings.setOnClickListener(settingsListener );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
