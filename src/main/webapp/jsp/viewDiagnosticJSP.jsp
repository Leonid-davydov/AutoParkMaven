<%@ page import="java.util.List" %>
<%@ page import="by.incubator.application.dto.VehicleDto" %>
<%@ page import="by.incubator.application.dto.OrdersDto" %><%--
  Created by IntelliJ IDEA.
  User: Леонид
  Date: 20.06.2022
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Просмотр машин</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <%
            List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
            List<OrdersDto> oldOrders = (List<OrdersDto>) request.getAttribute("oldOrders");
            List<OrdersDto> newOrders = (List<OrdersDto>) request.getAttribute("newOrders");
        %>
        <a class="ml-20" href="resources/index.html">На главную</a>
        <br/>
        <br/>
        <hr/>
        <br/>
        <table>
            <caption>Машины</caption>
            <tr>
                <th>Тип</th>
                <th>Модель</th>
                <th>Номер</th>
                <th>Масса</th>
                <th>Дата выпуска</th>
                <th>Цвет</th>
                <th>Модель двигателя</th>
                <th>Пробег</th>
                <th>Бак</th>
                <th>Была исправна</th>
                <th>Починена</th>
            </tr>
            <% if (dtoList.size() == 0) {%>
            <tr>
                <td colspan="10">
                    В автопарке нет машин
                </td>
            </tr>
            <%}%>
            <%
                boolean wasBroken;
                boolean isBroken;
                String wasOk;
                String isRepaired;

                for (VehicleDto dto : dtoList) {
                    wasBroken = false;
                    isBroken = false;

                    for (OrdersDto order : oldOrders) {
                        if (order.getVehicleId() == dto.getId()) {
                            wasBroken = true;
                            break;
                        }
                    }

                    for (OrdersDto order : newOrders) {
                        if (order.getVehicleId() == dto.getId()) {
                            isBroken = true;
                            break;
                        }
                    }

                    if (wasBroken) {
                        wasOk = "Нет";

                        if (isBroken) {
                            isRepaired = "Нет";
                        } else {
                            isRepaired = "Да";
                        }
                    } else {
                        wasOk = "Да";
                        isRepaired = "Нет";
                    }
            %>
            <tr>
                <td><%=dto.getTypeName()%></td>
                <td><%=dto.getModelName()%></td>
                <td><%=dto.getRegistrationNumber()%></td>
                <td><%=dto.getWeight()%></td>
                <td><%=dto.getManufactureYear()%></td>
                <td><%=dto.getColor()%></td>
                <td><%=dto.getEngineName()%></td>
                <td><%=dto.getMileage()%></td>
                <td><%=dto.getTankVolume()%></td>
                <td><%=wasOk%></td>
                <td><%=isRepaired%></td>
            </tr>
            <%}%>
        </table>
    </div>
</div>
</body>
</html>
