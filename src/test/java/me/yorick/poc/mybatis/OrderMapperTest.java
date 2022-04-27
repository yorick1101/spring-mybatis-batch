package me.yorick.poc.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;
import me.yorick.poc.mybatis.mapper.OrderMapper;

@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 使用真实的数据库
//@Rollback(value = false)    //default is true, rollback after test
@Slf4j
public class OrderMapperTest {
	
	@Autowired
	private OrderMapper orderMapper;
	
	
	/**
	 * Failed if mybatis:default-executor-type: BATCH added to application.yml
	 * @throws IllegalStateException
	 * @throws SQLException
	 */
	@Test
	public void batchInsert() throws IllegalStateException, SQLException {
		int size = 10000;
		final StopWatch watch = new StopWatch();
		watch.start();
		orderMapper.newOrders(OrderTestUtils.createOrders(size));
		watch.stop();
		log.info("watch:{}",watch.getLastTaskTimeMillis());
		assertEquals(size, orderMapper.count());
	}
	

}
