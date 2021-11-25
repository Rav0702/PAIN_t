import java.awt.Color;
import java.awt.Graphics;

/**
 * Klasa odpowiedzalna za rysowanie prostokatow, dziedziczy po MyBoundedShape
 */
public class MyRectangle extends MyBoundedShape
{ 
    /**
     * Konstruktor odwolujacy sie do konstruktora w MyBoundedShape
     */
    public MyRectangle()
    {
        super();
    }
    
    /** 
     * Konstruktor z parametrami x1 y1 x2 i y2 i color
     * przekazuje je do konstruktora w klasie MyBoundedShape.
     */
    public MyRectangle( int x1, int y1, int x2, int y2, Color color)
    {
        super(x1, y1, x2, y2, color);
    } 
    
    /**
     * Nadpisanie metody rysowania w MyBoundedShape 
     * ustawia kolor na ten pobrany z MyBoundedShape
     * i koordynaty na te pobrane z MyBoundedShape
     */
    @Override
    public void draw( Graphics g )
    {
        g.setColor( getColor() ); //ustawienie koloru
        g.fillRect( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() ); //rysowanie prostokatu
    }
    
    /**
    * Nadpisanie metody toSting() 
    * zwracanie parametrow figury potrzebnych do wczytania z pliku
    */
    @Override
    public String toString()
    {
    	return "R,"+getX1()+","+getY1()+","+getX2()+","+getY2()+","+getColor().getRGB();
    }    
}
