package assigment2.ex2.max.tictactoe;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final class OnClickHandler implements OnClickListener {
		private int m_row;
		private int m_col;

		public OnClickHandler(int row, int col) {
			m_row = row;
			m_col = col;
		}

		public void onClick(View v) {
			if (gameMatrix[m_row][m_col] == EMPTY) {
				if (turn == PLAYERONE) {
					if (!completed) {
						markAMove(PLAYERONE, m_row, m_col);
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					turn = PLAYERTWO;
					makeDroidMove();
				} else {
					tv2.setText("It's not your turn !");
				}
			}
		}
	}

	private static final int COLS = 3;
	private static final int ROWS = 3;
	private final int EMPTY = 0;
	private final int PLAYERONE = 1;
	private final int PLAYERTWO = 2;
	private TextView tv;
	private TextView tv2;
	private Button b;
	private int[][] gameMatrix;
	private ImageView[][] viewMatrix;
	private int turn = PLAYERONE;
	private OnClickListener listeners[][];
	private boolean completed = false;
	private SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// Create an intent stating which Activity you would like to start
			Intent intent = new Intent(MainActivity.this, MenuActivity.class);
			// Launch the Activity using the intent
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void markAMove(int type, int row, int col) {
		int resource = R.drawable.empty;
		if (type == PLAYERONE) {
			resource = R.drawable.x;
		}
		if (type == PLAYERTWO) {
			resource = R.drawable.o;
		}
		String st = mPrefs.getString(Common.IMAGE, null);
		if(st!=null && type == PLAYERONE)
		{
			viewMatrix[row][col].setImageBitmap(Common.StringToBitmap(st));
		}
		else
		{
			viewMatrix[row][col].setImageResource(resource);
		}
		viewMatrix[row][col].refreshDrawableState();
		gameMatrix[row][col] = type;

		if (checkWin()) {
			return;
		}

		if (!areAvliableMoves()) {
			tv2.setText("Tie !");
			b.setEnabled(true);
			b.setVisibility(View.VISIBLE);
			completed = true;
		}
	}

	private boolean areAvliableMoves() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (gameMatrix[i][j] == EMPTY)
					return true;
			}
		}
		return false;

	}

	private boolean checkWin() {
		return checkWin(PLAYERONE) || checkWin(PLAYERTWO);
	}

	private boolean checkWin(int player) {

		if (checkRows(player) || checkCols(player) || checkDiags(player)) {
			displayWin(player);
			return true;
		}
		return false;
	}

	private void makeDroidMove() {
		if (completed) {
			return;
		}

		displayTurn();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int level = mPrefs.getInt(Common.LEVEL, 1);
		Point p = new Point();
		if (level == 0) {
			p = makeStupidMove();
		}
		if (level == 1) {
			p = makeRandomMove();
		}
		if (level == 2) {
			p = makeMediumMove();
		}
		if (level > 2) {
			Toast.makeText(getApplicationContext(), "Level not supported",
					Toast.LENGTH_LONG).show();
			return;
		}
		markAMove(PLAYERTWO, p.x, p.y);
		turn = PLAYERONE;
		displayTurn();
		return;
	}

	private Point makeMediumMove() {
		Point p = getWinningMove();
		if (p != null) {
			return p;
		}
		p = getBlockingMove();

		if (p != null) {
			return p;
		}
		return makeRandomMove();
	}

	private Point getBlockingMove() {
		return findWinningMove(PLAYERONE);
	}

	private Point findWinningMove(int player) {
		// check rows
		for (int i = 0; i < ROWS; i++) {
			int ind = findWinningInTriple(gameMatrix[i][0], gameMatrix[i][1],
					gameMatrix[i][2], player);
			if (ind != -1) {
				return new Point(i, ind);
			}
		}
		// check cols
		for (int j = 0; j < COLS; j++) {
			int ind = findWinningInTriple(gameMatrix[0][j], gameMatrix[1][j],
					gameMatrix[2][j], player);
			if (ind != -1) {
				return new Point(ind, j);
			}
		}
		// check diag1
		int ind = findWinningInTriple(gameMatrix[0][0], gameMatrix[1][1],
				gameMatrix[2][2], player);
		if (ind != -1) {
			return new Point(ind, ind);
		}

		// check diag2
		ind = findWinningInTriple(gameMatrix[0][2], gameMatrix[1][1],
				gameMatrix[2][0], player);
		if (ind != -1) {
			return new Point(ind, 2-ind);
		}

		return null;
	}

	private int findWinningInTriple(int ind0, int ind1, int ind2, int player) {
		if (ind0 == player && ind1 == player && ind2 == EMPTY) {
			return 2;
		}
		if (ind0 == player && ind1 == EMPTY && ind2 == player) {
			return 1;
		}
		if (ind0 == EMPTY && ind1 == player && ind2 == player) {
			return 0;
		}
		return -1;
	}

	private Point getWinningMove() {
		return findWinningMove(PLAYERTWO);
	}

	private Point makeRandomMove() {
		Random randomGenerator = new Random();
		while (true) {
			int i = randomGenerator.nextInt(3);
			int j = randomGenerator.nextInt(3);
			if (gameMatrix[i][j] == EMPTY) {
				return new Point(i, j);
			}
		}
	}

	private Point makeStupidMove() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (gameMatrix[i][j] == EMPTY) {
					return new Point(i, j);
				}
			}
		}
		return new Point();
	}

	private boolean checkDiags(int player) {

		if (gameMatrix[0][0] == player && gameMatrix[1][1] == player
				&& gameMatrix[2][2] == player) {
			return true;
		}

		if (gameMatrix[0][2] == player && gameMatrix[1][1] == player
				&& gameMatrix[2][0] == player) {
			return true;
		}

		return false;

	}

	private boolean checkCols(int player) {
		for (int j = 0; j < COLS; j++) {
			boolean colFound = true;
			for (int i = 0; i < ROWS; i++) {
				if (gameMatrix[i][j] != player) {
					colFound = false;
				}
			}
			if (colFound) {
				return true;
			}
		}
		return false;
	}

	private boolean checkRows(int player) {
		for (int i = 0; i < ROWS; i++) {
			boolean rowFound = true;
			for (int j = 0; j < COLS; j++) {
				if (gameMatrix[i][j] != player) {
					rowFound = false;
				}
			}
			if (rowFound) {
				return true;
			}
		}
		return false;
	}

	private void displayWin(int player) {
		String name = getPlayersName(player);
		tv2.setText(name + " won !!!");
		b.setEnabled(true);
		b.setVisibility(View.VISIBLE);
		completed = true;
		if (player == PLAYERONE) {
			mPrefs.edit()
					.putInt(Common.WON_GAMES,
							mPrefs.getInt(Common.WON_GAMES, 0) + 1).commit();
		}
	}

	public void init() {
		mPrefs = getSharedPreferences(Common.PREFS_NAME, MODE_PRIVATE);
		tv = (TextView) findViewById(R.id.textView);
		tv2 = (TextView) findViewById(R.id.tv2);
		b = (Button) findViewById(R.id.button);

		tv2.setTextColor(Color.RED);

		gameMatrix = new int[ROWS][COLS];
		initViewMatrix();

		listeners = new OnClickListener[COLS][ROWS];
		addListeners();

		clear();
	}

	private void initViewMatrix() {
		viewMatrix = new ImageView[ROWS][COLS];

		viewMatrix[0][0] = (ImageView) findViewById(R.id.row1col1);
		viewMatrix[1][0] = (ImageView) findViewById(R.id.row1col2);
		viewMatrix[2][0] = (ImageView) findViewById(R.id.row1col3);
		viewMatrix[0][1] = (ImageView) findViewById(R.id.row2col1);
		viewMatrix[1][1] = (ImageView) findViewById(R.id.row2col2);
		viewMatrix[2][1] = (ImageView) findViewById(R.id.row2col3);
		viewMatrix[0][2] = (ImageView) findViewById(R.id.row3col1);
		viewMatrix[1][2] = (ImageView) findViewById(R.id.row3col2);
		viewMatrix[2][2] = (ImageView) findViewById(R.id.row3col3);
	}

	public void clear() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				markAMove(EMPTY, i, j);
			}
		}
		tv2.setText("");
		tv.setText("");

		b.setEnabled(false);
		b.setVisibility(View.INVISIBLE);
		completed = false;

		randomizeTurn();
	}

	private void randomizeTurn() {
		turn = PLAYERONE;
		if (Math.random() > 0.5) {
			turn = PLAYERTWO;
			makeDroidMove();
		} else {
			displayTurn();
		}
	}

	public void newGameClick(View view) {
		clear();
	}

	private void displayTurn() {
		String player = getPlayersName(turn);
		tv.setText(player + " turn !");
	}

	private String getPlayersName(int player) {

		String name = mPrefs.getString(Common.PLAYER_NAME, "Player1");
		if (player == PLAYERTWO) {
			name = "Droid's";
		}
		return name;

	}

	private void addListeners() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				listeners[i][j] = new OnClickHandler(i, j);
				viewMatrix[i][j].setOnClickListener(listeners[i][j]);
			}

		}

	}
}
