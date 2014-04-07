package game.dev.specter_stalker.model;

/*
 * Class for creating any type of polygon given vertices/coordinates and number of sides
 */
public class Polygon {

	// Polygon coodinates
	private final int[] polyY, polyX;

	// Number of sides in the polygon.
	private final int polySides;

	/*
	 * Constructor for Polygon, takes array of points x, array of points y, and
	 * number of sides
	 */
	public Polygon(final int[] px, final int[] py, final int ps) {
		polyX = px;
		polyY = py;
		polySides = ps;
	}

	/*
	 * Checks if the Polygon contains a given x or y point
	 */
	public boolean contains(final float x, final float y) {
		// X,Y is not within the Polygon
		boolean isWithin = false;
		// For however many sides the Polygon has...
		for (int i = 0, j = polySides - 1; i < polySides; j = i++) {
			// Check if the Y is within the range of the Y points
			if ((polyY[i] < y && polyY[j] >= y)
					|| (polyY[j] < y && polyY[i] >= y)) {
				// Check if the X is within the range of the X points
				if (polyX[i] + (y - polyY[i]) / (polyY[j] - polyY[i])
						* (polyX[j] - polyX[i]) < x) {
					// Then the point is within the polygon
					isWithin = !isWithin;
				}
			}
		}
		// Return if it is within
		return isWithin;
	}

}
