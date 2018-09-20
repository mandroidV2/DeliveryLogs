package com.app.deliverieslogs.presentation.deliverydetail;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.deliverieslogs.R;
import com.app.deliverieslogs.common.Constants;
import com.app.deliverieslogs.common.Utils;
import com.app.deliverieslogs.models.DeliveryItem;
import com.app.deliverieslogs.presentation.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/** Classs is used to create the delivery details activity
 *  @author mandroid_v2.0 */
public class DeliveryDetailsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DeliveryItem mDeliveryItem;
    private ImageView mDeliveryImg;
    private TextView mDeliveryDesc;
    private TextView mDeliveryAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_delivery_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the product object from intent
        mDeliveryItem = (DeliveryItem) getIntent().getSerializableExtra(Constants.DELIVERY_LOG_OBJ_KEY);

        // get the widgets
        mDeliveryImg = findViewById(R.id.itemImg);
        mDeliveryDesc = findViewById(R.id.itemDesc);
        mDeliveryAddress = findViewById(R.id.itemAddress);

        // set the delivery details
        if (mDeliveryItem != null) {
            // render images of stores
            Utils.renderImageUIL(getImageLoader(), options, mDeliveryItem.getImageUrl(), mDeliveryImg);
            mDeliveryDesc.setText(mDeliveryItem.getDescription());
            mDeliveryAddress.setText(mDeliveryItem.getLocation().getAddress());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add marker
        if (mDeliveryItem != null && mDeliveryItem.getLocation() != null) {
            LatLng latLng = new LatLng(mDeliveryItem.getLocation().getLat(), mDeliveryItem.getLocation().getLng());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
