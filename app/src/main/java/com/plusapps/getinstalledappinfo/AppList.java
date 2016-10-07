package com.plusapps.getinstalledappinfo;

import java.util.List;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AppList extends ListActivity {


    AppAdapter adapter = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);


        PackageManager pm = getPackageManager();
        Intent main = new Intent(Intent.ACTION_MAIN, null);

        main.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> launchables = pm.queryIntentActivities(main, 0);


        adapter = new AppAdapter(launchables);
        setListAdapter(adapter);
    }

    class AppAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm = null;

        AppAdapter(List<ResolveInfo> apps) {
            super(AppList.this, R.layout.row, apps);

            pm = getPackageManager();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = newView(parent);
            }

            bindView(position, convertView);

            return (convertView);
        }

        private View newView(ViewGroup parent) {
            return (getLayoutInflater().inflate(R.layout.row, parent, false));
        }

        private void bindView(int position, View row) {

            TextView label = (TextView) row.findViewById(R.id.label);


            String str = getItem(position).activityInfo.packageName;

            label.setText(str);

            ImageView icon = (ImageView) row.findViewById(R.id.icon);

            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }

    // TODO Auto-generated method stub

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ResolveInfo launchable = adapter.getItem(position);
        ActivityInfo activity = launchable.activityInfo;
        ComponentName name = new
                ComponentName(activity.applicationInfo.packageName, activity.name);
        Intent i = new Intent(Intent.ACTION_MAIN);

        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        i.setComponent(name);

        startActivity(i);


    }
}
