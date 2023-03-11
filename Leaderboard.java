import java.util.ArrayList;
import java.util.List;

public class Leaderboard {

    private List<Player> players;

    public Leaderboard() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getTopPlayers(int count) {
        return players.subList(0, Math.min(count, players.size()));
    }

    public List<Player> getAllPlayers() {
        return players;
    }
}
