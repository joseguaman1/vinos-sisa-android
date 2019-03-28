package sisa.vinos.com.vinos.fragmentos;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;

import sisa.vinos.com.vinos.R;
import sisa.vinos.com.vinos.adaptador.ListaAdapterVinosWS;
import sisa.vinos.com.vinos.adaptador.modelo.ModeloGanerico;
import sisa.vinos.com.vinos.utilidades.Utilidades;
import sisa.vinos.com.vinos.ws.VolleyPeticion;
import sisa.vinos.com.vinos.ws.peticiones.ConexinWS;
import sisa.vinos.com.vinos.ws.peticiones.modelo.CuentaJson;
import sisa.vinos.com.vinos.ws.peticiones.modelo.VinoJson;

public class FragmentoHome extends Fragment {
    public static String TITLE = "LISTA DE VINOS";
    private ListView listView;
    private ListaAdapterVinosWS listaAdapterVinosWS;
    private RequestQueue requestQueue;

    private Button btn_nuevoVino;

    public static FragmentoHome newInstance() {
        return new FragmentoHome();
    }

    private void cargarDialogo() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dlg_vino);
        dialog.setTitle(getActivity().getString(R.string.tlt_vino));

        final Spinner marca = (Spinner) dialog.findViewById(R.id.txt_marca);
        marca.setAdapter(Utilidades.cargarListaMarca(requestQueue, getActivity()));

        final Spinner tipo = (Spinner) dialog.findViewById(R.id.txt_tipo);
        tipo.setAdapter(Utilidades.cargarListaTipo(requestQueue, getActivity()));

        final Spinner pais = (Spinner) dialog.findViewById(R.id.txt_pais);
        pais.setAdapter(Utilidades.cargarListaPaises(requestQueue, getActivity()));

        Button dialogButtonRegistrar = (Button) dialog.findViewById(R.id.dialogButtonEscoger);
        dialogButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("ejemlo", ((ModeloGanerico) marca.getAdapter().getItem(marca.getSelectedItemPosition())).ID);
            }
        });

        Button dialogButtonCerrar = (Button) dialog.findViewById(R.id.dialogButtonCerrar);
        // if button is clicked, close the custom dialog
        dialogButtonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frm_home, container, false);
        listView = rootView.findViewById(R.id.mi_lista);
        listView.setEmptyView(rootView.findViewById(android.R.id.empty));
        btn_nuevoVino = rootView.findViewById(R.id.btn_nuevoVino);
        requestQueue = Volley.newRequestQueue(getActivity());
        if(!Utilidades.obtenerUsuarioLogin(getActivity()).getPermiso().equalsIgnoreCase("1")) {
            btn_nuevoVino.setVisibility(View.GONE);
        } else {
            btn_nuevoVino.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cargarDialogo();
                }
            });
        }

        consultaVinos();
        return rootView;//inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void consultaVinos() {
        VolleyPeticion<VinoJson[]> vinos = ConexinWS.listaVinos(
                getActivity(),
                Utilidades.obtenerUsuarioLogin(getActivity()).getToken(),
                new Response.Listener<VinoJson[]>() {
                    @Override
                    public void onResponse(VinoJson[] response) {
                        listaAdapterVinosWS = new ListaAdapterVinosWS(Arrays.asList(response), getContext());
                        listView.setAdapter(listaAdapterVinosWS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error en listar", "Error "+error.toString());
                        Toast toast1 = Toast.makeText(getActivity(),
                                getContext().getString(R.string.msg_no_busqueda), Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast1.show();
                    }
                }

        );
        requestQueue.add(vinos);
    }



}
