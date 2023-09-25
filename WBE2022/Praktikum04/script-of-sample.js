//Has to be removed for radar.zhaw.ch
//require('./scripts')

const scriptOfSample = function (sample, SCRIPTS) {
    const foundScript = SCRIPTS.find(_ => _.ranges.some(range => sample.codePointAt(0) >= range[0] && sample.codePointAt(0) <= range[1]))
    return foundScript ? foundScript.name : "unknown"
}

module.exports = { scriptOfSample }
