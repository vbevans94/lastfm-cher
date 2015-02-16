package com.vbevans94.lastfm.ui.album;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;
import com.vbevans94.lastfm.R;
import com.vbevans94.lastfm.data.api.entity.Album;

public class AlbumsAdapter extends ArrayAdapter<Album> {
    private final Picasso picasso;

    public AlbumsAdapter(Context context, Picasso picasso) {
        super(context, R.layout.item_album);

        this.picasso = picasso;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.item_album, null);
        }
        AlbumItemView itemView = (AlbumItemView) view;
        itemView.bind(getItem(position), picasso);

        return view;
    }
}
