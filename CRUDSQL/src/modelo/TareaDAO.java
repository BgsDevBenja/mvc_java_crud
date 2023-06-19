package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benja
 */
public class TareaDAO {
    
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conexion conectar = new Conexion();
    Tarea t = new Tarea();
    
    public List listar(){
        List<Tarea> datos = new ArrayList<>();
        try{
            con = conectar.getConnection();
            ps = con.prepareStatement("select * from tarea");
            rs = ps.executeQuery();
            while(rs.next()){
                Tarea t = new Tarea();
                t.setId(rs.getInt(1));
                t.setTitulo(rs.getString(2));
                t.setDescripcion(rs.getString(3));
                t.setEstado(rs.getString(4));
                datos.add(t);
            }
        }catch(Exception e){}
        return datos;
    }
    
    public int agregar(Tarea tar){
        int r = 0;
        String sql = "insert into tarea(titulo,descripcion,estado) value(?,?,?)";
        try{
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,tar.getTitulo());
            ps.setString(2,tar.getDescripcion());
            ps.setString(3,tar.getEstado());
            r = ps.executeUpdate();
            if(r==1){
                return 1;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
        }  
        return r;
    }
    
    public int Actualizar(Tarea tar) {  
        int r=0;
        String sql="update tarea set titulo=?,descripcion=?,estado=? where id=?";        
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);            
            ps.setString(1,tar.getTitulo());
            ps.setString(2,tar.getDescripcion());
            ps.setString(3,tar.getEstado());
            ps.setInt(4,tar.getId());
            r=ps.executeUpdate();    
            if(r==1){
                return 1;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
        }  
        return r;
    }
    
    public int Delete(int id){
        int r=0;
        String sql="update tarea set estado = 'Cancelado' where id = "+id;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            r= ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
}
