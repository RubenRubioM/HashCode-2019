package hashCode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class main {
	class Slide implements Comparable<Slide>{
		private char tipo;
		private Vector<Integer> numFoto;
		private Vector<String> tags;
		
		public Slide(char tipo, Vector<String> tags,Vector<Integer> numFoto) {
			this.tipo = tipo;			
			this.tags = new Vector<String>(tags);	
			this.numFoto = new Vector<Integer>(numFoto);
		}
		
		public Vector<Integer> getNumFoto() {
			return numFoto;
		}
		public char getTipo() {
			return tipo;
		}
		
		public Vector<String> getTags(){
			return tags;
		}
		
		public Vector<String> tagsDiferentes2(Vector<String> tags2){
			Vector<String> devolucion = new Vector<String>();
			
			for(int i=0; i<tags.size();i++) {
				for(int j=0; j<tags2.size();j++) {
					if(!tags.get(i).equalsIgnoreCase(tags2.get(j))) {
						devolucion.add(tags.get(i));
					}
				}
			}
			
			return devolucion;
		}
		
		//El numero de tags iguales
		public int tagsDiferentes(Vector<String> tags2) {
			
			int t1 = tags.size(); 
	        int t2 = tags2.size(); 
	        int rep = 0;
	        for(int i = 0; i < t1; i++) {
	            for (int j = 0; j < t2; j++) {
	                if(tags.elementAt(i).equalsIgnoreCase(tags2.elementAt(j))) {
	                    rep++;
	                }
	            }
	        }
			return rep;
		}

		@Override
		public int compareTo(Slide slide) {
			int t1 = tags.size(); 
	        int t2 = slide.getTags().size(); 
	        int rep = 0;
	        for(int i = 0; i < t1; i++) {
	            for (int j = 0; j < t2; j++) {
	                if(tags.elementAt(i).equalsIgnoreCase(slide.getTags().elementAt(j))) {
	                    rep++;
	                }
	            }
	        }
			return rep;
		}
	}

public static void main(String[] args) {
	
	File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    Vector<Slide> slidesHorizontales = new Vector<Slide>();
    Vector<Slide> slidesVerticales = new Vector<Slide>();

    try {
		archivo = new File ("c_memorable_moments.txt");
		fr = new FileReader (archivo);
		br = new BufferedReader(fr);

        // Lectura del fichero
        String linea;
        int i=0;
        int numFotos = 0;
        while((linea=br.readLine())!=null) {
        	//Primera linea el parametro del numero de fotos
        	if(i==0) {numFotos = Integer.parseInt(linea);}
        	else {
        		
        		//Extraemos datos para crear el Slide
        		String[] slideDatos = linea.split(" ");
        		Vector<String> tags = new Vector<String>();
        		for(int j=0; j<Integer.parseInt(slideDatos[1]);j++) {
        			tags.add(slideDatos[2+j]);
        		}
        		
        		//Añadimos el slide creado al array de todos los slides
        		char tipo = slideDatos[0].charAt(0);
        		Vector<Integer> numFotosIndex = new Vector<Integer>();
    			numFotosIndex.add(i-1);
        		if(tipo == 'V') {
        			
        			slidesVerticales.add(new main().new Slide(slideDatos[0].charAt(0), tags,numFotosIndex));
        		}else {
        			slidesHorizontales.add(new main().new Slide(slideDatos[0].charAt(0), tags,numFotosIndex));
        		}
        		
        		
        	}
        	i++;
        	
        } //Salimos de leer el fichero
        
        //Juntamos verticales
        int tagsDiferentes = 9999;
        int slideMenor = 0;
        for (int j = 0; j < slidesVerticales.size(); j++) {
			for (int j2 = 0; j2 < slidesVerticales.size(); j2++) {
				int numeroTags = slidesVerticales.get(j).tagsDiferentes(slidesVerticales.get(j2).getTags());
				if(tagsDiferentes>numeroTags) {
					tagsDiferentes= numeroTags;
					slideMenor = j2;
				}
			}
			Vector<Integer> numFotos3 = new Vector<Integer>();
			numFotos3.add(slidesVerticales.get(j).getNumFoto().get(0));
			numFotos3.add(slidesVerticales.get(slideMenor).getNumFoto().get(0));
			slidesHorizontales.add(new main().new Slide('H',slidesVerticales.get(j).tagsDiferentes2(slidesVerticales.get(slideMenor).getTags()),numFotos3));
			slidesVerticales.remove(j);
			if(slideMenor==0) {
				slidesVerticales.remove(slideMenor);
			}else {
				slidesVerticales.remove(slideMenor-1);
			}
			
				
			tagsDiferentes = 9999;
		}
        
        slidesHorizontales.sort(null);
        
        //Es el momento de escribir
        
        BufferedWriter writer = new BufferedWriter(new FileWriter("uwu3.txt"));
        writer.append(Integer.toString(slidesHorizontales.size()));
        writer.newLine();
        for(int c=0; c<slidesHorizontales.size(); c++) {
        	String escribir = null;
        	Vector<Integer> posiciones = slidesHorizontales.get(c).getNumFoto();
        	if(posiciones.size()==2) {
        		escribir = Integer.toString(posiciones.get(0)) +" " +Integer.toString(posiciones.get(1));
        	}else {
        		escribir = Integer.toString(posiciones.get(0));
        	}
        	System.out.println(escribir);
        	
        	writer.append(escribir);
        	writer.newLine();
        }
        writer.close();
      }catch(Exception e){
         e.printStackTrace();
      }finally{
    	  
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
}  
} // fin del main


