/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nrz.fairHandler.Controller.generalController;

import nrz.fairHandler.model.RealTimeModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author abderrahim
 */
public class updateShowedSectionController extends AbstractAction{
    private final RealTimeModel uIModel;
    private final int newShowedSection;

    public updateShowedSectionController(RealTimeModel uIModel, int newShowedSection) {
        this.uIModel = uIModel;
        this.newShowedSection = newShowedSection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        uIModel.setShowedSection(newShowedSection);
        
    }
    
    
}
