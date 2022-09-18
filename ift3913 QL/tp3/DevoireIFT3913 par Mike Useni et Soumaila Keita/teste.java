import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.currencyConverter.Currency;
import src.currencyConverter.MainWindow;

class teste {
	
	//------------------TESTS BOITE NOIRE-----------------------------
	
	//Approches de partition du domaine des entrées en classes d’équivalence
	@Test
	public void testPartitionDomaineSimple() {
		Currency c= new Currency("dol", "D");
		Double resultat = 36160.0;
		Double retour = c.convert(80.0,452.0);
		
		assertEquals(retour,resultat);
		
		Double resultat2 = -233200.0;
		Double retour2 = c.convert(-55.0,-4240.0);
		
		assertNotEquals(retour2, resultat2);
	}
	
	@Test
	public void testPartitionDomaineComplex() {
		MainWindow main =new MainWindow();
		ArrayList<Currency> currencies = Currency.init();
		
		Double retour=main.convert("US Dollar","US Dollar",currencies,5.0);
		Double resultat = 5.0;
		assertEquals(retour,resultat);
		
		Double retour2=main.convert("Euro","US Dollar", currencies,5.0);
		Double resultat2 = 5.37;
		assertEquals(retour2,resultat2);
		
		Double retour3=main.convert("CAN Dollar","US Dollar", currencies,5.0);
		Double resultat3 = 0.0;
		assertEquals(retour3,resultat3);
		
		Double retour4=main.convert("Euro","CAN Dollar", currencies,5.0);
		Double resultat4 = 0.0;
		assertEquals(retour4,resultat4);
		
		Double retour5=main.convert("US Dollar","US Dollar", currencies,-5.0);
		Double resultat5= -5.0;
		assertEquals(retour5,resultat5);
	}

	//Analyse des valeurs frontières.
	@Test
	public void testAnalyseFrontiereSimple() {
		Currency c= new Currency("dol", "D");
		
		Double resultat = 0.0;
		Double retour = c.convert(0.0,5.0);
		assertEquals(retour,resultat);
		
		Double resultat2 = -1000.0;
		Double retour2 = c.convert(-1.0,1000.0);
		assertEquals(retour2,resultat2);
	}
	//------------------TESTS BOITE BLANC-----------------------------
	
	//Critère de couverture des instructions 
	@Test
	void critereCoouvertureInsComplex() {
		Currency c= new Currency("EUR", "D");
		MainWindow main =new MainWindow();
		ArrayList<Currency> liste= c.init();
		
		Double deviceCovertie1=main.convert("Euro", "US Dollar",liste, 5.0);
		Double deviceCovertie2=main.convert("US Dollar", "KL",liste, 10.0);
		Double deviceCovertie3=main.convert("KL", "US Dollar",liste, 5.0);
		Double deviceCovertie4=main.convert("GHB", "KL",liste, 5.0);
		Double variable1 =5.37;
		Double variable2 =0.0;
		
		assertEquals(deviceCovertie1,variable1);
		assertEquals(deviceCovertie2, variable2);
		assertEquals(deviceCovertie3, variable2);
		assertEquals(deviceCovertie4, variable2);
		
		
	}
	
	@Test
	void critereCoouvertureInsSimple() {
		Currency c= new Currency("EUR", "D");
		MainWindow main =new MainWindow();
		ArrayList<Currency> liste= c.init();
		
		Double d=c.convert(451.0, 5000.0);
		Double v=Math.round(451.0*5000*100d)/100d;
		assertEquals(d,v);
		
	}

	//Critère de couverture des arcs du graphe de flot de contrôle
	@Test
	void critereCouvertureArcComplex() {
		Currency c= new Currency("EUR", "D");
		MainWindow main =new MainWindow();
		ArrayList<Currency> liste= c.init();
		
		//Pour la methode MainWindow.convert(String, String, ArrayList<Currency>, Double)
		//on passe a travers chaque arcs
		Double devise1=main.convert("Euro", "US Dollar",liste, 5.0);
		Double var1=5.37;
		assertEquals(devise1,var1);
		
		//on passe a travers le premier 
		Double devise2=main.convert("US Dollar", "CAD",liste, 10.0);
		Double var2=0.0;
		assertEquals(devise2,var2);
		
		// passe a travers le deuxieme arc
		Double devise3=main.convert("CAD", "Euro",liste, 10.0);
		Double var3=0.0;
		assertEquals(devise3,var3);
		
		//
		Double devise4=main.convert("EURO", "CAD",liste, 10.0);
		Double var4=0.0;
		assertEquals(devise4,var4);
		
	}
	
	//Critère couverture des chemins indépendants du graphe de flot de contrôle
	@Test
	void critereCouvertureCheminInpGrapComplex() {
		Currency c= new Currency("EUR", "D");
		MainWindow main =new MainWindow();
		ArrayList<Currency> liste= c.init();
		
		//Pour la methode MainWindow.convert(String, String, ArrayList<Currency>, Double)
		// les vecteurs
		//(111111000100000000) 
		Double devise5=main.convert("RT", "DF",liste, 10.0);
		Double var5=0.0;
		assertEquals(devise5, var5);
		
		//(111010111011100000) 	
		Double devise6=main.convert("RT", "EURO",liste, 10.0);
		Double var6=0.0;
		assertEquals(devise6, var6);
		
		//(111010111010111111)
		Double devise7=main.convert("Euro", "US Dollar",liste, 5.0);
		Double var7=5.37;
		assertEquals(devise7, var7);
		
	}
	
	@Test
	void critereCouvertureCheminInpGrapSimple() {
		Currency c= new Currency("EUR", "D");
		MainWindow main =new MainWindow();
		ArrayList<Currency> liste= c.init();
		
		//Pour La mÃ©thode Currency.convert(Double, Double) 
		//(11)
		Double dev=c.convert(451.0, 5000.0);
		Double varr1=Math.round(451.0*5000*100d)/100d;
		assertEquals(dev,varr1 );
	}

	//Critère de couverture des i-chemins
	@Test
	void critereCouvertureArcSimple() {
		Currency c= new Currency("EUR", "D");
		MainWindow main =new MainWindow();
		ArrayList<Currency> liste= c.init();
		
		//Pour La mÃ©thode Currency.convert(Double, Double)
		Double dev1=c.convert(451.0, 5000.0);
		Double varr2=Math.round(451.0*5000*100d)/100d;
		assertEquals(dev1,varr2);
	}
	
	//Critère de couverture des i-chemins
	@Test
	public void testCouvertureICheminComplex() {
		MainWindow main =new MainWindow();
		ArrayList<Currency> currencies = Currency.init();
		
		Double retour=main.convert("US Dollar","US Dollar",currencies,5.0);
		Double resultat = 5.0;
		assertEquals(retour,resultat);

		Double retour2=main.convert("Euro","Euro",currencies,5.0);
		Double resultat2 = 5.0;
		assertEquals(retour2,resultat2);
		
		Double retour3=main.convert("Chinese Yuan Renminbi","Chinese Yuan Renminbi",currencies,5.0);
		Double resultat3 = 5.0;
		assertEquals(retour3,resultat3);

	}
}
