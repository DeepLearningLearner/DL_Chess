package chess;

import alogrithm.BaseStepNode;
import alogrithm.Search;

import java.util.HashMap;
import java.util.Map;

/**
 * 玩家， 心里边有自己的思路， 有自己的棋局，
 * @author lixiangyang
 *
 */
public class Player {
	
	private Board board;
	
	private char color;
	
	private Search search;
	
	private int step;
	/**
	 * 玩家自己的棋子.
	 */
	private Map<String, Piece> ownPieces;
	
	public Player(Board board, char color, Search search){
		this.board = board;
		this.color = color;
		this.search = search;
		
		this.ownPieces = new HashMap<String, Piece>();
		
		// 初始化自己的棋子.
		for(Piece piece : this.board.pieces.values()){
			if(piece.key.startsWith(color + "")){
				ownPieces.put(piece.key, piece);
			}
		}
		
		// 初始化 search.
		search.setPlayer(this);
	}
	
	
	public BaseStepNode play(){
		BaseStepNode node = search.search();
		
		// 更新棋子状态.
		
		return node;
	}
	
	public char getColor(){
		return color;
	}


	public void killPiece(int[] loc) {
		Piece newPiece = this.board.getPiece(loc);
		
		if(newPiece != null && newPiece.color == this.color){
			this.ownPieces.remove(newPiece.key);
		}
	}


	public void updatePiece(String key, int[] position) {
		Piece piece = this.ownPieces.get(key);
		
		// 判断它是否吃子.
		Piece eaten = this.board.getPiece(position);
		
		// 判断它是否将军.
		boolean isKillBoss = Rules.isKillBoss(key, position, board);
		
		// 更新步长.
		this.step += 1;
		
		piece.update(position, eaten != null, isKillBoss, this.step);
	}
	
	public Board getBoard(){
		return this.board;
	}


	public int getStep() {
		return this.step;
	}
}
