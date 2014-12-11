package assigment2.ex2.max.tictactoe;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

	private final class OnClickHandler implements OnClickListener {
		private int m_row;
		private int m_col;

		public OnClickHandler(int row, int col) {
			m_row = row;
			m_col=col;
		}

		public void onClick(View v) {
			if (gameMatrix[m_row][m_col] == EMPTY) {
				if (turn == PLAYERONE) {
					if(!completed)
					{
						markAMove(PLAYERONE,m_row,m_col);
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

	public void markAMove(int type, int row, int col) {
		int resource = R.drawable.empty;
		if (type == PLAYERONE) {
			resource = R.drawable.x;
		}
		if (type == PLAYERTWO) {
			resource = R.drawable.o;
		}
		viewMatrix[row][col].setImageResource(resource);
		viewMatrix[row][col].refreshDrawableState();
		gameMatrix[row][col] = type;
		
		if(checkWin())
		{
			return;
		}
		
		if(!areAvliableMoves())
		{
			tv2.setText("Tie !");		
			b.setEnabled(true);
			b.setVisibility(View.VISIBLE);
			completed = true;
		}
	}

	private boolean areAvliableMoves() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if(gameMatrix[i][j]==EMPTY)
					return true;
			}
		}
		return false;
		
	}

	private boolean checkWin() {
		return checkWin(PLAYERONE)||checkWin(PLAYERTWO);		
	}

	private boolean checkWin(int player) {
			
		if(checkRows(player) || checkCols(player)|| checkDiags(player))
		{
			displayWin(player);
			return true;
		}	
		return false;
	}
	
	private void makeDroidMove()
	{
		if(completed)
		{
			return;
		}
		
		displayTurn();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Point p = makeStupidMove();
		markAMove(PLAYERTWO,p.x,p.y);
		turn = PLAYERONE;
		displayTurn();
		return;
	}

	private Point makeStupidMove() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if(gameMatrix[i][j] == EMPTY)
				{
					return new Point(i,j);
				}
			}
		}
		return new Point();
	}

	private boolean checkDiags(int player) {
		
		if(gameMatrix[0][0] == player && gameMatrix[1][1] == player && gameMatrix[2][2] == player)
		{
			return true;
		}
		
		if(gameMatrix[0][2] == player && gameMatrix[1][1] == player && gameMatrix[2][0] == player)
		{
			return true;
		}
		
		return false;
		
	}

	private boolean checkCols(int player) {
		for (int j = 0; j < COLS; j++) {
			boolean colFound = true;
			for (int i = 0; i < ROWS; i++) {
				if(gameMatrix[i][j] != player)
				{
					colFound = false;
				}
			}
			if(colFound)
			{
				return true;
			}
		}
		return false;				
	}

	private boolean checkRows(int player) {
		for (int i = 0; i < ROWS; i++) {
			boolean rowFound = true;
			for (int j = 0; j < COLS; j++) {
				if(gameMatrix[i][j] != player)
				{
					rowFound = false;
				}
			}
			if(rowFound)
			{
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
	}

	public void init() {
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
		}
		else
		{
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
		String name = "Your";
		if (player == PLAYERTWO) {
			name = "Droid's";
		}
		return name;
	}

	private void addListeners() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				listeners[i][j] = 
						new OnClickHandler(i,j);
				viewMatrix[i][j].setOnClickListener(listeners[i][j]);
			}
		
	}
	
	}
}
