/**
 * Sample Skeleton for 'ReversiScene.fxml' Controller Class
 */
package application;


import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

//we defined enum to distinguish each of our directions checking and to overload the function
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



class MiniMaxNode{//class for sending all relevant data to min-max algorith
    public ArrayList<MiniMaxNode> children;
    public MiniMaxNode parent;
    public boolean isMinOrMax;
    public double value;
    public int nOfWhite;
    public int nOfBlack;
    public byte[][] board;
    public boolean blackOrWhite;
    public coordinates coord;

}

class piece extends Circle { // pieces class for players,inherits from circle for shape attributes
	public piece(double i, Color aqua) {
		setRadius(i);
		setFill(aqua);
	}
}

class options {//options is the pieces to convert for specific playes move
	ArrayList<coordinates> optionscoordinates=new ArrayList<coordinates>();
	public options(ArrayList<coordinates> tempstreak)
	{
		this.optionscoordinates=tempstreak;
	}

}

public class Reversicontroller
{
	//validmoves represents the moves a player can make at any given moment and contains the pieces it'll change to the players color depending on the move
Hashtable<coordinates,options> validMoves=new Hashtable<coordinates,options>();
//flags for knowing the certain state of the game
private String turnFlag = "player1";
	private boolean computerFlagP2;
	private boolean computerFlagP1;
	private boolean firstTurn=true;
	private String p1Heuristic;
	private String p2Heuristic;
	private String p1MinOrMax;
	private String p2MinOrMax;
	private int depth;

	//board state and each of pieces count
	private byte[][] board = new byte[12][12];
	private byte whiteCounter;
	private byte blackCounter;

	//gui for objects for display purposes
    @FXML
    private Label turnLabel;

    @FXML
    private Pane turnShow,endGame;

    @FXML
    private TextField winner;

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
	Pane[][] panel = new Pane[12][12];
	//this class is for creating a thread to change gui
	class pcVSpc implements Runnable {

	public void run() {
	}
	public void change(Pane assign,piece circle){
		Platform.runLater ( () -> 	assign.getChildren().add(circle));
	}

	}
	class chageBoard implements Runnable{
		Pane temp1;
		ArrayList<coordinates> pickedOption;
		int xcor;
		int ycor;
		public chageBoard(Pane temp1, ArrayList<coordinates> pickedOption, int xcor, int ycor){
			this.temp1=temp1;
			this.pickedOption=pickedOption;
			this.xcor=xcor;
			this.ycor=ycor;
		}
		public void run(){
			changePieces();
		}
		public void changePieces(/*Pane temp1, ArrayList<coordinates> pickedOption, int xcor, int ycor*/){

			piece piece;
			piece=(piece) temp1.getChildren().get(0);
			if(turnFlag.matches("player1"))
			{
				piece.setFill(Color.AQUA);
				board[xcor][ycor]=1;
			}
			else if (turnFlag.matches("player2"))
			{
				piece.setFill(Color.SLATEGRAY);
				board[xcor][ycor]=2;
			}
			temp1.setDisable(true);
			pickedOption.remove(0);
			//Platform.runLater ( () -> 	changePieces());
		}


	}


