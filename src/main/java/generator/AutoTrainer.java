/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import generator.result.RepeatTrainingResult;
import generator.result.TrainingResult;
import generator.result.TrainingResultWithSample;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import statistic.Statistic;

/**
 *
 * @author Milan
 */
public class AutoTrainer implements Trainer {

    private List<TrainingSettings> trainingSettingsList = new ArrayList<>();

    private int maxHiddenNeurons = 10;
    private double maxLearningRate = 0.9;
    private double maxMomentum = 0.9;
    private boolean splitForTesting = false;
    private double learningRate = 0.1;
    private int hiddenNeurons = 1;
    private int repeat = 1;

    public AutoTrainer() {
        //generateTrainingSettings();
    }

    public void setTrainingAndTesting(boolean split) {
        this.splitForTesting = split;
    }

    /**
     * Default minimum is 1.
     * @param maxHiddenNeurons
     */
    public void setMaxHiddenNeurons(int maxHiddenNeurons) {
        this.hiddenNeurons = 1;
        this.maxHiddenNeurons = maxHiddenNeurons;
    }

    public void setHiddenNeurons(int minHiddenNeurons, int maxHiddenNeurons) {
        this.hiddenNeurons = minHiddenNeurons;
        this.maxHiddenNeurons = maxHiddenNeurons;
    }

    public void setMaxLearningRate(double maxLearningRate) {
        this.learningRate = 0.1;
        this.maxLearningRate = maxLearningRate;
    }

    public void setLearningRate(double minLearningRate, double maxLearningRate) {
        this.learningRate = minLearningRate;
        this.maxLearningRate = maxLearningRate;
    }

    public void setMaxMomentum(double maxMomentum) {
        this.maxMomentum = maxMomentum;
    }

    public void repeatNetwork(int times) {
        this.repeat = times;
    }

    private void generateTrainingSettings() {
        double j = learningRate;
        while (hiddenNeurons <= maxHiddenNeurons) {
            while (j <= maxLearningRate) {
                // for (double momentum = 0.1; momentum < maxMomentum; momentum += 0.1) { proveriti za sta je potreban momentum i kako se koristi!
                TrainingSettings ts = new TrainingSettings(j, 0.7, hiddenNeurons);
                this.trainingSettingsList.add(ts);
                j += 0.1;
                //}
            }
            j = learningRate;
            hiddenNeurons++;
        }
        System.out.println("Generated : " + this.trainingSettingsList.size() + " settings.");
    }

    @Override
    public List<TrainingResult> train(DataSet dataSet) {
        generateTrainingSettings();
        
        
        //dataSet.shuffle();
        
        if (splitForTesting) {
            return trainNetworkWithSample(dataSet);

        }

        if (this.repeat > 1) {
            return trainNetworkMultipleTimes(dataSet);
        }

        List<TrainingResult> trainingResultsList = new ArrayList<>();
        for (TrainingSettings ts : trainingSettingsList) {
            MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(dataSet.getInputSize(), ts.getHiddenNeurons(), dataSet.getOutputSize());

            BackPropagation bp = neuralNet.getLearningRule();

            bp.setLearningRate(ts.getLearningRate());
            bp.setMaxError(0.001);
            bp.setMaxIterations(20000);

            do {
                neuralNet.learn(dataSet);
                
                NumIterations = bp.getCurrentIteration();
                totalError = bp.getTotalNetworkError();
                TrainingResult result
                        = new TrainingResult(ts.getLearningRate(), ts.getMomentum(), ts.getHiddenNeurons(), totalError, NumIterations);
                trainingResultsList.add(result);
            } while (this.repeat-- > 1);
        }
        return trainingResultsList;
    }

    private int NumIterations;
    private double totalError;

    public void saveToCSVFile(List<TrainingResult> listOfResults) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        listOfResults.get(0).createHeaderForCsv(sb);

        PrintWriter pw = new PrintWriter(new File("result.csv"));

        for (TrainingResult tr : listOfResults) {
            tr.toCsvFile(sb);
            sb.append('\n');
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }

    private List<TrainingResult> trainNetworkWithSample(DataSet dataSet) {
        int[] samples = {50, 60, 70, 80, 90};

        List<TrainingResult> trainingResultsList = new ArrayList<>();
        for (int i = 0; i < samples.length; i++) {
            List<DataSet> trainingAndTestSet = dataSet.split(samples[i]);
            DataSet trainingSet = trainingAndTestSet.get(0);
            DataSet testingSet = trainingAndTestSet.get(1);

            for (TrainingSettings ts : trainingSettingsList) {
                MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(dataSet.getInputSize(), ts.getHiddenNeurons(), dataSet.getOutputSize());

                BackPropagation bp = neuralNet.getLearningRule();

                bp.setLearningRate(ts.getLearningRate());
                bp.setMaxError(0.001);
                bp.setMaxIterations(20000);

                neuralNet.learn(trainingSet);

                testNeuralNetwork(neuralNet, testingSet);

                NumIterations = bp.getCurrentIteration();
                totalError = bp.getTotalNetworkError();

                TrainingResultWithSample result
                        = new TrainingResultWithSample(ts.getLearningRate(), ts.getMomentum(),
                                ts.getHiddenNeurons(), totalError, NumIterations, samples[i], 100 - samples[i]);
                trainingResultsList.add(result);
            }
        }
        return trainingResultsList;
    }

    private void testNeuralNetwork(MultiLayerPerceptron neuralNet, DataSet testingSet) {
        for (DataSetRow testSetRow : testingSet.getRows()) {
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();

            System.out.print("Input: " + Arrays.toString(testSetRow.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));
        }
    }

    private List<TrainingResult> trainNetworkMultipleTimes(DataSet dataSet) {
        List<TrainingResult> trainingResultsList = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (TrainingSettings ts : trainingSettingsList) {
            MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(dataSet.getInputSize(), ts.getHiddenNeurons(), dataSet.getOutputSize());

            BackPropagation bp = neuralNet.getLearningRule();
            System.out.println("Training #"+ ++j);
            bp.setLearningRate(ts.getLearningRate());
            bp.setMaxError(0.01);
            bp.setMaxIterations(10000);

            List<TrainingResult> list = new ArrayList<>();
            i = this.repeat;
            do {
                dataSet.shuffle();
                neuralNet.learn(dataSet);

                NumIterations = bp.getCurrentIteration();
                totalError = bp.getTotalNetworkError();
                System.out.println("Number of iterations: "+ NumIterations + " | network error: "+totalError);
                TrainingResult result
                        = new TrainingResult(ts.getLearningRate(), ts.getMomentum(), ts.getHiddenNeurons(), totalError, NumIterations);
                list.add(result);
                System.out.println(i);
            } while (i-- > 1);
            
            RepeatTrainingResult rr = doStatistic(list);
            System.out.println("Iterations statistics: " + rr.getIt().toString() + "MSE statistics: "+ rr.getMSE().toString());
            trainingResultsList.add(rr);
        }
        return trainingResultsList;
    }

    private RepeatTrainingResult doStatistic(List<TrainingResult> list) {
        RepeatTrainingResult result;
        TrainingResult tr = list.get(0);
        
        Statistic iterationsStat = Statistic.calculateIterations(list);
        Statistic MSEStat = Statistic.calculateMSE(list);
        
        result = new RepeatTrainingResult(tr.getLearningRate(),tr.getMomentum(),tr.getHiddenNeurons(), MSEStat, iterationsStat);
        return result;
    }
}
