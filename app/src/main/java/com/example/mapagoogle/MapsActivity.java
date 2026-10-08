package com.example.mapagoogle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mapagoogle.databinding.ActivityMaps2Binding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private FusedLocationProviderClient ubicacion;
    private Marker puntoMarcado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ubicacion = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Puntos en el mapa
        LatLng starPoin = new LatLng(-33.498895, -70.616617);
        LatLng PuntoMoto = new LatLng(-33.498738, -70.616173);
        LatLng PuntoPoli = new LatLng(-33.498609, -70.615595);
        LatLng PuntoDulce = new LatLng(-33.50797374644561, -70.79157437996089);

        mMap.addMarker(new MarkerOptions()
                .position(starPoin)
                .title("Hola ")
                .snippet("Repartidor cerca"));

        // Puntos con iconos
        mMap.addMarker(new MarkerOptions()
                .position(PuntoMoto)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_moto_foreground))
                .title("Moto")
                .snippet("Repartidor cerca"));

        mMap.addMarker(new MarkerOptions()
                .position(PuntoPoli)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_poli_foreground))
                .title("Poli")
                .snippet("Carabinero cerca"));


        mMap.addMarker(new MarkerOptions()
                .position(PuntoDulce)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_dulsuratuhogar_foreground))
                .title("Pasteleria")
                .snippet("Dulsura En Tu Hogar"));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(starPoin, 18));

        // Marca punto en la vista: solo uno, si se marca otro se quita el anterior
        mMap.setOnMapClickListener(punto -> {
            if (puntoMarcado != null) {
                puntoMarcado.remove();
            }
            puntoMarcado = mMap.addMarker(new MarkerOptions()
                    .position(punto)
                    .title("Punto marcado")
                    .snippet("Toca este cartel para quitarlo"));
            Toast.makeText(this, "Punto marcado", Toast.LENGTH_SHORT).show();
        });

        // Quitar punto: tocar el marcador y luego tocar su cartel
        mMap.setOnInfoWindowClickListener(marker -> {
            marker.remove();
            Toast.makeText(this, "Punto quitado", Toast.LENGTH_SHORT).show();
        });

        // Geolocalizacion
        activarUbicacion();
    }

    @SuppressLint("MissingPermission")
    private void activarUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);
        ubicacion.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng yo = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(yo)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title("Estoy aqui"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(yo, 17));
            } else {
                Toast.makeText(this, "Activa el GPS para ver tu ubicacion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            activarUbicacion();
        } else {
            Toast.makeText(this, "Sin permiso de ubicacion", Toast.LENGTH_SHORT).show();
        }
    }
}
