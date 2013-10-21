package clm.developers.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AgregarVentaContadoActivity extends Activity implements View.OnClickListener{
    TextView tvParametroBundle;
    EditText etCantidad;
    Button btAgregar;
    Button btVer;
    Bundle parRecibe;
    CLista_Agregar listaAgregar = new CLista_Agregar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_venta_contado);

        btAgregar = (Button)findViewById(R.id.btAgregar);
        btVer = (Button)findViewById(R.id.btVer);
        btAgregar.setOnClickListener(this);
        btVer.setOnClickListener(this);

        tvParametroBundle = (TextView)findViewById(R.id.tvParametro);
        parRecibe = (Bundle) getIntent().getExtras();
        tvParametroBundle.setText(parRecibe.getString("parametroLista"));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btAgregar){
            //Toast.makeText(this, "venta", Toast.LENGTH_SHORT).show();
            etCantidad = (EditText) findViewById(R.id.etCantidad);
            String cant = etCantidad.getText().toString();
            String prod = parRecibe.getString("parametroLista");

            CProducto objProducto = new CProducto(prod, cant);
            listaAgregar.add(objProducto);

            Intent iVenta = new Intent(this, RegistrarVentaActivity.class);
            Bundle bolsaVenta = new Bundle();
            bolsaVenta.putParcelable("arrayProducto", listaAgregar);
            iVenta.putExtras(bolsaVenta);
            startActivity(iVenta);

        }else if(v.getId() == R.id.btVer){
            //Toast.makeText(this, "aqui"+listaAgregar.toString(),Toast.LENGTH_SHORT).show();

            Intent iVenta = new Intent(this, RegistrarVentaActivity.class);
            Bundle bolsaVenta = new Bundle();
            bolsaVenta.putParcelable("arrayProducto", listaAgregar);
            iVenta.putExtras(bolsaVenta);
            startActivity(iVenta);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agregar_venta_contado, menu);
        return true;


    }


}
