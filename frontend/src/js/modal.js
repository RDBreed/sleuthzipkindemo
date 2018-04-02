function Modal(){
  const Functions = new DOMFunctions();

  function openModal(id, title, text){
    document.getElementById(id).className = 'close';

  }
  this.openModal = openModal;
  this.closeModal = closeModal;
}