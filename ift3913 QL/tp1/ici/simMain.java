import java.util.ArrayList;
import java.util.Arrays;

public class simMain {
	public static String[] adnStat = {"Rap","Pop","Rnb","Rock"};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<personne> population = new ArrayList<personne>();
		
		 generatorSimPopulation(population,55555);
		
		System.out.println("population: "+population.size());
		affcheGout(population,55555);
	}
	
	
	public static void generatorSimPopulation(ArrayList<personne> population, int n) {
		for (int i = 0; i < n; i++) {
			personne temp = new personne(i);
			population.add(temp);
		}
	}
	
	public static void affcheGout(ArrayList<personne> population,int n) {
		int [] type = new int[4];
		for (int i = 0; i < n; i++) {
			int j = Arrays.asList(adnStat).indexOf(population.get(i).getAdn());
			type[j] += 1;
			//System.out.println("num: "+population.get(i).getNum()+" adn: "+population.get(i).getAdn()+"");
			
		}
		
		System.out.println("Rap: "+type[0]+" Pop: "+type[1]+" Rnb: "+type[2]+" Rock: "+type[3]);
	}
	

}
