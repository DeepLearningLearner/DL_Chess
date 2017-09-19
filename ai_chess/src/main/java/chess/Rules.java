package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tong on 12.15.
 * Define moving rules of pieces in the board.
 */
public class Rules {

    private static int[] pos;
    private static Board board;
    private static char player;

    private static boolean ignoreColor = false;

    public static ArrayList<int[]> getNextMove(String piece, int[] pos, Board board) {
        return getNextMoveByIgnoreColor(piece, pos, board, false);
    }

    public static ArrayList<int[]> getNextMoveIgnoreColor(String piece, int[] pos, Board board) {
        return getNextMoveByIgnoreColor(piece, pos, board, true);
    }

    private static ArrayList<int[]> getNextMoveByIgnoreColor(String piece, int[] pos, Board board, boolean ignoreColor){

        Rules.ignoreColor = ignoreColor;
        Rules.pos = pos;
        Rules.board = board;
        Rules.player = piece.charAt(0);
        switch (piece.charAt(1)) {
            case 'j':
                return jRules();
            case 'm':
                return mRules();
            case 'p':
                return pRules();
            case 'x':
                return xRules();
            case 's':
                return sRules();
            case 'b':
                return bRules();
            case 'z':
                return zRules();
            default:
                return null;
        }
    }

    private static ArrayList<int[]> mRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[][] target = new int[][]{{1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};
        int[][] obstacle = new int[][]{{0, -1}, {1, 0}, {1, 0}, {0, 1}, {0, 1}, {-1, 0}, {-1, 0}, {0, -1}};
        for (int i = 0; i < target.length; i++) {
            int[] e = new int[]{pos[0] + target[i][0], pos[1] + target[i][1]};
            int[] f = new int[]{pos[0] + obstacle[i][0], pos[1] + obstacle[i][1]};
            if (!board.isInside(e)) continue;
            if (board.isEmpty(f)) {
                if (board.isEmpty(e)) moves.add(e);
                else if (judgePlay(board.getPiece(e).color,player, ignoreColor)) moves.add(e);
            }
        }
        return moves;
    }

