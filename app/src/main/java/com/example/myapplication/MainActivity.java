package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import com.scandit.datacapture.barcode.capture.BarcodeCaptureListener;
import com.scandit.datacapture.barcode.capture.BarcodeCapture;
import com.scandit.datacapture.barcode.capture.BarcodeCaptureSession;
import com.scandit.datacapture.barcode.capture.BarcodeCaptureSettings;
import com.scandit.datacapture.barcode.data.Symbology;
import com.scandit.datacapture.core.capture.DataCaptureContext;
import com.scandit.datacapture.core.data.FrameData;
import com.scandit.datacapture.core.source.Camera;
import com.scandit.datacapture.core.source.CameraSettings;
import com.scandit.datacapture.core.ui.DataCaptureView;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String  SCANDIT_LICENSE_KEY = "AuUlhGTnQXOLQNsa2f3KmmAH8IrQG9AmKSYpJ6kKeX2UXdAVRm6EoPNS3m3PdcGU7zWfWp575DSWfn8RBWmDiPxmC8yQD7sqtG5ilAszFQWcHhhzNkJz+6REgYjxErL8+RT5Z74V916BbCDOOyyynscBdmubHzzYd2+4MFUGNZQEKoJJOhoS1Xs1vW07DBraYic0gs5rSsxNG3s/mQqhbtUrI/J2J/d+ngcYfJg+fB4vPbhi0yrww/A5uFW9GRshC14Yf45dtybNSo9lBzo35O4/6XRGAVZ1905g8G9ClihQZsKZoDgA/ugG/9kLAjs/BWghCg0v2LXPCRbMEijy8g04r0+7MZgBoVRDFdp5crt8RU6vvgQZ1v1QpBahdgZwEX3B5qBHpWvAfH+Cylx4vWB0dq1UbBZ8ok/ngPxZ5qP3DpR7bhDwKsJ1OEyQTEl6eWyI2h9dzNiAYymhoGeSTR91ZU9IXaHp1nccP5B0Gw4jb3Evm05z6H9R1MEzc4RMI0B8CtBxCNZBW9uqAWfvq/QhNy4+bxe0316UTwxvU/+qYyso138R7j9sOCl7aBYV2CYjA4Qs9c0GZ57tkBbXrmN9b56QL196x3KkAEFeb859J+9Zfl/ZBbl84JpueBeK1lxGN+8Mfyh0Z2pSfHgLtjxOVa95CSgBR3uiAPlQStmPbHBlDw0g6oZNQbymVvpOJwQauN13l8cyTiS6zztuf64FXhzBZYsZ+ljlN79FcjH/J88izkIC+vlHddBjAJyHmjMUkoZRc7M5Qn2xlWmBww1wSLgyEW2sDnKo5ORsnTwVb3BlYSQJQatPGAJMa04sKn7Voh8TC5OfCXOk9m4MtU5DoIlWKwLQpFlUVIwXcyNkKyCZsHAT5g5pA3YMRy1Ljzz+yT47Zrm6AiKWlC+YfE97Xy8TY3/LqkMRAP1ehOurVDjRgEDWuH1qcATkL+vPu2tf5oRFQAquCv5NSXmm5LY+RZIyYjTi6hcx1nd5p7gxQu+kOl3+My59Fu1Rcni2bXe6ISJ5H3BgaLSX52UdKI5P04cqFPPlrHUD+71FPhToW4CEIU5EM8F7CM0AVBGv2UZp7XALudwxX+fixgQWQuRy9moQefgcHG6pHWp3xLGVQppclHC3NSVkF3GjF84qLWFsfoNhSXd6Uz7WX2bXYKNBCcD+T/R6+zaEkr1C7PTIWEN3rV9uiRtw7FK8P0yn2Hlc4hF31BhQ9Q62NHS3RO1BmtLJhuAfwETRehzEDwV579PtagVevLKLHSEBmUaGUG7+sYo8IXrluTEzvXkY743hgFD31bQ/NbpvRpKeaSAV7OYSA7U02/RlM9/kmhgP8TgrXV57jGV3qUI76boPUvEkYxfDa7FVDF2z6yix+6G+kyxVEAYnwNHrFb061DrO2s1i4stBbrgOmUSM4rbK+L+IAr46sciWHAkjwTIh189lM2yYnXNxZfo5XHtB2F9nICRftHtYfGB3baIFSnBzECnVw93vOOJYS8DGvYF83vwnMyq+iv8gj/PiC1DShxTDqBnAVpX8V83atXEeOOzeI+6qa1dFq92vuG5R2MTpWtmCGvcFxoKoHBmd6T4iUUzTp4pBmTgZRO03CqsvzDOBUEsfDwdT6sqJkBLhyzo7g1mUvbawOONpmqrY3E7vYVZQolmjVfuGjjZPwuxlhl5062EMO2bR5sMrmdAjPRwjsW0gpuKcDBaqI7at0F1QD7qk0p1y7S7L41ZBpVdjhHrgG9wRbxVqJL1Koyh891o+5nM8whidQIXINZ7K3aOtU/qJxaNs/KXGZqYVfYQQXeZCDj0tiUmMbvDVmQP7DsrvAjhU+fP8PYNtI17C2kOK+zxCcsahML98235TNkuIpaEms2966q8VBGANvLyyDkpLRX4=";
    private static final int IMAGE_PICK_REQUEST_CODE = 1;
    private TextView resultsView;
    private DataCaptureContext dataCaptureContext;
    private Camera camera;
    private BarcodeCapture barcodeCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button selectImageButton = findViewById(R.id.select_image_button);
        Button testScanditButton = findViewById(R.id.test_scandit_button);
        Button stopScanditButton = findViewById(R.id.stop_scandit_button);
        resultsView = findViewById(R.id.results_view);

        selectImageButton.setOnClickListener(v -> openImagePicker());
        testScanditButton.setOnClickListener(v -> startScanditScanner());
        stopScanditButton.setOnClickListener(v -> stopScanditScanner());

        setupScanditScanner();
    }

    /**
     * Настраивает Scandit сканер при запуске приложения.
     */

    private long scanStartTime;

    private void setupScanditScanner() {
        // Create and set up DataCaptureContext with license key
        dataCaptureContext = DataCaptureContext.forLicenseKey(SCANDIT_LICENSE_KEY);

        // Set up the camera
        Camera camera = Camera.getDefaultCamera();
        if (camera != null) {
            CameraSettings cameraSettings = BarcodeCapture.createRecommendedCameraSettings();
            camera.applySettings(cameraSettings);
            dataCaptureContext.setFrameSource(camera);
        } else {
            resultsView.append("Scandit: Камера недоступна.\n");
            return;
        }

        // Set up BarcodeCapture
        BarcodeCaptureSettings settings = new BarcodeCaptureSettings();
        settings.enableSymbologies(EnumSet.allOf(Symbology.class)); // Enable all barcode symbologies
        barcodeCapture = BarcodeCapture.forDataCaptureContext(dataCaptureContext, settings);

        // Add DataCaptureView to the interface
        DataCaptureView dataCaptureView = DataCaptureView.newInstance(this, dataCaptureContext);
        FrameLayout rootView = findViewById(R.id.root_layout); // FrameLayout in the layout
        rootView.addView(dataCaptureView);

        // Add listener for scanning events
        barcodeCapture.addListener(new BarcodeCaptureListener() {
            @Override
            public void onBarcodeScanned(@NonNull BarcodeCapture barcodeCapture, @NonNull BarcodeCaptureSession session, @NonNull FrameData frameData) {
                // Capture the end time for scanning
                long scanEndTime = System.currentTimeMillis();
                long timeTaken = scanEndTime - scanStartTime;

                List<com.scandit.datacapture.barcode.data.Barcode> barcodes = Collections.singletonList(session.getNewlyRecognizedBarcode());
                for (com.scandit.datacapture.barcode.data.Barcode barcode : barcodes) {
                    runOnUiThread(() -> {
                        // Display the scanned barcode and scanning time
                        resultsView.append("Scandit: Сканированный код - " + barcode.getData() + "\n");
                        resultsView.append("Scandit: Время сканирования: " + timeTaken + " мс\n");
                    });
                }
            }

            @Override
            public void onSessionUpdated(@NonNull BarcodeCapture barcodeCapture, @NonNull BarcodeCaptureSession session, @NonNull FrameData frameData) {
                // Update session if needed
            }

            @Override
            public void onObservationStarted(@NonNull BarcodeCapture barcodeCapture) {
                // Optional: Do something when observation starts
            }

            @Override
            public void onObservationStopped(@NonNull BarcodeCapture barcodeCapture) {
                // Optional: Do something when observation stops
            }
        });
    }

    // Start scanning and track the time
    private void startScanditScanner() {
        if (dataCaptureContext != null) {
            Camera camera = Camera.getDefaultCamera();
            if (camera != null) {
                camera.switchToDesiredState(com.scandit.datacapture.core.source.FrameSourceState.ON);
           }
       }
        if (barcodeCapture != null) {
            barcodeCapture.setEnabled(true);  // Enable barcode capture
            scanStartTime = System.currentTimeMillis(); // Record the start time
        }
    }



    /**
     * Запускает Scandit сканер.
     */
