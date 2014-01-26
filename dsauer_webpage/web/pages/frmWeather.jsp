<%--
    Document   : frmUserData
    Created on : 30.05.2009., 21:36:40
    Author     : sheky
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id='uClient' scope="session" class='serviceClient.korisnici' type="serviceClient.korisnici" />
<jsp:useBean id='ajaxServlet' scope="session" class='servlet.ajaxServlet' type="servlet.ajaxServlet" />
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
    </head>
    <body>
        <h1 onclick="ch_size('search', 'resize','Prikaži rezultate pretrage','Sakrij rezultate pretrage'); ch_size('searchRes', 'resize','Prikaži rezultate pretrage','Sakrij rezultate pretrage');">
            <span id="resize" title="Sakrij rezultate pretrage" style="font-weight:bolder;font-size:13px;cursor:pointer; border:solid thin #E8E8E8;" >&nbsp;-&nbsp;</span>
            Vremenske prognoze</h1>
        <div id="err" style="width:730px;"></div>
        <%
            if ((userId <= 0)) {
                out.println("<div align=\"center\">");
                out.println("<p id=\"err\">Niste prijavljeni na sustav.<br/>");
                out.println("<a href=\"\" onclick=\"doRequest('GET',false,true,'content','pages/frmLoginUser.jsp');return false;\">Prijavite se na sustav</a>");
                out.println("</div>");
            } else { // ---------prijavljeni na sustav
%>
        <div id="search">            
            <% out.print(ajaxServlet.frmSearch());%>
        </div>
        <div id="msgLoader" align="center"></div>        
        <div id="searchRes"></div>
        <div id="resultArchive"></div>
        <div id="msgLoader2" align="center"></div>
        <div id="result"></div>
        <% } // ------------prijavljeni na sustav%>
    </body>
</html>
