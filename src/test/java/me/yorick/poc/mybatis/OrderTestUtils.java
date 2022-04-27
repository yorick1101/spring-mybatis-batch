package me.yorick.poc.mybatis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import me.yorick.poc.mybatis.model.BuySell;
import me.yorick.poc.mybatis.model.Order;
import me.yorick.poc.mybatis.model.OrderStatus;

public class OrderTestUtils {
	
	public static List<Order> createOrders(int size){
		return IntStream.rangeClosed(1, size)
				.mapToObj(i->
					Order.builder()
					.id(Integer.toString(i))
					.customerId(Integer.toString(i))
					.product("product")
					.amount(randomAmount())
					.price(randomPrice())
					.status(OrderStatus.OPEN)
					.buySell(BuySell.BUY)
					.openTimestamp(LocalDateTime.now())
					.build()
				).collect(Collectors.toList());
	}
	
	
	
	private static double randomPrice() {
		return randomDouble(100, 150, 4);
	}
	
	private static double randomAmount() {
		return randomDouble(1, 5, 1);
	}
	
	private static double randomDouble(int min, int max, int precision) {
		double randomNum = new Random().nextDouble(min, max);
		double multiplier = Math.pow(10, precision);
		return randomNum*multiplier/multiplier;
	}
}
