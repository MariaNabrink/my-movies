package main.java.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import main.java.repository.MoviesRepository;

@WebServlet("/MyMovies")
public class MyMovies extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MoviesRepository movieRepo;

	public MyMovies() {
		movieRepo = new MoviesRepository();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			String redirect = response.encodeRedirectURL(request
					.getContextPath() + "/UserLogin");
			response.sendRedirect(redirect);
		} else {
			List<Movie> listmovies = null;

			try {
				listmovies = movieRepo.getMovies();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			request.setAttribute("movies", listmovies);

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/jsp/MyMovies.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.getAttribute("username");

		Movie movie = new Movie();
		String title = Jsoup.clean(request.getParameter("title"),
				Whitelist.simpleText());
		String director = Jsoup.clean(request.getParameter("director"),
				Whitelist.simpleText());
		String year = Jsoup.clean(request.getParameter("year"),
				Whitelist.simpleText());
		String category = Jsoup.clean(request.getParameter("category"),
				Whitelist.simpleText());

		if (title.isEmpty() || director.isEmpty() || year.isEmpty()
				|| category.isEmpty()) {
			session.setAttribute("username", session.getAttribute("username"));

			String redirect = response.encodeRedirectURL(request
					.getContextPath() + "/jsp/AddMovie.jsp");
			response.sendRedirect(redirect);
		} else {
			try {

				movie.setTitle(title);
				movie.setDirector(director);
				movie.setYear(year);
				movie.setCategory(category);

				movieRepo.createMovie(movie);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			session.setAttribute("username", session.getAttribute("username"));

			String redirect = response.encodeRedirectURL(request
					.getContextPath() + "/MyMovies");
			response.sendRedirect(redirect);
		}
	}
}

