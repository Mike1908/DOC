
public class personne {

	public int num;
	public  String  adn;
	private String[] adnStat = {"Rap","Pop","Rnb","Rock"};
	//public String autre;
	
	public personne(int num) {
		// TODO Auto-generated constructor stub
		this.num=num;
		
		 this.adn = adnStat[style()];
	}
	
	public int getNum() {
		return this.num;
	}
	
	public String getAdn() {
		return this.adn;
	}
	
	public int style() {
		int j= (int)(Math.random() * (100) + 1);
		
		if(1 <= j && j <= 30) {
			return 0;
		}
		if(31 < j && j <= 70) {
			return 1;
		}
		if(71 < j && j <= 80) {
			return 2;
		}
		else{
			return 3;
		}
	}

}
