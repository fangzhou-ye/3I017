package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.MessageService;

@SuppressWarnings("serial")
public class AddMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		String text = req.getParameter("text");
		PrintWriter out = resp.getWriter();
		try {
			JSONObject res = MessageService.addMessage(from, to, text);
			out.println(res);
		} catch (NumberFormatException | JSONException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
