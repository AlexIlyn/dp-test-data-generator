package ru.sbrf.data.generator.data;

import csvdata.builder.enums.DRPA.AgreementType;
import lombok.Data;

import static csvdata.builder.RandomValueUtils.getRandomNumberOfLength;

@Data
public class AgrCollatDRPA {
    private String cust_id;
    private String agr_collat_id;
    private AgreementType agreementType;
    private AgrLinkDRPA agrCredCollat;

    public AgrCollatDRPA(SubjectDRPA guarantor, AgrCredDRPA agrCred, AgreementType agreementType) {
        this.cust_id = guarantor.getCust_id();
        this.agr_collat_id = getRandomNumberOfLength(10);
        this.agreementType = agreementType;
        this.agrCredCollat = new AgrLinkDRPA(agrCred, this);
    }
}
