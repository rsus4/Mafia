import javax.swing.*;
import java.util.*;
import java.lang.Math;
public class Game {

    Scanner input=new Scanner(System.in);
    private int userNo;

    public int getUserNo() {
        return userNo;
    }


    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public static HashMap<Integer,String> roles=new HashMap<Integer, String>();

    public boolean endOfGame(){
        boolean allMafiaDeadFlag=true;
        int mafiaCount=0;
        int otherCount=0;
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(Player.getListOfPlayers().get(i) instanceof Mafia){
                if(Player.getListOfPlayers().get(i).isAlive()){
                    allMafiaDeadFlag=false;
                }
            }
        }
        for(int i=0;i<Player.getNumOfPlayers();i++){
            if(Player.getListOfPlayers().get(i).isAlive()){
                if(Player.getListOfPlayers().get(i) instanceof Mafia){
                    mafiaCount+=1;
                }
                else{
                    otherCount+=1;
                }
            }
        }
        if(mafiaCount==otherCount){
            System.out.println("The Mafias have won");

            for(int i=0;i<Mafia.getNumOfMafias();i++){
                if(i!=Mafia.getNumOfMafias()-1) {
                    System.out.print("Player" + Mafia.getListOfMafias().get(i).getPlayerNo()+" and ");
                }
                else{
                    System.out.println("Player"+Mafia.getListOfMafias().get(i).getPlayerNo()+" were mafia.");
                }
            }//end for Mafia

            for(int i=0;i<Detective.getNumOfDetectives();i++){
                if(i!=Detective.getNumOfDetectives()-1) {
                    System.out.print("Player" + Detective.getListOfDetectives().get(i).getPlayerNo()+" and ");
                }
                else{
                    System.out.println("Player"+Detective.getListOfDetectives().get(i).getPlayerNo()+" were detectives.");
                }
            }//end for Detectives

            for(int i=0;i<Healer.getNumOfHealers();i++){
                if(i!=Healer.getNumOfHealers()-1) {
                    System.out.print("Player" + Healer.getListOfHealers().get(i).getPlayerNo()+" and ");
                }
                else{
                    System.out.println("Player"+ Healer.getListOfHealers().get(i).getPlayerNo()+" were healers.");
                }
            }//end for Healers


            for(int i=0;i<Commoner.numOfCommoners;i++){
                if(i!=Commoner.numOfCommoners-1) {
                    System.out.print("Player" + Commoner.getListOfCommoners().get(i).getPlayerNo()+" and ");
                }
                else{
                    System.out.println("Player"+ Commoner.getListOfCommoners().get(i).getPlayerNo()+" were Commoners.");
                }
            }//end for Commoners

            return true;
        }// Mafia have won flag

