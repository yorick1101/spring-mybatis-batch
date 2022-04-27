package me.yorick.poc.mybatis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.yorick.poc.mybatis.mapper.OrderMapper;
import me.yorick.poc.mybatis.model.Order;

@Service
public class OrderService {
	
	private final OrderMapper orderMapper;

	@Autowired
	public OrderService(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}
	
	public Optional<Order> findById(String id) {
		return Optional.ofNullable(orderMapper.findOrderById(id));
	}
	
	//@Transactional
	public void newOrders(List<Order> orders) {
		for(Order order: orders) {
			orderMapper.newOrder(order);
		}
	}
	
	public int count() {
		return orderMapper.count();
	}
	
	
	
}
