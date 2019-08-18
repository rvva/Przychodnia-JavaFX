package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Address {
    protected StringProperty country;
    protected StringProperty voivodeship;
    protected StringProperty city;
    protected StringProperty zipCode;
    protected StringProperty street;
    protected StringProperty buildingNumber;
    protected StringProperty apartmentNumber;


    public Address(String country, String voivodeship, String city, String zipCode, String street, String buildingNumber, String apartmentNumber) {
        this.country = new SimpleStringProperty(country);
        this.voivodeship = new SimpleStringProperty(voivodeship);
        this.city = new SimpleStringProperty(city);
        this.zipCode = new SimpleStringProperty(zipCode);
        this.street = new SimpleStringProperty(street);
        this.buildingNumber = new SimpleStringProperty(buildingNumber);
        this.apartmentNumber = new SimpleStringProperty(apartmentNumber);
    }

    public Address() {
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public String getVoivodeship() {
        return voivodeship.get();
    }

    public StringProperty voivodeshipProperty() {
        return voivodeship;
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getZipCode() {
        return zipCode.get();
    }

    public StringProperty zipCodeProperty() {
        return zipCode;
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public String getBuildingNumber() {
        return buildingNumber.get();
    }

    public StringProperty buildingNumberProperty() {
        return buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber.get();
    }

    public StringProperty apartmentNumberProperty() {
        return apartmentNumber;
    }

    public String getTotalAddress()
    {
        return "ul. "+ getStreet() + " " + getBuildingNumber() + (getApartmentNumber().equals("")?"\n":"/"+getApartmentNumber()+"\n")
                +getZipCode()+" "+getCity()+ " "+getCountry()+ ", woj. "+getVoivodeship();
    }

    @Override
    public String toString() {
        return "Address{" +
                "country=" + country +
                ", voivodeship=" + voivodeship +
                ", city=" + city +
                ", zipCode=" + zipCode +
                ", street=" + street +
                ", buildingNumber=" + buildingNumber +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }
}
