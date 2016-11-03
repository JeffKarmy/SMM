package com.jeffrkarmy.sheetmetalreference.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeffrkarmy.sheetmetalreference.R;


/**
 * Created by jrkar on 8/26/2016.
 */
public class customAdapterAlloy extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context context;
    AlloyOjb[] alloyList;

    public customAdapterAlloy(Context context, AlloyOjb[] list) {
        this.context = context;
        this.alloyList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alloyList.length;
    }

    @Override
    public Object getItem(int position) {
        return alloyList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            View view = convertView;

            if (view == null)
                view = inflater.inflate(R.layout.alloy, null);
            TextView alloy = (TextView) view.findViewById(R.id.textViewAlloy);
            TextView thk = (TextView) view.findViewById(R.id.textViewAlloyTHK);
            TextView radius = (TextView) view.findViewById(R.id.textViewAlloyRadius);

            String metal = (Integer.toString(alloyList[position].getAlloy()) + alloyList[position].getTemper());

            alloy.setText(metal);
            thk.setText(alloyList[position].getThickness());
            radius.setText(alloyList[position].getRadius());

            return view;
        } catch (Exception ex) {
            String e = ex.toString();
            View view = null;
            return view;
        }

    }
}
