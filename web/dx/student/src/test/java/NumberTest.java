import com.jiaowu.util.StringUtil;

/**
 * @author YaoZhen
 * @date 04-02, 11:52, 2018.
 */
public class NumberTest {
    public static void main(String[] args) {
        System.out.println(StringUtil.listToString(null));
        String cardNo = "'94152746', 'e4bf1d46', '840a557a', '447A3446', '6458597A', 'b40e497a', 'e436487a', '44625B7A', '4428497a', 'b47a4d7a', 'd4944c7a', 'e4795b7a', '94f6597a', '6444577a', '149e4d7a', 'C460407A', '045D1C46', '44d1447a', '243A497A', 'e4333a7a', 'd43e4a7a', '14274d7a', 'A247BA39', 'd49f5e7a', '7434527a', 'D4D41D46', '34ea1446', '1ae13ca4', 'f4ff4b7a', 'd43a3c7a', '94484E7A', '74d8457a', 'e4095a7a', '5425437a', '74ca567a', '34891846', 'd4c7537a', 'f425507a', '5422417a', 'f456d245', 'f42f527a', 'f4f13246', '34913246', 'a4c41746', 'A4DC427A', '64D4557A', '247a517a', '94E01C46', '04C95A7A', 'c4525d7a', 'b49fcf45', '4412497a', 'b4311c46', '043D2D46', '64b45b7a', '24de1e46', '1434557A', '14153346', 'e485467a', '84c6587a', 'c4fa3e7a', 'E6DB1FC9', '04C32D46', '849c2546', 'a4181346', '84472446', 'd456537a', '64644F7A', '74f95b7a', 'D4261946', 'F4646A79', 'd5c23ca4', '94424a7a', '81f1a8e9', '54705b7a', '74223F7A', '14EE2446', 'd4c91a46', 'F4C4407A', '34A9527A', '5408457a', 'd4cc457a', 'E4822646', 'ad803ca4', 'a4973f7a', '348C1D46', '54091F46', '84954D7A', 'b4883b7a', '944F537A', '4424447A', '94CC4C7A', '5461537A', '04a6587a', '0426457a', '347F597A', 'b4fe447a', '64DE2746', '2481517a', '443c3f7a', '04d31946', '34B41D46', '348d527a', '84f6497a'";

        String str = "2018-04-27";
        System.out.println(str.replace("-", "") + (Integer.parseInt("222") + 1));
    }
}
