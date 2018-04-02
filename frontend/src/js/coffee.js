function Coffee(){
  const Functions = new DOMFunctions();
  const Modal = new ToasterModal();
  async function getCoffee(State){
    await new Promise(resolve => setTimeout(resolve, 1000));
    new Http().makeRequest('GET',  State.getBaseUrl() + '/breakfast/coffee', State.getConversationId()).then(
        function (xmlHttpResponse) {
          State.setConversationId(xmlHttpResponse.getResponseHeader("x-b3-convid"));
          Functions.changeImage("coffee-cup", "images/fullcoffeecup.png");
          Functions.addListener("coffee-cup", "click", ()=>{
            Functions.displayNone("coffee-cup");
            Functions.displayBlock("full-coffee-cup");
          });
          Modal.openModal("Coffee", `Coffee is ready:<br />Temperature: `
              + xmlHttpResponse.response.temperature + `<br />Power: `
              + xmlHttpResponse.response.power + `<br />Preference: `
              + xmlHttpResponse.response.preference)
        }, function (error) {
          console.error("Failed!", error);
        });
  }
  this.getCoffee = getCoffee;
}