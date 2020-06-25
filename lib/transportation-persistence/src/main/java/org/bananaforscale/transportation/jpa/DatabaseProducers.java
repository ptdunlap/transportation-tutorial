package org.bananaforscale.transportation.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Creates the {@link EntityManager} and makes it available for injection via
 * CDI.
 *
 * ApplicationScoped - Only one bean would be created and injected for all users
 * for all injection points
 */
@ApplicationScoped
public class DatabaseProducers {

    @PersistenceContext(unitName = "mydb-persistence")
    private EntityManager em;

    @Produces
    public EntityManager entityManager() {
        return em;
    }
}