	//gui function to highlight the move with mouse cursor
	@FXML
	void highlight(MouseEvent event)
	{
		if(!computerFlagP1 ||(!computerFlagP2 && this.turnFlag.matches("player2"))){
		Pane temp = (Pane) event.getSource();
		temp.setStyle("-fx-background-color:#dae7f3;");
		String xPane=temp.getId().substring(1).split("_")[0];
		String yPane=temp.getId().split("_")[1];
		ArrayList<coordinates> values = validMoves.get(new coordinates(Integer.parseInt(xPane),Integer.parseInt(yPane))).optionscoordinates;
		for(coordinates optioncount:values){
			temp=panel[optioncount.x][optioncount.y];
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
		}
	}
	//gui function to remove highlight the move with mouse cursor
	@FXML
	void remove_highlight(MouseEvent event)
	{
		if(!computerFlagP1 ||!(computerFlagP2 && this.turnFlag.matches("player2"))){
		EventQueue.invokeLater(new Runnable(){
			public void run(){

		Pane temp = (Pane) event.getSource();
		coordinates cord;
		cord = getCoordinates(temp.getId().substring(1, temp.getId().length()));
		if ((cord.x + cord.y) % 2 == 0)
			temp.setStyle("-fx-background-color:white;");
		else
			temp.setStyle("-fx-background-color:pink;");
			String xPane=temp.getId().substring(1).split("_")[0];
			String yPane=temp.getId().split("_")[1];
			System.out.print(xPane+" "+yPane+"\n");
			if((validMoves.get(new coordinates(Integer.parseInt(xPane),Integer.parseInt(yPane)))!=null)){
				ArrayList<coordinates> values = validMoves.get(new coordinates(Integer.parseInt(xPane),Integer.parseInt(yPane))).optionscoordinates;
				for(coordinates optioncount:values){
					temp=panel[optioncount.x][optioncount.y];
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
			}
		}
		});
			}
	}

	//click handler for choosing a move, after clicking a move and establishing the coordinates it sends them to makemove to actually change the board
	@FXML
	void movePieces(MouseEvent event){
		Pane temp = null;
		if(!this.computerFlagP1 &&( this.turnFlag.matches("player1") || !this.computerFlagP2)){//if player VS player
		temp = (Pane) event.getSource();
		}

		if(makeMove(temp)==1)
			return;


		//after playing the players turn, play a computer turn.
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				while(computerFlagP2 && turnFlag.matches("player2")){
				movePieces();
				}
			}
		});
	}
	void movePieces(){	//overloaded function for PC VS PC play, requires no mouse trigger, establishes a move depending on results from minimax and alpha beta.
		//filling all the required data and sending to the chosen function.
		Pane temp = null;
		coordinates temp1 = null;
		MiniMaxNode root = new MiniMaxNode();
		root.board = board;
		if (this.turnFlag.equals("player1"))
			root.blackOrWhite = true;
		else
			root.blackOrWhite = false;
		root.children = new ArrayList<MiniMaxNode>();
		root.isMinOrMax = true;
		root.value = -1000000000;//Double.MIN_VALUE;
		countBlackAndWhite(board);
		root.nOfBlack = blackCounter;
		root.nOfWhite = whiteCounter;
		if(turnFlag.matches("player1")){
			switch (this.p1MinOrMax){
			case "MiniMax":
				minimax(root,depth);
				break;
			case "Alpha-Beta":
				alphaBeta(root, depth);
				break;
			}
		}
		else if(turnFlag.matches("player2")){
			switch (this.p2MinOrMax){
			case "MiniMax":
				minimax(root,depth);
				break;
			case "Alpha-Beta":
				alphaBeta(root, depth);
				break;
			}
		}
		for (MiniMaxNode iter : root.children){

			if (iter.value >= root.value) {
				root.value = iter.value;
				temp1 = iter.coord;
				break;
			}
		}
		temp=panel[temp1.x][temp1.y];
		//acquiring the chosen panel according to its name.
		//EventQueue.invokeLater(new Runnable(){
		//	public void run(){
		//	}});
		makeMove(temp);
	}

	  int makeMove(Pane temp)
	{
		 if(!firstTurn)
		 {
			 validMoves.clear();
			 calculateMoves(board,this.validMoves,this.turnFlag);
		 }
		 else
			 firstTurn=false;

		Color color = new Color(0, 0, 0, 0);
		Pane panetochange = null;
		int yIndex=Integer.parseInt(temp.getId().substring(1).split("_")[1]);
		int xIndex=Integer.parseInt(temp.getId().substring(1).split("_")[0]);
		//get the pieces to covert according to the chosen move
		if(!this.validMoves.isEmpty() && this.validMoves.containsKey(new coordinates(xIndex,yIndex))){
			ArrayList<coordinates> pickedOption=this.validMoves.get(new coordinates(xIndex,yIndex)).optionscoordinates;

			while(!pickedOption.isEmpty())
			{
				int xcor,ycor;
				xcor=pickedOption.get(0).x;
				ycor=pickedOption.get(0).y;
				panetochange=panel[xcor][ycor];
				//change actual pieces color
				Pane temp1=(Pane) panetochange;
				chageBoard switchColor = new chageBoard(temp1,pickedOption,xcor,ycor);
				switchColor.changePieces();
				/*piece piece;
				piece=(piece) temp1.getChildren().get(0);
				if(turnFlag.matches("player1"))
				{
					piece.setFill(Color.AQUA);
					board[xcor][ycor]=1;
				}
				else if (turnFlag.matches("player2"))
				{
					piece.setFill(Color.SLATEGRAY);
					board[xcor][ycor]=2;
				}
				temp1.setDisable(true);
				pickedOption.remove(0);*/
			}

			}
			Iterator<Map.Entry<coordinates,options>> it=this.validMoves.entrySet().iterator();
			Object optionToDisable = null;
			while(it.hasNext())
			{
				Map.Entry<coordinates,options> entry=it.next();
				optionToDisable=panel[entry.getKey().x][entry.getKey().y];
				//disable the options for one player to remove them from boards towards other player turn.
					Pane paneDisable=(Pane)optionToDisable;
					paneDisable.setDisable(true);
			}
			//update board to current status and change player turn.
		if (this.turnFlag.matches("player1"))
		{
			color = Color.AQUA;
			this.board[xIndex][yIndex] = 1;
			this.turnFlag="player2";
		}
		else if (this.turnFlag.matches("player2"))
		{
			color = Color.SLATEGRAY;
			this.board[xIndex][yIndex] = 2;
			this.turnFlag="player1";
		}
		//place new piece in chosen panel
		piece circle = new piece(24, color);
		circle.relocate(2, 2);
		pcVSpc pc=new pcVSpc();
		pc.change(temp,circle);
		temp.setDisable(true);
		this.validMoves.clear();//get next player moves
		if((calculateMoves(board,validMoves,this.turnFlag))){//check if next player has moves, if not, change back to the current player
			if(this.turnFlag.matches("player1"))
				this.turnFlag="player2";
			else if(this.turnFlag.matches("player2"))
				this.turnFlag="player1";
			if (calculateMoves(board,validMoves,this.turnFlag)){
			//if both players has no moves count and finish the game
				countBlackAndWhite(board);
				if(whiteCounter>blackCounter)
					winner.setText("Player 1 Wins");
				if(whiteCounter<blackCounter)
					winner.setText("Player 2 Wins");
				endGame.setVisible(true);
				return 1;
			}
		}
		 if(turnFlag.matches("player1"))
			 ((piece)this.turnShow.getChildren().get(0)).setFill(Color.AQUA);

		 if(turnFlag.matches("player2"))
			 ((piece)this.turnShow.getChildren().get(0)).setFill(Color.SLATEGRAY);
		return 0;
}

	  @FXML
	  //show score and load setting view
	    void newGame(ActionEvent event) throws IOException {
		  FXMLLoader loader = new FXMLLoader();
			Parent home_page_parent = loader.load(getClass().getResource(
					"params.fxml").openStream());
			Scene board = new Scene(home_page_parent);
			Stage board_stage = (Stage) ((Node) event.getSource()).getScene()
					.getWindow();
			board_stage.setScene(board);
			board_stage.show();
	    }

	public void initializeBoard()
	{//initialize board and set everything
		Pane toInsert = null;
		for (int i = 0; i < panel.length; i++) {
		    for (int j = 0; j < panel[i].length; j++) {
		        try {
		        	toInsert = (Pane) getClass().getDeclaredField("s"+Integer.toString(i)+"_"+Integer.toString(j)).get(this);
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
		        panel[i][j] =  toInsert;
		    }
		}
		piece player = new piece(24, Color.AQUA);
		player.relocate(2, 2);
		this.turnShow.getChildren().add(player);
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
		calculateMoves(board,validMoves, this.turnFlag);
		if(computerFlagP1){//if Pc VS Pc, run another thread and the proper function.
			EventQueue.invokeLater(new Runnable(){
				public void run(){
					while(true){
						if(whiteCounter+blackCounter<=142){
							try {
								TimeUnit.MILLISECONDS.sleep(40);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						movePieces();
						try {
							TimeUnit.MILLISECONDS.sleep(40);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						}
						else{
							if(whiteCounter>blackCounter)
								winner.setText("Player 1 Wins");
							if(whiteCounter<blackCounter)
								winner.setText("Player 2 Wins");
							endGame.setVisible(true);
							break;
						}
					}
				}
			});
		}
	}

	//next block is getters functions to retrieve setting from previous scene.
	public void GetHeuristic1(String Heuristic)
	{
		this.p2Heuristic=Heuristic;
	}

	public void GetHeuristic2(String Heuristic)
	{
		this.p2Heuristic=Heuristic;
	}

	public void getAiFunction(String function)
	{
		this.p1MinOrMax=function;
	}
	public void getAiFunction2(String value) {
		this.p2MinOrMax=value;
	}

	public void getDifficulty(String difficulty)
	{
		this.depth=Integer.parseInt(difficulty);
	}

	public void getPlayerOrComputer(int playAgainst)
	{
		switch (playAgainst){
			case 1://human vs. Pc-->1
				this.computerFlagP1=false;
				this.computerFlagP2=true;
				break;
			case 2://human vs. human-->2
				this.computerFlagP1=false;
				this.computerFlagP2=false;
				break;
			case 3://Pc vs. Pc-->3
				this.computerFlagP1=true;
				this.computerFlagP2=true;
		}
	}


	//end

	public coordinates getCoordinates(String name)
	{
		coordinates temp = new coordinates(Integer.parseInt(name.split("_")[0]), Integer.parseInt(name.split("_")[1]));
		return temp;
	}

	public boolean calculateMoves(byte[][] board,Hashtable<coordinates,options> options, String turnFlag)
	{//calculating players moves using check lines.
		int playerNumber = 0;
		if (turnFlag.matches("player1"))
			playerNumber = 1;
		else if (turnFlag.matches("player2"))
			playerNumber = 2;
		for (int i = 0; i < 12; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				if (board[i][j] == 0)
					continue;
				else if (board[i][j] == playerNumber)
				{
					checkLines(i, j, DIRECTION.ascending, options, board, turnFlag);
					checkLines(i, j, DIRECTION.descending, options, board, turnFlag);
					checkLines(i, j, DIRECTION.rightToLeft, options, board, turnFlag);
					checkLines(i, j, DIRECTION.leftToRight, options, board, turnFlag);
					checkLines(i, j, DIAGONAL.downAndLeft, options, board, turnFlag);
					checkLines(i, j, DIAGONAL.downAndRight, options, board, turnFlag);
					checkLines(i, j, DIAGONAL.upAndLeft, options, board, turnFlag);
					checkLines(i, j, DIAGONAL.upAndRight, options, board, turnFlag);
				}
			}
		}
		return options.isEmpty();
	}

	public void checkLines(int x, int y, DIRECTION direction, Hashtable<coordinates,options> validMoves, byte[][] board, String turnFlag)
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
		if (turnFlag.matches("player1"))
			playerNumber = 2;
		else if (turnFlag.matches("player2"))
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
				Pane temp = panel[tempX][tempY];
				Pane temp1 = (Pane) temp;
				if(validMoves.containsKey(new coordinates(tempX,tempY)))
					validMoves.get(new coordinates(tempX,tempY)).optionscoordinates.addAll(tempstreak);
				else
					validMoves.put(new coordinates(tempX,tempY), new options(tempstreak));
				if(validMoves.equals(this.validMoves))
				temp1.setDisable(false);

			}
		}
	}

	public void checkLines(int x, int y,DIAGONAL diagonal, Hashtable<coordinates,options> validMoves, byte[][] board, String turnFlag)
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
    	if (turnFlag.matches("player1"))
    		playerNumber=2;
    	else if(turnFlag.matches("player2"))
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
    			Pane temp = panel[tempX][tempY];
				Pane temp1=(Pane)temp;
				if(validMoves.containsKey(new coordinates(tempX,tempY)))
					validMoves.get(new coordinates(tempX,tempY)).optionscoordinates.addAll(tempstreak);
				else
					validMoves.put(new coordinates(tempX,tempY), new options(tempstreak));
				if(validMoves.equals(this.validMoves))
				temp1.setDisable(false);

    		}
    	}
    }


	void minimax(MiniMaxNode node, int depth){

		Hashtable<coordinates,options> allOptions=new Hashtable<coordinates,options>();
		 if (depth <= 0){
			 // stop step  and also calculate Heuristic func value on the leaf
			 node.value = HeuristicFunc(node.nOfWhite,node.nOfBlack);
             return ;
         }
		 else
		 {
			 if(node.blackOrWhite)
        	    calculateMoves(node.board,allOptions, "player1");
			 else
				 calculateMoves(node.board,allOptions, "player2");

        	if (!(allOptions.isEmpty())){

	        	Iterator<Map.Entry<coordinates,options>> it=allOptions.entrySet().iterator();
	 			while(it.hasNext())
	 			{
	 				Map.Entry<coordinates,options> entry=it.next();
	 				MiniMaxNode newNode = new MiniMaxNode();
	 				newNode.children = new ArrayList<MiniMaxNode>();
	 				newNode.coord = entry.getKey();
	 				newNode.board = duplicateBoard(node.board);
	 				newNode.isMinOrMax = !node.isMinOrMax;
	 				if (newNode.isMinOrMax){
	 					newNode.value = -1000000000;
	 				}
	 				else{
	 					newNode.value = Double.MAX_VALUE;
	 				}
	 				node.children.add(newNode);
	 				newNode.blackOrWhite = !node.blackOrWhite;
	 				byte tempBoW= (byte) (((newNode.blackOrWhite) ? 1 : 0) + 1);
	 				if(newNode.blackOrWhite)
	 				{
	 					newNode.nOfBlack = node.nOfBlack + entry.getValue().optionscoordinates.size() + 1;
	 					newNode.nOfWhite = node.nOfWhite - entry.getValue().optionscoordinates.size();
	 				}
	 				else
	 				{
	 					newNode.nOfWhite = node.nOfWhite + entry.getValue().optionscoordinates.size() + 1;
	 					newNode.nOfBlack = node.nOfBlack - entry.getValue().optionscoordinates.size();
	 				}
	 				changeBoard(newNode.board, entry, tempBoW);
	 				minimax(newNode, depth - 1);
	 				if(node.isMinOrMax)
	 				{
	 					node.value = Math.max(node.value, newNode.value);
	 				}
	 				else
	 				{
	 					node.value = Math.min(node.value, newNode.value);
	 				}
	 			}
        	}else{
        		node.value = HeuristicFunc(node.nOfWhite,node.nOfBlack);
        	}

 			return;

         }
	}

	private byte[][] duplicateBoard(byte[][] board){
		byte[][] tempBoard = new byte[12][12];
		for (int i=0; i<12; i++)
			for(int j=0; j<12; j++)
				tempBoard[i][j]=board[i][j];
		return tempBoard;
	}



	private double HeuristicFunc(int whiteCount, int blackCount){
		if (this.turnFlag.equals("player1")){
			return 100*(((double)whiteCount - (double)blackCount) / ((double)whiteCount+(double)blackCount)) ;
		}

		return 100*(((double)blackCount - (double)whiteCount)/ ((double)whiteCount+(double)blackCount)) ;

	}

	private double HeuristicFunc2(byte[][] board){
		int whiteCorners = 0;
		int blackCorners = 0;
		if (board[0][0] == 1)
			whiteCorners++;
		else if (board[0][0] == 2)
			blackCorners++;

		if (board[0][11] == 1)
			whiteCorners++;
		else if (board[0][11] == 2)
			blackCorners++;

		if (board[11][0] == 1)
			whiteCorners++;
		else if (board[11][0] == 2)
			blackCorners++;

		if (board[11][11] == 1)
			whiteCorners++;
		else if (board[11][11] == 2)
			blackCorners++;

		if ((whiteCorners+blackCorners) == 0){
			return 0;
		}
		else if (this.turnFlag.equals("player1")){


			return 100*(((double)whiteCorners - (double)blackCorners) / ((double)whiteCorners+(double)blackCorners)) ;
		}

		return 100*(((double)blackCorners - (double)whiteCorners)/ ((double)whiteCorners+(double)blackCorners)) ;

	}


	private double HeuristicFunc3(byte[][] board){
		Hashtable<coordinates,options> allOptions=new Hashtable<coordinates,options>();
		calculateMoves(board,allOptions,"player1");
		int whiteMoves = allOptions.size();
		calculateMoves(board,allOptions,"player2");
		int blackMoves = allOptions.size();
		if ((whiteMoves+blackMoves) == 0){
			return 0;
		}
		else if (this.turnFlag.equals("player1")){


			return 100*(((double)whiteMoves - (double)blackMoves) / ((double)whiteMoves+(double)blackMoves)) ;
		}

		return 100*(((double)blackMoves - (double)whiteMoves)/ ((double)blackMoves+(double)whiteMoves)) ;
	}




	private void changeBoard(byte board[][], Map.Entry<coordinates,options> entry, byte bOrW){
		ArrayList<coordinates> flipdisks = entry.getValue().optionscoordinates;
		int xcor = entry.getKey().x;
		int ycor = entry.getKey().y;
		board[xcor][ycor]=bOrW;
		while(!flipdisks.isEmpty())
		{
			xcor=flipdisks.get(0).x;
			ycor=flipdisks.get(0).y;
			board[xcor][ycor]=bOrW;
			flipdisks.remove(0);
		}
	}
	private void countBlackAndWhite(byte[][] board){
		whiteCounter = 0;
		blackCounter = 0;
		for (int i=0; i<12; i++){
			for(int j=0; j<12; j++){
				if(board[i][j]== 1)
					whiteCounter++;
				else if(board[i][j]== 2)
			        blackCounter++;
			}
		}

	}

	void alphaBeta(MiniMaxNode node, int depth){
		node.value = maxValue ( node, -1000000000, Double.MAX_VALUE, depth );
	}

	double maxValue(MiniMaxNode node, double alpha, double beta, int depth){

		Hashtable<coordinates,options> allOptions=new Hashtable<coordinates,options>();
		 if (depth <= 0){
			 // stop step  and also calculate Heuristic func value on the leaf
			 node.value = HeuristicFunc(node.nOfWhite,node.nOfBlack);

            return node.value ;
        }else{
        	if(node.blackOrWhite)
        	    calculateMoves(node.board,allOptions, "player1");
			 else
				 calculateMoves(node.board,allOptions, "player2");

        	if (!(allOptions.isEmpty())){
	        	Iterator<Map.Entry<coordinates,options>> it=allOptions.entrySet().iterator();
	 			while(it.hasNext())
	 			{
	 				Map.Entry<coordinates,options> entry=it.next();
	 				MiniMaxNode newNode = new MiniMaxNode();
	 				newNode.children = new ArrayList<MiniMaxNode>();
	 				newNode.coord = entry.getKey();
	 				newNode.board = duplicateBoard(node.board);
	 				newNode.isMinOrMax = !node.isMinOrMax;
	 				if (newNode.isMinOrMax){
	 					newNode.value = -1000000000;
	 				}else{
	 					newNode.value = Double.MAX_VALUE;
	 				}
	 				node.children.add(newNode);
	 				newNode.blackOrWhite = !node.blackOrWhite;
	 				byte tempBoW= (byte) (((newNode.blackOrWhite) ? 1 : 0) + 1);
	 				if(newNode.blackOrWhite)
	 				{
	 					newNode.nOfBlack = node.nOfBlack + entry.getValue().optionscoordinates.size() + 1;
	 					newNode.nOfWhite = node.nOfWhite - entry.getValue().optionscoordinates.size();
	 				}
	 				else
	 				{
	 					newNode.nOfWhite = node.nOfWhite + entry.getValue().optionscoordinates.size() + 1;
	 					newNode.nOfBlack = node.nOfBlack - entry.getValue().optionscoordinates.size();
	 				}
	 				changeBoard(newNode.board, entry, tempBoW);
	 				node.value = Math.max(node.value, minValue(newNode, alpha, beta, depth - 1));
	 				if (node.value >= beta)
	 					break;
	 				alpha = Math.max (alpha, node.value);

	 			}
        	}
        	else{
        		node.value = HeuristicFunc(node.nOfWhite,node.nOfBlack);
        	}
 			return node.value;

        }
	}

	double minValue(MiniMaxNode node, double alpha, double beta, int depth){
		Hashtable<coordinates,options> allOptions=new Hashtable<coordinates,options>();
		 if (depth <= 0){
			 // stop step  and also calculate Heuristic func value on the leaf
			 node.value = HeuristicFunc(node.nOfWhite,node.nOfBlack);

           return node.value ;
       }else{
    	   if(node.blackOrWhite)
       	      calculateMoves(node.board,allOptions, "player1");
			 else
			  calculateMoves(node.board,allOptions, "player2");
    	   if (!(allOptions.isEmpty())){
	       	    Iterator<Map.Entry<coordinates,options>> it=allOptions.entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry<coordinates,options> entry=it.next();
					MiniMaxNode newNode = new MiniMaxNode();
					newNode.children = new ArrayList<MiniMaxNode>();
					newNode.coord = entry.getKey();
					newNode.board = duplicateBoard(node.board);
					newNode.isMinOrMax = !node.isMinOrMax;
					if (newNode.isMinOrMax){
						newNode.value = -1000000000;
					}else{
						newNode.value = Double.MAX_VALUE;
					}
					node.children.add(newNode);
					newNode.blackOrWhite = !node.blackOrWhite;
	 				byte tempBoW= (byte) (((newNode.blackOrWhite) ? 1 : 0) + 1);
	 				if(newNode.blackOrWhite)
	 				{
	 					newNode.nOfBlack = node.nOfBlack + entry.getValue().optionscoordinates.size() + 1;
	 					newNode.nOfWhite = node.nOfWhite - entry.getValue().optionscoordinates.size();
	 				}
	 				else
	 				{
	 					newNode.nOfWhite = node.nOfWhite + entry.getValue().optionscoordinates.size() + 1;
	 					newNode.nOfBlack = node.nOfBlack - entry.getValue().optionscoordinates.size();
	 				}
	 				changeBoard(newNode.board, entry, tempBoW);
					node.value = Math.min(node.value, maxValue(newNode, alpha, beta, depth - 1));
					if (node.value <= alpha)
						break;
					beta = Math.min (beta, node.value);
				}
    	   }
    	   else{
       		node.value = HeuristicFunc(node.nOfWhite,node.nOfBlack);
       	}
			return node.value;

       }
	}
}
