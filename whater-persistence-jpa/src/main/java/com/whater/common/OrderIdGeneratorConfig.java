package com.whater.common;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.whater.common.util.SnowflakeIdWorker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderIdGeneratorConfig implements IdentifierGenerator {
 
  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {
    /**
     * 获取容器中的 Bean
     */
//    SnowflakeIdWorker snowflakeIdWorker = SpringUtil.getBean(SnowflakeIdWorker.class);
    long id = SnowflakeIdWorker.generateId();
    log.info("id -> [{}]", id);
    return id;
  }
}
