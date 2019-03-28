package sisa.vinos.com.vinos.utilidades;
import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

import sisa.vinos.com.vinos.DAOS.modelo.AccesoDatos;
import sisa.vinos.com.vinos.R;
import sisa.vinos.com.vinos.adaptador.modelo.ModeloGanerico;
import sisa.vinos.com.vinos.ws.VolleyPeticion;
import sisa.vinos.com.vinos.ws.peticiones.ConexinWS;
import sisa.vinos.com.vinos.ws.peticiones.modelo.MarcaJson;
import sisa.vinos.com.vinos.ws.peticiones.modelo.PaisJson;

public class Utilidades extends StringUtils {
    public static AccesoDatos obtenerUsuarioLogin(Activity activity) {
        AccesoDatos datosAcceso = null;
        try {
            datosAcceso = new AccesoDatos();
            datosAcceso = new AccesoDatos().getAccesoDatosDao(activity).queryForAll().get(0);

        } catch (SQLException ex) {
            Log.e("Utilidades","No encontro nada de datos");
        } catch (IndexOutOfBoundsException ie) {
            Log.e("Utilidades","Se fue de 0");
        }
        return datosAcceso;
    }

    public static ArrayAdapter<ModeloGanerico> cargarListaMarca(RequestQueue requestQueue, Activity actividad) {
        final ArrayAdapter<ModeloGanerico> lista = new ArrayAdapter(actividad, R.layout.combo_spinner);
        AccesoDatos datos = Utilidades.obtenerUsuarioLogin(actividad);
        VolleyPeticion<MarcaJson[]> marcas = ConexinWS.listaMarcas(
                actividad,
                datos.getToken(),
                datos.getPermiso(),
                new Response.Listener<MarcaJson[]>() {
                    @Override
                    public void onResponse(MarcaJson[] response) {
                        for(MarcaJson a : response) {
                            ModeloGanerico mg = new ModeloGanerico();
                            mg.label = a.nombre;
                            mg.ID = a.external_id;
                            lista.add(mg);
                        }
                        Log.e("Utilidades", "Marcas ");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(marcas);
        return  lista;
    }

    public static ArrayAdapter<ModeloGanerico> cargarListaTipo(RequestQueue requestQueue, Activity actividad) {
        final ArrayAdapter<ModeloGanerico> lista = new ArrayAdapter(actividad, R.layout.combo_spinner);
        AccesoDatos datos = Utilidades.obtenerUsuarioLogin(actividad);
        VolleyPeticion<String[]> marcas = ConexinWS.listaTipos(
                actividad,
                new Response.Listener<String[]>() {
                    @Override
                    public void onResponse(String[] response) {
                        for(String a : response) {
                            ModeloGanerico mg = new ModeloGanerico();
                            mg.label = a;
                            mg.ID = a;
                            lista.add(mg);
                        }
                        Log.e("Utilidades", "tipos ");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(marcas);
        return  lista;
    }

    public static ArrayAdapter<ModeloGanerico> cargarListaPaises(RequestQueue requestQueue, Activity actividad) {
        final ArrayAdapter<ModeloGanerico> lista = new ArrayAdapter(actividad, R.layout.combo_spinner);

        VolleyPeticion<PaisJson[]> marcas = ConexinWS.listaPaises(
                actividad,
                new Response.Listener<PaisJson[]>() {
                    @Override
                    public void onResponse(PaisJson[] response) {
                        for(PaisJson a : response) {
                            ModeloGanerico mg = new ModeloGanerico();
                            mg.label = a.name;
                            mg.ID = a.name;
                            lista.add(mg);
                        }
                        Log.e("Utilidades", "paises ");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(marcas);
        return  lista;
    }

}
