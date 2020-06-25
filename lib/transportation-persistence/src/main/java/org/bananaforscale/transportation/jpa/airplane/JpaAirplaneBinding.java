package org.bananaforscale.transportation.jpa.airplane;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.bananaforscale.transportation.binding.AirplaneBinding;

/**
 * A JPA implementation of the Airplane interface
 */
@Entity
@SequenceGenerator(name = "airplane_seq", sequenceName = "airplane_seq")
@Table(name = "airplane")
public class JpaAirplaneBinding implements AirplaneBinding {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airplane_seq")
    private Integer id;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
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

}
