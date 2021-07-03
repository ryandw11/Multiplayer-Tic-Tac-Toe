package me.ryandw11.server;

import me.ryandw11.server.commands.outgoing.PlayerCountReturnCommand;
import me.ryandw11.server.game.Game;
import me.ryandw11.server.game.TicClient;
import me.ryandw11.server.game.util.Time;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The main class of the server.
 */
public class Main {
    // The queue of players.
    private final static Queue<TicClient> playerQueue = new LinkedBlockingQueue<>();
    // the list of active games.
    private final static List<Game> activeGames = new CopyOnWriteArrayList<>();
    // If the server should terminate.
    private static boolean shouldTerminate = false;

    public static boolean debug;
    public static boolean messages;


    public static void main(String[] args) throws IOException {
        Main.debug = false;

        Server server = new Server();
        Scanner scanner = new Scanner(System.in);
        // Ask the user for a port to run on. Start the server on a different thread.
        System.out.println("Please enter in a port to run on: ");
        int port = scanner.nextInt();
        Thread thread = new Thread(() -> {
            try {
                server.start(port);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        thread.start();
        System.out.println("Started Tic-Tac-Toe server on port " + port);
        System.out.println("Type 'help' to view the commands you can run!");

        // Start the game logic thread.
        Time gameTime = new Time();
        AtomicReference<Double> cooldown = new AtomicReference<>(0.6d);
        Thread gameLogic = new Thread(() -> {
            while (!shouldTerminate) {
                // Match players into games.
                while (playerQueue.size() >= 2) {
                    Game game = new Game(playerQueue.poll(), playerQueue.poll());
                    addGame(game);
                }
                // Update the games.
                for (Game game : activeGames) {
                    game.update();
                }
                // Remove disconnected players from the queue.
                playerQueue.removeIf(client -> client.getClient().isTerminated());
                cooldown.updateAndGet(v -> v - gameTime.getElapsedTime());
                if(cooldown.get() < 0){
                    for(TicClient ticClient : playerQueue){
                        ticClient.getClient().sendCommand(new PlayerCountReturnCommand());
                    }
                    cooldown.set(0.6d);
                }

            }
        });
        gameLogic.start();

        String input;
        // This handles the server command system.
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("stop")) {
                server.stop();
                shouldTerminate = true;
                System.exit(0);
                return;
            } else if (input.equalsIgnoreCase("players")) {
                System.out.println("There are " + activePlayerCount() + " players currently!");
            } else if (input.equalsIgnoreCase("games")) {
                System.out.println("There are " + activeGames.size() + " games currently!");
            } else if (input.equalsIgnoreCase("message")) {
                Main.messages = !Main.messages;
                System.out.println("Join/Leave message have been set to " + Main.messages + "!");
            } else if (input.equalsIgnoreCase("debug")) {
                Main.debug = !Main.debug;
                System.out.println("Debug mode has been set to " + Main.debug + "!");
            } else if (input.equalsIgnoreCase("help")) {
                System.out.println("=======[ Tic-Tac-Toe Server ]=======");
                System.out.println("debug - Toggle debug mode for the server.");
                System.out.println("games - The number of games that are currently playing.");
                System.out.println("message - Toggle whether the server sends out join and leave messages.");
                System.out.println("players - The number of players playing (including people waiting in to join a game).");
                System.out.println("stop - Stop the server (This command might cause the server to not close peacefully).");
            }
        }
    }

    /**
     * Add a client to the queue.
     *
     * @param client The client to add.
     */
    public static void addClient(TicClient client) {
        playerQueue.add(client);
    }

    /**
     * Add a game to the list.
     *
     * @param game The game to add.
     */
    public static void addGame(Game game) {
        activeGames.add(game);
    }

    /**
     * Remove a game from the list.
     *
     * @param game The game to remove.
     */
    public static void removeGame(Game game) {
        activeGames.remove(game);
    }

    /**
     * Get the active player count.
     *
     * @return The active player count.
     */
    public static int activePlayerCount() {
        return playerQueue.size() + (activeGames.size() * 2);
    }

    /**
     * Remove a client from the queue.
     *
     * @param client The client to remove.
     */
    public static void removeClient(TicClient client) {
        playerQueue.remove(client);
    }
}
