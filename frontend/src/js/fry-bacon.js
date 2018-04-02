function Bacon(){
  const Functions = new DOMFunctions();
  const Modal = new ToasterModal();
  async function getFriedBacon(State) {
    Functions.displayBlock("fried-bacon");
    Functions.displayNone("bacon");
    await new Promise(resolve => setTimeout(resolve, 1000));
    new Http().makeRequest('GET',
        State.getBaseUrl() + '/breakfast/bacon', State.getConversationId()).then(
        function (xmlHttpResponse) {
          State.setConversationId(xmlHttpResponse.getResponseHeader("x-b3-convid"));
          Functions.displayBlock("bacon-on-bread");
          Functions.displayBlock("plate");
          Functions.displayNone("fried-bacon");
          Modal.openModal("Bacon", `Bacon is fried:<br />Preference: `
              + xmlHttpResponse.response.preference)
        }, function (error) {
          console.error("Failed!", error);
        });
  };
  this.getFriedBacon = getFriedBacon;
}