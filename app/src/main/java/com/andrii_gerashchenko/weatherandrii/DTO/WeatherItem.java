package com.andrii_gerashchenko.weatherandrii.DTO;

public class WeatherItem {

    private String city;
    private String temp;
    private String date;
    private String icon;

    public WeatherItem(String city, String temp, String date, String icon) {
        this.city = city;
        this.temp = temp;
        this.date = date;
        this.icon = icon;
    }

    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }

    public String getDate() {
        return date;
    }

    public String getIcon() {
        return icon;
    }
}
