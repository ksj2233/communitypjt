const info = document.querySelector(".info");
const html = document.querySelector("html");
const target = document.querySelector(".target");
let center = {
    x: target.getBoundingClientRect().left + (target.clientWidth / 2),
    y: target.getBoundingClientRect().top + (target.clientHeight / 2)
}

window.addEventListener('resize', () => {
    center = {
        x: target.getBoundingClientRect().left + (target.clientWidth / 2),
        y: target.getBoundingClientRect().top + (target.clientHeight / 2)
    }
    console.log('실행');
})

html.addEventListener('mousemove', (e) => {

    const x = center.y - e.clientY;
    const y = center.x - e.clientX;
    const radian = Math.atan2(y,x);
    const degree = ((radian/2) * -360 / Math.PI).toFixed(0);
    info.innerHTML = degree + '°';
    target.style.transform = 'translate(-50%, -50%) rotate(' + degree + 'deg)';

})