/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.Controller.productController;

import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.jpaController.exceptions.IllegalOrphanException;
import nrz.fairHandler.jpaController.exceptions.NonexistentEntityException;
import nrz.fairHandler.model.ProductModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import nrz.java.utility.ColorTense;
import nrz.java.view.component.FOptionPane;
import nrz.java.view.component.FTable;

/**
 *
 * @author abderrahim
 */
public class RemoveProductController extends AbstractAction {

    private final ProductModel productModel;
    private final FTable table;

    public RemoveProductController(ProductModel productModel, FTable table) {
        this.productModel = productModel;
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (table.getSelectedRow() != -1) {

            Pruduct pruduct = productModel.findProduct((Integer) table.getSelectedRowID());
            int confirmation = FOptionPane.showRemoveMessage(new ColorTense(Color.BLACK), "Supression d'un produit", "Etes-vous sure de vouloir supprimer le produit : " + pruduct.getArticleCode() + " - " + pruduct.getDesignation());
            if (confirmation == 1) {
                try {
                    productModel.destroyProduct(pruduct.getIdArticle());
                } catch (IllegalOrphanException ex) {
                    Logger.getLogger(RemoveProductController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(RemoveProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            FOptionPane.showInformationMessage(new ColorTense(Color.BLACK), "Aucun produit selectionné", "Veuilliez au préalable selectionner une ligne dans le tableau des produits");
        }
    }

}
