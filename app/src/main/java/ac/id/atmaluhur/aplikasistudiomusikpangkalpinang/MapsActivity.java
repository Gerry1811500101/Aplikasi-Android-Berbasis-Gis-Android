package ac.id.atmaluhur.aplikasistudiomusikpangkalpinang;

import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ac.id.atmaluhur.aplikasistudiomusikpangkalpinang.api.ApiClient;
import ac.id.atmaluhur.aplikasistudiomusikpangkalpinang.api.ApiService;
import ac.id.atmaluhur.aplikasistudiomusikpangkalpinang.databinding.ActivityMapsBinding;
import ac.id.atmaluhur.aplikasistudiomusikpangkalpinang.model.ListLocationModel;
import ac.id.atmaluhur.aplikasistudiomusikpangkalpinang.model.LocationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LocationModel> mListMArker = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //tombol zoom in zoom out
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        //diarahkan ke class getAlldatLocationlatitude

        getAllDataLocationLating();
    }

    private void getAllDataLocationLating () {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Menampilkan data Marker...");
        dialog.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListLocationModel> call = apiService.getAllLocation();
        call.enqueue(new Callback<ListLocationModel>() {
            @Override
            public void onResponse(Call<ListLocationModel> call, Response<ListLocationModel> response) {
                dialog.dismiss();
                mListMArker= response.body().getmData();
                initMarker(mListMArker);
            }

            @Override
            public void onFailure(Call<ListLocationModel> call, Throwable t) {
                    dialog.dismiss();
                Toast.makeText(MapsActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void  initMarker(List<LocationModel> listData) {
        for (int i=0; i<mListMArker.size();i++){
            LatLng location = new LatLng(Double.parseDouble(mListMArker.get(i).getLatitude()),Double.parseDouble(mListMArker.get(i).getLongtitude()));
            mMap.addMarker(new MarkerOptions().position(location).title(mListMArker.get(i).getImageLocationName()));
            LatLng latLng = new LatLng(Double.parseDouble(mListMArker.get(i).getLatitude()),Double.parseDouble(mListMArker.get(i).getLongtitude()));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude),11.0f ) );
        }
    }
}