package com.example.emngr.cntrlrimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emngr.cntrlr.AmountBalancerController;
import com.example.emngr.mongocoll.AmountBalancerCollection;
import com.example.emngr.mongorepoimpl.AmountBalancerRepoImpl;
import com.example.emngr.utl.UtilImpl;

import com.mongodb.BasicDBObject;

@RestController
public class AmountBalancerControllerImpl implements AmountBalancerController {

	@Autowired
	AmountBalancerRepoImpl amountRepo;

	private HashMap<String, String> response;
	private UtilImpl util;
	private HashMap<String, String> thisMonth;
	private List<AmountBalancerCollection> amountBalancerList;

	BasicDBObject response_Obj ;

	@Override
	@RequestMapping(value = "/addamount", method = RequestMethod.POST)
	public HashMap<String, String> addAmount(@RequestBody HashMap<String, String> request, HttpSession session) {
		response = new HashMap<String, String>();

		try {
			if (null == session.getAttribute("un") || session.getAttribute("un").toString().length() < 2) {
				response.put("rmessage", "INV_UN");
			} else if(null==request.get("un_to") || request.get("un_to").toString().length()<2){
				response.put("rmessage", "INV_UN_TO");
			}
			 else if(null==request.get("rsn") || request.get("rsn").toString().length()<2 || request.get("rsn").toString().length()>10){
					response.put("rmessage", "INV_RSN");
				}
			 else if(null==request.get("amnt") || Float.parseFloat(request.get("amnt").toString())<0.1){
					response.put("rmessage", "INV_AMNT");
				}
			else{

				request.put("un_frm", session.getAttribute("un").toString());

				util = new UtilImpl();
				thisMonth = util.getThisMonth();
				request.put("dy", thisMonth.get("dy").toString());
				request.put("mnth", thisMonth.get("mnth").toString());
				request.put("yr", thisMonth.get("yr").toString());
				request.put("tmstmp", thisMonth.get("tmstmp").toString());
				amountRepo.addAmount(request);
				response.put("rmessage", "SUCCESS");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("rmessage", "EXCEPTION");
			response.put("emessage", e.toString());
		}

		return response;
	}

	@Override
	@RequestMapping("/getmonthbalancer")
	public BasicDBObject getThisMonthAmountBalancerData(@RequestParam(required = false) boolean prevmnth) {

		response_Obj = new BasicDBObject();
		BasicDBObject r_di;
		List<BasicDBObject> l_di = new ArrayList<BasicDBObject>();
		float exp_total = 0;

		try {
			util = new UtilImpl();
			if(prevmnth) {
				thisMonth = util.getPreviuosMonth();
			}
			else {
				thisMonth = util.getThisMonth();
			}
			
			amountBalancerList = amountRepo.getAmountBalancerSummary(thisMonth);
			Iterator<AmountBalancerCollection> iterator = amountBalancerList.iterator();
			

			while (iterator.hasNext()) {
				AmountBalancerCollection amount = iterator.next();

				exp_total = exp_total + amount.getAmnt();

				

				

				// Adding to Response
				r_di = new BasicDBObject();
				r_di.append("un_from", amount.getUn_frm());
				r_di.append("un_to", amount.getUn_to());
				r_di.append("amnt", amount.getAmnt());
				r_di.append("dy", amount.getDy());
				r_di.append("mnth", amount.getMnth());
				r_di.append("yr", amount.getYr());
				r_di.append("rsn", amount.getRsn());

				l_di.add(r_di);

			}

			response_Obj.append("rmessage", "SUCCESS");
			response_Obj.append("di", l_di);
			response_Obj.append("yr", thisMonth.get("yr"));
			response_Obj.append("mnth", thisMonth.get("mnth"));
			response_Obj.append("texpense", exp_total);

		} catch (Exception e) {
			response_Obj.append("rmessage", "EXCEPTION");
			response_Obj.append("rmessage", e.toString());
			e.printStackTrace();
		}

		return response_Obj;
	}

	@Override
	@RequestMapping("/getusers")
	public BasicDBObject getUsers(HttpSession session) {

		response_Obj = new BasicDBObject();
		try {
			if(null==session.getAttribute("un") || session.getAttribute("un").toString().length()<2) {
				response_Obj.append("rmessage", "INV_UN");
				/*
				 * session.setAttribute("un","vksonpdl"); response_Obj.append("rmessage",
				 * "SUCCESS"); response_Obj.append("un_list",
				 * amountRepo.getUsers(session.getAttribute("un").toString()));
				 */
			}
			else {
				response_Obj.append("rmessage", "SUCCESS");
				response_Obj.append("un_list", amountRepo.getUsers(session.getAttribute("un").toString()));
			}
			
			
			
		} catch (Exception e) {
			response_Obj.append("rmessage", "EXCEPTION");
			response_Obj.append("rmessage", e.toString());
			e.printStackTrace();
		}
		return response_Obj;
	}

}