    private static ArrayList<int[]> jRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[] yOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] xOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int offset : yOffsets) {
            int[] rMove = new int[]{pos[0], pos[1] + offset};
            if (board.isEmpty(rMove)) moves.add(rMove);
            // 这个地方添加了逻辑，当遇到一个棋子的时候，则会退出.
            else if (board.isInside(rMove) && judgePlay(board.getPiece(rMove).color,player, ignoreColor)) {
                moves.add(rMove);
                break;
            } else break;
        }
        for (int offset : yOffsets) {
            int[] lMove = new int[]{pos[0], pos[1] - offset};
            if (board.isEmpty(lMove)) moves.add(lMove);
            else if (board.isInside(lMove) && judgePlay(board.getPiece(lMove).color,player, ignoreColor)) {
                moves.add(lMove);
                break;
            } else break;
        }
        for (int offset : xOffsets) {
            int[] uMove = new int[]{pos[0] - offset, pos[1]};
            if (board.isEmpty(uMove)) moves.add(uMove);
            else if (board.isInside(uMove) && judgePlay(board.getPiece(uMove).color,player,ignoreColor)) {
                moves.add(uMove);
                break;
            } else break;
        }
        for (int offset : xOffsets) {
            int[] dMove = new int[]{pos[0] + offset, pos[1]};
            if (board.isEmpty(dMove)) moves.add(dMove);
            else if (board.isInside(dMove) && judgePlay(board.getPiece(dMove).color,player, ignoreColor)) {
                moves.add(dMove);
                break;
            } else break;
        }
        return moves;
    }

    private static ArrayList<int[]> pRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[] yOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] xOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        boolean rr = false, ll = false, uu = false, dd = false;
        for (int offset : yOffsets) {
            int[] rMove = new int[]{pos[0], pos[1] + offset};
            if (!board.isInside(rMove)) break;
            boolean e = board.isEmpty(rMove);
            if (!rr) {
                if (e) moves.add(rMove);
                else rr = true;
            } else if (!e) {
                if (judgePlay(board.getPiece(rMove).color,player, ignoreColor)) moves.add(rMove);
                break;
            }
        }
        for (int offset : yOffsets) {
            int[] lMove = new int[]{pos[0], pos[1] - offset};
            if (!board.isInside(lMove)) break;
            boolean e = board.isEmpty(lMove);
            if (!ll) {
                if (e) moves.add(lMove);
                else ll = true;
            } else if (!e) {
                if (judgePlay(board.getPiece(lMove).color,player, ignoreColor)) {
                    moves.add(lMove);
                }
                break;
            }
        }
        for (int offset : xOffsets) {
            int[] uMove = new int[]{pos[0] - offset, pos[1]};
            if (!board.isInside(uMove)) break;
            boolean e = board.isEmpty(uMove);
            if (!uu) {
                if (e) moves.add(uMove);
                else uu = true;
            } else if (!e) {
                if (judgePlay(board.getPiece(uMove).color,player, ignoreColor)) moves.add(uMove);
                break;
            }
        }
        for (int offset : xOffsets) {
            int[] dMove = new int[]{pos[0] + offset, pos[1]};
            if (!board.isInside(dMove)) break;
            boolean e = board.isEmpty(dMove);
            if (!dd) {
                if (e) moves.add(dMove);
                else dd = true;
            } else if (!e) {
                if (judgePlay(board.getPiece(dMove).color, player, ignoreColor)) moves.add(dMove);
                break;
            }
        }
        return moves;
    }

    private static ArrayList<int[]> xRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[][] target = new int[][]{{-2, -2}, {2, -2}, {-2, 2}, {2, 2}};
        int[][] obstacle = new int[][]{{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
        for (int i = 0; i < target.length; i++) {
            int[] e = new int[]{pos[0] + target[i][0], pos[1] + target[i][1]};
            int[] f = new int[]{pos[0] + obstacle[i][0], pos[1] + obstacle[i][1]};
            if (!board.isInside(e) || (e[0] > 4 && player == 'b') || (e[0] < 5 && player == 'r')) continue;
            if (board.isEmpty(f)) {
                if (board.isEmpty(e)) moves.add(e);
                else if (judgePlay(board.getPiece(e).color,player, ignoreColor)) moves.add(e);
            }
        }
        return moves;
    }

    private static ArrayList<int[]> sRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[][] target = new int[][]{{-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
        for (int[] aTarget : target) {
            int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
            if (!board.isInside(e) || ((e[0] > 2 || e[1] < 3 || e[1] > 5) && player == 'b') || ((e[0] < 7 || e[1] < 3 || e[1] > 5) && player == 'r'))
                continue;
            if (board.isEmpty(e)) moves.add(e);
            else if (judgePlay(board.getPiece(e).color,player, ignoreColor)) moves.add(e);
        }
        return moves;
    }

    private static ArrayList<int[]> bRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        /* 3*3 block */
        int[][] target = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] aTarget : target) {
            int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
            if (!board.isInside(e) || ((e[0] > 2 || e[1] < 3 || e[1] > 5) && player == 'b') || ((e[0] < 7 || e[1] < 3 || e[1] > 5) && player == 'r'))
                continue;
            if (board.isEmpty(e)) moves.add(e);
            else if (judgePlay(board.getPiece(e).color,player, ignoreColor)) moves.add(e);
        }
        /* opposite 'b' */
        boolean flag = true;

        if(board.pieces.get("bb0") != null && board.pieces.get("rb0") != null){
            int[] oppoBoss = (player == 'r') ? board.pieces.get("bb0").position : board.pieces.get("rb0").position;
            if (oppoBoss[1] == pos[1]) {
                for (int i = Math.min(oppoBoss[0], pos[0]) + 1; i < Math.max(oppoBoss[0], pos[0]); i++) {
                    if (board.getPiece(i, pos[1]) != null) {
                        flag = false;
                        break;
                    }
                }
                if (flag) moves.add(oppoBoss);
            }
        }


        return moves;
    }

    private static ArrayList<int[]> zRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[][] targetU = new int[][]{{0, 1}, {0, -1}, {-1, 0}};
        int[][] targetD = new int[][]{{0, 1}, {0, -1}, {1, 0}};
        if (player == 'r') {
            if (pos[0] > 4) {
                int[] e = new int[]{pos[0] - 1, pos[1]};
                if (board.isEmpty(e)) moves.add(e);
                else if (judgePlay(board.getPiece(e).color,player,ignoreColor)) moves.add(e);
            } else {
                for (int[] aTarget : targetU) {
                    int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
                    if (!board.isInside(e)) continue;
                    if (board.isEmpty(e)) moves.add(e);
                    else if (judgePlay(board.getPiece(e).color,player, ignoreColor)) moves.add(e);
                }
            }
        }
        if (player == 'b') {
            if (pos[0] < 5) {
                int[] e = new int[]{pos[0] + 1, pos[1]};
                if (board.isEmpty(e)) moves.add(e);
                else if (judgePlay(board.getPiece(e).color, player, ignoreColor)) moves.add(e);
            } else {
                for (int[] aTarget : targetD) {
                    int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
                    if (!board.isInside(e)) continue;
                    if (board.isEmpty(e)) moves.add(e);
                    else if (judgePlay(board.getPiece(e).color,player,ignoreColor)) moves.add(e);
                }
            }
        }

        return moves;
    }
    
    
    /**
     * 判断是否是将军.
     */
    public static boolean isKillBoss(String piece, int[] pos, Board board){
    	try{
    		if(piece.charAt(1) == 'b'){
    			return false;
    		}
    		ArrayList<int[]> moves = Rules.getNextMove(piece, pos, board);
        	
        	for(int[] move : moves){
        		Piece eaten = board.getPiece(move);
        		
        		if(eaten != null && eaten.character == 'b'){
        			return true;
        		}
        	}
        	return false;
    	}catch(Exception e){
    		// 最后杀棋没有老将.
    		return false;
    	}
    	
    }
    
    /**
     * 判断是否赢棋， 放到规则里边.
     * @param board
     * @return
     */
    public static char hasWin(Board board) {
        /**
         * Judge has the game ended.
         * @return 'r' for RED wins, 'b' for BLACK wins, 'x' for game continues.
         * */
        boolean isRedWin = board.pieces.get("bb0") == null;
        boolean isBlackWin = board.pieces.get("rb0") == null;
        if (isRedWin) return 'r';
        else if (isBlackWin) return 'b';
        else return 'x';
    }


    /**
     * 不能循环将军，而且不吃子.
     * @param thinkingBoard
     * @param orgPiece
     * @return
     */
	public static boolean isIllegal(Board thinkingBoard, Piece orgPiece) {
        return isJiangjunIllegal(thinkingBoard, orgPiece) || isEatIllegal(thinkingBoard, orgPiece);
	}

	private static boolean isEatIllegal(Board thinkingBoard, Piece orgPiece){
	    List<Piece.PieceTrace> traces = orgPiece.getTraces();


	    if(traces.size() <= 3){
	        return false;
        }


        // 都不吃子。
        int start = traces.size();
	    while(start >= 1){
	        if(traces.get(start-1).isEat){
	            break;
            }

            if(start < traces.size()){
                if(traces.get(start).step == traces.get(start - 1).step + 1){

                }else{
                    break;
                }
            }

            start--;
        }

        // 记录循环次数,
        int count = 0;

	    int lastIndex = traces.size() - 1;
        int secondIndex = traces.size() - 2;


        for(int j = 2; j <= (lastIndex - start + 1) / 2; j++){
	        for(int i =  secondIndex - j; i >= start; i -= j){
	            if(traces.get(i+1).position[0] == traces.get(lastIndex).position[0] &&  traces.get(i+1).position[1] == traces.get(lastIndex).position[1]){

                }else{
	                break;
                }

                if(traces.get(i).position[0] == traces.get(secondIndex).position[0] &&  traces.get(i).position[1] == traces.get(secondIndex).position[1]){

                }else{
                    break;
                }
                count += 1;
            }

            if(count >= 2){
                return true;
            }
        }
        return false;
    }

    /**
     * 长将军.
     * @param thinkingBoard
     * @param orgPiece
     * @return
     */
	private static boolean isJiangjunIllegal(Board thinkingBoard, Piece orgPiece){
        List<Piece.PieceTrace> traces = orgPiece.getTraces();

        Piece.PieceTrace lastTrace = traces.get(traces.size() - 1);

        if(traces.size() < 3){
            return false;
        }

        int posDup = 0;
        for(int i = 0; i < 3; i++){
            int index = traces.size() - 1 - i;

            Piece.PieceTrace curTrace = traces.get(index);

            if(curTrace.step != lastTrace.step - i){
                return false;
            }

            if(curTrace.isEat){
                return false;
            }

            if(!curTrace.isKillBoss){
                return false;
            }

            int[] curPos = curTrace.position;

            if(curPos[0] == lastTrace.position[0] && curPos[1] == lastTrace.position[1]){
                posDup += 1;
            }
        }

        if(posDup >= 2){
            return true;
        }
        return false;
    }


    /**
     *  测试游戏规则.
     */
    public static void main(String[] args){
        Piece orgPiece = new Piece("bm1", new int[]{2,4});

        orgPiece.update(new int[]{1,2},false,false,0);
        orgPiece.update(new int[]{2,4}, false, false, 1);
        orgPiece.update(new int[]{1,2}, false, false, 2);
        orgPiece.update(new int[]{2,4}, false, false, 3);
        orgPiece.update(new int[]{1,2}, false, false, 4);
        orgPiece.update(new int[]{2,4}, false, false, 5);

        System.out.println(Rules.isEatIllegal(null, orgPiece));

    }


    /**
     *   如果不是在玩，依然返回true.
     *   如果在玩， 颜色相同，返回false.
     */
    private  static boolean judgePlay(char ch1, char ch2, boolean ignore){
        if(ignore){
            return true;
        }
        return ch1 != ch2;
    }
}
