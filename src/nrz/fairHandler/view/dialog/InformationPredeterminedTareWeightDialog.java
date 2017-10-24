/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.jpa.Weight;
import nrz.java.utility.ColorTense;
import nrz.java.utility.FConstant;
import nrz.java.utility.FCustomFormatter;
import nrz.java.utility.Futilities;
import nrz.java.view.component.FButton;
import nrz.java.view.component.FDialog;
import nrz.java.view.component.FormComponent;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 *
 * @author rahimAdmin
 */
public class InformationPredeterminedTareWeightDialog extends FDialog {

    private JPanel generalInformationPanel;
    private JPanel weightDetailsPanel;
    private JTextField genericCodeTextField;
    private JTextField rCDTextField;
    private JTextField progressiveCodeTextField;
    private JTextField tareCode;
    private JTextPane productTextPane;
    private JTextField typeTextField;
    private JLabel firstTicketDateTimeLabel;
    private JFormattedTextField grossWeightFormattedTextField;
    private JFormattedTextField tareWeightFormattedTextField;
    private JFormattedTextField netWeightFormattedTextField;
    private FButton cancelButton;
    private final Weight selectedWeight;

    public InformationPredeterminedTareWeightDialog(ColorTense dialogColor, String titleHeader, Weight weight) {
        super(dialogColor, titleHeader);
        this.selectedWeight = weight;
        this.setSize(new Dimension(420, 580));
        this.setLocationRelativeTo(null);

        this.build();

        this.genericCodeTextField.setEditable(false);
        this.rCDTextField.setEditable(false);
        this.progressiveCodeTextField.setEditable(false);
        this.tareCode.setEditable(false);
        this.productTextPane.setEditable(false);
        this.typeTextField.setEditable(false);
        this.grossWeightFormattedTextField.setEditable(false);
        this.tareWeightFormattedTextField.setEditable(false);
        this.netWeightFormattedTextField.setEditable(false);

        this.setVisible(true);
    }

    private void build() {
        this.initComponents();
        this.initFooter();
        this.initEvents();
        this.initLayout();
    }

