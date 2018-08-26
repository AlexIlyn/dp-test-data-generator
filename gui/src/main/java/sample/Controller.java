package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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

    private static String[] borrowerTypeValues = new String[]{"ЮЛ", "ИП"};
    private static String[] pledgeGuarantorTypeValues = new String[]{"ЮЛ", "ИП", "ФЛ"};
    private static String[] collateralGuarantorTypeValues = new String[]{"ЮЛ", "ИП", "ФЛ", "Заемщик"};

    @FXML
    void initialize() {
        assert parentContainer != null : "fx:id=\"parentContainer\" was not injected: check your FXML file 'sample.fxml'.";
        assert borrowerType != null : "fx:id=\"borrowerType\" was not injected: check your FXML file 'sample.fxml'.";
        initChoiceBox(borrowerType, borrowerTypeValues);
        initChoiceBox(pledgeGuarantorType, pledgeGuarantorTypeValues);
        initChoiceBox(collateralGuarantorType, collateralGuarantorTypeValues);
        generateFilesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Данные сгенерированны.");

                alert.showAndWait();
            }
        });
    }

    private void initChoiceBox(ChoiceBox choiceBox, String[] values){
        choiceBox.setItems(FXCollections.observableArrayList(values));
        choiceBox.getSelectionModel().select(0);
    }
}
