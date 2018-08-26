package ru.sbrf.data.generator;

import csvdata.builder.CSV_TestDataBuilder;
import csvdata.builder.enums.CaseSubType;
import csvdata.builder.enums.CaseType;
import lombok.Getter;

import java.io.IOException;

import static csvdata.builder.ValueType.*;

public class SapboClientsBuilder {
    public static final String SAPBO_CLIENTS_FILE_NAME = "1. Клиенты.csv";
    public static final String DRPA_AGR_CRED_FILE_NAME = "AGR_CRED.csv";
    public static final String DRPA_AGR_COLLAT_FILE_NAME = "AGR_COLLAT.csv";
    public static final String DRPA_AGRCRED_A_FILE_NAME = "AGRCRED_A.csv";
    public static String[] borrowerTypeValues = new String[]{"ЮЛ", "ИП"};

    private CSV_TestDataBuilder dataBuilder;

    @Getter
    private SAPBOSubject borrower;

    public SapboClientsBuilder(){
        try {
            this.dataBuilder = new CSV_TestDataBuilder(CaseType.SAPBO, CaseSubType.CLIENTS, SAPBO_CLIENTS_FILE_NAME);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void build(SAPBOSubject borrower){
        this.borrower = borrower;
        dataBuilder.buildRandomRecord();
        dataBuilder.setLastRecordFieldsWithType(CRM_ID, borrower.getCrm_id());
        dataBuilder.setLastRecordFieldsWithType(OGRN, borrower.getOgrn());
        dataBuilder.setLastRecordFieldsWithType(FULL_NAME, borrower.getFull_name());
        dataBuilder.setLastRecordFieldsWithType(INN, borrower.getInn());
        dataBuilder.setLastRecordFieldsWithType(ORG_TYPE, borrower.getClientType());
        try {
            dataBuilder.generate();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
