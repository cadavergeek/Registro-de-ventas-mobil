package pro.sodig.registrodeventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class addItem extends AppCompatDialogFragment {

    private EditText articulo;
    private EditText cantidad;
    private EditText precio;
    private addItemListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_item, null);
        articulo = view.findViewById(R.id.articulo);
        cantidad = view.findViewById(R.id.cantidad);
        precio = view.findViewById(R.id.precio);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String articuloSt = articulo.getText().toString();
                        String cantidadSt = cantidad.getText().toString();
                        String precioSt = precio.getText().toString();
                        listener.applyTexts(articuloSt, cantidadSt, precioSt);
                    }
                })
                .setNegativeButton("Cancelar", null);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (addItemListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface addItemListener{
        void applyTexts(String articuloSt, String cantidadSt, String precioSt);
    }
}
