/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.managePanel.realTimePanel;

import nrz.fairHandler.model.RealTimeModel;
import nrz.fairHandler.utility.FairHandlerUtility;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import org.javatuples.Pair;
import nrz.java.utility.ColorTense;
import nrz.java.utility.FConstant;
import nrz.java.utility.Futilities;
import nrz.fairHandler.utility.keys.RealTimeKeys;
import nrz.fairHandlerStates.model.FhsStatesModel;

/**
 *
 * @author abderrahim
 */
public class RealTimeViewerPanel extends JPanel implements Observer {

    private final RealTimeModel realTimeModel;

    private JLabel realTimeWeightLabel;
    private JSeparator verticalSeparator;
    private JScrollPane realTimeInformationScrollPane;
    private JPanel realTimeInformationsPanel;
    private ArrayList<Pair<JLabel, JLabel>> realTimeInformationsPairlabels;

    private final ColorTense colorTense;

    public RealTimeViewerPanel(RealTimeModel realTimeModel, ColorTense colorTense) {
        this.realTimeModel = realTimeModel;
        this.colorTense = colorTense;
        this.setOpaque(false);

        this.realTimeModel.addObserver(this);
        FhsStatesModel.getInstance().addObserver(this);
        this.build();
        this.setVisible(true);
    }

    private void build() {
        this.initViewBoard();
        this.initLayout();
    }

    private void initViewBoard() {
        this.realTimeWeightLabel = new JLabel("---- Kg");
        this.realTimeWeightLabel.setFont(new Font("Digital-7", Font.PLAIN, 80));
        this.realTimeWeightLabel.setBackground(colorTense);
        this.realTimeWeightLabel.setVerticalAlignment(JLabel.CENTER);
        this.realTimeWeightLabel.setVerticalTextPosition(JLabel.NORTH);
        this.realTimeWeightLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 25, 80));
        this.realTimeWeightLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.realTimeWeightLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.realTimeWeightLabel.setOpaque(true);
        this.realTimeWeightLabel.setForeground(Color.WHITE);

        this.verticalSeparator = new JSeparator();
        this.verticalSeparator.setOrientation(JSeparator.VERTICAL);
        this.verticalSeparator.setForeground(Color.WHITE);
        this.verticalSeparator.setBackground(Color.WHITE);
        this.verticalSeparator.setOpaque(true);

        this.realTimeInformationsPanel = new JPanel(null);
        this.realTimeInformationsPanel.setBackground(colorTense);
        this.realTimeInformationsPanel.setOpaque(true);
        this.realTimeInformationsPanel.setPreferredSize(new Dimension(0, 300));
        this.realTimeInformationScrollPane = new JScrollPane(this.realTimeInformationsPanel);
        this.realTimeInformationScrollPane.setBorder(null);

        this.realTimeInformationsPairlabels = new ArrayList<>();
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.DATE_HOUR_KEY));
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.PRINT_STATE));
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.RCD_CODE));
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.PRODUCT_CODE));
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.PREDETERMINED_TARE));
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.CENTER_OF_ZERO_STATE));
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.GENERIC_CODE));
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.TARE_CODE));
        this.realTimeInformationsPairlabels.add(this.addRealTimeNewInformations(RealTimeKeys.PROGRESSIVE_CODE));

        this.initInformationPanelLayout();

    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        GroupLayout.SequentialGroup smallSequentialGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup smallParallelGroup = layout.createParallelGroup();

        smallSequentialGroup.addComponent(this.realTimeWeightLabel, 0, 0, 2000);
        smallSequentialGroup.addComponent(this.verticalSeparator, 2, 2, 2);
        smallSequentialGroup.addComponent(this.realTimeInformationScrollPane, 0, 0, 500);

        smallParallelGroup.addComponent(this.realTimeWeightLabel, 0, 0, 2000);
        smallParallelGroup.addComponent(this.verticalSeparator, 2, 2, 2);
        smallParallelGroup.addComponent(this.realTimeInformationScrollPane, 0, 0, 2000);

        layout.setVerticalGroup(smallParallelGroup);
        layout.setHorizontalGroup(smallSequentialGroup);

    }

    private Pair<JLabel, JLabel> addRealTimeNewInformations(String firstLabelString) {

        JLabel firstLabel = new JLabel(firstLabelString);
        firstLabel.setForeground(Color.WHITE);
        firstLabel.setFont(FConstant.SEGOE_UI_22);
        firstLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        firstLabel.setOpaque(false);

        JLabel secondLabel = new JLabel();
        secondLabel.setForeground(Color.WHITE);
        secondLabel.setFont(FConstant.SEGOE_UI_22);
        secondLabel.setOpaque(false);

        return new Pair(firstLabel, secondLabel);
    }

    private void initInformationPanelLayout() {

        this.realTimeInformationsPairlabels.get(0).getValue0().setBounds(10, 10, 240, 30);
        Futilities.setOnTheLeft(this.realTimeInformationsPairlabels.get(0).getValue0(), this.realTimeInformationsPairlabels.get(0).getValue1(), 250, 30, 0);
        this.realTimeInformationsPanel.add(this.realTimeInformationsPairlabels.get(0).getValue0());
        this.realTimeInformationsPanel.add(this.realTimeInformationsPairlabels.get(0).getValue1());

        JLabel precedentElement = this.realTimeInformationsPairlabels.get(0).getValue0();
        JLabel firstLabel;
        JLabel secondLabel;
        for (int i = 1; i < realTimeInformationsPairlabels.size(); i++) {
            firstLabel = this.realTimeInformationsPairlabels.get(i).getValue0();
            secondLabel = this.realTimeInformationsPairlabels.get(i).getValue1();
            Futilities.setOnTheBottom(precedentElement, firstLabel, 240, 30, 0);
            Futilities.setOnTheLeft(firstLabel, secondLabel, 240, 30, 0);
            precedentElement = firstLabel;
            this.realTimeInformationsPanel.add(firstLabel);
            this.realTimeInformationsPanel.add(secondLabel);
        }
    }

    private void updateRealTimeInformationLabel() {
        for (int i = 0; i < realTimeInformationsPairlabels.size(); i++) {
            this.realTimeInformationsPairlabels.get(i).getValue1().setText(this.realTimeModel.getRealTimeInformation(this.realTimeInformationsPairlabels.get(i).getValue0().getText()));
        }
    }

    private void setRealTimeInformationLabelToZero() {
        for (int i = 0; i < realTimeInformationsPairlabels.size(); i++) {
            this.realTimeInformationsPairlabels.get(i).getValue1().setText("");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        int notification = (int) arg;
        if (o instanceof RealTimeModel) {
            switch (notification) {
                case (RealTimeModel.UPDATE_REAL_TIME_BOARD_NOTIFIER):
                    this.realTimeWeightLabel.setText(FairHandlerUtility.FormatteWeight(this.realTimeModel.getRealTimeWeight(), "Kg"));
                    this.updateRealTimeInformationLabel();
                    break;
            }
        }

        if (o instanceof FhsStatesModel) {
            switch (notification) {
                case (FhsStatesModel.BALANCE_CONNECTED_STATE):
                    if (!FhsStatesModel.getInstance().isBalanceConnectedState()) {
                        this.realTimeWeightLabel.setText("---- Kg");
                        this.setRealTimeInformationLabelToZero();
                    }
                    break;
            }
        }
        

    }
}
