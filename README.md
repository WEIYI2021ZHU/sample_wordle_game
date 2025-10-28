# Wordle -- Full Stack Documentation


## Overview
Wordle is a full-stack web application built using **Spring Boot 3.0(Java 17)** for the backend and
**React JS** for the front end. <br>

It allows users to choose difficulty level and play a game or the user could choose multilple mode and
guess with their friends together.

A demo video is here: https://youtu.be/_xEZzNfBKj0

To run this built project, you could use the following commands.

```
cd demoWordle
java -jar target/demoWordle-0.0.1-SNAPSHOT.jar
```

Then use the link http://localhost:8080/ to play the game.

I plan to delpoy the game on AWS afterwards.

## Data Preprocessing






## Backend

### Folder Structure

    src/main/java/com/weiyi/demoWordle/
     ├─ config/
     │   └─ GameController.java
     │
     │
     ├─ rest/
     │   └─ GameController.java
     ├─ entity/
     │   ├─ GameSession.java
     │   ├─ FeedbackResult.java
     │   ├─ GameLevel.java
     │   ├─ GameStatus.java
     │   └─ GameStartResponse.java
     ├─ service/
     │   ├─ GameService.java
     │   ├─ LevelWordService.java
     │   └─ impl/
     │       ├─ GameServiceImpl.java
     │       └─ LevelWordServiceImpl.java
     └─ WordleApplication.java







### Resources

    src/main/resources/
     ├─ wordlist/
     │   ├─ easy_level_words.txt
     │   ├─ medium_level_words.txt
     │   ├─ hard_level_words.txt
     │   ├─ expert_level_words.txt
     │   └─ five_letter_words.txt
     └─ application.porperties



### API Endpoints



|Method|Endpoint|Description|Request|Response|
|-----|------------------------|-------------------|------------|------------|
|`POST`|
|`POST`|


## Frontend


## Future Enhancements

-   **Dictionary:** I recommend using Merriam-Webster e-dictionary in the future.
-   **Leaderboard:** persistent scoring system using a database. To attach customers continue playing this game, a database is needed to allow the users register, log in and check their previous scores
-   **Real-time Tracking** Using kafka to handle the real-time multiple players' game at the same time
-   **Animations:** Unity WebGL embed.
