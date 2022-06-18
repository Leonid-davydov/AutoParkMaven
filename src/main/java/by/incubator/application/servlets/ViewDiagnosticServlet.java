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

@WebServlet(name = "/viewDiagnostic")
public class ViewDiagnosticServlet extends HttpServlet {
    Map<Class<?>, Class<?>> interfaceToImplementation = Main.initInterfaceToImplementation();
    ApplicationContext context = new ApplicationContext("by.incubator.application", interfaceToImplementation);
    VehicleTypeService vehicleTypeService = context.getObject(VehicleTypeService.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cars", vehicleTypeService.getVehicles());
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/jsp/viewDiagnosticJSP.jsp");
        dispatcher.forward(request, response);
    }
}
