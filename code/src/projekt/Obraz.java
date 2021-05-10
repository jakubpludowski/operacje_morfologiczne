package projekt;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

import org.dcm4che2.io.*;
import org.dcm4che2.data.*;
import org.dcm4che2.util.*;

//Klasa dziedzicząca po JPanel przedstawiająca obraz oraz przechowująca macierz wartości binarnych opowiadających za jest kolor
public class Obraz extends JPanel
{
    //Obraz wyświetlany
    private BufferedImage mObrazIn;
    //Wymiary obrazu
    private int mWidth, mHeight;
    //Macierz przechowujaca wartości 0 i 1 binarne
    private int[][] mPixels;
    //Wartość tonu potrzebna do binaryzacji
    private int mTon;

    //Getter
    public BufferedImage getObrazIn() {return mObrazIn;}
    public int getWidth() { return mWidth; }
    public int getHeight() { return mHeight; }
    public int[][] getPixels() {return mPixels;}
    public int getmTon() {return mTon;}

    //Setter
    public void setObrazIn(BufferedImage x){mObrazIn = x;}
    public void setmWidth(int x){mWidth = x;}
    public void setmHeight(int x){mHeight = x;}
    public void setmPixels(int[][] x) {mPixels = x; }
    public void setmTon(int x) {mTon = x;}


