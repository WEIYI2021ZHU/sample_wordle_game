import React, { useState, useEffect } from "react";
import axios from "axios";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import WordleBoard from "./WordleBoard";

export default function MultiplayerGame({ goBack }) {
  const [sessionId, setSessionId] = useState("");
  const [playerId, setPlayerId] = useState("");
  const [opponentId, setOpponentId] = useState(null);
  const [feedbacks, setFeedbacks] = useState([]);
  const [guess, setGuess] = useState("");
  const [stompClient, setStompClient] = useState(null);
  const [banner, setBanner] = useState(null);

  const connectWebSocket = (sessionId, playerId) => {
    const socket = new SockJS("/ws");
    const client = Stomp.over(socket);
    client.connect({}, () => {
      client.subscribe(`/topic/wordle/${sessionId}`, (message) => {
        const data = JSON.parse(message.body);
        if (data.status) setBanner(`Game Update: ${data.status}`);
        setFeedbacks((prev) => [...prev, data]);
      });
      client.send("/app/join", {}, JSON.stringify({ sessionId, playerId }));
    });
    setStompClient(client);
  };

  const startGame = async () => {
    const res = await axios.post(`/api/wordle/multi/start?level=EASY&playerId=${playerId}`);
    setSessionId(res.data.sessionId);
    setOpponentId(res.data.opponentId);
    setBanner("🎮 Waiting for opponent...");
    connectWebSocket(res.data.sessionId, res.data.playerId);
  };

  const joinGame = async () => {
    const res = await axios.post(`/api/wordle/multi/join/${sessionId}?playerId=${playerId}`);
    setOpponentId(res.data.opponentId);
    setBanner(`Game started! You vs ${res.data.opponentId}`);
    connectWebSocket(sessionId, playerId);
  };

  const makeGuess = () => {
    if (stompClient && stompClient.connected) {
      stompClient.send("/app/guess", {}, JSON.stringify({ sessionId, playerId, guess }));
      setGuess("");
    }
  };

  return (
    <div className="game-container">
      {!sessionId && (
        <>
          <input placeholder="Your Name" value={playerId} onChange={(e) => setPlayerId(e.target.value)} />
          <button onClick={startGame}>Create Game</button>
          <button onClick={joinGame}>Join Game</button>
        </>
      )}
      {sessionId && (
        <>
          <WordleBoard feedbacks={feedbacks} />
          <input value={guess} onChange={(e) => setGuess(e.target.value)} placeholder="Your guess" />
          <button onClick={makeGuess}>Send Guess</button>
          {banner && <div className="banner">{banner}</div>}
        </>
      )}
      <button onClick={goBack}>⬅ Back</button>
    </div>
  );
}
