import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] fractionsOfOpenSites;
    private final int trials;
    private final int length;
    private double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid ");
        }
        this.fractionsOfOpenSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int open = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    open++;
                }
            }
            this.fractionsOfOpenSites[i] = (double) open / (n * n);
        }

        this.trials = trials;
        this.length = n;
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionsOfOpenSites);
    }

    public double stddev() {
        return StdStats.stddev(fractionsOfOpenSites);
    }

    // sample standard deviation of percolation threshold
    // public double stddev() {
    //     double total = 0.0;
    //     double mean = this.mean();
    //     for (int i = 0; i < trials; i++) {
    //         total += Math.pow(this.fractionOfOpenSites() - mean, 2);
    //     }
    //     return Math.sqrt(total / (double) (trials - 1));
    // }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        // PercolationStats test = new PercolationStats(Integer.valueOf(args[0]),
        //                                              Integer.valueOf(args[1]));
        //
        PercolationStats test = new PercolationStats(50, 10);
        // double mean = test.mean();
        System.out.println("mean = " + test.mean());
        System.out.println("stddev = " + test.stddev());
        System.out.println(
                "95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi()
                        + "]");
    }

}