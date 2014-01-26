<%-- 
    Document   : frmUserData
    Created on : 30.05.2009., 21:36:40
    Author     : sheky
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id='uClient' scope="session" class='serviceClient.korisnici' type="serviceClient.korisnici" />
<%
            Integer userId = null;
            String uName = null;
            if (session.isNew()) {
                session.setAttribute("userId", 0);
                session.setAttribute("userName", 0);
            }

            try {
                userId = Integer.parseInt(session.getAttribute("userId").toString());
                uName = session.getAttribute("userName").toString();
            } catch (Exception ex) {
                session.setAttribute("userId", 0);
                session.setAttribute("userName", "");
                userId = 0;
                uName = "";
            }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Korisnička stranica</h1>
        <% if (userId <= 0) {
                out.println("<div align=\"center\">");
                out.println("<p id=\"err\">Niste prijavljeni na sustav.<br/>");
                out.println("<a href=\"\" onclick=\"doRequest('GET',false,true,'content','pages/frmLoginUser.jsp');return false;\">Prijavite se na sustav</a>");
                out.println("</div>");
            } else { // ---------prijavljeni na sustav
                service.UserData uData = new service.UserData();
                uData.setUserID(Integer.valueOf(session.getAttribute("userId").toString()));
                uData.setFirstName(session.getAttribute("firstName").toString());
                uData.setLastName(session.getAttribute("lastName").toString());
                uData.setUserName(session.getAttribute("userName").toString());
                uData.setEmail(session.getAttribute("email").toString());

                String uBalance = null;
        %>

        <table width="60%">
            <tr>
                <td>Ime:</td>
                <td><% out.print(uData.getFirstName());%></td>
            </tr>
            <tr>
                <td>Prezime:</td>
                <td><% out.print(uData.getLastName());%></td>
            </tr>
            <tr>
                <td>e-mail:</td>
                <td><% out.print(uData.getEmail());%></td>
            </tr>
            <tr>
                <td>Korisničko ime:</td>
                <td><% out.print(uData.getUserName());%></td>
            </tr>
            <tr>
                <td>Lozinka</td>
                <td id="changePass"><a href="" onclick="doRequest('GET',true,false,'changePass','ajaxServlet?command=changePass'); return false;">Promjena lozinke</a> </td>
            </tr>
        </table>

        <% } // ------------prijavljeni na sustav%>
    </body>
</html>
