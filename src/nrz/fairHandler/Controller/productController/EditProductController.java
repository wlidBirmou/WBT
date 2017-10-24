/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.Controller.productController;

import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.jpaController.exceptions.NonexistentEntityException;
import nrz.fairHandler.model.ProductModel;
import nrz.fairHandler.model.WeightModel;
import nrz.java.utility.ColorTense;
import nrz.java.view.component.FComboBox;
import nrz.java.view.component.FOptionPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author abderrahim
 */
public class EditProductController extends AbstractAction {

     private final JDialog dialog;
    private final ProductModel productModel;
    private final JTextField codeTextField;
    private final JTextField designationTextField;
    private final FComboBox uniteComboBox;
    private final JTextField categorieTextField;
       private final Pruduct pruduct;

    public EditProductController(JDialog dialog,ProductModel productModel, JTextField codeTextField, JTextField designationTextField, FComboBox uniteComboBox, JTextField categorieTextField,Pruduct pruduct) {
        this.productModel = productModel;
        this.codeTextField = codeTextField;
        this.designationTextField = designationTextField;
        this.uniteComboBox = uniteComboBox;
        this.categorieTextField = categorieTextField;
        this.pruduct=pruduct;
        this.dialog=dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(validForm()){
        pruduct.setArticleCode(this.formatteProducteCode());
        pruduct.setDesignation(designationTextField.getText());
        WeightModel weightModel=new WeightModel();
        pruduct.setUnite(weightModel.findUnit((Integer) uniteComboBox.getSelectedItemID()));
        pruduct.setCategorie(categorieTextField.getText());
        try {
            productModel.editProduct(pruduct);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EditProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EditProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dialog.dispose();
        }
    }

private boolean validForm() {
        Boolean validForm = true;
        if (codeTextField.getText().length() > 4 || codeTextField.getText().trim().isEmpty()) {
            FOptionPane.showWarningMessage(new ColorTense(Color.BLACK), "Champ code produits non valide", "Le champ <b>Code produit</b> doit etre un nombre entier, au maximum de quatres chiffres");
            validForm = false;

        } else if (this.nonValidProductCode()) {
            FOptionPane.showWarningMessage(new ColorTense(Color.BLACK), "Champ code produits non valide", "Le champ <b>Code produit</b> doit etre un nombre entier, au maximum de quatres chiffres");            
        } else if (designationTextField.getText().trim().isEmpty()) {
            FOptionPane.showWarningMessage(new ColorTense(Color.BLACK), "champ désignation vide", "Le champ <b>Désignation </b> est obligatoire");
            validForm = false;
        }
        return validForm;
    }

    private boolean nonValidProductCode() {
        try {
            Integer.parseInt(codeTextField.getText());
            return false;
        } catch (NumberFormatException ex) {
            return true;
        }
    }

    private String formatteProducteCode(){
        int length=codeTextField.getText().length();
        String formattedCode=codeTextField.getText();
        for(int i=0;i<4-length;i++){
            formattedCode="0"+formattedCode;
        }
        return formattedCode;
    }
}
