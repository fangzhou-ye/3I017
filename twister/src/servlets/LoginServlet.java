package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.LoginService;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		PrintWriter out = resp.getWriter();
		out.println("login : " + login);
		out.println("password : " + password);

		try {
			JSONObject res = LoginService.login(login, password);
			out.println(res.toString());
		} catch (JSONException e) {
			out.println("JSON error");
		}

	}
	
}
