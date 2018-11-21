import java.awt.Polygon;

public class Wall{

	private int inNum;

	public Wall(int inNum){
		this.inNum = inNum;
    }
    
    public Polygon getPoly(){
        Polygon myPolygon = new Polygon();
        if (inNum == 1){
            myPolygon.addPoint(350, 350);
            myPolygon.addPoint(420, 420);
            myPolygon.addPoint(420, 770);
            myPolygon.addPoint(350, 840);
        }
        else if (inNum == 2){
            myPolygon.addPoint(350+70, 350+70);
            myPolygon.addPoint(420+70, 420+70);
            myPolygon.addPoint(420+70, 770-70);
            myPolygon.addPoint(350+70, 840-70);
        }
        else if (inNum == 3){
            myPolygon.addPoint(350+70*2, 350+70*2);
            myPolygon.addPoint(420+70*2, 420+70*2);
            myPolygon.addPoint(420+70*2, 770-70*2);
            myPolygon.addPoint(350+70*2, 840-70*2);
        }
        else if (inNum == 4){
            myPolygon.addPoint(840, 350);
            myPolygon.addPoint(770, 420);
            myPolygon.addPoint(770, 770);
            myPolygon.addPoint(840, 840);
        }
        else if (inNum == 5){
            myPolygon.addPoint(840-70, 350+70);
            myPolygon.addPoint(770-70, 420+70);
            myPolygon.addPoint(770-70, 770-70);
            myPolygon.addPoint(840-70, 840-70);
        }
        else if (inNum == 6){
            myPolygon.addPoint(840-70*2, 350+70*2);
            myPolygon.addPoint(770-70*2, 420+70*2);
            myPolygon.addPoint(770-70*2, 770-70*2);
            myPolygon.addPoint(840-70*2, 840-70*2);
        }
        else if (inNum == 7){
            myPolygon.addPoint(490, 490);
            myPolygon.addPoint(700, 490);
            myPolygon.addPoint(700, 700);
            myPolygon.addPoint(490, 700);
        }
        else if (inNum == 8){
            myPolygon.addPoint(420, 420);
            myPolygon.addPoint(770, 420);
            myPolygon.addPoint(770, 770);
            myPolygon.addPoint(420, 770);
        }
        else if (inNum == 9){
            myPolygon.addPoint(350, 350);
            myPolygon.addPoint(840, 350);
            myPolygon.addPoint(840, 840);
            myPolygon.addPoint(350, 840);
        }

        return myPolygon;
    }
	//maybe make a method to return a polygon....it will simplify things
}