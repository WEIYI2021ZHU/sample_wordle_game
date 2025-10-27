import React, { useState, useEffect } from "react";
import axios from "axios";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import WordleBoard from "./WordleBoard";
import "../styles/Game.css";

const API_BASE = "http://localhost:8080/api/wordle";

export default function MultiplayerGame({ goBack }) {
  const [roomId, setRoomId] = useState("");
  const [playerId, setPlayerId] = useState("");
  const [opponentId, setOpponentId] = useState(null);
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
        // if (data.status.includes("joined")) {
        //   setOpponentId(data.playerId || "Unknown");
        // }
        // else {
        //   setFeedbacks((prev) => [...prev, data]);
        // }
         if (data.status === "WAITING_FOR_PLAYERS") {
            setBanner(`Waiting for opponent... Share this code: ${roomId}`);
            setStatus("WAITING_FOR_PLAYERS");
          } else if (data.status === "IN_PROGRESS") {
            setBanner("Both players joined! Game started.");
            setStatus("IN_PROGRESS");
          } else if (data.status === "WIN") {
            setBanner(`Player ${data.playerId} wins!`);
            setStatus("WIN");
          } else if (data.status === "LOSE") {
            setBanner("Game over! You lost.");
            setStatus("LOSE");
          } else {
            // Feedback or other game data
            setFeedbacks((prev) => [...prev, data]);
          }
        
      });
      client.send("/app/join", {}, JSON.stringify({ roomId, playerId }));
    });
    setStompClient(client);
  };


  const startGame = async () => {
    if (!playerId) return alert("Please Enter your name");
    try {
    const res = await axios.post(
      `${API_BASE}/multi/start?level=EASY&playerId=${playerId}`
    );

    const { roomId: newId } = res.data;

    if (!newId) throw new Error("No session ID returned from server.");

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
      setOpponentId(playerId);
      // setBanner("Joined game! Waiting for host...");
    } catch (err) {
      const message = err.response?.data?.error || "Error making guess. Please try again.";
      alert(message);
      alert("Invalid session ID or game not found.");
    }
  };

  const makeGuess = () => {
    if (!guess.trim()) return alert("Enter a word first!");
    if (!stompClient || !stompClient.connected)
      return alert("Not connected to the game yet!");

    stompClient.send(
      "/app/guess",
      {},
      JSON.stringify({ roomId, playerId, guess})
    );

    setGuess("");

  };

  return (
    <div className="game-container">
      {status === "NOT_STARTED" && (
        <>
          <input placeholder="Your Name" value={playerId} onChange={(e) => setPlayerId(e.target.value)} />
          <input value={roomId} onChange={(e) => setRoomId(e.target.value)} 
            placeholder="Enter session code to join"/>
          <button onClick={startGame}>Create Game</button>
          <button onClick={joinGame}>Join Game</button>
        </>
      )}
      
      {status === "WAITING_FOR_PLAYERS" && (
        <>
         {banner && <div className="banner">
          <button onClick={goBack}>Back</button>
        </div>
          }
          
        </>
      )}

      {status === "IN_PROGRESS" && roomId && (
        <>
          <WordleBoard feedbacks={feedbacks} />
          <input value={guess} onChange={(e) => setGuess(e.target.value)} placeholder="Your guess" />
          <button onClick={makeGuess}>Send Guess</button>
          {banner && <div className="banner">{banner}
            <button onClick={goBack}>Back</button>
          </div>}
        </>
      )}
      <button onClick={goBack}>⬅ Back</button>
    </div>
  );
}
