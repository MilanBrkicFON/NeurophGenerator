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
public class TrainingResultWithSample extends TrainingResult{
    private int testPercentage;
    private int trainingPercentage;

    public TrainingResultWithSample(double learningRate, double momentum, int hiddenNeurons,
            double error, int iterations,int testPercentage,int trainingPercentage) {
        super(learningRate, momentum, hiddenNeurons, error, iterations);
        this.testPercentage = testPercentage;
        this.trainingPercentage = trainingPercentage;
    }

    @Override
    public void createHeaderForCsv(StringBuilder sb) {
        sb.append("learning rate, hidden neurons, momentum, error, iterations, test precentage, training precentage\n");
    }

    @Override
    public void toCsvFile(StringBuilder sb) {
        super.toCsvFile(sb); 
        sb.append(',');
        sb.append(this.testPercentage);
        sb.append(',');
        sb.append(this.trainingPercentage);
    }
    
    
    
}
