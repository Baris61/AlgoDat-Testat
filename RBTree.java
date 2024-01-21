import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RBTree<T extends Comparable<T>> {
	final boolean RED = true; 
	final boolean BLACK = false;
	
	private Node root = null;
	
	// Knoten intitialisieren
	public class Node {
		
		 T data;
		 boolean color;
		 
		 Node left;
		 Node right;
		 Node parent;
		 
		 public Node(T data, Node parent, boolean color) {
		 this.data = data;
		 this.parent = parent;
		 this.color = color;
		 }
	}
	
	// Methode zum einfügen von neuen Knoten
	public void insert(T key) {
		 
		Node node = root; 
		Node parent = null;
		 
		 // Den richtigen Platz für den einzufügenden Knoten finden durch traversieren mithilfe des key 
		 while (node != null) {
			 
			 parent = node;
			 
			 // Wert des neuen Knotens mit den bereits vorhandenen Knoten vergleichen und jeweils rechts oder links weiterlaufen bis es keine Knoten mehr zum vergleichen gibt
			 int comp = key.compareTo(node.data);
			 
			 if (comp < 0) {
				 node = node.left;
				 } 
			 else if (comp > 0) {
					 node = node.right;
					 }
			 else {
				 throw new IllegalArgumentException("Tree already has a node with key " + key);
				 }
			 }
		 
		 // Neuen Knoten an passende stelle einfügen
		 Node newNode = new Node(key, parent, RED);
		 
		 // Der neue Knoten ist die Wurzel
		 if (parent == null) {
			 root = newNode;
		 } 
		 
		 // Der neue Knoten ist ein linkes Kind
		 else if (key.compareTo(parent.data) < 0) {
			 parent.left = newNode;
			 }
		 
		// Der neue Knoten ist ein rechtes Kind
		 else {
			 parent.right = newNode;
			 }
		 
		 // Nach dem Einfügen die Rot-Schwarz-Bedingungen überprüfen und ggf. korrigieren
		 fixRedBlackPropertiesAfterInsert(newNode);
	}
	
 // Methode zum Korrigieren der Rot-Schwarz-Bedingungen nach dem Einfügen
	public void fixRedBlackPropertiesAfterInsert(Node node){
		
		 // Solange der aktuelle Knoten nicht die Wurzel ist und der Vater rot ist, weitermachen
        while (node != root && node.parent != root && node.parent.color == RED) {
            // Vater ist linkes Kind des Großvaters
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
 
                // Fall 3: Vater und Onkelknoten sind rot
                if (uncle != null && uncle.color == RED) {
                    // Vater und Onkel auf Schwarz setzen, Großvater auf Rot setzen
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    // Mit dem Großvater fortsetzen
                    node = node.parent.parent;
                } else {
                    // Fall 4: Vater ist rot, Onkel ist schwarz, Knoten ist “innerer Enkel”
                    if (node == node.parent.right) {
                        // Knoten ist rechtes und Vater linkes Kind also Linksrotation des Vaters durchführen
                        node = node.parent;
                        rotateLeft(node);
                    }
                    // Fall 5: Vater ist rot, Onkel ist schwarz, Knoten ist “äußerer Enkel”
                    // Knoten und Vater sind linke Kinder also Vater und Großvater umfärben und Rechtsrotation des Großvaters durchführen
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                // Vater ist rechtes Kind des Großvaters (spiegelt das davor)
                Node uncle = node.parent.parent.left;
 
                // Fall 3: Vater und Onkelknoten sind rot
                if (uncle != null && uncle.color == RED) {
                    // Vater und Onkel auf Schwarz setzen, Großvater auf Rot setzen
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    // Mit dem Großvater fortsetzen
                    node = node.parent.parent;
                } else {
                    // Fall 4: Vater ist rot, Onkel ist schwarz, Knoten ist “innerer Enkel”
                    if (node == node.parent.left) {
                        // Knoten ist linkes und Vater ist rechtes Kind also Rechtsrotation des Vaters durchführen
                        node = node.parent;
                        rotateRight(node);
                    }
                    // Fall 5: Vater ist rot, Onkel ist schwarz, Knoten ist “äußerer Enkel”
                    // Knoten und Vater sind rechte Kinder also Vater und Großvater umfärben und Linksrotation des Großvaters durchführen
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        /* Fall 1: Der neue Knoten ist die Wurzel und 
         * Fall 2: Der Vater ist die Wurzel und rot
         * Die wurzel wird immer Schwarz gefärbt */
        root.color = BLACK;
	}
	
	// rechts Rotation eines Knotens
	private void rotateRight(Node node) {
		// Vater und linkes Kind bestimmen
		Node parent = node.parent;
		Node leftChild = node.left;
		
		node.left = leftChild.right; // das neue linke Kind ist das rechte Kind des alten linken Kindes
		
		// wenn das alte linke Kind ein rechtes Kind hat wird der zu rotierende Knoten der Vater davon
		if (leftChild.right != null) {
			leftChild.right.parent = node;
			}
		// der zu rotierende Knoten wird zum rechten Kind des alten linken Kindes
		leftChild.right = node;
		// das alte linke Kind zum Vater des zu rotierenden Knotens
		node.parent = leftChild;
		// Eltern-Kind-relation wird repariert
		replaceParentsChild(parent, node, leftChild);
	}
	
	// links Rotation eines Knotens
	private void rotateLeft(Node node) {
		// Vater und rechtes Kind bestimmen
		Node parent = node.parent;
		Node rightChild = node.right;
		
		node.right = rightChild.left;// das neue rechte Kind ist das linke Kind des alten rechten Kindes
		
		// wenn das alte rechte Kind ein linkes Kind hat wird der zu rotierende Knoten der Vater davon
		if (rightChild.left != null) {
			rightChild.left.parent = node;
			}
		// der zu rotierende Knoten wird zum linken Kind des alten rechten Kindes
		rightChild.left = node;
		// das alte rechte Kind zum Vater des zu rotierenden Knotens
		node.parent = rightChild;
		// Eltern-Kind-relation wird repariert
		replaceParentsChild(parent, node, rightChild);
	}
	
	// Eltern-Kind-relation reparieren
	private void replaceParentsChild(Node parent, Node oldChild, Node newChild) { 
		// der zu rotierende Knoten hat keinen Vater somit wird das neue Kind zur Wurzel
		if (parent == null) {
			root = newChild;
			}
		// der zu rotierende Knoten war ein linkes Kind also wird das neue Kind das linke Kind
		else if (parent.left == oldChild) {
			parent.left = newChild;
			}
		// der zu rotierende Knoten war ein rechtes Kind also wird das neue Kind das rechte Kind
		else if (parent.right == oldChild) {
			parent.right = newChild;
			} 
		else {
			throw new IllegalStateException("Node is not a child of its parent");
			}
		
		// das neue Kind bekommt den Vater des alten Kindes falls es nicht null ist
		if (newChild != null) {
			newChild.parent = parent;
			}
	}
	
	// Methode zum Drucken des Baums in DOT-Format
    public void printDOT(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("digraph G {\n");
            writer.write("  node [fontname=\"Arial\"];\n");
 
            if (root != null) {
                printDOTRecursive(root, writer);
            }
 
            writer.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // Rekursive Methode zum Drucken der Baumstruktur
    private void printDOTRecursive(Node node, BufferedWriter writer) throws IOException {
        if (node.left != null) {
            writer.write("  \"" + node.data + "\" -> \"" + node.left.data + "\" [label=\"L\"];\n");
            printDOTRecursive(node.left, writer);
        } else {
            writer.write("  \"" + node.data + "\" -> \"null" + node.data + "L\";\n");
            writer.write("  \"null" + node.data + "L\" [shape=point];\n");
        }
 
        if (node.right != null) {
            writer.write("  \"" + node.data + "\" -> \"" + node.right.data + "\" [label=\"R\"];\n");
            printDOTRecursive(node.right, writer);
        } else {
            writer.write("  \"" + node.data + "\" -> \"null" + node.data + "R\";\n");
            writer.write("  \"null" + node.data + "R\" [shape=point];\n");
        }
 
        // Farbe des Knotens in der DOT-Ausgabe
        String color = node.color == RED ? "red" : "black";
        writer.write("  \"" + node.data + "\" [color=" + color + ", fontcolor=" + color + "];\n");
    }
}
