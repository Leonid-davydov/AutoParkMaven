package by.incubator.application.servlets;

import by.incubator.application.dto.VehicleTypeService;
import by.incubator.application.infrastrucrure.core.impl.ApplicationContext;
import by.incubator.application.main.Main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "/viewTypes")
public class ViewCarTypesServlet extends HttpServlet {
    VehicleTypeService vehicleTypeService = new VehicleTypeService();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("types", vehicleTypeService.getTypes());

        PrintWriter pw = response.getWriter();

        pw.println("<html>");
        pw.println("<h1> Hello world </h1>");
        pw.println("<html>");
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/jsp/viewTypesJSP.jsp");
        dispatcher.forward(request, response);
    }
}
