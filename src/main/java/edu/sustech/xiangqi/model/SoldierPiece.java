package edu.sustech.xiangqi.model;

public class SoldierPiece extends Piece {
    public SoldierPiece(boolean isRed) {
        super(isRed ? "兵" : "卒", isRed);
    }
    @Override
    public boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int direction = isRed ? -1 : 1; //Kiểm tra hướng tiến (nếu đỏ thì đi lên, nếu đen thì đi xuống)
        //Kiểm tra nước đi thẳng
        if (toRow == fromRow + direction && toCol == fromCol) {
            return true;
        }
        //Check xem đã qua sông chưa
        boolean crossedRiver = isRed ? fromRow <= 4 : fromRow >= 5;
        //Nếu qua sống rồi thì đc đi ngang nữa nên check cả col cả col
        if (crossedRiver && toRow == fromRow && Math.abs(toCol - fromCol) == 1) {
            return true;
        }
        return false;
    }
}
