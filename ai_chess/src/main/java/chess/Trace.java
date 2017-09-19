package chess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 用于记录棋局和确保规则，防止重复走棋.
 */
public class Trace{

	private Board board = null;

	private LinkedList<String> traces;

	/**
	 * 同时保存下来整个的棋局.
	 */
	private List<String> boardHistory;

	/**
	 *  存储到日志中.
	 */
	private BufferedWriter logger;


	/**
	 * 该局谁胜利.
	 */
	private char winner = 'n';

	/**
	 * @param board
	 */

	public Trace(Board  board, String loggerFile, char winner){
		this.board = board;
		this.traces = new LinkedList<String>();
		this.boardHistory = new ArrayList<String>();
		try {
			if(loggerFile == null){
				this.logger = new BufferedWriter(new FileWriter(System.currentTimeMillis() + ".txt"));
			}else{
				this.logger = new BufferedWriter(new FileWriter(loggerFile));
			}

			this.winner = winner;

			System.err.println("logger sucess!");
		} catch (IOException e) {

		}
	}

	public void addTrace(Piece piece){
		this.traces.add(piece.toString());

		this.boardHistory.add(this.board.getCurBoard());

		try {
			if(this.winner != 'n'){
				this.logger.write( this.board.getCurBoard() + "\t" + this.winner + "\n");
			}else{
				this.logger.write( this.board.getCurBoard() + "\n");
			}
			this.logger.flush();
		} catch (IOException e) {
		}

	}


	public void notifySucess(char ch) {
		try {
			this.logger.write(ch + "\n");
			this.logger.close();
		} catch (IOException e) {
		}
	}
}


