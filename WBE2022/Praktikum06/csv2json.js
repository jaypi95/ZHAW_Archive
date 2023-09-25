/*
I didn't know which one of the "first" exercises we had to hand in (this one, async or promise)
So I only hand in this one
 */
const fs = require('fs')

const csv2json = (filepath, output, separator = ',') => {
    function createCSVAttributes(attributes){
        const regex = /"([a-zA-Z0-9 (,)&]*)"|(\w*)/gm
        let iterator = 0
        let createdAttribute
        createdAttribute = attributes.match(regex)
        let test = createdAttribute.indexOf("")
        while (test !== -1 && iterator <= 0){
            createdAttribute.splice(test, 1)
            iterator++
            test = createdAttribute.indexOf("")
        }
        return createdAttribute
    }

    try {
        let stats = fs.statSync(filepath)
        console.log("Size: " + stats.size)
        console.log("Last change: " + stats.mtime)
        var startReading = new Date().getTime()
        var data = fs.readFileSync(filepath, 'utf8')
    }
    catch(e){
        console.error("I dun goofed " + e)
        return false
    }

    let rows = data.split('\n')
    console.log("Length: " + rows.length + " lines")
    let readingTime = new Date() - startReading
    console.log("Reading time: " + readingTime + "ms")
    let startProcessing = new Date().getTime()
    let createdAttributes = createCSVAttributes(rows[0])
    rows.shift() //shifting so we can get to the next line
    let iterator = 0
    const result = []
    rows.forEach(item => {
        const entry = {}
        let columns = item.split(separator)
        for(let i = 0; i < item.length; i++){
            entry[createdAttributes[i]] = columns[i]
        }
        result.push(entry)
        iterator++
    })
    let generatedJSON = JSON.stringify(result)
    let processingTime = new Date() - startProcessing
    console.log('Processing time: ' + processingTime + 'ms')

    try{
        fs.writeFileSync(output, generatedJSON)
    } catch(e) {
        console.error(e)
        return false
    }
}

const args = process.argv.slice(2)
const filepath = args[0]
const output = args[1]

csv2json(filepath, output,',')