package sisa.vinos.com.vinos.DAOS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import sisa.vinos.com.vinos.DAOS.modelo.AccesoDatos;

public class Database_Helper extends OrmLiteSqliteOpenHelper {
    private static Database_Helper sDatabaseHelper;
    private static final String DATABASE_NAME = "vinos_sisa.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<AccesoDatos, Integer> accesoDatosDao;

    public Database_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, AccesoDatos.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, AccesoDatos.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<AccesoDatos, Integer> getAccesoDatosDao() throws SQLException {
        if (accesoDatosDao == null) {
            accesoDatosDao = getDao(AccesoDatos.class);
        }
        return accesoDatosDao;
    }

    public void vaciarBaseDatos() {
        try {
            getAccesoDatosDao().deleteBuilder().delete();

        } catch (Exception ex) {
            Log.e("AdaptadorDao","No se borro "+ex.toString());
        }
    }

    @Override
    public void close() {
        accesoDatosDao = null;
        super.close();
    }

    public static Database_Helper getInstance() {
        return sDatabaseHelper;

    }

    }
