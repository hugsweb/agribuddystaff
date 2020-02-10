package com.agribuddy.staff.checkin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.agribuddy.staff.R;
import com.agribuddy.staff.utils.base.BaseActionBarActivity;
import com.agribuddy.staff.utils.base.CountDownAnimation;
import com.agribuddy.staff.utils.base.TakePhotoUtils;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckIn extends BaseActionBarActivity {

    private CountDownAnimation countDownAnimation;
    @BindView(R.id.tvCountDown) TextView tvCountDown;
    @BindView(R.id.camera) CameraView cameraView;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);

        initToolbar(getString(R.string.menu_check_in));

        countDownAnimation = new CountDownAnimation(tvCountDown, 3);
        countDownAnimation.setCountDownListener(new CountDownAnimation.CountDownListener() {
            @Override
            public void onCountDownEnd(CountDownAnimation animation) {
                cameraView.captureImage();
            }
        });

        cameraView.addCameraKitListener(new CameraKitEventListener() {

            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                byte[] capturedImage = cameraKitImage.getJpeg();
                try {
                    FileOutputStream outputStream = new FileOutputStream(TakePhotoUtils.getOutputMediaFileUri().getPath());
                    outputStream.write(capturedImage);
                    outputStream.close();
                    finish();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }


    @OnClick(R.id.ivCheckIn)
    void doCheckIn() {
        showActionDialog("Are you sure you want to Check-In?");
    }

    private void showActionDialog(String desc) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.layout_dialog_box, null);

        TextView tvDescription = dialogView.findViewById(R.id.tvDescription);
        tvDescription.setText(desc);

        dialogView.findViewById(R.id.btNo).setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialogView.findViewById(R.id.btYes).setOnClickListener(view -> {
            dialog.dismiss();
            startCountDownAnimation();
        });

        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void startCountDownAnimation() {
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        countDownAnimation.setAnimation(alphaAnimation);
        // Customizable start count
        countDownAnimation.setStartCount(3);
        countDownAnimation.start();
    }

}
