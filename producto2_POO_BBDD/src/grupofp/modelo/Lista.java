/**
 * 
 */
package grupofp.modelo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author J-Programers
 *
 */
public class Lista <T> implements Iterable<T> {
	protected ArrayList <T> lista = new ArrayList <>();
	
	public void add(T objeto){ 
	    lista.add(objeto); 
	 } 
	 
	 public ArrayList<T> getList(){ 
	    return lista; 
	 } 
	
	public Iterator <T> iterator() {
		return lista.iterator();
	}

}
