package view;

import chess.Board;
import control.GameController;

/**
 * Created by didi on 17/9/13.
 */
public class CheckBoardView {


    public static void main(String[] args){
        String curBoard = "bx002,bb004,rm105,bs025,rj135,rp136,bz438,rj040,rz060,rz468,rs175,bj087,bj193,rb095";
        Board board = Board.loadBoard(curBoard);

        GameView view = new GameView(new GameController());

        view.init(board);
    }
}
