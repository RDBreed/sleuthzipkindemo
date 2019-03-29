function ToasterModal(){
  const parser = new DOMParser();

  function openModal(title, text){
    const newChild = document.createElement('div');
    newChild.setAttribute('class', 'toaster-modal');
    const holder = document.getElementById('toaster-modal-holder');
    holder.insertBefore(newChild, holder.firstChild);
    const html = _getInnerHtml();
    html.getElementById("title").innerText = title;
    html.getElementById("text").innerHTML = text;
    newChild.innerHTML = html.body.innerHTML;
    newChild.classList.add('show');
    const onClick = ()=>{
      newChild.classList.remove('show');
      newChild.classList.add('close');
    };
    newChild.addEventListener("click", onClick);
    setTimeout(function(){
      newChild.classList.remove('show');
      newChild.classList.add('close');
    }, 3000);
    setTimeout(function(){
      newChild.classList.remove('close');
      newChild.removeEventListener("click", onClick);
    }, 5000);
  }

  function _getInnerHtml(){
   return _render(`<h1 id="title">title</h1>
            <p id="text">text</p>`);
  }
  function _render(string){
    return parser.parseFromString(string, "text/html");
  }
  this.openModal = openModal;
}