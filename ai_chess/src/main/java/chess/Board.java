package chess;

import alogrithm.BaseStepNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tong on 12.03.
 * Chess > Board entity
 */


public class Board{
    public final static int BOARD_WIDTH = 9, BOARD_HEIGHT = 10;
    public Map<String, Piece> pieces;
    public char player = 'r';
    private Piece[][] cells = new Piece[BOARD_HEIGHT][BOARD_WIDTH];

    private Player[] players = new Player[2];
    
    /**
     * 对棋局进行记录便于分析.
     */
    private Trace trace = null;
    
    public void initTrace(String logerFile, char winner){
    	this.trace = new Trace(this, logerFile, winner);
    }
    
    public boolean isInside(int[] position) {
        return isInside(position[0], position[1]);
    }

    public boolean isInside(int x, int y) {
        return !(x < 0 || x >= BOARD_HEIGHT
                || y < 0 || y >= BOARD_WIDTH);
    }

    public boolean isEmpty(int[] position) {
        return isEmpty(position[0], position[1]);
    }

    public boolean isEmpty(int x, int y) {
        return isInside(x, y) && cells[x][y] == null;
    }


    public boolean update(Piece piece) {
        int[] pos = piece.position;
        cells[pos[0]][pos[1]] = piece;
        return true;
    }

    public Piece updatePiece(String key, int[] newPos) {
        Piece orig = pieces.get(key);
        Piece inNewPos = getPiece(newPos);
        /* If the new slot has been taken by another piece, then it will be killed.*/
        if (inNewPos != null)
            pieces.remove(inNewPos.key);
        /* Clear original slot and updatePiece new slot.*/
        int[] origPos = orig.position;
        
        // 该地方也更新了board.
        cells[origPos[0]][origPos[1]] = null;
        cells[newPos[0]][newPos[1]] = orig;
        orig.position = newPos;
        
        		
        return inNewPos;
    }
    
    public void changePlayer(){
    	// 该条件执行， 对于人而言，已经结束了下棋，kaishilun则立刻执行下边的流程.
        player = (player == 'r') ? 'b' : 'r';
    }

    public boolean backPiece(String key) {
        int[] origPos = pieces.get(key).position;
        cells[origPos[0]][origPos[1]] = pieces.get(key);
        return true;
    }

    public Piece getPiece(int[] pos) {
        return getPiece(pos[0], pos[1]);
    }

    public Piece getPiece(int x, int y) {
        return cells[x][y];
    }
    
    
    public String getCurBoard(){
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < BOARD_HEIGHT; i++){
			for(int j = 0; j < BOARD_WIDTH; j++){
				if(this.cells[i][j] != null){
					sb.append("," + this.cells[i][j]);
				}
			}
		}
	
		return sb.substring(1);
    }
    
    public void addTrace(String key){
    	Piece piece = this.pieces.get(key);
    
    	this.trace.addTrace(piece);
    }
    

	
    public ArrayList<BaseStepNode> generateMovesForAll(char player) {
        ArrayList<BaseStepNode> moves = new ArrayList<BaseStepNode>();
        for (Map.Entry<String, Piece> stringPieceEntry : pieces.entrySet()) {
            Piece piece = stringPieceEntry.getValue();
            if (piece.color != player) continue;
            for (int[] nxt : Rules.getNextMove(piece.key, piece.position, this))
                moves.add(new BaseStepNode(piece.key, piece.position, nxt));
        }
        return moves;
    }

	public void addPlayer(Player player) {
		char color = player.getColor();
		
		if(color == 'r'){
			this.players[0] = player;
		}else{
			this.players[1] = player;
		}
	}
	
	public Player getPlayer(char color){
		if(color == 'r'){
			return this.players[0];
		}else{
			return this.players[1];
		}
	}
	
	public Player getEnemy(char color){
		if(color == 'r'){
			return this.players[1];
		}else{
			return this.players[0];
		}
	}
	
	public Board copyForThinking(){
		Board board = new Board();
		Map<String, Piece> newPieces = new HashMap<String, Piece>();
		
		board.pieces = newPieces;
		for(Piece piece : this.pieces.values()){
			Piece newPiece = piece.clone();
			newPieces.put(piece.key, newPiece);
			board.update(newPiece);
		}
		
		board.trace = this.trace;
		
		return board;
	}

	public void notifySucess(char hasWin) {
		this.trace.notifySucess(hasWin);
	}

	public static Board loadBoard(String boardInfo){
        String[] pieces = boardInfo.split(",");

        Board board = new Board();


        board.pieces = new HashMap<String,Piece>();

        for(String p : pieces){
            int[] curPos = {p.charAt(3) - '0', p.charAt(4) - '0'};
            board.pieces.put(p.substring(0,3), new Piece(p.substring(0,3),curPos));
            board.cells[curPos[0]][curPos[1]] = board.pieces.get(p.substring(0,3));
        }
        return board;
    }

}