import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class oyunuBaslat {
    public static void main(String[] args){
        Oyun game = new Oyun();
    }
}

public class Oyun {
    long time1 = System.nanoTime();
    JFrame frame = new JFrame();
    oyunPaneli gamePanel = new oyunPaneli();
    kelimePaneli wordPanel = new kelimePaneli();
    ArrayList<Harf> letters = new ArrayList<>();
    ArrayList<Cizgi> lines = new ArrayList<>();
    Set<Harf> selectedLetters = new LinkedHashSet<>();
    boolean selecting = false;
    final int UNITSIZE = 60;
    ArrayList<String> foundWords = new ArrayList<>();
    String[] map = {"XKXXOXXXX", "ÇOĞULXXXX", "XLXXUXKOÇ", "XXXOKÇUXO", "XÇXKXXLXK", "XOĞULXXXL", "XKXLXXXXU"};
    kelimePaneli[] wordPanelArray = new kelimePaneli[9];
    int score = 0; // max score: 38

    public Oyun() {
        frame.setSize(500, 800);
        frame.setVisible(true);
        frame.add(gamePanel);
        frame.setResizable(false);
        frame.setTitle("Kelime Bulmaca");
        letters.add(new Harf(215, 430, "L"));
        letters.add(new Harf(50, 500, "O"));
        letters.add(new Harf(380, 500, "Ç"));
        letters.add(new Harf(50, 610, "U"));
        letters.add(new Harf(380, 610, "K"));
        letters.add(new Harf(215,670, "Ğ"));
        for(int i = 0; i < 9; i++){
            wordPanelArray[i] = new kelimePaneli();
            wordPanelArray[i].setSize(40, 40);
            gamePanel.add(wordPanelArray[i]);
        }
        for(int i = 0; i < 9; i++){
            for(int j=0; j < 7; j++){
                if(map[j].toCharArray()[i] == 'X') {
                    wordPanelArray[i].gizle(j);
                    continue;
                }
                wordPanelArray[i].kelimeYaz(j, String.valueOf(map[j].toCharArray()[i]));
            }
        }
    }

    //Kelimeler: Çoklu, Çoğul, Okçu, Okul, Oluk, Oğul, Koç, Kol, Çok, Kul

    public void kelimeBil(String s){
        s = s.toLowerCase();
        if(s.equals("çoğul") && !foundWords.contains("çoğul")){
            wordPanelArray[0].goster(1);
            wordPanelArray[1].goster(1);
            wordPanelArray[2].goster(1);
            wordPanelArray[3].goster(1);
            wordPanelArray[4].goster(1);
            score += 5;
            foundWords.add("çoğul");
        }
        if(s.equals("kol") && !foundWords.contains("kol")){
            wordPanelArray[1].goster(0);
            wordPanelArray[1].goster(1);
            wordPanelArray[1].goster(2);
            score += 3;
            foundWords.add("kol");
        }
        if(s.equals("oluk") && !foundWords.contains("oluk")){
            wordPanelArray[4].goster(0);
            wordPanelArray[4].goster(1);
            wordPanelArray[4].goster(2);
            wordPanelArray[4].goster(3);
            score += 4;
            foundWords.add("oluk");
        }
        if(s.equals("okçu") && !foundWords.contains("okçu")){
            wordPanelArray[3].goster(3);
            wordPanelArray[4].goster(3);
            wordPanelArray[5].goster(3);
            wordPanelArray[6].goster(3);
            score += 4;
            foundWords.add("okçu");
        }
        if(s.equals("koç") && !foundWords.contains("koç")){
            wordPanelArray[6].goster(2);
            wordPanelArray[7].goster(2);
            wordPanelArray[8].goster(2);
            score += 3;
            foundWords.add("koç");
        }
        if(s.equals("çoklu") && !foundWords.contains("çoklu")){
            wordPanelArray[8].goster(2);
            wordPanelArray[8].goster(3);
            wordPanelArray[8].goster(4);
            wordPanelArray[8].goster(5);
            wordPanelArray[8].goster(6);
            score += 5;
            foundWords.add("çoklu");
        }
        if(s.equals("kul") && !foundWords.contains("kul")){
            wordPanelArray[6].goster(2);
            wordPanelArray[6].goster(3);
            wordPanelArray[6].goster(4);
            score += 3;
            foundWords.add("kul");
        }
        if(s.equals("okul") && !foundWords.contains("okul")){
            wordPanelArray[3].goster(3);
            wordPanelArray[3].goster(4);
            wordPanelArray[3].goster(5);
            wordPanelArray[3].goster(6);
            score += 4;
            foundWords.add("okul");
        }
        if(s.equals("çok") && !foundWords.contains("çok")){
            wordPanelArray[1].goster(4);
            wordPanelArray[1].goster(5);
            wordPanelArray[1].goster(6);
            score += 3;
            foundWords.add("çok");
        }
        if(s.equals("oğul") && !foundWords.contains("oğul")){
            wordPanelArray[1].goster(5);
            wordPanelArray[2].goster(5);
            wordPanelArray[3].goster(5);
            wordPanelArray[4].goster(5);
            score += 4;
            foundWords.add("oğul");
        }
        if(score == 38){
            long time2 = System.nanoTime();
            JOptionPane.showMessageDialog(null, "TEBRIKLER! \nOYUNU TAMAMLADINIZ \nAlgoritma Programlama 2 Final Ödevi\n"+"Bulmaca " + (time2 - time1) / 1000000000 + " Saniye Sürdü");
            System.out.println("Bulmaca " + (time2 - time1) / 1000000000 + " Saniye Sürdü");
        }
    }

