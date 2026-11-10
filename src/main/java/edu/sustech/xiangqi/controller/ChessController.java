package edu.sustech.xiangqi.controller;

import edu.sustech.xiangqi.model.Board;
import edu.sustech.xiangqi.model.Piece;
import edu.sustech.xiangqi.view.BoardPanel;

import javax.swing.SwingUtilities;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Xử lý trạng thái nhấp chuột và lựa chọn
public class ChessController extends MouseAdapter {
    private final Board board;
    private final BoardPanel boardPanel;

    // Mặc định -1 có nghĩa là không có mảnh nào được chọn.
    private int selectedRow = -1;
    private int selectedCol = -1;

    public ChessController(Board board, BoardPanel boardPanel) {
        this.board = board;
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (!SwingUtilities.isLeftMouseButton(event)) {
            return;
        }

        Point position = boardPanel.getIntersectionAt(event.getX(), event.getY());
        if (position != null) {
            handleClick(position.y, position.x);
        }
    }

    private void handleClick(int row, int col) {
        if (row == selectedRow && col == selectedCol) {
            clearSelection();
            return;
        }

        Piece clickedPiece = board.getPieceAt(row, col);
        if (selectedRow == -1) {
            if (clickedPiece != null && clickedPiece.isRed() == board.isRedTurn()) { //Thêm cái check turn của bên nào
                select(row, col);
            }
            return;
        }

        // Chuyển đổi lựa chọn khi nhấp chuột vào một quân cờ đồng minh khác.
        Piece selectedPiece = board.getPieceAt(selectedRow, selectedCol);
        if (clickedPiece != null && selectedPiece != null
                && clickedPiece.isRed() == selectedPiece.isRed()) {
            select(row, col);
            return;
        }

        // Nếu thao tác di chuyển thất bại, người dùng có thể giữ nguyên lựa chọn của mình và chọn một điểm đến khác.
        if (board.tryMove(selectedRow, selectedCol, row, col)) {
            clearSelection();
        }
    }

    private void select(int row, int col) {
        selectedRow = row;
        selectedCol = col;
        boardPanel.showSelection(row, col);
    }

    private void clearSelection() {
        selectedRow = -1;
        selectedCol = -1;
        boardPanel.showSelection(-1, -1);
    }
}
