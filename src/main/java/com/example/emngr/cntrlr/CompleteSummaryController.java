package com.example.emngr.cntrlr;



import com.mongodb.BasicDBObject;

public interface CompleteSummaryController {
	public BasicDBObject getCompleteSummary(boolean prevMnth);

}
