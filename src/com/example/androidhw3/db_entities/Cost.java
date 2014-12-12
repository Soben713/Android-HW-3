package com.example.androidhw3.db_entities;

import static com.orm.SugarRecord.save;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table(name = "cost")
public class Cost {
	/** 
	 * This is the code I used to save a cost and retrieve their list
	 * the code could be placed anywhere.
	 * 
	 * Cost cost = new Cost(new Date(), 928347, true);
	 * save(cost);	
	 * List<Cost> costs = SugarRecord.listAll(Cost.class);
	 */
	private Date date;
	private int cost;
	private boolean isIncome;

	public Cost(Date date, int cost, boolean isIncome) {
		super();
		this.date = date;
		this.cost = cost;
		this.isIncome = isIncome;
	}
	
	public Cost(){
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public boolean isIncome() {
		return isIncome;
	}

	public void setIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}

	@Override
	public String toString() {
		return "Cost [date=" + date + ", cost=" + cost + ", isIncome="
				+ isIncome + "]";
	}
}
