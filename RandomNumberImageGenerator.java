package randomNumberGenerator;

/*
 * @author : Pranjali Shrivastava 
 * Output: The image generated as output is named as "RandomColorImage.png". Please look for the output
 * image in the source folder containing this java file. Or, for convenience, you can edit the path of the image
 * in the program
 */


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class RandomNumberImageGenerator {
	public static void main(String args[]) throws IOException{
		List<Integer> randomNumbers = getRandomNumbers();
		int imageWidth = 128;
		int imageHeight = 128;
		createImage(randomNumbers, imageHeight, imageWidth);
	}
	
	/* Create a 128X128 image of randomly chosen colors for each pixel, where numbers are chosen randomly from the randomColor list
	 * Choosing random number from the randomNumbers list is equivalent to making an API call to Random.org each time to get a new random number
	 * Thus this approach also saves the time */
	private static void createImage(List<Integer> randomNumbers, int imageHeight, int imageWidth) throws IOException {
		// TODO Auto-generated method stub
		BufferedImage image = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_INT_ARGB);
		Color col;
		int count = 0;
		Random r = new Random();
		for(int i = 0; i < imageWidth; i++){
			for(int j = 0; j < imageHeight; j++){
				if(count == 255){count = 0;}
				int red = randomNumbers.get(r.nextInt(randomNumbers.size()));
				int green = randomNumbers.get(r.nextInt(randomNumbers.size()));
				int blue = randomNumbers.get(r.nextInt(randomNumbers.size()));
				col = new Color(red, green, blue);
				image.setRGB(i, j, col.getRGB());
			}
		}
		File file = new File("RandomColorImage.png");
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e);
		}
	}

	/* calls the random.org API to get a the random numbers for colors from a distinct color range of (0-255)
	 * Since each of red, green and blue color can have a range of 0-255, so we enlist random 256 numbers in the color range.
	 * Taking a list of 256 random numbers increases the range of colors that can be produced using those numbers */
	private static List<Integer> getRandomNumbers() throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
		String httpMethod = "GET";
		URL url =new URL("https://www.random.org/integers/?num=256&min=0&max=255&col=1&base=10&format=plain&rnd=new");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(httpMethod);
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String reader =br.readLine();
		while(reader != null){
			randomNumbers.add(Integer.parseInt(reader));
			reader = br.readLine();
		}
		br.close();
		return randomNumbers;
	}
}
