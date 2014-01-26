var objDatum = null;

function trim(variable) {        
    return variable = variable.replace(/\n/g,'');
}

function ch_size(object_id, znak, title1, title2) {
    var el = document.getElementById(object_id);
    var h = el.style.display;    
    if(h=='') { //minimize
        el.style.display="none";
        document.getElementById(znak).innerHTML="&nbsp;+&nbsp;";
        document.getElementById(znak).title=title1;
    }else { //maximize
        el.style.display="";
        document.getElementById(znak).innerHTML="&nbsp;-&nbsp;";
        document.getElementById(znak).title=title2;
    }
}


function arcSelDatum (datum) {
    if(objDatum==null) {
        objDatum = document.getElementById("arhDatum");
    }
    objDatum.innerHTML=datum;
}