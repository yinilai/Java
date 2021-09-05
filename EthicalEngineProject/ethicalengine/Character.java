package ethicalengine;


/**
 * This is an abstract class which inherited by the Person class and Animal class.
 * It includes some basic information about a character like age, gender and bodytype.
 * This includes two static enumerator type objects: gender and bodytype.
 * It also includes the getter and setter methods and an abstract toString method.
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 *
 */

public abstract class Character 
{
	private int DEFAULT_AGE = 0; 
	private Gender DEFAULT_GENDER = Gender.UNKNOWN;
	private BodyType DEFAULT_BODYTYPE = BodyType.UNSPECIFIED;
	
	/**
	 * A Enumerator type of object Gender including types female, male and default option
	 * unkown
	 */
	public enum Gender {FEMALE, MALE, UNKNOWN}; 
	
	/**
	 * A Enumerator type of object Bodytype including avergae, atheletic, overweight
	 * and default option unspecified.
	 */
	public enum BodyType {AVERAGE, ATHLETIC, OVERWEIGHT, UNSPECIFIED};
	
	/**
	 * instance variable of character class: age
	 */
	private int age; 
	
	/**
	 * instance variable of character class: gender
	 */
	private Gender gender;
	
	/**
	 * instance variable of character class: bodytype
	 */
	private BodyType bodytype;
	
	/**
	 * Empty Character constructor. The instance variables will 
	 * be initiated by default values.
	 */
	public Character()
	{
		this.age = DEFAULT_AGE;
		this.gender = DEFAULT_GENDER;
		this.bodytype = DEFAULT_BODYTYPE;
	}
	
	/**
	 * Character constructor with parameter age, gender, bodytype
	 * @param age gae of this person instance
	 * @param gender gender of this person instance
	 * @param bodytype bodytype of this person instance
	 */
	public Character(int age, Gender gender, BodyType bodytype)
	{
		if(age < 0)
			this.age = DEFAULT_AGE;
		else
			this.age = age; 
		this.gender = gender;
		this.bodytype = bodytype;
	}
	
	/**
	 * copy Character construtor
	 * @param c another existed character instance
	 */
	public Character(Character c)
	{
		this.age = c.age;
		this.gender = c.gender;
		this.bodytype = c.bodytype;
	}

	/**
	 * getter method for age
	 * @return age of this character instance
	 */
	public int getAge() 
	{
		return age;
	}

	/**
	 * setter method set the age for this character instance
	 * @param age input age
	 */
	public void setAge(int age) 
	{
		if(age < 0)   
			this.age = DEFAULT_AGE;
		else
			this.age = age;
	}

	/**
	 * getter method for gender
	 * @return the gender of this character instance
	 */
	public Gender getGender() 
	{
		return gender;
	}

	/**
	 * setter method, set the gender for this character instance
	 * @param gender set the gender for this character instance
	 */
	public void setGender(Gender gender) 
	{
		this.gender = gender;
	}

	/**
	 * getter method for bodytype
	 * @return bodytype of this character instance
	 */
	public BodyType getBodyType() 
	{
		return bodytype;
	}

	/**
	 * setter method
	 * @param bodytype set the body type for this character instance
	 */
	public void setBodyType(BodyType bodytype) 
	{
		this.bodytype = bodytype;
	}
	
	/**
	 * abstract toString method, which will be overwrited by the inherited class
	 */
	public abstract String toString();
}
