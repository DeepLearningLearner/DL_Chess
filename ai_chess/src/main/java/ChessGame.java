import alogrithm.LRSearchModel;
import alogrithm.SearchModel;
import chess.Board;
import chess.Player;
import chess.Rules;
import control.GameController;
import view.GameView;

/**
 * Created by Tong on 12.08.
 * Main process of Chinese Chess Game.
 */
public class ChessGame {
    private Board board;

    private GameController controller;
    private GameView view;

    public static void main(String[] args) throws InterruptedException {
        ChessGame game = new ChessGame();

        game.init();
        game.run();
    }

    public void init() {
        controller = new GameController();
        board = controller.playChess();

        // 添加选手.
        board.addPlayer(new Player(board, 'r', new LRSearchModel()));
        board.addPlayer(new Player(board, 'b', new SearchModel()));
        board.initTrace(null, 'n');
        
        view = new GameView(controller);
        view.init(board);
    }

    public void run() throws InterruptedException {
        while (Rules.hasWin(board) == 'x') {
            view.showPlayer('r');
            /* AI black. */
            controller.responseMoveChess(board, view, 'r');

            //while (board.player ==  'r')

            if (Rules.hasWin(board) != 'x'){
            	board.notifySucess(Rules.hasWin(board));
            	view.showWinner(Rules.hasWin(board));
            }
            Thread.sleep(2000);
            view.showPlayer('b');
            /* AI in. */
            controller.responseMoveChess(board, view, 'b');
        }
        board.notifySucess(Rules.hasWin(board));
        view.showWinner(Rules.hasWin(board));
    }
}