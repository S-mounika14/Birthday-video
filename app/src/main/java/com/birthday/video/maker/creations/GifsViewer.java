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
import android.util.Log;
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
import com.birthday.video.maker.MultiShareActivity;
import com.birthday.video.maker.MultiShareActivityforGifs;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.CreationShare;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.activities.Media;
import com.birthday.video.maker.activities.NewMainActivity;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.Collections;


public class GifsViewer extends Fragment implements NewMainActivity.OnGifDeleteClickListener, NewMainActivity.OnGifBackPressedListener {

    //    private final AppCompatImageView delete,cancel;
    private RecyclerView mRecyclerView;
    private DisplayMetrics displayMetrics;
    public Toast toast;
    public TextView toasttext;
    private TextView textviewempty;
    private ImageAdapter imageAdapter;
    private Dialog deleteConformationDialog1;
    private Activity mContext;
    private static final int REQUEST_PERMISSION_DELETE = 40046;
    private static final int REQUEST_PERMISSION_BULK_DELETE = 40047;
    private static final int DELETE_CODE = 40048;
    private int clickPos;
    private boolean isLongClickedEnabled;
    private final ArrayList<Media> selectedListForDeletion = new ArrayList<>();
    private View view1;
    private LayoutVisible deleteLayoutVisible;

