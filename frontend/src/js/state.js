function PropertyState() {
  let conversationId = null;
  let baseUrl = 'https://localhost:9081';

  function setConversationId(convId) {
    conversationId = convId;
  }

  function getConversationId() {
    return conversationId;
  }
  function getBaseUrl(){
    return baseUrl;
  }
  this.setConversationId = setConversationId;
  this.getConversationId = getConversationId;
  this.getBaseUrl = getBaseUrl;
}