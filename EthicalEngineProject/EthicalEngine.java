import ethicalengine.*;
import ethicalengine.Character;
import ethicalengine.Character.BodyType;
import ethicalengine.Character.Gender;
import ethicalengine.Person.AgeCategory;
import ethicalengine.Person.Profession;
import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The EthicalEngine class has the main entry to execute this program.
 * This class contains a public Scanner keyboard, enum type variable(Decision).
 * This class also contains some basic functions which help in gathering information.
 * from the input. a couple of methods in reading input file to get the data.
 * Finally, it contains a static Decision making method, which would also be used in
 * Audit class.
 * 
 * Final_Project: Moral Machine
 * @author Yini Lai
 * username yinlai
 * student_ID 1127650
 *
 */
public class EthicalEngine 
{
	/**
	 * This scanner object is used to read input from the console
	 */
	public Scanner keyboard = new Scanner(System.in);
	
	/**
	 * This nested Enumeration class contains the decision type that can be made by the user
	 * in this particular program. It includes pedestrians and passengers
	 */
	public static enum Decision {PEDESTRIANS, PASSENGERS};
	
	/**
	 * Empty constructor of EthicalEngine class.
	 */
	public EthicalEngine()
	{
		
	}

	public static void main(String[] args) 
	{
		String DEFAULT_USERFILE = "user.log";
		String DEFAULT_ALGORITHMFILE = "results.log";
		int i = 0, DEFAULT_RUN = 3, DEFAULT_ALGORITHM_RUN = 100;
		String arg, configFile = null, resultFile = null;
		boolean interactiveMode = false, saveAnswer = false, invalidInput = true;
		EthicalEngine moralMachine = new EthicalEngine();
		Scenario[] scenarios = null;

		// get all the command-line options 

		while (i < args.length && (args[i].startsWith("-") || args[i].startsWith("--"))) 
		{
			arg = args[i++];
			if ("-h".equals(arg.toLowerCase()) || "--help".equals(arg.toLowerCase())) 
			{
				moralMachine.printHelpInfo();
				System.exit(0);
			} 
			else if ("-c".equals(arg.toLowerCase()) || "--config".equals(arg.toLowerCase())) 
			{
				if (i < args.length)
					configFile = args[i++];
				else
					moralMachine.printHelpInfo();
			} 
			else if ("-r".equals(arg.toLowerCase()) || "--results".equals(arg.toLowerCase())) 
			{
				if (i < args.length)
					resultFile = args[i++];
			} 
			else if ("-i".equals(arg.toLowerCase()) || "--iteractive".equals(arg.toLowerCase()))
				interactiveMode = true;
		}

		/** 
		 * Check whether the user set a file to store the statistics.
		 * if no and it is in interactive mode, save to default interactive result file.
		 * if no and it is in algorithm mode, save to deafult algorithm result file.
		 */ 
		
		if (resultFile == null) 
		{
			if (interactiveMode)
				resultFile = DEFAULT_USERFILE;
			else
				resultFile = DEFAULT_ALGORITHMFILE;
		}
		
		//If data file is provided, read the file data
		if(configFile != null)
		{
			try 
			{
				File fileObject = new File(configFile);
				if(fileObject.exists())
					scenarios = moralMachine.readScenarios(configFile);
				else
					throw new FileNotFoundException("ERROR: could not find config file.");
			}
			catch(FileNotFoundException e)
			{
				System.out.println(e);
				System.exit(0);
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
				
			}			
		}

		//interactive mode
		if(interactiveMode)
		{
			Audit audit = new Audit();
			boolean continuetest = true;
			Decision decision = null;
			
			String DEAFULT_USERTYPE = "User";
			audit.setAuditType(DEAFULT_USERTYPE);
							
			//interactive mode: print the welcome infomation
			try 
			{
				BufferedReader inputStream = new BufferedReader(new FileReader("welcome.ascii")); 	
				String line;
				while((line = inputStream.readLine()) != null)
				{
					System.out.println(line);
				}
				inputStream.close();
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println(e);
			} 
			catch (IOException e) 
			{
				System.out.println(e);
			}
					
			//interactive mode: check whether the user consent to save the statistics to the file
			while(invalidInput)
			{
				try
				{
					saveAnswer = moralMachine.consentSaving();
					invalidInput = false;
				}
				catch(InvalidInputException e)
				{
					System.out.print(e);
				}
			}
					
			//interactive mode: if there are scenarios read from the provided file, test those scenarios			
			if(scenarios != null)
			{
				int index = 0, run = 1;
				while(continuetest)
				{
					int runfile = DEFAULT_RUN;
					while(runfile > 0)
					{
						if(index < scenarios.length)
						{
							audit.setRun(run);
							boolean invalidinput = true;
							Scenario scenario = scenarios[index];
							System.out.println(scenario.toString());
							while(invalidinput)
							{
								System.out.println("Who should be saved? "
										+ "(passenger(s) [1] or pedestrian(s) [2])");
								String decisionInput = moralMachine.keyboard.nextLine();
								if("passenger".equals(decisionInput.toLowerCase()) 
										|| "passengers".equals(decisionInput.toLowerCase()) 
										|| "1".equals(decisionInput))
								{
									decision = Decision.PASSENGERS;
									invalidinput = false;
								}
								else if("pedestrian".equals(decisionInput.toLowerCase()) 
										|| "pedestrians".equals(decisionInput.toLowerCase()) 
										|| "2".equals(decisionInput))
								{
									decision = Decision.PEDESTRIANS;
									invalidinput = false;
								} 
							}
							audit.AuditScenario(scenario, decision);
							index++;
						}
						else
							break;
						runfile--;
					}
					audit.printStatistic();
					
					//interactive mode: if user previouslt consent to save the statistics, 
					//print them to file
					if(saveAnswer)
					{
						try 
						{
							audit.printToFile(resultFile);
						} 
						catch (IOException e) 
						{	
							System.out.println("Could not write to file " + resultFile);
						} 
					}
					
					//if there are scenarios left from the file, ask whether to continue					
					if(index < scenarios.length)
					{
						System.out.println("Would you like to continue? (yes/no)");
						String continueAnswer = moralMachine.keyboard.nextLine();
						if(continueAnswer.toLowerCase().equals("yes"))
							continuetest = true;
						else if(continueAnswer.toLowerCase().equals("no")) 
							System.exit(0);
					}
					
					//if no scenarios left, ask to quit
					else
					{
						System.out.println("That's all. Press Enter to quit.");
						if(moralMachine.keyboard.hasNextLine())
							System.exit(0);
					}
				}
			}	
					
			//interactive mode: if the configuration file did not be provided, 
			//randomly generate scenarios
			else
			{
				while(continuetest)
				{
					int runAlgorithm = DEFAULT_RUN;
					audit.setRun(runAlgorithm);
					while(runAlgorithm >0)
					{	
						boolean invalidinput = true;
						Scenario scenario = new ScenarioGenerator().generate();
						System.out.println(scenario.toString());
						while(invalidinput)
						{
							System.out.println("Who should be saved? "
									+ "(passenger(s) [1] or pedestrian(s) [2])");
							String decisionInput = moralMachine.keyboard.nextLine();
							if("passenger".equals(decisionInput.toLowerCase()) 
									|| "passengers".equals(decisionInput.toLowerCase()) 
									|| "1".equals(decisionInput))
							{
								decision = Decision.PASSENGERS;
								invalidinput = false;
							}
							else if("pedestrian".equals(decisionInput.toLowerCase()) 
									|| "pedestrians".equals(decisionInput.toLowerCase()) 
									|| "2".equals(decisionInput))
							{
								decision = Decision.PEDESTRIANS;
								invalidinput = false;
							}
						}
						audit.AuditScenario(scenario, decision); 
								
						runAlgorithm--;
					}
					
					audit.printStatistic();
					
					//if previously consent to save answer, save them to the file
					if(saveAnswer)
					{
						try 
						{
							audit.printToFile(resultFile);
						} 
						catch (IOException e) 
						{	
							System.out.println("Could not write to file " + resultFile);
						} 
					}
					
					//interactive mode: Ask whether to continue
					System.out.println("Would you like to continue? (yes/no)");
					String continueAnswer = moralMachine.keyboard.nextLine();
					if(continueAnswer.toLowerCase().equals("yes"))
						continuetest = true;
					else if(continueAnswer.toLowerCase().equals("no")) 
						System.exit(0);
				}
			}		
		}

		// Algorithm decide mode: when the congiration file was provided
		else if (scenarios != null) 
		{
			Audit audit = new Audit(scenarios);
			// audit.setAuditType("Algorithm"); if not set, deafult will set "unspecified"
			audit.run();
			audit.printStatistic();
			try 
			{
				audit.printToFile(resultFile);
			}
			catch (IOException e) 
			{
				System.out.println(e.getMessage());
			}
		}

		// Algorithm decide mode: when congiration file did not be provided
		else 
		{
			Audit audit = new Audit();
			// audit.setAuditType("Algorithm"); if not set, deafult will set "unspecified"
			audit.run(DEFAULT_ALGORITHM_RUN);
			audit.printStatistic();
			try 
			{
				audit.printToFile(resultFile);
			} 
			catch (IOException e) 
			{
				System.out.println(e.getMessage());
			}
		}
		
	}

