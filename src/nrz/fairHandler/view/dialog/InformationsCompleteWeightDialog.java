/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.dialog;

import nrz.java.utility.ColorTense;
import nrz.java.utility.FConstant;
import nrz.java.utility.FCustomFormatter;
import nrz.java.utility.Futilities;
import nrz.java.view.component.FButton;
import nrz.java.view.component.FDialog;
import nrz.java.view.component.FormComponent;
import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.jpa.Weight;
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
import nrz.fairHandlerStates.keys.FrameTockenKeys;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 *
 * @author abderrahim
 */
public class InformationsCompleteWeightDialog extends FDialog {

    private JPanel generalInformationPanel;
    private JPanel topShowedPanel;
    private JPanel bottomShowedPanel;
    private JTextField genericCodeTextField;
    private JTextField rCDTextField;
    private JTextField progressiveCodeTextField;
    private JTextField tareCode;
    private JTextPane productTextPane;
    private JTextField typeTextField;
    private JLabel firstTicketDateTimeLabel;
    private JLabel firstTicketLabel;
    private JLabel secondTicketLabel;
    private JLabel secondTicketDateTimeLabel;
    private JFormattedTextField tareFormattedTextField;
    private JFormattedTextField grossWeightFormattedTextField;
    private JFormattedTextField netWeightFormattedTextField;
    private FButton cancelButton;
    private final Weight selectedWeight;

    public InformationsCompleteWeightDialog(ColorTense dialogColor, String titleHeader, Weight weight) {
        super(dialogColor, titleHeader);
        this.selectedWeight = weight;
        this.setSize(new Dimension(860, 450));
        this.setLocationRelativeTo(null);

        this.build();

        this.genericCodeTextField.setEditable(false);
        this.rCDTextField.setEditable(false);
        this.progressiveCodeTextField.setEditable(false);
        this.tareCode.setEditable(false);
        this.productTextPane.setEditable(false);
        this.netWeightFormattedTextField.setEditable(false);
        this.typeTextField.setEditable(false);
        this.tareFormattedTextField.setEditable(false);
        this.grossWeightFormattedTextField.setEditable(false);

        this.setVisible(true);
    }

    private void build() {
        this.initComponents();
        this.initFooter();
        this.initEvents();
        this.initLayout();
    }

