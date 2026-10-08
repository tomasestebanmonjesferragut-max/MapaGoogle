package com.example.mapagoogle;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private WebView map = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        map = findViewById(R.id.map);
        map.getSettings().setJavaScriptEnabled(true);
        map.setWebViewClient(new WebViewClient());

        Toast.makeText(this, "Tengo que ver el Codigo de la Discodia", Toast.LENGTH_SHORT).show();

        mostrarPunto(-33.498895, -70.616617);

        findViewById(R.id.btnInicio).setOnClickListener(v -> mostrarPunto(-33.498895, -70.616617));
        findViewById(R.id.btnMoto).setOnClickListener(v -> mostrarPunto(-33.498738, -70.616173));
        findViewById(R.id.btnPoli).setOnClickListener(v -> mostrarPunto(-33.498609, -70.615595));
    }

    // Google Maps embebido: no necesita API key
    private void mostrarPunto(double lat, double lon) {
        map.loadUrl("https://maps.google.com/maps?q=" + lat + "," + lon + "&z=18&output=embed");
    }
}
