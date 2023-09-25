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
  children = []
  for (let index = 0; index < 42; index++) {
    document.getElementById("board").appendChild(
        elt('div',{"class":"field"}))
  }
  let field = elt("div", {class: "field" }, ...children)
  field.addEventListener("click", () => {
    console.log("click")
  })
}