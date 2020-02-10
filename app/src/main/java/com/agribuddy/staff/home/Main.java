package com.agribuddy.staff.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.agribuddy.staff.R;
import com.agribuddy.staff.checkin.CheckIn;
import com.agribuddy.staff.login.Login;
import com.agribuddy.staff.utils.base.BaseActionBarActivity;
import com.agribuddy.staff.utils.base.SmartToast;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Main extends BaseActionBarActivity {

    public RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        initToolbar(getString(R.string.app_name));

        rxPermissions = new RxPermissions(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));

        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

    }

    @OnClick(R.id.llCheckIn)
    void doCheckIn() {
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Intent intent = new Intent(Main.this, CheckIn.class);
                        startActivity(intent);
                    } else {
                        SmartToast.showToast(Main.this, getResources().getString(R.string.message_access_camera_or_storage_or_gps_denied));
                    }
                });
    }

    @OnClick(R.id.llLogout)
    void doLogout() {
        startActivity(new Intent(this, Login.class));
    }

}
