package app.fastyleApplication.fastyle;

public class CustomeFieldValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4995433707591853255L;
	
	private final String fieldName;
	
	public CustomeFieldValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
}
