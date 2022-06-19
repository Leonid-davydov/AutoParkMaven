<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="by.incubator.application.dto.VehicleTypeDto" %><%--
  Created by IntelliJ IDEA.
  User: Леонид
  Date: 16.06.2022
  Time: 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Types view</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
    <div class="center flex full-vh">
        <div class="vertical-center">
            <a class="ml-20" href="resources/index.html">To the main</a>
            <br/>
            <br/>
            <hr/>
            <br/>
            <%
                String sortKey = null;
                String order = null;
                if (request.getParameter("name") != null) sortKey = "name";
                if (request.getParameter("tax") != null) sortKey = "tax";
                if (request.getParameter("asc") != null) order = "asc";
                if (request.getParameter("desc") != null) order = "desc";
            %>
            <% if (sortKey != null) { %>
            <%
                String clearPath = "/viewTypes";
                String ascPath = "?" + sortKey + "&asc";
                String descPath = "?" + sortKey + "&desc";
            %>
            <div>
                <a class="ml-20" href="<%=descPath%>">
                    Sort Descending</a>
                <a class="ml-20" href="<%=ascPath%>">
                    Sort ascending</a>
                <a class="ml-20" href="<%=clearPath%>">
                    Clear search options</a>
            </div>
            <br/>
            <hr/>
            <br/>
            <% } %>
            <table>
                <caption>Vehicles types</caption>
                <tr>
                    <th>Name</th>
                    <th>Road tax coefficient</th>
                </tr>
                <%
                    List<VehicleTypeDto> dtoList =
                            (List<VehicleTypeDto>) request.getAttribute("types");
                    Comparator<VehicleTypeDto> comparator = null;

                    if (sortKey != null && sortKey.equals("name")) {
                        comparator = Comparator.comparing(
                                vehicleTypeDto -> vehicleTypeDto.getName());
                    }
                    if (sortKey != null && sortKey.equals("tax")) {
                        comparator = Comparator.comparingDouble(
                                vehicleTypeDto -> vehicleTypeDto.getTaxCoefficient());
                    }
                    if (order != null && comparator != null && order.equals("desc")) {
                        comparator = comparator.reversed();
                    }
                    if (comparator != null) {
                        dtoList.sort(comparator);
                    }

                    for (VehicleTypeDto dto : dtoList) {
                %>
                <tr>
                    <td><%=dto.getName()%></td>
                    <td><%=dto.getTaxCoefficient()%></td>
                </tr>
                <%}%>
            </table>
            <% if (dtoList.size() > 0) {%>
            <p>Minimum coefficient:
<strong><%=dtoList.stream().map(VehicleTypeDto::getTaxCoefficient)
        .min(Double::compare).get()%></strong></p>
            <p>Maximum coefficient:
<strong><%=dtoList.stream().map(VehicleTypeDto::getTaxCoefficient)
        .max(Double::compare).get()%></strong></p>
            <%}%>
            <br />
            <hr />
            <br />
            <div>
                <% if (request.getParameter("name") == null) {%><a class="ml-20"
href="/viewTypes?name">Sort by name</a><%}%>
                <% if (request.getParameter("tax") == null) {%><a class="ml-20"
href="/viewTypes?tax">Sort by coefficient</a><%}%>
            </div>
        </div>
    </div>
</body>
</html>
