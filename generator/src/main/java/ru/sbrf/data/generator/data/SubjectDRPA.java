package ru.sbrf.data.generator.data;

import csvdata.builder.ValueType;
import csvdata.builder.enums.DRPA.ClientType;
import lombok.Data;

import static csvdata.builder.RandomValueUtils.getRandomNumberOfLength;
import static csvdata.builder.RandomValueUtils.getRandomValueByValueType;

@Data
public class SubjectDRPA extends Subject {

    private String cust_id;
    private String doc_num;

    public SubjectDRPA(String borrowerType) {
        super(borrowerType);
        generateValues();
    }

    public SubjectDRPA(SubjectSAPBO subject){
        super(subject);
        generateValues();
        if(subject.getClientType().equals(csvdata.builder.enums.SAPBO.ClientType.LEGAL_ENTITY.toString())){
            this.setClientType(ClientType.LEGAL_ENTITY.toString());
        } else if(subject.getClientType().equals(csvdata.builder.enums.SAPBO.ClientType.BUSINESS_PERSON_ENTITY.toString())){
            this.setClientType(ClientType.BUSINESS_PERSON_ENTITY.toString());
        }
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
        }else if(inputType.equals(subjectTypeValues[2])){
            return ClientType.PERSON_ENTITY.toString();
        }
        return null;
    }
}
