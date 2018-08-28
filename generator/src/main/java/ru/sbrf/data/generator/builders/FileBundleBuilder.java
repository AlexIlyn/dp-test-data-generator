package ru.sbrf.data.generator.builders;

import csvdata.builder.enums.DRPA.AgreementType;
import csvdata.builder.enums.SubjectType;
import ru.sbrf.data.generator.data.AgrCollatDRPA;
import ru.sbrf.data.generator.data.AgrCredDRPA;
import ru.sbrf.data.generator.data.SubjectDRPA;
import ru.sbrf.data.generator.data.SubjectSAPBO;

import java.util.ArrayList;
import java.util.List;

public class FileBundleBuilder {
    SapboClientsBuilder  sapboClientsBuilder  = new SapboClientsBuilder();
    DrpaClientsBuilder   drpaClientsBuilder   = new DrpaClientsBuilder();
    DrpaAgrCredBuilder   drpaAgrCredBuilder   = new DrpaAgrCredBuilder();
    DrpaAgrCollatBuilder drpaAgrCollatBuilder = new DrpaAgrCollatBuilder();

    SubjectSAPBO borrowerSapbo;
    SubjectDRPA borrowerDrpa;
    List<AgrCredDRPA> agrCredsDRPA = new ArrayList<>();
    List<AgrCollatDRPA> agrCollatsPledge = new ArrayList<>();
    List<AgrCollatDRPA> agrCollatsCollateral = new ArrayList<>();
    List<AgrCollatDRPA> agrCollatsGuarantee = new ArrayList<>();
    List<SubjectDRPA> pledgeGuarantors = new ArrayList<>();
    List<SubjectDRPA> collateralGuarantors = new ArrayList<>();
    List<SubjectDRPA> guaranteeGuarantors = new ArrayList<>();

    public void prepareEntities(String borrowerType, String pledgeGuarantorType, String collateralGuarantorType, int agrCredCount){
        prepareBorrower(borrowerType);
        prepareAgrCred(agrCredCount);
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
        sapboClientsBuilder.cleanData();
        drpaClientsBuilder.cleanData();
        drpaAgrCredBuilder.cleanData();
        drpaAgrCollatBuilder.cleanData();

    }

    private void prepareBorrower(String borrowerType){
        borrowerSapbo = new SubjectSAPBO(borrowerType);
        borrowerDrpa = new SubjectDRPA(borrowerSapbo);
    }

    private void buildBorrower(){
        sapboClientsBuilder.build(borrowerSapbo);
        drpaClientsBuilder.build(borrowerDrpa);
    }

    private void prepareAgrCred(int count){
        for (int i = 0; i < count; i++) {
            agrCredsDRPA.add(new AgrCredDRPA(borrowerDrpa));
        }
    }

    private void buildAgrCred(){
        for (AgrCredDRPA agrCred : agrCredsDRPA){
            drpaAgrCredBuilder.build(agrCred);
        }
    }

    private void prepareAgrCollat(String pledgeGuarantorType, String collateralGuarantorType){
        SubjectDRPA pledgeGuarantor;
        SubjectDRPA guaranteeGuarantor;
        SubjectDRPA collateralGuarantor;
        for (AgrCredDRPA agrCred : agrCredsDRPA){
            pledgeGuarantor = new SubjectDRPA(pledgeGuarantorType);
            guaranteeGuarantor = new SubjectDRPA("ЮЛ");
            pledgeGuarantors.add(pledgeGuarantor);
            guaranteeGuarantors.add(guaranteeGuarantor);

            agrCollatsPledge.add(new AgrCollatDRPA(pledgeGuarantor, agrCred, AgreementType.PLEDGE));
            agrCollatsGuarantee.add(new AgrCollatDRPA(guaranteeGuarantor, agrCred, AgreementType.GUARANTEE));
            if(collateralGuarantorType.equals(SubjectType.BORROWER.toString())){
                collateralGuarantor = borrowerDrpa;
            } else {
                collateralGuarantor = new SubjectDRPA(collateralGuarantorType);
                collateralGuarantors.add(collateralGuarantor);
            }
            agrCollatsCollateral.add(new AgrCollatDRPA(collateralGuarantor, agrCred, AgreementType.COLLATERAL));
        }
    }

    private void buildAgrCollat(){
        for (SubjectDRPA subj : pledgeGuarantors){
            drpaClientsBuilder.build(subj);
        }
        for (SubjectDRPA subj : guaranteeGuarantors){
            drpaClientsBuilder.build(subj);
        }
        for (SubjectDRPA subj : collateralGuarantors){
            drpaClientsBuilder.build(subj);
        }
        for (AgrCollatDRPA agr : agrCollatsPledge){
            drpaAgrCollatBuilder.build(agr);
        }
        for (AgrCollatDRPA agr : agrCollatsGuarantee){
            drpaAgrCollatBuilder.build(agr);
        }
        for (AgrCollatDRPA agr : agrCollatsCollateral){
            drpaAgrCollatBuilder.build(agr);
        }
    }
}
