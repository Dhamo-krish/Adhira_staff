package com.example.hp.adhira_staff.Camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.adhira_staff.R;
import com.example.hp.adhira_staff.Updation;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannerQr extends AppCompatActivity {

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    final int RequestCameraPermissionID = 1001;
    String decresult;
    Boolean flag=false;
    Handler handler=new Handler();
    Button update;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);
        cameraView = (SurfaceView)findViewById(R.id.surface_view);
        textView = (TextView)findViewById(R.id.text_view);
        update=(Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(decresult!=null) {
                    System.out.println("DATA"+decresult);
                    String code[] =decresult.split("_");
                    System.out.println(""+code.length);
                    if(code.length>=4)
                    {
                        Intent intent = new Intent(ScannerQr.this, Updation.class);
                        intent.putExtra("code", code);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        textView.setText("Invalid Format , Scan The Valid Code");
                        update.setVisibility(View.INVISIBLE);

                    }

                }
            }
        });
        barcodeDetector=new BarcodeDetector.Builder(ScannerQr.this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource=new CameraSource.Builder(ScannerQr.this,barcodeDetector)
                .setRequestedPreviewSize(640,480)
                .setAutoFocusEnabled(true)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ScannerQr.this,
                            new String[]{Manifest.permission.CAMERA},
                            RequestCameraPermissionID);
                    return;
                }
                try
                {
                    cameraSource.start(cameraView.getHolder());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
            {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder)
            {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {
                //cameraSource.stop();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> items = detections.getDetectedItems();

                if(items.size()!=0)
                {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            decresult="";
                            textView.setVisibility(View.VISIBLE);
                            decresult=items.valueAt(0).displayValue.toString();
                            textView.setText("Detection Successful");
                            update.setVisibility(View.VISIBLE);
                        }
                    });


                }


            }
        });





    }

}

