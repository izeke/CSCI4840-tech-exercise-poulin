package datamodel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

/**
 * @since J2SE-1.8
 CREATE TABLE income (
  id INT NOT NULL AUTO_INCREMENT,    
  name VARCHAR(30) NOT NULL,   
  amount DECIMAL(15,2) NOT NULL,
  received_date DATETIME NOT NULL,
  insert_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,    
  PRIMARY KEY (id));
 */
@Entity
@DynamicInsert
@Table(name = "incomepoulin")
public class IncomePoulin implements Finance{

	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "id")
	   private Integer id;

	   @Column(name = "name")
	   private String name;

	   @Column(name = "amount")
	   private String amount;
	   
	   @Column(name = "received_date")
	   private String receivedDate;
	   
	   @Column(name = "insertDate", columnDefinition = "datetime not null default current_timestamp")
	   private String insertDate;

	   public IncomePoulin(Integer id, String name, String amount, String receivedDate) {
		   this.id = id;
		   this.name = name;
		   this.amount = amount;
		   this.receivedDate = receivedDate;
	   }
	   
	   public IncomePoulin(String name, String amount, String receivedDate) {
		   this.name = name;
		   this.amount = amount;
		   this.receivedDate = receivedDate;
	   }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransactionDate() {
		return receivedDate;
	}

	public void setreceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Integer getId() {
		return id;
	}
	
	public String getInsertDate() {
		return insertDate;
	}
}
