const equal = function(param1, param2){
    //check if the two objects are the same object
    if (param1 === param2) {
        return true;
    }
    //check if the two objects are not the same type
    if (typeof param1 !== typeof param2) {
        return false;
    }
    //check if the two objects are not both objects
    if (typeof param1 !== 'object' || typeof param2 !== 'object') {
        return false;
    }
    //check if the two objects have different numbers of keys
    if (Object.keys(param1).length !== Object.keys(param2).length) {
        return false;
    }
    //check if the two objects have the same keys
    for (let key in param1) {
        if (!param2.hasOwnProperty(key)) {
            return false;
        }
    }
    //check if the two objects have the same values
    for (let key in param1) {
        if (param1[key] !== param2[key]) {
            return false;
        }
    }
    //if all checks pass, return true
    return true;
}

module.exports = { equal }
