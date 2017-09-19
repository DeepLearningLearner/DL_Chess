package bridge.loader;

import chess.Board;
import chess.Piece;
import chess.Rules;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 特征生成工具，
 *  考虑的特征有, 考虑当前棋子是由谁下一步下棋. 特征数目是原来的2倍.
 *         1.   当前的棋子.
 *         2.   当前的棋子 以及  对应的位置.
 *         3.   当前的棋子 可以到达的点
 *         4.   当前的棋子 可以 可以保护 或者威胁的棋子.
 *         5.   当前的棋子 + 位置 + 被保护或者威胁的棋子
 * Created by didi on 17/9/9.
 */
public class FeatureGenerator2 {

    public static String getFeatureIndexByPiece(int featureIndex, double[] weight){
        String resultInfo = "";

        if(maxFeature1() > featureIndex){
            return Piece.SoredPieces[featureIndex] + "\t" + weight[featureIndex];
        }

        if(maxFeature1() + maxFeature2()  > featureIndex){
            int index2 = featureIndex - maxFeature1();



            String character = Piece.SoredPieces[index2 / (Board.BOARD_HEIGHT * Board.BOARD_WIDTH)];

            int posTotal = index2 % (Board.BOARD_HEIGHT * Board.BOARD_WIDTH);

            int height = posTotal / Board.BOARD_WIDTH;
            int width = posTotal % Board.BOARD_WIDTH;



            return character + "\t" + "loc:" + height + "," + width + "\t" + weight[featureIndex];
        }

        if(maxFeature1() + maxFeature2() + maxFeature3() > featureIndex){
            int index3 = featureIndex - maxFeature1() - maxFeature2();

            String character = Piece.SoredPieces[index3 / (Board.BOARD_HEIGHT * Board.BOARD_WIDTH)];

            int posTotal = index3 % (Board.BOARD_HEIGHT * Board.BOARD_WIDTH);

            int height = posTotal / Board.BOARD_WIDTH;
            int width = posTotal % Board.BOARD_WIDTH;

            return character + "\t" + "arrive loc:" + height + "," + width + "\t" + weight[featureIndex];
        }

        if(maxFeature1() + maxFeature2() + maxFeature3() + maxFeature4() > featureIndex){
            int index4 = featureIndex - maxFeature1() - maxFeature2() - maxFeature3();

            int first = index4 / Piece.SoredPieces.length;
            int second = index4 % Piece.SoredPieces.length;

            String character1 = Piece.SoredPieces[first];
            String character2 = Piece.SoredPieces[second];



            return character1 + "\t" + "eat:" + "\t" + character2 + "\t" + weight[featureIndex];
        }
        return null;
    }


    public static int maxFeature1(){
        return  Piece.SoredPieces.length;
    }

    public static int maxFeature2(){
        return Piece.SoredPieces.length * Board.BOARD_WIDTH * Board.BOARD_HEIGHT;
    }

    public static int maxFeature3(){
        return Piece.SoredPieces.length * Board.BOARD_WIDTH * Board.BOARD_HEIGHT;
    }

    public static int maxFeature4(){
        return Piece.SoredPieces.length * Piece.SoredPieces.length;
    }

    public static int maxFeature5(){
        return Piece.SoredPieces.length * Board.BOARD_WIDTH * Board.BOARD_HEIGHT * Piece.SoredPieces.length;
    }


    /**
     * 获取特征1 的特征以及对应的值.
     */
    public static double[] getFeature1(Board board){
        double[] features = new double[maxFeature1()];

        for(Piece p : board.pieces.values()){
            features[p.id] = 1;
        }

        return features;
    }



    /**
     * 获取索引.
     */
    public static int[] getFeature1Index(Board board){
        int[] indexArr = new int[board.pieces.values().size()];

        int i = 0;
        for(Piece p : board.pieces.values()){
            indexArr[i] = p.id;
            i += 1;
        }

        return indexArr;
    }


    /**
     * 获取特征2 的特征以及对应的值.
     */
    public static double[] getFeature2(Board board){
        double[] features = new double[maxFeature2()];

        for(Piece p : board.pieces.values()){
            int[] position = p.position;


            int index = p.id * Board.BOARD_WIDTH * Board.BOARD_HEIGHT + position[0] * Board.BOARD_WIDTH + position[1];


            features[ index ] = 1.0;
        }
        return features;
    }

