/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.managePanel.realTimePanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import nrz.fairHandlerStates.model.FhFhsStatesModel;
import nrz.fairHandlerStates.model.FhsStatesModel;
import nrz.java.utility.FConstant;
import nrz.java.utility.Futilities;
import nrz.java.utility.Icons;
import nrz.java.view.component.FormComponent;

/**
 *
 * @author rahimAdmin
 */
public class GeneralStatesPanel extends JPanel implements Observer {

    public final static int DEFAULT_PANEL_HEIGHT = 270;
    private JScrollPane jScrollPane;

    private JLabel balanceStateLabel;
    private JLabel serviceConnectionStateLabel;
    private JLabel receiveFrameStateLabel;
    private JLabel validFrameStateLabel;
    private JLabel dbWeightStoreSignalLabel;
    private JLabel dbDecoderStoreSignalLabel;

    private Timer reduceSizeTimer;
    private Timer increaseSizeTimer;

    int formComponentHeight = 30;
    int formComponentWidth = 300;

    public GeneralStatesPanel() {

        FhsStatesModel.getInstance().addObserver(this);
        FhFhsStatesModel.getInstance().addObserver(this);

        this.build();

        this.setVisible(true);
    }

    private void build() {
        this.setLayout(null);
        this.initPanel();
        this.initComponents();
        this.initTimer();
    }

