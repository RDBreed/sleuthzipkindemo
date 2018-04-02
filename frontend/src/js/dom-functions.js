function DOMFunctions(){
  this.displayBlock = function (elementId) {
    document.getElementById(elementId).style.display = 'initial';
  };
  this.changeImage = function(elementId, src){
    document.getElementById(elementId).src = src;
  };
  this.displayNone = function (elementId) {
    document.getElementById(elementId).style.display = 'none';
  };
  this.addClass = function (elementId, className) {
    document.getElementById(elementId).classList.add(className);
  };
  this.removeClass = function (elementId, className) {
    document.getElementById(elementId).classList.remove(className);
  };
  this.changeBackgroundImage = function (elementId, imageUrl) {
    document.getElementById(elementId).style.backgroundImage = 'url('
        + imageUrl + ')';
  };
  this.addListener = function(elementId, type, promise){
    document.getElementById(elementId).addEventListener(type, promise);
  };
  this.removeAllListeners = function (elementId) {
    const el = document.getElementById(elementId),
        copy = el.cloneNode(true);

    el.parentNode.replaceChild(copy, el);
    return copy;
  };
  this.whichTransitionEvent = function () {
    let t;
    const el = document.createElement('fakeelement');
    let transitions = {
      'animation': 'animationend',
      'OAnimation': 'oAnimationEnd',
      'MSAnimation': 'MSAnimationEnd',
      'WebkitAnimation': 'webkitAnimationEnd'
    };

    for (t in transitions) {
      if (el.style[t] !== undefined) {
        return transitions[t];
      }
    }
  };

}