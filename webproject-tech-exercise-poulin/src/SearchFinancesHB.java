import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.ExpensePoulin;
import datamodel.Finance;
import datamodel.IncomePoulin;
import util.Info;
import util.UtilDBPoulin;

@WebServlet("/SearchFinancesHB")
public class SearchFinancesHB extends HttpServlet implements Info {
   private static final long serialVersionUID = 1L;

   public SearchFinancesHB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String lowerDate = request.getParameter("lowerDate").trim();
      String upperDate = request.getParameter("upperDate").trim();

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      List<Finance> listTransactions = UtilDBPoulin.listTransactions(lowerDate, upperDate);
      display(listTransactions, out);
      out.println("<a href=/" + projectName + "/" + searchWebName + ">Search Finances</a> <br>");
      out.println("<a href=/" + projectName + "/" + insertExpenseWebName + ">Insert Expense</a> <br>");
      out.println("<a href=/" + projectName + "/" + insertIncomeWebName + ">Insert Income</a> <br>");
      out.println("</body></html>");
   }

   void display(List<Finance> listTransactions, PrintWriter out) {
	   out.println("<table><tr><th>Name</th><th>Type</th><th>Amount</th><th>Date</th></tr>");
      for (Finance transaction : listTransactions) {
    	  if (transaction instanceof ExpensePoulin) {
    		  out.println("<tr><td>" + transaction.getName() + "</td><td>" //
    				  + "Expense</td><td>" 
    				  + "-" + transaction.getAmount() + "</td><td>"
    				  + transaction.getTransactionDate() + "</td></tr>");
    	  } else if (transaction instanceof IncomePoulin) {
    		  out.println("<tr><td>" + transaction.getName() + "</td><td>" //
    				  + "Income</td><td>" 
    				  + "+" + transaction.getAmount() + "</td><td>"
    				  + transaction.getTransactionDate() + "</td></tr>");
    	  }
      }
      out.println("</table>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
