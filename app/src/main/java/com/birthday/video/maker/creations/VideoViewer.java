package com.birthday.video.maker.creations;

import static com.birthday.video.maker.Birthday_Video.Constants.deleteMedia;
import static com.birthday.video.maker.Birthday_Video.Constants.deleteMediaBulk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.birthday.video.maker.Bottomview_Fragments.CreationDiffCallback;
import com.birthday.video.maker.LayoutVisible;
import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.activities.Media;
import com.birthday.video.maker.activities.NewMainActivity;
import com.birthday.video.maker.activities.VideoActivity;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;


public class VideoViewer extends Fragment implements NewMainActivity.OnVideoDeleteClickListener, NewMainActivity.OnVideoBackPressedListener {
    private RecyclerView mRecyclerView;
    private DisplayMetrics displayMetrics;
    public Toast toast;
    public TextView toasttext;
    private Button allowPermissionsButton;
    private TextView textviewempty;
    private ImageAdapter imageAdapter;
    private static final int REQUEST_PERMISSION_DELETE = 40046;
    private static final int REQUEST_PERMISSION_BULK_DELETE = 40047;
    private static final int DELETE_CODE = 40043;
    private int clickPos;
    private Activity mContext;
    private boolean isLongClickedEnabled;
    private Dialog deleteConformationDialog;
    private final ArrayList<Media> selectedListForDeletion = new ArrayList<>();
    private View view;
    public static ArrayList<Media> videoMediaList = new ArrayList<>();
    private LayoutVisible deleteLayoutVisible;
    private ImageView videoviewempty;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public VideoViewer(){
    }
    public static VideoViewer newInstance() {
        return new VideoViewer();

    }


