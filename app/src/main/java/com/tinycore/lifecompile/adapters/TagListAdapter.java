package com.tinycore.lifecompile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.models.Tag;

import java.util.ArrayList;

public class TagListAdapter extends ArrayAdapter<Tag> {
    public TagListAdapter(Context context, ArrayList<Tag> users) {
        super(context, 0, users);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tag tag = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_note, parent, false);
        }

        TextView textViewName = (TextView) convertView.findViewById(R.id.noteContent);
        textViewName.setText(tag.Name);

        return convertView;
    }
}