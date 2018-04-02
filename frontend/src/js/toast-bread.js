function Bread() {
  const Functions = new DOMFunctions();
  // const Modal = new Modal();

  function getToastedBread(State) {
    Functions.removeClass("toast-bread-holder", "toasted");
    Functions.addClass("toast-bread-holder", "toastit");
    Functions.removeClass("toast-button-holder", "toasted");
    Functions.addClass("toast-button-holder", "toastit");

    let transitionEnd = Functions.whichTransitionEvent();
    document.getElementById("toast-bread-holder").addEventListener(
        transitionEnd, () => _toastIt(State), false);
  }

  function _toastIt(State) {
    const t0 = performance.now();
    new Http().makeRequest('GET',
        'https://localhost:8081/breakfast/toastedbread',
        State.getConversationId()).then(
        function (response) {
          const t1 = performance.now();
          State.setConversationId(response.getResponseHeader("x-b3-convid"));
          console.log("Success!", response);
          Functions.removeAllListeners("toast-bread-holder");
          const toastBread = Functions.removeAllListeners("toast-bread");
          if (t1 - t0 > 5000) {
            Functions.changeBackgroundImage("toast-bread", "images/dark.png");
            toastBread.addEventListener("click", function () {
              Functions.changeBackgroundImage("toast-bread",
                  "images/light.png");
            });
          } else {
            Functions.changeBackgroundImage("toast-bread", "images/brown.png");
            toastBread.addEventListener("click", _displayPlateWithBread);
          }
          _toasted();
          // Modal.openModal('modal');
        }, function (error) {
          _toasted();
          console.error("Failed!", error);
        });
  }

  function _toasted() {
    Functions.removeClass("toast-bread-holder", "toastit");
    Functions.addClass("toast-bread-holder", "toasted");
    Functions.removeClass("toast-button-holder", "toastit");
    Functions.addClass("toast-button-holder", "toasted");
  }

  function _displayPlateWithBread() {
    Functions.displayBlock("plate");
    Functions.displayBlock("brown-bread");
    Functions.displayNone("toast-bread");
  }

  this.getToastedBread = getToastedBread;
}
