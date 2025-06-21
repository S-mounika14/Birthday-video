package com.birthday.video.maker.stickers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.birthday.video.maker.R;
import com.birthday.video.maker.ads.AdsManager;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;

import java.util.ArrayList;

public class DownloadStickerFragment extends Fragment {

    private ArrayList<StickerCategory> sticker_cat;
    private Context mContext;
    private RelativeLayout.LayoutParams params;
    private OnStickerDownload onDownload;
    private ImageAdapter imageAdapter;
    private int pos;
    private Dialog watchVideoDialog;
    private Dialog loadingVideoDialog;
    private boolean isRewarded;
    private RecyclerView recyclerView;
    private Parcelable recyclerViewState;

    public interface OnStickerDownload {
        void onDownloadClick(int position,StickerCategory category,String categoryName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.download_layout, container, false);

        try {
            this.sticker_cat = (ArrayList<StickerCategory>)getArguments().getSerializable("sticker_category");
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            imageAdapter = new ImageAdapter();
            params = new RelativeLayout.LayoutParams(width / 2, width / 2);
            recyclerView = rootView.findViewById(R.id.gridview);
            recyclerView.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(imageAdapter);
            if(savedInstanceState!= null){
                recyclerViewState = savedInstanceState.getParcelable("rPosition");
            }
            if (recyclerViewState != null)
                gridLayoutManager.onRestoreInstanceState(recyclerViewState);
            watchVideoDialog = new Dialog(mContext, R.style.DialogSlideAnimationTopDown);
            watchVideoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            watchVideoDialog.setCancelable(true);
            watchVideoDialog.setCanceledOnTouchOutside(false);
            loadingVideoDialog = new Dialog(mContext, R.style.MaterialDialogSheet);
            loadingVideoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loadingVideoDialog.setCancelable(true);
            loadingVideoDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public static DownloadStickerFragment createNewInstance(ArrayList<StickerCategory> sticker_cat) {
        DownloadStickerFragment myFragment = new DownloadStickerFragment();

        Bundle args = new Bundle();
        args.putSerializable("sticker_category", sticker_cat);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        onDownload = (OnStickerDownload) mContext;
    }



    public void refresh(){
        if(imageAdapter!=null) {
            imageAdapter.notifyDataSetChanged();
        }
    }



    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.UserHolder> {



        public Object getItem(int position) {
            return sticker_cat.get(position);
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View main_view = getLayoutInflater().inflate(R.layout.more_stickers_adapter, parent, false);
            return new UserHolder(main_view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, @SuppressLint("RecyclerView") final int position) {
            try {
                holder.r1.setLayoutParams(params);
                Glide.with(mContext).load(sticker_cat.get(position).getResource()).into(holder.imgViewFlag);
                holder.cat_title.setText(sticker_cat.get(position).getStickerName());

                holder.r1.setOnClickListener(v -> {
                    try {
                        pos=position;
                        if(InternetStatus.isConnected(mContext))
                        showWatchVideoDialog();
                        else
                            Toast.makeText(mContext, R.string.internet_not_connected, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return sticker_cat.size();
        }


        class UserHolder extends RecyclerView.ViewHolder{
            ImageView imgViewFlag;
            TextView cat_title;
            RelativeLayout r1;
            UserHolder(View itemView) {
                super(itemView);
               imgViewFlag = itemView.findViewById(R.id.creation_imageView);
               cat_title = itemView.findViewById(R.id.cat_title);
               r1 = itemView.findViewById(R.id.rl_griditem_creation);
            }
        }
    }

    @SuppressLint("CheckResult")
    private void showWatchVideoDialog() {
        try {
            if (watchVideoDialog.getWindow() != null) {
                watchVideoDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    final WindowInsetsController insetsController = requireActivity().getWindow().getInsetsController();
                    if (insetsController != null) {
                        insetsController.hide(WindowInsets.Type.statusBars());
                    }
                } else {
                    watchVideoDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }

            @SuppressLint("InflateParams") final View watchDialogView = LayoutInflater.from(mContext).inflate(R.layout.watch_video_download_dialog, null, false);
            watchVideoDialog.setContentView(watchDialogView);

            RelativeLayout closeFrameLayout = watchDialogView.findViewById(R.id.close_dialog_frame_layout);
            RelativeLayout watchFrameLayout = watchDialogView.findViewById(R.id.watch_frame_layout);
            TextView contentTextView = watchDialogView.findViewById(R.id.tv_conform_text);
            contentTextView.setText(getString(R.string.watch_a_video_to_get_this_pack_for_free));


            closeFrameLayout.setOnClickListener(view -> {
                if (watchVideoDialog != null && watchVideoDialog.isShowing())
                    watchVideoDialog.dismiss();
            });

            watchFrameLayout.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                try {
//                    isRewarded = false;
//                    BirthdayWishMakerApplication.getInstance().getAdsManager().loadRewardedAd();
                    new Handler().postDelayed(() -> {
                        try {
//                            if (isRewarded) {
                                StickerCategory category = sticker_cat.get(pos);
                                String categoryName = category.getCategoryName();
                                onDownload.onDownloadClick(pos,category,categoryName);
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 500);

//                    if (watchVideoDialog != null && watchVideoDialog.isShowing())
//                        watchVideoDialog.dismiss();
//                    BirthdayWishMakerApplication.getInstance().getAdsManager().setRewardedAdListener(new AdsManager.OnRewardedAdListener() {
//                        @Override
//                        public void onAdShowedFullScreenContent() {
//
//                        }
//
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            try {
//                                BirthdayWishMakerApplication.getInstance().getAdsManager().loadRewardedAd();
//                                new Handler().postDelayed(() -> {
//                                    try {
//                                        if (isRewarded) {
//                                            StickerCategory category = sticker_cat.get(pos);
//                                            String categoryName = category.getCategoryName();
//                                            onDownload.onDownloadClick(pos,category,categoryName);
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }, 500);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onUserEarnedReward() {
//                            isRewarded = true;
//                        }
//
//                        @Override
//                        public void onAdLoaded() {
//                            try {
//                                if (loadingVideoDialog != null && loadingVideoDialog.isShowing()) {
//                                    new Handler().postDelayed(() -> loadingVideoDialog.dismiss(), 100);
//                                    BirthdayWishMakerApplication.getInstance().getAdsManager().showRewardedAd();
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                    if (BirthdayWishMakerApplication.getInstance().getAdsManager().isRewardedAdAvailable())
//                        BirthdayWishMakerApplication.getInstance().getAdsManager().showRewardedAd();
//                    else
//                        showLoadingVideoDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 250));


            if (watchVideoDialog != null && !watchVideoDialog.isShowing()) {
                watchVideoDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLoadingVideoDialog() {
        try {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int displayWidth = displayMetrics.widthPixels;
            if (loadingVideoDialog.getWindow() != null) {
                loadingVideoDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    final WindowInsetsController insetsController = requireActivity().getWindow().getInsetsController();
                    if (insetsController != null) {
                        insetsController.hide(WindowInsets.Type.statusBars());
                    }
                } else {
                    loadingVideoDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }

            @SuppressLint("InflateParams") final View loadingDialogView = LayoutInflater.from(mContext).inflate(R.layout.load_video, null, false);
            loadingVideoDialog.setContentView(loadingDialogView);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(loadingVideoDialog.getWindow().getAttributes());
            layoutParams.width = (int) (displayWidth * 0.9f);
            layoutParams.gravity = Gravity.CENTER;
            loadingVideoDialog.getWindow().setAttributes(layoutParams);

            FrameLayout closeFrameLayout = loadingDialogView.findViewById(R.id.close_dialog_frame_layout);
            ProgressBar loadingProgressBar = loadingDialogView.findViewById(R.id.loading_progress_bar);

            ColorDrawableCompat.setColorFilter(loadingProgressBar.getIndeterminateDrawable(), getResources().getColor(R.color.blueColorPrimary));
            closeFrameLayout.setOnClickListener(view -> {
                try {
                    if (loadingVideoDialog != null && loadingVideoDialog.isShowing())
                        loadingVideoDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if (loadingVideoDialog != null && !loadingVideoDialog.isShowing()) {
                loadingVideoDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try{
            if (recyclerView.getLayoutManager() != null)
                outState.putParcelable("rPosition", recyclerView.getLayoutManager().onSaveInstanceState());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
