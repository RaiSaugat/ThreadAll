package com.example.rai.threaddemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by sangitashres on 2/21/2017.
 */
public class CustomAdapter extends BaseAdapter {
    String[] teamName, points, images;
    LayoutInflater inflater;
    Context ctx;
    public CustomAdapter(Context c, String[] t, String[] p, String[] i) {
        ctx = c;
        teamName= t;
        points = p;
        images = i;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return teamName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi;
        vi = inflater.inflate(R.layout.teamlayout,null);
        TextView titleTeam = (TextView)vi.findViewById(R.id.titleTeam);
        TextView titlePoint = (TextView)vi.findViewById(R.id.titlePoint);
        TextView imgt = (TextView)vi.findViewById(R.id.imgPath);
        ImageView im = (ImageView)vi.findViewById(R.id.logo);
        titleTeam.setText(teamName[position]);
        titlePoint.setText(points[position]);
//        imgt.setText(images[position]);
        Picasso.with(ctx).load(images[position]).into(im);
        return vi;
    }
}
