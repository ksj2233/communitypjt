var time = 0;
        var starFlag = true;
        function init() {
            document.getElementById("hour").innerHTML = "00";
            document.getElementById("min").innerHTML = "00";
            document.getElementById("sec").innerHTML = "00";
        }
        $(".start").on("click", () => {
            // console.log("qwer");
            if (starFlag) {
                starFlag = false;
                if (time == 0) {
                    init();
                }
                timer = setInterval(function () {
                    time++;
                    min = Math.floor(time / 60);
                    hour = Math.floor(min / 60);
                    sec = time % 60;
                    min = min % 60;

                    var th = hour;
                    var tm = min;
                    var ts = sec;
                    if (th < 10) {
                        th = "0" + hour;
                    }
                    if (tm < 10) {
                        tm = "0" + min;
                    }
                    if (ts < 10) {
                        ts = "0" + sec;
                    }
                    document.getElementById("hour").innerHTML = th;
                    document.getElementById("min").innerHTML = tm;
                    document.getElementById("sec").innerHTML = ts;
                }, 1000);
            }
        });
        $(".stop").on("click", () => {
            clearInterval(timer);
            starFlag = true;
        })
        $(".reset").on("click", () => {
            starFlag = true;
            document.getElementById("sec").innerText = "00";
            document.getElementById("min").innerText = "00";
            document.getElementById("hour").innerText = "00";
            time = 0;
        })