package com.mybuy.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import com.mybuy.model.UpdateEndUserModel;
import com.mybuy.model.UpdateEndUser;


@WebServlet(name = "UpdateEndUserServlet", urlPatterns = {"/updateEndUser"})
public class UpdateEndUserServlet extends HttpServlet {
	
    private UpdateEndUserModel updateEndUserModel;

    @Override
    public void init() throws ServletException {
        super.init();
        updateEndUserModel = new UpdateEndUserModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String editedDataJson = request.getParameter("editedData");
        Gson gson = new Gson();
        UpdateEndUser[] editedData = gson.fromJson(editedDataJson, UpdateEndUser[].class);
        for (UpdateEndUser endUser : editedData) {
        	System.out.println("got1");
        	updateEndUserModel.updateUser(endUser);
        }
        request.getRequestDispatcher("/WEB-INF/view/customerRep.jsp").forward(request, response);
    }
    
}
