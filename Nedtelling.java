public class Nedtelling implements Runnable{
    Krukken gui;
    boolean riktig;
    public Nedtelling(Krukken gt, boolean r){
        gui = gt;
        riktig = r;
    }
    @Override
    public void run(){
        if (riktig){
            gui.status.setText("Svar registert");
            gui.display();
            try{Thread.sleep(1500);} catch (Exception e){System.out.println(e);}
            gui.resetTekst();
        } else {
            gui.status.setText("Svaret må være et heltall");
            gui.display();
            try{Thread.sleep(2500);} catch (Exception e){System.out.println(e);}
            gui.resetTekst();
        }

    }
}
