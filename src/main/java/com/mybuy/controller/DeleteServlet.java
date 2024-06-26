package com.mybuy.controller;

import com.mybuy.model.Delete;
import com.mybuy.model.DeleteModel;
import com.mybuy.model.UserType;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteServlet", urlPatterns = {"/delete"})
public class DeleteServlet extends HttpServlet {
	private DeleteModel deleteModel;
	
	@Override
	public void init() throws ServletException {
		super.init();
		deleteModel = new DeleteModel();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		if (type.equals("auction")) {
			Delete deleteForm = new Delete(id, null);
			boolean deletedAuction = deleteModel.deleteAuction(deleteForm);
		} else if (type.equals("bid")) {
			Delete deleteForm = new Delete(id, null);
			boolean deletedAuction = deleteModel.deleteBid(deleteForm);
		}
		else {
			Delete deleteForm = new Delete(id, UserType.fromString(type));
			boolean deletedUser = deleteModel.deleteUser(deleteForm);
		}
//		if (!deletedUser) {
//			request.setAttribute("deleteMessage", "Deletion Invalid");
//		} else {
//			request.setAttribute("deleteMessage", "Deletion Valid");
//		}
        String origin = request.getParameter("origin");
        request.getRequestDispatcher("/WEB-INF/view/" + origin + ".jsp").forward(request, response);
	}
}