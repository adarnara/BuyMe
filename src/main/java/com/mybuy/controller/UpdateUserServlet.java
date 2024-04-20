package com.mybuy.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import com.mybuy.model.UpdateUserModel;
import com.mybuy.model.UpdateUser;


@WebServlet(name = "UpdateUserServlet", urlPatterns = {"/updateUser"})
public class UpdateUserServlet extends HttpServlet {
	
    private UpdateUserModel updateUserModel;

    @Override
    public void init() throws ServletException {
        super.init();
        updateUserModel = new UpdateUserModel();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String editedDataJson = request.getParameter("editedData");
        Gson gson = new Gson();
        UpdateUser[] editedData = gson.fromJson(editedDataJson, UpdateUser[].class);
        for (UpdateUser user : editedData) {
        	updateUserModel.updateUser(user);
        }
        String origin = request.getParameter("origin");
        if ("admin".equals(origin)) {
            request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/view/customerRep.jsp").forward(request, response);
        }
    }
    
}