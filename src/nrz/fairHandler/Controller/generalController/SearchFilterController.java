/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.Controller.generalController;

import nrz.fairHandler.model.ProductModel;
import nrz.fairHandler.model.RealTimeModel;
import nrz.fairHandler.model.WeightModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author abderrahim
 */
public class SearchFilterController implements DocumentListener {

    private final RealTimeModel uIModel;
    private final ProductModel ProductModel;
    private final WeightModel weightModel;

    public SearchFilterController(RealTimeModel uIModel, ProductModel ProductModel, WeightModel weightModel) {
        this.uIModel = uIModel;
        this.ProductModel = ProductModel;
        this.weightModel = weightModel;
    }

    @Override

    public void insertUpdate(DocumentEvent e) {
        switch (uIModel.getShowedSection()) {
            case (RealTimeModel.SHOW_WEIGHT_SECTION_NOTIFIER):
                try {
                    weightModel.setFilter(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    Logger.getLogger(SearchFilterController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case (RealTimeModel.SHOW_PRODUCT_SECTION_NOTIFIER):
                try {
                    ProductModel.setFilter(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    Logger.getLogger(SearchFilterController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

        switch (uIModel.getShowedSection()) {
            case (RealTimeModel.SHOW_WEIGHT_SECTION_NOTIFIER):
                try {
                    weightModel.setFilter(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    Logger.getLogger(SearchFilterController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case (RealTimeModel.SHOW_PRODUCT_SECTION_NOTIFIER):
                try {
                    ProductModel.setFilter(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    Logger.getLogger(SearchFilterController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

}
