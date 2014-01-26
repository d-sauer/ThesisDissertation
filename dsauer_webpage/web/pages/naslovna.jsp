<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>naslovna</title>
        <meta name="author" content="Davor Sauer" />
        <meta name="keywords" content="WSDL FOI weather java web service soa" />
    </head>
    <body>
        <%
            String kor = null, prog=null;
            if(kor==null || prog==null) {
                serviceClient.readProperties rp = new serviceClient.readProperties();
                kor=rp.readProp("wsdl.korisnici");
                prog=rp.readProp("wsdl.prognoze");
            }
        %>
        <br>
        Ova web stranica dio je projektnog primjera diplomskog rada <h3>"RAZVOJ WEB SERVISA PRIMJENOM SERVISNO ORIJENTIRANE ARHITEKTURE"</h3><br/>
        Pomoću ove Web stranice nastoji se pokazati funckionalnost web servisa, na korisnicima pristupačniji način, putem web sučelja.
        <br/>
        Web stranica koristi dva web servisa, koji su razvijeni primjenom principa servisno orijentirane arhitekture.
        Jedan od servisa je servis za vremenske prognoze, čiji se wsdl dokument nalazi na adresi: <a href="<% out.print(prog); %>" target="_blank"><% out.print(prog); %></a> <br/>
        Ovaj servis arhivira vremenske prognoze za gradove SAD-a. Gradovi čije se prognoze arhiviraju, nisu unaprjed definirani,
        već se definicija gradova povećava tijekom korisnikovih zahtjeva za prognozom tih gradova. Na taj način sustav tijekom
        vremena uči, povećavajući svoju arhivu i definiciju gradova koji će se arhivirati.<br/>
        <br/>
        Drugi web servis stranica koristi za registraciju i autorizaciju korisnika koji mogu pristupati podacima o vremenskim prognozama pomoću  web stranice..
        Prilikom komunikacije web stranice s udaljenim servisom, za prijenos informacija koristi se SSL protokol.
        WSDL web servisa za rad s korisnicima se nalazi na adresi <a href="<% out.print(kor); %>" target="_blank"><% out.print(kor); %></a>

        
    </body>
</html>
