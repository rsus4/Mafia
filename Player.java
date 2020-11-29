import java.lang.management.PlatformLoggingMXBean;
import java.util.*;

public abstract class Player{
private int HP;
private final int HPLimit=0;
private static int numOfPlayers;
private int playerNo;
private boolean alive;
private static Player lastKilledHeal;
private static Player lastKilledVote;
private static boolean voting;

Scanner input=new Scanner(System.in);

    public static boolean isVoting() {
        return voting;
    }

    public static void setVoting(boolean voting) {
        Player.voting = voting;
    }

    public static Player getLastKilledHeal() {
        return lastKilledHeal;
    }

    public static void setLastKilledHeal(Player lastKilledHeal) {
        Player.lastKilledHeal = lastKilledHeal;
    }

    public static Player getLastKilledVote() {
        return lastKilledVote;
    }

    public static void setLastKilledVote(Player lastKilledVote) {
        Player.lastKilledVote = lastKilledVote;
    }

    public static int getNumOfPlayers() {
        return numOfPlayers;
    }

    public static void setNumOfPlayers(int numOfPlayers) {
        Player.numOfPlayers = numOfPlayers;
    }

    public Player(int HP,int playerNo){
        this.HP=HP;
        this.playerNo=playerNo;
        this.alive=true;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    private static MyGenericList<Player> ListOfPlayers=new MyGenericList<Player>();

    public static MyGenericList<Player> getListOfPlayers() {
        return ListOfPlayers;
    }

    public static void setListOfPlayers(MyGenericList<Player> listOfPlayers) {
        ListOfPlayers = listOfPlayers;
    }

//    public static void addListOfPlayers(MyGenericList<Player> listOfPlayers,int playerNo){
//        Player player=new Player(800,playerNo);
//        ListOfPlayers.add((Detective) player);
//    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHPLimit() {
        return HPLimit;
    }


    public int vote(int userNo){
        Player user=Player.getListOfPlayers().get(userNo);

            boolean done = false;
            int candidate = 0;
            while (!done) {
                System.out.println("Select a person to vote out: ");
                try {
                    candidate = input.nextInt();

                    if(candidate>Player.getNumOfPlayers() || candidate<1){
                        throw new UpperBoundException();
                    }
                    for(int i=0;i<Player.getNumOfPlayers();i++){
                        if(Player.getListOfPlayers().get(i).getPlayerNo()==candidate){
                            if(!Player.getListOfPlayers().get(i).isAlive()){
                                throw new VoteException();
                            }
                        }
                    }
                    done = true;
                }
                catch(UpperBoundException ups){
                    System.out.println("UpperBound Exception");
                }
                catch(VoteException vst){
                    System.out.println("The player you have voted for is dead");
                }
                catch (InputMismatchException inp) {
                    System.out.println("Wrong input:");
                    System.out.println("Try again");
                    input.next();
                }
            }//end input loop for option

            ArrayList<Integer> listOfAlivePlayers = new ArrayList<Integer>();
            //<PlayerNo>
            HashMap<Integer, Integer> mapOfAlivePlayers = new HashMap<Integer, Integer>();
            //     <PlayerNo,Votes>

            for (int i = 0; i < Player.getNumOfPlayers(); i++) {
                if (Player.getListOfPlayers().get(i).isAlive()) {
                    mapOfAlivePlayers.put(Player.getListOfPlayers().get(i).getPlayerNo(), 0);
                    listOfAlivePlayers.add(Player.getListOfPlayers().get(i).getPlayerNo());
                }//end if
            }//end for
            mapOfAlivePlayers.put(candidate, 1); // user vote
            for (int i = 0; i < listOfAlivePlayers.size() - 1; i++) {
                Random rand = new Random();
                int neta = listOfAlivePlayers.get(rand.nextInt(listOfAlivePlayers.size()));
                mapOfAlivePlayers.put(neta, mapOfAlivePlayers.get(neta) + 1);

            }//end for

            int votedOutPlayer=listOfAlivePlayers.get(0);
            int count=mapOfAlivePlayers.get(votedOutPlayer);
            for(int i=0;i<mapOfAlivePlayers.size();i++){
                if(count<mapOfAlivePlayers.get(listOfAlivePlayers.get(i))){ //ERROR OUT OF BOUNDS
                    count=mapOfAlivePlayers.get(listOfAlivePlayers.get(i));
                    votedOutPlayer=listOfAlivePlayers.get(i);
                }
            }
            System.out.println("Player"+votedOutPlayer+" has been voted out.");
            Player.getLastKilledVote().setAlive(false);
            return votedOutPlayer;
    }//end vote


