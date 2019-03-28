package sisa.vinos.com.vinos.ws.peticiones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;

import sisa.vinos.com.vinos.ws.VolleyPeticion;
import sisa.vinos.com.vinos.ws.peticiones.modelo.CuentaJson;
import sisa.vinos.com.vinos.ws.peticiones.modelo.MarcaJson;
import sisa.vinos.com.vinos.ws.peticiones.modelo.PaisJson;
import sisa.vinos.com.vinos.ws.peticiones.modelo.VinoJson;

public class ConexinWS {
    public static final String URL = "http://10.20.14.208:8090/api";

    public static VolleyPeticion<CuentaJson> inicioSesion(@NonNull final Context context,
                                                          @NonNull HashMap mapa,
                                                          @NonNull Response.Listener<CuentaJson> respoListener,
                                                          @NonNull Response.ErrorListener errorListener) {

        final String url = URL+"/inicio";
        VolleyPeticion request = new VolleyPeticion(context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                respoListener,
                errorListener);
        request.setResponseClass(CuentaJson.class);
        return request;
    }

    public static VolleyPeticion<VinoJson[]> listaVinos(@NonNull final Context context,
                                                             @NonNull String token,
                                                             @NonNull Response.Listener<VinoJson[]> respoListener,
                                                             @NonNull Response.ErrorListener errorListener) {

        final String url = URL+"/admin/vinos";
        VolleyPeticion request = new VolleyPeticion(context,
                Request.Method.GET,
                url,
                respoListener,
                errorListener);
        request.setResponseClass(VinoJson[].class);
        try {
            request.getHeaders().put("Authorization", token);
        } catch (Exception ex) {
            Log.e("listaVinos", "No se pudo enviar "+ex);
        }
        return request;
    }

    public static VolleyPeticion<MarcaJson[]> listaMarcas(@NonNull final Context context,
                                                          @NonNull String token,
                                                          @NonNull String permiso,
                                                          @NonNull Response.Listener<MarcaJson[]> respoListener,
                                                          @NonNull Response.ErrorListener errorListener) {

        final String url = URL+"/admin/marca/activos";
        VolleyPeticion request = new VolleyPeticion(context,
                Request.Method.GET,
                url,
                respoListener,
                errorListener);
        request.setResponseClass(MarcaJson[].class);
        try {
            request.getHeaders().put("Authorization", token);
            request.getHeaders().put("permiso", permiso);
        } catch (Exception ex) {
            Log.e("listaMarcas", "No se pudo enviar "+ex);
        }
        return request;
    }

    public static VolleyPeticion<String[]> listaTipos(@NonNull final Context context,
                                                          @NonNull Response.Listener<String[]> respoListener,
                                                          @NonNull Response.ErrorListener errorListener) {

        final String url = URL+"/admin/vinos/tipos";
        VolleyPeticion request = new VolleyPeticion(context,
                Request.Method.GET,
                url,
                respoListener,
                errorListener);
        request.setResponseClass(String[].class);
        return request;
    }

    public static VolleyPeticion<PaisJson[]> listaPaises(@NonNull final Context context,
                                                         @NonNull Response.Listener<PaisJson[]> respoListener,
                                                         @NonNull Response.ErrorListener errorListener) {

        final String url = "https://restcountries.eu/rest/v2/all";
        VolleyPeticion request = new VolleyPeticion(context,
                Request.Method.GET,
                url,
                respoListener,
                errorListener);
        request.setResponseClass(PaisJson[].class);
        return request;
    }

}
