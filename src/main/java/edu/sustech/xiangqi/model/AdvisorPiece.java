package edu.sustech.xiangqi.model;

// 红仕：每次斜走一格，不能离开红方九宫

public class AdvisorPiece extends Piece {
    public AdvisorPiece(boolean isRed) {
        super(isRed ? "仕" : "士", isRed);
    }
    public AdvisorPiece() {
        this(true);
    }
    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // 行和列必须都变化一格，不能直走、原地停留或跨越多格
        // Both the row and the column must change by exactly one square; you cannot move in
        // a straight line (without changing both coordinates), stay in the same spot, or skip over multiple squares.
        if (Math.abs(toRow - fromRow) != 1 || Math.abs(toCol - fromCol) != 1) {
            return false;
        }
        // Chỉ ở trong phần giữa của bàn cờ
        if (toCol < 3 || toCol > 5) {
            return false;
        }
        // Phán đoán đường thẳng
        // TODO: Determine the row for placing a black piece
        if (!isRed) {
            return toRow >= 0 && toRow <= 2;
        }
        return toRow >= 7 && toRow <= 9;
    }
}
