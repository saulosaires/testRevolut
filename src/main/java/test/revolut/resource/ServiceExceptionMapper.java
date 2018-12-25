package test.revolut.resource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import test.revolut.exception.BusinessException;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<BusinessException> {
 
	public ServiceExceptionMapper() {
	}

	public Response toResponse(BusinessException exception) {
		
		
 		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setError(exception.getMessage());

 
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
	}

	public class ErrorResponse {

	    private String error;

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

	  
	}
	
}
