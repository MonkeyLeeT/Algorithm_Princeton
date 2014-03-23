public class Percolation {
	private int side;
	private boolean percolates;
	private byte[] grid;
	private WeightedQuickUnionUF uf;

	public Percolation(int N) {
		grid = new byte[N * N];
		side = N;
		uf = new WeightedQuickUnionUF(N * N);
	}

	public void open(int i, int j) {
		if (i <= 0 || i > side || j <= 0 || j > side)
			throw new IndexOutOfBoundsException("row index i or column index j out of bounds");
		if (!isOpen(i, j)) {
			int temp; 
			int[] roots = {(i - 1) * side + j - 1, -1, -1, -1, -1}; 
			grid[roots[0]] = 1;
		if (i == 1)
			grid[roots[0]] |= 4; 
		if (i == side)
			grid[roots[0]] |= 2;
		if (i > 1 && grid[roots[0] - side] > 0) {
			temp = uf.find(roots[0] - side); 
			if (!((grid[temp] >= 4) && (grid[roots[0]] >= 4))) {
				roots[1] = temp; 
				uf.union(roots[0], roots[1]); 
				grid[roots[0]] |= grid[roots[1]];
			}
		}
		if (i < side && grid[roots[0] + side] > 0) {
			temp = uf.find(roots[0] + side);
			if (temp != roots[1] && !((grid[temp] >= 4) && (grid[roots[0]] >= 4))) {
				roots[2] = temp; 
				uf.union(roots[0], roots[2]); 
				grid[roots[0]] |= grid[roots[2]];
			}
		}
		if (j > 1 && grid[roots[0] - 1] > 0) {
			temp = uf.find(roots[0] - 1);
			if (temp != roots[2] && temp != roots[1] && !((grid[temp] >= 4) && (grid[roots[0]] >= 4))) {
				roots[3] = temp; 
				uf.union(roots[0], roots[3]); 
				grid[roots[0]] |= grid[roots[3]];
			}
		}
		if (j < side && grid[roots[0] + 1] > 0) {
			temp = uf.find(roots[0] + 1);
			if (temp != roots[3] && temp != roots[2] && temp != roots[1] && !((grid[temp] >= 4) && (grid[roots[0]] >= 4))) {
				roots[4] = temp; 
				uf.union(roots[0], roots[4]); 
				grid[roots[0]] |= grid[roots[4]];
			}
		}
		if (grid[roots[0]] == 7) {
			percolates = true;
		}
		for (int k = 1; k < 5; k++)
			if (roots[k] >= 0 && grid[roots[k]] > 0)
				grid[roots[k]] = grid[roots[0]];
		}
	}

	public boolean isOpen(int i, int j) {
		if (i <= 0 || i > side || j <= 0 || j > side)
			throw new IndexOutOfBoundsException("row index i or column index j out of bounds");
		return grid[(i - 1) * side + j - 1] > 0;
	}

	public boolean isFull(int i, int j) {
		if (i <= 0 || i > side || j <= 0 || j > side)
			throw new IndexOutOfBoundsException("row index i or column index j out of bounds");
		return isOpen(i, j) && grid[uf.find((i - 1) * side + j  - 1)] > 3;
	}

	public boolean percolates() {
		return percolates;
	}
}
