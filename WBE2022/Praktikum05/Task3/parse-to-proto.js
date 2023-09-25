const parseToProto = (attrs, obj)=>{
    const parsed = JSON.parse(attrs);
    for (var key in parsed) {
        obj[key] = parsed[key];
    }
    return obj;
};

module.exports = { parseToProto };

//Tests
//describe("Parse JSON and assign prototype", function () {
//    let { parseToProto } = require('../../parse-to-proto')
//
//    beforeEach(function () {
//    })
//
//    it("should be able to parse JSON object", function () {
//        let proto = {}
//        let obj = parseToProto('{"type":"cat","name":"Mimi","age":3}', proto)
//        expect(obj.age).toEqual(3)
//    })
//
//    it("should be able to assign prototype", function () {
//        let proto = { category: "animal" }
//        let obj = parseToProto('{"type":"cat","name":"Mimi","age":3}', proto)
//        expect(obj.category).toEqual('animal')
//    })
//
//})