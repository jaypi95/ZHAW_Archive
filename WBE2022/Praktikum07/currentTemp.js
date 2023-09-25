
const http = require('https')


const currentTemp = function(place){
    http.get(`https://wttr.in/${place}?format=j1`, (res) => {

        let data = ''
        res.on('data', (chunk) => {
            data += chunk
        })
        res.on('end', () => {
            const parsedJSON = JSON.parse(data)

            console.log(parsedJSON.current_condition[0].temp_C + '°')
        })
    })

}

currentTemp(process.argv.slice(2))