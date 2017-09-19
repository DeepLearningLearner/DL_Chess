package bridge.loader;

import chess.Board;
import chess.Piece;
import chess.Rules;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by didi on 17/9/6.
 */
public class BridgeParser {

    private static Map<Character,Character> keyPieceMap  = new HashMap<Character, Character>();


    static{
        keyPieceMap.put('炮','p');
        keyPieceMap.put('车','j');
        keyPieceMap.put('马','m');
        keyPieceMap.put('卒','z');
        keyPieceMap.put('兵','z');
        keyPieceMap.put('将','b');
        keyPieceMap.put('帅','b');
        keyPieceMap.put('象','x');
        keyPieceMap.put('士','s');
        keyPieceMap.put('相', 'x');
        keyPieceMap.put('仕','s');

    }

    /**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
    public static String toDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);

        return returnString;
    }

    public static String normalDigit(String str){

        // 对数据做全角转半角操作.
        str = toDBC(str);

        return str.replaceAll("一", "1")
                .replaceAll("二", "2")
                .replaceAll("三", "3")
                .replaceAll("四", "4")
                .replaceAll("五", "5")
                .replaceAll("六", "6")
                .replaceAll("七", "7")
                .replaceAll("八", "8")
                .replaceAll("九", "9");
    }

    public static Piece[] getPieces(Board board, String color, char name){
        Map<String, Piece> pieces = board.pieces;

        List<Piece> result = new ArrayList<Piece>();
        if(name == 'z'){
            for(int i = 0; i <= 4; i++){
                String key = color + name + i;
                if(pieces.containsKey(key)){
                    result.add(pieces.get(key));
                }
            }
        }else{
            for(int i = 0; i <= 1; i++){
                String key = color + name + i;
                if(pieces.containsKey(key)){
                    result.add(pieces.get(key));
                }
            }
        }
        return result.toArray(new Piece[0]);
    }


    private static int getChessCol(char ch, String color){
        if(color.equals("b")){
            return 9 - ( ch - '0');
        }
        return ch - '0' - 1;
    }


    /**
     * 给一个点获取到达的点 和 后两个的动作, 计算能够到达的点.
     */
    public static int[] getToFromAction(Board board, Piece targetPiece, char ch3, char ch4){
        int[] to = new int[2];

        int toCol = getChessCol(ch4, targetPiece.color + "");
        // 对piece进行移动.
        if(targetPiece.character == 'm'){
            to[1] = toCol;
            if(ch3 == '进' && Math.abs(toCol - targetPiece.position[1]) == 1){
                to[0] = targetPiece.position[0] + 2;
            }else if(ch3 == '进' && Math.abs(toCol - targetPiece.position[1]) == 2){
                to[0] = targetPiece.position[0] + 1;
            }else if(ch3 == '退' && Math.abs(toCol - targetPiece.position[1]) == 1){
                to[0] = targetPiece.position[0] - 2;
            }else if(ch3 == '退' && Math.abs(toCol - targetPiece.position[1]) == 2) {
                to[0] = targetPiece.position[0] - 1;
            }
        }else if(targetPiece.character == 's'){
            List<int[]> allMoves = Rules.getNextMove(targetPiece.key, targetPiece.position, board);

            for(int[] move : allMoves){
                if(move[1] == toCol && ch3 == '进' && move[0] == targetPiece.position[0] + 1){
                    to = move;
                    break;
                }else if(move[1] == toCol && ch3 == '退' && move[0] == targetPiece.position[0] - 1){
                    to = move;
                    break;
                }
            }
        }else if(targetPiece.character == 'x'){

            to[1] = toCol;

            List<int[]> allMoves = Rules.getNextMove(targetPiece.key, targetPiece.position, board);

            if(ch3 == '进'){
                to[0] = -1;
                for(int[] tMove : allMoves) {
                    if (tMove[1] == toCol) {
                        if (to[0] < tMove[0]) {
                            to[0] = tMove[0];
                        }
                    }
                }
            }else{
                to[0] = 10;
                for(int[] tMove : allMoves){
                    if(tMove[1] == toCol){
                        if(to[0] > tMove[0]){
                            to[0] = tMove[0];
                        }
                    }
                }
            }
        }else{
            if(ch3 == '平'){
                to[0] = targetPiece.position[0];
                to[1] = getChessCol(ch4, targetPiece.color + "");
            }else if(ch3 == '进'){
                to[0] = targetPiece.position[0] + (ch4 - '0');
                to[1] = targetPiece.position[1];
            }else{
                to[0] = targetPiece.position[0] - (ch4 - '0');
                to[1] = targetPiece.position[1];
            }
        }
        return to;
    }

