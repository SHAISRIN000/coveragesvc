package com.octank.coveragesvc.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import ch.qos.logback.core.net.SyslogOutputStream;
import static com.mongodb.client.model.Filters.*;



@RestController
@RequestMapping(path ="/coverages")
@XRayEnabled
public class CoverageSvc {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	
	
	
	
	@PostMapping(path= "/{policy}", consumes = "application/json", produces = "application/json")
    public CoverageDetails addCoverages(@PathVariable("policy") String policy,@RequestBody CoverageDetails coverages) 
    {
	    System.out.println("Entered inside Add Coverages");
		System.out.println("The Policy Number is "+policy);
	  
		Random ran = new Random();
		int x = ran.nextInt(100) + 100;
		int policyNumber = ran.nextInt(500000) + 5000;

		System.out.println("The id is "+x);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand("101")
                 .toUri();

		
		  
		  String connectionString =
"mongodb://octankdev:octankdev@octankdev1.cluster-cfseldobtmse.us-east-1.docdb.amazonaws.com:27017/?replicaSet=rs0&readPreference=secondaryPreferred";		  //octank.cluster-ct9cduhirshz.us-east-1.docdb.amazonaws.com:27017
		  MongoClientURI clientURI = new MongoClientURI(connectionString);
		  MongoClient mongoClient = new MongoClient(clientURI);
		  
		  MongoDatabase testDB = mongoClient.getDatabase("octankdev");
		  MongoCollection<Document> numbersCollection =
		  testDB.getCollection("coverages");
		//Converting a custom Class(Employee) to BasicDBObject
		  Gson gson = new Gson();
		    String json = gson.toJson(coverages);

		  Document doc = Document.parse(json);
	//	  BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(applicant));
		  numbersCollection.insertOne(doc);
		  System.out.println("Inserted Coverages Successfully");
		  
//		  Document doc = new Document("id", applicant.getId()).append("value", applicant);
//		  numbersCollection.insertOne(doc);
//		  
//		  MongoCursor<Document> cursor = numbersCollection.find().iterator(); try {
//		  while (cursor.hasNext()) { System.out.println(cursor.next().toJson()); } }
//		  finally { cursor.close(); }
		  System.out.println("Exit  Add Coverages");
			
return coverages;
	 


	}
	
	
	
}