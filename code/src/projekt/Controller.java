package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//Klasa Kontrolera implementująca interfejs ActionListener, posiadająca własny widok, 3 obrazy oraz obiekt App, któy jest oknem aplikacji
public class Controller implements ActionListener
{
    private View mView;
    private App mApp;
    private Obraz mObrazWe;
    private Obraz mObrazWeOrg;
    private Obraz mObrazWy;

    //Konstruktor
    public Controller(View v, App a, Obraz o1, Obraz o2)
    {
        this.mApp = a;
        this.mView = v;
        this.mObrazWe = o1;
        this.mObrazWy = o2;
        this.mObrazWeOrg = new Obraz(mView.getSciezka(), mView.getNazwa_domyslna());
    }

    //Implementacja wymaganych obsług wydarzeń, wraz ze wszystkimi przypadkami
    @Override
    public void actionPerformed(ActionEvent e)
    {

        //Pierwsze menu, wczytywanie obrazu, "ujawnianie" innych paneli
        if(e.getActionCommand().equals("Wczytaj Obraz"))
        {
            mView.wczytajObraz = new JFileChooser();
            if (mView.wczytajObraz.showOpenDialog(null)!=JFileChooser.CANCEL_OPTION)
            {
                mView.setSciezka(mView.wczytajObraz.getSelectedFile().getPath());
            }

            System.out.println(mView.getSciezka());


            mView.DodajObrazy(mView.getSciezka(),mView.getNazwa_domyslna());
            this.mObrazWe = mView.getmObrazWe();
            this.mObrazWeOrg = mView.getmObrazWeOrg();
            this.mObrazWy = mView.getmObrazWy();
            mView.setmTon(this.mObrazWe.getmTon());

            mView.getLeftTonPanel().removeAll();
            mView.setTRB1(new JRadioButton("Ton automatyczny:   " + mView.getmTon()));
            mView.getTRB1().setPreferredSize(new Dimension(180,40));

            mView.getLeftTonPanel().add(mView.getTRB1());
            mView.getLeftTonPanel().add(mView.getTRB2());



            mView.getCenterUpPanel().setVisible(false);
            mView.getMiddlePanel().setVisible(true);
            mView.getDownPanel().setVisible(true);
            mView.getRightUpPanel().setVisible(true);


        }

        //Zapisywanie obrazu
        else if(e.getSource()==mView.getB10())
        {
            this.mObrazWy.ZapiszObraz();
        }

        //Operacje morfologiczne

        //TopHat 3x3
        else if(e.getSource()==mView.getB7() && this.mView.getMRB1().isSelected())
        {
            mObrazWy.TopHat3x3(mObrazWy);

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //TopHat 5x5
        else if(e.getSource()==mView.getB7() && this.mView.getMRB2().isSelected())
        {
            mObrazWy.TopHat5x5(mObrazWy);

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Gradient 3x3
        else if(e.getSource()==mView.getB6() && this.mView.getMRB1().isSelected())
        {
            mObrazWy.Gradient3x3();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }
        //Gradient 5x5
        else if(e.getSource()==mView.getB6() && this.mView.getMRB2().isSelected())
        {
            mObrazWy.Gradient5x5();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }


        //Zamkniecie 3x3
        else if(e.getSource()==mView.getB5() && this.mView.getMRB1().isSelected())
        {
            mObrazWy.Dylatacja3x3();
            mObrazWy.Erozja3x3();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Zamknięcie 5x5
        else if(e.getSource()==mView.getB5() && this.mView.getMRB2().isSelected())
        {
            mObrazWy.Dylatacja5x5();
            mObrazWy.Erozja5x5();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Otwarcie 3x3
        else if(e.getSource()==mView.getB4() && this.mView.getMRB1().isSelected())
        {
            mObrazWy.Erozja3x3();
            mObrazWy.Dylatacja3x3();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Otwarcie 5x5
        else if(e.getSource()==mView.getB4() && this.mView.getMRB2().isSelected())
        {
            mObrazWy.Erozja5x5();
            mObrazWy.Dylatacja5x5();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Dylatacja 3x3
        else if(e.getSource()==mView.getB2() && this.mView.getMRB1().isSelected())
        {
            mObrazWy.Dylatacja3x3();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Dylatacja 5x5
        else if(e.getSource()==mView.getB2() && this.mView.getMRB2().isSelected())
        {
            mObrazWy.Dylatacja5x5();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Erozja 3x3
        else if(e.getSource()==mView.getB3() && this.mView.getMRB1().isSelected())
        {
            mObrazWy.Erozja3x3();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Erozja 5x5
        else if(e.getSource()==mView.getB3() && this.mView.getMRB2().isSelected())
        {
            mObrazWy.Erozja5x5();

            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Zamiana lewego obrazu na oryginalny
        else if(this.mView.getURB1().isSelected())
        {
            mView.getLeftMiddlePanel().removeAll();
            this.mObrazWeOrg = mView.getmObrazWeOrg();
            mView.getLeftMiddlePanel().add(this.mObrazWeOrg);
            this.mApp.repaint();
        }

        //Zamiana lewego obrazu na binarny
        else if(mView.getURB2().isSelected())
        {
            mView.getLeftMiddlePanel().removeAll();
            mView.getLeftMiddlePanel().add(this.mObrazWe);
            this.mApp.repaint();
        }

        //Binaryzacja automatyczna i pokazanie paneli
        else if(e.getSource()==mView.getB8() && mView.getTRB1().isSelected())
        {
            mView.getRightUpPanel().setVisible(true);
            mView.getB8().setVisible(false);
            mView.getLeftDownPanel().setVisible(true);
            mView.getUpRightDownPanel().setVisible(true);
            mView.getLeftUpPanel().setVisible(true);
            mView.getB10().setVisible(true);

            mObrazWe.ObliczTon();
            mObrazWy.ObliczTon();
            mObrazWe.BinaryzacjaAutomatyczna();
            mObrazWy.BinaryzacjaAutomatyczna();


            mView.getLeftMiddlePanel().removeAll();
            mView.getLeftMiddlePanel().add(this.mObrazWeOrg);
            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Binaryzacja z wybranym tonem i pokazanie paneli
        else if(e.getSource()==mView.getB8() && mView.getTRB2().isSelected())
        {
            mView.getRightUpPanel().setVisible(true);
            mView.getB8().setVisible(false);
            mView.getLeftDownPanel().setVisible(true);
            mView.getUpRightDownPanel().setVisible(true);
            mView.getLeftUpPanel().setVisible(true);
            mView.getB10().setVisible(true);

            mObrazWy.setmTon(mView.getmTon());
            mObrazWe.setmTon(mView.getmTon());
            mObrazWe.BinaryzacjaAutomatyczna();
            mObrazWy.BinaryzacjaAutomatyczna();


            mView.getLeftMiddlePanel().removeAll();
            mView.getLeftMiddlePanel().add(this.mObrazWe);
            mView.getRightMiddlePanel().removeAll();
            mView.getRightMiddlePanel().add(this.mObrazWy);
            this.mApp.repaint();
        }

        //Podawanie tonu
        else if(e.getSource()==mView.getB9())
        {
            mView.setmTon(Integer.parseInt(mView.getPodawanieTonuF().getText()));
        }

        //Wystwietlanie panelu do podania tonu
        else if(mView.getTRB2().isSelected() && !(mView.getTRB1().isSelected()))
        {
            mView.getRightTonPanel().setVisible(true);
        }

        //Chowanie panelu do podawania tonu
        else if(mView.getTRB1().isSelected() && !(mView.getTRB2().isSelected()))
        {
            mView.getRightTonPanel().setVisible(false);
            mApp.repaint();
        }




    }
}
