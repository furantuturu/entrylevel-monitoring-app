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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.FirebaseDatabase;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class QRScanActivity extends AppCompatActivity {
    TextView studentEmail;
    EditText qrcodeET, studentNameET;
    Button btnEnterClass;
    PreviewView cameraPreviewView;
    ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    Bundle received;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        studentEmail = findViewById(R.id.TVstudentemail);
        qrcodeET = findViewById(R.id.ETcode);
        studentNameET = findViewById(R.id.ETstudentname);
        btnEnterClass = findViewById(R.id.BTNenterclass);
        cameraPreviewView = findViewById(R.id.CameraPreview);

        received = getIntent().getExtras();
        studentEmail.setText(received.getString("EMAIL"));

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
                String studentName = studentNameET.getText().toString().substring(0, 1).toUpperCase() + studentNameET.getText().toString().substring(1).toLowerCase();
                String mail = received.getString("EMAIL");
                if (!TextUtils.isEmpty(studentName)) {
                    if (qrcodeET.getText().toString().equals("018f51fb-2a6c-7af0-ba64-0c9d88739aea")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("STUDENTNAME", studentName);

                        Date dNow = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");
                        String formattedDateTime = ft.format(dNow);

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", studentName);
                        map.put("email", mail);
                        map.put("datetime", formattedDateTime);

                        Random rand = new Random();
                        int max = 10000, min = 1000;
                        String randomIDNumber = Integer.toString(rand.nextInt(max - min + 1) + min);

                        FirebaseDatabase.getInstance().getReference().child("students").child(randomIDNumber).updateChildren(map);

                        Toast.makeText(QRScanActivity.this, "You have now entered the class", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(QRScanActivity.this, WelcomeActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else {
                        Toast.makeText(QRScanActivity.this, "Invalid Class Code", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(QRScanActivity.this, "Name shouldn't be blank", Toast.LENGTH_SHORT).show();

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