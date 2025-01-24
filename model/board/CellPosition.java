package model.board;

/**
 * Represents a position on {@link Board}.
 * <p>
 * This is a convenience class designed to be an immutable representation of a
 * position on the board.
 * <p>
 * This data structure is used in place of specifying row and column
 * individually. It's a small thing but helps keep the code cleaner.
 *
 * @param row    the row index of the position
 * @param column the column index of the position
 * @author Harris Majeed
 * @see Cell
 */
public record CellPosition(int row, int column)
{

}
