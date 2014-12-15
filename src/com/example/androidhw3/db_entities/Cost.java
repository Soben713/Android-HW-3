package com.example.androidhw3.db_entities;

import java.util.Date;

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
	private int income;

	public Cost(Date date, int cost, int income) {
		super();
		this.date = date;
		this.cost = cost;
		this.income = income;
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

	public int getIncome() {
		return income;
	}

	public void setIncome(int isIncome) {
		this.income = isIncome;
	}

	@Override
	public String toString() {
		return "Cost [date=" + date + ", cost=" + cost + ", income="
				+ income + "]";
	}
}
