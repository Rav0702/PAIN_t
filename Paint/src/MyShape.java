import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Shape;

/**
 * Abstrakcyjna klasa zawiera koordynaty i kolor okreslajace figure, mozna sie do nich odwolac i zmieniac za pomoca odpowiednich metod.
 */
abstract class MyShape
{
    private int x1,y1,x2,y2; //koordynaty ksztaltu
    private Color color; // kolor ksztaltu
    
    /**
    * Publiczny konstruktor
    * ustawia koordynaty na zero i kolor na czarny
    */
    public MyShape()
    {
        x1=0;
        y1=0;
        x2=0;
        y2=0;
        color=Color.BLACK;
     }
    
    /**
    * Konstruktor nadpisuje koordynaty i kolor w klasie
    */
    public MyShape(int x1, int y1, int x2, int y2, Color color)
    {
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.color=color;
    }
    
    //Metody do zmiany zmiennych
    
    /**
     * Metoda zmiany x1
     */
    public void setX1(int x1)
    {
        this.x1=x1;
    }   
    
    /**
     * Metoda zmiany  y1
     */
    public void setY1(int y1)
    {
        this.y1=y1;
    }   
    
    /**
     * Metoda zmiany x2
     */
    public void setX2(int x2)
    {
        this.x2=x2;
    }   
    
    /**
     * Metoda zmiany y2
     */
    public void setY2(int y2)
    {
        this.y2=y2;
    }   
    
    /**
     * Metoda zmiany color
     */
    public void setColor(Color color)
    {
        this.color=color;
    }
    
    
    //Metody odwołania sie do parametru
    
    /**
     * Metoda odwolania sie do x1
     */
    public int getX1()
    {
        return x1;
    }
    
    /**
     * Metoda odwolania sie do y1
     */
    public int getY1()
    {
        return y1;
    }
    
    /**
     * Metoda odwolania sie do x2
     */
    public int getX2()
    {
        return x2;
    }
    
    /**
     * Metoda odwolania sie do y2
     */
    public int getY2()
    {
        return y2;
    }
    
    /**
     * Metoda odwolania sie do color
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
    * Metoda sprawdzajaca czy podany punkt zawiera sie w figurze
    */    
    public boolean contains(int x, int y)
    {
    	if(((this.x1< x && x<this.x2)&&(this.y1< y && y<this.y2))||((this.x1> x && x>this.x2)&&(this.y1> y && y>this.y2)))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
          
    /**
     * Abstrakcyjna metoda rysujaca figure
     */
    abstract public void draw( Graphics g );

}
