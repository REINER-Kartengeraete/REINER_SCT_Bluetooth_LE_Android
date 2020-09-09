/*
* 
*   
* 
*   Created by Steve Spormann on 5.5.2015.
*   Copyright (c) 2015 REINER SCT. All rights reserved.
* 
*   Version:    0.5.3
*   Date:       1.03.2017
*   Autor:      S. Spormann
*   eMail:      mobile-sdk@reiner-sct.com
*/
package utilitis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import bluetooth.Bluetooth_ReaderInfo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class BluetoothReaderInfoAdapter.
 */
public class BluetoothReaderInfoAdapter extends ArrayAdapter<Bluetooth_ReaderInfo>{
  
	    /** The data. */
    	private List<Bluetooth_ReaderInfo> data = new ArrayList<Bluetooth_ReaderInfo>();
	    
	    /**
    	 * Instantiates a new bluetooth reader info adapter.
    	 *
    	 * @param context the context
    	 * @param data the data
    	 */
    	public BluetoothReaderInfoAdapter(Context context,  Collection<Bluetooth_ReaderInfo> data) {
	        super(context, android.R.layout.simple_list_item_2, android.R.id.text1, new ArrayList<Bluetooth_ReaderInfo>(data));
	        this.data.addAll(data);
	    }

	    /* (non-Javadoc)
    	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
    	 */
    	@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
		    View view = super.getView(position, convertView, parent);
		    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

		    text1.setText(data.get(position).getReaderName());
		    text2.setText(data.get(position).getReaderID());
		    return view;
	    }
	    
	    /* (non-Javadoc)
    	 * @see android.widget.ArrayAdapter#clear()
    	 */
    	public void clear()
	    {
		data.clear();
	    }
	    
	  
	
}
