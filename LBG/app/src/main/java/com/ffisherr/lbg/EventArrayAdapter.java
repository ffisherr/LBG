package com.ffisherr.lbg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class EventArrayAdapter extends ArrayAdapter<EventPesronse> {
    private final Context context;
    private final EventPesronse[] values;

    public EventArrayAdapter(Context context, EventPesronse[] values) {
        super(context, R.layout.event_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View eventView = inflater.inflate(R.layout.event_layout, parent, false);

        TextView textId    = eventView.findViewById(R.id.id_);
        TextView textDt    = eventView.findViewById(R.id.dt_);
        TextView textAbout = eventView.findViewById(R.id.about_);
        TextView textTitle = eventView.findViewById(R.id.title_);

        EventPesronse event = values[position];

        textId.setText(event.getId().toString());
        textDt.setText(event.getDt());
        textAbout.setText(event.getAbout());
        textTitle.setText(event.getTitle());

        return eventView;
    }

}
