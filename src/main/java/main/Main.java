/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import generator.AutoTrainer;
import generator.result.TrainingResult;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.neuroph.core.data.DataSet;

/**
 * kako iskoristiti vrednosti iz matrice(TP, TN, FP, FN) 
 * kako tokom testiranja videti broj pogodjenih, broj promasenih
 * Memento?
 * 
 */

/**
 *
 * @author Milan
 */
public class Main {
    private static final String FILEPATH = "Iris/Iris-dataset-normalised.txt";
    
    
    public static void main(String[] args) {
        AutoTrainer trainer = new AutoTrainer();
        trainer.repeatNetwork(3);
        trainer.setHiddenNeurons(10, 20);
        trainer.setLearningRate(0.3, 0.5);
        
        //trainer.generateTrainingSettings();
                
        DataSet dataSet = DataSet.createFromFile(FILEPATH, 4, 3, "\t",true);
        List<TrainingResult> list = trainer.train(dataSet);
        
        try {
            trainer.saveToCSVFile(list);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Error writing in file...");
        }
        
        System.out.println("Main done!");
    }
}
