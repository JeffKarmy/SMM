package com.jeffrkarmy.sheetmetalreference.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeffrkarmy.sheetmetalreference.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by jrkar on 8/16/2016.
 */
public class customAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private HistoryDB[] historyDBList;

    public customAdapter(Context context, HistoryDB[] list) {
        if (context != null && list != null) {
            this.context = context;
            this.historyDBList = list;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

    }

    @Override
    public int getCount() {
        return historyDBList.length;
    }

    @Override
    public Object getItem(int position) {
        return historyDBList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            View view = convertView;

            DecimalFormat df = new DecimalFormat("#.###");

            df.setRoundingMode(RoundingMode.CEILING);

            if (view == null)
                view = inflater.inflate(R.layout.row, null);

            TextView mt = (TextView) view.findViewById(R.id.textViewMT);
            TextView r = (TextView) view.findViewById(R.id.textViewRadius);
            TextView a = (TextView) view.findViewById(R.id.textViewAngle);
            TextView bd = (TextView) view.findViewById(R.id.textViewBD);
            TextView halfBD = (TextView) view.findViewById(R.id.textViewHalfBD);
            TextView date = (TextView) view.findViewById(R.id.textViewDate);

            mt.setText(df.format(historyDBList[position].getMaterialThickness()));
            a.setText(df.format(historyDBList[position].getAngle()));  /**TODO: need the unicode char \u00B0 the degree sigh**/
            r.setText(df.format(historyDBList[position].getRadius()));
            bd.setText(df.format(historyDBList[position].getBendDeduction()));
            halfBD.setText((df.format(historyDBList[position].getBendDeduction() / 2)));
            date.setText(historyDBList[position].getDate());

            return view;
        } catch (Exception ex) {
            return null;
        }

    }

}
