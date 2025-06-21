package com.birthday.video.maker.Birthday_Video.filters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Video.activity.Pojo.EditValues;
import com.birthday.video.maker.R;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class FilterAdapter extends BaseAdapter<FilterModel> {

    private int positionSelect =0;
    private int oldPosition = 0;
    private OnItemFilterClickedListener onItemFilterClickedListener;
    ArrayList<EditValues> imagesList = new ArrayList<>();
    private Context activity;
    private List<String> filterNames;

    public FilterAdapter(Activity activity, List<String> filterNames) {
        super(activity);
        this.activity = activity;
        this.filterNames = filterNames;
    }

    public void setOnItemFilterClickedListener(OnItemFilterClickedListener onItemFilterClickedListener) {
        this.onItemFilterClickedListener = onItemFilterClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_recycle_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
            ViewHolder viewHolder = (ViewHolder) holder;



            Glide.with(activity)
                    .load(list.get(position).getImg())
                    .transition(GenericTransitionOptions.with(R.anim.zoomin_recycle))
                    .fitCenter()
                    .into(viewHolder.iv);

            viewHolder.textView.setText(filterNames.get(position));



            if (position == positionSelect)
                viewHolder.item.setBackgroundColor(ContextCompat.getColor(activity, R.color.filter_selected_color));
            else
                viewHolder.item.setBackgroundColor(ContextCompat.getColor(activity, R.color.fonts_rec_bgcolor));

            viewHolder.item.setOnClickListener(view -> {

                try {
                    if (position == positionSelect) return;

                    oldPosition = positionSelect;
                    positionSelect = position;

                    notifyItemChanged(oldPosition);
                    notifyItemChanged(position);

                    if (onItemFilterClickedListener != null)
                        onItemFilterClickedListener.onItemFilterClicked(position,list.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        LinearLayout item;
        TextView textView;

        ViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.effect);
            item = view.findViewById(R.id.item);
            textView=view.findViewById(R.id.new_text);

        }
    }

    public boolean refreshFilterPosition(int position) {

        if (positionSelect == position) {
            return false;
        } else {
            oldPosition = positionSelect;
            positionSelect = position;
            notifyItemChanged(oldPosition);
            notifyItemChanged(position);


            return true;
        }
    }

    public FilterModel getCurrentFilterModel(int currentPosition){
        return list.get(currentPosition);
    }

    public int getPositionSelect() {
        return positionSelect;
    }

    public int getPositionOld() {
        return oldPosition;
    }

    public interface OnItemFilterClickedListener {
        void onItemFilterClicked(int position,FilterModel filterModel);
    }
}
