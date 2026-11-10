package edu.sustech.xiangqi;

public class Game {
    Piece[][] board = new Piece[10][9];
    //Khởi tạo bàn cờ 10 row 9 col

    boolean redTurn = true;
    //Nếu boolean này true thì là turn của đỏ

    public Game() { //Constructor
        setupBoard();
    }

    private void setupBoard() {
        char[] order = {
                'R','H','E','A','G',
                'A','E','H','R'
                //Thứ tự bàn cờ
        };

        //Các quân chính ở đầu đuôi bàn cờ
        for (int i=0; i<9; i++) {
            board[0][i]=new Piece(order[i],false);
            //Tạo quân đen ở trên cùng
            board[9][i]=new Piece(order[i],true);
            //Tạo quân đỏ ở dưới cùng
        }

        //Quân pháo
        board[2][1] = new Piece('C',false);
        board[2][7] = new Piece('C',false);
        board[7][1] = new Piece('C',true);
        board[7][7] = new Piece('C',true);

        //Quân tốt
        for (int i=0; i<9; i+=2) {
            board[3][i]=new Piece('P',false);
            board[3][i]=new Piece('P',true);
        }
    }

    //In bàn cờ
    public void printBoard() {
        System.out.println("    0 1 2 3 4 5 6 7 8");
        for (int r=0; r<10; r++) {
            System.out.printf("%d   ", r);
            for (int c=0; c<9; c++) {
                if (board[r][c] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[r][c].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }
}
