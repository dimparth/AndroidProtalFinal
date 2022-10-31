package com.example.androidprotalfinal.implementation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.models.CriticalResponse;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private final List<CriticalResponse> informationList;
    private final LayoutInflater layoutInflater;

    public MyAdapter(List<CriticalResponse> informationList, Context context) {
        this.informationList = informationList;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return informationList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) throws NullPointerException{
        view = layoutInflater.inflate(R.layout.layout_list, null);
        TextView infoText = view.findViewById(R.id.listText);
        TextView infoText2 = view.findViewById(R.id.listText2);
        TextView infoText3 = view.findViewById(R.id.listText3);
        infoText.setText(String.format("Area: %s", informationList.get(i).Locality));
        infoText2.setText(String.format("Incidents: %s", informationList.get(i).DangerCount));
        infoText3.setText(String.format("Category: %s", informationList.get(i).Category));
        return view;
    }
}
