package ru.sbrf.data.generator.builders;

import csvdata.builder.CSV_TestDataBuilder;
import csvdata.builder.enums.CaseSubType;
import csvdata.builder.enums.CaseType;
import ru.sbrf.data.generator.data.AgrCollatDRPA;

import java.io.IOException;

import static ru.sbrf.data.generator.filefields.AgrCredFieldNames.AGR_CRED_ID;
import static ru.sbrf.data.generator.filefields.AgrProvisFieldNames.*;

public class DrpaAgrCollatBuilder {
    public static final String DRPA_AGR_COLLAT_FILE_NAME = "AGR_COLLAT.csv";
    public static final String DRPA_AGRCRED_A_FILE_NAME = "AGRCRED_A.csv";

    private CSV_TestDataBuilder collatBuilder;
    private CSV_TestDataBuilder linkBuilder;

    public DrpaAgrCollatBuilder() {
        try {
            this.collatBuilder = new CSV_TestDataBuilder(CaseType.DRPA, CaseSubType.AGRPROVIS, DRPA_AGR_COLLAT_FILE_NAME);
            this.linkBuilder = new CSV_TestDataBuilder(CaseType.DRPA, CaseSubType.AGRLINK, DRPA_AGRCRED_A_FILE_NAME);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void build(AgrCollatDRPA agrCollat){
        collatBuilder.buildRandomRecord()
                .setLastRecordFieldValue(CUST_ID, agrCollat.getCust_id())
                .setLastRecordFieldValue(AGR_COLLAT_ID, agrCollat.getAgr_collat_id())
                .setLastRecordFieldValue(ASSET_PLEDGE_TYPE_ID, agrCollat.getAgreementType().toString());
        linkBuilder.buildRandomRecord()
                .setLastRecordFieldValue(AGR_CRED_ID, agrCollat.getAgrCredCollat().getAgr_cred_id())
                .setLastRecordFieldValue(AGR_COLLAT_ID, agrCollat.getAgr_collat_id());

    }

    public void generate(){
        try {
            collatBuilder.generate();
            linkBuilder.generate();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cleanData(){
        collatBuilder.cleanRecords();
        linkBuilder.cleanRecords();
    }
}
