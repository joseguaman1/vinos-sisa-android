package sisa.vinos.com.vinos.DAOS;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class AdaptadorDao <T, ID> extends BaseDaoImpl<T, ID> {
    /**
     * Constructor de la clase
     * @param dataClass Clase
     * @throws SQLException Execptcion
     */
    public AdaptadorDao(final Class<T> dataClass) throws SQLException {
        super(dataClass);
    }

    public AdaptadorDao(final ConnectionSource connectionSource, final Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public AdaptadorDao(final ConnectionSource connectionSource, final DatabaseTableConfig<T> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    /**
     * Crea un registro en la base de datos
     * @param data La clase a guardar
     * @return numero producto de la insercion
     * @throws SQLException
     */
    @Override
    public int create(final T data) throws SQLException {
        int result = super.create(data);
        // Send an event with EventBus or Otto
        return result;
    }

    /**
     * Actualiaa un dato en la base de datos
     * @param data La clase a actualizar
     * @return numero resultado de la actualizacion
     * @throws SQLException Execpcion
     */
    @Override
    public int update(T data) throws SQLException {
        return super.update(data);
    }

}
