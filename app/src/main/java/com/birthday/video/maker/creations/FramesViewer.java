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

import androidx.activity.result.IntentSenderRequest;
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
import com.birthday.video.maker.Medias;
import com.birthday.video.maker.MultiShareActivity;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.CreationShare;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.activities.Media;
import com.birthday.video.maker.activities.NewMainActivity;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
import com.birthday.video.maker.utils.FileUtilsAPI30;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;


public class FramesViewer extends Fragment implements NewMainActivity.OnCreationDeleteClickListener, NewMainActivity.OnBackPressedListener {


    private RecyclerView mRecyclerView;
    private DisplayMetrics displayMetrics;

    public Toast toast;
    private Button allowPermissionButton;

    public TextView toasttext;
    private TextView textviewempty;
    private ImageView imageviewmpty;
    private View view;
    private ImageAdapter imageAdapter;
    private static final int REQUEST_PERMISSION_DELETE = 40046;
    private static final int REQUEST_PERMISSION_BULK_DELETE = 40047;
    private static final int DELETE_CODE = 40044;
    private int clickPos;
    private Activity mContext;
    private boolean isLongClickedEnabled;
    private Dialog deleteConformationDialog;
    private final ArrayList<Media> selectedListForDeletion = new ArrayList<>();
    private ArrayList<Media> framesMediaList = new ArrayList<>();
    private LayoutVisible deleteLayoutVisible;

