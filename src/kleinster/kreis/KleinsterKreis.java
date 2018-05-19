package kleinster.kreis;

import java.util.Arrays;

public class KleinsterKreis {

    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();
        int fensterGröße = 500;
        int anzahlP = 50;
        int auszulosendeP = 13;
        boolean finished = false;

        float lose[] = new float[anzahlP];
        Arrays.fill(lose, 1);
        float anzahlLose = 0;

        float wkeiten[] = new float[anzahlP];

        int[] ausgelosteP = new int[auszulosendeP]; //Index in points
        Tuple[] points = new Tuple[anzahlP];        //Koordinaten
        Tuple cCenter;  //Zentrum des Kreises

        float radius;

        //pos. auslosen
        for (int i = 0; i < anzahlP; i++) {
            points[i] = new Tuple((int) (Math.random() * fensterGröße), (int) (Math.random() * fensterGröße));
            //System.out.println(points[i].x + "," + points[i].y);
        }

        //Grafik öffnen
        KreisFenster kf = new KreisFenster();
        kf.setVisible(true);

        while (!finished) {
            //Anzahl an Losen bestimmen
            for (int i = 0; i < anzahlP; i++) {
                anzahlLose += lose[i];
            }
            //Wkeiten der Punkte bestimmen
            for (int i = 0; i < anzahlP; i++) {
                wkeiten[i] = lose[i] / anzahlLose;
                System.out.println("Wkeit von Punkt " + i + " : " + wkeiten[i]);
            }

            //zu überprüfende Punkte auslosen
            for (int indexLP = 0; indexLP < auszulosendeP; indexLP++) {
                float zz = (float) Math.random();
                System.out.println("Zufallszahl: " + zz);
                System.out.println("IndexLP: " + indexLP);
                boolean equal = true;

                while (equal) {
                    //Es wird überprüft welcher Punkt gewählt werden soll
                    for (int indexP = 0; indexP < anzahlP; indexP++) {
                        if (indexP == 0) {
                            if (zz <= wkeiten[0]) {
                                ausgelosteP[indexLP] = 0;
                                break;
                            }
                            zz -= wkeiten[0];
                        } else {
                            if (zz <= wkeiten[indexP]) {
                                ausgelosteP[indexLP] = indexP;
                                break;
                            }
                            zz -= wkeiten[indexP];
                        }
                    }
                    System.out.println("Index des Gewählten Punktes: " + ausgelosteP[indexLP]);
                    //überprüft ob zufällig ausgewählter Punkt schonmal ausgewählt wurde. Falls ja wird neu generiert
                    for (int i = 0; i < indexLP; i++) {
                        if (ausgelosteP[i] == ausgelosteP[indexLP]) {
                            equal = true;
                            zz = (float) Math.random();
                            System.out.println("Gleiche Zahl! Neue Zufallszahl generiert: "+zz);
                            break;
                        } else {
                            equal = false;
                        }
                    }
                    if (indexLP == 0) {
                        equal = false;
                    }
                }
            }
            for (int i = 0; i < auszulosendeP; i++) {
                System.out.println("Gesammtindex von Punkt " + i + ": " + ausgelosteP[i]);
            }
            finished = true;
        }

    }

}
