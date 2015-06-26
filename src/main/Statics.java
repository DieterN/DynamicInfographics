package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import analyzation.PolicyType;

/**
 * Statics class that contains all parameters to set up the program.
 * 
 * @author Dieter
 *
 */
public class Statics {
	
	public static final boolean showGUI = true; //If true, the GUI is shown
	public static final boolean extractData = false;// If true, data is extracted from the MongoDB

	public static final boolean interactive = false; //If set to true -> interactive infographic (otherwise dynamic infographic)
	public static boolean highlighting = true; //If set to true -> main parts are highlighted when there extra part is shown
	
	public static final int frameOffsetX = 10; //thickness of the frame border, used to calculate mouse position (PositionX = MouseValue - frameOffsetX + Horscrollbar.value())
	public static final int frameOffsetY = 40; //thickness of the frame border, used to calculate mouse position (PositionY = MouseValue - frameOffsetY + Verscrollbar.value())

	public static boolean reading = false; //true if someone is reading the infographic
	public static String SID = "not_reading"; //unique SID identifying the current session
	
	public static final String default_text_name_field = "Fill in your name"; //Default text for the Name textfield
	public static String reader_name = "";	//Name of the current reader
	
	public static final List<String> infographicList = new ArrayList<String>(Arrays.asList("Eyes", "Drought", "Plane", "Water")); //List of all infographics that can be shown
	public static String currentInfographicFolder = ""; //Folder containing the currently shown Infographic
	
	public static final List<String> allowedPoliciesList = new ArrayList<String>(Arrays.asList("Attention", "Meditation")); //List of policies that can be chosen

	public static final int nbTimesOutExtraParts = 3; //Nb of times a measurement has to be made out of an extra part to hide it again
	public static final int nbOfTimesMeasuredInMainPart = 5; //Nb of times a high enough measurement has to be made in a mainpart to show an extra part
		
	public static UUID partId = null; //no part shown -> null otherwise id is id of main part that owns the shown extra part
	
	public static PolicyType policy = PolicyType.UNDEFINED; //Currently used policy
	public static int minPolicyValue = 50; //percentage of max value of policy to be used as threshold in decision whether or not to show extra information
	
	public static final String DBName = "DynamicInfographicsTest2"; //Name of the database wherin the measurements should be stored
	public static final double alpha = 0.4; //gives weight to new measurements
	
	public static final boolean extractOnlyOnePerson = false; //If true, only the measurements of the person with name 'extractName' is extracted
	public static final String extractName ="Nele Rober"; //If 'extractOnlyOnePerson' is true, only measurements of the person with this name will be extracted
	
	public static final boolean extractOnlyOneInfographic = false; //If true, only the measurements of the infographic with name 'extractInfographic' is extracted
	public static final String extractInfographic ="infographic_drought"; //If 'extractOnlyOneInfographic' is true, only measurements of the infographic with this name will be extracted
	
	public static int average = 0;
	
	//List of eSenses that should be extracted from the DB
	public static final List<String> eSensesToExtract = new ArrayList<String>(Arrays.asList("attention", "meditation", "delta", "theta","lowAlpha","highAlpha","lowBeta","highBeta","lowGamma","highGamma")); 

	public static final String heatChartsFolder = "rawheatcharts/";	//Folder wherin the heatcharts should be stored
	
	//If true, absolute heatcharts are drawn, meaning that the value 1 will be yellow and 100 will be red
	//If false, relatieve heatchars are drawn, meaning that the lowest measured value will be yellow and the highest will be red.
	public static boolean absoluteHeatChart = true; 
	
}