    /**
     * 为实现逻辑简单化，将红 和 黑分来计算.
     * @param board
     * @param color
     * @param action
     */
    public static void playChess(Board board, String color, String action){

        action = normalDigit(action);

        int[] to = new int[2];
        Piece targetPiece = null;

        if(color.equals("r")){
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < action.length(); i++){
                if(action.charAt(i) == '进'){
                    sb.append('退');
                }else if(action.charAt(i) == '退'){
                    sb.append('进');
                }else if(action.charAt(i) == '前'){
                    sb.append('后');
                }else if(action.charAt(i) == '后'){
                    sb.append('前');
                }else{
                    sb.append(action.charAt(i));
                }
            }
            action = sb.toString();
        }
        char ch1 = action.charAt(0);
        char ch2 = action.charAt(1);
        char ch3 = action.charAt(2);
        char ch4 = action.charAt(3);


        if(ch1 == '前' ||  ch1 == '2' || ch1 == '3' || ch1 == '4' || ch1 == '5' || ch1 == '后' || ch1 == '中'){
            // 第二个棋子是子的名称 或者是数字，则为卒子的纵线位置.

            char character;
            int xLoc =  -1;
            List<Piece> tmpPieces = new ArrayList<Piece>();
            if(Character.isDigit(ch2)){
                character = 'z';
                xLoc = getChessCol(ch2, color);

                Piece[] allPieces = getPieces(board,color, character);

                for(Piece p : allPieces){
                    if(p.position[1] == xLoc){
                        tmpPieces.add(p);
                    }
                }
            }else{
                character = keyPieceMap.get(ch2);

                Piece[] allPieces = getPieces(board, color , character);

                // 选取出 有多个在一列的pieces.
                int[] cols = new int[Board.BOARD_WIDTH];
                for(Piece p : allPieces){
                    cols[p.position[1]] = cols[p.position[1]] + 1;
                }

                for(int i = 0; i < Board.BOARD_WIDTH; i++) {
                    if (cols[i] > 1) {
                        for (Piece p : allPieces) {
                            if (p.position[1] == i) {
                                tmpPieces.add(p);
                            }
                        }
                        break;
                    }
                }
            }

            // 对tmpPieces按照 行 做一个逆序.
            Collections.sort(tmpPieces, new Comparator<Piece>() {
                public int compare(Piece o1, Piece o2) {
                    return o2.position[0] - o1.position[0];
                }
            });

            if(ch1 == '前' ){
                targetPiece = tmpPieces.get(0);
            }else if(ch1 == '中'){
                targetPiece = tmpPieces.get(1);
            }else if(ch1 == '后' ){
                targetPiece = tmpPieces.get(tmpPieces.size() - 1);
            }else if(color.equals("b")){
                targetPiece = tmpPieces.get(ch1 - '0' - 1);
            }else{
                targetPiece = tmpPieces.get(tmpPieces.size() - (ch1 - '0'));
            }
            to = getToFromAction(board, targetPiece, ch3, ch4);
        }else{
            char character = keyPieceMap.get(ch1);
            int xLoc = getChessCol(ch2, color);
            Piece[] allPieces = getPieces(board, color, character);

            List<Piece> tmpPieces = new ArrayList<Piece>();
            for(Piece p : allPieces){
                if(p.position[1] == xLoc){
                    tmpPieces.add(p);
                }
            }
            Collections.sort(tmpPieces, new Comparator<Piece>() {
                public int compare(Piece o1, Piece o2) {
                    return o2.position[0] - o1.position[0];
                }
            });

            // 特别的例子.
            if(character == 'x'){
                if(ch3 == '进'){
                    targetPiece = tmpPieces.get(tmpPieces.size() - 1);
                }else{
                    targetPiece = tmpPieces.get(0);
                }
                to = getToFromAction(board, targetPiece, ch3, ch4);
            }else{
                // 看哪一个能过去，使用哪一个.
                boolean isFound = false;
                for(Piece testPiece : tmpPieces){
                    int[] tmpTo = getToFromAction(board, testPiece, ch3, ch4);

                    List<int[]> moves = Rules.getNextMove(testPiece.key, testPiece.position, board);

                    for(int[] to1 : moves){
                        if(to1[0] == tmpTo[0] && to1[1] == tmpTo[1]){
                            to = to1;
                            isFound = true;
                            targetPiece = testPiece;
                            break;
                        }
                    }
                    if(isFound){
                        break;
                    }
                }

            }
        }

