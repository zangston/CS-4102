import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {

	public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("test1.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
			    lines.add(line.trim());
            }        

			// Call method and print the result
            Long start = System.currentTimeMillis();
            Supply supply = new Supply();
            int edgeWeightSum = supply.compute(lines);
            System.out.println(edgeWeightSum);
            Long end = System.currentTimeMillis();
            System.out.println("time: " + ((end - start) / 1000.0));

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred when reading file");
		}
	}

}
