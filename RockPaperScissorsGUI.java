import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RockPaperScissorsGUI extends JFrame {
    private JPanel panel;
    private JLabel userLabel, computerLabel, resultLabel, leaderboardLabel, nameLabel, nameField;
    private JButton rockButton, paperButton, scissorsButton, restartButton;
    private int userScore = 0, computerScore = 0, round = 0;
    private static Leaderboard leaderboard = new Leaderboard();

    public RockPaperScissorsGUI() {
        panel = new JPanel(new GridLayout(6, 3));
        userLabel = new JLabel("Your choice:");
        userLabel.setForeground(Color.BLUE);
        computerLabel = new JLabel("Computer's choice:");
        computerLabel.setForeground(Color.BLUE);
        resultLabel = new JLabel("Round " + (round + 1) + ": Choose your weapon!");
        resultLabel.setForeground(Color.RED);
        leaderboardLabel = new JLabel("Leaderboard: ");
        leaderboardLabel.setForeground(Color.RED);
        nameLabel = new JLabel("Enter your name:");
        nameLabel.setForeground(Color.BLACK);

        JTextField nameField = new JTextField(" ");
        nameField.setForeground(Color.BLACK);

        JButton submitButton = new JButton("Submit");
        submitButton.setForeground(Color.GREEN);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
                leaderboard.addPlayer(new Player(playerName, userScore));
                leaderboardLabel.setText("Leaderboard: " + " Name: " + playerName + " Highest Score: " + userScore);
            }
        });

        rockButton = new JButton("Rock");
        rockButton.setBackground(Color.GRAY);
        rockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRound("Rock");
            }
        });

        paperButton = new JButton("Paper");
        paperButton.setBackground(Color.GRAY);
        paperButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRound("Paper");
            }
        });

        scissorsButton = new JButton("Scissors");
        scissorsButton.setBackground(Color.GRAY);
        scissorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRound("Scissors");
            }
        });

        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });

        panel.add(userLabel);
        panel.add(computerLabel);
        panel.add(rockButton);
        panel.add(paperButton);
        panel.add(scissorsButton);
        panel.add(resultLabel);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(submitButton);
        panel.add(leaderboardLabel);
        panel.add(restartButton);

        add(panel);

        setTitle("Rock Paper Scissors");
        setSize(600, 500);
        getContentPane().setBackground(Color.BLUE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void playRound(String userChoice) {
        String computerChoice = getComputerChoice();
        String result = getResult(userChoice, computerChoice);

        userLabel.setText("Your choice: " + userChoice);
        computerLabel.setText("Computer's choice: " + computerChoice);
        resultLabel.setText(result);
        leaderboardLabel.setText("Leaderboard: " + nameField + userScore);

        round++;
        if (round == 3) {
            endGame();
        }
    }

    private String getComputerChoice() {
        int random = (int) (Math.random() * 3);
        String choice = "";
        switch (random) {
            case 0:
                choice = "Rock";
                break;
            case 1:
                choice = "Paper";
                break;
            case 2:
                choice = "Scissors";
                break;
        }
        return choice;
    }

    private String getResult(String userChoice, String computerChoice) {
        String result = "";
        if (userChoice.equals(computerChoice)) {
            result = "It's a tie!";
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors"))
                || (userChoice.equals("Paper") && computerChoice.equals("Rock"))
                || (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            result = "You win!";
            userScore++;
        } else {
            result = "You lose!";
            computerScore++;
        }
        return result + " Score: You " + userScore + ", Computer " + computerScore;
    }

    private void endGame() {
        String message = "";
        if (userScore > computerScore) {
            message = "You won the game!";
        } else if (computerScore > userScore) {
            message = "You lost the game!";
        } else {
            message = "The game ended in a tie!";
        }
        JOptionPane.showMessageDialog(null,
                message + " Submit your name to be added to the Leader Board. Press restart to play again.");
    }

    public void restart() {
        computerScore = 0;
        userLabel.setText("Player: 0");
        computerLabel.setText("Computer: 0");
        resultLabel.setText("");
        computerLabel.setText("");
        return;

    }
}
