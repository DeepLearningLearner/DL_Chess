package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tong on 12.03.
 * Chess > Piece entity
 */



public class Piece implements Cloneable {

    public static final String[] SoredPieces = {
            "bj0",
            "bm0",
            "bx0",
            "bs0",
            "bb0",
            "bs1",
            "bx1",
            "bm1",
            "bj1",
            "bp0",
            "bp1",
            "bz0",
            "bz1",
            "bz2",
            "bz3",
            "bz4",

            "rj0",
            "rm0",
            "rx0",
            "rs0",
            "rb0",
            "rs1",
            "rx1",
            "rm1",
            "rj1",
            "rp0",
            "rp1",
            "rz0",
            "rz1",
            "rz2",
            "rz3",
            "rz4"
    };

	public static class PieceTrace{
		
		int step;
		boolean isEat;
		int[] position;
		boolean isKillBoss;
		
		private PieceTrace(int[] position, boolean isEat,  boolean isKillBoss, int step){
			this.position = position;
			this.isEat = isEat;
			this.isKillBoss = isKillBoss;
			this.step = step;
		}
		
	}

    /**
     * 用于特征建设的时候使用.
     */
	public int id;
	
    public String key;
    public char color;
    public char character;
    public char index;
    public int[] position = new int[2];
    
    public List<PieceTrace> pieceTraces;
    
    

    public Piece(String name, int[] position) {
        this.key = name;
        this.color = name.charAt(0);
        this.character = name.charAt(1);
        this.index = name.charAt(2);
        this.position = position;
        
        this.pieceTraces = new ArrayList<PieceTrace>();

        for(int i = 0; i < Piece.SoredPieces.length; i++){
            if(Piece.SoredPieces[i].equals(this.key)){
                this.id = i;
                break;
            }
        }
    }

    public Piece(String info){
    	this.key = info.substring(0,info.length()-2);
    	this.color = info.charAt(0);
    	this.character = info.charAt(1);
    	this.index = info.charAt(2);
    	
    	this.position = new int[2];
    	this.position[0] = Integer.parseInt(info.substring(3,4));
    	this.position[1] = Integer.parseInt(info.substring(4,5));
    	
    	this.pieceTraces = new ArrayList<PieceTrace>();
    }
    
    public String toString(){
    	return key + position[0] + position[1];
    }
    
    @Override
    public Piece clone(){
        Piece piece = new Piece(this.key, new int[]{this.position[0], this.position[1]});
        
        for(PieceTrace trace : this.pieceTraces){
        	piece.pieceTraces.add(trace);
        }
        return piece;
    }
    
    public void update(int[] position,boolean isEat,boolean isKillBoss,int step){
    	this.pieceTraces.add(new PieceTrace(position, isEat, isKillBoss, step));
    }
    
    
    public void back(){
    	this.pieceTraces.remove(pieceTraces.size() - 1);
    }

	public List<PieceTrace> getTraces() {
		return this.pieceTraces;
	}
}
