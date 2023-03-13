const button = document.querySelector(".google");
        const box = document.querySelector(".googlebox");
        button.addEventListener("click", () => {
            if (box.style.display === "block") {
                box.style.display = "none";
            } else {
                box.style.display = "block";
            }
})