/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.utility;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import nrz.fairHandler.FairHandler;

/**
 *
 * @author abderrahim
 */
public class FairHandlerUtility {

    public final static void initializeAllFonts() throws FontFormatException {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\abderrahim\\Dropbox\\fairHandler\\src\\fairHandlerPackage\\utility\\fonts\\digital-7.ttf")));
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public final static String FormatteWeight(Double weight, String uniteAbreviation) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setGroupingSize(3);
        DecimalFormatSymbols dfs = decimalFormat.getDecimalFormatSymbols();
        dfs.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(dfs);
        String result=null;
        try{
         result= decimalFormat.format(weight) + " " + uniteAbreviation;
        }catch(IllegalArgumentException ex){
            Logger.getLogger(FairHandlerUtility.class.getName()).log(Level.SEVERE, null, ex);
            result="";
        }
        return result;
    }
}
