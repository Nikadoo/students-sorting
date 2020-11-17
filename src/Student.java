import java.io.*;
import java.util.Scanner;

public class Student {
	

	public static void main(String[] args) throws IOException {
		
		
		//Na pocz�tek wybieram metod� sortowania
				
				
				String metoda = "";
				
				Metoda odczyt = new Metoda();
				
				
				odczyt.Metoda(metoda); // metoda pobiera od u�ytkownika metod� sortowania
				
				
				System.out.println("Twoja metoda to: " + odczyt.metoda); // Wy�wietlam tak dla pewno�ci
				
				/* Odczytuj� plik
				 * Plik o nazwie Studenci.txt znajduje si� w folderze projektu
				 * Jest on testowy
				 * �cie�ka w programie : pulpit
				*/
				try {
					ReadFile(odczyt.metoda);
				} catch (FileNotFoundException e) {
					System.out.println("Nie mozna bylo znalezc pliku");
				}
				
				
			
				

	}
	
	public static void ReadFile(String metoda) throws IOException {
		
		File file = new File(System.getProperty("user.dir") + File.separator + "Studenci.txt");
		Metoda odczyt = new Metoda();
		
		odczyt.CountLines(file); // metoda liczy ilo�� rekord�w
		
		odczyt.TableFile(file,odczyt.lines,metoda); //metoda przypisuje wartosci z pliku do tabeli
		

	}
	
	
	

}
class Metoda{ 
	int lines = 0;
	String metoda="";
	
	public void CountLines(File file) throws IOException { 
		LineNumberReader lnr;
		
		if(file.exists()) {
			FileReader fr = new FileReader(file);
			 lnr = new LineNumberReader(fr);
			
			while(lnr.readLine() != null) {
				this.lines++;
				
			}
		} System.out.println("liczba lini to: " + lines); //wy�wietlam dla pewno�ci 
	}
	
	
	
	public void TableFile(File file, int lines,String metoda) throws FileNotFoundException {
		String[] imie = new String[lines];   //tworz� tablice
		String[] numerIndeksu = new String[lines];
		int[] rokUrodzenia = new int[lines];
		
		Scanner in = new Scanner(file);
		 
		for(int i=0;i<lines;i++) {   //Przypisuje wartosci do niej
			imie[i] = in.next();
			numerIndeksu[i] = in.next();
			rokUrodzenia[i] = in.nextInt();
		}
		/* 
		 * Ta metoda sprawdzi, kt�re dane u�y� do sortowania
		 * posortuje, oraz zapisze plik
		 */
		SprawdzenieMetody(metoda, imie, numerIndeksu, rokUrodzenia,lines); 
		
		
		in.close();
	}
	
	String Metoda (String metoda) { // wymagam od u�ytkownika podania metody
		Scanner in = new Scanner(System.in);
		System.out.println("Podaj metod� sortowania: \n 1.imiona \n 2.numerindeksu \n 3.rokurodzenia");
		this.metoda = in.next();
		in.close();
		return metoda;
	}
	String SprawdzenieMetody(String metoda, String[] imie, String[] numerIndeksu, int[] rokUrodzenia, int lines) throws FileNotFoundException {
		if(metoda.equals("imiona") || metoda.equals("1") ) { // sprawdzamy warunki
			
			Przypisindeksu(imie,lines); // Metoda przypisuje do imienia jego indeks w pocz�tkowej tabeli, abym mog�a odnalezc dane u�ytkownika
			
			SortString(imie,lines); //  sortowanie imienne
			
			metoda = "names"; //nadaj� imie do zapisu
			
			SaveString(imie, numerIndeksu, rokUrodzenia, lines, metoda); // zapis tylko tablicy String�w
		} else {
			if(metoda.equals("numerindeksu") || metoda.equals("2")) {
				Przypisindeksu(numerIndeksu,lines); // korzystam z tej samej metody co dla imion
				
				
				SortString(numerIndeksu,lines); // sortowanie indeksow
				
				metoda = "index"; // imie do zapisu
				
				SaveString(numerIndeksu,imie, rokUrodzenia, lines, metoda); 
			} else {
				if(metoda.equals("rokurodzenia") || metoda.equals("3")) { //  sortowanie roku
					
					Przypisindeksuroku(rokUrodzenia, lines); //Metoda przypisuje indeks w pocz�tkowej tabeli, jednak z wato�ci� int korzystam z innej metody
					
					SortInt(rokUrodzenia, lines); //sortowanie roku
					
					metoda = "years";
					
					SaveInt(rokUrodzenia, imie, numerIndeksu,lines, metoda ); //zapis int
					
				}
			}
		}
		return metoda;
	}



