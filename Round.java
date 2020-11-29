import java.lang.management.PlatformLoggingMXBean;
import java.util.*;

public class Round {
private static boolean voting;
private int round_no;
private static int round_counter;

    {
        round_no=++round_counter;
    }

public Round(boolean voting){
    this.voting=true;
}

    public boolean isVoting() {
        return voting;
    }

    public void setVoting(boolean voting) {
        this.voting = voting;
    }

    public static void start(int userNo){
        Round r1=new Round(true);
        System.out.println("Round "+ r1.round_no+":");
        int numberOfAlive=0;
        for(int i=0;i<Player.getNumOfPlayers();i++)
        {
            if(Player.getListOfPlayers().get(i).isAlive()){
                numberOfAlive+=1;
            }
        }
//        for(int i=0;i<Player.getNumOfPlayers();i++){
//            System.out.println(Player.getListOfPlayers().get(i).getPlayerNo()+ ""+Player.getListOfPlayers().get(i).isAlive());
//        }

        System.out.print(numberOfAlive+" players are remaining: " );

        List<PrintPlayer> myPrintList=new ArrayList<>();

        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(Player.getListOfPlayers().get(i).isAlive()){
                myPrintList.add(new PrintPlayer(Player.getListOfPlayers().get(i).getPlayerNo(),"HI"));
            }//end if
        }//end for

        Collections.sort(myPrintList);
        for(int i=0;i<myPrintList.size();i++){
            System.out.print("PLayer"+myPrintList.get(i).getpNo()+", ");
        }//end for

//                                          PRINT WITHOUT SORT
//        for(int i=0;i<Player.getNumOfPlayers();i++){
//            if(Player.getListOfPlayers().get(i).isAlive()){
//                System.out.print("PLayer"+Player.getListOfPlayers().get(i).getPlayerNo()+", ");
//            }//end if
//        }//end for

    System.out.println("are alive.");

