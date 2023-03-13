var messageWebSocket = new WebSocket("ws://" + location.host + "/messageAlertHandler");

var memberGet = $("#memberGet").val();
var memberGive = $("#memberGive").val();

$(document).ready(function () {

    $(".box-post").on("click",function(){
        alertMessage();
    });

    messageWebSocket.onopen = function (data) {
        //소켓이 열리면 동작
        console.log('열려부럿따.');

        var num = {
            'memberGive':memberGive
        }

        commonAjax('/hello/memoryUserNum',num,'post');

        messageWebSocket.onmessage = function (data) {
            //메시지가 오면 동작
        }

        messageWebSocket.onclose = function (data) {
            //소켓이 닫히면 동작
        }
    }
});

function commonAjax(url, parameter, type, calbak, contentType) {
    $.ajax({
        url: url,
        data: parameter,
        type: type,
        contentType: contentType != null ? contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
        success: function (res) {
            calbak(res);
        },
        error: function (err) {
            console.log('error');
            calbak(err);
        }
    });
}

// function send() {
//     var option = {
//         'memberGet' : memberGet,
//         'memberGive': memberGive
//     }
//     messageWebSocket.send(JSON.stringify(option));
// }

function alertMessage(){
    var option = {
        'memberGet' : memberGet,
        'memberGive': memberGive
    }

    commonAjax('/hello/alertpost',option,'post',function(){
        alert('발송되었습니다.');
    });

}
