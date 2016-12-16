/**
 * Sample Skeleton for 'ReversiScene.fxml' Controller Class
 */
package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * Sample Skeleton for 'ReversiScene.fxml' Controller Class
 */



public class Reversicontroller {


    @FXML
    void highlight(MouseEvent event) {

    }



    @FXML
    void makeMove(MouseEvent event) {
    	int x,y;
    	x=(int) (event.getX())/53;
    	y=(int) (event.getY())/52;
    	System.out.print("x pane is:"+x +" and y pane is:"+y+"\n");

    }

}
