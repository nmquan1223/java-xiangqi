package edu.sustech.xiangqi.model;

// 棋子的抽象父类。棋子保存身份信息，所在位置由 Board 的数组下标表示
// An abstract base class for chess pieces.
// Each piece stores its identity information,
// while its position is represented by an array index within the `Board`.

public abstract class Piece { // Chỉ có name và isRed
    //abstract class = 1 class trừu tượng, nói rằng mọi quân cờ đều có tên và phe đỏ/đen
    private final String name;
    protected final boolean isRed;

    protected Piece(String name, boolean isRed) {
        this.name = name;
        this.isRed = isRed;
    }

    public String getName() {
        return name;
    } // method để lấy tên

    public boolean isRed() {
        return isRed;
    } // method để xem là phe nào

    /**
     * 判断本棋子的局部走法是否合法
     * @return 是否可以移动
     */
    public abstract boolean canMove(Board board, int fromRow, int fromCol, int toRow, int toCol);
}
