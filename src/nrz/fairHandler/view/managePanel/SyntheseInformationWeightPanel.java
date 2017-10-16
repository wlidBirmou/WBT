/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.managePanel;

import nrz.fairHandler.jpa.Weight;
import nrz.fairHandler.model.RealTimeModel;
import nrz.fairHandler.model.WeightModel;
import nrz.fairHandler.utility.FairHandlerUtility;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import nrz.fairHandler.Controller.weightController.WeightAdjustementListener;
import nrz.fairHandler.Controller.weightController.WeightSelectedListener;
import nrz.fairHandlerStates.model.FhsStatesModel;
import org.joda.time.DateTime;
import nrz.java.utility.ColorTense;
import nrz.java.utility.FConstant;
import nrz.java.view.component.FTable;

/**
 *
 * @author abderrahim
 */
public class SyntheseInformationWeightPanel extends JPanel implements Observer {

    private final RealTimeModel uIModel;
    private final WeightModel weightModel;
    private JLabel titleLabel;
    private FTable weightTable;
    private final ColorTense colorTense;

    private ExecutorService weightTableUpdateExecutor;

    public SyntheseInformationWeightPanel(RealTimeModel uIModel, WeightModel weightModel, String titleString, ColorTense colorTense) {
        this.uIModel = uIModel;
        this.titleLabel = new JLabel();
        this.titleLabel.setText(titleString);
        this.colorTense = colorTense;
        this.weightModel = weightModel;

        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        this.uIModel.addObserver(this);
        this.weightModel.addObserver(this);
        FhsStatesModel.getInstance().addObserver(this);

        this.build();
        this.setVisible(true);
    }

    private void build() {
        this.initComponents();
        this.initTable();
        this.initEvents();
    }

    private void initComponents() {
        this.titleLabel.setOpaque(false);
        this.titleLabel.setFont(FConstant.SEGOE_UI_30);
        this.titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.titleLabel.setPreferredSize(new Dimension(this.getSize().width, 60));
        this.add(this.titleLabel, BorderLayout.NORTH);
    }

    private void initTable() {
        this.weightTable = new FTable(colorTense) {
            @Override
            public List<Object> getColumnsContent(Object o) {
                Weight weight = (Weight) o;

                ArrayList row = new ArrayList();
                row.add(weight.getIdWeight());
                row.add("" + (weightTable.getRowCount() + 1));
                row.add(weight.getGenericCode());
                row.add(weight.getReference());
                try {
                    row.add(weight.getArticle().getArticleCode() + " - " + weight.getArticle().getDesignation());
                } catch (NullPointerException ex) {
                    row.add("Non specifié");
                }

                row.add(weight.getType());
                DateTime firstTicketDate = new DateTime(weight.getFirstTicketDate());
                DateTime firstTicketTime = new DateTime(weight.getFirstTicketTime());
                row.add(firstTicketDate.toString("dd/MM/YYYY") + firstTicketTime.toString(" (HH:mm:ss)"));
                if (weight.getSecondTicketDate() != null && weight.getSecondTicketTime() != null) {
                    DateTime secondTicketDate = new DateTime(weight.getSecondTicketDate());

                    DateTime secondTicketTime = new DateTime(weight.getSecondTicketTime());
                    row.add(secondTicketDate.toString("dd/MM/YYYY") + secondTicketTime.toString(" (HH:mm:ss)"));
                } else {
                    row.add("/");
                }
                row.add(FairHandlerUtility.FormatteWeight(weight.getGrossWeight(), "Kg"));
                if (weight.getTare() != null) {
                    row.add(FairHandlerUtility.FormatteWeight(weight.getTare(), "Kg"));
                } else {
                    row.add("/");
                }
                if (weight.getNetWeight() != null) {
                    row.add(FairHandlerUtility.FormatteWeight(weight.getNetWeight(), "Kg"));
                } else {
                    row.add("/");
                }
                return row;
            }
        };

        this.weightTable.addFColumn("N°", 40);
        this.weightTable.addFColumn("Generic Code ", 120);
        this.weightTable.addFColumn("RCD ", 80);
        this.weightTable.addFColumn("PRODUITS");
        this.weightTable.addFColumn("Type", 150);
        this.weightTable.addFColumn("<html>1<sup>ere</sup> Pesée</html>", 160);
        this.weightTable.addFColumn("<html>2<sup>eme</sup> Pesée</html>", 160);
        this.weightTable.addFColumn("POIDS BRUTE", 130);
        this.weightTable.addFColumn("TARE", 130);
        this.weightTable.addFColumn("POIDS NET", 130);
        this.weightTable.addRows(weightModel.initWeightStep());
        this.add(this.weightTable.getScrollPane(), BorderLayout.CENTER);
    }

    private void initEvents() {
        this.weightTable.getScrollPane().getVerticalScrollBar().addAdjustmentListener(new WeightAdjustementListener(weightModel, weightTable));
        this.weightTable.getSelectionModel().addListSelectionListener(new WeightSelectedListener(weightTable, weightModel));
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }

    public FTable getWeightTable() {
        return weightTable;
    }

    public void setWeightTable(FTable weightTable) {
        this.weightTable = weightTable;
    }

    @Override
    public void update(Observable o, Object arg) {
        int notification = (int) arg;
        if (o instanceof RealTimeModel) {
            switch (notification) {
                case (RealTimeModel.SHOW_WEIGHT_SECTION_NOTIFIER):
                    this.weightTable.clear();
                    this.weightTable.addRows(weightModel.initWeightStep());
                    break;
                case (RealTimeModel.UPDATE_WEIGHT_NOTIFIER):
                    SwingWorker swingWorker = new SwingWorker() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SyntheseInformationWeightPanel.class.getName()).log(Level.SEVERE, null, ex);

                            }

                            return null;
                        }

                        @Override
                        protected void done() {
                            weightTable.clear();
                            weightTable.addRows(weightModel.initWeightStep());
                            super.done(); //To change body of generated methods, choose Tools | Templates.
                        }

                    };
                    swingWorker.execute();
                    break;
            }
        } else if (o instanceof WeightModel) {
            switch (notification) {
                case (WeightModel.EDITED_SEARCH_FILTER_NOTIFIER):
                    this.weightTable.clear();
                    this.weightTable.addRows(weightModel.initWeightStep());
                    break;

                case (WeightModel.EDITED_DATE_DEBUT_NOTIFIER):
                    this.weightTable.clear();
                    this.weightTable.addRows(weightModel.initWeightStep());
                    break;

                case (WeightModel.EDITED_DATE_FIN_NOTIFIER):
                    this.weightTable.clear();
                    this.weightTable.addRows(weightModel.initWeightStep());
                    break;
            }
        } else if (o instanceof FhsStatesModel) {

            switch (notification) {
                case (FhsStatesModel.DB_WEIGHT_STORE_SIGNAL_STATE):
                    if(FhsStatesModel.getInstance().isdBWeightStoreSignalState()){
                        SwingWorker swingWorker = new SwingWorker() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            try {
                                TimeUnit.SECONDS.sleep(60);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SyntheseInformationWeightPanel.class.getName()).log(Level.SEVERE, null, ex);

                            }

                            return null;
                        }

                        @Override
                        protected void done() {
                            weightTable.clear();
                            weightTable.addRows(weightModel.initWeightStep());
                            super.done(); //To change body of generated methods, choose Tools | Templates.
                        }

                    };
                    }
                    break;

            }
        }

    }

}
