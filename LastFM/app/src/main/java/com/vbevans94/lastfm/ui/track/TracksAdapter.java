package com.vbevans94.lastfm.ui.track;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vbevans94.lastfm.R;
import com.vbevans94.lastfm.data.api.entity.Track;

public class TracksAdapter extends ArrayAdapter<Track> {

    public TracksAdapter(Context context) {
        super(context, R.layout.item_track);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.item_track, null);
        }
        TrackItemView itemView = (TrackItemView) view;
        itemView.bind(getItem(position), position);
        return view;
    }
}
