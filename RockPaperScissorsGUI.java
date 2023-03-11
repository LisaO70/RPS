import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RockPaperScissorsGUI extends JFrame {
    private JPanel panelMenu;
    private JPanel panelGame;
    private JLabel userLabel, computerLabel, resultLabel;
    private JTextField dobField, nameField;
    private int userScore = 0, computerScore = 0, round = 0;
    private Leaderboard leaderboard = new Leaderboard();
    private Player currentPlayer;

    public RockPaperScissorsGUI() {
        super("Rock Paper Scissors");

        createMenuGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void createMenuGUI() {
        panelMenu = new JPanel();

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        JPanel namePanel = new JPanel();
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // date of birth
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobField = new JTextField(10);
        JPanel dobPanel = new JPanel();
        dobPanel.add(dobLabel);
        dobPanel.add(dobField);

        // leaderboard
        var topPlayers = leaderboard.getTopPlayers(10);

        String[] leaderboardData = new String[topPlayers.size()];
        for (int i = 0; i < topPlayers.size(); i++) {
            leaderboardData[i] = topPlayers.get(i).getName() + " / " + topPlayers.get(i).getDob() + " / "
                    + topPlayers.get(i).getScore();
        }

        JLabel leaderboardLabel = new JLabel("Leaderboard - Name, DoB & Highest Score:");
        JList<String> leaderboardList = new JList<>(leaderboardData);
        JScrollPane scrollPane = new JScrollPane(leaderboardList);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.add(leaderboardLabel);
        leaderboardPanel.add(scrollPane);

        // start button
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Game");
        startButton.setForeground(Color.GREEN);
        startButton.setPreferredSize(new Dimension(100, 100));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkDetails();
            }
        });
        buttonPanel.add(startButton);

        panelMenu.setLayout(new GridLayout(3, 1));
        panelMenu.add(namePanel);
        panelMenu.add(dobPanel);
        panelMenu.add(leaderboardPanel);
        panelMenu.add(buttonPanel);

        getContentPane().removeAll();
        getContentPane().add(panelMenu);
        pack();
        setVisible(true);
    }

    // creating the game GUI
    private void createGameGUI(Player currentPlayer) {
        panelGame = new JPanel();
        userScore = 0;
        computerScore = 0;
        round = 0;

        // user label
        userLabel = new JLabel(currentPlayer.getName() + ": 0 vs Computer: 0");
        userLabel.setForeground(Color.BLUE);
        JPanel scorePanel = new JPanel();
        scorePanel.add(userLabel);

        // creating the game buttons

        JPanel buttonPanelrps = new JPanel();
        buttonPanelrps.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 30));
        getContentPane().setBackground(Color.BLUE);

        JButton rockButton = new JButton("Rock");
        rockButton.setPreferredSize(new Dimension(200, 100));
        rockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRound("Rock");
            }
        });

        JButton paperButton = new JButton("Paper");
        paperButton.setPreferredSize(new Dimension(200, 100));
        paperButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRound("Paper");
            }
        });
        JButton scissorsButton = new JButton("Scissors");
        scissorsButton.setPreferredSize(new Dimension(200, 100));
        scissorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRound("Scissors");
            }
        });

        // Adding game buttons
        buttonPanelrps.add(rockButton);
        buttonPanelrps.add(paperButton);
        buttonPanelrps.add(scissorsButton);

        // Computer choice label
        JPanel computerPanel = new JPanel();
        computerLabel = new JLabel("Computer's choice: ");
        computerLabel.setForeground(Color.BLUE);
        computerPanel.add(computerLabel);

        // Result label
        JPanel resultPanel = new JPanel();
        resultLabel = new JLabel("Enjoy the game!");
        resultLabel.setForeground(Color.RED);
        resultPanel.add(resultLabel);

        panelGame.setLayout(new BoxLayout(panelGame, BoxLayout.Y_AXIS));
        panelGame.add(scorePanel);
        panelGame.add(buttonPanelrps);
        panelGame.add(computerPanel);
        panelGame.add(resultPanel);

        getContentPane().removeAll();
        getContentPane().add(panelGame);
        pack();
        setVisible(true);
    }

    private void checkDetails() {
        String name = nameField.getText();
        String dob = dobField.getText();
        if (name.isEmpty() || dob.isEmpty()) {
            JLabel label = new JLabel("Please enter your name and date of birth!");
            label.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, label, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            currentPlayer = new Player(name, dob);
            createGameGUI(currentPlayer);
        }
    }

    private void playRound(String userChoice) {
        String computerChoice = getComputerChoice();
        String result = getResult(userChoice, computerChoice);

        userLabel.setText("Your choice: " + userChoice);
        computerLabel.setText("Computer's choice: " + computerChoice);
        resultLabel.setText(result);

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
        currentPlayer.setScore(userScore);
        leaderboard.addPlayer(currentPlayer);
        createMenuGUI();
        String message = "";
        if (userScore > computerScore) {
            message = currentPlayer.getName() + " won the game!";
        } else if (computerScore > userScore) {
            message = currentPlayer.getName() + " lost the game!";
        } else {
            message = "The game ended in a tie!";
        }
        JOptionPane.showMessageDialog(null, "The computer chose " + getComputerChoice() + " " + message);
    }
}
