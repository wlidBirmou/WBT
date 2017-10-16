/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.Controller.weightController;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import nrz.fairHandler.model.WeightModel;
import nrz.java.view.component.FTable;

/**
 *
 * @author rahimAdmin
 */
public class WeightSelectedListener implements ListSelectionListener {

    private FTable table;
    private WeightModel model;

    public WeightSelectedListener(FTable table, WeightModel model) {
        this.table = table;
        this.model = model;
    }
    
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
       if(table.getSelectedRowID()!=null){
           this.model.setSelectedWeightId((int)table.getSelectedRowID());
       }else {
           this.model.setSelectedWeightId(-1);
           
       }
    }

}
