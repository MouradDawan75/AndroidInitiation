package fr.dawan.myapplication.entities;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartLine> lines = new ArrayList<>();

    public List<CartLine> getLines() {
        return lines;
    }

    public void setLines(List<CartLine> lines) {
        this.lines = lines;
    }


    public void addLine(CartLine line){
        int position = lines.indexOf(line);

        if(position == -1){
            //ligne inexistante
            lines.add(line);
        }else{
            //ligne existante -> modification de la quantité
            /*
            CartLine l = lines.get(position);
            int q = l.getQty();

            l.setQty(q+line.getQty());

             */
            //code optimisé
            lines.get(position).setQty(lines.get(position).getQty()+line.getQty());

        }

    }

    public void removeLine(CartLine line){
        lines.remove(line);
    }

    public int nbItems(){
        int n = 0;
        for (CartLine l : lines){
            n += l.getQty();
        }
        return n;
    }

    public double getCartTotal(){
        double t = 0;
        for (CartLine l : lines){
            t += l.getTotal();
        }

        return t;
    }
}
