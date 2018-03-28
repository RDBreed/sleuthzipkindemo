function PropertyState() {
  let conversationId = null;

  function setConversationId(convId) {
    conversationId = convId;
  }

  function getConversationId() {
    return conversationId;
  }
  this.setConversationId = setConversationId;
  this.getConversationId = getConversationId;
}