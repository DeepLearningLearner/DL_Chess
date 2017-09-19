import alogrithm.SearchModel;
import chess.Board;
import chess.Player;
import chess.Rules;
import control.GameController;


public class ChessNoView {
    private Board board;

    private GameController controller;

    public static void main(String[] args) throws InterruptedException {
        ChessNoView game = new ChessNoView();

        int times = 0;
        while(times <= 100000){
            game.init();
            game.run();
            times++;
        }

    }

    public void init() {
        controller = new GameController();
        board = controller.playChess();

        // 添加选手.
        board.addPlayer(new Player(board, 'r', new  SearchModel()));
        board.addPlayer(new Player(board, 'b', new  SearchModel()));
        board.initTrace(null, 'n');

    }

    public void run() throws InterruptedException {
        while (Rules.hasWin(board) == 'x') {

            /* AI black. */
            controller.responseMoveChess(board, 'r');


            if (Rules.hasWin(board) != 'x'){
                board.notifySucess(Rules.hasWin(board));
                return;
            }

            /* AI in. */
            controller.responseMoveChess(board, 'b');

            if(board.getPlayer('r').getStep() >= 200){
                board.notifySucess('x');
                return;
            }
        }
        board.notifySucess(Rules.hasWin(board));
    }
}

