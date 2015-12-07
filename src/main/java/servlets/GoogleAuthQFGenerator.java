package main.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base32;

@WebServlet("/GoogleAuthQRGenerator")
public class GoogleAuthQFGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String userName;
	private Base32 base32 = new Base32();
	private String randomString;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession	session = request.getSession(true);

		userName = (String) session.getAttribute("username");

		for (int i = 0; i < 1; i++) {
			try {

				randomString = RandomStringGenerator.generateRandomString(10,
						RandomStringGenerator.Mode.ALPHANUMERIC);

				session.setAttribute("secretString", randomString);

				base32.encodeAsString(randomString.getBytes());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String url = "https://www.google.com/chart"
				+ "?chs=200x200&chld=M|0&cht=qr&chl=otpauth://totp/MovieDB:"
				+ userName + "?secret="
				+ base32.encodeAsString(randomString.getBytes()) + "&issuer=MovieDB/";

		String redirect = response.encodeRedirectURL(url);
		response.sendRedirect(redirect);
	}

	public static class RandomStringGenerator {

		public static enum Mode {
			ALPHA, ALPHANUMERIC, NUMERIC
		}

		public static String generateRandomString(int length, Mode mode)
				throws Exception {

			StringBuffer buffer = new StringBuffer();
			String characters = "";

			switch (mode) {

			case ALPHA:
				characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
				break;

			case ALPHANUMERIC:
				characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
				break;

			case NUMERIC:
				characters = "1234567890";
				break;
			}

			int charactersLength = characters.length();

			for (int i = 0; i < length; i++) {
				double index = Math.random() * charactersLength;
				buffer.append(characters.charAt((int) index));
			}
			return buffer.toString();
		}
	}
}
