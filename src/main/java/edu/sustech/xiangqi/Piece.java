package edu.sustech.xiangqi;

public class Piece {
    char type; //Loại quân cờ
    //G-tướng A-Sĩ E-Tượng H-Mã R-Xe C-Pháo P-Tốt
    boolean red;

    public Piece(char type, boolean red) {
        this.type = type;
        this.red = red;
    }

    public char getSymbol() {
        if (red) {
            return type;
        }
        return Character.toLowerCase(type);
        //phe đen thì để lower case
    }

    //Piece p1 = new Piece('G',true);=> p1 là tướng đỏ

}