	// Methods in EnthicalEngine class
	
	/**
	 * This method is invoked to ask whether the user would like to save their decision 
	 * statistics to the file
	 * @return true if the user agree and false otherwise
	 * @throws InvalidInputException if user's answer is neither yes nor no
	 */
	public boolean consentSaving() throws InvalidInputException 
	{
		boolean inputline;
		System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
		String answer = keyboard.nextLine();
		if ("yes".equals(answer.toLowerCase()))
			inputline = true;
		else if ("no".equals(answer.toLowerCase()))
			inputline = false;
		else
			throw new InvalidInputException("Invalid response. ");

		return inputline;
	}

	/**
	 * This function is to read a series of scenarios from the provided file.
	 * It invokes some private help functions including: recursive function getScenario,
	 * recursive function readCharacters, readPerson function, readAnimal function,
	 * containProfession function, containGender function, containBodyType function.
	 * 
	 * @param filepath the filepath for provided file
	 * @return A list of Scenarios extracted from the provided file
	 * @throws IOException if the provided file does not exist
	 */
	public Scenario[] readScenarios(String filepath) throws IOException 
	{
		BufferedReader inputFile = new BufferedReader(new FileReader(filepath));
		Queue<String> lineQueue = new LinkedList<>();
		ArrayList<Scenario> scenariosLst = new ArrayList<Scenario>();
		int linecount[] = new int[1];

		if (inputFile.readLine() != null) 
		{
			linecount[0] = 1;
			getScenario(linecount, scenariosLst, inputFile, lineQueue);

			Scenario[] scenarios = new Scenario[scenariosLst.size()];
			scenarios = scenariosLst.toArray(scenarios);
			return scenarios;
		}

		inputFile.close();
		return null;
	}

