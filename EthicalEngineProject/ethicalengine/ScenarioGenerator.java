package ethicalengine;
import java.util.Random;

/**
 * This ScenarioGenerator class includes some necessary information
 * for creating a scenario. It can generate a scenario bases on 
 * the specified number of passenger and pedestrian etc.
 * It includes getter, setter methods, getRandomPerson method, 
 * getRandomAnimal method and generate method.
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 */
public class ScenarioGenerator 
{
	private int PassengerCountMin, PassengerCountMax, PedestrianCountMin, PedestrianCountMax;
	private int DEFAULT_MIN = 1;
	private int DEFAULT_MAX = 5;
	private Random random = new Random();
	
	/**
	 * Empty constructor
	 */
	public ScenarioGenerator()
	{
		this.PassengerCountMin = DEFAULT_MIN;
		this.PassengerCountMax = DEFAULT_MAX;
		this.PedestrianCountMin = DEFAULT_MIN;
		this.PedestrianCountMax = DEFAULT_MAX;
	}
	
	/**
	 * Constructor with parameter seed which set the seed for generating scenario
	 * @param seed the seed for random generator
	 */
	public ScenarioGenerator(long seed)
	{
		random.setSeed(seed);
		this.PassengerCountMin = DEFAULT_MIN;
		this.PassengerCountMax = DEFAULT_MAX;
		this.PedestrianCountMin = DEFAULT_MIN;
		this.PedestrianCountMax = DEFAULT_MAX;
	}
	
	/**
	 * Constructor with parameter seed and some restriction for the number of passengers
	 * and pedestriants
	 * @param seed the seed for generating scenario
	 * @param passengerCountMinimum minimum number of passenegers 
	 * @param passengerCountMaximum maximum number of passengers
	 * @param pedestrianCountMinimum minimum number of pedestrians
	 * @param pedestrianCountMaximum maximum number of pedestrians
	 */
	public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum, 
			int pedestrianCountMinimum, int pedestrianCountMaximum)
	{
		random.setSeed(seed);
		if(passengerCountMinimum <= passengerCountMaximum)
		{
			this.PassengerCountMin = passengerCountMinimum;
			this.PassengerCountMax = passengerCountMaximum;
		}
		else
		{
			this.PassengerCountMin = DEFAULT_MIN;
			this.PassengerCountMax = DEFAULT_MAX;
		}
		if(pedestrianCountMinimum <= pedestrianCountMaximum)
		{
			this.PedestrianCountMin = pedestrianCountMinimum;
			this.PedestrianCountMax = pedestrianCountMaximum;
		}
		else
		{
			this.PedestrianCountMin = DEFAULT_MIN;
			this.PedestrianCountMax = DEFAULT_MAX;
		}
	}
	
	/**
	 * setter method for the minimum number of passenger
	 * @param min minimum number of passengers
	 */
	public void setPassengerCountMin(int min)
	{
		if(min <= this.PassengerCountMax)
			this.PassengerCountMin = min;
	}
	
	/**
	 * setter method for the maximum number of passenger
	 * @param max maximum number of pedestrians
	 */
	public void setPassengerCountMax(int max)
	{
		if(max >= this.PassengerCountMin)
			this.PassengerCountMax = max;
	}
	
	/**
	 * setter medthod for minimum number of pedestrians
	 * @param min minimum number of pedestrians set
	 */
	public void setPedestrianCountMin(int min)
	{
		if(min <= this.PedestrianCountMax)
			this.PedestrianCountMin = min;
	}
	
	/**
	 * setter method for maximum number of pedestrian
	 * @param max maximum number of pedestrians set
	 */
	public void setPedestrianCountMax(int max)
	{
		if(max >= this.PedestrianCountMin)
			this.PedestrianCountMax = max;
	}
	
	/**
	 * randomly generate a person instance
	 * @return the generated person instance
	 */
	public Person getRandomPerson()
	{
		int age = random.nextInt(125); //the oldest person in the world is 122 years old.
		Character.Gender gender = Character.Gender.values()
				[random.nextInt(Character.Gender.values().length)];
		Character.BodyType bodyType = Character.BodyType.values()
				[random.nextInt(Character.BodyType.values().length)];
		Person.Profession profession = Person.Profession.values()
				[random.nextInt(Person.Profession.values().length)];
		boolean isPregnant = random.nextBoolean();
		Person person = new Person(age, profession, gender, bodyType, isPregnant);
		return person;
	}
	
	/**
	 * randomly generate a animal instance
	 * @return the generated animal instance
	 */
	public Animal getRandomAnimal()
	{
		String[] Species = {"DOG", "CAT", "BIRD", "TURTLE", "RABBIT", 
				"ELEPHANT", "DEER", "SQUIRREL"};
		int age = random.nextInt(200); 
		Character.Gender gender = Character.Gender.values()
				[random.nextInt(Character.Gender.values().length)];
		Character.BodyType bodyType = Character.BodyType.values()
				[random.nextInt(Character.BodyType.values().length)];
		String species = Species[random.nextInt(Species.length)];
		boolean isPet = random.nextBoolean();
		
		Animal animal = new Animal(species);
		animal.setAge(age);
		animal.setBodyType(bodyType);
		animal.setGender(gender);
		animal.setPet(isPet);
		
		return animal;
	}
	
	/**
	 * randomly generate the necessary characteristics to create a scenario
	 * @return the generated scenario
	 */
	public Scenario generate()
	{
		int NumOfPassenger = random.nextInt(PassengerCountMax-PassengerCountMin+1)
				+PassengerCountMin;
		int NumOfPedestrians= random.nextInt(PedestrianCountMax-PedestrianCountMin+1)
				+PedestrianCountMin;
		Character[] passengers = new Character[NumOfPassenger];
		Character[] pedestrians = new Character[NumOfPedestrians];
		boolean isLegalCrossing = random.nextBoolean();
		
		int hasyou, position = 0;
		hasyou = random.nextInt(3);
		if (hasyou == 0) //has you in car
			//randomly generate the position of you in the passenger list
			position = random.nextInt(NumOfPassenger); 
		else if (hasyou == 1) // has you on the street
			// randomly generate the position of you in the pedestrian list
			position = random.nextInt(NumOfPedestrians); 
		//else you are absent 
		for (int i = 0; i < NumOfPassenger; i++)
		{
			boolean rdChar = random.nextBoolean();
			if (rdChar)
			{
				Person person = getRandomPerson();
				passengers[i] = person;
				if (hasyou == 0 && position == i)
					person.setAsYou(true);
			}
			else
				passengers[i] = getRandomAnimal();
		}
		
		for (int i = 0; i < NumOfPedestrians; i++)
		{
			boolean rdChar = random.nextBoolean();
			if (rdChar) 
			{
				Person person = getRandomPerson();
				pedestrians[i] = person;
				if(hasyou == 1 && position == i)
					person.setAsYou(true);
			}
			else
				pedestrians[i] = getRandomAnimal();
		}
		Scenario scenario = new Scenario(passengers, pedestrians, isLegalCrossing);
		
		if (hasyou == 0)
			scenario.setYouInCar();
		else if (hasyou == 1)
			scenario.setYouInLane();
		
		return scenario;
	}
	
}
