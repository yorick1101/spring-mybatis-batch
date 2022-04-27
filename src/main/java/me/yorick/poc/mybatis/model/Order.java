package me.yorick.poc.mybatis.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
    private String id;
	private String customerId;
	private String product;
	
	private double price;
	private double amount;
	private LocalDateTime openTimestamp;
	private LocalDateTime updateTimestamp;
	private BuySell buySell;
	private OrderStatus status;
}
