package bridge.loader;

import chess.Board;
import control.GameController;

import java.io.File;
import java.io.IOException;

/**
 * Created by didi on 17/9/7.
 */
public class ChessGenerator {

    public static void playChessByNote(String noteBook, String outputFile) throws IOException {
        GameController controller = new GameController();

        Board board = controller.playChess();


        BridgeChess chess = BridgeParser.parseFile(noteBook);

        board.initTrace(outputFile, chess.getResult().charAt(0));


        while(true){
            // 根据棋谱计算谁先下.
            String oneAction = chess.play();

            if(oneAction == null){
                break;
            }

            BridgeParser.playChess(board, chess.getColor() + "", oneAction);

            String otherAction = chess.play();

            if(otherAction == null){
                break;
            }
            BridgeParser.playChess(board, chess.getColor() + "", otherAction);
        }

        board.notifySucess(chess.getResult().charAt(0));
    }


    public static void main(String[] args)  throws IOException{

        String inputPath = args[0];
        String outputPath = args[1];

        File file = new File(inputPath);

        File outputDir = new File(outputPath);
        if(!outputDir.exists()){
            outputDir.mkdir();
        }

        int index = 0;
        for(File child : file.listFiles()){
            try{
                playChessByNote(child.getCanonicalPath(), outputDir.getCanonicalPath() + "/" + index + ".txt");
                index += 1;
            }catch(Exception e){
                System.err.println(child.getName());
            }
        }



    }
}
