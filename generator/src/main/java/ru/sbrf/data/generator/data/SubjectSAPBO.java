package ru.sbrf.data.generator.data;

import csvdata.builder.enums.SAPBO.ClientType;

public class SubjectSAPBO extends Subject {

    public SubjectSAPBO(String borrowerType) {
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
