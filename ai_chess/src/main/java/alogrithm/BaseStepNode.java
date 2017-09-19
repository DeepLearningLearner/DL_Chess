package alogrithm;

/**
 * Created by Tong on 12.18.
 * Store piece move in alpha beta search.
 */
public class BaseStepNode {
    public String piece;
    public int[] from;
    public int[] to;
    public int value;

    public BaseStepNode(String piece, int[] from, int[] to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
    }
    
    public String toString(){
    	return piece + "," + from[0] + from[1] + "," + to[0] + to[1] + "," + value;
    }
}
