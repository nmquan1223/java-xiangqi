package edu.sustech.xiangqi.model;

// Lưu trữ dữ liệu bàn cờ và kiểm tra, thực hiện các nước đi một cách nhất quán.

public class Board {
    // Xác định kích thước của bàn cờ
    public static final int ROWS = 10;
    public static final int COLS = 9;

    private boolean redTurn = true; //Check turn của bên nào
    public boolean isRedTurn() { //Getter để dùng trong chess controller
        return redTurn;
    }

    // Lưu trữ vị trí quân cờ
    private final Piece[][] pieces = new Piece[ROWS][COLS];

    public Board() {
        initializePieces();
    }

    private void initializePieces() {
        // khởi tạo mấy quân của đen ở row 0
        pieces[0][0] = new RookPiece(false);
        pieces[0][1] = new HorsePiece(false);
        pieces[0][2] = new ElephantPiece(false);
        pieces[0][3] = new AdvisorPiece(false);
        pieces[0][4] = new GeneralPiece(false);
        pieces[0][5] = new AdvisorPiece(false);
        pieces[0][6] = new ElephantPiece(false);
        pieces[0][7] = new HorsePiece(false);
        pieces[0][8] = new RookPiece(false);
        // row 9 quân đỏ
        pieces[9][0] = new RookPiece(true);
        pieces[9][1] = new HorsePiece(true);
        pieces[9][2] = new ElephantPiece(true);
        pieces[9][3] = new AdvisorPiece(true);
        pieces[9][4] = new GeneralPiece(true);
        pieces[9][5] = new AdvisorPiece(true);
        pieces[9][6] = new ElephantPiece(true);
        pieces[9][7] = new HorsePiece(true);
        pieces[9][8] = new RookPiece(true);
        // Quân pháo
        pieces[2][1] = new CannonPiece(false);
        pieces[2][7] = new CannonPiece(false);
        pieces[7][1] = new CannonPiece(true);
        pieces[7][7] = new CannonPiece(true);
        // Quân tốt
        for (int col = 0; col < COLS; col += 2) {
            pieces[3][col] = new SoldierPiece(false);
            pieces[6][col] = new SoldierPiece(true);
        }
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
        // Done
        if (piece.isRed() != redTurn) {
            return false;
        }

        // TODO: Phân biệt quân cờ của mình và quân cờ của đối phương, rồi bắt chúng.
        // Done
        Piece target = getPieceAt(toRow, toCol);
        if (target != null && target.isRed() == piece.isRed()){ //Nếu cùng phe thì return false
            return false;
        }

        if (!piece.canMove(this, fromRow, fromCol, toRow, toCol)) {
            return false;
        }

        // TODO: Sau khi kiểm tra xem các tướng lĩnh và chỉ huy của chúng ta có an toàn hay không.
        pieces[fromRow][fromCol] = null;
        pieces[toRow][toCol] = piece;

        // TODO: Chỉ đổi lượt sau khi nước đi thành công và quyết định trạng thái như chiếu hết, thắng hoặc thua.

        redTurn = !redTurn; // Đổi turn sau 1 move
        return true;
    }
}
