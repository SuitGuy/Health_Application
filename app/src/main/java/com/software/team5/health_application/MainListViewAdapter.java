package com.software.team5.health_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * MainListViewAdapter
 * The reload adapter for MainListView
 *
 * Created by chenzitai on 18/02/16.
 */
public class MainListViewAdapter extends ArrayAdapter{
    List list = new ArrayList();

    static class DataHandler{
        ImageView icon;
        TextView name;
        TextView figure;
    }
    public MainListViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add (Object object){
        super.add(object);
        list.add(object);
    }
    @Override
    public void remove (Object object){
        super.remove(object);
        list.remove(object);
    }
    @Override
    public void clear(){
        super.clear();
        list.clear();
    }
    @Override
    public int getCount(){
        return this.list.size();
    }
    @Override
    public  Object getItem(int psition){
        return this.list.get(psition);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        DataHandler handler;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_row_layout,parent,false);
            handler = new DataHandler();
            handler.icon = (ImageView)row.findViewById(R.id.health_icon);
            handler.name = (TextView)row.findViewById(R.id.health_name);
            handler.figure = (TextView)row.findViewById(R.id.health_figure);
            row.setTag(handler);
        }
        else{
            handler = (DataHandler)row.getTag();
        }
        MainListViewProvider dataProvider;
        dataProvider = (MainListViewProvider)this.getItem(position);
        handler.icon.setImageResource(dataProvider.getHealth_icon_resource());
        handler.name.setText(dataProvider.getHealth_name());
        handler.figure.setText(dataProvider.getHealth_figre());

        return row;
    }
}
