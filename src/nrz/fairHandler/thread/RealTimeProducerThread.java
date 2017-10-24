/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import nrz.bframe.frame.BFrame;
import nrz.fairHandler.model.RealTimeModel;
import nrz.fairHandlerStates.bilanciai.ev2000Frame.Ev2000Frame;
import nrz.fairHandlerStates.keys.FrameTockenKeys;
import nrz.fairHandlerStates.model.FhFhsStatesModel;
import nrz.fairHandlerStates.model.FhsStatesModel;
import nrz.fairHandlerStates.model.GeneralSittingsModel;
import org.joda.time.DateTime;

/**
 *
 * @author abderrahim
 */
public class RealTimeProducerThread implements Runnable {

    private final RealTimeModel realTimeModel;
    private final BlockingQueue<BFrame> realTimeBlockingQueue;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    ExecutorService executorService;

    public RealTimeProducerThread(RealTimeModel realTimeModel) {
        this.realTimeBlockingQueue = new ArrayBlockingQueue(100);
        this.realTimeModel = realTimeModel;
    }

    public void run() {
        Ev2000Frame ev2000Frame;
        this.executeRealTimeProducer();

        while (true) {
            if (!FhFhsStatesModel.getInstance().isFH_FHS_connectionState()) {
                try {
                    this.socket = new Socket();
                    this.socket.connect(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),
                            GeneralSittingsModel.getInstance().getLocalNetworkPort()));
                    this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
                    FhFhsStatesModel.getInstance().setFH_FHS_connectionState(true);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(RealTimeProducerThread.class.getName()).log(Level.SEVERE, null, ex);
                    FhFhsStatesModel.getInstance().setFH_FHS_connectionState(false);
                } catch (IOException ex) {
//                    Logger.getLogger(RealTimeProducerThread.class.getName()).log(Level.SEVERE, null, ex);
                    if (this.socket != null) {
                        try {
                            this.socket.close();
                        } catch (IOException ex1) {
                            Logger.getLogger(RealTimeProducerThread.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                    if (this.objectInputStream != null) {
                        try {
                            this.objectInputStream.close();
                        } catch (IOException ex1) {
                            Logger.getLogger(RealTimeProducerThread.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                    FhFhsStatesModel.getInstance().setFH_FHS_connectionState(false);
                }
            } else {
                System.out.println(FhFhsStatesModel.getInstance().isFH_FHS_connectionState());
                try {
                    ev2000Frame = (Ev2000Frame) this.objectInputStream.readObject();
                    System.out.println(ev2000Frame.getGrossWeight() + " " + ev2000Frame.getDateTocken(FrameTockenKeys.DATE_KEY).toString("dd-MM-yyyy" + " " + DateTime.now().toString("hh:mm:ss")));
                    this.realTimeBlockingQueue.put(ev2000Frame);
                } catch (IOException ex) {
                    Logger.getLogger(RealTimeProducerThread.class.getName()).log(Level.SEVERE, null, ex);
                    FhFhsStatesModel.getInstance().setFH_FHS_connectionState(false);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RealTimeProducerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RealTimeProducerThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private void executeRealTimeProducer() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Ev2000Frame ev2000Frame;
                while (true) {
                    try {
                        ev2000Frame = (Ev2000Frame) realTimeBlockingQueue.take();
                        realTimeModel.reloadRealTimeInformation(ev2000Frame);
                        realTimeModel.relaodRealTimeViewer(ev2000Frame);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RealTimeProducerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
    }
}
