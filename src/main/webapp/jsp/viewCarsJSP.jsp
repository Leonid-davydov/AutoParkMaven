<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="java.util.Optional" %>
<%@ page import="by.incubator.application.dto.VehicleDto" %>
<%@ page import="java.util.function.Predicate" %><%--
  Created by IntelliJ IDEA.
  User: Леонид
  Date: 16.06.2022
  Time: 13:16
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

            Set<String> uniqTypes =
                    dtoList.stream().map(VehicleDto::getTypeName).collect(Collectors.toSet());
            Set<String> uniqModels =
                    dtoList.stream().map(VehicleDto::getModelName).collect(Collectors.toSet());
            Set<String> uniqMColors =
                    dtoList.stream().map(VehicleDto::getColor).collect(Collectors.toSet());
            Set<String> uniqEngineNames =
                    dtoList.stream().map(VehicleDto::getEngineName).collect(Collectors.toSet());
            Set<Double> uniqWeights =
                    dtoList.stream().map(VehicleDto::getWeight).collect(Collectors.toSet());
            Set<Integer> uniqYears =
                    dtoList.stream().map(VehicleDto::getManufactureYear).collect(Collectors.toSet());

            AtomicReference<Predicate<VehicleDto>> filter = new AtomicReference<>(vehicleDto -> true);

            Optional.ofNullable(request.getParameter("type")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehicleDto -> vehicleDto.getTypeName().equals(s)));
            });

            Optional.ofNullable(request.getParameter("model")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehicleDto -> vehicleDto.getModelName().equals(s)));
            });

            Optional.ofNullable(request.getParameter("color")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehicleDto -> vehicleDto.getColor().equals(s)));
            });

            Optional.ofNullable(request.getParameter("engine")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehicleDto -> vehicleDto.getEngineName().equals(s)));
            });

            Optional.ofNullable(request.getParameter("weight")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehicleDto -> Double.parseDouble(s) == vehicleDto.getWeight()));
            });

            Optional.ofNullable(request.getParameter("weight")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehicleDto -> Double.parseDouble(s) == vehicleDto.getWeight()));
            });

            Optional.ofNullable(request.getParameter("year")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> {
                filter.set(filter.get().and(vehicleDto -> Integer.parseInt(s) == vehicleDto.getManufactureYear()));
            });

            dtoList = dtoList.stream().filter(filter.get()).collect(Collectors.toList());
        %>
        <a class="ml-20" href="resources/index.html">На главную</a>
        <a class="ml-20" href="/AutoParkMaven_war_exploded/viewCars">Очистить фильтры</a>
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
                <th>&#x1F4CB;</th>
            </tr>
            <% if (dtoList.size() == 0) {%>
                <tr><td colspan="10">
                    Нет машин соответствующих параметрам</td> </tr>
            <%}%>
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
                <td><a href="<%="/AutoParkMaven_war_exploded/viewInfo?id=" + dto.getId()%>">&#x1F4C3;</a></td>
            </tr>
            <%}%>
        </table>
        <br />
        <div>
            <hr />
            <br />
            <form method="get" action="/AutoParkMaven_war_exploded/viewCars" class="flex">
                <div>
                    <p>Тип</p>
                    <select name="type" >
                        <option value=""
                                <%=request.getParameter("type") == null ? "selected" : ""%>>Не выбрано</option>
                        <%for (String s : uniqTypes) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("type") != null &&
                                s.equals(request.getParameter("type")) ? "selected" : "")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Модель</p>
                    <select name="model" >
                        <option value=""
                                <%=request.getParameter("model") == null ? "selected" : ""%>>Не выбрано</option>
                        <%for (String s : uniqModels) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("model") != null &&
                                        s.equals(request.getParameter("model")) ? "selected" : "")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div>
                    <p>Двигатель</p>
                    <select name="engine" >
                        <option value=""
                                <%=request.getParameter("engine") == null ? "selected" : ""%>>Не выбрано</option>
                        <%for (String s : uniqEngineNames) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("engine") != null &&
                                        s.equals(request.getParameter("engine")) ? "selected" : "")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Цвет</p>
                    <select name="color" >
                        <option value=""
                                <%=request.getParameter("color") == null ? "selected" : ""%>>Не выбрано</option>
                        <%for (String s : uniqMColors) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("color") != null &&
                                        s.equals(request.getParameter("color")) ? "selected" : "")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Масса</p>
                    <select name="weight" >
                        <option value=""
                                <%=request.getParameter("weight") == null ? "selected" : ""%>>Не выбрано</option>
                        <%for (Double s : uniqWeights) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("weight") != null && !request.getParameter("weight").equals("") &&
                                        s.equals(Double.parseDouble(request.getParameter("weight"))) ? "selected" : "")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Год</p>
                    <select name="year" >
                        <option value=""
                                <%=request.getParameter("year") == null ? "selected" : ""%>>Не выбрано</option>
                        <%for (Integer s : uniqYears) {%>
                        <option value="<%=s%>"
                                <%=(request.getParameter("year") != null && !request.getParameter("year").equals("") &&
                                        s.equals(Integer.parseInt(request.getParameter("year"))) ? "selected" : "")%>><%=s%></option>
                        <%}%>
                    </select>
                </div>
                <button class="ml-20" type="submit">Выбрать</button>
            </form>
            <br />
            <hr />
        </div>
    </div>
</div>
</body>
</html>
