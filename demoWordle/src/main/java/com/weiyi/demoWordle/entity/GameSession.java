package com.weiyi.demoWordle.entity;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private final String answer;
    private int currentRound;
    private final int maxRounds;
    private GameStatus status;
    private final List<String> history;
    private final List<List<String>> colorHistory;
    private String sessionId;

    // start a new session to guess
    public GameSession(String answer, int maxRounds) {
        this.answer = answer;
        this.maxRounds = maxRounds;
        this.currentRound = 0;
        this.status = GameStatus.IN_PROGRESS;
        this.history = new ArrayList<>();
        this.colorHistory = new ArrayList<>();
    }

    public FeedbackResult makeGuess(String guess) {
        guess = guess.toLowerCase();
        currentRound++;

        // check the guess and store the colors of each cell
        List<String> colors = evaluateColors(guess);
        history.add(guess);
        colorHistory.add(colors);

        if (guess.equals(answer)) {
            status = GameStatus.WIN;
        }
        else if (currentRound >= maxRounds) {
            status = GameStatus.LOSE;
        }

        return new FeedbackResult(guess, colors, status);
    }

    private List<String> evaluateColors(String guess) {
        // initially the colors are all gray
        List<String> colors = new ArrayList<>(List.of("gray", "gray", "gray", "gray", "gray"));
        char[] answerChars = answer.toCharArray();
        boolean[] used = new boolean[5];

        // First pass: greens. If the letter is right at the position
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answerChars[i]) {
                colors.set(i, "green");
                used[i] = true;
            }
        }

        // Second pass: yellows. If the answer contains the letter
        for (int i = 0; i < 5; i++) {
            if (!colors.get(i).equals("green")) {
                char g = guess.charAt(i);
                for (int j = 0; j < 5; j++) {
                    // mark all the same letters that are not in the right position
                    if (!used[j] && g == answerChars[j]) {
                        colors.set(i, "yellow");
                        used[j] = true;
                        break;
                    }
                }
            }
        }

        return colors;
    }

    // when pass to the nextRound, we would have hint
//    public void nextRound() {
//        this.currentRound++;
//        if (currentRound >= maxRounds && status == GameStatus.IN_PROGRESS) {
//            status = GameStatus.LOSE;
//        }
//    }

//    public boolean checkGuess(String guess) {
//        if (guess.equalsIgnoreCase(answer)) {
//            status = GameStatus.WIN;
//            return true;
//        }
//        nextRound();
//        return false;
//    }

    public String getAnswer() {
        return answer;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public GameStatus getStatus() {
        return status;
    }

    public List<String> getHistory() {
        return history;
    }

    public List<List<String>> getColorHistory() {
        return colorHistory;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
