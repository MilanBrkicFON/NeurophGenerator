/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator.result;

import java.util.List;
import statistic.Statistic;

/**
 *
 * @author Milan
 */
public class RepeatTrainingResult extends TrainingResult{
    private double meanSquaredError;
    private Statistic MSE;
    private Statistic it;
    
    public RepeatTrainingResult(double learningRate, double momentum, int hiddenNeurons, Statistic meanSquaredError, Statistic it) {
        super(learningRate, momentum, hiddenNeurons);
        this.MSE = meanSquaredError;
        this.it = it;
    }

    @Override
    public void createHeaderForCsv(StringBuilder sb) {
        sb.append("learning rate, hidden neurons, momentum, iterations, , , ,MSE, , , ,\n");
        sb.append(", , ,min, max, mean, std, min, max, mean, std, \n");
    }

    @Override
    public void toCsvFile(StringBuilder sb) {
       sb.append(getLearningRate());
       sb.append(',');
       sb.append(getHiddenNeurons());
       sb.append(',');
       sb.append(getMomentum());
       sb.append(',');
       sb.append(this.it.getMin());
       sb.append(',');
       sb.append(this.it.getMax());
       sb.append(',');
       sb.append(this.it.getMean());
       sb.append(',');
       sb.append(this.it.getStd());
       sb.append(',');
       sb.append(this.MSE.getMin());
       sb.append(',');
       sb.append(this.MSE.getMax());
       sb.append(',');
       sb.append(this.MSE.getMean());
       sb.append(',');
       sb.append(this.MSE.getStd());       
    }

    public Statistic getMSE() {
        return MSE;
    }

    public Statistic getIt() {
        return it;
    }
   
    
    
}