//    private void startScanditScanner() {
//        if (dataCaptureContext != null) {
//            Camera camera = Camera.getDefaultCamera();
//            if (camera != null) {
//                camera.switchToDesiredState(com.scandit.datacapture.core.source.FrameSourceState.ON);
//            }
//        }
//    }


    /**
     * Останавливает Scandit сканер.
     */
    private void stopScanditScanner() {
        if (dataCaptureContext != null) {
            Camera camera = Camera.getDefaultCamera();
            if (camera != null) {
                camera.switchToDesiredState(com.scandit.datacapture.core.source.FrameSourceState.OFF);
            }
        }
    }

    /**
     * Открывает стандартный выбор изображения.
     */
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE);
    }

    /**
     * Обрабатывает результат выбора изображения.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    InputImage image = InputImage.fromFilePath(this, selectedImageUri);
                    scanWithMLKit(image);
                } catch (Exception e) {
                    resultsView.append("Ошибка загрузки изображения: " + e.getMessage() + "\n");
                }
            }
        }
    }

    /**
     * Сканирует изображение с помощью ML Kit.
     */
    private void scanWithMLKit(InputImage image) {
        BarcodeScanner scanner = BarcodeScanning.getClient();

        long start = System.currentTimeMillis();

        scanner.process(image)
                .addOnSuccessListener(barcodes -> {
                    long end = System.currentTimeMillis();
                    displayScanResults(barcodes, end - start, "ML Kit");
                })
                .addOnFailureListener(e -> {
                    resultsView.append("Ошибка ML Kit: " + e.getMessage() + "\n");
                });
    }

    /**
     * Отображает результаты сканирования.
     */
    private void displayScanResults(List<Barcode> barcodes, long timeTaken, String scannerType) {
        if (barcodes.isEmpty()) {
            resultsView.append(scannerType + ": Штрихкоды не найдены.\n");
        } else {
            for (Barcode barcode : barcodes) {
                resultsView.append(scannerType + " Сканированный код: " + barcode.getRawValue() + "\n");
            }
        }
        resultsView.append(scannerType + " Время сканирования: " + timeTaken + " мс\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopScanditScanner();
    }
}
