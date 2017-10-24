/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.dialog;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import jrf.view.FlatJRViewer;
import net.sf.jasperreports.engine.JRException;
import nrz.fairHandler.model.WeightModel;
import nrz.fairHandler.reports.ReportServices;
import nrz.fairHandlerStates.keys.FrameTockenKeys;
import nrz.java.utility.ColorTense;
import nrz.java.view.component.FButton;
import nrz.java.view.component.FDialog;

/**
 *
 * @author rahimAdmin
 */
public class WeightReportDialog extends FDialog {

    private FButton cancelButton;
    private FlatJRViewer viewer;
    private WeightModel model;
    private ReportType reportType;

    public WeightReportDialog(ColorTense dialogColor, String titleHeader, WeightModel model, ReportType reportType) {
        super(dialogColor, titleHeader);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height - 60);
        this.setLocationRelativeTo(null);

        this.model = model;
        this.reportType = reportType;
        this.build();
        this.setVisible(true);

    }

    private void build() {
        this.initJRViewer();
        this.initFooter();
        this.initEvents();
        this.initLayout();
    }

    private void initJRViewer() {
        switch (this.reportType) {
            case ONE_WEIGHT_FORM:
                this.initOneWeightFormViewer();
                break;
            case WEIGHTS_ARRAY_FORM:
                this.initWeightArrayFormViewer();
                break;
        }
        this.addBodyComponent(viewer);
    }

    private void initOneWeightFormViewer() {
        switch (this.model.getSelectedWeight().getType()) {
            case FrameTockenKeys.UNIQUE_TYPE_KEY:
                try {
                    this.viewer = new FlatJRViewer(ReportServices.getUniqueWeightReport(this.model.getSelectedWeight()));
                } catch (JRException ex) {
                    Logger.getLogger(WeightReportDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case FrameTockenKeys.NEGATIVE_TYPE_KEY:
                try {
                    this.viewer = new FlatJRViewer(ReportServices.getNegativeWeightReport(this.model.getSelectedWeight()));
                } catch (JRException ex) {
                    Logger.getLogger(WeightReportDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case FrameTockenKeys.POSITIVE_TYPE_KEY:
                try {
                    this.viewer = new FlatJRViewer(ReportServices.getPositiveWeightReport(this.model.getSelectedWeight()));
                } catch (JRException ex) {
                    Logger.getLogger(WeightReportDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case FrameTockenKeys.NUL_TYPE_KEY:
                try {
                    this.viewer = new FlatJRViewer(ReportServices.getNullWeightReport(this.model.getSelectedWeight()));
                } catch (JRException ex) {
                    Logger.getLogger(WeightReportDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                case FrameTockenKeys.PREDETERMINED_TARE_KEY:
                try {
                    this.viewer = new FlatJRViewer(ReportServices.getPredeterminedWeightReport(this.model.getSelectedWeight()));
                } catch (JRException ex) {
                    Logger.getLogger(WeightReportDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }
    private void initWeightArrayFormViewer(){
        try {
            this.viewer=new FlatJRViewer(ReportServices.getWeightsArraysJRPrint(model));
        } catch (JRException ex) {
            Logger.getLogger(WeightReportDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initEvents() {
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initFooter() {
        this.cancelButton = new FButton("Fermer", null, dialogColor);
        this.cancelButton.setVertical();
        this.addFButton(cancelButton, 100);
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(this.dialogBody);
        this.dialogBody.setLayout(layout);

        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup();

        sequentialGroup.addComponent(this.viewer, 0, 0, 2000);
        parallelGroup.addComponent(this.viewer, 0, 0, 2000);
        layout.setHorizontalGroup(sequentialGroup);
        layout.setVerticalGroup(parallelGroup);

    }

    public enum ReportType {
        ONE_WEIGHT_FORM,
        WEIGHTS_ARRAY_FORM,
    }

}
