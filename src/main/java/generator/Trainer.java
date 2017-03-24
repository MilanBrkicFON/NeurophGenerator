/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import generator.result.TrainingResult;
import java.util.List;
import org.neuroph.core.data.DataSet;

/**
 *
 * @author Milan
 */
public interface Trainer {
    public List<TrainingResult> train(DataSet dataSet);
}
