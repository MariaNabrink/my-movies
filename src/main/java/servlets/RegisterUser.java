package main.java.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.repository.UserRepository;
import main.java.security.SaltAndHash;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserRepository userRepo;
	private SaltAndHash saltAndHash;

	public RegisterUser() {
		userRepo = new UserRepository();
		saltAndHash = new SaltAndHash();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String redirect = response.encodeRedirectURL(request.getContextPath()
				+ "/jsp/RegisterUser.jsp");
		response.sendRedirect(redirect);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userName = Jsoup.clean(request.getParameter("user"),
				Whitelist.simpleText());
		String password = Jsoup.clean(request.getParameter("password"),
				Whitelist.simpleText());

		try {

			User userNameExistTest = userRepo.getUserByName(userName);
			String hashedPassword = null;
			User user = new User();

			user.setUserName(userName);

			if (userName.isEmpty() || password.isEmpty() || user.getUserName().equalsIgnoreCase(userNameExistTest.getUserName())) {

				String redirect = response.encodeRedirectURL(request
						.getContextPath() + "/RegisterUser");
				response.sendRedirect(redirect);

			} else {

				HttpSession session = request.getSession(true);
				session.setAttribute("username", user.getUserName());

				hashedPassword = saltAndHash.generatePasswordHash(password);
				user.setPassword(hashedPassword);
				userRepo.createUser(user);

				String redirect = response.encodeRedirectURL(request
						.getContextPath() + "/MyMovies.servlet");
				response.sendRedirect(redirect);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

	}

}