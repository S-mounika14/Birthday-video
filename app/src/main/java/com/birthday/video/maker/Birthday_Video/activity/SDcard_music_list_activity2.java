package com.birthday.video.maker.Birthday_Video.activity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.activities.BaseActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.birthday.video.maker.Birthday_Video.adapters.Songs_Online_Apater;
import com.birthday.video.maker.Birthday_Video.video_maker.add_audio.SdcardRingtonesAdapter;
import com.birthday.video.maker.Birthday_Video.video_maker.data.MusicData;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.view.View.GONE;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.Resources.songs_urls;


public class SDcard_music_list_activity2 extends BaseActivity {
    public Music1Fragment fragment1;
    public Music2Fragment fragment2;
    public BirthdayWishMakerApplication myapplication;
    private ContentResolver contentResolver;
    public BroadcastReceiver receiver;
    private LinearLayout search_layout;
    private RelativeLayout search_button_layout;
    private EditText editText_search;
    private InputMethodManager imm;
    private CardView top_layout;
    private ImageView search_icon;
    public BirthdayWishMakerApplication application;
    public AssetManager mngr;
    public static String category;
    public static File f;
    private FrameLayout adContainerView;
    private AdView bannerAdView;


    public interface resetAllMusics2 {
        void resetMusic2();

        void pause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.music_layout);


        try {
            adContainerView = findViewById(R.id.adContainerView);
            adContainerView.post(new Runnable() {
                @Override
                public void run() {
                    if (InternetStatus.isConnected(getApplicationContext())) {
                        if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) adContainerView.getLayoutParams();
                            layoutParams.height = BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize().getHeightInPixels(getApplicationContext());
                            adContainerView.setLayoutParams(layoutParams);
                            adContainerView.setBackgroundColor(getResources().getColor(R.color.banner_ad_bg_creation));
                            bannerAdView = new AdView(getApplicationContext());
                            bannerAdView.setAdUnitId(getString(R.string.banner_id));
                            AdRequest adRequest = new AdRequest.Builder().build();
                            bannerAdView.setAdSize(BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize());
                            bannerAdView.loadAd(adRequest);
                            bannerAdView.setAdListener(new AdListener() {
                                @Override
                                public void onAdLoaded() {
                                    super.onAdLoaded();
                                    if (!isFinishing() && !isChangingConfigurations() && !isDestroyed()) {
                                        adContainerView.removeAllViews();
                                        bannerAdView.setVisibility(View.GONE);
                                        adContainerView.addView(bannerAdView);
                                        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_bottom_in);
                                        animation.setStartOffset(0);
                                        bannerAdView.startAnimation(animation);
                                        bannerAdView.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } else {
                            adContainerView.setVisibility(View.GONE);
                        }
                    } else {
                        adContainerView.setVisibility(View.GONE);
                    }
                }
            });
            application = BirthdayWishMakerApplication.getInstance();
            mngr = SDcard_music_list_activity2.this.getAssets();
            top_layout = findViewById(R.id.top_layout);
//            search_icon = findViewById(R.id.search_icon);
            search_layout = findViewById(R.id.search_layout);
            search_button_layout = findViewById(R.id.search_button_layout);
            editText_search = findViewById(R.id.editText_search);
            RelativeLayout music_back = findViewById(R.id.music_back);
            category = getIntent().getExtras().getString("category");

            myapplication = BirthdayWishMakerApplication.getInstance();
            TabLayout tabLayout = findViewById(R.id.tablayout);
            ViewPager viewPager = findViewById(R.id.pager);
            tabLayout.setSelectedTabIndicatorHeight(5);
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.black));
            MusicAdapter musicadapter = new MusicAdapter(getSupportFragmentManager());
            viewPager.setAdapter(musicadapter);
            tabLayout.setupWithViewPager(viewPager);

