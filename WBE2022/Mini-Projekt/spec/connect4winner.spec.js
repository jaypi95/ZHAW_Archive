import {connect4Winner} from "../connect4winner.js";

describe('Check for winner', function () {
    let board;
    const boardWidth = 7;
    const boardHeight = 6;
    const playerOfColor = "r"

    //beforeeach
    beforeEach(function () {
        board = Array(boardHeight).fill('').map(_ => Array(boardWidth).fill(''))
    });

    it('Should be able to recognize 4 tokens in a horizontally row as a win', function () {
        for (let i = 0; i < 4; i++) {
            board[0][i] = playerOfColor;
        }
        expect(connect4Winner(playerOfColor, board)).toBe(true);
    });

    it('Should be able to recognize 4 tokens in a vertically row as a win', function () {
        for (let i = 0; i < 4; i++) {
            board[i][0] = playerOfColor;
        }
        expect(connect4Winner(playerOfColor, board)).toBe(true);
    });


    it('Should be able to recognize 4 tokens in a ascending diagonal row as a win', function () {
        // smort way to do it
        for(let i = 0; i < 4; i++){
            board[i][i] = playerOfColor;
        }
        expect(connect4Winner(playerOfColor, board)).toBe(true);
    });

    it('Should be able to recognize 4 tokens in a anti diagonal row as a win', function () {
        // alternate way to do it
        board[5][0] = playerOfColor;
        board[4][1] = playerOfColor;
        board[3][2] = playerOfColor;
        board[2][3] = playerOfColor;
        expect(connect4Winner(playerOfColor, board)).toBe(true);
    });

    it('Should be able to recognize less than 4 tokens in a horizontally row (not a win)', function () {
        for (let i = 0; i < 3; i++) {
            board[0][i] = playerOfColor;
        }
        expect(connect4Winner(playerOfColor, board)).toBe(false);
    });


})
