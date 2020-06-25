package org.bananaforscale.transportation.rest.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bananaforscale.util.JsonUtil;
import org.bananaforscale.transportation.binding.AirplaneBinding;
import org.bananaforscale.transportation.binding.SimpleAirplaneBinding;
import org.bananaforscale.transportation.jpa.airplane.AirplanePersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST resource for all airplane functionality
 */
@Path("airplane")
public class AirplaneResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirplaneResource.class);

    // Pulls in the entity manager using CDI
    @Inject
    private AirplanePersistenceManager apm;

    /**
     * Retrieves all airplanes
     *
     * @return response object containing data
     */
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(apm.getAll()).build();
    }

    /**
     * Add an airplane.
     *
     * @param airplane the airplane to add
     * @return response object containing status and data
     */
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(SimpleAirplaneBinding airplane) {
        try {
            Integer id = apm.add(airplane);
            return Response.ok(JsonUtil.createSuccessMessage(String.valueOf(id))).build();
        } catch (Exception ex) {
            LOGGER.error("Could not add airplane", ex);
            return Response.ok(JsonUtil.createErrorMessage("An error occurred while adding airplane")).build();
        }

    }

    /**
     * Update the airplane by it's identifier. If the airplane doesn't exist by
     * the given identifier an error will be thrown.
     *
     * @param id id of the airplane to update.
     * @param airplane the updated airplane.
     * @return response object containing status and data
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id, SimpleAirplaneBinding airplane) {
        Boolean success = apm.update(id, airplane);
        if (success) {
            return Response.ok(JsonUtil.createSuccessMessage("The airplane was successfully updated")).build();
        } else {
            return Response.ok(JsonUtil.createErrorMessage("An error occurred while updating the airplane"))
                    .status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieve an airplane by it's identifier
     *
     * @param id the identifier of the airplane
     * @return the airplane in JSON format
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        AirplaneBinding ab = apm.getById(id);
        if (ab == null) {
            return Response.ok(JsonUtil.createErrorMessage("The airplane doesn't exist"))
                    .status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ab).build();
    }

    /**
     * Deletes an airplane by it's identifier.
     *
     * @param id the identifier of the airplane
     * @return Response whether the operation was successful
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Integer id) {
        Boolean success = apm.delete(id);
        if (success) {
            return Response.ok(JsonUtil.createSuccessMessage("The airplane was successfully deleted")).build();
        } else {
            return Response.ok(JsonUtil.createErrorMessage("An error occurred while deleting the airplane"))
                    .status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