    public int userDoesNotVote(int userNo){
        Player user=Player.getListOfPlayers().get(userNo);


        ArrayList<Integer> listOfAlivePlayers = new ArrayList<Integer>();
        //<PlayerNo>
        HashMap<Integer, Integer> mapOfAlivePlayers = new HashMap<Integer, Integer>();
        //     <PlayerNo,Votes>

        for (int i = 0; i < Player.getNumOfPlayers(); i++) {
            if (Player.getListOfPlayers().get(i).isAlive()) {
                mapOfAlivePlayers.put(Player.getListOfPlayers().get(i).getPlayerNo(), 0);
                listOfAlivePlayers.add(Player.getListOfPlayers().get(i).getPlayerNo());
            }//end if
        }//end for
        for (int i = 0; i < listOfAlivePlayers.size(); i++) {
            Random rand = new Random();
            int neta = listOfAlivePlayers.get(rand.nextInt(listOfAlivePlayers.size()));
            mapOfAlivePlayers.put(neta, mapOfAlivePlayers.get(neta) + 1);

        }//end for

        int votedOutPlayer=listOfAlivePlayers.get(0);
        int count=mapOfAlivePlayers.get(votedOutPlayer);
        for(int i=0;i<mapOfAlivePlayers.size();i++){
            if(count<mapOfAlivePlayers.get(listOfAlivePlayers.get(i))){ //ERROR OUT OF BOUNDS
                count=mapOfAlivePlayers.get(listOfAlivePlayers.get(i));
                votedOutPlayer=listOfAlivePlayers.get(i);
            }
        }
        System.out.println("Player"+votedOutPlayer+" has been voted out.");
        Player.getLastKilledVote().setAlive(false);
        return votedOutPlayer;
    }

//    @Override
//    public int compareTo(Object o) {
//        Player p=(Player)o;
//        if(this.getPlayerNo()>((Player) o).getPlayerNo()){
//            return 1;
//        }
//        else if(this.getPlayerNo()<((Player) o).getPlayerNo()){
//            return -1;
//        }
//        return 0;
//    }




}//end Player class



class Detective extends Player{
    private static int numOfDetectives;
    public Detective(int HP,int playerNo){
        super(HP,playerNo);
    }

    public static int getNumOfDetectives() {
        return numOfDetectives;
    }

    public static void setNumOfDetectives(int numOfDetectives) {
        Detective.numOfDetectives = numOfDetectives;
    }

    private static MyGenericList<Detective> ListOfDetectives = new MyGenericList<Detective>();

    public static MyGenericList<Detective> getListOfDetectives() {
        return ListOfDetectives;
    }

    public void setListOfDetectives(MyGenericList<Detective> listOfDetectives) {
        ListOfDetectives = listOfDetectives;
    }

    public static void addListofDetectives(MyGenericList<Detective> listOfDetectives,int playerNo){
        Player player=new Detective(800,playerNo);
        ListOfDetectives.add((Detective) player);
        Player.getListOfPlayers().add((Detective)player);
    }

    public Player detect(){

        ArrayList <Integer> listd=new ArrayList<Integer>();
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(getClass()!=Player.getListOfPlayers().get(i).getClass() && Player.getListOfPlayers().get(i).isAlive()){
                listd.add(i);
            }
        }//end for (listd has all players indeces of non detectives)

        Random rand=new Random();
        int indexPlayerChosen=listd.get(rand.nextInt(listd.size()));
        Player playerChosen=Player.getListOfPlayers().get(indexPlayerChosen);

