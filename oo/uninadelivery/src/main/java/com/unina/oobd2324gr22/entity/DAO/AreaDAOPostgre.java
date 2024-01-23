package com.unina.oobd2324gr22.entity.DAO;

import java.sql.SQLException;
import java.util.List;
import com.unina.oobd2324gr22.entity.DTO.Area;

// TODO @zGenny @RiccardoElena nly signature has been written, no implementation

public class AreaDAOPostgre implements AreaDAO {

    /**
     * PostgreSQL implementation of the insertArea method.
     */

    @Override
    public int insertArea(final Area area) throws SQLException {
        return 0;
    }

    /**
     * PostgreSQL implementation of the getAreas method.
     */
    @Override
    public List<Area> getAreas() throws SQLException {
        return null;
    }

    /**
     * PostgreSQL implementation of the getAreaByZipCodeAndCountry method.
     */

    @Override
    public Area getAreaByZipCodeAndCountry(
                                            final String zipCode,
                                            final String country)
          throws SQLException {
        return null;
    }

    /**
     * PostgreSQL implementation of the updateArea method.
     */

    @Override
    public int updateArea(final Area area) throws SQLException {
        return 0;
    }

    /**
     * PostgreSQL implementation of the deleteArea method.
     */

    @Override
    public int deleteArea(final Area area) throws SQLException {
        return 0;
    }
}
