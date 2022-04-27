package me.yorick.poc.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;
import me.yorick.poc.mybatis.model.Order;
import me.yorick.poc.mybatis.service.OrderService;

@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 使用真实的数据库
//@Rollback(value = false)    //default is true, rollback after test
@ComponentScan(basePackageClasses= {OrderService.class})
@Slf4j
public class OrderServiceTest {
	
	@Autowired
	private OrderService orderService;
	
	@Test
	public void testTransactionalBatch() throws InterruptedException {
		int size = 10000;
		final StopWatch watch = new StopWatch();
		watch.start();	
		List<Order> orders = OrderTestUtils.createOrders(size);
		orderService.newOrders(orders);
		watch.stop();
		int count = orderService.count();
		assertEquals(size, count);
		log.info("watch:{}",watch.getLastTaskTimeMillis());
		
	}
	
	
}
