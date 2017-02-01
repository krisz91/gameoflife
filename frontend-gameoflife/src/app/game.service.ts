import {Injectable, Inject} from '@angular/core';
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs";
import 'rxjs/add/operator/map'
import {Point} from "./point";
import {Game} from "./game";
import {GetGameNameDto} from "./get-game-name-dto";

@Injectable()
export class GameService {

  constructor(@Inject('API_URL') private apiUrl: string, private http: Http) {
  }


  getPatterns(): Observable<string[]> {

    return this.http.get(this.apiUrl + "/patterns")
      .map((response: Response) => {
          return response.json();
        }
      );
  }

  getPattern(patternName: string): Observable<Point[]> {
    return this.http.get(this.apiUrl + '/patterns/' + patternName)
      .map((response: Response) => {
          return (<any>response.json()).map(point => {
            return new Point(point.x, point.y);
          })
        }
      );
  }

  calculateNextGeneration(game: Game): Observable<Game> {
    return this.http.post(this.apiUrl + '/patterns/nextGeneration', game)
      .map((response: Response) => {
          return (<Game>response.json())
        }
      );
  }

  saveGame(currentGame: Game): Observable<Response> {

    return this.http.post(this.apiUrl + '/games', currentGame)
      .map((response: Response) => {
        return response;
      })
  }

  getSavedGameNames(): Observable<GetGameNameDto[]> {
    return this.http.get(this.apiUrl + '/games/names')
      .map((response: Response) => {
        return response.json();
      })
  }

  loadGame(id: string) {
    return this.http.get(this.apiUrl + '/games/id/'+id)
      .map((response: Response) => {
        return (<Game>response.json())
      })
  }
}
