package me.yorick.poc.mybatis.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.yorick.poc.mybatis.model.Order;
import me.yorick.poc.mybatis.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private OrderService orderService;
	
	@Autowired
	public void setOrderSerivice(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("/{id}")
	public Optional<Order> findById(@PathVariable String id) {
		long start = System.currentTimeMillis();
		try {
			return orderService.findById(id);
		}finally {
			System.out.print(System.currentTimeMillis()-start);
		}
		
		
	}

}
