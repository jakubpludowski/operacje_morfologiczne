package projekt;

import javax.swing.*;
import java.awt.*;


//Klasa App tworząca okno aplikacji, posiadająca własny widok oraz dwa obrazy dziedzicząca po JFrame
public abstract class App extends JFrame
{
    private View mView;
    private Obraz mObrazWe, mObrazWy;

    //Konstruktor domyślny
    public App()
    {
        mView = new View();
        mObrazWe = this.mView.getmObrazWe();
        mObrazWy = this.mView.getmObrazWy();
        this.add(mView);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);
        this.setLocation(0,0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Aplikacja do przetwarzania obrazów");
    }

    //Program wykonywany
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                App aplikacja = new App(){};
                Controller listener = new Controller(aplikacja.mView, aplikacja, aplikacja.mObrazWe, aplikacja.mObrazWy);
                aplikacja.mView.Ctrl(listener);
                aplikacja.repaint();
            }
        });

    }

}
