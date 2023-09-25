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

function addPiece(index, color){
    document.getElementById("board").childNodes[index +3].appendChild( elt('div',{"class": color+" piece"}))
}

function showBoard(){
    for (let index = 0; index < 42; index++) {
        document.getElementById("board").appendChild(
            elt('div',{"class":"field"}))
    }

    let i = 0
    state.forEach(row => {
        row.forEach(element => {
            if(element == 'b'){
                addPiece(i,'blue')
            }
            else if(element == 'r'){
                addPiece(i,'red')
            }
            i += 1
        })
    });

    setInterval(function(){
        let random = Math.floor(Math.random() * 42)
        color =  Math.floor(Math.random() * 2)
        if(document.getElementById("board").childNodes[random+3].hasChildNodes()){
            let element = document.getElementById("board").childNodes[random+3]
            element.removeChild(element.firstChild)
        }
        if(color == 0){
            addPiece(random,'blue')
        }
        else {
            addPiece(random,'red')
        }
    }, 1000);
}