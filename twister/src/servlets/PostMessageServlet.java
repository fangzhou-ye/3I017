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
public class PostMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idMessage = req.getParameter("idmessage");
		String email = req.getParameter("email");
		String content = req.getParameter("content");
		PrintWriter out = resp.getWriter();
		try {
			JSONObject res = MessageService.postMessage(idMessage, email, content);
			out.println(res);
		} catch (NumberFormatException | JSONException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
