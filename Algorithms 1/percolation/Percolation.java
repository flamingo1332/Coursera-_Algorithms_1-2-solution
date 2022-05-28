import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF percolation;
    private boolean[] open;
    private final int length;
    private int numOfOpen;
    private int topIndex;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid");
        }
        this.percolation = new WeightedQuickUnionUF(n * n + 2);
        // added top , bottom index for shorter runtime
        this.open = new boolean[n * n + 2];
        for (int i = 1; i <= n * n; i++) {
            open[i] = false;
        }
        open[0] = true;
        open[n * n + 1] = true;
        this.length = n;
        this.numOfOpen = 0;
        this.topIndex = n * n + 1;
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
        int p = colZ + rowZ * length + 1;
        if (!open[p]) {
            open[p] = true;
            numOfOpen++;
        }

        //connect to top index
        if (row == 1) {
            percolation.union(p, 0);
        }
        //connect to bottom index
        if (row == length) {
            percolation.union(p, length * length + 1);

        }

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
        return open[(row - 1) * length + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return percolation.find(0) == percolation.find((row - 1) * length + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }


    // does the system percolate?
    public boolean percolates() {
        return percolation.find(0) == percolation.find(length * length + 1);
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