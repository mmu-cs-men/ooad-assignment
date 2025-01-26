package model.listeners;

import model.game.Player;

/**
 * Listener interface to be notified when a player wins the game.
 *
 * <p>This interface follows the Observer pattern, where implementing classes
 * observe and respond to relevant events (in this case, a player winning the
 * game).</p>
 * @author Harris Majeed
 */
public interface WinListener
{
    void onWin(Player player);
}