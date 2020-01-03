/********************************************************************************
 
 ********************************************************************************/
package edu.fau.COT4930;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Brain extends Player
{   
    // track player moves
    private String[] array = {"1","2","3","4","5","6","7","8","9"};
    private Player player1;
    private Player player2;
    private int x_win = 0;
    private int o_win = 0;
    
    public void setMove(String player, int position)
    {
        array[position] = player;
    }
    
    // set the score for each player
    public void setScore(String x_o)
    {
        
        if("X".equals(x_o))
        {
            x_win++;
        }
        else
            o_win++;
    }
    
    public void resetScore()
    {
        x_win = 0;
        o_win = 0;
    }
    // Get the current score for each player
    public int getScore(String x_o)
    {
        if("X".equals(x_o)){return x_win;}
        else            
            return o_win;
    }
    
    public void resetMoves()
    {
        
        for(int i = 0; i < 9; i++)
        {
            array[i] = Integer.toString(i);
        }
    }
    public boolean winner()
    {
        // check for horizontal wins
        //-------------------------------------------------------|
        if(array[0] == array[1] && array[1] == array[2])
        {
            return true;
        }
        
        else if(array[3] == array[4] && array[4] == array[5])
        {
            return true;
        }
        
        else if(array[6] == array[7] && array[7] == array[8])
        {
            return true;
        }
        
        // check for vertical wins
        //-------------------------------------------------------|
        if(array[0] == array[3] && array[3] == array[6])
        {
            return true;
        }
        
        else if(array[1] == array[4] && array[4] == array[7])
        {
            return true;
        }
        
        else if(array[2] == array[5] && array[5] == array[8])
        {
            return true;
        }
        
        // check for Diagonal wins
        //-------------------------------------------------------|
        if(array[0] == array[4] && array[4] == array[8])
        {
            return true;
        }
        
        else if(array[2] == array[4] && array[4] == array[6])
        {
            return true;
        }
        
        return false;
    }
        
}