package edu.sustech.xiangqi.model;

public class HorsePiece extends Piece {
    public HorsePiece(boolean isRed) {
        super(isRed ? "傌" : "馬", isRed);
    }
    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        //Khởi tạo 2 biến check diff col và row
        int rowDiff = Math.abs(toRow-fromRow);
        int colDiff = Math.abs(toCol-fromCol);
        //movement logic
        if (!((rowDiff==1&&colDiff==2)||(rowDiff==2&&colDiff==1))) { //Check xem có đi được chữ L ko
            return false;
        }
        //Check quân chắn đường
        if (rowDiff==1) {
            int step = toCol > fromCol ? 1 : -1;
            return board.getPieceAt(fromRow, fromCol + step) == null;
        }
        else {
            int step = toRow > fromRow ? 1 : -1;
            return board.getPieceAt(fromRow + step, fromCol) == null;
        }
    }
}
