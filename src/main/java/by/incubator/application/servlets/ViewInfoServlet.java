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
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "/info")
public class ViewInfoServlet extends HttpServlet {
    VehicleTypeService vehicleTypeService = new VehicleTypeService();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        request.setAttribute("cars",
                vehicleTypeService.getVehicles().stream().filter(vehicleDto -> id ==
                vehicleDto.getId()).collect(Collectors.toList()));
        request.setAttribute("rents", vehicleTypeService.getRent(id));
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/jsp/viewCarInfoJSP.jsp");
        dispatcher.forward(request, response);
    }
}
