package com.andrii_gerashchenko.weatherandrii.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrii_gerashchenko.weatherandrii.DTO.WeatherItem;
import com.andrii_gerashchenko.weatherandrii.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherItemAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<WeatherItem> mWeatherItems;

    public WeatherItemAdapter(Context context, List<WeatherItem> weatherItems) {
        mContext = context;
        mWeatherItems = weatherItems;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mWeatherItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mWeatherItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_weather, parent, false);
        }

        WeatherItem weatherItem = getWeatherItem(position);

        ((TextView) view.findViewById(R.id.tvLocation)).setText(weatherItem.getCity());
        ((TextView) view.findViewById(R.id.tvTemp)).setText(weatherItem.getTemp());
        ((TextView) view.findViewById(R.id.tvDate)).setText(weatherItem.getDate());

        Picasso.get()
                .load(weatherItem.getIcon())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into((ImageView) view.findViewById(R.id.ivWeather));

        return view;
    }

    WeatherItem getWeatherItem(int position) {
        return (WeatherItem) getItem(position);
    }
}
