(function () {
  document.addEventListener("DOMContentLoaded", function () {
    let State = new PropertyState();
    document.getElementById("toast-button").addEventListener("click", ()=>new Bread().getToastedBread(State));
    document.getElementById("egg").addEventListener("click", ()=>new Egg().getFriedEgg(State));
    document.getElementById("bacon").addEventListener("click", ()=>new Bacon().getFriedBacon(State));
  });
})(this);