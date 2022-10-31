package com.example.androidprotalfinal.implementation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.models.UsersListResponse;


public class UsersAdapter extends BaseAdapter {
    private final UsersListResponse usersListResponse;
    private final LayoutInflater layoutInflater;

    public UsersAdapter(UsersListResponse usersListResponse, Context context) {
        this.usersListResponse = usersListResponse;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return usersListResponse.users.size();
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
        infoText.setText(String.format("Username: %s", usersListResponse.users.get(i).username));
        infoText2.setText(String.format("Role: %s", usersListResponse.users.get(i).role));
        return view;
    }
}
