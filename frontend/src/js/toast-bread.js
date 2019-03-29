function Bread() {
  const Functions = new DOMFunctions();
  const Modal = new ToasterModal();

  function getToastedBread(State) {
    Functions.removeClass("toast-bread-holder", "toasted");
    Functions.addClass("toast-bread-holder", "toastit");
    Functions.removeClass("toast-button-holder", "toasted");
    Functions.addClass("toast-button-holder", "toastit");
    setTimeout(()=>_toastIt(State), 1000);
  }

  function _toastIt(State) {
    const t0 = performance.now();
    new Http().makeRequest('GET',
        State.getBaseUrl() + '/breakfast/toastedbread',
        State.getConversationId()).then(
        function (xmlHttpResponse) {
          const t1 = performance.now();
          State.setConversationId(
              xmlHttpResponse.getResponseHeader("x-b3-convid"));
          console.log("Success!", xmlHttpResponse);
          Functions.removeAllListeners("toast-bread-holder");
          const toastBread = Functions.removeAllListeners("toast-bread");
          let extraInfo = ':';
          if (t1 - t0 > 5000) {
            Functions.changeBackgroundImage("toast-bread", "images/dark.png");
            extraInfo  = ' BUT BURNED :(';
            toastBread.addEventListener("click", function () {
              Functions.changeBackgroundImage("toast-bread",
                  "images/light.png");
            });
          } else {
            Functions.changeBackgroundImage("toast-bread", "images/brown.png");
            toastBread.addEventListener("click", _displayPlateWithBread);
          }
          _toasted();
          Modal.openModal("Toast", `Bread is toasted`+extraInfo+`<br />Temperature: `
              + xmlHttpResponse.response.temperature + `<br />Power: `
              + xmlHttpResponse.response.power
              + `<br />Preference: `
              + xmlHttpResponse.response.preferedDoneness)
        }, function (error) {
          _toasted();
          Modal.openModal("Toast", `Bread toasting went wrong. Try again!`);
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
