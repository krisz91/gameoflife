import {Component, OnInit} from '@angular/core';
import {GameService} from "./game.service";
import {Point} from "./point";
import {Game} from "./game";
import {Response} from "@angular/http";
import {GetGameNameDto} from "./get-game-name-dto";
import Timer = NodeJS.Timer;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  patterns: string[];

  rows: number = 100;

  cols: number = 100;

  selectedPattern: string;

  patternLoaded: boolean = false;

  currentGame: Game;

  savedGameNames: GetGameNameDto[];

  rowsArray: number[] = Array.from(Array(this.rows).keys());

  colsArray: number[] = Array.from(Array(this.cols).keys());

  selectedSavedGame: string;

  constructor(private gameService: GameService) {
  }


  ngOnInit(): void {
    this.gameService.getPatterns().subscribe(patterns => {
      this.patterns = patterns;

      this.selectedPattern = this.patterns[0];
    })


    this.loadSavedGames();
  }

  private loadSavedGames() {
    this.gameService.getSavedGameNames().subscribe((savedGameNames) => {
      this.savedGameNames = savedGameNames;
    })
  }

  hasElement(x: number, y: number): boolean {
    for (let i = 0; i < this.currentGame.livePoints.length; i++) {
      if (this.currentGame.livePoints[i].x == x && this.currentGame.livePoints[i].y == y) {
        return true;
      }
    }
    return false;
  }

  loadPattern(): void {

    this.gameService.getPattern(this.selectedPattern).subscribe(points => {

      this.currentGame = new Game(points, this.rows, this.cols, 0, "Próba");

      this.patternLoaded = true;
    })
  }

  getNextGeneration(): void {

    console.log('Game', this.currentGame)
    this.gameService.calculateNextGeneration(this.currentGame).subscribe((game: Game) => {

      this.setUpGame(game);
    })
  }

  setUpGame(game) {

    this.currentGame = game;

    this.patternLoaded = true;
  }

  makeLive(row: number, column: number): void {

    if (!this.hasElement(row, column)) {
      this.currentGame.livePoints.push(new Point(row, column));
    }
  }

  changeSize(): void {

    this.rowsArray = Array.from(Array(this.rows).keys());
    this.colsArray = Array.from(Array(this.cols).keys());
    let newPoints: Point[] = [];
    for (let i = 0; i < this.currentGame.livePoints.length; i++) {

      let actualPoint: Point = this.currentGame.livePoints[i];

      if (actualPoint.x < this.rows && actualPoint.y < this.cols) {

        newPoints.push(actualPoint);
      }

    }


    this.currentGame.livePoints = newPoints;
    console.log(this.currentGame.livePoints, newPoints);

  }

  saveState(): void {

    this.currentGame.name = prompt("Játék neve?", "Próba");

    if (this.currentGame.name == "") {
      alert("Név megadása kötelező");
    }
    this.gameService.saveGame(this.currentGame).subscribe((response: Response) => {
      alert("Sikeres mentés");
      this.loadSavedGames();
    })
  }

  loadSavedState(): void {
    this.gameService.loadGame(this.selectedSavedGame).subscribe((game: Game) => {
      this.setUpGame(game);
    })
  }


  timeoutHandler: Timer;

  isAutoPlay: boolean = false;


  autoplay(): void {

    this.isAutoPlay = true;
    this.timeoutHandler = setInterval(() => {
      this.getNextGeneration();
    }, 2000);
  }

  stopAutoPlay(): void {
    this.isAutoPlay = false;
    clearInterval(this.timeoutHandler);
  }
}
