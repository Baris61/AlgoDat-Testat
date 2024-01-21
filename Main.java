import java.util.Random;

class IntComparable implements Comparable<IntComparable> {
    private final int value;

    public IntComparable(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(IntComparable other) {
        return Integer.compare(this.value, other.value);
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

public class Main {
    public static void main(String[] args) {
        // Einen neuen Baum erstellen
        RBTree<IntComparable> redBlackTree = new RBTree<>();
 
        // 15 zuf�llige und unterschiedliche Integer-Zahlen einf�gen
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            int randomInt = random.nextInt(100); // Annahme: Zuf�llige Integer-Zahlen von 0 bis 99
            IntComparable intComparable = new IntComparable(randomInt);
 
            try {
                // Versuche, den zuf�lligen Integer in den Baum einzuf�gen
                redBlackTree.insert(intComparable);
                System.out.println("Eingef�gt: " + randomInt);           
 
                // Generiere einen Dateinamen f�r die DOT-Ausgabe
                String dotFileName = "RBTree_" + i + ".dot";
 
                // Drucke den aktuellen Zustand des Baums in einer DOT-Datei
                redBlackTree.printDOT(dotFileName);
 
            } catch (IllegalArgumentException e) {
                System.out.println("Ignoriert: " + randomInt + " (Doppelter Wert)");
            }
        }
    }
}


