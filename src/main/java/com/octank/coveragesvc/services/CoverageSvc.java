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
@RequestMapping(path = "/coverages")
@XRayEnabled
public class CoverageSvc {


	@PostMapping(path = "/{policy}", consumes = "application/json", produces = "application/json")
	public CoverageDetails addCoverages(@PathVariable("policy") String policy, @RequestBody CoverageDetails coverages) {
		System.out.println("Entered inside Add Coverages");
		System.out.println("The Policy Number is " + policy);

	
		coverages.setPolicyNumber(Integer.getInteger(policy));

		String connectionString = "mongodb://octankdev:octankdev@octankdev1.cluster-cfseldobtmse.us-east-1.docdb.amazonaws.com:27017/?replicaSet=rs0&readPreference=secondaryPreferred"; // octank.cluster-ct9cduhirshz.us-east-1.docdb.amazonaws.com:27017
		MongoClientURI clientURI = new MongoClientURI(connectionString);
		MongoClient mongoClient = new MongoClient(clientURI);

		MongoDatabase testDB = mongoClient.getDatabase("octankdev");
		MongoCollection<Document> numbersCollection = testDB.getCollection("coverages");
		Gson gson = new Gson();
		String json = gson.toJson(coverages);

		Document doc = Document.parse(json);
		numbersCollection.insertOne(doc);
		System.out.println("Inserted Coverages Successfully");
		System.out.println("Exit  Add Coverages");

		return coverages;

	}

	
	 @GetMapping(value = "/{policy}",produces= "application/json")
	    public CoverageDetails getPolicyCoverages(@PathVariable("policy") String policy)
	    {

		 System.out.print("The policy number is "+policy);
		 
		 
		 AWSXRay.beginSubsegment("Saving Applicant into DocumentDB");
		 List<Coverage> applicants=new ArrayList<Coverage>();
		  String connectionString =
		  "mongodb://octankdev:octankdev@octankdev1.cluster-cfseldobtmse.us-east-1.docdb.amazonaws.com:27017/?replicaSet=rs0&readPreference=secondaryPreferred";
		  //octank.cluster-ct9cduhirshz.us-east-1.docdb.amazonaws.com:27017
		  MongoClientURI clientURI = new MongoClientURI(connectionString);
		  MongoClient mongoClient = new MongoClient(clientURI);
		  
		  MongoDatabase db = mongoClient.getDatabase("octankdev");
		  MongoCollection<Document> numbersCollection =
		  db.getCollection("coverages");

//		// Retrieve to ensure object was inserted
//		    FindIterable<Document> iterable = db.getCollection("NameColl").find();
//		    iterable.forEach(new Block<Document>() {
//		      @Override
//		      public void apply(final Document document) {
//		        System.out.println(document); // See below to convert document back to Employee
//		      }
//		    });
		  BasicDBObject searchQuery = new BasicDBObject();
		  searchQuery.put("policyNumber",Integer.parseInt(policy));
		  CoverageDetails coverage=null;
		  MongoCursor<Document> cursor = numbersCollection.find(eq("policyNumber", Integer.parseInt(policy))).iterator();
			  try {
			  while (cursor.hasNext()) { 
				  Gson gson = new Gson();
				  System.out.println("JSON Retrieve is ");
				   coverage=gson.fromJson(cursor.next().toJson(), CoverageDetails.class);
				  
				//  System.out.println(cursor.next().toJson()); 
				  } 
			  }
			  finally { cursor.close(); }
			  AWSXRay.endSubsegment();
		    
//		 ContactDetails contacts=new ContactDetails(new Address("line2", "West", "MA", "01581"), "test@test.com");
//		 
//		    
//    Applicant applicant=new Applicant(101,"ssf","sdfsdf",contacts,false);
    return coverage;
	    }
	 
	
	
}