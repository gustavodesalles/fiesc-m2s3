package market.model.entities;

public class Example {
	private long id;
	private String fav_color;
	private int fav_number;
	
	public Example(long id, String fav_color, int fav_number) {
		this.id = id;
		this.fav_color = fav_color;
		this.fav_number = fav_number;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFav_color() {
		return fav_color;
	}
	public void setFav_color(String fav_color) {
		this.fav_color = fav_color;
	}
	public int getFav_number() {
		return fav_number;
	}
	public void setFav_number(int fav_number) {
		this.fav_number = fav_number;
	}

	@Override
	public String toString() {
		return "Example [id=" + id + ", fav_color=" + fav_color + ", fav_number=" + fav_number + "]";
	}
	
}
