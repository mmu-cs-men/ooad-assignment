package model.pieces;

import model.game.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class PieceFactory {

    private static final Map<String, BiFunction<Player, Boolean, Piece>> pieceCreators = new HashMap<>();

    static {
        pieceCreators.put("ram_red", (player, flipped) -> new Ram(player, flipped));
        pieceCreators.put("ram_blue", (player, flipped) -> new Ram(player, flipped));
        pieceCreators.put("biz_red", (player, flipped) -> new Biz(player));
        pieceCreators.put("biz_blue", (player, flipped) -> new Biz(player));
        pieceCreators.put("xor_red", (player, flipped) -> new Xor(player));
        pieceCreators.put("xor_blue", (player, flipped) -> new Xor(player));
        pieceCreators.put("tor_red", (player, flipped) -> new Tor(player));
        pieceCreators.put("tor_blue", (player, flipped) -> new Tor(player));
        pieceCreators.put("sau_red", (player, flipped) -> new Sau(player));
        pieceCreators.put("sau_blue", (player, flipped) -> new Sau(player));
    }

    public static Piece createPiece(String type, Player player) {
        String key = type.toLowerCase();
        BiFunction<Player, Boolean, Piece> creator = pieceCreators.get(key);
        if (creator != null) {
            return creator.apply(player, key.contains("flipped"));
        } else {
            throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }
}