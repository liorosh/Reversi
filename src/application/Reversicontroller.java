/**
 * Sample Skeleton for 'ReversiScene.fxml' Controller Class
 */
package application;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


enum DIAGONAL {
	upAndLeft, upAndRight, downAndLeft, downAndRight
}

enum DIRECTION {
	ascending, descending, leftToRight, rightToLeft
}

class coordinates { // coordinates for each tile
	int x;
	int y;

	public coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public boolean hasCoordinates(int x, int y){
		if (this.x==x && this.y==y)
			return true;
		return false;

	}
	@Override
	 public boolean equals(Object coordinates)
	 {
		coordinates coordinate=(coordinates) coordinates;
		if (this.x==coordinate.x &&this.y==coordinate.y)
			return true;

	return false;
	}
	 @Override
	 public int hashCode()
	 {
		return x;
	 }
}

class piece extends Circle { // pieces class for players
	public piece(double i, Color aqua) {
		setRadius(i);
		setFill(aqua);
	}
}

class options {
	ArrayList<coordinates> optionscoordinates=new ArrayList<coordinates>();
	public options(ArrayList<coordinates> tempstreak)
	{
		this.optionscoordinates=tempstreak;
	}

}

public class Reversicontroller
{
Hashtable<coordinates,options> validMoves=new Hashtable<coordinates,options>();
	private String turnFlag = "player1";
	private boolean computerFlagP2=false;
	private boolean computerFlagP1=false;
	@FXML
	private Pane s0_0, s0_1, s0_2, s0_3, s0_4, s0_5, s0_6, s0_7, s0_8, s0_9, s0_10, s0_11, s1_0, s1_1, s1_2, s1_3, s1_4,
			s1_5, s1_6, s1_7, s1_8, s1_9, s1_10, s1_11, s2_0, s2_1, s2_2, s2_3, s2_4, s2_5, s2_6, s2_7, s2_8, s2_9,
			s2_10, s2_11, s3_0, s3_1, s3_2, s3_3, s3_4, s3_5, s3_6, s3_7, s3_8, s3_9, s3_10, s3_11, s4_0, s4_1, s4_2,
			s4_3, s4_4, s4_5, s4_6, s4_7, s4_8, s4_9, s4_10, s4_11, s5_0, s5_1, s5_2, s5_3, s5_4, s5_5, s5_6, s5_7,
			s5_8, s5_9, s5_10, s5_11, s6_0, s6_1, s6_2, s6_3, s6_4, s6_5, s6_6, s6_7, s6_8, s6_9, s6_10, s6_11, s7_0,
			s7_1, s7_2, s7_3, s7_4, s7_5, s7_6, s7_7, s7_8, s7_9, s7_10, s7_11, s8_0, s8_1, s8_2, s8_3, s8_4, s8_5,
			s8_6, s8_7, s8_8, s8_9, s8_10, s8_11, s9_0, s9_1, s9_2, s9_3, s9_4, s9_5, s9_6, s9_7, s9_8, s9_9, s9_10,
			s9_11, s10_0, s10_1, s10_2, s10_3, s10_4, s10_5, s10_6, s10_7, s10_8, s10_9, s10_10, s10_11, s11_0, s11_1,
			s11_2, s11_3, s11_4, s11_5, s11_6, s11_7, s11_8, s11_9, s11_10, s11_11;
	private byte[][] board = new byte[12][12];

	@FXML
	void highlight(MouseEvent event)
	{
		Pane temp = (Pane) event.getSource();
		temp.setStyle("-fx-background-color:#dae7f3;");
		/*String xPane=temp.getId().substring(1).split("_")[0];
		String yPane=temp.getId().split("_")[1];
		ArrayList<coordinates> values = validMoves.get(new coordinates(Integer.parseInt(xPane),Integer.parseInt(yPane))).optionscoordinates;
		System.out.print("b");
		for(coordinates optioncount:values){
		try
		{
			temp = (Pane) getClass().getDeclaredField("s" + optioncount.x + "_" + optioncount.y)
					.get(this);
		}
		catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e)
		{
			e.printStackTrace();
		}
		piece piece=(piece) temp.getChildren().get(0);
		if(turnFlag.matches("player1"))
		{
			piece.setFill(Color.AQUA);
		}
		else if (turnFlag.matches("player2"))
		{
			piece.setFill(Color.SLATEGRAY);
		}
		}
*/
	}

