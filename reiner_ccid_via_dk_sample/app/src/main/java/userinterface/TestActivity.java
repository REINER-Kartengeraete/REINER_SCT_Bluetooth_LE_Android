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
package userinterface;


import com.example.reiner_ccid_via_dk_sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


// TODO: Auto-generated Javadoc
/**
 * The Class TestActivity.
 */
public class TestActivity extends Activity{

    /** The _ secoder info button. */
    private Button _SecoderInfoButton  = null;
    
    /** The _ ccid button. */
    private Button _CCIDButton  = null;
    
    /** The _ hhd button. */
    private Button _HHDButton  = null;
    
    /** The _ egk button. */
    private Button _EGKButton  = null;

    /** The _ esc button. */
    private Button _ESCButton  = null;
    
    /* (non-Javadoc)
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        _SecoderInfoButton = (Button) findViewById(R.id.buttonSecoderInfo);
        _CCIDButton = (Button) findViewById(R.id.buttonCCIDSecoder);
        _HHDButton = (Button) findViewById(R.id.buttonHHDSecoder);
        _EGKButton = (Button) findViewById(R.id.buttonEGKviaCCID);
        _ESCButton = (Button) findViewById(R.id.buttonESCCCID);
        _HHDButton.setOnClickListener(decider);
        _CCIDButton.setOnClickListener(decider);
        _SecoderInfoButton.setOnClickListener(decider);
        _EGKButton.setOnClickListener(decider);
        _ESCButton.setOnClickListener(decider);
        
    }
    
    
    /** The decider. */
    OnClickListener decider = new OnClickListener()
    {
	@Override
	public void onClick(View v) 
	{
	   Intent intent = null;
	   if(v.equals(_HHDButton))
	   {
	       intent = new Intent(getBaseContext(), HHDTestActivity.class);
	   }
	   else if(v.equals(_CCIDButton))
	   {
	       intent = new Intent(getBaseContext(), CCIDTestActivity.class);
	   }
	   else if(v.equals(_SecoderInfoButton))
	   {
	       intent = new Intent(getBaseContext(), Secoder3TestActivity.class);
	   }
	   else if(v.equals(_EGKButton))
	   {
	       intent = new Intent(getBaseContext(), EGKviaCCIDTestActivity.class);
	   }
       else if(v.equals(_ESCButton))
       {
           intent = new Intent(getBaseContext(), CCIDESCTestActivity.class);
       }
	   startActivity(intent);
	}
    };
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
