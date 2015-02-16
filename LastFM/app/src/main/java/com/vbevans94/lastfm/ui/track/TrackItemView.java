package com.vbevans94.lastfm.ui.track;

import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vbevans94.lastfm.R;
import com.vbevans94.lastfm.data.api.entity.Track;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TrackItemView extends RelativeLayout {

    private final static String DURATION_FORMAT = "%d:%02d";

    @InjectView(R.id.text_no)
    TextView textNo;

    @InjectView(R.id.text_name)
    TextView textName;

    @InjectView(R.id.text_duration)
    TextView textDuration;

    public TrackItemView(Context context, AttributeSet attrs) {
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

    public void bind(Track track, int position) {
        textName.setText(track.getName());
        textNo.setText(String.valueOf(position + 1));

        int duration = Integer.parseInt(track.getDuration());
        int minutes = duration / 60;
        int seconds = duration % 60;
        textDuration.setText(String.format(DURATION_FORMAT, minutes, seconds));
    }
}
