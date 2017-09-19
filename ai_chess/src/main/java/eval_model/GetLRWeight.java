package eval_model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by didi on 17/9/14.
 */
public class GetLRWeight {
    private double[] weights;

    private double intercept;

    // 加载模型里边的参数值.
    public GetLRWeight(String modelFile){
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


}
