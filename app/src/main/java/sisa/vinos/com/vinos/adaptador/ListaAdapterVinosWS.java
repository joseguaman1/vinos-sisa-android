package sisa.vinos.com.vinos.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sisa.vinos.com.vinos.R;
import sisa.vinos.com.vinos.utilidades.Utilidades;
import sisa.vinos.com.vinos.ws.peticiones.modelo.VinoJson;

public class ListaAdapterVinosWS extends ArrayAdapter <VinoJson> {
    private List<VinoJson> dataSet;
    Context mcontext;

    public ListaAdapterVinosWS(List<VinoJson> dataSet, Context mcontext) {
        super(mcontext, R.layout.item_lista, dataSet);
        this.dataSet = dataSet;
        this.mcontext = mcontext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View item = inflater.inflate(R.layout.item_lista, null);
        if(dataSet.get(position).foto != null && !Utilidades.isAllEmpty(dataSet.get(position).foto)) {
            Picasso.get().load(dataSet.get(position).foto).into((ImageView) item.findViewById(R.id.iv_avatar));
        }


        TextView nombre = (TextView) item.findViewById(R.id.tnombre);
        nombre.setText(dataSet.get(position).nombre);

        TextView marca = (TextView) item.findViewById(R.id.tmarca);
        marca.setText(dataSet.get(position).marca);

        TextView precio = (TextView) item.findViewById(R.id.tprecio);
        precio.setText("$ "+dataSet.get(position).precio);
        return item;
    }

}
