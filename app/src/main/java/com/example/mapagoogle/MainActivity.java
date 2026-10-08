package com.example.mapagoogle;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map = null;
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);

        LatLng starPoin = new LatLng(-33.498895, -70.616617);
        LatLng PuntoMoto = new LatLng(-33.498738, -70.616173);
        LatLng PuntoPoli = new LatLng(-33.498609, -70.615595);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(starPoin, 18));
        Toast.makeText(this, "Tengo que ver el Codigo de la Discodia", Toast.LENGTH_SHORT).show();

        map.addMarker(new MarkerOptions()
                .position(starPoin)
                .title("Hola ")
                .snippet("Repartidor cerca"));

        map.addMarker(new MarkerOptions()
                .position(PuntoMoto)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_moto_foreground))
                .title("Hola ")
                .snippet("Repartidor cerca"));

        map.addMarker(new MarkerOptions()
                .position(PuntoPoli)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_poli_foreground))
                .title("Hola ")
                .snippet("Repartidor cerca"));
    }
}