    private void addtoast(View view) {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.my_toast, view.findViewById(R.id.custom_toast_layout));
            toasttext = layout.findViewById(R.id.toasttext);
            toast = new Toast(getContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            mContext = (Activity) activity;

        }

    }


    public FramesViewer() {

    }

    public static FramesViewer newInstance() {
        return new FramesViewer();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (MyMarshmallow.isStoragePermissionGranted(mRecyclerView.getContext().getApplicationContext())) {
                allowPermissionButton.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                if (isLongClickedEnabled) {
                    ((NewMainActivity) mContext).delete.setVisibility(View.VISIBLE);
                    ((NewMainActivity) mContext).delete_lyt.setVisibility(View.VISIBLE);
                    ((NewMainActivity) mContext).cancelImageView.setVisibility(View.VISIBLE);
                    ((NewMainActivity) mContext).deleteCount.setVisibility(View.VISIBLE);
                    ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                } else {
                    ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                    ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                    ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                    ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                }
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    allowPermissionButton.setVisibility(View.GONE);
//                    textviewempty.setVisibility(View.GONE);
//                    imageviewmpty.setVisibility(View.GONE);
//                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    allowPermissionButton.setVisibility(View.VISIBLE);
//                    textviewempty.setVisibility(View.GONE);
                    imageviewmpty.setVisibility(View.GONE);
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
        ((NewMainActivity) requireActivity()).setOnBackPressedListener(this);
        ((NewMainActivity) requireActivity()).setOnCreationDeleteClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frames_viewer_lyt, container, false);
        try {
            displayMetrics = getResources().getDisplayMetrics();
            addtoast(view);
            textviewempty = view.findViewById(R.id.textempty);
            imageviewmpty=view.findViewById(R.id.imageviewempty);
            allowPermissionButton = view.findViewById(R.id.allow_button);
            mRecyclerView = view.findViewById(R.id.recyclerView);
//            ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            imageAdapter = new ImageAdapter();

            if (MyMarshmallow.isStoragePermissionGranted(mRecyclerView.getContext().getApplicationContext())) {
                getFromSdcard();
            }

            deleteConformationDialog = new Dialog(requireActivity());
            deleteConformationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                final WindowInsetsController insetsController = mContext.getWindow().getInsetsController();
                if (insetsController != null) {
                    insetsController.hide(WindowInsets.Type.statusBars());
                }
            } else {
                //noinspection deprecation
                deleteConformationDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
            deleteConformationDialog.setContentView(R.layout.custom_dialog_completed);
//
            TextView contentTextView = deleteConformationDialog.findViewById(R.id.dialouge_text);
            contentTextView.setText(getString(R.string.dialouge_text));
            deleteConformationDialog.findViewById(R.id.positive_button).setOnClickListener(view -> {

                try {
                    if (deleteConformationDialog != null && deleteConformationDialog.isShowing())
                        deleteConformationDialog.dismiss();
                    //                ((MainActivity) requireActivity()).setOnCreationDeleteClickListener(null);

                    if (selectedListForDeletion.size() == 1) {
                        try {
                            int currentPosition = framesMediaList.indexOf(selectedListForDeletion.get(0));
                            IntentSender intentSender = deleteMedia(requireActivity(), selectedListForDeletion.get(0));
                            if (intentSender == null) {
                                try {
                                    String uriString = framesMediaList.get(currentPosition).getUriString();
                                    framesMediaList.remove(currentPosition);
                                    imageAdapter.notifyItemRemoved(currentPosition);
                                    imageAdapter.notifyItemRangeChanged(currentPosition, framesMediaList.size());
                                    selectedListForDeletion.clear();
                                    Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();

                                    isLongClickedEnabled = false;
                                    ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                                    ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                                    ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                                    ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                                    if (framesMediaList.size() == 0) {
//                                        textviewempty.setVisibility(View.VISIBLE);
                                        imageviewmpty.setVisibility(View.VISIBLE);
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
                                int currentPosition = framesMediaList.indexOf(selectedListForDeletion.get(pos));
                                IntentSender intentSender = deleteMedia(requireActivity(), selectedListForDeletion.get(pos));
                                if (intentSender == null) {
                                    String uriString = framesMediaList.get(currentPosition).getUriString();
                                    framesMediaList.remove(currentPosition);
                                    imageAdapter.notifyItemRemoved(currentPosition);
                                    imageAdapter.notifyItemRangeChanged(currentPosition, framesMediaList.size());
                                    new MediaScanner(mContext, uriString);
                                }
                                pos++;
                            }
                            selectedListForDeletion.clear();
                            Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                            isLongClickedEnabled = false;
                            ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                            ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                            ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                            ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                            if (framesMediaList.size() == 0) {
//                                textviewempty.setVisibility(View.VISIBLE);
                                imageviewmpty.setVisibility(View.VISIBLE);
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
            allowPermissionButton.setOnClickListener(v -> MyMarshmallow.checkStorage(requireActivity(), new MyMarshmallow.OnPermissionRequestListener() {
                @Override
                public void onPermissionAvailable() {
                    getFromSdcard();
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    public void getFromSdcard() {
        try {
            if (isAdded()) {
                String where = "bucket_display_name" + "=? ";
                String[] args = {"Birthday Frames"};
                Cursor cursor = requireActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name"}, where,
                        args, "datetaken DESC");

                if (cursor != null) {
                    framesMediaList.clear();
                    for (int i = 0; i < cursor.getCount(); i++) {

                        cursor.moveToPosition(i);
                        int nameColumnIndex = cursor.getColumnIndex("_display_name");
                        String photoName = cursor.getString(nameColumnIndex);
                        if (photoName.endsWith(".jpg") || photoName.endsWith(".jpeg") || photoName.endsWith(".png")) {
                            long imageId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                            Uri photoContentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);
                            Media media = new Media();
                            media.setUriString(photoContentUri.toString());
                            media.setName(photoName);
                            media.setId(imageId);
                            framesMediaList.add(media);
                        }

                    }
                    cursor.close();
                }


                if (framesMediaList.size() > 0) {
//                    textviewempty.setVisibility(View.GONE);
                    imageviewmpty.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        Collections.reverse(framesMediaList);
                    mRecyclerView.setAdapter(imageAdapter);
                } else {
                    if (imageAdapter != null) {
                        imageAdapter.notifyDataSetChanged();
                    }
                    if (imageviewmpty != null) {
//                        textviewempty.setVisibility(View.VISIBLE);
                        imageviewmpty.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreationDelete(boolean cancel) {
        try {
            if (cancel) {
                for (Media p : selectedListForDeletion) {
                    this.imageAdapter.notifyItemChanged(framesMediaList.indexOf(p));
                }
                this.selectedListForDeletion.clear();
                this.isLongClickedEnabled = false;

                ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
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
    public boolean doBack() {
        if (this.isLongClickedEnabled) {
            try {
                for (Media p : selectedListForDeletion) {
                    this.imageAdapter.notifyItemChanged(framesMediaList.indexOf(p));
                }
                this.selectedListForDeletion.clear();
                this.isLongClickedEnabled = false;
                ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        } else {

            return true;
        }
    }
//    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.UserHolder> {
//        private Fragment fragment;
//        private Context mContext;
//        private ArrayList<Media> framesMediaList;
//        private List<Media> selectedListForDeletion = new ArrayList<>();
//        private boolean isLongClickedEnabled = false;
//        private RecyclerView mRecyclerView;
//        private ImageView imageviewmpty;
//        private final int DELETE_CODE = 100;
//        private int clickPos;
//        private DisplayMetrics displayMetrics;
//
//        public ImageAdapter(Fragment fragment, ArrayList<Media> framesMediaList, DisplayMetrics displayMetrics) {
//            this.fragment = fragment;
//            this.mContext = fragment.getContext();
//            this.framesMediaList = framesMediaList;
//            this.displayMetrics = displayMetrics;
//        }
//
//        @NonNull
//        @Override
//        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View main_view = LayoutInflater.from(mContext).inflate(R.layout.creations_adapter_item, parent, false);
//            return new UserHolder(main_view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull UserHolder holder, @SuppressLint("RecyclerView") int position) {
//            try {
//                Glide.with(mContext)
//                        .load(Uri.parse(framesMediaList.get(position).getUriString()))
//                        .placeholder(R.drawable.birthday_placeholder)
//                        .into(holder.imgViewFlag);
//
//                holder.imgViewFlag.setOnClickListener(v -> {
//                    try {
//                        if (!isLongClickedEnabled) {
//                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                                try {
//                                    clickPos = position;
//                                    if (mContext != null && fragment.isAdded()) {
//                                        Intent i = new Intent(mContext, MultiShareActivity.class);
//                                        Bundle bundle = new Bundle();
//                                        bundle.putParcelableArrayList("media_list", framesMediaList);
//                                        i.putExtras(bundle);
//                                        i.putExtra("imagePosition", position);
//                                        fragment.startActivityForResult(i, DELETE_CODE);
//                                        fragment.getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }, 4);
//                        } else {
//                            if (selectedListForDeletion.contains(framesMediaList.get(position))) {
//                                selectedListForDeletion.remove(framesMediaList.get(position));
//                                holder.imgViewFlag.setContentDescription((position + 1) + " image unselected from deletion.");
//                            } else {
//                                selectedListForDeletion.add(framesMediaList.get(position));
//                                holder.imgViewFlag.setContentDescription((position + 1) + " image selected for delete.");
//                            }
//                            notifyItemChanged(position);
//                            if (selectedListForDeletion.size() == 0) {
//                                isLongClickedEnabled = false;
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//
//                holder.imgViewFlag.setOnLongClickListener(view -> {
//                    try {
//                        isLongClickedEnabled = true;
//                        if (isLongClickedEnabled) {
//                            // Toggle selection state
//                            Media currentMedia = framesMediaList.get(position);
//                            if (selectedListForDeletion.contains(currentMedia)) {
//                                selectedListForDeletion.remove(currentMedia);
//                                holder.tick_symbol.setVisibility(View.GONE);
//                            } else {
//                                selectedListForDeletion.add(currentMedia);
//                                holder.tick_symbol.setVisibility(View.VISIBLE);
//                            }
//                            notifyItemChanged(position);
//                            // Show delete dialog
//                            showDeleteDialog(holder, position);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return true;
//                });
//
//                // Update tick_symbol visibility based on selection state
//                if (selectedListForDeletion.contains(framesMediaList.get(position))) {
//                    holder.tick_symbol.setVisibility(View.VISIBLE);
//                } else {
//                    holder.tick_symbol.setVisibility(View.GONE);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public ArrayList<Media> getList() {
//            return framesMediaList;
//        }
//
//        @Override
//        public int getItemCount() {
//            return framesMediaList.size();
//        }
//
//        private void showDeleteDialog(UserHolder holder, int position) {
//            try {
//                new MaterialDialog.Builder(requireActivity())
//                        .setTitle(String.valueOf(R.string.are_you_sure))
//                        .setMessage(String.valueOf(R.string.remove_alert_image))
//                        .setCancelable(false)
//                        .setPositiveButton(R.string.delete_it, (dialog, which) -> {
//                            try {
//                                // Delete all selected items
//                                for (Media media : new ArrayList<>(selectedListForDeletion)) {
//                                    int currentPosition = framesMediaList.indexOf(media);
//                                    if (currentPosition != -1) {
//                                        framesMediaList.remove(currentPosition);
//                                        notifyItemRemoved(currentPosition);
//                                        IntentSender intentSender = FileUtilsAPI30.deleteMedia(mContext, media);
//                                        if (intentSender == null) {
//                                            new MediaScanner(mContext, media.getUriString());
//                                        } else {
//                                            if (fragment.isAdded()) {
//                                                fragment.startActivityForResult(
//                                                        new IntentSenderRequest.Builder(intentSender).build().getFillInIntent(),
//                                                        REQUEST_PERMISSION_DELETE);
//                                            }
//                                        }
//                                    }
//                                }
//                                selectedListForDeletion.clear();
//                                holder.tick_symbol.setVisibility(View.GONE);
//                                notifyDataSetChanged();
//                                Toast.makeText(mContext, R.string.deleted_successfully, Toast.LENGTH_SHORT).show();
//                                if (framesMediaList.isEmpty()) {
//                                    if (mContext instanceof NewMainActivity) {
//                                        ((NewMainActivity) mContext).sendDeletedList();
//                                    }
//                                    if (imageviewmpty != null) {
//                                        imageviewmpty.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            dialog.dismiss();
//                        })
//                        .negativeButton(R.string.cancel, (dialog, which) -> {
//                            // Clear selection on cancel
//                            selectedListForDeletion.clear();
//                            holder.tick_symbol.setVisibility(View.GONE);
//                            notifyDataSetChanged();
//                            isLongClickedEnabled = false;
//                            dialog.dismiss();
//                        })
//                        .build()
//                        .show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void updateFramesListItems(ArrayList<Media> newMediaList) {
//            try {
//                if (mRecyclerView != null) {
//                    if (newMediaList.size() > 0) {
//                        imageviewmpty.setVisibility(View.GONE);
//                    } else {
//                        imageviewmpty.setVisibility(View.VISIBLE);
//                    }
//                    mRecyclerView.setAdapter(this);
//                }
//                if (framesMediaList != null) {
//                    final CreationDiffCallback diffCallback = new CreationDiffCallback(framesMediaList, newMediaList);
//                    final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//                    framesMediaList.clear();
//                    framesMediaList.addAll(newMediaList);
//                    diffResult.dispatchUpdatesTo(this);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        class UserHolder extends RecyclerView.ViewHolder {
//            ImageView imgViewFlag;
//            ImageView tick_symbol;
//
//            UserHolder(View itemView) {
//                super(itemView);
//                imgViewFlag = itemView.findViewById(R.id.imageView1);
//                tick_symbol = itemView.findViewById(R.id.tick_symbol);
//                imgViewFlag.getLayoutParams().width = displayMetrics.widthPixels / 2;
//                imgViewFlag.getLayoutParams().height = displayMetrics.widthPixels / 2;
//            }
//        }
//    }



    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.UserHolder> {

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View main_view = getLayoutInflater().inflate(R.layout.creations_adapter_item, parent, false);
            return new UserHolder(main_view);
        }

        private ImageAdapter() {

        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, @SuppressLint("RecyclerView") final int position) {
            try {
                Glide.with(getContext())
                        .load(Uri.parse(framesMediaList.get(position).getUriString()))
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
//                                        Intent i = new Intent(mContext, ImageViewer.class);
//                                        Bundle bundle = new Bundle();
//                                        bundle.putParcelable("media_object", framesMediaList.get(clickPos));
//                                        i.putExtras(bundle);
//                                        i.putExtra("from", "gallery");
//                                        startActivityForResult(i, DELETE_CODE);
//                                        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                        Intent i = new Intent(mContext, MultiShareActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelableArrayList("media_list", framesMediaList);
                                        i.putExtras(bundle);
                                        i.putExtra("imagePosition", position);
                                        startActivityForResult(i, DELETE_CODE);
                                        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }, 4);


                        } else {
                            if (selectedListForDeletion.contains(framesMediaList.get(position))) {
                                selectedListForDeletion.remove(framesMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.imgViewFlag.setContentDescription((position + 1) + " image unselected from deletion.");
                            } else {
                                selectedListForDeletion.add(framesMediaList.get(position));
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
                            if (selectedListForDeletion.contains(framesMediaList.get(position))) {
                                selectedListForDeletion.remove(framesMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.tick_symbol.setVisibility(View.GONE);
                                if (selectedListForDeletion.size() == 0) {
                                    isLongClickedEnabled = false;
                                    deleteLayoutVisible.isImagesDeleteLayoutVisible(selectedListForDeletion.size());
                                }
                            } else {
                                selectedListForDeletion.add(framesMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.tick_symbol.setVisibility(View.VISIBLE);
                            }
                            deleteLayoutVisible.isImagesDeleteLayoutVisible(selectedListForDeletion.size());
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });

                if (selectedListForDeletion.contains(framesMediaList.get(position))) {
                    holder.tick_symbol.setVisibility(View.VISIBLE);
                } else {
                    holder.tick_symbol.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ArrayList<Media> getList() {
            return framesMediaList;
        }

        @Override
        public int getItemCount() {
            return framesMediaList.size();
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

        public void updateFramesListItems(ArrayList<Media> newMediaList) {
            try {
                if (mRecyclerView != null ) {
                    if(newMediaList.size() > 0){
//                        textviewempty.setVisibility(View.GONE);
                        imageviewmpty.setVisibility(View.GONE);
                    }else {
//                        textviewempty.setVisibility(View.VISIBLE);
                        imageviewmpty.setVisibility(View.VISIBLE);
                    }
                }
                if(mRecyclerView != null) {
                    mRecyclerView.setAdapter(imageAdapter);
                }
                if (framesMediaList != null) {
                    final CreationDiffCallback diffCallback = new CreationDiffCallback(framesMediaList, newMediaList);
                    final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                    framesMediaList.clear();
                    framesMediaList.addAll(newMediaList);
                    diffResult.dispatchUpdatesTo(this);
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

            if (framesMediaList.size() > clickPos) {
                try {
                    framesMediaList.remove(clickPos);
                    imageAdapter.notifyItemRemoved(clickPos);
                    imageAdapter.notifyItemRangeChanged(clickPos, framesMediaList.size());
                    if (framesMediaList.size() == 0 && imageviewmpty != null) {
//                        textviewempty.setVisibility(View.VISIBLE);
                        imageviewmpty.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == REQUEST_PERMISSION_DELETE && resultCode == Activity.RESULT_OK) {
            try {
                IntentSender intentSender = deleteMedia(requireActivity(), selectedListForDeletion.get(0));
                if (intentSender == null) {
                    try {
                        int currentPosition = framesMediaList.indexOf(selectedListForDeletion.get(0));
                        framesMediaList.remove(currentPosition);
                        imageAdapter.notifyItemRemoved(currentPosition);
                        imageAdapter.notifyItemRangeChanged(currentPosition, framesMediaList.size());
                        selectedListForDeletion.clear();
                        Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                        isLongClickedEnabled = false;
                        ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                        ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                        ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                        ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                        if (framesMediaList.size() == 0) {
//                            textviewempty.setVisibility(View.VISIBLE);
                            imageviewmpty.setVisibility(View.VISIBLE);
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
                    int currentPosition = framesMediaList.indexOf(selectedListForDeletion.get(pos));
                    framesMediaList.remove(currentPosition);
                    imageAdapter.notifyItemRemoved(currentPosition);
                    imageAdapter.notifyItemRangeChanged(currentPosition, framesMediaList.size());
                    pos++;
                }
                selectedListForDeletion.clear();
                Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                isLongClickedEnabled = false;
                ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                if (framesMediaList.size() == 0) {
//                    textviewempty.setVisibility(View.VISIBLE);
                    imageviewmpty.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }


}