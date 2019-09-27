package com.example.emngr.cntrlrimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emngr.cntrlr.CompleteSummaryController;
import com.example.emngr.mongocoll.MonthExpenseCummaryCollection;
import com.example.emngr.mongorepo.CompleteSummaryRepo;
import com.example.emngr.utl.UtilImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

@RestController
public class CompleteSummaryControllerImpl implements CompleteSummaryController {

	@Autowired
	CompleteSummaryRepo summaryRepo;
	private List<MonthExpenseCummaryCollection> monthSummary;
	private UtilImpl util;
	private BasicDBObject response_obj;
	private HashMap<String, String> dateInfo;

	@Override
	@RequestMapping("/getmonthsummary")
	public BasicDBObject getCompleteSummary(@RequestParam(required = false) boolean prevmnth) {

		response_obj = new BasicDBObject();
		BasicDBObject obj;
		BasicDBList objList = new BasicDBList();

		try {
			util = new UtilImpl();
			if (prevmnth) {
				dateInfo = util.getPreviuosMonth();
			} else {
				dateInfo = util.getThisMonth();
			}

			monthSummary = summaryRepo.getCurrentMonthSummary(dateInfo.get("mnth").toString(),
					dateInfo.get("yr").toString());

			Iterator<MonthExpenseCummaryCollection> iterator = monthSummary.iterator();
			MonthExpenseCummaryCollection summary;
			while (iterator.hasNext()) {
				summary = iterator.next();
				obj = new BasicDBObject();
				obj.append("un", summary.getUn());
				obj.append("ab_p", summary.getAb_paid());
				obj.append("ab_np", summary.getAb_ntb_paid());
				obj.append("e_p", summary.getE_paid());
				obj.append("e_np", summary.getE_ntb_paid());
				obj.append("t_p", summary.getAb_paid() + summary.getE_paid());
				obj.append("t_np", summary.getAb_ntb_paid() + summary.getE_ntb_paid());
				objList.add(obj);
			}

			response_obj.append("rmessage", "SUCCESS");
			response_obj.append("mnth", dateInfo.get("mnth").toString());
			response_obj.append("yr", dateInfo.get("yr").toString());
			response_obj.append("dat", objList);
		} catch (Exception e) {
			response_obj.append("rmessage", "EXCEPTION");
			response_obj.append("emessage", e.toString());
			e.printStackTrace();
		}

		return response_obj;
	}

}
