package view;

/**
 * The CellClickListener interface acts as an observer in the observer pattern.
 * Implementations of this interface are notified when a cell has been clicked,
 * providing a way to react to user interactions on specific grid or board cells.
 * @author Harris
 */
public interface CellClickListener
{
    void onCellClicked(int row, int col);

}