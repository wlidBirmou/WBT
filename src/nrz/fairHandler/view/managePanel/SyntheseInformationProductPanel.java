/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.managePanel;

import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.model.ProductModel;
import nrz.fairHandler.model.RealTimeModel;
import nrz.fairHandler.view.dialog.InformationArticleDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nrz.java.utility.ColorTense;
import nrz.java.utility.FConstant;
import nrz.java.view.component.FTable;

/**
 *
 * @author abderrahim
 */
public class SyntheseInformationProductPanel extends JPanel implements Observer {

    private final RealTimeModel uIModel;
    private final ProductModel productModel;
    private JLabel titleLabel;
    private FTable productTable;
    private ColorTense colorTense;

    public SyntheseInformationProductPanel(RealTimeModel uIModel, ProductModel productModel, String title, ColorTense colorTense) {
        this.uIModel = uIModel;
        this.productModel = productModel;
        this.titleLabel = new JLabel(title);
        this.colorTense = colorTense;

        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        this.uIModel.addObserver(this);
        this.productModel.addObserver(this);

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
        this.productTable = new FTable(colorTense) {

            @Override
            public List<Object> getColumnsContent(Object o) {
                Pruduct pruduct = (Pruduct) o;
                ArrayList row = new ArrayList();
                row.add(pruduct.getIdArticle());
                row.add(this.getRowCount() + 1);
                row.add(pruduct.getArticleCode());
                row.add(pruduct.getDesignation());
                row.add(pruduct.getUnite().getDesignation() + " (" + pruduct.getUnite().getAbrevation() + ")");
                row.add(pruduct.getCategorie());
                return row;
            }
        };

        this.productTable.addFColumn("N°", 60);
        this.productTable.addFColumn("CODE PRODUIT ", 400);
        this.productTable.addFColumn("DESIGNATION", 400);
        this.productTable.addFColumn("UNITE", 150);
        this.productTable.addFColumn("CATEGORIE ");
        this.productTable.getScrollPane().setBorder(BorderFactory.createLineBorder(colorTense, 2, false));
        this.productTable.addRows(this.productModel.initProductStep());
        this.add(this.productTable.getScrollPane(), BorderLayout.CENTER);
    }

    private void initEvents() {
        this.productTable.addEnterAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                InformationArticleDialog io = new InformationArticleDialog(colorTense, "Informations produits", productModel.findProduct((int) productTable.getSelectedRowID()));
            }
        });
    }

    public FTable getProductTable() {
        return productTable;
    }

    public void setProductTable(FTable productTable) {
        this.productTable = productTable;
    }

    @Override
    public void update(Observable o, Object arg) {
        int notification = (int) arg;
        if (o instanceof RealTimeModel) {
            switch (notification) {
                case (RealTimeModel.SHOW_WEIGHT_SECTION_NOTIFIER):
                    this.productTable.clear();
                    this.productTable.addRows(productModel.initProductStep());
                    break;
            }
        } else if (o instanceof ProductModel) {
            switch (notification) {
                case (ProductModel.CREATE_PRODUCT_NOTIFIER):
                    productTable.clear();
                    productTable.addRows(productModel.initProductStep());
                    break;
                case (ProductModel.EDITED_SEARCH_FILTER_NOTIFIER):
                    productTable.clear();
                    productTable.addRows(productModel.initProductStep());
                    break;
                case (ProductModel.EDIT_PRODUCT_NOTIFIER):
                    Pruduct pruduct = productModel.findProduct((Integer) productTable.getSelectedRowID());
                    productTable.replaceRow(pruduct, productTable.getSelectedRow());
                    break;
                case (ProductModel.REMOVE_PRODUCT_NOTIFIER):
                    productTable.removeRow(productTable.getSelectedRow());
                    break;
                    case(ProductModel.END_SCROLL_PRODUCT_NOTIFIER):
                        this.productTable.addRows(productModel.getProductStep());
                        break;
            }
        }
    }

}
