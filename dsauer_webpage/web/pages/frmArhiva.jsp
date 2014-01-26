<%-- 
    Document   : newjsp
    Created on : 28.08.2009., 21:02:38
    Author     : sheky
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
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
        
    </head>
    <body>
<%
            if ((userId <= 0)) {
                out.println("<div align=\"center\">");
                out.println("<p id=\"err\">Niste prijavljeni na sustav.<br/>");
                out.println("<a href=\"\" onclick=\"doRequest('GET',false,true,'content','pages/frmLoginUser.jsp');return false;\">Prijavite se na sustav</a>");
                out.println("</div>");
            } else { // ---------prijavljeni na sustav
                String zip = request.getParameter("zip");
%>
        <h1 onclick="ch_size('arhiva', 'resize2','Prikaži arhivu','Sakrij arhivu');">
            <span id="resize2" title="Sakrij arhivu" style="font-weight:bolder;font-size:13px;cursor:pointer; border:solid thin #E8E8E8;" >&nbsp;-&nbsp;</span>
            Arhivirane prognoze</h1>
        <div id="arhiva">
            <input type="button" value="Prikaži najnoviju vremensku prognozu" onclick="<% out.print("doRequest2('GET',false,true,'result','pages/frmWeatherData.jsp?zip=" + zip + "');return false;"); %>"> <br/>
            Odabrani datum: <span id="arhDatum">odabrani datum</span><br>
            <%
                out.print(ajaxServlet.frmListArchive(zip));
            %>
        </div>
<% }  //else - korisnik je prijavljen na sustav %>
    </body>
</html>
