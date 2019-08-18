package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DecimalFormat;

public class PatientData extends Address{
    private StringProperty name;
    private StringProperty surname;
    private StringProperty phoneNumber;
    private StringProperty pesel;
    private StringProperty dateOfBirth;
    private StringProperty sex;
    private StringProperty birthplace;


    /**
     * Konstruktor służący do wczytywania wszystkich dotychczasowych danych z bazy danych.
     * Z jego pomocą będą tworzone obiekty, które znajdą się w liście pacjentów.
     */
    public PatientData(String country, String voivodeship, String city, String zipCode, String street, String buildingNumber, String apartmentBuilding,
                       String name, String surname, String phoneNumber, String pesel, String birthplace, String dateOfBirth, String sex) {
        super(country, voivodeship, city, zipCode, street, buildingNumber, apartmentBuilding);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.pesel = new SimpleStringProperty(pesel);
        this.birthplace = new SimpleStringProperty(birthplace);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.sex = new SimpleStringProperty(sex);
    }

    /**
     * Konstruktor służący do dodawania pacjenta przez formularz.
     * Pola data urdzin oraz płeć zostaną wygenerowane na podstawie numeru pesel.
     */
    public PatientData(String country, String voivodeship, String city, String zipCode, String street, String buildingNumber, String apartmentBuilding,
                       String name, String surname, String phoneNumber, String pesel, String birthplace) {
        super(country, voivodeship, city, zipCode, street, buildingNumber, apartmentBuilding);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.pesel = new SimpleStringProperty(pesel);
        this.birthplace = new SimpleStringProperty(birthplace);
        this.dateOfBirth = new SimpleStringProperty(calculateDateOfBirth(pesel));
        this.sex = new SimpleStringProperty(calculateSex(pesel));
    }


    public PatientData() {
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getPesel() {
        return pesel.get();
    }

    public StringProperty peselProperty() {
        return pesel;
    }

    /**
     * Metoda obliczająca sumę controlną numeru pesel.
     * @param pesel Numer PESEL.
     * @return wartość logiczna. True dla poprawnego numeru pesel. False dla niepoprawnego.
     */
    public static boolean peselValidation(String pesel)
    {
        if (pesel.matches("[0-9]+") && pesel.length()==11) {
            int result = 0;
            int scales[] = {9, 7, 3, 1, 9, 7, 3, 1, 9, 7};
            String peselArray[] = pesel.split("");
            for (int i = 0; i < pesel.length() - 1; i++) {
                result += scales[i] * Integer.parseInt(peselArray[i]);

            }
            return (result % 10) == Integer.parseInt(peselArray[10]);
        }
        return false;
    }

    /**
     * Metoda obliczająca datę urodzin na podstawie numeru PESEL.
     * @param pesel Numer PESEL.
     * @return Ciąg znaków reprezentujących datę urodzin w systemie europejskim.
     */
    public String calculateDateOfBirth(String pesel)
    {
            int day = Integer.parseInt(pesel.substring(4, 6));
            int mounth = Integer.parseInt(pesel.substring(2, 4));
            int year = Integer.parseInt(pesel.substring(0,2));
            if (mounth > 80 && mounth < 93) {
                year+=1800;
                mounth-=80;
            } else if (mounth > 0 && mounth < 13) {
                year+=1900;
            } else if (mounth > 20 && mounth < 33) {
                year+=2000;
                mounth-=20;
            } else if ( mounth > 40 && mounth < 53) {
                year+=2100;
                mounth-=40;
            } else if (mounth > 60 && mounth < 73) {
                year+=2200;
                mounth-=60;
            }

            return formatDateOfBirth(day, mounth, year);
    }

    /**
     * Metoda obliczająca płeć na podstawie numeru PESEL.
     * @param pesel Numer PESEL
     * @return Ciąg znaków reprezentujących płeć {Mężczyzna, Kobieta}.
     */
    public String calculateSex (String pesel)
    {
        if (Integer.parseInt(pesel.substring(9,10)) % 2 ==0)
            return "Kobieta";
        else
            return "Mężczyzna";
    }

    /**
     * Metoda konwertująca datę na system europejski.
     * @param day dzień
     * @param mounth miesiąc
     * @param year rok
     * @return Ciąg znaków reprezentujących datę w systemie europejskim
     */
    public String formatDateOfBirth (int day, int mounth, int year) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return new String(decimalFormat.format(day)+"-"+decimalFormat.format(mounth)+"-"+year);
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public String getBirthplace() {
        return birthplace.get();
    }

    public StringProperty birthplaceProperty() {
        return birthplace;
    }

    public String getBirthInformation()
    {
        return getDateOfBirth() + " " + getBirthplace();
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public StringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "PatientData{" +
                "name=" + name +
                ", surname=" + surname +
                ", phoneNumber=" + phoneNumber +
                ", pesel=" + pesel +
                ", country=" + " "+super.toString();
    }
}

