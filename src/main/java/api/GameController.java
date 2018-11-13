package api;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class GameController {
	

	private final AtomicInteger counter = new AtomicInteger();
	private Repository repository = new Repository();

	@RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getGame(@PathVariable("id") int id) {
		Game game = this.repository.getGameById(id);
		if (game == null) {
			return new ResponseEntity("Game with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/games", method = RequestMethod.GET)
	public ResponseEntity<List<Game>> listAllGames() {
		List<Game> games = this.repository.getGameRepo();
		// if (games.isEmpty()) {
		// return new ResponseEntity(HttpStatus.NO_CONTENT);
		// }
		return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
	}

	@RequestMapping(value = "/game", method = RequestMethod.POST)
	public ResponseEntity<?> createGame(@RequestBody Game game, UriComponentsBuilder ucBuilder) {
		if (this.repository.isGameExist(game)) {
			return new ResponseEntity("Unable to create. A Game with name " + game.getName() + " already exist.",
					HttpStatus.CONFLICT);
		}
		game.setId(counter.incrementAndGet());
		this.repository.addGame(game);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/game/{id}").buildAndExpand(game.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/game/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteGame(@PathVariable("id") int id) {
		Game game = this.repository.getGameById(id);
		if (game == null) {
			return new ResponseEntity("Unable to delete. Game with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		this.repository.deleteGameById(id);
		return new ResponseEntity<Game>(HttpStatus.OK);
	}

	@RequestMapping(value = "/game", method = RequestMethod.DELETE)
	public ResponseEntity<Game> deleteAllGames() {
		this.repository.deleteAllGames();
		System.out.println("DELETE");
		return new ResponseEntity<Game>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/game/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateGame(@PathVariable("id") int id, @RequestBody Game game) {
		Game currentGame = this.repository.getGameById(id);
		if (currentGame == null) {
			return new ResponseEntity("Unable to upate. Game with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		Game futurGame = this.repository.getGameByName(game.getName());
		if (futurGame != null) {
			return new ResponseEntity("Unable to upate. Game with name " + game.getName() + " already exist.",
					HttpStatus.CONFLICT);
		}
		currentGame.setName(game.getName());
		this.repository.updateGame(currentGame);
		return new ResponseEntity<Game>(currentGame, HttpStatus.OK);
	}
}
