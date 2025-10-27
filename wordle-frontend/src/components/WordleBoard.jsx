import React from "react";
import "../styles/WordleBoard.css";

// display the words that users have entered
export default function WordleBoard({ feedbacks }) {
  return (
    <div className="board">
      {feedbacks.map((round, i) => (
        <div key={i} className="row">
          {round.guess.split("").map((ch, j) => (
            <div key={j} className={`box ${round.colors[j]}`}>
              {ch.toUpperCase()}
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}