        if(playerChosen instanceof Mafia){
            System.out.println("Player"+ playerChosen.getPlayerNo() +" is Mafia");
            Player.setVoting(false);
            return playerChosen;

        }
        else{
            System.out.println("Player"+ playerChosen.getPlayerNo() +" is not Mafia");
            Player.setVoting(true);
            return playerChosen;
        }


    }//end detect



    public Player detectUser(){
        int choosePlayer=0;
        boolean done=false;
        while(!done) {
            System.out.print("Choose a player to test: ");
            try {
                choosePlayer = input.nextInt();

                if(choosePlayer>Player.getNumOfPlayers() || choosePlayer<1){
                        throw new UpperBoundException();
                }

                for(int i=0;i<Detective.getNumOfDetectives();i++){
                    if(Detective.getListOfDetectives().get(i).getPlayerNo()==choosePlayer){
                        throw new DetectiveException();
                    }
                }

                done=true;
            }// end try

            catch(UpperBoundException ups){
                System.out.println("Upper Bound Exception");
            }

            catch(DetectiveException minp){
                System.out.println("Error: YOu cannot choose a detective");
            }// end catch
            catch(InputMismatchException inp){
                System.out.println("Wrong input:");
                System.out.println("Try again");
                input.next();
            }// end catch
        }//end exception while

//        Player playerChosen=Player.getListOfPlayers().get(choosePlayer);
        Player playerChosen=Player.getListOfPlayers().get(0);
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(Player.getListOfPlayers().get(i).getPlayerNo()==choosePlayer){
                playerChosen=Player.getListOfPlayers().get(i);
            }//end if
        }//end for
        if(playerChosen instanceof Mafia){
            System.out.println("Player"+ playerChosen.getPlayerNo() +" is Mafia");
            Player.setVoting(false);
            return playerChosen;

        }
        else{
            System.out.println("Player"+ playerChosen.getPlayerNo() +" is not Mafia");
            Player.setVoting(true);
            return playerChosen;
        }

    }//end detectUser

}//end Detective class

class Healer extends Player{

    private static int numOfHealers;
    public Healer(int HP,int playerNo){
        super(HP,playerNo);
    }

    public static int getNumOfHealers() {
        return numOfHealers;
    }

    public static void setNumOfHealers(int numOfHealers) {
        Healer.numOfHealers = numOfHealers;
    }

    private static MyGenericList<Healer> ListOfHealers = new MyGenericList<Healer>();

    public static MyGenericList<Healer> getListOfHealers() {
        return ListOfHealers;
    }

    public void setListOfHealers(MyGenericList<Healer> listOfHealers) {
        ListOfHealers = listOfHealers;
    }

    public static void addListofHealers(MyGenericList<Healer> listOfHealers,int playerNo){
        Player player=new Healer(800,playerNo);
        ListOfHealers.add((Healer) player);
        Player.getListOfPlayers().add((Healer)player);
    }


    public void healUser(){
        int choosePlayer=0;
        boolean done=false;
        while(!done) {
            System.out.print("Choose a player to heal: ");
            try {
                choosePlayer = input.nextInt();
                if(choosePlayer>Player.getNumOfPlayers() || choosePlayer<1){
                    throw new UpperBoundException();
                }
                done=true;
            }// end try
            catch(UpperBoundException ups){
                System.out.println("Upper Bound Exception");
            }
            catch(InputMismatchException inp){
                System.out.println("Wrong input:");
                System.out.println("Try again");
                input.next();
            }// end catch
        }//end exception while

        Player playerChosen=Player.getListOfPlayers().get(0);
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(Player.getListOfPlayers().get(i).getPlayerNo()==choosePlayer){
                playerChosen=Player.getListOfPlayers().get(i);
            }//end if
        }//end for

        if(playerChosen.equals(Player.getLastKilledHeal())){
            playerChosen.setHP(playerChosen.getHP()+500);
//            System.out.println("Healer chose Player"+playerChosen.getPlayerNo());
        }
        else{
            playerChosen.setHP(playerChosen.getHP()+500);
            Player.getLastKilledHeal().setAlive(false);
//            System.out.println("Healer chose Player"+playerChosen.getPlayerNo());
        }

    }//end healUser


    public void heal() {


        ArrayList <Integer> listh=new ArrayList<Integer>(); // index of all players that are not detective and are alive in the ListOfPlayersList
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(Player.getListOfPlayers().get(i).isAlive()){
                listh.add(i);
            }
        }//end for (listh has all players indeces of alive)

        Random rand=new Random();
        int indexPlayerChosen=listh.get(rand.nextInt(listh.size()));
        Player playerChosen=Player.getListOfPlayers().get(indexPlayerChosen);
        if(playerChosen.equals(Player.getLastKilledHeal())){
            playerChosen.setHP(playerChosen.getHP()+500);
//            System.out.println("Healer chose Player"+playerChosen.getPlayerNo());
        }
        else{
            playerChosen.setHP(playerChosen.getHP()+500);
            Player.getLastKilledHeal().setAlive(false);
//            System.out.println("Healer chose Player"+playerChosen.getPlayerNo());
        }

        //if player healed not killed by mafia
//        playerChosen.setAlive(false);

    }//end heal function
}//end Healer Class


class Commoner extends Player{

    public static int numOfCommoners;

    public Commoner(int HP,int playerNo){
        super(HP,playerNo);
    }

    public int getNumOfCommoners() {
        return numOfCommoners;
    }

