import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.util.List;
import java.awt.Cursor;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Stworzenie GUI wz wykorzystaniem DrawPanel.
 * Stworzenie 4 przycisków load, save, info, manual.
 * Stworzenie przycisku wielokrotnego wyboru do wybierania opcji figur.
 * Dwie prywatne klasy odpowiedzialne za
 * obsluge przyciskow pojedynczego i wielokrotnego wyboru
 */
public class DrawFrame extends JFrame
{
    private JLabel stausLabel; //znacznik do wyswietlania koordynatow myszki
    private DrawPanel panel; //DrawPanel dla figur
    
    private JButton load; // przycisk do odczytywania z pliku
    private JButton save; // przycisk do zapisywania do pliku
    private JButton info; // przycisk do wyswietlenia info o programie
    private JButton manual; // przycisk do wyswietlenia instrukcji obslugi
    
    
    private JComboBox shapes; //przycisk wielokrotnego wyboru 
    
    //tablica opcji w przyciksku shapes
    private String[] shapeOptions= {"Triangle", "Rectangle", "Oval", "Move", "Resize"};
    
    private JPanel widgetJPanel; //zawiera przyciski
    private JPanel widgetPadder; 
    
    /**
     * Konstruktor okna programu, okresla tytuł JFrame.
     * Tworzy DrawPanel, ktory jest rozszerzeniem JPanel do tworzenia figur.
     * Inicjalizuje przyciski i je obsluguje.
     */
    public DrawFrame()
    {
        super("Paint"); //okreslenie tytulu JFrame
        
        
        JLabel statusLabel = new JLabel( "" ); //stworzenie JLabel do przekazania do DrawPanel
        
        panel = new DrawPanel(statusLabel); //Stworzenie DrawPanel
        
        //stworzenie przyciskow
        save= new JButton("Save");
		load= new JButton("Load");
		info= new JButton("Info");
		manual= new JButton("Manual");
        
        
        shapes = new JComboBox(shapeOptions);
        
        //Stworzenie menu z widgetow
        widgetJPanel = new JPanel();
        widgetJPanel.setLayout( new GridLayout(1, 6, 10, 10 ));       
        widgetPadder = new JPanel();
        widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));
            
        // dodanie przyciskow do menu
        widgetJPanel.add(save);
        widgetJPanel.add(load);
        widgetJPanel.add(info);
        widgetJPanel.add(manual);
        widgetJPanel.add(shapes);   
                      
        //dodanie widgetJPanel do widgetPadder
        widgetPadder.add(widgetJPanel);
        
        //dodanie widgetPadder i panel do JFrame
        add( widgetPadder, BorderLayout.NORTH);
        add( panel, BorderLayout.CENTER);
        
        // stworzenie new ButtonHandler do obslugi przyciskow
        ButtonHandler buttonHandler = new ButtonHandler();
        info.addActionListener(buttonHandler);
        save.addActionListener(buttonHandler);
        load.addActionListener(buttonHandler);
        manual.addActionListener(buttonHandler);
        
        //stworzenie ItemListenerHandler do obslugi opcji wielokrotnego wyboru
        ItemListenerHandler handler = new ItemListenerHandler();
        shapes.addItemListener(handler);
       
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 1000, 1000 );
        setVisible( true );
        
    } // koniec konstruktora DrawFrame
    
    /**
     * prywatna klasa do obslugi przyciskow
     */
    private class ButtonHandler implements ActionListener
    {
        public DrawFrame parent1;
        public void actionPerformed( ActionEvent event )
        {
            if (event.getActionCommand().equals("Load"))
            {
                panel.load();
            }
            else if (event.getActionCommand().equals("Save"))
            {
                panel.save();
            }
            else if (event.getActionCommand().equals("Info"))
            {
                JOptionPane.showMessageDialog(parent1, "Aplikacja Paint do rysowania i edycji figur stworzona przez Rafala Owczarskiego.");
            }
            else if (event.getActionCommand().equals("Manual"))
            {
                JOptionPane.showMessageDialog(parent1, "Wybierz odpowiednia figure aby narysowac ja na panelu\n"
				+ "Aby wczytac i zapisac plik uzyj przyciskow load i save.\n"
				+ "Opcje edycji figur znajduja sie pod opcjami move i resize.\n"
				+ "Aby przesunąć figurę wybierz ją lewym przyciskiem myszy i przeciągnij.\n"
				+ "Aby powiekszyc figure ją lewym przyciskiem myszy i uzyj scrolla.\n"
				+ "Aby zmienic kolor figury kliknij na nia prawym przyciskiem myszy i wybierz kolor.\n");
            }
             
        } //koniec metody actionPerformed
    } // konic klasy class ButtonHandler
    
    /**
     * prywatna klasa do obslugi opcji wielokrotnego wyboru
     */
    private class ItemListenerHandler implements ItemListener
    {
        public void itemStateChanged( ItemEvent event )
        {            
            // sprawdzenie czy jest zaznaczone
            if ( event.getStateChange() == ItemEvent.SELECTED )
            {
                // przekazanie odpowiedniej opcji
                if ( event.getSource() == shapes)
                {
                    panel.setcurrentOptionType(shapes.getSelectedIndex());
                }
            }
        
        } // koniec metody itemStateChanged
    }
    
} // koniec klasy DrawFrame
