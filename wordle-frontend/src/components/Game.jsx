import React, { useState } from "react";
import axios from "axios";
import WordleBoard from "./WordleBoard";
import "../styles/Game.css";

const API_BASE = "http://localhost:8080/api/wordle";
// const API_BASE = "/api/wordle";

function Game({ goBack }) {
  // connect it with the backend
  const [sessionId, setSessionId] = useState(null);
  // by default the level is set to EASY
  const [level, setLevel] = useState("EASY");
  const [guess, setGuess] = useState("");
  const [feedbacks, setFeedbacks] = useState([]);
  const [status, setStatus] = useState("NOT_STARTED");
  const [banner, setBanner] = useState(null);
  const [error, setError] = useState(null);

  // Start new game
  const startGame = async () => {
    try {
      const res = await axios.post(`${API_BASE}/start?level=${level}&mode=SINGLE`);
      setSessionId(res.data.sessionId);
      setGuess("");
      setError("");
      setFeedbacks([]);
      setStatus("IN_PROGRESS");
      setBanner(null);
    } catch (err) {
      console.error("Error starting game:", err);
    }
  };

  // Make a guess
  const makeGuess = async () => {
    try {
      const res = await axios.post(`${API_BASE}/guess/${sessionId}`, {
        guess: guess.toLowerCase(),
      });

      setFeedbacks([...feedbacks, res.data]);
      setGuess("");

      if (res.data.status === "WIN") {
        setStatus("WIN");
        setBanner(`Congratulations! You Win! The correct word is: ${res.data.answer.toUpperCase()}.`);
        // setBanner("You Win!!!")
      } else if (res.data.status === "LOSE") {
        setStatus("LOSE");
        setBanner(`Game Over! The answer is: ${res.data.answer.toUpperCase()}.`);
      }
    } catch (err) {
      // Catch invalid word from backend
      const message = err.response?.data?.error || "Error making guess. Please try again.";
      setError(message);

      // alert user for the input errors
      alert(message);
    }
  };

  //xAuto-restart after 3 seconds
  const restartAfterDelay = () => {
    setTimeout(() => {
      startGame();
    }, 3000);
  };

  return (
    <div className="game-container">

      {!sessionId && (
        <div className="start-panel">
          <label>Choose Level:</label>
          <select onChange={(e) => setLevel(e.target.value)} value={level}>
            <option value="EASY">Easy</option>
            <option value="MEDIUM">Medium</option>
            <option value="HARD">Hard</option>
            <option value="EXPERT">Expert</option>
          </select>
          <br/>
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
          <button
          className="restart-btn"
          onClick={startGame}
          style={{ marginTop: "20px" }}>
          New Game
        </button>
        <button onClick={goBack}>⬅ Back</button>
        </div>
      )}

      {/* After the current game, the user could start a new game*/}
{/*      {(status === "WIN" || status === "LOSE") && (
        <div>
          <button
          className="restart-btn"
          onClick={startGame}
          style={{ marginTop: "20px" }}>
          New Game
        </button>
        </div>
      )}*/}
    </div>
  );
}
export default Game;
