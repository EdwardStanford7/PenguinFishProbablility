/*
    Version of the problem in Rust
*/

use rand::Rng;
use std::collections::HashSet;
use std::hash::Hash;
use std::io::{self, Write};
use std::iter;

/*
    Compile-time constants
*/
// Dimensions of the grid
// The padded grid contains one additional initial and final row/col
const ROWS: usize = 42;
const COLS: usize = 49;
const PAD_ROWS: usize = ROWS + 2;
const PAD_COLS: usize = COLS + 2;

// Number of iterations to run
const NUM_ITERS: usize = 1000000;
// Number of steps to display progress
// (Should be a divisor of NUM_ITERS)
const PROGRESS_STEP: usize = 1000;

/*
    Generic depth-first search
*/
fn dfs<T, Src, Succ, Succs, Snk>(
    sources: Src,
    get_succs: Succ,
    is_sink: Snk,
) -> bool
where
    T: Clone + Hash + Eq,
    Src: Iterator<Item = T>,
    Succ: Fn(&T) -> Succs,
    Succs: Iterator<Item = T>,
    Snk: Fn(&T) -> bool,
{
    let mut visited: HashSet<T> = HashSet::new();
    let mut to_visit: Vec<T> = sources.collect();
    while let Some(curr) = to_visit.pop() {
        if visited.contains(&curr) {
            continue;
        } else if is_sink(&curr) {
            return true;
        } else {
            visited.insert(curr.clone());
            for next in get_succs(&curr) {
                to_visit.push(next);
            }
        }
    }
    false
}

/*
    Grid struct
*/
struct Grid([[bool; PAD_COLS]; PAD_ROWS]);
impl Grid {
    #[allow(clippy::needless_range_loop)]
    fn new_random() -> Self {
        let mut rng = rand::thread_rng();
        let mut grid = [[false; PAD_COLS]; PAD_ROWS];
        for row in 1..=ROWS {
            for col in 1..=COLS {
                grid[row][col] = rng.gen();
            }
        }
        Self(grid)
    }

    fn cell(&self, i: usize, j: usize) -> bool {
        self.0[i][j]
    }

    fn is_sink(&self, _i: usize, j: usize) -> bool {
        j == COLS
    }
    fn sources(&self) -> impl Iterator<Item = (usize, usize)> + '_ {
        (1..=ROWS).map(|i| (i, 1)).filter(|&(i, j)| self.cell(i, j))
    }
    fn adjacencies(
        &self,
        i: usize,
        j: usize,
    ) -> impl Iterator<Item = (usize, usize)> + '_ {
        iter::once((i - 1, j))
            .chain(iter::once((i + 1, j)))
            .chain(iter::once((i, j - 1)))
            .chain(iter::once((i, j + 1)))
            .filter(|&(r, c)| self.cell(r, c))
    }

    // Check if the fish can get across swimming only on 'true' cells
    fn fish_friendly(&self) -> bool {
        dfs(
            self.sources(),
            |&(i, j)| self.adjacencies(i, j),
            |&(i, j)| self.is_sink(i, j),
        )
    }
}

fn main() {
    println!("Running {} iterations for {} x {} grids", NUM_ITERS, ROWS, COLS);
    let mut friendly: usize = 0;
    for epoch in 0..(NUM_ITERS / PROGRESS_STEP) {
        print!(
            "Running iterations {}-{}...",
            epoch * PROGRESS_STEP,
            (epoch + 1) * PROGRESS_STEP - 1,
        );
        io::stdout().flush().unwrap();

        let mut new_friendly: usize = 0;
        for _ in 0..PROGRESS_STEP {
            let grid = Grid::new_random();
            if grid.fish_friendly() {
                new_friendly += 1;
            }
        }
        println!(" {} friendly", new_friendly);
        friendly += new_friendly;
    }

    println!("=== Results for {} x {} grids ===", ROWS, COLS);
    println!(
        "The fish can swim across in {}/{} cases ({:.3}%).",
        friendly,
        NUM_ITERS,
        (friendly as f64) * 100.0 / (NUM_ITERS as f64),
    );
}
