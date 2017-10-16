/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler;

import nrz.fairHandler.model.AbstractModel;
import nrz.fairHandler.model.RealTimeModel;
import nrz.fairHandler.utility.FairHandlerUtility;
import nrz.fairHandler.view.MainFrame;
import nrz.fairHandler.view.managePanel.MainStructurePanel;
import nrz.java.utility.ColorTense;
import nrz.java.utility.FConstant;
import nrz.java.utility.Icons;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import nrz.fairHandler.files.filesWatcher.ExecutionDirectoryWatcher;
import nrz.fairHandler.files.filesWatcher.SittingFilesDirectoryWatcher;
import nrz.fairHandler.jpa.Pruduct;
import nrz.fairHandler.jpa.Unit;
import nrz.fairHandler.model.ProductModel;
import nrz.fairHandler.model.WeightModel;
import nrz.fairHandler.thread.RealTimeProducerThread;
import nrz.fairHandlerStates.files.path.SoftDirectories;
import nrz.fairHandlerStates.model.FhStatesModel;
import nrz.fairHandlerStates.model.FhsStatesModel;
import nrz.fairHandlerStates.model.GeneralSittingsModel;
import nrz.java.synth.FlatSynthStyleFactory;
import org.jdom2.JDOMException;
import org.joda.time.DateTime;

/**
 *
 * @author abderrahim
 */
public class FairHandler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException, FontFormatException {

        RealTimeModel realTimeModel = RealTimeModel.getInstance();

        initDirectories();
        AbstractModel.initEntityManager();

        initStatesAndConfiguration();
        checkDBInitialTuple();
        initWatchers();
        initGUI(realTimeModel);
        initThread(realTimeModel);
    }

    private static void initWatchers() {
        
        
        try {
            ExecutionDirectoryWatcher executionDirectoryWatcher = new ExecutionDirectoryWatcher();
            Thread executionDirectoryThread = new Thread(executionDirectoryWatcher);
            executionDirectoryThread.start();
        } catch (IOException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            SittingFilesDirectoryWatcher sittingFilesDirectoryWatcher = new SittingFilesDirectoryWatcher();
            Thread sittingThread = new Thread(sittingFilesDirectoryWatcher);
            sittingThread.start();
        } catch (IOException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void initDirectories() {

        for (SoftDirectories directories : SoftDirectories.values()) {
            Files.exists(directories.getValue());
            if (!Files.exists(directories.getValue())) {
                try {
                    Files.createDirectory(directories.getValue());
                } catch (IOException ex) {
                    Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void initStatesAndConfiguration() {

        try {
            FhStatesModel.getInstance().setActif();
            FhStatesModel.getInstance().setOnLaunch();
        } catch (IOException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FhsStatesModel.getInstance().loadStatesFile();
        } catch (IOException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            GeneralSittingsModel.getInstance().loadGeneralSittingDocument();
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
            try {
                GeneralSittingsModel.getInstance().handleCorruptedFile();
            } catch (JDOMException | IOException ex1) {
                Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private static void initGUI(final RealTimeModel realTimeModel) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FairHandlerUtility.initializeAllFonts();
        } catch (FontFormatException ex) {
            Logger.getLogger(FairHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        Icons.setRelativeClass(FairHandler.class);
        Icons.setIconsRepositoryPath("icons/");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = MainFrame.getInstance();
                mainFrame.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                        Runtime.getRuntime().exit(0);

                    }
                });
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image softIcon = toolkit.getImage("C:\\Users\\abderrahim\\Dropbox\\fairHandler\\src\\fairHandlerPackage\\icons\\fairHandlerIcon.png");
                mainFrame.setIconImage(softIcon);
                MainStructurePanel mainStructurePanel = new MainStructurePanel((ColorTense) FConstant.TABLEAU_DE_BORD_COLOR, realTimeModel);
                mainFrame.updateMainPanel(mainStructurePanel);
                realTimeModel.setgUISynchronizer(true);
            }

        });

    }

    private static void checkDBInitialTuple() {

        WeightModel weightModel = new WeightModel();
        ProductModel productModel = new ProductModel();

        if (!weightModel.isUnitByAbreviationExist("Kg")) {
            Unit unit = new Unit();
            unit.setAbrevation("Kg");
            unit.setDesignation("Kilogrammes");
            unit.setIsUniteReference((short) 1);
            weightModel.createUnit(unit);
        }

        if (!productModel.isProductByCodeExist("0000")) {
            Pruduct pruduct = new Pruduct();
            pruduct.setArticleCode("0000");
            pruduct.setDesignation("Non specifié");
            pruduct.setAddDate(DateTime.now().toDate());
            Unit unit = weightModel.getUnitByAbreviation("Kg");
            pruduct.setUnite(unit);

            productModel.createProduct(pruduct);
        }
    }

    private static void initThread(RealTimeModel realTimeModel) {

        while (realTimeModel.isgUISynchronizer()) {

        }

        Thread realTimeConsumerThread = new Thread(new RealTimeProducerThread(realTimeModel));
        realTimeConsumerThread.start();
    }
}
