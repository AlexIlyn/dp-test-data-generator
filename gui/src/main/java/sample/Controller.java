package sample;

import csvdata.builder.enums.DRPA.AgreementType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.sbrf.data.generator.builders.*;
import ru.sbrf.data.generator.data.AgrCollatDRPA;
import ru.sbrf.data.generator.data.AgrCredDRPA;
import ru.sbrf.data.generator.data.SubjectDRPA;
import ru.sbrf.data.generator.data.SubjectSAPBO;

import static ru.sbrf.data.generator.builders.SapboClientsBuilder.borrowerTypeValues;

public class Controller {

    @FXML
    private VBox parentContainer;

    @FXML
    private ChoiceBox<String> borrowerType;
    @FXML
    private ChoiceBox<String> pledgeGuarantorType;
    @FXML
    private ChoiceBox<String> collateralGuarantorType;

    @FXML
    private TextField borrowerAggreements;

    @FXML
    private Button generateFilesButton;

    private static String[] pledgeGuarantorTypeValues = new String[]{"ЮЛ", "ИП", "ФЛ"};
    private static String[] collateralGuarantorTypeValues = new String[]{"ЮЛ", "ИП", "ФЛ", "Заемщик"};

    @FXML
    void initialize() {
        initChoiceBox(borrowerType, borrowerTypeValues);
        initChoiceBox(pledgeGuarantorType, pledgeGuarantorTypeValues);
        initChoiceBox(collateralGuarantorType, collateralGuarantorTypeValues);
        generateFilesButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Данные сгенерированны.");

            alert.showAndWait();
            FileBundleBuilder fileBundleBuilder = new FileBundleBuilder();
            fileBundleBuilder.prepareEntities(borrowerType.getSelectionModel().getSelectedItem(),
                    pledgeGuarantorType.getSelectionModel().getSelectedItem(),
                    collateralGuarantorType.getSelectionModel().getSelectedItem());
            fileBundleBuilder.buildEntities();
            fileBundleBuilder.generateFiles();
            /*SapboClientsBuilder  sapboClientsBuilder  = new SapboClientsBuilder();
            DrpaClientsBuilder   drpaClientsBuilder   = new DrpaClientsBuilder();
            DrpaAgrCredBuilder   drpaAgrCredBuilder   = new DrpaAgrCredBuilder();
            DrpaAgrCollatBuilder drpaAgrCollatBuilder = new DrpaAgrCollatBuilder();

            SubjectSAPBO borrower = new SubjectSAPBO(borrowerType.getSelectionModel().getSelectedItem());
            SubjectDRPA borrowerDRPA = new SubjectDRPA(borrower);
            SubjectDRPA guarantorDRPA = new SubjectDRPA("ФЛ");
            AgrCredDRPA agrCredDRPA = new AgrCredDRPA(borrowerDRPA);
            AgrCollatDRPA agrCollatDRPA = new AgrCollatDRPA(guarantorDRPA, agrCredDRPA, AgreementType.PLEDGE);

            sapboClientsBuilder.build(borrower);
            drpaClientsBuilder.build(borrowerDRPA);
            drpaClientsBuilder.build(guarantorDRPA);
            drpaAgrCredBuilder.build(agrCredDRPA);
            drpaAgrCollatBuilder.build(agrCollatDRPA);

            sapboClientsBuilder.generate();
            drpaClientsBuilder.generate();
            drpaAgrCredBuilder.generate();
            drpaAgrCollatBuilder.generate();*/
        });
    }

    private void initChoiceBox(ChoiceBox choiceBox, String[] values){
        choiceBox.setItems(FXCollections.observableArrayList(values));
        choiceBox.getSelectionModel().select(0);
    }
}
