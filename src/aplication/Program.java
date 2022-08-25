package aplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entitites.Sale;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.nextLine();
		
		List<Sale> list = new ArrayList<Sale>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			String line = br.readLine();
			while (line != null) {
				
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
								
			}
			
			Double priceAvg = list.stream().map(x -> x.averagePrice()).reduce(0.0, (x, y) -> x + y / list.size());

			Comparator<Sale> check = Comparator.comparing(Sale::averagePrice);

			List<Sale> firstFiveSales = list.stream().filter(x -> x.averagePrice() > priceAvg && x.getYear() == 2016)
					.sorted(check.reversed()).limit(5).toList();

			List<Sale> loganList = list.stream().filter(x -> x.getSeller().equals("Logan")).toList();

			Double amount = loganList.stream().filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.map(x -> x.getTotal()).reduce(0.0, (x, y) -> x + y);

			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");
			firstFiveSales.forEach(System.out::println);
			
			System.out.println();
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7: %.2f", amount);

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
			
		}
		
		sc.close();
		
	}

}
