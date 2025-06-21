package com.birthday.video.maker.creations;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;

import java.util.ArrayList;
import java.util.List;

class ShareCreationAdapter extends RecyclerView.Adapter<ShareCreationAdapter.MyViewHolder> {
    private List<ResolveInfo> apps = new ArrayList<>();
    private Activity context;
    private Intent intent;
    private Uri b;
    private LayoutInflater inflater;
    private PackageManager pm = null;
    private String from;
    private boolean installed;

    public ShareCreationAdapter(PackageManager pm, List<ResolveInfo> apps, Activity context, Intent intent, Uri b, String from) {
        inflater = LayoutInflater.from(context);
        this.pm = pm;
        this.apps = apps;
        this.context = context;
        this.from = from;
        this.intent = intent;
        this.b = b;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int pos) {


        holder.label.setText(apps.get(pos).loadLabel(pm));
        holder.icon.setImageDrawable(apps.get(pos).loadIcon(pm));

        holder.icon.setContentDescription("Share Image " + pos);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (from.equals("gallery")) {
                        ResolveInfo launchable = apps.get(pos);
                        ActivityInfo activity = launchable.activityInfo;
                        ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                        if (pm.getInstallerPackageName(name.getPackageName()) != null) {
                            intent.putExtra(Intent.EXTRA_STREAM, b);
                            intent.setType("image/png");
                            intent.setComponent(name);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, context.getString(R.string.app_not_installed_in_your_phone), Toast.LENGTH_SHORT).show();
                        }

                    } else if (from.equals("gif")) {
                        ResolveInfo launchable = apps.get(pos);
                        ActivityInfo activity = launchable.activityInfo;
                        ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                                activity.name);
                        if (pm.getInstallerPackageName(name.getPackageName()) != null) {
                            intent.putExtra(Intent.EXTRA_STREAM, b);
                            intent.setType("image/gif");
                            intent.setComponent(name);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, context.getString(R.string.app_not_installed_in_your_phone), Toast.LENGTH_SHORT).show();
                        }

                    } else if (from.equals("video")) {
                        ResolveInfo launchable = apps.get(pos);
                        ActivityInfo activity = launchable.activityInfo;
                        ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                                activity.name);
                        if (pm.getInstallerPackageName(name.getPackageName()) != null) {
                            intent.putExtra(Intent.EXTRA_STREAM, b);
                            intent.setType("video/3gp");
                            intent.setComponent(name);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, context.getString(R.string.app_not_installed_in_your_phone), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return apps.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        ImageView icon;


        MyViewHolder(View itemView) {
            super(itemView);

            label = (TextView) itemView.findViewById(R.id.label);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_META_DATA);
            app_installed = true;
        } catch (Exception e) {
            app_installed = false;
        }
        return app_installed;
    }
}

