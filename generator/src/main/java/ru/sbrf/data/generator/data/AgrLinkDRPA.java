package ru.sbrf.data.generator.data;

import lombok.Data;

@Data
public class AgrLinkDRPA {
    private String agr_cred_id;
    private String agr_collat_id;

    AgrLinkDRPA(AgrCredDRPA agrCred, AgrCollatDRPA agrCollat) {
        this.agr_cred_id = agrCred.getAgr_cred_id();
        this.agr_collat_id = agrCollat.getAgr_collat_id();
    }
}
