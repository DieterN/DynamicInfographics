package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import analyzation.PolicyType;

public class Statics {
	
	public static final boolean showGUI = true;
	public static final boolean extractData = false;

	public static final boolean interactive = true; //If set to true -> interactive infographic (otherwise dynamic infographic)
	public static boolean highlighting = true; //If set to true -> main parts are highlighted when there extra part is shown
	
	public static final int frameOffsetX = 10; //thickness of the frame border, used to calculate mouse position (PositionX = MouseValue - frameOffsetX + Horscrollbar.value())
	public static final int frameOffsetY = 40; //thickness of the frame border, used to calculate mouse position (PositionY = MouseValue - frameOffsetY + Verscrollbar.value())

	public static boolean reading = false; //true if someone is reading the infographic
	public static String SID = "not_reading"; //unique SID identifying the current session
	
	public static final String default_text_name_field = "Fill in your name";
	public static String reader_name = "";	
	
	public static final List<String> infographicList = new ArrayList<String>(Arrays.asList("Plane", "Water"));
	public static final List<String> allowedPoliciesList = new ArrayList<String>(Arrays.asList("Attention", "Meditation"));

	public static final int nbTimesOutExtraParts = 3;
	public static final int nbOfTimesMeasuredInMainPart = 5; 
	
	public static String inputFolder = "infographic_plane"; //this should also be the name of the infographic
	
	public static UUID partId = null; //no part shown -> null otherwise id is id of main part that owns the shown extra part
	
	public static PolicyType policy = PolicyType.UNDEFINED;
	public static int minPolicyValue = 50; //percentage of max value of policy to be used as threshold in decision whether or not to show extra information
	
	public static final String DBName = "DynamicInfographicsTest2";
	public static final double alpha = 0.4; //gives weight to new measurements
	
	// DATA EXTRACTION
	public static final boolean extractOnlyOnePerson = true;
	public static final String extractName ="montse";
	
	public static final boolean extractOnlyOneInfographic = false;
	public static final String extractInfographic ="infographic_drought";
	
	public static final List<String> eSensesToExtract = new ArrayList<String>(Arrays.asList("attention", "meditation"));
	
	public static final String heatChartsFolder = "heatcharts/";	
	public static boolean absoluteHeatChart = true;
}
