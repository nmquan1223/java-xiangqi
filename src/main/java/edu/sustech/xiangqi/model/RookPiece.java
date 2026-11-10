package edu.sustech.xiangqi.model;

public class RookPiece extends Piece {
    public RookPiece(boolean isRed) {
        super(isRed ? "俥" : "車", isRed);
    }
    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Movement logic here
        if (Math.abs(toCol-fromCol)!=0 && Math.abs(toRow-fromRow)!=0) {
            return false;
        }
        if (fromCol==toCol) { //Check xem có quân chặn đường dọc ko
            int step = toRow > fromRow ? 1 : -1; // Đi lên thì step là 1, xuống là -1
            for (int row=fromRow + step; row!=toRow; row+=step) {
                if (board.getPieceAt(row, fromCol) != null) {
                    return false;
                }
            }
        }
        else { //Check chắn đường khi đi ngang
            int step = toRow > fromRow ? 1 : -1;
            for (int col=fromCol+step; col!=toCol; col+=step) {
                if (board.getPieceAt(fromRow, col) != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
