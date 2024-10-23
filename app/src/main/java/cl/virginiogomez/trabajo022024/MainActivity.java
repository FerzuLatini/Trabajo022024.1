package cl.virginiogomez.trabajo022024;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Declaración de variables para la interfaz de usuario
    private TextView textViewLatitud, textViewLongitud, textViewDireccion;
    private Button btAceptar;
    private LocationManager mlocManager;
    private Localizacion localizacion;
    private MapView mapView;
    private GoogleMap googleMap;
    Button btLoginMapa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de las vistas
        textViewLatitud = findViewById(R.id.textViewLatitud);
        textViewLongitud = findViewById(R.id.textViewLongitud);
        textViewDireccion = findViewById(R.id.textViewDireccion);
        btAceptar = findViewById(R.id.btAceptar);
        mapView = findViewById(R.id.mapView);

        Intent intent = new Intent(this,MainActivity1.class);
        btLoginMapa= (Button)findViewById(R.id.btLoginMapa);





        // Configurar el MapView
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Obtener el servicio de ubicación del sistema
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        localizacion = new Localizacion();
        localizacion.setMainActivity(this);

        checkPermissions();

        // Configurar el evento de clic del botón Aceptar
        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGpsEnabled()) {
                    locationStart();
                } else {
                    // Muestra un mensaje al usuario si el GPS no está habilitado
                    textViewLatitud.setText("Habilita el GPS para continuar.");
                }
            }
        });


        btLoginMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(intent);
            }
        });







    }





    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            btAceptar.setVisibility(View.INVISIBLE);
        } else {
            btAceptar.setVisibility(View.VISIBLE);
        }
    }

    private void locationStart() {
        // Verifica los permisos antes de solicitar la ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            return;
        }

        // Solicita actualizaciones de ubicación solo del GPS
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, localizacion);
        textViewLatitud.setText("Esperando GPS...");
        textViewDireccion.setText("");
    }

    public void setLocation(Location loc) {
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    textViewDireccion.setText(DirCalle.getAddressLine(0));
                }

                // Mover la cámara a la ubicación actual
                LatLng currentLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15)); // Zoom a la ubicación actual
                googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("Ubicación actual")); // Agregar marcador

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Usar ubicación fija si la ubicación actual no es válida
            LatLng fixedLocation = new LatLng(-36.604498, -72.063440); // Coordenadas de una ubicación fija
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fixedLocation, 15)); // Zoom a la ubicación fija
            googleMap.addMarker(new MarkerOptions().position(fixedLocation).title("Ubicación Fija")); // Agregar marcador en la ubicación fija
            textViewLatitud.setText(String.valueOf(fixedLocation.latitude));
            textViewLongitud.setText(String.valueOf(fixedLocation.longitude));
            textViewDireccion.setText("Ubicación fija utilizada");
        }
    }

    private boolean isGpsEnabled() {
        return mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map; // Asigna el objeto GoogleMap
        googleMap.getUiSettings().setZoomControlsEnabled(true); // Habilita controles de zoom

        // Coordenadas de la primera ubicación fija
        LatLng fixedLocation1 = new LatLng(-36.6100415, -72.0994825);
        googleMap.addMarker(new MarkerOptions().position(fixedLocation1).title("Ubicación Fija 1")); // Agregar marcador en la primera ubicación
        // Mover la cámara a la primera ubicación fija
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fixedLocation1, 15f));

        // Coordenadas de la segunda ubicación fija
        LatLng fixedLocation2 = new LatLng(-36.609453, -72.101038);
        googleMap.addMarker(new MarkerOptions().position(fixedLocation2).title("Ubicación Fija 2")); // Agregar marcador en la segunda ubicación

        // Coordenadas de la tercera ubicación fija
        LatLng fixedLocation3 = new LatLng(-36.6003253, -72.099368);
        googleMap.addMarker(new MarkerOptions().position(fixedLocation3).title("Ubicación Fija 3")); // Agregar marcador en la tercera ubicación

        // Coordenadas de la cuarta ubicación fija
        LatLng fixedLocation4 = new LatLng(-36.5884205, -72.0842877);
        googleMap.addMarker(new MarkerOptions().position(fixedLocation4).title("Ubicación Fija 4")); // Agregar marcador en la cuarta ubicación
    }

    // Métodos para el ciclo de vida del MapView
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // Clase interna para manejar la ubicación
    public class Localizacion implements LocationListener {
        MainActivity mainActivity;

        public MainActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(@NonNull Location loc) {
            textViewLatitud.setText(String.valueOf(loc.getLatitude()));
            textViewLongitud.setText(String.valueOf(loc.getLongitude()));
            this.mainActivity.setLocation(loc); // Actualiza la ubicación y la dirección
        }

        @Override
        public void onProviderDisabled(String provider) {
            textViewLatitud.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            textViewLatitud.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