    public double uzaklikAl(int x1, int y1, int x2, int y2){
        double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return distance;
    }

    public class Cizgi {
        public int startX;
        public int startY;
        public int targetX;
        public int targetY;

        public Cizgi(int startX, int startY, int targetX, int targetY) {
            this.startX = startX;
            this.startY = startY;
            this.targetX = targetX;
            this.targetY = targetY;
        }
    }

    public class Pozisyon {
        public int x;
        public int y;
        public Pozisyon(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public class Harf {
        String str;
        public int x;
        public int y;
        public boolean selected = false;

        public void select(){
            this.selected = true;
        }

        public void deselect(){
            this.selected = false;
        }

        public boolean isOn(int x, int y){
            if(uzaklikAl(x, y, this.ortalamaAl().x, this.ortalamaAl().y) <= UNITSIZE / 2){
                return true;
            } else return false;
        }

        public Pozisyon ortalamaAl(){
            int centerX = x + UNITSIZE / 2;
            int centerY = y + UNITSIZE / 2;
            return new Pozisyon(centerX, centerY);
        }

        @Override
        public String toString(){
            return this.str;
        }

        public Harf(int x, int y, String str){
            this.x = x;
            this.y = y;
            this.str = str;
        }

    }

    class kelimePaneli extends JPanel {

        JLabel[] wordRows = new JLabel[7];

        public kelimePaneli(){
            this.setLayout(new GridLayout(7, 9));
            this.setSize(500, 400);
            this.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
            this.setBackground(Color.white);
            for(int i = 0; i < 7; i++){
                String text = "";
                wordRows[i] = new JLabel();
                wordRows[i].setHorizontalAlignment(JLabel.CENTER);
                wordRows[i].setOpaque(false);
                wordRows[i].setForeground(Color.white);
                wordRows[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                wordRows[i].setPreferredSize(new Dimension(30,30));
                this.add(wordRows[i]);
            }
        }

        public void kelimeYaz(int position, String text){
            this.wordRows[position].setText(text);
        }

        public void goster(int position){
            this.wordRows[position].setForeground(Color.black);
        }

        public void gizle(int position){
            this.wordRows[position].setVisible(false);
        }
    }

    class oyunPaneli extends JPanel {

        int msx; //Mouse start x
        int msy;
        int mtx; //Mouse target x
        int mty;

        public oyunPaneli(){
            this.setBackground(new Color(255,255,255));
            this.addMouseListener(new GameMouseListener());
            this.addMouseMotionListener(new GameMouseListener());
        }

        public void kelimeYaz(Graphics g, String letter, int x, int y){
            g.drawOval(215, 430, 60, 60);
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            ciz(g);
        }

        private void okCiz(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
            int dx = x2 - x1, dy = y2 - y1;
            double D = Math.sqrt(dx*dx + dy*dy);
            double xm = D - d, xn = xm, ym = h, yn = -h, x;
            double sin = dy / D, cos = dx / D;
            x = xm*cos - ym*sin + x1;
            ym = xm*sin + ym*cos + y1;
            xm = x;
            x = xn*cos - yn*sin + x1;
            yn = xn*sin + yn*cos + y1;
            xn = x;
            int[] xpoints = {x2, (int) xm, (int) xn};
            int[] ypoints = {y2, (int) ym, (int) yn};
            g.drawLine(x1, y1, x2, y2);
            g.fillPolygon(xpoints, ypoints, 3);
        }

        public void ciz(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("Puan:" + String.valueOf(score), 50, 400);
            for(Harf letter: letters){
                g.setColor(new Color(20, 133, 188));
                if(letter.selected) {
                    g.setColor(new Color(70, 200, 20));
                }
                g2.setStroke(new BasicStroke(5));
                g.drawOval(letter.x, letter.y, 60, 60);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                g.drawString(letter.str, letter.x + 20, letter.y + 40);
            }
            g.setColor(new Color(20, 133, 188));
            for(Cizgi line : lines){
                okCiz(g, line.startX, line.startY, line.targetX, line.targetY, 30, 10);
            }
        }

        class GameMouseListener extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                for(Harf l : letters) {
                    if(l.isOn(e.getX(), e.getY())){
                        selecting = true;
                        msx = l.ortalamaAl().x;
                        msy = l.ortalamaAl().y;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for(Harf l : letters){
                    l.deselect();
                }
                lines.clear();

                ArrayList<String> selectedStrings = new ArrayList<>();
                for(Harf l : selectedLetters){
                    selectedStrings.add(l.toString());
                }
                selectedLetters.clear();
                kelimeBil(String.join("",selectedStrings));

                selecting = false;
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if(!selecting) return;
                repaint();
                for(Harf l : letters) {
                    if(l.isOn(e.getX(), e.getY())){
                        if(!selectedLetters.contains(l)){
                            lines.add(new Cizgi(msx, msy, l.ortalamaAl().x, l.ortalamaAl().y));
                            msx = l.ortalamaAl().x;
                            msy = l.ortalamaAl().y;
                        }
                        l.select();
                        selectedLetters.add(l);
                    }
                }
            }

        }

    }
}

