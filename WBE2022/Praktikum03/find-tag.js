const findTag = function(tagString) {
    let extractedTag = undefined
    for(i = 0; i < tagString.length; i++){
      if(tagString[i] === "<"){
          for(j = i; j < tagString.length; j++){
              if(tagString[j] !== ">"){
                  if(tagString[j] === "<"){
                      extractedTag = (function () { return; })();
                  } else if(tagString[j] === " ") {
                      extractedTag = (function () { return; })();
                      break
                  } else{
                      if(extractedTag === undefined){
                          extractedTag = ""
                      }
                      extractedTag = extractedTag + tagString[j]
                  }
              } else{
                  return extractedTag
              }
          }
      }
  }
    return extractedTag
}

module.exports = { findTag }

console.log(findTag("<header>Text</header"))
console.log(findTag("blabla <br> blabla"))
console.log(findTag("123245 </header> bla"))
console.log(findTag("123245 <hea der> bla"))
console.log(findTag("123245 <hea<der> bla"))
console.log(findTag("123245 <hea<der bla"))