/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.model;

import nrz.fairHandler.jpa.Unit;
import nrz.fairHandler.jpa.Weight;
import nrz.fairHandler.jpaController.UnitJpaController;
import nrz.fairHandler.jpaController.WeightJpaController;
import nrz.fairHandler.jpaController.exceptions.IllegalOrphanException;
import nrz.fairHandler.jpaController.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import nrz.java.utility.Futilities;
import nrz.bframe.frameTocken.defaultFrameTocken.BitSetFrameTocken;
import static nrz.fairHandler.model.RealTimeModel.UPDATE_WEIGHT_NOTIFIER;
import nrz.fairHandlerStates.bilanciai.ev2000Frame.Ev2000Frame;
import nrz.fairHandlerStates.keys.FrameTockenKeys;

/**
 *
 * @author abderrahim
 */
public class WeightModel extends Observable {

    private final UnitJpaController unitJpaController = AbstractModel.getUnitJpaController();
    private final WeightJpaController weightJpaController = AbstractModel.getWeightJpaController();

    public final static int EDITED_SEARCH_FILTER_NOTIFIER = 10;
    public final static int EDITED_DATE_DEBUT_NOTIFIER = 11;
    public final static int EDITED_DATE_FIN_NOTIFIER = 12;
    public final static int NEW_SELECTED_WEIGHT = 13;

    private int weightStep = 0;
    private String filter = "";

    private Date beginDate;
    private Date endDate;
    private int selectedWeightID=-1;
    
    private boolean updateWeightFlag = true;

    public WeightModel() {
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
        this.setChanged();
        this.notifyObservers(EDITED_SEARCH_FILTER_NOTIFIER);
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date debutDate) {
        this.beginDate = debutDate;
        this.setChanged();
        this.notifyObservers(EDITED_DATE_DEBUT_NOTIFIER);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date finDate) {
        this.endDate = finDate;
        this.setChanged();
        this.notifyObservers(EDITED_DATE_FIN_NOTIFIER);
    }

    public void createWeight(Weight weight) {
        weightJpaController.create(weight);
    }

    public void editWeight(Weight weight) throws NonexistentEntityException, Exception {
        weightJpaController.edit(weight);
    }

    public void destroyWeight(Integer id) throws NonexistentEntityException {
        weightJpaController.destroy(id);
    }

    public Weight findWeight(Integer id) {
        return weightJpaController.findWeight(id);
    }

    public void createUnit(Unit unit) {
        unitJpaController.create(unit);
    }

    public void editUnit(Unit unit) throws IllegalOrphanException, NonexistentEntityException, Exception {
        unitJpaController.edit(unit);
    }

    public void destroyUnit(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        unitJpaController.destroy(id);
    }

    public Unit findUnit(Integer id) {
        return unitJpaController.findUnit(id);
    }

    public List initWeightStep() {
        this.weightStep = 0;
        return this.getWeightStep();
    }

    public List getWeightStep() {
        List list = weightJpaController.getWeightStep(weightStep, filter, Futilities.formatSqlDate(beginDate), Futilities.formatSqlDate(endDate));
        this.incrementStep();
        return list;
    }

    public List getWeights() {
        return weightJpaController.getWeights(filter, Futilities.formatSqlDate(beginDate), Futilities.formatSqlDate(endDate));
    }

    public List<Unit> getReferencesUnites() {
        return unitJpaController.getReferencesUnites();
    }

    private void incrementStep() {
        this.weightStep = this.weightStep + 50;
    }

    private synchronized void updateWeightTable(Ev2000Frame ev2000Frame) {
        BitSetFrameTocken reducedStateBitSetFrameTocken = (BitSetFrameTocken) ev2000Frame.getTocken(FrameTockenKeys.REDUCED_FRAME_STATE_KEY);
        if (reducedStateBitSetFrameTocken.getBitset(FrameTockenKeys.PRINT_REQUEST_KEY)) {
            if (this.updateWeightFlag) {
                this.setChanged();
                this.notifyObservers(UPDATE_WEIGHT_NOTIFIER);
                this.updateWeightFlag = false;
            }
        } else {
            this.updateWeightFlag = true;
        }
    }

    public Weight getSelectedWeight() {
        return this.weightJpaController.getWeight(this.selectedWeightID);
    }

    public void setSelectedWeightId(int selectedWeightID) {
        this.selectedWeightID = selectedWeightID;
        this.setChanged();
        this.notifyObservers(NEW_SELECTED_WEIGHT);
    }

    
    
    public boolean isUnitByAbreviationExist(String abreviation) {
        return unitJpaController.isUnitByAbreviationExist(abreviation);
    }

    public Unit getUnitByAbreviation(String abreviation) {
        return unitJpaController.getUnitByAbreviation(abreviation);
    }
    
    
}
