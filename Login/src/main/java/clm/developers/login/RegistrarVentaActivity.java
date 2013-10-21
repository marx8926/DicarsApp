package clm.developers.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RegistrarVentaActivity extends Activity {
    CLista_Agregar listaAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_venta);

        Bundle bolsaRecibe = getIntent().getExtras();
        listaAgregar = bolsaRecibe.getParcelable("arrayProducto");

        String[] lista_productos = getProductos(listaAgregar);

        final ListView lista = (ListView)findViewById(R.id.listaSimple);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista_productos);
        Toast.makeText(this, "esto" + adaptador, Toast.LENGTH_SHORT).show();

        lista.setAdapter(adaptador);
    }

    private String[] getProductos(CLista_Agregar lista_agregar)
    {
        String[]lista_prods=new String[lista_agregar.size()];
        for (int i=0;i<lista_agregar.size();i++)
        {
            lista_prods[i]=lista_agregar.get(i).getNombre();
        }
        return lista_prods;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registrar_venta, menu);
        return true;


    }
    
}
