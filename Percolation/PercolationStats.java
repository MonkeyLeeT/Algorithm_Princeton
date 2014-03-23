
public class PercolationStats {
		private int count;
		private double[] fraction;
		
	      // perform T independent computational experiments on an N-by-N grid
	   public PercolationStats(int N, int T) {
		   if (N <= 0 || T <= 0) throw new IllegalArgumentException("N and T have to be greater than 0");
		   fraction = new double[T];
		   int site;
		   for (int i = 0; i < T; i++) {
			   count = 0;
		   Percolation test = new Percolation(N);
		   while (!test.percolates()) {
			   site = StdRandom.uniform(N * N) + 1;
			   if (!(test.isOpen((site % N == 0 && site >= N) ? site / N : site / N + 1, (site % N == 0) ? N : site % N))) {
				   test.open((site % N == 0 && site >= N) ? site / N : site / N + 1, (site % N == 0) ? N : site % N);
				   count++;
			   }
		   }
		   fraction[i] = (count * 1.0 / (N * N)); 
		   }
	   }

	   public double mean()  {
		   return StdStats.mean(fraction);	   
	   }                   // sample mean of percolation threshold
	   
	   public double stddev() {
		   return StdStats.stddev(fraction);   
	   }                  // sample standard deviation of percolation threshold
	   
	   public double confidenceLo() {
		   return mean() - ((1.96 * stddev()) / Math.sqrt(fraction.length));
	   }            // returns lower bound of the 95% confidence interval
	   
	   public double confidenceHi() {
		   return mean() + ((1.96 * stddev()) / Math.sqrt(fraction.length));
	   }            // returns upper bound of the 95% confidence interval
	   
	   public static void main(String[] args) {
		   	PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	        StdOut.println("mean                    = " + ps.mean());
	        StdOut.println("stddev                  = " + ps.stddev());
	        StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
	   }// test client, described below

}
