package com.birthday.video.maker.Birthday_Video.customgallery;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import com.birthday.video.maker.Birthday_Video.activity.GridBitmaps_Activity2;
import com.birthday.video.maker.Birthday_Video.customgallery.hlistview.widget.HListView;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.async.AsyncTask;
import com.birthday.video.maker.marshmallow.MyMarshmallow;

import java.util.ArrayList;
import java.util.HashSet;


public class GalleryViewVideo implements OnClickListener {

    private final Context myContext;
    private final TextView mImageNext;
    private AlbumAdapter albumAdapter;
    private boolean f56b;
    private final GalleryActivityVideo galleryActivity;
    private GridAdapter gridAdapter;
    private ArrayList<Uri> imageUris;
    private ArrayList<Uri> getImageUris;
    private boolean isAlbumView;
    private final ListView listView;
    private SubAlbumAdapter mAdapter;
    private ArrayList<Album> mAlbumsList;
    private final GridView mGridView;
    private final CardView main;
    private final LinearLayout mLinearBottom;
    private final int mMaxPhotoSelection;
    private final LinearLayout mView;
    private final TextView textViewEmpty;
    private RelativeLayout.LayoutParams params;
    private final HListView mHListView;
    private int albumPosition;
    private final LinearLayout bottomLayout;
    private final LinearLayout toolLayout;
    private BackgroundTask backgroundTask;

    class C05931 implements OnClickListener {
        private final Context val$context;

        C05931(Context context) {
            this.val$context = context;
        }

