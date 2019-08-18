package receptionistApp;

import data.Visit;
import data.PatientData;
import dbUtil.dbConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import com.jfoenix.controls.*;
import javafx.stage.FileChooser;
import org.controlsfx.control.table.TableFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReceptionistController implements Initializable {

    @FXML
    private TableView<PatientData> patientsTableView;

    @FXML
    private TableColumn<PatientData, String> peselColumn;

    @FXML
    private TableColumn<PatientData, String> nameColumn;

    @FXML
    private TableColumn<PatientData, String> surnameColumn;

    @FXML
    private TableColumn<PatientData, String> phoneNumberColumn;

    @FXML
    private TableColumn<PatientData, String> addressColumn;

    @FXML
    private TextField countryField;

    @FXML
    private TextField voivodeshipField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField zipCodeField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField buildingNumberField;

    @FXML
    private TextField apartmentNumberField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField peselField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button addPatientButton;

    @FXML
    private Button clearFormButton;

    @FXML
    private TableView<PatientData> patientsTableViewVisit;

    @FXML
    private TableColumn<PatientData, String> peselColumnVisit;

    @FXML
    private TableColumn<PatientData, String> nameColumnVisit;

    @FXML
    private TableColumn<PatientData, String> surnameColumnVisit;

    @FXML
    private TableColumn<PatientData, String> phoneNumberColumnVisit;

    @FXML
    private TableColumn<PatientData, String> addressColumnVisit;

    @FXML
    private TextField visitName;

    @FXML
    private TextField visitSurname;

    @FXML
    private TextField visitPesel;

    @FXML
    private JFXDatePicker visitDatePicker;

    @FXML
    private Button addVisitButton;

    @FXML
    private ComboBox<String> visitDoctorComboBox;

    @FXML
    private JFXTimePicker visitTimePicker;

    @FXML
    private Label labelPeselValidation;

    @FXML
    private Label labelFieldValidation;

    @FXML
    private Label visitSectionVeryficationLabel;

    @FXML
    private TextField placeOfBirthField;

    @FXML
    private TextField dateOfBirthField;

    @FXML
    private TableColumn<PatientData, String> birthDataColumn;

    @FXML
    private TableColumn<PatientData, String> sexColumn;

    @FXML
    private TableColumn<PatientData, String> birthDataColumnVisit;

    @FXML
    private TableColumn<PatientData, String> sexColumnVisit;

    @FXML
    private TextField visitPhoneNumber;

    @FXML
    private TableView<Visit> visitTableView;

    @FXML
    private TableColumn<Visit, String> peselColumnVisitTable;

    @FXML
    private TableColumn<Visit, String> nameColumnVisitTable;

    @FXML
    private TableColumn<Visit, String> surnameColumnVisitTable;

    @FXML
    private TableColumn<Visit, String> phoneNumberColumnVisitTable;

    @FXML
    private TableColumn<Visit, LocalDate> dateColumn;

    @FXML
    private TableColumn<Visit, LocalTime> timeColumn;

    @FXML
    private TableColumn<Visit, String> doctorColumn;


    /**
     * Lista przechowywująca dane pajentów.
     */
    private ObservableList <PatientData> patientDataList;
    /**
     * Lista przechowywująca imię oraz nazwisko lekarzy.
     */
    private ObservableList <String> doctorsList;
    /**
     * Lista przechowywująca wizyty.
     */
    private ObservableList <Visit> visitsList;
    /**
     * Komponent do filtrowania listy pacjentów w widoku Wizyty.
     */
    private TableFilter <PatientData> tableViewPatientFilter;
    /**
     * Komponent do filtrowania listy wizyt w widoku Wizyty.
     */
    private TableFilter <Visit> tableViewVisitFilter;

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        patientDataList = FXCollections.observableArrayList();
        doctorsList = FXCollections.observableArrayList();
        visitsList = FXCollections.observableArrayList();
        try {
            loadPatientsData();
            tableViewPatientFilter = new TableFilter<>(patientsTableViewVisit);
            loadDocktors();
            loadVisitData();
            tableViewVisitFilter = new TableFilter<>(visitTableView);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        if (!doctorsList.isEmpty())
            visitDoctorComboBox.setItems(doctorsList);
        else
            visitDoctorComboBox.setPromptText("Brak dostępnych lekarzy");

        visitTimePicker.set24HourView(true);
        visitDatePicker.setValue(LocalDate.now());
        visitTimePicker.setValue(LocalTime.now());
        visitFilter();
    }


    /**
     * Metoda wczytująca dane pacjentów z bazy danych do listy zawierającej dane pacjentów.
     * Wczytane dane dodaję także odpowiednich komponenentów TableView.
     * @throws SQLException
     */
    private void loadPatientsData () throws SQLException
    {
        try (Connection connection = dbConnection.getConnection()){
            String sql = "select kraj, wojewodztwo, miasto, kod_pocztowy, ulica, nr_budynku, nr_lokalu, imie, nazwisko, numer_telefonu, pesel, " +
                    "miejsce_urodzenia, to_char(data_urodzenia, 'DD-MM-YYYY'), plec from adres join pacjent using (adres_id)";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {
                patientDataList.add(new PatientData(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                    resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),
                        resultSet.getString(12), resultSet.getString(13),resultSet.getString(14)));
            }
        }catch (SQLException ex) {
                ex.printStackTrace();
            }

            //Dla widoku pacjenci
            peselColumn.setCellValueFactory(new PropertyValueFactory<PatientData, String>("pesel"));
            nameColumn.setCellValueFactory(new PropertyValueFactory <PatientData, String> ("name"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory <PatientData, String> ("surname"));
            birthDataColumn.setCellValueFactory(new PropertyValueFactory<PatientData, String>("birthInformation"));
            sexColumn.setCellValueFactory(new PropertyValueFactory<PatientData, String>("sex"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<PatientData, String>("phoneNumber"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<PatientData, String>("totalAddress"));
            patientsTableView.setItems(patientDataList);
            //Dla widoku wizyty
            peselColumnVisit.setCellValueFactory(new PropertyValueFactory<PatientData, String>("pesel"));
            nameColumnVisit.setCellValueFactory(new PropertyValueFactory <PatientData, String> ("name"));
            surnameColumnVisit.setCellValueFactory(new PropertyValueFactory <PatientData, String> ("surname"));
            birthDataColumnVisit.setCellValueFactory(new PropertyValueFactory<PatientData, String>("birthInformation"));
            sexColumnVisit.setCellValueFactory(new PropertyValueFactory<PatientData, String>("sex"));
            phoneNumberColumnVisit.setCellValueFactory(new PropertyValueFactory<PatientData, String>("phoneNumber"));
            addressColumnVisit.setCellValueFactory(new PropertyValueFactory<PatientData, String>("totalAddress"));
            patientsTableViewVisit.setItems(patientDataList);
    }


    /**
     * Metoda wczytująca dane pacjentów z bazy danych do listy zawierającej dane Lekarzy.
     * @throws SQLException
     */
    private void loadDocktors() throws SQLException
    {
        try (Connection connection = dbConnection.getConnection()){
            String sql = "select imie, nazwisko from pracownik where stanowisko='Lekarz'";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {
                doctorsList.add(new String(resultSet.getString(1) +' '+ resultSet.getString(2)));
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metoda wczytująca dane pacjentów z bazy danych do listy zawierającej dane odnośnie wizyt.
     */
    private void loadVisitData()
    {
        try (Connection connection = dbConnection.getConnection()){
            String sql = "select wizyta.data_wizyty, pracownik.imie, pracownik.nazwisko, wizyta.pesel, pacjent.imie, " +
                    "pacjent.nazwisko, pacjent.numer_telefonu from wizyta join pacjent using (pesel) join pracownik using (pracownik_id);";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {
                visitsList.add(new Visit(resultSet.getTimestamp(1).toLocalDateTime().toLocalDate(), resultSet.getTimestamp(1).toLocalDateTime().toLocalTime(),
                        resultSet.getString(2) +" "+ resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7)));
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        peselColumnVisitTable.setCellValueFactory(new PropertyValueFactory<Visit, String>("peselNumber"));
        nameColumnVisitTable.setCellValueFactory(new PropertyValueFactory <Visit, String> ("patientName"));
        surnameColumnVisitTable.setCellValueFactory(new PropertyValueFactory <Visit, String> ("patientSurname"));
        phoneNumberColumnVisitTable.setCellValueFactory(new PropertyValueFactory<Visit, String>("phoneNumber"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Visit, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Visit, LocalTime>("time"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<Visit, String>("doctor"));

        visitTableView.setItems(visitsList);
    }

    /**
     * Metoda pomocnicza przy dodawaniu pacjenta do bazy danych. Każdy z nich posiada unikalny identyfikator adresu zamieszkania.
     * Metoda sprawdza czy wprowadzone dane do formularza już istnieją i mają swoją reprezentacje w bazie danych pod postacią identyfikatora.
     * @return Identyfikator adresu, który już istnieje w bazie danych. Jęśli takiego wpisu nie ma, zwraca 0.
     * @throws SQLException
     */
    private int getAddressId () throws SQLException {
        int resuldAdresId = 0;
        try (Connection connection = dbConnection.getConnection()) {
            String sqlGetAddresId = "select adres_id from adres where kraj=? and wojewodztwo=? and miasto=? and kod_pocztowy=? " +
                    "and ulica =? and nr_budynku=? and nr_lokalu=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAddresId);
            preparedStatement.setString(1, countryField.getText());
            preparedStatement.setString(2, voivodeshipField.getText());
            preparedStatement.setString(3, cityField.getText());
            preparedStatement.setString(4, zipCodeField.getText());
            preparedStatement.setString(5, streetField.getText());
            preparedStatement.setString(6, buildingNumberField.getText());
            preparedStatement.setString(7, apartmentNumberField.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                resuldAdresId = resultSet.getInt("adres_id");

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return resuldAdresId;
    }

    /**
     * Metoda dodająca pacjenta do bazy danych.
     * @param addressID unikalny identyfikator adresu
     * @throws SQLException
     */
    private void addPatient(int addressID) throws SQLException
    {
        try (Connection connection = dbConnection.getConnection()){
            String sqlInsertPatient = "insert into pacjent values (?,?,?,?,?,to_date(?,'DD-MM-YYYY'),?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertPatient);
            preparedStatement.setString(1, peselField.getText());
            preparedStatement.setString(2, nameField.getText());
            preparedStatement.setString(3, surnameField.getText());
            preparedStatement.setString(4, phoneNumberField.getText());
            preparedStatement.setInt(5, addressID);
            preparedStatement.setString(6, dateOfBirthField.getText());
            preparedStatement.setString(7, placeOfBirthField.getText());
            PatientData patientData = new PatientData();
            preparedStatement.setString(8, patientData.calculateSex(peselField.getText()));
            preparedStatement.execute();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Metoda dodający nowy adres do bazy danych.
     * @throws SQLException
     */
    private void addAddress() throws SQLException
    {
        try (Connection connection = dbConnection.getConnection()){
            String sqlInsertAddress = "insert into adres (kraj, wojewodztwo, miasto, kod_pocztowy, ulica, nr_budynku, nr_lokalu) " +
                    "values (?,?,?,?,?,?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertAddress);
            preparedStatement.setString(1, countryField.getText());
            preparedStatement.setString(2, voivodeshipField.getText());
            preparedStatement.setString(3, cityField.getText());
            preparedStatement.setString(4, zipCodeField.getText());
            preparedStatement.setString(5, streetField.getText());
            preparedStatement.setString(6, buildingNumberField.getText());
            preparedStatement.setString(7, apartmentNumberField.getText());
            preparedStatement.execute();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * Zdarzenie OnAction dla przycisku "Dodaj pacjenta"
     * @param event
     * @throws SQLException
     */
    @FXML
    void addPatientButtonOnAction(ActionEvent event) throws SQLException {

        if (PatientData.peselValidation(peselField.getText()) && !areFieldsEmptyPatientSection()) {

            try (Connection connection = dbConnection.getConnection()){

                int result = getAddressId();
                if (result!=0)
                {
                    addPatient(result);
                }
                else
                {
                    addAddress();
                    result = getAddressId();
                    addPatient(result);
                }

                patientDataList.add(new PatientData(countryField.getText(), voivodeshipField.getText(), cityField.getText(),
                        zipCodeField.getText(), streetField.getText(), buildingNumberField.getText(), apartmentNumberField.getText(),
                        nameField.getText(), surnameField.getText(), phoneNumberField.getText(), peselField.getText(), placeOfBirthField.getText()));

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "" + "Dodano pacjenta:\n" + nameField.getText() + " " +
                        surnameField.getText() + " o numerze pesel: " + peselField.getText() + " do bazy przychodni.", ButtonType.OK);
                alert.setHeaderText("Przychodnia - dodawanie pacjenta do bazy");
                alert.setTitle("Przychodnia | Recepcja");
                alert.showAndWait();
                clearFieldsPatientSection();
            } catch (SQLException ex) {
                labelFieldValidation.setText("Błąd wewnętrzny!");
                ex.printStackTrace();
            }
        }
        else
            labelFieldValidation.setText("Wypełnij poprawnie wszystkie wymagane pola.");
    }

    /**
     * Metoda czyszcząca zawartość pól w sekcji "Pacjenci".
     */
    private void clearFieldsPatientSection()
    {
        nameField.clear();
        surnameField.clear();
        streetField.clear();
        peselField.clear();
        phoneNumberField.clear();
        voivodeshipField.clear();
        cityField.clear();
        zipCodeField.clear();
        streetField.clear();
        buildingNumberField.clear();
        apartmentNumberField.clear();
        labelPeselValidation.setText("");
        labelFieldValidation.setText("");
        patientsTableView.getSelectionModel().clearSelection();
        dateOfBirthField.clear();
        placeOfBirthField.clear();
    }

    /**
     * Metoda czyszcząca zawartość pól w sekcji "Wizyty".
     */
    private void clearFieldsVisitSection()
    {
        visitName.clear();
        visitSurname.clear();
        visitPesel.clear();
        visitTimePicker.setValue(LocalTime.now());
        patientsTableViewVisit.getSelectionModel().clearSelection();
        tableViewPatientFilter.resetFilter();
    }


    /**
     * Zdarzenie OnAction dla przycisku "Wyczyść formularz" w sekcji "Pacjenci".
     * @param event
     */
    @FXML
    void clearFormButtonOnAction(ActionEvent event) {
        clearFieldsPatientSection();
    }


    /**
     * Zdarzenie OnKeyReleased dla pola PESEL w sekcji pacjenci.
     * Z każdym naciśniętym klawiszem sprawdzana jest suma kontrolna wprowadzonego numeru PESEL.
     * Informacja zwrotna jest przekazywana przez label.
     * @param event
     */
    @FXML
    private void peselFieldOnKeyReleased(KeyEvent event) {
        if (PatientData.peselValidation(peselField.getText()))
        {
            labelPeselValidation.setText("pesel poprawny");
            labelPeselValidation.setTextFill(Color.GREEN);
            PatientData patientData = new PatientData();
            dateOfBirthField.setText(patientData.calculateDateOfBirth(peselField.getText()));
        }
        else
        {
            labelPeselValidation.setText("pesel niepoprawny");
            labelPeselValidation.setTextFill(Color.RED);
        }
    }

    /**
     * Metoda sprawdzająca czy wszystkie wymagane pola służące do dodania pacjenta do bazy przychodni nie są puste.
     * @return True gdy któreś z pól jest puste.
     */
    private boolean areFieldsEmptyPatientSection()
    {
        return nameField.getText().isEmpty() || surnameField.getText().isEmpty() || peselField.getText().isEmpty() ||
                phoneNumberField.getText().isEmpty() || countryField.getText().isEmpty() || voivodeshipField.getText().isEmpty() ||
                cityField.getText().isEmpty() || zipCodeField.getText().isEmpty() || streetField.getText().isEmpty() ||
                buildingNumberField.getText().isEmpty() || placeOfBirthField.getText().isEmpty();
    }

    /**
     * Metoda sprawdzająca czy wszystkie wymagane pola służące do dodania wizyty do bazy przychodni nie są puste.
     * @return True gdy któreś z pól jest puste.
     */
    private boolean areFieldsEmptyVisitSection()
    {
        return visitName.getText().isEmpty() || visitSurname.getText().isEmpty() || visitPesel.getText().isEmpty() ||
                visitDatePicker.getValue() == null || visitTimePicker.getValue() == null || visitDoctorComboBox.getValue() == null ||
                visitPhoneNumber.getText().isEmpty();
    }

    /**
     * Zdarzenie OnMouseClicked obsługiwane przez TableView z sekcji "Wizyty". TableView reprezentuje listę pacjentów.
     * Podczas kliknięcia na danego pacjenta zostają automatycznie uzupełnione wszystkie wymagane pola służące do dodania nowej wizyty.
     * @param event
     */
    @FXML
    void patientsTableViewVisit_OnMouseClicked(MouseEvent event) {
        if (patientsTableViewVisit.getSelectionModel().getSelectedItem() != null)
        {
            PatientData patientData = patientsTableViewVisit.getSelectionModel().getSelectedItem();
            visitName.setText(patientData.getName());
            visitSurname.setText(patientData.getSurname());
            visitPesel.setText(patientData.getPesel());
            visitPhoneNumber.setText(patientData.getPhoneNumber());
        }
    }

    /**
     * Zdarzenie OnAction dla przycisku "Dodaj wizytę".
     * @param event
     */
    @FXML
    void addVisitButtoOnAction(ActionEvent event) {

        if (!areFieldsEmptyVisitSection())
        {
            String sqlGetEmployeeId = "select pracownik_id from pracownik where imie=? and nazwisko=?";
            String sqlInsertVisit = "insert into wizyta (pracownik_id, pesel, data_wizyty) values (?,?,?);";

            String [] NameSurname = visitDoctorComboBox.getValue().split(" ");
            int employee_id=1;

            try {
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlGetEmployeeId);

                preparedStatement.setString(1, NameSurname[0]);
                preparedStatement.setString(2, NameSurname[1]);
                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    employee_id = resultSet.getInt("pracownik_id");

                preparedStatement = connection.prepareStatement(sqlInsertVisit);
                preparedStatement.setInt(1, employee_id);
                preparedStatement.setString(2, visitPesel.getText());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(visitDatePicker.getValue().atTime(visitTimePicker.getValue())));
                preparedStatement.execute();



                visitsList.add(new Visit(visitDatePicker.getValue(), visitTimePicker.getValue(), visitDoctorComboBox.getValue(),
                        visitPesel.getText(), visitName.getText(), visitSurname.getText(), visitPhoneNumber.getText()));

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "" + "Dodano wizytę pacjenta:\n" + visitName.getText() +
                        " " + visitSurname.getText() + " o numerze pesel: " + visitPesel.getText() +".\n"+
                        "Dzień wizyty: " + Visit.formatData(visitDatePicker.getValue())+".\n" + "Godzina: "+
                        Visit.formatTime(visitTimePicker.getValue())+".", ButtonType.OK);
                alert.setHeaderText("Przychodnia - dodawanie wizyty do bazy");
                alert.setTitle("Przychodnia | Recepcja");
                alert.showAndWait();
                clearFieldsVisitSection();
            } catch (SQLException ex) {
                ex.printStackTrace();
                visitSectionVeryficationLabel.setText("Błąd wewnętrzny!");
            }
    }
        else
            visitSectionVeryficationLabel.setText("Wypełnij poprawnie wszystkie wymagane pola.");
        }


    /**
     * Zdarzenie OnAction dla pola visitDatePicker reprezentującego datę wizyty.
     * @param event
     */
    @FXML
    void visitDatePickerOnAction(ActionEvent event) {
        visitFilter();
    }

    /**
     * Metoda filtująca TableView odpowiedzialny za wyświetlanie wizyt.
     * Na daną tabele nakładany jest filtr w postaci daty wizyty. Data ta jest pobierana z pola visitDatePicker reprezentującego datę wizyty.
     */
    private void visitFilter()
    {
        tableViewVisitFilter.unSelectAllValues(dateColumn);
        tableViewVisitFilter.selectValue(dateColumn, visitDatePicker.getValue());
        tableViewVisitFilter.executeFilter();

        timeColumn.setSortType(TableColumn.SortType.ASCENDING);
        visitTableView.getSortOrder().setAll(timeColumn);
    }

    /**
     * Metoda zapisująca dane odnośnie wizyt. Są zapisywane wizyty jedynie z listy filtrującej.
     * To znaczy tylko te, które są aktualnie wyświetlane przez TableView odpowiadający za wyświetlanie listy wizyt.
     * @param event
     */
    @FXML
    void saveButtonOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File startDirectory = new File(System.getProperty("user.home")+"\\Desktop");
        fileChooser.setInitialDirectory(startDirectory);
       fileChooser.setInitialFileName("Wizyty " + Visit.formatData(visitDatePicker.getValue()));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Plik tekstowy (.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile!=null)
        {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile))){
                FilteredList<Visit> filterList = tableViewVisitFilter.getFilteredList();
                for (int i=0; i<filterList.size(); i++) {
                    bufferedWriter.append(String.format("%40s","*").replace(' ', '*'));
                    bufferedWriter.newLine();
                    bufferedWriter.append(peselColumnVisitTable.getText() + ':' +
                            String.format("%" + (40 - peselColumnVisitTable.getText().length()-1) +"s" , filterList.get(i).getPeselNumber()));
                    bufferedWriter.newLine();
                    bufferedWriter.append(nameColumnVisitTable.getText() + ':' +
                            String.format("%" + (40 - nameColumnVisitTable.getText().length()-1) +"s" , filterList.get(i).getPatientName()));
                    bufferedWriter.newLine();
                    bufferedWriter.append(surnameColumnVisitTable.getText() + ':' +
                            String.format("%" + (40 - surnameColumnVisitTable.getText().length()-1) +"s" , filterList.get(i).getPatientSurname()));
                    bufferedWriter.newLine();
                    bufferedWriter.append(phoneNumberColumnVisitTable.getText() + ':' +
                            String.format("%" + (40 - phoneNumberColumnVisitTable.getText().length()-1) +"s" , filterList.get(i).getPhoneNumber()));
                    bufferedWriter.newLine();
                    bufferedWriter.append(dateColumn.getText() + ':' +
                            String.format("%" + (40 - dateColumn.getText().length()-1) +"s" , Visit.formatData(filterList.get(i).getDate())));
                    bufferedWriter.newLine();
                    bufferedWriter.append(timeColumn.getText() + ':' +
                            String.format("%" + (40 - timeColumn.getText().length()-1) +"s" , filterList.get(i).getTime().toString()));
                    bufferedWriter.newLine();
                    bufferedWriter.append(doctorColumn.getText() + ':' +
                            String.format("%" + (40 - doctorColumn.getText().length()-1) +"s" , filterList.get(i).getDoctor()));
                    bufferedWriter.newLine();
                }
                }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}