package mongodb;

import java.util.ArrayList;
import java.util.List;

import main.Statics;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataExtractor {

	private static DB db;
	
	public DataExtractor(){
		connectToDatabase();
	}
	
	private void connectToDatabase(){	  	
        try{   
		   // To connect to mongodb server
           MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
           // Now connect to your databases
           db = mongoClient.getDB(Statics.DBName);
		   System.out.println("Connected to database");	
		  
        }catch(Exception e){
	       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }       
	}
	
	public List<DBObject> extractData(){
		if(db == null){
			System.out.println("Database not initialized");
			throw new IllegalStateException();
		}
		return extractMeasurementData();
	}

	private List<DBObject> extractMeasurementData() {
		DBCollection sessions = db.getCollection("sessions");
		
		BasicDBObject query = new BasicDBObject();
		
		if(Statics.extractOnlyOnePerson)
			query.append("name", Statics.extractName);
		if(Statics.extractOnlyOneInfographic)
			query.append("infographic", Statics.extractInfographic);
		
		DBObject match = new BasicDBObject("$match", query );

		BasicDBObject fields = new BasicDBObject();
		fields.put("_id", 0);
		fields.put("name", 1);
		fields.put("infographic", 1);
		fields.put("measurements.ESenseData", 1);
		fields.put("measurements.Position", 1);
		fields.put("measurements.ExtraPartId", 1);
		DBObject project = new BasicDBObject("$project", fields);
		
//		DBObject unwind = new BasicDBObject("$unwind", "$measurements");

//		AggregationOutput a = sessions.aggregate(match, project, unwind);
		AggregationOutput a = sessions.aggregate(match, project);

		List<DBObject> dataList = new ArrayList<>();
			
		for(DBObject d: a.results()){
			dataList.add(d);
		}
		
		return dataList;
	}	
}
