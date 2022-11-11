import java.lang.Math;

public class hanoiTower {
	//Fields
	public int height;
	public int platformA[];
	public int platformB[];
	public int platformC[];
	
	
	
	
	//Constructor
	public hanoiTower(int height) {
		this.height = height;
		this.platformA = new int[this.height];
		this.platformB = new int[this.height];
		this.platformC = new int[this.height];
		for(int i = 0; i < this.platformA.length; i++) {
			this.platformA[i] = i+1;				
		}
	}
	
	
	
	
	public void move(int platformX[], int platformY[]) {
		int diskToMove = 0;
		for(int i = 0; i < platformX.length; i++) {
			if(platformX[i] != 0) {
				diskToMove = i;
				
				break;
			}
		}
		for(int i = 0; i < platformY.length; i++) {
			if (i == platformY.length - 1 && platformY[i] == 0) {
				platformY[i] = platformX[diskToMove];
				platformX[diskToMove] = 0;
				break;
			}
			else if (platformY[i] == 0 && platformY[i+1] > platformX[diskToMove]) {
				platformY[i] = platformX[diskToMove];
				platformX[diskToMove] = 0;
				break;
			}
			else if(platformY[i] == 0 && platformY[i+1] < platformX[diskToMove] && platformY[i+1] != 0){
				System.out.println("UNVALID MOVE");
				break;
			}
			
		}
		
	}
	
	
	
	//inverter
	
	public static String[] inverter(String[] arr, char a, char b) {
		String[] invertedArr = new String[arr.length];
		for(int i = 0; i<arr.length; i++) {
			Boolean char0Processed = false;
			Boolean char1Processed = false;
			invertedArr[i] = arr[i];
			if(invertedArr[i].charAt(0) == a && char0Processed == false) {
				invertedArr[i] = b + "" + invertedArr[i].charAt(1);
				char0Processed = true;
			}
			if(invertedArr[i].charAt(0) == b && char0Processed == false) {
				invertedArr[i] = a + "" + invertedArr[i].charAt(1);
				char0Processed = true;
			}
			if(invertedArr[i].charAt(1) == a && char1Processed == false) {
				invertedArr[i] = invertedArr[i].charAt(0) + "" + b;
				char1Processed = true;
			}
			if(invertedArr[i].charAt(1) == b && char1Processed == false) {
				invertedArr[i] = invertedArr[i].charAt(0) + "" + a;
				char1Processed = true;
			}
		}
		
		return invertedArr;
	}
	
	
	
	
	//Generate array of strings so that when they applied to tower it gets solved
	
	public String[] solve(hanoiTower x) {
		if(x.height == 1) {
			String[] array = {"AC"};
			return array;
		}
		else {
			String[] prev_arr = solve(new hanoiTower(x.height - 1));
			String[] array = new String[((int) Math.pow(2, x.height)) - 1];
			for(int i = 0; i<array.length; i++) {
				if(i<array.length/2) {
					String[] firstHalfInverted = inverter(prev_arr, 'B', 'C');
					array[i] = firstHalfInverted[i];
				}
				else if(i == array.length/2) {
					array[i] = "AC";
				}
				else {
					String[] secondHalfInverted = inverter(prev_arr, 'A', 'B');
					array[i] = secondHalfInverted[i - array.length/2 - 1];
				}
			}
			return array;
		}
	}
	
	
	//Read sequence of actions and 
	public void draw(String[] solution) {
		System.out.println("===============");
		int from[] = null;
		int to[] = null;
		for(int i = 0; i < solution.length; i++) {
			if(solution[i].charAt(0) == 'A') {
				from = this.platformA;
			}
			else if(solution[i].charAt(0) == 'B') {
				from = this.platformB;
			}
			else if(solution[i].charAt(0) == 'C') {
				from = this.platformC;
			}
			if(solution[i].charAt(1) == 'A') {
				to = this.platformA;
			}
			else if(solution[i].charAt(1) == 'B') {
				to = this.platformB;
			}
			else if(solution[i].charAt(1) == 'C') {
				to = this.platformC;
			}
			this.move(from, to);
			this.print();
			System.out.println("==========");
		}
		
	}
	
	
	
	
	
	
	
	public void print() {
		for(int i = 0; i < this.height; i++) {
			if(this.platformA[i] == 0) {
				System.out.print("|  ");
			}
			else {
				System.out.print(platformA[i] + "  ");
			}
			if(this.platformB[i] == 0) {
				System.out.print("|  ");
			}
			else {
				System.out.print(platformB[i] + "  ");
			}
			if(this.platformC[i] == 0) {
				System.out.print("|\n");
			}
			else {
				System.out.print(platformC[i] + "\n");
			}
			
		}
	}
	

	public static void main(String[] args) {
		hanoiTower test = new hanoiTower(4);
		test.print();
		String[] solution = test.solve(test);
		test.draw(solution);

	}

}
