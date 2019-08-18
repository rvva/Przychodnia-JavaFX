package loginApp;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private LoginModel loginModel;

    @FXML
    private Label dbStatus;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private ComboBox<Option> optionComboBox;

    @FXML
    private Label loginStatus;



    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loginModel =new LoginModel();
        if (this.loginModel.isDatabaseConnected())
        {
            dbStatus.setText("Połączono z bazą danych.");
            dbStatus.setTextFill(Color.GREEN);
        }
        else
        {
            dbStatus.setText("Brak połączenia z bazą danych.");
            dbStatus.setTextFill(Color.RED);
        }

        this.optionComboBox.setItems(FXCollections.observableArrayList(Option.values()));

    }

    /**
     * Metoda przygotowująca scene dla pracownika - Recepcjonista.
     * @throws IOException
     */
    public void receptionistLogin() throws IOException {
        try {
            Stage receptionistStage = new Stage();
            Parent receptionistRoot = FXMLLoader.load(getClass().getResource("/receptionistApp/receptionistFXML.fxml"));

            Scene scene = new Scene(receptionistRoot);
            receptionistStage.setScene(scene);
            receptionistStage.setTitle("Przychodnia | Recepcja");
            receptionistStage.setResizable(false);
            receptionistStage.show();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Metoda przygotowująca scene dla pracownika - Lekarz.
     * @throws IOException
     */
    public void doctorLogin() throws IOException
    {
        try
        {
            Stage doctorStage = new Stage();
            Parent doctorRoot =  FXMLLoader.load(getClass().getResource("/doctorApp/doctorFXML.fxml"));

            Scene scene = new Scene(doctorRoot);
            doctorStage.setScene(scene);
            doctorStage.setTitle("Przychodnia | Lekarz");
            doctorStage.setResizable(false);
            doctorStage.show();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @FXML
    /**
     * Zdarzenie OnAction dla przycisku "Zaloguj się"
     * Wprowadzane hasło przez użytkownika jest poddawane funkcji hash'ujacej.
     */
    void loginButtonOnAction(ActionEvent event) {

        try {
            if (loginField.getText().isEmpty() || passwordField.getText().isEmpty() || optionComboBox.getValue() == null)
            {
                loginStatus.setText("Wypełnij \nwszystkie pola.");
            }
            else if (loginModel.isLogin(loginField.getText(), loginModel.hashPassword(passwordField.getText()), optionComboBox.getValue().toString())) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                switch (optionComboBox.getValue().toString()) {
                    case "Lekarz":
                        doctorLogin();
                        break;
                    case "Recepcjonista":
                        receptionistLogin();
                        break;
                }
            } else
                loginStatus.setText("Wprowadzono \nbłędne dane.");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