	@FXML
	void remove_highlight(MouseEvent event)
	{
		Pane temp = (Pane) event.getSource();
		coordinates cord;
		cord = getCoordinates(temp.getId().substring(1, temp.getId().length()));
		if ((cord.x + cord.y) % 2 == 0)
			temp.setStyle("-fx-background-color:white;");
		else
			temp.setStyle("-fx-background-color:pink;");
		//if(!computerFlagP2 && !turnFlag.equals("player2")){
		/*String xPane=temp.getId().substring(1).split("_")[0];
		String yPane=temp.getId().split("_")[1];
		System.out.print(xPane+" "+yPane+"\n");
		ArrayList<coordinates> values = validMoves.get(new coordinates(Integer.parseInt(xPane),Integer.parseInt(yPane))).optionscoordinates;

		for(coordinates optioncount:values){
		try
		{
			temp = (Pane) getClass().getDeclaredField("s" + optioncount.x + "_" + optioncount.y)
					.get(this);
		}
		catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e)
		{
			e.printStackTrace();
		}
		piece piece=(piece) temp.getChildren().get(0);
		if(turnFlag.matches("player1"))
		{
			piece.setFill(Color.SLATEGRAY);
		}
		else if (turnFlag.matches("player2"))
		{
			piece.setFill(Color.AQUA);
		}
		}
*/
	}

