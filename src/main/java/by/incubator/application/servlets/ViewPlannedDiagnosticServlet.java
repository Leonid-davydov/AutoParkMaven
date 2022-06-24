package by.incubator.application.servlets;

import by.incubator.application.checker.VehiclesChecker;
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

@WebServlet(name = "/viewPlannedDiagnostic")
public class ViewPlannedDiagnosticServlet extends HttpServlet {
    Map<Class<?>, Class<?>> interfaceToImplementation = Main.initInterfaceToImplementation();
    ApplicationContext context = new ApplicationContext("by.incubator.application", interfaceToImplementation);
    VehiclesChecker vehiclesChecker = context.getObject(VehiclesChecker.class);
    VehicleTypeService vehicleTypeService = new VehicleTypeService();

    @Override
    public void init() throws ServletException {
        super.init();
        vehiclesChecker.runServerChecker(context);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cars", vehicleTypeService.getVehicles());
        request.setAttribute("frequency", vehiclesChecker.getFrequency());
        request.setAttribute("date", vehiclesChecker.getDateOfLastChek());
        request.setAttribute("oldOrders", vehiclesChecker.getBeforeChek());
        request.setAttribute("newOrders", vehiclesChecker.getAfterChek());
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/jsp/viewPlannedDiagnosticJSP.jsp");
        dispatcher.forward(request, response);
    }
}
