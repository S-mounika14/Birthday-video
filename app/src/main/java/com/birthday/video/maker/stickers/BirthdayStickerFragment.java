package com.birthday.video.maker.stickers;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.birthday.video.maker.R;
import com.birthday.video.maker.Views.NetworkViewModel;
import com.birthday.video.maker.adapters.StickerRecyclerViewAdapter;
import com.birthday.video.maker.ads.NetworkStatus;
import com.birthday.video.maker.stickers.GridSpacingItemDecoration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BirthdayStickerFragment extends Fragment implements StickerRecyclerViewAdapter.StickerClick {
    public interface A {
        void B(String c, int position, String url);
    }

    private A a;
    private Context mContext;
    private RecyclerView recyclerViewStickers;
    private View touchBlocker;
    private FrameLayout magicAnimationLayout;
    private FrameLayout noInternetLayout;
    private Button btnRefresh;
    private TextView tvNoInternet;
    private String tag;
    private List<String> stickerUrls;
    private StickerRecyclerViewAdapter adapter;
    private NetworkViewModel networkViewModel;
    private String storagePath;
    private static final String DOWNLOAD_STATUS_PREFIX = "sticker_downloaded_";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        a = (A) context;
    }

    public void setPath(A a) {
        this.a = a;
    }

    public static BirthdayStickerFragment createNewInstance(String tag, ArrayList<String> stickerPack) {
        BirthdayStickerFragment fragment = new BirthdayStickerFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("sticker_pack", stickerPack);
        args.putString("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.viewpage, container, false);

        if (getArguments() != null) {
//            stickerUrls = getArguments().getStringArrayList("sticker_pack");
            stickerUrls = new ArrayList<>(getArguments().getStringArrayList("sticker_pack"));
            tag = getArguments().getString("tag");
        }

        initializeViews(rootView);
        setupRecyclerView();
        setupListeners();
        setupNetworkViewModel();

        return rootView;
    }

    private void initializeViews(View rootView) {
        recyclerViewStickers = rootView.findViewById(R.id.recycler_view_stickers);
        touchBlocker = rootView.findViewById(R.id.touch_blocker);
        magicAnimationLayout = rootView.findViewById(R.id.magic_animation_layout);
        tvNoInternet = rootView.findViewById(R.id.tv_no_internet);
        btnRefresh = rootView.findViewById(R.id.btn_refresh);
        noInternetLayout = rootView.findViewById(R.id.no_internet_layout);
    }

    public void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                2, // Two rows
                GridLayoutManager.HORIZONTAL,
                false
        );
        int spacingInPixels = 30;

        recyclerViewStickers.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(spacingInPixels, spacingInPixels, spacingInPixels, spacingInPixels);
            }
        });
        recyclerViewStickers.setLayoutManager(gridLayoutManager);
        adapter = new StickerRecyclerViewAdapter(mContext, stickerUrls, this);
        recyclerViewStickers.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setupListeners() {
        touchBlocker.setOnTouchListener((v, event) -> true);

        btnRefresh.setOnClickListener(v -> {
            if (NetworkStatus.isConnected(mContext)) {
                networkViewModel.setIsConnected(true);
                networkViewModel.triggerRefresh();
            } else {
                Toast.makeText(mContext, R.string.please_check_network_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupNetworkViewModel() {
        networkViewModel = new ViewModelProvider(requireActivity()).get(NetworkViewModel.class);

        networkViewModel.getIsConnected().observe(getViewLifecycleOwner(), isConnected -> {
            updateUIBasedOnConnection(isConnected);
        });

        networkViewModel.getRefreshTriggered().observe(getViewLifecycleOwner(), refresh -> {
            if (refresh && NetworkStatus.isConnected(mContext)) {
                loadStickersBasedOnStatus();
                networkViewModel.resetRefresh();
            }
        });
    }

    private void updateUIBasedOnConnection(boolean isConnected) {
        noInternetLayout.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        recyclerViewStickers.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        if (isConnected) {
            loadStickersFromLocalStorage();
        }
    }

    private boolean validateLocalStorage() {
        String path = getStoragePath();
        File directory = new File(path);
        if (!directory.exists()) {
            return false;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return false;
        }

        return true;
    }

    private String getStoragePath() {
        String basePath = mContext.getExternalFilesDir(null).getAbsolutePath();
        switch (tag) {
            case "BIRTHDAY":
                return basePath + "/BirthdayStickers";
            case "CHARACTER":
                return basePath + "/CharacterStickers";
            case "EXPRESSION":
                return basePath + "/ExpressionStickers";
            case "FLOWER":
                return basePath + "/FlowerStickers";
            case "LOVE":
                return basePath + "/LoveStickers";
            case "SMILEY":
                return basePath + "/SmileyStickers";
            default:
                return basePath + "/Stickers";
        }
    }

    private boolean isStickerDownloaded(String stickerUrl) {
        String fileName = stickerUrl.substring(stickerUrl.lastIndexOf('/') + 1);
        File file = new File(getStoragePath(), fileName);
        return file.exists();
    }

    private void downloadSingleSticker(String urlString, File directory, int position) {
        if (!NetworkStatus.isConnected(mContext)) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
                    magicAnimationLayout.setVisibility(View.GONE);
                    touchBlocker.setVisibility(View.GONE);
                    recyclerViewStickers.setAlpha(1f);
                });
            }
            return;
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream input = connection.getInputStream();
            String fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
            File file = new File(directory, fileName);

            if (file.exists()) {
                file.delete();
            }

            FileOutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 2];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.close();
            input.close();
            connection.disconnect();

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
//                    Toast.makeText(mContext, R.string.downloaded, Toast.LENGTH_SHORT).show();
                    magicAnimationLayout.setVisibility(View.GONE);
                    touchBlocker.setVisibility(View.GONE);
                    recyclerViewStickers.setAlpha(1f);
                    // Update stickerUrls with the local path
                    stickerUrls.set(position, file.getAbsolutePath());
                    adapter.notifyItemChanged(position);
                    // Notify listener with the local path
                    a.B(tag, position, file.getAbsolutePath());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(mContext, R.string.download_failed, Toast.LENGTH_SHORT).show();
                    magicAnimationLayout.setVisibility(View.GONE);
                    touchBlocker.setVisibility(View.GONE);
                    recyclerViewStickers.setAlpha(1f);
                });
            }
        }
    }

    private void loadStickersFromLocalStorage() {
        storagePath = getStoragePath();

        try {
            File folder = new File(storagePath);
            if (!folder.exists() || !validateLocalStorage()) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        recyclerViewStickers.setAlpha(1f);
                        magicAnimationLayout.setVisibility(View.GONE);
                        touchBlocker.setVisibility(View.GONE);
                        if (NetworkStatus.isConnected(mContext)) {
                            recyclerViewStickers.setVisibility(View.VISIBLE);
                            // Notify adapter with original stickerUrls
                            adapter.notifyDataSetChanged();
                        } else {
                            recyclerViewStickers.setVisibility(View.GONE);
                            noInternetLayout.setVisibility(View.VISIBLE);
                        }
                    });
                }
                return;
            }

            // Update stickerUrls with local paths for downloaded stickers
            File[] listFiles = folder.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                // Create a map of filenames to local paths for quick lookup
                Map<String, String> localStickerMap = new HashMap<>();
                for (File file : listFiles) {
                    String fileName = file.getName();
                    if (fileName.endsWith("png") || fileName.endsWith("jpg")) {
                        localStickerMap.put(fileName, file.getAbsolutePath());
                    }
                }

                // Update stickerUrls: use local path if available, keep remote URL otherwise
                for (int i = 0; i < stickerUrls.size(); i++) {
                    String url = stickerUrls.get(i);
                    String fileName = url.substring(url.lastIndexOf('/') + 1);
                    if (localStickerMap.containsKey(fileName)) {
                        stickerUrls.set(i, localStickerMap.get(fileName));
                    }
                }

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        recyclerViewStickers.setAlpha(1f);
                        magicAnimationLayout.setVisibility(View.GONE);
                        touchBlocker.setVisibility(View.GONE);
                        recyclerViewStickers.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    recyclerViewStickers.setAlpha(1f);
                    magicAnimationLayout.setVisibility(View.GONE);
                    touchBlocker.setVisibility(View.GONE);
                    recyclerViewStickers.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                });
            }
        }
    }

