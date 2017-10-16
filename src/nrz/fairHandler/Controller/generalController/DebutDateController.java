/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.Controller.generalController;

import com.toedter.calendar.JTextFieldDateEditor;
import nrz.fairHandler.model.ProductModel;
import nrz.fairHandler.model.RealTimeModel;
import nrz.fairHandler.model.WeightModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author abderrahim
 */
public class DebutDateController implements PropertyChangeListener {

    private final RealTimeModel uIModel;
    private final WeightModel weightModel;
    private final ProductModel productModel;

    public DebutDateController(RealTimeModel uIModel, WeightModel weightModel, ProductModel productModel) {
        this.uIModel = uIModel;
        this.weightModel = weightModel;
        this.productModel = productModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("date".equals(evt.getPropertyName())) {
            JTextFieldDateEditor dateDebutEditor = (JTextFieldDateEditor) evt.getSource();
            switch (uIModel.getShowedSection()) {
                case (RealTimeModel.SHOW_WEIGHT_SECTION_NOTIFIER):
                    this.weightModel.setBeginDate(dateDebutEditor.getDate());
                    break;
                case (RealTimeModel.SHOW_PRODUCT_SECTION_NOTIFIER):
                    this.productModel.setDebutDate(dateDebutEditor.getDate());
                    break;
            }
        }
    }
}
