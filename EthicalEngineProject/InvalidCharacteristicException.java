
/**
 * custom exception to be thrown when the cell of provided information file
 * does not accomdate a specific value
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 */
public class InvalidCharacteristicException extends Exception
{

	public InvalidCharacteristicException() 
	{
		super();
	}

	public InvalidCharacteristicException(String message) 
	{
		super(message);
	}		
}
