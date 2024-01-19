public class RBTree<T extends Comparable<T>> {
	final boolean red = true; 
	final boolean black = false;
	
	private Node root = null;
	
	// Knoten intitialisieren
	public class Node {
		
		 T data;
		 boolean color;
		 
		 Node left;
		 Node right;
		 Node parent;
		 Node uncle;
		 Node grandparent;
		 
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
		 Node newNode = new Node(key, parent, red);
		 
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
	
	public void fixRedBlackPropertiesAfterInsert(Node node){
		// Beziehungen des Knotens festlegen
		Node parent = node.parent;
		Node grandparent;
		Node uncle;
		
		// Knoten ist Wurzel und hat somit Kein Vater, Großvateroder und Onkel  
		if(parent == null){
			node = root;
			grandparent = null;
			uncle = null;
			}
		// Vater ist Wurzel und hat somit kein Großvater und Onkel
		else if (parent.parent == null) {
				parent = root;
				grandparent = null;
				uncle = null;
			}
		// Großvater ist Vaters Vater und Onkel ist der jeweils andere Kind des Großvaters
		else {
			grandparent = parent.parent;
			uncle = (node == grandparent.left) ? grandparent.right : grandparent.left;
			}
		
		// Fall 1: Der neue Knoten ist die Wurzel
		if (node == root) {
			node.color = black;
			return;
		}
		
		// Fall 2: Der Vater ist die Wurzel und rot		
		if (parent == root && parent.color == red) {
			parent.color = black;
	        
			// Fall 3: Vater und Onkelknoten sind rot
			if (uncle != null && uncle.color == red && parent.color == red) {
	            uncle.color = black;
	            grandparent.color = red;
	            fixRedBlackPropertiesAfterInsert(grandparent); // Rekursiver Aufruf für den Großvaterknoten
	            }
			return;
			}
		
		// Fall 4: Vater ist rot, Onkel ist schwarz, Knoten ist “innerer Enkel” 
		if (parent.color == red && (uncle == null || uncle.color == black)) {
			// Knoten ist rechtes inneres Kind
			if(node == parent.right && parent == grandparent.left){
				rotateLeft(parent);// Vater links rotieren
				fixRedBlackPropertiesAfterInsert(parent);// Rekursiver Aufruf für den vaterknoten
				return;
			}
			// Knoten ist linkes inneres Kind
			else if(node == parent.left && parent == grandparent.right){
				rotateRight(parent);// Vater rechts rotieren
				fixRedBlackPropertiesAfterInsert(parent);// Rekursiver Aufruf für den vaterknoten
				return;
			}
			
			// Fall 5: Vater ist rot, Onkel ist schwarz, Knoten ist “äußerer Enkel”
			// Knoten ist rechtes äußeres Kind
			else if(node == parent.right && parent == grandparent.right){
				rotateLeft(grandparent);// Großvater links rotieren
				fixRedBlackPropertiesAfterInsert(grandparent);// Rekursiver Aufruf für den Großvaterknoten
				return;
			}
			// Knoten ist linkes äußeres Kind
			else {
				rotateRight(grandparent);// Großvater rechts rotieren
				fixRedBlackPropertiesAfterInsert(grandparent);// Rekursiver Aufruf für den Großvaterknoten
				return;
			}
		}
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
}
