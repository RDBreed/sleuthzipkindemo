function Egg(){
  const Functions = new DOMFunctions();
  const Modal = new ToasterModal();

  async function getFriedEgg(State){
    Functions.displayBlock("fried-egg");
    Functions.displayNone("egg");
    await new Promise(resolve => setTimeout(resolve, 1000));
    new Http().makeRequest('GET', State.getBaseUrl() + '/breakfast/egg', State.getConversationId()).then(
        function (xmlHttpResponse) {
          State.setConversationId(xmlHttpResponse.getResponseHeader("x-b3-convid"));
          Functions.displayBlock("egg-on-bread");
          Functions.displayBlock("plate");
          Functions.displayNone("fried-egg");
          Modal.openModal("Egg", `Egg is fried:<br />Temperature: `
              + xmlHttpResponse.response.temperature + `<br />Gas usage: `
              + xmlHttpResponse.response.gas+ `<br />Preference: `
              + xmlHttpResponse.response.preference)
        },
        function (error) {
          console.error("Failed!", error);
        });
  }
  this.getFriedEgg = getFriedEgg;
}