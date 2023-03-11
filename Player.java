class Player {

    private String name;
    private int score;
    private String dob;

    Player(String name, String dob) {
        this.name = name;
        this.dob = dob;
    }

    String getName() {
        return name;
    }

    String getDob() {
        return dob;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }
}