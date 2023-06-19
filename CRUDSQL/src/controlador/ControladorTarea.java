/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Tarea;
import modelo.TareaDAO;

import vista.Vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author benja
 */
public class ControladorTarea  implements ActionListener{
    
    TareaDAO dao = new TareaDAO();
    Tarea t = new Tarea();
    vista.Vista vista = new Vista();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public ControladorTarea(vista.Vista v){
        this.vista = v;
        this.vista.btListar.addActionListener(this);
        this.vista.btGuardar.addActionListener(this);
        this.vista.btEditar.addActionListener(this);
        this.vista.btEliminar.addActionListener(this);
        this.vista.btOk.addActionListener(this);
        this.vista.btNuevo.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == vista.btListar){
            limpiarGrilla();
            Listar(vista.Grilla);
            nuevo();
        }
        if (e.getSource() == vista.btGuardar) {
            add();
            Listar(vista.Grilla);
            nuevo();
        }
        if (e.getSource() == vista.btEditar) {
            int fila = vista.Grilla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debee Seleccionar Una fila..!!");
            } else {
                int id = Integer.parseInt((String) vista.Grilla.getValueAt(fila, 0).toString());
                String titulo = (String) vista.Grilla.getValueAt(fila, 1);
                String descripcion = (String) vista.Grilla.getValueAt(fila, 2);
                String estado = (String) vista.Grilla.getValueAt(fila, 3);
                vista.txtId.setText("" + id);
                vista.txtTitulo.setText(titulo);
                vista.txtDescripcion.setText(descripcion);
                vista.cbEstado.setSelectedItem(estado);
            }
        }
        if (e.getSource() == vista.btOk) {
            Actualizar();
            Listar(vista.Grilla);
            nuevo();
        }
        if (e.getSource() == vista.btEliminar) {
            delete();
            Listar(vista.Grilla);
            nuevo();
        }
        if (e.getSource() == vista.btNuevo) {
            nuevo();
        }
    }
    
    void nuevo(){
        vista.txtId.setText("");
        vista.txtTitulo.setText("");
        vista.txtDescripcion.setText("");
        vista.cbEstado.setSelectedIndex(1);
        vista.txtTitulo.requestFocus();
    }
    
    public void delete(){
        int fila = vista.Grilla.getSelectedRow();
        if(fila == -1){
            JOptionPane.showMessageDialog(vista, "Debe seleccionar una Fila...!!!");
        }else{
            int id = Integer.parseInt((String) vista.Grilla.getValueAt(fila, 0).toString());
            dao.Delete(id);
            System.out.println("El resultado es "+id);
            JOptionPane.showMessageDialog(vista, "Tarea Eliminado...!!!");
        }
        limpiarGrilla();
    }
    
    public void add() {
        String titulo = vista.txtTitulo.getText();
        String descripcion = vista.txtDescripcion.getText();
        String estado = vista.cbEstado.getSelectedItem().toString();
        t.setTitulo(titulo);
        t.setDescripcion(descripcion);
        t.setEstado(estado);
        int r = dao.agregar(t);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Tarea Agregado con Exito.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error");
        }
        limpiarGrilla();
    }
    
    public void Actualizar() {
        if (vista.txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "No se Identifica el Id debe selecionar la opcion Editar");
        } else {
            int id = Integer.parseInt(vista.txtId.getText());
            String titulo = vista.txtTitulo.getText();
            String descripcion = vista.txtDescripcion.getText();
            String estado = vista.cbEstado.getSelectedItem().toString();
            t.setId(id);
            t.setTitulo(titulo);
            t.setDescripcion(descripcion);
            t.setEstado(estado);
            int r = dao.Actualizar(t);
            if (r == 1) {
                JOptionPane.showMessageDialog(vista, "Tarea Actualizado con Exito.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error");
            }
        }
        limpiarGrilla();
    }
    
    public void Listar(JTable tabla){
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Tarea> lista = dao.listar();
        Object[] objeto = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId();
            objeto[1] = lista.get(i).getTitulo();
            objeto[2] = lista.get(i).getDescripcion();
            objeto[3] = lista.get(i).getEstado();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);
    }
    
    void centrarCeldas(JTable tabla){
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vista.Grilla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
    
    
    void limpiarGrilla(){
        for(int i = 0; i < vista.Grilla.getRowCount(); i++){
            modelo.removeRow(i);
            i = i - 1;
        }
    }
}
