/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistic;

import generator.result.TrainingResult;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Milan
 */
public class Statistic {
    double min;
    double max;
    double mean;
    double std;

    public Statistic(double min, double max, double mean, double std) {
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.std = std;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMean() {
        return mean;
    }

    public double getStd() {
        return std;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setStd(double std) {
        this.std = std;
    }
    
    public Statistic(){
        
    }
    
    public static Statistic calculateIterations(List<TrainingResult> list) {
        int minIt = list.get(0).getIterations();
        int maxIt = list.get(0).getIterations();
        double meanIt;
        double stdIt;
        
        int sumIt = 0;
        double[] v = new double[list.size()];
        int i = 0;
        for(TrainingResult tr:list){
            if(tr.getIterations() < minIt){
                minIt = tr.getIterations();
            }
            if(tr.getIterations() > maxIt){
                maxIt = tr.getIterations();
            }
            sumIt += tr.getIterations();
            v[i++] = tr.getIterations();
        }
        
        meanIt = sumIt / list.size();
        
        stdIt = std(meanIt,v);
        
        return new Statistic(minIt, maxIt, meanIt, stdIt);
    }
    
    public static Statistic calculateMSE(List<TrainingResult> list) {
        double minIt = list.get(0).getError();
        double maxIt = list.get(0).getError();
        double meanIt;
        double stdIt;
        
        double pom = 0;
        double[] vrednosti = new double[list.size()];
        int i = 0 ;
        for(TrainingResult tr:list){
            if(tr.getError() < minIt){
                minIt = tr.getError();
            }
            if(tr.getError() > maxIt){
                maxIt = tr.getError();
            }
            pom += tr.getError();
            
            vrednosti[i++] = tr.getError();
        }
        
        meanIt = pom / list.size();
        
        stdIt = std(meanIt, vrednosti);
        
        return new Statistic(minIt, maxIt, meanIt, stdIt);
    }

    @Override
    public String toString() {
        return "Statistic{" + "min=" + min + ", max=" + max + ", mean=" + mean + ", std=" + std + '}';
    }
    
    

    private static double std(double meanIt, double[] list) {
        double std = 0 ;
        
        for(Double tr : list){
            std += Math.pow(tr - meanIt,2); 
        }
        return Math.sqrt(std /list.length);
    }
}
