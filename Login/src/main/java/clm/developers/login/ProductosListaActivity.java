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
    Intent i = this.getIntent();
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        user = i.getStringExtra("user");
        pass = i.getStringExtra("pass");



        mAdapter = new SelectionAdapter(this, R.layout.productos_lista, R.id.gridProductos, items);
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
