/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.Controller.weightController;

import nrz.fairHandler.model.WeightModel;
import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import nrz.java.view.component.FTable;

/**
 *
 * @author abderrahim
 */
public class WeightAdjustementListener implements AdjustmentListener {

    private final WeightModel weightModel;
    private final FTable table;

    public WeightAdjustementListener(WeightModel weightModel,FTable table) {
        this.weightModel = weightModel;
        this.table=table;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        Adjustable source = e.getAdjustable();
        if (source.getOrientation() == Adjustable.VERTICAL && e.getValue() != 0) {
            if (e.getValue() >= (source.getMaximum() - source.getVisibleAmount()) * (0.99)) {
                table.addRows(weightModel.getWeightStep());
            }
        }
    }

}
