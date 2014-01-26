/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.ArchivedData;
import service.CityData;
import serviceClient.korisnici;
import serviceClient.prognoze;

/**
 * @author sheky
 */
public class ajaxServlet extends HttpServlet {

    private LinkedList<service.CityData> listCData = new LinkedList<CityData>();
    public CityData cData = new CityData();
    Integer pageSize = 20;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        try {
            String command = request.getParameter("command").toLowerCase();
            String isCommand = null;
            
            HttpSession session = request.getSession(true);

            StringBuffer sb = new StringBuffer();

            isCommand = "loginUser";
            if (command.equals(isCommand.toLowerCase())) {
                response.setContentType("text/xml");
                String uName = request.getParameter("userName");
                String uPass = request.getParameter("userPass");

                service.UserData udata = korisnici.userAutentification(uName, uPass);


                if (udata.isError() == false) {
                    request.getSession();
                    session.setAttribute("userId", udata.getUserID());
                    session.setAttribute("firstName", udata.getFirstName());
                    session.setAttribute("lastName", udata.getLastName());
                    session.setAttribute("email", udata.getEmail());
                    session.setAttribute("userName", udata.getUserName());

                    sb.append("<body>");

                    sb.append("<object objectID=\"content\">");
                    sb.append("<![CDATA[");
                    sb.append("<div align=\"center\">");
                    sb.append("<h2>Uspješno ste se prijavili na sustav.</h2>");
                    sb.append("</div>");
                    sb.append("]]>");
                    sb.append("</object>");

                    sb.append("<object objectID=\"login\">");
                    sb.append("<![CDATA[<a href=\"\" onclick=\"doRequest('GET',true,true,'content','ajaxServlet?command=odjava');return false;\">Odjava</a>]]>");
                    sb.append("</object>");

                    sb.append("<object objectID=\"korisnik\">");
                    sb.append("<![CDATA[<a href=\"\" onclick=\"doRequest('GET',false,true,'content','pages/frmUserData.jsp');return false;\">" + uName + "</a>]]>");
                    sb.append("</object>");

                    sb.append("</body>");
                } else {
                    sb.append("<body>");
                    sb.append("<object objectID=\"err\">");
                    sb.append(udata.getMessage());
                    sb.append("</object>");
                    sb.append("</body>");

                }

            }

            isCommand = "odjava";
            if (command.equals(isCommand.toLowerCase())) {
                response.setContentType("text/xml");
                session.invalidate();


                sb.append("<body>");
                sb.append("<object objectID=\"content\">");
                sb.append("<![CDATA[");
                sb.append("<div align=\"center\">");
                sb.append("<h2>Uspješno ste se odjavili sa sustava</h2>");
                sb.append("</div>");
                sb.append("]]>");
                sb.append("</object>");

                sb.append("<object objectID=\"login\">");
                sb.append("<![CDATA[<a href=\"\" onclick=\"doRequest('GET',false,true,'content','pages/frmLoginUser.jsp');return false;\">Prijava</a>]]>");
                sb.append("</object>");

                sb.append("<object objectID=\"korisnik\">");
                sb.append("<![CDATA[<a href=\"\" >" + "" + "</a>]]>");
                sb.append("</object>");

                sb.append("</body>");
            }


            isCommand = "createNewUser";
            if (command.equals(isCommand.toLowerCase())) {
                response.setContentType("text/xml");
                String ime = request.getParameter("ime");
                String prezime = request.getParameter("prezime");
                String email = request.getParameter("email");
                String uName = request.getParameter("uName");
                String uPass = request.getParameter("uPass");


                String msg = null;
                if (ime == null || ime.length() == 0) {
                    msg = "Miste upisali ime.";
                } else if (prezime == null || prezime.length() == 0) {
                    msg = "Niste upisali prezime.";
                } else if (email == null || email.length() == 0 && !email.contains("@")) {
                    msg = "Niste upisali ispravan e-mail.";
                } else if (uName == null || uName.length() == 0) {
                    msg = "Niste upisali korisničko ime.";
                } else if (uPass == null || uPass.length() < 6) {
                    msg = "Lozinka mora sadržavati minimalno 6 znakova.";
                }

                if (msg == null) {
                    service.UserData udata = korisnici.registerUser(uName, uPass, ime, prezime, email);

                    if (udata.isError() == false) {
                        sb.append("<body>");
                        sb.append("<object objectID=\"content\">");
                        sb.append("<![CDATA["); //CDATA
                        sb.append("<div align=\"center\">");
                        sb.append("<h2>Registracija je uspješna.</h2>");
                        sb.append("Korisničko ime: " + udata.getUserName() + "<br/>");
                        sb.append("<a href=\"\" onclick=\"doRequest('GET',false,true,'content','pages/frmLoginUser.jsp');return false;\">Možete se prijaviti na sustav</a>");
                        sb.append("</div>");
                        sb.append("]]>");       //end CDATA
                        sb.append("</object>");
                        sb.append("</body>");
                    } else {
                        sb.append("<body>");
                        sb.append("<object objectID=\"err\">");
                        sb.append(udata.getMessage());
                        sb.append("</object>");
                        sb.append("</body>");
                    }
                } else {
                    sb.append("<body>");
                    sb.append("<object objectID=\"err\">");
                    sb.append(msg);
                    sb.append("</object>");
                    sb.append("</body>");
                }
            }


            isCommand = "changePass";
            if (command.equals(isCommand.toLowerCase())) {
                response.setContentType("text/xml");
                sb.append("<body>");
                sb.append("<object objectID=\"changePass\">");
                sb.append("<![CDATA["); //CDATA
                sb.append("<form id=\"frmNewPass\">");
                sb.append("<input name=\"userNewPass\" type=\"password\" size=\"20\" maxlength=\"30\"/><br/>");
                sb.append("<a href=\"\" onclick=\"doRequestForm('frmNewPass',true,'content','ajaxServlet?command=prihvatiUserPass');return false;\" >Prihvati</a>");
                sb.append("&nbsp;&nbsp;<a href=\"\" onclick=\"doRequest('GET',true,false,'content','ajaxServlet?command=odustaniUserPass');return false;\">Odustani</a>");
                sb.append("</form>");
                sb.append("]]>");       //end CDATA
                sb.append("</object>");
                sb.append("</body>");
            }

            isCommand = "prihvatiUserPass";
            if (command.equals(isCommand.toLowerCase())) {
                response.setContentType("text/xml");

                String uPass = request.getParameter("userNewPass");
                Integer userId = Integer.parseInt(session.getAttribute("userId").toString());

                service.UserData uData = korisnici.changeUserProprties(userId, uPass, "");

                if (uData.isError() == false) {
                    sb.append("<body>");
                    sb.append("<object objectID=\"changePass\">");
                    sb.append("<![CDATA["); //CDATA
                    sb.append(uData.getMessage() + "<br/>");
                    sb.append("<a href=\"\" onclick=\"doRequest('GET',true,false,'changePass','ajaxServlet?command=changePass');return false;\">Promjena lozinke</a>");
                    sb.append("]]>");       //end CDATA
                    sb.append("</object>");
                    sb.append("</body>");
                } else {
                    sb.append("<body>");
                    sb.append("<object objectID=\"err\">");
                    sb.append(uData.getMessage());       //end CDATA
                    sb.append("</object>");
                    sb.append("</body>");
                }
            }

            isCommand = "odustaniUserPass";
            if (command.equals(isCommand.toLowerCase())) {
                response.setContentType("text/xml");
                sb.append("<body>");
                sb.append("<object objectID=\"changePass\">");
                sb.append("<![CDATA["); //CDATA
                sb.append("<a href=\"\" onclick=\"doRequest('GET',true,false,'changePass','ajaxServlet?command=changePass');return false;\">Promjena lozinke</a>");
                sb.append("]]>");       //end CDATA
                sb.append("</object>");
                sb.append("</body>");
            }

            isCommand = "search";
            if (command.equals(isCommand.toLowerCase())) {
                String zip = request.getParameter("txtZIP");
                String city = request.getParameter("txtCity");
                String state = request.getParameter("txtState");
                String county = request.getParameter("txtCounty");
                String cbArchive = request.getParameter("cbHasArchive");


                boolean hasArchive = false;
                if (cbArchive == null) {
                    hasArchive = false;
                } else {
                    hasArchive = true;
                }

                getSearchResUSA(zip, city, state, county, hasArchive);

                response.setContentType("text/xml");
                sb.append("<body>");
                sb.append("<object objectID=\"searchRes\">");
                sb.append("<![CDATA["); //CDATA
                sb.append(frmSearchResUSA(0));
                sb.append("]]>");       //end CDATA
                sb.append("</object>");
                sb.append("</body>");
            }


            isCommand = "page";
            if (command.equals(isCommand.toLowerCase())) {
                response.setContentType("text/xml");
                String pg = request.getParameter("fromPg");
                Integer fromPg = Integer.parseInt(pg);

                sb.append("<body>");
                sb.append("<object objectID=\"searchRes\">");
                sb.append("<![CDATA[");
                sb.append(frmSearchResUSA(fromPg));
                sb.append("]]>");
                sb.append("</object>");
                sb.append("</body>");
            }

            //System.out.println(sb);
            out.print(sb);
        } finally {
            out.close();
        }
    }

    public void getSearchResUSA(String zip, String city, String state, String county, boolean hasArchive) {
        listCData = prognoze.searchCity(zip, city, state, county, hasArchive);
    }

    public String frmSearch() {
        StringBuffer sb = new StringBuffer();

        sb.append("<h1>Pretraživanje gradova</h1>");

        sb.append("<form id=\"frmSearch\">");
        sb.append("        Pretraživanje prema:<br/>&nbsp;(popunit minimalno jedan unos)");
        sb.append("        <table>");
        sb.append("            <tr>");
        sb.append("                <td>ZIP kod</td><td> <input type=\"text\" size=\"10\" id=\"txtZIP\" name=\"txtZIP\" onkeydown=\"if(event.keyCode==13) {doRequestForm('frmSearch',true,'searchRes','ajaxServlet?command=search');}\" /></td>");
        sb.append("                <td>&nbsp;Naziv grada</td><td> <input type=\"text\" size=\"12\" id=\"txtCity\" name=\"txtCity\" onkeydown=\"if(event.keyCode==13) {doRequestForm('frmSearch',true,'searchRes','ajaxServlet?command=search');}\"/></td>");
        sb.append("                <td>&nbsp;Naziv okruga</td><td> <input type=\"text\" size=\"12\" id=\"txtCounty\" name=\"txtCity\" onkeydown=\"if(event.keyCode==13) {doRequestForm('frmSearch',true,'searchRes','ajaxServlet?command=search');}\"/></td>");
        sb.append("                <td>&nbsp;Naziv države</td><td> <input type=\"text\" size=\"12\" id=\"txtState\" name=\"txtState\" onkeydown=\"if(event.keyCode==13) {doRequestForm('frmSearch',true,'searchRes','ajaxServlet?command=search');}\"/></td>");
        sb.append("            </tr><tr>");
        sb.append("                <td>&nbsp; <input type=\"checkbox\" id=\"cbHasArchive\" name=\"cbHasArchive\" value=\"true\"/></td><td>Gradovi s arhivama</td>");
        sb.append("                <td><input type=\"button\" value=\"Pretraži\" onclick=\"doRequestForm('frmSearch',true,'searchRes','ajaxServlet?command=search');\"/></td>");
        sb.append("                <td></td><td></td>");
        sb.append("                <td></td><td></td>");
        sb.append("            </tr>");
        sb.append("        </table>");
        sb.append("    </form>");

        return sb.toString();
    }

    public String frmSearchResUSA(Integer fromPg) {
        StringBuffer sb = new StringBuffer();
        String tmp_county = "";
        String tmp_zip = "";
        String tmp_state = "";
        String county = "";
        int left = 0;
        int count = 0;
        int br_rez = 0;
        int br_stranica = 0;
        int n = 0;

        try {

            br_rez = listCData.size();

            sb.append("<table width=\"100%\"><tr><td align=\"center\">");
            sb.append("Stranica");
            br_stranica =
                    br_rez / pageSize;
            if ((br_rez % pageSize) != 0) {
                br_stranica++;
            }

            for (n = 0; n <
                    br_stranica; n++) {
                if ((n % 20) == 0) {
                    sb.append("<br/>");
                }

                if (fromPg != n) {
                    sb.append("&nbsp;&nbsp;<a href=\"\" onclick=\"doRequest('GET',true,true,'searchRes','ajaxServlet?command=page&fromPg=" + n + "');return false;\">" + (n + 1) + "</a>");
                } else {
                    sb.append("&nbsp;&nbsp;<span class=\"actualPg\">" + (n + 1) + "</span>");
                }

            }

            sb.append("</td></tr></table>");



            if (listCData.size() == 0) {
                sb.append("<h1>Nema rezultata pretrage.</h1>");
            } else {
                sb.append("<hr/>");
                sb.append("<table width=\"100%\">");

                Iterator<CityData> itCData = listCData.iterator();

                while (itCData.hasNext()) {
                    cData = itCData.next();


                    if ((count >= (fromPg * pageSize)) && (count < ((fromPg * pageSize) + pageSize))) {
                        if (tmp_state.equals(cData.getState())) {
                            //ostaje ista država
                        } else {
                            //mjenja se država
                            tmp_state = cData.getState();
                            left = 0;
                            sb.append("<tr><td colspan=\"5\" class=\"state\" >Država: <span class=\"state\">" + tmp_state + "</span></td></tr>");
                        }


                        if (cData.getCounty() == null) {
                            county = "";
                        } else {
                            county = cData.getCounty();
                        }

                        if (tmp_county.endsWith(county)) {
                            tmp_zip = cData.getZipCode();
                            //sb.append("<a href=\"\" onclick=\"doRequest2('GET',false,true,'result','pages/frmWeatherData.jsp?zip=" + tmp_zip + "');return false;\" >" + cData.getCity() + " (" + tmp_zip + ")<a/><br/>");
                            sb.append("<a href=\"\" onclick=\"doRequest2('GET',false,true,'resultArchive','pages/frmArhiva.jsp?zip=" + tmp_zip + "');return false;\" >" + cData.getCity() + " (" + tmp_zip + ")<a/><br/>");
                        } else {
                            left++;
                            if (left % 2 != 0) { //ljevo
                                if (left > 2) {
                                    sb.append("</tr>");
                                }
                                sb.append("<tr  valign=\"top\">");
                                sb.append("<td>&nbsp;Okrug: <span class=\"county\">" + cData.getCounty() + "</span></td>");
                                tmp_county = cData.getCounty();
                                sb.append("<td>");
                                tmp_zip = cData.getZipCode();
                                //sb.append("<a href=\"\" onclick=\"doRequest2('GET',false,true,'result','pages/frmWeatherData.jsp?zip=" + tmp_zip + "');return false;\" >" + cData.getCity() + " (" + tmp_zip + ")<a/><br/>");
                                sb.append("<a href=\"\" onclick=\"doRequest2('GET',false,true,'resultArchive','pages/frmArhiva.jsp?zip=" + tmp_zip + "');return false;\" >" + cData.getCity() + " (" + tmp_zip + ")<a/><br/>");
                            } else { //desno
                                sb.append("</td>");
                                sb.append("<td width=\"10%\"></td>");
                                sb.append("<td>&nbsp;Okrug: <span class=\"county\">" + cData.getCounty() + "</span></td>");
                                tmp_county = cData.getCounty();
                                sb.append("<td>");

                                tmp_zip = cData.getZipCode();
                                //sb.append("<a href=\"\" onclick=\"doRequest2('GET',false,true,'result','pages/frmWeatherData.jsp?zip=" + tmp_zip + "');return false;\" >" + cData.getCity() + " (" + tmp_zip + ")<a/><br/>");
                                sb.append("<a href=\"\" onclick=\"doRequest2('GET',false,true,'resultArchive','pages/frmArhiva.jsp?zip=" + tmp_zip + "');return false;\" >" + cData.getCity() + " (" + tmp_zip + ")<a/><br/>");
                            }
                        }
                    }
                    count++;
                }
                sb.append("</table>");
            }

        } catch (Exception ex) {
            sb.append("Nema rezultata pretrage.");
        }

        return sb.toString();
    }

    public String frmListArchive(String zip) {
        StringBuffer sb = new StringBuffer();
        LinkedList<service.ArchivedData> listArcData = prognoze.listCityArchive(zip);
        ArchivedData aData = new ArchivedData();
        Iterator<service.ArchivedData> itArch = listArcData.iterator();
        int year = 0, month = 0;
        String datum = "";

        while (itArch.hasNext()) {
            aData = itArch.next();
            if (aData.isError() == true) {
                sb.append(aData.getMessage());
                break;
            } else {
                if (year != aData.getOnDate().getYear()) {
                    year = aData.getOnDate().getYear();
                    sb.append("godina: " + year + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                }
                if (month != aData.getOnDate().getMonth()) {
                    month = aData.getOnDate().getMonth();
                    sb.append("|");
                }
                datum = aData.getOnDate().getDay() + "." + aData.getOnDate().getMonth() + "." + aData.getOnDate().getYear() + "  " + aData.getOnDate().getHour() + ":" + aData.getOnDate().getMinute();
                sb.append("<a class=\"timeline\" href=\"\" title=\"" + datum + "\" onmouseover=\"arcSelDatum('" + datum + "');\" onclick=\"doRequest2('GET',false,true,'result','pages/frmWeatherData.jsp?zip=" + zip + "&id=" + aData.getId().toString() + "');return false;\" >" + "-" + "</a>");
            }
        }


        return sb.toString();
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