        board.updatePiece(targetPiece.key, to);
        board.addTrace(targetPiece.key);

    }


    public static BridgeChess parseFile(String bridgeFile)throws IOException{
        BridgeChess bridgeChess = new BridgeChess();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(bridgeFile),"utf-8"));

        String s = null;
        boolean isStartChess = false;


        boolean isFirst = true;
        while((s = reader.readLine()) != null){
            if(s.startsWith("标题")){
                bridgeChess.setTitle(s.replace("标题：","").trim());
            }else if(s.startsWith("分类")){
                bridgeChess.setCategory(s.replace("分类：","").trim());
            }else if(s.startsWith("来源：")){
                bridgeChess.setOrg(s.replace("来源：","").trim());
            }else if(s.startsWith("赛事类型：")){
                bridgeChess.setMatchKind(s.replace("赛事类型：","").trim());
            }else if(s.startsWith("赛事：")){
                bridgeChess.setMatchName(s.replace("赛事：","").trim());
            }else if(s.startsWith("轮次：")){
                bridgeChess.setRank(s.replace("轮次：","").trim());
            }else if(s.startsWith("组别：")){
                bridgeChess.setGroup(s.replace("组别：","").trim());
            }else if(s.startsWith("台次：")){
                bridgeChess.setTimes(s.replace("台次：","").trim());
            }else if(s.startsWith("日期：")){
                bridgeChess.setDate(s.replace("日期：","").trim());
            }else if(s.startsWith("地点：")){
                bridgeChess.setDate(s.replace("地点：","").trim());
            }else if(s.startsWith("时间规则：")){
                bridgeChess.setTimeRules(s.replace("时间规则：","").trim());
            }else if(s.startsWith("裁判员：")){
                bridgeChess.setJudge(s.replace("裁判员：","").trim());
            }else if(s.startsWith("记录员：")){
                bridgeChess.setRecorder(s.replace("记录员：","").trim());
            }else if(s.startsWith("红方：")){
                bridgeChess.setRedPlayer(s.replace("红方：","").trim());
            }else if(s.startsWith("红等级分：")){
                bridgeChess.setRedRank(s.replace("红等级分：","").trim());
            }else if(s.startsWith("红方用时：")){
                bridgeChess.setRedTime(s.replace("红方用时：","").trim());
            }else if(s.startsWith("红队：")){
                bridgeChess.setRedGroup(s.replace("红队：","").trim());
            }else if(s.startsWith("黑方：")){
                bridgeChess.setBlackPlayer(s.replace("黑方：","").trim());
            }else if(s.startsWith("黑等级分：")){
                bridgeChess.setBlackRank(s.replace("黑等级分：","").trim());
            }else if(s.startsWith("黑方用时：")){
                bridgeChess.setBlackTime(s.replace("黑方用时：","").trim());
            }else if(s.startsWith("黑队：")){
                bridgeChess.setBlackGroup(s.replace("黑队：","").trim());
            }else if(s.startsWith("解说：")){
                bridgeChess.setComment(s.replace("解说：","").trim());
            }else if(s.startsWith("录入：")){
                bridgeChess.setRecord(s.replace("录入：","").trim());
            }else if(s.startsWith("创建日期：")){
                bridgeChess.setCreateTime(s.replace("创建日期：","").trim());
            }else if(s.startsWith("修改日期：")){
                bridgeChess.setModifyTime(s.replace("修改日期：","").trim());
            }else if(s.startsWith("ECCO：")){
                bridgeChess.setEcco(s.replace("ECCO：","").trim());
            }else if(s.startsWith("棋谱类型：")){
                bridgeChess.setChessType(s.replace("棋谱类型：","").trim());
            }else if(s.startsWith("对局种类：")){
                bridgeChess.setPlayKind(s.replace("对局种类：","").trim());
            }else if(s.startsWith("结果：")){
                if(s.contains("红胜")){
                    bridgeChess.setResult("r");
                }else if(s.contains("黑胜")){
                    bridgeChess.setResult("b");
                }else if(s.contains("和")){
                    bridgeChess.setResult("x");
                }else{
                    bridgeChess.setResult("n");
                }
            }else if(s.startsWith("结束方式：")){
                bridgeChess.setEndType(s.replace("结束方式：",""));
            }else if(s.startsWith("FEN ：")){
                bridgeChess.setFen(s.replace("FEN ：",""));
            }else if(s.equals("")){
                isStartChess = true;
                continue;
            }

            // 使用正则表达是进行匹配.
            Pattern pattern = Pattern.compile(" *[0-9]+\\...*");

            if(isStartChess && pattern.matcher(s).matches()){
                String[] steps = s.trim().split("  *");

                if(steps.length >= 2){
                    if(!steps[1].contains("…………")){
                        if(isFirst){
                            isFirst = false;
                            bridgeChess.setColor('b');
                        }
                        bridgeChess.addRedSteps(steps[1]);
                    }
                }



                if(steps.length >= 3){
                    if(isFirst){
                        isFirst = false;
                        bridgeChess.setColor('r');
                    }
                    if(!steps[2].contains("…………")){
                        bridgeChess.addBlackSteps(steps[2]);
                    }
                }

                if(steps.length >= 5){
                    if(!steps[4].contains("…………")){
                        if(isFirst){
                            isFirst = false;
                            bridgeChess.setColor('b');
                        }
                        bridgeChess.addRedSteps(steps[4]);
                    }
                }

                if(steps.length >= 6){
                    if(isFirst){
                        isFirst = false;
                        bridgeChess.setColor('r');
                    }
                    if(!steps[5].contains("…………")){
                        bridgeChess.addBlackSteps(steps[5]);
                    }
                }

            }
        }

        if(!isStartChess){
            return null;
        }

        reader.close();

        return bridgeChess;
    }



    public static void main(String[] args){
        Pattern pattern = Pattern.compile(" *[0-9]+\\...*");
        System.out.println(pattern.matcher(" 27 车七平五").matches());
    }
}

