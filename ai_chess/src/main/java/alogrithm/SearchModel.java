package alogrithm;

import chess.Board;
import chess.Piece;
import chess.Player;
import chess.Rules;

import java.util.ArrayList;

/**
 * Created by Tong on 12.08.
 * Alpha beta search.
 */
public class SearchModel implements Search{
    private static int DEPTH = 2;


    /**
     * 每次不能够动象棋，每次search, 都需要将board记忆下来，然后进行思考.
     */
    private Board thinkingBoard;
    
    private Player player;
    
    private EvalModel evalModel;
    
    public SearchModel(){
    	this.evalModel = new EvalModel();
    }
    
    
    
    
    public BaseStepNode search() {
    	
    	thinkingBoard = this.player.getBoard().copyForThinking();
    	
        if (thinkingBoard.pieces.size() < 28)
            DEPTH = 2;
        if (thinkingBoard.pieces.size() < 16)
            DEPTH = 3;
        if (thinkingBoard.pieces.size() < 6)
            DEPTH = 4;
        if (thinkingBoard.pieces.size() < 4)
            DEPTH = 5;
        long startTime = System.currentTimeMillis();
        BaseStepNode best = null;
        ArrayList<BaseStepNode> moves = thinkingBoard.generateMovesForAll(player.getColor());
        
        for (BaseStepNode n : moves) {
            /* Move*/
            Piece eaten = thinkingBoard.updatePiece(n.piece, n.to);
            
            Piece orgPiece = thinkingBoard.pieces.get(n.piece);
            
            orgPiece.update(n.to, eaten != null, Rules.isKillBoss(n.piece, n.to, this.thinkingBoard), player.getStep() + 1);
            
            if(!Rules.isIllegal(this.thinkingBoard, orgPiece)){
            	n.value = alphaBeta(DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false, player.getColor());
                
                /* Select a best move during searching to save time*/
                if (best == null || n.value >= best.value)
                	best = n;
            }
            
            
            /* Back move*/
            thinkingBoard.updatePiece(n.piece, n.from);
            orgPiece.back();
            if (eaten != null) {
            	thinkingBoard.pieces.put(eaten.key, eaten);
            	thinkingBoard.backPiece(eaten.key);
            }
        }
        long finishTime = System.currentTimeMillis();
        
        
        return best;
    }


    private int alphaBeta(int depth, int alpha, int beta, boolean isMax, final char player) {
        /* Return evaluation if reaching leaf node or any side won.*/
        if (depth == 0 || Rules.hasWin(this.thinkingBoard) != 'x')
        	return this.evalModel.eval(this.thinkingBoard, player, isMax);
        ArrayList<BaseStepNode> moves = null;
        
        if(isMax){
        	moves = thinkingBoard.generateMovesForAll(player);
        }else{
        	if(player == 'r'){
        		moves = thinkingBoard.generateMovesForAll('b');
        	}else{
        		moves = thinkingBoard.generateMovesForAll('r');
        	}	
        }
        

        synchronized (this) {
            for (final BaseStepNode n : moves) {
                Piece eaten = thinkingBoard.updatePiece(n.piece, n.to);
                

                /* Only adopt multi threading strategy in depth 2. To avoid conjunction.*/
                if (isMax) alpha = Math.max(alpha, alphaBeta(depth - 1, alpha, beta, false, player));
                else beta = Math.min(beta, alphaBeta(depth - 1, alpha, beta, true, player));

                thinkingBoard.updatePiece(n.piece, n.from);
                if (eaten != null) {
                	thinkingBoard.pieces.put(eaten.key, eaten);
                	thinkingBoard.backPiece(eaten.key);
                }
            /* Cut-off */
                if (beta <= alpha) break;
            }
        }
        return isMax ? alpha : beta;
    }


	public void setPlayer(Player player) {
		this.player = player;
	}



}