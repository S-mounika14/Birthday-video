package com.birthday.video.maker.stickers;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.GenericTransitionOptions;
import com.birthday.video.maker.R;
import com.birthday.video.maker.comparator.LastModifiedFileComparator;
import com.birthday.video.maker.Birthday_Video.Constants;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;



public class LoveStickerFragment extends Fragment {
    private OnStickerItemClickedListener itemClickedListener;
    private ArrayList<String> paths;
    private String pathForStoringLoveStickersInPhone ;
    private GridView sticker_grid;
    public Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stickers_fragemnt, container, false);


        try {
            pathForStoringLoveStickersInPhone = Constants.getExternalString(context) + "/" + Constants.PROJECT_FOLDER + "/.LoveStickers/";
            File folder = new File(pathForStoringLoveStickersInPhone);
            if(!folder.exists()){
                //noinspection ResultOfMethodCallIgnored
                folder.mkdirs();
            }

            File[] list_files = folder.listFiles();


            if (list_files != null && list_files.length > 0) {
                paths = new ArrayList<>();
                paths.clear();

                for (File list_file : list_files) {
                    String fileName = list_file.getName();
                    if ((fileName.endsWith("png")) || fileName.endsWith("jpg"))
                        paths.add(list_file.getAbsolutePath());
                }

            }

            sticker_grid= rootView.findViewById(R.id.sticker_grid);
            if (paths != null)
            sticker_grid.setAdapter(new GridViewAdapter(getContext(),paths));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public static LoveStickerFragment createNewInstance() {

        return new LoveStickerFragment();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    private   class GridViewAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        private final ArrayList<String> image_paths;

        GridViewAdapter(Context context, ArrayList<String> paths) {
            this.context = context;
            this.image_paths=paths;
            itemClickedListener=(OnStickerItemClickedListener)context;

        }

        @Override
        public int getCount() {
            return image_paths.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {


            ImageView flag;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.sticker, parent, false);

            try {
                flag = itemView.findViewById(R.id.newtag);
                CardView main=itemView.findViewById(R.id.main);
                //  flag.setImageResource(thums[position]);
                // File file=new File( pathForStoringLoveStickersInPhone+"/"+paths.get(position));
                String str = paths.get(position).substring(0, paths.get(position).length() - 4);
                flag.setContentDescription("love"+str+" "+"sticker");

                Glide.with(context)
                        .load(paths.get(position))
                        .transition(GenericTransitionOptions.with(R.anim.zoomin_recycle))
                        .fitCenter()
                        .into(flag);

                main.setOnClickListener(arg0 -> new Handler(Looper.getMainLooper()).postDelayed(() -> itemClickedListener.onStickerItemClicked("LoveStickers", position, paths.get(position)),300));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return itemView;
        }
    }

    void refreshList(){

        File folder = new File(pathForStoringLoveStickersInPhone);
        if (!folder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
        }
        File[] list_files = folder.listFiles();


        if (list_files != null && list_files.length > 0) {
            paths = new ArrayList<>();
            paths.clear();
            Arrays.sort(list_files, LastModifiedFileComparator.LAST_MODIFIED_COMPARATOR);

            for (File list_file : list_files) {
                String fileName = list_file.getName();
                if ((fileName.endsWith("png")) || fileName.endsWith("jpg"))
                    paths.add(list_file.getAbsolutePath());
            }
            GridViewAdapter myAdapter = new GridViewAdapter(context, paths);
            sticker_grid.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }




    }
}
