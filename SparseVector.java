
public class SparseVector {
	
	//Hilfsklasse zur Verwaltung der Einträge
	private class Node { 
		
		// Wert des Knotens
		double value;
		// Index des Knotens
		int index;
		// Zeiger auf nächsten Eintrag
		Node next;
		
		Node(int index, double value){
			this.index = index;
			this.value = value;
			this.next = null;
		}
	}

	Node head;
	int length;
	
	//Standard-Konstruktor, Vektor leer
	SparseVector(){
		this.head = null;
		this.length = 0;
	}
	
	//Konstruktor, Vektor mit length n
	SparseVector(int n){
		head = null;
		this.length = n;
	}
	
	// Einfügen eines Wertes
	void setElement(int index, double value) {
		// fehlerhafte Eingabe
		if(index < 0 || index >= length) {
			throw new IllegalArgumentException("Ungültiger Index");
		}
		
		// Knoten am Anfang einfügen
		if (head == null || index < head.index) {
			Node newNode = new Node(index, value);
            newNode.next = head;
            head = newNode;
            }
		
		else {
			// aktueller und vorheriger Knoten
			Node current = head;
            Node previous = null;
            
            // richtige Position für Knoten finden
            while (current != null && index > current.index) {
            	previous = current;
                current = current.next;
            }

            // Aktualisiere den Wert, wenn der Index bereits existiert
            if (current != null && index == current.index) {
                current.value = value;
            } 
            
            // Knoten an gefundene Stelle einsetzen
            else {
            	Node newNode = new Node(index, value);
                newNode.next = current;
                previous.next = newNode;
            }
        }
    }
	
	// Wert an Indexstelle wiedergeben
	double getElement(int index) {
		// fehlerhafte Eingabe
		if(index < 0 || index >= length) {
			throw new IllegalArgumentException("Ungültiger Index");
	    }
		
		Node current = head;
        while (current != null && index > current.index) {
            current = current.next;
        }
        
        // Index existiert
        if(current != null && index == current.index) {
        	return current.value;
        }
        
        // Index existiert nicht
        else {
        	return 0.0;
        }
	}
	
	// Eintrag an Indexstelle entfernen
	void removeElement(int index){
		// fehlerhafte Eingabe
		if(index < 0 || index >= length) {
			throw new IllegalArgumentException("Ungültiger Index");
		}
		
		Node current = head;
        Node previous = null;
        
        // Eintrag suchen
        while (current != null && index != current.index) {
            previous = current;
            current = current.next;
        }
        
        // wenn gefunden entfernen
        if (current != null && index == current.index) {
        	// Wenn Eintrag am Anfang
            if (previous == null) {
                head = current.next;
            }
            
            else {
            	previous.next = current.next;
            }
        }
	}
	
	// Vektor length zurückgeben
	int getLength() {
		return length;
	}
	
	// Objekte auf Gleichheit überprüfen
	boolean equals(SparseVector other) {
		// length auf Gleichheit prüfen
		if (other == null || this.length != other.length) {
            return false;
        }
		
		// Indexstellen durchlufen und Werte auf Gleichheit prüfen
		for (int i = 0; i < length; i++) {
            if (this.getElement(i) != other.getElement(i)) {
                return false;
            }
        }

        return true;
	}
	
	// Addieren eines anderen Objekts zum aktuellen
	void add(SparseVector other) {
		// Objekte kann nicht addiert werden 
		if (other == null || this.length != other.length) {
            throw new IllegalArgumentException("Ungültiges Argument");
        }

		// Indexstellen durchlaufen, Werte addieren und Wert mit Summe ersetzen
        for (int i = 0; i < length; i++) {
            double sum = this.getElement(i) + other.getElement(i);
            this.setElement(i, sum);
        }
		
	}

}



