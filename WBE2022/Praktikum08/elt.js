function elt (type, attrs, ...children) {
  let node = document.createElement(type)
  Object.keys(attrs).forEach(key => {
    node.setAttribute(key, attrs[key])
  })
  for (let child of children) {
    if (typeof child != "string") node.appendChild(child)
    else node.appendChild(document.createTextNode(child))
  }
  return node
}

function showBoard(){
  for (let index = 0; index < 42; index++) {
    document.getElementById("board").appendChild(
        elt('div',{"class":"field"}))
  }
  for (let index = 0; index < 5; index++) {
    let random = Math.floor(Math.random() * 41)+3
    if(!document.getElementById("board").childNodes[random].hasChildNodes()){
      document.getElementById("board").childNodes[random].appendChild( elt('div',{"class":"blue piece"}))
    }
  }
  for (let index = 0; index < 5; index++) {
    let random = Math.floor(Math.random() * 41)+3
    if(!document.getElementById("board").childNodes[random].hasChildNodes()){
      document.getElementById("board").childNodes[random].appendChild( elt('div',{"class":"red piece"}))
    }
  }
}