    private void addtoast(View view) {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.my_toast,
                    (ViewGroup) view.findViewById(R.id.custom_toast_layout));
            toasttext = (TextView) layout.findViewById(R.id.toasttext);
            toast = new Toast(getContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mContext = (Activity) context;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            if (MyMarshmallow.isStoragePermissionGranted(mRecyclerView.getContext().getApplicationContext())) {
                allowPermissionsButton.setVisibility(View.GONE);
//                videoviewempty.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                try {
                    if (isLongClickedEnabled) {
                       ((NewMainActivity) mContext).delete.setVisibility(View.VISIBLE);
                       ((NewMainActivity) mContext).cancelImageView.setVisibility(View.VISIBLE);
                       ((NewMainActivity) mContext).deleteCount.setVisibility(View.VISIBLE);
                       ((NewMainActivity) mContext).delete_lyt.setVisibility(View.VISIBLE);
                       ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                    } else {
                       ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                       ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                       ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                       ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    allowPermissionsButton.setVisibility(View.GONE);
                }else {
                    allowPermissionsButton.setVisibility(View.VISIBLE);
//                    textviewempty.setVisibility(View.GONE);
                    videoviewempty.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((NewMainActivity) requireActivity()).setOnVideoBackPressedListener(this);
        ((NewMainActivity) requireActivity()).setOnVideoDeleteClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.video_viewer_lyt, container, false);

        try {
            displayMetrics = getResources().getDisplayMetrics();

            addtoast(view);
            mRecyclerView = view.findViewById(R.id.recyclerView);
            allowPermissionsButton = view.findViewById(R.id.allow_button);
            textviewempty = view.findViewById(R.id.textempty);
            videoviewempty = view.findViewById(R.id.videoviewempty);

            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            ((SimpleItemAnimator) Objects.requireNonNull(mRecyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            imageAdapter = new ImageAdapter();

            if (MyMarshmallow.isStoragePermissionGranted(mRecyclerView.getContext().getApplicationContext())) {
                getFromSdcard();
            }

            if (videoMediaList.size() != 0) {
//                textviewempty.setVisibility(View.INVISIBLE);
                videoviewempty.setVisibility(View.INVISIBLE);
                if (videoMediaList.size() != 0) {
//                    textviewempty.setVisibility(View.GONE);
                    videoviewempty.setVisibility(View.GONE);
                } else {
//                    textviewempty.setVisibility(View.VISIBLE);
                    videoviewempty.setVisibility(View.VISIBLE);
                }

            } else {
//                textviewempty.setVisibility(View.VISIBLE);
                videoviewempty.setVisibility(View.VISIBLE);
            }
            deleteConformationDialog = new Dialog(requireActivity());
            deleteConformationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                final WindowInsetsController insetsController = mContext.getWindow().getInsetsController();
                if (insetsController != null) {
                    insetsController.hide(WindowInsets.Type.statusBars());
                }
            } else {
                deleteConformationDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
            deleteConformationDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            deleteConformationDialog.setContentView(R.layout.custom_dialog_completed);

            TextView contentTextView = deleteConformationDialog.findViewById(R.id.dialouge_text);
            contentTextView.setText(getString(R.string.dialouge_text));
            deleteConformationDialog.findViewById(R.id.positive_button).setOnClickListener(view -> {

                try {
                    if (deleteConformationDialog != null && deleteConformationDialog.isShowing())
                        deleteConformationDialog.dismiss();
                    if (selectedListForDeletion.size() == 1) {
                        try {
                            int currentPosition = videoMediaList.indexOf(selectedListForDeletion.get(0));
                            IntentSender intentSender = deleteMedia(requireActivity(), selectedListForDeletion.get(0));
                            if (intentSender == null) {
                                try {
                                    String uriString = videoMediaList.get(currentPosition).getUriString();
                                    videoMediaList.remove(currentPosition);
                                    imageAdapter.notifyItemRemoved(currentPosition);
                                    imageAdapter.notifyItemRangeChanged(currentPosition, videoMediaList.size());
                                    selectedListForDeletion.clear();
                                    Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                                    isLongClickedEnabled = false;
                                   ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                                   ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                                   ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                                   ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                                    if (videoMediaList.size() == 0) {
                                        videoviewempty.setVisibility(View.VISIBLE);
//                                        textviewempty.setVisibility(View.VISIBLE);
                                    }
                                    new MediaScanner(mContext, uriString);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                startIntentSenderForResult(intentSender, REQUEST_PERMISSION_DELETE, null, 0, 0, 0, null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (selectedListForDeletion.size() > 1) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            IntentSender intentSender = deleteMediaBulk(requireActivity(), selectedListForDeletion);
                            if (intentSender != null)
                                startIntentSenderForResult(intentSender, REQUEST_PERMISSION_BULK_DELETE, null, 0, 0, 0, null);
                        } else {
                            for (int pos = 0; pos < selectedListForDeletion.size(); ) {
                                int currentPosition = videoMediaList.indexOf(selectedListForDeletion.get(pos));
                                IntentSender intentSender = deleteMedia(requireActivity(), selectedListForDeletion.get(pos));
                                if (intentSender == null) {
                                    String uriString = videoMediaList.get(currentPosition).getUriString();
                                    videoMediaList.remove(currentPosition);
                                    imageAdapter.notifyItemRemoved(currentPosition);
                                    imageAdapter.notifyItemRangeChanged(currentPosition, videoMediaList.size());
                                    new MediaScanner(mContext, uriString);
                                }
                                pos++;
                            }
                            selectedListForDeletion.clear();
                            Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                            isLongClickedEnabled = false;
                           ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                           ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                           ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                           ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                            if (videoMediaList.size() == 0) {
                                videoviewempty.setVisibility(View.VISIBLE);
//                                textviewempty.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            deleteConformationDialog.findViewById(R.id.negative_button).setOnClickListener(view -> {
                try {
                    if (deleteConformationDialog != null && deleteConformationDialog.isShowing()) {
                        deleteConformationDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }




    public void getFromSdcard() {
        try {
            if (isAdded()) {
                String where = "bucket_display_name" + "=? OR " + "bucket_display_name" + "=? ";
                String[] args = {"Creations", "Birthday Frame Video"};
                Cursor cursor = requireActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name"}, where, args, "datetaken DESC");
                if (cursor != null) {
                    videoMediaList.clear();

                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        int nameColumnIndex = cursor.getColumnIndex("_display_name");
                        String photoName = cursor.getString(nameColumnIndex);

                        if (photoName.endsWith(".mp4")) {
                            long imageId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                            Uri photoContentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, imageId);
                            Media media = new Media();
                            media.setUriString(photoContentUri.toString());
                            media.setName(photoName);
                            media.setId(imageId);
                            videoMediaList.add(media);
                        }

                    }
                    cursor.close();
                }
                if (videoMediaList.size() > 0) {
                    videoviewempty.setVisibility(View.GONE);
//                    textviewempty.setVisibility(View.GONE);
                    mRecyclerView.setAdapter(imageAdapter);
                } else {
                    if (imageAdapter != null) {
                        imageAdapter.notifyDataSetChanged();
                    }
                    if (videoviewempty != null) {
//                        textviewempty.setVisibility(View.VISIBLE);
                        videoviewempty.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onVideoDelete(boolean cancel) {
        try {
            if (cancel) {
                for (Media p : selectedListForDeletion) {
                    this.imageAdapter.notifyItemChanged(videoMediaList.indexOf(p));
                }
                this.selectedListForDeletion.clear();
                this.isLongClickedEnabled = false;

               ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
               ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
               ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
               ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
            } else {

                if (deleteConformationDialog != null && !deleteConformationDialog.isShowing()) {
                    deleteConformationDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean doVideoBack() {

        if (this.isLongClickedEnabled) {
            try {
                for (Media p : selectedListForDeletion) {
                    this.imageAdapter.notifyItemChanged(videoMediaList.indexOf(p));
                }
                this.selectedListForDeletion.clear();
                this.isLongClickedEnabled = false;
               ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
               ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
               ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
               ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        } else {

            return true;
        }
    }


    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.UserHolder> {

        @NonNull
        @Override
        public ImageAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View main_view = getLayoutInflater().inflate(R.layout.creations_adapter_item, parent, false);
            return new ImageAdapter.UserHolder(main_view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageAdapter.UserHolder holder, @SuppressLint("RecyclerView") final int position) {
            try {
                Glide.with(mRecyclerView.getContext())
                        .load(Uri.parse(videoMediaList.get(position).getUriString()))
                        .placeholder(R.drawable.birthday_placeholder)
                        .into(holder.imgViewFlag);

                holder.imgViewFlag.setOnClickListener(v -> {
                    try {
                        if (!isLongClickedEnabled) {
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                try {
                                    clickPos = position;
                                    if (mContext == null)
                                        mContext = getActivity();
                                    if (mContext != null && isAdded()) {
                                        Intent i = new Intent(mContext, VideoActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("media_object", videoMediaList.get(clickPos));
                                        i.putExtras(bundle);
                                        i.putExtra("from", "video");
                                        startActivityForResult(i, DELETE_CODE);
                                        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }, 4);


                        } else {
                            if (selectedListForDeletion.contains(videoMediaList.get(position))) {
                                selectedListForDeletion.remove(videoMediaList.get(position));
                               ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.imgViewFlag.setContentDescription((position + 1) + " image unselected from deletion.");
                            } else {
                                selectedListForDeletion.add(videoMediaList.get(position));
                               ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.imgViewFlag.setContentDescription((position + 1) + " image selected for delete.");
                            }
                            notifyItemChanged(position);
                            if (selectedListForDeletion.size() == 0) {
                                isLongClickedEnabled = false;
                               ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                               ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                               ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                               ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);

                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                holder.imgViewFlag.setOnLongClickListener(view ->
                {
                    isLongClickedEnabled = true;

                    try {

                        if (isLongClickedEnabled) {
                           ((NewMainActivity) mContext).delete_lyt.setVisibility(View.VISIBLE);
                           ((NewMainActivity) mContext).delete.setVisibility(View.VISIBLE);
                           ((NewMainActivity) mContext).cancelImageView.setVisibility(View.VISIBLE);
                           ((NewMainActivity) mContext).deleteCount.setVisibility(View.VISIBLE);
                            if (selectedListForDeletion.contains(videoMediaList.get(position))) {

                                selectedListForDeletion.remove(videoMediaList.get(position));
                               ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));

                                holder.tick_symbol.setVisibility(View.GONE);

                                if (selectedListForDeletion.size() == 0) {

                                    isLongClickedEnabled = false;
                                    deleteLayoutVisible.isImagesDeleteLayoutVisible(selectedListForDeletion.size());
                                }
                            } else {

                                selectedListForDeletion.add(videoMediaList.get(position));
                               ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));

                                holder.tick_symbol.setVisibility(View.VISIBLE);
                            }
                            deleteLayoutVisible.isImagesDeleteLayoutVisible(selectedListForDeletion.size());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                });
                if (selectedListForDeletion.contains(videoMediaList.get(position))) {
                    holder.tick_symbol.setVisibility(View.VISIBLE);
                } else {
                    holder.tick_symbol.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return videoMediaList.size();
        }

        class UserHolder extends RecyclerView.ViewHolder {
            ImageView imgViewFlag;
            ImageView tick_symbol;

            UserHolder(View itemView) {
                super(itemView);
                imgViewFlag = itemView.findViewById(R.id.imageView1);
                tick_symbol = itemView.findViewById(R.id.tick_symbol);
                imgViewFlag.getLayoutParams().width = displayMetrics.widthPixels / 2;
                imgViewFlag.getLayoutParams().height = displayMetrics.widthPixels / 2;
            }
        }

        public void updateVideoListItems(ArrayList<Media> newMediaList) {
            try {
                if(newMediaList.size() > 0){
//                    textviewempty.setVisibility(View.GONE);
                    videoviewempty.setVisibility(View.GONE);
                }else {
//                    textviewempty.setVisibility(View.VISIBLE);
                    videoviewempty.setVisibility(View.VISIBLE);
                }
                if (videoMediaList!=null) {
                    if (mRecyclerView!=null) {
                        mRecyclerView.setAdapter(imageAdapter);
                        final CreationDiffCallback diffCallback = new CreationDiffCallback(videoMediaList, newMediaList);
                        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                        videoMediaList.clear();
                        videoMediaList.addAll(newMediaList);
                        diffResult.dispatchUpdatesTo(this);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DELETE_CODE && resultCode == Activity.RESULT_OK) {
            if (videoMediaList.size() > clickPos) {
                try {

                    videoMediaList.remove(clickPos);
                    imageAdapter.notifyItemRemoved(clickPos);
                    imageAdapter.notifyItemRangeChanged(clickPos, videoMediaList.size());
                    if (videoMediaList.size() == 0 && videoviewempty != null) {
//                        textviewempty.setVisibility(View.VISIBLE);
                        videoviewempty.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == REQUEST_PERMISSION_DELETE && resultCode == Activity.RESULT_OK) {
            try {
                IntentSender intentSender =  deleteMedia(requireActivity(), selectedListForDeletion.get(0));
                if (intentSender == null) {
                    try {
                        int currentPosition = videoMediaList.indexOf(selectedListForDeletion.get(0));
                        videoMediaList.remove(currentPosition);
                        imageAdapter.notifyItemRemoved(currentPosition);
                        imageAdapter.notifyItemRangeChanged(currentPosition, videoMediaList.size());
                        selectedListForDeletion.clear();
                        Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                        isLongClickedEnabled = false;
                       ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                       ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                       ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                       ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                        if (videoMediaList.size() == 0) {
//                            textviewempty.setVisibility(View.VISIBLE);
                            videoviewempty.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_PERMISSION_BULK_DELETE && resultCode == Activity.RESULT_OK) {
            try {
                for (int pos = 0; pos < selectedListForDeletion.size(); ) {
                    int currentPosition = videoMediaList.indexOf(selectedListForDeletion.get(pos));
                    videoMediaList.remove(currentPosition);
                    imageAdapter.notifyItemRemoved(currentPosition);
                    imageAdapter.notifyItemRangeChanged(currentPosition, videoMediaList.size());
                    pos++;
                }
                selectedListForDeletion.clear();
                Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                isLongClickedEnabled = false;
               ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
               ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
               ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
               ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                if (videoMediaList.size() == 0) {
//                    textviewempty.setVisibility(View.VISIBLE);
                    videoviewempty.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public ImageAdapter getImageAdapter(){
        return imageAdapter;
    }
}