package clm.developers.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuVentaCobranzaActivity extends Activity implements View.OnClickListener{
    TextView tvUsuario;
    Button btVenta;
    Button btCobranza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_venta_cobranza);

        tvUsuario = (TextView)findViewById(R.id.tvUsuario);
        Bundle bRecibe = getIntent().getExtras();
        tvUsuario.setText(bRecibe.getString("UsuarioKey"));

        btVenta = (Button) findViewById(R.id.btVenta);
        btCobranza = (Button) findViewById(R.id.btCobranza);
        btVenta.setOnClickListener(this);
        btCobranza.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_venta_cobranza, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btVenta){
            //Toast.makeText(this, "venta", Toast.LENGTH_SHORT).show();
            Intent inListaProductos = new Intent(this, ProductosListaActivity.class);
            startActivity(inListaProductos);
        }else if(v.getId() == R.id.btCobranza){
            Toast.makeText(this, "cobranza", Toast.LENGTH_SHORT).show();
        }
    }
}
