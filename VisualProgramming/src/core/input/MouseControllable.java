package core.input;

import java.awt.event.MouseEvent;

public interface MouseControllable {
	public void mouseClicked(final MouseEvent e);
	public void mousePressed(final MouseEvent e);
	public void mouseDragged(final MouseEvent e);
	public void mouseReleased(final MouseEvent e);
}
