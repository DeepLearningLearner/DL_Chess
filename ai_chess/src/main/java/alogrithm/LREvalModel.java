package alogrithm;

import bridge.loader.FeatureGenerator;
import chess.Board;
import chess.Rules;
import eval_model.LogisticModel;

/**
 * Created by Tong on 12.08.
 * Eval Model.
 */
public class LREvalModel {

    private LogisticModel model;


    public LREvalModel(){
        model = new LogisticModel("/Users/didi/Desktop/xiangqi/model_kuaiqi.txt");
    }

    /**
     * @param player, eval the situation in player's perspective.
     */
    public int eval(Board board, char player, boolean isMax) {

        int result = 0;
        if(player == 'r'){
            result =  (int)(predictByLRIndex(board) * 100000000);
        }else{
            result = -1 * (int)(predictByLRIndex(board) * 100000000);
        }

        if(Rules.hasWin(board) == player){
            result =  result + 100000000;
        }else if(Rules.hasWin(board) != 'x'){
            result =  result - 100000000;
        }

        System.out.println(board.getCurBoard() + "\t" + result);

        return result;
    }


    public double predictByLR(Board board){
        double[] features = FeatureGenerator.getAllFeature(board);

        return model.predict(features) - 0.5;
    }

    public double predictByLRIndex(Board board){
        int[] featureIndex = FeatureGenerator.getAllFeatureIndex(board);

        return model.predict(featureIndex) - 0.5;
    }

    public static void main(String[] args){
        Board board = Board.loadBoard("bj000,bx002,bs003,bb004,bs105,bx106,bm107,bj108,bp021,bm022,bp127,bz030,bz132,bz234,bz336,bz438,rp142,rz060,rz162,rz264,rz366,rz468,rp071,rj090,rm091,rx092,rs093,rb094,rs195,rx196,rm197,rj198");

        LREvalModel model = new LREvalModel();

        double predict = model.predictByLRIndex(board);

        System.out.println(predict);
    }
}