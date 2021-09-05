package ethicalengine;


/**
 * This Scenario class includes the characteristics of particular scenario.
 * It includes a list of passengers, a list of pedestrians, whether you are in
 * car or in lane or absent, legal crossing, number of passengers, number of pedestrians.
 * It also includes the getter, setter method and the toString method.
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 */
public class Scenario 
{
	private Character[] passengers, pedestrians;
	private boolean isyouincar, hasyouinlane, islegalcrossing;
	private int passengercount, pedestrianscount;
	
	/**
	 * Constructor with parameter passengers, pedestrians, islegalcrossing
	 * @param passengers passenger list
	 * @param pedestrians pedestrians list
	 * @param isLegalCrossing whether it is legal crossing
	 */
	public Scenario(Character[] passengers, Character[] pedestrians, boolean isLegalCrossing)
	{
		this.passengers = passengers;
		this.pedestrians = pedestrians;
		this.islegalcrossing = isLegalCrossing;
		this.passengercount = passengers.length;
		this.pedestrianscount = pedestrians.length;
	}
	
	/**
	 * getter method for information about whether you are in car
	 * @return true if you are one of the passenger, false otherwise
	 */
	public boolean hasYouInCar() 
	{
		return isyouincar;
	}
	
	/**
	 * setter method for set you as a passenger
	 */
	public void setYouInCar() 
	{
		this.isyouincar = true;
	}

	/**
	 * getter method for information about whether you are the pedestrian
	 * @return true if you are one of the pedestrian and false otherwise
	 */
	public boolean hasYouInLane()
	{
		return hasyouinlane;
	}
	
	/**
	 * setter method for set you as one of the pedestrian
	 */
	public void setYouInLane() 
	{
		this.hasyouinlane = true;
	}

	/**
	 * getter method for getting the passenger list
	 * @return a list of passegeners may include animal and person
	 */
	public Character[] getPassengers() 
	{
		return passengers;
	}

	/**
	 * getter method for getting the pedestrian list
	 * @return a list of pedestrians may include animal and person
	 */
	public Character[] getPedestrians() 
	{
		return pedestrians;
	}

	/**
	 * getter method for legal crossing
	 * @return true if it is legal crossing, false otherwise
	 */
	public boolean isLegalCrossing() 
	{
		return islegalcrossing;
	}

	/**
	 * setter method for legal crossing
	 * @param isLegalCrossing true if want to set this scenario in legal crossing condition
	 */
	public void setLegalCrossing(boolean isLegalCrossing) 
	{
		this.islegalcrossing = isLegalCrossing;
	}

	/**
	 * getter method for get the total number of passenger in this scenario
	 * @return the number of passengers
	 */
	public int getPassengerCount() 
	{
		return passengercount;
	}

	/**
	 * getter method for getting the total number of pedestrian in this scenario
	 * @return the number of pedestrians in this scenario
	 */
	public int getPedestrianCount() 
	{
		return pedestrianscount;
	}
	
	/**
	 * overwrite the tostring method. To show the information about this scenario.
	 */
	public String toString()
	{
		StringBuilder ScenarioString = new StringBuilder();
		ScenarioString.append("======================================\n");
		ScenarioString.append("# Scenario\n");
		ScenarioString.append("======================================\n");
		if(isLegalCrossing())
			ScenarioString.append("Legal Crossing: yes\n");
		else
			ScenarioString.append("Legal Crossing: no\n");
		ScenarioString.append("Passengers " + "(" + getPassengerCount() + ")\n");
		for (int i = 0; i < passengers.length; i++)
		{
			ScenarioString.append("- " + passengers[i].toString() + "\n");
		}
		ScenarioString.append("Pedestrians " + "(" + getPedestrianCount() + ")\n");
		for (int i = 0; i < pedestrians.length; i++)
		{
			ScenarioString.append("- " + pedestrians[i].toString());
			if(i < pedestrians.length - 1)
				ScenarioString.append("\n");
		}
		return ScenarioString.toString();		
	}
}
