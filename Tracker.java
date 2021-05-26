public class Tracker {
    private String action;
    private Boolean isRoot;
    private Boolean isLeaf;
    private Boolean hasOnlyLeftSubTree;
    private Boolean hasOnlyRightSubTree;
    private Boolean hasTwoChildrens;
    private Boolean isSuccessorIsLeaf;
    private Boolean isSuccessorRightChild;
    private Boolean isNodeRightChild;
    private Boolean isDirectChild;
    private BacktrackingBST.Node x;
    private BacktrackingBST.Node successor;

    public Tracker(String action,
                   Boolean isRoot,
                   Boolean isLeaf,
                   Boolean hasOnlyLeftSubTree,
                   Boolean hasOnlyRightSubTree,
                   Boolean hasTwoChildrens,
                   Boolean isSuccessorIsLeaf,
                   Boolean isSuccessorRightChild,
                   Boolean isNodeRightChild,
                   Boolean isDirectChild,
                   BacktrackingBST.Node x,
                   BacktrackingBST.Node successor) {
        this.action = action;
        this.isRoot = isRoot;
        this.isLeaf = isLeaf;
        this.hasOnlyLeftSubTree = hasOnlyLeftSubTree;
        this.hasOnlyRightSubTree = hasOnlyRightSubTree;
        this.hasTwoChildrens = hasTwoChildrens;
        this.isSuccessorIsLeaf = isSuccessorIsLeaf;
        this.isSuccessorRightChild = isSuccessorRightChild;
        this.isDirectChild = isDirectChild;
        this.isNodeRightChild = isNodeRightChild;
        this.x = x;
        this.successor = successor;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }
    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }


    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Boolean getHasOnlyLeftSubTree() {
        return hasOnlyLeftSubTree;
    }

    public void setHasOnlyLeftSubTree(Boolean hasOnlyLeftSubTree) {
        this.hasOnlyLeftSubTree = hasOnlyLeftSubTree;
    }

    public Boolean getHasOnlyRightSubTree() {
        return hasOnlyRightSubTree;
    }

    public void setHasOnlyRightSubTree(Boolean hasOnlyRightSubTree) {
        this.hasOnlyRightSubTree = hasOnlyRightSubTree;
    }


    public Boolean getHasTwoChildrens() {
        return hasTwoChildrens;
    }

    public void setHasTwoChildrens(Boolean hasTwoChildrens) {
        this.hasTwoChildrens = hasTwoChildrens;
    }

    public BacktrackingBST.Node getX() {
        return x;
    }

    public BacktrackingBST.Node getSuccessor() {
        return successor;
    }

    public void setSuccessor(BacktrackingBST.Node successor) {
        this.successor = successor;
    }

    public Boolean getSuccessorRightChild() {
        return isSuccessorRightChild;
    }

    public void setSuccessorRightChild(Boolean isSuccessorRightChild) {
        this.isSuccessorRightChild = isSuccessorRightChild;
    }

    public Boolean getSuccessorIsLeaf() {
        return isSuccessorIsLeaf;
    }

    public void setSuccessorIsLeaf(Boolean isSuccessorIsLeaf) {
        this.isSuccessorIsLeaf = isSuccessorIsLeaf;
    }

    public Boolean getNodeRightChild() {
        return isNodeRightChild;
    }

    public void setNodeRightChild(Boolean isNodeRightChild) {
        this.isNodeRightChild = isNodeRightChild;
    }

    public Boolean getDirectChild() {
        return isDirectChild;
    }

    public void setDirectChild(Boolean directChild) {
        isDirectChild = directChild;
    }
}
