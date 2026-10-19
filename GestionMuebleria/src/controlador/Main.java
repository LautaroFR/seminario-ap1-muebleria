package controlador;
import dao.*;
import modelo.*;
import util.Conexion;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {
    private static final Scanner ENTRADA=new Scanner(System.in);
    private static final ClienteDAO CLIENTES=new ClienteDAO();
    private static final PedidoDAO PEDIDOS=new PedidoDAO();
    private static final PagoDAO PAGOS=new PagoDAO();
    private static String leer(String mensaje){System.out.print(mensaje);return ENTRADA.nextLine().trim();}
    private static int entero(String mensaje){return Integer.parseInt(leer(mensaje));}
    private static BigDecimal dinero(String mensaje){return new BigDecimal(leer(mensaje).replace(',','.'));}
    public static void main(String[] args){
        try(var c=Conexion.conectar()){System.out.println("Conectado a MySQL: muebleria");}
        catch(SQLException e){System.err.println("No se pudo conectar a MySQL: "+e.getMessage());return;}
        boolean continuar=true;
        while(continuar){
            System.out.println("\n=== GESTION DE MUEBLERIA (MYSQL) ===");
            System.out.println("1. Registrar cliente\n2. Listar clientes\n3. Registrar pedido\n4. Listar pedidos");
            System.out.println("5. Registrar pago\n6. Consultar saldo\n7. Actualizar estado\n8. Buscar pedido\n0. Salir");
            try{
                switch(entero("Opcion: ")){
                    case 1 -> System.out.println(CLIENTES.registrar(leer("Nombre: "),leer("Telefono: ")).mostrarInformacion());
                    case 2 -> {for(Cliente c:CLIENTES.listar())System.out.println(c.mostrarInformacion());}
                    case 3 -> System.out.println(PEDIDOS.registrar(entero("ID cliente: "),leer("Descripcion: "),dinero("Precio total: ")));
                    case 4 -> {for(Pedido p:PEDIDOS.listar())System.out.println(p);}
                    case 5 -> {int id=entero("ID pedido: ");PAGOS.registrar(id,dinero("Importe: "));System.out.println("Pago registrado. Saldo: $"+PEDIDOS.buscar(id).getSaldo());}
                    case 6 -> System.out.println("Saldo: $"+PEDIDOS.buscar(entero("ID pedido: ")).getSaldo());
                    case 7 -> {int id=entero("ID pedido: ");Pedido.Estado[] estados=Pedido.Estado.values();
                        for(int i=0;i<estados.length;i++)System.out.println((i+1)+". "+estados[i]);
                        int indice=entero("Estado (numero): ")-1;
                        if(indice<0||indice>=estados.length)throw new IllegalArgumentException("Estado fuera de rango");
                        PEDIDOS.actualizarEstado(id,estados[indice]);System.out.println("Estado actualizado");}
                    case 8 -> System.out.println(PEDIDOS.buscar(entero("ID pedido: ")));
                    case 0 -> {continuar=false;System.out.println("Hasta luego");}
                    default -> System.out.println("Opcion invalida");
                }
            }catch(SQLException|IllegalArgumentException|java.util.NoSuchElementException e){System.out.println("Error: "+e.getMessage());}
        }
    }
}
