import java.util.ArrayList;
import java.util.*;
public class Main {
    public static void main(String[] args){
        Scanner input=new Scanner(System.in);
        Game mafia=new Game();
        mafia.introScreen();
        int userNo= mafia.charSelect();
//        System.out.println("User 69 is "+userNo);
        mafia.play(userNo);
    }
}
