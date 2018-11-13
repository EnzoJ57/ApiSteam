package api;

public class Game {

    private int id;
    private String name;
    
    public Game() {}

    public Game(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
