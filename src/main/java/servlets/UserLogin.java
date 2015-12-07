package main.java.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.repository.UserRepository;
import main.java.security.SaltAndHash;
import main.java.security.TOTP;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int[] totps;
	
	private SaltAndHash saltAndHash;
	private UserRepository userRepo;
	
	public UserLogin(){
		saltAndHash = new SaltAndHash();
		userRepo = new UserRepository();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("username") != null) {
			String redirect = response.encodeRedirectURL(request
					.getContextPath() + "/MyMovies");
			response.sendRedirect(redirect);
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/jsp/UserLogin.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userName = Jsoup.clean(request.getParameter("user"),
				Whitelist.simpleText());
		String password = Jsoup.clean(request.getParameter("password"),
				Whitelist.simpleText());

		int auth = 0;

		if (!request.getParameter("auth").isEmpty()) {
			auth = Integer.parseInt(request.getParameter("auth"));
		}
		
		User user = new User();

		try {

			user = userRepo.getUserByName(userName);

			if (!request.getParameter("user").isEmpty()
					&& saltAndHash.validatePassword(password, user.getPassword())) {

				if (user.getUserName().equals(userName) && user.getSecret() != null && auth != 0) {
					totps = TOTP.generateCurrentTOTPValues(user.getSecret());

					for (int totp : totps) {

						if (user.getUserName() != null
								&& !user.getSecret().equals(null)
								&& totp == auth) {

							HttpSession session = request.getSession(true);
							session.setAttribute("username", user.getUserName());
							String redirect = response
									.encodeRedirectURL(request.getContextPath()
											+ "/MyMovies");
							response.sendRedirect(redirect);
						}
					}

				} else if (!user.getUserName().equals(null)
						&& user.getSecret() == null) {

					HttpSession session = request.getSession(true);
					session.setAttribute("username", user.getUserName());
					String redirect = response.encodeRedirectURL(request
							.getContextPath() + "/MyMovies");
					response.sendRedirect(redirect);
				} else {

					String redirect = response.encodeRedirectURL(request
							.getContextPath() + "/jsp/UserLogin.jsp");
					response.sendRedirect(redirect);
				}

			} else if (request.getParameter("user").isEmpty()
					|| !user.getPassword().equals(password)
					|| !user.getUserName().equals(userName)) {

				String redirect = response.encodeRedirectURL(request
						.getContextPath() + "/UserLogin");
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