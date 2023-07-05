import java.util.ArrayList;
import java.lang.Math;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class State implements Comparable<State> {
	
	private Cubie [][][] cube;
	private int K=6;
	private State father=null;
	private int score=0;
	private int moves=0;
	

State(int K , boolean randomized){
	this.setMoves();
	this.K=K;
	this.cube=new Cubie[6][3][3];
	if(randomized) {
		String[]colors=new String[6];
		colors[0]="G";
		colors[1]="R";
		colors[2]="B";
		colors[3]="O";
		colors[4]="Y";
		colors[5]="W";
		for (int i=0;i<6;i++) {
			for (int j=0;j<3;j++) {
				for (int k=0;k<3;k++) {		
						this.cube[i][j][k]=new Cubie(colors[i],i,j,k);
						
		}}}
		
		//xarin aplothtatas theorhsa oti sto 1o face (thn thesi cube[0][j][k]) mporoume na kanoume ola ta move down-move up/move left-move right
		//kai ola ta upoloipa sto 2o (thn thesi cube[1][j][k]), dhladh 3 moveUp kai 3 move down
		//logw summetriwn tou kuvou katalhgoume na exoume me auta, kai tis 18 pithanes kiniseis kathe katastasis
		
		List<Integer> move = new ArrayList<>(); //Oi fusikes kiniseis pou mporoume na kanoume ston kuvo (4 sto sunolo)
		List<Integer> row_line=new ArrayList<>(); //se poia grammh-sthlh tis kanoume
		List<Integer> face=new ArrayList<>();	// se poia opsi tou kuvou 
		for (int i=0;i<4;i++) {		
			move.add(i);			
			if (i<3) {row_line.add(i);}
			if(i<2) {face.add(i);}
		}
		
		//kanw tin paradoxi oti gia kathe arithmo apo to 0 mexri to 3 antistoixei kai mia kinisi
		//antisoixa apo to 0 ews to 3 mia stili-grammi antistoixa
		//kai gia to 0-1 einai to face sto opoio kanoume tin kinisi (isxuei mono gia ta moveUp-moveDown)
		
		Random r=new Random();
		for (int i=0;i<10;i++) {
			int randomAction=move.get(r.nextInt(move.size()));
			Random rRow=new Random();
			int randomRow=row_line.get(rRow.nextInt(row_line.size()));
			if(randomAction==0) {
				this.moveRight(randomRow);
			}
			if(randomAction==1) {
				this.moveLeft(randomRow);
			}
			if(randomAction==2) {
				Random rFace=new Random();
				int rface=face.get(rFace.nextInt(face.size()));
				this.moveUp(rface ,randomRow);
			}
			if(randomAction==3) {
				Random rFace=new Random();
				int rface=face.get(rFace.nextInt(face.size()));
				this.moveDown(rface ,randomRow);
			}
		}
		
		
		
		
	}	
	else {
		this.setMoves();
		this.K=K;
		String[]colors=new String[6];
		colors[0]="G";
		colors[1]="R";
		colors[2]="B";
		colors[3]="O";
		colors[4]="Y";
		colors[5]="W";
		for (int i=0;i<6;i++) {
			for (int j=0;j<3;j++) {
				for (int k=0;k<3;k++) {		
						this.cube[i][j][k]=new Cubie(colors[i],i,j,k);
						
		}}}
		this.moveRight(2);
		this.moveDown(1, 2);
		
	}
}



void setMoves() {
	if(this.father!=null) {
	this.moves=this.getFather().moves+1;
	}else {this.moves=0;}
	
}

int getMoves() {
	return this.moves;
}


State(Cubie[][][]cube) {
	this.setCube(cube);
	
}

Cubie[][][] getCube(){
	return this.cube;
}

Cubie [][][]setCube(Cubie[][][]cube) {
	this.cube=new Cubie[6][3][3];
	for(int k=0;k<6;k++) {
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				this.cube[k][i][j]=cube[k][i][j];
			}
		}
	}
	return this.cube;
}

	
public void printCube() {
		// TODO Auto-generated method stub
		for(int i=5;i<6;i++) {
			for(int j=0;j<3;j++) {
				System.out.print(" "+" "+" "+" ");
				for (int k=0;k<3;k++) {	System.out.print(cube[i][j][k].color);}
				System.out.println();}
		}
		System.out.println();
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				System.out.print(cube[3][i][j].color);
			}
			System.out.print(" ");
			for(int k=0;k<3;k++) {
				for(int j=0;j<3;j++) {
					System.out.print(cube[k][i][j].color);
				}
				System.out.print(" ");
			}
			System.out.println(" ");
		
		}
		System.out.println();
		for(int i=4;i<5;i++) {
			for(int j=0;j<3;j++) {
				System.out.print(" "+" "+" "+" ");
				for (int k=0;k<3;k++) {
				System.out.print(cube[i][j][k].color);
			}
				System.out.println();
		}}
		System.out.println();
		System.out.println("---------------------------------");
	}
	
	void setFather(State father)
		{
	        this.father = father;
	    }

	State getFather() {
		return this.father;
	}
	
	int getScore() {
		return (int)this.score;
	}
	
	@Override
	public boolean equals(Object obj) {
		for(int i=0;i<6;i++) {
			for(int j=0;j<3;j++) {
				for(int k=0;k<3;k++) {
					if(this.cube[i][j][k].color!=((State)obj).cube[i][j][k].color) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public int compareTo(State s) {
		return Integer.compare(this.score, s.score);
	}
	
	
	void evaluate()
    {
		this.score=Math.max(this.Manhattan3Dcorners(), this.Manhattan3Dsides())+this.getMoves();
    }

	
	 ArrayList<State> getChildren(){
		 ArrayList<State> children = new ArrayList<>();
		 State child=new State(this.cube);
	        for(int i=0;i<2;i++) {
	        	for(int j=0;j<3;j++) {
	        			//to moveUP 
	        			child=new State(this.cube);
	        			child.moveUp(i, j);
	        			child.setFather(this);
	        			child.setMoves();
	        			child.evaluate();
	        			children.add(child);
	        			
	        			//To moveDown
	        			child=new State(this.cube);
	        			child.moveDown(i, j);
	        			child.setFather(this);
	        			child.setMoves();
	        			child.evaluate();
	        			children.add(child);
	        			
	        			
	        		
	        	}
	        }
	        
	    	for(int j=0;j<3;j++) {
    			//to moveRight
    			child=new State(this.cube);
    			child.moveRight(j);
    			child.setFather(this);
    			child.setMoves();
    			child.evaluate();
    			children.add(child);
    			
    			//To moveLeft
    			child=new State(this.cube);
    			child.moveLeft(j);
    			child.setFather(this);
    			child.evaluate();
    			children.add(child);
    			
    		
    	}
	    	return children;
    }
	        
		 
		 

	
public boolean isFinal() {
		// TODO Auto-generated method stub
		int temp=0;	
		boolean flag;
		for (int i=0;i<6;i++) {
			flag=true;
			String tempColor=cube[i][0][0].color;
			for (int j=0;j<3;j++) { 
				for(int k=0;k<3;k++) {
					if(!(cube[i][j][k].color==tempColor)) {
						flag=false;
					
			}
			}}
			if (flag==true) temp++;
		}
		if (temp>=this.K) {return true;}
		else {return false;}
		
		
	}

	
public boolean moveUp(int side, int row) {
		// TODO Auto-generated method stub
		int tempside=side;
		if(row==1) {
			this.moveDown(tempside, 0);
			this.moveDown(tempside,2);
			return true;}
		
		Cubie[] tempcube;
		tempcube=new Cubie[3];
		int opossiteRow=2-row; //Ousastika to move up , sthn antitheti pleura ephrreazei thn sumplirwmatikh sthlh 
	if (side==0) {
		for (int i=0;i<3;i++) {
			tempcube[i]=cube[2][2-i][opossiteRow];
		}
		for (int i=0;i<3;i++) {
			cube[2][i][opossiteRow]=cube[5][2-i][row];
		}
		for (int i=0;i<3;i++) {
			cube[5][i][row]=cube[0][i][row];
		}
		for (int i=0;i<3;i++) {
			cube[0][i][row]=cube[4][i][row];
		}
		for (int i=0;i<3;i++) {
			cube[4][i][row]=tempcube[i];
		}
		//
		if (row==0) {
			Cubie [][] temp=new Cubie[3][3];
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					temp[i][j]=cube[3][j][2-i];
					
				}
			}
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					cube[3][i][j]=temp[i][j];
					
				}
			}
			
		}else if(row==2) {
			Cubie [][] temp=new Cubie[3][3];
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					temp[i][j]=cube[1][i][j];
					
				}
			}
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					cube[1][j][2-i]=temp[i][j];
					
				}
			}}
		
	return true;
	}else if(side==1) {
		for (int i=0;i<3;i++) {
			tempcube[i]=cube[3][i][opossiteRow];
		}
		for (int i=0;i<3;i++) {
			cube[3][2-i][opossiteRow]=cube[5][2-row][i];
		}
		for (int i=0;i<3;i++) {
			cube[5][2-row][i]=cube[1][i][row];
		}
		for (int i=0;i<3;i++) {
			cube[1][2-i][row]=cube[4][row][i];
		}
		for (int i=0;i<3;i++) {
			cube[4][row][i]=tempcube[i];
		}
		//
		if (row==0) {
			Cubie [][] temp=new Cubie[3][3];
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					temp[i][j]=cube[0][j][2-i];
					
				}
			}
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					cube[0][i][j]=temp[i][j];
					
				}
			}
			
		}else if(row==2) {
		Cubie [][] temp=new Cubie[3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				temp[i][j]=cube[2][i][j];
				
			}
		}
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cube[2][j][2-i]=temp[i][j];
				
			}
		}
		
	}


	return true;}
	return true;

	}

	
	public boolean moveDown(int side, int row) {
		// TODO Auto-generated method stub
		int tempside=side;
		if(row==1) {
			this.moveUp(tempside, 0);
			this.moveUp(tempside,2);
			return true;
		}
		Cubie[] tempcube;
		tempcube=new Cubie[3];
		int opossiteRow=2-row; 
	if (side==0) {
		for (int i=0;i<3;i++) {
			tempcube[i]=cube[5][i][row];
		}
		for (int i=0;i<3;i++) {
			cube[5][2-i][row]=cube[2][i][opossiteRow];
		}
		for (int i=0;i<3;i++) {
			cube[2][2-i][opossiteRow]=cube[4][i][row];
		}
		for (int i=0;i<3;i++) {
			cube[4][i][row]=cube[0][i][row];
		}
		for (int i=0;i<3;i++) {
			cube[0][i][row]=tempcube[i];
		}
		
		if (row==2) {
			Cubie [][] temp=new Cubie[3][3];
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					temp[i][j]=cube[1][j][2-i];
					
				}
			}
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					cube[1][i][j]=temp[i][j];
					
				}
			}
			
	}else if(row==0) {
		Cubie [][] temp=new Cubie[3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				temp[i][j]=cube[3][i][j];
				
			}
		}
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cube[3][j][2-i]=temp[i][j];
				
			}
		}
		
	}return true;}
	else if (side==1) {
		
		for (int i=0;i<3;i++) {
			tempcube[i]=cube[3][i][opossiteRow];
		}
		for (int i=0;i<3;i++) {
			cube[3][i][opossiteRow]=cube[4][row][i];
		}
		for (int i=0;i<3;i++) {
			cube[4][row][2-i]=cube[1][i][row];
		}
		for (int i=0;i<3;i++) {
			cube[1][i][row]=cube[5][2-row][i];
		}
		for (int i=0;i<3;i++) {
			cube[5][2-row][i]=tempcube[2-i];
		}
		//
		if (row==0) {
			Cubie [][] temp=new Cubie[3][3];
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					temp[i][j]=cube[0][i][j];
					
				}
			}
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					cube[0][j][2-i]=temp[i][j];
					
				}
			}
			
		}else if(row==2) {
		Cubie [][] temp=new Cubie[3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				temp[i][j]=cube[2][j][2-i];
				
			}
		}
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cube[2][i][j]=temp[i][j];
				
			}
		}}
	return true;}
	return true;
	}

	
	public boolean moveRight(int line) {
		// TODO Auto-generated method stub
		if(line==1) {
			this.moveLeft(0);
			this.moveLeft(2);
			return true;
		}
		if (line>=0&&line<=3) {
			Cubie[] tempcube;
			tempcube=new Cubie[3];
			for (int i=0;i<3;i++) {
				tempcube[i]=cube[0][line][i];
			}
			for (int i=0;i<3;i++) {
				cube[0][line][i]=cube[3][line][i];
			}
			for (int i=0;i<3;i++) {
				cube[3][line][i]=cube[2][line][i];
			}
			for (int i=0;i<3;i++) {
				cube[2][line][i]=cube[1][line][i];
			}
			for (int i=0;i<3;i++) {
				cube[1][line][i]=tempcube[i];
			}
			
			if(line==0) {
				Cubie [][] temp=new Cubie[3][3];
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						temp[i][j]=cube[5][j][2-i];
						
					}
				}
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						cube[5][i][j]=temp[i][j];
						
					}
				}
				
			}else if(line==2) {

				Cubie [][] temp=new Cubie[3][3];
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						temp[i][j]=cube[4][i][j];
						
					}
				}
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						cube[4][j][2-i]=temp[i][j];
						
					}
				}
		}else {
			
		}
			
			
			return true;}
		return true;

	}

	
	public boolean moveLeft(int line) {
		// TODO Auto-generated method stub
		if (line==1) {
			this.moveRight(0);
			this.moveRight(2);
			return true;
		}
		if (line>=0&&line<=3) {	
			Cubie[] tempcube;
			tempcube=new Cubie[3];
			for (int i=0;i<3;i++) {
				tempcube[i]=cube[3][line][i];
			}
			for (int i=0;i<3;i++) {
				cube[3][line][i]=cube[0][line][i];
			}
			for (int i=0;i<3;i++) {
				cube[0][line][i]=cube[1][line][i];
			}
			for (int i=0;i<3;i++) {
				cube[1][line][i]=cube[2][line][i];
			}
			for (int i=0;i<3;i++) {
				cube[2][line][i]=tempcube[i];
			}

			if(line==2) {
				Cubie [][] temp=new Cubie[3][3];
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						temp[i][j]=cube[4][j][2-i];
						
					}
				}
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						cube[4][i][j]=temp[i][j];
						
					}
				}
				
			}else if(line==0) {
			
				Cubie [][] temp=new Cubie[3][3];
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						temp[i][j]=cube[5][i][j];
						
					}
				}
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						cube[5][j][2-i]=temp[i][j];
						
					}
				}
			}
		return true;}else {return true;}}
	
	int Manhattan3Dcorners() {
		for (int i=0;i<6;i++) {
			for (int j=0;j<3;j+=2) {
				for(int k=0;k<3;k+=2) {
					this.score+=Math.abs(i-this.cube[i][j][1].i)+Math.abs(j-this.cube[i][j][1].j)+Math.abs(k-this.cube[i][j][k].k);
				}
			}
		}
		return score/4;
	}
		 
	int Manhattan3Dsides() {
		//Tha vro to Manhattan Distance gia tis pleures tou kuvou pou sto sunolo einai 12
		
		for(int i=0;i<4;i++) {
			if(i%2==0) {
				for(int j=0;j<3;j++) {
					if(j%2==0) {
						 this.score+=Math.abs(i-this.cube[i][j][1].i)+Math.abs(j-this.cube[i][j][1].j)+Math.abs(1-this.cube[i][j][1].k);
						
					}
					else {
						for(int k=0;k<3;k=k+2) {
							 this.score+=Math.abs(i-this.cube[i][j][k].i)+Math.abs(j-this.cube[i][j][k].j)+Math.abs(k-this.cube[i][j][k].k);
							
						}
					}
					
				}
				
			}
			else {
				for(int j=0;j<3;j=j+2) {
					this.score+=Math.abs(i-cube[i][j][1].i)+Math.abs(j-cube[i][j][1].j)+Math.abs(1-cube[i][j][1].k);
				}
				
			}
			
		}
		return score/4;
	}
	
	
	
	
	
	

	
	
	

	
}
	

		
		


	


