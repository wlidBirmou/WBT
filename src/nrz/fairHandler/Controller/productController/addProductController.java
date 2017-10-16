/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.Controller.productController;

import nrz.java.utility.ColorTense;
import nrz.java.view.component.FComboBox;
import nrz.java.view.component.FOptionPane;
import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.model.ProductModel;
import nrz.fairHandler.model.WeightModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author abderrahim
 */
public class addProductController extends AbstractAction {

    private final JDialog dialog;
    private final ProductModel productModel;
    private final JTextField codeTextField;
    private final JTextField designationTextField;
    private final FComboBox uniteComboBox;
    private final JTextField categorieTextField;

    public addProductController(JDialog dialog, ProductModel productModel, JTextField codeTextField, JTextField designationTextField, FComboBox uniteComboBox, JTextField categorieTextField) {
        this.productModel = productModel;
        this.codeTextField = codeTextField;
        this.designationTextField = designationTextField;
        this.uniteComboBox = uniteComboBox;
        this.categorieTextField = categorieTextField;
        this.dialog = dialog;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (validForm()) {
            Pruduct pruduct = new Pruduct();
            pruduct.setArticleCode(this.formatteProducteCode());
            pruduct.setDesignation(designationTextField.getText());
            pruduct.setCategorie(categorieTextField.getText());
            WeightModel weightModel = new WeightModel();
            pruduct.setUnite(weightModel.findUnit((Integer) uniteComboBox.getSelectedItemID()));

            productModel.createProduct(pruduct);
            dialog.dispose();

        }
    }

    private boolean validForm() {
        Boolean validForm = true;
        if (codeTextField.getText().length() > 4 || codeTextField.getText().trim().isEmpty()) {
            FOptionPane.showWarningMessage(new ColorTense(Color.BLACK), "Champ code produits non valide", "Le champ <b>Code produits</b> doit etre un nombre entiern, au maximum de quatres chiffres");
            validForm = false;

        } else if (this.nonValidProductCode()) {
            FOptionPane.showWarningMessage(new ColorTense(Color.BLACK), "Champ code produits non valide", "Le champ <b>Code produits</b> doit etre un nombre entiern, au maximum de quatres chiffres");            
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
