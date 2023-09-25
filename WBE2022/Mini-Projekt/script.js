// import { connect4winner } from "connect4winner.js";

const SERVICE = "http://localhost:3000/api/data/c4state?api-key=c4game"
let state = {
    board: [],
    nextPlayer: "red"
};
let next = undefined
let board = undefined;

function elt(type, attrs, ...children) {
    let node = document.createElement(type)
    Object.keys(attrs).forEach(key => {
        node.setAttribute(key, attrs[key])
    })
    for (let child of children) {
        if (typeof child != "string") node.appendChild(child)
        else node.appendChild(document.createTextNode(child))
    }
    return node
}

let showBoard = () => {
    const rows = state.board.map((row, rowIndex) => {
        const fields = row.map((field, fieldIndex) => {
            const classes = "field"
            const fieldElement = elt("div", {"class": classes, "data-row": rowIndex, "data-col": fieldIndex});
            if (field !== "" && "rb".includes(field)) {
                const colorClass = field === "r" ? "red" : "blue"
                const piece = elt("div", {class: colorClass + " piece", style: "z-index:-1"})
                fieldElement.appendChild(piece)
            }
            return fieldElement
        })
        return elt("div", {class: "row"}, ...fields)
    })
    board.innerHTML = ""
    board.append(...rows)
}

function initGame() {
    board = document.getElementsByClassName("board")[0]
    initState()
    showBoard()
    evaluateNextPlayer()
}

/**
 * Insert a figure of the color at the given position. If the randomly
 * choosen position is already occupied, then clear the cell.
 * @param color
 * @deprecated use evaluateClickedField instead
 */
function insertOrRemoveFigureAtRandomPlace(color) {
    // this method is not used in the current version of the game, but it may be useful in the future to implement a bot
    const row = Math.floor(Math.random() * state.board)
    const col = Math.floor(Math.random() * state.board[0].length)
    state[row][col] = state[row][col] === "" ? color[0] : ""
}

let initState = (rows = 6, cols = 7) => {
    state.board = Array(rows).fill('').map(_ => Array(cols).fill(''))
    state.nextPlayer = "red"
}

let evaluateNextPlayer = () => {
    next = next === "red" ? "blue" : "red"
    document.getElementById("nextPlayer").innerHTML = next;
    document.getElementById("nextPlayer").style.color = next;
}

let evaluateClickedField = (row, col) => {
    if (state.board[row][col] !== "") {
        alert("Field is already occupied!");
        return;
    }

    // fixme: optimize this loop
    for (let i = state.board.length - 1; i >= 0; i--) {
        if (state.board[i][col] === "") {
            state.board[i][col] = next[0];
            break;
        }
    }

    evaluateNextPlayer();
    showBoard();
}

document.addEventListener('DOMContentLoaded', () => {
    initGame()
    document.body.addEventListener("click", () => {
        if ("field,piece".includes(event.target.classList)) {
            // fixme: dont use event (Deprecated symbol used, consult docs for better alternative)
            const row = event.target.dataset.row
            const col = event.target.dataset.col
            evaluateClickedField(row, col);
            console.log("YOOO")
            if (connect4Winner(next, state.board)) {
                console.log("FUUUCK")
                alert("Winner is " + next);  // announce game end
                // initState();
                // showBoard();
            }
        }
    });

    document.getElementById("btnReset").addEventListener("click", () => {
        initState()
        showBoard()
        evaluateNextPlayer()
    })

});

function connect4Winner(color, board) {
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


