package model.listeners;

import model.game.Player;

/**
 * Listener interface to be notified when a player wins the game.
 *
 * @author Harris Majeed
 */
public interface WinListener
{
    void onWin(Player player);
}