            search_layout.setVisibility(View.GONE);
            search_button_layout.setVisibility(View.VISIBLE);

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() == 1) { // Stored Songs tab
//                        search_icon.setVisibility(View.INVISIBLE);
                        fragment2.resetMusic2();
                    } else { // Existing Songs tab
                        // search_icon.setVisibility(View.VISIBLE);
                        fragment1.resetMusic1();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}

                @Override
                public void onTabReselected(TabLayout.Tab tab) {}
            });



            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            fragment1 = ((Music1Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0)).getCurrFrament();
            fragment2 = ((Music2Fragment) viewPager.getAdapter().instantiateItem(viewPager, 1)).getCurrFrament();


            contentResolver = getContentResolver();

          /*  search_icon.setOnClickListener(view -> {

//                if (fragment2 != null) {
//                    fragment2.triggerSearch();
//                }
                search_button_layout.setVisibility(View.INVISIBLE);
                search_layout.setVisibility(View.VISIBLE);
                editText_search.setFocusable(true);
                editText_search.setFocusableInTouchMode(true);
                editText_search.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            });*/


            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageScrollStateChanged(int state) {
                }

                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    fragment1.resetMusic1();
                    fragment2.resetMusic2();
                    search_layout.setVisibility(View.INVISIBLE);
                    search_button_layout.setVisibility(View.VISIBLE);
                    fragment2.filter("");
                    imm.hideSoftInputFromWindow(search_button_layout.getWindowToken(), 0);
                }
            });

            editText_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = editText_search.getText().toString().toLowerCase(Locale.getDefault());
                    fragment2.filter(text);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            music_back.setOnClickListener(view -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  static class Music1Fragment extends Fragment implements Songs_Online_Apater.OnSongsClickListener {
        private MediaPlayer mediaPlayer;
        private View v;
        RecyclerView songs_list_rec;
        private ArrayList<String> allnames;
        private ArrayList<String> allpaths;
        private File[] listFile;
        String storagepath;
        String format = ".mp3";
        private TextView toasttext;
        private Toast toast;
        private int add;

        public Music1Fragment() {
            super();
            mediaPlayer = new MediaPlayer();
        }

        public  Music1Fragment createNewInstance() {

            return new Music1Fragment();

        }

        public Music1Fragment getCurrFrament() {
            return this;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.existed_music_rec_lyt, container, false);

            storagepath = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category;

            addtoast();
            initView(v);

            return v;
        }

        private void initView(View view) {

            try {
                createFolder();
                getnamesandpaths();
                songs_list_rec = view.findViewById(R.id.songs_list_rec);
                songs_list_rec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                GifsAdapter songs_adapter = new GifsAdapter(songs_urls);
                songs_list_rec.setAdapter(songs_adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }


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

        private void getnamesandpaths() {
            try {
                allnames = new ArrayList<>();
                allpaths = new ArrayList<>();
                File file = new File(storagepath);

                if (file.isDirectory()) {
                    listFile = file.listFiles();
                    for (File aListFile : listFile) {
                        String str1 = aListFile.getName();
                        int index = str1.indexOf(".");
                        String str = str1.substring(0, index);
                        allnames.add(str);
                        allpaths.add(aListFile.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private String saveDownloadedImage(String path, String name, String format) {

            File file = null;
            try {
                File myDir = new File(path);
                myDir.mkdirs();

                file = new File(myDir, name + format);
                if (file.exists())
                    file.delete();
                InputStream in = null;
                try {
                    in = getActivity().getAssets().open(path);
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buff = new byte[1024];
                    while (true) {
                        int read = in.read(buff);
                        if (read <= 0) {
                            break;
                        }
                        out.write(buff, 0, read);
                    }

                    MediaPlayer player = new MediaPlayer();
                    player.setDataSource(path);
                    player.setAudioStreamType(3);
                    player.prepare();
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return file.getAbsolutePath();
        }


        private boolean createAudio1(String path, String name) {

            File myDir = new File(storagepath);
            myDir.mkdirs();
            f = new File(myDir, name + ".mp3");

            try {
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(path);
                player.setAudioStreamType(3);
                player.prepare();
                final MusicData musicData = new MusicData();
                musicData.track_data = path;
                long dur = getDurationOfSound(getActivity(), f);
                if (dur != 0)
                    musicData.track_duration = dur;
                else if (player.getDuration() != 0)
                    musicData.track_duration = player.getDuration();
                musicData.track_Title = "temp";
                ((SDcard_music_list_activity2) getActivity()).application.setMusicData(musicData);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public long getDurationOfSound(Context context, Object soundFile) {
            int millis = 0;
            MediaPlayer mp = new MediaPlayer();
            try {
                Class<? extends Object> currentArgClass = soundFile.getClass();
                if (currentArgClass == Integer.class) {
                    AssetFileDescriptor afd = context.getResources().openRawResourceFd((Integer) soundFile);
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                } else if (currentArgClass == String.class) {
                    mp.setDataSource((String) soundFile);
                } else if (currentArgClass == File.class) {
                    mp.setDataSource(((File) soundFile).getAbsolutePath());
                }
                mp.prepare();
                millis = mp.getDuration();
            } catch (Exception e) {
                //  Logger.e(e.toString());
            } finally {
                mp.release();
                mp = null;
            }
            return millis;
        }

        public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.MyViewHolder> {
            private LayoutInflater infalter;
            String[] urls;


            public GifsAdapter(String[] urls) {
                this.urls = urls;
                infalter = LayoutInflater.from(getContext());
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = infalter.inflate(R.layout.songs_item_online, null);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
                try {

                    if (isNetworkAvailable(getContext())) {
                        Glide.with(getContext()).load(urls[pos]).into(holder.play_button_clk);

                    } else {
                        Glide.with(getContext()).load(urls[pos]).into(holder.play_button_clk);

                    }
                    holder.play_button_clk.setBackgroundResource(R.drawable.ic_music_play1);
                    add = 1;
                    int list = pos + add;
                    if (allnames.size() > 0) {

                        if (allnames.contains(String.valueOf(pos))) {
                            holder.download_button.setVisibility(GONE);
                        }

                    }
                    holder.ringtone_title.setText(getContext().getString(R.string.birthday_music) + list);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                holder.ringtone_layout_clk.setOnClickListener((View.OnClickListener) v -> {
                    //                        music_clkpos = pos;
                    //                        notifyDataSetChanged();

                    try {
                        getnamesandpaths();
                        if (category.equals("video")) {
                            if (allnames.size() > 0) {
                                if (allnames.contains(String.valueOf(pos))) {
                                    for (int i = 0; i < allnames.size(); i++) {
                                        try {
                                            String name = allnames.get(i);
                                            String modelname = String.valueOf(pos);
                                            if (name.equals(modelname)) {
                                                String path = allnames.get(i);
                                                String song_path = allpaths.get(i);

                                                createAudio1(song_path, String.valueOf(pos));
                                                ((SDcard_music_list_activity2) getActivity()).finish();

                                                break;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } else {

                                    if (isNetworkAvailable(getContext())) {

                                        new DownloadSongAsync(String.valueOf(pos), songs_urls[pos], holder.download_button).execute(songs_urls[pos]);


                                    } else {
                                        showDialog();
                                    }

                                }

                            } else {

                                if (isNetworkAvailable(getContext())) {

                                    new DownloadSongAsync(String.valueOf(pos), songs_urls[pos], holder.download_button).execute(songs_urls[pos]);
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
                return urls.length;
            }

            class MyViewHolder extends RecyclerView.ViewHolder {
                private final ImageView play_button_clk;
                RelativeLayout ringtone_layout_clk;
                TextView ringtone_title;
                ImageView download_button, downloaded_button;

                MyViewHolder(View itemView) {
                    super(itemView);
                    play_button_clk = itemView.findViewById(R.id.play_button_clk);
                    ringtone_layout_clk = itemView.findViewById(R.id.ringtone_layout_clk);
                    ringtone_title = itemView.findViewById(R.id.ringtone_title);
                    download_button = itemView.findViewById(R.id.download_button);
//                    downloaded_button = itemView.findViewById(R.id.downloaded_button);

                }
            }
        }


        class DownloadSongAsync extends AsyncTask<String, String, String> {

            private ProgressBuilder mProgressDialog;
            String name, links;
            ImageView download_button;

            public DownloadSongAsync(String name, String links, ImageView download_button) {
                this.name = name;
                this.links = links;
                this.download_button = download_button;

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressBuilder(getContext());
                mProgressDialog.showProgressDialog();
                mProgressDialog.setDialogText("Downloading...");


            }

            @Override
            protected String doInBackground(String... aurl) {
                int count;
                File myDir = new File(storagepath);
                myDir.mkdirs();
                f = new File(myDir, name + ".mp3");

                try {
                    URL url = new URL(links);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    int lenghtOfFile = conexion.getContentLength();
                    Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(f.getAbsolutePath());
                    byte data[] = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                }
                return f.getAbsolutePath();
            }

            protected void onProgressUpdate(String... progress) {
                Log.d("ANDRO_ASYNC", progress[0]);
//                mProgressDialog.setProgress(Integer.parseInt(progress[0]));
            }

            @Override
            protected void onPostExecute(String unused) {

                try {
                    if (mProgressDialog != null)
                        mProgressDialog.dismissProgressDialog();
                    String path = saveDownloadedImage(unused, name, format);
                    if (unused.contains(name)) {
                        download_button.setVisibility(View.GONE);
                    }

                    getnamesandpaths();
                    createAudio1(unused, name);
                    ((SDcard_music_list_activity2) getActivity()).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        public void resetMusic1() {
            try {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                }

            } catch (Exception e) {
                ((SDcard_music_list_activity2) getActivity()).finish();
            }


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
                toasttext.setText(this.getString(R.string.please_check_network_connection));
                toast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onPause() {
            super.onPause();

            if (mediaPlayer != null)
                mediaPlayer.reset();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mediaPlayer != null)
                mediaPlayer.release();


        }

        public void backpress() {
            if (mediaPlayer != null && mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }

        @Override
        public void onSongClick(int pos, ImageView iv) {

        }
    }

    public static class Music2Fragment extends Fragment implements SDcard_music_list_activity2.resetAllMusics2 {
        private RecyclerView songsRecyclerView, foldersRecyclerView;
        private ProgressBar progressBar;
        private TextView noMusicTextView, folderNameTextView;
        private ImageView backButton, searchImageView, alphabetSortImageView, descendingAscendingSortImageView;
        private ConstraintLayout musicSearchLayout;
        private EditText searchEditText;
        private ImageView searchCloseImageView;
        private FrameLayout folderSelectionLayout;
        private PopupWindow sortPopupWindow;
        private RadioGroup sortRadioGroup;
        private MediaPlayer mediaPlayer;
        private List<MusicData> musicDataList;
        private List<MusicData> searchDataList;
        private List<MusicData> allMusicDataList; // Added to preserve original song list
        private StoredSongsAdapter songsAdapter;
        private FolderAdapter folderAdapter;
        private Map<String, List<MusicData>> folderMap;
        private String currentFolder = "All Songs";
        private boolean isSearchInProgress = false;
        private InputMethodManager imm;

        public Music2Fragment() {
            super();
            mediaPlayer = new MediaPlayer();
        }

        public Music2Fragment createNewInstance() {
            return new Music2Fragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_music2_layout, container, false);
            initViews(view);
            setupRecyclerViews();
            setupSortPopup();
            loadMusicFiles();
            setupListeners();

            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            registerPhoneStateReceiver();
            return view;
        }

        private void initViews(View view) {
            songsRecyclerView = view.findViewById(R.id.songs_recycler_view);
            foldersRecyclerView = view.findViewById(R.id.folders_recycler_view);
            progressBar = view.findViewById(R.id.progress_bar);
            noMusicTextView = view.findViewById(R.id.no_music_text_view);
            folderNameTextView = view.findViewById(R.id.folder_name_text_view);
            backButton = view.findViewById(R.id.back_button);
            searchImageView = view.findViewById(R.id.search_image_view);
            alphabetSortImageView = view.findViewById(R.id.alphabet_sort_image_view);
            descendingAscendingSortImageView = view.findViewById(R.id.descending_ascending_sort_image_view);
            musicSearchLayout = view.findViewById(R.id.music_search_layout);
            searchEditText = view.findViewById(R.id.search_edit_text);
            searchCloseImageView = view.findViewById(R.id.mp3_search_close_image_view);
            folderSelectionLayout = view.findViewById(R.id.folder_selection_layout);

            musicSearchLayout.setVisibility(View.GONE);
            searchImageView.setVisibility(View.VISIBLE);
            folderSelectionLayout.setVisibility(View.VISIBLE);
            descendingAscendingSortImageView.setTag("Descending Order");
            progressBar.setVisibility(View.GONE);
        }

        private void setupRecyclerViews() {
            songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            songsRecyclerView.setHasFixedSize(true);
            songsRecyclerView.setItemAnimator(null);

            foldersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            foldersRecyclerView.setHasFixedSize(true);



        }

        private void loadMusicFiles() {
            progressBar.setVisibility(View.VISIBLE);
            musicDataList = new ArrayList<>();
            allMusicDataList = new ArrayList<>();
            searchDataList = new ArrayList<>();
            folderMap = new HashMap<>();
            try {
                Cursor cursor = getActivity().getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[]{"_id", "title", "_data", "_display_name", "duration", "album_id"},
                        "is_music != 0",
                        null,
                        "title ASC"
                );
                if (cursor != null) {
                    int trackId = cursor.getColumnIndex("_id");
                    int trackTitle = cursor.getColumnIndex("title");
                    int trackData = cursor.getColumnIndex("_data");
                    int trackDisplayName = cursor.getColumnIndex("_display_name");
                    int trackDuration = cursor.getColumnIndex("duration");
                    int albumId = cursor.getColumnIndex("album_id");

                    while (cursor.moveToNext()) {
                        String path = cursor.getString(trackData);
                        if (isAudioFile(path)) {
                            MusicData musicData = new MusicData();
                            musicData.track_Id = cursor.getLong(trackId);
                            musicData.track_Title = cursor.getString(trackTitle);
                            musicData.track_data = path;
                            musicData.track_displayName = cursor.getString(trackDisplayName);
                            musicData.track_duration = cursor.getLong(trackDuration);
                            // Fetch album art
                            long albumIdValue = cursor.getLong(albumId);
                            Uri albumArtUri = ContentUris.withAppendedId(
                                    Uri.parse("content://media/external/audio/albumart"), albumIdValue);
                            musicData.albumArt = albumArtUri.toString();
                            if (musicData.track_duration != 0) {
                                musicDataList.add(musicData);
                                allMusicDataList.add(musicData);
                                String folderName = getFolderName(path);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    folderMap.computeIfAbsent(folderName, k -> new ArrayList<>()).add(musicData);
                                }
                            }
                        }
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            songsAdapter = new StoredSongsAdapter(musicDataList, mediaPlayer, getActivity(), position -> {
                stopMediaPlayer();
                MusicData music = searchDataList.isEmpty() ? musicDataList.get(position) : searchDataList.get(position);
                Intent intent = new Intent(getActivity(), RingdroidEditActivity.class);
                intent.putExtra("music", music.track_data);
                startActivityForResult(intent, 11);
            });
            songsRecyclerView.setAdapter(songsAdapter);

            List<Folder> folderList = new ArrayList<>();
            folderList.add(new Folder(getString(R.string.all_songs), allMusicDataList.size(), new ArrayList<>(allMusicDataList), null));

//            folderList.add(new Folder("All Songs", allMusicDataList.size(), new ArrayList<>(allMusicDataList), null));
            for (Map.Entry<String, List<MusicData>> entry : folderMap.entrySet()) {
                folderList.add(new Folder(entry.getKey(), entry.getValue().size(), entry.getValue(), null));
            }
            folderAdapter = new FolderAdapter(folderList, folderName -> {
                currentFolder = folderName;
                folderNameTextView.setText(folderName);
                List<MusicData> newList = folderName.equals(getString(R.string.all_songs))
                        ? new ArrayList<>(allMusicDataList) : folderMap.get(folderName);
                musicDataList.clear();
                musicDataList.addAll(newList);
                searchDataList.clear();
                songsAdapter.updateList(musicDataList);
                foldersRecyclerView.setVisibility(View.GONE);
                songsRecyclerView.setVisibility(View.VISIBLE);
                noMusicTextView.setVisibility(musicDataList.isEmpty() ? View.VISIBLE : View.GONE);
                sortListByCurrentCriteria();
            });
            foldersRecyclerView.setAdapter(folderAdapter);

            progressBar.setVisibility(View.GONE);
            if (musicDataList.isEmpty()) {
                noMusicTextView.setVisibility(View.VISIBLE);
                songsRecyclerView.setVisibility(View.GONE);
            } else {
                noMusicTextView.setVisibility(View.GONE);
                songsRecyclerView.setVisibility(View.VISIBLE);
            }
        }





        @SuppressLint("StaticFieldLeak")
        private void setupListeners() {
            backButton.setOnClickListener(v -> getActivity().onBackPressed());
            folderSelectionLayout.setOnClickListener(v -> {
                foldersRecyclerView.setVisibility(foldersRecyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                songsRecyclerView.setVisibility(foldersRecyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            });
            searchImageView.setOnClickListener(v -> {
                searchImageView.setVisibility(View.GONE);
                folderSelectionLayout.setVisibility(View.GONE);
                musicSearchLayout.setVisibility(View.VISIBLE);
                searchEditText.requestFocus();
                searchEditText.setCursorVisible(true);
                imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
            });
            searchCloseImageView.setOnClickListener(v -> {
                searchDataList.clear();
                songsAdapter.updateList(musicDataList);
                noMusicTextView.setVisibility(View.GONE);
                songsRecyclerView.setVisibility(View.VISIBLE);
                folderSelectionLayout.setVisibility(View.VISIBLE);
                searchImageView.setVisibility(View.VISIBLE);
                musicSearchLayout.setVisibility(View.GONE);
                searchEditText.clearFocus();
                searchEditText.setCursorVisible(false);
                searchEditText.setText("");
                imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
            });
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s.toString().toLowerCase();
                    if (!isSearchInProgress) {
                        isSearchInProgress = true;
                        progressBar.setVisibility(View.VISIBLE);
                        searchDataList.clear();
                        for (MusicData music : musicDataList) {
                            if (music.track_Title != null && music.track_Title.toLowerCase().contains(query)) {
                                searchDataList.add(music);
                            }
                        }
                        songsAdapter.updateList(searchDataList.isEmpty() ? musicDataList : searchDataList);
                        progressBar.setVisibility(View.GONE);
                        noMusicTextView.setVisibility(searchDataList.isEmpty() && !query.isEmpty() ? View.VISIBLE : View.GONE);
                        songsRecyclerView.setVisibility(searchDataList.isEmpty() && !query.isEmpty() ? View.GONE : View.VISIBLE);
                        isSearchInProgress = false;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            descendingAscendingSortImageView.setOnClickListener(v -> {
                try {
                    stopMediaPlayer();
                    new AsyncTask<Void, Void, Void>() {
                        Parcelable recyclerViewState = null;

                        @Override
                        protected void onPreExecute() {
                            RecyclerView.LayoutManager layoutManager = songsRecyclerView.getLayoutManager();
                            if (layoutManager != null) {
                                recyclerViewState = layoutManager.onSaveInstanceState();
                            }
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            try {
                                String tag = (String) descendingAscendingSortImageView.getTag();
                                String descending = "Descending Order";
                                String ascending = "Ascending Order";

                                boolean isDescending = tag.equals(descending);
                                String newTag = isDescending ? ascending : descending;
                                descendingAscendingSortImageView.setTag(newTag);
                                descendingAscendingSortImageView.setTag(isDescending ? ascending : descending);
                                getActivity().runOnUiThread(() ->
                                        descendingAscendingSortImageView.setImageResource(isDescending ? R.drawable.ic_ascending_sort_24 : R.drawable.ic_descending_sort_24));

                                Collections.reverse(musicDataList);
                                if (!searchDataList.isEmpty()) {
                                    Collections.reverse(searchDataList);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            try {
                                songsAdapter.updateList(searchDataList.isEmpty() ? musicDataList : searchDataList); // New approach
                                RecyclerView.LayoutManager layoutManager = songsRecyclerView.getLayoutManager();
                                if (recyclerViewState != null && layoutManager != null) {
                                    layoutManager.onRestoreInstanceState(recyclerViewState);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            alphabetSortImageView.setOnClickListener(v -> {
                if (sortPopupWindow != null) {
                    sortPopupWindow.showAsDropDown(alphabetSortImageView, 0, 0, Gravity.END);
                }
            });
        }
        @SuppressLint("StaticFieldLeak")
        private void setupSortPopup() {
            View popupView = LayoutInflater.from(getContext()).inflate(R.layout.date_modified_popup_window, null);
            sortRadioGroup = popupView.findViewById(R.id.sort_radio_group);
            sortPopupWindow = new PopupWindow(popupView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
            sortPopupWindow.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            sortPopupWindow.setElevation(100);
            sortRadioGroup.check(R.id.date_radio_button); // Add this line
            sortRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                try {
                    stopMediaPlayer();
                    new AsyncTask<Void, Void, Void>() {
                        Parcelable recyclerViewState = null;

                        @Override
                        protected void onPreExecute() {
                            RecyclerView.LayoutManager layoutManager = songsRecyclerView.getLayoutManager();
                            if (layoutManager != null) {
                                recyclerViewState = layoutManager.onSaveInstanceState();
                            }
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            try {
                                Comparator<MusicData> comparator = null;
                                if (checkedId == R.id.name_radio_button) {
                                    comparator = (a, b) -> a.track_Title.compareToIgnoreCase(b.track_Title);
                                } else if (checkedId == R.id.date_radio_button) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        comparator = Comparator.comparingLong(a -> a.track_Id);
                                    }

//                               comparator = (a, b) -> Long.compare(a.track_Id, b.track_Id);
                                } else if (checkedId == R.id.duration_radio_button) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        comparator = Comparator.comparingLong(a -> a.track_duration);
                                    }

//                                    comparator = (a, b) -> Long.compare(a.track_duration, b.track_duration);
                                }
                                if (comparator != null) {
                                    Collections.sort(musicDataList, comparator);
                                    if (!searchDataList.isEmpty()) {
                                        Collections.sort(searchDataList, comparator);
                                    }
                                    String tag = (String) descendingAscendingSortImageView.getTag();
                                    if (!"Descending Order".equals(tag)) {
                                        Collections.reverse(musicDataList);
                                        if (!searchDataList.isEmpty()) {
                                            Collections.reverse(searchDataList);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            try {
                                songsAdapter.updateList(searchDataList.isEmpty() ? musicDataList : searchDataList);
                                RecyclerView.LayoutManager layoutManager = songsRecyclerView.getLayoutManager();
                                if (recyclerViewState != null && layoutManager != null) {
                                    layoutManager.onRestoreInstanceState(recyclerViewState);
                                }
                                sortPopupWindow.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }




        private void sortListByCurrentCriteria() {
            int checkedId = sortRadioGroup.getCheckedRadioButtonId();
            Comparator<MusicData> comparator = null;
            if (checkedId == R.id.name_radio_button) {
                comparator = (a, b) -> a.track_Title.compareToIgnoreCase(b.track_Title);
            } else if (checkedId == R.id.date_radio_button) {
                comparator = (a, b) -> Long.compare(a.track_Id, b.track_Id);
            } else if (checkedId == R.id.duration_radio_button) {
                comparator = (a, b) -> Long.compare(a.track_duration, b.track_duration);
            }
            if (comparator != null) {
                Collections.sort(musicDataList, comparator);
                if (!"Descending Order".equals(descendingAscendingSortImageView.getTag())) {
                    Collections.reverse(musicDataList);
                }
                songsAdapter.updateList(musicDataList);
//                songsAdapter.notifyDataSetChanged();
            }
        }


        private String getFolderName(String path) {
            String parent = new java.io.File(path).getParentFile().getName();
            return parent != null ? parent : "Unknown";
        }

        private boolean isAudioFile(String path) {
            return path != null && (path.endsWith(".mp3") || path.endsWith(".mp4"));
        }

        private void stopMediaPlayer() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            if (songsAdapter != null) {
                songsAdapter.stopAllPlaying();
            }
        }

        private void registerPhoneStateReceiver() {
            IntentFilter filter = new IntentFilter("android.intent.action.PHONE_STATE");
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
                        stopMediaPlayer();
                    }
                }
            };
            getActivity().registerReceiver(receiver, filter);
            ((SDcard_music_list_activity2) getActivity()).receiver = receiver;
        }

        @Override
        public void resetMusic2() {
            stopMediaPlayer();
            searchDataList.clear();
            songsAdapter.updateList(musicDataList);
            musicSearchLayout.setVisibility(View.GONE);
            searchImageView.setVisibility(View.VISIBLE);
            folderSelectionLayout.setVisibility(View.VISIBLE);
            searchEditText.setText("");
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void pause() {
            stopMediaPlayer();
        }

        public Music2Fragment getCurrFrament() {
            return this;
        }

        @Override
        public void onPause() {
            super.onPause();
            stopMediaPlayer();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (sortPopupWindow != null) {
                sortPopupWindow.dismiss();
            }
        }

        public void filter(String query) {
            if (searchEditText != null) {
                searchEditText.setText(query); // Triggers TextWatcher
            } else {
                searchDataList.clear();
                if (query == null || query.trim().isEmpty()) {
                    songsAdapter.updateList(musicDataList);
                    noMusicTextView.setVisibility(View.GONE);
                    songsRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    String lowerQuery = query.toLowerCase();
                    for (MusicData music : musicDataList) {
                        if (music.track_Title != null && music.track_Title.toLowerCase().contains(lowerQuery)) {
                            searchDataList.add(music);
                        }
                    }
                    songsAdapter.updateList(searchDataList);
                    noMusicTextView.setVisibility(searchDataList.isEmpty() ? View.VISIBLE : View.GONE);
                    songsRecyclerView.setVisibility(searchDataList.isEmpty() ? View.GONE : View.VISIBLE);
                }
            }
        }

        public void triggerSearch() {
            searchImageView.setVisibility(View.GONE);
            folderSelectionLayout.setVisibility(View.GONE);
            musicSearchLayout.setVisibility(View.VISIBLE);
            searchEditText.requestFocus();
            searchEditText.setCursorVisible(true);
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }








    @Override
    protected void onDestroy() {

        unregisterReceiver(receiver);
        super.onDestroy();
        if (adContainerView != null) {
            adContainerView.removeAllViews();
        }
        if (bannerAdView != null) {
            bannerAdView.destroy();
            bannerAdView = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {

        fragment1.backpress();

        if (fragment2 != null) {

            fragment2.resetMusic2();
        }

        if (search_layout.getVisibility() == View.VISIBLE) {
            search_button_layout.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.INVISIBLE);
            editText_search.setText("");
            editText_search.setFocusable(false);
            editText_search.setFocusableInTouchMode(false);
            finish();
        } else {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(top_layout.getWindowToken(), 0);
            super.onBackPressed();
        }
    }


    public class MusicAdapter extends FragmentPagerAdapter {


        public MusicAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                fragment1 = new Music1Fragment().createNewInstance();
                return fragment1;
            } else {
                fragment2 = new Music2Fragment().createNewInstance();
                return fragment2;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return context.getString(R.string.existed_songs);
            else
                return context.getString(R.string.stored_songs);
        }

        public Music1Fragment getFragment1() {
            return fragment1;
        }

        public Music2Fragment getFragment2() {
            return fragment2;
        }
    }



}