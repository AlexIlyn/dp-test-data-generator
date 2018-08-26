package ru.sbrf.data.generator;

import csvdata.builder.ValueType;
import csvdata.builder.enums.DRPA.ClientType;
import lombok.Data;

import static csvdata.builder.RandomValueUtils.getRandomNumberOfLength;
import static csvdata.builder.RandomValueUtils.getRandomValueByValueType;

@Data
public class DRPASubject extends Subject {

    private String cust_id;
    private String doc_num;

    public DRPASubject(String borrowerType) {
        super(borrowerType);
        generateValues();
    }

    public DRPASubject(SAPBOSubject subject){
        super(subject);
        generateValues();
    }

    private void generateValues() {
        cust_id = getRandomValueByValueType(ValueType.BIGINT);
        doc_num = String.format("%s %s %s", getRandomNumberOfLength(2), getRandomNumberOfLength(2), getRandomNumberOfLength(6));
    }

    @Override
    protected String getClientType(String inputType) {
        if(inputType.equals(subjectTypeValues[0])){
            return ClientType.LEGAL_ENTITY.toString();
        } else if(inputType.equals(subjectTypeValues[1])){
            return ClientType.BUSINESS_PERSON_ENTITY.toString();
        }
        return null;
    }
}
