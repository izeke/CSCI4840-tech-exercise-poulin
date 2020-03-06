package datamodel;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE expense (
  id INT NOT NULL AUTO_INCREMENT,    
  name VARCHAR(30) NOT NULL,   
  amount DECIMAL(15,2) NOT NULL,
  purchase_date DATETIME NOT NULL,
  insert_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,    
  PRIMARY KEY (id));
 */
@Entity
@Table(name = "expensepoulin")
public class ExpensePoulin {

	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "id")
	   private Integer id;

	   @Column(name = "name")
	   private String name;

	   @Column(name = "amount")
	   private String amount;
	   
	   @Column(name = "purchase_date")
	   private String purchaseDate;
	   
	   @Column(name = "insertDate", columnDefinition = "datetime default current_timestamp")
	   private String insertDate;

	   public ExpensePoulin(Integer id, String name, String amount, String purchaseDate) {
		   this.id = id;
		   this.name = name;
		   this.amount = amount;
		   this.purchaseDate = purchaseDate;
	   }
	   
	   public ExpensePoulin(String name, String amount, String purchaseDate) {
		   this.name = name;
		   this.amount = amount;
		   this.purchaseDate = purchaseDate;
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

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Integer getId() {
		return id;
	}
}
