package test;

import com.yicardtong.biz.work.WorkSourceService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * 测试类
 *
 * @author s.li
 * @create 2017-02-21-18:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-applicationContext.xml"})
public class Test {

    @Before
    public void setConfig(){
        try {
            Log4jConfigurer.initLogging("classpath:log4j.properties");
        } catch (FileNotFoundException ex) {
            System.err.println("Cannot Initialize log4j");
        }
    }

    @Autowired
    private WorkSourceService workSourceService;

    @org.junit.Test
    public void test(){
        List<Map<String,String>> consume_costValueList = workSourceService.queryWorkSourceList("select * from Work_Source");
        System.out.println(consume_costValueList+"++++++++++++++++++++++++++");
    }
}
