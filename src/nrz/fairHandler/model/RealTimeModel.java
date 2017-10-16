/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.model;

import java.util.HashMap;
import java.util.Observable;
import nrz.bframe.frameTocken.defaultFrameTocken.BitSetFrameTocken;
import nrz.fairHandler.utility.keys.RealTimeKeys;
import nrz.fairHandlerStates.bilanciai.ev2000Frame.Ev2000Frame;
import nrz.fairHandlerStates.keys.FrameTockenKeys;

/**
 *
 * @author abderrahim
 */
public class RealTimeModel extends Observable {

    public final static int SHOW_WEIGHT_SECTION_NOTIFIER = 1;
    public final static int SHOW_PRODUCT_SECTION_NOTIFIER = 2;
    public final static int UPDATE_REAL_TIME_BOARD_NOTIFIER = 4;
    public final static int UPDATE_WEIGHT_NOTIFIER = 5;
    
    private int showedSection = 1;
    private boolean printFlag=true;
    private static RealTimeModel model;

    private Double realTimeWeight = 0.;
    private HashMap<String, String> realTimeInformationHashMap;

    private boolean gUISynchronizer=false;
    
    private RealTimeModel() {
        this.realTimeInformationHashMap = new HashMap<>();
        this.realTimeInformationHashMap.put(RealTimeKeys.DATE_HOUR_KEY, "");
        this.realTimeInformationHashMap.put(RealTimeKeys.GENERIC_CODE, "");
        this.realTimeInformationHashMap.put(RealTimeKeys.RCD_CODE, "");
        this.realTimeInformationHashMap.put(RealTimeKeys.PRODUCT_CODE, "");
        this.realTimeInformationHashMap.put(RealTimeKeys.PROGRESSIVE_CODE, "");
        this.realTimeInformationHashMap.put(RealTimeKeys.PRINT_STATE, "");
        this.realTimeInformationHashMap.put(RealTimeKeys.CENTER_OF_ZERO_STATE, "");
        this.realTimeInformationHashMap.put(RealTimeKeys.STABLE_WEIGHT, "");
    }

    public static RealTimeModel getInstance() {
        if (model == null) {
            model = new RealTimeModel();
        }
        return model;
    }

    public String getRealTimeInformation(Object key) {
        return realTimeInformationHashMap.get(key);
    }

    public synchronized void relaodRealTimeViewer(Ev2000Frame ev2000Frame) {
        this.realTimeWeight = ev2000Frame.getGrossWeight();
        this.reloadRealTimeInformation(ev2000Frame);
        this.setChanged();
        this.notifyObservers(UPDATE_REAL_TIME_BOARD_NOTIFIER);
    }

    public synchronized void reloadRealTimeInformation(Ev2000Frame ev2000Frame) {
        this.realTimeInformationHashMap.get("Date (Heure) :");
        this.realTimeInformationHashMap.replace(RealTimeKeys.DATE_HOUR_KEY, ev2000Frame.getDateTocken(FrameTockenKeys.DATE_KEY).toString("dd/MM/YYYY") + "(" + ev2000Frame.getTimeTocken(FrameTockenKeys.TIME_KEY).toString("hh:mm:ss") + ")");
        this.realTimeInformationHashMap.replace(RealTimeKeys.GENERIC_CODE, ev2000Frame.getStringTocken(FrameTockenKeys.GENERIC_CODE_KEY));
        this.realTimeInformationHashMap.replace(RealTimeKeys.RCD_CODE, ev2000Frame.getStringTocken(FrameTockenKeys.RCD_CODE_KEY));
        this.realTimeInformationHashMap.replace(RealTimeKeys.PRODUCT_CODE, ev2000Frame.getStringTocken(FrameTockenKeys.PRODUCT_CODE_KEY));
        this.realTimeInformationHashMap.replace(RealTimeKeys.PROGRESSIVE_CODE, ev2000Frame.getStringTocken(FrameTockenKeys.PROGRESSIF_CODE_KEY));

        BitSetFrameTocken reducedStateBitSetFrameTocken = (BitSetFrameTocken) ev2000Frame.getTocken(FrameTockenKeys.REDUCED_FRAME_STATE_KEY);
        this.realTimeInformationHashMap.replace(RealTimeKeys.PRINT_STATE, this.getStringFromBoolean(reducedStateBitSetFrameTocken.getBitset(FrameTockenKeys.PRINT_REQUEST_KEY)));
        if(reducedStateBitSetFrameTocken.getBitset(FrameTockenKeys.PRINT_REQUEST_KEY)){
        if(printFlag){
            this.setChanged();
            this.notifyObservers(UPDATE_WEIGHT_NOTIFIER);
            this.printFlag=false;
        }
        }else{
            this.printFlag=true;
        }
        this.realTimeInformationHashMap.replace(RealTimeKeys.CENTER_OF_ZERO_STATE, this.getStringFromBoolean(reducedStateBitSetFrameTocken.getBitset(FrameTockenKeys.CENTER_OF_ZERO_KEY)));
        this.realTimeInformationHashMap.replace(RealTimeKeys.STABLE_WEIGHT, this.getStringFromBoolean(reducedStateBitSetFrameTocken.getBitset(FrameTockenKeys.STABLE_WEIGHT_KEY)));

    }

    public Double getRealTimeWeight() {
        return realTimeWeight;
    }

    public int getShowedSection() {
        return showedSection;
    }

    public void setShowedSection(int showedSection) {
        this.showedSection = showedSection;
        this.setChanged();
        this.notifyObservers(this.getShowedSection());
    }

    public String getStringFromBoolean(boolean bool) {
        if (bool) {
            return "Oui";
        } else {
            return "Non";
        }
    }

    public synchronized boolean isgUISynchronizer() {
        return gUISynchronizer;
    }

    public void setgUISynchronizer(boolean gUISynchronizer) {
        this.gUISynchronizer = gUISynchronizer;
    }
    
    

}
