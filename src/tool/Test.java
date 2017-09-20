package tool;

/**
 * Created by xwy_brh on 2017/9/12.
 */
public class Test {

    public static void main(String[] args) {

        try {
            System.out.println("hello, try");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("hello, finally");
        }
    }
}
