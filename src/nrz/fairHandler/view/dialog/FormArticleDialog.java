/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.dialog;

import nrz.fairHandler.Controller.productController.EditProductController;
import nrz.java.utility.ColorTense;
import nrz.java.utility.FConstant;
import nrz.java.utility.Futilities;
import nrz.java.view.component.FButton;
import nrz.java.view.component.FComboBox;
import nrz.java.view.component.FDialog;
import nrz.java.view.component.FormComponent;
import nrz.fairHandler.Controller.productController.addProductController;
import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.jpa.Unit;
import nrz.fairHandler.model.ProductModel;
import nrz.fairHandler.model.WeightModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextField;

/**
 *
 * @author abderrahim
 */
public class FormArticleDialog extends FDialog implements Observer {

    private final WeightModel weightModel=new WeightModel();
    private final ProductModel productModel;
    private JTextField codeTextField;
    private JTextField designationTextField;
    private JTextField categorieTextField;
    private FComboBox uniteComboBox;

    private FButton okButton;
    private FButton cancelButton;

    public FormArticleDialog( ProductModel productModel, ColorTense dialogColor, String titleHeader) {
        super(dialogColor, titleHeader);
        this.productModel = productModel;
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.productModel.addObserver(this);

        this.build();
        this.addFButton(okButton, 100);
        this.okButton.addActionListener(new addProductController(this,this.productModel, this.codeTextField, this.designationTextField, uniteComboBox, this.categorieTextField));

        this.setVisible(true);
    }

       public FormArticleDialog( ProductModel productModel, ColorTense dialogColor, String titleHeader,Pruduct product) {
        super(dialogColor, titleHeader);
        this.productModel = productModel;
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.productModel.addObserver(this);

        this.build();
        this.codeTextField.setText(product.getArticleCode());
        this.designationTextField.setText(product.getDesignation());
        this.categorieTextField.setText(product.getCategorie());
        
        this.okButton.addActionListener(new EditProductController(this,productModel, codeTextField, designationTextField, uniteComboBox, categorieTextField, product));        
        this.addFButton(okButton, 100);

        this.setVisible(true);
    }
    
    
    private void build() {
        this.initForm();
        this.initFooter();
        this.initEvents();
    }

    private void initForm() {

        this.codeTextField = new JTextField();
        this.codeTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent referenceComponent = new FormComponent("Référence :", this.codeTextField);
        referenceComponent.setHorizontal(100);
        referenceComponent.setBounds(10, 10, 375, referenceComponent.getPreferredSize().height);
        this.addBodyComponent(referenceComponent);

        this.designationTextField = new JTextField();
        this.designationTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent designationComponent = new FormComponent("Désignation :", this.designationTextField);
        designationComponent.setHorizontal(100);
        Futilities.setOnTheBottom(referenceComponent, designationComponent, 375, designationComponent.getPreferredSize().height);
        this.addBodyComponent(designationComponent);

        this.uniteComboBox = new FComboBox(dialogColor) {

            @Override
            public List<Object> getItemsContent(Object o) {
                List item = new ArrayList();

                Unit unit = (Unit) o;
                item.add(unit.getIdunite());
                item.add(unit.getDesignation() + " (" + unit.getAbrevation() + ")");
                return item;
            }
        };
        FormComponent uniteComponent = new FormComponent("Unité :", this.uniteComboBox);
        uniteComponent.setHorizontal(100);
        Futilities.setOnTheBottom(designationComponent, uniteComponent, 375, uniteComponent.getPreferredSize().height);
        this.addBodyComponent(uniteComponent);
        this.uniteComboBox.addItems(this.weightModel.getReferencesUnites());

        this.categorieTextField = new JTextField();
        this.categorieTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent categorieComponent = new FormComponent("Catégorie :", this.categorieTextField);
        categorieComponent.setHorizontal(100);
        Futilities.setOnTheBottom(uniteComponent, categorieComponent, 375, categorieComponent.getPreferredSize().height);
        this.addBodyComponent(categorieComponent);

    }

    private void initFooter() {
        this.okButton = new FButton("Enregister", null, dialogColor);
        this.okButton.setVertical();

        this.cancelButton = new FButton("Annuler", null, dialogColor);
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

    @Override
    public void update(Observable o, Object arg) {
        int notification = (int) arg;
        if (o instanceof ProductModel) {
            switch (notification) {
                case (ProductModel.CREATE_PRODUCT_NOTIFIER):
                    dispose();
                    break;
            }
        }
    }
}
