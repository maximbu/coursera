package com.course2.assigment2.ex2.trivia;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	int q=1;
	boolean wasInit = false;
	int correctCnt = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);		
		if(!wasInit)
		{
			setContentView(R.layout.activity_main);
			wasInit= true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 public void trueClick(View view) {
	     Clicked(true , (View)view.getParent());
	 }
	 
	 
	 public void falseClick(View view) {
		 Clicked(false , (View)view.getParent());
	 }

	 public void nextClick(View view) {
		 ShowQuestion((View) view.getParent());
		 HandleVisibility((View) view.getParent() , false);
	 }
	 
	 
	 private void  ShowQuestion(View view)
	 {
		 int id = R.string.q1;
		 switch(q)
		 {
		 case (1):
		 {
			 break;
		 }
		 case (2):
		 {
			 id = R.string.q2;
			 break;
		 }
		 case (3):
		 {
			 id = R.string.q3;
			 break;
		 }
		 }
		 
		 TextView question =  (TextView) view.findViewById(R.id.question);
		 question.setText(getResources().getText(id));
	 }
	 
	 private void Clicked(boolean val , View view)
	 {		    
		 boolean correctAnswer = true;
		 switch(q)
		 {
		 case (1):
		 {
			 correctAnswer = (val == true);
			 break;
		 }
		 case (2):
		 {
			 correctAnswer = (val == true);
			 break;
		 }
		 case (3):
		 {
			 correctAnswer = (val == false);
			 break;
		 }
		 }
		 
		 TextView answer =  (TextView) view.findViewById(R.id.answer);
		 if(correctAnswer)
		 {
			 answer.setText(getResources().getText(R.string.CorrectText));	
			 answer.setTextColor(Color.GREEN);
			 correctCnt++;
		 }
		 else
		 {
			 answer.setText(getResources().getText(R.string.WrongText));
			 answer.setTextColor(Color.RED);
		 }
		 HandleVisibility(view , true);
		 q++;
	 }

	 
	private void HandleVisibility(View view , boolean afterAnswer) {
 
		 View answer = view.findViewById(R.id.answer);		 		 
		 View next = view.findViewById(R.id.next);		 
		 View b1 = view.findViewById(R.id.button1);		 
		 View b2 = view.findViewById(R.id.button2);
		 
    	if(afterAnswer)
		 {
			 answer.setVisibility(View.VISIBLE);
			 next.setVisibility(View.VISIBLE);
			 
			 b1.setVisibility(View.INVISIBLE);
			 b2.setVisibility(View.INVISIBLE);
			 if(q==3)
			 {
				 handleLastQuestion(answer, next);
			 }
		 }
		 else
		 {
			 answer.setVisibility(View.INVISIBLE);
			 next.setVisibility(View.INVISIBLE);
			 b1.setVisibility(View.VISIBLE);
			 b2.setVisibility(View.VISIBLE);
		 }		 	 
		 
	}

	private void handleLastQuestion(View answer, View next) {

			Button b = (Button)next;
			b.setText(getResources().getText(R.string.TheEnd));
			b.setEnabled(false);
			
			TextView ans = (TextView)answer;
			ans.setText(ans.getText()+". You got "+correctCnt+" out of 3 correct !");
	}
	
}