	private void Przypisindeksu(String[] tab, int lines) {
		for(int i=0;i<lines;i++) { //Przypisuj� dla kazdego rekordu jego indeks pocz�tkowy
			tab[i]+=i;
		}

	}
	private void Przypisindeksuroku(int[] rokUrodzenia, int lines) { //dla intow przypisuj� wartosci 
		for(int i=0;i<lines;i++) { 
			rokUrodzenia[i]=Integer.valueOf(String.valueOf(rokUrodzenia[i]) + String.valueOf(i) );
			
		}
	}
	public void SortString(String[] tab,int lines) { //sortowanie String
		for(int i=0;i<lines -1;i++) {
			for(int j=0;j<lines-1;j++) {
				int index = 0; 
				int t1 = tab[j].length(); // zapisuje dlugosc wyrazu
				int t2 = tab[j+1].length();
				Comparision(tab,index,j, t1, t2); 
			}
		}
		
	}
	public static void Comparision(String[] tab, int index, int j, int t1, int t2) { //metoda porownania
		char c1 = tab[j].charAt(index); // zapisuje wartosci pod indeksem i porownuje
		char c2 = tab[j+1].charAt(index);
		
		String sub = "";
		if(c1 > c2) {  // zamieniam gdy litera jest "wi�ksza"
			sub = tab[j];
			tab[j] = tab[j+1];
			tab[j+1] = sub;
		}else {
			if(c1 == c2) { // sprawdzam warunek dla tej samej litery
				index++;
				
				if(index < t1 && index < t2) { // index sprawdza aby nie wyjsc poza wyraz
					
					Comparision(tab,index,j,t1,t2); // wykonuj� metod� ponownie je�eli index jest mniejszy
				} else {
					if(index == t2) { // je�eli index jest d�ugosci wyrazu (jedno imie zawiera sie w drugim np. Ada-Adam)
							sub = tab[j];      // to zamieniam krotsze imie z d�u�szym
							tab[j] = tab[j+1];
							tab[j+1] = sub;
					}
				} 
			}
		}
	}
	public void SortInt(int[] tab,int lines) { // sortowanie int�w
		for(int i=0;i<lines-1;i++) { 
			for(int j=0;j<lines-1;j++)
			{
				int pusty=0; 
				if(tab[j]>=tab[j+1]) { //gdy zmienna jest wi�ksza to zamieniam
					pusty = tab[j];
					tab[j] = tab[j+1];
					tab[j+1] = pusty;
				}
			}
		}
	}
	//zapis string�w
	public void SaveString(String[] tab, String[] tab1, int[] tab2, int lines, String metoda ) throws FileNotFoundException {
		PrintWriter zapis = new PrintWriter(System.getProperty("user.dir") + File.separator  + metoda + ".txt");
		for(int i=0;i<lines;i++) {
			int t = tab[i].length(); // t zapisuje dlugosc wyrazu
			char c = tab[i].charAt(t-1); // dzi�ki t znajduj� indeks pocz�tkowy wyrazu 
			int k=c;
			k=k-48; // konwersja char na int
			
			zapis.println(tab[i].substring(0,t-1) + " " + tab1[k] + " " + tab2[k]); // zapisujemy bez indeksu imienia oraz wartosci k indeksowe 
			}
		zapis.close();
	}
	
	//zapis int�w
	public void SaveInt(int[] tab, String[] tab1, String[] tab2, int lines, String metoda ) throws FileNotFoundException {
		PrintWriter zapis = new PrintWriter(System.getProperty("user.dir") + File.separator + metoda + ".txt");
		for(int i=0;i<lines;i++) {
			int c = tab[i] % 10; // ostatni indeks w roku
			
			zapis.println(tab[i]/10 + " " + tab1[c] + " " + tab2[c]); // zapisujemy bez indeksu roku oraz wartosci indeksowe c z tablic
			}
		zapis.close();
	}
	
	
}
