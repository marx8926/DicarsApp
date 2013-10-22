package clm.developers.cobranza;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NClienteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncliente);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(android.R.menu.ncliente, menu);
        return true;
    }
    
}
