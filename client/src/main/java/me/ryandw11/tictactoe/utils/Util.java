package me.ryandw11.tictactoe.utils;

import me.ryandw11.tictactoe.Player;

/**
 * This is a utilit
 */
public class Util {

    public static Player getOppositePlayer(Player player){
        return player == Player.PLAYER_ONE ? Player.PLAYER_TWO : Player.PLAYER_ONE;
    }

}
