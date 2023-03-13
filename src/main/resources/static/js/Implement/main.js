function call_login(){
    $.ajax({
        type: "get",
        url: "/modal/log-in",
        dataType: "text",
        success: function (data) {
            var str = data.split("<body>|</body>");
            console.log(str[1]);
        }
    });
}


