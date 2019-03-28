package sisa.vinos.com.vinos.DAOS.modelo;

import android.app.Activity;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

import sisa.vinos.com.vinos.DAOS.AdaptadorDao;
import sisa.vinos.com.vinos.DAOS.Database_Helper;

@DatabaseTable(tableName = "datos_acceso", daoClass = AdaptadorDao.class)
public class AccesoDatos {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "token")
    private String token;
    @DatabaseField(columnName = "usuario")
    private String usuario;
    @DatabaseField(columnName = "permiso")
    private String permiso;

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Dao<AccesoDatos, Integer> getAccesoDatosDao(Activity actividad) {
        Dao<AccesoDatos, Integer> datosAccesosDao = null;
        try {
            Database_Helper helper = new Database_Helper(actividad);
            datosAccesosDao = helper.getAccesoDatosDao();
        } catch (SQLException e) {
            Log.i("AccesoDatos","DatosAcceso error: "+ e);
        }

        return datosAccesosDao;
    }

}
