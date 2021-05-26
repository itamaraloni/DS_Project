

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;


    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }


    public Node getRoot() {
        return root;
    }

    public Node search(int x) {
        if (root == null || root.getKey() == x)
            return root;
        return root.search(x);
    }

    public boolean isLeaf(Node x) {
        return x.left == null && x.right == null;
    }

    public boolean hasOnlyLeftSubTree(Node x) {
        return x.left != null && x.right == null;
    }

    public boolean hasOnlyRightSubTree(Node x) {
        return x.left == null && x.right != null;
    }

    public boolean hasTwoChildrens(Node x) {
        return x.left != null && x.right != null;
    }

    public boolean isRoot(Node x) {
        return this.root == x;
    }

    public void insert(BacktrackingBST.Node z) {
        if (getRoot() == null) {
            root = z;
            stack.push(new Tracker("insert",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false, false, z,
                    null));
        } else {
            root.insert(z);
            stack.push(new Tracker("insert", false, false, false, false, false, false, false, false, false, z, null));
        }

        redoStack.push(false);


    }

    public void backtrack() {
        if (!stack.isEmpty()) {
            Tracker lastAction = (Tracker) stack.pop();
            redoStack.push(lastAction);

            Node x = lastAction.getX();
            Node successor = lastAction.getSuccessor();
            boolean isRoot = lastAction.getIsRoot();
            boolean isLeaf = lastAction.getIsLeaf();
            boolean hasOnlyLeftSubTree = lastAction.getHasOnlyLeftSubTree();
            boolean hasOnlyRightSubTree = lastAction.getHasOnlyRightSubTree();
            boolean hasTwoChildrens = lastAction.getHasTwoChildrens();
            boolean isNodeRightChild = lastAction.getNodeRightChild();
            boolean isSuccessorIsLeaf = lastAction.getSuccessorIsLeaf();
            boolean isSuccessorRightChild = lastAction.getSuccessorRightChild();
            boolean isDirectChild = lastAction.getDirectChild();

            if (lastAction.getAction() == "insert") {
                if (isRoot) {
                    root = null;
                } else {
                    delete(x);
                    stack.pop();
                    redoStack.pop();
                }
            }
            if (lastAction.getAction() == "delete") {
                if (isRoot) {
                    if (isLeaf) {
                        this.root = x;
                    } else if (hasOnlyLeftSubTree || hasOnlyRightSubTree) {
                        this.root.parent = x;
                        this.root = x;
                    } else if (hasTwoChildrens) {
                        if (isSuccessorIsLeaf) {
                            this.root = x;
                            replacePointersBetweenNodes(successor, x);
                            x.left.parent = x;

                            if (isSuccessorRightChild) {
                                x.right = successor;
                            } else {
                                x.right.parent = x;
                                successor.parent.left = successor;
                            }
                        } else { // has right sub tree
                            this.root = x;

                            if (isDirectChild) {
                                replacePointersBetweenNodes(successor, x);
                                x.right = successor;
                                x.left.parent = x;
                            } else { // the successor is not the right child of x
                                replacePointersBetweenNodes(successor, x);
                                successor.parent.left = successor;
                                successor.right.parent = successor;
                                x.left.parent = x;
                                x.right.parent = x;
                            }
                        }
                    }
                } else { // x is not root
                    if (isLeaf) {
                        backtrackParentPointers(x, isNodeRightChild);
                    } else if (hasOnlyLeftSubTree) {
                        x.left.parent = x;
                        backtrackParentPointers(x, isNodeRightChild);
                    } else if (hasOnlyRightSubTree) {
                        x.right.parent = x;
                        backtrackParentPointers(x, isNodeRightChild);
                    } else if (hasTwoChildrens) {
                        if (isSuccessorIsLeaf) {
                            replacePointersBetweenNodes(successor, x);
                            x.left.parent = x;

                            if (isSuccessorRightChild) {
                                x.right = successor;
                                x.left.parent = x;

                                backtrackParentPointers(x, isNodeRightChild);
                            } else {
                                successor.parent.left = successor;
                                x.right.parent = x;

                                backtrackParentPointers(x, isNodeRightChild);
                            }

                        } else { // has right sub tree
                            if (isDirectChild) {
                                replacePointersBetweenNodes(successor, x);
                                x.right = successor;

                                backtrackParentPointers(x, isNodeRightChild);
                            } else { // the successor is not the right child of x
                                replacePointersBetweenNodes(successor, x);
                                successor.parent.left = successor;
                                successor.right.parent = successor;

                                backtrackParentPointers(x, isNodeRightChild);
                            }
                            x.left.parent = x;
                            x.right.parent = x;

                        }
                    }
                }
            }
            System.out.println("backtracking performed");
        }

    }

    private void backtrackParentPointers(Node x, boolean isNodeRightChild) {
        if (isNodeRightChild) {
            x.parent.right = x;
        } else { // x is left child
            x.parent.left = x;
        }
    }

    @Override
    public void retrack() {
        Object lastPop = redoStack.pop();
        if (lastPop instanceof Boolean){
            redoStack.push(lastPop);
        } else {
            Tracker lastAction = (Tracker) lastPop;
            if (lastAction.getAction() == "delete") {
                delete(lastAction.getX());
                redoStack.pop();
            } else {
                Node x = lastAction.getX();
                if(!lastAction.getIsRoot()){
                    if (x.getKey()>x.parent.getKey()){
                        x.parent.right = x;
                    }
                    else{
                        x.parent.left = x;
                    }
                }else{
                    root=x;
                }
                stack.push(lastAction);
            }
        }

    }

    public void delete(Node x) {
        Tracker tracker = new Tracker("delete", false, false, false, false, false, false, false, false, false, x, null);
        if (isRoot(x)) {
            tracker.setIsRoot(true);
            if (isLeaf(x)) {
                tracker.setIsLeaf(true);
                this.root = null;
            } else if (hasOnlyLeftSubTree(x)) {
                tracker.setHasOnlyLeftSubTree(true);
                x.left.parent = null;
                this.root = x.left;
            } else if (hasOnlyRightSubTree(x)) {
                tracker.setHasOnlyRightSubTree(true);
                x.right.parent = null;
                this.root = x.right;
            } else if (hasTwoChildrens(x)) {
                tracker.setHasTwoChildrens(true);
                Node successor = this.successor(x);
                tracker.setSuccessor(successor);
                if (isLeaf(successor)) {
                    tracker.setSuccessorIsLeaf(true);

                    if (isNodeRightChild(successor)) {
                        // in this case successor.parent is always root
                        tracker.setSuccessorRightChild(true);
                        x.right = null;
                    } else {
                        successor.parent.left = null;
                        x.right.parent = successor;
                    }
                    x.left.parent = successor;
                    replacePointersBetweenNodes(successor, x);
                    this.root = successor;
                } else { // has right sub tree
                    removeSuccessorWithRightSubTree(x, successor, tracker);
                    this.root = successor;
                }
            }
        } else { // x is not root
            if (isLeaf(x)) {
                tracker.setIsLeaf(true);
                changeParentPointersToSuccessor(x, null, tracker);
            } else if (hasOnlyLeftSubTree(x)) {
                tracker.setHasOnlyLeftSubTree(true);
                changeParentPointersToSuccessor(x, x.left, tracker);
                x.left.parent = x.parent;
            } else if (hasOnlyRightSubTree(x)) {
                tracker.setHasOnlyRightSubTree(true);
                changeParentPointersToSuccessor(x, x.right, tracker);
                x.right.parent = x.parent;
            } else if (hasTwoChildrens(x)) {
                tracker.setHasTwoChildrens(true);
                Node successor = this.successor(x);
                tracker.setSuccessor(successor);
                if (isLeaf(successor)) {
                    tracker.setSuccessorIsLeaf(true);
                    if (isNodeRightChild(successor)) {
                        tracker.setSuccessorRightChild(true);
                        x.left.parent = successor;

                        // in this case successor.parent is always x
                        changeParentPointersToSuccessor(x, x.right, tracker);
                        x.right = null;
                    } else {
                        successor.parent.left = null;
                        changeParentPointersToSuccessor(x, successor, tracker);
                        x.right.parent = successor;
                    }
                    x.left.parent = successor;
                    replacePointersBetweenNodes(successor, x);
                } else { // has right sub tree
                    removeSuccessorWithRightSubTree(x, successor, tracker);
                }
            }
        }
        stack.push(tracker);
        redoStack.push(false);
    }

    private void removeSuccessorWithRightSubTree(Node x, Node successor, Tracker tracker) {
        if (successor.parent == x) { // successor is  direct child of x
            tracker.setDirectChild(true);
            if (!isRoot(x)) {
                changeParentPointersToSuccessor(x, successor, tracker);
            }
            x.left.parent = successor;
            replacePointersBetweenNodes(successor, x);
            replaceRightPointersBetweenNodes(x, successor);
        } else { // the successor is not the right child of x
            x.left.parent = successor;
            x.right.parent = successor;
            if (!isRoot(x)) {
                changeParentPointersToSuccessor(x, successor, tracker);
            }
            successor.parent.left = successor.right;
            successor.right.parent = successor.parent;

            replacePointersBetweenNodes(successor, x);
        }
    }

    private void changeParentPointersToSuccessor(Node x, Node successor, Tracker tracker) {
        if (isNodeRightChild(x)) {
            tracker.setNodeRightChild(true);
            x.parent.right = successor;
        } else { // x is left child
            x.parent.left = successor;
        }
    }

    private void replaceRightPointersBetweenNodes(Node x, Node successor) {
        Node tmp = successor.right;
        successor.right = x.right;
        x.right = tmp.right;
    }

    public boolean isNodeRightChild(Node x) {
        return x.parent.right == x;

    }

    public void replacePointersBetweenNodes(Node nodeA, Node nodeB) {
        Node tmp = new Node(0, null);

        tmp.left = nodeA.left;
        tmp.right = nodeA.right;
        tmp.parent = nodeA.parent;

        nodeA.left = nodeB.left;
        nodeA.right = nodeB.right;
        nodeA.parent = nodeB.parent;

        nodeB.left = tmp.left;
        nodeB.right = tmp.right;
        nodeB.parent = tmp.parent;
    }

    public Node minimum() {
        if (getRoot() == null)
            return root;
        else
            return root.minimum();
    }

    public Node maximum() {
        if (getRoot() == null)
            return root;
        else
            return root.maximum();
    }

    public Node successor(Node x) {
        if (this.maximum().getKey() == x.getKey()) // case 1: x is the maximum
            return null;
        else if (x.right != null) { // case 2: x has right child
            BacktrackingBST subTree = new BacktrackingBST(new Stack(), new Stack());
            subTree.insert(x.right);
            return subTree.minimum();
        } else { // case 3: x doesn't have a right child
            while (x.parent != null && x.parent.right == x) {
                x = x.parent;
            }
            return x.parent;
        }
    }

    public Node predecessor(Node x) {
        if (this.minimum().getKey() == x.getKey()) // case 1: x is minimum
            return null;
        else if (x.left != null) { // case 2: x has left child
            BacktrackingBST subTree = new BacktrackingBST(new Stack(), new Stack());
            subTree.insert(x.left);
            return subTree.maximum();
        } else { // case 3: x doesn't have left child
            while (x.parent != null && x.parent.left == x) {
                x = x.parent;
            }
            return x.parent;
        }
    }

    public void printPreOrder() {
        if (getRoot() == null)
            return;
        root.printPreOrder();
    }

    @Override
    public void print() {
        printPreOrder();
    }

    public static class Node {
        //These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;

        private BacktrackingBST.Node parent;
        private int key;
        private Object value;


        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;

        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public void insert(BacktrackingBST.Node z) {
            if (z.getKey() > this.getKey()) {
                if (right != null) {
                    this.right.insert(z);
                } else {
                    z.parent = this;
                    this.right = z;
                }

            } else {
                if (left != null) {
                    this.left.insert(z);
                } else {
                    z.parent = this;
                    this.left = z;
                }
            }
        }

        public Node maximum() {
            Node current = this;
            while (current.right != null)
                current = current.right;
            return current;
        }

        public Node minimum() {
            Node current = this;
            while (current.left != null)
                current = current.left;
            return current;
        }

        public Node search(int x) {
            if (this.getKey() == x)
                return this;
            else if (this.getKey() > x) {
                if (left != null)
                    return left.search(x);
                else
                    return null;
            } else {
                if (right != null)
                    return right.search(x);
                else
                    return null;
            }

        }

        public void printPreOrder() {
            System.out.print(this.getKey() + " ");
            if (left != null) {
                left.printPreOrder();
            }
            if (right != null) {
                right.printPreOrder();
            }
        }

    }
}