    private void initComponents() {
        this.topShowedPanel = new JPanel(null);
        this.topShowedPanel.setOpaque(false);

        this.bottomShowedPanel = new JPanel(null);
        this.bottomShowedPanel.setOpaque(false);

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

        this.progressiveCodeTextField = new JTextField(this.selectedWeight.getProgressiveCode());
        this.progressiveCodeTextField.setFont(FConstant.SEGOE_UI_13);
        this.progressiveCodeTextField.setOpaque(false);
        FormComponent progressiveCodeComponent = new FormComponent("Code progréssif :", this.progressiveCodeTextField);
        progressiveCodeComponent.setHorizontal(100);
        Futilities.setOnTheBottom(rCDComponent, progressiveCodeComponent, 400, 30);
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
        productScrollPane.setBackground(Color.WHITE);
        productScrollPane.setBorder(BorderFactory.createLineBorder(dialogColor.littleWhityColor(), 1));
        FormComponent productForm = new FormComponent("Produits :", productScrollPane);
        productForm.setHorizontal(100);
        productForm.getTextLabel().setFont(FConstant.SEGOE_UI_13);
        Futilities.setOnTheBottom(tareCodeComponent, productForm, 400, 65);
        this.generalInformationPanel.add(productForm);

        this.netWeightFormattedTextField = new JFormattedTextField(FCustomFormatter.getPriceDisplayFormat(this.selectedWeight.getNetWeight(), "Kg"));
//        this.netWeightFormattedTextField.setFormatterFactory(FCustomFormatter.getQuantityFormatterFactory());
        this.netWeightFormattedTextField.setValue(FCustomFormatter.getPriceDisplayFormat(this.selectedWeight.getNetWeight(), "Kg"));
        this.netWeightFormattedTextField.setOpaque(false);
        this.netWeightFormattedTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent netWeightForm = new FormComponent("Poids net :", this.netWeightFormattedTextField);
        netWeightForm.setHorizontal(100);
        Futilities.setOnTheBottom(productForm, netWeightForm, 400, 30);
        this.generalInformationPanel.add(netWeightForm);

        this.typeTextField = new JTextField(this.selectedWeight.getType());
        this.typeTextField.setFont(FConstant.SEGOE_UI_13);
        this.typeTextField.setOpaque(false);
        FormComponent typeComponent = new FormComponent("Type :", this.typeTextField);
        typeComponent.setHorizontal(100);
        Futilities.setOnTheBottom(netWeightForm, typeComponent, 400, 30);
        this.generalInformationPanel.add(typeComponent);

        this.firstTicketLabel = new JLabel("Premier ticket");
        this.firstTicketLabel.setOpaque(false);
        this.firstTicketLabel.setFont(FConstant.SEGOE_UI_14_BOLD);
        this.firstTicketLabel.setBounds(10, 10, 400, 30);
        this.topShowedPanel.add(firstTicketLabel);

        this.firstTicketDateTimeLabel = new JLabel();
        DateTime firstDateTime = new DateTime(selectedWeight.getFirstTicketDate());
        LocalTime firstLocalTime = new LocalTime(selectedWeight.getFirstTicketTime());
        this.firstTicketDateTimeLabel.setText("Date : " + firstDateTime.toString("dd-MM-YYYY") + " (" + firstLocalTime.toString("HH:mm:ss") + ")");
        this.firstTicketDateTimeLabel.setOpaque(false);
        this.firstTicketDateTimeLabel.setFont(FConstant.SEGOE_UI_14);
        Futilities.setOnTheBottom(this.firstTicketLabel, this.firstTicketDateTimeLabel, 400, 30);
        this.topShowedPanel.add(this.firstTicketDateTimeLabel);

        this.secondTicketLabel = new JLabel("Deuxième ticket");
        this.secondTicketLabel.setOpaque(false);
        this.secondTicketLabel.setFont(FConstant.SEGOE_UI_14_BOLD);
        this.secondTicketLabel.setBounds(10, 10, 375, 30);
        this.bottomShowedPanel.add(this.secondTicketLabel);

        this.secondTicketDateTimeLabel = new JLabel("Date :");
        DateTime secondDateTime = new DateTime(selectedWeight.getSecondTicketDate());
        LocalTime secondLocalTime = new LocalTime(selectedWeight.getSecondTicketTime());
        this.secondTicketDateTimeLabel.setText("Date : " + secondDateTime.toString("dd-MM-YYYY") + " (" + secondLocalTime.toString("HH:mm:ss") + ")");

        this.secondTicketDateTimeLabel.setOpaque(false);
        this.secondTicketDateTimeLabel.setFont(FConstant.SEGOE_UI_14);
        Futilities.setOnTheBottom(this.secondTicketLabel, this.secondTicketDateTimeLabel, 400, 30);
        this.bottomShowedPanel.add(this.secondTicketDateTimeLabel);

        this.tareFormattedTextField = new JFormattedTextField(FCustomFormatter.getPriceDisplayFormat(this.selectedWeight.getTare(), "Kg"));
//        this.tareFormattedTextField.setFormatterFactory(FCustomFormatter.getQuantityFormatterFactory());
        this.tareFormattedTextField.setOpaque(false);
        this.tareFormattedTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent tareForm = new FormComponent("Tare :", this.tareFormattedTextField);
        tareForm.setHorizontal(100);

        this.grossWeightFormattedTextField = new JFormattedTextField(FCustomFormatter.getPriceDisplayFormat(this.selectedWeight.getGrossWeight(), "Kg"));
        this.grossWeightFormattedTextField.setOpaque(false);
        this.grossWeightFormattedTextField.setFont(FConstant.SEGOE_UI_13);
        FormComponent grossWeightForm = new FormComponent("Poids brût :", this.grossWeightFormattedTextField);
        grossWeightForm.setHorizontal(100);

        if (selectedWeight.getType().equals(FrameTockenKeys.POSITIVE_TYPE_KEY)) {
            Futilities.setOnTheBottom(this.firstTicketDateTimeLabel, tareForm, 400, 30);
            this.topShowedPanel.add(tareForm);
            Futilities.setOnTheBottom(this.secondTicketDateTimeLabel, grossWeightForm, 400, 30);
            this.bottomShowedPanel.add(grossWeightForm);
        } else {
            Futilities.setOnTheBottom(this.firstTicketDateTimeLabel, grossWeightForm, 400, 30);
            this.topShowedPanel.add(grossWeightForm);
            Futilities.setOnTheBottom(this.secondTicketDateTimeLabel, tareForm, 400, 30);
            this.bottomShowedPanel.add(tareForm);
        }
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

        GroupLayout.SequentialGroup rightSequentialGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup rightParallelGroup = layout.createParallelGroup();

        rightSequentialGroup.addComponent(this.topShowedPanel, 200, 200, 200);
        rightSequentialGroup.addComponent(this.bottomShowedPanel, 200, 200, 200);

        rightParallelGroup.addComponent(this.topShowedPanel, 0, 0, 2000);
        rightParallelGroup.addComponent(this.bottomShowedPanel, 0, 0, 2000);

        sequentialGroup.addComponent(this.generalInformationPanel, 0, 0, 2000);
        sequentialGroup.addGroup(rightParallelGroup);

        parallelGroup.addComponent(this.generalInformationPanel, 400, 400, 400);
        parallelGroup.addGroup(rightSequentialGroup);

        layout.setHorizontalGroup(sequentialGroup);
        layout.setVerticalGroup(parallelGroup);
    }

}
