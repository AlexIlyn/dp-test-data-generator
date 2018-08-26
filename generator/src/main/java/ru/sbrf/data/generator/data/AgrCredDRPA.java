package ru.sbrf.data.generator.data;

import lombok.Data;

import static csvdata.builder.RandomValueUtils.getRandomNumberOfLength;

@Data
public class AgrCredDRPA {
    private String cust_id;
    private String agr_cred_id;

    public AgrCredDRPA(SubjectDRPA subject) {
        cust_id = subject.getCust_id();
        agr_cred_id = getRandomNumberOfLength(10);
    }
}
