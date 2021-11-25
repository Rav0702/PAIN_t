import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JColorChooser;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Klasa odpowiedzialna za obslugiwanie myszki do rysowania i edycji ksztaltow.
 * Do przechowywania wszystkich narysowanych ksztaltow uzywa listy myShapes.
 * Do zapisywania i odczytu pliku myShapes zamieniane jest na liste Stringow
 * w metodach save() i load();
 * Obecnie zaznaczony ksztalt przechowywany jest w zmiennej currentShapeObject.
 * Zawiera pomocnicze koordynaty myszy
 */

public class DrawPanel extends JPanel
{
	public DrawFrame parent; //Rysowanie DrawFrame JFrame na ktorej mozna obslugiwac okna dialogowe
	
	private int X1, X2, Y1, Y2, X3, Y3, SX1, SX2, SY1, SY2, position; //zmienne pomocnicze
    private List<MyShape> myShapes; //lista figow narysowanych
    
    JColorChooser colorChooser = new JColorChooser(); //odpowiedzialne za zmiane koloru wybranego ksztaltu
    
    //Zmienne aktualnego ksztaltu i opcji edycji
    private int currentOptionType; //0 dla trojkata, 1 dla prostokata, 2 dla elipsy, 3 dla przemieszczenia, 4 dla zmiany rozmiaru
    private MyShape currentShapeObject; //obecna figura
    private Color currentShapeColor; //obecny kolor figur
    
    
    private boolean move= false, resize= false;// zmienne pomocnicze okreslajace zaznaczenie opcji edycji
    
    JLabel statusLabel; //znacznik obecnej pozycji myszki
    
    /**
     * Konstruktor inicjalizuje liste wszystkich ksztaltow.
     * Reset do domyslnych parametrow obecnej figury.
     * obsluga myszki
     */
    public DrawPanel(JLabel statusLabel)
   	{
      	//inicjalizacja obecnych figur
        currentOptionType=0;
        currentShapeObject=null;
        currentShapeColor=Color.BLACK;
        
        myShapes= new ArrayList<MyShape>();        
        
        this.statusLabel = statusLabel; //inicjaliacja znacznika myszki
        
        setLayout(new BorderLayout()); //ustawienie ukladu okna na BorderLayout()
        setBackground( Color.WHITE ); //ustawienie koloru tla na bialy
        add( statusLabel, BorderLayout.SOUTH );  //dodanie znacznika myszki na dole panelu
        
        // obsluga myszki
        MouseHandler handler = new MouseHandler();                                    
        addMouseListener( handler );
        addMouseMotionListener( handler );         
        Wheel w = new Wheel();
        addMouseWheelListener(w);
    }
    
    /**
    * Metoda odczytywania z pliku
    */
    public void load()
	{
		File file = getFile();
		if(file==null || !file.exists())
		{
			return;
		}		
		ArrayList<String[]> data= new ArrayList<String[]>(); //stworzenie listy stingow do odczytania
		try
		{
			Scanner myReader = new Scanner(file);  	//myReader odczytuje dane z pliku
			while (myReader.hasNextLine())	//Odczytywanie kolejnych stringow z pliku i zapisanie do listu data
			{
		        String line = myReader.nextLine();
		        String[] ls = line.split(",");
		        data.add(ls);
		      }
		myReader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		if(data!=null)	
		{
			myShapes.clear();	//wyczyszczenie obecnych ksztaltow
			for(String[] obj : data)	//zamiana ze stringow na figury zgodnie z kodem figur
			{
				currentShapeObject=null;
				int xx1, yy1, xx2, yy2;
				int c;
				xx1=Integer.parseInt(obj[1]);
				yy1=Integer.parseInt(obj[2]);
				xx2=Integer.parseInt(obj[3]);
				yy2=Integer.parseInt(obj[4]);
				c=Integer.parseInt(obj[5]);
				setCurrentShapeColor(new Color(c));
				if(obj[0].equals("R"))
				{
					currentShapeObject= new MyRectangle(xx1, yy1, xx2, yy2, currentShapeColor);
				}					
				else if(obj[0].equals("O"))
				{
					currentShapeObject= new MyOval(xx1, yy1, xx2, yy2, currentShapeColor);
				}					
				else if(obj[0].equals("T"))
				{
					currentShapeObject= new MyTriangle(xx1, yy1, xx2, yy2, currentShapeColor);
				}					
				if(currentShapeObject!=null)	//dodawanie wlasciwych figur do listy myShapes
				{					
					myShapes.add(currentShapeObject);
					repaint();
				}
				
			}
		}
		
	}
	/**
	 * Metoda zapisywania do pliku
	 */
	public void save()
	{
		File file = getFile();
		if(file==null)
		{
			return;
		}
		if (!file.getAbsolutePath().endsWith(".db"))
		{
			file = new File(file.getAbsolutePath()+".db");
		}   
		    
		FileWriter w;
		try 
		{
			w = new FileWriter(file);
			for (int counter=1; counter<=myShapes.size()-1; counter++ )
			{
				int counter1= counter-1;
				if(myShapes.get(counter).toString()== myShapes.get(counter1).toString())
				{
					myShapes.remove(counter1);
				}
			}
			for (int counter=0; counter<=myShapes.size()-1; counter++ )	//zapis kazdego parametru figur z myShapes do pliku
			{
				w.write(myShapes.get(counter)+"\n");
			}
			w.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	
	}
	private File getFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select file");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("db files *.db", "db");
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection != JFileChooser.APPROVE_OPTION) 
		{
			return null;
		}
	
	return fileChooser.getSelectedFile();
	}
    
