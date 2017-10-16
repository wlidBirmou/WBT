/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view.managePanel;

import nrz.fairHandler.view.managePanel.realTimePanel.RealTimeViewerPanel;
import nrz.fairHandler.Controller.generalController.DebutDateController;
import nrz.fairHandler.Controller.generalController.FinDateController;
import nrz.fairHandler.Controller.generalController.SearchFilterController;
import nrz.fairHandler.Controller.generalController.updateShowedSectionController;
import nrz.fairHandler.Controller.productController.RemoveProductController;
import nrz.fairHandler.jpa.Weight;
import nrz.fairHandler.model.ProductModel;
import nrz.fairHandler.model.WeightModel;
import nrz.fairHandler.view.dialog.FormArticleDialog;
import nrz.fairHandler.view.dialog.InformationArticleDialog;
import nrz.fairHandler.view.dialog.InformationIncompleteWeightDialog;
import nrz.fairHandler.view.dialog.InformationsCompleteWeightDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import nrz.fairHandler.model.RealTimeModel;
import nrz.java.utility.ColorTense;
import nrz.java.utility.Icons;
import nrz.java.view.component.FButton;
import nrz.java.view.component.FMenu;
import nrz.java.view.component.FOptionPane;
import nrz.java.view.component.FPeriodChooser;
import nrz.java.view.componentInterface.FAdjacentToolTipable;
import nrz.fairHandler.view.MainFrame;
import nrz.fairHandler.view.dialog.InformationPredeterminedTareWeightDialog;
import nrz.fairHandler.view.dialog.WeightReportDialog;
import nrz.fairHandler.view.managePanel.realTimePanel.GeneralStatesPanel;
import nrz.fairHandlerStates.keys.FrameTockenKeys;
import nrz.java.utility.FConstant;
import nrz.java.utility.Futilities;

/**
 *
 * @author abderrahim
 */
public class MainStructurePanel extends JPanel implements Observer {
    
    private final WeightModel weightModel = new WeightModel();
    private final RealTimeModel realTimeModel;
    private final ProductModel productModel = new ProductModel();
    
    private FMenu horizontalMenu;
    private JSeparator horizontalSeparator;
    
    private FPeriodChooser periodChooser;
    private FButton verticalWeightButton;
    private FButton consultWeightButton;
    private FButton printWeightButton;
    private JPopupMenu printWeightPopupMenu;
    private JMenuItem printOneWeightMenuItem;
    private JMenuItem printWeightsArrayMenuItem;
    
    private FButton verticalProductButton;
    private FButton addProductButton;
    private FButton editProductButton;
    private FButton removeProductButton;
    private FButton consultProductButton;
    private FButton printProductButton;
    
    private FButton generalStatesButton;
    private GeneralStatesPanel generalStatesPanel;
    
    private FMenu verticalMenu;
    private JSeparator verticalSeparator;
    private RealTimeViewerPanel realTimeViewerPanel;
    private JPanel bodyPanel;
    private final SyntheseInformationWeightPanel syntheseInformationWeightPanel;
    private final SyntheseInformationProductPanel syntheseInformationProductPanel;
    private final ColorTense colorTense;
    
    public MainStructurePanel(ColorTense colorTense, RealTimeModel realTimeModel) {
        this.colorTense = colorTense;
        this.realTimeModel = realTimeModel;
        this.syntheseInformationWeightPanel = new SyntheseInformationWeightPanel(this.realTimeModel, weightModel, "ENREGISTREMENT DES POIDS", colorTense);
        this.syntheseInformationProductPanel = new SyntheseInformationProductPanel(this.realTimeModel, productModel, "TABLEAU DES PRODUITS", colorTense);
        this.setOpaque(false);
        
        this.realTimeModel.addObserver(this);
        
        this.build();
        this.setVisible(true);
        
    }
    
    private void build() {
        this.initHorizontalMenu();
        this.initVerticalRightMenu();
        this.initRealTimeViewerPanel();
        this.initBodyPanel();
        this.initPalettesLayersComponents();
        this.initEvents();
        this.initLayouts();
    }
    
    public WeightModel getWeightModel() {
        return weightModel;
    }
    
    public RealTimeModel getuIModel() {
        return realTimeModel;
    }
    
