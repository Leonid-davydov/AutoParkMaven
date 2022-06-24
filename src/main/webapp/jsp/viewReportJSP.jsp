<%@ page import="java.util.List" %>
<%@ page import="by.incubator.application.dto.VehicleDto" %>
<%@ page import="by.incubator.application.dto.RentsDto" %><%--
  Created by IntelliJ IDEA.
  User: Леонид
  Date: 19.06.2022
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчёт</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <%
            List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
            List<RentsDto> rentsDtoList = (List<RentsDto>) request.getAttribute("rents");

        %>
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
                <th>Доход с аренд</th>
                <th>Налог</th>
                <th>Итог</th>
            </tr>
            <% if (dtoList.size() == 0) {%>
            <tr><td colspan="10">
                В автопарке нет машин</td> </tr>
            <%}%>
            <%
                double incomeSum = 0.0;
                double taxSum = 0.0;

                for(VehicleDto dto : dtoList) {
                    double income = 0.0;

                    for (RentsDto rentsDto : rentsDtoList) {
                        if (rentsDto.getCarId() == dto.getId()) {
                            income += rentsDto.getCost();
                        }
                    }

                    incomeSum += income;
                    taxSum += dto.getTaxCoefficient();
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
                <td><%=income%></td>
                <td><%=dto.getTaxCoefficient()%></td>
                <td><%=income - dto.getTaxCoefficient()%></td>
            </tr>
            <%}%>
        </table>
        <br />
        <div>
            <hr />
            <br />
            <p>
                Средний налог за месяц: <strong><%=taxSum / dtoList.size()%></strong>
            </p>
            <p>
                Средний доход за месяц: <strong><%=incomeSum / dtoList.size()%></strong>
            </p>
            <p>
                Доход автопарка: <strong><%=incomeSum - taxSum%></strong>
            </p>
            <br />
            <hr />
        </div>
    </div>
</div>
</body>
</html>
