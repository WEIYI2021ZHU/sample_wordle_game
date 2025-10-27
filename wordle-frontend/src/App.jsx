import React, { useState } from "react";
import Game from "./components/Game";
import MultiplayerGame from "./components/MultiplayerGame";
import "./App.css";

function App() {
  const [mode, setMode] = useState(null);

  return (
    <div className="App">
      <h1>Wordle Game</h1>

      {!mode && (
        <div className="mode-select">
          <button onClick={() => setMode("SINGLE")}>Single Player</button>
          <button onClick={() => setMode("MULTIPLE")}>Multiplayer</button>
        </div>
      )}

      {mode === "SINGLE" && <Game goBack={() => setMode(null)} />}
      {mode === "MULTIPLE" && <MultiplayerGame goBack={() => setMode(null)} />}
    </div>
  );
}


export default App;