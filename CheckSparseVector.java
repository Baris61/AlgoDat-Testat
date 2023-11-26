
public class CheckSparseVector {
	public static void main (String[]args) {
		// Längenabfrage leerer Vektor
        SparseVector vector = new SparseVector();
        System.out.println("Länge des Vektors: " + vector.getLength());
        // Längenabfrage Vektor mit gegebenem Parameter n
        vector = new SparseVector(5);
        System.out.println("Länge des Vektors: " + vector.getLength());
        
        try {
        	// neues Element einfügen
            vector.setElement(0, 1.0);
            // Wert an Index 0 ausgeben
            System.out.println("Wert am Index 0 ist: " + vector.getElement(0));
        } catch (IllegalArgumentException e) {
            System.out.println("Falscher Index: " + e.getMessage());
        }
        
        try {
        	// weitere Elemente einfügen
            vector.setElement(2, 1.0);
            // Wert an Index 2 ausgeben
            System.out.println("Wert am Index 2 ist: " + vector.getElement(2));
        } catch (IllegalArgumentException e) {
            System.out.println("Falscher Index: " + e.getMessage());
        }
        
        try {
        	// Element entfernen
            vector.removeElement(2);
            System.out.println("Wert im Index 2: " + vector.getElement(2));
        } catch (IllegalArgumentException e) {
            System.out.println("ungültiger Index: " + e.getMessage());
        }
        
        //Vektorenvergleich  
        SparseVector v1 = new SparseVector(5);
        v1.setElement(0, 1.0);
        v1.setElement(2, 2.0);
        SparseVector v2 = new SparseVector(5);
        v2.setElement(0, 1.0);
        v2.setElement(2, 2.0);
        System.out.println(v1.equals(v2)+ "\t<- Vektoren sind gleich ?");
        
        SparseVector v3 = new SparseVector(5);
        v3.setElement(0, 1.0);
        v3.setElement(2, 2.0);
        SparseVector v4 = new SparseVector(6);
        v4.setElement(0, 1.0);
        v4.setElement(3, 2.0);
        System.out.println(v3.equals(v4)+ "\t<- Vektoren sind gleich ?");
        
        //Vektoraddition
        v1.add(v2);
        System.out.println("neu entstandene Werte sind\t" + v1.getElement(0) + "\tund\t" + v1.getElement(2));
    }
}