    /**
     * Wywolanie metody deaw dla istniejacych ksztaltow
     */
    public void paintComponent(Graphics g)
	{
    	super.paintComponent(g);  
    	List<MyShape> shapeArray= myShapes;   
    	for (int counter=0; counter<=shapeArray.size()-1; counter++ )
    	{
    		shapeArray.get(counter).draw(g);
    	}
    	if (currentShapeObject!=null)
            currentShapeObject.draw(g);       
	}
        
    /**
     * Ustawienie currentOptionType na odpowiedni typ 
     *(0 dla trojakta, 1 dla prostokata, 2 dla elipsy, 3 dla przesuniecia, 4 dla zmiany rozmiaru)
     */
    public void setcurrentOptionType(int type)
    {
        currentOptionType=type;
    }
    
    /**
     * Ustawienie currentShapeColor na kolor podany w argumencie
    */
    public void setCurrentShapeColor(Color color)
    {
        currentShapeColor=color;
    }
    
    /**
     * prywatna klasa odpowiedzialne za obsluge myszy, rozszerza MouseAdapter
     */
    
    private class MouseHandler extends MouseAdapter 
    {
        /**
         * Prawy przycisk myszy odpowiedzialny za rysowanie figur i zaznacznie figur
         * koordynaty X1,Y1, X2,Y2 sa ustawione na koordynaty X iY myszy
         */
        public void mousePressed(MouseEvent event)
        {
        	if(event.getButton()==MouseEvent.BUTTON1)
        	{
        	    switch (currentOptionType)
        	    {
        	        case 0:
        	        	move= false;
        	        	resize=false;
        	            currentShapeObject= new MyTriangle(event.getX(), event.getY(), event.getX(), event.getY(), currentShapeColor);
        	            break;
        	        case 1:
        	        	move= false;
        	        	resize=false;
        	            currentShapeObject= new MyRectangle(event.getX(), event.getY(), event.getX(), event.getY(), currentShapeColor); 
        	            break;
        	        case 2:
        	        	move= false;
        	        	resize=false;
        	            currentShapeObject= new MyOval(event.getX(), event.getY(), event.getX(), event.getY(), currentShapeColor);
        	            break;                             
        	                                           
        	        case 3:
        	        	move=true;
        	        	resize=false;
        	        	for (int counter=0; counter<=myShapes.size()-1; counter++ )	//aktualizacja currentShapeObject jezeli zostala kliknieta
    					{
    						if(myShapes.get(counter).contains(event.getX(), event.getY()))
    						{
    							//przeniesienie odpowiednich parametrow do zmiennych pomocniczych
    							currentShapeObject= myShapes.get(counter);
    							X1= event.getX();
    							Y1= event.getY();
    							X2= currentShapeObject.getX1();
    							Y2= currentShapeObject.getY1();
    							X3= currentShapeObject.getX2();
    							Y3= currentShapeObject.getY2();
    							position= counter;
    							break;
    						}
    					}
        	        	break;
        	        case 4:
        	        	resize=true;
        	        	move=false;
        	        	for (int counter=0; counter<=myShapes.size()-1; counter++ )	//aktualizacja currentShapeObject jezeli zostala kliknieta
    					{
    						if(myShapes.get(counter).contains(event.getX(), event.getY()))
    						{
    							currentShapeObject= myShapes.get(counter);
    							position= counter;
    							break;
    						}
    					}
    					                   
        	    }
        	}
        	else if(event.getButton()==MouseEvent.BUTTON3)
        	{	
        		for (int counter=0; counter<=myShapes.size()-1; counter++ )
    			{
    				if(myShapes.get(counter).contains(event.getX(), event.getY()))
    				{
    					Color CurrentShapeColor = JColorChooser.showDialog(parent, "Pick a Color", Color.GREEN);
    					myShapes.get(counter).setColor(CurrentShapeColor);
    					repaint();
    					break;
    				}
    			}        		
        	}        	
        } // koniec metody mousePressed
        
