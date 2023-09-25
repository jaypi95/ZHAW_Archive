function connect4winner(board, color) {
    // horizontal
    for (let i = 0; i < board.length; i++) {
        let connected = 0
        for (let j = 0; j < board[0].length; j++) {
            if (board[i][j] === color) {
                connected += 1
                if (connected === 4) return true
            } else {
                connected = 0
            }
        }
    }

    // vertical
    for (let j = 0; j < board[0].length; j++) {
        let connected = 0
        for (let i = 0; i < board.length; i++) {
            if (board[i][j] === color) {
                connected += 1
                if (connected === 4) return true
            } else {
                connected = 0
            }
        }
    }

    // diagonal: upper-left to lower-right
    for (let i = 0; i < board.length - 3; i++) {
        for (let j = 0; j < board[0].length - 3; j++) {
            if (board[i][j] === color) {
                if (
                    board[i + 1][j + 1] === color &&
                    board[i + 2][j + 2] === color &&
                    board[i + 3][j + 3] === color
                ) {
                    return true
                }
            }
        }
    }

    // diagonal: upper-right to lower-left
    for (let i = 0; i < board.length - 3; i++) {
        for (let j = 3; j < board[0].length; j++) {
            if (board[i][j] === color) {
                if (
                    board[i + 1][j - 1] === color &&
                    board[i + 2][j - 2] === color &&
                    board[i + 3][j - 3] === color
                ) {
                    return true
                }
            }
        }
    }

    return false
}

module.exports = { connect4winner }
