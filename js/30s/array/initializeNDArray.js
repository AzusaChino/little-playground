/*
* Create a n-dimensional array with given value.

Use recursion. Use Array.prototype.map() to generate rows where each is a new array initialized using initializeNDArray. */

const initializeNDArray = (val, ...args) =>
    args.length === 0 ? val : Array.from( {length: args[0]}).map(() => initializeNDArray(val, ...args.slice(1)))

initializeNDArray(1, 3); // [1,1,1]
initializeNDArray(5, 2, 2, 2); // [[[5,5],[5,5]],[[5,5],[5,5]]]