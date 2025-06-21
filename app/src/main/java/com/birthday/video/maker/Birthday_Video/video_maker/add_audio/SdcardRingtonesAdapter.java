package com.birthday.video.maker.Birthday_Video.video_maker.add_audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.birthday.video.maker.Birthday_Video.video_maker.data.MusicData;
import com.birthday.video.maker.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SdcardRingtonesAdapter extends BaseAdapter
{
    private List<MusicData> ringtones;
    private List<MusicData> ringtones_check;
    private Context context;
    private MediaPlayer mediaPlayer;
    private SparseBooleanArray playingRingtoneIds;
    private MediaPlayer mPlayer;
    private boolean earlier_boolean;
    private int earlier=0;
    protected OnTextItemClickListener onTextItemClickListener;


    public void setOnTextItemClickListener(OnTextItemClickListener onTextItemClickListener) {
        this.onTextItemClickListener = onTextItemClickListener;
    }
    public interface OnTextItemClickListener {
        void onTextItemClick(ImageView imageview,int position);
    }

    public SdcardRingtonesAdapter(List<MusicData> ringtones, MediaPlayer mediaPlayer, Context context) {
        this.ringtones = ringtones;
        this.context = context;
        this.mediaPlayer = mediaPlayer;
        playingRingtoneIds = new SparseBooleanArray();
        ringtones_check=new ArrayList<MusicData>();
        ringtones_check.addAll(ringtones);
    }

    public void togglePlaying(int position)
    {
        try
        {
            selectView(position, !playingRingtoneIds.get(position));
        }
        catch (Exception e)
        {
        }
    }


    public void selectView(int position, boolean value) {
        try {
            playingRingtoneIds.clear();
            if (value)
            {
                playingRingtoneIds.put(position, true);
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopAllPlaying() {

        if (mPlayer!=null)
        {
            mPlayer.pause();
            mPlayer=null;
        }

        playingRingtoneIds.clear();
    }

    @Override
    public int getCount() {
        return ringtones_check.size();
    }

    @Override
    public Object getItem(int position) {
        return ringtones_check.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.ringtone_list_row, parent, false);
            holder = new ViewHolder();
            holder.playButton = (ImageView) convertView.findViewById(R.id.play_button);
            holder.ringtoneTitle = (TextView) convertView.findViewById(R.id.ringtone_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.playButton.setImageResource(playingRingtoneIds.get(position) ? R.drawable.pause : R.drawable.play);

        holder.playButton.setOnClickListener(v -> {

            try {
                if (earlier_boolean && position!=earlier && mPlayer!=null)
                {
                    mPlayer.pause();
                    mPlayer=null;

                    Uri uri= Uri.parse(ringtones_check.get(position).track_data);
                    mPlayer = MediaPlayer.create(context, uri);
                    if (mPlayer!=null) {
                        mPlayer.setLooping(true);
                    }

                    try
                    {
                        if (mPlayer!=null)
                            mPlayer.prepare();
                        return;
                    }
                    catch (IllegalStateException e2)
                    {

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    if (!playingRingtoneIds.get(position))
                    {
                        if (mPlayer!=null)
                            mPlayer.start();
                    }
                }
                else
                {
                    if (mPlayer==null)
                    {
                        Uri uri= Uri.parse(ringtones_check.get(position).track_data);
                        mPlayer = MediaPlayer.create(context, uri);
                        if (mPlayer!=null)
                        {
                            mPlayer.setLooping(true);
                        }

                        try
                        {
                            if (mPlayer!=null)
                                mPlayer.prepare();
                            return;
                        }
                        catch (IllegalStateException e2)
                        {

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (!playingRingtoneIds.get(position))
                        {
                            if (mPlayer!=null)
                                mPlayer.start();
                        }
                    }
                    else
                    {
                        mPlayer.pause();
                        mPlayer=null;
                    }
                }


                earlier_boolean=true;
                earlier=position;


                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        togglePlaying(position);
                    }
                });

                if (!playingRingtoneIds.get(position))
                {
                    ringtones_check.get(position).play(context, mediaPlayer);
                }

                togglePlaying(position);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });

        holder.ringtoneTitle.setOnClickListener(view -> onTextItemClickListener.onTextItemClick(holder.playButton,position));

        // ringtoneTitle
        holder.ringtoneTitle.setText(ringtones_check.get(position).getTrack_Title());

        return convertView;
    }

    public List<MusicData> filtere(String charText)
    {
        if (mPlayer!=null)
        {
            if (mPlayer.isPlaying())
            {
                mPlayer.pause();
                mPlayer=null;


                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        togglePlaying(earlier);
                    }
                });


                if (!playingRingtoneIds.get(earlier))
                {
                    ringtones_check.get(earlier).play(context, mediaPlayer);
                }

                togglePlaying(earlier);

            }
        }




        charText = charText.toLowerCase(Locale.getDefault());
        ringtones_check.clear();
        if (charText.length() == 0)
        {
            ringtones_check.addAll(ringtones);
        }
        else
        {
            char[] ca=charText.toCharArray();



            for (MusicData wp : ringtones)
            {


                if (wp.getTrack_displayName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    ringtones_check.add(wp);
                }



            }
        }
        notifyDataSetChanged();

        return ringtones_check;
    }

    private class ViewHolder {
        ImageView playButton;
        TextView ringtoneTitle;
    }
}
