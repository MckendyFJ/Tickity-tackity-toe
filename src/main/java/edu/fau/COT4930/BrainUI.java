//--------------------------------------------------------------------------------
// BrainUI class 
//--------------------------------------------------------------------------------

package edu.fau.COT4930;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.awt.Insets;

import java.io.*;
import java.awt.*;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.*;

import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException;
//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------

public class BrainUI extends JPanel{
    
    public static int turn = 0;     // track the number of turns
    public static String x_o = "X"; // track player's turn
    public static String stats;     // contains stats to be displayed
    // tic tac toe layout
    public static JFrame frame = new JFrame("Tickity Tackity Toe");
    public static JPanel tic_tac_toe = new JPanel(new GridLayout(3, 3));
    
    //Jframe components
    public static JTextField status = new JTextField(20);
    public static JButton col1row1 = new JButton("");
    public static JButton col2row1 = new JButton("");
    public static JButton col3row1 = new JButton("");
    public static JButton col1row2 = new JButton("");
    public static JButton col2row2 = new JButton("");
    public static JButton col3row2 = new JButton("");
    public static JButton col1row3 = new JButton("");
    public static JButton col2row3 = new JButton("");
    public static JButton col3row3 = new JButton("");
    

    //public static InputStream audio = new FileInputStream("activeSound.wav");
    // New Game instantiated
    public static Brain Game = new Brain();
    
    public BrainUI()
    {
        turn = 0;
        x_o = "X";
    }   
    
    // Reset the tic tac toe grid
    public static void reset()
    {
        turn = 0;
        x_o = "X";
        
        Game.resetMoves();
        
        stats = "x's turn - [x]:" + Game.getScore("X")+
             "\t[o]:"+ Game.getScore("O");
        
       status.setText(stats);
       col1row1.setText("");
       col2row1.setText("");
       col3row1.setText("");
       col3row1.setText("");
       col1row2.setText("");
       col2row2.setText("");
       col3row2.setText("");
       col1row3.setText("");
       col2row3.setText("");
       col3row3.setText("");
       
       tic_tac_toe.validate();
       tic_tac_toe.repaint();


       frame.validate();
       frame.repaint();

    }  
    
    // Disable grid to avoid unnecessary key strokes
    public static void disable_grid()
    {
        col1row1.setEnabled(false);
        col2row1.setEnabled(false);
        col3row1.setEnabled(false);       
        col1row2.setEnabled(false);
        col2row2.setEnabled(false);
        col3row2.setEnabled(false);
        col1row3.setEnabled(false);
        col2row3.setEnabled(false);
        col3row3.setEnabled(false);
    }
    
    // Re-enable grid for new game
    public static void enable_grid()
    {
        col1row1.setEnabled(true);
        col2row1.setEnabled(true);
        col3row1.setEnabled(true);       
        col1row2.setEnabled(true);
        col2row2.setEnabled(true);
        col3row2.setEnabled(true);
        col1row3.setEnabled(true);
        col2row3.setEnabled(true);
        col3row3.setEnabled(true);
    }
    
