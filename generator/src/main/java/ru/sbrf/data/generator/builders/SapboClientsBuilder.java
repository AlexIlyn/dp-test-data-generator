package ru.sbrf.data.generator.builders;

import csvdata.builder.CSV_TestDataBuilder;
import csvdata.builder.enums.CaseSubType;
import csvdata.builder.enums.CaseType;
import lombok.Getter;
import ru.sbrf.data.generator.data.SubjectSAPBO;

import java.io.IOException;

import static csvdata.builder.ValueType.*;

public class SapboClientsBuilder {
    public static final String SAPBO_CLIENTS_FILE_NAME = "1. Клиенты.csv";
    public static String[] borrowerTypeValues = new String[]{"ЮЛ", "ИП"};

    private CSV_TestDataBuilder dataBuilder;

    @Getter
    private SubjectSAPBO borrower;

    public SapboClientsBuilder(){
        try {
            this.dataBuilder = new CSV_TestDataBuilder(CaseType.SAPBO, CaseSubType.CLIENTS, SAPBO_CLIENTS_FILE_NAME);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void build(SubjectSAPBO borrower){
        this.borrower = borrower;
        dataBuilder.buildRandomRecord()
                .setLastRecordFieldsWithType(CRM_ID, borrower.getCrm_id())
                .setLastRecordFieldsWithType(INN, borrower.getInn())
                .setLastRecordFieldsWithType(OGRN, borrower.getOgrn())
                .setLastRecordFieldsWithType(FULL_NAME, borrower.getFull_name())
                .setLastRecordFieldsWithType(ORG_TYPE, borrower.getClientType());
    }

    public void generate(){
        try {
            dataBuilder.generate();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
