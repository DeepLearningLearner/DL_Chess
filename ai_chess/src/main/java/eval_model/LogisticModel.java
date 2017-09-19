package eval_model;

import bridge.loader.FeatureGenerator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by didi on 17/9/13.
 */
public class LogisticModel {

    private double[] weights;

    private double intercept;

    // 加载模型里边的参数值.
    public LogisticModel(String modelFile){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(modelFile), "utf-8"));

            // 首先读取第一行特征对应的参数.
            String[] alpha = reader.readLine().split(",");

            weights = new double[alpha.length];

            for(int i = 0; i < weights.length; i++){
                weights[i] = Double.parseDouble(alpha[i]);
            }
            this.intercept = Double.parseDouble(reader.readLine());

            reader.close();
        }catch (Exception e){

        }
    }

    public double predict(double[] features){
        // 根据公式进行计算.
        double sum = 0.0;
        for(int i = 0; i < weights.length; i++){
            sum += weights[i] * features[i];
        }

        sum += intercept;

        return 1.0/ (1 + Math.exp(-1 * sum));
    }

    public double predict(int[] featureIndex){
        // 根据公式进行计算.
        double sum = 0.0;
        for(int i = 0; i < featureIndex.length; i++){
            sum += weights[featureIndex[i]];
        }
        sum += intercept;

        return 1.0/ (1 + Math.exp(-1 * sum));
    }



    public static void main(String[] args){
        LogisticModel model = new LogisticModel("/Users/didi/Desktop/xiangqi/model.txt.2");

        for(int i = 0; i < model.weights.length; i++){
            System.out.println(FeatureGenerator.getFeatureIndexByPiece(i,model.weights));
        }
    }
}
