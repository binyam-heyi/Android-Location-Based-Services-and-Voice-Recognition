package se.kth.binyam.maps;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class HelloItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> items;
	private Context context;
	
	public HelloItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		items = new ArrayList<OverlayItem>();
		this.context = context;
	}
	
	public void addOverlayItem(OverlayItem item) {
		items.add(item);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int index) {
		return items.get(index);
	}
	
	@Override
	public int size() {
		return items.size();
	}
	
	@Override
	protected boolean  onTap(int index) {
		OverlayItem item = items.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
}