	@FXML
	void movePieces(MouseEvent event){
		Pane temp = null;
		if(!this.computerFlagP1 &&( this.turnFlag.matches("player1") || !this.computerFlagP2)){
		temp = (Pane) event.getSource();
		}
		makeMove(temp);
		if(this.computerFlagP2 && this.turnFlag.matches("player2")){
			coordinates temp1;
			Random generator = new Random();
			Object[] values = validMoves.keySet().toArray();
			Object randomValue = values[generator.nextInt(values.length)];
			temp1=(coordinates) randomValue;
			try
			{
				temp = (Pane) getClass().getDeclaredField("s" + Integer.toString(temp1.x) + "_" + Integer.toString(temp1.y))
						.get(this);
			}
			catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
					| SecurityException e)
			{

				e.printStackTrace();
			}
			makeMove(temp);
		}
	}
	void movePieces(){
		Pane temp = null;

		if (computerFlagP1){
			coordinates temp1;
			Random generator = new Random();
			Object[] values = validMoves.keySet().toArray();
			if(values.length!=0){
			Object randomValue = values[generator.nextInt(values.length)];
			temp1=(coordinates) randomValue;
			try
			{
				temp = (Pane) getClass().getDeclaredField("s" + Integer.toString(temp1.x) + "_" + Integer.toString(temp1.y))
						.get(this);
			}
			catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
					| SecurityException e)
			{
				e.printStackTrace();
			}
			System.out.println("x: "+temp1.x+"_"+"y:"+temp1.y);
			makeMove(temp);
			}
		}
		if(computerFlagP2 && turnFlag.matches("player2")){
			coordinates temp1;
			Random generator = new Random();
			Object[] values = validMoves.keySet().toArray();
			if(values.length!=0){
			Object randomValue = values[generator.nextInt(values.length)];
			temp1=(coordinates) randomValue;
			try
			{
				temp = (Pane) getClass().getDeclaredField("s" + Integer.toString(temp1.x) + "_" + Integer.toString(temp1.y))
						.get(this);
			}
			catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
					| SecurityException e)
			{
				e.printStackTrace();
			}
			System.out.println("x: "+temp1.x+"_"+"y:"+temp1.y);
			makeMove(temp);
			}
			else
				turnFlag="player1";
		}
	}

	void makeMove(Pane temp)
	{
		Color color = new Color(0, 0, 0, 0);
		Object panetochange = null;
		int yIndex=Integer.parseInt(temp.getId().substring(1).split("_")[1]);
		int xIndex=Integer.parseInt(temp.getId().substring(1).split("_")[0]);
		if(!this.validMoves.isEmpty() && this.validMoves.containsKey(new coordinates(xIndex,yIndex))){
			ArrayList<coordinates> pickedOption=this.validMoves.get(new coordinates(xIndex,yIndex)).optionscoordinates;
			while(!pickedOption.isEmpty())
			{
				int xcor,ycor;
				xcor=pickedOption.get(0).x;
				ycor=pickedOption.get(0).y;
				try
				{
					panetochange = getClass().getDeclaredField("s" + Integer.toString(xcor) + "_" + Integer.toString(ycor))
							.get(this);
				}
				catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException e)
				{
					e.printStackTrace();
				}
				Pane temp1=(Pane) panetochange;
				piece piece;
				piece=(piece) temp1.getChildren().get(0);
				if(this.turnFlag.matches("player1"))
				{
					piece.setFill(Color.AQUA);
					board[xcor][ycor]=1;
				}
				else if (this.turnFlag.matches("player2"))
				{
					piece.setFill(Color.SLATEGRAY);
					board[xcor][ycor]=2;
				}
				temp1.setDisable(true);
				pickedOption.remove(0);
				}
			Iterator<Map.Entry<coordinates,options>> it=this.validMoves.entrySet().iterator();
			Object optionToDisable = null;
			while(it.hasNext())
			{
				Map.Entry<coordinates,options> entry=it.next();
					try {
						optionToDisable = getClass().getDeclaredField("s"+Integer.toString(entry.getKey().x)+"_"+Integer.toString(entry.getKey().y)).get(this);
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
						e.printStackTrace();
					}
					Pane paneDisable=(Pane)optionToDisable;
					paneDisable.setDisable(true);
			}
		if (this.turnFlag.matches("player1"))
		{
			color = Color.AQUA;
			this.board[xIndex][yIndex] = 1;
			this.turnFlag="player2";
		} else if (this.turnFlag.matches("player2"))
		{
			color = Color.SLATEGRAY;
			this.board[xIndex][yIndex] = 2;
			this.turnFlag="player1";
		}
		piece circle = new piece(24, color);
		circle.relocate(2, 2);
		temp.getChildren().add(circle);
		temp.setDisable(true);
		this.validMoves.clear();
		}
		else{
		}
		if((calculateMoves(board,validMoves))){
			if(this.turnFlag.matches("player1"))
				this.turnFlag="player2";
			else if(this.turnFlag.matches("player2"))
				this.turnFlag="player1";
			calculateMoves(board,validMoves);

		}
		//if computer
		//minmax
	}

	@FXML
	public void initialize() throws InterruptedException
	{
		/*piece first = new piece(24, Color.AQUA);
		first.relocate(2, 2);
		this.s0_0.getChildren().add(first);
		this.board[0][0] = 1;

		piece second = new piece(24, Color.AQUA);
		second.relocate(2, 2);
		this.s1_1.getChildren().add(second);
		this.board[1][1] = 1;

		piece third = new piece(24, Color.SLATEGRAY);
		third.relocate(2, 2);
		this.s1_0.getChildren().add(third);
		this.board[1][0] = 2;

		piece fourth = new piece(24, Color.SLATEGRAY);
		fourth.relocate(2, 2);
		this.s0_1.getChildren().add(fourth);
		this.board[0][1] = 2;
		s6_5.setDisable(true);
		calculateMoves(this.board,this.validMoves);
		if(this.computerFlagP1){*/




		piece first = new piece(24, Color.AQUA);
		first.relocate(2, 2);
		s5_5.getChildren().add(first);
		board[5][5] = 1;
		piece second = new piece(24, Color.AQUA);
		second.relocate(2, 2);
		s6_6.getChildren().add(second);
		board[6][6] = 1;
		piece third = new piece(24, Color.SLATEGRAY);
		third.relocate(2, 2);
		s5_6.getChildren().add(third);
		board[5][6] = 2;
		piece fourth = new piece(24, Color.SLATEGRAY);
		fourth.relocate(2, 2);
		s6_5.getChildren().add(fourth);
		board[6][5] = 2;
		calculateMoves(board,validMoves);
		if(computerFlagP1){
			/*Platform.runLater(new Runnable()
		    {
				@Override
		      public void run()
		      {*/
					int i=0;
		        while(i<70)
		        {
		        	//Timer timer = new Timer(100, this);
		       //   try {
					//TimeUnit.SECONDS.sleep(1);
				//} catch (InterruptedException e) {
				//	e.printStackTrace();
				//}
		          movePieces();
		          i++;
		        }
		      }
		   /* });
		}*/
System.out.print("x");
	}

	public void getDifficulty(String difficulty)
	{
		System.out.println(difficulty);
	}

	public void getPlayerOrComputer(String playAgainst)
	{

		//human vs. Pc-->1
		//human vs. human-->2
		//Pc vs. Pc-->3
		System.out.println(playAgainst);
	}
	/*
	 * maybe switch to working with a matrix, and color the pieces according to
	 * the matrix, it'll be easier should check it in the morning, after
	 * updating the matrix that a click has been made, update the matrix, go
	 * through all of it and switch colors
	 */

	public coordinates getCoordinates(String name)
	{
		coordinates temp = new coordinates(Integer.parseInt(name.split("_")[0]), Integer.parseInt(name.split("_")[1]));
		return temp;
	}

	public boolean calculateMoves(byte[][] board,Hashtable<coordinates,options> options)
	{
		int playerNumber = 0;
		if (this.turnFlag.matches("player1"))
			playerNumber = 1;
		else if (this.turnFlag.matches("player2"))
			playerNumber = 2;
		for (int i = 0; i < 12; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				if (board[i][j] == 0)
					continue;
				else if (board[i][j] == playerNumber)
				{
					checkLines(i, j, DIRECTION.ascending);
					checkLines(i, j, DIRECTION.descending);
					checkLines(i, j, DIRECTION.rightToLeft);
					checkLines(i, j, DIRECTION.leftToRight);
					checkLines(i, j, DIAGONAL.downAndLeft);
					checkLines(i, j, DIAGONAL.downAndRight);
					checkLines(i, j, DIAGONAL.upAndLeft);
					checkLines(i, j, DIAGONAL.upAndRight);
				}
			}
		}

		//System.out.print("finished moves");
		return validMoves.isEmpty();
	}

	public void checkLines(int x, int y, DIRECTION direction)
	{
		int tempX = x, tempY = y;
		byte xvalue = 0, yvalue = 0;
		ArrayList<coordinates> tempstreak=new ArrayList<coordinates>();
		switch (direction)
		{
		case ascending:
			xvalue = -1;
			tempX += xvalue;
			break;
		case descending:
			xvalue = 1;
			tempX += xvalue;
			break;
		case rightToLeft:
			yvalue = -1;
			tempY += yvalue;
			break;
		case leftToRight:
			yvalue = 1;
			tempY += yvalue;
			break;
		}
		int playerNumber = 0;
		if (this.turnFlag.matches("player1"))
			playerNumber = 2;
		else if (this.turnFlag.matches("player2"))
			playerNumber = 1;
		if ((tempX >= 0 && tempX <= 11) &&(tempY>=0 && tempY<=11) && board[tempX][tempY] != 0 && board[tempX][tempY]==playerNumber)
		{
			while ((tempX>=0 && tempX<=11) &&(tempY>=0 && tempY<=11) && board[tempX][tempY] == playerNumber)
			{
				tempstreak.add(new coordinates(tempX,tempY));
				tempX += xvalue;
				tempY += yvalue;
			}
			if ((tempX>=0 && tempX<=11) &&(tempY>=0 && tempY<=11) && board[tempX][tempY] == 0)
			{
				Object temp = null;
				try
				{
					temp = getClass().getDeclaredField("s" + Integer.toString(tempX) + "_" + Integer.toString(tempY))
							.get(this);
				}
				catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException e)
				{
					e.printStackTrace();
				}
				Pane temp1 = (Pane) temp;
				if(validMoves.containsKey(new coordinates(tempX,tempY)))
					validMoves.get(new coordinates(tempX,tempY)).optionscoordinates.addAll(tempstreak);
				else
				validMoves.put(new coordinates(tempX,tempY), new options(tempstreak));
				temp1.setDisable(false);
			}
		}
	}

	public void checkLines(int x, int y,DIAGONAL diagonal)
	{
    	int tempX=x,tempY=y;
    	ArrayList<coordinates> tempstreak=new ArrayList<coordinates>();
    	byte xvalue=0,yvalue=0;
    	switch(diagonal)
    	{
    	case upAndLeft:
	    	xvalue=-1;
	    	yvalue=-1;
    	break;
    	case upAndRight:
	    	xvalue=-1;
	    	yvalue=1;
    	break;
    	case downAndLeft:
	    	xvalue=1;
	    	yvalue=-1;
    	break;
    	case downAndRight:
	    	xvalue=1;
	    	yvalue=1;
    	break;
    	}
    	tempX+=xvalue;
    	tempY+=yvalue;
    	int playerNumber=0;
    	if (this.turnFlag.matches("player1"))
    		playerNumber=2;
    	else if(this.turnFlag.matches("player2"))
    		playerNumber=1;
    	if((tempX>=0 && tempX<=11) && (tempY>=0 && tempY<=11) && board[tempX][tempY]!=0 && board[tempX][tempY]==playerNumber)
    	{
    		while((tempX>=0 && tempX<=11) &&(tempY>=0 && tempY<=11) && board[tempX][tempY]==playerNumber)
    		{
    			tempstreak.add(new coordinates(tempX,tempY));
    			tempX+=xvalue;
    			tempY+=yvalue;
    		}
    		if((tempX>=0 && tempX<=11) &&(tempY>=0 && tempY<=11) && board[tempX][tempY]==0)
    		{
    			Object temp = null;

				try {
					temp = getClass().getDeclaredField("s"+Integer.toString(tempX)+"_"+Integer.toString(tempY)).get(this);
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
				Pane temp1=(Pane)temp;
				if(validMoves.containsKey(new coordinates(tempX,tempY)))
					validMoves.get(new coordinates(tempX,tempY)).optionscoordinates.addAll(tempstreak);
				else
				validMoves.put(new coordinates(tempX,tempY), new options(tempstreak));
	    		temp1.setDisable(false);
    		}
    	}
    }


	void minimax(int[][]board, Hashtable<coordinates, options> options, int depth){
		 if (depth <= 0){ // stop step  and also calculate Heuristic func value on the leaf
             HeuristicFunc(board, options);
             return ;
         }else{

         }
	}


	private void HeuristicFunc(int[][] board, Hashtable<coordinates, options> options) {


	}
}
