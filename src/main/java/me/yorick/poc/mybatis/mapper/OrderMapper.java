package me.yorick.poc.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.executor.BatchResult;

import me.yorick.poc.mybatis.model.Order;
import me.yorick.poc.mybatis.model.OrderStatus;

@Mapper
public interface OrderMapper {
	
	Order findOrderById(String id);
	
	void newOrders(List<Order> orders);
	
	void newOrdersMulti(List<Order> orders);
	
	void newOrder(Order order);
	
	int count();
	
	@Flush
	List<BatchResult> flush();
	
	void updateOrders(List<Order> orders);
	
	void updateStatus(String id, OrderStatus status);


}
 