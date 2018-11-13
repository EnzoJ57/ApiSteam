package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Repository {

	private File f = new File("dataPerso.json");
	private List<Game> listGame;

	public Repository() {
		this.listGame = new ArrayList<Game>();
		//this.read();
	}

	public List<Game> getGameRepo() {
		return listGame;
	}

	public void addGame(Game g) {
		this.listGame.add(g);
		this.writeJson();
	}

	public void deleteGameById(int id) {
		for (Game g : this.listGame) {
			if (g.getId() == id) {
				this.listGame.remove(g);
			}
		}
		this.writeJson();
	}

	public Game getGameById(int id) {
		for (Game g : this.listGame) {
			if (g.getId() == id)
				return g;
		}
		return null;
	}

	public void deleteAllGames() {
		this.listGame.clear();
		this.writeJson();
	}

	public void updateGame(Game game) {
		Game g = getGameById(game.getId());
		g.setName(game.getName());
		this.writeJson();
	}

	public boolean isGameExist(Game game) {
		if (getGameByName(game.getName()) == null) {
			return false;
		}
		return true;
	}

	public Game getGameByName(String name) {
		for (Game g : this.listGame) {
			if (g.getName().equals(name))
				return g;
		}
		return null;
	}

	public void writeJson() {
		try (FileWriter file = new FileWriter(f)) {
			for (Game game : this.listGame) {
				JSONObject obj = new JSONObject();
				obj.put("id", game.getId());
				obj.put("name", game.getName());

				file.write(obj.toJSONString());
				file.flush();

				System.out.print(obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void read(){
    	JSONParser parser = new JSONParser();

        try {
        	
            Object obj = parser.parse(new FileReader(f));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            long id = (long) jsonObject.get("id");
            System.out.println(id);
            
            String name = (String) jsonObject.get("name");
            System.out.println(name);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
