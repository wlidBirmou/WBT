/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nrz.fairHandler.Controller.productController;

import nrz.fairHandler.model.ProductModel;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 *
 * @author abderrahim
 */
public class ProductAdjustementListener implements AdjustmentListener{
    private final ProductModel productModel;

    public ProductAdjustementListener(ProductModel productModel) {
        this.productModel = productModel;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        this.productModel.endScrollWeight();
    }
    
}
