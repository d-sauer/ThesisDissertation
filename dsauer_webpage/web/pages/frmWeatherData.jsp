<%--
    Document   : frmWeatherData
    Created on : 07.06.2009., 13:47:49
    Author     : sheky
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id='wZahtjev' scope="session" class='serviceClient.prognoze' type="serviceClient.prognoze" />
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
    <body>
<%
            if ((userId <= 0)) {
                out.println("<div align=\"center\">");
                out.println("<p id=\"err\">Niste prijavljeni na sustav.<br/>");
                out.println("<a href=\"\" onclick=\"doRequest('GET',false,true,'content','pages/frmLoginUser.jsp');return false;\">Prijavite se na sustav</a>");
                out.println("</div>");
            } else { // ---------prijavljeni na sustav
%>

        <br/>
        <hr/>
        <% if (request.getParameter("zip").isEmpty()) {
            out.println("Nema rezultata.");
        } else {
            String zip = request.getParameter("zip");
            String date = request.getParameter("date");
            String id = request.getParameter("id");
            service.WeatherData wData = new service.WeatherData();
            service.CityData cData = new service.CityData();                        

            wData = wZahtjev.cityWeather(zip, date, id);
            cData = wZahtjev.cityInfo(zip);

            
            if (wData.isSubstitute() == true) {
                out.println("Servis za vremenske prognoze ne raspolaže podacima za grad s ZIP kodom " + zip + ".<br/>");
                out.println("Postoje alternativne informacije za prvi bliži grad sa ZIP kodom: ");
                out.print("<b><a onclick=\"doRequest2('GET',false,true,'result','pages/frmWeatherData.jsp?zip=" + wData.getSubstituteZIP() + "');return false;\" >" + wData.getSubstituteZIP() + "<a/></b>");
            } else if(wData==null || wData.isError()==true || cData.isError()==true) {
                out.println("Došlo je do pogreške<br/>");
                out.println(wData.getMessage() + "<br/>");
                out.println(cData.getMessage());
            } else {

        %>
       <table width="100%">
            <tr>
                <td class="weatherTitle"><% out.println(cData.getCity());%> &nbsp;&nbsp;&nbsp;&nbsp;<span class="datum">datum:<% out.println( wData.getDate().getDay() + "."  + wData.getDate().getMonth() + "." + wData.getDate().getYear() + "." + "   " + wData.getDate().getHour() + ":" + wData.getDate().getMinute()); %></span></td>
            </tr>
            <tr><td>
                    <div id="wLeftData">
                        <table width="100%" align="left" border="0">
                            <tr>
                                <td width="40%">Poštanski broj:</td>
                                <td><% out.println(cData.getZipCode());%></td>
                            </tr>
                            <tr>
                                <td>Država:</td>
                                <td><% out.println(cData.getState());%></td>
                            </tr>
                            <tr><td colspan="3"><hr/></td></tr>
                            <tr>
                                <td>Vremenska prognoza</td>
                                <td><% out.println(wData.getCurrDesc());%></td>
                                <td class="icon"><% out.println("<img src=\"images/weatherBug/" + wData.getCurrIcon() + "\"  width=\"80%\" height=\"80%\" />");%></td>
                            </tr>
                            <tr>
                                <td>Temperatura</td>
                                <td><% out.println(wData.getTemperature().toString() + " " + wData.getTemperatureUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td rowspan="2"><img src="images/termometar.png"/></td>
                                <td>Maks: <% out.println(wData.getTemperatureHigh().toString() + " " + wData.getTemperatureUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td>Min: <% out.println(wData.getTemperatureLow().toString() + " " + wData.getTemperatureUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr><td><br/></td></tr>
                            <tr>
                                <td>Vlažnost zraka</td>
                                <td><% out.println(wData.getHumidity().toString() + " " + wData.getHumidityUni());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td rowspan="2"><img alt="humidity" src="images/humidity.jpg"/></td>
                                <td>Maks: <% out.println(wData.getHumidityHigh().toString() + " " + wData.getHumidityUni());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td>Min: <% out.println(wData.getHumidityLow().toString() + " " + wData.getHumidityUni());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr><td><br/></td></tr>
                            <tr>
                                <td>Tlak zraka</td>
                                <td><% out.println(wData.getPressure().toString() + " " + wData.getPressureUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>Maks: <% out.println(wData.getPressureHigh().toString() + " " + wData.getPressureUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>Min: <% out.println(wData.getPressureLow().toString() + " " + wData.getPressureUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr><td colspan="3"><hr/></td></tr>
                            <tr>
                                <td>Brzina vjetra</td>
                                <td><% out.println(wData.getWindSpeed().toString() + " " + wData.getWindSpeedUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td>Smjer vjetra</td>
                                <td><% out.println(wData.getWindDirection());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td>Količina kiše</td>
                                <td><% out.println(wData.getRainToday().toString() + " " + wData.getRainUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td align="right">Prosječno:</td>
                                <td><% out.println(wData.getRainRate().toString() + " " + wData.getRainRateUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                            <tr>
                                <td align="right">Maks:</td>
                                <td><% out.println(wData.getRainRateMax().toString() + " " + wData.getRainRateUnit());%></td>
                                <td class="icon"></td>
                            </tr>
                        </table>
                    </div>
                    <div id="wRightData">
                        <table>
                            <tr>
                                <td colspan="2">
                                        <% String lat = cData.getLatitude();
                                           String lng = cData.getLongitude();
                                           lat = lat.replace("+000", "");
                                           lat = lat.replace("+00", "");
                                           lat = lat.replace("+0", "");
                                           lat = lat.replace("+", "");
                                           lat = lat.replace("-000", "-");
                                           lat = lat.replace("-00", "-");
                                           lat = lat.replace("-0", "-");
                                           lat = lat.replace("\n", "");

                                           lng = lng.replace("+000", "");
                                           lng = lng.replace("+00", "");
                                           lng = lng.replace("+0", "");
                                           lng = lng.replace("+", "");
                                           lng = lng.replace("-000", "-");
                                           lng = lng.replace("-00", "-");
                                           lng = lng.replace("-0", "-");
                                           lng = lng.replace("\n", "");
                                        %>
                                    <div id="gmap" align="center" onclick="initLoader(<% out.println(lat); %>,<% out.println(lng); %> , 'gmap');">
                                        Kliknite za prikaz karte..
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top">Geografske koordinate</td>
                                <td valign="top">širina (lat):<% out.println(cData.getLatitude());%><br/>
                                    dužina (lng): <% out.println(cData.getLongitude());%>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">Vremenska zona: <% out.println( wData.getTimeZone());%></td>
                            </tr>
                            <tr align="center">
                                <td><img alt="sunrise" src="images/sunrise.jpg" /></td>
                                <td>Izlazak sunca<br/>
                                    <% out.println(wData.getSunrise().toString());%>
                                </td>
                            </tr>
                            <tr align="center">
                                <td><img alt="sunset" src="images/sunset.jpg" /></td>
                                <td>Zalazak sunca<br/>
                                    <% out.println(wData.getSunset().toString());%>
                                </td>
                            </tr>
                            <tr align="center">
                                <td>Mjesečeva mjena</td>
                                <td align="left" style="background-color:#E8E8E8;"><% out.println("<img src=\"images/weatherBug/" + wData.getMoonPhaseImage() + "\"  width=\"15%\" height=\"15%\" />");%></td>
                            </tr>

                        </table>
                    </div>
            </td></tr>
        </table>
        <%      } //kraj else za NOT wData==null
        } //kraj else za NOT getparam(zip)==null
} //else - od korisnik je prijavljen na sustav%>
    </body>
</html>