        public void onClick(View v) {


            if (GalleryViewVideo.this.mAdapter.mList.size() > 0) {

                try {
                    try {
                        if (GalleryViewVideo.this.mAdapter.mList.size() >= 3) {
                            Intent intent = new Intent(galleryActivity, GridBitmaps_Activity2.class);
                            intent.putExtra("values", mAdapter.mList);
//                           intent.putParcelableArrayListExtra("multipleImage", arrayList);
                            GalleryViewVideo.this.mView.getContext().startActivity(intent);
                            galleryActivity.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                        } else {
                            LayoutInflater inflater = galleryActivity.getLayoutInflater();
                            View layout = inflater.inflate(R.layout.cust_toast,
                                    (ViewGroup) galleryActivity.findViewById(R.id.toast_layout_root));

                            TextView text = (TextView) layout.findViewById(R.id.text);
                            text.setText(val$context.getString(R.string.please_select_atleast_three_images));

                            Toast toast = new Toast(galleryActivity);
                            toast.setGravity(Gravity.BOTTOM, 0, 300);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                        }
                    } catch (Exception ignored) {
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                try {
                    GalleryViewVideo.this.mAdapter.mList.size();
                    Toast.makeText(mView.getContext().getApplicationContext(), "Please select images", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class C05942 implements OnItemClickListener {
        C05942() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (GalleryViewVideo.this.isAlbumView) {
                albumPosition = position;
                GalleryViewVideo.this.refreshAlbumListSelection(position);
                GalleryViewVideo.this.fillGallery(GalleryViewVideo.this.mAlbumsList.get(position).albumId);
                GalleryViewVideo.this.f56b = false;
            }
        }
    }

    class C05953 implements OnScrollListener {
        C05953() {
        }

        public void onScrollStateChanged(AbsListView listView, int scrollState) {
            GalleryViewVideo.this.f56b = true;
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    class C05964 implements OnItemClickListener {
        private final Context val$context;


        C05964(Context context) {
            this.val$context = context;
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {

            try {

                Uri uri = GalleryViewVideo.this.imageUris.get(position);
                GalleryViewVideo.this.addElements(uri);

                ((GalleryActivityVideo) this.val$context).onPhotoSelection(GalleryViewVideo.this.mAdapter.getCount());


            } catch (Exception ignored) {

            }

        }
    }


    class C05986 implements Runnable {
        private final int val$position;

        C05986(int i) {
            this.val$position = i;
        }

        public void run() {

            GalleryViewVideo.this.listView.smoothScrollToPosition(this.val$position);
        }
    }

    class C05997 implements Runnable {
        private final int val$position;

        C05997(int i) {
            this.val$position = i;
        }

        public void run() {
            GalleryViewVideo.this.listView.smoothScrollToPosition(this.val$position);
        }
    }


    private static class Album {
        String albumId;
        private String albumName;
        private String imageUrl;
        private boolean selected;

        void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @SuppressWarnings("unused")
        public String getImageUrl() {
            return this.imageUrl;
        }

        public boolean isSelected() {
            return this.selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    private class AlbumAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater mInflater;
        ArrayList<Album> mList;

        AlbumAdapter(Context context, ArrayList<Album> imageList) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
            this.mList = new ArrayList<>();
            this.mList = imageList;
        }

        public int getCount() {
            return this.mList.size();
        }

        public Album getItem(int position) {
            return this.mList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = this.mInflater.inflate(R.layout.grid_collage_row_album_item_video, null);
                GalleryViewVideo.this.f56b = true;
            }
            ImageView imageView = convertView.findViewById(R.id.imageView1);
            TextView textview = convertView.findViewById(R.id.albumName);
            if (this.mList.get(position).isSelected()) {
                convertView.setBackgroundResource(R.drawable.gallery_click_background);

            } else {
                convertView.setBackgroundResource(R.drawable.gallery_grid_background);
            }
            if (GalleryViewVideo.this.f56b) {
                Glide.with(mContext)
                        .load(this.mList.get(position).imageUrl)
                        .into(imageView);
            }

            textview.setText(this.mList.get(position).albumName);
            return convertView;
        }
    }

    private class GridAdapter extends BaseAdapter {
        private final DisplayMetrics displaymetrics;
        OnCheckedChangeListener mCheckedChangeListener;
        Context mContext;
        LayoutInflater mInflater;
        ArrayList<Uri> mList;
        SparseBooleanArray mSparseBooleanArray;

        class C06001 implements OnCheckedChangeListener {
            C06001() {
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GridAdapter.this.mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
                GalleryViewVideo.this.gridAdapter.getCheckedItems().size();
            }
        }

        GridAdapter(Context context, ArrayList<Uri> imageList) {
            this.mCheckedChangeListener = new C06001();
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
            this.mSparseBooleanArray = new SparseBooleanArray();
            this.mList = new ArrayList<>();
            this.mList = imageList;
            this.displaymetrics = mContext.getResources().getDisplayMetrics();
        }

        ArrayList<Uri> getCheckedItems() {
            ArrayList<Uri> mTempArray = new ArrayList<>();
            for (int i = 0; i < this.mList.size(); i++) {
                if (this.mSparseBooleanArray.get(i)) {
                    mTempArray.add(this.mList.get(i));
                }
            }
            return mTempArray;
        }

        public int getCount() {
            return GalleryViewVideo.this.imageUris.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = this.mInflater.inflate(R.layout.grid_collage_row_multiphoto_item_video, null);
            }
            try {
                ToggleButton mButton = convertView.findViewById(R.id.toggleButton1);

                RelativeLayout grid_relative = convertView.findViewById(R.id.grid_relative);
                grid_relative.getLayoutParams().width = (int) (displaymetrics.widthPixels / 2f);
                grid_relative.getLayoutParams().height = (int) (displaymetrics.widthPixels / 3f);

//                if (params != null)
//                    grid_relative.setLayoutParams(params);

                try {
                    Glide.with(mContext)
                            .load(GalleryViewVideo.this.imageUris.get(position))
                            .placeholder(R.drawable.loading)
                            .into((ImageView) convertView.findViewById(R.id.imageView1));
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    Glide.with(mContext)
                            .load(GalleryViewVideo.this.imageUris.get(0))
                            .placeholder(R.drawable.loading)
                            .into((ImageView) convertView.findViewById(R.id.imageView1));
                }
                mButton.setTag(position);
                mButton.setChecked(this.mSparseBooleanArray.get(position));
                mButton.setOnCheckedChangeListener(this.mCheckedChangeListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }

    private class SubAlbumAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater mInflater;
        ArrayList<String> mList;

        class C06012 implements OnClickListener {
            private final int val$position;

            C06012(int i) {
                this.val$position = i;
            }

            public void onClick(View v) {
                GalleryViewVideo.this.removeElements(this.val$position);
            }
        }

        SubAlbumAdapter(Context context, ArrayList<Uri> imageList) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
            this.mList = new ArrayList<>();
            if (imageList.size() > 0) {
                for (Uri uri : imageList)
                    addElements(uri);
                ((GalleryActivityVideo) this.mContext).onPhotoSelection(getCount());
            }
        }

        private void addElements(Uri albumUri) {
            if (this.mList.size() < GalleryViewVideo.this.mMaxPhotoSelection) {
                this.mList.add(albumUri.toString());
                notifyDataSetChanged();
                return;
            }
            Toast.makeText(mView.getContext().getApplicationContext(),
                    "Maximum " + GalleryViewVideo.this.mMaxPhotoSelection + " photos can be selected.",
                    Toast.LENGTH_SHORT).show();
        }


        private void remove(int album) {
            try {
                this.mList.remove(album);
                notifyDataSetChanged();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int getCount() {
            return this.mList.size();
        }

        public String getItem(int position) {
            return this.mList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = this.mInflater.inflate(R.layout.grid_collage_test_item_video, null);
            }

            try {
                Glide.with(mContext)
                        .load(mList.get(position))
                        .placeholder(R.drawable.loading)
                        .into((ImageView) convertView.findViewById(R.id.image));
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                Glide.with(mContext)
                        .load(mList.get(0))
                        .placeholder(R.drawable.loading)
                        .into((ImageView) convertView.findViewById(R.id.image));
            }

            convertView.setOnClickListener(new C06012(position));
            return convertView;
        }
    }


    GalleryViewVideo(Context context, CardView main, LinearLayout view, HListView mLinearBottomLayout,
                     LinearLayout mLinearNext, LinearLayout mLinearDone, int maxSelection,
                     ProgressBuilder hud1, int albumPosition) {

        this.f56b = true;
        this.mMaxPhotoSelection = maxSelection;
        this.mView = view;
        this.main = main;
        this.myContext = context;
        this.galleryActivity = (GalleryActivityVideo) context;
        this.listView = this.mView.findViewById(R.id.listViewImage);
        this.mGridView = this.mView.findViewById(R.id.gridGallery);
        mHListView = mLinearBottomLayout.findViewById(R.id.hListView1);
        this.albumPosition = albumPosition;
        this.textViewEmpty = this.mView.findViewById(R.id.textview_no_gallery_image);


        mHListView.setHeaderDividersEnabled(true);
        mHListView.setFooterDividersEnabled(true);

        this.mLinearBottom = mLinearBottomLayout.findViewById(R.id.linearBottom);
        this.bottomLayout = mLinearNext;
        this.toolLayout = mLinearDone;
        mImageNext = this.toolLayout.findViewById(R.id.imageDone1);

        MyMarshmallow.checkStorage(this.galleryActivity, () -> {
            backgroundTask = new BackgroundTask();
            backgroundTask.execute();
        });

    }

    private class BackgroundTask extends AsyncTask<String, String, String> {
        Cursor image_cursor;
        HashSet<String> hash_set;

        BackgroundTask() {
            isAlbumView = true;
            mAlbumsList = new ArrayList<>();
            hash_set = new HashSet<>();
            image_cursor = mView.getContext().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "bucket_display_name", "bucket_id"}, null, null, "datetaken DESC ");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String s) {
            try {

                if (image_cursor != null) {
                    for (int i = 0; i < image_cursor.getCount(); i++) {
                        try {
                            Album album = new Album();
                            image_cursor.moveToPosition(i);
                            int albumColumnIndex = image_cursor.getColumnIndex("bucket_display_name");//name
                            int albumIdColumnIndex = image_cursor.getColumnIndex("bucket_id");//id for getting images in folder
                            int dataColumnIndex = image_cursor.getColumnIndex("_id");
                            Uri photoUri = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, image_cursor.getString(dataColumnIndex));

                            String albumName = image_cursor.getString(albumColumnIndex);
                            album.setAlbumName(albumName);
                            album.setImageUrl(photoUri.toString());
                            album.albumId = image_cursor.getString(albumIdColumnIndex);
                            if (hash_set.add(albumName)) {
                                mAlbumsList.add(album);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                int mainWidth = main.getMeasuredWidth();
                params = new RelativeLayout.LayoutParams(mainWidth / 2, mainWidth / 2);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

                fillAlbumFirst();
                getImageUris = new ArrayList<>();

                albumAdapter = new AlbumAdapter(mView.getContext(), mAlbumsList);

                mAdapter = new SubAlbumAdapter(myContext, new ArrayList<>());
                mHListView.setAdapter(mAdapter);


                mImageNext.setOnClickListener(new C05931(myContext));
                listView.setAdapter(albumAdapter);
                listView.setOnItemClickListener(new C05942());
                listView.setOnScrollListener(new C05953());
                mGridView.setOnItemClickListener(new C05964(myContext));
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    try {
                        if (galleryActivity != null) {
                            Animation slideUpAnimation = AnimationUtils.loadAnimation(galleryActivity, R.anim.slide_up);
                            bottomLayout.startAnimation(slideUpAnimation);
                        }
                        bottomLayout.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 500);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onBackgroundError(Exception e) {

        }
    }

    public void onBackPressed() {
        this.isAlbumView = true;
        this.listView.setAdapter(this.albumAdapter);
    }


    @SuppressLint("Range")
    private void fillGallery(String albumId) {

        try {
            Cursor image_cursor = null;
            if (albumId != null)
                image_cursor = this.mView.getContext().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "bucket_id=?", new String[]{albumId}, "datetaken DESC");
            this.imageUris = new ArrayList<>();
            if (image_cursor != null) {
                for (int i = 0; i < image_cursor.getCount(); i++) {
                    image_cursor.moveToPosition(i);
                    checkCorruptImages(ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, image_cursor.getInt(image_cursor.getColumnIndex("_id"))));
                }
                image_cursor.close();
            }
            this.textViewEmpty.setVisibility(View.GONE);
            this.mGridView.setVisibility(View.VISIBLE);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {

                try {
                    gridAdapter = new GridAdapter(mView.getContext(), imageUris);
                    mGridView.setAdapter(gridAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 300);

            if (this.imageUris == null || this.imageUris.size() <= 0) {
                this.textViewEmpty.setVisibility(View.VISIBLE);
                this.mGridView.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkCorruptImages(Uri contentUri) {
        try {
//            if (((double) this.mView.getContext().getContentResolver().openFileDescriptor(contentUri, "r").getStatSize()) != 0.0d) {
            this.imageUris.add(contentUri);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {


        if (this.mLinearBottom != null) {
            this.mLinearBottom.removeAllViews();
        }
    }

    private void addElements(Uri uri) {
        this.mAdapter.addElements(uri);

    }

    private void removeElements(int position) {
        try {
            this.mAdapter.remove(position);
            this.mAdapter.notifyDataSetChanged();
            this.galleryActivity.onPhotoSelection(this.mAdapter.getCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
    }

    private void fillAlbumFirst() {

        try {
            if (this.listView != null) {
                this.mAlbumsList.get(albumPosition).setSelected(true);
                fillGallery(this.mAlbumsList.get(albumPosition).albumId);
                this.listView.post(new C05986(albumPosition));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void refreshAlbumListSelection(int position) {
        try {
            if (this.mAlbumsList != null && this.mAlbumsList.size() > 0) {
                for (int i = 0; i < this.mAlbumsList.size(); i++) {
                    this.mAlbumsList.get(i).setSelected(false);
                }
                this.mAlbumsList.get(position).setSelected(true);
                if (this.albumAdapter != null) {
                    this.albumAdapter.notifyDataSetChanged();
                    this.listView.post(new C05997(position));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAlbumPosition() {
        return albumPosition;
    }


    public ArrayList<String> getSelectedUrisList() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (String uri : mAdapter.mList)
            stringArrayList.add(uri.toString());
        return stringArrayList;
    }

    public void cancelBackgroundTask() {
        if (backgroundTask != null)
            backgroundTask.cancel();
    }

    public void initBackgroundTask() {
        backgroundTask = new BackgroundTask();
        backgroundTask.execute();
    }

}
