package edu.sustech.xiangqi.model;

// Lưu trữ dữ liệu bàn cờ và kiểm tra, thực hiện các nước đi một cách nhất quán.

public class Board {
    // Xác định kích thước của bàn cờ
    public static final int ROWS = 10;
    public static final int COLS = 9;

    private boolean redTurn = true; //Check turn của bên nào

    private Boolean winnerIsRed = null; //Check xem đã hết ván chưa
    //QUAN TRỌNG: dùng Boolean thay vì boolean vì Boolean có thể chứa "null"
    public Boolean getWinnerIsRed() { //Getter
        return winnerIsRed;
    }

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
        if (winnerIsRed != null) { //Check xem đã hết cờ chưa
            return false;
        }

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

        // Chống ăn thẳng con tướng
        if (target instanceof GeneralPiece) {
            return false;
        }

        if (!piece.canMove(this, fromRow, fromCol, toRow, toCol)) {
            return false;
        }

        pieces[fromRow][fromCol] = null;
        pieces[toRow][toCol] = piece;

        // TODO: Chỉ đổi lượt sau khi nước đi thành công và quyết định trạng thái như chiếu hết, thắng hoặc thua.
        //before: if (isInCheck(piece.isRed()))
        if (isInCheck(piece.isRed())) {
            pieces[fromRow][fromCol] = piece;
            pieces[toRow][toCol] = target;
            return false;
        }
        redTurn = !redTurn; //Đổi turn sau 1 move

        if (!hasAnyLegalMove(redTurn)) { //Check xem còn move ko, nếu ko thì winnerIsRed là bên thắng
            winnerIsRed = piece.isRed();
            //VD Đỏ vừa đi nước chiếu hết
            //-> redTurn = false vì đến bên đen
            //ko có legalmove -> if hoạt động -> winnerIsRed = phe thắng
        }
        return true;
    }

    //Implement facing generals - check xem có vướng mặt tướng không
    private boolean areGeneralsFacing() {
        int redRow = -1, redCol = -1;
        int blackRow = -1, blackCol = -1;
        // Tìm 2 con tướng
        for (int row = 0; row < ROWS; row++) {
            for (int col = 3; col <= 5; col++) {
                Piece piece = pieces[row][col];
                if (piece instanceof GeneralPiece) { //Tìm tọa độ của quân tướng = instanceof
                    if (piece.isRed()) {
                        redRow = row;
                        redCol = col;
                    } else {
                        blackRow = row;
                        blackCol = col;
                    }
                }
            }
        }
        // Check xem cả 2 con tướng có chung 1 cột ko
        if (redRow == -1 || blackRow == -1 || redCol != blackCol) {
            return false;
        }
        // Check xem có quân giữa 2 quân tướng không
        for (int row = blackRow + 1; row < redRow; row++) {
            if (pieces[row][redCol] != null) {
                return false;
            }
        }
        return true;
    }

    // TODO: Sau khi kiểm tra xem các tướng của chúng ta có an toàn hay không.
    // Done
    public boolean isInCheck(boolean red) {
        int generalRow = -1;
        int generalCol = -1;

        //Find general - Tìm con tướng
        for (int i=0; i<ROWS; i++) {
            for (int j=0; j<COLS; j++) {
                Piece p = pieces[i][j];

                if (p instanceof GeneralPiece && p.isRed() == red) {
                    generalRow = i;
                    generalCol = j;
                }
            }
        }

        if (generalRow == -1) return true;

        //Kiểm tra xem có quân nào của địch ăn được tướng ko
        for (int i=0; i<ROWS; i++) {
            for (int j=0; j<COLS; j++) {
                Piece enemy = pieces[i][j];

                if (enemy != null && enemy.isRed() != red) {
                    if (enemy.canMove(this, i, j, generalRow, generalCol)) {
                        return true;
                    }
                }
            }
        }

        //Kiểm tra facing generals
        return areGeneralsFacing();
    }

    //Check xem có bị checkmate không
    public boolean hasAnyLegalMove(boolean red) {
        //Tìm quân của phe "red" này
        for (int fromRow=0; fromRow<ROWS; fromRow++) {
            for (int fromCol=0; fromCol<COLS; fromCol++) {
                Piece piece = pieces[fromRow][fromCol];
                if (piece ==null || piece.isRed() !=red) {
                    continue;
                }
                //Thử tất cả các nước
                for (int toRow=0; toRow<ROWS; toRow++) {
                    for (int toCol=0; toCol<COLS; toCol++) {
                        Piece target = pieces[toRow][toCol];
                        if (target != null && target.isRed() == red) { //Nếu ko có quân thuộc phe cần kiểm tra
                            continue;
                        }
                        if (target instanceof GeneralPiece) { //Target là tướng thì skip
                            continue;
                        }
                        if (!piece.canMove(this, fromRow, fromCol, toRow, toCol)) { //ko thể canmove thì skip
                            continue;
                        }
                        // Giả lập đi thử
                        pieces[fromRow][fromCol] = null;
                        pieces[toRow][toCol] = piece;
                        boolean safe = !isInCheck(red); //Nếu tướng đang bị chiếu mà move thì safe = false
                        // Rollback
                        pieces[fromRow][fromCol] = piece;
                        pieces[toRow][toCol] = target;
                        if (safe) { //Nếu safe thì đi được
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
