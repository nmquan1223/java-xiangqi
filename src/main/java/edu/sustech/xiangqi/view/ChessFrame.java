package edu.sustech.xiangqi.view;

import edu.sustech.xiangqi.model.Board;

import javax.swing.*;

public final class ChessFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final BoardPanel boardPanel;

    public ChessFrame(Board board) {
        super("中国象棋 - Chinese Chess - Cờ Tướng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardPanel = new BoardPanel(board);
        boardPanel.setLayout(null);  // 关键：关闭布局管理器

        JButton button = new JButton("Click");
        button.setBounds(0, 0, 70, 20);  // x, y, width, height
        boardPanel.add(button);
        JLabel label=new JLabel("Sample");
        label.setBounds(80, 0, 70, 20);
        boardPanel.add(label);
        button.addActionListener(e->{
            label.setText("Click");
        });
        setContentPane(boardPanel);
        setSize(510,600);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
}
