package com.andrii_gerashchenko.weatherandrii.DTO;

public class ChosenLocation {
    private String countryName;
    private String adminArea;
    private String locality;
    private double latitude;
    private double longitude;

    public ChosenLocation(String countryName, String adminArea, String locality, double latitude, double longitude) {
        this.countryName = countryName;
        this.adminArea = adminArea;
        this.locality = locality;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public String getLocality() {
        return locality;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
