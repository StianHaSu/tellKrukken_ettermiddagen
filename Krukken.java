import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobKOctets;

import java.awt.image.BufferedImage;
import javax.security.auth.callback.TextInputCallback;
import javax.swing.*;

import java.io.File;

import java.util.Scanner;

public class Krukken{
    JFrame vindu;
    JLabel status;
    JLabel overskrift;
    JPanel en;
    GhostText brukernavn, epost, svar;
    JButton avgi, resett;
    Logikk l;

    final static Font TEXT_FONT = new Font(Font.MONOSPACED, Font.BOLD, 12);

    JLabel pic2 = null;
    BufferedImage img2 = null;
    BufferedImage img3 = null;
    
    public Krukken(Logikk l){
        this.l = l;
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e){
            System.out.println(e);
        }

        UIManager.put("Button.focusInputMap", new UIDefaults.LazyInputMap(new
            Object[] {
            "ENTER", "pressed",
            "released ENTER", "released"
        }));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //Ramme og panel
        vindu = new JFrame("Tell pins i krukken");
        vindu.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        vindu.setLocationRelativeTo(null);

        en = new JPanel();
        en.setLayout(null);

        //Input-tekst
        brukernavn = new GhostText("Fullt navn", 15);
        epost = new GhostText("E-post", 25);
        svar = new GhostText("Svar", 8);

        //Knapper
        avgi = new JButton("AVGI SVAR");
        resett = new JButton();

        status = new JLabel("");

        overskrift = new JLabel("Hvor mange pins er det i krukken?");
        overskrift.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        
        try{
            URL imgURL = new File( "krukke6.png" ).toURI().toURL();
            img2 = ImageIO.read(imgURL);} catch (Exception exce){System.out.println(exce);}
        pic2 = new JLabel(new ImageIcon(img2));

        try{
            URL imgURL = new File( "at-teal.svg" ).toURI().toURL();
            img3 = ImageIO.read(imgURL);
            vindu.setIconImage(img3);
        } catch (Exception exce){System.out.println(exce);}

        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Insets insets = en.getInsets();

        brukernavn.setBounds(520+insets.left, 420+insets.top, 150, 35);
        epost.setBounds(675+insets.left, 420+insets.top, 150, 35);
        svar.setBounds(830+insets.left, 420+insets.top, 150, 35);

        avgi.setBounds(520+insets.left, 460+insets.top, 460, 30);

        overskrift.setBounds(385+insets.left, 350+insets.top, 800, 50);
        status.setBounds(660+insets.left, 475+insets.top, 300, 50);
    

        pic2.setBounds(0,0, screenSize.width, screenSize.height);

        Color color = new Color(155, 214, 195);
        Color btnC = new Color(0, 128, 128);
        Color white = new Color(255, 255, 255);
        Color sand = new Color(255,244,226);

        en.setBackground( color );
        avgi.setBackground(btnC);
        avgi.setForeground(white);
        avgi.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        avgi.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        
        brukernavn.setBackground(sand);
        brukernavn.setForeground(Color.BLACK);
        brukernavn.setFont(TEXT_FONT);
        brukernavn.setHorizontalAlignment(JTextField.CENTER);
        brukernavn.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        epost.setForeground(Color.BLACK);
        epost.setBackground(sand);
        epost.setFont(TEXT_FONT);
        epost.setHorizontalAlignment(JTextField.CENTER);
        epost.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        svar.setBackground(sand);
        svar.setForeground(Color.BLACK);
        svar.setFont(TEXT_FONT);
        svar.setHorizontalAlignment(JTextField.CENTER);
        svar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        overskrift.setForeground(white);

            class avgiSvar implements ActionListener{
                Krukken gui;
                String tekst;
                JTextField bruker, svaret, epost;
                Logikk lo;
                Thread tell = null;
                
                public avgiSvar(JTextField brukeren, JTextField epost, JTextField svaret, Krukken g){
                    bruker = brukeren;
                    this.epost = epost;
                    this.svaret = svaret;
                    gui = g;
                }

                @Override
                public void actionPerformed(ActionEvent e){
                    String nyttSvar = bruker.getText()+","+epost.getText()+","+svar.getText();
                    try {
                        Integer.parseInt(svaret.getText());
                    } catch (NumberFormatException nfe){
                        tell = new Thread(new Nedtelling(gui, false));
                        tell.start();
                        return;
                    }
                    
                    l.skrivTilFil(nyttSvar);
                    tell = new Thread(new Nedtelling(gui, true));
                    tell.start();
                }
            }
        
        avgi.addActionListener(new avgiSvar(brukernavn, epost, svar, this)); 
        display();

    }

    public void display(){
        en.removeAll();
        en.add(overskrift);
        en.add(brukernavn);
        en.add(epost);
        en.add(svar);
        en.add(avgi);
        en.add(status);
        en.add(pic2);

        vindu.add(en);
        vindu.pack();

        overskrift.requestFocusInWindow();

        vindu.setVisible(true);

    }

    public void resetTekst(){
        brukernavn.setText("Fullt navn");
        epost.setText("E-post");
        svar.setText("Svar");
        status.setText("");
    }
}