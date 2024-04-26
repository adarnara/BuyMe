package com.mybuy.controller;

import com.mybuy.model.SalesReportModel;
import com.mybuy.model.TopThings;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SalesReportServlet", urlPatterns = {"/salesReport"})
public class SalesReportServlet extends HttpServlet {
	
	private SalesReportModel salesReportModel;
	
	@Override
	public void init() throws ServletException {
		super.init();
		salesReportModel = new SalesReportModel();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "totalEarnings":
                request.getSession().setAttribute("data", "Total Earnings: " + salesReportModel.getEarningsData());
                request.getSession().setAttribute("activeTab", "totalEarnings");
            	request.getSession().setAttribute("activeTab2", "");
                break;
            case "earningsPer":
                request.getSession().setAttribute("activeTab", "earningsPer");
                break;
            case "bestItems":
                String numberStr1 = request.getParameter("number1");
                int number1 = 3; //default
                try {
                    number1 = Integer.parseInt(numberStr1);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format, using default");
                }
                List<TopThings> topItems = salesReportModel.getTopItems(number1);
            	request.getSession().setAttribute("activeTab", "bestItems");
            	request.getSession().setAttribute("activeTab2", "");
                request.setAttribute("topItems", topItems);
                break;
            case "bestBuyers":
                String numberStr2 = request.getParameter("number2");
                int number2 = 3; //default
                try {
                    number2 = Integer.parseInt(numberStr2);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format, using default");
                }
                List<TopThings> topBuyers = salesReportModel.getTopBuyers(number2);
            	request.getSession().setAttribute("activeTab", "bestBuyers");
            	request.getSession().setAttribute("activeTab2", "");
                request.setAttribute("topBuyers", topBuyers);
                break;
            case "perItem":
            	List<TopThings> perItem = salesReportModel.getPerItem();
            	request.getSession().setAttribute("activeTab", "earningsPer");
            	request.getSession().setAttribute("activeTab2", "perItem");
                request.setAttribute("perItem", perItem);
            	break;
            case "perCategory":
            	List<TopThings> perCategory = salesReportModel.getPerCategory();
            	request.getSession().setAttribute("activeTab", "earningsPer");
            	request.getSession().setAttribute("activeTab2", "perCategory");
                request.setAttribute("perCategory", perCategory);
            	break;
            case "perBuyer":
            	List<TopThings> perBuyer = salesReportModel.getPerBuyer();
            	request.getSession().setAttribute("activeTab", "earningsPer");
            	request.getSession().setAttribute("activeTab2", "perBuyer");
                request.setAttribute("perBuyer", perBuyer);
            	break;
            default:
                request.getSession().setAttribute("data", "Invalid request");
                request.getSession().setAttribute("activeTab", "none");
            	request.getSession().setAttribute("activeTab2", "");
                break;
        }
        
        request.setAttribute("showSalesPart", true);
        request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
    }
    private String getEarningsPerTypeData() { return "Earnings Per Type Data Here"; }
    private String getBestSellingItemsData() { return "Best Selling Items Data Here"; }

}