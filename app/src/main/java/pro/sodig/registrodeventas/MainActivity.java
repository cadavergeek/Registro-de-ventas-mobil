package pro.sodig.registrodeventas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements addItem.addItemListener{

    final List<String> list = new ArrayList<>();
    TextView saleTotal;
    float totalSaleQ = 0;
    private static final String TAG = "MyActivity";
    final TextAdapter adapter = new TextAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listaVentaInd = findViewById(R.id.listaVentaIndiv);
        adapter.setData(list);
        listaVentaInd.setAdapter(adapter);
        listaVentaInd.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long ind){
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Borrar artículo?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] separated = list.get(position).split("/");
                                float totalSale = calcTotal(Float.parseFloat(separated[1]), Float.parseFloat(separated[2]));
                                updateGrandTotal(totalSale * -1);
                                list.remove(position);
                                adapter.setData(list);
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();

                dialog.show();
            }
        });

        final Button cerrarVenta = findViewById(R.id.cerrarVenta);

        cerrarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskInput = new EditText(MainActivity.this);
                taskInput.setSingleLine();
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Agregar artículo")
                        .setView(taskInput)
                        .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.add(taskInput.getText().toString());
                                adapter.setData(list);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();
            }
        });

        final Button agregarVenta = findViewById(R.id.agregarVenta);
        agregarVenta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addItem additem = new addItem();
                additem.show(getSupportFragmentManager(), "Cadena ejemplo");
            }
        });
    }

    class TextAdapter extends BaseAdapter{
        List<String> list = new ArrayList<>();
        void setData(List<String> mlist){
            list.clear();
            list.addAll(mlist);
            notifyDataSetChanged();
        }

        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)
                    MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.item, parent, false);
            String[] separated = list.get(position).split("/");
            TextView tvArticulo = rowView.findViewById(R.id.articulo);
            TextView tvCantidad = rowView.findViewById(R.id.cantidad);
            TextView tvPrecio = rowView.findViewById(R.id.precio);
            TextView tvTotal = rowView.findViewById(R.id.totalArt);
            //Log.i(TAG, list.get(position));
            float totalSale = calcTotal(Float.parseFloat(separated[1]), Float.parseFloat(separated[2]));
            tvArticulo.setText(separated[0]);
            tvCantidad.setText(separated[1] + " pcs");
            tvPrecio.setText("$" + separated[2] + "c/u");
            tvTotal.setText("$" + Float.toString(totalSale));
            return rowView;
        }
    }

    @Override
    public void applyTexts(String articuloSt, String cantidadSt, String precioSt) {
        float totalSale = calcTotal(Float.parseFloat(cantidadSt), Float.parseFloat(precioSt));
//        totalSaleQ = totalSaleQ + totalSale;
        //final TextAdapter adapter = new TextAdapter();
        updateGrandTotal(totalSale);
        list.add(articuloSt + "/" + cantidadSt + "/" + precioSt);
        adapter.setData(list);
    }

    private void updateGrandTotal(float monto){
        totalSaleQ = totalSaleQ + monto;
        saleTotal = findViewById(R.id.showTotal);
        saleTotal.setText("Total Venta: $" + Float.toString(totalSaleQ));
    }

    private float calcTotal(float cantidad, float precio){
        //float cant = 0;
        if(cantidad==0){
            cantidad = 1;
        }
        float cTotal = cantidad * precio;
        return cTotal;
    }
}
