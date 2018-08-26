package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.sbrf.data.generator.*;

import static ru.sbrf.data.generator.SapboClientsBuilder.borrowerTypeValues;

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
            SapboClientsBuilder sapboClientsBuilder = new SapboClientsBuilder();
            DrpaClientsBuilder drpaClientsBuilder = new DrpaClientsBuilder();
            SAPBOSubject borrower = new SAPBOSubject(borrowerType.getSelectionModel().getSelectedItem());
            DRPASubject borrowerDRPA = new DRPASubject(borrower);
            sapboClientsBuilder.build(borrower);
            drpaClientsBuilder.build(borrowerDRPA);
        });
    }

    private void initChoiceBox(ChoiceBox choiceBox, String[] values){
        choiceBox.setItems(FXCollections.observableArrayList(values));
        choiceBox.getSelectionModel().select(0);
    }
}
