
/**
 * custom exception to be thrown when the users does not explicitly 
 * give the answer about whether to save the statistics.
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 */

public class InvalidInputException extends Exception 
{

	public InvalidInputException() 
	{
		super();
	}

	public InvalidInputException(String message) 
	{
		super(message);
	}
}
