package edu.sustech.xiangqi.model;

public class CannonPiece extends Piece {
    public CannonPiece(boolean isRed) {
        super(isRed ? "炮" : "砲", isRed);
    }
    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Movement logic here
        if (Math.abs(toCol-fromCol)!=0 && Math.abs(toRow-fromRow)!=0) {
            return false;
        }
        //Đếm số quân giữa nguồn và đích
        int count = 0;
        //Check quân chắn đường
        if (fromRow == toRow) { // Đi ngang
            int step = toCol > fromCol ? 1 : -1;
            for (int col = fromCol + step; col != toCol; col += step) {
                if (board.getPieceAt(fromRow, col) != null) {
                    count++;
                }
            }
        }
        else { //Đi dọc
            int step = toRow > fromRow ? 1 : -1;
            for (int row = fromRow + step; row != toRow; row += step) {
                if (board.getPieceAt(row, fromCol) != null) {
                    count++;
                }
            }
        }
        //Kiểm tra xem có quân ăn hay không
        Piece target = board.getPieceAt(toRow, toCol); //Khởi tạo target
        //Nếu không có quân ở đích, pháo chỉ được đi khi đường hoàn toàn trống
        if (target == null) {
            return count == 0;
        }
        return count == 1;
    }
}
