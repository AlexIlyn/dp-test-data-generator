package ru.sbrf.data.generator.builders;

import csvdata.builder.enums.DRPA.AgreementType;
import ru.sbrf.data.generator.data.AgrCollatDRPA;
import ru.sbrf.data.generator.data.AgrCredDRPA;
import ru.sbrf.data.generator.data.SubjectDRPA;
import ru.sbrf.data.generator.data.SubjectSAPBO;

import java.util.List;

public class FileBundleBuilder {
    SapboClientsBuilder  sapboClientsBuilder  = new SapboClientsBuilder();
    DrpaClientsBuilder   drpaClientsBuilder   = new DrpaClientsBuilder();
    DrpaAgrCredBuilder   drpaAgrCredBuilder   = new DrpaAgrCredBuilder();
    DrpaAgrCollatBuilder drpaAgrCollatBuilder = new DrpaAgrCollatBuilder();

    SubjectSAPBO borrowerSapbo;
    SubjectDRPA borrowerDrpa;
    AgrCredDRPA agrCredDRPA;
    AgrCollatDRPA agrCollatPledge;
    AgrCollatDRPA agrCollatCollateral;
    AgrCollatDRPA agrCollatGuarantee;
    //List<SubjectDRPA> guarantorsDrpa;
    SubjectDRPA pledgeGuarantor;
    SubjectDRPA collateralGuarantor;
    SubjectDRPA guaranteeGuarantor;

    public void prepareEntities(String borrowerType, String pledgeGuarantorType, String collateralGuarantorType){
        prepareBorrower(borrowerType);
        prepareAgrCred();
        prepareAgrCollat(pledgeGuarantorType, collateralGuarantorType);
    }

    public void buildEntities(){
        buildBorrower();
        buildAgrCred();
        buildAgrCollat();
    }

    public void generateFiles(){
        sapboClientsBuilder.generate();
        drpaClientsBuilder.generate();
        drpaAgrCredBuilder.generate();
        drpaAgrCollatBuilder.generate();

    }

    private void prepareBorrower(String borrowerType){
        borrowerSapbo = new SubjectSAPBO(borrowerType);
        borrowerDrpa = new SubjectDRPA(borrowerSapbo);
    }

    private void buildBorrower(){
        sapboClientsBuilder.build(borrowerSapbo);
        drpaClientsBuilder.build(borrowerDrpa);
    }

    private void prepareAgrCred(){
        agrCredDRPA = new AgrCredDRPA(borrowerDrpa);
    }

    private void buildAgrCred(){
        drpaAgrCredBuilder.build(agrCredDRPA);
    }

    private void prepareAgrCollat(String pledgeGuarantorType, String collateralGuarantorType){
        pledgeGuarantor = new SubjectDRPA(pledgeGuarantorType);
        collateralGuarantor = new SubjectDRPA(collateralGuarantorType);
        guaranteeGuarantor = new SubjectDRPA("ЮЛ");
        agrCollatPledge = new AgrCollatDRPA(pledgeGuarantor, agrCredDRPA, AgreementType.PLEDGE);
        agrCollatCollateral = new AgrCollatDRPA(collateralGuarantor, agrCredDRPA, AgreementType.COLLATERAL);
        agrCollatGuarantee = new AgrCollatDRPA(guaranteeGuarantor, agrCredDRPA, AgreementType.GUARANTEE);
    }

    private void buildAgrCollat(){
        drpaClientsBuilder.build(pledgeGuarantor);
        drpaClientsBuilder.build(collateralGuarantor);
        drpaClientsBuilder.build(guaranteeGuarantor);
        drpaAgrCollatBuilder.build(agrCollatPledge);
        drpaAgrCollatBuilder.build(agrCollatCollateral);
        drpaAgrCollatBuilder.build(agrCollatGuarantee);
    }
}