    public ProductModel getArticleModel() {
        return productModel;
    }
    
    private void initHorizontalMenu() {
        this.horizontalMenu = new FMenu(colorTense.WhityColor(), 90, true, FMenu.HORIZONTAL_FMENU);
        
        this.consultWeightButton = new FButton("Consulter", Icons.getIcon("ConsultIcon.png"));
        this.consultWeightButton.setVertical();
        this.consultWeightButton.setPreferredSize(new Dimension(80, 0));
        
        this.printWeightButton = new FButton("Imprimer", Icons.getIcon("printIcon.png"));
        this.printWeightButton.setVertical();
        this.printWeightButton.setPreferredSize(new Dimension(80, 0));
        
        this.printWeightPopupMenu = new JPopupMenu();
        this.printWeightPopupMenu.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        this.printWeightPopupMenu.setPreferredSize(new Dimension(250, 80));
        
        this.printOneWeightMenuItem = new JMenuItem();
        this.printOneWeightMenuItem.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        this.initPrintWeightPopupItem(this.printOneWeightMenuItem, "Imprimer un bon de pesée", null);
        
        this.printWeightsArrayMenuItem = new JMenuItem();
        this.printWeightsArrayMenuItem.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        this.initPrintWeightPopupItem(this.printWeightsArrayMenuItem, "Imprimer un tableau de pesée", null);
        
        this.addProductButton = new FButton("Ajouter", Icons.getIcon("addArticleIcon.png"));
        this.addProductButton.setVertical();
        this.addProductButton.setPreferredSize(new Dimension(80, 0));
        
        this.editProductButton = new FButton("Modifier", Icons.getIcon("editArticleIcon.png"));
        this.editProductButton.setVertical();
        this.editProductButton.setPreferredSize(new Dimension(80, 0));
        
        this.removeProductButton = new FButton("Supprimer", Icons.getIcon("removeArticleIcon.png"));
        this.removeProductButton.setVertical();
        this.removeProductButton.setPreferredSize(new Dimension(80, 0));
        
        this.consultProductButton = new FButton("Consulter", Icons.getIcon("ConsultIcon.png"));
        this.consultProductButton.setVertical();
        this.consultProductButton.setPreferredSize(new Dimension(80, 0));
        
        this.printProductButton = new FButton("Imprimer", Icons.getIcon("printIcon.png"));
        this.printProductButton.setVertical();
        this.printProductButton.setPreferredSize(new Dimension(80, 0));
        
        this.periodChooser = new FPeriodChooser(colorTense);
        
        this.horizontalSeparator = new JSeparator();
        this.horizontalSeparator.setOrientation(JSeparator.HORIZONTAL);
        this.horizontalSeparator.setForeground(this.colorTense);
        this.horizontalSeparator.setBackground(this.colorTense);
        this.horizontalSeparator.setOpaque(true);
        
        this.horizontalMenu.addFButton(consultWeightButton);
        this.horizontalMenu.addFButton(printWeightButton);
        this.horizontalMenu.addSeparator();
        this.horizontalMenu.addSeparator();
        this.horizontalMenu.addFPeriode(periodChooser);
        
    }
    
    private void initVerticalRightMenu() {
        this.verticalMenu = new FMenu(this.colorTense.WhityColor(), 40, false, FMenu.VERTICAL_FMENU);
        this.verticalMenu.setGap(0);
        this.verticalSeparator = new JSeparator();
        this.verticalSeparator.setOrientation(JSeparator.VERTICAL);
        this.verticalSeparator.setForeground(this.colorTense);
        this.verticalSeparator.setBackground(this.colorTense);
        this.verticalSeparator.setOpaque(true);
        
        this.verticalWeightButton = new FButton("", Icons.getIcon("balanceIconMiniature.png"));
        this.verticalWeightButton.setVertical();
        this.verticalWeightButton.setBorder(BorderFactory.createLineBorder(colorTense, 3));
        this.verticalWeightButton.addFAdjacentToolTip("Onglet des pesées", FAdjacentToolTipable.LEFT_OF_CONTAINER, colorTense);
        this.verticalMenu.addFButton(verticalWeightButton);
        this.verticalProductButton = new FButton("", Icons.getIcon("articleIconMiniature.png"));
        this.verticalProductButton.setVertical();
        this.verticalProductButton.addFAdjacentToolTip("Onglet des produits", FAdjacentToolTipable.LEFT_OF_CONTAINER, colorTense);
        this.verticalMenu.addFButton(verticalProductButton);
    }
    
