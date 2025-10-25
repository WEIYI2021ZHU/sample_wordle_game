import React, { useState } from "react";
import axios from "axios";
import WordleBoard from "./WordleBoard";
import "../styles/Game.css";

const API_BASE = "http://localhost:8080/api/game";

export default function Game() {
  const [sessionId, setSessionId] = useState(null);
  const [level, setLevel] = useState("EASY");
  const [guess, setGuess] = useState("");
  const [feedbacks, setFeedbacks] = useState([]);
  const [status, setStatus] = useState("NOT_STARTED");
  const [banner, setBanner] = useState(null);

  // Start new game
  const startGame = async () => {
    try {
      const res = await axios.post(`${API_BASE}/start?level=${level}`);
      setSessionId(res.data.sessionId);
      setFeedbacks([]);
      setStatus("IN_PROGRESS");
      setBanner(null);
    } catch (err) {
      console.error("Error starting game:", err);
    }
  };

  // Make a guess
  const makeGuess = async () => {
    if (!guess || guess.length !== 5) {
      alert("Please enter a 5-letter word.");
      return;
    }

    try {
      const res = await axios.post(`${API_BASE}/guess/${sessionId}`, {
        guess: guess.toLowerCase(),
      });

      setFeedbacks([...feedbacks, res.data]);
      setGuess("");

      if (res.data.status === "WIN") {
        setStatus("WIN");
        setBanner("🎉 You Win!");
      } else if (res.data.status === "LOSE") {
        setStatus("LOSE");
        setBanner("💀 Game Over!");
      }
    } catch (err) {
      console.error("Error making guess:", err);
    }
  };

  // Auto-restart after 3 seconds
  const restartAfterDelay = () => {
    setTimeout(() => {
      startGame();
    }, 3000);
  };

  return (
    <div className="game-container">
      <h1>Wordle Game</h1>

      {!sessionId && (
        <div className="start-panel">
          <label>Choose Level:</label>
          <select onChange={(e) => setLevel(e.target.value)} value={level}>
            <option value="EASY">Easy</option>
            <option value="MEDIUM">Medium</option>
            <option value="HARD">Hard</option>
            <option value="EXPERT">Expert</option>
          </select>
          <button onClick={startGame}>Start Game</button>
        </div>
      )}

      {status === "IN_PROGRESS" && sessionId && (
        <>
          <div className="guess-panel">
            <input
              type="text"
              value={guess}
              onChange={(e) => setGuess(e.target.value)}
              maxLength={5}
              placeholder="Enter 5-letter word"
            />
            <button onClick={makeGuess}>Guess</button>
          </div>
          <WordleBoard feedbacks={feedbacks} />
        </>
      )}

      {banner && (
        <div className={`banner ${status.toLowerCase()}`}>
          <p>{banner}</p>
        </div>
      )}
    </div>
  );
}
