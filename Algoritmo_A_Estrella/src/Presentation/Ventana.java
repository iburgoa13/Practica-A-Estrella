package Presentation;

import Controller.Controller;
import Deal.Algorithm_Astar;
import Deal.Coordinates;
import Deal.Road;
import Deal.Transfer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ventana extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    // Variables declaration - do not modify
    private JButton _init;
    private JButton _reset;
    private JButton _redim;



    private JPanel _panelBoard;
    private JPanel _panelInputs;

    private JLabel _iniLabel;
    private JTextField textFieldIniX;
    private JTextField textFieldIniY;

    private JLabel _endLabel;
    private JTextField textFieldFinX;
    private JTextField textFieldFinY;

    private JTextField textFieldAnchuraTablero;
    private JTextField textFieldAlturaTablero;

    private static int _w = 15;
    private static int _h = 10;
    private Controller _controller;
    int[][] obstacleMap;
    private TableroCell tableroCeldas;
    private JPanel panelInputsIni;
    private JPanel panelInputsFin;
    private JPanel panelInputsTam;
    private JPanel panelInputsIniFin;
    private JPanel panelTamInputs;
    private JPanel panelInsputsSolo;
    private String stringTamañoTiempoTablero;

    public Ventana() {
        tableroCeldas = new TableroCell(Ventana._w,
                Ventana._h);

        create();
        agregarManejadoresDeEventos();

        textFieldIniX.requestFocus();
        textFieldIniX.requestFocus(true);

    }

    public Ventana(int anchura, int altura ) {
        _w = anchura;

        _h = altura;
        _controller = new Controller(_w, _h);
        tableroCeldas = new TableroCell(Ventana._w,
                Ventana._h);

        tableroCeldas.inicializarEscuchadores(this);
        obstacleMap = new int[_w + 1][_h + 1];
        create();
        agregarManejadoresDeEventos();
    }
    private void agregarManejadoresDeEventos() {
        ALElementosVentana oyenteAL = new ALElementosVentana();
        KLElementosVentana oyenteKL = new KLElementosVentana();
        FLElementosVentana oyenteFL = new FLElementosVentana();

        _init.addActionListener(oyenteAL);
        _redim.addActionListener(oyenteAL);
        _reset.addActionListener(oyenteAL);

        _init.addKeyListener((KeyListener) oyenteKL);
        _redim.addKeyListener(oyenteKL);
        _reset.addKeyListener(oyenteKL);

        textFieldIniY.addFocusListener((FocusListener) oyenteFL);
        textFieldFinY.addFocusListener(oyenteFL);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void create() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        this.setSize(1200, 1000);
        this.setTitle("Algortimo A* [ " + _w + "x" + _h
                + " ] - Iker Burgoa Muñoz - 4ºE - Ingeniería del Software");


        _init = new javax.swing.JButton("Iniciar");
        _reset = new javax.swing.JButton("Reiniciar");
        _redim = new javax.swing.JButton("Redimensionar");

        textFieldIniX = new javax.swing.JTextField(5);
        textFieldIniY = new javax.swing.JTextField(5);
        textFieldFinX = new javax.swing.JTextField(5);
        textFieldFinY = new javax.swing.JTextField(5);

        textFieldAlturaTablero = new javax.swing.JTextField(5);
        textFieldAnchuraTablero = new javax.swing.JTextField(5);

        _iniLabel = new javax.swing.JLabel();
        _endLabel = new javax.swing.JLabel();

        _panelBoard = new javax.swing.JPanel();
        _panelInputs = new JPanel();
        panelInputsIni = new JPanel();
        panelInputsFin = new JPanel();
        panelInputsTam = new JPanel();

        Font font = new Font("Futura", Font.BOLD, 12);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);


        _iniLabel.setText("Inicio:");
        _iniLabel.setFont(font);
        _endLabel.setText("Final:");
        _endLabel.setFont(font);

        panelInputsIniFin = new JPanel();
        panelInputsIniFin.setLayout(new BorderLayout());
        panelInsputsSolo = new JPanel();
        panelInputsIniFin.setBorder(BorderFactory.createTitledBorder(""));

        this.setLayout(new BorderLayout());
        _panelBoard.add(tableroCeldas);

        _panelInputs.setLayout(new FlowLayout());

        panelInputsIni.add(textFieldIniX);
        panelInputsIni.add(textFieldIniY);
        panelInputsIni.setBorder(BorderFactory.createTitledBorder("Inicio"));
        panelInsputsSolo.add(panelInputsIni);

        panelInputsFin.add(textFieldFinX);
        panelInputsFin.add(textFieldFinY);
        panelInputsFin.setBorder(BorderFactory.createTitledBorder("Final"));
        panelInsputsSolo.add(panelInputsFin);

        panelInputsIniFin.add(panelInsputsSolo);
        panelInputsIniFin.add(_init, BorderLayout.SOUTH);

        panelInputsTam.setLayout(new BorderLayout());
        panelTamInputs = new JPanel();

        panelTamInputs.add(textFieldAnchuraTablero);
        panelTamInputs.add(textFieldAlturaTablero);
        panelInputsTam.add(panelTamInputs);

        stringTamañoTiempoTablero = "Tablero[ " + _w + "x"
                + _h + " ]";

        panelInputsTam.setBorder(BorderFactory
                .createTitledBorder(stringTamañoTiempoTablero));
        panelInputsTam.add(_redim, BorderLayout.SOUTH);

        _panelInputs.add(panelInputsIniFin);

        _panelInputs.add(panelInputsTam);

        _panelInputs.add(_reset);

        this.add(tableroCeldas, BorderLayout.CENTER);
        this.add(_panelInputs, BorderLayout.SOUTH);


        setSize(1000, 700);
        setLocationRelativeTo(this);

        // pack();
        textFieldIniX.requestFocus();
    }


    protected void pintarCaminoCeldas(final Road caminoMasCorto,
                                      final Coordinates coord_Inicio, final Coordinates coord_Fin) {
        new Thread(new Runnable() {

            public void run() {
                if (caminoMasCorto == null) {
                    JOptionPane.showMessageDialog(null,
                            "No existe un camino posible");
                    restaurarVentana();
                } else {
                    for (int x = 0; x < _w; x++) {

                        for (int y = 0; y < _h; y++) {

                            if (caminoMasCorto.contains(x, y)) {
                                try {
                                    // Retardo para pintar celdas
                                    Thread.sleep(120);

                                    tableroCeldas.pintarCeldaCamino(x, y);
                                } catch (InterruptedException e) {
                                    JOptionPane.showMessageDialog(null,
                                            "Hubo un error en el proceso");
                                }

                            } else {

                                if (obstacleMap[x][y] != 1) {

                                    tableroCeldas.pintarCeldaNormal(x, y);
                                }

                                if (coord_Inicio.getCoordX() == x
                                        && coord_Inicio.getCoordY() == y) {
                                    tableroCeldas.pintarInicio(x, y);
                                }

                                if (coord_Fin.getCoordX() == x
                                        && coord_Fin.getCoordY() == y) {
                                    tableroCeldas.pintarInicio(x, y);
                                }

                            }
                        }
                    }

                    int xFinal = Integer.parseInt(textFieldFinX.getText());
                    int yFinal = Integer.parseInt(textFieldFinY.getText());
                    tableroCeldas.pintarFinal(xFinal, yFinal);
                }
            }

        }).start();
    }
    protected void restaurarVentana() {
        dispose();
        new Ventana(_w, _h).pintarse();
    }

    public void pintarse() {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger
                    .getLogger(Ventana.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger
                    .getLogger(Ventana.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger
                    .getLogger(Ventana.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger
                    .getLogger(Ventana.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Cell fuente = (Cell) e.getSource(); // el que generó el evento
        tableroCeldas.pintarObstaculo(fuente.getFila(), fuente.getColumna());
        obstacleMap[fuente.getFila()][fuente.getColumna()] = 1;
    }


    public class ALElementosVentana implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            // Acciones boton Comenzar
            if (event.getSource() == _init) {
                try {

                    int xInicio = Integer.parseInt(textFieldIniX.getText());
                    int yInicio = Integer.parseInt(textFieldIniY.getText());

                    int xFinal = Integer.parseInt(textFieldFinX.getText());
                    int yFinal = Integer.parseInt(textFieldFinY.getText());

                    if (xInicio > _w - 1)
                        JOptionPane.showMessageDialog(null,
                                "Coor X Inicio fuera de rango");
                    else if (yInicio > _h - 1)
                        JOptionPane.showMessageDialog(null,
                                "Coor Y Inicio fuera de rango");
                    else if (xFinal > _w - 1)
                        JOptionPane.showMessageDialog(null,
                                "Coor X Final fuera de rango");
                    else if (yFinal > _h - 1)
                        JOptionPane.showMessageDialog(null,
                                "Coor Y Final fuera de rango");
                    else {

                        Coordinates coord_Inicio = new Coordinates(xInicio,
                                yInicio);
                        Coordinates coord_Fin = new Coordinates(xFinal, yFinal);


                        Transfer miTransfer = new Transfer(coord_Inicio,
                                coord_Fin, obstacleMap);

                        // Se manda la peticion para calcular el camino más
                        // corto, dado un inicio, un fin y un conjunto de
                        // obstaculos.
                        Transfer costeTiempo = (Transfer) _controller.action(Controller.CALCULAR_CAMINO_MAS_CORTO, miTransfer);

                        Road caminoMasCorto = (Road) _controller.action(Controller.GET_CAMINO_MAS_CORTO, null);



                        panelInputsTam.setBorder(BorderFactory
                                .createTitledBorder(stringTamañoTiempoTablero
                                        .concat(" - " + costeTiempo.getCostTime()
                                                + " ms")));

                        pintarCaminoCeldas(caminoMasCorto, coord_Inicio,
                                coord_Inicio);

                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null,
                            "Introduzca un número valido");
                }
            }

            if (event.getSource() == _redim) {
                try {

                    int anchura = Integer.parseInt(textFieldAnchuraTablero
                            .getText());
                    int altura = Integer.parseInt(textFieldAlturaTablero
                            .getText());

                    Ventana._h = altura;
                    Ventana._w = anchura;
                    if(altura<=2 && anchura<=2){
                        JOptionPane.showMessageDialog(null,"Tamaño minimo 3x3");
                    }
                    else  Ventana.this.restaurarVentana();
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null,
                            "Introduzca un número valido");
                }

               // Ventana.this.restaurarVentana();
            }

            if (event.getSource() == _reset) {
                Ventana.this.restaurarVentana();
            }

        }
    }


    public class KLElementosVentana implements KeyListener {

        public void keyPressed(KeyEvent arg0) {

            if (arg0.getKeyCode() == 10) {

                if (_init.isFocusOwner()) {
                    _init.doClick();
                    _init.requestFocus();
                }

                if (_redim.isFocusOwner()) {
                    _redim.doClick();
                }

                if (_reset.isFocusOwner()) {
                    _reset.doClick();
                }
            }

        }

        public void keyReleased(KeyEvent arg0) {
            // TODO Auto-generated method stub

        }

        public void keyTyped(KeyEvent arg0) {
            // TODO Auto-generated method stub

        }

    }


    public class FLElementosVentana implements FocusListener {

        public void focusGained(FocusEvent arg0) {

        }

        public void focusLost(FocusEvent arg0) {

            if (arg0.getSource() == textFieldIniY) {
                try {

                    int xInicio = Integer.parseInt(textFieldIniX.getText());
                    int yInicio = Integer.parseInt(textFieldIniY.getText());

                    if (xInicio > _w - 1)
                        JOptionPane.showMessageDialog(null,
                                "Coor X Inicio fuera de rango");
                    else if (yInicio > _h - 1)
                        JOptionPane.showMessageDialog(null,
                                "Coor Y Inicio fuera de rango");
                    else {

                        tableroCeldas.getCelda(xInicio, yInicio)
                                .setColorInicio();

                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null,
                            "Introduzca un número valido");
                }
            }

            if (arg0.getSource() == textFieldFinY) {
                try {

                    int xFinal = Integer.parseInt(textFieldFinX.getText());
                    int yFinal = Integer.parseInt(textFieldFinY.getText());

                    if (xFinal > _w - 1)
                        JOptionPane.showMessageDialog(null,
                                "Coor X Final fuera de rango");
                    else if (yFinal > _h - 1)
                        JOptionPane.showMessageDialog(null,
                                "Coor Y Final fuera de rango");
                    else {

                        tableroCeldas.getCelda(xFinal, yFinal).setColorFinal();

                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null,
                            "Introduzca un número valido");
                }
            }
        }
    }
}