    private void initPanel() {

        this.jScrollPane = new JScrollPane();
        this.jScrollPane.add(this);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(FConstant.TABLEAU_DE_BORD_COLOR));
    }

    private void initComponents() {
        this.balanceStateLabel = new JLabel();
        this.balanceStateLabel.setHorizontalAlignment(JLabel.LEFT);
        this.balanceStateLabel.setVerticalAlignment(JLabel.CENTER);

        FormComponent balanceConnectionComponent = new FormComponent("Connection a la balance :", this.balanceStateLabel);
        balanceConnectionComponent.setHorizontal(250);
        balanceConnectionComponent.getTextLabel().setFont(FConstant.SEGOE_UI_15);
        balanceConnectionComponent.setBounds(10, 10, this.formComponentWidth, this.formComponentHeight);
        this.add(balanceConnectionComponent);

        this.serviceConnectionStateLabel = new JLabel();
        this.serviceConnectionStateLabel.setHorizontalAlignment(JLabel.LEFT);
        this.serviceConnectionStateLabel.setVerticalAlignment(JLabel.CENTER);

        FormComponent serviceConnectionComponent = new FormComponent("Connection au service :", this.serviceConnectionStateLabel);
        serviceConnectionComponent.setHorizontal(250);
        serviceConnectionComponent.getTextLabel().setFont(FConstant.SEGOE_UI_15);
        Futilities.setOnTheBottom(balanceConnectionComponent, serviceConnectionComponent, this.formComponentWidth, this.formComponentHeight);
        this.add(serviceConnectionComponent);

        this.receiveFrameStateLabel = new JLabel();
        this.receiveFrameStateLabel.setHorizontalAlignment(JLabel.LEFT);
        this.receiveFrameStateLabel.setVerticalAlignment(JLabel.CENTER);

        FormComponent frameStateComponent = new FormComponent("Envoie de trames :", this.receiveFrameStateLabel);
        frameStateComponent.setHorizontal(250);
        frameStateComponent.getTextLabel().setFont(FConstant.SEGOE_UI_15);
        Futilities.setOnTheBottom(serviceConnectionComponent, frameStateComponent, this.formComponentWidth, this.formComponentHeight);
        this.add(frameStateComponent);

        this.validFrameStateLabel = new JLabel();
        this.validFrameStateLabel.setHorizontalAlignment(JLabel.LEFT);
        this.validFrameStateLabel.setVerticalAlignment(JLabel.CENTER);

        FormComponent validFrameStateComponent = new FormComponent("Validité de trames :", this.validFrameStateLabel);
        validFrameStateComponent.setHorizontal(250);
        validFrameStateComponent.getTextLabel().setFont(FConstant.SEGOE_UI_15);
        Futilities.setOnTheBottom(frameStateComponent, validFrameStateComponent, this.formComponentWidth, this.formComponentHeight);
        this.add(validFrameStateComponent);

        this.dbWeightStoreSignalLabel = new JLabel();
        this.dbWeightStoreSignalLabel.setHorizontalAlignment(JLabel.LEFT);
        this.dbWeightStoreSignalLabel.setVerticalAlignment(JLabel.CENTER);

        FormComponent dbWeightStoreSignalComponent = new FormComponent("Enregistrement fichier (pesées) :", this.dbWeightStoreSignalLabel);
        dbWeightStoreSignalComponent.setHorizontal(250);
        dbWeightStoreSignalComponent.getTextLabel().setFont(FConstant.SEGOE_UI_15);
        Futilities.setOnTheBottom(validFrameStateComponent, dbWeightStoreSignalComponent, this.formComponentWidth, this.formComponentHeight);
        this.add(dbWeightStoreSignalComponent);

        this.dbDecoderStoreSignalLabel = new JLabel();
        this.dbDecoderStoreSignalLabel.setHorizontalAlignment(JLabel.LEFT);
        this.dbDecoderStoreSignalLabel.setVerticalAlignment(JLabel.CENTER);

        FormComponent dbDecoderStoreSignalComponent = new FormComponent("Enregistrement fichier (Decoder) :", this.dbDecoderStoreSignalLabel);
        dbDecoderStoreSignalComponent.setHorizontal(250);
        dbDecoderStoreSignalComponent.getTextLabel().setFont(FConstant.SEGOE_UI_15);
        Futilities.setOnTheBottom(dbWeightStoreSignalComponent, dbDecoderStoreSignalComponent, this.formComponentWidth, this.formComponentHeight);
        this.add(dbDecoderStoreSignalComponent);
        if (!FhFhsStatesModel.getInstance().isFH_FHS_connectionState()) {
            this.balanceStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
            this.serviceConnectionStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
            this.dbDecoderStoreSignalLabel.setIcon(Icons.getIcon("offLight.png"));
            this.dbWeightStoreSignalLabel.setIcon(Icons.getIcon("offLight.png"));
            this.validFrameStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
            this.receiveFrameStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
        } else {
            this.serviceConnectionStateLabel.setIcon(Icons.getIcon("chelta.png"));
            if (FhsStatesModel.getInstance().isBalanceConnectedState()) {
                this.balanceStateLabel.setIcon(Icons.getIcon("chelta.png"));
            } else {
                this.balanceStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
            }

            if (FhsStatesModel.getInstance().isdBDecoderStateStoreSignalState()) {
                this.dbDecoderStoreSignalLabel.setIcon(Icons.getIcon("onLight.png"));
            } else {
                this.dbDecoderStoreSignalLabel.setIcon(Icons.getIcon("offLight.png"));
            }
            if (FhsStatesModel.getInstance().isdBWeightStoreSignalState()) {
                this.dbWeightStoreSignalLabel.setIcon(Icons.getIcon("onLight.png"));
            } else {
                this.dbWeightStoreSignalLabel.setIcon(Icons.getIcon("offLight.png"));
            }
            if (FhsStatesModel.getInstance().isValidBalanceFrameState()) {
                this.validFrameStateLabel.setIcon(Icons.getIcon("chelta.png"));
            } else {
                this.validFrameStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
            }
            if (FhsStatesModel.getInstance().isReceiveBalanceFrameState()) {
                this.receiveFrameStateLabel.setIcon(Icons.getIcon("chelta.png"));
            } else {
                this.receiveFrameStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
            }
        }
    }

    private void initTimer() {
        this.reduceSizeTimer = new Timer(10, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSize().height == 0) {
                    reduceSizeTimer.stop();
                } else {
                    if (getSize().height >= 20) {
                        setSize(getSize().width, getSize().height - 20);
                        revalidate();
                        repaint();
                    } else {
                        setSize(getSize().width, 0);
                        revalidate();
                        repaint();
                        reduceSizeTimer.stop();
                    }
                }
            }
        });

        this.increaseSizeTimer = new Timer(10, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSize().height == DEFAULT_PANEL_HEIGHT) {
                    increaseSizeTimer.stop();
                } else {
                    if (getSize().height >= DEFAULT_PANEL_HEIGHT - 20) {
                        setSize(getSize().width, DEFAULT_PANEL_HEIGHT);
                        revalidate();
                        repaint();
                        increaseSizeTimer.stop();
                    } else {
                        setSize(getSize().width, getSize().height + 20);
                        revalidate();
                        repaint();
                    }
                }

            }
        });

    }

    public boolean isSizeChanging() {
        return increaseSizeTimer.isRunning() || reduceSizeTimer.isRunning();
    }

    public void reduce() {
        reduceSizeTimer.start();
    }

    public void devallop() {
        increaseSizeTimer.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        int notification = (int) arg;
        if (o instanceof FhsStatesModel) {
            switch (notification) {
                case (FhsStatesModel.BALANCE_CONNECTED_STATE):
                    if (FhsStatesModel.getInstance().isBalanceConnectedState()) {
                        this.balanceStateLabel.setIcon(Icons.getIcon("chelta.png"));
                    } else {
                        this.balanceStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
                    }
                    break;
                case (FhsStatesModel.DB_DECODER_STATE_STORE_SIGNAL_STATE):
                    if (FhsStatesModel.getInstance().isdBDecoderStateStoreSignalState()) {
                        this.dbDecoderStoreSignalLabel.setIcon(Icons.getIcon("onLight.png"));
                    } else {
                        this.dbDecoderStoreSignalLabel.setIcon(Icons.getIcon("offLight.png"));
                    }
                    break;
                case (FhsStatesModel.DB_WEIGHT_STORE_SIGNAL_STATE):
                    if (FhsStatesModel.getInstance().isdBWeightStoreSignalState()) {
                        this.dbWeightStoreSignalLabel.setIcon(Icons.getIcon("onLight.png"));
                    } else {
                        this.dbWeightStoreSignalLabel.setIcon(Icons.getIcon("offLight.png"));
                    }
                    break;
                case (FhsStatesModel.RECEIVE_BALANCE_FRAME_STATE):
                    if (FhsStatesModel.getInstance().isReceiveBalanceFrameState()) {
                        this.receiveFrameStateLabel.setIcon(Icons.getIcon("chelta.png"));
                    } else {
                        this.receiveFrameStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
                    }
                    break;
                case (FhsStatesModel.VALID_BALANCE_FRAME_STATE):
                    if (FhsStatesModel.getInstance().isValidBalanceFrameState()) {
                        this.validFrameStateLabel.setIcon(Icons.getIcon("chelta.png"));
                    } else {
                        this.validFrameStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
                    }
                    break;

            }
        }
        if (o instanceof FhFhsStatesModel) {
            switch (notification) {
                case (FhFhsStatesModel.FH_FHS_CONNECTION_STATE):
                    if (FhFhsStatesModel.getInstance().isFH_FHS_connectionState()) {
                        this.serviceConnectionStateLabel.setIcon(Icons.getIcon("chelta.png"));
                        try {
                            FhsStatesModel.getInstance().loadStatesFile();
                        } catch (IOException ex) {
                            Logger.getLogger(GeneralStatesPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(GeneralStatesPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        this.serviceConnectionStateLabel.setIcon(Icons.getIcon("redCrossIcon.png"));
                        FhsStatesModel.getInstance().setBalanceConnectedState(false);
                        FhsStatesModel.getInstance().setdBDecoderStateStoreSignalState(false);
                        FhsStatesModel.getInstance().setdBWeightStoreSignalState(false);
                        FhsStatesModel.getInstance().setdBWeightStoreSignalState(false);

                    }
                    break;
            }
        }
    }

}
