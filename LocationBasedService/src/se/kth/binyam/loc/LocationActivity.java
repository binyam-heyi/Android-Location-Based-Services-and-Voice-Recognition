package se.kth.binyam.loc;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationActivity extends Activity {

	private LocationManager locationManager;
	private String bestProvider;
	private LocationListener locationListener;
	private Location currentLocation, previousLocation;
	
	private TextView infoView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        infoView = (TextView) findViewById(R.id.InfoView);
        
        initLocationService();
    }
    
    private void initLocationService() {
    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	List<String> providers = locationManager.getProviders(true); // Only enabled providers
    	for(String provider: providers) {
    		Log.i("LocationActivity", "provider " + provider);
    	}

    	// Determine which provider to use
    	Criteria criteria = new Criteria();
    	criteria.setAccuracy(Criteria.ACCURACY_FINE); 
    	criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
    	criteria.setAltitudeRequired(false);
    	criteria.setCostAllowed(false);
    	bestProvider = locationManager.getBestProvider(criteria, true);
    	infoView.setText("Best provider: " + bestProvider);
    	
    	locationListener = new LocationListenerExample();
    }
    
    protected void onResume() {
    	super.onResume();
    	locationManager.requestLocationUpdates(
    			bestProvider, 1000, 10, locationListener); // 1 sec, 10 meters
    }
    
    protected void onPause() {
    	super.onPause();
    	locationManager.removeUpdates(locationListener);
    }
    
    private class LocationListenerExample implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			Log.i("LocationActivity", "location = " + location.toString());
			displayLocationAndAddresses(location);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}
    }
    
    private void displayLocationAndAddresses(Location location) {
		previousLocation = currentLocation;
		currentLocation = location;
		StringBuffer buffer = new StringBuffer("Current location: \n");
		buffer.append("(" + location.getLatitude() + "," + location.getLongitude() + ")\n");
		buffer.append("accuracy " + location.getAccuracy() + " meters \n");
		
		if(previousLocation != null) {
			float distance = currentLocation.distanceTo(previousLocation);
			buffer.append("Distance from previous location " + distance + " meters \n");
		}
		
		// Geo-coding
		Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(
					currentLocation.getLatitude(), 
					currentLocation.getLongitude(), 5); // Max 5 results
			if(addresses != null && addresses.size() != 0) {
				for(Address address: addresses) {
					buffer.append(
							address.getLocality() + ", " + address.getCountryName() + " \n");
				}
			}
			else {
				buffer.append("Failed to get address \n");
			}
		}
		catch(Exception e) {
			buffer.append("Failed to get address \n");
		}
		
		infoView.setText(buffer);
    }
}