    private void initRealTimeViewerPanel() {
        this.realTimeViewerPanel = new RealTimeViewerPanel(this.realTimeModel, colorTense);
    }
    
    private void initBodyPanel() {
        this.bodyPanel = new JPanel(new BorderLayout());
        this.bodyPanel.add(this.syntheseInformationWeightPanel);
        this.bodyPanel.setOpaque(false);
    }
    
    private void initPalettesLayersComponents() {
        this.generalStatesButton = new FButton("", Icons.getIcon("dropDown.png"), this.colorTense);
        this.generalStatesButton.setSize(320, 20);
        this.generalStatesButton.setHorizontalAlignment(FButton.CENTER);
        this.generalStatesButton.setVerticalAlignment(FButton.CENTER);
        this.generalStatesButton.setBorder(BorderFactory.createLineBorder(FConstant.TABLEAU_DE_BORD_COLOR, 1, true));
        MainFrame.getInstance().getLayeredPane().add(generalStatesButton, JLayeredPane.PALETTE_LAYER);
        
        this.generalStatesPanel = new GeneralStatesPanel();
        this.generalStatesPanel.setSize(320, 0);
        MainFrame.getInstance().getLayeredPane().add(generalStatesPanel, JLayeredPane.PALETTE_LAYER);
        
    }
    