	//A recursive function for getting each scenario from the file
	private void getScenario(int[] linecount, ArrayList<Scenario> scenariosLst,
			BufferedReader inputFile, Queue<String> lineQueue) throws IOException 
	{
		ArrayList<Character> passengersLst = new ArrayList<Character>();
		ArrayList<Character> pedestriansLst = new ArrayList<Character>();
		boolean islegal = false;
		String splitBy = ",";
		int CLASSINDEX = 0, COUNTINDEX = 0;

		String line = lineQueue.poll();
		if (line == null) 
		{
			line = inputFile.readLine();
			linecount[COUNTINDEX] = linecount[COUNTINDEX] + 1;
		}

		//extract information from couples of lines in the file to create a scenario
		if (line != null) 
		{
			String[] string = line.split(splitBy);
			if ("scenario:green".equals(string[CLASSINDEX].toLowerCase()))
				islegal = true;
			else if ("scenario:red".equals(string[CLASSINDEX].toLowerCase()))
				islegal = false;

			readCharacters(linecount, inputFile, lineQueue, passengersLst, pedestriansLst);

			Character[] passengers = new Character[passengersLst.size()];
			passengers = passengersLst.toArray(passengers);
			Character[] pedestrians = new Character[pedestriansLst.size()];
			pedestrians = pedestriansLst.toArray(pedestrians);
			Scenario scenario = new Scenario(passengers, pedestrians, islegal);
			scenariosLst.add(scenario);
			getScenario(linecount, scenariosLst, inputFile, lineQueue);
		}
	}

