package by.incubator.application.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/viewTypes")
public class ViewCarTypesServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("types", vehicleTypeService.getTypes());
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/jsp/viewTypesJSP.jsp");
        dispatcher.forward(request, response);
    }
}
