import java.util.ArrayList;
import java.util.Vector;

public class main {

    // TODO: needs qualitative tests

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(10);
        arrayList.add(20);
        arrayList.add(30);
        arrayList.add(40);
        arrayList.add(50);
        System.out.print("arrayList: ");
        System.out.println(arrayList);
        Vector<Integer> vector = new Vector<Integer>(arrayList);
        
        Checkers.theEnd(0);
   }
}

