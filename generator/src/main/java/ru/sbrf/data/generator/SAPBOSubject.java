package ru.sbrf.data.generator;

import csvdata.builder.enums.SAPBO.ClientType;

public class SAPBOSubject extends Subject {

    public SAPBOSubject(String borrowerType) {
        super(borrowerType);
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
