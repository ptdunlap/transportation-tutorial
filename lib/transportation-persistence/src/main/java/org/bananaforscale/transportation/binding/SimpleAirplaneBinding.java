package org.bananaforscale.transportation.binding;

/**
 * Simple bean implementation for of the Airplane interface.
 */
public class SimpleAirplaneBinding implements AirplaneBinding {

    private Integer id;
    private String manufacturer;
    private String model;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "SimpleAirplaneBinding{" + "id=" + id + ", manufacturer=" + manufacturer + ", model=" + model + '}';
    }

}
