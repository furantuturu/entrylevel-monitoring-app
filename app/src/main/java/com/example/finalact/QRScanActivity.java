package com.example.finalact;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class QRScanActivity extends AppCompatActivity {
    TextView studentName;
    EditText qrcodeET;
    Button btnEnterClass;
    PreviewView cameraPreviewView;
    ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    public static final String MyPREFERENCES = "MYPREFS";
    SharedPreferences sharedPreferences;
    String spUname;
    DbManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        db = new DbManager(this);

        studentName = findViewById(R.id.TVstudentname);
        qrcodeET = findViewById(R.id.ETcode);
        btnEnterClass = findViewById(R.id.BTNenterclass);
        cameraPreviewView = findViewById(R.id.CameraPreview);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        spUname = sharedPreferences.getString(StudentLoginActivity.UNAME, null);

        studentName.setText("Welcome, " + spUname.substring(0, 1).toUpperCase() + spUname.substring(1).toLowerCase());

        // checking camera permissions
        if (ContextCompat.checkSelfPermission(QRScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            init();
        } else {
            ActivityCompat.requestPermissions(QRScanActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
        }

        listeners();
    }

    private void listeners() {
        btnEnterClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrcodeET.getText().toString().equals("018f51fb-2a6c-7af0-ba64-0c9d88739aea")) {
                    try {
                        db.updateRow(1);

                        Intent intent = new Intent(QRScanActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e("DB Error", e.toString());
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(QRScanActivity.this, "Invalid Class Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void init(){
        cameraProviderListenableFuture = ProcessCameraProvider.getInstance(QRScanActivity.this);

        cameraProviderListenableFuture.addListener(new Runnable() {
            @Override
            public void run() {

                try {
                    ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, ContextCompat.getMainExecutor(QRScanActivity.this));
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(reqCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        } else {
            Toast.makeText(QRScanActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindImageAnalysis(ProcessCameraProvider processCameraProvider) {

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1280, 720))
                                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(QRScanActivity.this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                @OptIn(markerClass = ExperimentalGetImage.class) Image mediaImage = image.getImage();

                if (mediaImage != null) {
                    InputImage image2 = InputImage.fromMediaImage(mediaImage, image.getImageInfo().getRotationDegrees());

                    BarcodeScanner scanner = BarcodeScanning.getClient();

                    Task<List<Barcode>> results = scanner.process(image2);

                    results.addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            for (Barcode barcode : barcodes) {
                                final String getValue = barcode.getRawValue();

                                qrcodeET.setText(getValue);
                            }

                            image.close();
                            mediaImage.close();
                        }
                    });
                }
            }
        });

        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(cameraPreviewView.getSurfaceProvider());
        processCameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }
}