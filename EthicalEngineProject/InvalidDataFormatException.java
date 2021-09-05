
/**
 * custom exception to be thrown when the line of provided information file
 * does not have 10 values
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 */
public class InvalidDataFormatException extends Exception 
{

	public InvalidDataFormatException() 
	{
		super();
	}

	public InvalidDataFormatException(String message) 
	{
		super(message);
	}

}
