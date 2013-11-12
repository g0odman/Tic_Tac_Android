package com.example.tic_tac_android;

import com.example.tic_tac_android.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainGame extends Activity
{

	public static enum Token
	{
		X, O, P;
	}

	@SuppressLint("NewApi")
	private Token token, current = Token.X;
	private int pos = 0, p1score=0,p2score=0;
	public static int size=4;
	private Token[][] board = new Token[size][size];
	public boolean check(Token a[][], Token token)
	{
		//run lines for every possible winning move
		boolean checker = false;
		for (int i = 0; i < size; i++)
		{
			checker = lines(a, i, 0, 0, 1, token);
			if (checker)
				return checker;

			checker = lines(a, 0, i, 1, 0, token);
			if (checker)
				return checker;
		}
		checker = lines(a, 0, 0, 1, 1, token);
		if (checker)
			return checker;
		checker = lines(a, 0, size-1, 1, -1, token);
		return checker;

	}
//checks for winning moves in given direction
	public static boolean lines(Token a[][], int row, int column, int dr, int dc, Token token)
	{
		while (a[row][column] == token || a[row][column] == Token.P)
		{	row += dr;
			column += dc;
			if (row == size || column == size)
			{
				return true;
			}
		}
		return false;
	}
//determines whether the current player may select a tile or is forced to play a triangle
	public void determineTurn()
	{
		if (current == Token.X)
		{
			current = Token.O;
			if (pos < 2)
				changeState(false);
			else
			{
				token = Token.P;
				pos -= 2;
			}
		}
		else
		{
			current = Token.X;
			if (pos % 2 == 0)
				changeState(false);
			else
			{
				token = Token.P;
				pos--;
			}
		}
	}
//Gets the choice of Token
	public void getChoice(View view)
	{
		if (view.getId() == findViewById(R.id.button18).getId()){
			token = Token.P;
		}

		else
		{
			token = current;
			if (token == Token.X)
				pos += 1;
			else
				pos += 2;
		}
		changeState(true);
	}
// Sets either the board to be off and the token selection on or the  opposite 
	private void changeState(boolean yaOrNay)
	{
		findViewById(R.id.button1).setEnabled(yaOrNay);
		findViewById(R.id.button2).setEnabled(yaOrNay);
		findViewById(R.id.button3).setEnabled(yaOrNay);
		findViewById(R.id.button4).setEnabled(yaOrNay);
		findViewById(R.id.button5).setEnabled(yaOrNay);
		findViewById(R.id.button6).setEnabled(yaOrNay);
		findViewById(R.id.button7).setEnabled(yaOrNay);
		findViewById(R.id.button8).setEnabled(yaOrNay);
		findViewById(R.id.button9).setEnabled(yaOrNay);
		findViewById(R.id.button10).setEnabled(yaOrNay);
		findViewById(R.id.button11).setEnabled(yaOrNay);
		findViewById(R.id.button12).setEnabled(yaOrNay);
		findViewById(R.id.button13).setEnabled(yaOrNay);
		findViewById(R.id.button14).setEnabled(yaOrNay);
		findViewById(R.id.button15).setEnabled(yaOrNay);
		findViewById(R.id.button16).setEnabled(yaOrNay);
		findViewById(R.id.button17).setEnabled(!yaOrNay);
		findViewById(R.id.button18).setEnabled(!yaOrNay);

	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_game);
		// Show the Up button in the action bar.
		setupActionBar();
		createGame();

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
// processes the turn selection and checks for game over
	public void newTurn(View view)
	{	
		int c = Integer.parseInt(view.getTag().toString())%4;
		int r = Integer.parseInt(view.getTag().toString())/4;
		board[r][c] = token;
		if (token == Token.X)
			view.setBackgroundColor(Color.RED);
		else
			if (token == Token.O)
				view.setBackgroundColor(Color.BLUE);
			else
				view.setBackgroundColor(Color.MAGENTA);
		if (check(board, current)){
			newGame();
			if(current == Token.X)
				p1score+=1;
			else
				p2score+=1;
			
		}
			
		view.setTag("-1");
		view.setEnabled(false);
		determineTurn();
		
	}

	public void createGame()
	{
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				board[i][j] = null;
		changeState(false);
		
		
	}
	public void newGame(){
		((EditText) findViewById(R.id.editText1)).setText("Player 1:" + p1score);
		((EditText) findViewById(R.id.editText2)).setText("Player 2:" + p2score);
		current = Token.X;
		token = Token.X;
		pos=0;
	}
}