function Bacon(){
  const Functions = new DOMFunctions();
  async function getFriedBacon(State) {
    Functions.displayBlock("fried-bacon");
    Functions.displayNone("bacon");
    await new Promise(resolve => setTimeout(resolve, 1000));
    new Http().makeRequest('GET',
        'https://localhost:8081/breakfast/bacon', State.getConversationId()).then(
        function (response) {
          State.setConversationId(response.getResponseHeader("x-b3-convid"));
          Functions.displayBlock("bacon-on-bread");
          Functions.displayBlock("plate");
          Functions.displayNone("fried-bacon");
        }, function (error) {
          console.error("Failed!", error);
        });
  };
  this.getFriedBacon = getFriedBacon;
}