    public void setNumOfCommoners(int numOfCommoners) {
        Commoner.numOfCommoners = numOfCommoners;
    }
   private static MyGenericList<Commoner> ListOfCommoners = new MyGenericList<Commoner>();

    public static MyGenericList<Commoner> getListOfCommoners() {
        return ListOfCommoners;
    }

    public void setListOfCommoners(MyGenericList<Commoner> listOfCommoners) {
        ListOfCommoners = listOfCommoners;
    }

    public static void addListofCommoners(MyGenericList<Commoner> listOfCommoners,int playerNo){
        Player player=new Commoner(1000,playerNo);
        ListOfCommoners.add((Commoner) player);
        Player.getListOfPlayers().add((Commoner)player);
    }


}//end Commoner



class Mafia extends Player{

    public Mafia(int HP,int playerNo){
        super(HP,playerNo);
    }

    private static int numOfMafias;

    public static int getNumOfMafias() {
        return numOfMafias;
    }

    public static void setNumOfMafias(int numOfMafias) {
        Mafia.numOfMafias = numOfMafias;
    }

   private static MyGenericList<Mafia> ListOfMafias = new MyGenericList<Mafia>();

    public static MyGenericList<Mafia> getListOfMafias() {
        return ListOfMafias;
    }

    public static void setListOfMafias(MyGenericList<Mafia> listOfMafias) {
        ListOfMafias = listOfMafias;
    }

    public static void addListofMafias(MyGenericList<Mafia> listOfMafias,int playerNo){
        Player player=new Mafia(2500,playerNo);
        ListOfMafias.add((Mafia) player);
        Player.getListOfPlayers().add((Mafia)player);
    }


    public void killUser(){

        int choosePlayer=0;
        boolean done=false;
        while(!done) {
            System.out.print("Choose a target: ");
            try {
                choosePlayer = input.nextInt();

                if(choosePlayer>Player.getNumOfPlayers() || choosePlayer<1){
                    throw new UpperBoundException();
                }
                for(int i=0;i<Mafia.getNumOfMafias();i++){
                    if(Mafia.getListOfMafias().get(i).getPlayerNo()==choosePlayer){
                        throw new MafiaException();
                    }
                }

                for(int i=0;i<Player.getNumOfPlayers();i++){
                    if(Player.getListOfPlayers().get(i).getPlayerNo()==choosePlayer){
                        if(!Player.getListOfPlayers().get(i).isAlive()){
                            throw new VoteException();
                        }
                    }
                }
                done=true;
            }// end tryups
            catch(UpperBoundException ups){
                System.out.println("Upper Bound Exception");
            }
            catch(VoteException vst){
                System.out.println("Player already dead");
            }
            catch(MafiaException minp){
                System.out.println("Error: You cannot target a mafia");
            }// end catch
            catch(InputMismatchException inp){
                System.out.println("Wrong input:");
                System.out.println("Try again");
                input.next();
            }// end catch
        }//end exception while

        Player playerChosen=Player.getListOfPlayers().get(0);
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(Player.getListOfPlayers().get(i).getPlayerNo()==choosePlayer){
                playerChosen=Player.getListOfPlayers().get(i);
            }//end if
        }//end for


        int combinedMafiaHP=0;
        int mafiaCount=0;
        int targetHP= playerChosen.getHP();
        for(int i=0;i<Mafia.getNumOfMafias();i++){
            if(Mafia.getListOfMafias().get(i).isAlive() && Mafia.getListOfMafias().get(i).getHP()>0){
                mafiaCount+=1;
                combinedMafiaHP+=Mafia.getListOfMafias().get(i).getHP();
//                System.out.println("MAfia is Player "+Mafia.getListOfMafias().get(i).getPlayerNo());
            }
        }//end for
//        System.out.println("Mafia count" +mafiaCount);
        float ratio=targetHP/mafiaCount;
        int copy=targetHP;
        int minMafiaHP=Mafia.getListOfMafias().get(0).getHP();
        int minMafiaindex=0;
        while(copy>0){
            if(mafiaCount>1) {
                for (int i = 1; i < mafiaCount; i++) {
                    int tempHP = Mafia.getListOfMafias().get(i).getHP();
                    if (tempHP < minMafiaHP) {
                        minMafiaHP = tempHP;
                        minMafiaindex = i;
                    }//end if
                }//end minimumelement for
            }//end conditional if for for loop
//            System.out.println("HIII6");
            if(ratio<minMafiaHP) {
                for(int i=0;i< mafiaCount;i++){
//                    System.out.println("HIII");
                    Mafia.getListOfMafias().get(i).setHP(Mafia.getListOfMafias().get(i).getHP()-(int)ratio);
                }//end setter for
                copy=0;
            }//end easy vaala if
            else{
                for(int i=0;i< mafiaCount;i++){
                    Mafia.getListOfMafias().get(i).setHP(Mafia.getListOfMafias().get(i).getHP()-minMafiaHP);
                }//end setter for
                copy=copy-minMafiaHP* mafiaCount;
//                System.out.println("Copy= "+copy);
//                list1.remove(minMafiaindex);
            }
        }//end while (Mafias have 0 HP's, plaer HP)
//        System.out.println("Mafia wants to kill Player"+playerChosen.getPlayerNo());
        playerChosen.setHP(0);
        Player.setLastKilledHeal(playerChosen);
        Player.setLastKilledVote(playerChosen);



    }//end KillUser

    public void kill(){
        ArrayList <Integer> list1=new ArrayList<Integer>(); // index of all players that are not mafia and are alive in the ListOfPlayersList
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(getClass()!=Player.getListOfPlayers().get(i).getClass() && Player.getListOfPlayers().get(i).isAlive()){
                list1.add(i);
//                System.out.println("HII");
            }
        }