    public static int[] getFeature2Index(Board board){
        int[] indexArr = new int[board.pieces.values().size()];

        int i = 0;
        for(Piece p : board.pieces.values()){
            int[] position = p.position;
            int index  = p.id * Board.BOARD_WIDTH * Board.BOARD_HEIGHT + position[0] * Board.BOARD_WIDTH + position[1];
            indexArr[i] = index;
            i += 1;
        }

        return indexArr;
    }

    /**
     * 获取特征3 对应的值.
     */
    public static double[] getFeature3(Board board){
        double[] features = new double[maxFeature3()];

        for(Piece p : board.pieces.values()){
            // 查看可以到达的点.

            List<int[]> moves = Rules.getNextMoveIgnoreColor(p.key,p.position,board);

            for(int[] move : moves){
                int index = p.id * Board.BOARD_WIDTH * Board.BOARD_HEIGHT + move[0] * Board.BOARD_WIDTH + move[1];
                features[index] = 1.0;
            }
        }
        return features;
    }

    public static int[] getFeature3Index(Board board){
        int[] indexArr = new int[maxFeature3()];

        int i = 0;
        for(Piece p : board.pieces.values()){
            // 查看可以到达的点.

            List<int[]> moves = Rules.getNextMoveIgnoreColor(p.key,p.position,board);

            for(int[] move : moves){
                int index = p.id * Board.BOARD_WIDTH * Board.BOARD_HEIGHT + move[0] * Board.BOARD_WIDTH + move[1];
                indexArr[i] = index;
                i += 1;
            }
        }


        int[] result = new int[i];
        System.arraycopy(indexArr,0,result,0,i);
        return result;
    }

    /**
     * 获取特征4 对应的值.
     */
    public static double[] getFeature4(Board board){
        double[] features = new double[maxFeature4()];

        for(Piece p : board.pieces.values()){

            // 考虑可以威胁到棋子.
            List<int[]> moves = Rules.getNextMoveIgnoreColor(p.key, p.position, board);

            for(int[] move : moves){
                Piece eaten = board.getPiece(move);

                if(eaten != null){
                    features[p.id * Piece.SoredPieces.length + eaten.id] = 1.0;
                }
            }
        }



        return features;
    }

    public static int[] getFeature4Index(Board board){
        int[] indexArr = new int[maxFeature4()];

        int i = 0;
        for(Piece p : board.pieces.values()){

            // 考虑可以威胁到棋子.
            List<int[]> moves = Rules.getNextMoveIgnoreColor(p.key, p.position, board);

            for(int[] move : moves){
                Piece eaten = board.getPiece(move);

                if(eaten != null){
                    int index = p.id * Piece.SoredPieces.length + eaten.id;
                    indexArr[i] = index;
                    i += 1;
                }
            }
        }


        int[] result = new int[i];
        System.arraycopy(indexArr,0,result,0,i);
        return result;
    }


    /**
     * 获取特征5 对应的值.
     */
    public static int[] getFeature5Index(Board board){
        int[] indexArr = new int[board.pieces.size() * board.pieces.size() * board.BOARD_HEIGHT * board.BOARD_WIDTH];

        int i = 0;
        for(Piece p : board.pieces.values()){

            // 考虑可以威胁到棋子.
            List<int[]> moves = Rules.getNextMoveIgnoreColor(p.key, p.position, board);

            for(int[] move : moves){
                Piece eaten = board.getPiece(move);

                if(eaten != null){
                    int index = p.id *  Board.BOARD_HEIGHT * Board.BOARD_WIDTH * Piece.SoredPieces.length +  (Board.BOARD_WIDTH * p.position[0] + p.position[1]) * Piece.SoredPieces.length  + eaten.id;
                    indexArr[i] = index;
                    i += 1;
                }
            }
        }
        int[] result = new int[i];
        System.arraycopy(indexArr,0,result,0,i);
        return result;
    }


    public static double[] getFeature5(Board board){
        double[] features = new double[maxFeature5()];

        for(Piece p : board.pieces.values()){

            // 考虑可以威胁到棋子.
            List<int[]> moves = Rules.getNextMoveIgnoreColor(p.key, p.position, board);

            for(int[] move : moves){
                Piece eaten = board.getPiece(move);

                if(eaten != null){
                    features[p.id *  Board.BOARD_HEIGHT * Board.BOARD_WIDTH * Piece.SoredPieces.length +  (Board.BOARD_WIDTH * p.position[0] + p.position[1]) * Piece.SoredPieces.length  + eaten.id] = 1.0;
                }
            }
        }
        return features;
    }

