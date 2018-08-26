package ru.sbrf.data.generator.builders;

import csvdata.builder.CSV_TestDataBuilder;
import csvdata.builder.enums.CaseSubType;
import csvdata.builder.enums.CaseType;
import lombok.Getter;
import ru.sbrf.data.generator.data.SubjectDRPA;

import java.io.IOException;

import static csvdata.builder.ValueType.CRM_ID;
import static csvdata.builder.ValueType.OGRN;
import static csvdata.builder.ValueType.ORG_TYPE;
import static csvdata.builder.ValueType.FULL_NAME;
import static csvdata.builder.ValueType.INN;
import static ru.sbrf.data.generator.filefields.CustFileFieldNames.CUST_ID;
import static ru.sbrf.data.generator.filefields.CustFileFieldNames.DOC_NUM;

public class DrpaClientsBuilder {
    public static final String DRPA_CUST_FILE_NAME = "CUST.csv";

    private CSV_TestDataBuilder dataBuilder;

    public DrpaClientsBuilder(){
        try {
            this.dataBuilder = new CSV_TestDataBuilder(CaseType.DRPA, CaseSubType.CUST, DRPA_CUST_FILE_NAME);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public DrpaClientsBuilder build(SubjectDRPA borrower){
        dataBuilder.buildRandomRecord();
        dataBuilder.setLastRecordFieldValue(CUST_ID, borrower.getCust_id());
        dataBuilder.setLastRecordFieldValue(DOC_NUM, borrower.getDoc_num());

        dataBuilder.setLastRecordFieldsWithType(CRM_ID, borrower.getCrm_id());
        dataBuilder.setLastRecordFieldsWithType(OGRN, borrower.getOgrn());
        dataBuilder.setLastRecordFieldsWithType(FULL_NAME, borrower.getFull_name());
        dataBuilder.setLastRecordFieldsWithType(INN, borrower.getInn());
        dataBuilder.setLastRecordFieldsWithType(ORG_TYPE, borrower.getClientType());
        return this;
    }
    public void generate(){
        try {
            dataBuilder.generate();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
