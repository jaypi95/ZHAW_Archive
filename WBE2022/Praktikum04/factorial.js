const factorial = function(number) {
    bigNumber = BigInt(number)
    if(bigNumber === 0n){
        bigNumber = 1n
    } else {
        for (i = bigNumber - 1n; i > 0; i--) {
            bigNumber = bigNumber * i
        }
    }
    if(typeof number !== "bigint") {
        if (bigNumber <= Number.MAX_SAFE_INTEGER) {
            bigNumber = Number(bigNumber)
        }
    }
    return bigNumber;
}

module.exports = { factorial }

console.log(factorial(50n))
console.log(factorial(0))
console.log(factorial(6))
console.log(factorial(0n))
console.log(factorial(1))
console.log(factorial(1n))
console.log(factorial(2))