        if(allMafiaDeadFlag){
            System.out.println("The Mafias have lost");

            for(int i=0;i<Mafia.getNumOfMafias();i++){
                if(i!=Mafia.getNumOfMafias()-1) {
                    System.out.print("Player" + Mafia.getListOfMafias().get(i).getPlayerNo()+" and ");
                }
                else{
                    System.out.println("Player"+Mafia.getListOfMafias().get(i).getPlayerNo()+" were mafia.");
                }
            }//end for Mafia

            for(int i=0;i<Detective.getNumOfDetectives();i++){
                if(i!=Detective.getNumOfDetectives()-1) {
                    System.out.print("Player" + Detective.getListOfDetectives().get(i).getPlayerNo()+" and ");
                }
                else{
                    System.out.println("Player"+Detective.getListOfDetectives().get(i).getPlayerNo()+" were detectives.");
                }
            }//end for Detectives

            for(int i=0;i<Healer.getNumOfHealers();i++){
                if(i!=Healer.getNumOfHealers()-1) {
                    System.out.print("Player" + Healer.getListOfHealers().get(i).getPlayerNo()+" and ");
                }
                else{
                    System.out.println("Player"+ Healer.getListOfHealers().get(i).getPlayerNo()+" were healers.");
                }
            }//end for Healers


            for(int i=0;i<Commoner.numOfCommoners;i++){
                if(i!=Commoner.numOfCommoners-1) {
                    System.out.print("Player" + Commoner.getListOfCommoners().get(i).getPlayerNo()+" and ");
                }
                else{
                    System.out.println("Player"+ Commoner.getListOfCommoners().get(i).getPlayerNo()+" were Commoners.");
                }
            }//end for Commoners
            return true;
        }//Mafias have lost flag
        return false;
    }// end endOfGame

    public void introScreen(){
        System.out.println("Welcome to Mafia");
        boolean done=false;
        int numberOfPlayers=0;
        while(!done) {
            System.out.print("Enter Number of players: ");
            try {
                numberOfPlayers = input.nextInt();
                Player.setNumOfPlayers(numberOfPlayers);
//                done=true;
                if(numberOfPlayers<6){
                    throw new MinimumPlayersException();
                }
                done=true;
            }//end try

            catch(MinimumPlayersException minp){
                System.out.println("Error: 6 Players required");
            }
            catch(InputMismatchException inp){
                System.out.println("Wrong input:");
                System.out.println("Try again");
                input.next();
            }

        }//end while
        assignPlayers(numberOfPlayers);
        System.out.println("Choose a Character");
        System.out.println("1)Mafia");
        System.out.println("2)Detective");
        System.out.println("3)Healer");
        System.out.println("4)Commoner");
        System.out.println("5)Assign Randomly");
    }//end introScreen


    public int charSelect(){
        boolean done=false;
        int choose=0;
        while(!done){
            try {
                choose = input.nextInt();
                if(choose>5 || choose<1){
                    throw new OptionDoesNotExist();
                }
                done=true;
            }
            catch(OptionDoesNotExist minp){
                System.out.println("Error: Invalid option");
            }
            catch(InputMismatchException inp){
                System.out.println("Wrong input:");
                System.out.println("Try again");
                input.next();
            }
        }//end input loop for option

        User myPlayer;
        if(choose==1){
            for(int i=1;i<roles.size()+1;i++){
                if(roles.get(i).equals("Mafia")){
                    userNo=i;
                    break;
                }
            }
            myPlayer=new User("Mafia",userNo);

        }
        else if(choose==2){

            for(int i=1;i<roles.size()+1;i++){
                if(roles.get(i).equals("Detective")){
                    userNo=i;
                    break;
                }
            }
            myPlayer=new User("Detective",userNo);

        }
        else if(choose==3){

            for(int i=1;i<roles.size()+1;i++){
                if(roles.get(i).equals("Healer")){
                    userNo=i;
                    break;
                }
            }
            myPlayer=new User("Healer",userNo);

        }
        else if(choose==4){

            for(int i=1;i<roles.size()+1;i++){
                if(roles.get(i).equals("Commoner")){
                    userNo=i;
                    break;
                }
            }
            myPlayer=new User("Commoner",userNo);

        }
        else{
            int a=genRandom();
            a=a+1;//indexing

            if(a==1){

                for(int i=1;i<roles.size()+1;i++){
                    if(roles.get(i).equals("Mafia")){
                        userNo=i;
                        break;
                    }
                }
                myPlayer=new User("Mafia",userNo);



            }//end a==1
            else if(a==2){

                for(int i=1;i<roles.size()+1;i++){
                    if(roles.get(i).equals("Detective")){
                        userNo=i;
                        break;
                    }
                }
                myPlayer=new User("Detective",userNo);

            }//end a==2

            else if(a==3){

                for(int i=1;i<roles.size()+1;i++){
                    if(roles.get(i).equals("Healer")){
                        userNo=i;
                        break;
                    }
                }
                myPlayer=new User("Healer",userNo);

            }//end a==3

            else{

                for(int i=1;i<roles.size()+1;i++){
                    if(roles.get(i).equals("Commoner")){
                        userNo=i;
                        break;
                    }//end if
                }//end for
               myPlayer=new User("Commoner",userNo);

            }//end a==4
        }//end option 5(random select)
        System.out.println("You are Player"+userNo+".");
        System.out.println("You are a "+ myPlayer.getType());
        System.out.print("Other "+ myPlayer.getType() +"s are: [");
        for(int i=1;i<roles.size()+1;i++){
            if(i!=userNo) {
                if (roles.get(i).equals(myPlayer.getType())) {
                    System.out.print("Player" + i + ",");
                }//end string compare
            }//end userno
        }//end for
        System.out.println("]");
        return userNo;
    }//end charSelect

    public int genRandom(){
        Random rand=new Random();
        int rand1=rand.nextInt(4);
        return rand1;
    }

    public void assignUser(){

    }//end assignUser

    public static int getPlayer(ArrayList<Integer> array){
        int n=array.size();
        int index=(int)(Math.random()*n);
        int num=array.get(index);
        array.set(index,array.get(n-1));
        array.remove(n-1);
        return num;
    }

    public void assignPlayers(int numberOfPlayers){
    Mafia.setNumOfMafias(numberOfPlayers/5);
    Detective.setNumOfDetectives(numberOfPlayers/5);
    Healer.setNumOfHealers(Math.max(1,numberOfPlayers/10));
    Commoner.numOfCommoners=numberOfPlayers-Mafia.getNumOfMafias()-Detective.getNumOfDetectives()-Healer.getNumOfHealers();
//    System.out.println("Mafias: "+Mafia.getNumOfMafias());
//    System.out.println("Detectives: "+Detective.getNumOfDetectives());
//    System.out.println("Healers: "+Healer.getNumOfHealers());
//    System.out.println("Commoners: "+Commoner.numOfCommoners);
    System.out.println(numberOfPlayers);

        ArrayList<Integer> array =new ArrayList<>(numberOfPlayers);
        for(int i=0;i<numberOfPlayers;i++){
            array.add(i+1);
        }
        for(int i=0;i<Mafia.getNumOfMafias();i++){
            int play=getPlayer(array);
            Mafia.addListofMafias(Mafia.getListOfMafias(),play);
            roles.put(play,"Mafia");
        }
        for(int i=0;i<Detective.getNumOfDetectives();i++){
            int play=getPlayer(array);
            Detective.addListofDetectives(Detective.getListOfDetectives(),play);
            roles.put(play,"Detective");
        }
        for(int i=0;i<Healer.getNumOfHealers();i++){
            int play=getPlayer(array);
            Healer.addListofHealers(Healer.getListOfHealers(),play);
            roles.put(play,"Healer");
        }
        for(int i=0;i<Commoner.numOfCommoners;i++){
            int play=getPlayer(array);
            Commoner.addListofCommoners(Commoner.getListOfCommoners(),play);
            roles.put(play,"Commoner");
        }
//    for(int i=1;i<roles.size()+1;i++){
//        System.out.println(roles.get(i));
//    }


    }//end assignPlayers


    public void play(int userNo){

        while(!endOfGame()){
            Round.start(userNo);
        }

    }//end play


}
