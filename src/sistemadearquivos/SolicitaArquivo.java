/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemadearquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class SolicitaArquivo extends javax.swing.JFrame {

    static final int port = 8181;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String[] args;
    String[] arquivos;

    public SolicitaArquivo(String[] args) {
        initComponents();
        this.args = args;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btDownload = new javax.swing.JButton();
        btConectar = new javax.swing.JButton();
        btDesconectar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        listArquivos = new javax.swing.JList();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(700, 300));
        setResizable(false);

        btDownload.setText("Download");
        btDownload.setActionCommand("btDownload");
        btDownload.setEnabled(false);
        btDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDownloadActionPerformed(evt);
            }
        });

        btConectar.setText("Conectar");
        btConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConectarActionPerformed(evt);
            }
        });

        btDesconectar.setText("Desconectar");
        btDesconectar.setEnabled(false);
        btDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDesconectarActionPerformed(evt);
            }
        });

        listArquivos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listArquivos.setSelectedIndex(0);
        jScrollPane1.setViewportView(listArquivos);

        jScrollPane2.setViewportView(jScrollPane1);

        jMenu3.setLabel("Sistema de Arquivos");
        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(btDesconectar)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btDesconectar)
                    .addComponent(btConectar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConectarActionPerformed
        conectaSocket(args);
        btConectar.setEnabled(false);
        btDesconectar.setEnabled(true);
        btDownload.setEnabled(true);
    }//GEN-LAST:event_btConectarActionPerformed

    private void btDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDesconectarActionPerformed
        out.println("fim"); // manda o que o usuario escreveu
        out.flush();
        System.exit(1);
    }//GEN-LAST:event_btDesconectarActionPerformed

    private void btDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDownloadActionPerformed
        if (listArquivos.getSelectedIndex() >= 0) {
            try {
                out.println(listArquivos.getSelectedValue());
                out.flush();
                String nomeFile = in.readLine();
                String newNome = nomeFile.replace(".", "2.");
                OutputStream os = new FileOutputStream(newNome);
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                out.println("vai");
                out.flush();
                String test = "ok";
                while (test.equals("ok")) {
                    test = in.readLine();
                    if (test.equals("acabou")) {
                        test = "acabou";
                    } else {
                        bw.write(test);
                        bw.newLine();
                        test = in.readLine();
                        out.println("vai");
                        out.flush();
                    }
                }
                bw.close();

            } catch (IOException ex) {
                Logger.getLogger(SolicitaArquivo.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(rootPane, "Download realizado com sucesso!!!");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione o arquivo");
        }
    }//GEN-LAST:event_btDownloadActionPerformed

    public static void main(String[] args) {
        //inicia interface grafica
        SolicitaArquivo gui = new SolicitaArquivo(args);
        gui.setVisible(true);
    }

    public void conectaSocket(String[] args) {
        try {
            if (args.length == 1) {
                socket = new Socket(args[0], port);
            } else {
                InetAddress addr = InetAddress.getByName("localhost"); // pega o ip do servidor
                socket = new Socket(addr, port);
            }
            System.out.println("Socket:" + socket);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())));
            carregaLista();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregaLista() throws IOException {
        arquivos = null;
        // Recebe a quantiddade
        String s = in.readLine();
        arquivos = new String[Integer.parseInt(s)];

        out.println("vai");
        out.flush();

        String test = in.readLine();
        int i = 0;
        while (test.equals("ok")) {
            test = in.readLine();
            if (test.equals("acabou")) {
                test = "acabou";
            } else {
                arquivos[i] = test;
                i++;
                out.println("vai");
                out.flush();
                test = in.readLine();
            }
        }

        listArquivos.setListData(arquivos);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btConectar;
    private javax.swing.JButton btDesconectar;
    private javax.swing.JButton btDownload;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listArquivos;
    // End of variables declaration//GEN-END:variables
}
