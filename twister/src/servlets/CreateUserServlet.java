package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.CreateUserService;

public class CreateUserServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		/*
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String nom = req.getParameter("nom");
		String prenom = req.getParameter("prenom");
		PrintWriter out = resp.getWriter();
		out.println(login);
		out.println(password);
		out.println(nom);
		out.println(prenom);
		*/
		try {
			JSONObject res = CreateUserService.createUser(login, password, nom, prenom);
			out.println(res.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