//    private void loadStickersFromLocalStorage() {
//        storagePath = getStoragePath();
//
//        try {
//            File folder = new File(storagePath);
//            if (!folder.exists() || !validateLocalStorage()) {
//                if (getActivity() != null) {
//                    getActivity().runOnUiThread(() -> {
//                        recyclerViewStickers.setAlpha(1f);
//                        magicAnimationLayout.setVisibility(View.GONE);
//                        touchBlocker.setVisibility(View.GONE);
//                        if (NetworkStatus.isConnected(mContext)) {
//                            recyclerViewStickers.setVisibility(View.VISIBLE);
//                        } else {
//                            recyclerViewStickers.setVisibility(View.GONE);
//                            noInternetLayout.setVisibility(View.VISIBLE);
//                        }
//                    });
//                }
//                return;
//            }
//
//            File[] listFiles = folder.listFiles();
//            if (listFiles != null && listFiles.length > 0) {
//                List<String> localStickerUrls = new ArrayList<>();
//                for (File file : listFiles) {
//                    String fileName = file.getName();
//                    if (fileName.endsWith("png") || fileName.endsWith("jpg")) {
//                        localStickerUrls.add(file.getAbsolutePath());
//                    }
//                }
//                if (!localStickerUrls.isEmpty()) {
//                    stickerUrls.clear();
//                    stickerUrls.addAll(localStickerUrls);
//                    if (getActivity() != null) {
//                        getActivity().runOnUiThread(() -> {
//                            recyclerViewStickers.setAlpha(1f);
//                            magicAnimationLayout.setVisibility(View.GONE);
//                            touchBlocker.setVisibility(View.GONE);
//                            recyclerViewStickers.setVisibility(View.VISIBLE);
//                            adapter.notifyDataSetChanged();
//                        });
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkInternetAndLoadStickers();
    }

    private void checkInternetAndLoadStickers() {
        boolean isConnected = NetworkStatus.isConnected(mContext);
        networkViewModel.setIsConnected(isConnected);
        loadStickersBasedOnStatus();
    }

    public void onPageChange() {
        checkInternetAndLoadStickers();
    }

    private void loadStickersBasedOnStatus() {
        if (NetworkStatus.isConnected(mContext)) {
            noInternetLayout.setVisibility(View.GONE);
            recyclerViewStickers.setVisibility(View.VISIBLE);
            loadStickersFromLocalStorage();
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            recyclerViewStickers.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStickerClick(String stickerCategory, int position, String stickerUrl) {
        if (isStickerDownloaded(stickerUrl)) {
            a.B(stickerCategory, position, stickerUrl);
        } else {
            if (NetworkStatus.isConnected(mContext)) {
                touchBlocker.setVisibility(View.VISIBLE);
                magicAnimationLayout.setVisibility(View.VISIBLE);
                recyclerViewStickers.setAlpha(0.5f);
                new Thread(() -> {
                    File directory = new File(getStoragePath());
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    downloadSingleSticker(stickerUrl, directory, position);
                }).start();
            } else {
                Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
/*
public class BirthdayStickerFragment extends Fragment implements StickerRecyclerViewAdapter.StickerClick {

    private ArrayList<String> stickerUrls;
    private RecyclerView recyclerViewStickers;
    private Context mContext;
    private Button button;
    private StickerRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private String tag;
    private String storagePath;
    private View touchBlocker;
    private TextView tvNoInternet;
    private Button btnRefresh;
    private FrameLayout noInternetLayout;
    private NetworkViewModel networkViewModel;
    private static final String DOWNLOAD_STATUS_PREFIX = "isDownloaded_";

    public interface A {
        void B(String c, int position, String url);

    }

    private A a;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context; // Assign Context here
        a = (A) context;
    }

    public void setPath(A a) {
        this.a = a;
    }

    public static BirthdayStickerFragment createNewInstance(String tag, ArrayList<String> stickerPack) {
        BirthdayStickerFragment fragment = new BirthdayStickerFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("sticker_pack", stickerPack);
        args.putString("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.viewpage, container, false);

        if (getArguments() != null) {
            stickerUrls = getArguments().getStringArrayList("sticker_pack");
            tag = getArguments().getString("tag");
        }

        initializeViews(rootView);
        setupRecyclerView();
        setupListeners();
        setupNetworkViewModel();

        return rootView;
    }


    private void initializeViews(View rootView) {
        recyclerViewStickers = rootView.findViewById(R.id.recycler_view_stickers);
        button = rootView.findViewById(R.id.btn_download);
        touchBlocker = rootView.findViewById(R.id.touch_blocker);
        progressBar = rootView.findViewById(R.id.progress_bar);
        tvNoInternet = rootView.findViewById(R.id.tv_no_internet);
        btnRefresh = rootView.findViewById(R.id.btn_refresh);
        noInternetLayout = rootView.findViewById(R.id.no_internet_layout);
//        mContext = (MainActivity) getActivity();
    }

    public void setupRecyclerView() {
//        recyclerViewStickers.setLayoutManager(new GridLayoutManager(mContext, 3));
//        int spacingInPixels = dpToPx(3);
//        int greyColor = ContextCompat.getColor(getContext(), R.color.white);
//        recyclerViewStickers.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPixels, true, greyColor));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                2, // Two rows
                GridLayoutManager.HORIZONTAL,
                false
        );
        int spacingInPixels = 30;

        recyclerViewStickers.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(spacingInPixels, spacingInPixels, spacingInPixels, spacingInPixels);
            }
        });
        recyclerViewStickers.setLayoutManager(gridLayoutManager);
        adapter = new StickerRecyclerViewAdapter(mContext, stickerUrls);
        adapter.setOnStickerClickListener(this);
        recyclerViewStickers.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setupListeners() {
        touchBlocker.setOnTouchListener((v, event) -> true);

        btnRefresh.setOnClickListener(v -> {
            if (NetworkStatus.isConnected(mContext)) {
                networkViewModel.setIsConnected(true);
                networkViewModel.triggerRefresh();
            } else {
                Toast.makeText(mContext, R.string.please_check_network_connection, Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(v -> {
            if (NetworkStatus.isConnected(mContext)) {
                startDownload();
            } else {
                Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startDownload() {
        touchBlocker.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewStickers.setAlpha(0.5f);

        new Thread(() -> {
            downloadAllStickers();
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(mContext, R.string.downloaded, Toast.LENGTH_SHORT).show();
                    setStickerDownloadedStatus(tag, true);
                    loadStickersFromLocalStorage();
                });
            }
        }).start();
    }

    private void setupNetworkViewModel() {
        networkViewModel = new ViewModelProvider(requireActivity()).get(NetworkViewModel.class);

        networkViewModel.getIsConnected().observe(getViewLifecycleOwner(), isConnected -> {
            if (!isStickerPackDownloaded()) {
                updateUIBasedOnConnection(isConnected);
            }
        });

        networkViewModel.getRefreshTriggered().observe(getViewLifecycleOwner(), refresh -> {
            if (refresh && NetworkStatus.isConnected(mContext)) {
                loadStickersBasedOnStatus();
                networkViewModel.resetRefresh();
            }
        });
    }

    private void updateUIBasedOnConnection(boolean isConnected) {
        noInternetLayout.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        button.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        recyclerViewStickers.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        if (isConnected) {
            loadStickersFromLocalStorage();
        }
    }

    private boolean validateLocalStorage() {
        String path = getStoragePath();
        File directory = new File(path);
        if (!directory.exists()) {
            return false;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            setStickerDownloadedStatus(tag, false);
            return false;
        }

        int validStickerCount = 0;
        for (File file : files) {
            if (file.getName().endsWith("png") || file.getName().endsWith("jpg")) {
                validStickerCount++;
            }
        }

        if (validStickerCount != stickerUrls.size()) {
            setStickerDownloadedStatus(tag, false);
            return false;
        }

        return true;
    }

    private String getStoragePath() {
        String basePath = mContext.getExternalFilesDir(null).getAbsolutePath();
        switch (tag) {
            case "BIRTHDAY":
                return basePath + "/BirthdayStickers";
            case "CHARACTER":
                return basePath + "/CharacterStickers";
            case "EXPRESSION":
                return basePath + "/ExpressionStickers";
            case "FLOWER":
                return basePath + "/FlowerStickers";
            case "LOVE":
                return basePath + "/LoveStickers";
            case "SMILEY":
                return basePath + "/SmileyStickers";
            default:
                return basePath + "/Stickers";
        }
    }

    private void setStickerDownloadedStatus(String stickerTag, boolean isDownloaded) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        prefs.edit().putBoolean(DOWNLOAD_STATUS_PREFIX + stickerTag, isDownloaded).apply();
    }

    private boolean isStickerPackDownloaded() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean isDownloaded = prefs.getBoolean(DOWNLOAD_STATUS_PREFIX + tag, false);

        if (isDownloaded && !validateLocalStorage()) {
            return false;
        }

        return isDownloaded;
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkInternetAndLoadStickers();
    }

    private void checkInternetAndLoadStickers() {
        boolean isConnected = NetworkStatus.isConnected(mContext);
        networkViewModel.setIsConnected(isConnected);
        loadStickersBasedOnStatus();
    }

    public void onPageChange() {
        checkInternetAndLoadStickers();
    }

    private void loadStickersBasedOnStatus() {
        if (isStickerPackDownloaded()) {
            loadStickersFromLocalStorage();
            noInternetLayout.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            recyclerViewStickers.setVisibility(View.VISIBLE);
            recyclerViewStickers.setAlpha(1f);
        } else {
            if (NetworkStatus.isConnected(mContext)) {
                noInternetLayout.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                recyclerViewStickers.setVisibility(View.VISIBLE);
            } else {
                noInternetLayout.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                recyclerViewStickers.setVisibility(View.GONE);
            }
        }
    }

    private void downloadAllStickers() {
        if (!NetworkStatus.isConnected(mContext)) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(mContext, mContext.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });
            }
            return;
        }

        storagePath = getStoragePath();
        File directory = new File(storagePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            for (String urlString : stickerUrls) {
                downloadSingleSticker(urlString, directory);
            }
            setStickerDownloadedStatus(tag, true);
        } catch (Exception e) {
            e.printStackTrace();
            setStickerDownloadedStatus(tag, false);
        }
    }

    private void downloadSingleSticker(String urlString, File directory) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream input = connection.getInputStream();
        String fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
        File file = new File(directory, fileName);

        if (file.exists()) {
            file.delete();
        }

        FileOutputStream output = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 2];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        output.close();
        input.close();
        connection.disconnect();
    }

    private void loadStickersFromLocalStorage() {
        storagePath = getStoragePath();

        try {
            File folder = new File(storagePath);
            if (!folder.exists() || !validateLocalStorage()) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        button.setVisibility(View.VISIBLE);
                        recyclerViewStickers.setAlpha(1f);
                        progressBar.setVisibility(View.GONE);
                        touchBlocker.setVisibility(View.GONE);
                        if (NetworkStatus.isConnected(mContext)) {
                            recyclerViewStickers.setVisibility(View.VISIBLE);
                        } else {
                            recyclerViewStickers.setVisibility(View.GONE);
                            noInternetLayout.setVisibility(View.VISIBLE);
                        }
                    });
                }
                return;
            }

            File[] listFiles = folder.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                List<String> localStickerUrls = new ArrayList<>();
                for (File file : listFiles) {
                    String fileName = file.getName();
                    if (fileName.endsWith("png") || fileName.endsWith("jpg")) {
                        localStickerUrls.add(file.getAbsolutePath());
                    }
                }
                if (!localStickerUrls.isEmpty()) {
                    stickerUrls.clear();
                    stickerUrls.addAll(localStickerUrls);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            button.setVisibility(View.GONE);
                            recyclerViewStickers.setAlpha(1f);
                            progressBar.setVisibility(View.GONE);
                            touchBlocker.setVisibility(View.GONE);
                            recyclerViewStickers.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            setStickerDownloadedStatus(tag, false);
        }
    }


    @Override
    public void onStickerClick(String stickerCategory, int position, String stickerUrl) {
        a.B(stickerCategory, position, stickerUrl);


    }
}*/
