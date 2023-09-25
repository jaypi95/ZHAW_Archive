package ch.zhaw.ads;

import javax.swing.*;
import java.awt.*;
import java.util.*;


public class GraphicPanel extends JPanel {

	String figure;

	public void setFigure(String figure) {
		this.figure = figure;
		paint(getGraphics());
	}

	private void drawRect(Graphics g, double x, double y, double width, double height, String style) {
		int w = getWidth();
		int h = getHeight();
		int ix0 = (int) (w * x);
		int iy0 = (int) (h * y);
		int ix1 = (int) (w * (x + width));
		int iy1 = (int) (h * (y + height));
		if (style.equals("draw")) {
			g.drawRect(ix0, h - iy1, ix1 - ix0, iy1 - iy0);
		} else {
			g.fillRect(ix0, h - iy1, ix1 - ix0, iy1 - iy0);
		}
	}

	private void drawFigure(Graphics g) {
		if (figure != null) {
			int w = getWidth();
			int h = getHeight();
			g.setColor(Color.black);
			StringTokenizer tok = new StringTokenizer(figure, " <>=/,\"\n");
			while (tok.hasMoreTokens()) {
				String fig = tok.nextToken();
				if (fig.equals("line")) {
					tok.nextToken();
					double x1 = Double.parseDouble(tok.nextToken());
					tok.nextToken();
					double y1 = Double.parseDouble(tok.nextToken());
					tok.nextToken();
					double x2 = Double.parseDouble(tok.nextToken());
					tok.nextToken();
					double y2 = Double.parseDouble(tok.nextToken());
					g.drawLine((int) (x1 * w), h - (int) (y1 * h),
							(int) (x2 * w), h - (int) (y2 * h));
				} else if (fig.equals("rect")) {
					tok.nextToken();
					double x = Double.parseDouble(tok.nextToken());
					tok.nextToken();
					double y = Double.parseDouble(tok.nextToken());
					tok.nextToken();
					double width = Double.parseDouble(tok.nextToken());
					tok.nextToken();
					double height = Double.parseDouble(tok.nextToken());
					tok.nextToken();
					String style = tok.nextToken();
					drawRect(g, x, y, width, height, style);
				} else if (fig.equals("color")) {
					tok.nextToken();
					int red = Integer.parseInt(tok.nextToken());
					tok.nextToken();
					int green = Integer.parseInt(tok.nextToken());
					tok.nextToken();
					int blue = Integer.parseInt(tok.nextToken());
					g.setColor(new Color(red, green, blue));
				}
			}
		}
	}

	private void clear(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		g.setColor(new Color(240, 240, 240));
		g.fillRect(0, 0, w, h);
	}

	public void paint(Graphics g) {
		clear(g);
		drawFigure(g);
	}
}