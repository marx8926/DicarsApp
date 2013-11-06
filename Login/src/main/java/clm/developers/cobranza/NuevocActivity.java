package clm.developers.cobranza;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import clm.developers.login.R;

public class NuevocActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nuevoc, menu);
        return true;
    }
    
}
