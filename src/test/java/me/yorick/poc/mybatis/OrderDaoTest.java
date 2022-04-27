package me.yorick.poc.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;
import me.yorick.poc.mybatis.dao.OrderDao;

@MybatisTest
@ContextConfiguration(classes=OrderDao.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 使用真实的数据库
//@Rollback(value = false)    //default is true, rollback after test
@Slf4j
public class OrderDaoTest {
	
	
	@Autowired
	private OrderDao orderDao;

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Test
	public void batchInsertLoop() {
		int size = 10000;
		final StopWatch watch = new StopWatch();
		watch.start();
		orderDao.batchSaveOrdersEach(OrderTestUtils.createOrders(size));
		watch.stop();
		log.info("batchInsertLoop watch:{}",watch.getLastTaskTimeMillis());
		assertEquals(size, JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders"));
	}
	
	@Test
	public void batchInsertOnce() {
		int size = 10000;
		final StopWatch watch = new StopWatch();
		watch.start();
		orderDao.batchSaveOrdersOnce(OrderTestUtils.createOrders(size));
		watch.stop();
		log.info("batchInsertOnce watch:{}",watch.getLastTaskTimeMillis());
		assertEquals(size, JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders"));
	}
	
	
}
