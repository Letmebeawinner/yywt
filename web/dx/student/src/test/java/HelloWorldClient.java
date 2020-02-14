import com.jiaowu.webservice.UserManageAddServiceHttpBindingStub;
import com.jiaowu.webservice.UserManageAddServiceLocator;
import com.jiaowu.webservice.UserManageAddServicePortType;
import org.apache.axis.AxisEngine;
import org.apache.axis.EngineConfiguration;

/**
 * 警告需要配置axis
 * AXIS_LIB=%AXIS_HOME%\lib 环境变量
 *
 * [DEBUG]-14:19:03-org.apache.axis.ConfigurationException
 * .logException(ConfigurationException.java:110) - Exception:
 *
 * org.apache.axis.ConfigurationException:
 * No service named UserManageAddServiceHttpPort is available
 * DEBUG级别的日志报这个错是正常现象,
 *
 * @see AxisEngine#getService(java.lang.String)
 * 因为axis先从
 * @see EngineConfiguration#getService(javax.xml.namespace.QName)
 * 里找SOAPService, 找不到虽然抛<code>debug</code>异常, 但继续往下
 * 再尝试从
 * @see EngineConfiguration#getServiceByNamespaceURI(java.lang.String)
 * 是能找到SOAPService的
 *
 * @author Maxwe
 * @date 2018/3/28
 */
public class HelloWorldClient {
  public static void main(String[] argv) {
      try {
          UserManageAddServiceLocator locator = new UserManageAddServiceLocator();
          UserManageAddServicePortType portType = locator.getUserManageAddServiceHttpPort();
          // If authorization is required
          ((UserManageAddServiceHttpBindingStub)portType).setUsername("admin");
          ((UserManageAddServiceHttpBindingStub)portType).setPassword("UMCAdministrator");

          // invoke business method
          String rs = portType.addUser(
                  "test",
                  "真实姓名",   "111111",
                  1,
                  "/",
                  1521208610,"15701389147");

          // TODO 删除接口无效
          String rs1 = portType.delUser("test");

          String rs3 = portType.addUser(
                  "test",
                  "真实姓名","111111",
                  1,
                  "/",
                  1521208610,"15701389147");

//          long time = System.currentTimeMillis();
//          System.out.println(time/1000);
//          System.out.println(1521208610);

          System.out.println(rs);
          System.out.println(rs1);
          System.out.println(rs3);
      } catch (javax.xml.rpc.ServiceException | java.rmi.RemoteException ex) {
          ex.printStackTrace();
      }
  }
}
