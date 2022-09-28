import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class mainIcsec {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	
		String cheminDossier = inputDossier();
		String fichierCSV = inputFichier();
		
		//C:/Users/mike useni/Desktop/pp/ppDoc/ift3913 QL/tp1/ici
		//test
		
		File repertoire = new File(cheminDossier);
        String liste[] = repertoire.list();
        
        
        ArrayList<CouplageClass> listeClouplage = lectureRepertoire(liste);
        listeClouplage = Couplage(cheminDossier, liste, listeClouplage);
        
        ecrireFichie(cheminDossier,fichierCSV,listeClouplage);
        lectureFichieCSV(cheminDossier,fichierCSV);
    
	}
	
	public static ArrayList<CouplageClass> Couplage( String cheminDossier ,String [] liste,ArrayList<CouplageClass> listeClouplage) throws IOException {
 
        if (liste != null) {
        	
            for (int i = 0; i < liste.length; i++) {
            	try {
        			
        			BufferedReader br = new BufferedReader(new FileReader(cheminDossier+"/"+liste[i]));
        			String ligne = null;
        			
        			
        		    while ((ligne = br.readLine()) != null){
        		    	
        		    	String[] data = ligne.split(",");
        		     
        		    	for (String val : data){
        		    		
	    		          for (int j = 0; j < listeClouplage.size(); j++) {
	    		        	  
	    		        	  if(val.contains(listeClouplage.get(j).nomClasse) && (!liste[i].split("\\.")[0].equals(listeClouplage.get(j).nomClasse))) {
								int n = listeClouplage.get(j).getCSEC();
								listeClouplage.get(j).setCSEC(n+1);
	    		        	  }
							
	    		          }
        		          
        		      }
        		    	
        		    }
        		    br.close();
        		    
        		} catch (FileNotFoundException e) {
        			e.printStackTrace();
        		}
                
            }
        } else {
            System.err.println("Nom de repertoire invalide");
        }
        
        return listeClouplage;
	}
	
	
	public static void ecrireFichie(String cheminDossier,String fichier,ArrayList<CouplageClass> aEcrire) throws IOException {
		try {
			
			File file = new File(cheminDossier+"/"+fichier);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int i = 0; i < aEcrire.size(); i++) {
				bw.write(""+aEcrire.get(i).nomClasse+" : "+aEcrire.get(i).CSEC+"\n");
			}

			bw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void lectureFichieCSV(String cheminDossier,String fichier) throws IOException {
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(cheminDossier+"/"+fichier));
			String ligne = null;
			
		    while ((ligne = br.readLine()) != null){
		    	
		    	String[] data = ligne.split(",");
		     
		    	for (String val : data){
		          System.out.println(val);
		        }
		    }
		    br.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void lectureFichie(String cheminDossier,String fichier) throws IOException {
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(cheminDossier+"/"+fichier+".java"));
			String ligne = null;
			
		    while ((ligne = br.readLine()) != null){
		    	
		    	String[] data = ligne.split(",");
		     
		    	for (String val : data){
		          System.out.println(val);
		        }
		    }
		    br.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<CouplageClass> lectureRepertoire( String [] liste) {
		
		ArrayList<CouplageClass> listeClouplage = null;
 
        if (liste != null) {
        	
        	listeClouplage = new ArrayList<CouplageClass>();
        	
            for (int i = 0; i < liste.length; i++) {
            	 	
            	//////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!/////////
            	String temp = liste[i];
            	String[] data = temp.split("\\.");
            	
            	CouplageClass classeC = new CouplageClass(data[0],0);
            	listeClouplage.add(classeC);
                
            }
        } else {
            System.err.println("Nom de repertoire invalide");
        }
        
        return listeClouplage;
	}
	
	
	
	public static String inputDossier () {
		Scanner myObj = new Scanner(System.in);
			
		System.out.println("Entrez le chemin d un dossier");
		
		return myObj.nextLine();
	}
	
	public static String inputFichier () {
		Scanner myObj = new Scanner(System.in);
			
		System.out.println("Entrez un fichier CSV");
		
		return myObj.nextLine();
	}

}
