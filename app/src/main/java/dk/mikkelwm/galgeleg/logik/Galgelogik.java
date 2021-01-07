package dk.mikkelwm.galgeleg.logik;

import java.util.ArrayList;
import java.util.Random;

public class Galgelogik {

    ArrayList<String> muligeOrd = new ArrayList<String>();
    private ArrayList<String> brugteBogstaver = new ArrayList<String>();

    private static String ord;
    private String synligtOrd;
    private int antalForkerteBogstaver;
    private boolean sidsteBogstavVarKorrekt;
    private boolean spilletErVundet;
    private boolean spilletErTabt;

    public Galgelogik() {
        muligeOrd.add("røv");
        muligeOrd.add("stativ");
        muligeOrd.add("kasket");
        muligeOrd.add("info");
        muligeOrd.add("bord");
        muligeOrd.add("stol");

        startNytSpil();
    }

    private static final Galgelogik instance = new Galgelogik();
    public static Galgelogik getInstance(){
        return instance;
    }

    public ArrayList<String> getBrugteBogstaver() {
        return brugteBogstaver;
    }

    public String getSynligtOrd() {
        return synligtOrd;
    }

    public String getOrd() {
        return ord;
    }

    public int getAntalForkerteBogstaver() {
        return antalForkerteBogstaver;
    }

    public boolean erSidsteBogstavKorrekt() {
        return sidsteBogstavVarKorrekt;
    }

    public boolean erSpilletVundet() {
        return spilletErVundet;
    }

    public boolean erSpilletTabt() {
        return spilletErTabt;
    }

    public boolean erSpilletSlut() {
        return spilletErTabt || spilletErVundet;
    }

    public void startNytSpil() {
        brugteBogstaver.clear();
        antalForkerteBogstaver = 0;
        spilletErVundet = false;
        spilletErTabt = false;
        if (muligeOrd.isEmpty()) throw new IllegalStateException("Listen over mulige ord er tom!");
        ord = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
        System.out.println("Nyt spil - det skjulte ord er: "+ ord);
        opdaterSynligtOrd();
    }

    private void opdaterSynligtOrd() {
        synligtOrd = "";
        spilletErVundet = true;

        for (int n = 0; n < ord.length(); n++) {
            String bogstav = ord.substring(n, n + 1);
            if (brugteBogstaver.contains(bogstav)) {
                synligtOrd = synligtOrd + bogstav;
            } else {
                synligtOrd = synligtOrd + "*";
                spilletErVundet = false;
            }
        }
    }

    public void gætBogstav(String bogstav) {
        if (bogstav.length() != 1) return;
        //str = "Der gættes på bogstavet: " + bogstav;
        System.out.println("Der gættes på bogstavet: " + bogstav);
        if (brugteBogstaver.contains(bogstav)) return;
        if (spilletErVundet || spilletErTabt) return;

        brugteBogstaver.add(bogstav);

        if (ord.contains(bogstav)) {
            sidsteBogstavVarKorrekt = true;
            System.out.println("Bogstavet var korrekt: " + bogstav);
        } else {
            sidsteBogstavVarKorrekt = false;
            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
            antalForkerteBogstaver = antalForkerteBogstaver + 1;
            if (antalForkerteBogstaver > 6) {
                spilletErTabt = true;
            }
        }
        opdaterSynligtOrd();
    }
}
