
const reset = document.querySelector(".reset");
const large = document.querySelector(".large");
const small = document.querySelector(".small");
const content = document.querySelector(".content");
const entermenu = document.querySelector(".entermenu");
const button = document.querySelector(".menu");

let i = 10;

reset.addEventListener("click", ()=>{
    content.value='';
})
large.addEventListener("click", ()=>{
    i++
    content.style.fontSize = i+"px";
})
small.addEventListener("click", ()=>{
    i--;
    content.style.fontSize = i+"px";
})
entermenu.addEventListener("click", ()=>{
    if(button.style.display === "block"){
        button.style.display = "none";
    } else{
        button.style.display = "block";
    }
})