    /**
     * 在考虑当前局面的时候，考虑当前棋局是由谁来走子.
     * @param board
     * @param turn
     * @return
     */
    public static double[] getAllFeature(Board board, int turn){
        double[] resultList = new double[(maxFeature1() + maxFeature2() + maxFeature3() + maxFeature4() + maxFeature5()) * 2];

        int offset = turn == 0 ? 0 : maxFeature1() + maxFeature2() + maxFeature3() + maxFeature4() + maxFeature5();

        // 特征1.
        double[] feature1 = getFeature1(board);
        for(int i = 0; i < feature1.length; i++){
            resultList[offset + i] = feature1[i];
        }

        // 特征2.
        double[] feature2 = getFeature2(board);
        for(int i = 0; i < feature2.length; i++){
            resultList[offset + i + maxFeature1()] = feature2[i];
        }

        // 特征3.
        double[] feature3 = getFeature3(board);
        for(int i = 0; i < feature3.length; i++){
            resultList[offset + i + maxFeature1() + maxFeature2()] = feature3[i];
        }

        // 特征4.
        double[] feature4 = getFeature4(board);
        for(int i = 0; i < feature4.length; i++){
            resultList[offset + i + maxFeature1() + maxFeature2() + maxFeature3()] = feature4[i];
        }

        return resultList;
    }



    public static int[] getAllFeatureIndex(Board board, int turn){
        LinkedList<Integer> resultList = new LinkedList<Integer>();


        int offset = turn == 0 ? 0 : maxFeature1() + maxFeature2() + maxFeature3() + maxFeature4() + maxFeature5();

        // 特征一。
        int[] feature1 = getFeature1Index(board);
        for(int i = 0; i < feature1.length; i++){
            resultList.add(feature1[i] + offset);
        }

        // 特征2.
        int[] feature2 = getFeature2Index(board);
        for(int i = 0; i < feature2.length; i++){
            resultList.add(feature2[i] + maxFeature1() + offset);
        }

        // 特征3.
        int[] feature3 = getFeature3Index(board);
        for(int i = 0; i < feature3.length; i++){
            resultList.add(feature3[i] + maxFeature1() + maxFeature2() + offset);
        }

        // 特征4.
        int[] feature4 = getFeature4Index(board);
        for(int i = 0; i < feature4.length; i++){
            resultList.add(feature4[i] + maxFeature1() + maxFeature2() + maxFeature3() + offset);
        }

        // 特征5.
//        int[] feature5 = getFeature5Index(board);
//        for(int i = 0; i < feature5.length; i++){
//            double[] feature5 = getFeature5(board);
//        for(int i = 0; i < feature5.length; i++){
//            resultList[i + maxFeature1() + maxFeature2() + maxFeature3() + maxFeature4()] = feature5[i];
//        }        resultList.add(feature5[i] + maxFeature1() + maxFeature2() + maxFeature3() + maxFeature4());
//        }

        int[] finalIndexArr = new int[resultList.size()];

        for(int i = 0; i < finalIndexArr.length; i++){
            finalIndexArr[i] = resultList.removeFirst();
        }
        return finalIndexArr;
    }

    public static void main(String[] args)throws Exception{
        String inputPath = args[0];
        String outputPath = args[1];

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath),"utf-8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath),"utf-8"));

        String line = null;
        while((line = reader.readLine()) != null){
            String[] words = line.split("\t");

            String chessInfo = words[0];
            String color = words[1];
            String winner = words[2];


            int label = 0;
            if(winner.equals("r")){
                label = 1;
            }

            int turn = 0;

            if(color.equals("b")){
                turn = 0;
            }else{
                turn = 1;
            }

            Board board = Board.loadBoard(chessInfo);

            double[] features = null;

            try{
                features = getAllFeature(board,turn);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

            StringBuffer sb = new StringBuffer();

            sb.append(label);

            for(int i = 0; i < features.length; i++){
                if(Math.abs(features[i]) > 1e-7){
                    sb.append(" " +  i + ":" + features[i]);
                }
            }

            writer.write(sb + "\n");

        }

        reader.close();
        writer.close();
    }
}
