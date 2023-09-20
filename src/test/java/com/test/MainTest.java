package com.test;

import book.manage.sql.SqlUtil;
import org.junit.jupiter.api.Test;

public class MainTest {

    //Error attempting to get column 'desc' from result set.
    // Cause: java.sql.SQLDataException: Cannot determine value type from string '我是信息'
    //在Book实体类中加入 空参构造器   public Book() {}
    // 简单的说 mybatis 去查询的时候要用到无参构造方法
    @Test
    public void test1(){
        SqlUtil.doSqlWork(mapper -> {
            mapper.getBorrowList().forEach(System.out::println);
        });
    }

}