    public static void buttonClicked(JButton button, int position)
    {
        if(button.getText().trim().equals(""))
            {
           // set input to X or O depending on who's turn
            if(turn > 0 && x_o == "X"){x_o = "O";}
            else if(turn > 0 && x_o == "O"){x_o = "X";}
            button.setText(x_o);
            Game.setMove(x_o, position);        
            turn++;
            if(x_o == "X")
            {
                status.setText("o's turn - [x]:"
                + Game.getScore("X")+"\t[o]:"+ Game.getScore("O"));
            }
            else
            {
                status.setText("x's turn - [x]:"
                + Game.getScore("X")+"\t[o]:"+ Game.getScore("O"));
            }

            // Check for winner or tie
            if(Game.winner())
            {
                status.setText("Player "+ x_o +" won!");
                Game.setScore(x_o);
                int input = JOptionPane.showConfirmDialog(frame, 
                "Do you want to go again?", status.getText(), 
                 JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                // only exit if user confirm to do so
                if(input == 0){reset();}
                else
                {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    disable_grid();
                    status.setText("[x]:" + Game.getScore("X")+"\t[o]:"
                            + Game.getScore("O"));
                }
            }
            else if(turn >= 9 && !Game.winner())
            {
                status.setText("It's a Tie!");
                int input = JOptionPane.showConfirmDialog(frame, 
                "Do you want to go again?", status.getText(), 
                 JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                // only exit if user confirm to do so
                if(input == 0){reset();}
                else
                {
                    //System.exit(0);
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    disable_grid();
                    status.setText("[x]:" + Game.getScore("X")+"\t[o]:"
                            + Game.getScore("O"));
                }
            }
        }
    }
    
 /////////////////////////////////////////////////////////////////////////////////
 // MAIN FUNCTION
    public static void main(String[] args)
    { 
        BrainUI UI = new BrainUI();
     
        //JFrame frame = new JFrame("Tickity Tackity Toe");   // creating frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set exit property
        
        // creating menu bar + menu items
        JMenuBar menuBar = new JMenuBar();
        JMenu File = new JMenu("File");
        JMenu About = new JMenu("About");
        JMenu Help = new JMenu("Help");
        
        // creating and adding "New Game" menu items
        JMenuItem newGame = new JMenuItem("New Game");
        
        // creating and adding "Reset All" menu items
        JMenuItem resetGame = new JMenuItem("Reset All");
        
        // creating and adding "Quit Game" menu items
        JMenuItem quitGame = new JMenuItem("Quit Game");
        
        // New Game menu item implementation
        //-------------------------------------------------------------------|
        newGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent event) 
            {
                int input = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to restart? All progress will be lost"
                        +"\nExcept for current score!",
                "Restart Game", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                
                // only exit if user confirm to do so
                if(input == 0){reset(); enable_grid();}
                else
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }    
        });
        
