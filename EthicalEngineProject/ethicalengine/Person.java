package ethicalengine;

import java.util.ArrayList;

/**
 * Person is a child class inherited from Character class
 * This class includes some basic informatin like profession, age category etc., 
 * getter, setter and toString method about a person.
 * Also, include two enumerator type object Professiona dn Agecatgory
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 */
public class Person extends Character
{
	private Profession DEFAULT_PROFESSION = Profession.NONE;
	private Profession DEFAULT_ADULT_PROFESSION = Profession.UNKNOWN;
	private boolean SHOULD_NOT_PREGNANT = false;
	
	private Profession profession;
	private AgeCategory ageCategory;
	private boolean isPregnant, isYou;
	
	/**
	 * A enumerator type of object Profession including doctor, ceo, criminal
	 * homeless, unemployed, unknown, none, scientist and athelete
	 */
	public static enum Profession {DOCTOR, CEO, CRIMINAL, HOMELESS, 
		UNEMPLOYED, UNKNOWN, SCIENTIST, ATHELETE, NONE};
		
	/**
	 * A enumerator type of object Age category including baby, child, audlt
	 * and senior
	 */
	public enum AgeCategory {BABY, CHILD, ADULT, SENIOR};
	
	
	/**
	 * Empty Person constructor inherited from the parent class
	 */
	public Person()
	{
		super();
		this.profession = DEFAULT_PROFESSION;
		this.isPregnant = SHOULD_NOT_PREGNANT;
	}
	
	/**
	 * Constructor with parameter age, gender and bodytype inherited from
	 * the parent class
	 * @param age age of this person instance
	 * @param gender gender of this person instance
	 * @param bodytype bodytype of this person instance
	 */
	public Person(int age, Gender gender, BodyType bodytype)
	{
		super(age, gender, bodytype); 
		int ADULTAGELOWERBOUND = 17, ADULTAGEUPPERBOUND = 68;
		if (super.getAge() < ADULTAGELOWERBOUND || super.getAge() > ADULTAGEUPPERBOUND)
			this.profession = DEFAULT_PROFESSION;
		else
			this.profession = DEFAULT_ADULT_PROFESSION;
		
		setAgeCategory(age);
		
		this.isPregnant = SHOULD_NOT_PREGNANT;
	}

	/**
	 * Constructor with parameter age, profession, gender, bodytype and ispregant
	 * @param age age of this person instance
	 * @param profession profession type of this person instance
	 * @param gender gender of this person instance
	 * @param bodytype body type of this person instance
	 * @param isPregnant whether this person is pregant or not 
	 */
	public Person(int age, Profession profession, Gender gender, 
			BodyType bodytype, boolean isPregnant) 
	{
		super(age, gender, bodytype);
		int ADULTAGELOWERBOUND = 17, ADULTAGEUPPERBOUND = 68;
		if (this.getAge() < ADULTAGELOWERBOUND || this.getAge() > ADULTAGEUPPERBOUND)
			this.profession = DEFAULT_PROFESSION;
		else
		{
			if (profession != null && profession != Profession.NONE)
				this.profession = profession;
			else
				this.profession = DEFAULT_ADULT_PROFESSION;
		}
		
		setAgeCategory(age);
		
		if (this.getGender() != Gender.FEMALE && this.ageCategory != AgeCategory.ADULT)
			this.isPregnant = SHOULD_NOT_PREGNANT;
		else
			this.isPregnant = isPregnant;
	}
	
	/**
	 * copy constructor of this class
	 * @param otherPerson existed person 
	 */
	public Person(Person otherPerson) 
	{
		super(otherPerson); 
		
		this.profession = otherPerson.profession;
		this.ageCategory = otherPerson.ageCategory;
		this.isPregnant = otherPerson.isPregnant;
		this.isYou = otherPerson.isYou;
	}
	
	
	//getter and setter methods
	
	/**
	 * getter method for age category
	 * @return the age category of this person instance
	 */
	public AgeCategory getAgeCategory()
	{		
		return ageCategory;
	}
	
	/**
	 * setter method for age category
	 * @param age set the age category for this person instance according to
	 * the age.
	 */
	public void setAgeCategory(int age)
	{
		int BABYAGELBOUND = 0, BABYAGEURBOUND= 4, CHILDAGELBOUND = 5, CHILDAGEUBOUND = 16,
				ADULTAGELBOUND = 17, ADULTAGEUBOUND = 68;
		if(age >= BABYAGELBOUND && age <= BABYAGEURBOUND)
			ageCategory = AgeCategory.BABY;		
		else if (age >= CHILDAGELBOUND && age <= CHILDAGEUBOUND)
			ageCategory = AgeCategory.CHILD;
		else if (age >= ADULTAGELBOUND  && age <= ADULTAGEUBOUND)
			ageCategory = AgeCategory.ADULT;
		else
			ageCategory = AgeCategory.SENIOR;
	}

	/**
	 * getter method for profession
	 * @return the profession type of this person instance
	 */
	public Profession getProfession() 
	{
		return profession;
	}

	/**
	 * getter method for whether this person instance is pregant or not info
	 * @return
	 */
	public boolean isPregnant() 
	{
		return isPregnant;
	}

	/**
	 * setter method, set pregnant for this person instance
	 * @param isPregnant
	 */
	public void setPregnant(boolean isPregnant)
	{
		if (this.getGender() != Gender.FEMALE)
			this.isPregnant = SHOULD_NOT_PREGNANT;
		else if(this.ageCategory != AgeCategory.ADULT)
			this.isPregnant = SHOULD_NOT_PREGNANT;
		else
			this.isPregnant = isPregnant;
	}
	
	/**
	 * getter method for whether this person is you
	 * @return true if his/she is you, otherwise return false
	 */
	public boolean isYou() 
	{
		return isYou;
	}

	/**
	 * set this person instance to be you
	 * @param isYou true, set this person as you
	 */
	public void setAsYou(boolean isYou) 
	{
		this.isYou = isYou;
	}
	
	/**
	 * rewrite the toString method for Person class
	 * @return basic information about this person instance includes
	 * you, bodytype, age category, profession, gender, pregnant
	 */
	public String toString()
	{
		StringBuilder CharString = new StringBuilder();
		ArrayList<String> characteristics = new ArrayList<String>();
		
		characteristics.add(super.getBodyType().toString().toLowerCase());
		characteristics.add(getAgeCategory().toString().toLowerCase());
		characteristics.add(super.getGender().toString().toLowerCase());
		
		if (isYou == true)
			characteristics.add(0, "you");
		if(profession != Profession.NONE)
			characteristics.add(characteristics.indexOf(ageCategory.toString().toLowerCase()) 
					+ 1, profession.toString().toLowerCase());
		if(isPregnant == true)
			characteristics.add("pregnant");	
		
		for (int i = 0; i < characteristics.size(); i++)
		{
			CharString.append(characteristics.get(i));
			if(i < characteristics.size() - 1)
				CharString.append(" ");
		}
		return CharString.toString();
	}
}
