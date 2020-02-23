/**
 * public Node(Node next){
 * this.next = next;
 * }
 * <p>
 * public Parent(Node head, Node tail, int rank){
 * this.head = head;
 * this.tail = tail;
 * this.rank = rank;
 * }
 */
public class DisjointSet {

    int size;
    private Node[] arr;

    public void uandf(int n) {
        this.arr = new Node[n];
        this.size = n;
    }

    public void make_set(int i) {
        //create new parent node at index i
        this.arr[i] = new Node();

        //create new parent of linked list and set rank
        this.arr[i].rank = 0;

        this.arr[i].parent = this.arr[i];

        this.arr[i].data = i; //set data for the node to keep track of connected components
    }

    public void union_sets(int i, int j) {
        Node node_i = find_set(i);
        Node node_j = find_set(j);

        if (node_i.rank == node_j.rank) {
            //if ranks of both are the same
            //point node_j parent to node_i and increment node i rank by 1
            node_j.parent = node_i.parent.parent;
            node_i.rank++;
        }
        //if the ranks are not the same
        else {
            if (node_i.rank > node_j.rank) {
                node_j.parent = node_i.parent.parent;
            } else {
                node_i.parent = node_j.parent.parent;
            }
        }
    }

    public Node find_set(int i) {
        this.arr[i].parent = this.arr[i].parent.parent;
        return this.arr[i].parent;
    }

    public int final_sets() {
        int sets = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.arr[i] != null && this.arr[i].parent == this.arr[i] &&
                    this.arr[i].parent.data == i) {
                sets++;
            }
        }

        return sets;
    }

    class Node {
        int rank;
        Node parent;
        int data;
    }

}