/**
 * Created by kai-w on 17/02/17.
 */
public class GeneratePassword {

    public static void main(String[] args) {
        System.out.println(new Creator(args[0], args[1]).create());
    }
}
