package max.somenamefor.assigment2_app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Runner extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent();
		//intent.setClass(this, JabberwockyActivity.class);
		intent.setClass(this, NasaActivity.class);
		
		startActivity(intent);
		finish();
	}

}
