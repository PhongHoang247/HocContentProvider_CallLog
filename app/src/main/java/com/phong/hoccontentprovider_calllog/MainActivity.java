package com.phong.hoccontentprovider_calllog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView lvCallLog;
    ArrayAdapter<String> adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        docCallLog();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void docCallLog() {
        String projection[] = {CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Cursor cursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection, null, null, CallLog.Calls.DATE + " Asc");
        adapter.clear();
        while (cursor.moveToNext()){
            long date = cursor.getLong(0);
            Date d = new Date(date);
            String sDate = sdf.format(d);
            String number = cursor.getString(1);
            long duration = cursor.getLong(2);
            adapter.add(sDate + "\n" + number + "\n" +duration);
            cursor.close();
        }
    }

    private void addControls() {
        lvCallLog = findViewById(R.id.lvCallLog);
        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1);
        lvCallLog.setAdapter(adapter);
    }


}
