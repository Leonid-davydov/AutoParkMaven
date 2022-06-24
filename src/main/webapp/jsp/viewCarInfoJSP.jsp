<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="java.util.Optional" %>
<%@ page import="by.incubator.application.dto.VehicleDto" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="by.incubator.application.dto.RentsDto" %><%--
  Created by IntelliJ IDEA.
  User: Леонид
  Date: 19.06.2022
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Просмотр информации о машине</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <%
            List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");

            long id = Long.parseLong(request.getParameter("id"));

            VehicleDto vehicle = null;

            for (VehicleDto dto : dtoList) {
                if (dto.getId() == id) {
                    vehicle = dto;
                    break;
                }
            }
        %>
        <a class="ml-20" href="/AutoParkMaven_war_exploded/viewCars">Назад</a>
        <a class="ml-20" href="resources/index.html">На главную</a>
        <br />
        <br />
        <hr />
        <br />
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
                <th>Расход</th>
                <th>Коэфф налога</th>
                <th>km на полный бак</th>
            </tr>
            <%
                for(VehicleDto dto : dtoList) {
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
                <td><%=dto.getPer100km()%></td>
                <td><%=dto.getTax()%></td>
                <td><%=dto.getMaxKm()%></td>
            </tr>
            <%}%>
        </table>
        <br />
        <div>
            <hr />
            <br />
            <p>
                Налог за месяц: <strong><%=vehicle.getTaxCoefficient()%></strong>
            </p>
            <br />
            <hr />
        </div>

        <table>
            <caption>Аренды</caption>
            <tr>
                <th>Дата</th>
                <th>Стоимость</th>
            </tr>
            <%
                List<RentsDto> rentsDtoList = (List<RentsDto>) request.getAttribute("rents");
                double sum = rentsDtoList.stream().mapToDouble(dto -> dto.getCost()).reduce((s1, s2) -> s1 + s2).orElse(0);

                for(RentsDto dto : rentsDtoList) {
            %>
            <tr>
                <td><%=dto.getDate()%></td>
                <td><%=dto.getCost()%></td>
            </tr>
            <%}%>
        </table>
        <p>
            Сумма: <strong><%=sum%></strong>
        </p>
        <p>
            Доход: <strong><%=sum - vehicle.getTaxCoefficient()%></strong>
        </p>
    </div>
</div>
</body>
</html>