        /**
         * Kiedy przycisk zostaje puszczony a opcja move nie zostala wybrana
         * parametry x2 i y2 currentShapeObject zostaja ustawione na pozycje myszki,
         * nastepnie narysowania figura jest dodawana do myShapes.
         * Jesli wybrana jest opcja move koordynaty currentShapeObject zostaja przesuniete
         * o wektor przesuniecia myszki
         */
        public void mouseReleased( MouseEvent event )
        {
            if(!move && !resize)
            {
            	currentShapeObject.setX2(event.getX());
            	currentShapeObject.setY2(event.getY());
            
           		myShapes.add(currentShapeObject); 
            	repaint();
            	currentShapeObject=null; 
            }
            else if(move)
            {
            	
            	currentShapeObject.setX1(X2+ event.getX()- X1);
            	currentShapeObject.setY1(Y2+ event.getY()- Y1);
            	currentShapeObject.setX2(X3+ event.getX()- X1);
            	currentShapeObject.setY2(Y3+ event.getY()- Y1);
            	
            	myShapes.add(currentShapeObject);
            
           		repaint();
            	currentShapeObject=null; 
            	
            }           
        } // koniec metody mouseReleased
        
        /**
         * Metoda ustawiania znacznika pomocniczych koordynatow myszki podczas przesuniecia
         */
        public void mouseMoved( MouseEvent event )
        {
            statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d",event.getX(),event.getY()));
        } // koniec metody mouseMoved
        
        /**
         * Metoda obsugi rysowania podczas przeciagania myszki
         * parametry x2 i y2 currentShapeObject zostaja ustawione na pozycje myszki,
         * nastepnie narysowania figura jest dodawana do myShapes.
         * Jesli wybrana jest opcja move koordynaty currentShapeObject zostaja przesuniete
         * o wektor przesuniecia myszki
         */
        public void mouseDragged( MouseEvent event )
        {
            if(!move && !resize)
            {
            	
            	currentShapeObject.setX2(event.getX());
            	currentShapeObject.setY2(event.getY());
            }
            else if(move)
            {
            	currentShapeObject.setX1(X2+ event.getX()- X1);
            	currentShapeObject.setY1(Y2+ event.getY()- Y1);
            	currentShapeObject.setX2(X3+ event.getX()- X1);
            	currentShapeObject.setY2(Y3+ event.getY()- Y1);
            	
            	//myShapes.get(position)=null;
            	myShapes.add(currentShapeObject);
            
           		//redo currentShapeObject in myShapes
            	repaint();           	
            }            
            //ustawienie pomocniczego znacznika koordynatow myszki
            statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d",event.getX(),event.getY()));
            
            repaint();
            
        } //koniec metody mouseDragged       
    }// koniec MouseHandler
    
    
    /**
     * Obsuga scrolla, klasa Wheel implementuje MouseWheelListener
     * jezeli zaznaczona jest opcja resize przesuniecie koordynatow
     * x1, x2, y1 i y2 o odpowiedni wektor, aby powiekszyc figure
     */
    private class Wheel implements MouseWheelListener 
    {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) 
        {
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) 
            {
            	
            	if(currentShapeObject!=null && resize)
            	{
            		int amount =(int) (e.getWheelRotation() * 3f);
                    SX1= Math.max(currentShapeObject.getX1(), currentShapeObject.getX2());
    				SY1= Math.max(currentShapeObject.getY1(), currentShapeObject.getY2());
    				SX2= Math.min(currentShapeObject.getX1(), currentShapeObject.getX2());
    				SY2= Math.min(currentShapeObject.getY1(), currentShapeObject.getY2());
    				
    				SX1+= amount;
    				SY1+= amount;
    				SX2-= amount;
    				SY2-= amount;
    				
    				currentShapeObject.setX1(SX1);            		
            		currentShapeObject.setX2(SX2);
            		currentShapeObject.setY1(SY1);
            		currentShapeObject.setY2(SY2);
    				
    				myShapes.add(currentShapeObject);
    				
                    repaint();
            	}
            }            
        }
        
    }
    
} // end class DrawPanel
