let currentSlide = 0;
const slides =document.getElementsByClassName('slide');
function showSlide(n){
    slides[currentSlide].style.display="none";
    currentSlide=(n+ slides.length) % slides.length;
    slides[currentSlide].style.display="block";
}
function nextSlide(){
    showSlide(currentSlide + 1);
}
function prevSlide(){
    showSlide(currentSlide - 1);
}

document.getElementById("prevBtn").addEventListener("click",prevSlide);
document.getElementById("nextBtn").addEventListener("click",nextSlide);

setInterval(nextSlide,4000);