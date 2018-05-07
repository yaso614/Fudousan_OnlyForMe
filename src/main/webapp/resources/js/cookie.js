function setCookie(name, value, day){
    let expire = new Date();
    expire.setDate(expire.getDate() + day);

    let cookies = name + "=" + escape(value) + "; path=/";

    if (typeof day != "undefined"){
        cookies += "; expires=" + expire.toGMTString() + ";";
    }

    document.cookie = cookies;
}

function getCookie(name){
    name = name + "=";

    let cookieData = document.cookie;
    let start = cookieData.indexOf(name);
    let value = "";

    if (start != -1){
        start += name.length;
        let end = cookieData.indexOf(";", start);

        if (end == -1){
            end = cookieData.length;
        }

        value = cookieData.substring(start, end);
    }

    return unescape(value);
}