        // About  menu item implementation
        //-------------------------------------------------------------------|
        About.addMenuListener(new MenuListener()
        {
            @Override
            public void menuSelected (MenuEvent event) 
            {
                int input = JOptionPane.showConfirmDialog(frame, 
                "Tickity Tackity Toe\nVersion 1.0\nFrancois Joseph\nCopyright 2019"
                        + "\nAll Rights Reserved",
                "Help Menu", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } 
            
            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        
        // Help menu item implementation
        //-------------------------------------------------------------------|
        Help.addMenuListener(new MenuListener()
        {
            @Override
            public void menuSelected (MenuEvent event) 
            {
                int input = JOptionPane.showConfirmDialog(frame, 
                "Click on any tiles to start playing\n"
                        + "Restart, Reset or Quit at anytime from the File menu",
                "Help Menu", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } 
            
            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        
        
        // Reset Game menu item implementation
        //-------------------------------------------------------------------|
        resetGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent event) 
            {
                int input = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to reset? All progress \nand current scores"
                        + " will be lost", "Reset Game", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                
                // only exit if user confirm to do so
                if(input == 0)
                {
                    reset(); 
                    Game.resetScore();
                    status.setText("x's turn - [x]:" + Game.getScore("X")+
                            "\t[o]:"+ Game.getScore("O"));
                    enable_grid();
                }
                else
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }    
        });
        
        
        // Quit Game menu item implementation
        //-------------------------------------------------------------------|
        quitGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent event) 
            {
                int input = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to quit?", "Customized Dialog", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                
                // only exit if user confirm to do so
                if(input == 0){System.exit(0);}
                else
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }    
        });
        
        
      // add exit confirmation message upon exiting the program
      //-------------------------------------------------------------------|  
      frame.addWindowListener(new WindowAdapter()
      {
        @Override
          public void windowClosing(WindowEvent e) 
          {          
            int input = JOptionPane.showConfirmDialog(frame, 
            "Are you sure you want to quit?", "Quit Game", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            // only exit if user confirm to do so
            if(input == 0){System.exit(0);}
            else
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
          }
      });
        
        // setting and adding menu bar and its items
        //-------------------------------------------------------------------|  
        frame.setJMenuBar(menuBar);
        menuBar.add(File);
        menuBar.add(About);
        menuBar.add(Help);
       
        //File.add(undo);
        File.add(newGame);
        File.add(resetGame);
        File.add(quitGame);
        
        //JTextField status = new JTextField(20);
        status.setFont(new Font("Helvetica", Font.PLAIN, 20));
        status.setBackground(Color.gray);
        status.setForeground(Color.white);
        status.setText("x's turn");      // text field initial text
        status.setEditable(false);
        
       
       //tic tac toe grid
        //-------------------------------------------------------------------| 
        //Col1Row1-----------------------------------------------------------|  
            //JButton col1row1 = new JButton("");
            col1row1.setPreferredSize(new Dimension(100, 100));
            col1row1.setFont(new Font("Helvetica", Font.PLAIN, 50));            
            col1row1.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {                        
                        buttonClicked(col1row1, 0);                      
                    }
            });
        //-------------------------------------------------------------------|     
        //Col2Row1-----------------------------------------------------------|     
            //JButton col2row1 = new JButton("");
            col2row1.setFont(new Font("Helvetica", Font.PLAIN, 50));
            col2row1.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {                        
                        buttonClicked(col2row1, 1);                    
                    }
                
            });
            
        //-------------------------------------------------------------------|         
        //Col3Row1-----------------------------------------------------------|      
            //JButton col3row1 = new JButton("");
            col3row1.setFont(new Font("Helvetica", Font.PLAIN, 50));
            col3row1.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        buttonClicked(col3row1, 2);                        
                    }
            });
            
            
        //-------------------------------------------------------------------| 
        //Col1Row2-----------------------------------------------------------|  
            //JButton col1row2 = new JButton("");
            col1row2.setFont(new Font("Helvetica", Font.PLAIN, 50));
            col1row2.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        buttonClicked(col1row2, 3);
                    }
            });
            
        //-------------------------------------------------------------------| 
        //Col2Row2-----------------------------------------------------------|  
           // JButton col2row2 = new JButton("");
            col2row2.setFont(new Font("Helvetica", Font.PLAIN, 50));
            col2row2.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        buttonClicked(col2row2, 4);
                    }
            });
            
        //-------------------------------------------------------------------|     
        //Col3Row2-----------------------------------------------------------|      
            //JButton col3row2 = new JButton("");
            col3row2.setFont(new Font("Helvetica", Font.PLAIN, 50));
            col3row2.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        buttonClicked(col3row2, 5);
                    }
            });
            
        //-------------------------------------------------------------------|     
        //Col1Row3-----------------------------------------------------------|       
            //JButton col1row3 = new JButton("");
            col1row3.setFont(new Font("Helvetica", Font.PLAIN, 50));
            col1row3.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        buttonClicked(col1row3, 6);
                    }
            });
            
        //-------------------------------------------------------------------|     
        //Col2Row3-----------------------------------------------------------|  
            //JButton col2row3 = new JButton("");
            col2row3.setFont(new Font("Helvetica", Font.PLAIN, 50));
            col2row3.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        buttonClicked(col2row3, 7);
                    }
            });
            
        //-------------------------------------------------------------------|     
        //Col3Row3-----------------------------------------------------------|  
            //JButton col3row3 = new JButton("");
            col3row3.setFont(new Font("Helvetica", Font.PLAIN, 50));
            col3row3.addActionListener(new ActionListener()
            {
                @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        buttonClicked(col3row3, 8);
                    }
            });
            
            
            // setting up final frame
            frame.setLayout(new FlowLayout());       
            frame.add(status);
            status.setHorizontalAlignment(JTextField.CENTER);
            frame.add(Box.createVerticalStrut(100));
            
            // Add tic tac toe buttons to jpanel grid
            tic_tac_toe.add(col1row1);
            tic_tac_toe.add(col2row1);
            tic_tac_toe.add(col3row1);

            tic_tac_toe.add(col1row2);
            tic_tac_toe.add(col2row2);
            tic_tac_toe.add(col3row2);

            tic_tac_toe.add(col1row3);
            tic_tac_toe.add(col2row3);
            tic_tac_toe.add(col3row3);
            
            // add jpanel to frame
            frame.add(tic_tac_toe);
       
       frame.setMinimumSize(new Dimension(450,500));
       frame.setMaximumSize(new Dimension(450,500));
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);

    }
    
}