    private void initEvents() {
        this.addProductButton.addActionListener(new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormArticleDialog(productModel, colorTense, "Ajout d'un article");
            }
        });
        
        this.editProductButton.addActionListener(new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (syntheseInformationProductPanel.getProductTable().getSelectedRow() != -1) {
                    new FormArticleDialog(productModel, colorTense, "Ajout d'un article", productModel.findProduct((Integer) syntheseInformationProductPanel.getProductTable().getSelectedRowID()));
                } else {
                    FOptionPane.showInformationMessage(new ColorTense(Color.BLACK), "Aucun produit selectionné", "Veuilliez au préalable selectionner une ligne dans le tableau des produits");
                }
            }
        });
        
        this.printWeightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printWeightPopupMenu.show(printWeightButton, 0, printWeightButton.getHeight());
            }
        });
        this.removeProductButton.addActionListener(new RemoveProductController(productModel, syntheseInformationProductPanel.getProductTable()));
        
        this.consultProductButton.addActionListener(new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (syntheseInformationProductPanel.getProductTable().getSelectedRow() == -1) {
                    FOptionPane.showInformationMessage(new ColorTense(Color.BLACK), "Informations", "Aucune Ligne du tableau n'est selectionnée,"
                            + " veuillez au préalable selectionner une ligne pour pouvoir la consulter");
                } else {
                    new InformationArticleDialog(colorTense, "Informations produits", productModel.findProduct((int) syntheseInformationProductPanel.getProductTable().getSelectedRowID()));
                }
            }
        });
        
        this.printOneWeightMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainStructurePanel.this.syntheseInformationWeightPanel.getWeightTable().getSelectedRowID() != null) {
                    new WeightReportDialog(colorTense, "rapport d'impression", weightModel, WeightReportDialog.ReportType.ONE_WEIGHT_FORM);
                } else {
                    FOptionPane.showInformationMessage(new ColorTense(Color.BLACK), "Informations", "Aucune Ligne du tableau n'est selectionnée,"
                            + " veuillez au préalable selectionner une ligne pour pouvoir la consulter");
                    
                }
            }
        });
        this.printWeightsArrayMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new WeightReportDialog(colorTense, "rapport d'impression", weightModel, WeightReportDialog.ReportType.WEIGHTS_ARRAY_FORM);
            }
        });
        this.verticalWeightButton.addActionListener(new updateShowedSectionController(realTimeModel, RealTimeModel.SHOW_WEIGHT_SECTION_NOTIFIER));
        this.verticalProductButton.addActionListener(new updateShowedSectionController(realTimeModel, RealTimeModel.SHOW_PRODUCT_SECTION_NOTIFIER));
        
        this.horizontalMenu.getSearchField().getDocument().addDocumentListener(new SearchFilterController(realTimeModel, productModel, weightModel));
        this.periodChooser.addDebutPropertyChangeListener(new DebutDateController(realTimeModel, weightModel, productModel));
        this.periodChooser.addFinPropertyChangeListener(new FinDateController(realTimeModel, weightModel, productModel));
        this.syntheseInformationWeightPanel.getWeightTable().addEnterAction(new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (syntheseInformationWeightPanel.getWeightTable().getSelectedRow() != -1) {
                    Weight weight = weightModel.findWeight((Integer) syntheseInformationWeightPanel.getWeightTable().getSelectedRowID());
                    if (weight.getType().equals(FrameTockenKeys.POSITIVE_TYPE_KEY) || weight.getType().equals(FrameTockenKeys.NEGATIVE_TYPE_KEY)
                            || weight.getType().equals(FrameTockenKeys.NUL_TYPE_KEY)) {
                        InformationsCompleteWeightDialog i = new InformationsCompleteWeightDialog(colorTense, "Informations sur la pesée", weight);
                    } else if (weight.getType().equals(FrameTockenKeys.PREDETERMINED_TARE_KEY)) {
                        InformationPredeterminedTareWeightDialog i = new InformationPredeterminedTareWeightDialog(colorTense, "Informations sur la pesée", weight);
                    } else {
                        InformationIncompleteWeightDialog i = new InformationIncompleteWeightDialog(colorTense, "Informations sur la pesée", weight);
                    }
                }
            }
        });
        
        this.consultWeightButton.addActionListener(new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (syntheseInformationWeightPanel.getWeightTable().getSelectedRow() != -1) {
                    Weight weight = weightModel.findWeight((Integer) syntheseInformationWeightPanel.getWeightTable().getSelectedRowID());
                    if (weight.getType().equals(FrameTockenKeys.POSITIVE_TYPE_KEY) || weight.getType().equals(FrameTockenKeys.NEGATIVE_TYPE_KEY) || weight.getType().equals(FrameTockenKeys.NUL_TYPE_KEY)) {
                        InformationsCompleteWeightDialog i = new InformationsCompleteWeightDialog(colorTense, "Informations sur la pesée", weight);
                    }
                    if (weight.getType().equals(FrameTockenKeys.PREDETERMINED_TARE_KEY)) {
                        InformationPredeterminedTareWeightDialog i = new InformationPredeterminedTareWeightDialog(colorTense, "Informations sur la pesée", weight);
                    } else {
                        InformationIncompleteWeightDialog i = new InformationIncompleteWeightDialog(colorTense, "Informations sur la pesée", weight);
                    }
                    
                } else {
                    FOptionPane.showErrorMessage(new ColorTense(Color.BLACK), "Aucune ligne selectionnée", "Aucune ligne du tableau n'est selectionnée, veuillez au préalable selectionner une ligne.");
                }
            }
        });
        
        this.generalStatesButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!generalStatesPanel.isSizeChanging()) {
                    if (generalStatesPanel.getSize().height == 0) {
                        generalStatesButton.setIcon(Icons.getIcon("dropUp.png"));
                        generalStatesPanel.devallop();
                    } else {
                        generalStatesButton.setIcon(Icons.getIcon("dropDown.png"));
                        generalStatesPanel.reduce();
                    }
                }
            }
        });
    }
    
    private void initLayouts() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        
        GroupLayout.SequentialGroup smallSequentialGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup smallParallelGroup = layout.createParallelGroup();
        
        GroupLayout.SequentialGroup bigSequentialGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup bigParallelGroup = layout.createParallelGroup();
        
        GroupLayout.SequentialGroup veryBigSequentialGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup veryBigParallelGroup = layout.createParallelGroup();
        
        smallSequentialGroup.addGap(10);
        smallSequentialGroup.addComponent(realTimeViewerPanel, 0, 0, 130);
        smallSequentialGroup.addComponent(bodyPanel, 0, 0, 2000);
        smallSequentialGroup.addGap(10);
        
        smallParallelGroup.addComponent(realTimeViewerPanel, 0, 0, 2000);
        smallParallelGroup.addComponent(bodyPanel, 0, 0, 2000);
        
        bigSequentialGroup.addComponent(verticalMenu, 40, 40, 40);
        bigSequentialGroup.addComponent(verticalSeparator, 5, 5, 5);
        bigSequentialGroup.addGap(10);
        bigSequentialGroup.addGroup(smallParallelGroup);
        bigSequentialGroup.addGap(10);
        
        bigParallelGroup.addComponent(verticalMenu, 0, 0, 2000);
        bigParallelGroup.addComponent(verticalSeparator, 0, 0, 2000);
        bigParallelGroup.addGroup(smallSequentialGroup);
        
        veryBigSequentialGroup.addComponent(horizontalMenu, 90, 90, 90);
        veryBigSequentialGroup.addComponent(horizontalSeparator, 5, 5, 5);
        veryBigSequentialGroup.addGroup(bigParallelGroup);
        
        veryBigParallelGroup.addComponent(horizontalMenu,
                0, 0, 2000);
        veryBigParallelGroup.addComponent(horizontalSeparator, 0, 0, 2000);
        veryBigParallelGroup.addGroup(bigSequentialGroup);
        
        layout.setHorizontalGroup(veryBigParallelGroup);
        layout.setVerticalGroup(veryBigSequentialGroup);
        
    }
    
    private void updateSection() {
        
        this.horizontalMenu.clearMenuItems();
        switch (realTimeModel.getShowedSection()) {
            case (RealTimeModel.SHOW_WEIGHT_SECTION_NOTIFIER):
                this.horizontalMenu.addFButton(consultWeightButton);
                this.horizontalMenu.addFButton(printWeightButton);
                this.horizontalMenu.addSeparator();
                this.horizontalMenu.addSeparator();
                this.horizontalMenu.addFPeriode(periodChooser);
                this.verticalWeightButton.setBorder(BorderFactory.createLineBorder(colorTense, 3));
                this.verticalProductButton.setBorder(null);
                this.updateBodyPanel(this.syntheseInformationWeightPanel);
                break;
            case (RealTimeModel.SHOW_PRODUCT_SECTION_NOTIFIER):
                this.horizontalMenu.addFButton(this.addProductButton);
                this.horizontalMenu.addFButton(this.editProductButton);
                this.horizontalMenu.addFButton(this.removeProductButton);
                this.horizontalMenu.addFButton(this.consultProductButton);
                this.horizontalMenu.addFButton(this.printProductButton);
                this.verticalProductButton.setBorder(BorderFactory.createLineBorder(colorTense, 3));
                this.verticalWeightButton.setBorder(null);
                this.updateBodyPanel(this.syntheseInformationProductPanel);
                break;
        }
        this.horizontalMenu.getSearchField().setText("");
        this.periodChooser.clearChoosedPeriod();
        
    }
    
    public void updateBodyPanel(JPanel panel) {
        this.bodyPanel.removeAll();
        this.bodyPanel.add(panel, BorderLayout.CENTER);
        this.bodyPanel.revalidate();
        this.bodyPanel.repaint();
    }
    
    private void initPrintWeightPopupItem(JMenuItem item, String string, Icon icon) {
        item.setText(string);
        if (icon != null) {
            item.setIcon(icon);
        }
        item.setFont(FConstant.SEGOE_UI_13);
        item.setOpaque(true);
        item.setBackground(Color.WHITE);
        this.printWeightPopupMenu.add(item);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        Point location = new Point(realTimeViewerPanel.getLocationOnScreen().x - MainFrame.getInstance().getLayeredPane().getLocationOnScreen().x, realTimeViewerPanel.getLocationOnScreen().y - MainFrame.getInstance().getLayeredPane().getLocationOnScreen().y);
        generalStatesButton.setLocation(location.x + realTimeViewerPanel.getWidth() - 340, location.y + realTimeViewerPanel.getHeight());
        
        Futilities.setOnTheBottom(generalStatesButton, generalStatesPanel, generalStatesPanel.getWidth(), generalStatesPanel.getHeight(), 0);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        int notification = (int) arg;
        if (o instanceof RealTimeModel) {
            switch (notification) {
                case (RealTimeModel.SHOW_WEIGHT_SECTION_NOTIFIER):
                    this.updateSection();
                    break;
                case (RealTimeModel.SHOW_PRODUCT_SECTION_NOTIFIER):
                    this.updateSection();
                    break;
            }
        }
    }
    
}
