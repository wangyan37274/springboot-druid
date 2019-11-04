import com.wangyan.druid.Application;
import com.wangyan.druid.test.SingletonDB;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * @Auther: wangyan
 * @Date: 2019/10/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class Test {

    private static SingletonDB db = SingletonDB.getInstance();
    @Autowired
    private DataSource druidDataSource;

    @org.junit.Test
    public void test() {
        String sql = "UPDATE t_user_app SET appname = ? WHERE nid = ?";
        String appName = "3333";
        Integer nid = 4;
        db.begin(druidDataSource);
        db.execute(druidDataSource,sql,appName,nid);
        //db.commit();
    }

    @org.junit.Test
    public void test1() {
        String sql = "UPDATE t_user_app SET appname = ? WHERE nid = ?";
        String appName = "3333";
        Integer nid = 4;
        //db.begin(druidDataSource);
        db.execute(druidDataSource,sql,appName,nid);
        //db.commit();
    }
}