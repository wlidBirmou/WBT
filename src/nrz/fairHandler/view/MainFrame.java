/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.view;

import nrz.java.view.component.FMainMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import nrz.java.utility.Icons;

/**
 *
 * @author abderrahim
 */
public class MainFrame extends JFrame implements Observer {

    private FMainMenu mainMenu;
    private JPanel mainPanel;
    private static MainFrame mainFrame;
    
    
    private MainFrame() throws HeadlessException {
        this("FairHandlerSoft");
    }
    
    public static MainFrame getInstance(){
        if(mainFrame==null){
            mainFrame=new MainFrame();
        }
        return mainFrame;
    }

    
    
    private MainFrame(String title) throws HeadlessException {
        super(title);
        this.setMinimumSize(new Dimension(1280, 700));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.build();
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    private void build() {
        this.initMainMenu();
        this.initBodyPanel();
        this.initLayout();
    }

    private void initMainMenu() {
        this.mainMenu = new FMainMenu(new Color(31, 145, 252));
        this.mainMenu.setPreferredSize(new Dimension(0, 40));
        this.mainMenu.setOpaque(true);
        this.mainMenu.createFMenuButton("Accueil", "Accueil", Icons.getIcon("homeIcon.png"));

        this.mainMenu.createFMenuButton("Paramètres", "parametre", Icons.getIcon("settingIcon.png"));

//        this.mainMenu.getButtonWithCode("parametre").createPopupItem("premier", "premier item", null, new ActionListener() {

////            @Override
////            public void actionPerformed(ActionEvent e) {
////
////            }
////        });
    }

    private void initBodyPanel() {
        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setPreferredSize(new Dimension(2000, 2000));
        this.mainPanel.setOpaque(true);
        this.mainPanel.setBackground(Color.WHITE);
    }

    private void initLayout() {
        this.setLayout(new BorderLayout());
        this.getContentPane().add(this.mainMenu, BorderLayout.NORTH);
        this.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
        this.update(null);
    }

    public void updateMainPanel(JPanel panel) {

        this.mainPanel.removeAll();
        this.mainPanel.add(panel, BorderLayout.CENTER);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
