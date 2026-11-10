package edu.sustech.xiangqi.model;

public class ElephantPiece extends Piece {
    public ElephantPiece(boolean isRed) {
        super(isRed ? "相" : "象", isRed);
    }
    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        //movement logic
        if (rowDiff != 2 || colDiff != 2) {
            return false;
        }
        //Giới hạn di chuyển của tượng (chưa đến sông)
        if (isRed && toRow < 5) {
            return false;
        }
        if (!isRed && toRow > 4) {
            return false;
        }
        //Check quân chắn đường
        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;
        return board.getPieceAt(midRow, midCol) == null;
    }
}
