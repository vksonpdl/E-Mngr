package com.example.emngr.cntrlrimpl;

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

import com.example.emngr.bean.ExpenseStatementBean;
import com.example.emngr.cntrlr.ExpenseController;
import com.example.emngr.mail.MailSenderEmngr;
import com.example.emngr.mongocoll.ExpenseCollection;
import com.example.emngr.mongorepoimpl.CredentialRepoImpl;
import com.example.emngr.mongorepoimpl.ExpenseRepoImpl;
import com.example.emngr.utl.UtilImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

@RestController
public class ExpenseControllerImpl implements ExpenseController {


	@Autowired
	MailSenderEmngr mail;

	@Autowired
	ExpenseRepoImpl expenseRepo;
	@Autowired
	CredentialRepoImpl credentialRepo;
	ExpenseCollection expense;
	ExpenseStatementBean esBean;

	UtilImpl util;
	private HashMap<String, String> dateObj;

	@Override
	@RequestMapping(value = "/addexpense", method = RequestMethod.POST)
	public HashMap<String, String> addExpense(@RequestBody HashMap<String, String> request, HttpSession session) {
		HashMap<String, String> response = new HashMap<String, String>();
		
		try {

			
			if (null == session.getAttribute("un") || session.getAttribute("un").toString().length() < 2) {
				response.put("rmessage", "INV_UN");
			} else if (null == request.get("cat") || request.get("cat").toString().length() < 2) {
				response.put("rmessage", "INV_CAT");
			} else if (null == request.get("exp") || Double.parseDouble(request.get("exp")) < 0.1) {
				response.put("rmessage", "INV_EXP");
			}

			else {
				String un = session.getAttribute("un").toString();
				request.put("un", un);
				util = new UtilImpl();

				HashMap<String, String> dateObj = util.getThisMonth();

				request.put("dy", dateObj.get("dy"));
				request.put("mnth", dateObj.get("mnth"));
				request.put("tmstmp", dateObj.get("tmstmp"));
				request.put("yr", dateObj.get("yr"));

				expenseRepo.addExpense(request, credentialRepo.getUserList());
				response.put("rmessage", "SUCCESS");
	
				mail.sendAddExpenseEmail(request, credentialRepo.getEmailList());				

			}

		} catch (Exception e) {
			response.put("rmessage", "EXCEPTION");
			response.put("rexception", e.toString());
			e.printStackTrace();

		}
		return response;
	}

	@Override
	@RequestMapping("/getmonthexpense")
	public BasicDBObject viewThisMonthExpense(@RequestParam(required = false) boolean prevmnth) {
		BasicDBObject response = new BasicDBObject();

		BasicDBList expense_list = new BasicDBList();
		BasicDBObject expense_obj;

		try {
			util = new UtilImpl();
			if(prevmnth) {
				dateObj = util.getPreviuosMonth();
			}
			else {
				dateObj = util.getThisMonth();
			}
			
			List<ExpenseCollection> expenses = expenseRepo.getMonthExpense(dateObj.get("yr"), dateObj.get("mnth"));
			Iterator<ExpenseCollection> iterator = expenses.iterator();

			// Contain Total Expense for Current Month
			float exp_sum = 0;

			// Iterating Through Expenses and Adding to Expense Paid
			while (iterator.hasNext()) {

				expense_obj = new BasicDBObject();

				expense = (ExpenseCollection) iterator.next();
				exp_sum = exp_sum + expense.getExp();
				expense_obj.append("un", expense.getUn());
				expense_obj.append("exp", expense.getExp());
				expense_obj.append("cat", expense.getCat());
				expense_obj.append("dy", expense.getDy());
				expense_obj.append("mnth", expense.getMnth());
				expense_obj.append("yr", expense.getYr());

				expense_list.add(expense_obj);

			}

			response.append("rmessage", "SUCCESS");
			response.append("texpense", exp_sum);
			response.append("yr", dateObj.get("yr"));
			response.append("mnth", dateObj.get("mnth"));
			response.append("avgexp", (exp_sum / credentialRepo.getUserList().size()));
			response.append("exp", expense_list);

		} catch (Exception e) {
			response.append("rmessage", "EXCEPTION");
			response.append("emessage", e.toString());
		}

		return response;
	}

}
