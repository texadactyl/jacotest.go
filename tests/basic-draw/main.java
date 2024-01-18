public class main {

	public static void main(String[] args) {

		boolean visible = false;

		// -------------------------------------------------- Frame #1

		DrawToolkit dtk = new DrawToolkit(visible);
		System.out.println("DrawToolkit initialised ok");

		System.out.println("square .....");
        dtk.square(.2, .8, .1);

		System.out.println("filledSquare .....");
        dtk.filledSquare(.3, .5, .2);

		System.out.println("rectangle .....");
        dtk.rectangle(.4, .6, .1, .2);

		System.out.println("filledRectangle .....");
        dtk.filledRectangle(.7, .9, .2, .3);

		System.out.println("circle .....");
        dtk.circle(.8, .2, .2);

		System.out.println("pen color (RED) and radius .....");
        dtk.setPenColor(dtk.RED);
        dtk.setPenRadius(.02);

		System.out.println("diamond (BLUE) and radius .....");
		dtk.setPenRadius();
		dtk.setPenColor(dtk.BLUE);

		System.out.println("ellipse .....");
        dtk.ellipse(.8, .2, .2, .3);

		System.out.println("filledEllipse .....");
        dtk.filledEllipse(.8, .2, .2, .3);

		System.out.println("arc .....");
        dtk.arc(.8, .2, .1, 200, 45);

		System.out.println("polygon .....");
		double[] x = { .1, .2, .3, .2 };
		double[] y = { .2, .3, .2, .1 };
		dtk.polygon(x, y);

		System.out.println("filledPolygon .....");
		dtk.filledPolygon(x, y);

		System.out.println("save frame1.png .....");
		dtk.show();
		dtk.save("frame1.png");

		// -------------------------------------------------- Frame #2

		System.out.println("Change canvas size .....");
		dtk.setCanvasSize(512, 512);

		System.out.println("setScale .....");
		dtk.setScale(0.2, 0.8);

		System.out.println("black text .....");
		dtk.setPenColor(dtk.BLACK);
		dtk.text(0.2, 0.4, "black text");

		dtk.clear(dtk.GREEN);

		System.out.println("white text .....");
		dtk.setPenColor(dtk.WHITE);
		dtk.text(0.4, 0.6, "white text");

		dtk.textLeft(0.6, 0.8, "white textLeft");
		dtk.textRight(0.8, 0.999, "white textRight");

		System.out.println("save frame2.jpeg .....");
		dtk.show();
		dtk.save("frame2.jpeg");

		System.out.println("Success!");

		if (! visible)
			System.exit(0);
	}

}