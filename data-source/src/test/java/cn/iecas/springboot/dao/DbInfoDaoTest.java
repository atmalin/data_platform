package cn.iecas.springboot.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest()
public class DbInfoDaoTest {
    @Autowired
    private DbInfoDao dbInfoDao;

//    @Test
//    void test1(){
//        System.out.println("1");
//    }
    @Test
    void testfindDbInfoBeanByNameLike(){
        System.out.println(dbInfoDao.findAll());
    }

}
