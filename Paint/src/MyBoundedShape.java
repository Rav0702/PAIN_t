import java.awt.Color;
import java.awt.Graphics;

/**
 * Abstrakcyjna klasa z metoda rysowania dziedziczy po MyShape
 * zawiera metody do rysowania elips, trojkatow i prostokatow
 */
abstract class MyBoundedShape extends MyShape
{
    
    
    /**
     * konstruktor odwolujacy sie do konstruktora w MyShape
     */
    public MyBoundedShape()
    {
        super();
    }
    
    /** 
     * Konstruktor z parametrami x1 y1 x2 i y2 i color
     * przekazuje je do konstruktora w klasie MyBoundedShape.
     */
    public MyBoundedShape(int x1, int y1, int x2, int y2, Color color)
    {
        super(x1, y1, x2, y2, color);
    }
        
    /**
     * Zwraca gorny lewy rog figury po X
     * do rysowania elips i skalowania figur
     */
    public int getUpperLeftX()
    {
        return Math.min(getX1(),getX2());
    }
    
    /**
     * Zwraca gorny lewy rog figury po Y
     * do rysowania elips i skalowania figur
     */
    public int getUpperLeftY()
    {
        return Math.min(getY1(),getY2());
    }
    
    /**
     * Zwraca dolny prawy rog figury po X
     * do skalowania figur
     */
    public int getLowerRightX()
    {
        return Math.min(getX1(),getX2());
    }
    
    /**
     * Zwraca dolny prawy rog figury po Y
     * do skalowania figur
     */
    public int getLowerRightY()
    {
        return Math.min(getY1(),getY2());
    }
    
    /**
     * zwraca szerokosc do rysowania elips
     */
    public int getWidth()
    {
        return Math.abs(getX1()-getX2());
    }
    
    //Accessor methods
    
    /**
     * zwraca wysokosc do rysowania elips
     */
    public int getHeight()
    {
        return Math.abs(getY1()-getY2());
    }
        
    /**
     * Abstrakcyjna metoda do rysowania figur
     */
    abstract public void draw( Graphics g );
} // koniec klasy MyBoundedShape
