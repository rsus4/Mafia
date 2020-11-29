import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;

public class PrintPlayer implements Comparable<PrintPlayer>{
    private int pNo;
    private String fun;

    public PrintPlayer(int pNo,String fun){
        super();
        this.pNo=pNo;
        this.fun=fun;
    }

    public int getpNo() {
        return pNo;
    }

    public void setpNo(int pNo) {
        this.pNo = pNo;
    }

    public int compareTo(PrintPlayer o) {
        if(this.pNo>o.pNo){
            return 1;
        }
        else if(this.pNo<o.pNo){
            return -1;
        }
        return 0;
    }

}
