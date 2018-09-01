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

    public class Wind{
        Double speed;
    }

    public class WeatherDescription {
        String icon;
    }

    public WeatherLocation(Main temp, List<WeatherDescription> desctiption) {
        this.temp = temp;
        this.desctiption = desctiption;
    }

    public Calendar getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timestamp * 1000);
        return date;
    }

    public String getTemp() { return String.valueOf(temp.temp); }

    public String getPerssure() {return  String.valueOf(temp.pressure);}

    public String getTempMin() { return String.valueOf(temp.temp_min); }

    public String getWindSpeed() { return String.valueOf(wind.speed);}

    public String getTempMax() { return String.valueOf(temp.temp_max); }

    public String getTempInteger() { return String.valueOf(temp.temp.intValue()); }

    public String getTempWithDegree() { return String.valueOf(temp.temp.intValue()) + "\u00B0"; }

    public String getCity() { return city; }

    public String getIcon() { return desctiption.get(0).icon; }

    public String getIconUrl() {
        return "http://openweathermap.org/img/w/" + desctiption.get(0).icon + ".png";
    }

// "weather":[{
// "id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations",
// "main":{"temp":285.514,"pressure":1013.75,"humidity":100,"temp_min":285.514,"temp_max":285.514,"sea_level":1023.22,"grnd_level":1013.75},
// "wind":{"speed":5.52,"deg":311},"clouds":{"all":0},"dt":1485792967,
// "sys":{"message":0.0025,"country":"JP","sunrise":1485726240,"sunset":1485763863},
// "id":1907296,"name":"Tawarano","cod":200}
}
