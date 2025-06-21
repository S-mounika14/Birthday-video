package com.birthday.video.maker.Birthday_Video.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.Birthday_Video.mp3cutter_ringtonemaker.MarkerView;
import com.birthday.video.maker.Birthday_Video.mp3cutter_ringtonemaker.SeekTest;
import com.birthday.video.maker.Birthday_Video.mp3cutter_ringtonemaker.SongMetadataReader;
import com.birthday.video.maker.Birthday_Video.mp3cutter_ringtonemaker.WaveformView;
import com.birthday.video.maker.Birthday_Video.mp3cutter_ringtonemaker.soundfile.CheapSoundFile;
import com.birthday.video.maker.Birthday_Video.video_maker.data.MusicData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;


public class RingdroidEditActivity extends Activity implements MarkerView.MarkerListener, WaveformView.WaveformListener {
    private long mLoadingLastUpdateTime;
    private boolean mLoadingKeepGoing;
    private ProgressDialog mProgressDialog;
    private CheapSoundFile mSoundFile;


    private File mFile;
    private String mFilename;
    private String mDstFilename;
    private String mTitle;
    private String mExtension;
    private String mRecordingFilename;
    private Uri mRecordingUri;
    private WaveformView mWaveformView;
    private MarkerView mStartMarker;
    private MarkerView mEndMarker;
    private TextView mStartText;
    private TextView mEndText;
    private TextView mInfo;
    private ImageView mPlayButton;
    private ImageView mZoomInButton;
    private ImageView mZoomOutButton;
    private boolean mKeyDown;
    private String mCaption = "";
    private int mWidth;
    private int mMaxPos;
    private int mStartPos;
    private int mEndPos;
    private boolean mStartVisible;
    private boolean mEndVisible;
    private int mLastDisplayedStartPos;
    private int mLastDisplayedEndPos;
    private int mOffset;
    private int mOffsetGoal;
    private int mFlingVelocity;
    private int mPlayStartMsec;
    private int mPlayStartOffset;
    private int mPlayEndMsec;
    private Handler mHandler;
    private boolean mIsPlaying;
    private MediaPlayer mPlayer2;
    private boolean mCanSeekAccurately;
    private boolean mTouchDragging;
    private float mTouchStart;
    private int mTouchInitialOffset;
    private int mTouchInitialStartPos;
    private int mTouchInitialEndPos;
    private long mWaveformTouchStartMsec;
    private float mDensity;
    private int mMarkerLeftInset;
    private int mMarkerRightInset;
    private int mMarkerBottomOffset;

    private static final int CMD_SAVE = 1;
    private static final int CMD_RESET = 2;
    private static final int CMD_ABOUT = 3;
    private static final int REQUEST_CODE_RECORD = 1;
    public static final String PREF_ERROR_COUNT = "error_count";
    public static final String PREF_ERR_SERVER_CHECK =
            "err_server_check";
    public static final String PREF_ERR_SERVER_ALLOWED =
            "err_server_allowed";

    public static final int SERVER_ALLOWED_UNKNOWN = 0;
    public static final int SERVER_ALLOWED_NO = 1;
    public static final int SERVER_ALLOWED_YES = 2;
    private static final int REQUEST = 112;
    public static Uri newUri;
    public static CharSequence newTitle;
    public static Dialog reminder_dialog;
    private RelativeLayout ringtone_back;
    private ImageView mRewindButton,mFfwdButton;

    private TextView mMiddleText; // Add this with other TextView declarations



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mRecordingFilename = null;
        mRecordingUri = null;
        mPlayer2 = null;
        mIsPlaying = false;
        reminder_dialog = new Dialog(RingdroidEditActivity.this, R.style.DialogSlideAnim);
        reminder_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        mFilename = intent.getStringExtra("music");
        mSoundFile = null;
        mKeyDown = false;

        if (mFilename.equals("record")) {
            try {
                Intent recordIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(recordIntent, REQUEST_CODE_RECORD);
            } catch (Exception e) {
                showFinalAlert(e, R.string.record_error);
            }
        }
        mHandler = new Handler();
        loadGui();
        mHandler.postDelayed(mTimerRunnable, 100);