    private final ArrayList<Media> gifsMediaList = new ArrayList<>();
    private Button allowPermissionsButton;
    private ImageView gifviewempty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mContext = (Activity) context;
        }

    }


    public GifsViewer() {

    }

    public static GifsViewer newInstance() {
        return new GifsViewer();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (MyMarshmallow.isStoragePermissionGranted(mRecyclerView.getContext().getApplicationContext())) {
                allowPermissionsButton.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    allowPermissionsButton.setVisibility(View.GONE);
                } else{
                    allowPermissionsButton.setVisibility(View.VISIBLE);
//                textviewempty.setVisibility(View.GONE);
                    gifviewempty.setVisibility(View.GONE);
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
        ((NewMainActivity) requireActivity()).setOnGifBackPressedListener(this);
        ((NewMainActivity) requireActivity()).setOnGifDeleteClickListener(this);
    }

    private void addToast(View view) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.gifs_viewer_lyt, container, false);

        try {
            displayMetrics = getResources().getDisplayMetrics();
            addToast(view1);
            mRecyclerView = view1.findViewById(R.id.recyclerView);
            textviewempty = view1.findViewById(R.id.textempty);
            gifviewempty=view1.findViewById(R.id.gifviewempty);
            allowPermissionsButton = view1.findViewById(R.id.allow_button);

            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            imageAdapter = new ImageAdapter();

            if (MyMarshmallow.isStoragePermissionGranted(mRecyclerView.getContext().getApplicationContext())) {
                getFromSdcard();
            }
            deleteConformationDialog1 = new Dialog(requireActivity());
            deleteConformationDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                final WindowInsetsController insetsController = mContext.getWindow().getInsetsController();
                if (insetsController != null) {
                    insetsController.hide(WindowInsets.Type.statusBars());
                }
            } else {
                //noinspection deprecation
                deleteConformationDialog1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
            deleteConformationDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            deleteConformationDialog1.setContentView(R.layout.custom_dialog_completed);

            TextView contentTextView = deleteConformationDialog1.findViewById(R.id.dialouge_text);
            contentTextView.setText(getString(R.string.dialouge_text));

            deleteConformationDialog1.findViewById(R.id.positive_button).setOnClickListener(view -> {


                try {
                    if (deleteConformationDialog1 != null && deleteConformationDialog1.isShowing())
                        deleteConformationDialog1.dismiss();
                    if (selectedListForDeletion.size() == 1) {
                        try {
                            int currentPosition = gifsMediaList.indexOf(selectedListForDeletion.get(0));
                            IntentSender intentSender = deleteMedia(requireActivity(), selectedListForDeletion.get(0));
                            if (intentSender == null) {
                                try {
                                    String uriString = gifsMediaList.get(currentPosition).getUriString();
                                    gifsMediaList.remove(currentPosition);
                                    imageAdapter.notifyItemRemoved(currentPosition);
                                    imageAdapter.notifyItemRangeChanged(currentPosition, gifsMediaList.size());
                                    selectedListForDeletion.clear();
                                    Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                                    isLongClickedEnabled = false;
                                    ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                                    ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                                    ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                                    ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                                    if (gifsMediaList.size() == 0) {
//                                        textviewempty.setVisibility(View.VISIBLE);
                                        gifviewempty.setVisibility(View.VISIBLE);
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
                                int currentPosition = gifsMediaList.indexOf(selectedListForDeletion.get(pos));
                                IntentSender intentSender = deleteMedia(requireActivity(), selectedListForDeletion.get(pos));
                                if (intentSender == null) {
                                    String uriString = gifsMediaList.get(currentPosition).getUriString();
                                    gifsMediaList.remove(currentPosition);
                                    imageAdapter.notifyItemRemoved(currentPosition);
                                    imageAdapter.notifyItemRangeChanged(currentPosition, gifsMediaList.size());
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
                            if (gifsMediaList.size() == 0) {
//                                textviewempty.setVisibility(View.VISIBLE);
                                gifviewempty.setVisibility(View.VISIBLE);
                            }

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            deleteConformationDialog1.findViewById(R.id.negative_button).setOnClickListener(view -> {

                try {
                    if (deleteConformationDialog1 != null && deleteConformationDialog1.isShowing()) {
                        deleteConformationDialog1.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view1;
    }


    public void getFromSdcard() {
        try {
            if (isAdded()) {
                String where = "bucket_display_name" + "=? ";
                String[] args = {"Birthday Frames"};
                Cursor cursor = requireActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name"}, where,
                        args, "datetaken DESC");
                if (cursor != null) {
                    gifsMediaList.clear();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        int nameColumnIndex = cursor.getColumnIndex("_display_name");
                        String photoName = cursor.getString(nameColumnIndex);
                        if (photoName.endsWith(".gif")) {
                            long imageId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                            Uri photoContentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);

                            Media media = new Media();
                            media.setUriString(photoContentUri.toString());

                            media.setName(photoName);
                            media.setId(imageId);
                            gifsMediaList.add(media);
                        }

                    }
                    cursor.close();
                }
                if (gifsMediaList.size() > 0) {

//                    textviewempty.setVisibility(View.GONE);
                    gifviewempty.setVisibility(View.GONE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        Collections.reverse(gifsMediaList);
                    mRecyclerView.setAdapter(imageAdapter);

                } else {
                    if (imageAdapter != null) {
                        imageAdapter.notifyDataSetChanged();
                    }
                    if (gifviewempty != null) {
//                        textviewempty.setVisibility(View.VISIBLE);
                        gifviewempty.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onGifDelete(boolean cancel) {

        try {
            if (cancel) {
                for (Media p : selectedListForDeletion) {
                    this.imageAdapter.notifyItemChanged(gifsMediaList.indexOf(p));
                }
                this.selectedListForDeletion.clear();
                this.isLongClickedEnabled = false;

                ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
            } else {

                if (deleteConformationDialog1 != null && !deleteConformationDialog1.isShowing()) {
                    deleteConformationDialog1.show();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doGifBack() {

        if (this.isLongClickedEnabled) {
            try {
                for (Media p : selectedListForDeletion) {
                    this.imageAdapter.notifyItemChanged(gifsMediaList.indexOf(p));
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
                        .load(Uri.parse(gifsMediaList.get(position).getUriString()))
                        .placeholder(R.drawable.birthday_placeholder)
                        .into(holder.imgViewFlag);
               /* holder.imgViewFlag.setOnClickListener(v -> {
                    try {
                        if (!isLongClickedEnabled) {
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                try {
                                    clickPos = position;
                                    if (mContext == null)
                                        mContext = getActivity();
                                    if (mContext != null && isAdded()) {
//

                                        Intent i = new Intent(mContext, MultiShareActivityforGifs.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelableArrayList("media_list", gifsMediaList);
                                        i.putExtras(bundle);
                                        i.putExtra("from", "gif");
                                        i.putExtra("imagePosition", position);
                                        startActivityForResult(i, DELETE_CODE);
                                        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }, 4);


                        } else {
                            if (selectedListForDeletion.contains(gifsMediaList.get(position))) {
                                selectedListForDeletion.remove(gifsMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.imgViewFlag.setContentDescription((position + 1) + " image unselected from deletion.");
                            } else {
                                selectedListForDeletion.add(gifsMediaList.get(position));
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
                            if (selectedListForDeletion.contains(gifsMediaList.get(position))) {

                                selectedListForDeletion.remove(gifsMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));

                                holder.tick_symbol.setVisibility(View.GONE);

                                if (selectedListForDeletion.size() == 0) {

                                    isLongClickedEnabled = false;
                                    deleteLayoutVisible.isImagesDeleteLayoutVisible(selectedListForDeletion.size());
                                }
                            } else {

                                selectedListForDeletion.add(gifsMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));

                                holder.tick_symbol.setVisibility(View.VISIBLE);
                            }
                            deleteLayoutVisible.isImagesDeleteLayoutVisible(selectedListForDeletion.size());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });*/


                holder.imgViewFlag.setOnClickListener(v -> {
                    try {
                        Log.d("GIF_CLICK", "Image clicked at position: " + position);

                        if (!isLongClickedEnabled) {
                            Log.d("GIF_CLICK", "Normal click detected");

                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                try {
                                    clickPos = position;
                                    if (mContext == null)
                                        mContext = getActivity();

                                    if (mContext != null && isAdded()) {
                                        Log.d("GIF_CLICK", "Launching MultiShareActivityforGifs");

                                        Intent i = new Intent(mContext, MultiShareActivityforGifs.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelableArrayList("media_list", gifsMediaList);
                                        i.putExtras(bundle);
                                        i.putExtra("from", "gif");
                                        i.putExtra("imagePosition", position);
                                        startActivityForResult(i, DELETE_CODE);
                                        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                    }
                                } catch (Exception e) {
                                    Log.e("GIF_CLICK", "Exception in postDelayed runnable: ", e);
                                }

                            }, 4);

                        } else {
                            Log.d("GIF_CLICK", "Selection mode is enabled (long click active)");
                            if (selectedListForDeletion.contains(gifsMediaList.get(position))) {
                                Log.d("GIF_CLICK", "Removing image at position " + position + " from deletion list");
                                selectedListForDeletion.remove(gifsMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.imgViewFlag.setContentDescription((position + 1) + " image unselected from deletion.");
                            } else {
                                Log.d("GIF_CLICK", "Adding image at position " + position + " to deletion list");
                                selectedListForDeletion.add(gifsMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.imgViewFlag.setContentDescription((position + 1) + " image selected for delete.");
                            }
                            notifyItemChanged(position);
                            if (selectedListForDeletion.size() == 0) {
                                Log.d("GIF_CLICK", "No images selected, hiding delete layout");
                                isLongClickedEnabled = false;
                                ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                                ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                                ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                                ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("GIF_CLICK", "Exception in onClick: ", e);
                    }
                });

                holder.imgViewFlag.setOnLongClickListener(view -> {
                    isLongClickedEnabled = true;
                    Log.d("GIF_LONG_CLICK", "Long click detected at position: " + position);

                    try {
                        if (isLongClickedEnabled) {
                            ((NewMainActivity) mContext).delete_lyt.setVisibility(View.VISIBLE);
                            ((NewMainActivity) mContext).delete.setVisibility(View.VISIBLE);
                            ((NewMainActivity) mContext).cancelImageView.setVisibility(View.VISIBLE);
                            ((NewMainActivity) mContext).deleteCount.setVisibility(View.VISIBLE);

                            if (selectedListForDeletion.contains(gifsMediaList.get(position))) {
                                Log.d("GIF_LONG_CLICK", "Image already selected, removing from list");
                                selectedListForDeletion.remove(gifsMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.tick_symbol.setVisibility(View.GONE);

                                if (selectedListForDeletion.size() == 0) {
                                    Log.d("GIF_LONG_CLICK", "No images left in selection after long click");
                                    isLongClickedEnabled = false;
                                    deleteLayoutVisible.isImagesDeleteLayoutVisible(selectedListForDeletion.size());
                                }
                            } else {
                                Log.d("GIF_LONG_CLICK", "Adding image to selection list on long click");
                                selectedListForDeletion.add(gifsMediaList.get(position));
                                ((NewMainActivity) mContext).deleteCount.setText(String.valueOf(selectedListForDeletion.size()));
                                holder.tick_symbol.setVisibility(View.VISIBLE);
                            }
                            deleteLayoutVisible.isImagesDeleteLayoutVisible(selectedListForDeletion.size());
                        }
                    } catch (Exception e) {
                        Log.e("GIF_LONG_CLICK", "Exception in onLongClick: ", e);
                    }
                    return true;
                });

                if (selectedListForDeletion.contains(gifsMediaList.get(position))) {
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
            return gifsMediaList.size();
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

        public void updateGifsListItems(ArrayList<Media> newMediaList) {

            try {
                if(newMediaList.size() > 0){
//                    textviewempty.setVisibility(View.GONE);
                    gifviewempty.setVisibility(View.GONE);
                }else {
//                    textviewempty.setVisibility(View.VISIBLE);
                    gifviewempty.setVisibility(View.VISIBLE);
                }
                if (mRecyclerView != null) {
                    mRecyclerView.setAdapter(imageAdapter);
                    final CreationDiffCallback diffCallback = new CreationDiffCallback(gifsMediaList, newMediaList);
                    final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                    gifsMediaList.clear();
                    gifsMediaList.addAll(newMediaList);
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
        try {
            if (requestCode == DELETE_CODE && resultCode == Activity.RESULT_OK) {
                if (gifsMediaList.size() > clickPos) {
                    try {

                        gifsMediaList.remove(clickPos);
                        imageAdapter.notifyItemRemoved(clickPos);
                        imageAdapter.notifyItemRangeChanged(clickPos, gifsMediaList.size());
                        if (gifsMediaList.size() == 0 && gifviewempty != null) {
                            gifviewempty.setVisibility(View.VISIBLE);
//                            textviewempty.setVisibility(View.VISIBLE);
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

                            int currentPosition = gifsMediaList.indexOf(selectedListForDeletion.get(0));
                            gifsMediaList.remove(currentPosition);
                            imageAdapter.notifyItemRemoved(currentPosition);
                            imageAdapter.notifyItemRangeChanged(currentPosition, gifsMediaList.size());
                            selectedListForDeletion.clear();
                            Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                            isLongClickedEnabled = false;
                            ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                            ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                            ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                            ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                            if (gifsMediaList.size() == 0) {
//                                textviewempty.setVisibility(View.VISIBLE);
                                gifviewempty.setVisibility(View.VISIBLE);
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
                        int currentPosition = gifsMediaList.indexOf(selectedListForDeletion.get(pos));
                        gifsMediaList.remove(currentPosition);
                        imageAdapter.notifyItemRemoved(currentPosition);
                        imageAdapter.notifyItemRangeChanged(currentPosition, gifsMediaList.size());
                        pos++;
                    }
                    selectedListForDeletion.clear();
                    Toast.makeText(requireActivity(), getString(R.string.files_deleted_successfully), Toast.LENGTH_SHORT).show();
                    isLongClickedEnabled = false;
                    ((NewMainActivity) mContext).delete.setVisibility(View.GONE);
                    ((NewMainActivity) mContext).deleteCount.setVisibility(View.GONE);
                    ((NewMainActivity) mContext).cancelImageView.setVisibility(View.GONE);
                    ((NewMainActivity) mContext).delete_lyt.setVisibility(View.GONE);
                    if (gifsMediaList.size() == 0) {
//                        textviewempty.setVisibility(View.VISIBLE);
                        gifviewempty.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }
}