package edu.sustech.xiangqi.model;

// 红帅

public class GeneralPiece extends Piece {
    public GeneralPiece(boolean isRed) {
        super(isRed ? "帅" : "将", isRed); //Nếu isRed false thì tên con tướng đen khác
    }
    public GeneralPiece() {
        this(true); //Gọi constructor đầu tiên để tạo tướng đỏ mặc định
    }
    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // TODO：Áp dụng các quy tắc di chuyển cho quân tướng.
        if (toCol < 3 || toCol > 5) { // Chỉ được đi trong khu vực giữa bàn cờ
            return false;
        }
        if ((Math.abs(toCol-fromCol)+Math.abs(toRow-fromRow))!=1) { // Chỉ đc đi 1 ô
            return false;
        }
        if (!isRed) {
            return toRow >= 0 && toRow <= 2;
        }
        return toRow >= 7 && toRow <= 9;
    }
}