    private void initComponents() {
        this.weightDetailsPanel = new JPanel(null);
        this.weightDetailsPanel.setOpaque(false);

        this.generalInformationPanel = new JPanel(null);
        this.generalInformationPanel.setOpaque(false);

        this.genericCodeTextField = new JTextField(this.selectedWeight.getGenericCode());
        this.genericCodeTextField.setFont(FConstant.SEGOE_UI_13);
        this.genericCodeTextField.setOpaque(false);
        FormComponent genericCodeComponent = new FormComponent("Code générique :", this.genericCodeTextField);
        genericCodeComponent.setHorizontal(100);
        genericCodeComponent.setBounds(10, 10, 400, genericCodeComponent.getPreferredSize().height);
        this.generalInformationPanel.add(genericCodeComponent);

        this.rCDTextField = new JTextField(this.selectedWeight.getReference());
        this.rCDTextField.setFont(FConstant.SEGOE_UI_13);
        this.rCDTextField.setOpaque(false);
        FormComponent rCDComponent = new FormComponent("RCD :", this.rCDTextField);
        rCDComponent.setHorizontal(100);
        Futilities.setOnTheBottom(genericCodeComponent, rCDComponent, 400, 30);
        this.generalInformationPanel.add(rCDComponent);

        this.typeTextField = new JTextField(selectedWeight.getType());
        this.typeTextField.setFont(FConstant.SEGOE_UI_13);
        this.typeTextField.setOpaque(false);
        FormComponent typeComponent = new FormComponent("Type :", this.typeTextField);
        typeComponent.setHorizontal(100);
        Futilities.setOnTheBottom(rCDComponent, typeComponent, 400, 30);
        this.generalInformationPanel.add(typeComponent);

        this.progressiveCodeTextField = new JTextField(this.selectedWeight.getProgressiveCode());
        this.progressiveCodeTextField.setFont(FConstant.SEGOE_UI_13);
        this.progressiveCodeTextField.setOpaque(false);
        FormComponent progressiveCodeComponent = new FormComponent("Progréssive :", this.progressiveCodeTextField);
        progressiveCodeComponent.setHorizontal(100);
        Futilities.setOnTheBottom(typeComponent, progressiveCodeComponent, 400, 30);
        this.generalInformationPanel.add(progressiveCodeComponent);

        this.tareCode = new JTextField(this.selectedWeight.getTareCode());
        this.tareCode.setFont(FConstant.SEGOE_UI_13);
        this.tareCode.setOpaque(false);
        FormComponent tareCodeComponent = new FormComponent("Code tare :", this.tareCode);
        tareCodeComponent.setHorizontal(100);
        Futilities.setOnTheBottom(progressiveCodeComponent, tareCodeComponent, 400, 30);
        this.generalInformationPanel.add(tareCodeComponent);

        Pruduct pruduct = this.selectedWeight.getArticle();
        this.productTextPane = new JTextPane();
        if (pruduct != null) {
            this.productTextPane.setText(pruduct.getArticleCode() + " - " + pruduct.getDesignation());
        } else {
            this.productTextPane.setText("Non Specifié");
        }
        this.productTextPane.setFont(FConstant.SEGOE_UI_13);
        this.productTextPane.setBackground(Color.WHITE);
        JScrollPane productScrollPane = new JScrollPane(productTextPane);
        productScrollPane.setFont(FConstant.SEGOE_UI_13);
        productScrollPane.setOpaque(false);
        productScrollPane.setBorder(BorderFactory.createLineBorder(dialogColor.littleWhityColor(), 1));
        FormComponent productForm = new FormComponent("Produits :", productScrollPane);
        productForm.setHorizontal(100);
        Futilities.setOnTheBottom(tareCodeComponent, productForm, 400, 65);
        this.generalInformationPanel.add(productForm);

        this.firstTicketDateTimeLabel = new JLabel();
        DateTime firstDateTime = new DateTime(selectedWeight.getFirstTicketDate());
        LocalTime firstLocalTime = new LocalTime(selectedWeight.getFirstTicketTime());
        this.firstTicketDateTimeLabel.setText("Date : " + firstDateTime.toString("dd-MM-YYYY") + " (" + firstLocalTime.toString("HH:mm:ss") + ")");
        this.firstTicketDateTimeLabel.setOpaque(false);
        this.firstTicketDateTimeLabel.setFont(FConstant.SEGOE_UI_14);
        this.firstTicketDateTimeLabel.setBounds(10, 10, 400, 30);
        this.weightDetailsPanel.add(this.firstTicketDateTimeLabel);

        this.grossWeightFormattedTextField = new JFormattedTextField();
        this.grossWeightFormattedTextField.setValue(FCustomFormatter.getPriceDisplayFormat(this.selectedWeight.getGrossWeight(), "Kg"));
        this.grossWeightFormattedTextField.setOpaque(false);
        this.grossWeightFormattedTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent grossWeightForm = new FormComponent("Poids brût :", this.grossWeightFormattedTextField);
        grossWeightForm.setHorizontal(100);
        Futilities.setOnTheBottom(this.firstTicketDateTimeLabel, grossWeightForm, 400, 30);
        this.weightDetailsPanel.add(grossWeightForm);

        this.tareWeightFormattedTextField = new JFormattedTextField();
        this.tareWeightFormattedTextField.setValue(FCustomFormatter.getPriceDisplayFormat(this.selectedWeight.getTare(), "Kg"));
        this.tareWeightFormattedTextField.setOpaque(false);
        this.tareWeightFormattedTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent tareWeightForm = new FormComponent("Tare :", this.tareWeightFormattedTextField);
        tareWeightForm.setHorizontal(100);
        Futilities.setOnTheBottom(grossWeightForm, tareWeightForm, 400, 30);
        this.weightDetailsPanel.add(tareWeightForm);

        this.netWeightFormattedTextField = new JFormattedTextField();
        this.netWeightFormattedTextField.setValue(FCustomFormatter.getPriceDisplayFormat(this.selectedWeight.getNetWeight(), "Kg"));
        this.netWeightFormattedTextField.setOpaque(false);
        this.netWeightFormattedTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent netWeightForm = new FormComponent("Poids net :", this.netWeightFormattedTextField);
        netWeightForm.setHorizontal(100);
        Futilities.setOnTheBottom(tareWeightForm, netWeightForm, 400, 30);
        this.weightDetailsPanel.add(netWeightForm);

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

    private void initLayout() {
        GroupLayout layout = new GroupLayout(this.dialogBody);
        this.dialogBody.setLayout(layout);

        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup();

        sequentialGroup.addComponent(this.generalInformationPanel, 0, 0, 300);
        sequentialGroup.addComponent(this.weightDetailsPanel, 0, 0, 190);

        parallelGroup.addComponent(this.generalInformationPanel, 0, 0, 2000);
        parallelGroup.addComponent(this.weightDetailsPanel, 0, 0, 2000);

        layout.setHorizontalGroup(parallelGroup);
        layout.setVerticalGroup(sequentialGroup);
    }

}