    //Konstruktor
    public Obraz(String sciezka,String domyslne )
    {
        if (sciezka ==null)
        {
            sciezka = domyslne;
        }

        File imageFile = new File(sciezka);
        try{
            mObrazIn = ImageIO.read(imageFile);
        }
        catch (IOException e)
        {

            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
        this.mWidth = this.mObrazIn.getWidth();
        this.mHeight = this.mObrazIn.getHeight();
        this.mPixels = new int[this.mWidth][this.mHeight];
        for(int i=0; i<mWidth; i++)
        {
            for (int j = 0; j < mHeight; j++)
            {
                mPixels[i][j] = mObrazIn.getRGB(i,j);
            }
        }
        //Automatyczne wyznaczenie tonu na podstawie histogramu
        setmTon(ObliczTon());

        Dimension dimension = new Dimension(this.mWidth, this.mHeight);
        setPreferredSize(dimension);
    }

    //Konstruktor obrazu z podawanymi wymiarami oraz macierzą pikseli
    public Obraz(int w, int h, int[][] dane_piksele)
    {

        this.mWidth = w;
        this.mHeight = h;
        this.mPixels = new int[w][h];
        for(int i=0; i<mWidth; i++)
        {
            for (int j = 0; j < mHeight; j++)
            {
                this.mObrazIn.setRGB(i,j,dane_piksele[i][j]);
            }
        }
        Dimension dimension = new Dimension(this.mWidth, this.mHeight);
        setPreferredSize(dimension);
    }

    //Zapisywanie obrazu
    public void ZapiszObraz()
    {
        {
            Obraz nowy_obraz = this;
            File obrazek = new File("zapisany_obraz.jpg");
            try
            {
                ImageIO.write(nowy_obraz.mObrazIn, "jpg", obrazek);
            }
            catch (IOException e)
            {
                System.err.println("Blad zapisu obrazka");
                e.printStackTrace();
            }

        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.mObrazIn,0,0, this);

    }

    //Metoda wyliczajaca ton na podstawie histogramu
    public int ObliczTon()
    {
        int max=0;
        int min=255;

        for (int i = 0; i<this.getObrazIn().getWidth(); i++)
        {
            for (int j=0; j< this.getObrazIn().getHeight(); j++)
            {
                int color = this.mObrazIn.getRGB(i, j);

                int red = (0xff0000 & color) >> 16;
                int green = (0x00ff00 & color) >> 8;
                int blue = (0x0000ff & color);

                int m=(red+green+blue);

                if(m<756 & m>min & m>max)
                {
                    max = m;
                }
                else if(m<min)
                {
                    min = m;
                }
            }
        }
        return (max+min)/2;
    }

    //Metoda binaryzująca obraz na podstawie tonu
    public void BinaryzacjaAutomatyczna()
    {

         for (int i = 0; i<this.getObrazIn().getWidth(); i++)
         {
            for (int j=0; j<this.getObrazIn().getHeight(); j++)
            {
                int color = this.mObrazIn.getRGB(i, j);

                int red = (0xff0000 & color) >> 16;
                int green = (0x00ff00 & color) >> 8;
                int blue = (0x0000ff & color);

                int m=(red+green+blue);
                if(m >= this.mTon)
                {
                    this.mObrazIn.setRGB(i, j, 0xffffff);
                    this.mPixels[i][j]=1;
                }
                else
                {
                    this.mObrazIn.setRGB(i, j, 0x000000);
                    this.mPixels[i][j]=0;
                }
            }
         }

         //return new_Obraz;
    }

    //Metoda binaryzacji (nieużywana, ponieważ ton jest przechowywany jako pole klasy Obraz)
    public void BinaryzacjaZTonem(int ton)
    {
        for (int i = 0; i<this.getObrazIn().getWidth(); i++)
        {
            for (int j = 0; j < this.getObrazIn().getHeight(); j++)
            {
                int color = this.mObrazIn.getRGB(i, j);

                int red = (0xff0000 & color) >> 16;
                int green = (0x00ff00 & color) >> 8;
                int blue = (0x0000ff & color);

                int m=(red+green+blue);
                if(m >= ton)
                {
                    this.mObrazIn.setRGB(i, j, 0xffffff);
                    this.mPixels[i][j]=1;
                }
                else
                {
                    this.mObrazIn.setRGB(i, j, 0x000000);
                    this.mPixels[i][j]=0;
                }

            }

        }
    }

    //Erozja dla okna 3x3
    public void Erozja3x3()
    {
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(i>2  && i<this.mObrazIn.getWidth()-1 && j>2 && j<this.mObrazIn.getHeight()-1)
                {
                    if(this.mPixels[i][j]==0 && (this.mPixels[i-1][j-1]==1 || this.mPixels[i][j-1]==1 || this.mPixels[i+1][j-1]==1 || this.mPixels[i-1][j]==1 || this.mPixels[i+1][j]==1 || this.mPixels[i-1][j+1]==1 || this.mPixels[i][j+1]==1 || this.mPixels[i+1][j+1]==1))
                    {
                        this.mPixels[i][j]=2;
                    }
                }

                if(this.mPixels[i][j] == 2 || this.mPixels[i][j]==1)
                    {
                        this.mObrazIn.setRGB(i, j, 0xffffff);
                    }
                else
                    {
                        this.mObrazIn.setRGB(i, j, 0);
                    }
            }
        }

        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(this.mPixels[i][j]==2)
                {
                    this.mPixels[i][j]=1;
                }
            }
        }
    }

    //Erozja dla okna 5x5
    public void Erozja5x5()
    {
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(i>3  && i<this.mObrazIn.getWidth()-2 && j>3 && j<this.mObrazIn.getHeight()-2)
                {
                    if(this.mPixels[i][j]==0 && (this.mPixels[i-2][j-2]==1 | this.mPixels[i-2][j-1]==1 | this.mPixels[i-2][j]==1 | this.mPixels[i-2][j+1]==1 |
                            this.mPixels[i-2][j+2]==1 | this.mPixels[i-1][j+2]==1 | this.mPixels[i][j+2]==1 | this.mPixels[i+1][j+2]==1 | this.mPixels[i+2][j+2]==1 |
                            this.mPixels[i+2][j+1]==1 | this.mPixels[i+2][j]==1 | this.mPixels[i+2][j-1]==1 | this.mPixels[i+2][j-2]==1 | this.mPixels[i+1][j-2]==1 |
                            this.mPixels[i][j-2]==1 | this.mPixels[i+1][j-2]==1 | this.mPixels[i+1][j+1]==1 | this.mPixels[i-1][j+1]==1 | this.mPixels[i-1][j-1]==1 | this.mPixels[i+1][j-1]== 1 |
                            this.mPixels[i][j+1]==1 |this.mPixels[i][j-1]==1 |this.mPixels[i-1][j]==1 |this.mPixels[i+1][j]==1))
                    {
                        this.mPixels[i][j]=2;
                    }

                }

                if(this.mPixels[i][j] == 2 || this.mPixels[i][j]==1)
                {
                    this.mObrazIn.setRGB(i, j, 0xffffff);

                }
                else
                    {
                    this.mObrazIn.setRGB(i, j, 0);
                    }
            }
        }

        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(this.mPixels[i][j]==2)
                {
                    this.mPixels[i][j]=1;
                }
            }
        }
    }


    public void Dylatacja3x3()
    {
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(i>2  && i<this.mObrazIn.getWidth()-1 && j>2 && j<this.mObrazIn.getHeight()-1)
                {
                    if(this.mPixels[i][j]==1 && (this.mPixels[i-1][j-1]==0 || this.mPixels[i][j-1]==0 || this.mPixels[i+1][j-1]==0 || this.mPixels[i-1][j]==0 || this.mPixels[i+1][j]==0 || this.mPixels[i-1][j+1]==0 || this.mPixels[i][j+1]==0 || this.mPixels[i+1][j+1]==0))
                    {
                        this.mPixels[i][j]=2;
                    }

                }
                if(this.mPixels[i][j] == 2 || this.mPixels[i][j]==0)
                {
                    this.mObrazIn.setRGB(i, j, 0);

                }
                else
                    {
                        this.mObrazIn.setRGB(i, j, 0xffffff);
                }
            }
        }
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(this.mPixels[i][j]==2)
                {
                    this.mPixels[i][j]=0;
                }
            }
        }

    }

    public void Dylatacja5x5()
    {
        for (int i = 0; i<this.mObrazIn.getWidth(); i++){
            for (int j=0; j< this.mObrazIn.getHeight(); j++){
                if(i>3  && i<this.mObrazIn.getWidth()-2 && j>3 && j<this.mObrazIn.getHeight()-2)
                {
                    if(this.mPixels[i][j]==1 && (this.mPixels[i-2][j-2]==0 | this.mPixels[i-2][j-1]==0 | this.mPixels[i-2][j]==0 | this.mPixels[i-2][j+1]==0 | this.mPixels[i-2][j+2]==0 | this.mPixels[i-1][j+2]==0 |
                            this.mPixels[i][j+2]==0 | this.mPixels[i+1][j+2]==0 | this.mPixels[i+2][j+2]==0 | this.mPixels[i+2][j+1]==0 | this.mPixels[i+2][j]==0 | this.mPixels[i+2][j-1]==0 | this.mPixels[i+2][j-2]==0 |
                            this.mPixels[i+1][j-2]==0 | this.mPixels[i][j-2]==0 | this.mPixels[i+1][j-2]==0 | this.mPixels[i+1][j+1]==0 | this.mPixels[i-1][j+1]==0 | this.mPixels[i-1][j-1]==0 | this.mPixels[i+1][j-1]== 0 |
                            this.mPixels[i][j+1]==0 |this.mPixels[i][j-1]==0 |this.mPixels[i-1][j]==0 |this.mPixels[i+1][j]==0 ))
                    {
                        this.mPixels[i][j]=2;
                    }


                }

                if(this.mPixels[i][j] == 2 || this.mPixels[i][j]==0){
                    this.mObrazIn.setRGB(i, j, 0);

                }
                else{
                    this.mObrazIn.setRGB(i, j, 0xffffff);
                }
            }
        }
        for (int i = 0; i<this.mObrazIn.getWidth(); i++){
            for (int j=0; j< this.mObrazIn.getHeight(); j++){
                if(this.mPixels[i][j]==2)
                {
                    this.mPixels[i][j]=0;
                }
            }
        }

    }

    //Algorytm gradientu korzystający z operacji erozji i dylatacji
    public void Gradient3x3()
    {
        //operacja erozji
        int[][] erozja = new int[this.mObrazIn.getWidth()][this.mObrazIn.getHeight()];
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {
                erozja[i][j] = this.mPixels[i][j];
            }
        }
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(i>2  && i<this.mObrazIn.getWidth()-1 && j>2 && j<this.mObrazIn.getHeight()-1)
                {
                    if(erozja[i][j]==0 && (erozja[i-1][j-1]==1 || erozja[i][j-1]==1 || erozja[i+1][j-1]==1 || erozja[i-1][j]==1 || erozja[i+1][j]==1 || erozja[i-1][j+1]==1 || erozja[i][j+1]==1 || erozja[i+1][j+1]==1))
                    {
                        erozja[i][j]=2;
                    }
                }
            }
        }
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(erozja[i][j]==2)
                {
                    erozja[i][j]=1;
                }
            }
        }

        //operacja dylatacji
        int[][] dylatacja = new int[this.mObrazIn.getWidth()][this.mObrazIn.getHeight()];
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {
                dylatacja[i][j] = this.mPixels[i][j];
            }
        }
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(i>2  && i<this.mObrazIn.getWidth()-1 && j>2 && j<this.mObrazIn.getHeight()-1)
                {
                    if(dylatacja[i][j]==1 && (dylatacja[i-1][j-1]==0 || dylatacja[i][j-1]==0 || dylatacja[i+1][j-1]==0 || dylatacja[i-1][j]==0 || dylatacja[i+1][j]==0 || dylatacja[i-1][j+1]==0 || dylatacja[i][j+1]==0 || dylatacja[i+1][j+1]==0))
                    {
                        dylatacja[i][j]=2;
                    }

                }
            }
        }
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(dylatacja[i][j]==2)
                {
                    dylatacja[i][j]=0;
                }
            }
        }
        //różnica między erozją a dylatacją
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {
                int g = erozja[i][j] - dylatacja[i][j];

                if(g == 0 || g==-1)
                {
                    this.mObrazIn.setRGB(i, j, 0);

                }
                else{
                    this.mObrazIn.setRGB(i, j, 0xffffff);
                }

            }
        }
    }

    public void Gradient5x5()
    {
        int[][] erozja = new int[this.mObrazIn.getWidth()][this.mObrazIn.getHeight()];
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {
                erozja[i][j] = this.mPixels[i][j];
            }
        }

        int[][] dylatacja = new int[this.mObrazIn.getWidth()][this.mObrazIn.getHeight()];
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {
                dylatacja[i][j] = this.mPixels[i][j];
            }
        }

        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(i>3  && i<this.mObrazIn.getWidth()-2 && j>3 && j<this.mObrazIn.getHeight()-2)
                {
                    if(erozja[i][j]==0 && (erozja[i-2][j-2]==1 | erozja[i-2][j-1]==1 | erozja[i-2][j]==1 | erozja[i-2][j+1]==1 | erozja[i-2][j+2]==1 | erozja[i-1][j+2]==1 | erozja[i][j+2]==1 | erozja[i+1][j+2]==1 | erozja[i+2][j+2]==1 | erozja[i+2][j+1]==1 | erozja[i+2][j]==1 | erozja[i+2][j-1]==1 | erozja[i+2][j-2]==1 | erozja[i+1][j-2]==1 | erozja[i][j-2]==1 | erozja[i+1][j-2]==1))
                    {
                        erozja[i][j]=2;
                    }

                }

            }
        }

        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j=0; j< this.mObrazIn.getHeight(); j++)
            {
                if(erozja[i][j]==2)
                {
                    erozja[i][j]=1;
                }
            }
        }

        for (int i = 0; i<this.mObrazIn.getWidth(); i++){
            for (int j=0; j< this.mObrazIn.getHeight(); j++){
                if(i>3  && i<this.mObrazIn.getWidth()-2 && j>3 && j<this.mObrazIn.getHeight()-2){
                    if(dylatacja[i][j]==1 && (dylatacja[i-2][j-2]==0 | dylatacja[i-2][j-1]==0 | dylatacja[i-2][j]==0 | dylatacja[i-2][j+1]==0 | dylatacja[i-2][j+2]==0 | dylatacja[i-1][j+2]==0 | dylatacja[i][j+2]==0 | dylatacja[i+1][j+2]==0 | dylatacja[i+2][j+2]==0 | dylatacja[i+2][j+1]==0 | dylatacja[i+2][j]==0 | dylatacja[i+2][j-1]==0 | dylatacja[i+2][j-2]==0 | dylatacja[i+1][j-2]==0 | dylatacja[i][j-2]==0 | dylatacja[i+1][j-2]==0)){
                        dylatacja[i][j]=2;
                    }


                }
            }
        }
        for (int i = 0; i<this.mObrazIn.getWidth(); i++){
            for (int j=0; j< this.mObrazIn.getHeight(); j++){
                if(dylatacja[i][j]==2)
                {
                    dylatacja[i][j]=0;
                }
            }
        }

        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {
                int g = erozja[i][j] - dylatacja[i][j];

                if(g == 0 || g==-1)
                {
                    this.mObrazIn.setRGB(i, j, 0);

                }
                else{
                    this.mObrazIn.setRGB(i, j, 0xffffff);
                }

            }
        }

    }

    //Metoda Top-Hat tworząca kopię obrazu, wykonująca Erozję oraz dylatacje (otwarcie) na obrazie, a następnie zaznaczajaca różnice między nimi
    public void TopHat3x3(Obraz x)
    {
        //stworzenie kopii macierzy binarnej
        int[][] kopia = new int[this.mObrazIn.getWidth()][this.mObrazIn.getHeight()];
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {
                kopia[i][j] = this.mPixels[i][j];
            }
        }

        x.Erozja3x3();
        x.Dylatacja3x3();

        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {

                if(kopia[i][j] == x.mPixels[i][j])
                {
                    this.mObrazIn.setRGB(i, j, 0);

                }
                else{
                    this.mObrazIn.setRGB(i, j, 0xffffff);
                }



            }
        }

    }

    public void TopHat5x5(Obraz x)
    {
        //stworzenie kopii macierzy binarnej
        int[][] kopia = new int[this.mObrazIn.getWidth()][this.mObrazIn.getHeight()];
        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {
                kopia[i][j] = this.mPixels[i][j];
            }
        }

        x.Erozja5x5();
        x.Dylatacja5x5();

        for (int i = 0; i<this.mObrazIn.getWidth(); i++)
        {
            for (int j = 0; j < this.mObrazIn.getHeight(); j++)
            {

                if(kopia[i][j] == x.mPixels[i][j])
                {
                    this.mObrazIn.setRGB(i, j, 0);

                }
                else{
                    this.mObrazIn.setRGB(i, j, 0xffffff);
                }



            }
        }

    }


}
