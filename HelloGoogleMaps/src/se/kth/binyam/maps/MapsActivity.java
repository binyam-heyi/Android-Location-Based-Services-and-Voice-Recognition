/**
 * NB! To run this application you have to substitute my (debug) key in the
 * layout file, android:apiKey="...", with your own (debug) key.
 * How to generate an API key: 
 * http://code.google.com/intl/sv/android/add-ons/google-apis/mapkey.html
 */


package se.kth.binyam.maps;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MapsActivity extends MapActivity {

	private MapView mapView;
	private HelloItemizedOverlay itemizedOverlay;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);

        Drawable drawable = getResources().getDrawable(R.drawable.pin);
        itemizedOverlay = new HelloItemizedOverlay(drawable, this);
        List<Overlay> overlays = mapView.getOverlays();
        overlays.add(itemizedOverlay);
        
        addOverlayItem();
    }
    
    private void addOverlayItem() {
        GeoPoint point = new GeoPoint(59324082, 18071136);
        OverlayItem item = new OverlayItem(
        		point, "Hello, World!", "Welcome to Stockholm!");
        itemizedOverlay.addOverlayItem(item);
        
        // Zoom in and center
		MapController mapController = mapView.getController();
		mapController.setZoom(15); // 1-21
		mapController.setCenter(point);
    }
    
    @Override
    protected boolean isRouteDisplayed() {
    	// We do not display routes
    	return false;
    }
}