	//A recursive function for extracting Characters like passenger list and pedestran list 
	private void readCharacters(int[] linecount, BufferedReader inputFile, 
			Queue<String> lineQueue, ArrayList<Character> passengersLst, 
			ArrayList<Character> pedestriansLst) throws IOException 
	{
		String[] string = null;
		String splitBy = ",";
		int DEFAULT_LENGTH = 10, CLASSINDEX = 0, ROLEINDEX = 9, COUNTINDEX = 0;

		//To restore the line storing legal crossing information if it exists
		String line = lineQueue.poll();
		
		//if the restore nothing/null, read a new line from the file again
		if (line == null) 
		{
			line = inputFile.readLine();
			linecount[COUNTINDEX] = linecount[COUNTINDEX] + 1;
		}
	
		if (line != null) 
		{
			string = line.split(splitBy);
			try 
			{
				if (string.length == DEFAULT_LENGTH) 
				{
					if ("person".equals(string[CLASSINDEX].toLowerCase()) 
							&& "passenger".equals(string[ROLEINDEX].toLowerCase()))
						passengersLst.add(readPerson(linecount, string));
					else if ("animal".equals(string[CLASSINDEX].toLowerCase())
							&& "passenger".equals(string[ROLEINDEX].toLowerCase()))
						passengersLst.add(new Animal(readAnimal(linecount, string)));
					else if ("person".equals(string[CLASSINDEX].toLowerCase())
							&& "pedestrian".equals(string[ROLEINDEX].toLowerCase()))
						pedestriansLst.add(new Person(readPerson(linecount, string)));
					else if ("animal".equals(string[CLASSINDEX].toLowerCase()) 
							&& "pedestrian".equals(string[ROLEINDEX].toLowerCase()))
						pedestriansLst.add(new Animal(readAnimal(linecount, string)));
				} 
				else if ("scenario".equals((string[CLASSINDEX].split(":"))[0].toLowerCase())) 
				{
					lineQueue.offer(line);
					return;
				} 
				else
					throw new InvalidDataFormatException("WARNING: invalid data format "
							+ "in config file in line " + linecount[COUNTINDEX]);
			} 
			catch (NumberFormatException e) 
			{
				System.out.println("WARNING: invalid characteristic in config file in "
						+ "line " + linecount[COUNTINDEX]);
			} 
			catch (InvalidDataFormatException e) 
			{
				System.out.println(e.getMessage());
			}
			readCharacters(linecount, inputFile, lineQueue, passengersLst, pedestriansLst);
		}
	}

