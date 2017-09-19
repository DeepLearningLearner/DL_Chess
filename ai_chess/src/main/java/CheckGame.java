import alogrithm.LRSearchModel;
import chess.Board;
import chess.Player;
import control.GameController;


public class CheckGame {
    private Board board;

    private GameController controller;

    public static void main(String[] args) throws InterruptedException {
        CheckGame game = new CheckGame();

        game.init();
        game.run();

    }

    public void init() {
        controller = new GameController();
        board = controller.playChess();

        board.initTrace(null, 'x');
        // 添加选手.
        board.addPlayer(new Player(board, 'r', new LRSearchModel()));
        board.addPlayer(new Player(board, 'b', new  LRSearchModel()));
    }

    public void run() throws InterruptedException {
        /* AI black. */
        controller.responseMoveChess(board, 'r');
    }
}

