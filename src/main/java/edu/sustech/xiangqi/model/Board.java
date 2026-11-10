package edu.sustech.xiangqi.model;

// Lưu trữ dữ liệu bàn cờ và kiểm tra, thực hiện các nước đi một cách nhất quán.

public class Board {
    // Xác định kích thước của bàn cờ
    public static final int ROWS = 10;
    public static final int COLS = 9;

    // Lưu trữ vị trí quân cờ
    private final Piece[][] pieces = new Piece[ROWS][COLS];

    public Board() {
        initializePieces();
    }

    private void initializePieces() { // Tạo quân cờ đỏ
        // Red pieces
        pieces[9][3] = new AdvisorPiece(true);
        pieces[9][4] = new GeneralPiece(true);
        pieces[9][5] = new AdvisorPiece(true);

        // Black pieces
        pieces[0][3] = new AdvisorPiece(false);
        pieces[0][4] = new GeneralPiece(false);
        pieces[0][5] = new AdvisorPiece(false);
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public Piece getPieceAt(int row, int col) { //Getter lấy position của quân cờ
        if (!isInside(row, col)) {
            return null;
        }
        return pieces[row][col];
    }

    public boolean tryMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isInside(fromRow, fromCol) || !isInside(toRow, toCol)) {
            return false;
        }
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }

        Piece piece = getPieceAt(fromRow, fromCol);
        if (piece == null) {
            return false;
        }

        // TODO: Add a turn-based check to ensure that only the current player can move their own pieces.

        // TODO: Phân biệt quân cờ của mình và quân cờ của đối phương, rồi bắt chúng.
        if (getPieceAt(toRow, toCol) != null) {
            return false;
        }

        if (!piece.canMove(this, fromRow, fromCol, toRow, toCol)) {
            return false;
        }

        // TODO: Sau khi kiểm tra xem các tướng lĩnh và chỉ huy của chúng ta có an toàn hay không.
        pieces[fromRow][fromCol] = null;
        pieces[toRow][toCol] = piece;

        // TODO: Chỉ đổi lượt sau khi nước đi thành công và quyết định trạng thái như chiếu hết, thắng hoặc thua.
        return true;
    }
}