//        for(int i=0;i< list1.size();i++){
//            System.out.println(Player.getListOfPlayers().get(list1.get(i)));
//        }//No Mafia should be printed here


        //Randomly chose a player to be killed
        Random rand=new Random();
        int indexPlayerChosen=list1.get(rand.nextInt(list1.size()));
        Player playerChosen=Player.getListOfPlayers().get(indexPlayerChosen);


        int combinedMafiaHP=0;
        int mafiaCount=0;
        int targetHP= playerChosen.getHP();
        for(int i=0;i<Mafia.getNumOfMafias();i++){
            if(Mafia.getListOfMafias().get(i).isAlive() && Mafia.getListOfMafias().get(i).getHP()>0){
                mafiaCount+=1;
                combinedMafiaHP+=Mafia.getListOfMafias().get(i).getHP();
//                System.out.println("MAfia is Player "+Mafia.getListOfMafias().get(i).getPlayerNo());
            }

        }//end for
//        System.out.println("Mafia count" +mafiaCount);
        if(mafiaCount!=0){
            float ratio=targetHP/mafiaCount;
            int copy=targetHP;
            int minMafiaHP=Mafia.getListOfMafias().get(0).getHP();
            int minMafiaindex=0;
            while(copy>0){
                if(mafiaCount>1) {
                    for (int i = 1; i < mafiaCount; i++) {
                        int tempHP = Mafia.getListOfMafias().get(i).getHP();
                        if (tempHP < minMafiaHP) {
                            minMafiaHP = tempHP;
                            minMafiaindex = i;
                        }//end if
                    }//end minimumelement for
                }//end conditional if for for loop
//            System.out.println("HIII6");
                if(ratio<minMafiaHP) {
                    for(int i=0;i< mafiaCount;i++){
//                    System.out.println("HIII");
                        Mafia.getListOfMafias().get(i).setHP(Mafia.getListOfMafias().get(i).getHP()-(int)ratio);
                    }//end setter for
                    copy=0;
                }//end easy vaala if
                else{
                    for(int i=0;i< mafiaCount;i++){
                        Mafia.getListOfMafias().get(i).setHP(Mafia.getListOfMafias().get(i).getHP()-minMafiaHP);
                    }//end setter for
                    copy=copy-minMafiaHP* mafiaCount;
//                System.out.println("Copy= "+copy);
//                list1.remove(minMafiaindex);
                }
            }//end while (Mafias have 0 HP's, plaer HP)
//            System.out.println("Mafia wants to kill Player"+playerChosen.getPlayerNo());
            playerChosen.setHP(0);
            Player.setLastKilledHeal(playerChosen);
            Player.setLastKilledVote(playerChosen);

        }


//  playerChosen.setAlive(false);
    }//end kill


}//end Mafia Class

//    public static int getRandomElement(List<Integer> list)
//    {
//        Random rand = new Random();
//        return list.get(rand.nextInt(list.size()));
//    }



class MyGenericList<T>{
    private ArrayList <T> Person;
    public MyGenericList(){
        Person=new ArrayList<T>();
    }
    public void add(T o){
        Person.add(o);
    }
    public T get(int i){
        return Person.get(i);
    }
}//end MyGenerator class






//1) fix copy function
//2)fix vote if person dies himself
//3)fix detective if person dies himself
//4)fix heal person dies himself
//5)fix vote in general
//6)fix kill if person dies
//6)Can't kill dead players exception
