package com.birthday.video.maker.Birthday_Video.video_title;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.birthday.video.maker.Birthday_Gifs.DownloadFileAsync;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.activities.ProgressBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.apptitle;
import static com.birthday.video.maker.Resources.end_title;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.Resources.start_title;
import static com.birthday.video.maker.Resources.video_start_thumb;
import static com.birthday.video.maker.Resources.video_zip_names;
import static com.birthday.video.maker.Resources.video_zip_urls;


public class Start_Title_Fragment extends Fragment {

    public int currentpos, lastpos = -1;
    private DisplayMetrics displayMetrics;
    private RecyclerView start_recycler;
    private RelativeLayout capture_layout;
    private ImageView imageview_start;
    private ArrayList<String> allnames;
    private ArrayList<String> allpaths;
    private File[] listFile;
    private String storagepath;
    private String category;
    private String storagepath_name;
    private GifsAdapter adapter;
    private TextView toasttext;
    private Toast toast;
    private Uri firstimgUri;
    private Uri secondimgUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_title_lyt, container, false);

        try {
            displayMetrics = getResources().getDisplayMetrics();

            capture_layout = view.findViewById(R.id.capture_layout);
            start_recycler = view.findViewById(R.id.start_recycler);
            imageview_start = view.findViewById(R.id.imageview_start);

            start_recycler.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3f);
            start_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

            currentpos = 0;
            addtoast();
            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), start_title[currentpos]);
            imageview_start.setImageBitmap(Resources.images_bitmap);


            category = "videoend";
            storagepath = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category;
            createFolder();
            getgifnamesandpaths1();
            Resources.image1 = SaveImageToExternal(BitmapFactory.decodeResource(getResources(), start_title[0]));
            Resources.image2 = SaveImageToExternal_s(BitmapFactory.decodeResource(getResources(), end_title[0]));

            setadapter(video_start_thumb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    private void setadapter(Integer[] titles) {
        try {
            start_recycler.setHasFixedSize(true);
            start_recycler.setVisibility(View.VISIBLE);
            adapter = new GifsAdapter(titles);
            start_recycler.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.MyViewHolder> {
        private LayoutInflater infalter;
        Integer[] urls;


        public GifsAdapter(Integer[] urls) {
            this.urls = urls;
            infalter = LayoutInflater.from(getContext());
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = infalter.inflate(R.layout.gif_item_video_s, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {


            try {
                if (pos <= 1) {
                    Glide.with(getContext()).load(start_title[pos]).into(holder.imageView);
                    holder.download_icon_sf.setVisibility(View.GONE);
                } else {
                    if (isNetworkAvailable(getContext())) {
                        Glide.with(getContext()).load(urls[pos - 2]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);
                    } else {
                        Glide.with(getContext()).load(urls[pos - 2]).placeholder(R.drawable.no_internet).into(holder.imageView);
                    }
                    if (allnames.size() > 0) {
                        if (allnames.contains(video_zip_names[pos])) {
                            holder.download_icon_sf.setVisibility(GONE);
                        } else {
                            holder.download_icon_sf.setVisibility(VISIBLE);

                        }
                    } else {
                        holder.download_icon_sf.setVisibility(VISIBLE);

                    }
                }
                if (currentpos == pos) {
                    holder.imageview_sel.setVisibility(View.VISIBLE);
                } else {
                    holder.imageview_sel.setVisibility(View.GONE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.imageView.setOnClickListener(v -> {
                try {
                    getgifnamesandpaths1();
                    lastpos = currentpos;
                    currentpos = holder.getAdapterPosition();
                    notifyDataSetChanged();

                    if (allnames.size() > 0) {
                        if (allnames.contains(video_zip_names[pos])) {
                            for (int i = 0; i < allnames.size(); i++) {
                                String name = allnames.get(i);
                                String modelname = video_zip_names[pos];
                                if (name.equals(modelname)) {
                                    String path = allnames.get(i);
                                    storagepath_name = getContext().getFilesDir().getAbsolutePath()
                                            + File.separator + "Birthday Frames"
                                            + File.separator + ".Downloads"
                                            + File.separator + category
                                            + File.separator + path;

                                    File file1 = new File(storagepath_name);
                                    Log.d("FileCheck", "Directory path: " + storagepath_name);

                                    if (file1.isDirectory()) {
                                        File[] innerDirs = file1.listFiles();  // e.g., .../vidpromo3/vidpromo3

                                        if (innerDirs != null) {
                                            for (File innerFolder : innerDirs) {
                                                Log.d("FileCheck", "Found inner directory: " + innerFolder.getAbsolutePath());

                                                if (innerFolder.isDirectory()) {
                                                    File[] imageFiles = innerFolder.listFiles();

                                                    if (imageFiles != null) {
                                                        for (File imgFile : imageFiles) {
                                                            String fileName = imgFile.getName();
                                                            Log.d("FileCheck", "Found file: " + fileName);

                                                            if (fileName.startsWith("front_")) {
                                                                String str2 = imgFile.getAbsolutePath();
                                                                Log.d("FileCheck", "Matched start_: " + str2);
                                                                imageview_start.setImageBitmap(BitmapFactory.decodeFile(str2));
                                                                Resources.image1 = str2;
                                                                notifyPositions();
                                                            }

                                                            if (fileName.startsWith("back_")) {
                                                                String str2 = imgFile.getAbsolutePath();
                                                                Log.d("FileCheck", "Matched end_: " + str2);
                                                                Resources.image2 = str2;
                                                                notifyPositions();
                                                            }
                                                        }
                                                    } else {
                                                        Log.d("FileCheck", "No files found in: " + innerFolder.getAbsolutePath());
                                                    }
                                                }
                                            }
                                        } else {
                                            Log.d("FileCheck", "No inner directories found in: " + storagepath_name);
                                        }
                                    } else {
                                        Log.d("FileCheck", "Not a directory: " + storagepath_name);
                                    }

                                    break;
                                }


/*
                                if (name.equals(modelname)) {
                                    String path = allnames.get(i);
                                    storagepath_name = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + path;
                                    File file1 = new File(storagepath_name);
                                    File[] list = file1.listFiles();
                                    if (file1.isDirectory()) {
                                        listFile = file1.listFiles();
                                        if (listFile != null) {
                                            for (File aListFile : listFile) {
                                                String str2 = aListFile.getAbsolutePath();
                                                    if (str2.contains("start_")) {
                                                        //                                                currentpos = pos;
                                                        imageview_start.setImageBitmap(BitmapFactory.decodeFile(str2));
                                                        Resources.image1 = str2;

                                                        notifyPositions();
                                                    }
                                                    if (str2.contains("end_")) {
                                                        //                                                currentpos = pos;

                                                        Resources.image2 = str2;

                                                        notifyPositions();
                                                    }


                                            }
                                        }
                                    }
                                    break;
                                }
*/

                            }
                        } else {

                            if (isNetworkAvailable(getContext())) {
                                if (pos <= 1) {
                                    offline(pos);

                                } else {
                                    downloadAndUnzipContent(video_zip_names[pos], video_zip_urls[pos], pos);
                                }
                            } else {
                                if (pos <= 1) {
                                    offline(pos);
                                } else {
                                    showDialog();
                                }
                            }
                        }
                    } else {
                        if (isNetworkAvailable(getContext())) {
                            if (pos <= 1) {
                                offline(pos);
                            } else {
                                downloadAndUnzipContent(video_zip_names[pos], video_zip_urls[pos], pos);
                            }
                        } else {
                            if (pos <= 1) {
                                offline(pos);
                            } else {
                                showDialog();
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }

        @Override
        public int getItemCount() {
            return urls.length + 2;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView, imageview_sel;
            RelativeLayout download_icon_sf;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                imageview_sel = itemView.findViewById(R.id.imageview_sel);
                download_icon_sf = itemView.findViewById(R.id.download_icon_sf);
                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.8f);
                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.8f);
                imageview_sel.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.8f);
                imageview_sel.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.8f);
                download_icon_sf.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.8f);
                download_icon_sf.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.8f);
            }
        }
    }

    private void notifyPositions() {
        try {
            adapter.notifyItemChanged(lastpos);
            adapter.notifyItemChanged(currentpos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.connection_toast, (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout));
            toasttext = (TextView) layout.findViewById(R.id.toasttext);
            toasttext.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/normal.ttf"));
            toast = new Toast(getContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {

        try {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 400);
            toasttext.setText(R.string.please_check_network_connection);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getgifnamesandpaths1() {
        try {
            allnames = new ArrayList<>();
            allpaths = new ArrayList<>();
            File file = new File(storagepath);

            if (file.isDirectory()) {
                listFile = file.listFiles();
                for (File aListFile : listFile) {
                    String str1 = aListFile.getName();
                    allnames.add(str1);
                    allpaths.add(aListFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void downloadAndUnzipContent(String name, String url, final int pos) {

        final ProgressBuilder progressDialog = new ProgressBuilder(getContext());
        progressDialog.showProgressDialog();
        progressDialog.setDialogText("Downloading....");

        DownloadFileAsync download = new DownloadFileAsync(storagepath, name + ".zip", getContext(), new DownloadFileAsync.PostDownload() {
            @Override
            public void downloadDone(File file) {

                try {
                    if (progressDialog != null)
                        progressDialog.dismissProgressDialog();
                    getgifnamesandpaths1();
                    String str1 = null;
                    if (file != null) {
                        str1 = file.getName();
                        if (str1 != null) {
                            int index = str1.indexOf(".");
                            String str = str1.substring(0, index);
                            storagepath_name = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + str;
                            File file1 = new File(storagepath_name);
                            File[] list = file1.listFiles();

                          /*  if (file1.isDirectory()) {
                                listFile = file1.listFiles();
                                for (File aListFile : listFile) {
                                    String str2 = aListFile.getAbsolutePath();
                                    if (str2.contains("start_")) {
                                        imageview_start.setImageBitmap(BitmapFactory.decodeFile(str2));
                                        Resources.image1 = str2;
                                    }
                                    if (str2.contains("end_")) {
                                        imageview_start.setImageBitmap(BitmapFactory.decodeFile(str2));
                                        Resources.image2 = str2;

                                    }
                                    notifyPositions();
                                }
                            }*/

                            if (file1.isDirectory()) {
                                listFile = file1.listFiles();
                                if (listFile != null) {
                                    for (File aListFile : listFile) {
                                        if (aListFile.isDirectory()) {
                                            File[] innerFiles = aListFile.listFiles();
                                            if (innerFiles != null) {
                                                for (File innerFile : innerFiles) {
                                                    String fileName = innerFile.getName();
                                                    String str2 = innerFile.getAbsolutePath();
                                                    Log.d("InnerFile", "Found file: " + str2);

                                                    if (fileName.startsWith("front_")) {
                                                        imageview_start.setImageBitmap(BitmapFactory.decodeFile(str2));
                                                        Resources.image1 = str2;
                                                        Log.d("InnerFile", "Set image1: " + str2);
                                                    } else if (fileName.startsWith("back_")) {
                                                        imageview_start.setImageBitmap(BitmapFactory.decodeFile(str2));
                                                        Resources.image2 = str2;
                                                        Log.d("InnerFile", "Set image2: " + str2);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    notifyPositions();
                                }
                            }

                            file.delete();
                        }
                    } else {
                        Toast.makeText(imageview_start.getContext().getApplicationContext(), "Download Not Done", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        download.execute(url);
    }


    private void createFolder() {
        try {
            if (!mainFolder.exists()) {
                mainFolder.mkdirs();
            }
            File typeFolder = new File(storagepath);
            if (!typeFolder.exists()) {
                typeFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void offline(int pos) {
        try {
            imageview_start.setImageBitmap(BitmapFactory.decodeResource(getResources(), start_title[pos]));
            Resources.image1 = SaveImageToExternal(BitmapFactory.decodeResource(getResources(), start_title[pos]));
            Resources.image2 = SaveImageToExternal_s(BitmapFactory.decodeResource(getResources(), end_title[pos]));
            notifyPositions();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String SaveImageToExternal_s(Bitmap finalBitmap) {


        File file = null;
        try {
            File myDir = new File(getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + "Video end");

            myDir.mkdirs();

            String fname = "image" + "_" + apptitle + "_" + ".png";
            file = new File(myDir, fname);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                          secondimgUri = uri;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();

    }

    private String SaveImageToExternal(Bitmap finalBitmap) {


        File file = null;
        try {
            File myDir = new File(getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + "Video Title");

            myDir.mkdirs();
            String fname = "image" + "_" + apptitle + "_" + ".png";
            file = new File(myDir, fname);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            firstimgUri = uri;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();

    }


}