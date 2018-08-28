package ru.sbrf.data.generator.builders;

import csvdata.builder.CSV_TestDataBuilder;
import csvdata.builder.enums.CaseSubType;
import csvdata.builder.enums.CaseType;
import ru.sbrf.data.generator.data.AgrCredDRPA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.sbrf.data.generator.filefields.AgrCredFieldNames.AGR_CRED_ID;
import static ru.sbrf.data.generator.filefields.AgrCredFieldNames.CUST_ID;

public class DrpaAgrCredBuilder {
    public static final String DRPA_AGR_CRED_FILE_NAME = "AGR_CRED.csv";

    private CSV_TestDataBuilder dataBuilder;

    private List<AgrCredDRPA> agrCreds;

    public DrpaAgrCredBuilder() {
        this.agrCreds = new ArrayList<>();
        try {
            this.dataBuilder = new CSV_TestDataBuilder(CaseType.DRPA, CaseSubType.AGRCRED, DRPA_AGR_CRED_FILE_NAME);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void build(AgrCredDRPA agrCred){
        dataBuilder.buildRandomRecord()
                .setLastRecordFieldValue(CUST_ID, agrCred.getCust_id())
                .setLastRecordFieldValue(AGR_CRED_ID, agrCred.getAgr_cred_id());
    }

    public void generate(){
        try {
            dataBuilder.generate();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cleanData(){
        dataBuilder.cleanRecords();
    }
}
