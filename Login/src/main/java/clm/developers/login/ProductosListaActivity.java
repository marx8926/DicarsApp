package clm.developers.login;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProductosListaActivity extends ListActivity {
    private SelectionAdapter mAdapter;
    List<String> items = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String arg0 = "P001"+"DAYIZZ"+"70"+"15";
        items.add(arg0);
        items.add("Quick");
        items.add("Chiel");
        items.add("Briki");

        mAdapter = new SelectionAdapter(this, R.layout.productos_lista, R.id.tvListaProd, items);
        setListAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.productos_lista, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent in = new Intent(this, AgregarVentaContadoActivity.class);
        Bundle par = new Bundle();
        par.putString("parametroLista", items.get(position));
        //par.putStringArrayList("lista", ((ArrayList<String>) items));
        in.putExtras(par);
        startActivity(in);

    }
}
