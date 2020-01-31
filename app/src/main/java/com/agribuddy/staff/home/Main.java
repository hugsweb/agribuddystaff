package com.agribuddy.staff.home;

import android.content.Intent;
import android.os.Bundle;

import com.agribuddy.staff.R;
import com.agribuddy.staff.checkin.CheckIn;
import com.agribuddy.staff.login.Login;
import com.agribuddy.staff.utils.base.BaseActionBarActivity;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.drawerlayout.widget.DrawerLayout;


import butterknife.ButterKnife;
import butterknife.OnClick;


public class Main extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        initToolbar(getString(R.string.app_name));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));

        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

    }

    @OnClick(R.id.llCheckIn)
    void doCheckIn(){
        startActivity(new Intent(this, CheckIn.class));
    }

    @OnClick(R.id.llLogout)
    void doLogout(){
        startActivity(new Intent(this, Login.class));
    }

}
