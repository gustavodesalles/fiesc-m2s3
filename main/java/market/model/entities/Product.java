package market.model.entities;

public class Product {
	
	private long id;
	
	private String name;
	
	private String description;
	
	private Category category;
	
	public Product(String name, String description) {
		this.name = name;
		this.description = description;
	};
	
	public Product() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	
}
