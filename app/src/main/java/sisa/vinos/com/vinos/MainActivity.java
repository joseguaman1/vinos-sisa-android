package sisa.vinos.com.vinos;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import sisa.vinos.com.vinos.DAOS.Database_Helper;
import sisa.vinos.com.vinos.DAOS.modelo.AccesoDatos;
import sisa.vinos.com.vinos.adaptador.ViewPagerAdapter;
import sisa.vinos.com.vinos.utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private Activity actividad;
    private RequestQueue requestQueue;

    public Activity getActividad() {
        actividad = this;
        return actividad;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        setViewPager();
        mostrarInicioSesion();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void setViewPager() {
        mViewPager = findViewById(R.id.page_conten);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void mostrarInicioSesion() {
        AccesoDatos accesoDatos = new AccesoDatos();
        List<AccesoDatos> lista = new ArrayList<>();
        try {
            lista = accesoDatos.getAccesoDatosDao(this).queryForAll();
        } catch (Exception ex){
            Log.e("MainActivity", "No se encontro datos en AccesoDatos");
        }
        if(lista.isEmpty()) {
            Intent intent = new Intent(this, InicioSesionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void cerra_sesion() {

        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                getActividad());

        alertDialogBuilder.setTitle(R.string.titilo_advertencia);
        alertDialogBuilder.setMessage(R.string.salir_sesion);
        alertDialogBuilder.setPositiveButton(R.string.btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Database_Helper dh = new Database_Helper(getActividad());
                dh.vaciarBaseDatos();
                mostrarInicioSesion();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.btn_cancelar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_logout) {
            cerra_sesion();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
