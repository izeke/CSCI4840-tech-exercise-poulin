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
      out.println("<ul>");

      List<Finance> listTransactions = UtilDBPoulin.listTransactions(lowerDate, upperDate);
      display(listTransactions, out);
      out.println("</ul>");
      out.println("<a href=/" + projectName + "/" + searchWebName + ">Search Data</a> <br>");
      out.println("</body></html>");
   }

   void display(List<Finance> listTransactions, PrintWriter out) {
      for (Finance transaction : listTransactions) {
    	  if (transaction instanceof ExpensePoulin) {
//    		  System.out.println("[DBG] " + transaction.getId() + ", " //
//    				  + transaction.getName() + ", " //
//    				  + "Expense, " 
//    				  + "-" + transaction.getAmount() + ", "
//    				  + transaction.getTransactionDate());
    		  
    		  out.println("<li>" + transaction.getId() + ", " //
    				  + transaction.getName() + ", " //
    				  + "Expense, " 
    				  + "-" + transaction.getAmount() + ", "
    				  + transaction.getTransactionDate() + "</li>");
    	  } else if (transaction instanceof IncomePoulin) {
//    		  System.out.println("[DBG] " + transaction.getId() + ", " //
//    				  + transaction.getName() + ", " //
//    				  + "Income, " 
//    				  + "+" + transaction.getAmount() + ", "
//    				  + transaction.getTransactionDate());
    		  
    		  out.println("<li>" + transaction.getId() + ", " //
    				  + transaction.getName() + ", " //
    				  + "Income, " 
    				  + "+" + transaction.getAmount() + ", "
    				  + transaction.getTransactionDate() + "</li>");
    		  
    	  }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
