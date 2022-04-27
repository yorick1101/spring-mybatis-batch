package me.yorick.poc.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Mapper;

import me.yorick.poc.mybatis.model.Order;

@Mapper
public interface OrderMapper {
	
	Order findOrderById(String id);
	@Flush
	void newOrders(List<Order> orders);
	@Flush
	void newOrder(Order order);
	
	int count();

}
 