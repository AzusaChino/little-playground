/*Creates a function that accepts up to one argument, ignoring any additional arguments.

Call the provided function, fn, with just the first argument given.*/

const unary = fn => val => fn(val)

['6', '8','10'].map(unary(parseInt))
