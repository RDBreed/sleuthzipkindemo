function Egg(){
  const Functions = new DOMFunctions();

  async function getFriedEgg(State){
    Functions.displayBlock("fried-egg");
    Functions.displayNone("egg");
    await new Promise(resolve => setTimeout(resolve, 1000));
    new Http().makeRequest('GET', 'http://localhost:8081/breakfast/egg', State.getConversationId()).then(
        function (response) {
          State.setConversationId(response.getResponseHeader("x-b3-convid"));
          Functions.displayBlock("egg-on-bread");
          Functions.displayBlock("plate");
          Functions.displayNone("fried-egg");
        }, function (error) {
          console.error("Failed!", error);
        });
  }
  this.getFriedEgg = getFriedEgg;
}