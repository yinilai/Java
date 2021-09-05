package ethicalengine;

/**
 * Animal is a child class inherited from Character class
 * This class includes some basic informatin like animal's species and whether
 * it is a pet or not, getter, setter methods and toString method. 
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 */
public class Animal extends Character 
{
	
	private String species;
	private String DEFAULT_SPECIES = null;
	private boolean ispet;
	
	/**
	 * Empty constructor inherited from the parent class
	 */
	public Animal()
	{
		super();
		this.species = DEFAULT_SPECIES;
	}
	
	/**
	 * Constructor with species paramter
	 * @param species species of this animal instance
	 */
	public Animal(String species)
	{
		super();
		this.species = species;
	}
	
	/**
	 * copy constructor of this animal class
	 * @param otherAnimal
	 */
	public Animal(Animal otherAnimal)
	{
		super(otherAnimal); 
		this.species = otherAnimal.species;
		this.ispet = otherAnimal.ispet;
	}

	/**
	 * getter method for species
	 * @return string about the species of this animal instance
	 */
	public String getSpecies() 
	{
		return species;
	}

	/**
	 * setter method for species
	 * @param species string of species
	 */
	public void setSpecies(String species)
	{
		this.species = species;
	}

	/**
	 * getter method for this animal instance
	 * @return true if this animal instance is a pet and false otherwise.
	 */
	public boolean isPet()
	{
		return ispet;
	}

	/**
	 * setter method for set this animal instance to be pet
	 * @param ispet true if set this animal to be pet
	 */
	public void setPet(boolean ispet)
	{
		this.ispet = ispet;
	}
	
	/**
	 * rewrite the to String method inherited from the parent class
	 * show the basic information about this animal instance
	 */
	public String toString() 
	{
		if (isPet() == true)
			return getSpecies().toLowerCase() + " is pet";
		return getSpecies().toLowerCase();
	}

	
}
