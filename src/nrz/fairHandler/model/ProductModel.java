/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.model;

import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.jpaController.PruductJpaController;
import nrz.fairHandler.jpaController.exceptions.IllegalOrphanException;
import nrz.fairHandler.jpaController.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import nrz.java.utility.Futilities;

/**
 *
 * @author abderrahim
 */
public class ProductModel extends Observable {

    private final PruductJpaController articleJpaController = AbstractModel.getPruductJpaController();

    public final static int CREATE_PRODUCT_NOTIFIER = 1;
    public final static int EDIT_PRODUCT_NOTIFIER = 2;
    public final static int REMOVE_PRODUCT_NOTIFIER = 3;
    public final static int EDITED_SEARCH_FILTER_NOTIFIER = 4;
    public final static int EDITED_DATE_DEBUT_NOTIFIER = 5;
    public final static int EDITED_DATE_FIN_NOTIFIER = 6;
    public final static int END_SCROLL_PRODUCT_NOTIFIER = 7;

    private int productStep = 0;
    private String filter = "";
    private int selectedArticleId = -1;
    private Date debutDate;
    private Date finDate;

    public ProductModel() {

    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
        this.setChanged();
        this.notifyObservers(EDITED_SEARCH_FILTER_NOTIFIER);
    }

    public int getSelectedArticleId() {
        return selectedArticleId;
    }

    public void setSelectedArticleId(int selectedArticleId) {
        this.selectedArticleId = selectedArticleId;
    }

    public Date getDebutDate() {
        return debutDate;
    }

    public void setDebutDate(Date debutDate) {
        this.debutDate = debutDate;
        this.setChanged();
        this.notifyObservers(EDITED_DATE_DEBUT_NOTIFIER);
    }

    public Date getFinDate() {
        return finDate;
    }

    public void setFinDate(Date finDate) {
        this.finDate = finDate;
        this.setChanged();
        this.notifyObservers(EDITED_DATE_FIN_NOTIFIER);
    }

    public void endScrollWeight() {
        this.setChanged();
        this.notifyObservers(END_SCROLL_PRODUCT_NOTIFIER);
    }

    public void createProduct(Pruduct pruduct) {
        articleJpaController.create(pruduct);
        this.setChanged();
        this.notifyObservers(CREATE_PRODUCT_NOTIFIER);
    }

    public void editProduct(Pruduct pruduct) throws IllegalOrphanException, NonexistentEntityException, Exception {
        articleJpaController.edit(pruduct);
        this.setChanged();
        this.notifyObservers(EDIT_PRODUCT_NOTIFIER);
    }

    public void destroyProduct(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        articleJpaController.destroy(id);
        this.setChanged();
        this.notifyObservers(REMOVE_PRODUCT_NOTIFIER);
    }

    public Pruduct findProduct(Integer id) {
        return articleJpaController.findPruduct(id);
    }

    public List initProductStep() {
        this.productStep = 0;
        List list = articleJpaController.getProductStep(productStep, filter, Futilities.formatSqlDate(debutDate), Futilities.formatSqlDate(finDate));
        this.incrementStep();
        return list;

    }

    public List getProductStep() {
        List list = articleJpaController.getProductStep(productStep, filter, Futilities.formatSqlDate(debutDate), Futilities.formatSqlDate(finDate));
        this.incrementStep();
        return list;

    }

    private void incrementStep() {
        this.productStep = this.productStep + 50;
    }

    public boolean isProductByCodeExist(String productCode) {
        return articleJpaController.isProductByCodeExist(productCode);
    }
    
    

}
