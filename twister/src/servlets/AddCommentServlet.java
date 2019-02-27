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

import services.CommentService;

@SuppressWarnings("serial")
public class AddCommentServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idMessage = req.getParameter("idmessage");
		String idComment = req.getParameter("idcomment");
		String email = req.getParameter("email");
		String text = req.getParameter("text");
		PrintWriter out = resp.getWriter();
		try {
			JSONObject res = CommentService.addComment(idMessage, idComment, email, text);
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
