package clm.developers.cobranza;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import clm.developers.login.R;

public class ClientepagoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientepago);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clientepago, menu);
        return true;
    }
    
}
