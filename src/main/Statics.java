package main;

import java.util.UUID;

import analyzation.PolicyType;

public class Statics {
	
	public static final boolean showGUI = true;
	public static final boolean extractData = false;
	
	public static boolean reading = false; //true if someone is reading the infographic
	public static String SID = "not_reading"; //unique SID identifying the current session
	public static final int frameOffsetX = 10; //thickness of the frame border, used to calculate mouse position (PositionX = MouseValue - frameOffsetX + Horscrollbar.value())
	public static final int frameOffsetY = 40; //thickness of the frame border, used to calculate mouse position (PositionY = MouseValue - frameOffsetY + Verscrollbar.value())

	public static final String default_text_name_field = "Fill in your name";
	public static String reader_name = "";	

//	public static final String inputFolder = "infographic_drought"; //this should also be the name of the infographic
//	public static final int nbTimesOutExtraParts = 5;
//	public static final int nbOfTimesMeasuredInMainPart = 5; 
	
	public static final String inputFolder = "infographic_eyes"; //this should also be the name of the infographic
	public static final int nbTimesOutExtraParts = 5;
	public static final int nbOfTimesMeasuredInMainPart = 5; 
	
//	public static final String inputFolder = "infographic_space"; //this should also be the name of the infographic
//	public static final int nbTimesOutExtraParts = 5;
//	public static final int nbOfTimesMeasuredInMainPart = 15; 
	
	public static UUID partId = null; //no part shown -> null otherwise id is id of main part that owns the shown extra part
	
	public static final double alpha = 0.4; //gives weight to new measurements
	
	public static final int minAttentionValue = 50; //how much must the concentration be to show an extra part
	public static final int minMeditationValue = 50; //how much must the concentration be to show an extra part
	public static final int minCombinedValue = 100; //how much must the concentration be to show an extra part
	
//	public static final PolicyType policy = PolicyType.ATTENTION;
//	public static final PolicyType policy = PolicyType.MEDITATION;
	public static final PolicyType policy = PolicyType.COMBINED;
	
	public static final String DBName = "DynamicInfographics";
	
	// DATA EXTRACTION
	public static final boolean extractOnlyOnePerson = true;
	public static final String extractName ="montse";
	
	public static final boolean extractOnlyOneInfographic = false;
	public static final String extractInfographic ="infographic_drought";
	
	public static final String heatChartsFolder = "heatcharts/";	
	public static boolean absoluteHeatChart = true;
}
