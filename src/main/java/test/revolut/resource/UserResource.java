package test.revolut.resource;

 

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import test.revolut.exception.BusinessException;
import test.revolut.model.User;
import test.revolut.service.UserService;


@Path("/user")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UserResource {
     
	private static Logger LOGGER = Logger.getLogger(UserResource.class);

	@Inject
	private UserService userService;
	
	/**
	 * Find user by userName
	 * @param userName
	 * @return
	 * @throws BusinessException
	 */
    @GET
    @Path("/{userName}")
    public Response getUserByName(@PathParam("userName") String userName) throws BusinessException {
    	
         
        Optional<User> user = userService.findByUserName(userName);
        
        if (!user.isPresent()) {
            throw new WebApplicationException("User Not Found", Response.Status.NOT_FOUND);
        }
        
        return Response.status(Response.Status.OK).entity(user.get().toString()).build();
    }
 
    
    /**
     * Create User
     * @param user
     * @return
     * @throws BusinessException
     */
    @POST   
    public Response createUser(User user) throws BusinessException {
    	
        if (user == null) {
            throw new WebApplicationException("Invalid Param", Response.Status.BAD_REQUEST);
        }
    	
        Optional<User> u = userService.findByUserName(user.getUserName());
    	
        if (u.isPresent()) {
            throw new WebApplicationException("User name already exist", Response.Status.CONFLICT);
        }
        
        user.getAccount().setUser(user);
        
        user = userService.create(user);

        
        if (user!=null) {
            return Response.status(Response.Status.OK).entity(user.toString()).build();
        } else {
        	LOGGER.error("m=createUser");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
 
        
    }
    
    /**
     * Update User
     * @param userId
     * @param user
     * @return
     * @throws BusinessException
     */
    @PUT
    @Path("/{userId}")
    public Response updateUser(@PathParam("userId") Long userId,User user) throws BusinessException {
    	
        if (user == null || userId<=0) {
            throw new WebApplicationException("Invalid Params", Response.Status.BAD_REQUEST);
        }
        User u = userService.findById(userId);
        if(u==null) {
        	 throw new WebApplicationException("Invalid Id", Response.Status.NOT_FOUND);
        }
         
        u.updateValues(user.getUserName(), user.getEmailAddress());
        
        u = userService.update(u);
        
       
        if (u!=null) {
            return Response.status(Response.Status.OK).entity(u.toString()).build();
        } else {
        	LOGGER.error("m=updateUser");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
    }
    
    /**
     * Delete User by Id
     * @param userId
     * @return
     * @throws BusinessException
     */
    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") Long userId) throws BusinessException {
    	
        if (userId == null ||userId<0) {
            throw new WebApplicationException("Invalid Params", Response.Status.BAD_REQUEST);
        }
    	
        User user= userService.findById(userId);
        
        if(user==null) {
        	  throw new WebApplicationException("Invalid ID", Response.Status.NOT_FOUND);
        }
        
         user = userService.delete(user);
        if (user!=null) {
            return Response.status(Response.Status.OK).entity(user.toString()).build();
        } else {
        	LOGGER.error("m=deleteUser");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
