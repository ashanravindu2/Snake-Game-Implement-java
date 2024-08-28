# Snake Game

## Description
Snake Game is a classic arcade game where the player controls a snake that moves around the screen, eating food pellets to grow longer. The game continues until the snake collides with itself or the boundaries of the play area. It's a simple yet addictive game that tests the player's reflexes and strategy.

![Screenshot](src/main/resources/Screenshots/Screenshot%202024-02-21%20132413.png)


![Screenshot](src/main/resources/Screenshots/Screenshot%202024-02-21%20132441.png)


![Screenshot](src/main/resources/Screenshots/Screenshot%202024-02-21%20132518.png)




## Features
- Classic snake gameplay with intuitive controls.
- Increasing difficulty as the snake grows longer.
- Score tracking to keep track of the player's progress.
- Sound effects for enhanced gameplay experience.
- Leaderboard to display top scores.

## Technologies Used
- JavaFX for the user interface.
- Java programming language for game logic.
- Git for version control.
- IDE: IntelliJ IDEA

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed on your system.
- Git installed on your system (optional).
- MySQL installed on your system.

### Installation
1. Clone the repository:
  git clone https://github.com/ashanravindu2/Snake-Game-Implement-java.git

2. Open the project in your preferred IDE.
3. Build and run the project.

### Setting Up the Database
1. Navigate to the `src/main/resources/sql` directory in your project.
2. Execute the SnakeGame.sql script to create the database schema:
  mysql -u username -p < SnakeGame.sql

  Replace `username` with your MySQL username. You will be prompted to enter your MySQL password. Alternatively, you can use a MySQL client to execute the script.

## Usage
- Use arrow keys or WASD keys to control the direction of the snake.
- Eat food pellets to grow longer.
- Avoid colliding with the snake's body or the edges of the play area.

## Acknowledgements
This project was inspired by the classic Snake game.



