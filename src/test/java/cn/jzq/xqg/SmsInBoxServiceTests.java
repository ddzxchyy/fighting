package cn.jzq.xqg;

import org.junit.Assert;
//import org.junit.Test;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 收件箱 service 单元测试类
 * - 翻页
 * - limit
 * - 排序 按 id 倒序
 * - 只查询发送对象为自己的短信记录
 *
 * @author by jzq
 * @date 2020/1/10
 */
@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class SmsInBoxServiceTests {

    @Test
    public void pageTest() {
        Assert.assertFalse(true);
    }

    @Test
    public void limitTest() {

    }

    @Test
    public void sortTest() {

    }

    @Test
    public void user_ShouldQuery_OwnData() {

    }

}
