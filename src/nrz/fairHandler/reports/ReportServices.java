/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import nrz.fairHandler.jpa.Weight;
import nrz.fairHandler.model.WeightModel;
import nrz.fairHandlerStates.files.path.SoftFiles;
import nrz.fairHandlerStates.keys.FrameTockenKeys;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 *
 * @author rahimAdmin
 */
public class ReportServices {

    private static final String FIRST_TIME_WEIGHT_KEY = "firstTimeWeight";
    private static final String FIRST_DATE_WEIGHT_KEY = "firstDateWeight";
    private static final String SECOND_DATE_WEIGHT_KEY = "secondDateWeight";
    private static final String SECOND_TIME_WEIGHT_KEY = "secondTimeWeight";
    private static final String GROSS_WEIGHT_KEY = "grossWeight";
    private static final String UNIT_ABREVIATION_KEY = "unitAbreviation";
    private static final String RCD_KEY = "RCD";
    private static final String TYPE_KEY = "type";
    private static final String PRODUCT_DESIGNATION_KEY = "articleDesignation";
    private static final String PRODUCT_CODE_KEY = "articleCode";
    private static final String PROGRESSIVE_CODE_KEY = "progressiveCode";
    private static final String NET_WEIGHT_KEY = "netWeight";
    private static final String TARE_WEIGHT_KEY = "tareWeight";
    private static final String KEYWORDS_FILTER_KEY = "keywordsFilter";
    private static final String BEGIN_DATE_FILTER = "beginDateFilter";
    private static final String END_DATE_FILTER = "endDateFilter";

    private static Map<String, Object> standardFormMap;

    static {
        standardFormMap = new HashMap<>();
        standardFormMap.put(FIRST_TIME_WEIGHT_KEY, null);
        standardFormMap.put(FIRST_DATE_WEIGHT_KEY, null);
        standardFormMap.put(SECOND_TIME_WEIGHT_KEY, null);
        standardFormMap.put(SECOND_DATE_WEIGHT_KEY, null);
        standardFormMap.put(GROSS_WEIGHT_KEY, null);
        standardFormMap.put(NET_WEIGHT_KEY, null);
        standardFormMap.put(TARE_WEIGHT_KEY, null);
        standardFormMap.put(UNIT_ABREVIATION_KEY, null);
        standardFormMap.put(RCD_KEY, null);
        standardFormMap.put(TYPE_KEY, null);
        standardFormMap.put(PRODUCT_DESIGNATION_KEY, null);
        standardFormMap.put(PRODUCT_CODE_KEY, null);
        standardFormMap.put(PROGRESSIVE_CODE_KEY, null);
        standardFormMap.put(KEYWORDS_FILTER_KEY, null);
        standardFormMap.put(BEGIN_DATE_FILTER, null);
        standardFormMap.put(END_DATE_FILTER, null);

    }

    public static JasperPrint getUniqueWeightReport(Weight weight) throws JRException {
        standardFormMap.replace(FIRST_TIME_WEIGHT_KEY, weight.getFirstTicketTime());
        standardFormMap.replace(FIRST_DATE_WEIGHT_KEY, weight.getFirstTicketDate());
        standardFormMap.replace(GROSS_WEIGHT_KEY, weight.getGrossWeight());
        standardFormMap.replace(UNIT_ABREVIATION_KEY, weight.getArticle().getUnite().getAbrevation());
        standardFormMap.replace(RCD_KEY, weight.getReference());
        standardFormMap.replace(TYPE_KEY, weight.getType());
        standardFormMap.replace(PRODUCT_DESIGNATION_KEY, weight.getArticle().getDesignation());
        standardFormMap.replace(PRODUCT_CODE_KEY, weight.getArticle().getArticleCode());
        standardFormMap.replace(PROGRESSIVE_CODE_KEY, weight.getProgressiveCode());

        return JasperFillManager.fillReport(SoftFiles.UNIQUE_WEIGHT_FILE_PATH.getValue().toString(), standardFormMap, new JREmptyDataSource(1));
    }

