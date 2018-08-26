package ru.sbrf.data.generator.data;

import csvdata.builder.RandomValueUtils;
import csvdata.builder.ValueType;
import csvdata.builder.enums.DRPA.ClientType;
import lombok.Data;

import static csvdata.builder.ValueType.CRM_ID;

@Data
public abstract class Subject {

    protected static String[] subjectTypeValues = new String[]{"ЮЛ", "ИП", "ФЛ"};
    public static String[] borrowerTypeValues = new String[]{"ЮЛ", "ИП"};

    private String crm_id;
    private String full_name;
    private String inn;
    private String ogrn;
    private String clientType;

    public Subject(String borrowerType) {
        crm_id = RandomValueUtils.getRandomValueByValueType(CRM_ID);
        ogrn = RandomValueUtils.getRandomNumberOfLength(15);
        clientType = getClientType(borrowerType);
        if(borrowerType.equals(subjectTypeValues[0])){
            full_name = RandomValueUtils.getRandomValueByValueType(ValueType.COMPANY_NAME);
            inn = RandomValueUtils.getRandomNumberOfLength(10);
        } else if(borrowerType.equals(subjectTypeValues[1])){
            full_name = "ИП " + RandomValueUtils.getRandomValueByValueType(ValueType.PERSON_FULL_NAME);
            inn = RandomValueUtils.getRandomNumberOfLength(12);
        } else if(borrowerType.equals(subjectTypeValues[2])){
            full_name = RandomValueUtils.getRandomValueByValueType(ValueType.PERSON_FULL_NAME);
            inn = RandomValueUtils.getRandomNumberOfLength(12);
        }
    }
    public Subject(Subject subject) {
        crm_id = subject.crm_id;
        full_name = subject.full_name;
        inn = subject.inn;
        ogrn = subject.ogrn;
        clientType = subject.clientType;
    }

    protected abstract String getClientType(String inputType);
}
