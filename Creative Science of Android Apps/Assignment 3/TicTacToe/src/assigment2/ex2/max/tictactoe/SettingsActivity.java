package assigment2.ex2.max.tictactoe;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private Spinner mOptions;
	private EditText mName;
	private Button mSelectImage;
	private Button mSave;
	private int selectedLevel;
	private Bitmap mBitmap;
	private SharedPreferences mPrefs;
	private Button mDefaultImg;
	
	private static final int REQUEST_CODE = 1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		mName = (EditText) findViewById(R.id.name);
		mOptions = (Spinner) findViewById(R.id.options);
		mSelectImage = (Button) findViewById(R.id.selectImage);
		mSave = (Button) findViewById(R.id.save);
		mDefaultImg = (Button) findViewById(R.id.defaultImage);
		
		mPrefs = getSharedPreferences(Common.PREFS_NAME,MODE_PRIVATE);
		String name = mPrefs.getString(Common.PLAYER_NAME, "Player1");
		if(name != "Player1")
		{
			mName.setText(name);
		}
		
		setListeners();
		
		handleOptions();
	}


	private void setListeners() {
		OnClickListener selectImageListener = new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Do some Intent magic to open the Gallery? Yes!
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(
						Intent.createChooser(intent, "Select..."), REQUEST_CODE);
			}
		};
		mSelectImage.setOnClickListener(selectImageListener );
		
		OnClickListener saveListener = new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				onSave();
			}
		};
		mSave.setOnClickListener(saveListener );
		
		OnClickListener defaultListener = new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				mBitmap = null;
			}
		};
		mDefaultImg.setOnClickListener(defaultListener );
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// Create an intent stating which Activity you would like to start
			Intent intent = new Intent(SettingsActivity.this, MenuActivity.class);			
			// Launch the Activity using the intent
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void handleOptions() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.level_options, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mOptions.setAdapter(adapter);
		int selected = mPrefs.getInt(Common.LEVEL, 1);
		mOptions.setSelection(selected);
		
		OnItemSelectedListener listener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				selectedLevel = pos;
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		//listener = new 
		mOptions.setOnItemSelectedListener(listener);
	}
	
	
	
	
	public void onSave()
	{
		mPrefs.edit().putString(Common.PLAYER_NAME, mName.getText().toString())
		.putInt(Common.LEVEL,selectedLevel)
		.putString(Common.IMAGE,Common.BitmapToString(mBitmap))
		.commit();
		
		Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			try {
				InputStream stream = getContentResolver().openInputStream(uri);

				BitmapFactory.Options options = new BitmapFactory.Options();
				int ratio = setSampleRatio(stream, options);

				stream = getContentResolver().openInputStream(uri);
				Bitmap bm = BitmapFactory.decodeStream(stream, null, options);
				stream.close();
				if (mBitmap != null) {
					mBitmap.recycle();
				}
				// Make a mutable bitmap...
				mBitmap = Bitmap.createScaledBitmap(bm , bm.getWidth()/ratio, bm.getHeight()/ratio,
						false);
			} catch (Exception e) {
			}

		}
	}


	private int setSampleRatio(InputStream stream,
			BitmapFactory.Options options) throws IOException {
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeStream(stream, null, options);
		stream.close();

		int w = options.outWidth;
		int h = options.outHeight;

		int displayW = 450;
		int displayH = 450;

		int sample = 1;

		while (w > displayW * sample || h > displayH * sample) {
			sample = sample * 2;
		}

		options.inJustDecodeBounds = false;
		options.inSampleSize = sample;
		
		return sample;
	}
	


}
