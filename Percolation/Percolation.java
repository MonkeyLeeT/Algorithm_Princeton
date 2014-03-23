import java.util.Arrays;

public class Percolation {
	private int side;
	private boolean percolates;
	private byte[] grid;
	private WeightedQuickUnionUF uf;

	/**
	 * create N-by-N grid, with all sites blocked.
	 * @param N side size of the grid
	 */
	public Percolation(int N) {
		grid = new byte[N * N];
		side = N;
		uf = new WeightedQuickUnionUF(N * N);
	}

	/**
	 * open site (row i, column j) if it is not already open
	 * check if any of the components in union is connected to bottom and mark both if any
	 * @param i row number of the site
	 * @param j column number of the site
	 * @throws exception if row index is outside (0, N];
	 */
	public void open(int i, int j) {
		if (i <= 0 || i > side || j <= 0 || j > side)
			throw new IndexOutOfBoundsException("row index i or column index j out of bounds");
		if (!isOpen(i, j)) {
			int temp;
			int[] roots = {(i - 1) * side + j - 1, -1, -1, -1, -1};
			grid[roots[0]] = 1;
		if (i == 1)
			grid[roots[0]] *= 3; 
		if (i == side)
			grid[roots[0]] *= 2;
		if (j > 1 && isOpen(i, j - 1)) {
			temp = uf.find((i - 1) * side + j - 1 - 1);
			if (!((grid[temp] >= 3) && (grid[roots[0]] >= 3))) {
				roots[1] = temp;
				uf.union((i - 1) * side + j - 1, roots[1]);
				if(grid[roots[1]] * grid[roots[0]] % 6 == 0)
					grid[roots[0]] = 6;
				grid[roots[0]] = (grid[roots[1]] >= grid[roots[0]]) ? grid[roots[1]] : grid[roots[0]];
			}
		}
		if (j < side && isOpen(i, j + 1)) {
			temp = uf.find((i - 1) * side + j + 1 - 1);
			if ((!Arrays.asList(roots).contains(new Integer(temp))) && !((grid[temp] >= 3) && (grid[roots[0]] >= 3))) {
				roots[2] = temp;
				uf.union((i - 1) * side + j - 1, roots[2]);
				if(grid[roots[2]] * grid[roots[0]] % 6 == 0)
					grid[roots[0]] = 6;
				grid[roots[0]] = (grid[roots[2]] >= grid[roots[0]]) ? grid[roots[2]] : grid[roots[0]];
			}
		}
		if (i > 1 && isOpen(i - 1, j)) {
			temp = uf.find((i - 2) * side + j - 1);
			if ((!Arrays.asList(roots).contains(new Integer(temp))) && !((grid[temp] >= 3) && (grid[roots[0]] >= 3))) {
				roots[3] = temp;
				uf.union((i - 1) * side + j - 1, roots[3]);
				if(grid[roots[3]] * grid[roots[0]] % 6 == 0)
					grid[roots[0]] = 6;
				grid[roots[0]] = (grid[roots[3]] >= grid[roots[0]]) ? grid[roots[3]] : grid[roots[0]];
			}
		}
		if (i < side && isOpen(i + 1, j)) {
			temp = uf.find(i * side + j - 1);
			if ((!Arrays.asList(roots).contains(new Integer(temp))) && !((grid[temp] >= 3) && (grid[roots[0]] >= 3))) {
				roots[4] = temp;
				uf.union((i - 1) * side + j - 1, roots[4]);
				if(grid[roots[4]] * grid[roots[0]] % 6 == 0)
					grid[roots[0]] = 6;
				grid[roots[0]] = (grid[roots[4]] >= grid[roots[0]]) ? grid[roots[4]] : grid[roots[0]];
			}
		}
		if (grid[roots[0]] % 6 == 0)
			percolates = true;
		for (int k = 1; k < 5; k++)
			if (roots[k] >= 0 && grid[roots[k]] > 0)
				grid[roots[k]] = grid[roots[0]];
		}
	}

	/**
	 * is site (row i, column j) open?
	 * @param i row number of the site
	 * @param j column number of the site
	 * @return if the site is open
	 */
	public boolean isOpen(int i, int j) {
		if (i <= 0 || i > side || j <= 0 || j > side)
			throw new IndexOutOfBoundsException("row index i or column index j out of bounds");
		return grid[(i - 1) * side + j - 1] != 0;
	}

	/**
	 * is site (row i, column j) full?
	 * @param i row number of the site
	 * @param j column number of the site
	 * @return true if the site is full
	 */
	public boolean isFull(int i, int j) {
		if (i <= 0 || i > side || j <= 0 || j > side)
			throw new IndexOutOfBoundsException("row index i or column index j out of bounds");
		return grid[uf.find((i - 1) * side + j  - 1)] > 2;
	}

	/**
	 * does the system percolate?
	 * @return if the system percolate
	 */
	public boolean percolates() {
		return percolates;
	}
}
