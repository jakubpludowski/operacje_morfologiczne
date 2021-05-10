package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JPanel
{
    //Deklaracja elementów GUI
    private JPanel upPanel, middlePanel, downPanel;
    private JPanel leftUpPanel, centerUpPanel, rightUpPanel;
    private JPanel leftMiddlePanel, rightMiddlePanel;
    private JPanel leftDownPanel, rightDownPanel, upRightDownPanel, downRightDownPanel;
    private JPanel leftTonPanel, rightTonPanel;
    private JButton B1, B2,B3,B4,B5,B6,B7,B8, B9, B10;
    private JRadioButton URB1, URB2, MRB1, MRB2, TRB1, TRB2;
    private JLabel podawanieTonuL;
    private JTextField podawanieTonuF;
    private ButtonGroup UBG, MBG, TBG;
    public JFileChooser wczytajObraz;
    //Deklaracja obrazów
    private Obraz mObrazWe, mObrazWeOrg, mObrazWeBin, mObrazWy;
    //deklaracja ścieżek dostępu do obrazów
    private String sciezka;
    private String nazwa_domyslna = "java.jpg";

    private int mTon;

    //Kontruktor
    public View()
    {

    //Panele główne
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS ));
        //this.setBorder(BorderFactory.createTitledBorder("mainPanel"));
        this.setVisible(true);

        this.upPanel = new JPanel();
        this.add(upPanel);
        this.upPanel.setLayout(new GridLayout(1,3));
        //this.upPanel.setBorder(BorderFactory.createTitledBorder("upPanel"));
        this.upPanel.setVisible(true);
        this.add(upPanel, BorderLayout.NORTH);


        this.middlePanel = new JPanel();
        this.add(middlePanel);
        this.middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.X_AXIS ));
        this.middlePanel.setVisible(false);
        this.add(middlePanel, BorderLayout.CENTER);


        this.downPanel = new JPanel();
        this.add(downPanel);
        this.downPanel.setLayout(new GridLayout(1,2));
        this.downPanel.setVisible(false);
        this.add(downPanel, BorderLayout.SOUTH);


    //Panel górny
        this.leftUpPanel = new JPanel();
        this.upPanel.add(leftUpPanel);
        this.leftUpPanel.setVisible(false);

        this.centerUpPanel = new JPanel();
        this.upPanel.add(centerUpPanel);
        this.centerUpPanel.setVisible(true);

        this.rightUpPanel = new JPanel();
        this.upPanel.add(rightUpPanel);
        this.rightUpPanel.setVisible(false);

    //Panel na obrazy
        this.leftMiddlePanel = new JPanel();
        this.middlePanel.add(leftMiddlePanel);
        this.leftMiddlePanel.setVisible(true);


        this.rightMiddlePanel = new JPanel();
        this.middlePanel.add(rightMiddlePanel);
        this.rightMiddlePanel.setVisible(true);


    //Panel na Guziki
        this.leftDownPanel = new JPanel();
        this.downPanel.add(leftDownPanel);
        this.leftDownPanel.setBorder(BorderFactory.createTitledBorder("Guziki"));
        this.leftDownPanel.setLayout(new GridLayout(3,2));
        this.leftDownPanel.setVisible(false);

    //Panel Ustawienia
        this.rightDownPanel = new JPanel();
        this.downPanel.add(rightDownPanel);
        this.rightDownPanel.setBorder(BorderFactory.createTitledBorder("Ustawienia"));
        this.rightDownPanel.setLayout(new BoxLayout(this.rightDownPanel,BoxLayout.Y_AXIS ));
        this.rightDownPanel.setVisible(true);

    //Panel Macierze
        this.upRightDownPanel = new JPanel();
        this.rightDownPanel.add(upRightDownPanel);
        this.upRightDownPanel.setBorder(BorderFactory.createTitledBorder("Macierze"));
        this.rightDownPanel.add(upRightDownPanel, BorderLayout.NORTH);
        this.upRightDownPanel.setVisible(false);
    //Panel Tony
        this.downRightDownPanel = new JPanel();
        this.rightDownPanel.add(downRightDownPanel);
        this.downRightDownPanel.setBorder(BorderFactory.createTitledBorder("Tony"));
        this.rightDownPanel.add(downRightDownPanel, BorderLayout.SOUTH);
        this.downRightDownPanel.setLayout(new BoxLayout(this.downRightDownPanel,BoxLayout.X_AXIS ));
        this.downRightDownPanel.setVisible(true);

    //Panel wybór tonu
        this.leftTonPanel = new JPanel();
        this.downRightDownPanel.add(leftTonPanel);
        this.downRightDownPanel.add(leftTonPanel, BorderLayout.WEST);
        this.leftTonPanel.setVisible(true);

    //Panel podanie tonu
        this.rightTonPanel = new JPanel();
        this.downRightDownPanel.add(rightTonPanel);
        this.downRightDownPanel.add(rightTonPanel, BorderLayout.EAST);
        this.rightTonPanel.setVisible(false);

    //OBRAZY
        this.DodajObrazy(this.getSciezka(),this.getNazwa_domyslna());

    //GUZIKI
        B1 = new JButton("Wczytaj Obraz");          B1.setPreferredSize(new Dimension(150,40));
        B2 = new JButton("Dylatacja");              B2.setPreferredSize(new Dimension(150,40));
        B3 = new JButton("Erozja");                 B3.setPreferredSize(new Dimension(150,40));
        B4 = new JButton("Otwarcie");               B4.setPreferredSize(new Dimension(150,40));
        B5 = new JButton("Zamknięcie");             B5.setPreferredSize(new Dimension(150,40));
        B6 = new JButton("Gradient");               B6.setPreferredSize(new Dimension(150,40));
        B7 = new JButton("Top Hat");                B7.setPreferredSize(new Dimension(150,40));
        B8 = new JButton("Stwórz obraz binarny");   B8.setPreferredSize(new Dimension(170,40));
        B9 = new JButton("Zapisz");                 B9.setPreferredSize(new Dimension(90,40));
        B10 = new JButton("Zapisz obraz na dysku"); B10.setPreferredSize(new Dimension(190,40));

        this.centerUpPanel.add(B1);
        this.leftDownPanel.add(B2);
        this.leftDownPanel.add(B3);
        this.leftDownPanel.add(B4);
        this.leftDownPanel.add(B5);
        this.leftDownPanel.add(B6);
        this.leftDownPanel.add(B7);
        this.rightUpPanel.add(B8);
        this.rightUpPanel.add(B10);
        this.B10.setVisible(false);

    //RADIOBUTTONS

        URB1 = new JRadioButton("Obraz oryginalny");    URB1.setPreferredSize(new Dimension(150,40));
        URB2 = new JRadioButton("Obraz binarny");       URB1.setPreferredSize(new Dimension(150,40));
        UBG = new ButtonGroup();
        UBG.add(URB1); UBG.add(URB2);
        this.leftUpPanel.add(URB1);
        this.leftUpPanel.add(URB2);


        MRB1 = new JRadioButton("Macierz 3x3");         MRB1.setPreferredSize(new Dimension(150,40));
        MRB2 = new JRadioButton("Macierz 5x5");         MRB2.setPreferredSize(new Dimension(150,40));
        MBG = new ButtonGroup();
        MBG.add(MRB1); MBG.add(MRB2);
        this.upRightDownPanel.add(MRB1);
        this.upRightDownPanel.add(MRB2);

        TRB1 = new JRadioButton("Ton automatyczny:   " + this.getmObrazWe().getmTon()); TRB1.setPreferredSize(new Dimension(180,40));
        TRB2 = new JRadioButton("Ton wybrany");                                         TRB2.setPreferredSize(new Dimension(120,40));
        TBG = new ButtonGroup();
        TBG.add(TRB1); TBG.add(TRB2);
        this.leftTonPanel.add(TRB1);
        this.leftTonPanel.add(TRB2);

    //Wybór i podanie tonu
        this.podawanieTonuL = new JLabel("Podaj wartość tonu");
        this.podawanieTonuL.setPreferredSize(new Dimension(150,30));
        this.rightTonPanel.add(podawanieTonuL);
        this.podawanieTonuL.setVisible(true);

        this.podawanieTonuF = new JTextField();
        this.podawanieTonuF.setPreferredSize(new Dimension(300,30));
        this.rightTonPanel.add(podawanieTonuF);
        this.podawanieTonuF.setVisible(true);

        this.rightTonPanel.add(B9);


    }

    //Funkcja dodająca do panelu obrazy (używana do odświeżania, które występuje często, aby kod był bardziej przejrzysty)
    public void DodajObrazy(String naz, String dom)
    {
        leftMiddlePanel.removeAll();
        rightMiddlePanel.removeAll();

        mObrazWe = new Obraz(naz, dom);
        mObrazWeOrg = new Obraz(naz, dom);
        mObrazWe.setVisible(true);
        this.leftMiddlePanel.add(mObrazWe);
        mObrazWy = new Obraz(naz, dom);
        mObrazWy.setVisible(true);
        this.rightMiddlePanel.add(mObrazWy);
    }

    //Gettery Paneli
    public JPanel getUpPanel() {return this.upPanel;}
    public JPanel getMiddlePanel() {return this.middlePanel;}
    public JPanel getDownPanel() {return this.downPanel;}
    public JPanel getLeftUpPanel() {return this.leftUpPanel;}
    public JPanel getCenterUpPanel() {return this.centerUpPanel;}
    public JPanel getRightUpPanel() {return this.rightUpPanel;}
    public JPanel getRightMiddlePanel() {return this.rightMiddlePanel;}
    public JPanel getLeftMiddlePanel() {return this.leftMiddlePanel;}
    public JPanel getLeftDownPanel() {return this.leftDownPanel;}
    public JPanel getRightDownPanel() {return this.rightDownPanel;}
    public JPanel getUpRightDownPanel() {return this.upRightDownPanel;}
    public JPanel getDownRightDownPanel() {return this.downRightDownPanel;}
    public JPanel getLeftTonPanel() {return this.leftTonPanel;}
    public JPanel getRightTonPanel() {return this.rightTonPanel;}

    //Gettery guzikow
    public JRadioButton getTRB1() {return this.TRB1;}
    public void setTRB1(JRadioButton x) {TRB1 =x;}
    public JRadioButton getTRB2() {return this.TRB2;}
    public JRadioButton getMRB1() {return this.MRB1;}
    public JRadioButton getMRB2() {return this.MRB2;}
    public JRadioButton getURB1() {return this.URB1;}
    public JRadioButton getURB2() {return this.URB2;}

    public JButton getB1() {return this.B1;}
    public JButton getB2() {return this.B2;}
    public JButton getB3() {return this.B3;}
    public JButton getB4() {return this.B4;}
    public JButton getB5() {return this.B5;}
    public JButton getB6() {return this.B6;}
    public JButton getB7() {return this.B7;}
    public JButton getB8() {return this.B8;}
    public JButton getB9() {return this.B9;}
    public JButton getB10() {return this.B10;}

    //Inne gettery i settery
    public JFileChooser getWczytajObraz() {return wczytajObraz;}
    public JTextField getPodawanieTonuF() {return podawanieTonuF;}

    public String getSciezka() {return this.sciezka;}
    public void setSciezka(String s){this.sciezka=s;}

    public String getNazwa_domyslna()
    {
        return nazwa_domyslna;
    }

    //Gettery obrazów
    public Obraz getmObrazWe() {return mObrazWe;}
    public Obraz getmObrazWeOrg() {return mObrazWeOrg;}
    public Obraz getmObrazWeBin() {return mObrazWeBin;}
    public Obraz getmObrazWy() {return mObrazWy;}

    //Settery obrazów
    public void setmObrazWe(Obraz x){mObrazWe = x;}
    public void setmObrazWeOrg(Obraz x){mObrazWeOrg = x;}
    public void setmObrazWeBin(Obraz x){mObrazWeBin = x;}
    public void setmObrazWy(Obraz x){mObrazWy = x;}

    public int getmTon(){return this.mTon;}
    public void setmTon(int x){this.mTon = x;}

    //Dodanie kontrolera do guzików
    public void Ctrl(ActionListener c)
    {
        this.B1.addActionListener(c);
        this.B2.addActionListener(c);
        this.B3.addActionListener(c);
        this.B4.addActionListener(c);
        this.B5.addActionListener(c);
        this.B6.addActionListener(c);
        this.B7.addActionListener(c);
        this.B8.addActionListener(c);
        this.B9.addActionListener(c);
        this.B10.addActionListener(c);
        this.TRB1.addActionListener(c);
        this.TRB2.addActionListener(c);
        this.URB1.addActionListener(c);
        this.URB2.addActionListener(c);
        this.MRB1.addActionListener(c);
        this.MRB2.addActionListener(c);

    }

}
