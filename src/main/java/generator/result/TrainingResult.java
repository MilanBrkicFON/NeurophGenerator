/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator.result;

/**
 *
 * @author Milan
 */
public class TrainingResult {


    private double learningRate;
    private double momentum;
    private int hiddenNeurons;
    private double error;
    private int iterations;
    private double maxError;

   
    public TrainingResult(double learningRate, double momentum, int hiddenNeurons, double error, int iterations) {
        this(learningRate, momentum, hiddenNeurons);
        this.error = error;
        this.iterations = iterations;
    }

    public TrainingResult(double learningRate, double momentum, int hiddenNeurons) {
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.hiddenNeurons = hiddenNeurons;
    }

    
    public double getError() {
        return error;
    }

    public int getIterations() {
        return iterations;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public double getMomentum() {
        return momentum;
    }

    public int getHiddenNeurons() {
        return hiddenNeurons;
    }

    public double getMaxError() {
        return maxError;
    }
   
    
    
    public void toCsvFile(StringBuilder sb){
        sb.append(this.learningRate);
        sb.append(',');
        sb.append(this.hiddenNeurons);
        sb.append(',');
        sb.append(this.momentum);
        sb.append(',');
        sb.append(this.error);
        sb.append(',');
        sb.append(this.iterations);
        
        
    }

    public void createHeaderForCsv(StringBuilder sb) {
        StringBuilder sb1 = sb;
        
        sb1.append("learning rate, hidden neurons, momentum, error, iterations\n");
    }
    
    
}
