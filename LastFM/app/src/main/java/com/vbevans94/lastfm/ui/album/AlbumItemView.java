package com.vbevans94.lastfm.ui.album;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vbevans94.lastfm.R;
import com.vbevans94.lastfm.data.api.entity.Album;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AlbumItemView extends RelativeLayout {

    @InjectView(R.id.image_art)
    ImageView imageArt;

    @InjectView(R.id.text_name)
    TextView textName;

    @InjectView(R.id.text_count)
    TextView textCount;

    public AlbumItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.inject(this);
    }

    public void bind(Album album, Picasso picasso) {
        String imageUrl = album.getImageUrl();
        picasso.load(imageUrl)
                .into(imageArt);

        textName.setText(album.getName());
        textCount.setText(getContext().getString(R.string.title_listened, album.getPlaycount()));
    }
}
