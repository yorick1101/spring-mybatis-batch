package me.yorick.poc.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;
import me.yorick.poc.mybatis.mapper.OrderMapper;
import me.yorick.poc.mybatis.model.Order;
import me.yorick.poc.mybatis.model.OrderStatus;

@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 使用真实的数据库
//@Rollback(value = false)    //default is true, rollback after test
@Slf4j
public class OrderMapperTest{
	
	@Autowired
	private OrderMapper orderMapper;
	
	int size = 5;
	int updateCount = 3;
	
	/**
	 * Failed if mybatis:default-executor-type: BATCH added to application.yml
	 * @throws IllegalStateException
	 * @throws SQLException
	 */
	@Test
	public void multiRowInsert() {
		final StopWatch watch = new StopWatch();
		watch.start();
		orderMapper.newOrders(OrderTestUtils.createOrders(size));
		watch.stop();
		log.info("multiRowInsert watch:{}",watch.getLastTaskTimeMillis());
		assertEquals(size, orderMapper.count());
	}
	
	@Test
	public void multiInsert() {
		final StopWatch watch = new StopWatch();
		watch.start();
		orderMapper.newOrdersMulti(OrderTestUtils.createOrders(size));
		watch.stop();
		log.info("multiInsert watch:{}",watch.getLastTaskTimeMillis());
		assertEquals(size, orderMapper.count());
	}
	
	
	
	@Test
	public void multipleStatementsUpdateStatus() {
		orderMapper.newOrders(OrderTestUtils.createOrders(size));
		orderMapper.flush();
		List<Order> updates = IntStream.range(1, updateCount).mapToObj(i -> Order.builder().id(Integer.toString(i)).status(OrderStatus.CLOSE).build()).collect(Collectors.toList());
		final StopWatch watch = new StopWatch();
		watch.start();
		for(Order order : updates)
			orderMapper.updateStatus(order.getId(), order.getStatus());
		orderMapper.flush();
		watch.stop();
		log.info("Multiple Statements watch:{}",watch.getLastTaskTimeMillis());
	}
	
	@Test
	public void oneFatStatementUpdateStatus() {
		
		orderMapper.newOrders(OrderTestUtils.createOrders(size));
		orderMapper.flush();
		List<Order> updates = IntStream.range(1, updateCount).mapToObj(i -> Order.builder().id(Integer.toString(i)).status(OrderStatus.CLOSE).build()).collect(Collectors.toList());
		final StopWatch watch = new StopWatch();
		watch.start();
		orderMapper.updateOrders(updates);
		orderMapper.flush();
		watch.stop();
		log.info("One Statement Update watch:{}",watch.getLastTaskTimeMillis());
		
	}

}
