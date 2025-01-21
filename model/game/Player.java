package model.game;

public record Player(String id)
{
    public String getDisplayName()
    {
        return "Player " + id + " (" + color + ")";
    }

}