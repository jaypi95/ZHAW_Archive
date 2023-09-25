export function connect4winner(color, board) {
    // check for horizontal win
    console.log("Check horizontal");
    for (let row = 0; row < board.length; row++) {
        for (let col = 0; col < board[row].length - 3; col++) {
            if (board[row][col] === color && board[row][col + 1] === color && board[row][col + 2] === color && board[row][col + 3] === color) {
                return true;
            }
        }
    }
    console.log("Horizontal checked");


    console.log("Check vertical");
// check for vertical win
    for (let row = 0; row < board.length - 3; row++) {
        for (let col = 0; col < board[row].length; col++) {
            if (board[row][col] === color && board[row + 1][col] === color && board[row + 2][col] === color && board[row + 3][col] === color) {
                return true;
            }
        }
    }
    console.log("vertical checked");

    console.log("Check diagonal");

// check for diagonal win
    for (let row = 0; row < board.length - 3; row++) {
        for (let col = 0; col < board[row].length - 3; col++) {
            if (board[row][col] === color && board[row + 1][col + 1] === color && board[row + 2][col + 2] === color && board[row + 3][col + 3] === color) {
                return true;
            }
        }
    }
    console.log("diagonal checked");

    console.log("Check anti diagonal");
// check for anti diagonal win
    for (let row = 0; row < board.length - 3; row++) {
        for (let col = 3; col < board[row].length; col++) {
            if (board[row][col] === color && board[row + 1][col - 1] === color && board[row + 2][col - 2] === color && board[row + 3][col - 3] === color) {
                return true;
            }
        }
    }
    console.log("anti diagonal checked");

    return false
}

