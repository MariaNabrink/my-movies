package main.java.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.repository.UserRepository;

@WebServlet("/UserAuthSettings")
public class UserAuthSettings extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserRepository userRepo;

	public UserAuthSettings() {
		userRepo = new UserRepository();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("username") != null) {
			String redirect = response.encodeRedirectURL(request
					.getContextPath() + "/UserAuthSettings");
			response.sendRedirect(redirect);
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/UserLogin.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		String userName = (String) session.getAttribute("username");

		String secretString = (String) session.getAttribute("secretString");

		try {
			User user = userRepo.getUserByName(userName);
			user.setSecret(secretString);

			userRepo.updateUser(user);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String redirect = response.encodeRedirectURL(request.getContextPath()
				+ "/MyMovies");
		response.sendRedirect(redirect);
	}
}