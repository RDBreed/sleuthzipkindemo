function Coffee(){
  const Functions = new DOMFunctions();

  async function getCoffee(State){
    await new Promise(resolve => setTimeout(resolve, 1000));
    new Http().makeRequest('GET', 'https://localhost:8081/breakfast/coffee', State.getConversationId()).then(
        function (response) {
          State.setConversationId(response.getResponseHeader("x-b3-convid"));
          Functions.changeImage("coffee-cup", "images/fullcoffeecup.png");
          Functions.addListener("coffee-cup", "click", ()=>{
            Functions.displayNone("coffee-cup");
            Functions.displayBlock("full-coffee-cup");
          });
        }, function (error) {
          console.error("Failed!", error);
        });
  }
  this.getCoffee = getCoffee;
}