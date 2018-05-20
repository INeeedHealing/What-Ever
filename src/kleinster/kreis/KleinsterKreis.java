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
        Tuple cCenter = new Tuple(0, 0);  //Zentrum des Kreises

        double radius = 0;

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

            //zu überprüfende Punkte auslosen. getestet
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
                            System.out.println("Gleiche Zahl! Neue Zufallszahl generiert: " + zz);
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
            boolean foundCircle = false;
            //Alle möglichen Paare von Punkten checken und schauen ob sie den Kleinste Kreis definieren. Nicht getestet 
            for (int i = 0; i < auszulosendeP; i++) {
                for (int j = 0; j < auszulosendeP; j++) {
                    int x1 = points[ausgelosteP[i]].x;
                    int x2 = points[ausgelosteP[j]].x;

                    int y1 = points[ausgelosteP[i]].y;
                    int y2 = points[ausgelosteP[j]].y;

                    int dx = Math.abs(x1 - x2);
                    int dy = Math.abs(y1 - y2);

                    cCenter.x = (x1 + x2) / 2;
                    cCenter.y = (y1 + y2) / 2;

                    radius = Math.pow((dx * dx + dy * dy), 0.5);

                    //checken ob alle drin sind
                    foundCircle = true;
                    for (int k = 0; k < auszulosendeP; k++) {
                        int x = points[ausgelosteP[k]].x;
                        int y = points[ausgelosteP[k]].y;

                        double dx2 = Math.abs(cCenter.x - x);
                        double dy2 = Math.abs(cCenter.y - y);

                        if (radius < Math.pow((dx2 * dx2 + dy2 * dy2), 0.5)) {//wenn der Kreis nicht der kleinste umschließende ist check beenden
                            foundCircle = false;
                            break;
                        }
                    }
                    if (foundCircle) {
                        break;
                    }
                }
                if (foundCircle) {
                    break;
                }
            }

            //wenn kein Kreis gefunden wurde, alle Kreise, die durch drei Punkte definiert werden checken
            if (!foundCircle) {
                System.out.println("3er Suche");
            }

            //checken ob alle drin sind
            finished = true;
            for (int k = 0; k < anzahlP; k++) {
                int x = points[k].x;
                int y = points[k].y;

                double dx2 = Math.abs(cCenter.x - x);
                double dy2 = Math.abs(cCenter.y - y);

                if (radius < Math.pow((dx2 * dx2 + dy2 * dy2), 0.5)) {//wenn der Kreis nicht der kleinste umschließende ist check beenden
                    lose[k] *= 2;
                    finished = false;
                }
            }
        }
        System.out.println("Gebrauchte Zeit: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
        System.out.println("fertig");
    }
}
