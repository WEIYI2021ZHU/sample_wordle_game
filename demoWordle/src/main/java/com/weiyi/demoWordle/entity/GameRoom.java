package com.weiyi.demoWordle.entity;

import com.weiyi.demoWordle.exception.PlayerException;
import com.weiyi.demoWordle.exception.RoomFullException;
import lombok.Data;

import java.util.*;

// this is the space for multiple users
public class GameRoom {
    private String roomId;
    private final String answer;
    private final int maxRounds;

    private GameStatus status = GameStatus.WAITING_FOR_PLAYERS;
    private int currentRound;
    private Set<String> players;

    // shared state for all players
    private List<String> guesses;
    private List<List<String>> colorHistory;


    public GameRoom(String roomId, String answer, int maxRounds) {
        this.roomId = roomId;
        this.answer = answer;
        this.maxRounds = maxRounds;
        this.currentRound = 0;
        this.players= new HashSet<>();
        this.guesses = new ArrayList<>();
        this.colorHistory = new ArrayList<>();
    }

    public void addPlayer(String playerId) {
        if (players.contains(playerId))
            throw new PlayerException(playerId);
        if (players.size() >= 2)
            throw new RoomFullException(roomId);

        players.add(playerId);

        if (players.size() == 2) {
            status = GameStatus.IN_PROGRESS;
        }
    }

    public FeedbackResult makeGuess(String playerId, String guess) {
        guess = guess.toLowerCase();
        currentRound++;
        guesses.add(guess);

        List<String> colors = evaluateColors(guess);
        colorHistory.add(colors);

        FeedbackResult feedback = new FeedbackResult(guess, colors, status, answer);

        if (guess.equals(answer)) {
            status = GameStatus.WIN;
            feedback.setStatus(status);
//            feedback.setMessage(playerId + " guessed correctly!");
            feedback.setMessage(playerId);
        } else if (currentRound >= maxRounds) {
            status = GameStatus.LOSE;
            feedback.setStatus(status);
            feedback.setMessage("Game over! The word was: " + answer);
        } else {
            feedback.setMessage(playerId + " guessed: " + guess);
        }

        return feedback;
    }

    private List<String> evaluateColors(String guess) {
        // initially the colors are all gray
        List<String> colors = new ArrayList<>(List.of("gray", "gray", "gray", "gray", "gray"));

        for (int i = 0; i < 5; i++) {
            // First pass: greens. If the letter is right at the position
            if (guess.charAt(i) == answer.charAt(i)) {
                colors.set(i, "green");
            }
            // Second pass: yellows. If the answer contains the letter
            else if (answer.indexOf(guess.charAt(i)) != -1) {
                colors.set(i, "yellow");
            }
        }

        return colors;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getAnswer() {
        return answer;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<String> guesses) {
        this.guesses = guesses;
    }

    public List<List<String>> getColorHistory() {
        return colorHistory;
    }

    public void setColorHistory(List<List<String>> colorHistory) {
        this.colorHistory = colorHistory;
    }
}
