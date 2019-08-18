package data;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class Visit {
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<LocalTime> time;
    private StringProperty doctor;
    private StringProperty peselNumber;
    private StringProperty patientName;
    private StringProperty patientSurname;
    private StringProperty phoneNumber;

    public Visit(LocalDate date, LocalTime time, String doctor, String peselNumber, String patientName, String patientSurname, String phoneNumber) {
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.doctor = new SimpleStringProperty(doctor);
        this.peselNumber = new SimpleStringProperty(peselNumber);
        this.patientName = new SimpleStringProperty(patientName);
        this.patientSurname = new SimpleStringProperty(patientSurname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    /**
     * Metoda konwertująca datę na system europejski.
     * @param date data typu LocalDate
     * @return Ciąg znaków reprezentujących datę w systemie europejskim
     */
    public static String formatData(LocalDate date)
    {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(date.getDayOfMonth())+"-"+decimalFormat.format(date.getMonthValue())+"-"+date.getYear();
    }

    public static String formatTime(LocalTime time)
    {
        return time.getHour()+":"+time.getMinute();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public String getDoctor() {
        return doctor.get();
    }

    public StringProperty doctorProperty() {
        return doctor;
    }

    public String getPeselNumber() {
        return peselNumber.get();
    }

    public StringProperty peselNumberProperty() {
        return peselNumber;
    }

    public String getPatientName() {
        return patientName.get();
    }

    public StringProperty patientNameProperty() {
        return patientName;
    }

    public String getPatientSurname() {
        return patientSurname.get();
    }

    public StringProperty patientSurnameProperty() {
        return patientSurname;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }
}
