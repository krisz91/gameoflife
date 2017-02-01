import {Component, OnInit} from '@angular/core';
import {GameService} from "./game.service";
import {Point} from "./point";
import {Game} from "./game";
import {Response} from "@angular/http";
import {GetGameNameDto} from "./get-game-name-dto";

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

  livePoints: Point[];

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
    for (let i = 0; i < this.livePoints.length; i++) {
      if (this.livePoints[i].x == x && this.livePoints[i].y == y) {
        return true;
      }
    }
    return false;
  }

  loadPattern(): void {

    this.gameService.getPattern(this.selectedPattern).subscribe(points => {

      this.livePoints = points;

      this.currentGame = new Game(this.livePoints, this.rows, this.cols, 0, "Próba");

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
    this.livePoints = game.livePoints;

    this.currentGame = game;

    this.patternLoaded = true;
  }

  makeLive(row: number, column: number): void {

    if (!this.hasElement(row, column)) {
      this.livePoints.push(new Point(row, column));
    }
  }

  changeSize(): void {

    let newPoints: Point[] = [];
    for (let i = 0; i < this.livePoints.length; i++) {

      let actualPoint: Point = this.livePoints[i];

      if (actualPoint.x < this.rows && actualPoint.y < this.cols) {

        newPoints.push(actualPoint);
      }

    }
    console.log(this.rows, this.cols);

    this.livePoints = newPoints;
    this.rowsArray = Array.from(Array(this.rows).keys());
    this.colsArray = Array.from(Array(this.cols).keys());

    console.log(this.rowsArray, this.colsArray);
  }

  saveState(): void {

    this.currentGame.name = prompt("Játék neve?", "Próba");
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
}
