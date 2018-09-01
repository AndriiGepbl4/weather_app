package com.andrii_gerashchenko.weatherandrii.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

public class WeatherLocation {
    @SerializedName("main")
    private Main temp;

    @SerializedName("weather")
    private List<WeatherDescription> desctiption;

    @SerializedName("name")
    private String city;

    @SerializedName("dt")
    private long timestamp;

    @SerializedName("wind")
    private Wind wind;

    public class Main {
        Double temp;
        Double temp_min;
        Double temp_max;
        Double pressure;
    }

    public class Wind {
        Double speed;
    }

    public class WeatherDescription {
        String icon;
    }

    public WeatherLocation(Main temp, List<WeatherDescription> desctiption) {
        this.temp = temp;
        this.desctiption = desctiption;
    }

    public String getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timestamp * 1000);

        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);
        int hour = date.get(Calendar.HOUR);
        int minute = date.get(Calendar.MINUTE);

        String strDate = day + "/" + month + "/" + year
                + " " + hour + ":" + minute;
        return strDate;
    }

    public String getTemp() {
        return String.valueOf(temp.temp);
    }

    public String getPerssure() {
        return String.valueOf(temp.pressure);
    }

    public String getTempMin() {
        return String.valueOf(temp.temp_min);
    }

    public String getWindSpeed() {
        return String.valueOf(wind.speed);
    }

    public String getTempMax() {
        return String.valueOf(temp.temp_max);
    }

    public String getTempInteger() {
        return String.valueOf(temp.temp.intValue());
    }

    public String getTempWithDegree() {
        return String.valueOf(temp.temp.intValue()) + "\u00B0";
    }

    public String getCity() {
        return city;
    }

    public String getIcon() {
        return desctiption.get(0).icon;
    }

    public String getIconUrl() {
        return "http://openweathermap.org/img/w/" + desctiption.get(0).icon + ".png";
    }
}