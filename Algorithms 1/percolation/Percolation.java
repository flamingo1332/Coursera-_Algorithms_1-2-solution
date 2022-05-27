import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF percolation;
    private boolean[] open;
    private final int length;
    private int numOfOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid");
        }
        this.percolation = new WeightedQuickUnionUF(n * n);
        this.open = new boolean[n * n];
        for (int i = 0; i < n * n; i++) {
            open[i] = false;
        }
        this.length = n;
        this.numOfOpen = 0;
    }

    private void validate(int row, int col) {
        int n = length;
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("index is not between 1 and " + n);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int rowZ = row - 1;
        int colZ = col - 1;
        if (!open[rowZ * length + colZ]) {
            open[rowZ * length + colZ] = true;
            numOfOpen++;
        }

        int p = colZ + rowZ * length;
        if (rowZ != 0) {
            if (isOpen(row - 1, col)) {
                percolation.union(p, p - length);
            }
        }
        if (rowZ != length - 1) {
            if (isOpen(row + 1, col)) {
                percolation.union(p, p + length);
            }
        }
        if (colZ != 0) {
            if (isOpen(row, col - 1)) {
                percolation.union(p, p - 1);
            }
        }
        if (colZ != length - 1) {
            if (isOpen(row, col + 1)) {
                percolation.union(p, p + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[(row - 1) * length + col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int rowZ = row - 1;
        int colZ = col - 1;
        if (!isOpen(row, col)) {
            return false;
        }
        int find = percolation.find(colZ + rowZ * length);
        for (int i = 0; i < length; i++) {
            if (find == percolation.find(i)) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int num = 0;
        for (boolean i : open) {
            if (i) {
                num += 1;
            }
        }
        return num;
    }


    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < length; i++) {
            if (isFull(length, i + 1)) {
                return true;
            }
        }
        return false;
    }


    // test client (optional)
    public static void main(String[] args) {
        // Percolation perc = new Percolation(5);
        // perc.open(1, 1);
        // perc.open(2, 2);
        // perc.open(3, 3);
        // perc.open(4, 4);
        // perc.open(5, 5);
        // System.out.println(perc.percolation.find(5));
        // System.out.println(perc.percolates());
    }
}