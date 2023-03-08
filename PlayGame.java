public class PlayGame {
    private String userChoice;
    private String computerChoice;
    private String result;
    private String leaderBoard;

    public PlayGame(String userChoice, String computerChoice, String LeaderBoard) {
        this.userChoice = userChoice;
        this.computerChoice = computerChoice;
        this.leaderBoard = LeaderBoard;
        setResult();
    }

    public String getUserChoice() {
        return userChoice;
    }

    public String getComputerChoice() {
        return computerChoice;
    }

    public String getResult() {
        return result;
    }

    private void setResult() {
        if (userChoice.equals(computerChoice)) {
            result = "It's a tie!";
        } else if ((userChoice.equals("rock") && computerChoice.equals("scissors"))
                || (userChoice.equals("paper") && computerChoice.equals("rock"))
                || (userChoice.equals("scissors") && computerChoice.equals("paper"))) {
            result = "You win!";
        } else {
            result = "You lose!";
        }
    }

    public String getLeaderBoard() {
        return leaderBoard;
    }

}
