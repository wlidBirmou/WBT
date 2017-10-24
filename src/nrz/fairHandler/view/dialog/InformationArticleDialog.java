/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.dialog;

import nrz.java.utility.ColorTense;
import nrz.java.utility.FConstant;
import nrz.java.utility.Futilities;
import nrz.java.view.component.FButton;
import nrz.java.view.component.FDialog;
import nrz.java.view.component.FormComponent;
import nrz.fairHandler.jpa.Pruduct;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 *
 * @author abderrahim
 */
public class InformationArticleDialog extends FDialog {

    private JTextField referenceTextField;
    private JTextField designationTextField;
    private JTextField categorieTextField;
    private JTextField unitTextField;

    private FButton cancelButton;

    public InformationArticleDialog(ColorTense dialogColor, String titleHeader, Pruduct product) {
        super(dialogColor, titleHeader);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.build();
        this.referenceTextField.setText(product.getArticleCode());
        this.designationTextField.setText(product.getDesignation());
        this.unitTextField.setText(product.getUnite().getDesignation() + " (" + product.getUnite().getAbrevation() + ")");
        this.categorieTextField.setText(product.getCategorie());

        this.setVisible(true);
        
    }

    private void build() {
        this.initForm();
        this.initFooter();
        this.initEvents();
    }

    private void initForm() {

        this.referenceTextField = new JTextField();
        this.referenceTextField.setFont(FConstant.SEGOE_UI_13);
        this.referenceTextField.setEditable(false);
        this.referenceTextField.setBackground(Color.WHITE);
        FormComponent referenceComponent = new FormComponent("Code Produit :", this.referenceTextField);
        referenceComponent.setHorizontal(100);
        referenceComponent.setBounds(10, 10, 375, referenceComponent.getPreferredSize().height);
        this.addBodyComponent(referenceComponent);

        this.designationTextField = new JTextField();
        this.designationTextField.setFont(FConstant.SEGOE_UI_13);
        this.designationTextField.setEditable(false);
        this.designationTextField.setBackground(Color.WHITE);
        FormComponent designationComponent = new FormComponent("Désignation :", this.designationTextField);
        designationComponent.setHorizontal(100);
        Futilities.setOnTheBottom(referenceComponent, designationComponent, 375, designationComponent.getPreferredSize().height);
        this.addBodyComponent(designationComponent);

        this.unitTextField = new JTextField();
        this.unitTextField.setFont(FConstant.SEGOE_UI_13);
        this.unitTextField.setEditable(false);
        this.unitTextField.setBackground(Color.WHITE);
        FormComponent unitComponent = new FormComponent("Unité :", this.unitTextField);
        unitComponent.setHorizontal(100);
        Futilities.setOnTheBottom(designationComponent, unitComponent, 375, designationComponent.getPreferredSize().height);
        this.addBodyComponent(unitComponent);

        this.categorieTextField = new JTextField();
        this.categorieTextField.setFont(FConstant.SEGOE_UI_13);
        this.categorieTextField.setEditable(false);
        this.categorieTextField.setBackground(Color.WHITE);
        FormComponent categorieComponent = new FormComponent("Catégorie :", this.categorieTextField);
        categorieComponent.setHorizontal(100);
        Futilities.setOnTheBottom(unitComponent, categorieComponent, 375, categorieComponent.getPreferredSize().height);
        this.addBodyComponent(categorieComponent);

    }

    
    private void initFooter() {
        this.cancelButton = new FButton("Fermer", null, dialogColor);
        this.cancelButton.setVertical();
        this.addFButton(cancelButton, 100);

    }

    private void initEvents() {
        this.cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
