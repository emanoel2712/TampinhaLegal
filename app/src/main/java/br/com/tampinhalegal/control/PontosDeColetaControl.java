package br.com.tampinhalegal.control;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.tampinhalegal.R;
import br.com.tampinhalegal.model.DTO.PontoColetaDTO;
import br.com.tampinhalegal.model.adapter.CustomInfoWindowAdapter;
import br.com.tampinhalegal.model.entidades.PontoColeta;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PontosDeColetaControl implements OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener {

    private FragmentActivity fragmentActivity;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    private List<PontoColeta> listPontoColeta = new ArrayList<>();

    public PontosDeColetaControl(FragmentActivity activity) {
        this.fragmentActivity = activity;
        initComponents();

    }

    private void initComponents() {
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentActivity.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        carregarPontos();
    }


    public void clickUsarGPS() {
        requestLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        requestLocationPermission();
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);


    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(fragmentActivity, "Botão de minha localização clicado", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(fragmentActivity, "Minha localização:\n" + location, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(fragmentActivity, perms)) {
            mMap.setMyLocationEnabled(true);


            LocationManager locationManager = (LocationManager) fragmentActivity.getSystemService(Context.LOCATION_SERVICE);
            String locationProvider = LocationManager.NETWORK_PROVIDER;

            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            double userLat = lastKnownLocation.getLatitude();
            double userLong = lastKnownLocation.getLongitude();

            LatLng latLng = new LatLng(userLat, userLong);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F));

            Toast.makeText(fragmentActivity, "Permissão já concedida", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(fragmentActivity, "Por favor conceda a permissão de localização", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

    public void clickPesquisar() {

        EditText addressPesq = fragmentActivity.findViewById(R.id.editPesqEndereco);
        String address = addressPesq.getText().toString();

        List<Address> addressList = null;
        MarkerOptions userMarkerOptions = new MarkerOptions();

        if (!TextUtils.isEmpty(address)) {
            Geocoder geocoder = new Geocoder(fragmentActivity);

            try {
                addressList = geocoder.getFromLocationName(address, 6);

                if (addressList != null) {
                    for (int i = 0; i < addressList.size(); i++) {
                        Address userAddress = addressList.get(i);
                        LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                        userMarkerOptions.position(latLng);
                        userMarkerOptions.title(address);
                        userMarkerOptions.snippet("");

                        userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        mMap.addMarker(userMarkerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        mMap.animateCamera(CameraUpdateFactory.zoomTo(16F));
                        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(fragmentActivity));
                    }
                } else {
                    Toast.makeText(fragmentActivity, "Localização não existe", Toast.LENGTH_SHORT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(fragmentActivity, "Por favor digite uma localização", Toast.LENGTH_SHORT);
        }

    }


    public void carregarPontos() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = "http://192.168.15.5:8080/TampinhaLegalWS/api/tampinhalegal/pontocoleta";
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                Toast.makeText(fragmentActivity, "Iniciando requisição", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                Toast.makeText(fragmentActivity, "Tentativa " + retryNo, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String pontocoletaJSON = new String(bytes);

                MarkerOptions userMarkerOptions = new MarkerOptions();
                List<Address> addressList = null;
                Geocoder geocoder = new Geocoder(fragmentActivity);
                Gson gson = new Gson();

                Type tipo = new TypeToken<List<PontoColetaDTO>>() {
                }.getType();

                List<PontoColetaDTO> listPontosDTO = gson.fromJson(pontocoletaJSON, tipo);

                for (PontoColetaDTO p : listPontosDTO) {
                    listPontoColeta.add(p.getPontoColeta());

//                    String address = addressPesq.getText().toString();
                    String address = p.getPontoColeta().getEndereco();
                    String telefone = p.getPontoColeta().getTelefone();
                    String nome = p.getPontoColeta().getNome();

                    try {
                        addressList = geocoder.getFromLocationName(address, 6);

                        if (addressList != null) {
                            for (int ix = 0; ix < addressList.size(); ix++) {
                                Address userAddress = addressList.get(ix);
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());


                                // Shows the InfoWindow or hides it if it is already opened.
                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(nome);
                                userMarkerOptions.snippet("Tel: " + telefone + " \n " + "Endereço: " + address);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                                mMap.addMarker(userMarkerOptions);

                                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(fragmentActivity));
                            }
                        } else {
                            Toast.makeText(fragmentActivity, "Localização não existe", Toast.LENGTH_SHORT);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(fragmentActivity, "Falhou.", Toast.LENGTH_SHORT).show();
            }


        });

    }


}


