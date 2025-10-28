# Wordle -- Full Stack Documentation


## Overview
Wordle is a full-stack web application built using **Spring Boot 3.0(Java 17)** for the backend and
**React JS** for the front end. <br>

It allows users to choose difficulty level and play a game or the user could choose multilple mode and
guess with their friends together.

A demo video is here: https://youtu.be/_xEZzNfBKj0

I have finished Task 1,2 and 4.

To run this built project, you could use the following commands.

```
cd demoWordle
java -jar target/demoWordle-0.0.1-SNAPSHOT.jar
```

Then open http://localhost:8080/ to play the game.

I plan to delpoy the game on AWS afterwards.

## Data Preprocessing

The raw data comes from WordNet 3.1.
I filtered the data by using wordfreq library and separate those words into 4 different levels.


## Backend

### Folder Structure

    src/main/java/com/weiyi/demoWordle/
     ├─ config/
     │   └─ WebSocketConfig.java
     │
     ├─ rest/
     │   ├─ WordController.java
     │   └─ WebSocketGameController.java
     │
     │   
     ├─ entity/
     │   ├─ FeedbackResult.java
     │   ├─ GameSession.java
     │   ├─ GameLevel.java
     │   ├─ GameMode.java
     │   ├─ GameRoom.java
     │   ├─ GameStatus.java
     │   ├─ GameResult.java
     │   ├─ GuessFeeback.java
     │   ├─ GuessMessage.java
     │   ├─ JoinMessage.java
     │   ├─ LetterFeedback.java
     │   └─ GameStartResponse.java
     │
     ├─ service/
     │   ├─ GameService.java
     │   ├─ LevelWordService.java
     │   └─ impl/
     │       ├─ GameServiceImpl.java
     │       └─ LevelWordServiceImpl.java
     │
     ├─ exception/
     │   ├─ GameException.java
     │   ├─ GameAlreadyEndedException.java
     │   ├─ GlobalExceptionHandler.java
     │   ├─ InvalidLengthException.java
     │   ├─ InvalidLetterException.java
     │   ├─ InvalidWordException.java
     │   ├─ PlayerException.java
     │   ├─ RoomFullException.java
     │   └─ SessionNotFoundException.java
     │
     └─ WordleApplication.java


- The config package contains websocket configuration which allows the real-time information transfer between multiple users
- The controller package contains WordController which controls the rest APIs and WebSocketGameController for managing real-time data transfer
- The entity package defines entities and enums
- The service package includes interface and implementations that return the results
- The exception package includes defined exceptions and the handler
- WordleApplication contains tha main function



### Resources

    src/main/resources/
     ├─ wordLists/
     │   ├─ easy_level_words.txt
     │   ├─ medium_level_words.txt
     │   ├─ hard_level_words.txt
     │   ├─ expert_level_words.txt
     │   └─ five_letter_words.txt
     └─ application.porperties

I used Python to hanlde the data preprocessing and copy paste the txt files under resources such that spring boot application could read.


### REST API Endpoints

Eg.

|Method|Endpoint|Description|Request|Response|
|-----|------------------------|-------------------|------------|------------|
|`POST`|`/api/wordle/start?level=EASY&mode=SINGLE`|simple mode| `GameLevel` (enum), `GameMode` (enum) | `GameSession`|
|`POST`|/api/wordle/guess/{sessionId}`| Submits a guess|  `{ "guess": "apple" }`  | `FeedbackResult`|


### Logic Summary

1.  `LevelWordServiceImpl` loads `.txt` files for each difficulty.
2.  `GameServiceImpl` creates a session with:
    -   Random word (answer)
    -   Max rounds based on difficulty
3.  `makeGuess()` compares each letter → generates color feedback.
4.  Session is updated and stored in a map.
5.  If correct → `WIN`; if rounds exceeded → `LOSE`.


## Frontend

### Technologies

- React.js
- Axios (REST requests)
- SockJS + STOMP.js (WebSocket client)
- CSS Modules

### Main Structure
    src/
     ├─ components/
     │   ├─ Game.jsx
     │   ├─ MultiplayerGame.jsx
     │   └─ WordleBoard.jsx
     ├─ styles/
     │   ├─ Game.css
     │   ├─ MultiplayerGame.css
     │   └─ WordleBoard.css
     ├─ App.jsx
     ├─ index.js
     └─ setupProxy.js



## Future Enhancements

-   **Dictionary:** I recommend using Merriam-Webster e-dictionary in the future.
-   **Leaderboard:** persistent scoring system using a database. To attach customers continue playing this game, a database is needed to allow the users register, log in and check their previous scores
-   **Real-time Tracking** Using kafka to handle the real-time multiple players' game at the same time
-   **Animations:** Unity WebGL embed.
