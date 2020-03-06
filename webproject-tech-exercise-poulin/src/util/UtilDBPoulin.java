/**
 */
package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import datamodel.EmployeePoulin;
import datamodel.ExpensePoulin;
import datamodel.Finance;
import datamodel.IncomePoulin;

/**
 * @since JavaSE-1.8
 */
public class UtilDBPoulin {
   static SessionFactory sessionFactory = null;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory != null) {
         return sessionFactory;
      }
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
   }

   public static List<EmployeePoulin> listEmployees() {
      List<EmployeePoulin> resultList = new ArrayList<EmployeePoulin>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         List<?> employees = session.createQuery("FROM EmployeePoulin").list();
         for (Iterator<?> iterator = employees.iterator(); iterator.hasNext();) {
            EmployeePoulin employee = (EmployeePoulin) iterator.next();
            resultList.add(employee);
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static List<EmployeePoulin> listEmployees(String keyword) {
      List<EmployeePoulin> resultList = new ArrayList<EmployeePoulin>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         List<?> employees = session.createQuery("FROM EmployeePoulin").list();
         for (Iterator<?> iterator = employees.iterator(); iterator.hasNext();) {
            EmployeePoulin employee = (EmployeePoulin) iterator.next();
            if (employee.getName().startsWith(keyword)) {
               resultList.add(employee);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }
   
   public static List<Finance> listTransactions(String lowerDate, String upperDate) {
	      List<Finance> resultList = new ArrayList<Finance>();

	      Session session = getSessionFactory().openSession();
	      Transaction tx = null;

	      try {
	         tx = session.beginTransaction();
	         List<?> expenses = session.createQuery("FROM ExpensePoulin WHERE purchase_date BETWEEN :lower AND :upper ORDER BY purchase_date ASC")
	        		 .setParameter("lower", lowerDate).setParameter("upper", upperDate).list();
	         List<?> income = session.createQuery("FROM IncomePoulin WHERE received_date BETWEEN :lower AND :upper ORDER BY received_date ASC")
	        		 .setParameter("lower", lowerDate).setParameter("upper", upperDate).list();
	         ListIterator<?> expensesIterator = expenses.listIterator();
	         ListIterator<?> incomeIterator = income.listIterator();
	         while (expensesIterator.hasNext() || incomeIterator.hasNext()) {
	        	 if (expensesIterator.hasNext() && !incomeIterator.hasNext()) {
	        		 resultList.add((ExpensePoulin)expensesIterator.next());
	        	 } else if (!expensesIterator.hasNext() && incomeIterator.hasNext()) {
	        		 resultList.add((IncomePoulin)incomeIterator.next());
	        	 } else {
	        		 ExpensePoulin nextExpense = (ExpensePoulin)expensesIterator.next();
	        		 IncomePoulin nextIncome = (IncomePoulin)incomeIterator.next();
	        		 if (nextExpense.getTransactionDate().compareTo(nextIncome.getTransactionDate()) < 0) {
	        			 resultList.add(nextExpense);
	        			 incomeIterator.previous();
	        		 } else {
	        			 resultList.add(nextIncome);
	        			 expensesIterator.previous();
	        		 }
	        		 System.out.println(nextExpense.getTransactionDate());
	        		 System.out.println(nextIncome.getTransactionDate());
	        		 System.out.println(nextExpense.getTransactionDate().compareTo(nextIncome.getTransactionDate()));
	        		 
	        	 }
	         }
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx != null)
	            tx.rollback();
	         e.printStackTrace();
	      } finally {
	         session.close();
	      }
	      return resultList;
	   }

   public static void createEmployees(String userName, String age, String phone) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(new EmployeePoulin(userName, Integer.valueOf(age), phone));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
   
   public static void createExpense(String name, String amount, String purchaseDate) {
	   Session session = getSessionFactory().openSession();
	   Transaction tx = null;
	   try {
		   tx = session.beginTransaction();
		   session.save(new ExpensePoulin(name, amount, purchaseDate));
		   tx.commit();
	   } catch (HibernateException e) {
		   if (tx != null) {
			   tx.rollback();
		   }
		   e.printStackTrace();
	   } finally {
		   session.close();
	   }
   }
   
   public static void createIncome(String name, String amount, String receivedDate) {
	   Session session = getSessionFactory().openSession();
	   Transaction tx = null;
	   try {
		   tx = session.beginTransaction();
		   session.save(new IncomePoulin(name, amount, receivedDate));
		   tx.commit();
	   } catch (HibernateException e) {
		   if (tx != null) {
			   tx.rollback();
		   }
		   e.printStackTrace();
	   } finally {
		   session.close();
	   }
   }
}
