package org.bananaforscale.transportation.binding;

import java.io.Serializable;

/**
 * Interface to be used for Airplane implementations
 */
public interface AirplaneBinding extends Serializable {

    Integer getId();

    void setId(Integer id);

    String getManufacturer();

    void setManufacturer(String manufacturer);

    String getModel();

    void setModel(String model);

}