	//A function for reading and create a Person instance based on the provided characteristics
	private Person readPerson(int[] linecount, String[] string)
	{
		Profession DEFAULT_PROFESSION = Profession.NONE;
		Profession DEFAULT_ADULTPROFESSION = Profession.UNKNOWN;
		Gender DEFAULT_GENDER  = Gender.UNKNOWN;
		BodyType DEFAULT_BODYTYPE = BodyType.UNSPECIFIED;
		Profession profession = null;
		Gender gender = null;
		BodyType bodytype = null;
		boolean isPregnant = false;
		int age = 0, GENDERINDEX = 1, AGEINDEX = 2, BODYTYPEINDEX = 3, PROFESSIONINDEX = 4, 
				PREGNANTINDEX = 5, ISYOUINDEX = 6, COUNTINDEX = 0; 
		
		// Since if there is a wrong character input or if character cannot cast into particular 
		// data type we should show the warning information and set them the to default value
		// Although it seems that it is not normal to throw an exception and catch it immediately
		try 
		{
			if (containGender(string[GENDERINDEX].toUpperCase()))
				gender = Gender.valueOf(string[GENDERINDEX].toUpperCase());
			else			
				throw new InvalidCharacteristicException("WARNING: invalid characteristic in "
						+ "config file in line " + linecount[COUNTINDEX]);
		}
		catch(InvalidCharacteristicException e)
		{
			System.out.println(e.getMessage());
			gender = DEFAULT_GENDER;
		}

		try 
		{
			age = Integer.parseInt(string[AGEINDEX]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("WARNING: invalid number format in config file in "
					+ "line " + linecount[COUNTINDEX]);
		}

		try 
		{
			if (containBodyType(string[BODYTYPEINDEX].toUpperCase()))
				bodytype = BodyType.valueOf(string[BODYTYPEINDEX].toUpperCase());
			else
				throw new InvalidCharacteristicException("WARNING: invalid characteristic in "
						+ "config file in line " + linecount[COUNTINDEX]);
		}
		catch(InvalidCharacteristicException e)
		{
			System.out.println(e.getMessage());
			bodytype = DEFAULT_BODYTYPE;
		}

		if (age >= 17 && age <= 68) 
		{
			try
			{
				if (containProfession(string[PROFESSIONINDEX].toUpperCase()))
					profession = Profession.valueOf(string[PROFESSIONINDEX].toUpperCase());
				else	
					throw new InvalidCharacteristicException(
							"WARNING: invalid characteristic in config file in line " 
					+ linecount[COUNTINDEX]);
			}
			catch(InvalidCharacteristicException e)
			{
				System.out.println(e.getMessage());
				profession = DEFAULT_ADULTPROFESSION;
			}
		}
		else
			profession = DEFAULT_PROFESSION;

		try 
		{
			isPregnant = Boolean.parseBoolean(string[PREGNANTINDEX]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("WARNING: invalid number format in config file in "
					+ "line " + linecount[COUNTINDEX]);
		}

		Person person = new Person(age, profession, gender, bodytype, isPregnant);
		person.setAsYou(Boolean.parseBoolean(string[ISYOUINDEX]));
		return person;
	}

	//A function for creating animal instances based on provided characteristics
	private Animal readAnimal(int[] linecount, String[] string)
	{
		Gender DEFAULT_GENDER = Gender.UNKNOWN;
		Animal animal = new Animal();
		int COUNTINDEX = 0, GENDERINDEX = 1, AGEINDEX = 2, SPECIESINDEX = 7, PETINDEX = 8;
		
		try
		{
			animal.setSpecies(string[SPECIESINDEX]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("WARNING: invalid number format in config file in "
					+ "line " + linecount[COUNTINDEX]);
		}
		
		try 
		{
			animal.setAge(Integer.parseInt(string[AGEINDEX]));
		}
		catch(NumberFormatException e)
		{
			System.out.println("WARNING: invalid number format in config file in "
					+ "line " + linecount[COUNTINDEX]);
		}

		try 
		{
			if (containGender(string[GENDERINDEX].toUpperCase()))
				animal.setGender(Gender.valueOf(string[GENDERINDEX].toUpperCase()));
			else
				throw new InvalidCharacteristicException(
					"WARNING: invalid characteristic in config file in line " 
				+ linecount[COUNTINDEX]);
		}
		catch(InvalidCharacteristicException e)
		{
			System.out.println(e.getMessage());
			animal.setGender(DEFAULT_GENDER);
		}

		try 
		{
			animal.setPet(Boolean.parseBoolean(string[PETINDEX]));
		}
		catch(NumberFormatException e)
		{
			System.out.println("WARNING: invalid number format in config file in "
					+ "line " + linecount[COUNTINDEX]);
		}

		return animal;
	}

	//A function for checking whether the provided string is a legal type of profession
	private boolean containProfession(String string) 
	{
		for (Profession profession : Profession.values()) 
		{
			if (profession.name().equals(string))
				return true;
		}
		return false;
	}

	//A function for checking whether the provided string is a legal type of gender
	private boolean containGender(String string) 
	{
		for (Gender gender : Gender.values()) 
		{
			if (gender.name().equals(string))
				return true;
		}
		return false;
	}

	//A function for checking the provided string is a legal type of body type
	private boolean containBodyType(String string) 
	{
		for (BodyType bodytype : BodyType.values()) 
		{
			if (bodytype.name().equals(string))
				return true;
		}
		return false;
	}
	
	/**
	 * This function is to print the help information
	 * @return A series of legal input information
	 */
	public void printHelpInfo() 
	{
		System.out.println("EthicalEngine - COMP90041 - Final Project\n");
		System.out.println("Usage: java EthicalEngine [arguments]\n");
		System.out.println("Arguments:");
		System.out.println("   -c or --config      Optional: path to config file");
		System.out.println("   -h or --help        Print Help (this message) and exit");
		System.out.println("   -r or --results     Optional: path to results log file");
		System.out.println("   -i or --interactive OPtional: launches interactive mode");
	}

	//Static Decision Algorithm
	
	/**
	 * This is a algorithm decision function. \n
	 * The characteristics used in making decision include: \n
	 * 1. Person: Number of person in either group; \n
	 * 2. Profession: Percentage of criminal in either group; \n
	 * 3. Legal crossing; \n
	 * 4. Age Category: Percentage of Adult and Children; \n
	 * 5. Pet: Number of pet in either group; \n
	 * It would invoke some private help functions including PetCount,
	 * AdultAndChildPercent, CriminalPercent,PersonCount;
	 * 
	 * @param scenario provided scenario
	 * @return Decision based on the designed algorithm, either passenger or pedestriant
	 */
	public static Decision decide(Scenario scenario) 
	{
		Character[] passenger, pedestrian;
		int paCount, peCount, paPersonCount, pePersonCount, paPetCount, pePetCount;
		double paCriminalPc, peCriminalPc, PaAdultChildPc, PeAdiltChildPc;
		
		//This part is to extract some useful information
		passenger = scenario.getPassengers();
		pedestrian = scenario.getPedestrians();
		paCount = scenario.getPassengerCount();
		peCount = scenario.getPedestrianCount();
		
		//get the number of persons
		paPersonCount = PersonCount(passenger);
		pePersonCount = PersonCount(pedestrian);
		
		//get the percentage of criminals in either group
		paCriminalPc = CriminalPercent(passenger, paPersonCount);
		peCriminalPc = CriminalPercent(pedestrian, pePersonCount);
		
		//get the percentage of adult and child in either group
		PaAdultChildPc = AdultAndChildPercent(passenger, paPersonCount);
		PeAdiltChildPc = AdultAndChildPercent(pedestrian, pePersonCount);
		
		//get the number of pet in either group
		paPetCount = PetCount(passenger);
		pePetCount = PetCount(pedestrian);

		//Based on extracted information, do the decision
		if (paPersonCount > pePersonCount)
			if (paCriminalPc > peCriminalPc)
				if (scenario.isLegalCrossing())
					return Decision.PEDESTRIANS;
				else
					return Decision.PASSENGERS;
			else if (PaAdultChildPc < PeAdiltChildPc) 
				if (scenario.isLegalCrossing())
					return Decision.PEDESTRIANS;
				else
					return Decision.PASSENGERS;
			else
				return Decision.PASSENGERS;

		else if (paPersonCount < pePersonCount)
			if (paCriminalPc < peCriminalPc)
				if (!scenario.isLegalCrossing())
					return Decision.PASSENGERS;
				else
					return Decision.PEDESTRIANS;
			else if (PaAdultChildPc > PeAdiltChildPc)
				if (!scenario.isLegalCrossing())
					return Decision.PASSENGERS;
				else
					return Decision.PEDESTRIANS;
			else
				return Decision.PEDESTRIANS;

		else if (scenario.isLegalCrossing())
			return Decision.PEDESTRIANS;
		else if (paPetCount > pePetCount)
			return Decision.PASSENGERS;
		else if (paPetCount < pePetCount)
			return Decision.PEDESTRIANS;
		else if (paCount >= peCount)
			return Decision.PASSENGERS;
		else
			return Decision.PEDESTRIANS;
	}

	//count the number of pet in given character list
	private static int PetCount(Character[] characterLst) 
	{
		int petcount = 0;
		for (Character character : characterLst) {
			if (character instanceof Animal)
				if (((Animal) character).isPet() == true)
					petcount++;
		}
		return petcount;
	}

	//calculate the percentage of adult and children in given character list
	private static double AdultAndChildPercent(Character[] characterLst, int personcount) 
	{
		int adultchildcount = 0;
		// for (int i = 0; i < characterLst.length; i++)
		for (Character character : characterLst) {
			if (character instanceof Person)
				if (((Person) character).getAgeCategory() == AgeCategory.ADULT
						|| ((Person) character).getAgeCategory() == AgeCategory.CHILD)
					adultchildcount++;
		}
		if (adultchildcount != 0 && personcount != 0)
			return adultchildcount / personcount;
		else
			return 0;
	}

	//calculate the percentage of criminal in given character list
	private static double CriminalPercent(Character[] characterLst, int personcount) 
	{
		int criminalcount = 0;
		for (Character character : characterLst) {
			if (character instanceof Person)
				if (((Person) character).getProfession() == Profession.CRIMINAL)
					criminalcount++;
		}
		if (criminalcount != 0 && personcount != 0)
			return criminalcount / personcount;
		else
			return 0;
	}

	//count the number of person in given character list
	private static int PersonCount(Character[] characterLst) 
	{
		int personcount = 0;
		for (Character character : characterLst) 
		{
			if (character instanceof Person)
				personcount++;
		}
		return personcount;
	}
}
