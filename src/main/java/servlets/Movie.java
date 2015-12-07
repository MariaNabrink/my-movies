package main.java.servlets;

import com.sun.istack.internal.NotNull;

public class Movie {

	private int id;
	@NotNull
	private String title;
	@NotNull
	private String director;
	@NotNull
	private String year;
	@NotNull
	private String category;
	private static int idCounter;

	public Movie(int id, String title, String director, String year, String category) {
		setId(id);
		setTitle(title);
		setDirector(director);
		setYear(year);
		setCategory(category);
	}

	public Movie() {
	}

	public static int generateId() {
		return ++idCounter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", director="
				+ director + ", year=" + year + ", category=" + category + "]";
	}

}
