import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DisjointSetTest {
    @Test
    public void uandf() {
        DisjointSet disjointSet = new DisjointSet();
        disjointSet.uandf(10);
        assertEquals(0, disjointSet.final_sets());
        disjointSet.make_set(1);
        assertEquals(1, disjointSet.final_sets());
    }

    @Test
    public void make_set() {
        DisjointSet disjointSet = new DisjointSet();
        disjointSet.uandf(10);
        disjointSet.make_set(0);
        assert (disjointSet.find_set(0).rank == 0);
    }

    @Test
    public void union_sets() {
        DisjointSet disjointSet = new DisjointSet();
        disjointSet.uandf(10);
        for (int i = 0; i < 10; i++) {
            disjointSet.make_set(i);
        }
        disjointSet.union_sets(0, 1);
        assertEquals(9, disjointSet.final_sets());
        assertEquals(disjointSet.find_set(0).rank, 1);

        disjointSet.union_sets(1, 2);
        assertEquals(8, disjointSet.final_sets());
        assertEquals(disjointSet.find_set(1).rank, 1);

        disjointSet.union_sets(0, 3);
        assertEquals(7, disjointSet.final_sets());
        assertEquals(disjointSet.find_set(0).rank, 1);

        disjointSet.union_sets(4, 5);
        assertEquals(6, disjointSet.final_sets());
        assertEquals(disjointSet.find_set(4).rank, 1);
    }

    @Test
    public void find_set() {
        DisjointSet disjointSet = new DisjointSet();
        disjointSet.uandf(10);
        for (int i = 0; i < 10; i++) {
            disjointSet.make_set(i);
        }
        disjointSet.union_sets(0, 1);

        disjointSet.union_sets(1, 2);

        disjointSet.union_sets(0, 3);

        disjointSet.union_sets(4, 5);

        assertEquals(disjointSet.find_set(2).data, 0);
        assertEquals(disjointSet.find_set(5).data, 4);
    }
}