    public static JasperPrint getNegativeWeightReport(Weight weight) throws JRException {
        fillParametersForComplete(weight);
        return JasperFillManager.fillReport(SoftFiles.NEGATIVE_WEIGHT_FILE_PATH.getValue().toString(), standardFormMap, new JREmptyDataSource(1));
    }

    public static JasperPrint getPositiveWeightReport(Weight weight) throws JRException {
        fillParametersForComplete(weight);
        return JasperFillManager.fillReport(SoftFiles.POSITIVE_WEIGHT_FILE_PATH.getValue().toString(), standardFormMap, new JREmptyDataSource(1));
    }

    public static JasperPrint getNullWeightReport(Weight weight) throws JRException {
        fillParametersForComplete(weight);
        return JasperFillManager.fillReport(SoftFiles.POSITIVE_WEIGHT_FILE_PATH.getValue().toString(), standardFormMap, new JREmptyDataSource(1));
    }

    public static JasperPrint getPredeterminedWeightReport(Weight weight) throws JRException {
        standardFormMap.replace(FIRST_TIME_WEIGHT_KEY, weight.getFirstTicketTime());
        standardFormMap.replace(FIRST_DATE_WEIGHT_KEY, weight.getFirstTicketDate());
        standardFormMap.replace(GROSS_WEIGHT_KEY, weight.getGrossWeight());
        standardFormMap.replace(TARE_WEIGHT_KEY, weight.getTare());
        standardFormMap.replace(NET_WEIGHT_KEY, weight.getNetWeight());
        standardFormMap.replace(UNIT_ABREVIATION_KEY, weight.getArticle().getUnite().getAbrevation());
        standardFormMap.replace(RCD_KEY, weight.getReference());
        standardFormMap.replace(TYPE_KEY, weight.getType());
        standardFormMap.replace(PRODUCT_DESIGNATION_KEY, weight.getArticle().getDesignation());
        standardFormMap.replace(PRODUCT_CODE_KEY, weight.getArticle().getArticleCode());
        standardFormMap.replace(PROGRESSIVE_CODE_KEY, weight.getProgressiveCode());

        return JasperFillManager.fillReport(SoftFiles.PREDETERMINED_TARE_FILE_PATH.getValue().toString(), standardFormMap, new JREmptyDataSource(1));
    }

    private static void fillParametersForComplete(Weight weight) {
        standardFormMap.replace(FIRST_TIME_WEIGHT_KEY, weight.getFirstTicketTime());
        standardFormMap.replace(FIRST_DATE_WEIGHT_KEY, weight.getFirstTicketDate());
        standardFormMap.replace(GROSS_WEIGHT_KEY, weight.getGrossWeight());
        standardFormMap.replace(UNIT_ABREVIATION_KEY, weight.getArticle().getUnite().getAbrevation());
        standardFormMap.replace(RCD_KEY, weight.getReference());
        standardFormMap.replace(TYPE_KEY, weight.getType());
        standardFormMap.replace(PRODUCT_DESIGNATION_KEY, weight.getArticle().getDesignation());
        standardFormMap.replace(PRODUCT_CODE_KEY, weight.getArticle().getArticleCode());
        standardFormMap.replace(PROGRESSIVE_CODE_KEY, weight.getProgressiveCode());
        standardFormMap.replace(SECOND_DATE_WEIGHT_KEY, weight.getSecondTicketDate());
        standardFormMap.replace(SECOND_TIME_WEIGHT_KEY, weight.getSecondTicketTime());
        standardFormMap.replace(NET_WEIGHT_KEY, weight.getNetWeight());
        standardFormMap.replace(TARE_WEIGHT_KEY, weight.getTare());
    }

    public static JasperPrint getWeightsArraysJRPrint(WeightModel model) throws JRException {
        standardFormMap.replace(BEGIN_DATE_FILTER, model.getBeginDate());
        standardFormMap.replace(END_DATE_FILTER, model.getEndDate());
        standardFormMap.replace(KEYWORDS_FILTER_KEY, model.getFilter());
        return JasperFillManager.fillReport(SoftFiles.WEIGHTS_ARRAY_FILE_PATH.getValue().toString(),
                standardFormMap, new JRBeanCollectionDataSource(model.getWeights()));
    }
}
