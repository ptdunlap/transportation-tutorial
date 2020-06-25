package org.bananaforscale.transportation.jpa.airplane;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaQuery;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.bananaforscale.transportation.binding.AirplaneBinding;
import org.bananaforscale.transportation.binding.SimpleAirplaneBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An enterprise bean that allows for CRUD operations of airplanes into the data
 * source. A stateless bean is being used to allow multiple instances for
 * performance considerations.
 */
@Stateless
public class AirplanePersistenceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirplanePersistenceManager.class);
    private final PropertyUtilsBean beanUtil;

    @Inject
    private EntityManager em;

    public AirplanePersistenceManager() {
        beanUtil = new PropertyUtilsBean();
    }

    /**
     * Add an airplane into the data source
     *
     * @param airplane
     * @return the identifier within the data source
     */
    public Integer add(AirplaneBinding airplane) {
        JpaAirplaneBinding jpaBinding = new JpaAirplaneBinding();

        // make sure an ID isn't already set
        airplane.setId(null);

        try {
            beanUtil.copyProperties(jpaBinding, airplane);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            LOGGER.error("An error occurred while transforming to JPA bean", ex);
            return null;
        }
        LOGGER.info("Persisting: " + jpaBinding.toString());
        em.persist(jpaBinding);
        return jpaBinding.getId();
    }

    /**
     * Update an airplane within the data source.
     *
     * @param id the id of the existing airplane
     * @param airplane the new airplane
     * @return the result of the operation
     */
    public boolean update(Integer id, AirplaneBinding airplane) {
        if (!exists(id)) {
            LOGGER.info("Cannot update airplane with id [" + id + "] because it doesn't exist");
            return false;
        }

        JpaAirplaneBinding jpaBinding = new JpaAirplaneBinding();
        try {
            beanUtil.copyProperties(jpaBinding, airplane);
            jpaBinding.setId(id);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            LOGGER.error("An error occurred while transforming to JPA bean", ex);
            return false;
        }

        LOGGER.info("Updating airplane with id: " + jpaBinding.getId());
        em.merge(jpaBinding);
        return true;
    }

    /**
     * Delete an airplane from the data source
     *
     * @param id the id of the airplane to delete
     * @return the result of the operation
     */
    public boolean delete(Integer id) {
        try {
            JpaAirplaneBinding jpaMapping = em.find(JpaAirplaneBinding.class, id);
            if (jpaMapping == null) {
                LOGGER.info("Could not remove service with id [" + id + "] as it doesn't exist in the database");
                return false;
            }
            LOGGER.info("Deleting airplane with id: " + id);
            em.remove(jpaMapping);
        } catch (IllegalArgumentException | TransactionRequiredException ex) {
            LOGGER.error("An error occurred while removing airplane with id: " + id, ex);
            return false;
        }
        return true;
    }

    /**
     * Retrieve all airplanes that exist within the data source
     *
     * @return a list of airplanes
     */
    public List<AirplaneBinding> getAll() {
        CriteriaQuery<JpaAirplaneBinding> criteria = em.getCriteriaBuilder().createQuery(JpaAirplaneBinding.class);
        criteria.select(criteria.from(JpaAirplaneBinding.class));
        List<JpaAirplaneBinding> jpaList = em.createQuery(criteria).getResultList();
        List<AirplaneBinding> planeList = new ArrayList<>();
        for (JpaAirplaneBinding jsb : jpaList) {
            try {
                SimpleAirplaneBinding sab = new SimpleAirplaneBinding();
                beanUtil.copyProperties(sab, jsb);
                planeList.add(sab);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                LOGGER.error("An error occurred while transforming from JPA bean", ex);
            }
        }
        return planeList;
    }

    /**
     * Delete all airplanes that exist within the data source
     *
     * @return the number of airplanes deleted
     */
    public Integer deleteAll() {
        int deletedCount = em.createQuery("DELETE FROM JpaAirplaneBinding").executeUpdate();
        LOGGER.info("Deleted [" + deletedCount + "] airplanes from the database");
        return deletedCount;
    }

    /**
     * Retrieve an airplane by its identifier.
     *
     * @param id the id of the airplane
     * @return the airplane if it exists, null if not
     */
    public AirplaneBinding getById(Integer id) {
        JpaAirplaneBinding jpaBinding = em.find(JpaAirplaneBinding.class, id);
        if (jpaBinding == null) {
            LOGGER.info("Cannot retrieve airplane with id [" + id + "] because it doesn't exist");
            return null;
        }
        try {
            SimpleAirplaneBinding sab = new SimpleAirplaneBinding();
            beanUtil.copyProperties(sab, jpaBinding);
            return sab;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            LOGGER.error("An error occurred while transforming from JPA bean", ex);
            return null;
        }
    }

    /**
     * Check to see if an airplane exists within the data source
     *
     * @param id the id of the airplane
     * @return whether it exists
     */
    private boolean exists(Integer id) {
        JpaAirplaneBinding jpaBinding = em.find(JpaAirplaneBinding.class, id);
        return jpaBinding != null;
    }

}
