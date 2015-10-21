package com.manchau.streamDemos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GrocerySorter {
	public static void main(String[] args) {
		double maxPrice = 3.1d;
		List<GroceryItem> groceryList = new ArrayList<>();
		groceryList.add(new GroceryItem("Cheese", 4.3d,2));
		groceryList.add(new GroceryItem("Noodles", 1.5d,2));
		groceryList.add(new GroceryItem("Tomatoes", 3.2d,1));
		groceryList.add(new GroceryItem("Milk", 1.2d,3));
		groceryList.add(new GroceryItem("Chips", 2.25d,1));
		groceryList.add(new GroceryItem("Coke", 2.29d,1));
		groceryList.add(new GroceryItem("Salad", 3.4d,2));
		groceryList.add(new GroceryItem("Bread", 2.25d,1));
		
		// show the grocery list
		groceryList.stream().forEach(item -> System.out.println(item.toString()));
		
		// Show items with prices less than maxPrice
		List<String> names = groceryList.stream().filter(item -> item.getPrice()*item.getQuantity() < maxPrice )
							.sorted(Comparator.comparing(GroceryItem::getPrice))
							.map(item -> item.getName())
							.collect(Collectors.toList());
		System.out.println("Items less than 3.0 Euros: " + names);
	}
}

class GroceryItem {
	private final String NAME;
	private double price;
	private int quantity;

	public GroceryItem(String name, double price, int quantity) {
		this.NAME = name;
		this.price = price;
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return NAME;
	}
	
	@Override
	public String toString() {
		String description = "[Name: " + NAME + ", Price: " + price + ", Quantity: " + quantity + "]";
		return description;
	}
}