package sample;

import csvdata.builder.enums.DRPA.AgreementType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        borrowerAggreements.setText("1");
        borrowerAggreements.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    borrowerAggreements.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (borrowerAggreements.getText().length() > 3) {
                    String s = borrowerAggreements.getText().substring(0, 3);
                    borrowerAggreements.setText(s);
                }
            }
        });
        generateFilesButton.setOnAction(event -> {

            FileBundleBuilder fileBundleBuilder = new FileBundleBuilder();
            fileBundleBuilder.prepareEntities(borrowerType.getSelectionModel().getSelectedItem(),
                    pledgeGuarantorType.getSelectionModel().getSelectedItem(),
                    collateralGuarantorType.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(borrowerAggreements.getText()));
            fileBundleBuilder.buildEntities();
            fileBundleBuilder.generateFiles();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Данные сгенерированны.");

            alert.showAndWait();
        });
    }

    private void initChoiceBox(ChoiceBox choiceBox, String[] values){
        choiceBox.setItems(FXCollections.observableArrayList(values));
        choiceBox.getSelectionModel().select(0);
    }
}