        if (!mFilename.equals("record")) {
            loadFromFile();
        }
    }

    @Override
    protected void onDestroy() {
        if (mPlayer2 != null && mPlayer2.isPlaying()) {
            mPlayer2.stop();
        }
        mPlayer2 = null;
        if (mRecordingFilename != null) {
            try {
                if (!new File(mRecordingFilename).delete()) {
                    showFinalAlert(new Exception(), R.string.app_name);
                }
                getContentResolver().delete(mRecordingUri, null, null);
            } catch (SecurityException e) {
                showFinalAlert(e, R.string.app_name);
            }
        }

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {

        if (requestCode == 108) {
            try {
                Uri contactData = dataIntent.getData();
                Cursor c = managedQuery(contactData, null, null, null, null);
                if (c.moveToFirst()) {
                    @SuppressLint("Range") String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    @SuppressLint("Range") String id = c.getString(
                            c.getColumnIndex(ContactsContract.Contacts._ID));

                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id);

                    ContentValues values = new ContentValues();
                    values.put(ContactsContract.Contacts.CUSTOM_RINGTONE, String.valueOf(newUri));
                    getContentResolver().update(uri, values, null, null);
                    String message = getResources().getText(R.string.success_contact_ringtone) + " " + name;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (requestCode != REQUEST_CODE_RECORD) {
            return;
        }

        if (resultCode != RESULT_OK) {
            finish();
            return;
        }

        if (dataIntent == null) {
            finish();
            return;
        }


        mRecordingUri = dataIntent.getData();
        mRecordingFilename = getFilenameFromUri(mRecordingUri);
        mFilename = mRecordingFilename;
        loadFromFile();

    }

    /**
     * Called when the orientation changes and/or the keyboard is shown
     * or hidden.  We don't need to recreate the whole activity in this
     * case, but we do need to redo our layout somewhat.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        final int saveZoomLevel = mWaveformView.getZoomLevel();
        super.onConfigurationChanged(newConfig);

        loadGui();
        enableZoomButtons();

        mHandler.postDelayed(() -> {
            mStartMarker.requestFocus();
            markerFocus(mStartMarker);

            mWaveformView.setZoomLevel(saveZoomLevel);
            mWaveformView.recomputeHeights(mDensity);

            updateDisplay();
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        try {
            if (menu != null) {
                menu.findItem(CMD_SAVE).setVisible(true);
                menu.findItem(CMD_RESET).setVisible(true);
                menu.findItem(CMD_ABOUT).setVisible(true);
            }
        }
        catch (Exception e)
        {}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CMD_SAVE:
                onSave();
                return true;
            case CMD_RESET:
                resetPositions();
                mOffsetGoal = 0;
                updateDisplay();
                return true;
            case CMD_ABOUT:
                onAbout(this);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            onPlay(mStartPos);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void waveformDraw() {
        mWidth = mWaveformView.getMeasuredWidth();
        if (mOffsetGoal != mOffset && !mKeyDown)
            updateDisplay();
        else if (mIsPlaying) {
            updateDisplay();
        } else if (mFlingVelocity != 0) {
            updateDisplay();
        }
    }


    public void waveformTouchStart(float x) {
        try {
            mTouchDragging = true;
            mTouchStart = x;
            mTouchInitialOffset = mOffset;
            mFlingVelocity = 0;
            mWaveformTouchStartMsec = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waveformTouchMove(float x) {
        try {
            mOffset = trap((int) (mTouchInitialOffset + (mTouchStart - x)));
            updateDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waveformTouchEnd() {

        mTouchDragging = false;
        mOffsetGoal = mOffset;
        long elapsedMsec = System.currentTimeMillis() -
                mWaveformTouchStartMsec;
        if (elapsedMsec < 300) {
            if (mIsPlaying) {
                int seekMsec = mWaveformView.pixelsToMillisecs(
                        (int) (mTouchStart + mOffset));
                if (seekMsec >= mPlayStartMsec &&
                        seekMsec < mPlayEndMsec) {
                    mPlayer2.seekTo(seekMsec - mPlayStartOffset);
                } else {
                    handlePause();
                }
            } else {
                onPlay((int) (mTouchStart + mOffset));
            }
        }
    }

    public void waveformFling(float vx) {
        try {
            mTouchDragging = false;
            mOffsetGoal = mOffset;
            mFlingVelocity = (int) (-vx);
            updateDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void markerDraw() {
    }

    public void markerTouchStart(MarkerView marker, float x) {
        try {
            mTouchDragging = true;
            mTouchStart = x;
            mTouchInitialStartPos = mStartPos;
            mTouchInitialEndPos = mEndPos;
            handlePause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markerTouchMove(MarkerView marker, float x) {
        try {
            float delta = x - mTouchStart;

            if (marker == mStartMarker) {
                mStartPos = trap((int) (mTouchInitialStartPos + delta));
                mEndPos = trap((int) (mTouchInitialEndPos + delta));
            } else {
                mEndPos = trap((int) (mTouchInitialEndPos + delta));
                if (mEndPos < mStartPos)
                    mEndPos = mStartPos;
            }

            updateDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markerTouchEnd(MarkerView marker) {
        mTouchDragging = false;
        if (marker == mStartMarker) {
            setOffsetGoalStart();
        } else {
            setOffsetGoalEnd();
        }
    }

    public void markerLeft(MarkerView marker, int velocity) {
        mKeyDown = true;

        if (marker == mStartMarker) {
            int saveStart = mStartPos;
            mStartPos = trap(mStartPos - velocity);
            mEndPos = trap(mEndPos - (saveStart - mStartPos));
            setOffsetGoalStart();
        }

        if (marker == mEndMarker) {
            if (mEndPos == mStartPos) {
                mStartPos = trap(mStartPos - velocity);
                mEndPos = mStartPos;
            } else {
                mEndPos = trap(mEndPos - velocity);
            }

            setOffsetGoalEnd();
        }

        updateDisplay();
    }

    public void markerRight(MarkerView marker, int velocity) {
        mKeyDown = true;

        if (marker == mStartMarker) {
            int saveStart = mStartPos;
            mStartPos += velocity;
            if (mStartPos > mMaxPos)
                mStartPos = mMaxPos;
            mEndPos += (mStartPos - saveStart);
            if (mEndPos > mMaxPos)
                mEndPos = mMaxPos;

            setOffsetGoalStart();
        }

        if (marker == mEndMarker) {
            mEndPos += velocity;
            if (mEndPos > mMaxPos)
                mEndPos = mMaxPos;

            setOffsetGoalEnd();
        }

        updateDisplay();
    }

    public void markerEnter(MarkerView marker) {
    }

    public void markerKeyUp() {
        mKeyDown = false;
        updateDisplay();
    }

    public void markerFocus(MarkerView marker) {
        mKeyDown = false;
        if (marker == mStartMarker) {
            setOffsetGoalStartNoUpdate();
        } else {
            setOffsetGoalEndNoUpdate();
        }

        // Delay updaing the display because if this focus was in
        // response to a touch event, we want to receive the touch
        // event too before updating the display.
        mHandler.postDelayed(new Runnable() {
            public void run() {
                updateDisplay();
            }
        }, 100);
    }


    public static void onAbout(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(R.string.app_name)
                .setMessage(R.string.app_name)
                .setPositiveButton(R.string.alert_ok_button, null)
                .setCancelable(false)
                .show();
    }

    private void loadGui() {
        setContentView(R.layout.example);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;

        mMarkerLeftInset = (int) (46 * mDensity);
        mMarkerRightInset = (int) (48 * mDensity);
        mMarkerBottomOffset = (int) (10 * mDensity);

        mStartText = (TextView) findViewById(R.id.starttext);
        mStartText.addTextChangedListener(mTextWatcher);

        mEndText = (TextView) findViewById(R.id.endtext);
        mInfo = (TextView) findViewById(R.id.info);
        // Ensure mInfo is visible (already removed mInfo.setVisibility(View.GONE))

        mWaveformView = (WaveformView) findViewById(R.id.waveform);
        if (mWaveformView != null) {
            mWaveformView.setListener(this);
        }

        mStartMarker = (MarkerView) findViewById(R.id.startmarker);
        mStartMarker.setOnClickListener(mMarkStartListener);
        mStartMarker.setListener(this);
        mStartMarker.setFocusable(true);
        mStartMarker.setFocusableInTouchMode(true);
        mStartMarker.setAlpha(1.0f);
//        mStartMarker.setAlpha(255);
        mStartVisible = true;

        mEndMarker = (MarkerView) findViewById(R.id.endmarker);
        mEndMarker.setOnClickListener(mMarkEndListener);
        mEndMarker.setListener(this);
        mEndMarker.setFocusable(true);
        mEndMarker.setFocusableInTouchMode(true);
        mEndMarker.setAlpha(1.0f);

//        mEndMarker.setAlpha(255);
        mEndVisible = true;

        mPlayButton = (ImageView) findViewById(R.id.play);
        mPlayButton.setEnabled(false);
        mPlayButton.setOnClickListener(mPlayListener);

        mRewindButton = (ImageView) findViewById(R.id.rew);
        mRewindButton.setOnClickListener(mRewindListener);


        mFfwdButton = (ImageView) findViewById(R.id.ffwd);
        mFfwdButton.setOnClickListener(mFfwdListener);

        mZoomInButton = (ImageView) findViewById(R.id.zoomin);
        mZoomInButton.setOnClickListener(mZoomInListener);

        mZoomOutButton = (ImageView) findViewById(R.id.zoomout);
        mZoomOutButton.setOnClickListener(mZoomOutListener);

        // Initialize save button
        TextView saveButton = (TextView) findViewById(R.id.maintext_ringtone_edit);
        saveButton.setOnClickListener(v -> {
            Log.d("Ringdroid", "Save button clicked");
            onSave();
        });

        // Initialize minus and plus buttons for start and end times
        ringtone_back = findViewById(R.id.ringtone_back);

        ImageView startMinusButton = (ImageView) findViewById(R.id.start_time_minus_image_view);
        ImageView startPlusButton = (ImageView) findViewById(R.id.start_time_plus_image_view);
        ImageView endMinusButton = (ImageView) findViewById(R.id.end_time_minus_image_view);
        ImageView endPlusButton = (ImageView) findViewById(R.id.end_time_plus_image_view);

        // Set click listeners for start time adjustments
        startMinusButton.setOnClickListener(v -> {
            if (mWaveformView != null && mWaveformView.isInitialized()) {
                double currentSeconds = mWaveformView.pixelsToSeconds(mStartPos);
                currentSeconds = Math.max(0, currentSeconds - 1);
                mStartPos = mWaveformView.secondsToPixels(currentSeconds);
                mEndPos = mStartPos + (mTouchInitialEndPos - mTouchInitialStartPos);
                if (mEndPos > mMaxPos) {
                    mEndPos = mMaxPos;
                    mStartPos = mEndPos - (mTouchInitialEndPos - mTouchInitialStartPos);
                }
                updateDisplay();
            }
        });

        startPlusButton.setOnClickListener(v -> {
            if (mWaveformView != null && mWaveformView.isInitialized()) {
                double currentSeconds = mWaveformView.pixelsToSeconds(mStartPos);
                double maxSeconds = mWaveformView.pixelsToSeconds(mMaxPos - (mTouchInitialEndPos - mTouchInitialStartPos));
                currentSeconds = Math.min(maxSeconds, currentSeconds + 1);
                mStartPos = mWaveformView.secondsToPixels(currentSeconds);
                mEndPos = mStartPos + (mTouchInitialEndPos - mTouchInitialStartPos);
                updateDisplay();
            }
        });
        // Set click listeners for end time adjustments
        endMinusButton.setOnClickListener(v -> {
            if (mWaveformView != null) {
                // Get current end time in seconds
                double currentSeconds = mWaveformView.pixelsToSeconds(mEndPos);
                // Decrease by 1 second
                currentSeconds = Math.max(mWaveformView.pixelsToSeconds(mStartPos) + 1, currentSeconds - 1);
                // Convert back to pixels
                mEndPos = mWaveformView.secondsToPixels(currentSeconds);
                updateDisplay();
            }
        });

        endPlusButton.setOnClickListener(v -> {
            if (mWaveformView != null) {
                // Get current end time in seconds
                double currentSeconds = mWaveformView.pixelsToSeconds(mEndPos);
                // Increase by 1 second
                currentSeconds = Math.min(mWaveformView.pixelsToSeconds(mMaxPos), currentSeconds + 1);
                // Convert back to pixels
                mEndPos = mWaveformView.secondsToPixels(currentSeconds);
                updateDisplay();
            }
        });
        ringtone_back.setOnClickListener(view -> onBackPressed());


        enableDisableButtons();
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void loadFromFile() {
        try {
            mFile = new File(mFilename);
            mExtension = getExtensionFromFilename(mFilename);

            SongMetadataReader metadataReader = new SongMetadataReader(
                    getApplicationContext(), mFilename);
            mTitle = "temp";
            String mArtist = metadataReader.mArtist;

            String titleLabel = mTitle;
            if (mArtist != null && mArtist.length() > 0) {
                titleLabel += " - " + mArtist;
            }
            setTitle(titleLabel);

            mLoadingLastUpdateTime = System.currentTimeMillis();
            mLoadingKeepGoing = true;
            mProgressDialog = new ProgressDialog(RingdroidEditActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setTitle(R.string.progress_dialog_loading);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setOnCancelListener(
                    dialog -> mLoadingKeepGoing = false);
            mProgressDialog.show();

            final CheapSoundFile.ProgressListener listener =
                    fractionComplete -> {
                        long now = System.currentTimeMillis();
                        if (now - mLoadingLastUpdateTime > 100) {
                            mProgressDialog.setProgress(
                                    (int) (mProgressDialog.getMax() *
                                            fractionComplete));
                            mLoadingLastUpdateTime = now;
                        }
                        return mLoadingKeepGoing;
                    };

            // Create the MediaPlayer in a background thread
            mCanSeekAccurately = false;
            new Thread() {
                public void run() {
                    mCanSeekAccurately = SeekTest.CanSeekAccurately(
                            getPreferences(Context.MODE_PRIVATE));

                    try {
                        MediaPlayer player = new MediaPlayer();
                        player.setDataSource(mFile.getAbsolutePath());
                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        player.prepare();
                        mPlayer2 = player;
                    } catch (final IOException e) {
                        Runnable runnable = () -> handleFatalError(
                                "ReadError",
                                getResources().getText(R.string.read_error),
                                e);
                        mHandler.post(runnable);
                    }
                    ;
                }
            }.start();

            // Load the sound file in a background thread
            new Thread() {
                public void run() {
                    try {
                        mSoundFile = CheapSoundFile.create(mFile.getAbsolutePath(),
                                listener);

                        if (mSoundFile == null) {
                            mProgressDialog.dismiss();
                            String name = mFile.getName().toLowerCase();
                            String[] components = name.split("\\.");
                            String err;
                            if (components.length < 2) {
                                err = getResources().getString(R.string.no_extension_error);
                            } else {
                                err = getResources().getString(R.string.bad_extension_error);
                            }
                            final String finalErr = err;
                            Runnable runnable = () -> handleFatalError("UnsupportedExtension", finalErr, new Exception());
                            mHandler.post(runnable);
                            return;
                        }
                    } catch (final Exception e) {
                        mProgressDialog.dismiss();
                        e.printStackTrace();
                        mInfo.setText(e.toString());
                        Runnable runnable = () -> handleFatalError("ReadError", getResources().getText(R.string.read_error), e);
                        mHandler.post(runnable);
                        return;
                    }
                    mProgressDialog.dismiss();
                    if (mLoadingKeepGoing) {
                        Runnable runnable = () -> finishOpeningSoundFile();
                        mHandler.post(runnable);
                    } else {
                        finish();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finishOpeningSoundFile() {
        try {
            if (mWaveformView == null) {
                Log.e("Ringdroid", "WaveformView is null in finishOpeningSoundFile");
                loadGui(); // Reinitialize GUI if not set
                if (mWaveformView == null) {
                    showFinalAlert(new Exception(), "Failed to initialize WaveformView");
                    return;
                }
            }
            mWaveformView.setSoundFile(mSoundFile);
            mWaveformView.recomputeHeights(mDensity);

            mMaxPos = mWaveformView.maxPos();
            mLastDisplayedStartPos = -1;
            mLastDisplayedEndPos = -1;

            mTouchDragging = false;

            mOffset = 0;
            mOffsetGoal = 0;
            mFlingVelocity = 0;

            mStartPos = mWaveformView.secondsToPixels(3.0); // Set start marker to 3 seconds (00:03)

            // Initialize start and end positions
//            mStartPos = 0; // Start at the beginning (0 seconds)
            // Set end position to the full length of the song
            mEndPos = mMaxPos; // End at the maximum position (full song length)

            mStartText.setText("00:03");

            // Ensure initial display is correct
//            mStartText.setText("00:00"); // Explicitly set start time to 00:00
            double songDurationSeconds = mWaveformView.pixelsToSeconds(mMaxPos);
            int totalSeconds = (int) songDurationSeconds;
            int minutes = totalSeconds / 60;
            int remainingSeconds = totalSeconds % 60;
            mEndText.setText(String.format("%02d:%02d", minutes, remainingSeconds)); // Set end time to song duration

            resetPositions();

            mInfo.setText(""); // Remove unwanted text

            updateDisplay();
            mPlayButton.setEnabled(true); // Enable play button after initialization
        } catch (Exception e) {
            e.printStackTrace();
            showFinalAlert(e, "Error opening sound file");
        }
    }
    private synchronized void updateDisplay() {
        if (mIsPlaying) {
            try {
                int now = mPlayer2.getCurrentPosition() + mPlayStartOffset;
                int frames = mWaveformView.millisecsToPixels(now);
                mWaveformView.setPlayback(frames);
                setOffsetGoalNoUpdate(frames - mWidth / 2);
                if (now >= mPlayEndMsec) {
                    handlePause();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!mTouchDragging) {
            int offsetDelta;

            if (mFlingVelocity != 0) {
                try {
                    float saveVel = mFlingVelocity;

                    offsetDelta = mFlingVelocity / 30;
                    if (mFlingVelocity > 80) {
                        mFlingVelocity -= 80;
                    } else if (mFlingVelocity < -80) {
                        mFlingVelocity += 80;
                    } else {
                        mFlingVelocity = 0;
                    }

                    mOffset += offsetDelta;

                    if (mOffset + mWidth / 2 > mMaxPos) {
                        mOffset = mMaxPos - mWidth / 2;
                        mFlingVelocity = 0;
                    }
                    if (mOffset < 0) {
                        mOffset = 0;
                        mFlingVelocity = 0;
                    }
                    mOffsetGoal = mOffset;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    offsetDelta = mOffsetGoal - mOffset;

                    if (offsetDelta > 10)
                        offsetDelta = offsetDelta / 10;
                    else if (offsetDelta > 0)
                        offsetDelta = 1;
                    else if (offsetDelta < -10)
                        offsetDelta = offsetDelta / 10;
                    else if (offsetDelta < 0)
                        offsetDelta = -1;
                    else
                        offsetDelta = 0;

                    mOffset += offsetDelta;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            // Adjust marker positions for WaveformView to prevent drawing indicators at the edge
            int adjustedStartPos = mStartPos;
            int adjustedEndPos = mEndPos;

            // If the start marker is off-screen to the left, set its position to a value that WaveformView won't draw
            int startX = mStartPos - mOffset - mMarkerLeftInset;
            if (startX + mStartMarker.getWidth() < 0) {
                adjustedStartPos = mOffset - 1000; // Move it far off-screen to the left
            }

            // If the end marker is off-screen to the left or right, set its position to a value that WaveformView won't draw
            int endX = mEndPos - mOffset - mEndMarker.getWidth() + mMarkerRightInset;
            if (endX + mEndMarker.getWidth() < 0) {
                adjustedEndPos = mOffset - 1000; // Move it far off-screen to the left
            } else if (endX >= mWaveformView.getMeasuredWidth()) {
                adjustedEndPos = mOffset + mWaveformView.getMeasuredWidth() + 1000; // Move it far off-screen to the right
            }

            mWaveformView.setParameters(adjustedStartPos, adjustedEndPos, mOffset);
            mWaveformView.invalidate();

            // Display the total song duration in mInfo TextView
            String totalDuration = formatTime(mMaxPos);
            mInfo.setText(totalDuration);

            mStartText.setText(formatTime(mStartPos));
            mEndText.setText(formatTime(mEndPos));

            mStartMarker.setContentDescription(getResources().getText(R.string.start_marker) + " " + formatTime(mStartPos));
            mEndMarker.setContentDescription(getResources().getText(R.string.end_marker) + " " + formatTime(mEndPos));

            if (startX + mStartMarker.getWidth() >= 0) {
                if (!mStartVisible) {
                    mHandler.postDelayed(() -> {
                        mStartVisible = true;
                        mStartMarker.setAlpha(1.0f);
                    }, 0);
                }
            } else {
                if (mStartVisible) {
                    mStartMarker.setAlpha(0.0f);
                    mStartVisible = false;
                }
                startX = 0;
            }

            if (endX + mEndMarker.getWidth() >= 0) {
                if (!mEndVisible) {
                    mHandler.postDelayed(() -> {
                        mEndVisible = true;
                        mEndMarker.setAlpha(1.0f);
                    }, 0);
                }
            } else {
                if (mEndVisible) {
                    mEndMarker.setAlpha(0.0f);
                    mEndVisible = false;
                }
                endX = 0;
            }

            FrameLayout.LayoutParams startParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            startParams.setMargins(startX, 10, 10, 0); // Changed top margin from -10 to 10 to move startmarker downward
            mStartMarker.setLayoutParams(startParams);

            FrameLayout.LayoutParams endParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            endParams.setMargins(endX, mWaveformView.getMeasuredHeight() - mEndMarker.getHeight() - mMarkerBottomOffset, 0, 0);
            mEndMarker.setLayoutParams(endParams);

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }



    private Runnable mTimerRunnable = new Runnable() {
        public void run() {
            // Updating an EditText is slow on Android.  Make sure
            // we only do the update if the text has actually changed.
            if (mStartPos != mLastDisplayedStartPos &&
                    !mStartText.hasFocus()) {
                mStartText.setText(formatTime(mStartPos));
                mLastDisplayedStartPos = mStartPos;
            }

            if (mEndPos != mLastDisplayedEndPos &&
                    !mEndText.hasFocus()) {
                mEndText.setText(formatTime(mEndPos));
                mLastDisplayedEndPos = mEndPos;
            }

            mHandler.postDelayed(mTimerRunnable, 100);
        }
    };

    private void enableDisableButtons() {
        if (mIsPlaying) {
            try {
                mPlayButton.setImageResource(R.drawable.ic_music_pause);
                mPlayButton.setContentDescription(getResources().getText(R.string.stop));
//                mRewindButton.setImageAlpha(255);
//                mFfwdButton.setImageAlpha(255);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }


        } else {
            try {
                mPlayButton.setImageResource(R.drawable.ic_music_play1);
//                mRewindButton.setImageAlpha(140);
//                mFfwdButton.setImageAlpha(140);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void resetPositions() {
        try {
            if (mWaveformView != null && mWaveformView.isInitialized()) {
                mStartPos = mWaveformView.secondsToPixels(3.0);
                mEndPos = mWaveformView.secondsToPixels(8.0);
                mTouchInitialStartPos = mStartPos;
                mTouchInitialEndPos = mEndPos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int trap(int pos) {
        if (pos < 0)
            return 0;
        if (pos > mMaxPos)
            return mMaxPos;
        return pos;
    }

    private void setOffsetGoalStart() {
        setOffsetGoal(mStartPos - mWidth / 2);
    }

    private void setOffsetGoalStartNoUpdate() {
        setOffsetGoalNoUpdate(mStartPos - mWidth / 2);
    }

    private void setOffsetGoalEnd() {
        setOffsetGoal(mEndPos - mWidth / 2);
    }

    private void setOffsetGoalEndNoUpdate() {
        setOffsetGoalNoUpdate(mEndPos - mWidth / 2);
    }

    private void setOffsetGoal(int offset) {
        setOffsetGoalNoUpdate(offset);
        updateDisplay();
    }

    private void setOffsetGoalNoUpdate(int offset) {
        if (mTouchDragging) {
            return;
        }

        try {
            mOffsetGoal = offset;
            if (mOffsetGoal + mWidth / 2 > mMaxPos)
                mOffsetGoal = mMaxPos - mWidth / 2;
            if (mOffsetGoal < 0)
                mOffsetGoal = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatTime(int pixels) {
        try {
            if (mWaveformView != null && mWaveformView.isInitialized()) {
                // Convert pixels to seconds
                double seconds = mWaveformView.pixelsToSeconds(pixels);

                // Convert total seconds to minutes and seconds
                int totalSeconds = (int) seconds;
                int minutes = totalSeconds / 60;
                int remainingSeconds = totalSeconds % 60;

                // Format as MM:SS with leading zeros
                return String.format("%02d:%02d", minutes, remainingSeconds);
            } else {
                return "00:00";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "00:00";
    }

    private String formatDecimal(double x) {
        int xWhole = 0;
        int x_Fraction = 0;
        try {
            xWhole = (int) x;
            x_Fraction = (int) (100 * (x - xWhole) + 0.5);
            if (x_Fraction >= 100) {
                xWhole++;
                x_Fraction -= 100;
                if (x_Fraction < 10) {
                    x_Fraction *= 10;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (x_Fraction < 10)
            return xWhole + ".0" + x_Fraction;
        else
            return xWhole + "." + x_Fraction;
    }

    private synchronized void handlePause() {
        if (mPlayer2 != null && mPlayer2.isPlaying()) {
            mPlayer2.pause();
        }
        mWaveformView.setPlayback(-1);
        mIsPlaying = false;
        enableDisableButtons();
    }

    private synchronized void onPlay(int startPosition) {
        if (mIsPlaying) {
            handlePause();
            return;
        }

        if (mPlayer2 == null) {
            return;
        }

        if (mWaveformView == null) {
            Log.e("Ringdroid", "WaveformView is null in onPlay");
            Toast.makeText(this, "Waveform is still loading", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mPlayStartMsec = mWaveformView.pixelsToMillisecs(startPosition);
            if (startPosition < mStartPos) {
                mPlayEndMsec = mWaveformView.pixelsToMillisecs(mStartPos);
            } else if (startPosition > mEndPos) {
                mPlayEndMsec = mWaveformView.pixelsToMillisecs(mMaxPos);
            } else {
                mPlayEndMsec = mWaveformView.pixelsToMillisecs(mEndPos);
            }

            mPlayStartOffset = 0;

            int startFrame = mWaveformView.secondsToFrames(mPlayStartMsec * 0.001);
            int endFrame = mWaveformView.secondsToFrames(mPlayEndMsec * 0.001);
            int startByte = mSoundFile.getSeekableFrameOffset(startFrame);
            int endByte = mSoundFile.getSeekableFrameOffset(endFrame);
            if (mCanSeekAccurately && startByte >= 0 && endByte >= 0) {
                try {
                    mPlayer2.reset();
                    mPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    FileInputStream subsetInputStream = new FileInputStream(mFile.getAbsolutePath());
                    mPlayer2.setDataSource(subsetInputStream.getFD(), startByte, endByte - startByte);
                    mPlayer2.prepare();
                    mPlayStartOffset = mPlayStartMsec;
                } catch (Exception e) {
                    mPlayer2.reset();
                    mPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mPlayer2.setDataSource(mFile.getAbsolutePath());
                    mPlayer2.prepare();
                    mPlayStartOffset = 0;
                }
            }

            mPlayer2.setOnCompletionListener(arg0 -> handlePause());
            mIsPlaying = true;

            if (mPlayStartOffset == 0) {
                mPlayer2.seekTo(mPlayStartMsec);
            }
            mPlayer2.start();
            updateDisplay();
            enableDisableButtons();
        } catch (Exception e) {
            showFinalAlert(e, R.string.play_error);
        }
    }


    private void showFinalAlert(Exception e, CharSequence message) {
        CharSequence title;
        if (e != null) {
            Log.e("Ringdroid", "Error: " + message);
            Log.e("Ringdroid", getStackTrace(e));
            title = getResources().getText(R.string.alert_title_failure);
            setResult(RESULT_CANCELED, new Intent());
        } else {
            Log.i("Ringdroid", "Success: " + message);
            title = getResources().getText(R.string.alert_title_success);
        }

        new AlertDialog.Builder(RingdroidEditActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_ok_button, (dialog, whichButton) -> finish())
                .setCancelable(false)
                .show();
    }

    private void showFinalAlert(Exception e, int messageResourceId) {
        showFinalAlert(e, getResources().getText(messageResourceId));
    }

    private String makeRingtoneFilename(CharSequence title, String extension) {
        String parentdir;
        String path = null;
        try {
            if (!Constants.TEMP_DIRECTORY.exists())
                Constants.TEMP_DIRECTORY.mkdirs();

            parentdir = Constants.TEMP_DIRECTORY.toString();
            File parentDirFile = new File(parentdir);
            parentDirFile.mkdirs();
            if (!parentDirFile.isDirectory()) {
                parentdir = "/sdcard";
            }

            String filename = "";
            for (int i = 0; i < title.length(); i++) {
                if (Character.isLetterOrDigit(title.charAt(i))) {
                    filename += title.charAt(i);
                }
            }

            // Try to make the filename unique
            path = null;
            for (int i = 0; i < 100; i++) {
                String testPath;
                if (i > 0)
                    testPath = parentdir + "/" + filename + i + extension;
                else
                    testPath = parentdir + "/" + filename + extension;
                try {
                    RandomAccessFile f = new RandomAccessFile(
                            new File(testPath), "r");
                } catch (Exception e) {
                    // Good, the file didn't exist
                    path = testPath;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    private void saveRingtone(final CharSequence title) {
        final String outPath = makeRingtoneFilename(title, mExtension);

        if (outPath == null) {
            showFinalAlert(new Exception(), R.string.no_unique_filename);
            return;
        }

        mDstFilename = outPath;

        if (new File(mDstFilename).exists()) {
            new File(mDstFilename).delete();
        }

        double startTime = mWaveformView.pixelsToSeconds(mStartPos);
        double endTime = mWaveformView.pixelsToSeconds(mEndPos);
        final int startFrame = mWaveformView.secondsToFrames(startTime);
        final int endFrame = mWaveformView.secondsToFrames(endTime);
        final int duration = (int) (endTime - startTime + 0.5);

        // Create an indeterminate progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(R.string.progress_dialog_saving);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        // Save the sound file in a background thread
        new Thread() {
            public void run() {
                final File outFile = new File(outPath);
                try {
                    // Write the new file
                    mSoundFile.WriteFile(outFile,
                            startFrame,
                            endFrame - startFrame);

                    // Try to load the new file to make sure it worked
                    final CheapSoundFile.ProgressListener listener =
                            new CheapSoundFile.ProgressListener() {
                                public boolean reportProgress(double frac) {

                                    return true;  // Keep going
                                }
                            };
                    CheapSoundFile.create(outPath, listener);
                } catch (Exception e) {
                    mProgressDialog.dismiss();

                    CharSequence errorMessage;
                    if (e.getMessage().equals("No space left on device")) {
                        errorMessage = getResources().getText(
                                R.string.no_space_error);
                        e = null;
                    } else {
                        errorMessage = getResources().getText(
                                R.string.write_error);
                    }

                    final CharSequence finalErrorMessage = errorMessage;
                    final Exception finalException = e;
                    Runnable runnable = new Runnable() {
                        public void run() {
                            handleFatalError(
                                    "WriteError",
                                    finalErrorMessage,
                                    finalException);
                        }
                    };
                    mHandler.post(runnable);
                    return;
                }
                mProgressDialog.dismiss();
                Runnable runnable = () -> {
                    BirthdayWishMakerApplication application = BirthdayWishMakerApplication.getInstance();
                    int seconds = (int) ((application.getSelectedImages().size() - 1) * application.getSecond());
                    MusicData musicData = new MusicData();
                    musicData.track_data = mDstFilename.toString();
                    long duration1 = getDurationOfSound(getApplicationContext(), mDstFilename.toString());
                    musicData.track_duration = duration1;
                    BirthdayWishMakerApplication.getInstance().setMusicData(musicData);
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();

                };
                mHandler.post(runnable);


            }
        }.start();
    }


    public static long getDurationOfSound(Context context, Object soundFile) {
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


    private void handleFatalError(
            final CharSequence errorInternalName,
            final CharSequence errorString,
            final Exception exception) {
        Log.i("Ringdroid", "handleFatalError");

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        int failureCount = prefs.getInt(PREF_ERROR_COUNT, 0);
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(PREF_ERROR_COUNT, failureCount + 1);
        prefsEditor.commit();

        // Check if we already have a pref for whether or not we can
        // contact the server.
        int serverAllowed = prefs.getInt(PREF_ERR_SERVER_ALLOWED,
                SERVER_ALLOWED_UNKNOWN);

        if (serverAllowed == SERVER_ALLOWED_NO) {
            Log.i("Ringdroid", "ERR: SERVER_ALLOWED_NO");

            // Just show a simple "write error" message
            showFinalAlert(exception, errorString);
            return;
        }

        if (serverAllowed == SERVER_ALLOWED_YES) {
            Log.i("Ringdroid", "SERVER_ALLOWED_YES");

            new AlertDialog.Builder(RingdroidEditActivity.this)
                    .setTitle(R.string.alert_title_failure)
                    .setMessage(errorString)
                    .setPositiveButton(
                            R.string.alert_ok_button,
                            (dialog, whichButton) -> finish())
                    .setCancelable(false)
                    .show();
            return;
        }

        // The number of times the user must have had a failure before
        // we'll ask them.  Defaults to 1, and each time they click "Later"
        // we double and add 1.
        final int allowServerCheckIndex =
                prefs.getInt(PREF_ERR_SERVER_CHECK, 1);
        if (failureCount < allowServerCheckIndex) {
            Log.i("Ringdroid", "failureCount " + failureCount +
                    " is less than " + allowServerCheckIndex);
            // Just show a simple "write error" message
            showFinalAlert(exception, errorString);
            return;
        }

        final SpannableString message = new SpannableString(errorString);
        Linkify.addLinks(message, Linkify.ALL);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title_failure)
                .setMessage(message)
                .setPositiveButton(
                        R.string.alert_ok_button,
                        (dialog12, whichButton) -> {
                            prefsEditor.putInt(PREF_ERR_SERVER_ALLOWED, SERVER_ALLOWED_YES);
                            prefsEditor.commit();
                            finish();
                        })
                .setNeutralButton("",
                        (dialog1, whichButton) -> {
                            prefsEditor.putInt(PREF_ERR_SERVER_CHECK, 1 + allowServerCheckIndex * 2);
                            prefsEditor.commit();
                            finish();
                        })
                .setNegativeButton(
                        R.string.cancel,
                        (dialog13, whichButton) -> {
                            prefsEditor.putInt(PREF_ERR_SERVER_ALLOWED,
                                    SERVER_ALLOWED_NO);
                            prefsEditor.commit();
                            finish();
                        })
                .setCancelable(false)
                .show();

        // Make links clicky
        ((TextView) dialog.findViewById(android.R.id.message))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }


   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do here
                } else {
                    Toast.makeText(getApplicationContext(), "The app was not allowed to read your store.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }*/

    private void onSave() {
        if (mIsPlaying) {
            handlePause();
        }
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            public void handleMessage(Message response) {
                newTitle = (CharSequence) response.obj;
                saveRingtone(newTitle);
            }
        };
      /*  if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(RingdroidEditActivity.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) RingdroidEditActivity.this, PERMISSIONS, REQUEST);
            } else {
                Message message = Message.obtain(handler);
                message.obj = mTitle;
                message.arg1 = 3;
                message.sendToTarget();
            }
        } else {
            Message message = Message.obtain(handler);
            message.obj = mTitle;
            message.arg1 = 3;
            message.sendToTarget();
        }*/
        Message message = Message.obtain(handler);
        message.obj = mTitle;
        message.arg1 = 3;
        message.sendToTarget();

    }


    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void enableZoomButtons() {
        mZoomInButton.setEnabled(mWaveformView.canZoomIn());
        mZoomOutButton.setEnabled(mWaveformView.canZoomOut());
    }

    private OnClickListener mSaveListener = sender -> {
        Animation bounce= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_anim);
        sender.startAnimation(bounce);
        bounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onSave();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    };

    private OnClickListener mPlayListener = new OnClickListener() {
        public void onClick(View sender) {
            onPlay(mStartPos);
        }
    };

    private OnClickListener mZoomInListener = new OnClickListener() {
        public void onClick(View sender) {
            mWaveformView.zoomIn();
            mStartPos = mWaveformView.getStart();
            mEndPos = mWaveformView.getEnd();
            mMaxPos = mWaveformView.maxPos();
            mOffset = mWaveformView.getOffset();
            mOffsetGoal = mOffset;
            enableZoomButtons();
            updateDisplay();
        }
    };

    private OnClickListener mZoomOutListener = new OnClickListener() {
        public void onClick(View sender) {
            mWaveformView.zoomOut();
            mStartPos = mWaveformView.getStart();
            mEndPos = mWaveformView.getEnd();
            mMaxPos = mWaveformView.maxPos();
            mOffset = mWaveformView.getOffset();
            mOffsetGoal = mOffset;
            enableZoomButtons();
            updateDisplay();
        }
    };

    private OnClickListener mRewindListener = new OnClickListener() {
        public void onClick(View sender) {
            if (mIsPlaying) {
                int newPos = mPlayer2.getCurrentPosition() - 5000;
                if (newPos < mPlayStartMsec)
                    newPos = mPlayStartMsec;
                mStartMarker.setEnabled(true);

                mPlayer2.seekTo(newPos);
            } else {
                mStartMarker.requestFocus();
                mStartMarker.setEnabled(false);
                markerFocus(mStartMarker);
                Toast.makeText(getApplicationContext(), "Button works while playing music only", Toast.LENGTH_SHORT).show();

            }
        }
    };

    private OnClickListener mFfwdListener = new OnClickListener() {
        public void onClick(View sender) {

            if (mIsPlaying) {
                int newPos = 5000 + mPlayer2.getCurrentPosition();
                if (newPos > mPlayEndMsec)
                    newPos = mPlayEndMsec;
                mPlayer2.seekTo(newPos);

            } else {
                mEndMarker.requestFocus();
                markerFocus(mEndMarker);
                Toast.makeText(getApplicationContext(), "Button works while playing music only", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private OnClickListener mMarkStartListener = new OnClickListener() {
        public void onClick(View sender) {
            if (mIsPlaying) {
                mStartPos = mWaveformView.millisecsToPixels(
                        mPlayer2.getCurrentPosition() + mPlayStartOffset);
                updateDisplay();
            }
        }
    };

    private OnClickListener mMarkEndListener = new OnClickListener() {
        public void onClick(View sender) {
            if (mIsPlaying) {
                mEndPos = mWaveformView.millisecsToPixels(
                        mPlayer2.getCurrentPosition() + mPlayStartOffset);
                updateDisplay();
                handlePause();
            }
        }
    };

    private TextWatcher mTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        public void onTextChanged(CharSequence s,
                                  int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            try {
                if (mStartText.hasFocus()) {
                    try {
                        String time = mStartText.getText().toString(); // Expect MM:SS
                        mStartPos = mWaveformView.secondsToPixels(
                                Double.parseDouble(
                                        mStartText.getText().toString()));
                        updateDisplay();
                    } catch (NumberFormatException e) {
                    }
                }
                if (mEndText.hasFocus()) {
                    try {
                        mEndPos = mWaveformView.secondsToPixels(
                                Double.parseDouble(
                                        mEndText.getText().toString()));
                        updateDisplay();
                    } catch (NumberFormatException e) {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private String getStackTrace(Exception e) {
        ByteArrayOutputStream stream = null;
        try {
            stream = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(stream, true);
            e.printStackTrace(writer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return stream.toString();
    }

    private String getExtensionFromFilename(String filename) {
        return filename.substring(filename.lastIndexOf('.'),
                filename.length());
    }

    private String getFilenameFromUri(Uri uri) {
        Cursor c = null;
        int dataIndex = 0;
        try {
            c = managedQuery(uri, null, "", null, null);
            if (c.getCount() == 0) {
                return null;
            }
            c.moveToFirst();
            dataIndex = c.getColumnIndexOrThrow(
                    MediaStore.Audio.Media.DATA);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return c.getString(dataIndex);
    }


}
