package generator;

/**
 *
 * @author Milan
 */
public class TrainingSettings {
    
    private double learningRate;
    private double momentum;
    private int hiddenNeurons;
    
    
    public TrainingSettings(double learningRate, double momentum, int hiddenNeurons) {
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.hiddenNeurons = hiddenNeurons;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getMomentum() {
        return momentum;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public int getHiddenNeurons() {
        return hiddenNeurons;
    }

    public void setHiddenNeurons(int hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
    }
    
    
}
