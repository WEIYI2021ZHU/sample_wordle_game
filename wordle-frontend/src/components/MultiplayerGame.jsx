import React, { useState, useEffect } from "react";
import axios from "axios";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import WordleBoard from "./WordleBoard";
import "../styles/Game.css";
import "../styles/MultiplayerGame.css";

const API_BASE = "http://localhost:8080/api/wordle";

export default function MultiplayerGame({ goBack }) {
  const [roomId, setRoomId] = useState("");
  const [level, setLevel] = useState("EASY");
  const [playerId, setPlayerId] = useState("");
  const [feedbacks, setFeedbacks] = useState([]);
  const [guess, setGuess] = useState("");
  const [stompClient, setStompClient] = useState(null);
  const [banner, setBanner] = useState(null);
  const [status, setStatus] = useState("NOT_STARTED");

  const connectWebSocket = (roomId, playerId) => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = Stomp.over(socket);
    client.connect({}, () => {
      console.log("Connected to WebSocket");
      client.subscribe(`/topic/wordle/${roomId}`, (message) => {
        const data = JSON.parse(message.body);
        console.log("Received:", data);

          if (data.error) {
            alert(`${data.text}`);
            return;
          }

         // At first, wait for another player
         if (data.status === "WAITING_FOR_PLAYERS") {
            setBanner(`Waiting for opponent... Share this code: ${roomId}`);
            setStatus("WAITING_FOR_PLAYERS");
          }
          else if (data.status === "IN_PROGRESS" && data.guess && data.colors) {
              // this is a feedback result
            setFeedbacks((prev) => [...prev, data]);
            if (data.status) {
              setStatus(data.status);
            }
            // if (data.status === "WIN") {
            //   setBanner(`Congratulations! The answer is ${data.answer.toUpperCase()}`);
            //   setStatus("WIN");
            // }
            // else if (data.status === "LOSE") {
            //   setBanner(`Game over! You lost. The answer is ${data.answer.toUpperCase()}`);
            //   setStatus("LOSE");
            // }
          } 
          else if (data.status === "IN_PROGRESS") {
            setBanner("Both players joined! Game started.");
            setStatus("IN_PROGRESS");
          } else if (data.status === "WIN") {
            setBanner(`Congratulations to you all! The answer is ${data.answer.toUpperCase()}`);
            setStatus("WIN");
          } else if (data.status === "LOSE") {
            setBanner(`Game over! You lost. The answer is ${data.answer.toUpperCase()}`);
            setStatus("LOSE");
          }
        
      });

      client.subscribe("/topic/errors", (message) => {
      const data = JSON.parse(message.body);
      console.error("WebSocket error:", data.message);
      alert(`${data.message}`);
      });

      client.send("/app/join", {}, JSON.stringify({ roomId, playerId }));
    });
    setStompClient(client);
  };


  const startGame = async () => {
    if (!playerId) return alert("Please Enter your name");
    try {
    const res = await axios.post(
      `${API_BASE}/multi/start?level=${level}&playerId=${playerId}`
    );

    // in case the parameter is not parsed properly, give roomId a new name
    const { roomId: newId } = res.data;

    if (!newId) throw new Error("No Room ID returned from server.");

    setRoomId(newId);
    setBanner(`Waiting for opponent... Share this code: ${newId}`);
    setStatus("WAITING_FOR_PLAYERS");

    setTimeout(() => connectWebSocket(newId, playerId), 200);
  } catch (err) {
    console.error("Failed to start game:", err);

    // Try to extract backend message
    const message =
      err.response?.data?.message ||
      err.response?.data?.error ||
      err.message ||
      "Unknown error starting game.";

    // Show to user
    alert(`Error starting game: ${message}`);

    // Optional: show banner so UI doesn’t stay blank
    setBanner("Failed to start game. Please try again.");
    setStatus("NOT_STARTED");
  }
  };


  const joinGame = async () => {
    if (!playerId || !roomId) return alert("Enter both your name and session code!");
    try {
      // const res = await axios.post("/app/guess");
      setStatus("IN_PROGRESS");
      connectWebSocket(roomId, playerId);
      // setOpponentId(playerId);
      // setBanner("Joined game! Waiting for host...");
    } catch (err) {
      const message = err.response?.data?.error || "Error joining. Please try again.";
      alert(message);
      alert("Invalid session ID or game not found.");
    }
  };

  const makeGuess = () => {
    if (!guess.trim()) return alert("Enter a word first!");
    if (!stompClient || !stompClient.connected)
      return alert("Not connected to the game yet!");

    try {
      stompClient.send(
        "/app/guess",
        {},
        JSON.stringify({ roomId, playerId, guess})
      );
      setGuess("");
    }
    catch (err) {
      alert("Failed to send your guess.");
    }
    

  };

  return (
    <div className="game-container">
      {status === "NOT_STARTED" && (
        <>
          <input placeholder="Your Name" value={playerId} className = "input_player_name"
            onChange={(e) => setPlayerId(e.target.value)} />
          <input value={roomId} className = "input_roomId"
            onChange={(e) => setRoomId(e.target.value)} 
            placeholder="Enter room code to join"/>
          <label>Choose Level:</label>
          <select onChange={(e) => setLevel(e.target.value)} value={level}>
            <option value="EASY">Easy</option>
            <option value="MEDIUM">Medium</option>
            <option value="HARD">Hard</option>
            <option value="EXPERT">Expert</option>
          </select>
          <button onClick={startGame}>Create Room</button>
          <button onClick={joinGame}>Join Game</button>
          <button onClick={goBack}>Back</button>
        </>
      )}
      
      {status === "WAITING_FOR_PLAYERS" && banner && (
        <div className="banner">
          {banner}
          <br></br>
          <button onClick={goBack}>Back</button>
        </div>
      )}

      {status === "IN_PROGRESS" && roomId && (
        <>
          {playerId && <label>Player: {playerId}</label>}
          <input value={guess} maxLength={5} className="guess-input"
            onChange={(e) => setGuess(e.target.value)} placeholder="Your guess" />
          <button onClick={makeGuess} className="guess-button">Send Guess</button>
          <WordleBoard feedbacks={feedbacks} />
        </>
      )}

      {(status === "WIN" || status === "LOSE") && banner && (
          <div className="banner">
          {banner}
          <br/>
          <button onClick={goBack}>Back</button>
        </div>
        )
      }
    </div>
  );
}
