package api;

public class Game {

    private int id;
    private String name;
    private String category;
    
    public Game() {}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