        String userType=Game.roles.get(userNo);
        Player user=Player.getListOfPlayers().get(0);
        boolean isUserAlive=user.isAlive();
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(Player.getListOfPlayers().get(i).getPlayerNo()==userNo){
                user=Player.getListOfPlayers().get(i);
                isUserAlive=user.isAlive();
            }
        }



        if(userType.equals("Mafia")){
            boolean allMafiasDead=true;
            for(int i=0;i<Mafia.getNumOfMafias();i++){
                if(Mafia.getListOfMafias().get(i).isAlive()){
                    allMafiasDead=false;
                }
            }

            boolean allDetectivesDead=true;
            for(int i=0;i<Detective.getNumOfDetectives();i++){
                if(Detective.getListOfDetectives().get(i).isAlive()){
                    allDetectivesDead=false;
                }
            }

            boolean allHealersDead=true;
            for(int i=0;i<Healer.getNumOfHealers();i++){
                if(Healer.getListOfHealers().get(i).isAlive()){
                    allHealersDead=false;
                }
            }



            if(isUserAlive){
                if(!allMafiasDead) {
                    Mafia mf = new Mafia(-500, -1);//object to call kill method
                    for (int i = 0; i < Mafia.getNumOfMafias(); i++) {
                        if (Mafia.getListOfMafias().get(i).isAlive()) {
                            mf = Mafia.getListOfMafias().get(i);
                            break;
                        }
                    }
                    mf.killUser();
                }

            }

            else{
                if(!allMafiasDead){
                    Mafia mf = new Mafia(-500, -1);//object to call kill method
                    for (int i = 0; i < Mafia.getNumOfMafias(); i++) {
                        if (Mafia.getListOfMafias().get(i).isAlive()) {
                            mf = Mafia.getListOfMafias().get(i);
                            break;
                        }
                    }
                    mf.kill();

                }
                else{
                    System.out.println("Shouldn't come here idk, endgame condition met");
                }

            }






            Player p1=Player.getLastKilledVote();
            if(!allDetectivesDead){
                Detective dt=new Detective(-500,-1);

                for(int i=0;i<Detective.getNumOfDetectives();i++){
                    if(Detective.getListOfDetectives().get(i).isAlive()){
                        dt=Detective.getListOfDetectives().get(i);
                        break;
                    }//end if
                }//end for
                p1=dt.detect();
            }

            System.out.println("Detectives have chosen a player to test.");

            if(!allHealersDead){
                Healer hl=new Healer(-500,-1);

                for(int i=0;i<Healer.getNumOfHealers();i++){
                    if(Healer.getListOfHealers().get(i).isAlive()){
                        hl=Healer.getListOfHealers().get(i);
                    }//end if
                }//end for

                hl.heal();
            }
            else{
                Player.getLastKilledHeal().setAlive(false);
            }

            System.out.println("Healers have chosen someone to heal.");
            System.out.println("--End of actions--");
            isUserAlive=user.isAlive();
            if(Player.getLastKilledHeal().isAlive()){
                System.out.println("No one died.");
            }//Healer healed Killed person hence no one died
            else{
                System.out.println("Player"+Player.getLastKilledHeal().getPlayerNo()+" has died.");
            }

            if(Player.isVoting()){
//                System.out.println(69);
                Healer hel= new Healer(-500,-2);
                int votedOutPlayer=0;
                if(isUserAlive){
                    votedOutPlayer=hel.vote(userNo);
                }
                else{
                    votedOutPlayer=hel.userDoesNotVote(userNo);
                }

                for(int i=0;i<Player.getNumOfPlayers();i++){
                    if(Player.getListOfPlayers().get(i).getPlayerNo()==votedOutPlayer){
                        Player.getListOfPlayers().get(i).setAlive(false);
                    }
                }

            }// Voting if Mafia was not selected by Detective ended
            else{
                if(!allDetectivesDead) {
                    System.out.println("Player " + p1.getPlayerNo() + " is voted out.");
                    for (int i = 0; i < Player.getNumOfPlayers(); i++) {
                        if (Player.getListOfPlayers().get(i).getPlayerNo() == p1.getPlayerNo()) {
                            Player.getListOfPlayers().get(i).setAlive(false);
                        }
                    }
                }
                else{
                    System.out.println("Pls go away");
                }
            }//No voting since Detective chose Mafia

//
//            for(int i=0;i<Player.getNumOfPlayers();i++){
//                if(!Player.getListOfPlayers().get(i).isAlive()){
//                    System.out.println("Dead players "+ Player.getListOfPlayers().get(i).getPlayerNo());
//                }
//            }

            System.out.println("--End of Round "+r1.round_no+"--");



        }//Mafia clause




        else if(userType.equals("Detective")){

            boolean allMafiasDead=true;
            for(int i=0;i<Mafia.getNumOfMafias();i++){
                if(Mafia.getListOfMafias().get(i).isAlive()){
                    allMafiasDead=false;
                }
            }

            boolean allDetectivesDead=true;
            for(int i=0;i<Detective.getNumOfDetectives();i++){
                if(Detective.getListOfDetectives().get(i).isAlive()){
                    allDetectivesDead=false;
                }
            }

            boolean allHealersDead=true;
            for(int i=0;i<Healer.getNumOfHealers();i++){
                if(Healer.getListOfHealers().get(i).isAlive()){
                    allHealersDead=false;
                }
            }

            if(!allMafiasDead) {
                Mafia mf = new Mafia(-500, -1);//object to call kill method
                for (int i = 0; i < Mafia.getNumOfMafias(); i++) {
                    if (Mafia.getListOfMafias().get(i).isAlive()) {
                        mf = Mafia.getListOfMafias().get(i);
                        break;
                    }
                }
                mf.kill();
            }
            System.out.println("Mafias have chosen their target");


            Player p1=Player.getLastKilledVote();

            if(isUserAlive){
                if(!allDetectivesDead){
                    Detective dt=new Detective(-500,-1);

                    for(int i=0;i<Detective.getNumOfDetectives();i++){
                        if(Detective.getListOfDetectives().get(i).isAlive()){
                            dt=Detective.getListOfDetectives().get(i);
                            break;
                        }//end if
                    }//end for
                    p1=dt.detectUser();
                }

            }
            else{
                if(!allDetectivesDead){
                    Detective dt=new Detective(-500,-1);

                    for(int i=0;i<Detective.getNumOfDetectives();i++){
                        if(Detective.getListOfDetectives().get(i).isAlive()){
                            dt=Detective.getListOfDetectives().get(i);
                            break;
                        }//end if
                    }//end for
                    p1=dt.detect();
                }
                else{
                    System.out.println("Testing, all detectives dead");
                }
            }

//            System.out.println("Detectives have chosen a player to test.");

            if(!allHealersDead){
                Healer hl=new Healer(-500,-1);

                for(int i=0;i<Healer.getNumOfHealers();i++){
                    if(Healer.getListOfHealers().get(i).isAlive()){
                        hl=Healer.getListOfHealers().get(i);
                    }//end if
                }//end for

                hl.heal();
            }
            else{
                Player.getLastKilledHeal().setAlive(false);
            }

            System.out.println("Healers have chosen someone to heal.");
            System.out.println("--End of actions--");
            isUserAlive=user.isAlive();
            if(Player.getLastKilledHeal().isAlive()){
                System.out.println("No one died.");
            }//Healer healed Killed person hence no one died
            else{
                System.out.println("Player"+Player.getLastKilledHeal().getPlayerNo()+" has died.");
            }

            if(Player.isVoting()){
//                System.out.println(69);
                Healer hel= new Healer(-500,-2);
                int votedOutPlayer=0;
                if(isUserAlive){
                    votedOutPlayer=hel.vote(userNo);
                }
                else{
                    votedOutPlayer=hel.userDoesNotVote(userNo);
                }
                for(int i=0;i<Player.getNumOfPlayers();i++){
                    if(Player.getListOfPlayers().get(i).getPlayerNo()==votedOutPlayer){
                        Player.getListOfPlayers().get(i).setAlive(false);
                    }
                }

            }// Voting if Mafia was not selected by Detective ended
            else{
                if(!allDetectivesDead) {
                    System.out.println("Player " + p1.getPlayerNo() + " is voted out.");
                    for (int i = 0; i < Player.getNumOfPlayers(); i++) {
                        if (Player.getListOfPlayers().get(i).getPlayerNo() == p1.getPlayerNo()) {
                            Player.getListOfPlayers().get(i).setAlive(false);
                        }
                    }
                }
                else{
                    System.out.println("Please don't come here I'll kill you");
                }
            }//No voting since Detective chose Mafia

//
//            for(int i=0;i<Player.getNumOfPlayers();i++){
//                if(!Player.getListOfPlayers().get(i).isAlive()){
//                    System.out.println("Dead players "+ Player.getListOfPlayers().get(i).getPlayerNo());
//                }
//            }

            System.out.println("--End of Round "+r1.round_no+"--");



        }//end User Detective clause



        else if(userType.equals("Healer")){

            boolean allMafiasDead=true;
            for(int i=0;i<Mafia.getNumOfMafias();i++){
                if(Mafia.getListOfMafias().get(i).isAlive()){
                    allMafiasDead=false;
                }
            }

            boolean allDetectivesDead=true;
            for(int i=0;i<Detective.getNumOfDetectives();i++){
                if(Detective.getListOfDetectives().get(i).isAlive()){
                    allDetectivesDead=false;
                }
            }

            boolean allHealersDead=true;
            for(int i=0;i<Healer.getNumOfHealers();i++){
                if(Healer.getListOfHealers().get(i).isAlive()){
                    allHealersDead=false;
                }
            }

            if(!allMafiasDead) {
                Mafia mf = new Mafia(-500, -1);//object to call kill method
                for (int i = 0; i < Mafia.getNumOfMafias(); i++) {
                    if (Mafia.getListOfMafias().get(i).isAlive()) {
                        mf = Mafia.getListOfMafias().get(i);
                        break;
                    }
                }
                mf.kill();
            }
            System.out.println("Mafias have chosen their target");
            Player p1=Player.getLastKilledVote();

            if(!allDetectivesDead){
                Detective dt=new Detective(-500,-1);

                for(int i=0;i<Detective.getNumOfDetectives();i++){
                    if(Detective.getListOfDetectives().get(i).isAlive()){
                        dt=Detective.getListOfDetectives().get(i);
                        break;
                    }//end if
                }//end for
                p1=dt.detect();
            }
            System.out.println("Detectives have chosen a player to test.");

            if(isUserAlive) {
                if (!allHealersDead) {
                    Healer hl = new Healer(-500, -1);
                    for (int i = 0; i < Healer.getNumOfHealers(); i++) {
                        if (Healer.getListOfHealers().get(i).isAlive()) {
                            hl = Healer.getListOfHealers().get(i);
                        }//end if
                    }//end for

                    hl.healUser();
                }//all healers vaala if
                else {
                    Player.getLastKilledHeal().setAlive(false);
                }//all healers vaala else
            }//if UserAlive heal

            else{
                if (!allHealersDead) {
                    Healer hl = new Healer(-500, -1);
                    for (int i = 0; i < Healer.getNumOfHealers(); i++) {
                        if (Healer.getListOfHealers().get(i).isAlive()) {
                            hl = Healer.getListOfHealers().get(i);
                        }//end if
                    }//end for

                    hl.heal();
                }//all healers vaala if
                else {
                    Player.getLastKilledHeal().setAlive(false);
                }//all healers vaala else
            }

            System.out.println("Healers have chosen someone to heal.");
            System.out.println("--End of actions--");
            isUserAlive=user.isAlive();
            if(Player.getLastKilledHeal().isAlive()){
                System.out.println("No one died.");
            }//Healer healed Killed person hence no one died
            else{
                System.out.println("Player"+Player.getLastKilledHeal().getPlayerNo()+" has died.");
            }

            if(Player.isVoting()){
//                System.out.println(69);
                Healer hel= new Healer(-500,-2);
                int votedOutPlayer=0;
                if(isUserAlive){
                    votedOutPlayer=hel.vote(userNo);
                }
                else{
                    votedOutPlayer=hel.userDoesNotVote(userNo);
                }
                for(int i=0;i<Player.getNumOfPlayers();i++){
                    if(Player.getListOfPlayers().get(i).getPlayerNo()==votedOutPlayer){
                        Player.getListOfPlayers().get(i).setAlive(false);
                    }
                }

            }// Voting if Mafia was not selected by Detective ended
            else{
                if(!allDetectivesDead) {
                    System.out.println("Player " + p1.getPlayerNo() + " is voted out.");
                    for (int i = 0; i < Player.getNumOfPlayers(); i++) {
                        if (Player.getListOfPlayers().get(i).getPlayerNo() == p1.getPlayerNo()) {
                            Player.getListOfPlayers().get(i).setAlive(false);
                        }
                    }
                }
                else{
                    System.out.println("You shouldn't be here ideally.");
                }
            }//No voting since Detective chose Mafia

//
//            for(int i=0;i<Player.getNumOfPlayers();i++){
//                if(!Player.getListOfPlayers().get(i).isAlive()){
//                    System.out.println("Dead players "+ Player.getListOfPlayers().get(i).getPlayerNo());
//                }
//            }

            System.out.println("--End of Round "+r1.round_no+"--");


        }//end User Healer Clause




        else{

            boolean allMafiasDead=true;
            for(int i=0;i<Mafia.getNumOfMafias();i++){
                if(Mafia.getListOfMafias().get(i).isAlive()){
                    allMafiasDead=false;
                }
            }

            boolean allDetectivesDead=true;
            for(int i=0;i<Detective.getNumOfDetectives();i++){
                if(Detective.getListOfDetectives().get(i).isAlive()){
                    allDetectivesDead=false;
                }
            }

            boolean allHealersDead=true;
            for(int i=0;i<Healer.getNumOfHealers();i++){
                if(Healer.getListOfHealers().get(i).isAlive()){
                    allHealersDead=false;
                }
            }

        if(!allMafiasDead){
            Mafia mf=new Mafia(-500,-1);//object to call kill method
            for(int i=0;i<Mafia.getNumOfMafias();i++){
                if(Mafia.getListOfMafias().get(i).isAlive()){
                    mf=Mafia.getListOfMafias().get(i);
                    break;
                }
            }
            mf.kill();
        }

            System.out.println("Mafias have chosen their target");
        Player p1=Player.getLastKilledVote();
        if(!allDetectivesDead){
            Detective dt=new Detective(-500,-1);

            for(int i=0;i<Detective.getNumOfDetectives();i++){
                if(Detective.getListOfDetectives().get(i).isAlive()){
                    dt=Detective.getListOfDetectives().get(i);
                    break;
                }//end if
            }//end for
            p1=dt.detect();
        }

            System.out.println("Detectives have chosen a player to test.");


        if(!allHealersDead){
            Healer hl=new Healer(-500,-1);

            for(int i=0;i<Healer.getNumOfHealers();i++){
                if(Healer.getListOfHealers().get(i).isAlive()){
                    hl=Healer.getListOfHealers().get(i);
                }//end if
            }//end for

            hl.heal();
        }
        else{
            Player.getLastKilledHeal().setAlive(false);
        }

            System.out.println("Healers have chosen someone to heal.");
            System.out.println("--End of actions--");

            isUserAlive=user.isAlive();
//            System.out.println("IS user alive "+isUserAlive+" Player"+user.getPlayerNo());
            if(Player.getLastKilledHeal().isAlive()){
                System.out.println("No one died.");
            }//Healer healed Killed person hence no one died
            else{
                System.out.println("Player"+Player.getLastKilledHeal().getPlayerNo()+" has died.");
                }


            if(Player.isVoting()){
//                System.out.println(69
                Healer hel= new Healer(-500,-2);
                int votedOutPlayer=0;
                if(isUserAlive){
                    votedOutPlayer=hel.vote(userNo);
                }
                else{
                    votedOutPlayer=hel.userDoesNotVote(userNo);
                }

                    for(int i=0;i<Player.getNumOfPlayers();i++){
                    if(Player.getListOfPlayers().get(i).getPlayerNo()==votedOutPlayer){
                        Player.getListOfPlayers().get(i).setAlive(false);
                    }
                }

            }// Voting if Mafia was not selected by Detective ended
            else{
                if(!allDetectivesDead) {
                    System.out.println("Player " + p1.getPlayerNo() + " is voted out.");
                    for (int i = 0; i < Player.getNumOfPlayers(); i++) {
                        if (Player.getListOfPlayers().get(i).getPlayerNo() == p1.getPlayerNo()) {
                            Player.getListOfPlayers().get(i).setAlive(false);
                        }
                    }
                }
                else{
                    System.out.println("You shouldn't ideally be here.");
                }
            }//No voting since Detective chose Mafia



//
//            for(int i=0;i<Player.getNumOfPlayers();i++){
//                if(!Player.getListOfPlayers().get(i).isAlive()){
//                    System.out.println("Dead players "+ Player.getListOfPlayers().get(i).getPlayerNo());
//                }
//            }

            System.out.println("--End of Round "+r1.round_no+"--");

        }//end if commoner clause




}



}
