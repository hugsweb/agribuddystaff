package com.agribuddy.staff.checkin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.agribuddy.staff.R;
import com.agribuddy.staff.utils.base.BaseActionBarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckIn extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);

        initToolbar(getString(R.string.menu_check_in));
    }

    @OnClick(R.id.ivCheckIn)
    void doCheckIn(){
        showActionDialog("Are you sure you want to Check-In?");
    }

    private void showActionDialog(String desc) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.layout_dialog_box, null);

        TextView tvDescription = dialogView.findViewById(R.id.tvDescription);
        tvDescription.setText(desc);

        dialogView.findViewById(R.id.btNo).setOnClickListener( view -> {
            dialog.dismiss();
        });

        dialogView.findViewById(R.id.btYes).setOnClickListener( view -> {
            dialog.dismiss();
        });

        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.show();
    }

}
