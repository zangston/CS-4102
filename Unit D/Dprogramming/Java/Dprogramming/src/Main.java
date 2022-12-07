/**
 * @author Winston Zhang, wyz5rge
 * Main.java
 * This is the file that calls the Tiling Dino algorithm
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("test2.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
			    lines.add(line.trim());
            }        

            Long start = System.currentTimeMillis();
            TilingDino td = new TilingDino();
			List<String> result = td.compute(lines);
            Long end = System.currentTimeMillis();

            for (String resLine : result)
                System.out.println(resLine);
            
            System.out.println("time: " + ((end - start) / 1000.0));

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred when reading file");
		}
	}
}