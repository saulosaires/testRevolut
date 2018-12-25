package test.revolut.exception;

public class RepositoryException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3476771874038529437L;

	public RepositoryException(String message, Throwable cause) {
        super(message, cause);
        
    }
}