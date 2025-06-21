package com.birthday.video.maker.Birthday_Cakes.custGallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.birthday.video.maker.Birthday_Video.customgallery.hlistview.widget.HListView;
import com.birthday.video.maker.R;
import com.birthday.video.maker.async.AsyncTask;
import com.birthday.video.maker.marshmallow.MyMarshmallow;

import java.util.ArrayList;
import java.util.HashSet;


public class PhotoGalleryView implements OnClickListener {

    private final Context myContext;
    private AlbumAdapter albumAdapter;
    private boolean f56b;
    private final PhotoSelectionActivity galleryActivity;
    private GridAdapter gridAdapter;
    private ArrayList<Uri> imageUris;
    private boolean isAlbumView;
    private final ListView listView;
    private SubAlbumAdapter mAdapter;
    private ArrayList<Album> mAlbumsList;
    private final GridView mGridView;
    private final CardView main;
    private final LinearLayout mLinearBottom;
    private final LinearLayout mView;
    private final TextView textViewEmpty;
    private RelativeLayout.LayoutParams params;

    private final HListView mHListView;
    private int albumPosition;
    private BackgroundTask backgroundTask;


    class C05942 implements OnItemClickListener {
        C05942() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (PhotoGalleryView.this.isAlbumView) {
                albumPosition = position;
                PhotoGalleryView.this.refreshAlbumListSelection(position);
                PhotoGalleryView.this.fillGallery(PhotoGalleryView.this.mAlbumsList.get(position).albumId);
                PhotoGalleryView.this.f56b = false;
            }
        }
    }

    class C05953 implements OnScrollListener {
        C05953() {
        }

        public void onScrollStateChanged(AbsListView listView, int scrollState) {
            PhotoGalleryView.this.f56b = true;
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    class C05964 implements OnItemClickListener {
        C05964() { }
        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            try {
                Intent intent = new Intent();
                intent.putExtra("image_uri", imageUris.get(position));
                galleryActivity.setResult(Activity.RESULT_OK, intent);
                galleryActivity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    class C05986 implements Runnable {
        private final int val$position;

        C05986(int i) {
            this.val$position = i;
        }

        public void run() {

            PhotoGalleryView.this.listView.smoothScrollToPosition(this.val$position);
        }
    }

    class C05997 implements Runnable {
        private final int val$position;

        C05997(int i) {
            this.val$position = i;
        }

        public void run() {
            PhotoGalleryView.this.listView.smoothScrollToPosition(this.val$position);
        }
    }


    private static class Album {
        String albumId;
        private String albumName;
        private Uri imageUri;
        private boolean selected;

        void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        void setImageUri(Uri imageUri) {
            this.imageUri = imageUri;
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
                convertView = this.mInflater.inflate(R.layout.grid_collage_row_album_item, null);
                PhotoGalleryView.this.f56b = true;
            }
            ImageView imageView = convertView.findViewById(R.id.imageView1);
            TextView textview = convertView.findViewById(R.id.albumName);
            if (this.mList.get(position).isSelected()) {
                try {
                    convertView.setBackgroundResource(R.drawable.gallery_click_background);

                } catch (OutOfMemoryError e) {
                    Bitmap bitmap = setBitmapOptions1(R.drawable.gallery_click_background, 1);
                    Drawable drawable = new BitmapDrawable(myContext.getResources(), bitmap);
                    convertView.setBackground(drawable);
                    e.printStackTrace();
                }

            } else {
                try {
                    convertView.setBackgroundResource(R.drawable.gallery_grid_background);

                } catch (OutOfMemoryError e) {
                    Bitmap bitmap = setBitmapOptions1(R.drawable.gallery_grid_background, 1);
                    Drawable drawable = new BitmapDrawable(myContext.getResources(), bitmap);
                    convertView.setBackground(drawable);
                    e.printStackTrace();
                }
            }
            if (PhotoGalleryView.this.f56b) {
                Glide.with(mContext)
                        .load(this.mList.get(position).imageUri)
                        .into(imageView);
            }

            textview.setText(this.mList.get(position).albumName);
            return convertView;
        }
    }

    private Bitmap setBitmapOptions1(int path, int scale) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inSampleSize = (int) Math.pow(2, scale);
            bitmap = BitmapFactory.decodeResource(myContext.getResources(), path, o);
        } catch (OutOfMemoryError e) {
            setBitmapOptions1(path, ++scale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private class GridAdapter extends BaseAdapter {
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
                gridAdapter.getCheckedItems().size();
            }
        }


        GridAdapter(Context context, ArrayList<Uri> imageList) {
            this.mCheckedChangeListener = new C06001();
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
            this.mSparseBooleanArray = new SparseBooleanArray();
            this.mList = new ArrayList<>();
            this.mList = imageList;
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
            return PhotoGalleryView.this.imageUris.size();
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
                convertView = this.mInflater.inflate(R.layout.grid_collage_row_multiphoto_item_single, null);
            }

            RelativeLayout grid_relative = convertView.findViewById(R.id.grid_relative);
            ImageView imageView1 = convertView.findViewById(R.id.imageView1);


            if (params != null)
                grid_relative.setLayoutParams(params);
            try {
                Glide.with(mContext)
                        .load(PhotoGalleryView.this.imageUris.get(position))
                        .into(imageView1);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                if (PhotoGalleryView.this.imageUris.size() > 0) {
                    Glide.with(mContext)
                            .load(PhotoGalleryView.this.imageUris.get(0))
                            .into((ImageView) convertView.findViewById(R.id.imageView1));
                }

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
                PhotoGalleryView.this.removeElements(this.val$position);
            }
        }


        SubAlbumAdapter(Context context, ArrayList<String> imageList) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
            this.mList = new ArrayList<>();
            this.mList = imageList;
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
                convertView = this.mInflater.inflate(R.layout.grid_collage_test_item_single, null);
            }

            try {
                Glide.with(mContext)
                        .load(mList.get(position))
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


    PhotoGalleryView(Context context, LinearLayout view, HListView mLinearBottomLayout, int albumPosition) {

        this.f56b = true;
        this.mView = view;
        this.galleryActivity = (PhotoSelectionActivity) context;
        this.listView = this.mView.findViewById(R.id.listViewImage);
        this.mGridView = this.mView.findViewById(R.id.gridGallery1);
        this.albumPosition = albumPosition;

        this.textViewEmpty = this.mView.findViewById(R.id.textview_no_gallery_image);
        myContext = context;
        mHListView = mLinearBottomLayout.findViewById(R.id.hListView1);
        mHListView.setHeaderDividersEnabled(true);
        mHListView.setFooterDividersEnabled(true);

        this.main = this.mView.findViewById(R.id.main);
        this.mLinearBottom = mLinearBottomLayout.findViewById(R.id.linearBottom);
        MyMarshmallow.checkStorage(this.galleryActivity, () -> {
            backgroundTask = new BackgroundTask();
            backgroundTask.execute();
        });


    }

    private class BackgroundTask extends AsyncTask<String, String, String> {
        Cursor image_cursor;
        HashSet<String> hash_set;
        private int mainWidth;

        BackgroundTask() {
            try {
                isAlbumView = true;
                mAlbumsList = new ArrayList<>();
                hash_set = new HashSet<>();
                image_cursor = mView.getContext().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "bucket_display_name", "bucket_id", "date_added"}, null, null, "datetaken DESC");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                        Album album = new Album();
                        image_cursor.moveToPosition(i);
                        int albumColumnIndex = image_cursor.getColumnIndex("bucket_display_name");//name
                        int albumIdColumnIndex = image_cursor.getColumnIndex("bucket_id");//id for getting images in folder
                        @SuppressLint("Range") int imageId = image_cursor.getInt(image_cursor.getColumnIndex("_id"));
                        Uri photoContentUri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, imageId);
                        String albumName = image_cursor.getString(albumColumnIndex);
                        album.setAlbumName(albumName);
                        album.setImageUri(photoContentUri);
                        album.albumId = image_cursor.getString(albumIdColumnIndex);
                        if (hash_set.add(albumName)) {
                            mAlbumsList.add(album);
                        }
                    }
                    image_cursor.close();
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

               mainWidth = main.getMeasuredWidth();
                params = new RelativeLayout.LayoutParams(mainWidth / 2, mainWidth / 2);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

                if (mAlbumsList.size() > 0) {
                    try {
                albumAdapter = new AlbumAdapter(mView.getContext(), mAlbumsList);
                mAdapter = new SubAlbumAdapter(myContext, new ArrayList<>());
                mHListView.setAdapter(mAdapter);
                listView.setAdapter(albumAdapter);
                if (mainWidth>0)
                fillAlbumFirst();
                else{
                   main.post(() -> {
                       try {
                           mainWidth= main.getMeasuredWidth();
                           params = new RelativeLayout.LayoutParams(mainWidth / 2, mainWidth / 2);
                           params.addRule(RelativeLayout.CENTER_IN_PARENT);
                           fillAlbumFirst();
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   });
                }
                listView.setOnItemClickListener(new C05942());
                listView.setOnScrollListener(new C05953());
                mGridView.setOnItemClickListener(new C05964());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(mView.getContext().getApplicationContext(), myContext.getString(R.string.no_photos), Toast.LENGTH_SHORT).show();


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
            this.imageUris.add(contentUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setVisibility(boolean flag) {
        if (flag) {
            this.mView.setVisibility(View.VISIBLE);
        } else {
            this.mView.setVisibility(View.GONE);
        }
    }

    void refresh() {

        if (this.mLinearBottom != null) {
            this.mLinearBottom.removeAllViews();
        }
    }


    private void removeElements(int position) {
        this.mAdapter.remove(position);
        this.mAdapter.notifyDataSetChanged();
        this.galleryActivity.onPhotoSelection(this.mAdapter.getCount());
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


    public void cancelBackgroundTask() {
        if (backgroundTask != null)
            backgroundTask.cancel();
    }

    public void initBackgroundTask() {
        backgroundTask = new BackgroundTask();
        backgroundTask.execute();
    }


}
