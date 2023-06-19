package com.example.uscream.netsales;

import com.example.uscream.order.Order;
import com.example.uscream.payroll.Payroll;
import com.example.uscream.selling.Selling;
import com.example.uscream.store.Store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Netsales {
	@Id
	@SequenceGenerator(name="seq_gen", sequenceName="seq_netsales", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_netsales")
	private int netsalesnum;
	
	@JoinColumn(name="storeid", nullable=false)
	private Store store;
	
	@JoinColumn(name="selling", nullable=false)
	private Selling selling;
	
	@JoinColumn(name="payroll", nullable=false)
	private Payroll payroll;
	
	@JoinColumn(name="order", nullable=false)
	private Order order;

}